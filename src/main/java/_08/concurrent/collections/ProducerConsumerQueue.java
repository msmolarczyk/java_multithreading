package _08.concurrent.collections;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class ProducerConsumerQueue {
    
    private static class Producer extends Thread {
        private BlockingQueue<Integer> queue;
        private int number;

        public Producer(BlockingQueue<Integer> c, int number) {
            this.queue = c;
            this.number = number;
        }

        public void run() {
            try {
                for (int i = 0; i < 10; i++) {
                    queue.put(i);
                    System.out.println("Producer #" + this.number + " put: " + i);
                }
                sleep((int) (Math.random() * 100));
                queue.put(-1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
   
    private static class Consumer extends Thread {
        private BlockingQueue<Integer> queue;
        private int number;

        public Consumer(BlockingQueue<Integer> d, int number) {
            this.queue = d;
            this.number = number;
        }

        public void run() {
            try {
                Integer value = null;
                while (!((value = queue.take()).equals(-1)))
                    System.out.println("Consumer #" + this.number + " got: " + value);
            } catch (InterruptedException intEx) {
                System.out.println("Interrupted! " + "Last one out, turn out the lights!");
            }
        }
    }

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new SynchronousQueue<>();
        Producer p1 = new Producer(queue, 1);
        Consumer c1 = new Consumer(queue, 1);
        p1.start();
        c1.start();
    }
}
