package _01.introduction;

import java.util.List;
import java.util.ArrayList;

public class MultipleThreads {
	
	static int counter = 0;
	static List<Thread> threads = new ArrayList<>();
	
	static Runnable r = new Runnable() {
	    @Override
	    public void run(){
	    	for (int i = 0; i < 10000; i++)  {
	    		incrementCounter();
	    	}
	    }
	};
	
	private static void incrementCounter() {
		counter++;
	}
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(r);
			t.start();
			threads.add(t);
		}
		
		for (Thread t : threads) {
			t.join();
		}
		System.out.println(counter);
	}
}
