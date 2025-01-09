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

messageValidation.style.color = "red";
messageValidation.style.backgroundColor = "white";

const regex = new Regex(/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$/);


// Ecouteur d'événement sur le bouton Créer qui vérifie en front :
// le pattern du mot de passe et la correspondance

formSuscribe.addEventListener("submit", (event) => {

    let motDePasse = document.getElementById("motDePasse").value;

    event.preventDefault();

    verifTailleMdp(motDePasse);



});