package com.agrifake.domain.gallery;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "gallery_item", schema = "gallery")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GalleryItem {
  //========== ギャラリーエンティティ ==========
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  // ===== カテゴリー =====
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Category category;
  
  // ===== タイトル =====
  @Column(nullable = false)
  private String title;
  
  // ===== 説明 =====
  @Column(nullable = false, columnDefinition = "TEXT")
  private String description;
  
  // ===== サムネ画像 =====
  @Column(name = "thumbnail_image_url", nullable = false)
  private String thumbnailImageUrl;
  
  // =====  詳細画像 =====
  @Column(name = "detail_image_url")
  private String detailImageUrl;
  
  // ===== 公開フラグ =====
  @Builder.Default
  @Column(name = "published", nullable = false)
  private boolean published = true; // デフォルトは公開
  
  // ===== 論理削除フラグ =====
  @Builder.Default
  @Column(name = "is_deleted", nullable = false)
  private boolean isDeleted = false;
  
  // ===== 作成日時 =====
  @Column(name = "created_at", updatable = false)
  private LocalDateTime createdAt;
  
  // ===== 更新日時 =====
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
  
  // ===== 新規登録 =====
  // 新規登録時に作成日時・更新日時を自動設定
  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }
  
  // ===== 更新 =====
  // 更新時に更新日時のみを更新
  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }
  
}