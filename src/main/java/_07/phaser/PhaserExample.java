package _07.phaser;

import java.util.concurrent.Phaser;

public class PhaserExample {

	public static void main(String[] args) {
		Phaser phaser = new Phaser();
		
		for (int i = 0; i < 3; i++) {
			PhaserTask phaserTask = new PhaserTask(i, phaser);
			new Thread(phaserTask).start();
		}
	}

	private static class PhaserTask implements Runnable {

		private final Phaser phaser;
		private final String taskId;

		public PhaserTask(int i, Phaser pPhaser) {
			phaser = pPhaser;
			taskId = "Thread_" + i;
		}

		@Override
		public void run() {
			
			phaser.register();	// register on the fly

			// First phase
			doSomeWork();

			int arr1 = phaser.arrive();	// let the phaser know that you have arrived this point of the task
			int unarrivedParties1 = phaser.getUnarrivedParties();	// ask the phaser how many parties are not yet here
			
			if (phaser.getPhase() == arr1) {
				System.out.println(taskId + " completed phase " + arr1 + " and waiting arraival of " + 
									unarrivedParties1 + " threads so that it can enter phase " + (arr1 + 1));
			}
			else {
				System.out.println(taskId + " completed phase " + (arr1) + 
									" (the last one to reach) and now all tasks will proceed to phase " + (arr1 + 1));
			}
			
			phaser.awaitAdvance(arr1);	// be in sync with other threads

			
			
			// Second phase
			doSomeWork();

			int arr2 = phaser.arrive();	// let the phaser know that you have arrived this point of the task
			int unarrivedParties2 = phaser.getUnarrivedParties(); // ask the phaser how many parties are not yet here

			if (phaser.getPhase() == arr2) {
				System.out.println(taskId + " completed phase " + arr2 + " and waiting arraival of " + 
									unarrivedParties2 + " threads so that it can enter phase " + (arr2 + 1));
			}
			else {
				System.out.println(taskId + " completed phase " + (arr2) + 
									" (the last one to reach) and now all tasks will proceed to phase " + (arr2 + 1));
			}
			phaser.awaitAdvance(arr2);	// be in sync with other threads

			// and so on ...
			
			// at some point a task could de-register itself from the phaser
			phaser.arriveAndDeregister();	// de-registered threads is not considered need not be in sync with the other threads any more
			

		}

		public void doSomeWork() {
			for (int i = 1; i < 100000000; i++) {
				int temp = i;
				temp = ((temp ^ 54) * 4) / temp ^ 34 * (temp ^ (temp << 2) ^ temp);
			}
		}
	}
}