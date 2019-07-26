package _03.explicit.locks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

// TODO - przerobic na slownik


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
                return map.put(key, value);
            } finally {
                write.unlock();
            }
        }

        public V get(Object key) {
            read.lock();
            try {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
                return map.get(key);
            } finally {
                read.unlock();
            }
        }
    }

    public static void main(String[] args) {

        final ReadWriteMap<String, String> map = new ReadWriteMap<String, String>(new HashMap<String, String>());

        class Reader implements Runnable {
            private int i;

            @Override
            public void run() {
                System.out.println(map.get("" + i));
            }

            Reader(int i) {
                this.i = i;
            }
        }

        class Writer implements Runnable {
            private int i;

            @Override
            public void run() {
                map.put("" + i, "Value: " + i);
            }

            Writer(int i) {
                this.i = i;
            }
        }

        for (int i = 0; i < 10; i++) {
            map.put("" + i, "Value: " + i);
        }
        for (int i = 0; i < 10; i++) {
            new Thread(new Writer(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            new Thread(new Reader(i)).start();
        }
    }
}
