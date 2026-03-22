import { products } from './products.js';

window.openModal = function(img) {
  const id = img.dataset.id;

  fetch(`/gallery/${id}`)
    .then(res => res.json())
    .then(data => {

      document.getElementById("detailImage").src = data.detailImage || data.thumbnailImage;
      document.getElementById("modalTitle").textContent = data.title;
      document.getElementById("modalDescription").textContent = data.description;

      showModal();

    })
    .catch(err => console.error(err));
}

// ========== 仮データ埋め込み ==========
// ===== カテゴリ分け =====
const freshProduce = products.filter(p => p.category === 'FRESH_PRODUCE');
const processedProducts = products.filter(p => p.category === 'PROCESSED_PRODUCTS');

// ===== HTML生成（Thymeleafと同じ構造）=====
const createItem = (item) => {
  return `
    <div class="gallery-item">
      <img
        src="${item.thumbnailImage}"
        class="gallery-thumb"
        data-id="${item.id}"
        onclick="openModal(this)"
      >
    </div>
  `;
};

// ===== 描画 =====
const renderList = (list, id) => {
  const el = document.getElementById(id);
  if (!el) return;

  // Thymeleafが描画してたら何もしない
  if (el.children.length > 0) return;

  el.innerHTML = list.map(createItem).join('');
};

// ===== 実行 =====
document.addEventListener('DOMContentLoaded', () => {
  renderList(freshProduce, 'freshProduceList');
  renderList(processedProducts, 'processedProductsList');
});
