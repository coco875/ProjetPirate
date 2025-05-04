package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

import carte.Carte;
import carte.CarteAttaque;
import carte.CarteOffensive;
import carte.CartePopularite;
import carte.CarteStrategique;
import controllers.ControlCartePlateau;
import controllers.ControlCarteSpeciale;
import controllers.ControlJeu;
import controllers.ControlJoueur;
import controllers.ControlMarche;
import controllers.ControlPioche;
import jeu.Jeu;
import joueur.Joueur;
import joueur.Pirate;

/**
 * @brief Test unitaire de la classe ControlJoueur
 */
public class TestControlJoueur {
    public static void main(String[] args) {
        System.out.println("Exécution des tests de TestControlJoueur...");
        testCreationControlJoueur();
        testPiocher();
        testInitialiserMain();
        testJouerCarte();
        testPerdrePointsDeVie();
        testGagnerPopularite();
        testRecevoirEffets();
        testJouerTour();
        System.out.println("Tests de TestControlJoueur terminés.");
    }
    
    private static void testCreationControlJoueur() {
        // Création des objets nécessaires
        ControlJeu controlJeu = new ControlJeu(); // Utiliser le constructeur sans argument
        ControlPioche controlPioche = controlJeu.getControlPioche();
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));

        // Création du contrôleur dans le bon ordre
        ControlJoueur controlJoueur = new ControlJoueur(joueur, controlJeu, controlPioche);

        // Création du contrôleur de plateau après création du contrôleur joueur
        Joueur joueur2 = new Joueur("Adversaire", new Pirate("Barbe Noire"));
        ControlJoueur controlJoueur2 = new ControlJoueur(joueur2, controlJeu, controlPioche);
        ControlCartePlateau controlCartePlateau = new ControlCartePlateau(controlJoueur, controlJoueur2);
        controlJoueur.setControlCartePlateau(controlCartePlateau);
        controlJoueur2.setControlCartePlateau(controlCartePlateau);

        // Vérification
        assertNotNull("Le contrôleur joueur ne devrait pas être null", controlJoueur);
        assertEquals("Le joueur devrait être correctement assigné", joueur, controlJoueur.getJoueur());
        System.out.println("Test de création de ControlJoueur réussi!");
    }
    
    private static void testPiocher() {
        // Création des objets nécessaires
        ControlJeu controlJeu = new ControlJeu(); // Utiliser le constructeur sans argument
        ControlPioche controlPioche = controlJeu.getControlPioche();
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));

        // Création du contrôleur
        ControlJoueur controlJoueur = new ControlJoueur(joueur, controlJeu, controlPioche);

        // Taille initiale de la main
        int tailleInitiale = joueur.getMain().size();

        // Pioche d'une carte
        Carte cartePiochee = controlJoueur.piocher();

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
        ControlJeu controlJeu = new ControlJeu(); // Utiliser le constructeur sans argument
        ControlPioche controlPioche = controlJeu.getControlPioche();
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));

        // Création du contrôleur
        ControlJoueur controlJoueur = new ControlJoueur(joueur, controlJeu, controlPioche);

        // Initialisation de la main
        controlJoueur.initialiserMain();

        // Vérification - la méthode initialiserMain() pioche 5 cartes
        assertEquals("La main devrait contenir 5 cartes après initialisation", 
                   5, joueur.getMain().size());

        System.out.println("Test d'initialisation de la main réussi!");
    }
    
    private static void testJouerCarte() {
        // Création des objets nécessaires
        ControlJeu controlJeu = new ControlJeu(); // Utiliser le constructeur sans argument
        ControlPioche controlPioche = controlJeu.getControlPioche();

        Joueur joueur1 = new Joueur("TestJoueur1", new Pirate("Jack Sparrow"));
        Joueur joueur2 = new Joueur("TestJoueur2", new Pirate("Barbe Noire"));

        controlJeu.creerJoueur(joueur1.getNom(), joueur1.getPirate().getNom());
        controlJeu.creerJoueur(joueur2.getNom(), joueur2.getPirate().getNom());
        ControlJoueur controlJoueur1 = controlJeu.getJoueur(0);
        ControlJoueur controlJoueur2 = controlJeu.getJoueur(1);

        CarteOffensive carteOffensive = new CarteAttaque("Épée", "Une épée tranchante", 2, 2);
        controlJoueur1.getJoueur().ajouterCarte(carteOffensive);

        int tailleInitiale = controlJoueur1.getJoueur().getMain().size();

        boolean resultat = controlJoueur1.jouerCarte(0);

        assertTrue("La carte devrait pouvoir être jouée", resultat);
        assertEquals("La taille de la main devrait avoir diminué de 1", 
                   tailleInitiale - 1, controlJoueur1.getJoueur().getMain().size());
        assertFalse("La carte ne devrait plus être dans la main du joueur", 
                  controlJoueur1.getJoueur().getMain().contains(carteOffensive));

        System.out.println("Test de jouer une carte réussi!");
    }
    
    private static void testPerdrePointsDeVie() {
        // Création des objets nécessaires
        ControlJeu controlJeu = new ControlJeu(); // Utiliser le constructeur sans argument
        ControlPioche controlPioche = controlJeu.getControlPioche();
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));

        // Création du contrôleur
        ControlJoueur controlJoueur = new ControlJoueur(joueur, controlJeu, controlPioche);

        // Points de vie initiaux
        int vieInitiale = joueur.getPointsDeVie();

        // Perdre des points de vie
        controlJoueur.perdrePointsDeVie(2);

        // Vérification
        Assert.assertEquals("Les points de vie devraient avoir diminué de 2", 
                   (long)vieInitiale - 2, (long)joueur.getPointsDeVie());

        System.out.println("Test de perdre des points de vie réussi!");
    }
    
    private static void testGagnerPopularite() {
        // Création des objets nécessaires
        ControlJeu controlJeu = new ControlJeu(); // Utiliser le constructeur sans argument
        ControlPioche controlPioche = controlJeu.getControlPioche();
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));

        // Création du contrôleur
        ControlJoueur controlJoueur = new ControlJoueur(joueur, controlJeu, controlPioche);

        // Popularité initiale
        int popInitiale = joueur.getPopularite();

        // Gagner de la popularité
        controlJoueur.gagnerPopularite(2);

        // Vérification
        Assert.assertEquals("La popularité devrait avoir augmenté de 2", 
                   (long)popInitiale + 2, (long)joueur.getPopularite());

        System.out.println("Test de gagner de la popularité réussi!");
    }
    
    private static void testRecevoirEffets() {
        // Création des objets nécessaires
        ControlJeu controlJeu = new ControlJeu(); // Utiliser le constructeur sans argument
        ControlPioche controlPioche = controlJeu.getControlPioche();
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));

        // Création du contrôleur
        ControlJoueur controlJoueur = new ControlJoueur(joueur, controlJeu, controlPioche);

        // État initial
        int vieInitiale = joueur.getPointsDeVie();
        int popInitiale = joueur.getPopularite();

        // Recevoir des effets (degats, popularite)
        controlJoueur.recevoirEffets(2, 1);

        Assert.assertEquals("Les points de vie devraient avoir diminué de 2", 
                   (long)vieInitiale - 2, (long)joueur.getPointsDeVie());
        Assert.assertEquals("La popularité devrait avoir augmenté de 1", 
                   (long)popInitiale + 1, (long)joueur.getPopularite());

        vieInitiale = joueur.getPointsDeVie();
        popInitiale = joueur.getPopularite();

        controlJoueur.recevoirEffets(-2, -1);

        Assert.assertEquals("Les points de vie devraient avoir augmenté de 2", 
                   (long)vieInitiale + 2, (long)joueur.getPointsDeVie());
        Assert.assertEquals("La popularité devrait avoir diminué de 1", 
                   (long)popInitiale - 1, (long)joueur.getPopularite());

        System.out.println("Test de recevoir des effets réussi!");
    }
    
    private static void testJouerTour() {
        // Création des objets nécessaires
        ControlJeu controlJeu = new ControlJeu(); // Utiliser le constructeur sans argument
        ControlPioche controlPioche = controlJeu.getControlPioche();

        controlJeu.creerJoueur("TestJoueur1", "Jack Sparrow");
        controlJeu.creerJoueur("TestJoueur2", "Barbe Noire");
        ControlJoueur controlJoueur1 = controlJeu.getJoueur(0);

        controlJoueur1.initialiserMain();

        int tailleAvant = controlJoueur1.getJoueur().getMain().size();

        controlJoueur1.jouerTour();

        // La méthode jouerTour pioche une carte PUIS en joue une. Le nombre de cartes ne change donc pas.
        assertEquals("La main devrait avoir le même nombre de cartes après la pioche et le jeu du tour", 
                 tailleAvant, controlJoueur1.getJoueur().getMain().size());

        System.out.println("Test de jouer un tour (pioche et jeu) réussi!");
    }
}