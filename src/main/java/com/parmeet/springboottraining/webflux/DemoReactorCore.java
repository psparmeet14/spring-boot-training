package com.parmeet.springboottraining.webflux;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DemoReactorCore {

    public void testFluxAndMonoNoSubscribe() {
        Flux<Integer> just = Flux.just(1, 2, 3, 4);
        Mono<Integer> just1 = Mono.just(1);
        Publisher<String> just2 = Mono.just("foo");
    }

    public void testFluxAndMonoWithSubscribe() {
        List<Integer> elements = new ArrayList<>();
        Flux.just(1, 2, 3, 4).subscribe(elements::add);
        System.out.println("testFluxAndMonoWithSubscribe() = " + elements);
    }

    public void testFluxAndMonoWithSubscriberInterface() {
        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        elements.add(integer);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        System.out.println(elements);
    }

    public void testFluxAndMonoBackpressure() {
        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .subscribe(new Subscriber<Integer>() {
                    private Subscription s;
                    int onNextAmount;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.s = s;
                        s.request(2);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        elements.add(integer);
                        onNextAmount++;
                        if (elements.size() % 2 == 0) {
                            s.request(2);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        System.out.println(elements);
    }

    public void testFluxAndMonoMapOperation() {
        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .map(i -> i * 2) // map() will be applied when onNext() is called
                .subscribe(elements::add);

        System.out.println("testFluxAndMonoMapOperation() = " + elements);
    }

    public void testFluxAndMonoCombine() {
        List<Integer> elements = new ArrayList<>();

        Flux.just(1, 2)
                .concatWith(Flux.just(3, 4))
                .subscribe(elements::add);

        System.out.println("testFluxAndMonoCombine() = " + elements);
    }

    public void testFluxAndMonoCombineZipWith() {
        List<String> elements = new ArrayList<>();

        Flux.just(1, 2, 3, 4)
                .map(i -> i * 2)
                .zipWith(Flux.range(0, Integer.MAX_VALUE),
                        (one, two) -> String.format("First Flux: %d, Second Flux: %d", one, two))
                .subscribe(elements::add);

        System.out.println("testFluxAndMonoCombineZipWith() = " + elements);

        // We will have one Subscription per flux.
        // onNext() calls are also alternated.
    }

    public void testCompletableFlux() {
        var publish = Flux.create(fluxSink -> {
                    while (true) {
                        fluxSink.next(System.currentTimeMillis());
                    }
                })
                .sample(Duration.ofSeconds(2))
                .publish();

        publish.subscribe(System.out::println);
        publish.subscribe(System.out::println);

        publish.connect();
    }

    public void testFluxDifferentThread() {
        List<Integer> elements = new ArrayList<>();
        Flux.just(1, 2, 3, 4)
                .map(i -> i * 2)
                .subscribeOn(Schedulers.parallel())
                .subscribe(elements::add);
        System.out.println("testFluxDifferentThread() = " + elements);
    }
}
