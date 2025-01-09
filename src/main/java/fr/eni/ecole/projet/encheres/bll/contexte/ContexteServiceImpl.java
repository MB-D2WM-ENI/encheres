package fr.eni.ecole.projet.encheres.bll.contexte;

import fr.eni.ecole.projet.encheres.bo.Utilisateur;
import fr.eni.ecole.projet.encheres.dal.UtilisateurDAO;
import org.springframework.stereotype.Service;

@Service
public class ContexteServiceImpl implements ContexteService {
    private UtilisateurDAO utilisateurDAO;

    public ContexteServiceImpl(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }

    @Override
    public Utilisateur charger(String email) {
        return utilisateurDAO.read(email);
    }
}
