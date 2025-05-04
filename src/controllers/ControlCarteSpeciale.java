package controllers;

import carte.CarteCoupSpecial;

public class ControlCarteSpeciale {
    
    private ControlJoueur joueur1;
    private ControlJoueur joueur2;
    
    public ControlCarteSpeciale(ControlJoueur joueur1, ControlJoueur joueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
    }

    /**
     * Active une carte spéciale
     */
    public void activerCarteSpeciale(CarteCoupSpecial carte) {
        if (carte != null && !carte.estJouee()) {
            carte.setEstJouee(true);
            System.out.println("Carte spéciale activée: " + carte.getNomCarte());
        }
    }
    
    /**
     * Active l'effet spécial d'une carte
     */
    public void activerEffetSpecial(CarteCoupSpecial carte, ControlJoueur joueurActif, ControlJoueur joueurAdverse) {
        // Logique pour activer l'effet spécial de la carte
        if (carte != null) {
            System.out.println("Carte spéciale activée: " + carte.getNomCarte());
            // Implémenter la logique spécifique de l'effet ici
            // Par exemple:
            // if (carte.getEffet().equals("SOIN")) {
            //     joueurActif.gagnerPointsDeVie(carte.getValeur());
            // } else if (carte.getEffet().equals("ATTAQUE")) {
            //     joueurAdverse.perdrePointsDeVie(carte.getValeur());
            // }
        }
    }
    
    /**
     * Applique les effets des cartes spéciales jouées
     */
    public void appliquerEffetsCartes() {
        System.out.println("Application des effets des cartes spéciales");
        // Logique pour appliquer les effets des cartes spéciales
    }
    
    /**
     * Défausse les cartes utilisées
     */
    public void defausserCartes() {
        System.out.println("Défausse des cartes spéciales utilisées");
        // Logique pour défausser les cartes utilisées
    }
}