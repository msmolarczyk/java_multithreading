package _14.fork.join;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDone {

	private static int[] getArray() {
		final int[] array = new int[20000000];
		Random generator = new Random(19580427);
		for (int i = 0; i < array.length; i++) {
			array[i] = generator.nextInt(500000);
		}
		return array;
	}

	@SuppressWarnings("serial")
	private static class Solver extends RecursiveTask<Long> {
		private int[] array;
		private int low;
		private int high;
		private static final int splitSize = 40000;

		public Solver(int[] array, int low, int high) {
			this.array = array;
			this.low = low;
			this.high = high;
		}

		@Override
		protected Long compute() {
			if (high - low <= splitSize) {
				//return new Long(array[0]);
				int part_sum = 0;
				for (int i = low; i < high; i++) {
					part_sum += array[i];
				}
				return new Long(part_sum);
			} else {
				int mid = (low + high) >>> 1;
				Solver s1 = new Solver(array, low, mid);
				Solver s2 = new Solver(array, mid, high);

				invokeAll(s1, s2);
				return s1.join() + s2.join();
			}
		}
	}

	public static void main(String[] args) {
		int[] array = getArray();
		// Check the number of available processors
		int nThreads = Runtime.getRuntime().availableProcessors();
		System.out.println(nThreads);
		Solver solver = new Solver(array, 0, array.length);
		
		ForkJoinPool pool = new ForkJoinPool(nThreads);
		Long result = pool.invoke(solver);
		
		System.out.println("Done. Result: " + result);
		long sum = 0;
		// Check if the result was ok
		for (int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		System.out.println("Verification. Result: " + sum);
	}
}
