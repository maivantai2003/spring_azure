package com.nhom27.nhatkykhambenh.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PaginationResponse<T> {

    private int totalItem;
    private int totalPages;
    private int currentPage;
    private int pageSize;
    private boolean isFirst;
    private boolean isLast;
    private List<T> items;

    private PaginationResponse() {
    
    }

    public static <T, E> PaginationResponse<T> create(Page<E> page) {
        PaginationResponse<T> pagination = new PaginationResponse<>();
        pagination.totalItem = (int) page.getTotalElements();
        pagination.totalPages = page.getTotalPages();
        pagination.currentPage = (int) page.getNumber();
        pagination.pageSize = page.getSize();
        pagination.isFirst = page.isFirst();
        pagination.isLast = page.isLast();
        return pagination;
    }

}