package _11.executor.completion.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ClumsyExample {
	
	private static class MyRunnable implements Runnable {
		
		private int i, result;
		
		public int getResult(){
			return result;
		}
		
		@Override
		public void run() {
			result = i*i;
		}
		
		MyRunnable(int i){
			this.i = i;
		}
	}

	public static void main(String[] args) throws InterruptedException {

		ExecutorService executor = Executors.newFixedThreadPool(20);
		
		List<MyRunnable> list = new ArrayList<MyRunnable>();
		
		for (int i = 1; i <= 100; i++) {
			MyRunnable thread = new MyRunnable(i);
			executor.execute(thread);
			list.add(thread);
		}
		
		executor.shutdown();
		executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
		
		int result = 0;
		for(MyRunnable thread : list){
			result += thread.getResult();
		}
		System.out.println(result);
	}
}