package com.agrifake.service.api;

import java.io.IOException;
import java.util.List;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Value;

import com.agrifake.dto.recruit.RecruitApplicationDto;

@Service
public class RecruitApplicationService {
  
  // ===== メール送信機能 =====
  @Autowired
  private JavaMailSender mailSender;
  
  // ===== 管理者宛メールアドレス（application.propertiesから取得） =====
  @Value("${mail.recruit.to}")
  private String recruitMailTo;
  
  // ===== 送信元アドレス（SMTPユーザー/application.propertiesから取得） =====
  @Value("${spring.mail.username}")
  private String mailFrom;

  
  // ===== 添付ファイルチェック =====
  private void validateFile(MultipartFile file, List<String> allowedExtensions, List<String> allowedMimeTypes) {
    if (file == null || file.isEmpty()) return;
    
    // ----- ファイル名チェック -----
    String filename = file.getOriginalFilename();
    if (filename == null || !filename.contains(".")) {
      throw new IllegalArgumentException("不正なファイル名です");
    }
    
    // ----- 拡張子チェック -----
    String ext = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    if (!allowedExtensions.contains(ext)) {
      throw new IllegalArgumentException("許可されていないファイル形式です");
    }
    
    // ----- MIMEタイプチェック -----
    String mimeType = file.getContentType();
    if (mimeType == null || !allowedMimeTypes.contains(mimeType)) {
      throw new IllegalArgumentException("許可されていないMIMEタイプのファイルです");
    }
  }

  
  // ===== 添付ファイルの存在&サイズチェック =====
  // ----- 履歴書（必須） -----
  private void validateResume(MultipartFile resume) {
    if (resume == null || resume.isEmpty()) {
      throw new IllegalArgumentException("履歴書は必須です");
    }
    if (resume.getSize() > 5 * 1024 * 1024) {
      throw new IllegalArgumentException("履歴書は5MB以内でアップロードしてください");
    }
    
    validateFile(resume,
        List.of("pdf", "doc", "docx"),
        List.of("application/pdf",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/octet-stream"));
  }
  
  // ----- 職務経歴書（任意） -----
  private void validateCareerHistory(MultipartFile careerHistory) {
    if (careerHistory == null || careerHistory.isEmpty()) return; // 任意
    if (careerHistory.getSize() > 5 * 1024 * 1024) {
      throw new IllegalArgumentException("職務経歴書は5MB以内でアップロードしてください");
    }
    validateFile(careerHistory,
        List.of("pdf", "doc", "docx"),
        List.of("application/pdf",
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/octet-stream"));
  }
  
  // ===== メールヘッダインジェクション対策 =====
  private String sanitizeForEmail(String input) {
    if (input == null) return ""; // 任意項目対応
    return input.replaceAll("[\\r\\n]", ""); // 改行を削除
  }
  

  // ===== 採用応募メール送信 =====
  public void sendApplication(RecruitApplicationDto dto) throws MessagingException, IOException {
    
    // ----- 添付ファイルバリデーション -----
    validateResume(dto.getResume());
    validateCareerHistory(dto.getCareerHistory());
    
    // ----- 管理者宛てメール作成 -----
    MimeMessage adminMessage = mailSender.createMimeMessage();
    MimeMessageHelper adminHelper = new MimeMessageHelper(adminMessage, true);
    
    // ----- 管理者メールアドレス -----
    adminHelper.setFrom(mailFrom);
    adminHelper.setTo(recruitMailTo);
    
    // ----- 件名 -----
    adminHelper.setSubject("【あぐりふぇいく】採用応募");
    
    // ----- メール本文 -----
    String text =
      "お名前：" + sanitizeForEmail(dto.getName()) + "\n" +
      "電話番号：" + sanitizeForEmail(dto.getPhone()) + "\n" +
      "メールアドレス：" + sanitizeForEmail(dto.getEmail()) + "\n" +
      "備考：" + "\n" + dto.getNote();
    adminHelper.setText(text);
    
    // ----- 添付ファイル（存在&サイズチェック） -----
    // --- 履歴書 ---
    if (dto.getResume() != null && !dto.getResume().isEmpty()) {
      adminHelper.addAttachment(dto.getResume().getOriginalFilename(), dto.getResume());
    }
    // --- 職務経歴書 ---
    if (dto.getCareerHistory() != null && !dto.getCareerHistory().isEmpty()) {
      adminHelper.addAttachment(dto.getCareerHistory().getOriginalFilename(), dto.getCareerHistory());
    }
      
    // ----- メール送信処理 -----
    mailSender.send(adminMessage);
    
    
    // ----- 応募者宛て自動返信 -----
    MimeMessage replyMessage = mailSender.createMimeMessage();
    MimeMessageHelper replyHelper = new MimeMessageHelper(replyMessage, true, "UTF-8");
    
    replyHelper.setFrom(mailFrom);
    replyHelper.setTo(sanitizeForEmail(dto.getEmail()));
    replyHelper.setSubject("【株式会社あぐりふぇいく】採用応募受付");
    
    String replyText = 
        dto.getName() + " 様\n\n" +
            "この度はご応募いただき誠にありがとうございます。\n" +
            "エントリー内容を正常に受け付けいたしました。\n\n" +
            "現在、担当者にて内容を確認しております。\n" +
            "選考結果につきましては、5営業日以内にご連絡いたしますので、今しばらくお待ちください。\n\n" +
            "万が一、5営業日を過ぎても連絡がない場合は、\n" +
            "お手数ですが下記までお問い合わせください。\n\n" +
            "※本メールは自動送信です。本メールへご返信いただいてもご対応できかねます。\n\n" +
            "引き続きどうぞよろしくお願いいたします。\n\n\n" +
            "----------------------------------------\n" +
            "株式会社あぐりふぇいく" +
            "採用担当\n" +
            "agrifake@aguri.fake.com\n" +
            "----------------------------------------\n";
    replyHelper.setText(replyText);
    
    // ----- メール送信処理 -----
    mailSender.send(replyMessage);

  }
  
}