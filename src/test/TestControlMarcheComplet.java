package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import carte.*;
import controllers.*;
import joueur.*;
import jeu.*;

/**
 * Test complet pour la classe ControlMarche
 */
public class TestControlMarcheComplet {
    
    private ControlMarche controlMarche;
    private ControlJoueur controlJoueur;
    private Joueur joueur;
    private Pirate pirate;
    private ControlPioche controlPioche;
    
    @BeforeEach
    public void setUp() {
        // Préparation des objets nécessaires pour les tests
        pirate = new Pirate("Jack Sparrow");
        joueur = new Joueur("TestJoueur", pirate);
        controlPioche = new ControlPioche();
        controlJoueur = new ControlJoueur(joueur, null, controlPioche);
        controlMarche = new ControlMarche();
        
        // Donner de l'or au joueur pour les achats
        joueur.gagnerOr(100);
    }
    
    @Test
    @DisplayName("Test d'initialisation du marché")
    public void testInitialiserMarche() {
        // Le marché devrait être vide au départ
        assertEquals(0, controlMarche.getNombreCartesDisponibles());
        
        // Initialiser le marché avec 5 cartes
        controlMarche.initialiserMarche(5);
        
        // Vérifier qu'il y a maintenant 5 cartes
        assertEquals(5, controlMarche.getNombreCartesDisponibles());
    }
    
    @Test
    @DisplayName("Test d'affichage du marché")
    public void testAfficherMarche() {
        controlMarche.initialiserMarche(3);
        String affichage = controlMarche.afficherMarche();
        
        // Vérifier que l'affichage contient les informations attendues
        assertNotNull(affichage);
        assertTrue(affichage.contains("Cartes disponibles dans le marché"));
        
        // Vérifier que chaque carte est mentionnée
        for (int i = 0; i < controlMarche.getNombreCartesDisponibles(); i++) {
            assertTrue(affichage.contains("[" + i + "]"));
        }
    }
    
    @Test
    @DisplayName("Test d'achat de carte")
    public void testAcheterCarte() {
        // Initialiser le marché
        controlMarche.initialiserMarche(3);
        
        // Obtenir le nombre d'or initial
        int orInitial = joueur.getOr();
        
        // Obtenir la première carte pour connaître son coût
        Carte carte = controlMarche.getCarteDuMarche(0);
        int coutCarte = carte.getCout();
        
        // Acheter la carte
        boolean achat = controlMarche.acheterCarte(0, controlJoueur);
        
        // Vérifier que l'achat a réussi
        assertTrue(achat);
        
        // Vérifier que l'or a été dépensé
        assertEquals(orInitial - coutCarte, joueur.getOr());
        
        // Vérifier que la carte a été ajoutée à la main du joueur
        assertTrue(joueur.getCarteEnMain().contains(carte));
        
        // Vérifier que la carte n'est plus dans le marché
        assertEquals(2, controlMarche.getNombreCartesDisponibles());
        assertNotEquals(carte, controlMarche.getCarteDuMarche(0));
    }
    
    @Test
    @DisplayName("Test d'achat de carte échoué par manque d'or")
    public void testAchatEchoueManqueOr() {
        // Initialiser le marché
        controlMarche.initialiserMarche(3);
        
        // Réduire l'or du joueur à 0
        joueur.setOr(0);
        
        // Tentative d'achat
        boolean achat = controlMarche.acheterCarte(0, controlJoueur);
        
        // Vérifier que l'achat a échoué
        assertFalse(achat);
        
        // Vérifier que la carte est toujours dans le marché
        assertEquals(3, controlMarche.getNombreCartesDisponibles());
    }
    
    @Test
    @DisplayName("Test d'achat avec indice invalide")
    public void testAchatIndiceInvalide() {
        // Initialiser le marché
        controlMarche.initialiserMarche(3);
        
        // Tentative d'achat avec indice négatif
        boolean achat1 = controlMarche.acheterCarte(-1, controlJoueur);
        assertFalse(achat1);
        
        // Tentative d'achat avec indice trop grand
        boolean achat2 = controlMarche.acheterCarte(10, controlJoueur);
        assertFalse(achat2);
        
        // Vérifier que le marché est intact
        assertEquals(3, controlMarche.getNombreCartesDisponibles());
        
        // Vérifier que l'or n'a pas été dépensé
        assertEquals(100, joueur.getOr());
    }
    
    @Test
    @DisplayName("Test d'ajout de carte au marché")
    public void testAjouterCarte() {
        // Créer une carte
        Carte carte = new Carte(TypeCarte.OFFENSIVE, "Test", "Carte de test", 1, 0);
        
        // Ajouter la carte au marché
        controlMarche.ajouterCarte(carte);
        
        // Vérifier que la carte a été ajoutée
        assertEquals(1, controlMarche.getNombreCartesDisponibles());
        assertEquals(carte, controlMarche.getCarteDuMarche(0));
    }
    
    @Test
    @DisplayName("Test de vidage du marché")
    public void testViderMarche() {
        // Initialiser le marché
        controlMarche.initialiserMarche(5);
        
        // Vérifier que le marché contient des cartes
        assertEquals(5, controlMarche.getNombreCartesDisponibles());
        
        // Vider le marché
        controlMarche.viderMarche();
        
        // Vérifier que le marché est vide
        assertEquals(0, controlMarche.getNombreCartesDisponibles());
    }
    
    @Test
    @DisplayName("Test de récupération d'une carte")
    public void testGetCarteDuMarche() {
        // Initialiser le marché
        Carte carte = new Carte(TypeCarte.OFFENSIVE, "Test", "Carte de test", 1, 0);
        controlMarche.ajouterCarte(carte);
        
        // Récupérer la carte
        Carte carteRecuperee = controlMarche.getCarteDuMarche(0);
        
        // Vérifier que c'est la bonne carte
        assertEquals(carte, carteRecuperee);
        
        // Vérifier que la récupération avec indice invalide retourne null
        assertNull(controlMarche.getCarteDuMarche(-1));
        assertNull(controlMarche.getCarteDuMarche(10));
    }
}