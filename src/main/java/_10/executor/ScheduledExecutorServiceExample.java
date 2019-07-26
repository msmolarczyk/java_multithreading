package _10.executor;

import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceExample {

	public static class MyTask implements Runnable {
		public void run() {
			System.out.println(new Date(System.currentTimeMillis()));
		}
	}

	public static void main(String[] args) {
		// creates thread pool of size 1
		ScheduledThreadPoolExecutor threadPool = new ScheduledThreadPoolExecutor(1);
		//threadPool.schedule(new MyTask(), 1, TimeUnit.SECONDS);

		 threadPool.scheduleAtFixedRate(new MyTask(), 1, 1, TimeUnit.SECONDS);
		// threadPool.shutdown();
	}
}
