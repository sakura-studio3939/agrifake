package com.agrifake.service.gallery;

//import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agrifake.domain.gallery.Category;
import com.agrifake.domain.gallery.GalleryItem;
import com.agrifake.dto.gallery.GalleryAdminResponse;
import com.agrifake.dto.gallery.GalleryApiResponse;
import com.agrifake.dto.gallery.GalleryCreateRequest;
import com.agrifake.mapper.gallery.GalleryMapper;
import com.agrifake.repository.GalleryRepository;

@Service
public class GalleryService {
  
  private final GalleryRepository repository;
  private final FileStorageService fileStorageService;
  
  public GalleryService(GalleryRepository repository,FileStorageService fileStorageService) {
    this.repository = repository;
    this.fileStorageService = fileStorageService;
  }
  
  // ========== 投稿処理 ==========
  public void create(GalleryCreateRequest dto) {
    
    // ===== 画像保存 =====
    String thumbPath = fileStorageService.saveThumbnail(dto.getThumbnailImage());
    String detailPath = fileStorageService.saveDetail(dto.getDetailImage());
    
    // ----- サムネ画像がない場合は詳細画像を使う -----
    if (detailPath == null || detailPath.isBlank()) {
      detailPath = thumbPath;
    }
    
    // ===== MapperでEntity作成 =====
    GalleryItem item = GalleryMapper.toEntity(dto,  thumbPath, detailPath);

    repository.save(item);
  }
  
  
  // ========== 更新処理 ============
  public void update(Long id, GalleryCreateRequest dto) {
    
    // ===== 既存のEntity取得 =====
    GalleryItem item = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("ギャラリーが存在しません"));
    
    // ===== 画像保存 =====
    String thumbPath = null;
    String detailPath = null;
    
    if (dto.getThumbnailImage() != null && !dto.getThumbnailImage().isEmpty()) {
      thumbPath = fileStorageService.saveThumbnail(dto.getThumbnailImage());
    }
    
    if (dto.getDetailImage() != null && !dto.getDetailImage().isEmpty()) {
      detailPath = fileStorageService.saveDetail(dto.getDetailImage());
    }
    
    // ===== MapperでEntityを更新 =====
    GalleryMapper.updateEntity(item,  dto, thumbPath, detailPath);
    
    // ===== 保存 =====
    repository.save(item);
  }
  
  // -----  -----
  public GalleryItem getById(Long id) {
    return repository.findById(id)
        .orElseThrow(() -> new RuntimeException("ギャラリーが存在しません"));
  }
  
  
  // ===== 論理削除 =====
  @Transactional
  public void delete(Long id) {
    GalleryItem item = repository
       .findByIdAndIsDeletedFalse(id)
       .orElseThrow(() -> new RuntimeException("ギャラリーが存在しません"));
       
    // JavaBeanのbooleanルールで setter は setDeleted()
    item.setDeleted(true);
  }
  
  
  // ========== Admin一覧 ==========
  public List<GalleryAdminResponse> getAdminList(Category category) {
    
    return repository.findByCategoryAndIsDeletedFalseOrderByIdAsc(category)
        .stream()
        .map(item -> new GalleryAdminResponse(
            item.getId(),
            item.getTitle(),
            item.getDescription(),
            item.getThumbnailImageUrl(),
            item.getDetailImageUrl(),
            item.isPublished(),
            item.isDeleted(),
            item.getCreatedAt(),
            item.getUpdatedAt()
        ))
        .collect(Collectors.toList());
  }
  
  // ========== Admin詳細（モーダル） ==========
  public GalleryAdminResponse getAdminDetail(Long id) {
    GalleryItem item = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("ギャラリーが存在しません"));
    
    return GalleryMapper.toAdminResponse(item);
  }
  
  public void togglePublished(Long id) {
    GalleryItem item = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("ギャラリーが存在しません"));
    
    item.setPublished(!item.isPublished());
    repository.save(item);
  }
  
  
  // ========== Public一覧 ==========
  public List<GalleryApiResponse> getApiList(Category category) {
    
    return repository.findByCategoryAndIsDeletedFalseAndPublishedTrueOrderByIdAsc(category)
        .stream()
        .map(GalleryMapper::toApiResponse)
        .collect(Collectors.toList());
  }
  
  // ========== Public詳細 ==========
  public GalleryApiResponse getApiDetail(Long id) {

    GalleryItem item = repository
        .findByIdAndIsDeletedFalseAndPublishedTrue(id)
        .orElseThrow(() -> new RuntimeException("ギャラリーが存在しません"));

    return GalleryMapper.toApiResponse(item);
  }
  
}
