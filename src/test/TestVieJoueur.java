package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controllers.ControlJoueur;
import controllers.ControlZoneJoueur;
import controllers.ControlPioche;
import jeu.ZoneOffensive;
import jeu.ZoneStrategique;
import joueur.Joueur;
import joueur.Pirate;

@DisplayName("Tests de la vie et popularité du joueur")
public class TestVieJoueur {
    
    private Joueur joueur;
    private ControlJoueur controlJoueur;
    private ControlZoneJoueur zoneJoueur;
    private ControlPioche controlPioche;
    
    @BeforeEach
    public void initialiser() {
        // Initialiser les éléments nécessaires
        Pirate pirate = new Pirate("Chef");
        joueur = new Joueur(pirate);
        ZoneOffensive zoneOffensive = new ZoneOffensive();
        ZoneStrategique zoneStrategique = new ZoneStrategique();
        zoneJoueur = new ControlZoneJoueur(zoneOffensive, zoneStrategique);
        controlPioche = new ControlPioche();
        controlJoueur = new ControlJoueur(joueur, controlPioche, zoneJoueur);
    }
    
    @Test
    @DisplayName("Test des points de vie initiaux")
    public void testPointsDeVieInitiaux() {
        assertEquals(5, joueur.getPointsDeVie(), "Les points de vie initiaux devraient être 5");
    }
    
    @Test
    @DisplayName("Test de la popularité initiale")
    public void testPopulariteInitiale() {
        assertEquals(0, joueur.getPopularite(), "La popularité initiale devrait être 0");
    }
    
    @Test
    @DisplayName("Test de la perte de points de vie")
    public void testPerdrePointsDeVie() {
        controlJoueur.perdrePointsDeVie(2);
        assertEquals(3, joueur.getPointsDeVie(), "Après avoir perdu 2 PV, le joueur devrait avoir 3 PV");
    }
    
    @Test
    @DisplayName("Test du gain de popularité")
    public void testGagnerPopularite() {
        controlJoueur.gagnerPopularite(3);
        assertEquals(3, joueur.getPopularite(), "Après avoir gagné 3 points de popularité, le joueur devrait avoir 3 points");
    }
    
    @Test
    @DisplayName("Test de la limite minimale des points de vie")
    public void testLimiteMinimalePV() {
        // Perdre plus de PV que le joueur n'en possède
        controlJoueur.perdrePointsDeVie(10);
        assertEquals(0, joueur.getPointsDeVie(), "Les points de vie ne peuvent pas descendre en dessous de 0");
    }
    
    @Test
    @DisplayName("Test de la limite minimale de popularité")
    public void testLimiteMinimalePopularite() {
        // Gagner de la popularité puis en perdre plus
        controlJoueur.gagnerPopularite(5);
        controlJoueur.perdrePopularite(10);
        assertEquals(0, joueur.getPopularite(), "La popularité ne peut pas descendre en dessous de 0");
    }
    
    @Test
    @DisplayName("Test des combinaisons de gains et pertes")
    public void testCombinaisonsGainsPertes() {
        // Série de gains et pertes
        controlJoueur.perdrePointsDeVie(2); // 5 -> 3 PV
        controlJoueur.gagnerPopularite(4);  // 0 -> 4 Pop
        controlJoueur.gagnerPointsDeVie(1); // 3 -> 4 PV
        controlJoueur.perdrePopularite(2);  // 4 -> 2 Pop
        
        assertEquals(4, joueur.getPointsDeVie(), "Les points de vie devraient être 4 après les opérations");
        assertEquals(2, joueur.getPopularite(), "La popularité devrait être 2 après les opérations");
    }
}
