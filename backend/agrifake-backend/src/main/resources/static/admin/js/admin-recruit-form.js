// ========== import ==========
// ===== complete.html/confirm.htmlの差し込み =====
import { loadFormModalComponents } from "../../../common/assets/js/form-modal-loader.js";
// ===== complete.html/confirm.htmlの読み込み =====
import { formModal } from "../../../common/assets/js/form-modal.js";


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
  const formValue = document.getElementById("recruit-form");

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
        field.setCustomValidity(`${formLabel}を入力してください`);
      }
    });

    // ----- 入力開始でエラーメッセージリセット -----
    field.addEventListener("input", () => {
      field.setCustomValidity("");
    });
  });

  // ===== モーダルテキスト =====
  const modal = formModal({
    confirmTitle: "応募要項内容確認",
    confirmMessage: "以下の内容で投稿してよろしいですか？",
    completeTitle: "更新完了",
    completeMessage: `
      採用情報が更新されました
    `,

    // ----- 確認モーダル送信ボタンクリック時の送信処理 -----
    onConfirm: async (formData) => {
      // --- APIへPOST送信 ---
      const response = await fetch("/api/admin-recruit", {
        method: "POST",
        body: formData
      });

      // --- サーバーエラー ---
      if (!response.ok) {
        throw new Error("送信失敗");
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
    const positionValue = formData.get("position")?.trim();
    const employmentTypeValue = formData.get("employment_type")?.trim();
    const jobDescriptionValue = formData.get("job_description")?.trim();
    const salaryValue = formData.get("salary")?.trim();
    const locationValue = formData.get("location")?.trim();
    const requirementsValue = formData.get("requirements")?.trim();
    const workSchedule = formData.get("work_schedule")?.trim();
    const benefitValue = formData.get("benefit")?.trim();
    const notesValue = formData.get("notes")?.trim();

    // ----- 確認モーダルに表示する内容 -----
    const formContentsHTML = `
      <p><strong>募集職種：</strong>${positionValue}</p>
      <p><strong>雇用形態：</strong>${employmentTypeValue}</p>
      <p><strong>仕事内容：</strong>${jobDescriptionValue}</p>
      <p><strong>給与：</strong>${salaryValue}</p>
      <p><strong>勤務地：</strong>${locationValue}</p>
      <p><strong>応募条件：</strong>${requirementsValue}</p>
      <p><strong>勤務時間：</strong>${workSchedule}</p>
      <p><strong>待遇・福利厚生：</strong>${benefitValue}</p>
      <p><strong>備考：</strong>${notesValue}</p>
    `;

    // ----- 確認モーダルを開く -----
    modal.openConfirm(formContentsHTML, formData);
  });
}

