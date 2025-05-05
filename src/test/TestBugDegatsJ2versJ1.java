package test;

import static org.junit.Assert.assertEquals;

import carte.CarteOffensive;
import controllers.ControlCartePlateau;
import controllers.ControlJeu;
import joueur.Joueur;

/**
 * Test vérifiant la correction du bug où le joueur 2 n'infligeait pas 
 * correctement des dégâts au joueur 1
 */
public class TestBugDegatsJ2versJ1 {

    public static void main(String[] args) {
        System.out.println("Test pour vérifier l'application des dégâts du joueur 2 vers le joueur 1");
        
        // Test d'une attaque simple
        testAttaqueJ2versJ1();
        
        // Test de plusieurs attaques
        testPartieComplete();
        
        System.out.println("Tous les tests ont réussi!");
    }

    /**
     * Test simple: le joueur 2 joue une carte d'attaque directe
     */
    private static void testAttaqueJ2versJ1() {
        System.out.println("--- Test d'attaque simple J2 vers J1 ---");
        
        // Initialisation
        ControlJeu controlJeu = new ControlJeu();
        Joueur joueur1 = controlJeu.creerJoueur("Joueur1", "Jack Sparrow");
        Joueur joueur2 = controlJeu.creerJoueur("Joueur2", "Barbe Noire");
        
        // Points de vie initiaux
        joueur1.setVie(5);
        joueur2.setVie(5);
        
        ControlCartePlateau cCartePlateau = controlJeu.getControlCartePlateau();
        
        // Carte d'attaque du joueur 2
        CarteOffensive attaqueJ2 = new CarteOffensive("Canon", "Un puissant canon", 2, 0, 
                                                     CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        cCartePlateau.ajouterCarteOffensiveJ2(attaqueJ2);
        
        System.out.println("Avant attaque: J1 PV=" + joueur1.getPointsDeVie() + ", J2 PV=" + joueur2.getPointsDeVie());
        
        // Application des effets
        cCartePlateau.appliquerEffetsCartesOffensives();
        
        System.out.println("Après attaque: J1 PV=" + joueur1.getPointsDeVie() + ", J2 PV=" + joueur2.getPointsDeVie());
        
        // Vérifications
        assertEquals("Le joueur 1 devrait avoir perdu 2 points de vie", 3, joueur1.getPointsDeVie());
        assertEquals("Le joueur 2 ne devrait pas avoir perdu de points de vie", 5, joueur2.getPointsDeVie());
    }

    /**
     * Test simulant une partie avec des attaques des deux joueurs
     */
    private static void testPartieComplete() {
        System.out.println("--- Test de partie complète avec attaques des deux joueurs ---");
        
        // Initialisation
        ControlJeu controlJeu = new ControlJeu();
        Joueur joueur1 = controlJeu.creerJoueur("Joueur1", "Jack Sparrow");
        Joueur joueur2 = controlJeu.creerJoueur("Joueur2", "Barbe Noire");
        
        joueur1.setVie(5);
        joueur2.setVie(5);
        
        ControlCartePlateau cCartePlateau = controlJeu.getControlCartePlateau();
        
        // Tour 1: attaques des deux côtés
        CarteOffensive attaqueJ1 = new CarteOffensive("Sabre", "Un sabre tranchant", 1, 0, 
                                                    CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        CarteOffensive attaqueJ2 = new CarteOffensive("Pistolet", "Un pistolet précis", 2, 0, 
                                                    CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        
        cCartePlateau.ajouterCarteOffensiveJ1(attaqueJ1);
        cCartePlateau.ajouterCarteOffensiveJ2(attaqueJ2);
        
        System.out.println("Tour 1 - Avant: J1 PV=" + joueur1.getPointsDeVie() + ", J2 PV=" + joueur2.getPointsDeVie());
        
        cCartePlateau.appliquerEffetsCartesOffensives();
        
        System.out.println("Tour 1 - Après: J1 PV=" + joueur1.getPointsDeVie() + ", J2 PV=" + joueur2.getPointsDeVie());
        
        // Vérifications après tour 1
        assertEquals("Le joueur 1 devrait avoir 3 points de vie après le tour 1", 3, joueur1.getPointsDeVie());
        assertEquals("Le joueur 2 devrait avoir 4 points de vie après le tour 1", 4, joueur2.getPointsDeVie());
        
        // Nettoyage pour le tour 2
        cCartePlateau.defausserCartesPlateau();
        
        // Tour 2: attaque du joueur 2 uniquement
        CarteOffensive attaqueJ2Tour2 = new CarteOffensive("Canon", "Un puissant canon", 3, 0, 
                                                         CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        cCartePlateau.ajouterCarteOffensiveJ2(attaqueJ2Tour2);
        
        System.out.println("Tour 2 - Avant: J1 PV=" + joueur1.getPointsDeVie() + ", J2 PV=" + joueur2.getPointsDeVie());
        
        cCartePlateau.appliquerEffetsCartesOffensives();
        
        System.out.println("Tour 2 - Après: J1 PV=" + joueur1.getPointsDeVie() + ", J2 PV=" + joueur2.getPointsDeVie());
        
        // Vérifications après tour 2
        assertEquals("Le joueur 1 devrait avoir 0 points de vie après le tour 2", 0, joueur1.getPointsDeVie());
        assertEquals("Le joueur 2 devrait toujours avoir 4 points de vie après le tour 2", 4, joueur2.getPointsDeVie());
    }
}