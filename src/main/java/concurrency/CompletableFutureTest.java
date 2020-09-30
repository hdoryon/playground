package concurrency;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureTest {


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String s = "null";
//        final ExecutorService executorService = Executors.newFixedThreadPool(4);
        final CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("supplyAsync " + Thread.currentThread().getName());
            return 1;
        }).thenApply(i -> {
            System.out.println("thenApply      " + Thread.currentThread().getName());
            return i + 1;
        }).thenApplyAsync(i -> {
            System.out.println("thenApplyAsync " + Thread.currentThread().getName());
            s.toCharArray();
            return i + 1;
        }).handleAsync((integer, throwable) -> {
            if (throwable == null) {
                return integer;
            }
            System.out.println("throwable:" + throwable);
            throwable.printStackTrace();
            return 15;
        });

//        completableFuture.e
//        Thread.sleep(1000);
//        completableFuture.completeExceptionally(new Exception("aaaaa"));
        System.out.println("isDone = " + completableFuture.isDone());
        System.out.println("isException = " + completableFuture.isCompletedExceptionally());
        final Integer integer = completableFuture.get();
        System.out.println(integer);
        System.out.println(integer);
        System.out.println(integer);
        System.out.println(integer);
        System.out.println(integer);
        System.out.println(0);
    }
}
