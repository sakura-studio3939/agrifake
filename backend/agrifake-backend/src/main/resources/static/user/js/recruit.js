// ========== import ==========
// ===== confirm.htmlの読み込み =====
import { formModal } from "/common/js/form-modal.js";

// ===== /nを<br>に変換 =====
function nl2br(text) {
  return text.replace(/\n/g, "<br>");
}

// ========== DOMロード ==========
document.addEventListener("DOMContentLoaded", () => {

  // フォーム初期化
  initForm();
})


// ========== フォーム初期化 ==========
function initForm() {
  const entryFormElements = getFormElements();
  setupValidation(entryFormElements.form);
  const confirmModal = setupModal(entryFormElements.form);
  setupSubmit(entryFormElements, confirmModal);
}

// ========== DOM取得 ==========
function getFormElements() {
  const form = document.getElementById("entry-form");

  return {
    form,
    resumeInput: form.querySelector('input[name="resume"]'),
    careerInput: form.querySelector('input[name="careerHistory"]')
  };
}

// ========== バリデーション ==========
function setupValidation(formValue) {
  // ===== バリデーションテキスト変更処理 =====
  formValue.querySelectorAll("input, textarea").forEach((field) => {

    // ----- エラーメッセージ用の表示名（data-labelがない場合は「この項目」） -----
    const formLabel = field.dataset.label ?? "この項目";

    // ----- バリデーションエラー（HTML標準チェックにカスタムメッセージ追加） -----
    field.addEventListener("invalid", () => {

      // --- 必須未入力 ---
      if (field.validity.valueMissing) {

        // - テキストorファイル判定 -
        if (field.type === "file") {
          field.setCustomValidity(`${formLabel}を添付してください`);
        } else {
          field.setCustomValidity(`${formLabel}を入力してください`)
        }

      // --- メールアドレスの形式エラー ---
      } else if (field.validity.typeMismatch) {
        field.setCustomValidity(`${formLabel}を正しい形式で入力してください`);
      }
    });

    // ----- 入力開始でエラーメッセージリセット -----
    field.addEventListener("input", () => {
      field.setCustomValidity("");
    });
  });
}


// ========== モーダル初期化 ==========
function setupModal(form) {

  return formModal({
    // ----- モーダルテキスト -----
    confirmTitle: "応募内容の確認",
    confirmMessage: "以下の内容で送信してよろしいですか？",

    // ----- Ajax送信処理へ移行 -----
    onConfirm: () => sendAjax(form)
  });
}


// ========== Ajax送信処理 ==========
// 引数：form要素
function sendAjax(form) {

  console.log("sendAjax開始");
  // ===== フォームデータ取得 =====
  const formData = new FormData(form);

  // ===== Ajaxでサーバー送信 =====
  fetch(form.action + "/api", {
    method: "POST",
    body: formData
  })
  .then(async (response) => {

    // ----- サーバーからのJSONレスポンスを取得 -----
    const data = await response.json();
    console.log("status:", response.status);
    console.log("response body:", data);

    // ----- 確認モーダル非表示 -----
    document.getElementById("confirm_modal").classList.add("hidden");

    // ----- 完了モーダルテキスト -----
    document.querySelector("#complete_modal .complete-title").textContent = data.title;
    // --- メッセージの改行反映 ---
    const completeMessage = document.querySelector("#complete_modal .complete-message");
    completeMessage.textContent = data.message;
    completeMessage.style.whiteSpace = "pre-line";

    // ----- 完了モーダル表示 -----
    document.getElementById("complete_modal").classList.remove("hidden");

    // ----- 成功にフォームリセット -----
    if (response.ok) {
      form.reset();
    }

  })
  // 通信エラー発生時
  .catch((error) => {
    console.error("エラー詳細:", error);
    alert("通信エラーが発生しました");
  });
}


// ========== 送信処理 ==========
function setupSubmit(formElements, confirmModalValue) {
  const { form, resumeInput, careerInput } = formElements;

  // ===== フォーム送信時の処理を設定 =====
  form.addEventListener("submit", (e) => {

    // ----- ページリロードやサーバー送信を停止（確認モーダル表示用） -----
    e.preventDefault();

    // ----- HTML標準バリデーション実行 -----
    if (!form.checkValidity()) {
      // --- NGなら標準アラートを表示して処理中断 ---
      form.reportValidity();
      return;
    }

    // ----- フォームの値を取得 -----
    const formData = new FormData(form);

    if (!validateFiles(formData, resumeInput, careerInput)) {
      return;
    }

    // ----- 確認モーダルに表示する内容を作成 -----
    const confirmContent = buildConfirmContent(formData);

    // ----- 確認モーダルを開く -----
    confirmModalValue.openConfirm(confirmContent);
  })
}


// ========== ファイル検証 ==========
function validateFiles(formData, resumeInput, careerInput) {

  // ===== ファイル取得 =====
  const resumeFile = formData.get("resume");
  const careerFile = formData.get("careerHistory");

  // ===== 許可拡張子 =====
  const allowedExtensions = ["pdf", "doc", "docx"];

  // =====  ファイル拡張子チェック =====
  const isValidFile = (file) => {
    if (!file || file.size === 0) return false;
    const ext = file.name.split(".").pop().toLowerCase();
    return allowedExtensions.includes(ext);
  };

  // ===== 履歴書（必須）拡張子チェック =====
  if (!isValidFile(resumeFile)) {
    resumeInput.setCustomValidity("履歴書はPDFまたはWord形式でアップロードしてください");
    resumeInput.reportValidity();
    return false;
  }

  // ===== 職務経歴書（任意）拡張子チェック =====
  if (careerFile && careerFile.size > 0 && !isValidFile(careerFile)) {
    careerInput.setCustomValidity("職務経歴書はPDFまたはWord形式でアップロードしてください")
    careerInput.reportValidity();
    return false;
  }

  return true;
}


// ========== 確認モーダル表示内容作成 ==========
function buildConfirmContent(formData) {

  // ===== 一時的にまとめるDocumentFragmentを作成 =====
  const formFragments = document.createDocumentFragment();

  // ===== テキスト入力値取得 =====
  const nameValue = formData.get("name")?.trim();
  const phoneValue = formData.get("phone")?.trim();
  const emailValue = formData.get("email")?.trim();
  const noteValue = formData.get("note")?.trim();

  // ===== ファイル情報取得 =====
  const resumeFile = formData.get("resume");
  const careerFile = formData.get("careerHistory");
  // ===== ファイル名取得 =====
  const resumeFileName = resumeFile?.name;
  const careerFileName = careerFile?.name;

  // ===== 確認モーダル用の行を作成 =====
  formFragments.appendChild(createMailRow("お名前：", nameValue));
  formFragments.appendChild(createMailRow("電話番号：", phoneValue || "未入力"));
  formFragments.appendChild(createMailRow("メールアドレス：", emailValue));
  formFragments.appendChild(createMailRow("備考：", noteValue || "未入力"));
  formFragments.appendChild(createMailRow("履歴書：", resumeFileName));
  formFragments.appendChild(createMailRow("職務経歴書：", careerFileName || "未添付"));

  return formFragments;
}


// ========== 完了モーダル閉じる ==========
document.addEventListener("click", (e) => {

  // クリックされた要素が閉じるボタンかチェック
  if (e.target.id === "completeCloseBtn") {
    document.getElementById("complete_modal").classList.add("hidden");
  }
});


// ========== XSS対策 ==========
// innerHTMLではなくtextContentとTextNodeを使用
function createMailRow(label, value) {

  // ===== <p>作成 =====
  const mailRow = document.createElement("p");
  // ===== <strong>作成 =====
  const valueStrong = document.createElement("strong");

  // ===== 文字列をtextContentでセット =====
  valueStrong.textContent = label;

  // ===== 値文字列もテキストノードとして作成 =====
  const mailText = document.createTextNode(value);

  // ===== <p> にラベルと値を追加 =====
  mailRow.appendChild(valueStrong);
  mailRow.appendChild(mailText);

  return mailRow;
}

