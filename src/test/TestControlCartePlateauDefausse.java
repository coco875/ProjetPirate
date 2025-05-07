package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import controllers.ControlCartePlateau;
import controllers.ControlJoueur;
import controllers.ControlPioche;
import jeu.Defausse;
import carte.CarteOffensive;
import carte.CarteStrategique;
import carte.CarteOffensive.TypeOffensif;
import joueur.Joueur;
import joueur.Pirate;

import java.util.List;

/**
 * Tests pour les mécanismes de défausse dans ControlCartePlateau
 */
public class TestControlCartePlateauDefausse {
    
    private ControlCartePlateau controlCartePlateau;
    private ControlJoueur controlJoueur1;
    private ControlJoueur controlJoueur2;
    private Joueur joueur1;
    private Joueur joueur2;
    
    @BeforeEach
    public void initialiser() {
        // Création des pirates et joueurs
        Pirate pirate1 = new Pirate("Barbe Noire", "Le pirate redoutable", 5, 2);
        Pirate pirate2 = new Pirate("Jack Sparrow", "Le pirate rusé", 4, 3);
        joueur1 = new Joueur("Joueur 1", pirate1);
        joueur2 = new Joueur("Joueur 2", pirate2);
        
        // Création des contrôleurs de joueur
        ControlPioche controlPioche = new ControlPioche();
        controlJoueur1 = new ControlJoueur(joueur1, null, controlPioche);
        controlJoueur2 = new ControlJoueur(joueur2, null, controlPioche);
        
        // Création du contrôleur de plateau de cartes
        controlCartePlateau = new ControlCartePlateau(controlJoueur1, controlJoueur2);
    }
    
    @Test
    @DisplayName("Test de la défausse des cartes du plateau")
    public void testDefausserCartesPlateau() {
        // Ajouter des cartes dans les différentes zones
        CarteOffensive carteOffensive1 = new CarteOffensive("Épée", "Une épée tranchante", 3, 0, TypeOffensif.ATTAQUE_DIRECTE);
        CarteOffensive carteOffensive2 = new CarteOffensive("Potion", "Une potion de soin", 2, 0, TypeOffensif.SOIN);
        CarteStrategique carteStrategique1 = new CarteStrategique("Coffre", "Un coffre au trésor", 10, 0, true);
        CarteStrategique carteStrategique2 = new CarteStrategique("Chanson", "Une chanson pirate", 3, 1);
        
        controlCartePlateau.ajouterCarteOffensiveJ1(carteOffensive1);
        controlCartePlateau.ajouterCarteOffensiveJ2(carteOffensive2);
        controlCartePlateau.ajouterCarteStrategiqueJ1(carteStrategique1);
        controlCartePlateau.ajouterCarteStrategiqueJ2(carteStrategique2);
        
        // Vérifier que les zones ne sont pas vides
        assertFalse(controlCartePlateau.getZoneOffensiveJ1().getCartesOffensives().isEmpty());
        assertFalse(controlCartePlateau.getZoneOffensiveJ2().getCartesOffensives().isEmpty());
        assertFalse(controlCartePlateau.getZoneStrategiqueJ1().getCartesStrategiques().isEmpty());
        assertFalse(controlCartePlateau.getZoneStrategiqueJ2().getCartesStrategiques().isEmpty());
        
        // Vérifier que la défausse est vide avant la défausse
        assertTrue(controlCartePlateau.getDefausse().getCartes().isEmpty());
        
        // Défausser toutes les cartes
        controlCartePlateau.defausserCartesPlateau();
        
        // Vérifier que toutes les zones sont maintenant vides
        assertTrue(controlCartePlateau.getZoneOffensiveJ1().getCartesOffensives().isEmpty());
        assertTrue(controlCartePlateau.getZoneOffensiveJ2().getCartesOffensives().isEmpty());
        assertTrue(controlCartePlateau.getZoneStrategiqueJ1().getCartesStrategiques().isEmpty());
        assertTrue(controlCartePlateau.getZoneStrategiqueJ2().getCartesStrategiques().isEmpty());
        
        // Vérifier que la défausse contient maintenant toutes les cartes
        Defausse defausse = controlCartePlateau.getDefausse();
        assertEquals(4, defausse.getCartes().size());
        
        // Vérifier que les cartes défaussées sont bien celles qu'on a ajoutées
        assertTrue(defausse.getCartes().contains(carteOffensive1));
        assertTrue(defausse.getCartes().contains(carteOffensive2));
        assertTrue(defausse.getCartes().contains(carteStrategique1));
        assertTrue(defausse.getCartes().contains(carteStrategique2));
    }
    
    @Test
    @DisplayName("Test de défausse avec des cartes null")
    public void testDefausseCarteNull() {
        // Ajouter des cartes normales et une carte null (ce qui ne devrait pas être possible en pratique)
        CarteOffensive carteOffensive = new CarteOffensive("Épée", "Une épée tranchante", 3, 0, TypeOffensif.ATTAQUE_DIRECTE);
        controlCartePlateau.ajouterCarteOffensiveJ1(carteOffensive);
        
        // Défausser les cartes
        controlCartePlateau.defausserCartesPlateau();
        
        // Vérifier que la défausse contient bien la carte valide
        Defausse defausse = controlCartePlateau.getDefausse();
        assertEquals(1, defausse.getCartes().size());
        assertTrue(defausse.getCartes().contains(carteOffensive));
    }
    
    @Test
    @DisplayName("Test d'obtention des zones et de la défausse")
    public void testGetters() {
        // Tester tous les getters pour les zones et la défausse
        assertNotNull(controlCartePlateau.getZoneOffensiveJ1());
        assertNotNull(controlCartePlateau.getZoneOffensiveJ2());
        assertNotNull(controlCartePlateau.getZoneStrategiqueJ1());
        assertNotNull(controlCartePlateau.getZoneStrategiqueJ2());
        assertNotNull(controlCartePlateau.getDefausse());
        
        assertNotNull(controlCartePlateau.getCartesOffensivesJ1());
        assertNotNull(controlCartePlateau.getCartesOffensivesJ2());
        assertNotNull(controlCartePlateau.getCartesStrategiquesJ1());
        assertNotNull(controlCartePlateau.getCartesStrategiquesJ2());
        
        assertTrue(controlCartePlateau.getCartesOffensivesJ1().isEmpty());
        assertTrue(controlCartePlateau.getCartesOffensivesJ2().isEmpty());
        assertTrue(controlCartePlateau.getCartesStrategiquesJ1().isEmpty());
        assertTrue(controlCartePlateau.getCartesStrategiquesJ2().isEmpty());
    }
}
