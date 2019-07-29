package _13.completable.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Api {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        
        CompletableFuture<String> f1 = new CompletableFuture<>();
        CompletableFuture<String> f2 = CompletableFuture.completedFuture("OK");
//        CompletableFuture<String> f2 = CompletableFuture.failedFuture
        CompletableFuture<Void> f3 = CompletableFuture.runAsync(() -> {
            System.out.println("Running asynchronously");
        });
//        f3.
        
        CompletableFuture<Double> future = CompletableFuture.completedFuture("STRING")
                .thenApply(String::toLowerCase)
                .thenApply(String::length)
                .thenApplyAsync(i -> i + 123)
                .thenApplyAsync(i -> Math.E * i);
        System.out.println(future.get());
        
        /////////////////
        
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            return "Combining two CompletableFutures";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            return "and getting a new CompletableFuture";
        });

        CompletableFuture<String> result = future1.thenCombine(future2, (str1, str2) -> str1 + " " + str2);
        System.out.println("Value- " + result.get());
        
        
        ////////////
        
        CompletableFuture<Integer> f4 = CompletableFuture.supplyAsync( () -> 0);
        f4.thenApplyAsync( x -> x + 1 )
        .thenApplyAsync( x -> x + 1 )
        .thenAcceptAsync( x -> System.out.println(x));
        
    }
}
