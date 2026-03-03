package com.agrifake.config;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

/* ========== 採用応募の送信メールで使用する設定値を管理 ========== */
@Component
@ConfigurationProperties(prefix = "mail.recruit")
public class MailProperties {
    private String to;
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
}