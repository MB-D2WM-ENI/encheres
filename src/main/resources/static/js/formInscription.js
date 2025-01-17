/***
 *
 * Déclaration des constantes pour le mot de passe
 * Vérifier et valider le pattern
 * Vérifier et valider la correspondance
 */


let messageValidation = document.getElementById("messageValidation");
const btnCreerProfil = document.getElementById("btnCreerProfil");
const formSuscribe = document.getElementById("formSuscribe");
const pseudo = document.getElementById("pseudo");
const email = document.getElementById("email");
const prenom = document.getElementById("prenom");
const nom = document.getElementById("nom");
const telephone = document.getElementById("telephone");
const rue = document.getElementById("rue");
const codePostal = document.getElementById("codePostal");
const ville = document.getElementById("ville");
const password = document.getElementById("motDePasse");
const passwordConfirmation = document.getElementById("confirmationMdp");


messageValidation.classList.remove('error');


const regexMdp = new RegExp(/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$/);


formSuscribe.addEventListener("submit", (event) => {

    let motDePasse = document.getElementById("motDePasse").value;
    let confirmationMdp = document.getElementById("confirmationMdp").value;

    // Vérification du mot de passe et de sa confirmation
    if (motDePasse !== "" && confirmationMdp !== "") {
        if (motDePasse !== confirmationMdp) {
            event.preventDefault();
            messageValidation.classList.add("error");
            messageValidation.textContent = "Les deux mots de passe ne correspondent pas";
            setTimeout(() => messageValidation.textContent = "", 5000);
            return;
        }

        // Vérification du format du mot de passe avec regex
        if (!regexMdp.test(motDePasse)) {
            event.preventDefault();
            messageValidation.classList.add("error");
            messageValidation.innerHTML = "Veuillez saisir un mot de passe avec : un chiffre, une minuscule, une majuscule, un caractère spécial et entre 8 et 20 caractères";
            setTimeout(() => messageValidation.textContent = "", 6000);
            return;
        }
    } else {
        // Si le mot de passe ou la confirmation sont vides
        event.preventDefault();
        messageValidation.classList.add("error");
        messageValidation.textContent = "Veuillez entrer un mot de passe et sa confirmation.";
        setTimeout(() => messageValidation.textContent = "", 5000);
        return;
    }
});

