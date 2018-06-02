package io.github.forezp.netty.rpc.core.protocol.server;

import io.github.forezp.netty.rpc.core.common.entity.NettyRpcRequest;
import io.github.forezp.netty.rpc.core.common.entity.NettyRpcResponse;
import io.github.forezp.netty.rpc.core.common.exception.CommonException;
import io.github.forezp.netty.rpc.core.monitor.Monitor;
import io.github.forezp.netty.rpc.core.util.NettyRpcApplication;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by forezp on 2018/5/26.
 */
public class RequestHandler extends AbstractRequestHandler {


    @Override
    public void handle(NettyRpcRequest request, NettyRpcResponse response) {
        if (request.isHeatBeat()) {
            return;
        }
        beforeHandle(response);
        Object serviceBean = getInvokeBean(request);
        if (serviceBean == null) {
            throw new CommonException("no bean found for handle request");
        }
        Class<?> clz = serviceBean.getClass();
        // 获取所要调用的方法
        Method method;
        Object result = null;
        try {
            method = clz.getMethod(request.getMethod(), request.getParamTypes());
            result = method.invoke(serviceBean, request.getParams());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //TODO monitor
        response.setMessageId(request.getMessageId());
        response.setInterfaceClass(request.getInterfaceClass());
        response.setSyn(request.isSyn());
        response.setStartTime(request.getStartTime());
        response.setResult(result);
        response.setTraceId(request.getTraceId());
        afterHandle(response);

        Monitor monitor = excutorContainer.getMonitor();
        if (monitor != null) {
            monitor.execute(monitor.create(response));
        }

    }

    private Object getInvokeBean(NettyRpcRequest request) {

        if (!StringUtils.isEmpty(request.getRpcClz())
                && NettyRpcApplication.containsBean(request.getRpcClz())) {
            return NettyRpcApplication.getBean(request.getRpcClz());
        }

        return NettyRpcApplication.getBean(request.getInterfaceClass());

    }
}
