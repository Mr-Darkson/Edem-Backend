function deleteMessages() {
    const checkboxes = document.querySelectorAll('input[name="message"]:checked');
    const messageIds = Array.from(checkboxes).map(cb => cb.value);

    fetch('/deleteMessages', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(messageIds),
    })
        .then(response => {
            if (response.ok) {
                alert('Выбранные сообщения успешно удалены');
            } else {
                alert('Произошла ошибка при удалении сообщений');
            }
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
}