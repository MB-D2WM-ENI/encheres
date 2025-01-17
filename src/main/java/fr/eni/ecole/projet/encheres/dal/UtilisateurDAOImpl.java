package fr.eni.ecole.projet.encheres.dal;

import fr.eni.ecole.projet.encheres.bo.Adresse;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

    // Requêtes
    private final String FIND_BY_PSEUDO = "SELECT pseudo, nom, prenom, email, telephone FROM UTILISATEURS WHERE pseudo = :pseudo";
    private final String FIND_BY_PSEUDO_UTILISATEUR_ADRESSE = "SELECT a.no_adresse, u.pseudo, u.nom, u.prenom, u.email, u.telephone, a.rue, a.code_postal, a.ville, u.credit FROM UTILISATEURS u INNER JOIN ADRESSES a ON a.no_adresse = u.no_adresse WHERE u.pseudo = :pseudo";
    private final String INSERT_UTILISATEUR = "INSERT INTO UTILISATEURS(pseudo, nom, prenom, email, telephone, mot_de_passe, no_adresse ) VALUES (:pseudo, :nom, :prenom, :email, :telephone, :motDePasse, :noAdresse)";
    private final String UPDATE_UTILISATEUR = "UPDATE UTILISATEURS SET nom =:nom, prenom =:prenom, email =:email, telephone =:telephone, credit = :credit WHERE pseudo =:pseudo";

    private static final String COUNT_EMAIL = "SELECT COUNT(email) FROM UTILISATEURS WHERE email = :email";
    private static final String COUNT_PSEUDO = "SELECT COUNT(pseudo) FROM UTILISATEURS WHERE pseudo = :pseudo";

    private static final Logger log = LoggerFactory.getLogger(UtilisateurDAO.class);


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // Retourne un utilisateur à partir de son pseudo
    @Override
    public Utilisateur read(String pseudo) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", pseudo);
        return namedParameterJdbcTemplate.queryForObject(FIND_BY_PSEUDO, namedParameters, new BeanPropertyRowMapper<>(Utilisateur.class));
    }

    // Retourne les informations d'un utilisateur avec l'adresse
    @Override
    public Utilisateur readUserAdresse(String pseudo) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", pseudo);
        return namedParameterJdbcTemplate.queryForObject(FIND_BY_PSEUDO_UTILISATEUR_ADRESSE,namedParameters, new UtilisateurRowMapper());
    }

    // Création d'un utilisateur
    @Override
    public void create(Utilisateur utilisateur) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", utilisateur.getPseudo());
        namedParameters.addValue("nom", utilisateur.getNom());
        namedParameters.addValue("prenom", utilisateur.getPrenom());
        namedParameters.addValue("email", utilisateur.getEmail());
        namedParameters.addValue("telephone", utilisateur.getTelephone());
        namedParameters.addValue("motDePasse", utilisateur.getMotDePasse());
        namedParameters.addValue("noAdresse", utilisateur.getAdresse().getId());
        namedParameterJdbcTemplate.update(INSERT_UTILISATEUR, namedParameters);
    }


    // Mise à jour du profil utilisateur
    @Override
    public void update(Utilisateur utilisateur) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", utilisateur.getPseudo()); // Correction ici
        namedParameters.addValue("nom", utilisateur.getNom());
        namedParameters.addValue("prenom", utilisateur.getPrenom());
        namedParameters.addValue("email", utilisateur.getEmail());
        namedParameters.addValue("telephone", utilisateur.getTelephone());
        namedParameters.addValue("credit", utilisateur.getCredit());
        namedParameterJdbcTemplate.update(UPDATE_UTILISATEUR, namedParameters);
    }


    // L'email doit être unique
    @Override
    public int uniqueEmail(String email) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("email", email);
        return namedParameterJdbcTemplate.queryForObject(COUNT_EMAIL, namedParameters, Integer.class);
    }


    // Le pseudo doit être unique
    @Override
    public int uniquePseudo(String pseudo) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pseudo", pseudo);
        return namedParameterJdbcTemplate.queryForObject(COUNT_PSEUDO, namedParameters, Integer.class);
    }


    // RowMapper pour la lecture de l'utilisateur et de son adresse - Adresse est associé à Utilisateur
    class UtilisateurRowMapper implements RowMapper<Utilisateur> {
        @Override
        public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
            Utilisateur u = new Utilisateur();

            u.setPseudo(rs.getString("pseudo"));
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setEmail(rs.getString("email"));
            u.setTelephone(rs.getString("telephone"));

            Adresse a = new Adresse();
            a.setNom(rs.getString("rue"));
            a.setCodePostal(rs.getString("code_postal"));
            a.setVille(rs.getString("ville"));
            a.setId(rs.getLong("no_adresse"));

            u.setAdresse(a);
            return u;
        }
    }
}
