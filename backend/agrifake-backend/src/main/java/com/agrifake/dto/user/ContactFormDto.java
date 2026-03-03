package com.agrifake.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ContactFormDto {
  
  // ===== 必須 =====
  @NotBlank(message = "お名前は必須です")
  private String name;
  
  @NotBlank(message = "メールアドレスは必須です")
  @Email(message = "正しいメールアドレス形式で入力してください")
  private String email;
  
  @NotBlank(message = "お問い合わせ内容をご入力ください")
  private String contactMessage;
  
  // ===== 任意 =====
  private String phone;
}