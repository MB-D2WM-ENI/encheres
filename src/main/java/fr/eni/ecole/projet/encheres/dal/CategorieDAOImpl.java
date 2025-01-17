package fr.eni.ecole.projet.encheres.dal;

import fr.eni.ecole.projet.encheres.bo.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CategorieDAOImpl implements CategorieDAO {

    private final String FIND_ALL = "SELECT no_categorie, libelle FROM CATEGORIES";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<Categorie> findAll() {
        return jdbcTemplate.query(FIND_ALL, new CategorieRowMapper());
    }

    static class CategorieRowMapper implements RowMapper<Categorie> {

        @Override
        public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException {

            Categorie c = new Categorie();
            c.setId(rs.getInt("no_categorie"));
            c.setLibelle(rs.getString("libelle"));
            return c;
        }
    }
}
