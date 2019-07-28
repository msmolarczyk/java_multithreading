package _07.phaser;

public class Exercise {

    private static void doSomeWork(long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {

        Thread t1 = new MyThread(/**/ 1000);
        Thread t2 = new MyThread(/**/ 3000);
        Thread t3 = new MyThread(/**/ 5000);
        t1.start();
        t2.start();
        t3.start();
    }

    private static class MyThread extends Thread {
        int sleep;

        MyThread(int sleep) {
            this.sleep = sleep;
        }

        public void run() {
            
            // Phase 1
            
            System.out.println(this.getName() + " begin, phase: "  + 1);
            doSomeWork(sleep);

            // Phase 2

            System.out.println(this.getName() + " intermediate 1, phase: " + 2);
            doSomeWork(sleep);
            
            // Phase 3

            System.out.println(this.getName() + " intermediate 2, phase: " + 3);
            doSomeWork(sleep);
            
            // End

            System.out.println(this.getName() + " end");
        }
    }
}