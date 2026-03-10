package com.agrifake.dto.recruit;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;


public class RecruitApplicationDto {
  
  // ===== 必須 =====
  @NotBlank(message = "お名前は必須です")
  private String name;
  
  @NotBlank(message = "メールアドレスは必須です")
  @Email(message = "正しいメールアドレス形式で入力してください")
  private String email;
  
  @NotNull(message = "履歴書は必須です")
  private MultipartFile resume;

  
  // ===== 任意 =====
  private String phone;
  private String note;
  private MultipartFile careerHistory;

  
  // ===== getter/setter =====
  // Lombok不使用
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getPhone() {
    return phone;
  }
  
  public void setPhone(String phone) {
    this.phone = phone;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getNote() {
    return note;
  }
  
  public void setNote(String note) {
    this.note = note;
  }
  
  public MultipartFile getResume () {
    return resume;
  }
  
  public void setResume(MultipartFile resume) {
    this.resume = resume;
  }
  
  public MultipartFile getCareerHistory() {
    return careerHistory;
  }
  
  public void setCareerHistory(MultipartFile careerHistory) {
    this.careerHistory = careerHistory;
  }

}