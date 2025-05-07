package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import carte.Carte;
import carte.TypeCarte;

/**
 * Tests pour la classe Carte
 */
public class TestCarte {
    
    @Test
    @DisplayName("Test de la création d'une carte")
    public void testCreationCarte() {
        // Test constructeur complet
        Carte carteComplete = new Carte(TypeCarte.OFFENSIVE, "Épée", "Une épée tranchante", 3, 1, 5);
        
        assertEquals("Épée", carteComplete.getNomCarte());
        assertEquals("Une épée tranchante", carteComplete.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carteComplete.getType());
        assertEquals(3, carteComplete.getValeur());
        assertEquals(1, carteComplete.getValeurSecondaire());
        assertEquals(5, carteComplete.getCout());
        
        // Test constructeur sans coût
        Carte carteSansCout = new Carte(TypeCarte.STRATEGIQUE, "Potion", "Une potion de soin", 2, 0);
        
        assertEquals("Potion", carteSansCout.getNomCarte());
        assertEquals("Une potion de soin", carteSansCout.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carteSansCout.getType());
        assertEquals(2, carteSansCout.getValeur());
        assertEquals(0, carteSansCout.getValeurSecondaire());
        assertEquals(10, carteSansCout.getCout()); // Coût par défaut à 10
        
        // Test constructeur simplifié
        Carte carteSimplifie = new Carte(TypeCarte.OFFENSIVE, "Chance", "Carte de chance");
        
        assertEquals("Chance", carteSimplifie.getNomCarte());
        assertEquals("Carte de chance", carteSimplifie.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carteSimplifie.getType());
        assertEquals(0, carteSimplifie.getValeur()); // Valeur par défaut à 0
        assertEquals(0, carteSimplifie.getValeurSecondaire()); // Valeur secondaire par défaut à 0
        assertEquals(10, carteSimplifie.getCout()); // Coût par défaut à 10
        
        // Test constructeur JSON
        Carte carteJson = new Carte("CarteTresor", 42, TypeCarte.STRATEGIQUE);
        
        assertEquals("CarteTresor", carteJson.getNomCarte());
        assertEquals("ID: 42", carteJson.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carteJson.getType());
        assertEquals(42, carteJson.getId());
    }
    
    @Test
    @DisplayName("Test des getters et setters")
    public void testGettersSetters() {
        Carte carte = new Carte(TypeCarte.OFFENSIVE, "Épée", "Une épée tranchante", 3, 1, 5);
        
        // Test setters
        carte.setNomCarte("Épée magique");
        carte.setDescription("Une épée magique puissante");
        carte.setValeur(5);
        carte.setValeurSecondaire(2);
        carte.setCout(8);
        carte.setCheminImage("images/épée_magique.png");
        
        // Test getters après les modifications
        assertEquals("Épée magique", carte.getNomCarte());
        assertEquals("Une épée magique puissante", carte.getDescription());
        assertEquals(5, carte.getValeur());
        assertEquals(2, carte.getValeurSecondaire());
        assertEquals(8, carte.getCout());
        assertEquals("images/épée_magique.png", carte.getCheminImage());
    }
    
    @Test
    @DisplayName("Test de la méthode effetCarte")
    public void testEffetCarte() {
        Carte carte = new Carte(TypeCarte.OFFENSIVE, "Épée", "Une épée tranchante", 3, 1, 5);
        
        Carte.EffetCarte effet = carte.effetCarte();
        
        assertNotNull(effet);
        // Par défaut, tous les effets sont à 0 ou false
        assertEquals(0, effet.degatsInfliges);
        assertEquals(0, effet.degatsSubis);
        assertEquals(0, effet.orGagne);
        assertEquals(0, effet.orPerdu);
        assertEquals(0, effet.populariteGagnee);
        assertEquals(0, effet.vieGagnee);
        assertFalse(effet.estAttaque);
        assertFalse(effet.estPopularite);
        assertFalse(effet.estSpeciale);
        assertFalse(effet.estPassive);
        assertFalse(effet.estTresor);
        assertFalse(effet.estSoin);
    }
    
    @Test
    @DisplayName("Test de la méthode toString")
    public void testToString() {
        Carte carte = new Carte(TypeCarte.STRATEGIQUE, "Coffre", "Un coffre au trésor", 10, 0, 5) {
            @Override
            public EffetCarte effetCarte() {
                EffetCarte effet = super.effetCarte();
                effet.orGagne = 10;
                effet.estTresor = true;
                return effet;
            }
        };
        
        String description = carte.toString();
        
        assertTrue(description.contains("Coffre"));
        assertTrue(description.contains("Un coffre au trésor"));
        assertTrue(description.contains("Or gagné: 10"));
    }
    
    @Test
    @DisplayName("Test de l'unicité des IDs")
    public void testIdUnique() {
        Carte carte1 = new Carte(TypeCarte.OFFENSIVE, "Carte 1", "Description 1", 1, 1, 5);
        Carte carte2 = new Carte(TypeCarte.OFFENSIVE, "Carte 2", "Description 2", 1, 1, 5);
        
        assertNotEquals(carte1.getId(), carte2.getId());
    }
}