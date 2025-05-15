package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import carte.Carte;
import carte.CarteAttaque;
import carte.CarteSoin;
import carte.CarteTresor;
import carte.TypeCarte;

/**
 * Tests pour les classes dérivées de Carte
 * La classe Carte est maintenant abstraite, les tests ont été adaptés
 * pour utiliser les classes concrètes dérivées
 */
@DisplayName("Tests pour les classes de cartes")
public class TestCarte {
    
    @Test
    @DisplayName("Test de la création d'une carte d'attaque")
    public void testCreationCarteAttaque() {
        // Test constructeur de CarteAttaque
        CarteAttaque carteAttaque = new CarteAttaque("Épée", "Une épée tranchante", 10, 3, 1);
        
        assertEquals("Épée", carteAttaque.getNomCarte());
        assertEquals("Une épée tranchante", carteAttaque.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carteAttaque.getType());
        assertEquals(3, carteAttaque.effetCarte().degatsInfliges);
        assertEquals(10, carteAttaque.getCout()); // Coût par défaut à 10
    }
    
    @Test
    @DisplayName("Test de la création d'une carte de soin")
    public void testCreationCarteSoin() {
        // Test constructeur de CarteSoin
        CarteSoin carteSoin = new CarteSoin("Potion", "Une potion de soin", 5);
        
        assertEquals("Potion", carteSoin.getNomCarte());
        assertEquals("Une potion de soin", carteSoin.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carteSoin.getType());
        assertEquals(5, carteSoin.effetCarte().vieGagne);
        assertEquals(10, carteSoin.getCout()); // Coût par défaut à 10
    }
    
    @Test
    @DisplayName("Test de la création d'une carte trésor")
    public void testCreationCarteTresor() {
        // Test constructeur de CarteTresor
        CarteTresor carteTresor = new CarteTresor("Coffre", "Un coffre au trésor", 10);
        
        assertEquals("Coffre", carteTresor.getNomCarte());
        assertEquals("Un coffre au trésor", carteTresor.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carteTresor.getType());
        assertEquals(10, carteTresor.effetCarte().orGagne);
        assertEquals(10, carteTresor.getCout()); // Coût par défaut à 10
    }
    
    @Test
    @DisplayName("Test des getters et setters")
    public void testGettersSetters() {
        // Test sur une carte concrète (CarteAttaque)
        CarteAttaque carte = new CarteAttaque("Épée", "Une épée tranchante", 3, 8);
        
        // Test setters
        carte.setNomCarte("Épée magique");
        carte.setDescription("Une épée magique puissante");
        carte.setCout(12);
        
        // Test getters après les modifications
        assertEquals("Épée magique", carte.getNomCarte());
        assertEquals("Une épée magique puissante", carte.getDescription());
        assertEquals(12, carte.getCout());
    }
    
    @Test
    @DisplayName("Test de la méthode effetCarte")
    public void testEffetCarte() {
        // Test de l'effet d'une carte d'attaque
        CarteAttaque carteAttaque = new CarteAttaque("Épée", "Une épée tranchante", 10, 3, 1);
        Carte.EffetCarte effetAttaque = carteAttaque.effetCarte();
        
        assertNotNull(effetAttaque);
        assertEquals(3, effetAttaque.degatsInfliges);
        assertEquals(0, effetAttaque.vieGagne);
        
        // Test de l'effet d'une carte de soin
        CarteSoin carteSoin = new CarteSoin("Potion", "Une potion de soin", 5);
        Carte.EffetCarte effetSoin = carteSoin.effetCarte();
        
        assertNotNull(effetSoin);
        assertEquals(0, effetSoin.degatsInfliges);
        assertEquals(5, effetSoin.vieGagne);
        
        // Test de l'effet d'une carte trésor
        CarteTresor carteTresor = new CarteTresor("Coffre", "Un coffre au trésor", 10);
        Carte.EffetCarte effetTresor = carteTresor.effetCarte();
        
        assertNotNull(effetTresor);
        assertEquals(10, effetTresor.orGagne);
    }
    
    @Test
    @DisplayName("Test de la méthode toString")
    public void testToString() {
        // Test toString avec une carte trésor
        CarteTresor carteTresor = new CarteTresor("Coffre", "Un coffre au trésor", 10);
        String description = carteTresor.toString();
        
        assertTrue(description.contains("Coffre"));
        assertTrue(description.contains("Un coffre au trésor"));
        assertTrue(description.contains("Or gagné"));
    }
    
    @Test
    @DisplayName("Test de l'unicité des instances")
    public void testInstancesUniques() {
        CarteAttaque carte1 = new CarteAttaque("Carte 1", "Description 1", 10, 1, 0);
        CarteAttaque carte2 = new CarteAttaque("Carte 2", "Description 2", 10, 1, 0);
        
        // Vérifier que ce sont des instances différentes
        assertNotSame(carte1, carte2);
    }
}
