package com.agrifake.dto;

// ========== フォーム送信内容確認モーダルテキストテンプレートDTO ==========
//フォーム送信時の確認モーダルに表示するタイトル・メッセージ・ボタンラベルを保持するクラス
public class ConfirmModalDto {
    private String title;
    private String message;
    private String cancelLabel;
    private String submitLabel;

    // ===== コンストラクタ =====
    // 各フィールド（タイトル・メッセージ・キャンセルボタン・送信ボタンラベル）を初期化
    public ConfirmModalDto(String title, String message, String cancelLabel, String submitLabel) {
        this.title = title;
        this.message = message;
        this.cancelLabel = cancelLabel;
        this.submitLabel = submitLabel;
    }

    // ===== getter =====
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getCancelLabel() { return cancelLabel; }
    public String getSubmitLabel() { return submitLabel; }
}