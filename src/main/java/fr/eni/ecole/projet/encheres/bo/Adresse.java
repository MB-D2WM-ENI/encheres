package fr.eni.ecole.projet.encheres.bo;

import java.util.Objects;

public class Adresse {

    private long id;

    @NotBlank(message = "{NotBlank.utilisateur.adresse.nom}")
    @Size(min = 4, max = 150)
    private String nom;

    @NotBlank
    @Size(min = 1, max = 5)
    private String codePostal;

    @NotBlank
    @Size(min = 4, max = 150)
    private String ville;

    // Constructeur par défaut
    public Adresse() {
    }

    public Adresse(String codePostal, long id, String nom, String ville) {
        this.codePostal = codePostal;
        this.id = id;
        this.nom = nom;
        this.ville = ville;
    }

    // Insérer les constructeurs avec paramètres spécifiques si besoin

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @Override
    public String toString() {
        return "Adresse {" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Adresse adresse = (Adresse) o;
        return id == adresse.id && Objects.equals(nom, adresse.nom) && Objects.equals(codePostal, adresse.codePostal) && Objects.equals(ville, adresse.ville);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, codePostal, ville);
    }
}
