package com.xj;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

public class Test {


    public static void main(String args[]) {
        System.out.println("runRx");
        runRx();
    }

    public static void runRx() {
        Observable<String> observable = Observable.create(
                new Observable.OnSubscribe<String>() {

                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("observable onNext");
                        subscriber.onCompleted();
                    }
                });

        Subscriber<String> stringSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");

            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError");
            }

            @Override
            public void onNext(String s) {
                System.out.println(s);
            }
        };

        Observable<String> observable1 = Observable.just("observable1 just");
        Action1<String> stringAction1 = (s) -> System.out.println("stringAction1 " + s);

//        observable.subscribe(stringSubscriber);
//        observable1.subscribe(stringAction1);
//
//        observable.subscribe(stringAction1);
//
//        Observable.just("observable1")
//                .map(s -> (s + "_jie").hashCode())
//                .map(i -> i + "")
//                .subscribe(s -> log(s));


        query("xj")
                .subscribe(list -> {
                    for (String s : list) {
                        log(s);
                    }
                });
        query("xj from")
                .subscribe(list -> {
                    Observable.from(list)
                            .subscribe(s -> log(s));
                });
        query("xj flatMap")
                .flatMap(list -> Observable.from(list))
                .subscribe(s -> log(s));

        query("xj flatMap getTitle")
                .flatMap(list -> Observable.from(list))
                .flatMap(s -> getTextTitle(s))
                .filter(t -> t != null)
                .take(2)
                .doOnNext(t -> save(t))
                .subscribe(t -> log(t));

    }

    public static Observable<List<String>> query(String text) {
        return Observable.just(getList(text));
    }

    public static List<String> getList(String text) {
        List<String> list = new ArrayList<>();
        list.add(text + "_a");
        list.add(text + "_b");
        list.add(text + "_c");
        return list;
    }

    public static Observable<String> getTextTitle(String text) {
        return Observable.just(text + "_title");
    }

    public static void save(String text) {
        log(text + "_save");
    }

    public static void log(String s) {
        System.out.println(s);
    }
}
