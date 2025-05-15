package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import carte.Carte;
import carte.CarteAttaque;
import carte.CarteSoin;
import carte.TypeCarte;
import carte.CarteOffensive.TypeOffensif;

/**
 * Tests pour les classes de cartes
 */
public class TestCartesConcretes {
    
    @Test
    @DisplayName("Test de la création d'une carte d'attaque")
    public void testCreationCarteAttaque() {
        // Test constructeur complet
        CarteAttaque carteComplete = new CarteAttaque("Épée", "Une épée tranchante", 5, 3, 1);
        
        assertEquals("Épée", carteComplete.getNomCarte());
        assertEquals("Une épée tranchante", carteComplete.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carteComplete.getType());
        assertEquals(TypeOffensif.ATTAQUE, carteComplete.getTypeOffensif());
        assertEquals(5, carteComplete.getCout());
        
        // Test constructeur sans coût
        CarteAttaque carteSansCout = new CarteAttaque("Dague", "Une petite dague", 2, 0);
        
        assertEquals("Dague", carteSansCout.getNomCarte());
        assertEquals("Une petite dague", carteSansCout.getDescription());
        assertEquals(10, carteSansCout.getCout()); // Coût par défaut à 10
    }
    
    @Test
    @DisplayName("Test de la création d'une carte de soin")
    public void testCreationCarteSoin() {
        // Test constructeur complet
        CarteSoin carteComplete = new CarteSoin("Potion", "Une potion de soin", 4, 2);
        
        assertEquals("Potion", carteComplete.getNomCarte());
        assertEquals("Une potion de soin", carteComplete.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carteComplete.getType());
        assertEquals(TypeOffensif.SOIN, carteComplete.getTypeOffensif());
        assertEquals(4, carteComplete.getCout());
        
        // Test constructeur sans coût
        CarteSoin carteSansCout = new CarteSoin("Bandage", "Un simple bandage", 1);
        
        assertEquals("Bandage", carteSansCout.getNomCarte());
        assertEquals("Un simple bandage", carteSansCout.getDescription());
        assertEquals(10, carteSansCout.getCout()); // Coût par défaut à 10
    }
    
    @Test
    @DisplayName("Test des effets d'une carte d'attaque")
    public void testEffetCarteAttaque() {
        CarteAttaque carteAttaque = new CarteAttaque("Épée", "Une épée tranchante", 5, 3, 1);
        
        Carte.EffetCarte effet = carteAttaque.effetCarte();
        
        assertNotNull(effet);
        assertEquals(3, effet.degatsInfliges);
        assertEquals(1, effet.degatsSubis);
        assertEquals(0, effet.populariteGagnee);
        assertEquals(0, effet.vieGagne);
        assertEquals(0, effet.orGagne);
    }
    
    @Test
    @DisplayName("Test des effets d'une carte de soin")
    public void testEffetCarteSoin() {
        CarteSoin carteSoin = new CarteSoin("Potion", "Une potion de soin", 4, 2);
        
        Carte.EffetCarte effet = carteSoin.effetCarte();
        
        assertNotNull(effet);
        assertEquals(0, effet.degatsInfliges);
        assertEquals(0, effet.degatsSubis);
        assertEquals(0, effet.populariteGagnee);
        assertEquals(2, effet.vieGagne);
        assertEquals(0, effet.orGagne);
    }
    
    @Test
    @DisplayName("Test du chemin d'image d'une carte")
    public void testCheminImage() {
        CarteAttaque carteAttaque = new CarteAttaque("Épée de Pirate", "Une épée de pirate", 5, 3, 1);
        String cheminAttaque = carteAttaque.getCheminImage();
        assertTrue(cheminAttaque.contains("offensive/attaque"));
        assertTrue(cheminAttaque.endsWith("épée_de_pirate.jpg"));
        
        CarteSoin carteSoin = new CarteSoin("Potion de Vie", "Une potion magique", 4, 2);
        String cheminSoin = carteSoin.getCheminImage();
        assertTrue(cheminSoin.contains("offensive/soin"));
        assertTrue(cheminSoin.endsWith("potion_de_vie.jpg"));
    }
}
