package fr.eni.ecole.projet.encheres.controller;

import fr.eni.ecole.projet.encheres.bll.ArticleService;
import fr.eni.ecole.projet.encheres.bo.ArticleAVendre;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String afficherArticlesVentes(Model model) {
        List<ArticleAVendre> lstArticles = articleService.getArticles();
        model.addAttribute("articles", lstArticles);
        return "index";
    }

}
