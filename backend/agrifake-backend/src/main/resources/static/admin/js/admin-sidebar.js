// ===== 表示中のページリンク無効化 =====
const disableCurrentLink = () => {

  // ----- URLの末尾から取得 -----
  const currentPage = location.pathname.split('/').pop();

  // ----- サイドバーのリンク取得 -----
  document.querySelectorAll('.nav__item a').forEach(link => {
    const linkHref = link.getAttribute('href');

    if (linkHref && linkHref.endsWith(currentPage)) {
      link.classList.add('is-current');
    }
  });

};

// ===== 初期実行 =====
disableCurrentLink();

