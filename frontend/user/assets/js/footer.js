// ========== LiveServer用footer差し込み ==========
const header = document.getElementById("footer");

if (header && header.children.length === 0) {
  fetch("/user/components/footer.html")
    .then(res => res.text())
    .then(html => {
      header.innerHTML = html;
    });
}