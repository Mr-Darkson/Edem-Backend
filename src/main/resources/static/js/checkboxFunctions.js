function selectAllCheckboxes() {
    let checkboxes = document.querySelectorAll('.readmeorno-span');

    checkboxes.forEach(function (checkbox) {
        checkbox.checked = true;
    });

    document.getElementById('highlight-message').setAttribute('onclick', 'deselectAllCheckboxes()');
    document.querySelector('.one_block-text').innerText = 'Снять выделение';
}

function deselectAllCheckboxes() {
    let checkboxes = document.querySelectorAll('.readmeorno-span');

    checkboxes.forEach(function (checkbox) {
        checkbox.checked = false;
    });

    document.getElementById('highlight-message').setAttribute('onclick', 'selectAllCheckboxes()');
    document.querySelector('.one_block-text').innerText = 'Выделить все';
}


function deleteSelectedMessages() {
    let selectedMessages = [];
    let checkboxes = document.querySelectorAll('.readmeorno-span');

    checkboxes.forEach(function (checkbox) {
        if (checkbox.checked) {
            selectedMessages.push(checkbox.id);
        }
    });

    console.log(JSON.stringify({messages: selectedMessages}))

    // Отправка запроса на сервер
    fetch('/service/rest/deleteMessages', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({messages: selectedMessages})
    })
    .then(response => {
         if (response.ok) {
                // Успешный ответ от сервера, можно обновить интерфейс или выполнить другие действия
         }
    })
    .catch(error => {
        console.error('Ошибка при удалении сообщений:', error);
    });

}

