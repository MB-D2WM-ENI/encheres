package fr.eni.ecole.projet.encheres.controller;

import fr.eni.ecole.projet.encheres.bll.UtilisateurService;
import fr.eni.ecole.projet.encheres.bo.Utilisateur;
import fr.eni.ecole.projet.encheres.exceptions.BusinessException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@RequestMapping("/")
public class UtilisateursController {

    private static final Logger log = LoggerFactory.getLogger(UtilisateursController.class);
    private final UtilisateurService utilisateurService;

    public UtilisateursController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    //Afficher la page d'inscription d'un nouvel utilisateur
    @GetMapping("/inscription")
    public String creerUtilisateur(Model model) {

        Utilisateur utilisateur = new Utilisateur();
        model.addAttribute("utilisateur", utilisateur);
        return "view-inscription";
    }


    // Récupérer les informations de l'utilisateur lors de son inscription
    @PostMapping("/inscription")
    public String creerUtilisateur(@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur,
                                   BindingResult bindingResult) {

        // S'il y a des erreurs, on retourne sur la page d'inscription avec les erreurs affichées à l'utilisateur
        if (bindingResult.hasErrors()) {
            System.out.println("il y a des erreurs");
            return "view-inscription";
        } else {
            // Sinon on ajoute l'utilisateur à la table UTILISATEURS
            try {
                utilisateurService.creerUtilisateur(utilisateur);
                return "redirect:/";
            } catch (BusinessException e) {
                // On affiche les messages d'erreur - On injecte dans le contexte BindingResult
                e.getClefsExternalisation().forEach(key -> {
                    ObjectError error = new ObjectError("globalError", key);
                    bindingResult.addError(error);
                });
                return "view-inscription";
            }
        }
    }

    // Affichage du profil de l'utilisateur
    @GetMapping("/profilUtilisateur")
    public String afficherProfilUtilisateur(Model model, Principal principal) {

        final String pseudoUtilisateur = principal.getName();
        Utilisateur utilisateur = utilisateurService.findByPseudo(pseudoUtilisateur);

        if (utilisateur != null) {
            model.addAttribute("utilisateur", utilisateur);
            return "view-profil-utilisateur";
        } else
            System.out.println("Utilisateur inconnu!!");
        return "redirect:/";
    }

    // Dirige vers la page de modification de profil
    @GetMapping("/modifierUtilisateur")
    public String modifierProfilUtilisateur(Model model, Principal principal) {

        final String pseudoUtilisateur = principal.getName();

        Utilisateur utilisateurExistant = utilisateurService.readUserAdresseByPseudo(pseudoUtilisateur);
        if (utilisateurExistant != null) {
            model.addAttribute("utilisateur", utilisateurExistant);
            return "view-modifier-profil";
        } else {
            System.out.println("Utilisateur inconnu!!");
            return "redirect:/";
        }
    }

    // Modifie les informations d'un utilisateur
    @PostMapping("/modifierUtilisateur")
    public String modifierProfilUtilisateur(@Valid @ModelAttribute("utilisateur") Utilisateur utilisateur,
                                            BindingResult bindingResult,
                                            @RequestParam("action") String action) {


        // Si erreurs les retourner et rediriger sur la page de modification du profil
        // Sinon try update des données utilisateur et/ou son adresse
        // Catch remonter les erreurs de la BLL et retourner à la page de modification du profil
        switch (action) {
            case "modifier":
                if (bindingResult.hasErrors()) {
                    System.out.println("il y a des erreurs");
                    return "view-modifier-profil";
                } else {
                    try {
                        utilisateurService.modifierProfil(utilisateur);
                        return "redirect:/profilUtilisateur";
                    } catch (BusinessException e) {
                        // On affiche les messages d'erreur - On injecte dans le contexte BindingResult
                        e.getClefsExternalisation().forEach(key -> {
                            ObjectError error = new ObjectError("globalError", key);
                            bindingResult.addError(error);
                        });
                        return "view-modifier-profil";
                    }
                }

            case "supprimer":
                if (bindingResult.hasErrors()) {
                    System.out.println("il y a des erreurs");
                    return "view-modifier-profil";
                } else {
                    try {
                        // TODO Logique pour supprimer l'utilisateur
                        //utilisateurService.deleteUtilisateur(utilisateur.getId());
                        return "redirect:/profilUtilisateur";
                    } catch (BusinessException e) {
                        // On affiche les messages d'erreur - On injecte dans le contexte BindingResult
                        e.getClefsExternalisation().forEach(key -> {
                            ObjectError error = new ObjectError("globalError", key);
                            bindingResult.addError(error);
                        });
                        return "view-modifier-profil";
                    }
                }

            default:
                return "redirect:/profilUtilisateur";
        }
    }


    // Dirige vers la page de modification du mot de passe
    @GetMapping("/modifierMotDePasse")
    public String modifierMotDePasse(Model model, Principal principal) {
        final String pseudoUtilisateur = principal.getName();

        // TODO Ajouter une requête pour vérifier le mot de passe

        Utilisateur utilisateurExistant = utilisateurService.findByPseudo(pseudoUtilisateur);
        if (utilisateurExistant != null) {
            model.addAttribute("utilisateur", utilisateurExistant);
            return "view-modifier-mot-de-passe";
        } else {
            System.out.println("Utilisateur inconnu!!");
            return "redirect:/";
        }
    }

    // Dirige vers la page pour créer un article
    @GetMapping("vendreArticle")
    public String vendreArticle(Model model, Principal principal) {
        final String pseudoUtilisateur = principal.getName();
        Utilisateur utilisateurExistant = utilisateurService.findByPseudo(pseudoUtilisateur);
        if (utilisateurExistant != null) {
            model.addAttribute("utilisateur", utilisateurExistant);
            return "view-vendre-article";
        } else {
            System.out.println("Utilisateur inconnu!!");
            return "redirect:/";
        }
    }

}

