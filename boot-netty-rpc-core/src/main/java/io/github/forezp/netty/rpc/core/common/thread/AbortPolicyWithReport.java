package io.github.forezp.netty.rpc.core.common.thread;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ThreadPoolExecutor;

// 任务饱和时, 抛弃任务，抛出异常
public class AbortPolicyWithReport extends ThreadPoolExecutor.AbortPolicy {
    private static final Logger LOG = LoggerFactory.getLogger(AbortPolicyWithReport.class);

    private String threadName;

    public AbortPolicyWithReport() {
        this(null);
    }

    public AbortPolicyWithReport(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
        if (threadName != null) {
            LOG.error("Thread pool [{}] is exhausted, executor={}", threadName, executor.toString());
        }

        super.rejectedExecution(runnable, executor);
    }
}