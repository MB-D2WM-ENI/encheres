package fr.eni.ecole.projet.encheres.bo;

import jakarta.validation.constraints.*;

import java.util.Objects;

public class Utilisateur {

    @NotBlank
    @Size(min = 4, max=250)
    @Pattern(regexp = "^[a-zA-Z0-9_]*$")
    private String pseudo;

    @NotBlank
    @Size(min = 4, max=250)
    private String nom;

    @NotBlank
    @Size(min = 2, max=250)
    private String prenom;

    @NotBlank
    @Email
    private String email;

    private String telephone;


    private String motDePasse;

    private int credit;

    private boolean admin;


    // Association unidirectionnelle

    @NotNull
    private Adresse adresse;

    // Constructeur par défaut
    public Utilisateur() {
        adresse = new Adresse();
        credit = 10;
    }

    public Utilisateur( String pseudo, String prenom, String nom, String email, int credit, Adresse adresse, String telephone) {
        this.pseudo = pseudo;
        this.prenom = prenom;
        this.nom = nom;
        this.email = email;
        this.credit = credit;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    // Insérer les constructeurs avec paramètres spécifiques si besoin

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    @Override
    public String toString() {
        return "Utilisateur {" +
                "pseudo='" + pseudo + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", credit=" + credit +
                ", admin=" + admin +
                ", adresse=" + adresse +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return credit == that.credit && admin == that.admin && Objects.equals(pseudo, that.pseudo) && Objects.equals(nom, that.nom) && Objects.equals(prenom, that.prenom) && Objects.equals(email, that.email) && Objects.equals(telephone, that.telephone) && Objects.equals(motDePasse, that.motDePasse) && Objects.equals(adresse, that.adresse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pseudo, nom, prenom, email, telephone, motDePasse, credit, admin, adresse);
    }
}
