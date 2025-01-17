package fr.eni.ecole.projet.encheres.dal;

import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;
import fr.eni.ecole.projet.encheres.bo.Categorie;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

    private final String FIND_VENTE = "SELECT a.nom_article, a.date_fin_encheres, a.statut_enchere, a.id_utilisateur, COALESCE(a.prix_vente, a.prix_initial) AS prix_affiche, a.no_categorie FROM ARTICLES_A_VENDRE a INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.pseudo INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie WHERE a.statut_enchere = 1";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<ArticleAVendre> findArticleVente() {
        return namedParameterJdbcTemplate.query(FIND_VENTE, new ArticleRowMapper());
    }

    @Override
    public List<ArticleAVendre> filtreArticle(String nom, int categorie, String filtre, int statutEnchere, String pseudo) {

        // Utilisation d'une variable locale plutôt qu'une constante, car la requête va être modifiée !
        String recherche = "SELECT a.nom_article, a.date_fin_encheres, a.statut_enchere, a.id_utilisateur, COALESCE(a.prix_vente, a.prix_initial) AS prix_affiche, a.no_categorie FROM ARTICLES_A_VENDRE a INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.pseudo INNER JOIN CATEGORIES c ON a.no_categorie = c.no_categorie WHERE a.statut_enchere = :statut_enchere";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("statut_enchere", statutEnchere);

        // Recherche par nom (insensible à la casse)
        if (nom != null && !nom.isEmpty()) {
            recherche += " AND LOWER(a.nom_article) LIKE LOWER(:nom_article)";
            params.addValue("nom_article", "%" + nom + "%");
        }

        // Recherche par catégorie
        if (categorie > 0) {
            recherche += " AND a.no_categorie = :no_categorie";
            params.addValue("no_categorie", categorie);
        }

        // Filtrer par utilisateur uniquement si pseudo est non nul (pour bouton "Ventes")
        if (pseudo != null) {
            recherche += " AND a.id_utilisateur = :id_utilisateur";
            params.addValue("id_utilisateur", pseudo);
        }

        /* === Afficher les infos générées ===
        System.out.println("Requête SQL générée : " + recherche);
        System.out.println("Params fournis : " + params);
        */

        return namedParameterJdbcTemplate.query(recherche, params, new ArticleRowMapper());
    }

    class ArticleRowMapper implements RowMapper<ArticleAVendre> {

        @Override
        public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {

            ArticleAVendre aav = new ArticleAVendre();

            aav.setNom(rs.getString("nom_article"));
            aav.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
            aav.setStatut(rs.getInt("statut_enchere"));
            aav.setPrixInitial(rs.getInt("prix_affiche"));
            aav.setPrixVente(rs.getInt("prix_affiche"));

            // Association pour Categorie
            Categorie ca = new Categorie();
            ca.setId(rs.getInt("no_categorie"));
            aav.setCategorie(ca);

            // Association pour Utilisateur
            Utilisateur us = new Utilisateur();
            us.setPseudo(rs.getString("id_utilisateur"));
            aav.setVendeur(us);

            return aav;
        }
    }

}
