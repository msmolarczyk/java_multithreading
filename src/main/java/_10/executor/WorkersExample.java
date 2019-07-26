package _10.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WorkersExample {

    private static class RunningThread implements Runnable {

        private String command;

        public RunningThread(String s) {
            this.command = s;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " Start. Command = " + command);
            processCommand();
            System.out.println(Thread.currentThread().getName() + " End.");
        }

        private void processCommand() {
            try {
                Thread.sleep((long) (Math.random() * 2000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return this.command;
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 4, 10, TimeUnit.SECONDS,
//                new ArrayBlockingQueue<Runnable>(2), Executors.defaultThreadFactory(), new RejectedExecutionHandler() {
//                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//                        System.out.println(r.toString() + " is rejected");
//                    }
//                });

        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            Runnable thread = new RunningThread("" + i);
            executor.execute(thread);
        }
        executor.shutdown();
        executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
//        printStatus((ThreadPoolExecutor) executor);
    }

    public static void printStatus(ThreadPoolExecutor executor) {
        System.out.println("\nFinished all threads");
        System.out.println("PoolSize: " + executor.getPoolSize());
        System.out.println("CorePoolSize: " + executor.getCorePoolSize());
        System.out.println("ActiveCount: " + executor.getActiveCount());
        System.out.println("CompletedTaskCount: " + executor.getCompletedTaskCount());
        System.out.println("TaskCount: " + executor.getTaskCount());
        System.out.println("isShutdown: " + executor.isShutdown());
        System.out.println("isTerminated: " + executor.isTerminated());
    }
}
