package com.agrifake.controller.user;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.agrifake.dto.user.ContactFormDto;
import com.agrifake.service.user.ContactFormService;


@Controller
@RequestMapping("/contact")
public class ContactFormController {
  
  // ===== サービスクラス定義（メール送信やファイル処理など） =====
  private final ContactFormService service;
  
  // ===== コンストラクタインジェクション =====
  public ContactFormController(ContactFormService service) {
    this.service = service;
  }
  
  // ========== お問い合わせフォーム送信処理（Ajax） ==========
  @PostMapping("/api")
  @ResponseBody
  public ResponseEntity<Map<String, String>> sendApplicationAjax(
      @Valid @ModelAttribute ContactFormDto dto, // フォーム入力内容とDTOにバインドしてバリデーション
      BindingResult bindingResult) // バリデーション結果を取得
      throws MessagingException, IOException {
    
    // ===== レスポンス用のMapを作成（モーダル表示用のタイトル・メッセージ） =====
    Map<String, String> response = new HashMap<>();
    
    // ===== 入力チェック（DTOのバリデーション結果） =====
    if (bindingResult.hasErrors()) {
      response.put("title", "入力エラー");
      response.put("message", "入力内容に不備があります");
      return ResponseEntity.badRequest().body(response); // HTTP 400 で返却
    }
    
    try {
      // ===== 応募内容をメール送信または保存するサービス呼び出し =====
      service.sendContact(dto);
      
    } catch (Exception e) {
      // ===== 予期せぬサーバーエラー =====
      response.put("title", "サーバーエラー");
      response.put("message", "サーバーエラーが発生しました");
      return ResponseEntity.internalServerError().body(response); // HTTP 500で返却
    }
    
    // ===== 成功時のエラーメッセージ =====
    response.put("title", "お問い合わせ内容送信完了");
    response.put("message",
        "お問い合わせを受け付けました。\nこの度はお問い合わせいただき、誠にありがとうございます。\n内容を確認のうえ、担当者よりご連絡いたします。");
    
    // ===== HTTP 200で成功レスポンスを返す =====
    return ResponseEntity.ok(response); // HTTP 200 OK
  }
  
}


