package fr.eni.ecole.projet.encheres.bll;

import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;
import fr.eni.ecole.projet.encheres.dal.ArticleDAO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleDAO articleDAO;

    public ArticleServiceImpl(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @Override
    public List<ArticleAVendre> getArticles() {
        return articleDAO.findArticleVente();
    }

    @Override
    public List<ArticleAVendre> findArticle(String nom, int categorie, String filtre, int selection, String pseudo) {

        // Switch en fonction du statut_enchère et des "value" des <select> (selection)
        int statutEnchere;
        switch (selection) {
            case 1:
            case 4:
                statutEnchere = 0;
                break;
            case 2:
            case 5:
                statutEnchere = 1;
                break;
            case 3:
            case 6:
                statutEnchere = 2;
                break;
            default:
                statutEnchere = 0; // Valeur par défaut => enchère en cours
        }

        // Condition pour gérer l'affichage des articles via le pseudo selon si le filtre est sur Achat ou Vente
        String utilisateur;
        if ("vente_article".equals(filtre)) {
            utilisateur = pseudo;
        } else {
            utilisateur = null;
        }

        return articleDAO.filtreArticle(nom, categorie, filtre, statutEnchere, utilisateur);
    }

}
