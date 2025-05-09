package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import carte.Carte;
import carte.CartePopularite;
import carte.CarteSoin;
import carte.CarteSpeciale;
import carte.CarteTresor;
import carte.CarteStrategique;
import carte.CarteStrategique.TypeStrategique;

/**
 * Tests pour la classe CarteStrategique et ses méthodes de conversion
 */
public class TestCarteStrategique {
    
    @Test
    @DisplayName("Test de la création d'une carte stratégique de popularité")
    public void testCreationCartePopularite() {
        CarteStrategique carte = new CarteStrategique("Test", "Carte test", 3, 1);
        
        assertEquals("Test", carte.getNomCarte());
        assertEquals("Carte test", carte.getDescription());
        assertEquals(TypeStrategique.POPULARITE, carte.getTypeStrategique());
        assertEquals(3, carte.getValeur());
        assertEquals(1, carte.getValeurSecondaire());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte stratégique spéciale")
    public void testCreationCarteSpeciale() {
        CarteStrategique carte = new CarteStrategique("Malédiction", "Maudit l'adversaire", "Réduction dégâts", 3);
        
        assertEquals("Malédiction", carte.getNomCarte());
        assertEquals("Maudit l'adversaire", carte.getDescription());
        assertEquals(TypeStrategique.SPECIALE, carte.getTypeStrategique());
        assertEquals("Réduction dégâts", carte.getEffetSpecial());
        assertEquals(3, carte.getValeur());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte stratégique trésor")
    public void testCreationCarteTresor() {
        CarteStrategique carte = new CarteStrategique("Coffre", "Un coffre au trésor", 10, 0, true);
        
        assertEquals("Coffre", carte.getNomCarte());
        assertEquals("Un coffre au trésor", carte.getDescription());
        assertEquals(TypeStrategique.TRESOR, carte.getTypeStrategique());
        assertEquals(10, carte.getOrGagne());
        assertEquals(0, carte.getOrPerdu());
    }
    
    @Test
    @DisplayName("Test de conversion d'une carte popularité")
    public void testFromCartePopularite() {
        CartePopularite cartePopularite = new CartePopularite("Chanson", "Une chanson pirate", 3, 1);
        CarteStrategique carteStrategique = CarteStrategique.fromCartePopularite(cartePopularite);
        
        assertEquals("Chanson", carteStrategique.getNomCarte());
        assertEquals("Une chanson pirate", carteStrategique.getDescription());
        assertEquals(TypeStrategique.POPULARITE, carteStrategique.getTypeStrategique());
        assertEquals(3, carteStrategique.getValeur());
        assertEquals(1, carteStrategique.getValeurSecondaire());
    }
    
    @Test
    @DisplayName("Test de conversion d'une carte spéciale")
    public void testFromCarteSpeciale() {
        CarteSpeciale carteSpeciale = new CarteSpeciale("Malédiction", "Maudit l'adversaire", "Réduction dégâts", 3);
        CarteStrategique carteStrategique = CarteStrategique.fromCarteSpeciale(carteSpeciale);
        
        assertEquals("Malédiction", carteStrategique.getNomCarte());
        assertEquals("Maudit l'adversaire", carteStrategique.getDescription());
        assertEquals(TypeStrategique.SPECIALE, carteStrategique.getTypeStrategique());
        assertEquals("Réduction dégâts", carteStrategique.getEffetSpecial());
        assertEquals(3, carteStrategique.getValeur());
    }
    
    @Test
    @DisplayName("Test de conversion d'une carte trésor")
    public void testFromCarteTresor() {
        CarteTresor carteTresor = new CarteTresor("Coffre", "Un coffre au trésor", 10, 0, 0);
        CarteStrategique carteStrategique = CarteStrategique.fromCarteTresor(carteTresor);
        
        assertEquals("Coffre", carteStrategique.getNomCarte());
        assertEquals("Un coffre au trésor", carteStrategique.getDescription());
        assertEquals(TypeStrategique.TRESOR, carteStrategique.getTypeStrategique());
        
        Carte.EffetCarte effet = carteStrategique.effetCarte();
        assertEquals(10, effet.orGagne);
        assertTrue(effet.estTresor);
    }
    
    @Test
    @DisplayName("Test des effets de carte stratégique par type")
    public void testEffetCarteStrategique() {
        // Test carte popularité
        CarteStrategique cartePopularite = new CarteStrategique("Chanson", "Chanson pirate", 3, 1);
        Carte.EffetCarte effetPopularite = cartePopularite.effetCarte();
        assertEquals(3, effetPopularite.populariteGagnee);
        assertTrue(effetPopularite.estPopularite);
        
        // Test carte passive
        CarteStrategique cartePassive = new CarteStrategique("Bouclier", "Protection", 2, 3, "Défense");
        Carte.EffetCarte effetPassive = cartePassive.effetCarte();
        assertTrue(effetPassive.estPassive);
        assertEquals(3, effetPassive.dureeEffet);
        
        // Test carte spéciale
        CarteStrategique carteSpeciale = new CarteStrategique("Sort", "Sort magique", "Effet spécial", 2);
        Carte.EffetCarte effetSpeciale = carteSpeciale.effetCarte();
        assertTrue(effetSpeciale.estSpeciale);
        assertEquals("Effet spécial", effetSpeciale.effetSpecial);
        
        // Test carte trésor
        CarteStrategique carteTresor = new CarteStrategique("Or", "Pièces d'or", 15, 0, true);
        Carte.EffetCarte effetTresor = carteTresor.effetCarte();
        assertEquals(15, effetTresor.orGagne);
        assertTrue(effetTresor.estTresor);
        
        // Test carte trésor avec perte d'or
        CarteStrategique cartePerteTresor = new CarteStrategique("Taxe", "Taxes portuaires", 0, 10, true);
        Carte.EffetCarte effetPerteTresor = cartePerteTresor.effetCarte();
        assertEquals(10, effetPerteTresor.orPerdu);
        assertTrue(effetPerteTresor.estTresor);
    }
}
