function openRightModal(url) {
    const modal = document.getElementById("right-modal");
    const modalBody = document.getElementById("right-modal-body");

    fetch(url)
        .then(response => response.text())
        .then(data => {
            modalBody.innerHTML = data;
            modal.style.display = "block";
        })
        .catch(error => console.error('Error loading modal content:', error));
}

function closeRightModal() {
    const modal = document.getElementById("right-modal");
    modal.style.display = "none";
}

window.onclick = function(event) {
    const modal = document.getElementById("right-modal");
    if (event.target === modal) {
        modal.style.display = "none";
    }
}