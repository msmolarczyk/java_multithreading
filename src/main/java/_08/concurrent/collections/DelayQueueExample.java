package _08.concurrent.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueExample {

	private static class DelayedTest implements Delayed {
		public long delayTime = 10;
		private long origin;

		DelayedTest(long delayTime) {
			this.delayTime = delayTime;
			this.origin = System.currentTimeMillis();
		}

		@Override
		public int compareTo(Delayed ob) {
			if (this.delayTime < ((DelayedTest) ob).delayTime) {
				return -1;
			} else if (this.delayTime > ((DelayedTest) ob).delayTime) {
				return 1;
			}
			return 0;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			return unit.convert(delayTime - (System.currentTimeMillis() - origin),
					TimeUnit.NANOSECONDS);
		}
	}

	public static void main(String... args) throws InterruptedException {
		DelayQueue<DelayedTest> dq = new DelayQueue<DelayedTest>();
		DelayedTest ob1 = new DelayedTest(10);
		DelayedTest ob2 = new DelayedTest(0);
		DelayedTest ob3 = new DelayedTest(15);
		DelayedTest ob4 = new DelayedTest(2000);

		dq.offer(ob1);
		dq.offer(ob2);
		dq.offer(ob3);
		dq.offer(ob4);

		Thread.sleep(200);
		
		//System.out.println(dq.take().delayTime);
		
		final Collection<DelayedTest> expired = new ArrayList<DelayedTest>();
		dq.drainTo(expired);
		
		for (DelayedTest delayedTest : expired) {
			System.out.println(delayedTest.delayTime);
		}
		
		System.out.println("Iterator:");
		Iterator<DelayedTest> itr = dq.iterator();
		while (itr.hasNext()) {
			DelayedTest dt = (DelayedTest) itr.next();
			System.out.println(dt.delayTime);
		}
	}
}
