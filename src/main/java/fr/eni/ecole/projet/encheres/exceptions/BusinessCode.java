package fr.eni.ecole.projet.encheres.exceptions;

public class BusinessCode {


    // clés de validation des BO


    // Validation INSCRIPTION UTILISATEUR

    public static final String VALIDATION_UTILISATEUR_DB_NULL = "validation.utilisateur.db.null";
    public static final String VALIDATION_ADRESSE_NULL = "validation.adresse.null";

    public static final String VALIDATION_UTILISATEUR_PSEUDO_BLANK = "validation.utilisateur.pseudo.blank";
    public static final String VALIDATION_UTILISATEUR_PSEUDO_LENGTH = "validation.utilisateur.pseudo.length";
    public static final String VALIDATION_UTILISATEUR_PSEUDO_PATTERN = "validation.utilisateur.pseudo.pattern";
    public static final String VALIDATION_UTILISATEUR_UNIQUE_PSEUDO = "validation.utilisateur.unique.pseudo";

    public static final String VALIDATION_UTILISATEUR_NOM_BLANK = "validation.utilisateur.nom.blank";
    public static final String VALIDATION_UTILISATEUR_NOM_LENGTH = "validation.utilisateur.nom.length";

    public static final String VALIDATION_UTILISATEUR_PRENOM_BLANK = "validation.utilisateur.prenom.blank";
    public static final String VALIDATION_UTILISATEUR_PRENOM_LENGTH = "validation.utilisateur.prenom.length";

    public static final String VALIDATION_UTILISATEUR_EMAIL_BLANK = "validation.utilisateur.email.blank";
    public static final String VALIDATION_UTILISATEUR_UNIQUE_EMAIL = "validation.utilisateur.unique.email";

    public static final String VALIDATION_UTILISATEUR_ADRESSE_BLANK = "validation.utilisateur.adresse.blank";
    public static final String VALIDATION_UTILISATEUR_RUE_BLANK = "validation.utilisateur.rue.blank";
    public static final String VALIDATION_UTILISATEUR_RUE_LENGTH = "validation.utilisateur.rue.length";

    public static final String VALIDATION_UTILISATEUR_CODEPOSTAL_BLANK = "validation.utilisateur.codePostal.blank";
    public static final String VALIDATION_UTILISATEUR_CODEPOSTAL_LENGTH = "validation.utilisateur.codePostal.length";


    public static final String VALIDATION_UTILISATEUR_VILLE_BLANK = "validation.utilisateur.ville.blank";
    public static final String VALIDATION_UTILISATEUR_VILLE_LENGTH = "validation.utilisateur.ville.length";


    public static final String VALIDATION_UTILISATEUR_MOTDEPASSE_BLANK = "validation.utilisateur.motDePasse.blank";
    public static final String VALIDATION_UTILISATEUR_MOTDEPASSE_PATTERN = "validation.utilisateur.motDePasse.pattern";

    public static final String BLL_UTILISATEUR_SELECT_ERREUR = "bll.utilisateur.select.erreur";
    public static final String BLL_UTILISATEUR_UPDATE_ERREUR = "bll.utilisateur.update.erreur";


}
