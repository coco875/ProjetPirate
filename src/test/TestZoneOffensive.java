package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import jeu.ZoneOffensive;
import carte.CarteAttaque;
import carte.CarteSoin;
import carte.CarteOffensive;

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
        CarteAttaque carte1 = new CarteAttaque("Épée", "Une épée tranchante", 5, 3, 1);
        CarteSoin carte2 = new CarteSoin("Potion", "Une potion de soin", 3, 2);
        
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
        CarteAttaque carte1 = new CarteAttaque("Épée", "Une épée tranchante", 5, 3, 1);
        CarteSoin carte2 = new CarteSoin("Potion", "Une potion de soin", 3, 2);
        
        zoneOffensive.ajouterCarte(carte1);
        zoneOffensive.ajouterCarte(carte2);
        
        // Vérifier que les cartes sont bien ajoutées
        assertEquals(2, zoneOffensive.getCartesOffensives().size());
        
        // Vider la zone
        zoneOffensive.viderZone();
        
        // Vérifier que la zone est vide
        assertTrue(zoneOffensive.getCartesOffensives().isEmpty(), "La zone offensive devrait être vide après vidage");
    }
}