package _08.concurrent.collections;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TransferQueue;

public class TransferQueueExample {

    public static void main(String[] args) throws InterruptedException {
        final TransferQueue<String> transfer = new LinkedTransferQueue<String>();
        // transfer.transfer("Hello"); //Wait for a consumer

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    transfer.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
        Thread.sleep(1000);
        if (transfer.tryTransfer("World")) {// Don't wait for a consumer
            System.out.println("The element has been transfered to a consumer");
        } else {
            System.out.println("There were no waiting consumer. The element has not been enqueued.");
        }

        // boolean transfered = transfer.tryTransfer("Goodbye", 5, TimeUnit.SECONDS);

    }
}
