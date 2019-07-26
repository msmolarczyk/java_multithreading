package _13.completable.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture<String> completableFuture = new CompletableFuture<String>();
        completableFuture.complete("Future's Result");
        String result = completableFuture.get();
        System.out.println(result);
        
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            // Simulate a long-running Job   
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("I'll run in a separate thread than the main thread.");
        });
        
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Result of the asynchronous computation";
        });
        String result2 = future2.get();
        System.out.println(result2);
        
        
        /////////////
        
        CompletableFuture<String> whatsYourNameFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Rajeev";
         });

         // Attach a callback to the Future using thenApply()
         CompletableFuture<String> greetingFuture = whatsYourNameFuture.thenApply(name -> {
            return "Hello " + name;
         });

         // Block and get the result of the future.
         System.out.println(greetingFuture.get()); // Hello Rajeev
         
         ///////////////
         
         CompletableFuture<String> welcomeText = CompletableFuture.supplyAsync(() -> {
             try {
                 TimeUnit.SECONDS.sleep(1);
             } catch (InterruptedException e) {
                throw new IllegalStateException(e);
             }
             return "Rajeev";
         }).thenApply(name -> {
             return "Hello " + name;
         }).thenApply(greeting -> {
             return greeting + ", Welcome to the CalliCoder Blog";
         });

         System.out.println(welcomeText.get());
         
         //////////////////
         
         combiningFutures();
         
         CompletableFuture<Integer> f = CompletableFuture.supplyAsync( () -> 0);
         f.thenApply( x -> x + 1 )
         .thenApply( x -> x + 1 )
         .thenAccept( x -> System.out.println(x));
         
         System.out.println("After thenApply");
         
         CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync( () -> 0);
         f2.thenApplyAsync( x -> x + 1 )
         .thenApplyAsync( x -> x + 1 )
         .thenAcceptAsync( x -> System.out.println(x));
         
         System.out.println("After thenAcceptAsync");
    }

    private static void combiningFutures() throws InterruptedException, ExecutionException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
             return "Combining two CompletableFutures";
         });

         CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
             return "and getting a new CompletableFuture";
         });

         CompletableFuture<String> result = future1.thenCombine(future2, (str1, str2) -> str1 + " " + str2);
         System.out.println("Value- " + result.get());
    }
}
