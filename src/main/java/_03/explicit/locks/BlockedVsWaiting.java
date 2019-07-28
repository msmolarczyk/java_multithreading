package _03.explicit.locks;

import java.util.concurrent.locks.ReentrantLock;

public class BlockedVsWaiting {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("---------------------\nLock:");
        lockExample();
        System.out.println("---------------------\nSynchronized:");
        synchronizedExample();
    }

    private static void lockExample() throws InterruptedException {
        final ReentrantLock lock = new ReentrantLock();

        Thread thread = new Thread(new Runnable() {
            public void run() {
                try {
                    lock.lockInterruptibly();
                } catch (InterruptedException e) {
                    System.out.println("interrupted");
                }
            }
        });

        lock.lock();
        thread.start();

        Thread.sleep(1000);
        System.out.println(thread.getState());
        thread.interrupt();

        Thread.sleep(1000);
        System.out.println(thread.getState());
    }

    private static void synchronizedExample() throws InterruptedException {
        final Object object = new Object();

        Thread thread = new Thread(new Runnable() {
            public void run() {
                synchronized (object) {
                }
            }
        });
        synchronized (object) {
            thread.start();
            Thread.sleep(1000);
            System.out.println(thread.getState());
            thread.interrupt();

            Thread.sleep(1000);
            System.out.println(thread.getState());
        }
    }
}
