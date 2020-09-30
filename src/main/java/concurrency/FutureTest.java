package concurrency;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.*;

public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        run1();
    }

    private static void run1() throws ExecutionException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ListeningExecutorService listeningExecutorService =
                MoreExecutors.listeningDecorator(
//                        MoreExecutors.getExitingExecutorService(
                                (ThreadPoolExecutor) Executors.newFixedThreadPool(3)
//                                , 10, TimeUnit.SECONDS)
                                );
//        listeningExecutorService.invokeAll()
        ListenableFuture<String> future = listeningExecutorService.submit(
                () -> {Object o = null;
                    System.out.println("before sleep");
                    countDownLatch.countDown();
                    Thread.sleep(4000);
                    System.out.println("after sleep");;
                return "a";}
        );

        countDownLatch.await();
        System.out.println("done");

    }
}
