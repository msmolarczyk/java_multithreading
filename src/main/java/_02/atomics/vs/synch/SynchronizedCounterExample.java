package _02.atomics.vs.synch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedCounterExample {
	
	static class Task implements Runnable {
	    private int taskId;
	    private SynchronizedCounter synchronizedCounter;
	  
	    public Task(int id, SynchronizedCounter atomicCounter){
	        this.taskId = id;
	        this.synchronizedCounter = atomicCounter;
	    }
	  
	    @Override
	    public void run() {
	        System.out.println("Task ID : " + this.taskId +" performed by " 
	                           + Thread.currentThread().getName() + " counter: " + synchronizedCounter.getNextValue());
	    }
	}
	
	private static class SynchronizedCounter {

		private int value;

		public synchronized int getValue() {
			return value;
		}

		public synchronized int getNextValue() {
	    	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return value++;
		}

		public synchronized int getPreviousValue() {
			return value--;
		}
	}
	
    public static void main(String args[]) {
    	
    	SynchronizedCounter synchronizedCounter = new SynchronizedCounter();
    	
        ExecutorService service = Executors.newFixedThreadPool(10000);
        long startTime = System.currentTimeMillis();
        
        for (int i =0; i<10000; i++){
            service.submit(new Task(i, synchronizedCounter));
        }
        System.out.println(System.currentTimeMillis() - startTime);
     }
}
