package test;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import carte.CarteAttaque;
import carte.CarteOffensive;
import controllers.ControlCartePlateau;
import controllers.ControlJeu;
import controllers.ControlJoueur;
import joueur.Joueur;
import joueur.Pirate;

public class TestBugDegatsJ2versJ1 {

    @Test
    public void testAttaqueJ2versJ1() {
        // Initialisation
        ControlJeu controlJeu = new ControlJeu();
        Pirate pirate1 = new Pirate("AnneBonny");
        Pirate pirate2 = new Pirate("BarbeNoire");
        
        controlJeu.initialiserJeu(pirate1, pirate2);
        
        ControlJoueur controlJoueur1 = controlJeu.getJoueur(0);
        ControlJoueur controlJoueur2 = controlJeu.getJoueur(1);
        
        Joueur joueur1 = controlJoueur1.getJoueur();
        Joueur joueur2 = controlJoueur2.getJoueur();
        
        // Points de vie initiaux
        joueur1.setVie(5);
        joueur2.setVie(5);
        
        ControlCartePlateau cCartePlateau = controlJeu.getControlCartePlateau();
        
        // Carte d'attaque du joueur 2
        CarteOffensive attaqueJ2 = new CarteAttaque("Canon", "Un puissant canon", 2, 2, 0);
        
        //ajout des cartes à la mains des joueurs, sinon jouerCarte renvoie false et ne place pas la carte dans la zoneOffensive
        controlJoueur2.getJoueur().ajouterCarte(attaqueJ2);
        
        controlJoueur2.jouerCarte(attaqueJ2);

        
        // Application des effets
        cCartePlateau.appliquerEffetCarte();
        
        // Vérifications
        assertEquals(3, joueur1.getPointsDeVie(), "Le joueur 1 devrait avoir perdu 2 points de vie");
        assertEquals(5, joueur2.getPointsDeVie(), "Le joueur 2 ne devrait pas avoir perdu de points de vie");
    }

    @Test
    public void testPartieComplete() {
        // Initialisation
    	ControlJeu controlJeu = new ControlJeu();
        Pirate pirate1 = new Pirate("AnneBonny");
        Pirate pirate2 = new Pirate("BarbeNoire");
         
        controlJeu.initialiserJeu(pirate1, pirate2);
         
        ControlJoueur controlJoueur1 = controlJeu.getJoueur(0);
        ControlJoueur controlJoueur2 = controlJeu.getJoueur(1);
         
        Joueur joueur1 = controlJoueur1.getJoueur();
        Joueur joueur2 = controlJoueur2.getJoueur();
        
        joueur1.setVie(5);
        joueur2.setVie(5);
        
        ControlCartePlateau cCartePlateau = controlJeu.getControlCartePlateau();
        
        // Tour 1: attaques des deux côtés
        CarteOffensive attaqueJ1 = new CarteAttaque("Sabre", "Un sabre tranchant", 2, 1, 0);
        CarteOffensive attaqueJ2 = new CarteAttaque("Pistolet", "Un pistolet précis", 2, 2, 0);
        
        //ajout des cartes à la mains des joueurs, sinon jouerCarte renvoie false et ne place pas la carte dans la zoneOffensive
        controlJoueur1.getJoueur().ajouterCarte(attaqueJ1);
        controlJoueur2.getJoueur().ajouterCarte(attaqueJ2);
        
    	controlJoueur1.jouerCarte(attaqueJ1);
    	controlJoueur2.jouerCarte(attaqueJ2);
    	
    	assertEquals(false, cCartePlateau.getZoneJoueur1().getZoneOffensive().getCartesOffensives().isEmpty(), "Le joueur 1 a une zone de carte offensive vide");
    	assertEquals(false, cCartePlateau.getZoneJoueur2().getZoneOffensive().getCartesOffensives().isEmpty(), "Le joueur 2 a une zone de carte offensive vide");

        
        cCartePlateau.appliquerEffetCarte();
        
        // Vérifications après tour 1
        assertEquals(3, joueur1.getPointsDeVie(), "Le joueur 1 devrait avoir 3 points de vie après le tour 1");
        assertEquals(4, joueur2.getPointsDeVie(), "Le joueur 2 devrait avoir 4 points de vie après le tour 1");
        
        // Nettoyage pour le tour 2
        cCartePlateau.defausserCartesPlateau();
        
        // Tour 2: attaque du joueur 2 uniquement
        CarteOffensive attaqueJ2Tour2 = new CarteAttaque("Canon", "Un puissant canon", 2, 3, 0);
        
        //ajout des cartes à la mains des joueurs, sinon jouerCarte renvoie false et ne place pas la carte dans la zoneOffensive
        controlJoueur2.getJoueur().ajouterCarte(attaqueJ2Tour2);
        
        controlJoueur2.jouerCarte(attaqueJ2Tour2);
        
        
        cCartePlateau.appliquerEffetCarte();
        
        // Vérifications après tour 2
        assertEquals(0, joueur1.getPointsDeVie(), "Le joueur 1 devrait avoir 0 points de vie après le tour 2");
        assertEquals(4, joueur2.getPointsDeVie(), "Le joueur 2 devrait toujours avoir 4 points de vie après le tour 2");
    }
}