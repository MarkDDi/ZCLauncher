package com.word.wordinsidehome.service.image;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

class ImageLoaderEngine {

    private ExecutorService taskDistributor;
    private Executor taskExecutor;
    private Executor taskExecutorForCachedImages;

    ImageLoaderEngine() {
        super();
        LinkedBlockingQueue linkqueue =new LinkedBlockingQueue();
        taskExecutorForCachedImages= new ThreadPoolExecutor(3, 3, 0, TimeUnit.MILLISECONDS, ((BlockingQueue)
                linkqueue), new DefaultThreadFactory(4));
        this.taskDistributor = Executors.newCachedThreadPool();
    }

    void submit(LoadAndDisplayImageTask task) {
        this.taskExecutorForCachedImages.execute(((Runnable)task));
    }

    class DefaultThreadFactory implements ThreadFactory {
        private final ThreadGroup group;
        private final String namePrefix;
        private final AtomicInteger poolNumber = new AtomicInteger(1);;
        private final AtomicInteger threadNumber;
        private final int threadPriority;


        

        DefaultThreadFactory(int threadPriority) {
            super();
            this.threadNumber = new AtomicInteger(1);
            this.threadPriority = threadPriority;
            SecurityManager v0 = System.getSecurityManager();
            ThreadGroup v1 = v0 != null ? v0.getThreadGroup() : Thread.currentThread().getThreadGroup();
            this.group = v1;
            this.namePrefix = "uil-pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread v0 = new Thread(this.group, r, String.valueOf(this.namePrefix) + this.threadNumber
                    .getAndIncrement(), 0);
            if(v0.isDaemon()) {
                v0.setDaemon(false);
            }

            v0.setPriority(this.threadPriority);
            return v0;
        }
    }
}

