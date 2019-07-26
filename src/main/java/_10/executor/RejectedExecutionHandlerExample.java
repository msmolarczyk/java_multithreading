package _10.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class RejectedExecutionHandlerExample {
	static private class DemoThread extends Thread {
		public void run() {
			System.out.println("Hello");
		}
	}

	public static void main(String... args) {
		Runnable thread = new DemoThread();
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
		
		//(new ThreadPoolExecutor.AbortPolicy()).rejectedExecution(thread, executor);
		//(new ThreadPoolExecutor.CallerRunsPolicy()).rejectedExecution(thread, executor);
		//(new ThreadPoolExecutor.DiscardPolicy()).rejectedExecution(thread, executor);
		//(new ThreadPoolExecutor.DiscardOldestPolicy()).rejectedExecution(thread, executor);
		
		executor.shutdown();
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
		//submitting to a shut down executor, RejectedExecutionHandler is going to fire up
		executor.execute(thread);
	}
}