package fr.eni.ecole.projet.encheres.dal;

import fr.eni.ecole.projet.encheres.bo.Adresse;

public interface AdresseDAO {

    Adresse read(long noAdresse);


    // Méthode qui créé l'adresse
    void create(Adresse adresse);

    // Méthode qui met à jour l'adresse
    void update(Adresse adresse);


}
