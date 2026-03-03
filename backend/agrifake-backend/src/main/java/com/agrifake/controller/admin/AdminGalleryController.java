package com.agrifake.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminGalleryController {

    @GetMapping("/gallery")
    public String gallery() {
        return "admin/pages/admin-gallery";
    }
}