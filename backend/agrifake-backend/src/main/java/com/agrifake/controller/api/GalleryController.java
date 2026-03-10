package com.agrifake.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.agrifake.domain.gallery.Category;
import com.agrifake.service.gallery.GalleryService;
import com.agrifake.dto.gallery.GalleryApiResponse;

@Controller
public class GalleryController {
  
  private final GalleryService service;
  
  public GalleryController(GalleryService service) {
    this.service = service;
  }
  
  // ===== 一覧 =====
  @GetMapping("/gallery")
  public String gallery(Model model) {
    
    // ----- 農作物リスト -----
    model.addAttribute("freshProduce",
        service.getApiList(Category.FRESH_PRODUCE));
    
    // ----- 加工品リスト -----
    model.addAttribute("processedProducts",
        service.getApiList(Category.PROCESSED_PRODUCTS));
    
    return "user/pages/gallery";
  }
 
  // ===== モーダル =====
  @GetMapping("/gallery/{id}")
  @ResponseBody
  public GalleryApiResponse getGallery(@PathVariable Long id) {
    return service.getApiDetail(id);
  }
  
}