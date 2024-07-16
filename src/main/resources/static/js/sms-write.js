


//SEND-REDIRECT Desktop

document.getElementById("reply-button").addEventListener("click", reply)
document.getElementById("redirect-button").addEventListener("click", redirect)

const modalTheme = document.getElementById("send_letter_theme")
const modalText = document.getElementById("send_letter_text")
const letterTheme = document.getElementById("letter_theme")
const letterText = document.getElementById("letter_text")

const senderLogin = document.getElementById("senderLogin");
const toWhom = document.getElementById("submithire")

const sendForm = document.getElementById("sendForm")

const pinFiles = document.getElementById("pinFiles")


function reply() {
    pinFiles.classList.remove("hidden")
    toWhom.value = senderLogin.textContent;
    toWhom.readOnly = true;


    sendForm.action="/service/mailbox"
    console.log(sendForm)
    modalTheme.textContent = null;
    modalTheme.readOnly = false;
    modalText.textContent = null;
    modalText.readOnly = false;


}

function redirect() {
    pinFiles.classList.add("hidden")
    toWhom.readOnly = false;
    toWhom.value = ""

    sendForm.action="/service/message/redirect"
    console.log(sendForm)
    modalTheme.textContent = letterTheme.textContent.substring(5);
    modalTheme.readOnly = true;
    modalText.textContent = letterText.textContent;
    modalText.readOnly = true;

}

//SEND REDIRECT MOBILE
document.getElementById("reply-button_mb").addEventListener("click", reply)
document.getElementById("redirect-button_mb").addEventListener("click", redirect)

