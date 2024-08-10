package com.cunoc.edu.gt.utils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;

import static java.util.stream.Collectors.*;

public interface StreamUtils {

    static <T> Collector<T, ?, List<T>> toUnmodifiableList() {
        return collectingAndThen(toList(), Collections::unmodifiableList);
    }

    static <T> Collector<T, ?, Set<T>> toUnmodifiableSet() {
        return collectingAndThen(toSet(), Collections::unmodifiableSet);
    }
}
