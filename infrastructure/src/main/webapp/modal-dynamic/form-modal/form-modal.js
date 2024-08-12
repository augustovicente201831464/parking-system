function openModal(url) {
    const modal = document.getElementById("form-modal");
    const modalBody = document.getElementById("modal-body");

    fetch(url)
        .then(response => response.text())
        .then(data => {
            modalBody.innerHTML = data;
            modal.style.display = "block";
        })
        .catch(error => console.error('Error loading modal content:', error));
}

function closeModal() {
    const modal = document.getElementById("form-modal");
    modal.style.display = "none";
}

window.onclick = function(event) {
    const modal = document.getElementById("form-modal");
    if (event.target === modal) {
        modal.style.display = "none";
    }
}