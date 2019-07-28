package queue.producer.consumer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class BlockingQueueProducer implements Runnable {
    private BlockingQueue<String> queue;
    List<String> messages = Arrays.asList("Aurochs", "Caspian Tiger", "Portuguese Ibex", "Caucasian Moose", "Caucasian Wisent", "Carpathian Wisent",
            "Majorcan Hare");

    public BlockingQueueProducer(BlockingQueue<String> d) {
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

class BlockingQueueConsumer implements Runnable {
    private BlockingQueue<String> queue;

    public BlockingQueueConsumer(BlockingQueue<String> d) {
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

public class ABQApp {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(1);
        Thread producer = new Thread(new BlockingQueueProducer(queue));
        Thread consumer = new Thread(new BlockingQueueConsumer(queue));
        producer.start();
        consumer.start();
    }
}
