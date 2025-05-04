package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
    public static void main(String[] args) {
        // Test de création d'un ControlCartePlateau
        testCreationControlCartePlateau();
        
        // Test d'ajout de carte offensive
        testAjouterCarteOffensive();
        
        // Test d'ajout de carte stratégique
        testAjouterCarteStrategique();
        
        // Test de l'application des effets de cartes offensives
        testAppliquerEffetsCartesOffensives();
        
        // Test de l'application des effets de cartes stratégiques
        testAppliquerEffetsCartesStrategiques();
        
        // Test de défausse des cartes du plateau
        testDefausserCartesPlateau();
        
        System.out.println("Tous les tests de ControlCartePlateau ont réussi!");
    }
    
    private static void testCreationControlCartePlateau() {
        // Création des objets nécessaires
        ControlPioche cPioche = new ControlPioche();
        ControlJoueur cJoueur1 = new ControlJoueur(null, null, cPioche);
        ControlJoueur cJoueur2 = new ControlJoueur(null, null, cPioche);
        
        // Création du contrôleur
        ControlCartePlateau cCartePlateau = new ControlCartePlateau(cJoueur1, cJoueur2);
        
        // Vérification
        assertNotNull("Le contrôleur de carte plateau ne devrait pas être null", cCartePlateau);
        System.out.println("Test de création de ControlCartePlateau réussi!");
    }
    
    private static void testAjouterCarteOffensive() {
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
        assertEquals("La zone offensive J1 devrait contenir 1 carte", 
                   1, cCartePlateau.getZoneOffensiveJ1().getCartesOffensives().size());
        assertTrue("La zone offensive J1 devrait contenir la carte ajoutée", 
                 cCartePlateau.getZoneOffensiveJ1().getCartesOffensives().contains(carteOffensive));
        assertEquals("La zone offensive J2 ne devrait pas contenir de carte", 
                   0, cCartePlateau.getZoneOffensiveJ2().getCartesOffensives().size());
        
        System.out.println("Test d'ajout de carte offensive réussi!");
    }
    
    private static void testAjouterCarteStrategique() {
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
        assertEquals("La zone stratégique J1 devrait contenir 1 carte", 
                   1, cCartePlateau.getZoneStrategiqueJ1().getCartesStrategiques().size());
        assertTrue("La zone stratégique J1 devrait contenir la carte ajoutée", 
                 cCartePlateau.getZoneStrategiqueJ1().getCartesStrategiques().contains(carteStrat));
        assertEquals("La zone stratégique J2 ne devrait pas contenir de carte", 
                   0, cCartePlateau.getZoneStrategiqueJ2().getCartesStrategiques().size());
        
        System.out.println("Test d'ajout de carte stratégique réussi!");
    }
    
    private static void testAppliquerEffetsCartesOffensives() {
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
        
        System.out.println("DEBUG - AVANT: J1 PV=" + vieInitialeJ1 + " Or=" + orInitialJ1);
        System.out.println("DEBUG - AVANT: J2 PV=" + vieInitialeJ2 + " Or=" + orInitialJ2);
        
        // Ajout de cartes offensives
        // J1 joue : Attaque (2 dégâts infligés, 1 subi), Soin (3 PV), Vol (8 Or)
        CarteOffensive attaqueJ1 = new CarteOffensive("Canon", "", 2, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        CarteOffensive soinJ1 = new CarteOffensive("Potion", "", 3); // Constructeur Soin
        CarteOffensive volJ1 = new CarteOffensive("Vol de Butin", "", 8, true); // Constructeur Tresor Offensif
        cCartePlateau.ajouterCarteOffensiveJ1(attaqueJ1);
        cCartePlateau.ajouterCarteOffensiveJ1(soinJ1);
        cCartePlateau.ajouterCarteOffensiveJ1(volJ1);
        
        // J2 joue : Attaque (1 dégât infligé, 0 subi)
        CarteOffensive attaqueJ2 = new CarteOffensive("Mousquet", "", 1, 0, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        cCartePlateau.ajouterCarteOffensiveJ2(attaqueJ2);
        
        System.out.println("DEBUG - Cartes J1: " + cCartePlateau.getZoneOffensiveJ1().getCartesOffensives().size());
        for (CarteOffensive c : cCartePlateau.getZoneOffensiveJ1().getCartesOffensives()) {
            System.out.println("DEBUG - Carte J1: " + c.getNomCarte() + ", Type=" + c.getTypeOffensif() + 
                              (c.getTypeOffensif() == CarteOffensive.TypeOffensif.SOIN ? ", Soin=" + c.getVieGagnee() : ""));
        }
        
        // Application des effets
        cCartePlateau.appliquerEffetsCartesOffensives();
        
        System.out.println("DEBUG - APRES: J1 PV=" + joueur1.getPointsDeVie() + " Or=" + joueur1.getOr());
        System.out.println("DEBUG - APRES: J2 PV=" + joueur2.getPointsDeVie() + " Or=" + joueur2.getOr());
        
        // Vérifications J1
        // Dégâts subis: 1 (attaqueJ1) + 1 (attaqueJ2) = 2
        // Soins reçus: 3 (soinJ1)
        // Total PV J1: vieInitialeJ1 - 2 + 3 = vieInitialeJ1 + 1
        // Mais limité à 5 selon les règles du jeu
        assertEquals("PV J1 incorrects", (long)5, (long)joueur1.getPointsDeVie());
        // Or volé: 8 (volJ1), mais limité à ce que possède J2 (3)
        // Total Or J1: orInitialJ1 + 3
        assertEquals("Or J1 incorrect", (long)(orInitialJ1 + 3), (long)joueur1.getOr());

        // Vérifications J2
        // Dégâts subis: 2 (attaqueJ1)
        // Or perdu: 3 (tout son or, car volJ1 > orInitialJ2)
        // Total PV J2: vieInitialeJ2 - 2
        assertEquals("PV J2 incorrects", (long)(vieInitialeJ2 - 2), (long)joueur2.getPointsDeVie());
        // Total Or J2: 0
        assertEquals("Or J2 incorrect", (long)0, (long)joueur2.getOr());
        
        System.out.println("Test d'application des effets de cartes offensives (amélioré) réussi!");
    }
    
    private static void testAppliquerEffetsCartesStrategiques() {
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
        assertEquals("Popularité J1 incorrecte", (long)(popInitialeJ1 + 2), (long)joueur1.getPopularite());
        // Dégâts subis: 1 (popJ1)
        assertEquals("PV J1 incorrects", (long)(vieInitialeJ1 - 1), (long)joueur1.getPointsDeVie());
        // Or gagné: 10 (tresorJ1)
        assertEquals("Or J1 incorrect", (long)(orInitialJ1 + 10), (long)joueur1.getOr());

        // Vérifications J2
        // Popularité gagnée: 1 (popJ2)
        assertEquals("Popularité J2 incorrecte", (long)(popInitialeJ2 + 1), (long)joueur2.getPopularite());
        // Dégâts subis: 0 (popJ2)
        assertEquals("PV J2 incorrects", (long)vieInitialeJ2, (long)joueur2.getPointsDeVie());
        // Or gagné: 0
        assertEquals("Or J2 incorrect", (long)orInitialJ2, (long)joueur2.getOr());
        
        System.out.println("Test d'application des effets de cartes stratégiques (amélioré) réussi!");
    }
    
    private static void testDefausserCartesPlateau() {
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
        assertEquals("La zone offensive J1 devrait être vide après défausse", 
                   0, cCartePlateau.getZoneOffensiveJ1().getCartesOffensives().size());
        assertEquals("La zone offensive J2 devrait être vide après défausse", 
                   0, cCartePlateau.getZoneOffensiveJ2().getCartesOffensives().size());
        assertEquals("La zone stratégique J1 devrait être vide après défausse", 
                   0, cCartePlateau.getZoneStrategiqueJ1().getCartesStrategiques().size());
        assertEquals("La zone stratégique J2 devrait être vide après défausse", 
                   0, cCartePlateau.getZoneStrategiqueJ2().getCartesStrategiques().size());
        
        System.out.println("Test de défausse des cartes du plateau réussi!");
    }
}