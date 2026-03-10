package com.agrifake.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.agrifake.domain.gallery.Category;
import com.agrifake.domain.gallery.GalleryItem;

public interface GalleryRepository extends JpaRepository<GalleryItem, Long> {

  // ===== Admin =====
  // 論理削除されていないもの
  // id順
  // カテゴリー別取得
  List<GalleryItem> findByCategoryAndIsDeletedFalseOrderByIdAsc(Category category);
  
  // ----- モーダル用単体取得 -----
  Optional<GalleryItem> findByIdAndIsDeletedFalse(Long id);
  
  // ===== Public =====
  // カテゴリー別取得
  // id順に取得
  // 論理削除と非公開にされていないもの
  List<GalleryItem> findByCategoryAndIsDeletedFalseAndPublishedTrueOrderByIdAsc(Category category);
  
  // ----- モーダル用単体取得 -----
  Optional<GalleryItem> findByIdAndIsDeletedFalseAndPublishedTrue(Long id);
}

