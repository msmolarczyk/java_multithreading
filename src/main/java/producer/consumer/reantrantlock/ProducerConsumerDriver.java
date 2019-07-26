package producer.consumer.reantrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerDriver {

    private static class IntBuffer {
        private int contents;
        private final ReentrantLock lock = new ReentrantLock(true); // implements
                                                                    // fairness
        private boolean available = false;
        final Condition intProduced = lock.newCondition();
        final Condition intConsumed = lock.newCondition();

        public int get(int ID) {
            lock.lock();

            try {
                while (available == false) {
                    intProduced.await(); // if no new Int is available, wait for
                                         // a producer to make one
                }
                available = false; // the new int is about to be consumed
                                   // (method is locked so no other threads can
                                   // interrupt) so we can let the producer
                                   // provide a new int
                intConsumed.signal(); // signal to producer that the int has
                                      // been consumed
                System.out.println("Consumer #" + ID + " got: " + contents);
                return contents; // return(consume) the produced value
            } catch (Exception e) {
                e.printStackTrace();
                return contents; // return the produced value
            } finally {
                lock.unlock(); // unlock to allow next consumer to wait for an
                               // int
            }
        }

        public void put(int value, int ID) {
            lock.lock();

            try {
                while (available == true) {
                    intConsumed.await(); // wait for consumer to take value
                }

                contents = value; // once the consumer has taken a value put a
                                  // new one in the buffer
                System.out.println("Producer #" + ID + " put: " + contents);
                available = true;
                intProduced.signal(); // indicate that a new int is available in
                                      // the buffer

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }

    private static class Producer extends Thread {
        private IntBuffer cubbyhole;
        private int prodID;
        private static int numOfProd;

        public Producer(IntBuffer c) {
            cubbyhole = c;
            prodID = ++numOfProd;
        }

        public void run() {
            for (int i = 0; i < 10; i++) {
                cubbyhole.put(i, prodID);
                try {
                    sleep((int) (Math.random() * 100));
                } catch (InterruptedException e) {
                }
            }
        }
    }

    private static class Consumer extends Thread {
        private IntBuffer cubbyhole;
        private int consumID;
        private static int numOfConsum;

        public Consumer(IntBuffer c) {
            cubbyhole = c;
            consumID = ++numOfConsum;
        }

        public void run() {
            for (int i = 0; i < 10; i++) {
                cubbyhole.get(consumID);
            }
        }
    }

    public static void main(String args[]) {
        IntBuffer ib = new IntBuffer();
        Producer p1 = new Producer(ib);
        Producer p2 = new Producer(ib);
        Consumer c1 = new Consumer(ib);
        Consumer c2 = new Consumer(ib);

        p1.start();
        p2.start();
        c1.start();
        c2.start();
    }
}
