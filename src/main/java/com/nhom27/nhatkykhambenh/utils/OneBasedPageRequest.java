package com.nhom27.nhatkykhambenh.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class OneBasedPageRequest extends PageRequest {

    public OneBasedPageRequest(int page, int size) {
        super(page - 1, size, Sort.unsorted());
    }

    public OneBasedPageRequest(int page, int size, Sort sort) {
        super(page - 1, size, sort);
    }


    @Override
    public int getPageNumber() {
        return super.getPageNumber() + 1;
    }
}
