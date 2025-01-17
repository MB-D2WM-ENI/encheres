package fr.eni.ecole.projet.encheres.bll;

import fr.eni.ecole.projet.encheres.bo.Categorie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategorieService {

    List<Categorie> getCategories();

}
