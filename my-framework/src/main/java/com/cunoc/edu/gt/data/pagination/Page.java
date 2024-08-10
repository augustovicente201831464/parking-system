package com.cunoc.edu.gt.data.pagination;

import com.cunoc.edu.gt.data.pagination.util.PageImpl;

import java.util.Collections;
import java.util.function.Function;

public interface Page<T> extends Slice<T> {

    static <T> Page<T> empty() {
        return empty(Pageable.unpaged());
    }

    static <T> Page<T> empty(Pageable pageable) {
        return new PageImpl<>(Collections.emptyList(), pageable, 0);
    }

    int getTotalPages();

    long getTotalElements();

    <U> Page<U> map(Function<? super T, ? extends U> converter);
}