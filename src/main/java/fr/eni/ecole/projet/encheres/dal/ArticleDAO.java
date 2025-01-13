package fr.eni.ecole.projet.encheres.dal;

import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;
import java.util.List;

public interface ArticleDAO {

   List<ArticleAVendre> findArticleVente();

}
