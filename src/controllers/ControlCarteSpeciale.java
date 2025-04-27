package controllers;

import carte.CarteCoupSpecial;

public class ControlCarteSpeciale {

    public void activerCarteSpeciale(CarteCoupSpecial carte) {
        if (carte != null && !carte.estJouee()) {
            carte.setEstJouee(true);
            System.out.println("Carte spéciale activée: " + carte.getNom());
        }
    }
}