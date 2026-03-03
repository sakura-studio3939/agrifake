// ========== 表示中のページリンク無効化 ==========
const disableCurrentLink = () => {
  // URLの末尾からファイル名取得
  const currentPage = location.pathname.split('/').pop();

  // ヘッダー内のリンクをチェック
  document.querySelectorAll('.nav__item a').forEach(link => {
    const linkHref = link.getAttribute('href');

    // 現在のページと一致するリンクに「is-current」を付与
    if (linkHref && linkHref.endsWith(currentPage)) {
      link.classList.add('is-current');
    }
  });
};


// ========== ナビゲーションバー表示 ==========
const setupHamburger = () => {
  const hamburgerMenu = document.getElementById("hamburger-menu");
  const navBar = document.getElementById("nav__bar");
  const body = document.body;
  const navBarOverlay = document.getElementById("nav__bar--overlay");

  if (!hamburgerMenu || !navBar) return;

  hamburgerMenu.addEventListener("click", () => {
    const scrollBarWidth = window.innerWidth - document.documentElement.clientWidth;

    navBar.classList.toggle("active");
    hamburgerMenu.classList.toggle("active");
    body.classList.toggle("no-scroll");
    navBarOverlay.classList.toggle("active");

    // ===== スクロール無効時の画面のズレ防止 =====
    if (document.body.classList.contains("no-scroll")) {
      document.body.style.paddingRight = scrollBarWidth + "px";
    } else {
      document.body.style.paddingRight = "";
    }
  });
};


// ========== ヘッダー初期化 ==========
const loadHeader =  () => {
    // ----- 表示中のページリンク無効化 -----
    disableCurrentLink();

    // ----- ハンバーガーメニュー表示 -----
    setupHamburger();
};

// ========== 初期実行 ==========
document.addEventListener("DOMContentLoaded", loadHeader);

