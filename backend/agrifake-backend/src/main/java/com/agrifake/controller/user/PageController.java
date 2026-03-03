package com.agrifake.controller.user;

import com.agrifake.dto.ConfirmModalDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.agrifake.dto.user.RecruitApplicationDto;
import com.agrifake.dto.user.ContactFormDto;
import org.springframework.ui.Model;

@Controller
public class PageController {

    // ===== TOPページ =====
    @GetMapping("/top")
    public String top() {
        return "user/pages/top";
    }

    // ===== 会社情報ページ =====
    @GetMapping("/company")
    public String company() {
        return "user/pages/company";
    }

    // ===== ギャラリーページ =====
    @GetMapping("/gallery")
    public String gallery() {
        return "user/pages/gallery";
    }

    // ===== 採用情報ページ =====
    @GetMapping("/recruit")
    public String recruit(Model model) {
      // ----- 採用応募確認モーダルテキスト -----
      model.addAttribute("confirm", new ConfirmModalDto(
          "応募内容の確認",
          "以下の内容で送信してよろしいですか？",
          "戻る",
          "送信"
      ));
      model.addAttribute("recruitApplicationDto", new RecruitApplicationDto());
        return "user/pages/recruit";
    }

    // ===== お問い合わせページ =====
    @GetMapping("/contact")
    public String contact(Model model) {
      // ----- お問い合わせ内容確認モーダルテキスト -----
      model.addAttribute("confirm", new ConfirmModalDto(
          "お問い合わせ内容の確認",
          "以下の内容で送信してよろしいですか？",
          "戻る",
          "送信"
      ));
      model.addAttribute("contactFormDto", new ContactFormDto());
        return "user/pages/contact";
    }
}