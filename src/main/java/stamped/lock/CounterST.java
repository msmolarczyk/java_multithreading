package stamped.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

public class CounterST {
    int c = 0;

    public static void main(String[] args) {
        StampedLock sl = new StampedLock();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CounterST cst = new CounterST();

        // Runnable as lambda - read
        Runnable readTask = () -> {
            long stamp = sl.readLock();
            try {
                System.out.println("value " + cst.getValue());

            } finally {
                sl.unlockRead(stamp);
            }
        };

        // Runnable as lambda - Write lock
        Runnable writeTask = () -> {
            long stamp = sl.writeLock();
            try {
                cst.increment();
            } finally {
                sl.unlockWrite(stamp);
            }
        };

        // 3 write tasks
        executor.submit(writeTask);
        executor.submit(writeTask);
        executor.submit(writeTask);
        // 1 read task
        executor.submit(readTask);
        executor.shutdown();

    }

    public void increment() {
        c++;
        System.out.println("in increment " + c);
    }

    public int getValue() {
        return c;
    }

}
