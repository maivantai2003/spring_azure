package com.nhom27.nhatkykhambenh.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("admin")
    public String adminPage(HttpSession session) {
        List<String> roles = (List<String>) session.getAttribute("roles");
        if (roles == null || !roles.contains("ADMIN")) {
            return "redirect:/";
        }
        return "admin/dashboard";
    }

    @GetMapping("base")
    public String base() {
        return "base";
    }

    @GetMapping("admin/thongke")
    public String thongKe() {
        return "admin/thongke";
    }

}
