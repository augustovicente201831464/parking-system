package com.cunoc.edu.gt.data.pagination.util;

import com.cunoc.edu.gt.data.pagination.Pageable;
import com.cunoc.edu.gt.data.pagination.Slice;
import com.cunoc.edu.gt.utils.Assert;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

abstract class Chunk<T> implements Slice<T>, Serializable {

    @Serial
    private static final long serialVersionUID = 867755909294344406L;

    private final List<T> content = new ArrayList<>();
    private final Pageable pageable;

    /**
     * Creates a new {@link Chunk} with the given content and the given governing {@link Pageable}.
     *
     * @param content  must not be {@literal null}.
     * @param pageable must not be {@literal null}.
     */
    public Chunk(List<T> content, Pageable pageable) {

        Assert.notNull(content, "Content must not be null");
        Assert.notNull(pageable, "Pageable must not be null");

        this.content.addAll(content);
        this.pageable = pageable;
    }

    public int getNumber() {
        return pageable.isPaged() ? pageable.getPageNumber() : 0;
    }

    public int getSize() {
        return pageable.isPaged() ? pageable.getPageSize() : content.size();
    }

    public int getNumberOfElements() {
        return content.size();
    }

    public boolean hasPrevious() {
        return getNumber() > 0;
    }

    public boolean isFirst() {
        return !hasPrevious();
    }

    public boolean isLast() {
        return !hasNext();
    }

    public Pageable nextPageable() {
        return hasNext() ? pageable.next() : Pageable.unpaged();
    }

    public Pageable previousPageable() {
        return hasPrevious() ? pageable.previousOrFirst() : Pageable.unpaged();
    }

    public boolean hasContent() {
        return !content.isEmpty();
    }

    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    @Override
    public Pageable getPageable() {
        return pageable;
    }

    @Override
    public Sort getSort() {
        return pageable.getSort();
    }

    public Iterator<T> iterator() {
        return content.iterator();
    }

    /**
     * Applies the given {@link Function} to the content of the {@link Chunk}.
     *
     * @param converter must not be {@literal null}.
     * @return
     */
    protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {

        Assert.notNull(converter, "Function must not be null");

        return this.stream().map(converter::apply).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Chunk<?> that)) {
            return false;
        }

        boolean contentEqual = this.content.equals(that.content);
        boolean pageableEqual = this.pageable.equals(that.pageable);

        return contentEqual && pageableEqual;
    }

    @Override
    public int hashCode() {

        int result = 17;

        result += 31 * pageable.hashCode();
        result += 31 * content.hashCode();

        return result;
    }
}
