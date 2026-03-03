package com.agrifake.controller;

// Springの依存関係・メール送信・Web用のアノテーション
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// メール送信テスト
@RestController
public class MailTestController {

    // SpringのJavaMailSenderを注入
    @Autowired
    private JavaMailSender mailSender;

    // GETリクエストで/mail-testにアクセスした時にこのメソッドを呼び出す
    @GetMapping("/mail-test")
    public String sendTestMail() {

    // 送信するメールの内容作成
    SimpleMailMessage message = new SimpleMailMessage();
 
    // 送信先メールアドレス
    message.setTo("5aku5aku.sakura.yae+agrifake.admin@gmail.com");

    // メールの件名
    message.setSubject("Spring Boot Starter Mail テストメール");

    // メール本文
    message.setText("メール送信テスト");

    // メール送信
    mailSender.send(message);

    // ブラウザに表示メッセージ
    return "メールを送信しました"; 
  }
}

