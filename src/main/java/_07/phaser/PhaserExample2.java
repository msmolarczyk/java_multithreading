package _07.phaser;

import java.util.concurrent.Phaser;

public class PhaserExample2 {

    private static void doSomeWork(long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {

        Phaser phaser = new Phaser();

        Thread t1 = new MyThread(phaser, 1000);
        Thread t2 = new MyThread(phaser, 3000);
        Thread t3 = new MyThread(phaser, 5000);
        t1.start();
        t2.start();
        t3.start();
    }

    private static class MyThread extends Thread {

        Phaser phaser;
        int sleep;

        MyThread(Phaser phaser, int sleep) {
            this.phaser = phaser;
            this.sleep = sleep;
        }

        public void run() {
            phaser.register();

            System.out.println(this.getName() + " begin, phase: "  +  phaser.getPhase());
            doSomeWork(sleep);

            phaser.arriveAndAwaitAdvance();

            System.out.println(this.getName() + " intermediate 1, phase: " +  phaser.getPhase());
            doSomeWork(sleep);
            
            phaser.arriveAndAwaitAdvance();

            System.out.println(this.getName() + " intermediate 2, phase: " + phaser.getPhase());
            doSomeWork(sleep);

            System.out.println(this.getName() + " end, phase: " +  phaser.getPhase());
            phaser.arriveAndDeregister();
        }
    }
}
