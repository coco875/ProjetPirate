package carte;

import java.util.ArrayList;
import java.util.List;

public class Marche {
    private List<Carte> cartesDisponibles;

    public Marche() {
        this.cartesDisponibles = new ArrayList<>();
    }

    public void ajouterCarte(Carte carte) {
        if (carte != null) {
            this.cartesDisponibles.add(carte);
        }
    }

    public List<Carte> getCartesDisponibles() {
        return this.cartesDisponibles;
    }

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