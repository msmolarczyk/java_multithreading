package queue.producer.consumer;

// We would like to synchronize ProducerGood and ConsumerGood so that
// ProducerGood puts a number in the buffer, then the ConsumerGood takes it
// out, then the ProducerGood puts another number, and so on.

// This solution provides the right behavior
// We have changed the class Buffer to include wait() and notify()
// We also have changed the ProducerGood and consumer classes
// slightly to handle a new exception

public class ConsumerProducerGood {

    public static void main(String[] args) {
        Buffer buf = new Buffer();

        // create new threads
        Thread prod = new ProducerGood(10, buf);
        Thread cons = new ConsumerGood(10, buf);

        // starting threads
        prod.start();
        cons.start();

        // Wait for the threads to finish
        try {
            prod.join();
            cons.join();
        } catch (InterruptedException e) {
            return;
        }
    }

}

class Buffer {
    private int contents;
    private boolean empty = true;

    public synchronized void put(int i) throws InterruptedException {
        while (empty == false) { // wait till the buffer becomes empty
            try {
                wait();
            } catch (InterruptedException e) {
                throw e;
            }
        }
        contents = i;
        empty = false;
        System.out.println("ProducerGood: put..." + i);
        notify();
    }

    public synchronized int get() throws InterruptedException {
        while (empty == true) { // wait till something appears in the buffer
            try {
                wait();
            } catch (InterruptedException e) {
                throw e;
            }
        }
        empty = true;
        notify();
        int val = contents;
        System.out.println("ConsumerGood: got..." + val);
        return val;
    }
}

class ProducerGood extends Thread {
    private int n;
    private Buffer prodBuf;

    public ProducerGood(int m, Buffer buf) {
        n = m;
        prodBuf = buf;
    }

    public void run() {
        for (int i = 0; i < n; i++) {
            try {
                Thread.sleep((int) Math.random() * 100); // sleep for a randomly
                                                         // chosen time
            } catch (InterruptedException e) {
                return;
            }

            try {
                prodBuf.put(i + 1); // starting from 1, not 0
            } catch (InterruptedException e) {
                return;
            }

        }
    }
}

class ConsumerGood extends Thread {
    private int n;
    private Buffer consBuf;

    public ConsumerGood(int m, Buffer buf) {
        n = m;
        consBuf = buf;
    }

    public void run() {
        int value;
        for (int i = 0; i < n; i++) {
            try {
                value = consBuf.get();
            } catch (InterruptedException e) {
                return;
            }
            try {
                Thread.sleep((int) Math.random() * 100); // sleep for a randomly
                                                         // chosen time
            } catch (InterruptedException e) {
                return;
            }

        }
    }
}
