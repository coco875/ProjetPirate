package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import controllers.*;
import joueur.*;
import carte.*;
import jeu.*;

/**
 * Test complet pour la classe ControlJeu
 */
public class TestControlJeuComplet {
    
    private ControlJeu controlJeu;
    
    @BeforeEach
    public void setUp() {
        controlJeu = new ControlJeu();
    }
    
    @Test
    @DisplayName("Test d'initialisation du jeu")
    public void testInitialisationJeu() {
        // Vérification que les contrôleurs sont bien initialisés
        assertNotNull(controlJeu.getControlCartePlateau());
        assertNotNull(controlJeu.getControlCarteSpeciale());
        assertNotNull(controlJeu.getControlMarche());
        assertNotNull(controlJeu.getControlPioche());
    }
    
    @Test
    @DisplayName("Test de création et gestion des joueurs")
    public void testCreationJoueurs() {
        // Création des joueurs
        Joueur joueur1 = controlJeu.creerJoueur("Joueur1", "Jack Sparrow");
        Joueur joueur2 = controlJeu.creerJoueur("Joueur2", "Barbe Noire");
        
        // Vérification des joueurs créés
        assertNotNull(joueur1);
        assertNotNull(joueur2);
        assertEquals("Joueur1", joueur1.getNom());
        assertEquals("Joueur2", joueur2.getNom());
        assertEquals("Jack Sparrow", joueur1.getPirate().getNom());
        assertEquals("Barbe Noire", joueur2.getPirate().getNom());
        
        // Vérification que les contrôleurs des joueurs sont bien initialisés
        ControlJoueur controlJoueur1 = controlJeu.getJoueur(0);
        ControlJoueur controlJoueur2 = controlJeu.getJoueur(1);
        
        assertNotNull(controlJoueur1);
        assertNotNull(controlJoueur2);
        assertSame(joueur1, controlJoueur1.getJoueur());
        assertSame(joueur2, controlJoueur2.getJoueur());
        
        // Vérification des méthodes d'accès
        assertEquals(2, controlJeu.getNombreJoueurs());
        assertEquals("Joueur1", controlJeu.getNomJoueur(0));
        assertEquals("Joueur2", controlJeu.getNomJoueur(1));
        assertEquals(joueur1.getPointsDeVie(), controlJeu.getPointsDeVieJoueur(0));
        assertEquals(joueur2.getPointsDeVie(), controlJeu.getPointsDeVieJoueur(1));
    }
    
    @Test
    @DisplayName("Test d'initialisation d'une partie")
    public void testInitialiserPartie() {
        // Création des joueurs
        controlJeu.creerJoueur("Joueur1", "Jack Sparrow");
        controlJeu.creerJoueur("Joueur2", "Barbe Noire");
        
        // Initialisation de la partie
        controlJeu.initialiserPartie();
        
        // Vérification que chaque joueur a bien reçu des cartes
        ControlJoueur controlJoueur1 = controlJeu.getJoueur(0);
        ControlJoueur controlJoueur2 = controlJeu.getJoueur(1);
        
        assertTrue(controlJoueur1.getNombreCartesEnMain() > 0);
        assertTrue(controlJoueur2.getNombreCartesEnMain() > 0);
        
        // Vérification que le marché est initialisé
        ControlMarche controlMarche = controlJeu.getControlMarche();
        assertTrue(controlMarche.getNombreCartesDisponibles() > 0);
    }
    
    @Test
    @DisplayName("Test de gestion d'un tour de jeu")
    public void testGestionTourDeJeu() {
        // Création des joueurs
        controlJeu.creerJoueur("Joueur1", "Jack Sparrow");
        controlJeu.creerJoueur("Joueur2", "Barbe Noire");
        
        // Initialisation de la partie
        controlJeu.initialiserPartie();
        
        // Récupération des joueurs
        ControlJoueur joueur1 = controlJeu.getJoueur(0);
        ControlJoueur joueur2 = controlJeu.getJoueur(1);
        
        // Forcer les cartes en main pour les tests
        Joueur j1 = joueur1.getJoueur();
        Joueur j2 = joueur2.getJoueur();
        j1.viderMain();
        j2.viderMain();
        
        // Ajouter des cartes pour les tests
        CarteOffensive attaqueJ1 = new CarteOffensive("Épée", "Test", 2, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        CarteStrategique strategiqueJ1 = new CarteStrategique("Chanson", "Test", 3, 1);
        
        CarteOffensive attaqueJ2 = new CarteOffensive("Mousquet", "Test", 3, 0, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        CarteStrategique strategiqueJ2 = new CarteStrategique("Coffre", "Test", 5, 0, true);
        
        j1.ajouterCarteEnMain(attaqueJ1);
        j1.ajouterCarteEnMain(strategiqueJ1);
        j2.ajouterCarteEnMain(attaqueJ2);
        j2.ajouterCarteEnMain(strategiqueJ2);
        
        // S'assurer que les joueurs ont assez de points de vie pour le test
        j1.setVie(10);
        j2.setVie(10);
        j1.setOr(10);
        j2.setOr(10);
        
        // Points de vie initiaux
        int vieInitialeJ1 = j1.getPointsDeVie();
        int vieInitialeJ2 = j2.getPointsDeVie();
        int orInitialJ1 = j1.getOr();
        int orInitialJ2 = j2.getOr();
        int popInitialeJ1 = j1.getPopularite();
        
        // Vérification au début du tour
        assertEquals(0, controlJeu.getJoueurActif());
        
        // Simuler le tour de jeu 1
        
        // Joueur 1 joue ses cartes
        controlJeu.jouerCarte(0, 0, true); // carte offensive (attaque)
        controlJeu.jouerCarte(0, 0, false); // carte stratégique (popularité)
        controlJeu.finirTourJoueur(0);
        
        // Joueur 2 joue ses cartes
        assertEquals(1, controlJeu.getJoueurActif());
        controlJeu.jouerCarte(1, 0, true); // carte offensive (attaque)
        controlJeu.jouerCarte(1, 0, false); // carte stratégique (trésor)
        controlJeu.finirTourJoueur(1);
        
        // Application des effets de cartes
        controlJeu.appliquerEffetsCartes();
        
        // Vérifications après le tour
        // J1 : -3 PV (attaque J2), +3 popularité (stratégique J1)
        // J2 : -2 PV (attaque J1), +5 or (stratégique J2)
        assertEquals(vieInitialeJ1 - 3, j1.getPointsDeVie());
        assertEquals(vieInitialeJ2 - 2, j2.getPointsDeVie());
        assertEquals(orInitialJ1, j1.getOr());
        assertEquals(orInitialJ2 + 5, j2.getOr());
        assertEquals(popInitialeJ1 + 3, j1.getPopularite());
        
        // Vérification que les zones sont vidées
        assertEquals(0, controlJeu.getControlCartePlateau().getCartesOffensivesJ1().size());
        assertEquals(0, controlJeu.getControlCartePlateau().getCartesOffensivesJ2().size());
        assertEquals(0, controlJeu.getControlCartePlateau().getCartesStrategiquesJ1().size());
        assertEquals(0, controlJeu.getControlCartePlateau().getCartesStrategiquesJ2().size());
    }
    
    @Test
    @DisplayName("Test des méthodes d'accès aux contrôleurs")
    public void testAccesseursControleurs() {
        assertNotNull(controlJeu.getControlCartePlateau());
        assertNotNull(controlJeu.getControlCarteSpeciale());
        assertNotNull(controlJeu.getControlMarche());
        assertNotNull(controlJeu.getControlPioche());
    }
    
    @Test
    @DisplayName("Test de la méthode getPioche")
    public void testGetPioche() {
        Pioche pioche = controlJeu.getPioche();
        assertNotNull(pioche);
    }
    
    @Test
    @DisplayName("Test de la méthode getDefausse")
    public void testGetDefausse() {
        Defausse defausse = controlJeu.getDefausse();
        assertNotNull(defausse);
    }
    
    @Test
    @DisplayName("Test condition de victoire")
    public void testConditionVictoire() {
        // Création des joueurs
        Joueur joueur1 = controlJeu.creerJoueur("Joueur1", "Jack Sparrow");
        Joueur joueur2 = controlJeu.creerJoueur("Joueur2", "Barbe Noire");
        
        // Par défaut, personne n'a gagné
        assertEquals(-1, controlJeu.verifierFinJeu());
        
        // Joueur 1 a 0 PV
        joueur1.setVie(0);
        assertEquals(1, controlJeu.verifierFinJeu()); // Joueur 2 gagne
        
        // Les deux joueurs ont 0 PV
        joueur1.setVie(0);
        joueur2.setVie(0);
        assertEquals(-1, controlJeu.verifierFinJeu()); // Match nul
        
        // Reset
        joueur1.setVie(5);
        joueur2.setVie(5);
        assertEquals(-1, controlJeu.verifierFinJeu()); // Personne n'a gagné
        
        // Joueur 2 a 0 PV
        joueur2.setVie(0);
        assertEquals(0, controlJeu.verifierFinJeu()); // Joueur 1 gagne
    }
    
    @Test
    @DisplayName("Test de jouer une carte avec des indices invalides")
    public void testJouerCarteIndicesInvalides() {
        // Création des joueurs
        controlJeu.creerJoueur("Joueur1", "Jack Sparrow");
        controlJeu.creerJoueur("Joueur2", "Barbe Noire");
        
        // Tentative de jouer une carte avec un indice de joueur invalide
        assertFalse(controlJeu.jouerCarte(-1, 0, true));
        assertFalse(controlJeu.jouerCarte(2, 0, true));
        
        // Tentative de jouer une carte avec un indice de carte invalide
        assertFalse(controlJeu.jouerCarte(0, -1, true));
        assertFalse(controlJeu.jouerCarte(0, 10, true));
    }
}