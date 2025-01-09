package fr.eni.ecole.projet.encheres.bll.contexte;

import fr.eni.ecole.projet.encheres.bo.Utilisateur;

public interface UtilisateurService {
    Utilisateur charger(String pseudo);
}
