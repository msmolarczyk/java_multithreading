package _03.explicit.locks;

public class ProducerConsumerTemplate {
    private static class Item {
        private int content;

        public synchronized int get() {
            return content;
        }

        public synchronized void put(int value) {
            content = value;
        }
    }

    private static class Producer extends Thread {
        private Item item;
        private int number;

        public Producer(Item c, int number) {
            item = c;
            this.number = number;
        }

        public void run() {
            for (int i = 0; i < 10; i++) {
                item.put(i);
                System.out.println("Producer #" + this.number + " put: " + i);
                try {
                    sleep((int) (Math.random() * 100));
                } catch (InterruptedException e) {
                }
            }
        }
    }

    private static class Consumer extends Thread {
        private Item item;
        private int number;

        public Consumer(Item c, int number) {
            item = c;
            this.number = number;
        }

        public void run() {
            int value = 0;
            for (int i = 0; i < 10; i++) {
                value = item.get();
                System.out.println("Consumer #" + this.number + " got: " + value);
            }
        }
    }

    public static void main(String[] args) {
        Item c = new Item();
        Producer p1 = new Producer(c, 1);
        Consumer c1 = new Consumer(c, 1);
        p1.start();
        c1.start();
    }
}
