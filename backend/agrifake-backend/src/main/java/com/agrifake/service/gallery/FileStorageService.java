package com.agrifake.service.gallery;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

// ========== アップロード画像をDBに保存し、URLを返す ==========
@Service
public class FileStorageService {
  
  // ===== サムネ画像保存先 =====
  private final String thumbnailDir = "uploads/gallery/thumbnail/";

  // ===== 詳細画像保存先 =====
  private final String detailDir = "uploads/gallery/detail/";
  
  // ===== サムネ画像保存 =====
  public String saveThumbnail(MultipartFile file) {
    return saveImage(file, thumbnailDir);
  }
  
  // ===== 詳細画像保存 =====
  public String saveDetail(MultipartFile file) {
    return saveImage(file, detailDir);
  }
  
  // ===== 共通画像保存処理 =====
  private String saveImage(MultipartFile file, String uploadDir) {
    
    try {
      
      // ----- ファイル添付がない場合 -----
      // 詳細画像は任意のため、未添付の場合はnullかemptyになり保存しない
      if (file == null || file.isEmpty()) {
        return null;
      }
      
      // ----- ファイルサイズチェック -----
      long maxSize = 5 * 1024 * 1024; // 5MB
      
      if (file.getSize() > maxSize) {
        throw new RuntimeException("ファイルサイズは5MB以下にしてください");
      }
      
      // ----- MIMEタイプ取得 -----
      String contentType = file.getContentType();
      
      if (contentType == null) {
        throw new RuntimeException("MIMEタイプ取得エラー");
      }
      
      // --- MIMEタイプチェック ---
      List<String> allowedTypes = List.of(
          "image/jpeg",
          "image/png",
          "image/webp");
      
      if (!allowedTypes.contains(contentType)) {
        throw new RuntimeException("画像ファイルのみアップロード可能です"); 
      }
      
      // ----- ファイル名取得 -----
      String originalFilename = file.getOriginalFilename();
      
      // --- セキュリティやブラウザの仕様などでファイル名を取得できない場合の安全策 ---
      if (originalFilename == null) {
        return null;
      }
      
      // ----- 拡張子取得 -----
      int dotIndex = originalFilename.lastIndexOf(".");
      
      // --- 拡張子が存在しない場合 ---
      if (dotIndex == -1) {
        throw new RuntimeException("拡張子が存在しません");
      }
      
      String extension = originalFilename.substring(dotIndex).toLowerCase().trim();
      
      // ----- 拡張子チェック -----
      List<String> allowedExtensions = List.of(".jpg", ".jpeg", ".png", ".webp");
        
      if (!allowedExtensions.contains(extension)) {
        throw new RuntimeException("許可されていないファイル形式です");
      }

      
      // ----- UUIDでファイル名作成 -----
      // 同じファイル名の衝突回避
      String newFileName = UUID.randomUUID() + extension;
      
      // ----- 保存先パス作成 -----
      Path path = Paths.get(uploadDir + newFileName);
      
      // ----- ディレクトリ作成（ない場合） -----
      Files.createDirectories(path.getParent());
      
      // ----- 保存処理 -----
      file.transferTo(path);
      
      // ----- DBに保存するURL -----
      // Windows対策構成
      return "/" + uploadDir.replace("\\", "/") + newFileName;
    
    } catch (IOException e) {
      throw new RuntimeException("画像保存エラー");
    }
  }
}