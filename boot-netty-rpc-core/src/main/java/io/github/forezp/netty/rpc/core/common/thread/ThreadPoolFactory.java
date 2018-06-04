package io.github.forezp.netty.rpc.core.common.thread;

import io.github.forezp.netty.rpc.core.common.enums.ThreadQueueType;
import io.github.forezp.netty.rpc.core.common.enums.ThreadRejectedPolicyType;
import io.github.forezp.netty.rpc.core.config.CommonProperties;
import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ${DESCRIPTION}
 *
 * @author fangzhipeng
 * create 2018-05-28
 **/
public class ThreadPoolFactory {

    Logger LOG = LoggerFactory.getLogger( ThreadPoolFactory.class );
    private static Map<String, ThreadPoolExecutor> serverThreadPoolMap = Maps.newConcurrentMap();
    private static Map<String, ThreadPoolExecutor> clientThreadPoolMap = Maps.newConcurrentMap();
    private static CommonProperties commonProperties;


    public static ThreadPoolExecutor createServerPoolExecutor(String interfaceName) {

        return createThreadPoolExecutor( serverThreadPoolMap, interfaceName, commonProperties.getServerPoolCoreSize()
                , commonProperties.getServerPoolMaxSize(), commonProperties.getServerPoolKeepAliveTime(),
                commonProperties.getServerPoolQunueType(), commonProperties.getServerPoolQunueSize(),
                commonProperties.getServerPoolRejectType() );
    }


    public static ThreadPoolExecutor createThreadPoolDefaultExecutor() {
        return createThreadPoolExecutor(CommonProperties.CPUS * 1,
                CommonProperties.CPUS * 2,
                15 * 60 * 1000,
                false);
    }


    public static ThreadPoolExecutor createThreadPoolExecutor( int corePoolSize,  int maximumPoolSize, long keepAliveTime, boolean allowCoreThreadTimeOut) {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue(  ) ,
                new BlockingPolicyWithReport());
        threadPoolExecutor.allowCoreThreadTimeOut(allowCoreThreadTimeOut);

        return threadPoolExecutor;
    }

    public static ThreadPoolExecutor createClientPoolExecutor(String interfaceName) {

        return createThreadPoolExecutor( clientThreadPoolMap, interfaceName, commonProperties.getClientPoolCoreSize()
                , commonProperties.getClientPoolMaxSize(), commonProperties.getClientPoolKeepAliveTime(),
                commonProperties.getClientPoolQunueType(), commonProperties.getClientPoolQunueSize(),
                commonProperties.getClientPoolRejectType() );
    }

    public static ThreadPoolExecutor createThreadPoolExecutor(Map<String, ThreadPoolExecutor> executorMap, String interfaceName,
                                                              int coreSize, int maxSize, Long keepAliveTime, String queneType,
                                                              int queueSize, String rejectType) {

        if (executorMap.get( interfaceName ) != null) {
            return executorMap.get( interfaceName );
        } else {
            ThreadPoolExecutor newThreadPool = new ThreadPoolExecutor( CommonProperties.CPUS * coreSize
                    , CommonProperties.CPUS * maxSize, keepAliveTime, TimeUnit.MILLISECONDS
                    , createQuene( queneType, queueSize ), createThreadFactory( interfaceName )
                    , createRejectedExecutionHandler( rejectType ) );
            if (newThreadPool != null) {
                executorMap.putIfAbsent( interfaceName, newThreadPool );
            }
            return newThreadPool;
        }
    }

    private static RejectedExecutionHandler createRejectedExecutionHandler(String rejectedPolicy) {

        ThreadRejectedPolicyType rejectedPolicyType = ThreadRejectedPolicyType.fromString( rejectedPolicy );

        switch (rejectedPolicyType) {
            case BLOCKING_POLICY_WITH_REPORT:
                return new BlockingPolicyWithReport();
            case CALLER_RUNS_POLICY_WITH_REPORT:
                return new CallerRunsPolicyWithReport();
            case ABORT_POLICY_WITH_REPORT:
                return new AbortPolicyWithReport();
            case REJECTED_POLICY_WITH_REPORT:
                return new RejectedPolicyWithReport();
            case DISCARDED_POLICY_WITH_REPORT:
                return new DiscardedPolicyWithReport();
        }

        return null;
    }

    private static ThreadFactory createThreadFactory(final String threadName) {

        return new ThreadFactory() {
            private AtomicInteger number = new AtomicInteger( 0 );

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread( runnable, threadName + "-" + number.getAndIncrement() );
            }
        };

    }

    private static BlockingQueue<Runnable> createQuene(String queue, int queueCapacity) {

        ThreadQueueType queueType = ThreadQueueType.fromString( queue );
        switch (queueType) {
            case LINKED_BLOCKING_QUEUE:
                return new LinkedBlockingQueue<>( queueCapacity );
            case ARRAY_BLOCKING_QUEUE:
                return new ArrayBlockingQueue<>( queueCapacity );
            case SYNCHRONOUS_QUEUE:
                return new SynchronousQueue<>();
        }
        return null;
    }


    public static void setCommonProperties(CommonProperties commonProperties) {
        ThreadPoolFactory.commonProperties = commonProperties;
    }
}
