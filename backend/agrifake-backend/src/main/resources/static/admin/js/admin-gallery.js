// ===== ギャラリー表示処理 =====
// DOM取得
const freshProduceList = document.getElementById('freshProduceList');
const processedProductsList = document.getElementById('processedProductsList');

// カテゴリーマップ
const CATEGORY_MAP = {
  FRESH_PRODUCE: freshProduceList,
  PROCESSED_PRODUCTS: processedProductsList
};

// 生産物のループ処理
products.forEach(product => {
  const li = document.createElement('li');
  li.className = 'gallery__item';

  // 一覧用サムネイル（未設定の場合は no-image を使用）
  const thumbnailSrc = product.thumbnailImage || 'assets/images/products/no-image.jpg';

  // liの中身をHTMlで設定
  li.innerHTML = `
  <img src="${thumbnailSrc}" alt="${product.name}">
  `;

  // クリックイベントを付与
  li.addEventListener('click', () => {
    openProductModal(product);
  });

  // カテゴリー判別
  const targetList = CATEGORY_MAP[product.category];
  // 存在チェック
  // 想定していないカテゴリーがあってもスルーして処理続行
  if (targetList) {
    targetList.appendChild(li);
  }
});

// ===== モーダル表示 =====
function openProductModal(products) {
  const productModal = document.getElementById('product_modal');

  const productImage = document.getElementById('modalProductImage');
  const productLabel = document.getElementById('modalProductLabel');
  const productDescription = document.getElementById('modalProductDescription');
  const productCreatedAt = document.getElementById('modalProductCreatedAt');
  const productUpdatedAt = document.getElementById('modalProductUpdatedAt');

  productImage.src =
    products.detailImage ||
    products.thumbnailImage ||
      '/assets/images/no-image.jpg';

  productImage.alt = products.name;

  // テキスト対応
  productLabel.textContent = products.name;
  productDescription.textContent = products.description;
  productCreatedAt.textContent = products.createdAt;
  productUpdatedAt.textContent = products.updatedAt;

  // モーダル表示
  productModal.classList.remove("hidden");
}

// ===== モーダルを閉じる =====
function closeProductModal() {
  const productModal = document.getElementById('product_modal');
  productModal.classList.add('hidden');
}

// ===== ×ボタン =====
document.getElementById('modalProductClose').addEventListener('click', closeProductModal);

// ===== 背景クリックで閉じる =====
document
  .querySelector('#product_modal .modal__overlay')
  .addEventListener('click', closeProductModal);

// ===== SECで閉じる =====
document.addEventListener('keydown', event => {
  if (event.key === 'Escape') {
    closeProductModal();
  }
});

