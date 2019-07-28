package blocking.queue;

import java.util.LinkedList;

public class Main2 {
    public static void main(String[] argv) {
        WorkQueue queue = new WorkQueue();

        int numWorkers = 2;
        Worker2[] workers = new Worker2[numWorkers];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker2(queue);
            workers[i].start();
        }

        for (int i = 0; i < 100; i++) {
            queue.addWork(i);
        }
    }
}

class WorkQueue {
    LinkedList<Object> queue = new LinkedList<Object>();

    public synchronized void addWork(Object o) {
        queue.addLast(o);
        notify();
    }

    public synchronized Object getWork() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return queue.removeFirst();
    }
}

class Worker2 extends Thread {
    WorkQueue q;

    Worker2(WorkQueue q) {
        this.q = q;
    }

    public void run() {
        try {
            while (true) {
                Object x = q.getWork();

                if (x == null) {
                    break;
                }
                System.out.println(x);
            }
        } catch (InterruptedException e) {
        }
    }
}
