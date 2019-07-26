package _06.cyclic.barrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CyclicBarrierExample {

    //Runnable task for each thread
    private static class Task implements Runnable {

        private CyclicBarrier barrier;

        public Task(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " is waiting on barrier");
                int count = barrier.await();
                System.out.println(Thread.currentThread().getName() + " has crossed the barrier");
                System.out.println("Inside thread " + Thread.currentThread().getName() + " count is " + count);
            } catch (InterruptedException ex) {
                Logger.getLogger(CyclicBarrierExample.class.getName()).log(Level.SEVERE, Thread.currentThread().getName(), ex);
            } catch (BrokenBarrierException ex) {
                Logger.getLogger(CyclicBarrierExample.class.getName()).log(Level.SEVERE, Thread.currentThread().getName(), ex);
            }
        }
    }

    public static void main(String args[]) {

        final CyclicBarrier cb = new CyclicBarrier(3, new Runnable(){
            @Override
            public void run(){
                System.out.println("All parties are arrived at barrier, lets play");
            }
        });

        for (int i = 0; i < 3; i++) {
            Thread t = new Thread(new Task(cb), "Thread " + i);
            t.start();
        }
        
//        starting threads
//        Thread ta = new Thread(new Task(cb), "Thread a");
//        Thread tb = new Thread(new Task(cb), "Thread b");
//        Thread tc = new Thread(new Task(cb), "Thread c");
//        ta.start();
//        tb.start();
//        t2.interrupt(); // this will cause every thread waiting on the barrier throw BrokenBarrierException
//        tc.start();
    }
}
