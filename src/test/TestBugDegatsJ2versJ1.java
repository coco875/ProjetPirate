package test;

import static org.junit.Assert.assertEquals;

import carte.CarteOffensive;
import controllers.ControlCartePlateau;
import controllers.ControlJeu;
import joueur.Joueur;

/**
 * Test spécifique pour détecter et corriger le bug où le joueur 2 n'inflige
 * pas correctement des dégâts au joueur 1.
 */
public class TestBugDegatsJ2versJ1 {

    public static void main(String[] args) {
        System.out.println("Test pour vérifier l'application des dégâts du joueur 2 vers le joueur 1");
        
        // Cas 1: Test avec une carte d'attaque directe jouée par le joueur 2
        testAttaqueJ2versJ1();
        
        // Cas 2: Test simulant une partie complète avec plusieurs attaques
        testPartieComplete();
        
        System.out.println("Tous les tests ont réussi!");
    }

    /**
     * Test simple: le joueur 2 joue une carte d'attaque directe contre le joueur 1
     */
    private static void testAttaqueJ2versJ1() {
        System.out.println("--- Test d'attaque simple J2 vers J1 ---");
        
        // Initialisation
        ControlJeu controlJeu = new ControlJeu();
        Joueur joueur1 = controlJeu.creerJoueur("Joueur1", "Jack Sparrow");
        Joueur joueur2 = controlJeu.creerJoueur("Joueur2", "Barbe Noire");
        
        // On s'assure que les joueurs ont bien 5 points de vie au départ
        joueur1.setVie(5);
        joueur2.setVie(5);
        
        // Récupération du contrôleur de plateau
        ControlCartePlateau cCartePlateau = controlJeu.getControlCartePlateau();
        
        // Le joueur 2 joue une carte d'attaque qui inflige 2 dégâts
        CarteOffensive attaqueJ2 = new CarteOffensive("Canon", "Un puissant canon", 2, 0, 
                                                     CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        cCartePlateau.ajouterCarteOffensiveJ2(attaqueJ2);
        
        // Affichage des points de vie avant l'attaque
        System.out.println("Avant attaque: J1 PV=" + joueur1.getPointsDeVie() + ", J2 PV=" + joueur2.getPointsDeVie());
        
        // Application des effets des cartes
        cCartePlateau.appliquerEffetsCartesOffensives();
        
        // Affichage des points de vie après l'attaque
        System.out.println("Après attaque: J1 PV=" + joueur1.getPointsDeVie() + ", J2 PV=" + joueur2.getPointsDeVie());
        
        // Vérification: le joueur 1 doit avoir perdu 2 points de vie
        assertEquals("Le joueur 1 devrait avoir perdu 2 points de vie", 3, joueur1.getPointsDeVie());
        
        // Vérification: le joueur 2 ne doit pas avoir perdu de points de vie
        assertEquals("Le joueur 2 ne devrait pas avoir perdu de points de vie", 5, joueur2.getPointsDeVie());
    }

    /**
     * Test plus complet simulant une partie avec des attaques des deux côtés
     */
    private static void testPartieComplete() {
        System.out.println("--- Test de partie complète avec attaques des deux joueurs ---");
        
        // Initialisation
        ControlJeu controlJeu = new ControlJeu();
        Joueur joueur1 = controlJeu.creerJoueur("Joueur1", "Jack Sparrow");
        Joueur joueur2 = controlJeu.creerJoueur("Joueur2", "Barbe Noire");
        
        // On s'assure que les joueurs ont bien 5 points de vie au départ
        joueur1.setVie(5);
        joueur2.setVie(5);
        
        // Récupération du contrôleur de plateau
        ControlCartePlateau cCartePlateau = controlJeu.getControlCartePlateau();
        
        // Tour 1: Les deux joueurs jouent des cartes d'attaque
        CarteOffensive attaqueJ1 = new CarteOffensive("Sabre", "Un sabre tranchant", 1, 0, 
                                                    CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        CarteOffensive attaqueJ2 = new CarteOffensive("Pistolet", "Un pistolet précis", 2, 0, 
                                                    CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        
        cCartePlateau.ajouterCarteOffensiveJ1(attaqueJ1);
        cCartePlateau.ajouterCarteOffensiveJ2(attaqueJ2);
        
        // Affichage des points de vie avant l'attaque
        System.out.println("Tour 1 - Avant: J1 PV=" + joueur1.getPointsDeVie() + ", J2 PV=" + joueur2.getPointsDeVie());
        
        // Application des effets des cartes
        cCartePlateau.appliquerEffetsCartesOffensives();
        
        // Affichage des points de vie après l'attaque
        System.out.println("Tour 1 - Après: J1 PV=" + joueur1.getPointsDeVie() + ", J2 PV=" + joueur2.getPointsDeVie());
        
        // Vérification après le tour 1
        assertEquals("Le joueur 1 devrait avoir 3 points de vie après le tour 1", 3, joueur1.getPointsDeVie());
        assertEquals("Le joueur 2 devrait avoir 4 points de vie après le tour 1", 4, joueur2.getPointsDeVie());
        
        // Défausse des cartes du plateau pour le tour suivant
        cCartePlateau.defausserCartesPlateau();
        
        // Tour 2: Le joueur 2 joue une attaque plus puissante
        CarteOffensive attaqueJ2Tour2 = new CarteOffensive("Canon", "Un puissant canon", 3, 0, 
                                                         CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        cCartePlateau.ajouterCarteOffensiveJ2(attaqueJ2Tour2);
        
        // Affichage des points de vie avant l'attaque du tour 2
        System.out.println("Tour 2 - Avant: J1 PV=" + joueur1.getPointsDeVie() + ", J2 PV=" + joueur2.getPointsDeVie());
        
        // Application des effets des cartes pour le tour 2
        cCartePlateau.appliquerEffetsCartesOffensives();
        
        // Affichage des points de vie après l'attaque du tour 2
        System.out.println("Tour 2 - Après: J1 PV=" + joueur1.getPointsDeVie() + ", J2 PV=" + joueur2.getPointsDeVie());
        
        // Vérification après le tour 2
        assertEquals("Le joueur 1 devrait avoir 0 points de vie après le tour 2", 0, joueur1.getPointsDeVie());
        assertEquals("Le joueur 2 devrait toujours avoir 4 points de vie après le tour 2", 4, joueur2.getPointsDeVie());
    }
}