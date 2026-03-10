package com.agrifake.mapper.gallery;

import java.time.LocalDateTime;

import com.agrifake.domain.gallery.GalleryItem;
import com.agrifake.dto.gallery.GalleryAdminResponse;
import com.agrifake.dto.gallery.GalleryApiResponse;
import com.agrifake.dto.gallery.GalleryCreateRequest;


public class GalleryMapper {
  
  // ===== 新規作成用 =====
  // ギャラリー画面のフォーム入力をGalleryEntityへ変換
  public static GalleryItem toEntity(GalleryCreateRequest dto, String thumbPath, String detailPath) {
    
    GalleryItem item = new GalleryItem();
    
    // ----- エンティティ生成 -----
    item.setCategory(dto.getCategory());
    item.setTitle(dto.getTitle());
    item.setDescription(dto.getDescription());
    item.setThumbnailImageUrl(thumbPath);
    item.setDetailImageUrl(detailPath);
    item.setCreatedAt(LocalDateTime.now());
    item.setUpdatedAt(LocalDateTime.now());
        
    return item;

  }
  
  // ===== 編集用 =====
  // 既存EntityをフォームDTOで更新する
  public static void updateEntity(GalleryItem item, GalleryCreateRequest dto, String thumbPath, String detailPath) {
    
    // ----- 更新内容 -----
    item.setCategory(dto.getCategory());
    item.setTitle(dto.getTitle());
    item.setDescription(dto.getDescription());
    
    // --- 画像は新しいものがあれば差し替える ---
    if (thumbPath != null) {
      item.setThumbnailImageUrl(thumbPath);
    }
    if (detailPath != null) {
      item.setDetailImageUrl(detailPath);
    }
    
    // 更新日時
    item.setUpdatedAt(LocalDateTime.now());
  }
  
    
  
  // ===== 表示用（Admin） =====
  // Entity → AdminDTO
  public static GalleryAdminResponse toAdminResponse(GalleryItem item){
  
   return new GalleryAdminResponse(
       item.getId(),
       item.getTitle(),
       item.getDescription(),
       item.getThumbnailImageUrl(),
       item.getDetailImageUrl(),
       item.isPublished(),
       item.isDeleted(),
       item.getCreatedAt(),
       item.getUpdatedAt()
   );
  
  }
  
  // ===== 表示用（Public） =====
  // Entity → PublicDTO
  public static GalleryApiResponse toApiResponse(GalleryItem item){
  
   return new GalleryApiResponse(
       item.getId(),
       item.getTitle(),
       item.getDescription(),
       item.getThumbnailImageUrl(),
       item.getDetailImageUrl()
   );

}
  
  
}

