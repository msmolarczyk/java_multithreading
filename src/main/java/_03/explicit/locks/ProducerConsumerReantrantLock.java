package _03.explicit.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerReantrantLock {

	private static class Item {
		private int contents;
		private boolean available = false;

		private ReentrantLock lock = new ReentrantLock();
		private Condition condition = lock.newCondition();

		public int get() {
			lock.lock();
			try {
				while (available == false) {
					try {
						condition.await();
					} catch (InterruptedException e) {}
				}
				available = false;
				condition.signal();
				return contents;
			} finally {
				lock.unlock();
			}
		}

		public void put(int value) {
			lock.lock();
			try {
				while (available == true) {
					try {
						condition.await();
					} catch (InterruptedException e) {
					}
				}
				contents = value;
				available = true;
				condition.signal();
			} finally {
				lock.unlock();
			}
		}
	}

	private static class Producer extends Thread {
		private Item itm;
		private int number;

		public Producer(Item c, int number) {
			itm = c;
			this.number = number;
		}

		public void run() {
			for (int i = 0; i < 10; i++) {
				itm.put(i);
				System.out.println("Producer #" + this.number + " put: " + i);
				try {
					sleep((int) (Math.random() * 100));
				} catch (InterruptedException e) {
				}
			}
		}
	}

	private static class Consumer extends Thread {
		private Item itm;
		private int number;

		public Consumer(Item c, int number) {
			itm = c;
			this.number = number;
		}

		public void run() {
			int value = 0;
			for (int i = 0; i < 10; i++) {
				value = itm.get();
				System.out.println("Consumer #" + this.number + " got: "
						+ value);
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