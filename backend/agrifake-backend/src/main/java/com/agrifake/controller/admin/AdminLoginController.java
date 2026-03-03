package com.agrifake.controller.admin;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

// ========== Loginコントローラー ==========
@Controller
@RequestMapping("/admin")
public class AdminLoginController {

    @GetMapping("/login")
    public String login() {
        return "admin/pages/login";
    }
}