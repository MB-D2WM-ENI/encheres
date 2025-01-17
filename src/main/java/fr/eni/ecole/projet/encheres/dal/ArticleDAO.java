package fr.eni.ecole.projet.encheres.dal;

import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;

import java.util.List;

public interface ArticleDAO {

   List<ArticleAVendre> findArticleVente();

   List<ArticleAVendre> filtreArticle(String nom, int categorie, String filtre, int statutEnchere, String pseudo);
}
