package _08.concurrent.collections;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapExample {

    public static void main(String[] args) {

        // ConcurrentHashMap
        Map<String, String> myMap = new ConcurrentHashMap<String, String>();
        processMap(myMap);

        ConcurrentHashMap<String, String> cm = (ConcurrentHashMap<String, String>) myMap;
        String str = cm.putIfAbsent("3", "new3");
        System.out.println(str);

        // HashMap
        myMap = new HashMap<String, String>();
        // processMap(myMap);
    }

    private static void processMap(Map<String, String> myMap) {
        myMap.put("1", "1");
        myMap.put("2", "1");
        // myMap.put("3", "1");
        myMap.put("4", "1");
        myMap.put("5", "1");
        myMap.put("6", "1");
        System.out.println("ConcurrentHashMap before iterator: " + myMap);
        Iterator<String> it = myMap.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next();
            if (key.equals("3")) myMap.put(key, "replaced_value");
        }
        System.out.println("ConcurrentHashMap after iterator: " + myMap);
    }

}