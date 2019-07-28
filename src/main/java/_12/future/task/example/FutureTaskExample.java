package _12.future.task.example;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Callable<String> eval = new Callable<String>() {
            public String call() throws InterruptedException {
                Thread.sleep(4000);
                return "some result";
            }
        };
        FutureTask<String> ft = new FutureTask<String>(eval);

        new Thread(() -> {
            ft.run(); // get() needs executor

        }).start();
        System.out.println("Awaiting result: ");
        System.out.println(ft.get());
        System.out.println(ft.get());
    }
}