package _14.fork.join;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import static java.util.Arrays.asList;

public class ForkJoinExample {
    Random random = new Random();

    public void loadArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(10000); // Generates numbers from 0 to 10000
        }
    }

    public static void main(String[] args) {
        ForkJoinExample sort = new ForkJoinExample();

        int arrayLength = 100_000_000;
        int array[] = new int[arrayLength];

        // No. of times sequential & Parallel operation are going to be performed
        final int iterations = 10;

        for (int i = 0; i < iterations; i++) {
            long start = System.currentTimeMillis();
            sort.loadArray(array);

            System.out.println("Sequential processing time: " + (System.currentTimeMillis() - start) + " ms");

        }

        System.out.println("Number of processor available: " + Runtime.getRuntime().availableProcessors());

        ForkJoinPool fjpool = new ForkJoinPool(64); // Default parallelism level = Runtime.getRuntime().availableProcessors()

        for (int i = 0; i < iterations; i++) {
            // Create a task with the complete array
            RecursiveAction action = new RandomFillAction(array, 0, array.length);
            long start = System.currentTimeMillis();
            fjpool.invoke(action);

            System.out.println("Parallel processing time: " + (System.currentTimeMillis() - start) + " ms");
        }

        System.out.println("Number of steals: " + fjpool.getStealCount() + "\n");
    }
}

class RandomFillAction extends RecursiveAction {
    private static final long serialVersionUID = 1L;
    final int low;
    final int high;
    private int[] array;
    final int splitSize = 40000; // Some threshold size to spit the task

    public RandomFillAction(int[] array, int low, int high) {
        this.low = low;
        this.high = high;
        this.array = array;
    }

    @Override
    protected void compute() {
        if (high - low > splitSize) {
            // task is huge so divide in half
            int mid = (low + high) >>> 1;
            invokeAll(asList(new RandomFillAction(array, low, mid), new RandomFillAction(array, mid, high)));
        } else {
            // Some calculation logic
            Random random = new Random();
            for (int i = low; i < high; i++) {
                array[i] = random.nextInt(10000);
            }
        }
    }
}