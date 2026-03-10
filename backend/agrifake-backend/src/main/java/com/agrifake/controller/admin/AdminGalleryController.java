package com.agrifake.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.agrifake.domain.gallery.Category;
import com.agrifake.dto.gallery.GalleryCreateRequest;
import com.agrifake.dto.gallery.GalleryAdminResponse;
import com.agrifake.service.gallery.GalleryService;
import com.agrifake.domain.gallery.GalleryItem;

@Controller
@RequestMapping("/admin/gallery")
public class AdminGalleryController {

  private final GalleryService service;
  
  public AdminGalleryController(GalleryService service) {
    this.service = service;
  }
  
    // ===== 一覧 =====
    @GetMapping
    public String gallery(Model model) {
      
      // ----- 農作物リスト -----
      model.addAttribute("freshProduce",
          service.getAdminList(Category.FRESH_PRODUCE));

      // ----- 加工品リスト -----
      model.addAttribute("processedProducts", 
          service.getAdminList(Category.PROCESSED_PRODUCTS));
      
      return "admin/pages/admin-gallery";
    }
    
    
    // ===== 一覧 =====
    @GetMapping("/{id}")
    @ResponseBody
    public GalleryAdminResponse getGallery(@PathVariable Long id) {
      return service.getAdminDetail(id);
    }
    
    
    
    // ===== フォーム表示 =====
    @GetMapping("/new")
    public String newForm(Model model) {
      
      model.addAttribute("gallery", new GalleryCreateRequest());
      model.addAttribute("isEdit", false); // 新規作成フラグ
      
      return "admin/pages/admin-gallery-form";
    }
    
    
    // ===== 新規投稿 =====
    @PostMapping("/create")
    public String create(@ModelAttribute GalleryCreateRequest dto) {
      
      service.create(dto);
      
      return "redirect:/admin/gallery";
    }
    
    
    // ===== 編集フォーム表示 =====
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {

      // ----- DBからデータを取得 -----
      GalleryItem item = service.getById(id); // ServiceにgetByIdメソッドを用意

      // ----- DTOにマッピング -----
      GalleryCreateRequest dto = new GalleryCreateRequest();
      dto.setId(item.getId());
      dto.setCategory(item.getCategory());
      dto.setTitle(item.getTitle());
      dto.setDescription(item.getDescription());
      dto.setThumbnailPreview(item.getThumbnailImageUrl()); // プレビュー用
      dto.setDetailPreview(item.getDetailImageUrl());       // プレビュー用

      model.addAttribute("gallery", dto);
      model.addAttribute("isEdit", true); // 編集フラグ
      model.addAttribute("id", id);

      return "admin/pages/admin-gallery-form";
    }
    
    // ===== 編集処理 =====
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute GalleryCreateRequest dto) {
      service.update(id, dto);
      return "redirect:/admin/gallery";
    }
    
    
    // ===== 公開非公開切り替え =====
    @PostMapping("/togglePublished/{id}")
    @ResponseBody
    public GalleryAdminResponse togglePublished(@PathVariable Long id) {
      service.togglePublished(id);
      return service.getAdminDetail(id);
    }
    
    
    // ===== 論理削除 =====
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
      service.delete(id);
      
      return "redirect:/admin/gallery";
    }
}