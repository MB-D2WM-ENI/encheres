document.getElementById('ouvert-ferme').addEventListener("click", togglePassword)

function togglePassword() {
    let password = document.getElementById('inputPwd');
    let icone = document.getElementById('ouvert-ferme')
    if (password.type === 'password') {
        password.type = 'text';
        icone.src = '/images/open.png';
    } else {
        password.type = 'password';
        icone.src = '/images/close.png';
    }
}


