package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import carte.Carte;
import carte.CartePopularite;
import carte.TypeCarte;
import carte.CarteStrategique.TypeStrategique;

/**
 * Tests pour la classe CartePopularite
 */
public class TestCartePopularite {
    
    @Test
    @DisplayName("Test de la création d'une carte de popularité")
    public void testCreationCartePopularite() {
        // Test constructeur complet
        CartePopularite carteComplete = new CartePopularite("Discours", "Un discours inspirant", 3, 2, 1);
        
        assertEquals("Discours", carteComplete.getNomCarte());
        assertEquals("Un discours inspirant", carteComplete.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carteComplete.getType());
        assertEquals(TypeStrategique.POPULARITE, carteComplete.getTypeStrategique());
        assertEquals(3, carteComplete.getCout());
        
        // Test constructeur sans coût
        CartePopularite carteSansCout = new CartePopularite("Récit", "Un récit d'aventures", 2, 0);
        
        assertEquals("Récit", carteSansCout.getNomCarte());
        assertEquals("Un récit d'aventures", carteSansCout.getDescription());
        assertEquals(10, carteSansCout.getCout()); // Coût par défaut à 10
    }
    
    @Test
    @DisplayName("Test des effets d'une carte de popularité")
    public void testEffetCartePopularite() {
        CartePopularite carte = new CartePopularite("Discours", "Un discours inspirant", 3, 2, 1);
        
        Carte.EffetCarte effet = carte.effetCarte();
        
        assertNotNull(effet);
        assertEquals(0, effet.degatsInfliges);
        assertEquals(1, effet.degatsSubis);
        assertEquals(2, effet.populariteGagnee);
        assertEquals(0, effet.vieGagne);
        assertEquals(0, effet.orGagne);
    }
    
    @Test
    @DisplayName("Test du chemin d'image d'une carte de popularité")
    public void testCheminImage() {
        CartePopularite carte = new CartePopularite("Grand Discours", "Un grand discours inspirant", 3, 2, 1);
        String chemin = carte.getCheminImage();
        assertTrue(chemin.contains("strategique/popularite"));
        assertTrue(chemin.endsWith("grand_discours.jpg"));
    }
}
