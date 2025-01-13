package fr.eni.ecole.projet.encheres.dal;

import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ArticleDAOImpl implements ArticleDAO {

    private final String FIND_VENTE = "SELECT a.nom_article, a.date_fin_encheres, a.statut_enchere, a.id_utilisateur, COALESCE(a.prix_vente, a.prix_initial) AS prix_affiche FROM ARTICLES_A_VENDRE a INNER JOIN UTILISATEURS u ON a.id_utilisateur = u.pseudo WHERE a.statut_enchere = 1";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<ArticleAVendre> findArticleVente() {
        return namedParameterJdbcTemplate.query(FIND_VENTE, new ArticleRowMapper());
    }

    static class ArticleRowMapper implements RowMapper<ArticleAVendre> {

        @Override
        public ArticleAVendre mapRow(ResultSet rs, int rowNum) throws SQLException {

            ArticleAVendre aav = new ArticleAVendre();
            aav.setNom(rs.getString("nom_article"));
            aav.setDateFinEncheres(rs.getDate("date_fin_encheres").toLocalDate());
            aav.setStatut(rs.getInt("statut_enchere"));
            aav.setPrixInitial(rs.getInt("prix_affiche"));
            aav.setPrixVente(rs.getInt("prix_affiche"));

            // Association pour Utilisateur
            Utilisateur us = new Utilisateur();
            us.setPseudo(rs.getString("id_utilisateur"));
            aav.setVendeur(us);
            return aav;
        }
    }

}
