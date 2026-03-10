package com.agrifake.service.api;

import java.io.IOException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Value;

import com.agrifake.dto.contact.ContactFormDto;

@Service 
public class ContactFormService {
  
  // ===== メール送信機能 =====
  @Autowired
  private JavaMailSender mailSender;
  
  // ===== 管理者宛メールアドレス（application.propertiesから取得） =====
  @Value("${mail.contact.to}")
  private String contactMailTo;
  
  // ===== 送信元アドレス（SMTPユーザー/application.propertiesから取得） =====
  @Value("${spring.mail.username}")
  private String mailFrom;
  
  //===== メールヘッダインジェクション対策 =====
  private String sanitizeForEmail(String input) {
    if (input == null) return ""; // 任意項目対応
    return input.replaceAll("[\\r\\n]", ""); // 改行を削除
  }
  
  // ===== お問い合わせメール送信 =====
  public void sendContact(ContactFormDto dto) throws MessagingException, IOException {
    
    MimeMessage adminMessage = mailSender.createMimeMessage();
    
    MimeMessageHelper adminHelper = new MimeMessageHelper(adminMessage, false, "UTF-8");
    
    // ----- 管理者メールアドレス -----
    adminHelper.setFrom(mailFrom);
    adminHelper.setTo(contactMailTo);
    
    // ----- 件名 -----
    adminHelper.setSubject("【あぐりふぇいく】お問い合わせ");
    
    // ----- メール本文 -----
    String text = 
        "お名前：" + sanitizeForEmail(dto.getName()) + "\n" +
        "電話番号：" + sanitizeForEmail(dto.getPhone()) + "\n" +
        "メールアドレス：" + sanitizeForEmail(dto.getEmail()) + "\n" +
        "お問い合わせ内容：" + "\n" + dto.getContactMessage();
    adminHelper.setText(text);
    
    // ----- メール送信処理 -----
    mailSender.send(adminMessage);
    
    
    // ----- 応募者宛て自動返信 -----
    MimeMessage replyMessage = mailSender.createMimeMessage();
    MimeMessageHelper replyHelper = new MimeMessageHelper(replyMessage, true, "UTF-8");
    
    replyHelper.setFrom(mailFrom);
    replyHelper.setTo(sanitizeForEmail(dto.getEmail()));
    replyHelper.setSubject("【株式会社あぐりふぇいく】お問い合わせ受付");
    
    String replyText =
        dto.getName() + " 様\n\n" +
            "この度はお問い合わせいただき誠にありがとうございます。\n" +
            "お問い合わせ内容を正常に受け付けいたしました。\n\n" +
            "現在、担当者にて内容を確認しております。\n" +
            "お問い合わせ内容につきましては、5営業日以内にご連絡いたしますので、今しばらくお待ちください。\n\n" +
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