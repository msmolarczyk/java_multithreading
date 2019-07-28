package _07.phaser;

import java.util.concurrent.CountDownLatch;

public class ExerciseSolution {

    private static void doSomeWork(long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {

        CountDownLatch cl1 = new CountDownLatch(3);
        CountDownLatch cl2 = new CountDownLatch(3);
        CountDownLatch cl3 = new CountDownLatch(3);

        Thread t1 = new MyThread(cl1, cl2, cl3, 1000);
        Thread t2 = new MyThread(cl1, cl2, cl3, 3000);
        Thread t3 = new MyThread(cl1, cl2, cl3, 5000);
        t1.start();
        t2.start();
        t3.start();
    }

    private static class MyThread extends Thread {

        CountDownLatch cl1;
        CountDownLatch cl2;
        CountDownLatch cl3;
        int sleep;

        MyThread(CountDownLatch cl1, CountDownLatch cl2, CountDownLatch cl3, int sleep) {
            this.cl1 = cl1;
            this.cl2 = cl2;
            this.cl3 = cl3;
            this.sleep = sleep;
        }

        public void run() {
            System.out.println(this.getName() + " begin, phase: "  + 1);
            doSomeWork(sleep);
            cl1.countDown();
            try {
                cl1.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(this.getName() + " intermediate 1, phase: " + 2);
            doSomeWork(sleep);
            
            cl2.countDown();
            try {
                cl2.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(this.getName() + " intermediate 2, phase: " + 3);
            doSomeWork(sleep);
            
            cl3.countDown();
            try {
                cl3.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(this.getName() + " end");
        }
    }
}
