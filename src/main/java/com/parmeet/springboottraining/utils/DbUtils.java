package com.parmeet.springboottraining.utils;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Objects.nonNull;

public class DbUtils {

    public static <T> void updateIfPresent(Consumer<T> consumer, T value) {
        if (nonNull(value)) {
            consumer.accept(value);
        }
    }

    public static <T> void updateIfChanged(Consumer<T> consumer, T value, Supplier<T> supplier) {
        Predicate<T> shouldUpdate = (match) -> !value.equals(match);

        if (nonNull(value) && shouldUpdate.test(supplier.get())) {
            consumer.accept(value);
        }
    }
}
