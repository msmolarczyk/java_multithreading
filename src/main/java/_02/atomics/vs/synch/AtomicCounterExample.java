package _02.atomics.vs.synch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;



public class AtomicCounterExample {
	
	private static class Task implements Runnable {
	    private int taskId;
	    private AtomicCounter atomicCounter;
	  
	    public Task(int id, AtomicCounter atomicCounter){
	        this.taskId = id;
	        this.atomicCounter = atomicCounter;
	    }
	  
	    @Override
	    public void run() {
	        System.out.println("Task ID : " + this.taskId +" performed by " 
	                           + Thread.currentThread().getName() + " counter: " + atomicCounter.getNextValue());
	    }
	}

	private static class AtomicCounter {
	    private final AtomicInteger value = new AtomicInteger(0);

	    public int getValue(){
	        return value.get();
	    }

	    public int getNextValue(){
	    	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return value.incrementAndGet();
	    }

	    public int getPreviousValue(){
	        return value.decrementAndGet();
	    }
	}
	
	
    public static void main(String args[]) {
    	
    	AtomicCounter atomicCounter = new AtomicCounter();
    	
        ExecutorService service = Executors.newFixedThreadPool(10000);
        long startTime = System.currentTimeMillis();
        for (int i =0; i<10000; i++){
            service.submit(new Task(i, atomicCounter));
        }
        System.out.println(System.currentTimeMillis() - startTime);
     }
}
