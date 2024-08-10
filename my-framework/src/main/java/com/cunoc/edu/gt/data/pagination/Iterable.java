package com.cunoc.edu.gt.data.pagination;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/**
 * Interface for a Java Streamable. This is basically a {@link Iterable} with a few additional methods.
 *
 * @param <T> the type of the elements in the stream
 */
public interface Iterable<T> {
    Iterator<T> iterator();

    default void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        Iterator var2 = this.iterator();

        while(var2.hasNext()) {
            T t = (T) var2.next();
            action.accept(t);
        }

    }

    default Spliterator<T> spliterator() {
        return Spliterators.spliteratorUnknownSize(this.iterator(), 0);
    }
}