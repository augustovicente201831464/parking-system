package com.cunoc.edu.gt.data.pagination.util;

import com.cunoc.edu.gt.data.pagination.Pageable;

import java.io.Serializable;

public abstract class AbstractPageRequest implements Pageable, Serializable {

    public AbstractPageRequest(int pageNumber, int pageSize) {

        if (pageNumber < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero");
        }

        if (pageSize < 1) {
            throw new IllegalArgumentException("Page size must not be less than one");
        }

        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public long getOffset() {
        return (long) pageNumber * (long) pageSize;
    }

    @Override
    public boolean hasPrevious() {
        return pageNumber > 0;
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public abstract Pageable next();

    public abstract Pageable previous();

    @Override
    public abstract Pageable first();

    @Override
    public int hashCode() {

        final int prime = 31;
        int result = 1;

        result = prime * result + pageNumber;
        result = prime * result + pageSize;

        return result;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AbstractPageRequest other = (AbstractPageRequest) obj;
        return pageNumber == other.pageNumber && pageSize == other.pageSize;
    }

    private final int pageNumber;
    private final int pageSize;

    private static final long serialVersionUID = 1232825578694716871L;
}