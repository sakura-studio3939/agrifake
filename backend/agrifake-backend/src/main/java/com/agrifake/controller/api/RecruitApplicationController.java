package com.agrifake.controller.api;


import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.agrifake.dto.recruit.RecruitApplicationDto;
import com.agrifake.service.api.RecruitApplicationService;


@Controller
@RequestMapping("/recruit")
public class RecruitApplicationController {
  
  // ===== サービスクラス定義（メール送信やファイル処理など） =====
  private final RecruitApplicationService service;
    
  //===== コンストラクタインジェクション =====
  public RecruitApplicationController(RecruitApplicationService service) {
    this.service = service;
  }

  
  // ========== 応募フォーム送信処理（Ajax） ==========
  @PostMapping("/api")
  @ResponseBody
  public ResponseEntity<Map<String, String>> sendApplicationAjax(
      @Valid @ModelAttribute RecruitApplicationDto dto, // フォーム入力内容をDTOにバインドしてバリデーション
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
      service.sendApplication(dto);
      
    } catch (IllegalArgumentException e) {
      // ===== ファイル関連の例外（添付形式やサイズなど） =====
      response.put("title", "ファイルエラー");
      response.put("message", e.getMessage());
      return ResponseEntity.badRequest().body(response);
      
    } catch (Exception e) {
      // ===== 予期せぬサーバーエラー =====
      response.put("title", "サーバーエラー");
      response.put("message", "サーバーエラーが発生しました"); // 内部エラーはユーザー向けメッセージ
      return ResponseEntity.internalServerError().body(response); // HTTP 500で返却
    }

      
    // ===== 成功時のエラーメッセージ =====
    response.put("title", "応募完了");
    response.put("message",
        "ご応募ありがとうございます。\nご内容を確認のうえ、担当者よりご連絡いたします。");
    return ResponseEntity.ok(response);
  }
}


