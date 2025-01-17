package fr.eni.ecole.projet.encheres.controller;

import fr.eni.ecole.projet.encheres.bll.ArticleService;
import fr.eni.ecole.projet.encheres.bll.CategorieService;
import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;
import fr.eni.ecole.projet.encheres.bo.Categorie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/")
@SessionAttributes({"categoriesSession"})
public class ArticleController {

    private final ArticleService articleService;
    private final CategorieService categorieService;

    public ArticleController(ArticleService articleService, CategorieService categorieService) {
        this.articleService = articleService;
        this.categorieService = categorieService;
    }

    @GetMapping
    public String afficherArticlesVentes(Model model) {
        List<ArticleAVendre> lstArticles = articleService.getArticles();
        model.addAttribute("articles", lstArticles);
        return "index";
    }

    @ModelAttribute("categoriesSession")
    public List<Categorie> chargerCategories() {
        return categorieService.getCategories();
    }

    @PostMapping("/recherche")
    public String recupInfoForm(
            @RequestParam(name = "recherche", required = false, defaultValue = "") String nom,
            @RequestParam(name = "categorie", required = false, defaultValue = "0") int categorie,
            @RequestParam(name = "filtre", required = false, defaultValue = "achat_article") String filtre,
            @RequestParam(name = "selection", required = false, defaultValue = "2") int selection,
            Authentication auth,
            Model model) {

        // Récupérer le nom de l'utilisateur connecté
        String pseudo = auth.getName();

     /* === Afficher les infos récupérées ===
        System.out.println("Nom : " + nom);
        System.out.println("Catégorie : " + categorie);
        System.out.println("Catégorie : " + filtre);
        System.out.println("Catégorie : " + selection);
        System.out.println("Catégorie : " + pseudo);
     */

        List<ArticleAVendre> art = articleService.findArticle(nom, categorie, filtre, selection, pseudo);

        model.addAttribute("articles", art);

        return "index";
    }

}
