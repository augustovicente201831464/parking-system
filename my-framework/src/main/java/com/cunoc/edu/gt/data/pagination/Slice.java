package com.cunoc.edu.gt.data.pagination;

import com.cunoc.edu.gt.data.pagination.util.PageRequest;
import com.cunoc.edu.gt.data.pagination.util.Sort;
import com.cunoc.edu.gt.data.pagination.util.Streamable;
import com.cunoc.edu.gt.data.pagination.util.Unpaged;

import java.util.List;
import java.util.function.Function;

public interface Slice<T> extends Streamable<T> {

    static Pageable unpaged() {
        return Unpaged.INSTANCE;
    }

    int getNumber();
    int getSize();
    int getNumberOfElements();
    List<T> getContent();
    boolean hasContent();
    boolean isFirst();
    boolean isLast();
    boolean hasNext();
    Sort getSort();
    boolean hasPrevious();
    default Pageable getPageable() {
        return PageRequest.of(getNumber(), getSize(), getSort());
    }
    Pageable nextPageable();
    Pageable previousPageable();
    <U> Slice<U> map(Function<? super T, ? extends U> converter);
    default Pageable nextOrLastPageable() {
        return hasNext() ? nextPageable() : getPageable();
    }
    default Pageable previousOrFirstPageable() {
        return hasPrevious() ? previousPageable() : getPageable();
    }
}