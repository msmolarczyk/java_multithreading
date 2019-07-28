package _14.fork.join;

import java.util.Arrays;
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
        private int[] list;

        public Solver(int[] list) {
            this.list = list;
        }

        @Override
        protected Long compute() {
            if (list.length == 1) {
                return Long.valueOf(list[0]);
            } else {
                int midpoint = list.length / 2;
                int[] l1 = Arrays.copyOfRange(list, 0, midpoint);
                int[] l2 = Arrays.copyOfRange(list, midpoint, list.length);

                Solver s1 = new Solver(l1);
                Solver s2 = new Solver(l2);

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
        Solver solver = new Solver(array);

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
