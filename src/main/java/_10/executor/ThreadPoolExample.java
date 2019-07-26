package _10.executor;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {
    
    final static class MyTask implements Runnable {
        private int taskId;

        public MyTask(int id) {
            this.taskId = id;
        }

        @Override
        public void run() {
            System.out.println("Task ID : " + this.taskId + " performed by " + Thread.currentThread().getName());
        }
    }

    static public class MyHandler implements UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("Uncaught exception");
        }
    }
	
	public static void main(String args[]) throws InterruptedException {
		//ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
	    ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 10000, TimeUnit.DAYS, new LinkedBlockingQueue<Runnable>(10));
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
//		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
//		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
		
        final MyHandler eh = new MyHandler();

        ThreadFactory threadFactory = new ThreadFactory() {

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setUncaughtExceptionHandler(eh);
                return t;
            }
        };

        executor.setThreadFactory(threadFactory);

        for (int i = 0; i < 100; i++) {
            executor.execute(new MyTask(i));
        }

        executor.shutdown();
        executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);

        System.out.println("PoolSize: " + executor.getPoolSize());
        System.out.println("CorePoolSize: " + executor.getCorePoolSize());
        System.out.println("ActiveCount: " + executor.getActiveCount());
        System.out.println("CompletedTaskCount: " + executor.getCompletedTaskCount());
        System.out.println("TaskCount: " + executor.getTaskCount());
        System.out.println("isShutdown: " + executor.isShutdown());
        System.out.println("isTerminated: " + executor.isTerminated());
	}
}
