package _04.semaphore;

import java.util.concurrent.Semaphore;

public class SemaphoreSimpleExample {
    public static void main(String args[]) throws Exception {
        Semaphore sem = new Semaphore(1, true);
        Thread thrdA = new Thread(new SyncOutput(sem, "Message 1"));
        Thread thrdB = new Thread(new SyncOutput(sem, "Message 2"));

        thrdA.start();
        thrdB.start();

        thrdA.join();
        thrdB.join();
        System.out.println("Exiting");
    }
}

class SyncOutput implements Runnable {
    Semaphore sem;
    String msg;

    SyncOutput(Semaphore s, String m) {
        sem = s;
        msg = m;
    }

    public void run() {
        try {
            sem.acquire();
            Thread.sleep(500);
            System.out.println(msg);
        } catch (Exception exc) {
            System.out.println("Error Writing File");
        }
        sem.release();
    }
}
