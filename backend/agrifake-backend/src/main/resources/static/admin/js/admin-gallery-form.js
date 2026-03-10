// ========== import ==========
// ===== complete.html/confirm.htmlの差し込み =====
//import { loadFormModalComponents } from "../../../common/assets/js/form-modal-loader.js";
// ===== complete.html/confirm.htmlの読み込み =====
//import { formModal } from "../../../common/assets/js/form-modal.js";


// ========== HTML差し込み ==========
document.addEventListener("DOMContentLoaded", async () => {
  // ===== モーダルDOM生成のためcomplete.html/confirm.htmlの差し込み =====
  await loadFormModalComponents();
  // ===== 差し込み後に初期化 =====
  initForm();
})

console.log(loadFormModalComponents);
console.log(formModal);


// ========== フォーム初期化処理 ==========
function initForm() {
  // ===== フォーム要素の取得 =====
  const formValue = document.getElementById("gallery-form");

  // フォーム取得確認（開発用）
  console.log(formValue);


  // ===== バリデーションテキスト変更処理 =====
  formValue.querySelectorAll("input, textarea").forEach((field) => {

    // -----エラーメッセージ用の表示名（data-labelがない場合は「この項目」） -----
    const formLabel = field.dataset.label ?? "この項目";

    // ----- バリデーションエラー（バリデーションチェックはHTML側で実行） -----
    field.addEventListener("invalid", () => {

      // --- 必須未入力 ---
      if (field.validity.valueMissing) {
        if (field.type === "file") {
          field.setCustomValidity(`${formLabel}を添付してください`);
        } else if (field.type === "radio") {
          field.setCustomValidity(`${formLabel}を選択してください`);
        } else {
          field.setCustomValidity(`${formLabel}を入力してください`);
        }
      }
    });

    // ----- 入力開始でエラーメッセージリセット -----
    if (field.type === "radio") {
      // ラジオの場合は同じグループの全てをリセット
      field.addEventListener("change", () => {
        const radios = formValue.querySelectorAll(`input[name="${field.name}"]`);
        radios.forEach(radio => radio.setCustomValidity(""));
      });
    } else {
      field.addEventListener("input", () => {
        field.setCustomValidity("");
      });
    }
  });


  // ===== モーダルテキスト =====
  const modal = formModal({
    confirmTitle: "ギャラリー内容の確認あああ",
    confirmMessage: "以下の内容で更新してよろしいですか？",
    completeTitle: "更新完了",
    completeMessage: `
      ギャラリーが更新されました
    `,

    // ----- 確認モーダル送信ボタンクリック時の送信処理 -----
    onConfirm: async (formData) => {
      // --- APIへPOST送信 ---
      const response = await fetch("/api/admin-gallery", {
        method: "POST",
        body: formData
      });

      // --- サーバーエラー ---
      if (!response.ok) {
        throw new Error("送信失敗")
      }

      // --- 送信成功後はフォームリセット ---
      formValue.reset();
    }
  });


  // ===== フォーム送信イベント =====
  formValue.addEventListener("submit", (e) => {
    // ----- デフォルト送信（ページリロード）を防いでJS制御に切り替え -----
    e.preventDefault();

    // ----- HTMLバリデーション実行 -----
    if (!formValue.checkValidity()) {
      formValue.reportValidity();
      return;
    }

    // ----- フォームの値を取得 -----
    const formData = new FormData(formValue);

    // ----- フォームの値を変数に格納 -----
    const formCategory = formData.get("category");
    const categoryMap = {
      FRESHPRODUCE: "農作物",
      PROCESSEDPRODUCTS: "加工品"
    };
    const categoryValue = categoryMap[formCategory] || "";

    const titleValue = formData.get("title")?.trim();
    const descriptionValue = formData.get("description")?.trim();

    const thumbnailImage = formData.get("thumbnailImage");
    const detailImage = formData.get("detailImage");

    const thumbnailImageURL = URL.createObjectURL(thumbnailImage);
    const detailImageURL = detailImage && detailImage.size > 0 ? URL.createObjectURL(detailImage) : null;

    // --- 画像名取得 ---
    const thumbnailImageName = thumbnailImage.name;
    const detailImageName = detailImage && detailImage.size > 0 ? detailImage.name : "未添付";

    // ----- 確認モーダルに表示する内容 -----
    const formHTML= `
      <p><strong>カテゴリー：</strong>${categoryValue}</p>
      <p><strong>名前：</strong>${titleValue}</p>
      <p><strong>詳細：</strong>${descriptionValue}</p>
      <p><strong>一覧用画像：</strong>${thumbnailImageName}</p>
      <img src="${thumbnailImageURL}" style="max-width: 200px; display:block; margin-bottom: 10px;">
      <p><strong>詳細用画像：</strong>${detailImageName && detailImage.size > 0 ? detailImageName : "未添付"}</p>
      ${detailImageURL ? `<img src="${detailImageURL}" style"max-width:200px;">`: ""}
    `;

    // 確認モーダル展開
    modal.openConfirm(formHTML, formData);
  });

}

