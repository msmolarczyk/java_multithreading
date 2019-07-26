package _09.runnable.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RunnableVsCallable {
	
	public static class MyRunnable implements Runnable {
		@Override
		public void run() {
		}
	}
	
	public static class MyCallable implements Callable<String> {
		@Override
		public String call() throws Exception {
			return "Hello from callable";
		}
	}
	
	public static class ThrowsExceptionCallable implements Callable<String> {
		@Override
		public String call() throws Exception {
			System.out.println("Before exception");
			throw new RuntimeException();
		}
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		ExecutorService exec = Executors.newFixedThreadPool(2);
		
		exec.execute(new MyRunnable());
		
		Future<String> f1 = exec.submit(new MyCallable());
		System.out.println("Result from future: " + f1.get());
		
		Future<String> f2 = exec.submit(new ThrowsExceptionCallable());
		// Exception from Callable is wrapped in ExecutionException
		// and doesn't get rethrown until get() is not called on the corresponding Future.
		//f2.get();
		
		exec.shutdown();
	}
}
