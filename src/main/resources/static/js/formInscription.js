/***
 *
 * Déclaration des constantes pour le mot de passe
 * Vérifier et valider le pattern
 * Vérifier et valider la correspondance
 */


const confirmationMdp = document.getElementById("confirmationMdp").value;
let messageValidation = document.getElementById("messageValidation");
const btnCreerProfil = document.getElementById("btnCreerProfil");
const formSuscribe = document.getElementById("formSuscribe");

messageValidation.classList.remove('error');

const regexMdp = new RegExp(/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$/);


// Ecouteur d'événement sur le bouton Créer qui vérifie en front :
// le pattern du mot de passe et la correspondance

btnCreerProfil.addEventListener("click", (event) => {

    let motDePasse = document.getElementById("motDePasse").value;
    console.log(motDePasse);

    event.preventDefault();

    if (!regexMdp.test(motDePasse)) {
        console.log(motDePasse);
        messageValidation.classList.add("error");
        messageValidation.innerHTML = "Veuillez saisir un mot de passe avec : un chiffre, une minuscule, une majuscule, un caractère spécial et entre 8 et 20 caractères";
        setTimeout(function () {
            messageValidation.innerHTML = "";
        }, 6000);
    }

    if (motDePasse !== confirmationMdp) {
        console.log(motDePasse);
        messageValidation.classList.add("error");
        messageValidation.textContent = "Les deux mots de passe ne correspondent pas";
        setTimeout(function () {
            messageValidation.innerHTML = "";
        }, 5000);
    }


});