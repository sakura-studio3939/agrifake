package com.agrifake.dto.gallery;

import lombok.AllArgsConstructor;
import lombok.Data;

//========== 管理者側ギャラリー画面用 ==========
@Data
@AllArgsConstructor
public class GalleryApiResponse {

  private Long id;
  
  // ===== タイトル =====
  private String title;

  // ===== 詳細 =====
  private String description;

  // ===== サムネ画像 =====
  private String thumbnailImage;
  
  // ===== 詳細画像 =====
  private String detailImage;

}