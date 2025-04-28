package test;

import static org.junit.Assert.assertNotNull;

import carte.CarteAttaque;
import carte.CartePopularite;
import controllers.ControlCartePlateau;
import controllers.ControlJoueur;
import controllers.ControlPioche;
import jeu.ZoneAttaque;
import jeu.ZonePop;
import joueur.Joueur;
import joueur.Pirate;

/**
 * @brief Test unitaire de la classe ControlCartePlateau
 */
public class TestControlCartePlateau {
    public static void main(String[] args) {
        // Test de création d'un ControlCartePlateau
        testCreationControlCartePlateau();
        
        // Test d'ajout de carte d'attaque
        testAjouterCarteAttaque();
        
        // Test d'ajout de carte de popularité
        testAjouterCartePopularite();
        
        // Test de l'application des effets de cartes d'attaque
        testAppliquerEffetsCartesAttaque();
        
        // Test de l'application des effets de cartes de popularité
        testAppliquerEffetsCartesPopularite();
        
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
    
    private static void testAjouterCarteAttaque() {
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
        
        // Création d'une carte d'attaque
        CarteAttaque carteAttaque = new CarteAttaque("Épée", "Une épée tranchante", 2, 2);
        
        // Taille initiale de la zone d'attaque
        ZoneAttaque zoneAttaque = cCartePlateau.getZoneAttaque();
        int tailleInitiale = zoneAttaque.getCartesAttaque().size();
        
        // Ajout de la carte
        cCartePlateau.ajouterCarteAttaque(carteAttaque);
        
        // Vérification
        assertEquals("La taille de la zone d'attaque devrait avoir augmenté de 1", 
                   tailleInitiale + 1, zoneAttaque.getCartesAttaque().size());
        assertTrue("La zone d'attaque devrait contenir la carte ajoutée", 
                 zoneAttaque.getCartesAttaque().contains(carteAttaque));
        
        System.out.println("Test d'ajout de carte d'attaque réussi!");
    }
    
    private static void testAjouterCartePopularite() {
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
        
        // Création d'une carte de popularité
        CartePopularite cartePop = new CartePopularite("Chanson", "Une chanson entrainante", 2, 2);
        
        // Taille initiale de la zone de popularité
        ZonePop zonePop = cCartePlateau.getZonePop();
        int tailleInitiale = zonePop.getCartesPopularite().size();
        
        // Ajout de la carte
        cCartePlateau.ajouterCartePopularite(cartePop);
        
        // Vérification
        assertEquals("La taille de la zone de popularité devrait avoir augmenté de 1", 
                   tailleInitiale + 1, zonePop.getCartesPopularite().size());
        assertTrue("La zone de popularité devrait contenir la carte ajoutée", 
                 zonePop.getCartesPopularite().contains(cartePop));
        
        System.out.println("Test d'ajout de carte de popularité réussi!");
    }
    
    private static void testAppliquerEffetsCartesAttaque() {
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
        
        // Points de vie initiaux du joueur 2
        int vieInitiale = joueur2.getPointsDeVie();
        
        // Ajout de cartes d'attaque
        CarteAttaque carte1 = new CarteAttaque("Épée", "Une épée tranchante", 1, 1);
        CarteAttaque carte2 = new CarteAttaque("Pistolet", "Un pistolet puissant", 2, 2);
        cCartePlateau.ajouterCarteAttaque(carte1);
        cCartePlateau.ajouterCarteAttaque(carte2);
        
        // Application des effets
        cCartePlateau.appliquerEffetsCartesAttaque();
        
        // Vérification
        assertEquals("Les points de vie du joueur 2 devraient avoir diminué de 3", 
                   vieInitiale - 3, joueur2.getPointsDeVie());
        
        System.out.println("Test d'application des effets de cartes d'attaque réussi!");
    }
    
    private static void testAppliquerEffetsCartesPopularite() {
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
        
        // Popularité initiale du joueur 1
        int popInitiale = joueur1.getPopularite();
        
        // Ajout de cartes de popularité
        CartePopularite carte1 = new CartePopularite("Chanson", "Une chanson entraînante", 1, 1);
        CartePopularite carte2 = new CartePopularite("Danse", "Une danse spectaculaire", 2, 2);
        cCartePlateau.ajouterCartePopularite(carte1);
        cCartePlateau.ajouterCartePopularite(carte2);
        
        // Application des effets
        cCartePlateau.appliquerEffetsCartesPopularite();
        
        // Vérification
        assertEquals("La popularité du joueur 1 devrait avoir augmenté de 3", 
                   popInitiale + 3, joueur1.getPopularite());
        
        System.out.println("Test d'application des effets de cartes de popularité réussi!");
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
        CarteAttaque carteAttaque = new CarteAttaque("Épée", "Une épée tranchante", 2, 2);
        CartePopularite cartePop = new CartePopularite("Chanson", "Une chanson entraînante", 2, 2);
        cCartePlateau.ajouterCarteAttaque(carteAttaque);
        cCartePlateau.ajouterCartePopularite(cartePop);
        
        // Défausse des cartes
        cCartePlateau.defausserCartesPlateau();
        
        // Vérification
        assertEquals("La zone d'attaque devrait être vide", 
                   0, cCartePlateau.getZoneAttaque().getCartesAttaque().size());
        assertEquals("La zone de popularité devrait être vide", 
                   0, cCartePlateau.getZonePop().getCartesPopularite().size());
        
        System.out.println("Test de défausse des cartes du plateau réussi!");
    }
}