package com.agrifake.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CompleteController {

    @GetMapping("/complete")
    public String complete() {
        return "common/components/form-modal/complete";
    }
}