package com.cunoc.edu.gt.data.pagination.util;

import com.cunoc.edu.gt.data.pagination.Page;
import com.cunoc.edu.gt.data.pagination.Pageable;

import java.io.Serial;
import java.util.List;
import java.util.function.Function;

/**
 * Implementation of {@link Page}.
 *
 * @param <T> the type of which the page consists.
 * @Author: Augusto Vicente
 */
public class PageImpl<T> extends Chunk<T> implements Page<T> {

    @Serial
    private static final long serialVersionUID = 867755909294344406L;

    private final long total;

    public PageImpl(List<T> content, Pageable pageable, long total) {

        super(content, pageable);

        this.total = pageable.toOptional().filter(it -> !content.isEmpty())//
                .filter(it -> it.getOffset() + it.getPageSize() > total)//
                .map(it -> it.getOffset() + content.size())//
                .orElse(total);
    }

    public PageImpl(List<T> content) {
        this(content, Pageable.unpaged(), null == content ? 0 : content.size());
    }

    @Override
    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
    }

    @Override
    public long getTotalElements() {
        return total;
    }

    @Override
    public boolean hasNext() {
        return getNumber() + 1 < getTotalPages();
    }

    @Override
    public boolean isLast() {
        return !hasNext();
    }

    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return new PageImpl<>(getConvertedContent(converter), getPageable(), total);
    }

    @Override
    public String toString() {

        String contentType = "UNKNOWN";
        List<T> content = getContent();

        if (!content.isEmpty() && content.get(0) != null) {
            contentType = content.get(0).getClass().getName();
        }

        return String.format("Page %s of %d containing %s instances", getNumber() + 1, getTotalPages(), contentType);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PageImpl<?> that)) {
            return false;
        }

        return this.total == that.total && super.equals(obj);
    }

    @Override
    public int hashCode() {

        int result = 17;

        result += 31 * (int) (total ^ total >>> 32);
        result += 31 * super.hashCode();

        return result;
    }
}