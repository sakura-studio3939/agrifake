package com.agrifake.config;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

/* ========== お問い合わせの送信メールで使用する設定値を管理 ========== */
@Component
@ConfigurationProperties(prefix = "mail.contact")
public class ContactMailProperties {
    private String to;
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
}

