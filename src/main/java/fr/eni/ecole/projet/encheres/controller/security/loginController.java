package fr.eni.ecole.projet.encheres.controller.security;

import fr.eni.ecole.projet.encheres.bll.contexte.ContexteService;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;

@Controller
@SessionAttributes({ "utilisateurConnecte" })
public class loginController {

    private ContexteService service;

    public loginController(ContexteService service) {
        this.service = service;
    }

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/session")
    String chargerUtilisateurEnSession(@ModelAttribute("utilisateurConnecte") Utilisateur utilisateurConnecte, Principal principal) {
        String email = principal.getName();
        Utilisateur aCharger = service.charger(email);
        if (aCharger != null) {
            utilisateurConnecte.setPseudo(aCharger.getPseudo());
            utilisateurConnecte.setNom(aCharger.getNom());
            utilisateurConnecte.setPrenom(aCharger.getPrenom());
            utilisateurConnecte.setEmail(aCharger.getEmail());
            utilisateurConnecte.setTelephone(aCharger.getTelephone());
            utilisateurConnecte.setMotDePasse(aCharger.getMotDePasse());
            utilisateurConnecte.setCredit(aCharger.getCredit());
            utilisateurConnecte.setAdmin(aCharger.isAdmin());

        } else {
            utilisateurConnecte.setPseudo(null);
            utilisateurConnecte.setNom(null);
            utilisateurConnecte.setPrenom(null);
            utilisateurConnecte.setEmail(null);
            utilisateurConnecte.setTelephone(null);
            utilisateurConnecte.setMotDePasse(null);
            utilisateurConnecte.setCredit(0);
            utilisateurConnecte.setAdmin(false);

        }
        System.out.println(utilisateurConnecte);

        return "redirect:/";
    }

    // Cette méthode met par défaut un nouveau membre en session
    @ModelAttribute("utilisateurConnecte")
    public Utilisateur utilisateurConnecte() {
        System.out.println("Add Attribut Session");
        return new Utilisateur();
    }

}
