//ALWAYS-ACTIVE
let cbx = document.querySelectorAll('.readmeorno-span');
const deleteButton = document.getElementById('deleteButton');
const selectAll = document.getElementById("highlight-message");
deleteButton.addEventListener("click", deleteSelectedMessages);
selectAll.addEventListener("click",selectAllCheckboxes); //ADD SELECTED ALL FUNCTION TO BUTTON
cbx.forEach(c => {
    c.addEventListener('change', function() {
        const checkedCheckboxes = document.querySelectorAll('.readmeorno-span:checked');
        if (checkedCheckboxes.length > 0) {
            deleteButton.classList.remove('hidden');
        } else {
            deleteButton.classList.add('hidden');
        }
    });
})




//FUNCTIONS
function selectAllCheckboxes() {
    let checkboxes = document.querySelectorAll('.readmeorno-span');

    checkboxes.forEach(function (checkbox) {
        deleteButton.classList.remove('hidden');
        checkbox.checked = true;
    });

    let bt = document.getElementById('highlight-message')
    bt.removeEventListener('click', selectAllCheckboxes);
    bt.addEventListener('click', deselectAllCheckboxes);
    document.querySelector('.one_block-text').innerText = 'Снять выделение';
}

function deselectAllCheckboxes() {
    let checkboxes = document.querySelectorAll('.readmeorno-span');

    checkboxes.forEach(function (checkbox) {
        deleteButton.classList.add('hidden');
        checkbox.checked = false;
    });

    let bt = document.getElementById('highlight-message')
    bt.removeEventListener('click', deselectAllCheckboxes);
    bt.addEventListener('click', selectAllCheckboxes);
    document.querySelector('.one_block-text').innerText = 'Выделить все';
}

function deleteSelectedMessages() {
    let checkboxes = document.querySelectorAll('.readmeorno-span:checked');
    const ids = Array.from(checkboxes)
        .filter(checkbox => checkbox.checked)
        .map(checkbox => parseInt(checkbox.id));

    if (ids.length > 0) {
        console.log(JSON.stringify({ids: ids}))

        fetch('/service/api/deleteMessages', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ids: ids})
        }) .then(responce => {
            if(responce.ok) {
                location.reload();
            }
        })
            .catch(error => console.error('Ошибка:', error));
    }
    else {
        alert('Сообщения не выделены');
    }

}




