package com.agrifake.dto.gallery;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

// ========== 管理者側ギャラリー画面用 ==========
@Data
@AllArgsConstructor
public class GalleryAdminResponse {
  
  // ===== 主キー =====
  private Long id;
  
  // ===== タイトル =====
  private String title;
  
  // ===== 詳細 =====
  private String description;
  
  // ===== サムネ画像 =====
  private String thumbnailImage;
  
  // ===== 詳細画像 =====
  private String detailImage;
  
  // ===== 公開フラグ =====
  private boolean published;
  
  // ===== 論理削除フラグ =====
  private boolean is_deleted; 
  
  // ===== 作成日時 =====
  private LocalDateTime createdAt;
  
  // ===== 更新日時 =====
  private LocalDateTime updatedAt;

}