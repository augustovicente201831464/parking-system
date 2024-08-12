package com.cunoc.edu.gt.input.handling.pagination;

import com.cunoc.edu.gt.constants.AttributeNameConstant;
import com.cunoc.edu.gt.data.pagination.util.PageRequest;
import com.cunoc.edu.gt.data.pagination.util.Sort;
import jakarta.servlet.http.HttpServletRequest;

import java.util.logging.Logger;

public class PaginationHandling {

    public static PageRequest handlePagination(HttpServletRequest req) {
        int page = req.getParameter(AttributeNameConstant.PAGE) == null ? 1 : Integer.parseInt(req.getParameter(AttributeNameConstant.PAGE));
        int size = req.getParameter(AttributeNameConstant.SIZE) == null ? 10 : Integer.parseInt(req.getParameter(AttributeNameConstant.SIZE));
        String sort = req.getParameter(AttributeNameConstant.SORT);
        if (sort == null || sort.isEmpty()) {
            sort = "codigo";
        }

        boolean asc = req.getParameter(AttributeNameConstant.DIRECTION).equals("true");
        return PageRequest.of(page - 1, size, Sort.by(asc ? Sort.Direction.ASC : Sort.Direction.DESC, sort));
    }
}