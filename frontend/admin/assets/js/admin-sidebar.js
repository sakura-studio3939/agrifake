// ===== 表示中のページリンク無効化 =====
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


// ===== 「sidebar.html」ををDOMへ差し込む =====
const loadSidebar = async (id, file) => {
  try {
    const response = await fetch(file);

    // 通信失敗時はエラー出力
    if (!response.ok) {
      throw new Error(`Failed to load header: ${response.status}`)
    }

    const sidebarHtml = await response.text();
    const sidebarId = document.getElementById(id);

    // 「id="header"」が取得できなかった場合
    if (!sidebarId) {
      console.log(`Element #${id} not found`)
      return;
    }

    // DOMへ差し込む
    sidebarId.innerHTML = sidebarHtml;

    // 「header.html」差し込み後に現在のページリンクの無効化
    disableCurrentLink();

  } catch (error) {
    console.error(error);
  }
};

// --- 初期実行 ---
loadSidebar('sidebar', '../components/admin-sidebar.html');

