package _03.explicit.locks;

import java.util.concurrent.locks.ReentrantLock;

public class LockVsSynchronized {
	
	private final ReentrantLock lock = new ReentrantLock();
	
	   public void lockedMethod() {
		     lock.lock();  // block until condition holds
		     try {
		       // ... method body
		     } finally {
		       lock.unlock();
		     }
	   }
	   
	   public synchronized void synchronizedMethod() {
		       // ... method body
	   }
	   
	   public static void main(String[] args) {
		
		   Object o = new Object();
		   synchronized(o){
			   // synchronized block body
		   }
	}

}
