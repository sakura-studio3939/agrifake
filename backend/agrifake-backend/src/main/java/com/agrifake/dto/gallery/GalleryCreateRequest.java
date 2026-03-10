package com.agrifake.dto.gallery;

import com.agrifake.domain.gallery.Category;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

// ========== ギャラリー投稿フォーム項目 ==========
@Data
public class GalleryCreateRequest {
  
  private Long id;
  
  // ===== カテゴリー =====
  private Category category;
  
  // ===== タイトル =====
  private String title;
  
  // ===== 詳細 =====
  private String description;
  
  // ===== サムネ画像 =====
  private MultipartFile thumbnailImage;
  
  // ===== 詳細画像 =====
  private MultipartFile detailImage;
  
  // ===== 編集用: 既存画像プレビュー =====
  private String thumbnailPreview;
  private String detailPreview;
  
}


