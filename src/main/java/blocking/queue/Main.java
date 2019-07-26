package blocking.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
	public static void main(String[] argv) throws Exception {
		int capacity = 10;
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(capacity);

		int numWorkers = 2;
		Worker[] workers = new Worker[numWorkers];
		for (int i = 0; i < workers.length; i++) {
			workers[i] = new Worker(queue);
			workers[i].start();
		}

		for (int i = 0; i < 100; i++) {
			queue.put(i);
		}
	}
}

class Worker extends Thread {
	BlockingQueue<Integer> q;

	Worker(BlockingQueue<Integer> q) {
		this.q = q;
	}

	public void run() {
		try {
			while (true) {
				Integer x = q.take();
				if (x == null) {
					break;
				}
				System.out.println(Thread.currentThread().getName() + " " + x);
			}
		} catch (InterruptedException e) {
		}
	}
}