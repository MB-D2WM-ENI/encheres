package fr.eni.ecole.projet.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UtilisateursController {


    //Afficher la page d'inscription d'un nouvel utilisateur
    @GetMapping("/inscription")
    public String creerUtilisateur() {
        return "view-suscribe";
    }


    // Récupérer les informations de l'utilisateur lors de son inscription
    @PostMapping("/inscription")
    public String creerUtilisateur(Model model) {
        return "view-suscribe";
    }
}
