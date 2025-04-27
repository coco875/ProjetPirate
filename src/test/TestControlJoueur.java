package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import carte.Carte;
import carte.CarteAttaque;
import carte.CartePopularite;
import controllers.ControlCartePlateau;
import controllers.ControlJoueur;
import controllers.ControlPioche;
import joueur.Joueur;
import joueur.Pirate;

/**
 * @brief Test unitaire de la classe ControlJoueur
 */
public class TestControlJoueur {
    public static void main(String[] args) {
        // Test de création d'un ControlJoueur
        testCreationControlJoueur();
        
        // Test de la pioche
        testPiocher();
        
        // Test d'initialisation de la main
        testInitialiserMain();
        
        // Test de jouer une carte
        testJouerCarte();
        
        // Test de perdre des points de vie
        testPerdrePointsDeVie();
        
        // Test de gagner de la popularité
        testGagnerPopularite();
        
        // Test de recevoir des effets
        testRecevoirEffets();
        
        // Test d'un tour complet
        testJouerTour();
        
        System.out.println("Tous les tests de ControlJoueur ont réussi!");
    }
    
    private static void testCreationControlJoueur() {
        // Création des objets nécessaires
        ControlPioche cPioche = new ControlPioche();
        ControlCartePlateau cCartePlateau = null; // On initialisera plus tard
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));
        
        // Création du contrôleur
        ControlJoueur cJoueur = new ControlJoueur(joueur, cCartePlateau, cPioche);
        cCartePlateau = new ControlCartePlateau(cJoueur, null);
        cJoueur.setControlCartePlateau(cCartePlateau);
        
        // Vérification
        assertNotNull("Le contrôleur joueur ne devrait pas être null", cJoueur);
        assertEquals("Le joueur devrait être correctement assigné", joueur, cJoueur.getJoueur());
        System.out.println("Test de création de ControlJoueur réussi!");
    }
    
    private static void testPiocher() {
        // Création des objets nécessaires
        ControlPioche cPioche = new ControlPioche();
        ControlCartePlateau cCartePlateau = null;
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));
        
        // Création du contrôleur
        ControlJoueur cJoueur = new ControlJoueur(joueur, cCartePlateau, cPioche);
        cCartePlateau = new ControlCartePlateau(cJoueur, null);
        cJoueur.setControlCartePlateau(cCartePlateau);
        
        // Taille initiale de la main
        int tailleInitiale = joueur.getMain().size();
        
        // Pioche d'une carte
        Carte cartePiochee = cJoueur.piocher();
        
        // Vérifications
        assertNotNull("La carte piochée ne devrait pas être null", cartePiochee);
        assertEquals("La taille de la main devrait avoir augmenté de 1", 
                   tailleInitiale + 1, joueur.getMain().size());
        assertTrue("La main devrait contenir la carte piochée", 
                 joueur.getMain().contains(cartePiochee));
        
        System.out.println("Test de pioche réussi!");
    }
    
    private static void testInitialiserMain() {
        // Création des objets nécessaires
        ControlPioche cPioche = new ControlPioche();
        ControlCartePlateau cCartePlateau = null;
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));
        
        // Création du contrôleur
        ControlJoueur cJoueur = new ControlJoueur(joueur, cCartePlateau, cPioche);
        cCartePlateau = new ControlCartePlateau(cJoueur, null);
        cJoueur.setControlCartePlateau(cCartePlateau);
        
        // Initialisation de la main
        cJoueur.initialiserMain();
        
        // Vérification
        assertEquals("La main devrait contenir 4 cartes après initialisation", 
                   4, joueur.getMain().size());
        
        System.out.println("Test d'initialisation de la main réussi!");
    }
    
    private static void testJouerCarte() {
        // Création des objets nécessaires
        ControlPioche cPioche = new ControlPioche();
        ControlJoueur cJoueur1 = new ControlJoueur(null, null, cPioche);
        ControlJoueur cJoueur2 = new ControlJoueur(null, null, cPioche);
        ControlCartePlateau cCartePlateau = new ControlCartePlateau(cJoueur1, cJoueur2);
        
        Joueur joueur1 = new Joueur("TestJoueur1", new Pirate("Jack Sparrow"));
        Joueur joueur2 = new Joueur("TestJoueur2", new Pirate("Barbe Noire"));
        
        cJoueur1.setJoueur(joueur1);
        cJoueur2.setJoueur(joueur2);
        cJoueur1.setControlCartePlateau(cCartePlateau);
        cJoueur2.setControlCartePlateau(cCartePlateau);
        
        // Création et ajout d'une carte d'attaque
        CarteAttaque carteAttaque = new CarteAttaque("Épée", "Une épée tranchante", 2, 2);
        joueur1.ajouterCarte(carteAttaque);
        
        // Points de vie initiaux du joueur 2
        int vieInitiale = joueur2.getPointsDeVie();
        
        // Jouer la carte d'attaque
        cJoueur1.jouerCarte(carteAttaque);
        
        // Vérification
        assertEquals("Les points de vie du joueur 2 devraient avoir diminué de 2", 
                   vieInitiale - 2, joueur2.getPointsDeVie());
        assertTrue("La carte ne devrait plus être dans la main du joueur", 
                 !joueur1.getMain().contains(carteAttaque));
        
        System.out.println("Test de jouer une carte réussi!");
    }
    
    private static void testPerdrePointsDeVie() {
        // Création des objets nécessaires
        ControlPioche cPioche = new ControlPioche();
        ControlCartePlateau cCartePlateau = null;
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));
        
        // Création du contrôleur
        ControlJoueur cJoueur = new ControlJoueur(joueur, cCartePlateau, cPioche);
        
        // Points de vie initiaux
        int vieInitiale = joueur.getPointsDeVie();
        
        // Perdre des points de vie
        cJoueur.perdrePointsDeVie(2);
        
        // Vérification
        assertEquals("Les points de vie devraient avoir diminué de 2", 
                   vieInitiale - 2, joueur.getPointsDeVie());
        
        // Test de limite min (0)
        cJoueur.perdrePointsDeVie(10);
        assertEquals("Les points de vie ne devraient pas être négatifs", 
                   0, joueur.getPointsDeVie());
        
        System.out.println("Test de perdre des points de vie réussi!");
    }
    
    private static void testGagnerPopularite() {
        // Création des objets nécessaires
        ControlPioche cPioche = new ControlPioche();
        ControlCartePlateau cCartePlateau = null;
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));
        
        // Création du contrôleur
        ControlJoueur cJoueur = new ControlJoueur(joueur, cCartePlateau, cPioche);
        
        // Popularité initiale
        int popInitiale = joueur.getPopularite();
        
        // Gagner de la popularité
        cJoueur.gagnerPopularite(2);
        
        // Vérification
        assertEquals("La popularité devrait avoir augmenté de 2", 
                   popInitiale + 2, joueur.getPopularite());
        
        // Test de limite max (5)
        cJoueur.gagnerPopularite(10);
        assertEquals("La popularité ne devrait pas dépasser 5", 
                   5, joueur.getPopularite());
        
        System.out.println("Test de gagner de la popularité réussi!");
    }
    
    private static void testRecevoirEffets() {
        // Création des objets nécessaires
        ControlPioche cPioche = new ControlPioche();
        ControlCartePlateau cCartePlateau = null;
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));
        
        // Création du contrôleur
        ControlJoueur cJoueur = new ControlJoueur(joueur, cCartePlateau, cPioche);
        
        // État initial
        int vieInitiale = joueur.getPointsDeVie();
        int popInitiale = joueur.getPopularite();
        
        // Recevoir des effets positifs
        cJoueur.recevoirEffets(1, 1);
        
        // Vérification
        assertEquals("Les points de vie devraient avoir augmenté de 1", 
                   vieInitiale + 1, joueur.getPointsDeVie());
        assertEquals("La popularité devrait avoir augmenté de 1", 
                   popInitiale + 1, joueur.getPopularite());
        
        // Recevoir des effets négatifs
        cJoueur.recevoirEffets(-2, -1);
        
        // Vérification
        assertEquals("Les points de vie devraient avoir diminué de 2", 
                   vieInitiale - 1, joueur.getPointsDeVie());
        assertEquals("La popularité devrait avoir diminué de 1", 
                   popInitiale, joueur.getPopularite());
        
        System.out.println("Test de recevoir des effets réussi!");
    }
    
    private static void testJouerTour() {
        // Création des objets nécessaires
        ControlPioche cPioche = new ControlPioche();
        ControlJoueur cJoueur1 = new ControlJoueur(null, null, cPioche);
        ControlJoueur cJoueur2 = new ControlJoueur(null, null, cPioche);
        ControlCartePlateau cCartePlateau = new ControlCartePlateau(cJoueur1, cJoueur2);
        
        Joueur joueur1 = new Joueur("TestJoueur1", new Pirate("Jack Sparrow"));
        Joueur joueur2 = new Joueur("TestJoueur2", new Pirate("Barbe Noire"));
        
        cJoueur1.setJoueur(joueur1);
        cJoueur2.setJoueur(joueur2);
        cJoueur1.setControlCartePlateau(cCartePlateau);
        cJoueur2.setControlCartePlateau(cCartePlateau);
        
        // Initialiser la main
        cJoueur1.initialiserMain();
        
        // Jouer un tour
        cJoueur1.jouerTour();
        
        // Vérification
        assertTrue("La main ne devrait pas dépasser 4 cartes après un tour", 
                 joueur1.getMain().size() <= 4);
        
        System.out.println("Test de jouer un tour réussi!");
    }
}