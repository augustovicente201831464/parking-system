package com.cunoc.edu.gt.data.pagination;

import com.cunoc.edu.gt.utils.Assert;
import com.cunoc.edu.gt.data.pagination.util.PageRequest;
import com.cunoc.edu.gt.data.pagination.util.Sort;
import com.cunoc.edu.gt.data.pagination.util.Unpaged;

import java.util.Optional;


public interface Pageable {

    static Pageable unpaged() {
        return Unpaged.INSTANCE;
    }

    static Pageable ofSize(int pageSize) {
        return PageRequest.of(0, pageSize);
    }
    default boolean isPaged() {
        return true;
    }
    default boolean isUnpaged() {
        return !isPaged();
    }
    int getPageNumber();
    int getPageSize();
    long getOffset();
    Sort getSort();

    default Sort getSortOr(Sort sort) {
        Assert.notNull(sort, "Fallback Sort must not be null");
        return getSort().isSorted() ? getSort() : sort;
    }

    default Optional<Pageable> toOptional() {
        return isUnpaged() ? Optional.empty() : Optional.of(this);
    }

    Pageable next();
    Pageable previousOrFirst();
    Pageable first();
    Pageable withPage(int pageNumber);
    boolean hasPrevious();
}