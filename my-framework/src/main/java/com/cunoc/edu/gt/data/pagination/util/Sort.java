package com.cunoc.edu.gt.data.pagination.util;

import com.cunoc.edu.gt.utils.Assert;
import com.cunoc.edu.gt.utils.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Sort {

    private static final long serialVersionUID = 5737186511678863905L;
    private static final Sort UNSORTED = Sort.by(new Order[0]);
    public static final Direction DEFAULT_DIRECTION = Direction.ASC;
    private final List<Order> orders;

    protected Sort(List<Order> orders) {
        this.orders = orders;
    }

    public static Sort by(Order... orders) {

        return new Sort(Arrays.asList(orders));
    }

    public static Sort by(List<Order> orders) {

        Assert.notNull(orders, "Orders must not be null");

        return orders.isEmpty() ? Sort.unsorted() : new Sort(orders);
    }

    public static Sort by(Direction direction, String... properties) {

        Assert.notNull(direction, "Direction must not be null");
        Assert.notNull(properties, "Properties must not be null");
        Assert.isTrue(properties.length > 0, "At least one property must be given");

        return Sort.by(Arrays.stream(properties)//
                .map(it -> new Order(direction, it))//
                .collect(Collectors.toList()));
    }

    public static Sort unsorted() {
        return UNSORTED;
    }

    public boolean isSorted() {
        return !isEmpty();
    }

    public boolean isEmpty() {
        return orders.isEmpty();
    }

    public static class Order implements Serializable {

        public Order(Direction direction, String property) {
            this(direction, property, DEFAULT_IGNORE_CASE, DEFAULT_NULL_HANDLING);
        }

        public Order(Direction direction, String property, boolean ignoreCase, NullHandling nullHandling) {

            if (!StringUtils.hasText(property)) {
                throw new IllegalArgumentException("Property must not be null or empty");
            }

            this.direction = direction == null ? DEFAULT_DIRECTION : direction;
            this.property = property;
            this.ignoreCase = ignoreCase;
            this.nullHandling = nullHandling;
        }

        private static final long serialVersionUID = 1522511010900108987L;
        private static final boolean DEFAULT_IGNORE_CASE = false;
        private static final NullHandling DEFAULT_NULL_HANDLING = NullHandling.NATIVE;

        private final Direction direction;
        private final String property;
        private final boolean ignoreCase;
        private final NullHandling nullHandling;
    }


    public enum Direction {
        ASC, DESC;
    }

    public enum NullHandling {
        NATIVE,
        NULLS_FIRST,
        NULLS_LAST;
    }
}