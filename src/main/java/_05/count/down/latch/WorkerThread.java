package _05.count.down.latch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class WorkerThread implements Runnable {

    private CountDownLatch latch = null;
    private int name = 0;

    public WorkerThread(CountDownLatch latch, int name) {
        this.latch = latch;
        this.name = name;
    }

    public void run() {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String s = fmt.format(new Date());
        System.out.println(s + "=Doing some work for thread: " + this.name);
        try {
            Thread.sleep(name * 1000);
            s = fmt.format(new Date());
            System.out.println(s + "=Work done for thread: " + this.name);
            latch.countDown();
            s = fmt.format(new Date());
            System.out.println(s + "=Inside thread " + this.name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(new WorkerThread(latch, i));
            t.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Done with latch processing");
    }
}
