package com.Iris.demo.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageRequestUtil extends PageRequest {
    protected PageRequestUtil(int page, int size, Sort sort) {
        super(page, size, sort);
    }

    public static PageRequest of(int displayPageNumber, int numberOfRowsPerPage, String ordering, String orderingField) {
        return of(
                displayPageNumber - 1,
                numberOfRowsPerPage,
                Sort.Direction.valueOf(ordering),
                orderingField);
    }
}
