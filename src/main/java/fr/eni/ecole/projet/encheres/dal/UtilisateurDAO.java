package fr.eni.ecole.projet.encheres.dal;

import fr.eni.ecole.projet.encheres.bo.Utilisateur;

public interface UtilisateurDAO {

    Utilisateur read(String pseudo);

    Utilisateur readUserAdresse(String pseudo);

    void create(Utilisateur utilisateur);

    void update(Utilisateur utilisateur);

    int uniqueEmail(String email);

    int uniquePseudo(String pseudo);
}