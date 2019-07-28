package _14.fork.join;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ForkJoinExample2 {

    private static class Problem {
        private final int[] list = new int[2000000];

        public Problem() {
            Random generator = new Random(19580427);
            for (int i = 0; i < list.length; i++) {
                list[i] = generator.nextInt(500000);
            }
        }

        public int[] getList() {
            return list;
        }
    }

    @SuppressWarnings("serial")
    private static class Solver extends RecursiveAction {
        private int[] list;
        public long result;

        public Solver(int[] array) {
            this.list = array;
        }

        public long getResult() {
            return result;
        }

        @Override
        protected void compute() {
            if (list.length == 1) {
                result = list[0];
            } else {
                int midpoint = list.length / 2;
                int[] l1 = Arrays.copyOfRange(list, 0, midpoint);
                int[] l2 = Arrays.copyOfRange(list, midpoint, list.length);

                Solver s1 = new Solver(l1);
                Solver s2 = new Solver(l2);

                invokeAll(s1, s2);
                result = s1.result + s2.result;
            }
        }
    }

    public static void main(String[] args) {
        Problem test = new Problem();
        // Check the number of available processors
        int nThreads = Runtime.getRuntime().availableProcessors();
        System.out.println(nThreads);
        Solver solver = new Solver(test.getList());
        ForkJoinPool pool = new ForkJoinPool(nThreads);
        pool.invoke(solver);
        long result = solver.getResult();
        System.out.println("Done. Result: " + result);
        long sum = 0;
        // Check if the result was ok
        for (int i = 0; i < test.getList().length; i++) {
            sum += test.getList()[i];
        }
        System.out.println("Done. Result: " + sum);
    }
}
