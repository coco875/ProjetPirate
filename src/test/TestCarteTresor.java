package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import carte.Carte;
import carte.CarteTresor;
import carte.TypeCarte;
import carte.CarteStrategique.TypeStrategique;

/**
 * Tests pour la classe CarteTresor
 */
public class TestCarteTresor {
    
    @Test
    @DisplayName("Test de la création d'une carte trésor")
    public void testCreationCarteTresor() {
        // Test constructeur complet
        CarteTresor carteComplete = new CarteTresor("Coffre", "Un coffre au trésor", 5, 10);
        
        assertEquals("Coffre", carteComplete.getNomCarte());
        assertEquals("Un coffre au trésor", carteComplete.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carteComplete.getType());
        assertEquals(TypeStrategique.TRESOR, carteComplete.getTypeStrategique());
        assertEquals(5, carteComplete.getCout());
        
        // Test constructeur sans coût
        CarteTresor carteSansCout = new CarteTresor("Pièce d'or", "Une pièce d'or brillante", 3);
        
        assertEquals("Pièce d'or", carteSansCout.getNomCarte());
        assertEquals("Une pièce d'or brillante", carteSansCout.getDescription());
        assertEquals(10, carteSansCout.getCout()); // Coût par défaut à 10
    }
    
    @Test
    @DisplayName("Test des effets d'une carte trésor")
    public void testEffetCarteTresor() {
        CarteTresor carte = new CarteTresor("Coffre", "Un coffre au trésor", 5, 10);
        
        Carte.EffetCarte effet = carte.effetCarte();
        
        assertNotNull(effet);
        assertEquals(0, effet.degatsInfliges);
        assertEquals(0, effet.degatsSubis);
        assertEquals(0, effet.populariteGagnee);
        assertEquals(0, effet.vieGagne);
        assertEquals(10, effet.orGagne);
    }
    
    @Test
    @DisplayName("Test du chemin d'image d'une carte trésor")
    public void testCheminImage() {
        CarteTresor carte = new CarteTresor("Coffre d'Or", "Un coffre rempli d'or", 5, 10);
        String chemin = carte.getCheminImage();
        assertTrue(chemin.contains("strategique/tresor"));
        assertTrue(chemin.endsWith("coffre_d'or.jpg"));
    }
}
