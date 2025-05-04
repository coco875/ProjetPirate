package jeu;

import java.util.ArrayList;
import java.util.List;
import carte.Carte;

/**
 * Représente la défausse de cartes du jeu
 */
public class Defausse {
    private List<Carte> cartes;

    public Defausse() {
        this.cartes = new ArrayList<>();
    }

    /**
     * Ajoute une carte à la défausse
     */
    public void ajouterCarte(Carte carte) {
        if (carte != null) {
            this.cartes.add(carte);
        }
    }

    /**
     * Retourne la liste des cartes dans la défausse
     */
    public List<Carte> getCartes() {
        return cartes;
    }

    /**
     * Vide la défausse
     */
    public void vider() {
        this.cartes.clear();
    }
}