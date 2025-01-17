package fr.eni.ecole.projet.encheres.bll;

import fr.eni.ecole.projet.encheres.bo.Adresse;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;
import fr.eni.ecole.projet.encheres.dal.AdresseDAO;
import fr.eni.ecole.projet.encheres.dal.UtilisateurDAO;
import fr.eni.ecole.projet.encheres.exceptions.BusinessCode;
import fr.eni.ecole.projet.encheres.exceptions.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private UtilisateurDAO utilisateurDAO;
    private AdresseDAO adresseDAO;
    //    private final PasswordEncoder mdpCrypte;
    private static final Logger log = LoggerFactory.getLogger(UtilisateurService.class);


    // Constructeur
    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, AdresseDAO adresseDAO) {
        this.utilisateurDAO = utilisateurDAO;
        this.adresseDAO = adresseDAO;
    }


    // Appel de la méthode create
    @Override
    @Transactional
    public void creerUtilisateur(Utilisateur utilisateur) {

        // Encoder le mot de passe
        // String motDepasseEncode = mdpCrypte.encode(utilisateur.getMotDePasse());
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        // Encodez le mot de passe de l'utilisateur
        String motDePasseEncode = passwordEncoder.encode(utilisateur.getMotDePasse());


        // Validation des données via la couche présentation
        BusinessException be = new BusinessException();
        boolean isValid = true;

//        isValid &= validerPseudoUtilisateur(utilisateur.getPseudo(), be);

        Adresse adresse = utilisateur.getAdresse();

        isValid &= validerAdresse(adresse, be);
//        isValid &= validerPseudo(utilisateur.getPseudo(), be);
        isValid &= validerUniquePseudo(utilisateur.getPseudo(), be);
        isValid &= validerNom(utilisateur.getNom(), be);
        isValid &= validerPrenom(utilisateur.getPrenom(), be);
        isValid &= validerEmail(utilisateur.getEmail(), be);
        isValid &= validerUniqueEmail(utilisateur.getEmail(), be);
        isValid &= validerRue(adresse.getNom(), be);
        isValid &= validerCodePostal(adresse.getCodePostal(), be);
        isValid &= validerVille(adresse.getVille(), be);
        isValid &= validerMotDePasse(utilisateur.getMotDePasse(), be);

        if (isValid) {
            // Mettre à jour le mot de passe crypté
            utilisateur.setMotDePasse(motDePasseEncode);
            // Création d'un utilisateur et de son adresse
            adresseDAO.create(adresse);
            utilisateurDAO.create(utilisateur);
        } else {
            throw be;
        }
    }

    // Méthode pour modifier le profil
    @Override
    @Transactional
    public void modifierProfil(Utilisateur utilisateur) {

        BusinessException be = new BusinessException();
        boolean isValid = true;

        // récupération et instanciation de l'objet Adresse
        Adresse adresse = utilisateur.getAdresse();

        isValid &= validerRue(adresse.getNom(), be);
        isValid &= validerCodePostal(adresse.getCodePostal(), be);
        isValid &= validerVille(adresse.getVille(), be);

        if (isValid) {
            try {
                utilisateurDAO.update(utilisateur);
                Utilisateur utilisateurPseudo = utilisateurDAO.readUserAdresse(utilisateur.getPseudo());
                adresse.setId(utilisateurPseudo.getAdresse().getId());
                adresseDAO.update(adresse);
            } catch (DataAccessException e) {
                throw e;
            }
        }
    }


    // Retourne le profil de l'utilisateur à partir de son pseudo
    @Override
    public Utilisateur findByPseudo(String pseudo) {

        // Validation des données de la couche présentation
        BusinessException be = new BusinessException();

        try {
            return utilisateurDAO.read(pseudo);
        } catch (DataAccessException e) {
            // Rollback automatique
            be.add(BusinessCode.BLL_UTILISATEUR_SELECT_ERREUR);
            throw be;
        }
    }

    // Retourne les infos d'un utilisateur avec son adresse pour la page de modification de profil
    @Override
    public Utilisateur readUserAdresseByPseudo(String pseudo) {

        return utilisateurDAO.readUserAdresse(pseudo);
    }

    // Met à jour les données d'un utilisateur (sauf le mot de passe) dont son adresse

    /**
     * Méthodes des validations des BO
     */
    // Valider l'utilisateur
    private boolean validerPseudoUtilisateur(String pseudo, BusinessException be) {

        // Le pseudo doit exister - S'il n'existe pas, il y aura levée d'exception
        // DataAccessException

        try {
            Utilisateur u = utilisateurDAO.read(pseudo);
            if (u == null) {
                // il n'y a pas d'utilisateur correspondant à la base
                be.add(BusinessCode.VALIDATION_UTILISATEUR_DB_NULL);
                return false;
            }
        } catch (DataAccessException e) {
            // On ne trouve pas l'utilisateur (ni en base)
            be.add(BusinessCode.BLL_UTILISATEUR_SELECT_ERREUR);
            return false;
        }
        return true;
    }

    // Valider l'adresse
    private boolean validerAdresse(Adresse a, BusinessException be) {
        if (a == null) {
            be.add(BusinessCode.VALIDATION_ADRESSE_NULL);
            return false;
        }
        return true;
    }

    // Valider le pseudo
    private boolean validerPseudo(String pseudo, BusinessException be) {
        if (pseudo == null || pseudo.isBlank()) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_PSEUDO_BLANK);
            return false;
        }

        if (pseudo.length() < 4 || pseudo.length() > 250) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_PSEUDO_LENGTH);
            return false;
        }

        String regex = "^[a-zA-Z0-9_]*$";

        if (!pseudo.matches(regex)) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_PSEUDO_PATTERN);
            return false;
        }
        return true;
    }

    // Valider que le pseudo soit unique
    private boolean validerUniquePseudo(String pseudo, BusinessException be) {
        int count = utilisateurDAO.uniquePseudo(pseudo);
        if (count == 1) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_UNIQUE_PSEUDO);
            return false;
        }
        return true;
    }

    // Valider le nom
    private boolean validerNom(String nom, BusinessException be) {
        if (nom == null || nom.isBlank()) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_NOM_BLANK);
            return false;
        }

        if (nom.length() < 4 || nom.length() > 250) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_NOM_LENGTH);
            return false;
        }
        return true;
    }

    // valider le prénom
    private boolean validerPrenom(String prenom, BusinessException be) {
        if (prenom == null || prenom.isBlank()) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_PRENOM_BLANK);
            return false;
        }
        if (prenom.length() < 2 || prenom.length() > 250) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_PRENOM_LENGTH);
            return false;
        }
        return true;
    }

    // valider l'email
    private boolean validerEmail(String email, BusinessException be) {
        if (email == null || email.isBlank()) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_EMAIL_BLANK);
            return false;
        }
        return true;
    }

    // Valider que l'email soit unique
    private boolean validerUniqueEmail(String email, BusinessException be) {
        int count = utilisateurDAO.uniqueEmail(email);
        if (count == 1) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_UNIQUE_EMAIL);
            return false;
        }
        return true;
    }

    // valider l'adresse  : rue, code postal, ville
    private boolean validerRue(String rue, BusinessException be) {
        if (rue == null || rue.isBlank()) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_RUE_BLANK);
            return false;
        }
        if (rue.length() < 4 || rue.length() > 150) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_RUE_LENGTH);
            return false;
        }
        return true;
    }

    private boolean validerCodePostal(String codePostal, BusinessException be) {
        if (codePostal == null || codePostal.isBlank()) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_CODEPOSTAL_BLANK);
            return false;
        }

        if (codePostal.length() != 5) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_CODEPOSTAL_LENGTH);
            return false;
        }
        return true;
    }

    // valider la ville
    private boolean validerVille(String ville, BusinessException be) {
        if (ville == null || ville.isBlank()) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_VILLE_BLANK);
            return false;
        }

        if (ville.length() < 4 || ville.length() > 250) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_VILLE_LENGTH);
            return false;
        }
        return true;
    }

    // valider le mot de passe
    private boolean validerMotDePasse(String motDePasse, BusinessException be) {
        if (motDePasse == null || motDePasse.isBlank()) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_MOTDEPASSE_BLANK);
            return false;
        }

        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$";

        if (!motDePasse.matches(regex)) {
            be.add(BusinessCode.VALIDATION_UTILISATEUR_MOTDEPASSE_PATTERN);
            return false;
        }
        return true;
    }


}
