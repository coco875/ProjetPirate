package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import carte.CarteOffensive;
import carte.CarteStrategique;
import controllers.ControlCartePlateau;
import controllers.ControlJoueur;
import controllers.ControlPioche;
import controllers.ControlJeu;
import jeu.ZoneStrategique;
import joueur.Joueur;
import joueur.Pirate;

/**
 * @brief Test unitaire de la classe ControlCartePlateau
 */
public class TestControlCartePlateau {
    
    @Test
    public void testCreationControlCartePlateau() {
        // Création des objets nécessaires
        ControlPioche cPioche = new ControlPioche();
        ControlJoueur cJoueur1 = new ControlJoueur(null, null, cPioche);
        ControlJoueur cJoueur2 = new ControlJoueur(null, null, cPioche);
        
        // Création du contrôleur
        ControlCartePlateau cCartePlateau = new ControlCartePlateau(cJoueur1, cJoueur2);
        
        // Vérification
        assertNotNull(cCartePlateau, "Le contrôleur de carte plateau ne devrait pas être null");
    }
    
    @Test
    public void testAjouterCarteOffensive() {
        // Création des objets nécessaires
        ControlPioche cPioche = new ControlPioche();
        Joueur joueur1 = new Joueur("TestJoueur1", new Pirate("Jack Sparrow"));
        Joueur joueur2 = new Joueur("TestJoueur2", new Pirate("Barbe Noire"));
        ControlJoueur cJoueur1 = new ControlJoueur(joueur1, null, cPioche);
        ControlJoueur cJoueur2 = new ControlJoueur(joueur2, null, cPioche);
        
        // Création du contrôleur
        ControlCartePlateau cCartePlateau = new ControlCartePlateau(cJoueur1, cJoueur2);
        cJoueur1.setControlCartePlateau(cCartePlateau);
        cJoueur2.setControlCartePlateau(cCartePlateau);
        
        // Création d'une carte offensive
        CarteOffensive carteOffensive = new CarteOffensive("Épée", "Une épée tranchante", 2, 2,
                                                         CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        
        // Ajout de la carte pour joueur 1
        cCartePlateau.ajouterCarteOffensiveJ1(carteOffensive);
        assertEquals(1, cCartePlateau.getZoneOffensiveJ1().getCartesOffensives().size(), 
                    "La zone offensive J1 devrait contenir 1 carte");
        assertTrue(cCartePlateau.getZoneOffensiveJ1().getCartesOffensives().contains(carteOffensive),
                  "La zone offensive J1 devrait contenir la carte ajoutée");
        assertEquals(0, cCartePlateau.getZoneOffensiveJ2().getCartesOffensives().size(),
                    "La zone offensive J2 ne devrait pas contenir de carte");
    }
    
    @Test
    public void testAjouterCarteStrategique() {
        // Création des objets nécessaires
        ControlPioche cPioche = new ControlPioche();
        Joueur joueur1 = new Joueur("TestJoueur1", new Pirate("Jack Sparrow"));
        Joueur joueur2 = new Joueur("TestJoueur2", new Pirate("Barbe Noire"));
        ControlJoueur cJoueur1 = new ControlJoueur(joueur1, null, cPioche);
        ControlJoueur cJoueur2 = new ControlJoueur(joueur2, null, cPioche);
        
        // Création du contrôleur
        ControlCartePlateau cCartePlateau = new ControlCartePlateau(cJoueur1, cJoueur2);
        cJoueur1.setControlCartePlateau(cCartePlateau);
        cJoueur2.setControlCartePlateau(cCartePlateau);
        
        // Création d'une carte stratégique
        CarteStrategique carteStrat = new CarteStrategique("Chanson", "Une chanson entrainante", 2, 2);
        
        // Ajout de la carte pour joueur 1
        cCartePlateau.ajouterCarteStrategiqueJ1(carteStrat);
        assertEquals(1, cCartePlateau.getZoneStrategiqueJ1().getCartesStrategiques().size(),
                    "La zone stratégique J1 devrait contenir 1 carte");
        assertTrue(cCartePlateau.getZoneStrategiqueJ1().getCartesStrategiques().contains(carteStrat),
                  "La zone stratégique J1 devrait contenir la carte ajoutée");
        assertEquals(0, cCartePlateau.getZoneStrategiqueJ2().getCartesStrategiques().size(),
                    "La zone stratégique J2 ne devrait pas contenir de carte");
    }
    
    @Test
    public void testAppliquerEffetsCartesOffensives() {
        // Création des objets nécessaires via ControlJeu
        ControlJeu controlJeu = new ControlJeu();
        
        // Créer les joueurs via ControlJeu
        Joueur joueur1 = controlJeu.creerJoueur("TestJoueur1", "Jack Sparrow");
        Joueur joueur2 = controlJeu.creerJoueur("TestJoueur2", "Barbe Noire");
        
        // Récupérer les contrôleurs des joueurs et du plateau
        ControlJoueur cJoueur1 = controlJeu.getJoueur(0);
        ControlJoueur cJoueur2 = controlJeu.getJoueur(1);
        ControlCartePlateau cCartePlateau = controlJeu.getControlCartePlateau();
        
        // Forcer les valeurs initiales pour le test
        joueur1.setVie(5);
        joueur2.setVie(5);
        joueur1.setOr(3);
        joueur2.setOr(3);
        
        // Valeurs initiales
        int vieInitialeJ1 = joueur1.getPointsDeVie();
        int vieInitialeJ2 = joueur2.getPointsDeVie();
        int orInitialJ1 = joueur1.getOr();
        int orInitialJ2 = joueur2.getOr();
        
        // Ajout de cartes offensives
        // J1 joue : Attaque (2 dégâts infligés, 1 subi), Soin (3 PV)
        // La carte "Vol de Butin" est remplacée par une attaque normale
        CarteOffensive attaqueJ1 = new CarteOffensive("Canon", "", 2, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        CarteOffensive soinJ1 = new CarteOffensive("Potion", "", 3); // Constructeur Soin
        CarteOffensive attaqueExtraJ1 = new CarteOffensive("Vol de Butin", "Cette carte était auparavant une carte de vol", 1, 0, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        cCartePlateau.ajouterCarteOffensiveJ1(attaqueJ1);
        cCartePlateau.ajouterCarteOffensiveJ1(soinJ1);
        cCartePlateau.ajouterCarteOffensiveJ1(attaqueExtraJ1);
        
        // J2 joue : Attaque (1 dégât infligé, 0 subi)
        CarteOffensive attaqueJ2 = new CarteOffensive("Mousquet", "", 1, 0, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        cCartePlateau.ajouterCarteOffensiveJ2(attaqueJ2);
        
        // Application des effets
        cCartePlateau.appliquerEffetsCartesOffensives();
        
        // Vérifications modifiées - plus de vol d'or
        // Dégâts subis par J1: 1 (attaqueJ1) + 1 (attaqueJ2) = 2
        // Soins reçus par J1: 3 (soinJ1)
        // Total PV J1: vieInitialeJ1 - 2 + 3 = vieInitialeJ1 + 1, mais limité à 5
        assertEquals(5, joueur1.getPointsDeVie(), "PV J1 incorrects");
        // Or de J1 inchangé (plus de vol d'or)
        assertEquals(orInitialJ1, joueur1.getOr(), "Or J1 incorrect");

        // Dégâts subis par J2: 2 (attaqueJ1) + 1 (attaqueExtraJ1) = 3
        // Total PV J2: vieInitialeJ2 - 3
        assertEquals(vieInitialeJ2 - 3, joueur2.getPointsDeVie(), "PV J2 incorrects");
        // Or de J2 inchangé (plus de vol d'or)
        assertEquals(orInitialJ2, joueur2.getOr(), "Or J2 incorrect");
    }
    
    @Test
    public void testAppliquerEffetsCartesStrategiques() {
        // Création des objets nécessaires via ControlJeu
        ControlJeu controlJeu = new ControlJeu();
        
        // Créer les joueurs via ControlJeu
        Joueur joueur1 = controlJeu.creerJoueur("TestJoueur1", "Jack Sparrow");
        Joueur joueur2 = controlJeu.creerJoueur("TestJoueur2", "Barbe Noire");
        
        // Récupérer les contrôleurs des joueurs et du plateau
        ControlJoueur cJoueur1 = controlJeu.getJoueur(0);
        ControlJoueur cJoueur2 = controlJeu.getJoueur(1);
        ControlCartePlateau cCartePlateau = controlJeu.getControlCartePlateau();
        
        // Valeurs initiales
        int popInitialeJ1 = joueur1.getPopularite();
        int popInitialeJ2 = joueur2.getPopularite();
        int orInitialJ1 = joueur1.getOr();
        int orInitialJ2 = joueur2.getOr();
        int vieInitialeJ1 = joueur1.getPointsDeVie();
        int vieInitialeJ2 = joueur2.getPointsDeVie();

        // Ajout de cartes stratégiques
        // J1 joue : Popularité (2 pop gagnée, 1 dégât subi), Trésor (10 or gagné)
        CarteStrategique popJ1 = new CarteStrategique("Chanson", "", 2, 1); // Constructeur Popularité
        // Utiliser le bon constructeur pour Tresor (orGagne, orPerdu, estTresor)
        CarteStrategique tresorJ1 = new CarteStrategique("Coffre", "", 10, 0, true);
        cCartePlateau.ajouterCarteStrategiqueJ1(popJ1);
        cCartePlateau.ajouterCarteStrategiqueJ1(tresorJ1);
        
        // J2 joue : Popularité (1 pop gagnée, 0 dégât subi)
        CarteStrategique popJ2 = new CarteStrategique("Danse", "", 1, 0); // Constructeur Popularité
        cCartePlateau.ajouterCarteStrategiqueJ2(popJ2);
        
        // Application des effets
        cCartePlateau.appliquerEffetsCartesStrategiques();
        
        // Vérifications J1
        // Popularité gagnée: 2 (popJ1)
        assertEquals(popInitialeJ1 + 2, joueur1.getPopularite(), "Popularité J1 incorrecte");
        // Dégâts subis: 1 (popJ1)
        assertEquals(vieInitialeJ1 - 1, joueur1.getPointsDeVie(), "PV J1 incorrects");
        // Or gagné: 10 (tresorJ1)
        assertEquals(orInitialJ1 + 10, joueur1.getOr(), "Or J1 incorrect");

        // Vérifications J2
        // Popularité gagnée: 1 (popJ2)
        assertEquals(popInitialeJ2 + 1, joueur2.getPopularite(), "Popularité J2 incorrecte");
        // Dégâts subis: 0 (popJ2)
        assertEquals(vieInitialeJ2, joueur2.getPointsDeVie(), "PV J2 incorrects");
        // Or gagné: 0
        assertEquals(orInitialJ2, joueur2.getOr(), "Or J2 incorrect");
    }
    
    @Test
    public void testDefausserCartesPlateau() {
        // Création des objets nécessaires
        ControlPioche cPioche = new ControlPioche();
        Joueur joueur1 = new Joueur("TestJoueur1", new Pirate("Jack Sparrow"));
        Joueur joueur2 = new Joueur("TestJoueur2", new Pirate("Barbe Noire"));
        ControlJoueur cJoueur1 = new ControlJoueur(joueur1, null, cPioche);
        ControlJoueur cJoueur2 = new ControlJoueur(joueur2, null, cPioche);
        
        // Création du contrôleur
        ControlCartePlateau cCartePlateau = new ControlCartePlateau(cJoueur1, cJoueur2);
        cJoueur1.setControlCartePlateau(cCartePlateau);
        cJoueur2.setControlCartePlateau(cCartePlateau);
        
        // Ajout de cartes
        CarteOffensive carteOffensive = new CarteOffensive("Épée", "Une épée tranchante", 2, 2,
                                                         CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        CarteStrategique carteStrat = new CarteStrategique("Chanson", "Une chanson entraînante", 2, 2);
        cCartePlateau.ajouterCarteOffensiveJ1(carteOffensive);
        cCartePlateau.ajouterCarteStrategiqueJ2(carteStrat);
        
        // Défausse des cartes
        cCartePlateau.defausserCartesPlateau();
        
        // Vérification
        assertEquals(0, cCartePlateau.getZoneOffensiveJ1().getCartesOffensives().size(),
                    "La zone offensive J1 devrait être vide après défausse");
        assertEquals(0, cCartePlateau.getZoneOffensiveJ2().getCartesOffensives().size(),
                    "La zone offensive J2 devrait être vide après défausse");
        assertEquals(0, cCartePlateau.getZoneStrategiqueJ1().getCartesStrategiques().size(),
                    "La zone stratégique J1 devrait être vide après défausse");
        assertEquals(0, cCartePlateau.getZoneStrategiqueJ2().getCartesStrategiques().size(),
                    "La zone stratégique J2 devrait être vide après défausse");
    }
}