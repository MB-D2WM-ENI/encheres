package fr.eni.ecole.projet.encheres.dal;

import fr.eni.ecole.projet.encheres.bo.Adresse;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

@Repository
public class AdresseDAOImpl implements AdresseDAO {


    // Requêtes
    private final String FIND_BY_NO_ADRESSE = "SELECT no_adresse FROM ADRESSES WHERE no_adresse = no_adresse";
    private final String INSERT_ADRESSE = "INSERT INTO ADRESSES(rue, code_postal, ville, adresse_eni) "
            + " VALUES(:rue, :code_postal, :ville, :adresse_eni)";
    private final String UPDATE_ADRESSE = "UPDATE ADRESSES SET rue =:rue, code_postal =:code_postal, ville =:ville WHERE no_adresse =:no_adresse";


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public Adresse read(long noAdresse) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_adresse", noAdresse);
        return namedParameterJdbcTemplate.queryForObject(FIND_BY_NO_ADRESSE, namedParameters, new AdresseRowMapper());
    }

    // Méthode qui créé l'adresse de l'utilisateur via son email
    @Override
    public void create(Adresse adresse) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("rue", adresse.getNom());
        namedParameters.addValue("code_postal", adresse.getCodePostal());
        namedParameters.addValue("ville", adresse.getVille());
        namedParameters.addValue("adresse_eni", false);
        namedParameterJdbcTemplate.update(INSERT_ADRESSE, namedParameters, keyHolder);

        if (keyHolder != null && keyHolder.getKey() != null) {
            // Mise à jour de l'identifiant de l'adresse auto-généré par la base
            adresse.setId(keyHolder.getKey().longValue());
        }
    }

    // Méthode qui met à jour l'adresse d'un utilisateur
    @Override
    public void update(Adresse adresse) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("no_adresse", adresse.getId());
        namedParameters.addValue("rue", adresse.getNom());
        namedParameters.addValue("code_postal", adresse.getCodePostal());
        namedParameters.addValue("ville", adresse.getVille());
        namedParameterJdbcTemplate.update(UPDATE_ADRESSE, namedParameters);
    }

    // RowMapper pour gérer le nom des colonnes
    class AdresseRowMapper implements RowMapper<Adresse> {
        @Override
        public Adresse mapRow(ResultSet rs, int rowNum) throws SQLException {
            Adresse a = new Adresse();
            a.setId(rs.getLong("no_adresse"));
            a.setNom(rs.getString("rue"));
            a.setCodePostal(rs.getString("code_postal"));
            a.setVille(rs.getString("ville"));
            return a;
        }
    }




}
