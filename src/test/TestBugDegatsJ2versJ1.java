package test;

/*
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import carte.CarteOffensive;
import controllers.ControlCartePlateau;
import controllers.ControlJeu;
import joueur.Joueur;

public class TestBugDegatsJ2versJ1 {

    @Test
    public void testAttaqueJ2versJ1() {
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
        
        // Application des effets
        cCartePlateau.appliquerEffetsCartesOffensives();
        
        // Vérifications
        assertEquals(3, joueur1.getPointsDeVie(), "Le joueur 1 devrait avoir perdu 2 points de vie");
        assertEquals(5, joueur2.getPointsDeVie(), "Le joueur 2 ne devrait pas avoir perdu de points de vie");
    }

    @Test
    public void testPartieComplete() {
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
        
        cCartePlateau.appliquerEffetsCartesOffensives();
        
        // Vérifications après tour 1
        assertEquals(3, joueur1.getPointsDeVie(), "Le joueur 1 devrait avoir 3 points de vie après le tour 1");
        assertEquals(4, joueur2.getPointsDeVie(), "Le joueur 2 devrait avoir 4 points de vie après le tour 1");
        
        // Nettoyage pour le tour 2
        cCartePlateau.defausserCartesPlateau();
        
        // Tour 2: attaque du joueur 2 uniquement
        CarteOffensive attaqueJ2Tour2 = new CarteOffensive("Canon", "Un puissant canon", 3, 0, 
                                                         CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        cCartePlateau.ajouterCarteOffensiveJ2(attaqueJ2Tour2);
        
        cCartePlateau.appliquerEffetsCartesOffensives();
        
        // Vérifications après tour 2
        assertEquals(0, joueur1.getPointsDeVie(), "Le joueur 1 devrait avoir 0 points de vie après le tour 2");
        assertEquals(4, joueur2.getPointsDeVie(), "Le joueur 2 devrait toujours avoir 4 points de vie après le tour 2");
    }
}*/