package _11.executor.completion.service;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GoodExample {
	
	private static class MyCallable implements Callable<Integer> {
		
		private int i;
		
		@Override
		public Integer call() {
			return i*i;
		}
		
		MyCallable(int i){
			this.i = i;
		}
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ExecutorService executor = Executors.newFixedThreadPool(20);
		CompletionService<Integer> cs = new ExecutorCompletionService<>(executor);
		
		for (int i = 1; i <= 100; i++) {
			Callable<Integer> callable = new MyCallable(i);
			cs.submit(callable);
		}
		
		int result = 0;
		for (int i = 0; i < 100; i++) {
			result += cs.take().get();
		}
		
		executor.shutdown();
		System.out.println(result);
	}
}