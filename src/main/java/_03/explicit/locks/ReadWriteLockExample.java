package _03.explicit.locks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    private static class ReadWriteMap<K, V> {
        private final Map<K, V> map;
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        private final Lock read = lock.readLock();
        private final Lock write = lock.writeLock();

        public ReadWriteMap(Map<K, V> map) {
            this.map = map;
        }

        public V put(K key, V value) {
            write.lock();
            try {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                return map.put(key, value);
            } finally {
                write.unlock();
            }
        }

        public V get(Object key) {
            read.lock();
            try {
                return map.get(key);
            } finally {
                read.unlock();
            }
        }
    }

    public static int fib(int n) {
        int prev = 0, next = 1, result = 0;
        for (int i = 0; i < n; i++) {
            result = prev + next;
            prev = next;
            next = result;
        }
        return result;
    }

    public static void main(String[] args) {

        final ReadWriteMap<Integer, Integer> map = new ReadWriteMap<Integer, Integer>(new HashMap<Integer, Integer>());

        class Reader implements Runnable {
            private int i;

            @Override
            public void run() {
                System.out.println("Reading: " + i + " : " + map.get(i));
            }

            Reader(int i) {
                this.i = i;
            }
        }

        class Writer implements Runnable {
            private int i;

            @Override
            public void run() {
                int f = fib(i);
                System.out.println("Writing: " + i + " : " + f);
                map.put(i, f);
            }

            Writer(int i) {
                this.i = i;
            }
        }

        for (int i = 0; i < 10; i++) {
            new Thread(new Writer(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(new Reader(i)).start();
        }
    }
}
