package com.zhiyong.saas.sequence.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author du_imba
 */
public class NamedThreadFactory implements ThreadFactory {

    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final String namePrefix;
    private final boolean daemon;

    /**
     *
     * @param namePrefix
     * @param daemon
     */
    public NamedThreadFactory(String namePrefix, boolean daemon) {
        this.daemon = daemon;
        SecurityManager ss = System.getSecurityManager();
        group = (ss != null) ? ss.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        this.namePrefix = namePrefix;
    }

    public NamedThreadFactory(String namePrefix) {
        this(namePrefix, false);
    }

    @Override
    public Thread newThread(Runnable rr) {
        Thread tt = new Thread(group, rr, namePrefix + "-thread-" + threadNumber.getAndIncrement(), 0);
        tt.setDaemon(daemon);
        return tt;
    }

}
