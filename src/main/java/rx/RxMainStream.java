package rx;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RxMainStream {

    public static void main(String[] args) throws InterruptedException {
        final Observable<Integer> observable = Observable.create(subscriber -> {
            try {
                System.out.println("emitter thread " + Thread.currentThread().getName());
                for (int i = 0; i < 10; i++) {
                    subscriber.onNext(i);
                }
//                Thread.sleep(0);
                System.out.println("out of emitter");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        final Disposable disposable = observable
                .observeOn(Schedulers.computation())
                .subscribe(integer ->
                        System.out.println("inside consumer1 thread " + Thread.currentThread().getName() + " "  + integer))
                ;
        final Disposable disposable2 = observable
                .observeOn(Schedulers.computation())
                .subscribe(integer ->
                        System.out.println("inside consumer2 thread " + Thread.currentThread().getName() + " "  + integer))
                ;

        Thread.sleep(10000);

    }
}
