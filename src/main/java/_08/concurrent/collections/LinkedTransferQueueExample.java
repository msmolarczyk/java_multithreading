package _08.concurrent.collections;

import java.util.concurrent.LinkedTransferQueue;

public class LinkedTransferQueueExample {
    public static LinkedTransferQueue<Item> linkTransQ = new LinkedTransferQueue<>();

    public static void main(String[] args) {
        Thread producerThread = new Thread(new ItemProducer());
        Thread consumerThread = new Thread(new ItemConsumer());
        producerThread.start();
        consumerThread.start();
    }
}

class Item {

    private String description;
    private int itemId;

    public String getDescription() {
        return description;
    }

    public int getItemId() {
        return itemId;
    }

    public Item() {
        this.description = "Default Item";
        this.itemId = 0;
    }

    public Item(String description, int itemId) {
        this.description = description;
        this.itemId = itemId;
    }
}

class ItemProducer implements Runnable {

    @Override
    public void run() {
        String itemName = "";
        int itemId = 0;
        try {
            for (int x = 1; x <= 8; x++) {
                itemName = "Item_" + x;
                itemId = x;
                LinkedTransferQueueExample.linkTransQ.offer(new Item(itemName, itemId));
                System.out.println("New Item Added: " + itemName + " " + itemId);
                Thread.currentThread().sleep(250);
                if (LinkedTransferQueueExample.linkTransQ.hasWaitingConsumer() == true) {
                    System.out.println("Hurry up!");
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

class ItemConsumer implements Runnable {

    @Override
    public void run() {
        try {
            Thread.currentThread().sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        while (true) {
            try {
                generateOrder(LinkedTransferQueueExample.linkTransQ.take());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void generateOrder(Item item) {
        System.out.println(item.getDescription());
        System.out.println(item.getItemId());
    }
}
