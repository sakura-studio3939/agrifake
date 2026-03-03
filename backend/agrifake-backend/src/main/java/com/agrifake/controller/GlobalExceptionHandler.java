package com.agrifake.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// ========== MIMEタイプ例外処理 ==========
@ControllerAdvice
public class GlobalExceptionHandler {
  
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
    // ----- 400 Bad Request で返す -----
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                         .body(ex.getMessage());
  }
  
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleServer(Exception ex) {
    // ----- 500 Internet Server Error で返す -----
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                         .body("サーバーエラーが発生しました");
  }
}
