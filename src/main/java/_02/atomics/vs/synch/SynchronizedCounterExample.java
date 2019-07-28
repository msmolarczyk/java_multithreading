package _02.atomics.vs.synch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedCounterExample {

    static class Task implements Runnable {
        private int taskId;
        private SynchronizedCounter synchronizedCounter;

        public Task(int id, SynchronizedCounter atomicCounter) {
            this.taskId = id;
            this.synchronizedCounter = atomicCounter;
        }

        @Override
        public void run() {
            System.out.println("Task ID : " + this.taskId + " performed by " + Thread.currentThread().getName() + " counter: "
                    + synchronizedCounter.increment());
        }
    }

    private static class SynchronizedCounter {
        private int value;

        public synchronized int increment() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return ++value;
        }
    }

    public static void main(String args[]) {

        SynchronizedCounter synchronizedCounter = new SynchronizedCounter();

        ExecutorService service = Executors.newFixedThreadPool(10000);
        for (int i = 0; i < 1000; i++) {
            service.submit(new Task(i, synchronizedCounter));
        }
    }
}
