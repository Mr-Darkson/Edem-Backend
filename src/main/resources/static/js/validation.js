function checkPasswords() {
    var password1 = document.getElementById("password").value;
    var password2 = document.getElementById("repeat-password").value;

    var submitButton = document.getElementById("registerFetch");

    if (password1 !== password2) {
        submitButton.disabled = true;
        console.log('disabled');
    } else {
        submitButton.disabled = false;
        console.log('enabled');
    }
}

// Подключение функции к событию input на полях ввода паролей
document.getElementById("password").addEventListener("input", checkPasswords);
document.getElementById("repeat-password").addEventListener("input", checkPasswords);