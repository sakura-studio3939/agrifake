// ===== 公開非公開切り替え =====
function togglePublish(btn, id) {
  // ===== CSRFトークンを meta タグから取得 =====
  const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

  fetch(`/admin/gallery/togglePublished/${id}`, {
    method: 'POST',
    headers: {
      [header]: token, // CSRFトークン
      'Content-Type': 'application/json'
    }
  })
  .then(res => res.json())
  .then(data => {
    // ボタン変更
    btn.textContent = data.published ? '公開中' : '非公開';

    // サムネ更新
    const thumb = document.querySelector(`.gallery-thumb[data-id="${id}"]`)
                          .forEach(el => {
                            el.dataset.published = data.published;
                          });

    if (thumb) {
      thumb.dataset.published = data.published;
    }
  })
  .catch(err => console.error(err));
}

window.openModal = function(img) {

  const id = img.dataset.id;

  fetch(`/admin/gallery/${id}`)
    .then(response => {

      // ----- HTTPエラーを検知 -----
      if (!response.ok) {
        throw new Error("サーバーエラーです");
      }

      return response.json();
    })
    .then(data => {

      // ===== モーダル取得 =====
      const modal = document.getElementById("galleryModal");

      // ===== データ取得 =====
      const detailImage = document.getElementById("detailImage");
      const title = document.getElementById("modalTitle");
      const description = document.getElementById("modalDescription");
      const createdAt = document.getElementById("createdAt");
      const updatedAt = document.getElementById("updatedAt");
      const publishBtn = document.getElementById("publishBtn");

      // ===== 表示内容 =====
      detailImage.src = data.detailImage || data.thumbnailImage;  // 詳細画像がない場合は詳細画像を使用
      title.textContent = data.title;
      description.textContent = data.description;
      createdAt.textContent = "作成日: " + data.createdAt;
      updatedAt.textContent = "更新日: " + data.updatedAt;

      // ===== 編集ボタンリンクを動的にセット =====
      const editBtn = document.getElementById("editBtn");
      editBtn.href = `/admin/gallery/edit/${data.id}`;

      // ===== 公開非公開切り替えボタンを動的に設定 =====
      publishBtn.textContent = data.published ? "公開中" : "非公開";

      publishBtn.onclick = function() {
        togglePublish(this, data.id);
      };

      // ===== 削除 =====
      setDeleteAction(data.id);

      modal.classList.remove("hidden");

    });
}

// ========== 論理削除 ==========
function setDeleteAction(id) {
  const form = document.getElementById("deleteForm");
  form.action = "/admin/gallery/delete/" + id;
}

// ========== 削除確認 ==========
const deleteForm = document.getElementById("deleteForm");

if (deleteForm) {
  deleteForm.addEventListener("submit", function(e) {
    if (!confirm("このギャラリーを削除しますか？\nこの操作は元に戻せません。")) {
      e.preventDefault();
    }
  });
}