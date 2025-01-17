package fr.eni.ecole.projet.encheres.exceptions;

import java.util.ArrayList;
import java.util.List;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private List<String> clefsExternalisation;


    public BusinessException() {
        super();
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }


    public List<String> getClefsExternalisation() {
        return clefsExternalisation;
    }

    /**
     * Permet d'ajouter une clef d'erreur
     *
     * @param clef
     *
     * @comportement initialiste la liste si besoin
     *
     * */
    public void add(String clef){
        if(clefsExternalisation == null){
            clefsExternalisation = new ArrayList<>();
        }
        clefsExternalisation.add(clef);
    }

    /**
     * @return permet de confirmer si des erreurs ont été chargées
     * */
    public boolean isValid(){
        return clefsExternalisation == null || clefsExternalisation.isEmpty();
    }
}
