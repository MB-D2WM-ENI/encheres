const formulaire = document.getElementById('formulaire');
const barreRecherche = document.getElementById('recherche');
const selectionCategorie = document.getElementById('categorie');
const nomArticle = document.querySelectorAll('.lien-article');
const cartes = document.querySelectorAll('div.card');
const boutons = document.getElementById('boutons');
const boutonAnnuler = document.getElementById('annuler');

// Pour gérer le mode connecté / déconnecté
const userCo = document.getElementById('utilisateur');

if(userCo == null) {

    // Évènement d'écoute sur le formulaire en mode déconnecté
    formulaire.addEventListener('submit', function (event) {
        // Empêche le comportement par défaut (envoi du formulaire)
        event.preventDefault();

        // Récupérer les valeurs des champs (recherche & categorie)
        const rechercheValue = barreRecherche.value;
        const categorieValue = selectionCategorie.value;

        // Boucle pour chaque article
        nomArticle.forEach((lien, index)  => {

            // Conversion du texte saisie et du nom de l'article
            const rechercheConvert = rechercheValue.toLowerCase();
            const nomArtConvert = lien.textContent.toLowerCase();

            // Pointage des divs via l'index pour l'affichage/masquage des divs
            const carte = cartes[index];

            // Condition pour afficher/masquer les divs
            if (nomArtConvert.includes(rechercheConvert) && categorieValue === carte.attributes.value.value || rechercheConvert === nomArtConvert && categorieValue === carte.attributes.value.value) {
                carte.style.display = "block";
            // Condition supplémentaire pour le cas où la sélection est sur "TOUTES"
            } else if (nomArtConvert.includes(rechercheConvert) && categorieValue === "0" || rechercheConvert === nomArtConvert && categorieValue === "0") {
                carte.style.display = "block";

            } else {
                carte.style.display = "none";
            }

        });

    });

        // Évènement d'écoute sur le bouton reset qui affiche tous les articles au clic
        boutonAnnuler.addEventListener('click', function (e) {
            cartes.forEach((card) => {
               card.style.display = "block";
            });
        });

} else {

        // Évènement d'écoute sur le bouton reset qui redirige vers "/" pour annuler les filtres en mode connecté
        boutonAnnuler.addEventListener('click', function (reset) {
            window.location.href = '/';
        });

        // Gestion visuelle des boutons radios en mode déconnecté
        const boutonAchats = document.getElementById('achat_article');
        const boutonVentes = document.getElementById('vente_article');
        const selectAchats = document.getElementById('select-achat');
        const selectVentes = document.getElementById('select-vente');

        // Appel de la fonction pour initialiser par défaut le "disabled"
        desactivationSelect();

        // Évènements d'écoute sur les boutons pour les changements
        boutonAchats.addEventListener('change', desactivationSelect);
        boutonVentes.addEventListener('change', desactivationSelect);

        function desactivationSelect() {
            if (boutonAchats.checked === true) {
                selectAchats.disabled = false;
                selectVentes.disabled = true;
            } else if (boutonVentes.checked === true) {
                selectVentes.disabled = false;
                selectAchats.disabled = true;
            }
        }
}