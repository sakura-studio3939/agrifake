// ===== モーダルを開く =====
function showModal() {
  const modal = document.getElementById("galleryModal");
  modal.classList.remove("hidden");
}

// ===== モーダルを閉じる =====
function closeModal() {
  const modal = document.getElementById("galleryModal");
  modal.classList.add("hidden");
}

// ===== モーダル外クリックで閉じる =====
document.addEventListener("click", function(e) {
  const modal = document.getElementById("galleryModal");

  if (e.target === modal) {
    closeModal();
  }
});

// ===== ESCキーでモーダルを閉じる =====
document.addEventListener("keydown", function(e) {

  const modal = document.getElementById("galleryModal");

  if (e.key === "Escape" && !modal.classList.contains("hidden")) {
    closeModal();
  }

});