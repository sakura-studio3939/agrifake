package com.agrifake.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// ========== uploadフォルダ内の画像をブラウザからもアクセス可能にする処理 ==========
// SpringBootはsrc/main/resources/static配下のファイルしか見ない
// なのでuploadsフォルダを参照するよう設定
@Configuration
public class ResourceConfig implements WebMvcConfigurer {
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    
    // ===== URLと実際のフォルダを紐づける =====
    registry.addResourceHandler("/uploads/**")
            .addResourceLocations("file:uploads/");
  }
}


