package _02.atomic.variables;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample {
	static AtomicInteger ai= new AtomicInteger(10);
	
	private static class AddThread implements Runnable{

		@Override
		public void run() {
			//ads 5 to the current value
			ai.addAndGet(5);
		
			//if the current value is 15, then set current value to 5
			ai.compareAndSet(15,5);
			System.out.println(ai.get());
		}
	}
	
	private static class SubThread implements Runnable{

		@Override
		public void run() {
			//subtract 1
			ai.decrementAndGet();
			
			//if the current value is 10, then set current value to 20
			ai.compareAndSet(10,20);
			System.out.println(ai.get());
		}
	}
	
   public static void main(String... args){
	   new Thread(new AddThread()).start();
	   new Thread(new SubThread()).start();
   }
}