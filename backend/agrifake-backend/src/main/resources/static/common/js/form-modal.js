// ========== 確認モーダル制御 ==========
export function formModal(formModalConfig) {
  // ===== DOM取得 =====
  // ----- 確認モーダルDOM -----
  const confirmModal = document.getElementById("confirm_modal");

  // ----- 確認モーダル要素 -----
  const confirmTitle = confirmModal.querySelector(".confirm-title");
  const confirmMessage = confirmModal.querySelector(".confirm-message");
  const confirmBody = document.getElementById("confirmBody");

  // ----- ボタン -----
  const backBtn = confirmModal.querySelector(".modal-back-btn");
  const confirmBtn = confirmModal.querySelector(".modal-confirm-btn");
  /* const closeBtn = completeModal.querySelector(".modal-close-btn"); */


  //  ===== 文言差し替え（各ページ用js内で文言をセット） =====
  confirmTitle.textContent = formModalConfig.confirmTitle;
  confirmMessage.textContent = formModalConfig.confirmMessage;

  // ===== 確認モーダル表示 =====
  function openConfirm(confirmContent) {
    // ----- モーダルの中身リセット -----
    confirmBody.innerHTML = "";

    // ----- DOMノード判定 -----
    if (confirmContent instanceof Node) {
      confirmBody.appendChild(confirmContent);
    } else {
      confirmBody.textContent = confirmContent;
    }

    confirmModal.classList.remove("hidden");
  }

  //  ----- 確認モーダルを閉じる -----
  function closeConfirm() {
    confirmModal.classList.add("hidden");
  }


  // ===== ボタン制御 =====
  // ----- 戻るボタン -----
  backBtn.addEventListener("click", closeConfirm);

  // ----- 確認ボタン -----
  confirmBtn.addEventListener("click", () => {
    try {
      // --- 二重送信防止 ---
      confirmBtn.disabled = true;
      // --- 送信時戻るボタンクリック不可 ---
      backBtn.disabled = true;

      // --- 送信処理 ---
      if (formModalConfig.onConfirm) {
        formModalConfig.onConfirm();
      }

      // --- 送信成功時 ---
      // closeConfirm();

      // --- 送信失敗時 ---
    } catch (error) {
      alert("送信に失敗しました");
      console.error(error);
      confirmBtn.disabled = false;
    }
  });

  return {
    openConfirm
  };
}

