package test;

import jeu.Pioche;
import joueur.Joueur;
import joueur.Pirate;
import carte.Carte;
import carte.CarteAttaque;
import carte.CarteSoin;
import carte.CartePopularite;
import carte.CarteTresor;
import carte.TypeCarte;
import controllers.ControlJeu;
import controllers.ControlJoueur;
import controllers.ControlPioche;
import controllers.ControlCartePlateau;
import controllers.ControlZoneJoueur;
import jeu.ZoneOffensive;
import jeu.ZoneStrategique;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Tests pour la classe Pioche (nouvelle version)
 */
public class TestPioche {

    private Pioche pioche;
    private ControlPioche controlPioche;
    private ControlJeu controlJeu;
    private ControlJoueur controlJoueur1;
    private ControlJoueur controlJoueur2;
    private Joueur joueur1;
    private Joueur joueur2;
    
    @BeforeEach
    public void initialiser() {
        // Création de la pioche et du contrôleur
        pioche = new Pioche();
        controlPioche = new ControlPioche();
        
        // Initialisation du contrôleur principal du jeu
        controlJeu = new ControlJeu();
        
        // Création des pirates
        Pirate pirate1 = new Pirate("Barbe Noire");
        Pirate pirate2 = new Pirate("Anne Bonny");
        
        // Initialisation du jeu
        controlJeu.initialiserJeu(pirate1, pirate2);
        
        // Récupérer les contrôleurs de joueurs
        controlJoueur1 = controlJeu.getJoueur(0);
        controlJoueur2 = controlJeu.getJoueur(1);
        
        // Récupérer les joueurs
        joueur1 = controlJoueur1.getJoueur();
        joueur2 = controlJoueur2.getJoueur();
    }
    
    @Test
    @DisplayName("Test d'initialisation de la pioche")
    public void testInitialisationPioche() {
        // Initialiser la pioche
        controlPioche.initialiserPioche();
        
        // Vérifier que la pioche n'est pas vide
        assertFalse(controlPioche.estVide(), "La pioche ne devrait pas être vide après initialisation");
        
        // Vérifier qu'il y a des cartes dans la pioche
        assertTrue(controlPioche.getNombreCartes() > 0, 
                "La pioche devrait contenir des cartes après initialisation");
    }
    
    @Test
    @DisplayName("Test de pioche d'une carte")
    public void testPiocherCarte() {
        // Initialiser la pioche
        controlPioche.initialiserPioche();
        
        // Nombre initial de cartes
        int nombreCartesInitial = controlPioche.getNombreCartes();
        
        // Piocher une carte
        Carte cartePiochee = controlPioche.piocher();
        
        // Vérifications
        assertNotNull(cartePiochee, "La carte piochée ne devrait pas être null");
        assertEquals(nombreCartesInitial - 1, controlPioche.getNombreCartes(), 
                "Le nombre de cartes dans la pioche devrait être diminué de 1");
    }
    
    @Test
    @DisplayName("Test de pioche jusqu'à vider la pioche")
    public void testPiocherJusquaVide() {
        // Initialiser la pioche avec un nombre connu de cartes
        List<Carte> cartes = new ArrayList<>();
        cartes.add(new CarteAttaque("Épée", "Une épée tranchante", 10, 2, 0));
        cartes.add(new CarteSoin("Potion", "Une potion de soin", 10, 3));
        cartes.add(new CartePopularite("Chanson", "Une chanson populaire", 10, 2, 0));
        cartes.add(new CarteTresor("Coffre", "Un coffre d'or", 10, 5));
        
        
        ControlPioche controlPiocheTest = new ControlPioche();
        controlPiocheTest.initialiserPioche();
        
        // Nombre initial de cartes
        int nombreCartesInitial = controlPiocheTest.getNombreCartes();
        
        // Piocher jusqu'à vider la pioche
        for (int i = 0; i < nombreCartesInitial; i++) {
            Carte carte = controlPiocheTest.piocher();
            assertNotNull(carte, "La carte " + i + " piochée ne devrait pas être null");
        }
        
        // La pioche devrait être vide
        assertTrue(controlPiocheTest.estVide(), "La pioche devrait être vide après avoir pioché toutes les cartes");
        
        // Essayer de piocher encore une carte
        Carte carteSupplementaire = controlPiocheTest.piocher();
        assertNull(carteSupplementaire, "Piocher dans une pioche vide devrait retourner null");
    }
    
    @Test
    @DisplayName("Test d'initialisation de la main d'un joueur")
    public void testInitialiserMain() {
        // S'assurer que la pioche est initialisée
    	controlJeu = new ControlJeu();
    	Pirate pirate1 = new Pirate("AnneBonny");
    	Pirate pirate2 = new Pirate("BarbeNoire");
    	controlJeu.initialiserJeu(pirate1, pirate2);
        controlPioche = controlJeu.getControlPioche();
        controlPioche.initialiserPioche();
        
        controlJoueur1 = controlJeu.getJoueur(0);
        // Initialiser la main du joueur 1
        controlJoueur1.initialiserMain();
        
        // Vérifier que le joueur a des cartes en main
        assertFalse(joueur1.getMain().isEmpty(), "La main du joueur ne devrait pas être vide");
        
        // Vérifier qu'il y a 5 cartes en main (1 spéciale + 4 de la pioche)
        assertEquals(5, joueur1.getMain().size(), 
                "La main du joueur devrait contenir 5 cartes après initialisation");
    }
}
