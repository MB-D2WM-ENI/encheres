package fr.eni.ecole.projet.encheres.bll;

import fr.eni.ecole.projet.encheres.bo.Adresse;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;

public interface UtilisateurService {



    // Ajout d'un utilisateur (méthode create de UtilisateurDAO)
    void creerUtilisateur(Utilisateur utilisateur);

    // Modification du profil utilisateur
    void modifierProfil(Utilisateur utilisateur);

    Utilisateur findByPseudo(String pseudo);

    Utilisateur readUserAdresseByPseudo(String pseudo);

}
