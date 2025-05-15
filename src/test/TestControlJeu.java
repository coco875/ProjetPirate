package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import carte.CarteAttaque;
import carte.CarteSoin;
import carte.CartePopularite;
import carte.CarteTresor;
import carte.Carte;
import carte.TypeCarte;

import controllers.ControlJeu;
import controllers.ControlJoueur;
import controllers.ControlCartePlateau;
import controllers.ControlPioche;
import controllers.ControlZoneJoueur;

import jeu.ZoneOffensive;
import jeu.ZoneStrategique;
import joueur.Joueur;
import joueur.Pirate;

import java.util.List;

@DisplayName("Tests du contrôleur ControlJeu (nouvelle version)")
public class TestControlJeu {
    
    private ControlJeu controlJeu;
    private Pirate pirate1;
    private Pirate pirate2;
    private ControlJoueur controlJoueur1;
    private ControlJoueur controlJoueur2;
    
    @BeforeEach
    public void initialiser() {
        controlJeu = new ControlJeu();
        pirate1 = new Pirate("Barbe Noire");
        pirate2 = new Pirate("Anne Bonny");
        controlJeu.initialiserJeu(pirate1, pirate2);
        
        // Récupérer les contrôleurs de joueurs
        controlJoueur1 = controlJeu.getJoueur(0);
        controlJoueur2 = controlJeu.getJoueur(1);
    }

    @Test
    @DisplayName("Test de l'initialisation des joueurs")
    public void testInitialisationJoueurs() {
        // Vérifications
        assertNotNull(controlJeu.getJoueur(0), "Le joueur 1 devrait être initialisé");
        assertNotNull(controlJeu.getJoueur(1), "Le joueur 2 devrait être initialisé");
        
        // Vérification des noms de pirates
        assertEquals("Barbe Noire", controlJeu.getJoueur(0).getJoueur().getPirate().getNom(), 
                "Le nom du pirate 1 est incorrect");
        assertEquals("Anne Bonny", controlJeu.getJoueur(1).getJoueur().getPirate().getNom(), 
                "Le nom du pirate 2 est incorrect");
    }
    
    @Test
    @DisplayName("Test de l'initialisation du jeu")
    public void testInitialiserJeu() {
        // Vérifier que les composants du jeu sont initialisés
        assertNotNull(controlJeu.getControlPioche(), "Le contrôleur de pioche devrait être initialisé");
        assertNotNull(controlJeu.getControlCartePlateau(), "Le contrôleur de carte plateau devrait être initialisé");
        
        // Vérifier les joueurs
        assertNotNull(controlJeu.getJoueur(0), "Le contrôleur de joueur 1 devrait être initialisé");
        assertNotNull(controlJeu.getJoueur(1), "Le contrôleur de joueur 2 devrait être initialisé");
    }
    
    @Test
    @DisplayName("Test de la gestion des cartes sur le plateau")
    public void testGestionCartesPlateau() {
        // Création des cartes
        CarteAttaque carteAttaque = new CarteAttaque("Épée", "Une épée tranchante", 2, 1, 0);
        CarteTresor carteTresor = new CarteTresor("Coffre", "Un coffre rempli d'or", 3, 5);
        
        // Récupérer les zones des joueurs
        ControlZoneJoueur zoneJoueur1 = controlJeu.getControlZoneJoueur1();
        
        // Ajouter des cartes dans les zones
        zoneJoueur1.ajouterCarteOffensive(carteAttaque);
        zoneJoueur1.ajouterCarteStrategique(carteTresor);
        
        // Vérifications
        assertEquals(1, zoneJoueur1.getZoneOffensive().getCartesOffensives().size(), 
                    "La zone offensive du joueur 1 devrait contenir 1 carte");
        assertEquals(1, zoneJoueur1.getZoneStrategique().getCartesStrategiques().size(), 
                    "La zone stratégique du joueur 1 devrait contenir 1 carte");
        
        // Vérifier que les cartes sont bien celles ajoutées
        assertTrue(zoneJoueur1.getZoneOffensive().getCartesOffensives().contains(carteAttaque),
                  "La zone offensive du joueur 1 devrait contenir la carte attaque");
        assertTrue(zoneJoueur1.getZoneStrategique().getCartesStrategiques().contains(carteTresor),
                  "La zone stratégique du joueur 1 devrait contenir la carte trésor");
    }
    
    @Test
    @DisplayName("Test de la vider les zones")
    public void testViderZones() {
        // Création des cartes
        CarteAttaque carteAttaque = new CarteAttaque("Épée", "Une épée tranchante", 2, 1, 0);
        CarteTresor carteTresor = new CarteTresor("Coffre", "Un coffre rempli d'or", 3, 5);
        
        // Récupérer les zones des joueurs
        ControlZoneJoueur zoneJoueur1 = controlJeu.getControlZoneJoueur1();
        
        // Ajouter des cartes dans les zones
        zoneJoueur1.ajouterCarteOffensive(carteAttaque);
        zoneJoueur1.ajouterCarteStrategique(carteTresor);
        
        // Vider les zones
        zoneJoueur1.viderZone();
        
        // Vérifications
        assertEquals(0, zoneJoueur1.getZoneOffensive().getCartesOffensives().size(), 
                    "La zone offensive du joueur 1 devrait être vide");
        assertEquals(0, zoneJoueur1.getZoneStrategique().getCartesStrategiques().size(), 
                    "La zone stratégique du joueur 1 devrait être vide");
    }
    
    @Test
    @DisplayName("Test de la gestion du joueur actif")
    public void testGestionJoueurActif() {
        // Initialisation
        controlJeu.setJoueurActif(0);
        
        // Vérification
        assertEquals(0, controlJeu.getJoueurActif(), "Le joueur 1 devrait être actif");
        
        // Changer de joueur actif
        controlJeu.setJoueurActif(1);
        
        // Vérification
        assertEquals(1, controlJeu.getJoueurActif(), "Le joueur 2 devrait être actif");
    }
}
