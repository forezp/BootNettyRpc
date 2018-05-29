package io.github.forezp.netty.rpc.core.common.entity;



import java.io.Serializable;
import java.util.concurrent.CyclicBarrier;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-21
 **/
public class ResponseSyncEntity implements Serializable {
    private static final long serialVersionUID = 5149083798031489971L;

    private Object result;
    private Exception exception;
    private CyclicBarrier barrier;

    public ResponseSyncEntity() {

    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public CyclicBarrier getBarrier() {
        return barrier;
    }

    public void setBarrier(CyclicBarrier barrier) {
        this.barrier = barrier;
    }
}