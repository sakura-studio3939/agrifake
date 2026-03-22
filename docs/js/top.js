// ========= ヒーロー画像切り替え ==========

// ===== 表示画像 =====
const heroImagePaths = [
  `/user/images/top/hero_spring_rice_paddy.png`,
  `/user/images/top/hero_daikon_radish_field.png`,
  `/user/images/top/hero_greenhouse_tomatoes.png`,
  `/user/images/top/hero_autumn_rice_field.png`,
  `/user/images/top/hero_snowmelt_river.png`
];

const heroImages = [];

const heroSlideImages = document.getElementById("hero-slideshow");

let currentImageIndex = 0;

// ===== 画像事前読み込み =====
heroImagePaths.forEach((imagePath) => {
  const image = new Image();
  image.src = imagePath;
  heroImages.push(image);
});

//5 ===== 秒おきに画像切り替え =====
// ※ 現在は他セクション実装中のため一時停止
// ※ デザイン確定後にフェード演出込みで再実装予定
/*
setInterval(() => {
  currentImageIndex = (currentImageIndex + 1) % heroImages.length;
  heroSlideImages.src = heroImages[currentImageIndex].src;
}, 5000);
*/






