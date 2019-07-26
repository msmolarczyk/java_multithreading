package _01.introduction;

public class ThreadExample {

	static int counter = 0;
	
	private static void incrementCounterLoop() {
		for (int i = 0; i < 10_000; i++) {
			incrementCounter();
		}
	}

	private static void incrementCounter() {
		counter++;
	}
	
	public static void main(String[] args) throws InterruptedException {
		
//		Runnable r = new Runnable() {
//			public void run() {
//				incrementCounterLoop();
//			}
//		};
//		
//		Thread t = new Thread() {
//			public void run() {
//				incrementCounterLoop();
//			}
//		};
//		
//		t.start();
//		
//		Thread t2 = new Thread(r);
//		t2.start();
//		
//		System.out.println(counter);
	}
}
