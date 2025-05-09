package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import controllers.*;
import carte.*;
import jeu.*;
import joueur.*;
import java.util.List;

/**
 * Tests JUnit pour la classe ControlCartePlateau
 */
public class TestControlCartePlateauJUnit {
    
    private ControlCartePlateau controlCartePlateau;
    private ControlJoueur controlJoueur1;
    private ControlJoueur controlJoueur2;
    private ControlPioche controlPioche;
    private Joueur joueur1;
    private Joueur joueur2;
    
    @BeforeEach
    public void setUp() {
        // Création des objets nécessaires pour les tests
        controlPioche = new ControlPioche();
        
        // Création des pirates et joueurs
        Pirate pirate1 = new Pirate("Barbe Noire");
        Pirate pirate2 = new Pirate("Jack Sparrow");
        joueur1 = new Joueur("Joueur1", pirate1);
        joueur2 = new Joueur("Joueur2", pirate2);
        
        // Initialisation des points de vie et or
        joueur1.perdrePointsDeVie(-20); // Initialise à 20 PV
        joueur2.perdrePointsDeVie(-20); // Initialise à 20 PV
        joueur1.gagnerOr(10);
        joueur2.gagnerOr(10);
        
        // Création des contrôleurs de joueurs
        controlJoueur1 = new ControlJoueur(joueur1, null, controlPioche);
        controlJoueur2 = new ControlJoueur(joueur2, null, controlPioche);
        
        // Création du contrôleur de plateau de cartes
        controlCartePlateau = new ControlCartePlateau(controlJoueur1, controlJoueur2);
        
        // Mise à jour des contrôleurs de joueurs avec le contrôleur de plateau
        controlJoueur1.setControlCartePlateau(controlCartePlateau);
        controlJoueur2.setControlCartePlateau(controlCartePlateau);
    }
    
    @Test
    @DisplayName("Test du constructeur et des getters de base")
    public void testConstructeurEtGetters() {
        // Vérifier que toutes les zones sont correctement initialisées
        assertNotNull(controlCartePlateau.getZoneOffensiveJ1());
        assertNotNull(controlCartePlateau.getZoneOffensiveJ2());
        assertNotNull(controlCartePlateau.getZoneStrategiqueJ1());
        assertNotNull(controlCartePlateau.getZoneStrategiqueJ2());
        assertNotNull(controlCartePlateau.getDefausse());
        
        // Vérifier que les zones sont vides
        assertTrue(controlCartePlateau.getCartesOffensivesJ1().isEmpty());
        assertTrue(controlCartePlateau.getCartesOffensivesJ2().isEmpty());
        assertTrue(controlCartePlateau.getCartesStrategiquesJ1().isEmpty());
        assertTrue(controlCartePlateau.getCartesStrategiquesJ2().isEmpty());
    }
    
    @Test
    @DisplayName("Test de la méthode setJoueurs")
    public void testSetJoueurs() {
        // Créer de nouveaux contrôleurs de joueurs
        Joueur nouveauJoueur1 = new Joueur("Nouveau1", new Pirate("Pirate1"));
        Joueur nouveauJoueur2 = new Joueur("Nouveau2", new Pirate("Pirate2"));
        
        ControlJoueur nouveauControlJoueur1 = new ControlJoueur(nouveauJoueur1, null, controlPioche);
        ControlJoueur nouveauControlJoueur2 = new ControlJoueur(nouveauJoueur2, null, controlPioche);
        
        // Mettre à jour les joueurs
        controlCartePlateau.setJoueurs(nouveauControlJoueur1, nouveauControlJoueur2);
        
        // Maintenant nous devons tester si les joueurs ont été correctement définis
        // Pour cela, nous pouvons ajouter des cartes et voir si les effets sont appliqués aux bons joueurs
        
        // Ajouter une carte d'attaque pour le joueur 1
        CarteOffensive carteAttaque = new CarteOffensive("Épée", "Une épée tranchante", 
                                                    3, 0, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        controlCartePlateau.ajouterCarteOffensiveJ1(carteAttaque);
        
        // Appliquer les effets
        int pvInitiaux = nouveauJoueur2.getPointsDeVie();
        controlCartePlateau.appliquerEffetsCartesOffensives();
        
        // Vérifier que le joueur 2 (nouveau) a perdu des points de vie
        assertEquals(pvInitiaux - 3, nouveauJoueur2.getPointsDeVie());
    }
    
    @Test
    @DisplayName("Test d'ajout et récupération des cartes offensives")
    public void testAjoutEtGetCartesOffensives() {
        // Ajouter des cartes offensives pour les deux joueurs
        CarteOffensive carteJ1_1 = new CarteOffensive("Épée", "Une épée tranchante", 
                                                3, 0, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        CarteOffensive carteJ1_2 = new CarteOffensive("Potion", "Une potion de soin", 3);
        
        CarteOffensive carteJ2_1 = new CarteOffensive("Hache", "Une hache puissante", 
                                               4, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        CarteOffensive carteJ2_2 = new CarteOffensive("Baume", "Un baume guérisseur", 2);
        
        // Ajouter les cartes aux zones
        controlCartePlateau.ajouterCarteOffensiveJ1(carteJ1_1);
        controlCartePlateau.ajouterCarteOffensiveJ1(carteJ1_2);
        controlCartePlateau.ajouterCarteOffensiveJ2(carteJ2_1);
        controlCartePlateau.ajouterCarteOffensiveJ2(carteJ2_2);
        
        // Vérifier que les cartes ont bien été ajoutées
        List<CarteOffensive> cartesJ1 = controlCartePlateau.getCartesOffensivesJ1();
        List<CarteOffensive> cartesJ2 = controlCartePlateau.getCartesOffensivesJ2();
        
        assertEquals(2, cartesJ1.size());
        assertEquals(2, cartesJ2.size());
        
        // Vérifier que les cartes sont correctes
        assertTrue(cartesJ1.contains(carteJ1_1));
        assertTrue(cartesJ1.contains(carteJ1_2));
        assertTrue(cartesJ2.contains(carteJ2_1));
        assertTrue(cartesJ2.contains(carteJ2_2));
    }
    
    @Test
    @DisplayName("Test d'ajout et récupération des cartes stratégiques")
    public void testAjoutEtGetCartesStrategiques() {
        // Ajouter des cartes stratégiques pour les deux joueurs
        CarteStrategique carteJ1_1 = new CarteStrategique("Chanson", "Une chanson populaire", 3, 0);
        CarteStrategique carteJ1_2 = new CarteStrategique("Coffre", "Un coffre au trésor", 5, 0, true);
        
        CarteStrategique carteJ2_1 = new CarteStrategique("Danse", "Une danse entraînante", 4, 1);
        CarteStrategique carteJ2_2 = new CarteStrategique("Diamant", "Un diamant précieux", 8, 0, true);
        
        // Ajouter les cartes aux zones
        controlCartePlateau.ajouterCarteStrategiqueJ1(carteJ1_1);
        controlCartePlateau.ajouterCarteStrategiqueJ1(carteJ1_2);
        controlCartePlateau.ajouterCarteStrategiqueJ2(carteJ2_1);
        controlCartePlateau.ajouterCarteStrategiqueJ2(carteJ2_2);
        
        // Vérifier que les cartes ont bien été ajoutées
        List<CarteStrategique> cartesJ1 = controlCartePlateau.getCartesStrategiquesJ1();
        List<CarteStrategique> cartesJ2 = controlCartePlateau.getCartesStrategiquesJ2();
        
        assertEquals(2, cartesJ1.size());
        assertEquals(2, cartesJ2.size());
        
        // Vérifier que les cartes sont correctes
        assertTrue(cartesJ1.contains(carteJ1_1));
        assertTrue(cartesJ1.contains(carteJ1_2));
        assertTrue(cartesJ2.contains(carteJ2_1));
        assertTrue(cartesJ2.contains(carteJ2_2));
    }
    
    @Test
    @DisplayName("Test d'application des effets des cartes offensives - Attaque")
    public void testAppliquerEffetsCartesOffensivesAttaque() {
        // Ajouter des cartes d'attaque pour les deux joueurs
        CarteOffensive carteJ1 = new CarteOffensive("Épée", "Une épée tranchante", 
                                              3, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        CarteOffensive carteJ2 = new CarteOffensive("Hache", "Une hache puissante", 
                                              4, 2, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        
        // PV initiaux
        int pvJ1Initial = joueur1.getPointsDeVie();
        int pvJ2Initial = joueur2.getPointsDeVie();
        
        // Ajouter les cartes aux zones
        controlCartePlateau.ajouterCarteOffensiveJ1(carteJ1);
        controlCartePlateau.ajouterCarteOffensiveJ2(carteJ2);
        
        // Appliquer les effets
        controlCartePlateau.appliquerEffetsCartesOffensives();
        
        // Vérifier les résultats:
        // J1 devrait subir:
        // - 4 dégâts de l'attaque de J2
        // - 1 dégât de son propre effet "dégâts subis"
        assertEquals(pvJ1Initial - 4 - 1, joueur1.getPointsDeVie());
        
        // J2 devrait subir:
        // - 3 dégâts de l'attaque de J1
        // - 2 dégâts de son propre effet "dégâts subis"
        assertEquals(pvJ2Initial - 3 - 2, joueur2.getPointsDeVie());
    }
    
    @Test
    @DisplayName("Test d'application des effets des cartes offensives - Soin")
    public void testAppliquerEffetsCartesOffensivesSoin() {
        // Réduire d'abord les PV des joueurs
        joueur1.perdrePointsDeVie(5);
        joueur2.perdrePointsDeVie(8);
        
        // PV après réduction
        int pvJ1Initial = joueur1.getPointsDeVie();
        int pvJ2Initial = joueur2.getPointsDeVie();
        
        // Ajouter des cartes de soin pour les deux joueurs
        CarteOffensive carteJ1 = new CarteOffensive("Potion", "Une potion de soin", 3);
        CarteOffensive carteJ2 = new CarteOffensive("Baume", "Un baume guérisseur", 4);
        
        // Ajouter les cartes aux zones
        controlCartePlateau.ajouterCarteOffensiveJ1(carteJ1);
        controlCartePlateau.ajouterCarteOffensiveJ2(carteJ2);
        
        // Appliquer les effets
        controlCartePlateau.appliquerEffetsCartesOffensives();
        
        // Vérifier les résultats - chaque joueur devrait récupérer des PV
        assertEquals(pvJ1Initial + 3, joueur1.getPointsDeVie());
        assertEquals(pvJ2Initial + 4, joueur2.getPointsDeVie());
    }
    
    @Test
    @DisplayName("Test d'application des effets des cartes offensives - Combinaison Attaque et Soin")
    public void testAppliquerEffetsCartesOffensivesCombinees() {
        // PV initiaux
        int pvJ1Initial = joueur1.getPointsDeVie();
        int pvJ2Initial = joueur2.getPointsDeVie();
        
        // Ajouter des cartes d'attaque et de soin pour les deux joueurs
        CarteOffensive attaqueJ1 = new CarteOffensive("Épée", "Une épée tranchante", 
                                                3, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        CarteOffensive soinJ1 = new CarteOffensive("Potion", "Une potion de soin", 3);
        
        CarteOffensive attaqueJ2 = new CarteOffensive("Hache", "Une hache puissante", 
                                                4, 2, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        CarteOffensive soinJ2 = new CarteOffensive("Baume", "Un baume guérisseur", 4);
        
        // Ajouter les cartes aux zones
        controlCartePlateau.ajouterCarteOffensiveJ1(attaqueJ1);
        controlCartePlateau.ajouterCarteOffensiveJ1(soinJ1);
        controlCartePlateau.ajouterCarteOffensiveJ2(attaqueJ2);
        controlCartePlateau.ajouterCarteOffensiveJ2(soinJ2);
        
        // Appliquer les effets
        controlCartePlateau.appliquerEffetsCartesOffensives();
        
        // Vérifier les résultats:
        // J1 devrait: perdre (4+1) dégâts, puis gagner 3 PV
        assertEquals(pvJ1Initial - 4 - 1 + 3, joueur1.getPointsDeVie());
        
        // J2 devrait: perdre (3+2) dégâts, puis gagner 4 PV
        assertEquals(pvJ2Initial - 3 - 2 + 4, joueur2.getPointsDeVie());
    }
    
    @Test
    @DisplayName("Test d'application des effets des cartes stratégiques - Popularité")
    public void testAppliquerEffetsCartesStrategiquesPopularite() {
        // Valeurs initiales
        int populariteJ1Initial = joueur1.getPopularite();
        int populariteJ2Initial = joueur2.getPopularite();
        int pvJ1Initial = joueur1.getPointsDeVie();
        int pvJ2Initial = joueur2.getPointsDeVie();
        
        // Ajouter des cartes de popularité pour les deux joueurs
        CarteStrategique carteJ1 = new CarteStrategique("Chanson", "Une chanson populaire", 3, 1);
        CarteStrategique carteJ2 = new CarteStrategique("Danse", "Une danse entraînante", 4, 2);
        
        // Ajouter les cartes aux zones
        controlCartePlateau.ajouterCarteStrategiqueJ1(carteJ1);
        controlCartePlateau.ajouterCarteStrategiqueJ2(carteJ2);
        
        // Appliquer les effets
        controlCartePlateau.appliquerEffetsCartesStrategiques();
        
        // Vérifier les résultats:
        // J1 devrait gagner 3 popularité et perdre 1 PV
        assertEquals(populariteJ1Initial + 3, joueur1.getPopularite());
        assertEquals(pvJ1Initial - 1, joueur1.getPointsDeVie());
        
        // J2 devrait gagner 4 popularité et perdre 2 PV
        assertEquals(populariteJ2Initial + 4, joueur2.getPopularite());
        assertEquals(pvJ2Initial - 2, joueur2.getPointsDeVie());
    }
    
    @Test
    @DisplayName("Test d'application des effets des cartes stratégiques - Trésor")
    public void testAppliquerEffetsCartesStrategiquesTresor() {
        // Valeurs initiales
        int orJ1Initial = joueur1.getOr();
        int orJ2Initial = joueur2.getOr();
        
        // Ajouter des cartes trésor pour les deux joueurs
        CarteStrategique gainJ1 = new CarteStrategique("Coffre", "Un coffre au trésor", 5, 0, true);
        CarteStrategique perteJ2 = new CarteStrategique("Malédiction", "Une malédiction", 0, 3, true);
        
        // Ajouter les cartes aux zones
        controlCartePlateau.ajouterCarteStrategiqueJ1(gainJ1);
        controlCartePlateau.ajouterCarteStrategiqueJ2(perteJ2);
        
        // Appliquer les effets
        controlCartePlateau.appliquerEffetsCartesStrategiques();
        
        // Vérifier les résultats:
        // J1 devrait gagner 5 or
        assertEquals(orJ1Initial + 5, joueur1.getOr());
        
        // J2 devrait perdre 3 or
        assertEquals(orJ2Initial - 3, joueur2.getOr());
    }
    
    @Test
    @DisplayName("Test de la méthode defausserCartesPlateau")
    public void testDefausserCartesPlateau() {
        // Ajouter des cartes pour les deux joueurs
        CarteOffensive offensiveJ1 = new CarteOffensive("Épée", "Une épée tranchante", 
                                                  3, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        CarteStrategique strategiqueJ1 = new CarteStrategique("Chanson", "Une chanson populaire", 3, 1);
        
        CarteOffensive offensiveJ2 = new CarteOffensive("Hache", "Une hache puissante", 
                                                  4, 2, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        CarteStrategique strategiqueJ2 = new CarteStrategique("Danse", "Une danse entraînante", 4, 2);
        
        // Ajouter les cartes aux zones
        controlCartePlateau.ajouterCarteOffensiveJ1(offensiveJ1);
        controlCartePlateau.ajouterCarteStrategiqueJ1(strategiqueJ1);
        controlCartePlateau.ajouterCarteOffensiveJ2(offensiveJ2);
        controlCartePlateau.ajouterCarteStrategiqueJ2(strategiqueJ2);
        
        // Vérifier que les zones ne sont pas vides
        assertEquals(1, controlCartePlateau.getCartesOffensivesJ1().size());
        assertEquals(1, controlCartePlateau.getCartesStrategiquesJ1().size());
        assertEquals(1, controlCartePlateau.getCartesOffensivesJ2().size());
        assertEquals(1, controlCartePlateau.getCartesStrategiquesJ2().size());
        
        // Vérifier que la défausse est vide initialement
        assertEquals(0, controlCartePlateau.getDefausse().getCartes().size());
        
        // Défausser les cartes
        controlCartePlateau.defausserCartesPlateau();
        
        // Vérifier que les zones sont maintenant vides
        assertEquals(0, controlCartePlateau.getCartesOffensivesJ1().size());
        assertEquals(0, controlCartePlateau.getCartesStrategiquesJ1().size());
        assertEquals(0, controlCartePlateau.getCartesOffensivesJ2().size());
        assertEquals(0, controlCartePlateau.getCartesStrategiquesJ2().size());
        
        // Vérifier que les cartes sont dans la défausse (4 cartes au total)
        Defausse defausse = controlCartePlateau.getDefausse();
        assertEquals(4, defausse.getCartes().size());
    }
}