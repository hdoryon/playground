package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {

    public static void main(String[] args) {
        final ExecutorService executorService = Executors.newFixedThreadPool(4);
//        executorService.submit(() -> {
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//
//            }
//            System.out.println(1);
//        });

//        executorService.shutdown();


    }
}
