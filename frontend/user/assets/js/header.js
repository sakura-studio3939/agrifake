// ========== ヘッダー初期化 ==========
const loadHeader = () => {
  disableCurrentLink();
  setupHamburger();
};

// ========== LiveServer用header差し込み ==========
const header = document.getElementById("header");

if (header && header.children.length === 0) {
  fetch("/user/components/header.html")
    .then(res => res.text())
    .then(html => {
      header.innerHTML = html;

      // ★ header挿入後に初期化
      loadHeader();
    });
} else {
  // ========== 初期実行 ==========
  document.addEventListener("DOMContentLoaded", loadHeader);
}


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
  const navBarOverlay = document.getElementById("nav__bar--overlay");
  const body = document.body;

  if (!hamburgerMenu || !navBar || !navBarOverlay) return;

  // ===== メニューを閉じる関数 =====
  const closeMenu = () => {
    navBar.classList.remove("active");
    hamburgerMenu.classList.remove("active");
    body.classList.remove("no-scroll");
    navBarOverlay.classList.remove("active");
    body.style.paddingRight = "";
  };

  // ===== ハンバーガークリック =====
  hamburgerMenu.addEventListener("click", () => {
    const scrollBarWidth = window.innerWidth - document.documentElement.clientWidth;

    navBar.classList.toggle("active");
    hamburgerMenu.classList.toggle("active");
    body.classList.toggle("no-scroll");
    navBarOverlay.classList.toggle("active");

    // === スクロール無効時の画面のズレ防止 ===
    if (body.classList.contains("no-scroll")) {
      body.style.paddingRight = scrollBarWidth + "px";
    } else {
      body.style.paddingRight = "";
    }
  });

  // ===== ESCキーで閉じる =====
  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") {
      closeMenu();
    }
  });

  // ===== overlayクリックで閉じる =====
  navBarOverlay.addEventListener("click", closeMenu);
};


