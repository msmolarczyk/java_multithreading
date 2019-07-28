package queue.producer.consumer;

import java.util.*;
import java.util.concurrent.*;

class Producer implements Runnable {
    private BlockingQueue<String> queue;
    List<String> messages = Arrays.asList();

    public Producer(BlockingQueue<String> d) {
        this.queue = d;
    }

    public void run() {
        try {
            for (String s : messages) {
                queue.put(s);
            }
            queue.put("DONE");
        } catch (InterruptedException intEx) {
            System.out.println("Interrupted! " + "Last one out, turn out the lights!");
        }
    }
}

class Consumer implements Runnable {
    private BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> d) {
        this.queue = d;
    }

    public void run() {
        try {
            String msg = null;
            while (!((msg = queue.take()).equals("DONE")))
                System.out.println(msg);
        } catch (InterruptedException intEx) {
            System.out.println("Interrupted! " + "Last one out, turn out the lights!");
        }
    }
}

public class SynQApp {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new SynchronousQueue<String>();
        Thread producer = new Thread(new Producer(queue));
        Thread consumer = new Thread(new Consumer(queue));
        producer.start();
        consumer.start();
    }
}
