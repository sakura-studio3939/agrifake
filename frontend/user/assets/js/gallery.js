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
