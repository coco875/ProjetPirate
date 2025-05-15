package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import carte.*;
import controllers.*;
import jeu.*;
import joueur.*;

public class TestGainOr {

    private ControlJeu controlJeu;
    private Joueur joueur1, joueur2;
    private ControlCartePlateau controlCartePlateau;
    private ControlZoneJoueur zoneJoueur1, zoneJoueur2;
    
    @BeforeEach
    public void setUp() {
        System.out.println("\n==== Début du test de gain d'or ====");
        controlJeu = new ControlJeu();
        
        // Créer les pirates
        Pirate pirate1 = new Pirate("Capitaine Trésor");
        Pirate pirate2 = new Pirate("Adversaire");
        
        // Initialiser le jeu avec les pirates
        controlJeu.initialiserJeu(pirate1, pirate2);
        
        // Récupérer les joueurs
        joueur1 = controlJeu.getJoueur(0).getJoueur();
        joueur2 = controlJeu.getJoueur(1).getJoueur();
        
        // Récupérer les contrôleurs
        controlCartePlateau = controlJeu.getControlCartePlateau();
        zoneJoueur1 = controlJeu.getControlZoneJoueur1();
        zoneJoueur2 = controlJeu.getControlZoneJoueur2();
        
        // Initialiser les valeurs d'or des joueurs
        joueur1.setOr(5);
        joueur2.setOr(5);
        
        System.out.println("Joueurs créés avec 5 or chacun");
    }
    
    @AfterEach
    public void tearDown() {
        System.out.println("==== Fin du test de gain d'or ====");
    }

    @Test
    public void testGainOrBase() {
        System.out.println("\n--- Test gain d'or basique ---");
        
        // Créer une carte trésor avec 3 or de gain
        CarteTresor carteTresor = new CarteTresor("Coffre d'or", "Un coffre rempli d'or", 10, 3);
        
        // Ajouter la carte à la zone stratégique du joueur 1
        zoneJoueur1.ajouterCarteStrategique(carteTresor);
        
        // Vérifier l'or initial
        assertEquals(5, joueur1.getOr(), "L'or initial du joueur 1 devrait être 5");
        
        // Appliquer l'effet de la carte
        controlCartePlateau.appliquerEffetCarte();
        
        // Vérifier le gain d'or
        assertEquals(8, joueur1.getOr(), "Après application de l'effet, l'or du joueur 1 devrait être 8");
        
        System.out.println("Or initial: 5, Or après gain: " + joueur1.getOr() + 
                          ", OrGagne=" + carteTresor.effetCarte().orGagne + " ✓");
    }
    
    @Test
    public void testGainSimultane() {
        System.out.println("\n--- Test gains d'or simultanés ---");
        
        // Créer deux cartes trésor pour le joueur 1
        CarteTresor carteTresor1 = new CarteTresor("Petit trésor", "Un petit coffre d'or", 8, 2);
        CarteTresor carteTresor2 = new CarteTresor("Grand trésor", "Un grand coffre d'or", 12, 4);
        
        // Ajouter les cartes à la zone stratégique du joueur 1
        zoneJoueur1.ajouterCarteStrategique(carteTresor1);
        zoneJoueur1.ajouterCarteStrategique(carteTresor2);
        
        // Vérifier l'or initial
        assertEquals(5, joueur1.getOr(), "L'or initial du joueur 1 devrait être 5");
        
        // Appliquer les effets des cartes
        controlCartePlateau.appliquerEffetCarte();
        
        // Vérifier le gain d'or total (5 + 2 + 4 = 11)
        assertEquals(11, joueur1.getOr(), "Après application des effets, l'or du joueur 1 devrait être 11");
        
        System.out.println("Or initial: 5, Or après gains: " + joueur1.getOr() + " ✓");
    }
    
    @Test
    public void testGainPourDeuxJoueurs() {
        System.out.println("\n--- Test gains d'or pour deux joueurs ---");
        
        // Créer une carte trésor pour chaque joueur
        CarteTresor carteTresor1 = new CarteTresor("Trésor joueur 1", "Trésor du joueur 1", 10, 3);
        CarteTresor carteTresor2 = new CarteTresor("Trésor joueur 2", "Trésor du joueur 2", 10, 2);
        
        // Ajouter les cartes aux zones stratégiques respectives
        zoneJoueur1.ajouterCarteStrategique(carteTresor1);
        zoneJoueur2.ajouterCarteStrategique(carteTresor2);
        
        // Vérifier l'or initial
        assertEquals(5, joueur1.getOr(), "L'or initial du joueur 1 devrait être 5");
        assertEquals(5, joueur2.getOr(), "L'or initial du joueur 2 devrait être 5");
        
        // Appliquer les effets des cartes
        controlCartePlateau.appliquerEffetCarte();
        
        // Vérifier les gains d'or
        assertEquals(8, joueur1.getOr(), "Après application des effets, l'or du joueur 1 devrait être 8");
        assertEquals(7, joueur2.getOr(), "Après application des effets, l'or du joueur 2 devrait être 7");
        
        System.out.println("Joueur 1 - Or initial: 5, Or après gain: " + joueur1.getOr() + " ✓");
        System.out.println("Joueur 2 - Or initial: 5, Or après gain: " + joueur2.getOr() + " ✓");
    }
}
