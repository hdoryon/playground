package rx;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.Random;

public class RxMain {

    public static void main(String[] args) throws InterruptedException {
        final Observable<String> just = Observable.just("long", "longer", "longest");
        just
                .flatMap(v ->
                        performLongOperation(v)
                                .doOnNext(s -> System.out.println("processing item on thread " + Thread.currentThread().getName()))
                                .subscribeOn(Schedulers.newThread())
                )
                .map(integer -> {
                    System.out.println("inside lmabda" + Thread.currentThread().getName());
                    ;
                    return integer + 1;
                })
                .subscribe(length -> System.out.println("received item length " + length + " on thread " + Thread.currentThread().getName()));

        just
                .flatMap(v ->
                        performLongOperation(v)
                                .doOnNext(s -> System.out.println("processing item on thread " + Thread.currentThread().getName()))
                                .subscribeOn(Schedulers.newThread())
                )
                .map(integer -> {
                    System.out.println("inside lmabda" + Thread.currentThread().getName());
                    ;
                    return integer + 1;
                })
                .subscribe(length -> System.out.println("received item length " + length + " on thread " + Thread.currentThread().getName()));

        Thread.sleep(10000);
    }

    /**
     * Returns length of each param wrapped into an Observable.
     */
    protected static Observable<Integer> performLongOperation(String v) {
        Random random = new Random();
        try {
            System.out.println("inside long op with thread" + Thread.currentThread().getName());
            Thread.sleep(random.nextInt(3) * 1000);
            return Observable.just(v.length());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
