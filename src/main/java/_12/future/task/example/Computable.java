package _12.future.task.example;

public interface Computable<A, V> {
	V compute(A arg) throws InterruptedException;
}
