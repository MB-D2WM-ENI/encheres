package fr.eni.ecole.projet.encheres.controller.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({ "utilisateurConnecte" })
public class loginController {

    @GetMapping("/login")
    String login() {
        return "login";
    }

}
