package carte;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente le marché où les joueurs peuvent acheter des cartes
 */
public class Marche {
    private List<Carte> cartesDisponibles;

    /**
     * Constructeur du marché
     */
    public Marche() {
        this.cartesDisponibles = new ArrayList<>();
    }

    /**
     * Ajoute une carte au marché
     */
    public void ajouterCarte(Carte carte) {
        if (carte != null) {
            this.cartesDisponibles.add(carte);
        }
    }

    /**
     * Récupère la liste des cartes disponibles à l'achat
     */
    public List<Carte> getCartesDisponibles() {
        return this.cartesDisponibles;
    }

    /**
     * Affiche les cartes disponibles au marché
     */
    public void afficherMarché() {
        System.out.println("Marché: Liste des cartes disponibles.");
        if (cartesDisponibles.isEmpty()) {
            System.out.println("Le marché est vide.");
        } else {
            for (int i = 0; i < cartesDisponibles.size(); i++) {
                Carte carte = cartesDisponibles.get(i);
                System.out.println((i + 1) + ". " + carte.getNomCarte() + " (Coût: " + carte.getCout() + ")");
            }
        }
    }
}