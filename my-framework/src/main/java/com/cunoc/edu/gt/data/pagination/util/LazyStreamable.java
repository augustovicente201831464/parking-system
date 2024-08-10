package com.cunoc.edu.gt.data.pagination.util;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

final class LazyStreamable<T> implements Streamable<T> {

    private final Supplier<? extends Stream<T>> stream;

    private LazyStreamable(Supplier<? extends Stream<T>> stream) {
        this.stream = stream;
    }

    public static <T> LazyStreamable<T> of(Supplier<? extends Stream<T>> stream) {
        return new LazyStreamable<T>(stream);
    }

    @Override
    public Iterator<T> iterator() {
        return stream().iterator();
    }

    @Override
    public Stream<T> stream() {
        return stream.get();
    }

    public Supplier<? extends Stream<T>> getStream() {
        return this.stream;
    }

    @Override
    public String toString() {
        return "LazyStreamable(stream=" + this.getStream() + ")";
    }
}