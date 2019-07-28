package _02.atomics.vs.synch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounterExample {

    private static class Task implements Runnable {
        private int taskId;
        private AtomicCounter atomicCounter;

        public Task(int id, AtomicCounter atomicCounter) {
            this.taskId = id;
            this.atomicCounter = atomicCounter;
        }

        @Override
        public void run() {
            System.out.println("Task ID : " + this.taskId + " performed by " + Thread.currentThread().getName() + " counter: " + atomicCounter.increment());
        }
    }

    private static class AtomicCounter {
        private final AtomicInteger value = new AtomicInteger(0);

        public int increment() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return value.incrementAndGet();
        }
    }

    public static void main(String args[]) {

        AtomicCounter atomicCounter = new AtomicCounter();

        ExecutorService service = Executors.newFixedThreadPool(10000);
        for (int i = 0; i < 1000; i++) {
            service.submit(new Task(i, atomicCounter));
        }
    }
}
