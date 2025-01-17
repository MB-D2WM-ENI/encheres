package fr.eni.ecole.projet.encheres.bll;

import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;

import java.util.List;

public interface ArticleService {

    List<ArticleAVendre> getArticles();

    List<ArticleAVendre> findArticle(String nom, int categorie, String filtre, int selection, String pseudo);
}
