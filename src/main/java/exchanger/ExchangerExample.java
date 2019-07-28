package exchanger;

import java.util.concurrent.Exchanger;

class ExchangerRunnable implements Runnable {

    Exchanger<String> exchanger = null;
    String object = null;

    public ExchangerRunnable(Exchanger<String> exchanger, String object) {
        this.exchanger = exchanger;
        this.object = object;
    }

    public void run() {
        try {
            String previous = object;

            object = exchanger.exchange(object);

            System.out.println(Thread.currentThread().getName() + " exchanged " + previous + " for " + object);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ExchangerExample {

    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<String>();

        ExchangerRunnable exchangerRunnable1 = new ExchangerRunnable(exchanger, "A");

        ExchangerRunnable exchangerRunnable2 = new ExchangerRunnable(exchanger, "B");

        new Thread(exchangerRunnable1).start();
        new Thread(exchangerRunnable2).start();
    }

}
