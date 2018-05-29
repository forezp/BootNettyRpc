package io.github.forezp.netty.rpc.core.common.thread;


public interface RejectedRunnable extends Runnable {
    // 如果任务被拒绝，用户自行处理
    void rejected();
}