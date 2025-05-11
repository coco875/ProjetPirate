package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import jeu.ZoneOffensive;
import carte.CarteOffensive;
import carte.TypeCarte;
import carte.CarteOffensive.TypeOffensif;

import java.util.List;

/**
 * Tests pour la classe ZoneOffensive
 */
public class TestZoneOffensive {
    
    private ZoneOffensive zoneOffensive;
    
    @BeforeEach
    public void initialiser() {
        zoneOffensive = new ZoneOffensive();
    }
    
    @Test
    @DisplayName("Test de la création d'une zone offensive")
    public void testCreationZoneOffensive() {
        assertNotNull(zoneOffensive, "La zone offensive ne devrait pas être null");
        assertNotNull(zoneOffensive.getCartesOffensives(), "La liste de cartes ne devrait pas être null");
        assertTrue(zoneOffensive.getCartesOffensives().isEmpty(), "La zone offensive devrait être vide à la création");
    }
    
    @Test
    @DisplayName("Test d'ajout de cartes dans la zone offensive")
    public void testAjouterCarte() {
        // Créer quelques cartes de test
        CarteOffensive carte1 = new CarteOffensive("Épée", "Une épée tranchante", 3, 1, TypeOffensif.ATTAQUE);
        CarteOffensive carte2 = new CarteOffensive("Potion", "Une potion de soin", 2, 0, TypeOffensif.SOIN);
        
        // Ajouter les cartes à la zone
        zoneOffensive.ajouterCarte(carte1);
        zoneOffensive.ajouterCarte(carte2);
        
        // Vérifier que les cartes ont bien été ajoutées
        List<CarteOffensive> cartes = zoneOffensive.getCartesOffensives();
        assertEquals(2, cartes.size(), "La zone offensive devrait contenir 2 cartes");
        assertTrue(cartes.contains(carte1), "La zone offensive devrait contenir la carte 1");
        assertTrue(cartes.contains(carte2), "La zone offensive devrait contenir la carte 2");
    }
    
    @Test
    @DisplayName("Test de vidage de la zone offensive")
    public void testViderZone() {
        // Ajouter des cartes à la zone
        CarteOffensive carte1 = new CarteOffensive("Épée", "Une épée tranchante", 3, 1, TypeOffensif.ATTAQUE);
        CarteOffensive carte2 = new CarteOffensive("Potion", "Une potion de soin", 2, 0, TypeOffensif.SOIN);
        zoneOffensive.ajouterCarte(carte1);
        zoneOffensive.ajouterCarte(carte2);
        
        // Vérifier que la zone n'est pas vide
        assertFalse(zoneOffensive.getCartesOffensives().isEmpty(), "La zone offensive ne devrait pas être vide");
        
        // Vider la zone
        zoneOffensive.viderZone();
        
        // Vérifier que la zone est vide
        assertTrue(zoneOffensive.getCartesOffensives().isEmpty(), "La zone offensive devrait être vide après vidage");
    }
}
