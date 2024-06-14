function selectAllCheckboxes() {
    let checkboxes = document.querySelectorAll('.readmeorno-span');

    checkboxes.forEach(function(checkbox) {
        checkbox.checked = true;
    });

    document.getElementById('highlight-message').setAttribute('onclick', 'deselectAllCheckboxes()');
    document.querySelector('.one_block-text').innerText = 'Снять выделение';
}

function deselectAllCheckboxes() {
    let checkboxes = document.querySelectorAll('.readmeorno-span');

    checkboxes.forEach(function(checkbox) {
        checkbox.checked = false;
    });

    document.getElementById('highlight-message').setAttribute('onclick', 'selectAllCheckboxes()');
    document.querySelector('.one_block-text').innerText = 'Выделить все';
}