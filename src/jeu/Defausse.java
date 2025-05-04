package jeu;

import java.util.ArrayList;
import java.util.List;
import carte.Carte;

public class Defausse {
    private List<Carte> cartes;

    public Defausse() {
        this.cartes = new ArrayList<>();
    }

    /**
     * @brief Ajoute une carte à la défausse.
     * @param carte La carte à ajouter.
     */
    public void ajouterCarte(Carte carte) {
        if (carte != null) {
            this.cartes.add(carte);
        }
    }

    /**
     * @brief Retourne la liste des cartes dans la défausse.
     * @return La liste des cartes.
     */
    public List<Carte> getCartes() {
        return cartes;
    }

    /**
     * @brief Vide la défausse.
     */
    public void vider() {
        this.cartes.clear();
    }
}