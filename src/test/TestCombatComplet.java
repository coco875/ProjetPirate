package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import carte.CarteAttaque;
import carte.CarteSoin;
import carte.CartePopularite;
import carte.CarteTresor;
import controllers.ControlCartePlateau;
import controllers.ControlJeu;
import controllers.ControlJoueur;
import joueur.Joueur;
import joueur.Pirate;

/**
 * Test complet d'un combat entre deux joueurs utilisant différents types de cartes
 */
public class TestCombatComplet {

    private ControlJeu controlJeu;
    private ControlJoueur controlJoueur1;
    private ControlJoueur controlJoueur2;
    private Joueur joueur1;
    private Joueur joueur2;
    private ControlCartePlateau controlCartePlateau;

    @BeforeEach
    public void initialiser() {
        // Initialisation du jeu
        controlJeu = new ControlJeu();
        Pirate pirate1 = new Pirate("Jack Sparrow");
        Pirate pirate2 = new Pirate("Barbe Noire");
        
        controlJeu.initialiserJeu(pirate1, pirate2);
        
        // Récupération des contrôleurs
        controlJoueur1 = controlJeu.getJoueur(0);
        controlJoueur2 = controlJeu.getJoueur(1);
        
        // Récupération des joueurs
        joueur1 = controlJoueur1.getJoueur();
        joueur2 = controlJoueur2.getJoueur();
        
        // Initialisation des points de vie
        joueur1.setVie(5);
        joueur2.setVie(5);
        
        // Initialisation de l'or
        joueur1.setOr(10);
        joueur2.setOr(10);
        
        // Récupération du contrôleur du plateau
        controlCartePlateau = controlJeu.getControlCartePlateau();
    }
    
    @Test
    @DisplayName("Test d'un combat complet avec différents types de cartes")
    public void testCombatComplet() {
        // Création des cartes pour le joueur 1
        CarteAttaque carteAttaqueJ1 = new CarteAttaque("Épée", "Une épée tranchante", 3, 2, 1);
        CarteSoin carteSoinJ1 = new CarteSoin("Potion", "Une potion magique", 2);
        CartePopularite cartePopulariteJ1 = new CartePopularite("Discours", "Un discours motivant", 2, 0);
        
        // Création des cartes pour le joueur 2
        CarteAttaque carteAttaqueJ2 = new CarteAttaque("Pistolet", "Un pistolet précis", 2, 1, 0);
        CarteTresor carteTresorJ2 = new CarteTresor("Coffre", "Un coffre plein d'or", 5);
        
        // Ajout des cartes aux mains des joueurs
        joueur1.ajouterCarte(carteAttaqueJ1);
        joueur1.ajouterCarte(carteSoinJ1);
        joueur1.ajouterCarte(cartePopulariteJ1);
        
        joueur2.ajouterCarte(carteAttaqueJ2);
        joueur2.ajouterCarte(carteTresorJ2);
        
        // Premier tour : les deux joueurs jouent une carte d'attaque
        controlJoueur1.jouerCarte(carteAttaqueJ1);
        controlJoueur2.jouerCarte(carteAttaqueJ2);

        
        // Application des effets
        controlCartePlateau.appliquerEffetCarte();
        
        // Vérifications après le premier tour
        assertEquals(3, joueur1.getPointsDeVie(), "Joueur 1 devrait avoir 3 points de vie après le tour 1");
        assertEquals(3, joueur2.getPointsDeVie(), "Joueur 2 devrait avoir 3 points de vie après le tour 1");
        
        // Défausse des cartes du plateau
        controlCartePlateau.defausserCartesPlateau();
        
        // Deuxième tour : J1 joue une carte de soin, J2 joue une carte de trésor
        controlJoueur1.jouerCarte(carteSoinJ1);
        controlJoueur2.jouerCarte(carteTresorJ2);
        
        // Application des effets
        controlCartePlateau.appliquerEffetCarte();
        
        // Vérifications après le deuxième tour
        assertEquals(5, joueur1.getPointsDeVie(), "Joueur 1 devrait avoir 5 points de vie (max) après le tour 2");
        assertEquals(3, joueur2.getPointsDeVie(), "Joueur 2 devrait toujours avoir 3 points de vie après le tour 2");
        assertEquals(10, joueur1.getOr(), "Joueur 1 devrait toujours avoir 10 or après le tour 2");
        assertEquals(15, joueur2.getOr(), "Joueur 2 devrait avoir 15 or après le tour 2");
        
        // Défausse des cartes du plateau
        controlCartePlateau.defausserCartesPlateau();
        
        // Troisième tour : J1 joue une carte de popularité
        controlJoueur1.jouerCarte(cartePopulariteJ1);
        
        // Application des effets
        controlCartePlateau.appliquerEffetCarte();
        
        // Vérifications après le troisième tour
        assertEquals(5, joueur1.getPointsDeVie(), "Joueur 1 devrait toujours avoir 5 points de vie après le tour 3");
        assertEquals(3, joueur2.getPointsDeVie(), "Joueur 2 devrait toujours avoir 3 points de vie après le tour 3");
        assertEquals(2, joueur1.getPopularite(), "Joueur 1 devrait avoir 2 points de popularité après le tour 3");
        assertEquals(0, joueur2.getPopularite(), "Joueur 2 devrait avoir 0 points de popularité après le tour 3");
    }
}
