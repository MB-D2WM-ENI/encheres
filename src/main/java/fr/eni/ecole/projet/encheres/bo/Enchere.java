package fr.eni.ecole.projet.encheres.bo;

import java.time.LocalDate;
import java.util.Objects;

public class Enchere {

    private LocalDate date;
    private int montant;

    // Association unidirectionnelle
    private Utilisateur acquereur;
    private ArticleAVendre articleAVendre;

    // Constructeur par défaut
    public Enchere(){
    }

    // Insérer les constructeurs avec paramètres spécifiques si besoin

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public Utilisateur getAcquereur() {
        return acquereur;
    }

    public void setAcquereur(Utilisateur acquereur) {
        this.acquereur = acquereur;
    }

    public ArticleAVendre getArticleAVendre() {
        return articleAVendre;
    }

    public void setArticleAVendre(ArticleAVendre articleAVendre) {
        this.articleAVendre = articleAVendre;
    }

    @Override
    public String toString() {
        return "Enchere {" +
                "date=" + date +
                ", montant=" + montant +
                ", acquereur=" + acquereur +
                ", articleAVendre=" + articleAVendre +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Enchere enchere = (Enchere) o;
        return montant == enchere.montant && Objects.equals(date, enchere.date) && Objects.equals(acquereur, enchere.acquereur) && Objects.equals(articleAVendre, enchere.articleAVendre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, montant, acquereur, articleAVendre);
    }
}
