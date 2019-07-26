package _02.atomic.variables;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayExample {
    public static AtomicIntegerArray aia = new AtomicIntegerArray(10);

    public static class AddThread implements Runnable {
        @Override
        public void run() {
            // add 15 to the element at index at index 1
            aia.addAndGet(1, 15);

            // if current value is 15, then 5 is set as the current at index 1
            aia.compareAndSet(1, 15, 5);

            System.out.println(aia.get(1));
        }
    }

    public static class SubThread implements Runnable {

        @Override
        public void run() {
            // subtract 1 from value at index 1
            aia.decrementAndGet(1);

            // (int index, int expect, int update)
            aia.compareAndSet(1, 14, 20);
            System.out.println(aia.get(1));
        }
    }

    public static void main(String... args) {
        new Thread(new AddThread()).start();
        new Thread(new SubThread()).start();
    }
}