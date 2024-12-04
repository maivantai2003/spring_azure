package com.nhom27.nhatkykhambenh.utils;

import org.springframework.ui.Model;

public class ModelUtils {
    public static void addPaginationAttributes(Model model, int page, int size, int totalPages, long totalItems, String query) {
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("query", query);
    }
}
