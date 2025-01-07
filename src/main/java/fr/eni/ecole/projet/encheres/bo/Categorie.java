package fr.eni.ecole.projet.encheres.bo;

import java.util.Objects;

public class Categorie {

    private long id;
    private String libelle;

    // Constructeur par défaut
    public Categorie() {
    }

    // Insérer les constructeurs avec paramètres spécifiques si besoin

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "Categorie {" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return id == categorie.id && Objects.equals(libelle, categorie.libelle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, libelle);
    }
}
