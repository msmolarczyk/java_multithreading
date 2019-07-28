package _08.concurrent.collections;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Performance {

    static private class AddingThread extends Thread {
        Map<String, String> map;
        String str;

        public AddingThread(Map<String, String> map, String str) {
            this.map = map;
            this.str = str;
        }

        public void run() {
            map.put(str, str);
        }
    }

    static private class RemovingThread extends Thread {
        Map<String, String> map;
        String str;

        public RemovingThread(Map<String, String> map, String str) {
            this.map = map;
            this.str = str;
        }

        public void run() {
            map.remove(str);
        }
    }

    private static SecureRandom random = new SecureRandom();

    public static String createRandomString() {
        return new BigInteger(130, random).toString(32);
    }

    private static void testPerformance(Map<String, String> synchroMap) {
        int threads = 20000;

        String[] stringArray = new String[threads];
        for (int i = 0; i < threads; i++) {
            String str = createRandomString();
            new AddingThread(synchroMap, str).start();
            stringArray[i] = str;
        }

        for (String string : stringArray) {
            new RemovingThread(synchroMap, string).start();
        }
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String> synchroMap = Collections.synchronizedMap(map);
        testPerformance(synchroMap);
        System.out.println(System.currentTimeMillis() - start);

        start = System.currentTimeMillis();
        ConcurrentMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
        testPerformance(concurrentHashMap);
        System.out.println(System.currentTimeMillis() - start);
    }
}
