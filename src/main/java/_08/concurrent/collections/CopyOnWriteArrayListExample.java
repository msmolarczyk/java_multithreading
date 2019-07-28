package _08.concurrent.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListExample {

    public static void main(String args[]) {
        // CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
        // addToList(list);
        // cleanListUsingFor(list);
        // System.out.println(list);

        ArrayList<String> list2 = new ArrayList<String>();
        addToList(list2);
        cleanListUsingFor(list2);
        System.out.println(list2);
    }

    private static void addToList(List<String> list) {
        list.add("Java");
        list.add("Scala");
        list.add("Groovy");
        list.add("Clojure");
    }

    private static void cleanListUsingIrerator(List<String> list) {
        // add, remove operator is not supported by CopyOnWriteArrayList iterator
        Iterator<String> failSafeIterator = list.iterator();
        while (failSafeIterator.hasNext()) {
            System.out.printf("Read from CopyOnWriteArrayList : %s %n", failSafeIterator.next());
            failSafeIterator.remove(); // not supported in CopyOnWriteArrayList in Java
        }
    }

    private static void cleanListUsingFor(List<String> list) {
        for (String e : list) {
            System.out.printf("Read from CopyOnWriteArrayList : %s %n", e);
            list.remove(e);
        }
    }
}