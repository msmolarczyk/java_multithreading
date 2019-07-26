package _05.count.down.latch;

import java.util.concurrent.CountDownLatch;

public class CDLDemoDone {

    private static class MyThread2 implements Runnable {

        CountDownLatch latch;

        MyThread2(CountDownLatch c) {
            latch = c;
            new Thread(this).start();
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            try {
                latch.await();
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread().getName() + " - completed");
        }
    }

    private static class MyThread implements Runnable {
        CountDownLatch latch;

        MyThread(CountDownLatch c) {
            latch = c;
            new Thread(this).start();
        }

        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(i);
                latch.countDown(); // decrement count
            }
        }
    }

    public static void main(String args[]) {
        CountDownLatch cdl = new CountDownLatch(5);
        new MyThread(cdl);

        for (int i = 0; i < 10; i++) {
            new MyThread2(cdl);
        }

        try {
            cdl.await();
        } catch (InterruptedException exc) {
            System.out.println(exc);
        }
        System.out.println("Done");
    }
}