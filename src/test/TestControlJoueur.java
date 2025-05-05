package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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
    
    @Test
    public void testCreationControlJoueur() {
        // Création des objets nécessaires
        ControlJeu controlJeu = new ControlJeu();
        ControlPioche controlPioche = controlJeu.getControlPioche();
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));

        // Création du contrôleur
        ControlJoueur controlJoueur = new ControlJoueur(joueur, controlJeu, controlPioche);

        // Création du contrôleur de plateau
        Joueur joueur2 = new Joueur("Adversaire", new Pirate("Barbe Noire"));
        ControlJoueur controlJoueur2 = new ControlJoueur(joueur2, controlJeu, controlPioche);
        ControlCartePlateau controlCartePlateau = new ControlCartePlateau(controlJoueur, controlJoueur2);
        controlJoueur.setControlCartePlateau(controlCartePlateau);
        controlJoueur2.setControlCartePlateau(controlCartePlateau);

        // Vérification
        assertNotNull(controlJoueur, "Le contrôleur joueur ne devrait pas être null");
        assertEquals(joueur, controlJoueur.getJoueur(), "Le joueur devrait être correctement assigné");
    }
    
    @Test
    public void testPiocher() {
        // Création des objets nécessaires
        ControlJeu controlJeu = new ControlJeu();
        ControlPioche controlPioche = controlJeu.getControlPioche();
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));
        ControlJoueur controlJoueur = new ControlJoueur(joueur, controlJeu, controlPioche);

        // Taille initiale de la main
        int tailleInitiale = joueur.getMain().size();

        // Pioche d'une carte
        Carte cartePiochee = controlJoueur.piocher();

        // Vérifications
        assertNotNull(cartePiochee, "La carte piochée ne devrait pas être null");
        assertEquals(tailleInitiale + 1, joueur.getMain().size(), "La taille de la main devrait avoir augmenté de 1");
        assertTrue(joueur.getMain().contains(cartePiochee), "La main devrait contenir la carte piochée");
    }
    
    @Test
    public void testInitialiserMain() {
        // Création des objets
        ControlJeu controlJeu = new ControlJeu();
        ControlPioche controlPioche = controlJeu.getControlPioche();
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));
        ControlJoueur controlJoueur = new ControlJoueur(joueur, controlJeu, controlPioche);

        // Initialisation de la main
        controlJoueur.initialiserMain();

        // Vérification - la méthode initialiserMain() pioche 5 cartes
        assertEquals(5, joueur.getMain().size(), "La main devrait contenir 5 cartes après initialisation");
    }
    
    @Test
    public void testJouerCarte() {
        // Création des objets nécessaires
        ControlJeu controlJeu = new ControlJeu();
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

        assertTrue(resultat, "La carte devrait pouvoir être jouée");
        assertEquals(tailleInitiale - 1, controlJoueur1.getJoueur().getMain().size(), "La taille de la main devrait avoir diminué de 1");
        assertFalse(controlJoueur1.getJoueur().getMain().contains(carteOffensive), "La carte ne devrait plus être dans la main du joueur");
    }
    
    @Test
    public void testPerdrePointsDeVie() {
        // Création des objets nécessaires
        ControlJeu controlJeu = new ControlJeu();
        ControlPioche controlPioche = controlJeu.getControlPioche();
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));
        ControlJoueur controlJoueur = new ControlJoueur(joueur, controlJeu, controlPioche);

        // Points de vie initiaux
        int vieInitiale = joueur.getPointsDeVie();

        // Perdre des points de vie
        controlJoueur.perdrePointsDeVie(2);

        // Vérification
        assertEquals(vieInitiale - 2, joueur.getPointsDeVie(), "Les points de vie devraient avoir diminué de 2");
    }
    
    @Test
    public void testGagnerPopularite() {
        // Création des objets nécessaires
        ControlJeu controlJeu = new ControlJeu();
        ControlPioche controlPioche = controlJeu.getControlPioche();
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));
        ControlJoueur controlJoueur = new ControlJoueur(joueur, controlJeu, controlPioche);

        // Popularité initiale
        int popInitiale = joueur.getPopularite();

        // Gagner de la popularité
        controlJoueur.gagnerPopularite(2);

        // Vérification
        assertEquals(popInitiale + 2, joueur.getPopularite(), "La popularité devrait avoir augmenté de 2");
    }
    
    @Test
    public void testRecevoirEffets() {
        // Création des objets nécessaires
        ControlJeu controlJeu = new ControlJeu();
        ControlPioche controlPioche = controlJeu.getControlPioche();
        Joueur joueur = new Joueur("TestJoueur", new Pirate("Jack Sparrow"));
        ControlJoueur controlJoueur = new ControlJoueur(joueur, controlJeu, controlPioche);

        // État initial
        int vieInitiale = joueur.getPointsDeVie();
        int popInitiale = joueur.getPopularite();

        // Recevoir des effets (degats, popularite)
        controlJoueur.recevoirEffets(2, 1);

        assertEquals(vieInitiale - 2, joueur.getPointsDeVie(), "Les points de vie devraient avoir diminué de 2");
        assertEquals(popInitiale + 1, joueur.getPopularite(), "La popularité devrait avoir augmenté de 1");

        vieInitiale = joueur.getPointsDeVie();
        popInitiale = joueur.getPopularite();

        controlJoueur.recevoirEffets(-2, -1);

        assertEquals(vieInitiale + 2, joueur.getPointsDeVie(), "Les points de vie devraient avoir augmenté de 2");
        assertEquals(popInitiale - 1, joueur.getPopularite(), "La popularité devrait avoir diminué de 1");
    }
    
    @Test
    public void testJouerTour() {
        // Création des objets nécessaires
        ControlJeu controlJeu = new ControlJeu();
        ControlPioche controlPioche = controlJeu.getControlPioche();

        controlJeu.creerJoueur("TestJoueur1", "Jack Sparrow");
        controlJeu.creerJoueur("TestJoueur2", "Barbe Noire");
        ControlJoueur controlJoueur1 = controlJeu.getJoueur(0);

        controlJoueur1.initialiserMain();

        int tailleAvant = controlJoueur1.getJoueur().getMain().size();

        controlJoueur1.jouerTour();

        // La méthode jouerTour pioche une carte PUIS en joue une. Le nombre de cartes ne change donc pas.
        assertEquals(tailleAvant, controlJoueur1.getJoueur().getMain().size(), "La main devrait avoir le même nombre de cartes après la pioche et le jeu du tour");
    }
}