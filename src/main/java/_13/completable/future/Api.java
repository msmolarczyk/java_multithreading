package _13.completable.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Api {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

//        basic();

//        runAsync();

//        chainedEvents();

//        thenCombine();
        
//        thenCompose();
    }

    private static void thenCompose() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> future = 
                CompletableFuture.supplyAsync(() -> 1)
                                 .thenApply(x -> x+1);
        System.out.println(future.get());

            CompletableFuture<Integer> future2 = 
                CompletableFuture.supplyAsync(() -> 1)
                                 .thenCompose(x -> CompletableFuture.supplyAsync(() -> x+1));
            System.out.println(future2.get());
    }

    private static void runAsync() {
        @SuppressWarnings("static-access")
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(2);
                System.out.println("runAsync: 1");
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("I'll run in a separate thread than the main thread.");
        }).runAsync(() -> {
            try {
                Thread.sleep(1);
                System.out.println("runAsync: 2");
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("I'll run in a separate thread than the main thread.");
        });

        @SuppressWarnings("static-access")
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2);
                System.out.println("supplyAsync: 1");
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of the asynchronous computation";
        }).supplyAsync(() -> {
            try {
                Thread.sleep(1);
                System.out.println("supplyAsync: 2");
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of the asynchronous computation";
        });
    }

    private static void thenCombine() throws InterruptedException, ExecutionException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            return "Combining two CompletableFutures";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            return "and getting a new CompletableFuture";
        });

        CompletableFuture<String> result = future1.thenCombine(future2, (str1, str2) -> str1 + " " + str2);
        System.out.println("Value- " + result.get());
    }

    private static void basic() {
        CompletableFuture<String> f1 = new CompletableFuture<>();
        CompletableFuture<String> f2 = CompletableFuture.completedFuture("OK");
//        CompletableFuture<String> f2 = CompletableFuture.failedFuture
        CompletableFuture<Void> f3 = CompletableFuture.runAsync(() -> {
            System.out.println("Running asynchronously");
        });
    }

    private static void chainedEvents() throws InterruptedException, ExecutionException {
        CompletableFuture<Double> future = CompletableFuture.completedFuture("STRING")
                .thenApply(String::toLowerCase)
                .thenApply(String::length)
                .thenApplyAsync(i -> i + 123)
                .thenApplyAsync(i -> Math.E * i)
                .exceptionally(i -> -1.0);
        System.out.println(future.get());
    }
}
