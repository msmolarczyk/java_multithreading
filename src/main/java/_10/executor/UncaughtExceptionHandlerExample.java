package _10.executor;

import java.lang.Thread.UncaughtExceptionHandler;

public class UncaughtExceptionHandlerExample {

	static private class MyHandler implements UncaughtExceptionHandler {
		@Override
		public void uncaughtException(Thread t, Throwable e) {
			System.out.println("Exception thrown from: " + t.getName());
		}
	}

	public static void main(String[] args) {
		Thread.currentThread().setUncaughtExceptionHandler(new MyHandler());
		throw new RuntimeException();	
	}
}
