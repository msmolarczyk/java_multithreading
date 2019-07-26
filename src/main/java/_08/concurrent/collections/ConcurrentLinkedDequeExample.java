package _08.concurrent.collections;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentLinkedDequeExample {
	public static void main(String args[]) {
		Deque<String> queue = new ConcurrentLinkedDeque<String>();

		queue.add("A");
		queue.add("B");
		queue.add("C");
		queue.add("D");
		queue.add("E");

		System.out.println("The Deque elements are : " + queue);
		System.out.println(queue.getFirst());
		System.out.println("The Deque elements are : " + queue);
		System.out.println(queue.pollFirst());
		System.out.println("The Deque elements are : " + queue);
		
		queue.addFirst("X");
		System.out.println("The Deque elements are : " + queue);
		
		System.out.println(queue.removeLast());
		System.out.println("The Deque elements are : " + queue);
	}
}