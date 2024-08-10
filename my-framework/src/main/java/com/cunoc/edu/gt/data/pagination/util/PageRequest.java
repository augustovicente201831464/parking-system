package com.cunoc.edu.gt.data.pagination.util;

import com.cunoc.edu.gt.data.pagination.Pageable;

import java.io.Serial;
import java.util.Optional;

public class PageRequest extends AbstractPageRequest {

    public static PageRequest of(int pageNumber, int pageSize, Sort sort) {
        return new PageRequest(pageNumber, pageSize, sort);
    }

    public static PageRequest of(int pageNumber, int pageSize) {
        return of(pageNumber, pageSize, Sort.unsorted());
    }

    @Override
    public boolean isPaged() {
        return super.isPaged();
    }

    @Override
    public boolean isUnpaged() {
        return super.isUnpaged();
    }

    @Override
    public Sort getSort() {
        return null;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previous() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    public PageRequest withSort(Sort.Direction direction, String... properties) {
        return new PageRequest(getPageNumber(), getPageSize(), Sort.by(direction, properties));
    }

    @Override
    public Optional<Pageable> toOptional() {
        return super.toOptional();
    }

    protected PageRequest(int pageNumber, int pageSize, Sort sort) {
        super(pageNumber, pageSize);
    }

    @Serial
    private static final long serialVersionUID = -4541509938956089562L;
}