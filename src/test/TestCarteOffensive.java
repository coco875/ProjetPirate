package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import carte.Carte;
import carte.CarteAttaque;
import carte.CarteOffensive;
import carte.CarteSoin;
import carte.TypeCarte;

/**
 * Tests pour la classe CarteOffensive
 *//*
@DisplayName("Tests pour la classe CarteOffensive")
public class TestCarteOffensive {
    
    @Test
    @DisplayName("Test de la création d'une carte offensive d'attaque directe")
    public void testCreationCarteAttaqueDirecte() {
        CarteOffensive carte = new CarteOffensive("Épée", "Une épée tranchante", 3, 1, CarteOffensive.TypeOffensif.ATTAQUE);
        
        assertEquals("Épée", carte.getNomCarte());
        assertEquals("Une épée tranchante", carte.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carte.getType());
        assertEquals(CarteOffensive.TypeOffensif.ATTAQUE, carte.getTypeOffensif());
        assertEquals(3, carte.getDegatsInfliges());
        assertEquals(1, carte.getDegatsSubis());
        assertEquals(0, carte.getVieGagnee());
        assertEquals(0, carte.getOrGagne());
        assertFalse(carte.estJouee());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte offensive avec coût")
    public void testCreationCarteOffensiveAvecCout() {
        CarteOffensive carte = new CarteOffensive("Mousquet", "Un mousquet puissant", 4, 2, 
                                                 CarteOffensive.TypeOffensif.ATTAQUE, 15);
        
        assertEquals("Mousquet", carte.getNomCarte());
        assertEquals("Un mousquet puissant", carte.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carte.getType());
        assertEquals(CarteOffensive.TypeOffensif.ATTAQUE, carte.getTypeOffensif());
        assertEquals(4, carte.getDegatsInfliges());
        assertEquals(2, carte.getDegatsSubis());
        assertEquals(15, carte.getCout());
        assertFalse(carte.estJouee());
        assertEquals(0, carte.getCoutSpecial());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte de soin")
    public void testCreationCarteSoin() {
        CarteOffensive carte = new CarteOffensive("Potion", "Une potion de soin", 5);
        
        assertEquals("Potion", carte.getNomCarte());
        assertEquals("Une potion de soin", carte.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carte.getType());
        assertEquals(CarteOffensive.TypeOffensif.SOIN, carte.getTypeOffensif());
        assertEquals(5, carte.getVieGagnee());
        // Pour les cartes de soin, la valeur est utilisée pour les soins, donc getDegatsInfliges() renvoie aussi cette valeur
        assertEquals(5, carte.getDegatsInfliges());
        assertEquals(0, carte.getDegatsSubis());
        assertFalse(carte.estJouee());
    }
    
    @Test
    @DisplayName("Test des getters et setters")
    public void testGettersSetters() {
        CarteOffensive carte = new CarteOffensive("Test", "Carte de test", 1, 1, CarteOffensive.TypeOffensif.ATTAQUE);
        
        // Test setters
        carte.setTypeOffensif(CarteOffensive.TypeOffensif.COUP_SPECIAL);
        carte.setEstJouee(true);
        carte.setCoutSpecial(5);
        
        // Test getters
        assertEquals(CarteOffensive.TypeOffensif.COUP_SPECIAL, carte.getTypeOffensif());
        assertTrue(carte.estJouee());
        assertEquals(5, carte.getCoutSpecial());
    }
    
    @Test
    @DisplayName("Test des méthodes de vérification de type")
    public void testMethodesVerificationType() {
        CarteOffensive carteAttaque = new CarteOffensive("Épée", "Une épée tranchante", 3, 1, CarteOffensive.TypeOffensif.ATTAQUE);
        CarteOffensive carteSoin = new CarteOffensive("Potion", "Une potion de soin", 5);
        
        // Tests pour carte Attaque
        assertTrue(carteAttaque.estAttaqueDirecte());
        assertFalse(carteAttaque.estSoin());
        assertFalse(carteAttaque.estTresorOffensif());
        
        // Tests pour carte Soin
        assertFalse(carteSoin.estAttaqueDirecte());
        assertTrue(carteSoin.estSoin());
        assertFalse(carteSoin.estTresorOffensif());
    }
    
    @Test
    @DisplayName("Test de la méthode effetCarte pour carte d'attaque directe")
    public void testEffetCarteAttaqueDirecte() {
        CarteOffensive carte = new CarteOffensive("Épée", "Une épée tranchante", 3, 1, CarteOffensive.TypeOffensif.ATTAQUE);
        
        Carte.EffetCarte effet = carte.effetCarte();
        
        assertEquals(3, effet.degatsInfliges);
        assertEquals(1, effet.degatsSubis);
        assertTrue(effet.estAttaque);
        assertFalse(effet.estSoin);
        assertFalse(effet.estSpeciale);
        assertEquals(0, effet.orGagne);
        assertEquals(0, effet.orPerdu);
    }
    
    @Test
    @DisplayName("Test de la méthode effetCarte pour carte de soin")
    public void testEffetCarteSoin() {
        CarteOffensive carte = new CarteOffensive("Potion", "Une potion de soin", 5);
        
        Carte.EffetCarte effet = carte.effetCarte();
        
        assertEquals(5, effet.vieGagnee);
        assertTrue(effet.estSoin);
        assertFalse(effet.estAttaque);
        assertFalse(effet.estSpeciale);
        assertEquals(0, effet.orGagne);
        assertEquals(0, effet.orPerdu);
    }
    
    @Test
    @DisplayName("Test de la méthode toString pour carte d'attaque")
    public void testToStringAttaqueDirecte() {
        CarteOffensive carte = new CarteOffensive("Épée", "Une épée tranchante", 3, 1, CarteOffensive.TypeOffensif.ATTAQUE);
        
        String description = carte.toString();
        
        assertTrue(description.contains("Épée"));
        assertTrue(description.contains("Une épée tranchante"));
        assertTrue(description.contains("Dégâts infligés: 3"));
        assertTrue(description.contains("Dégâts subis: 1"));
    }
    
    @Test
    @DisplayName("Test de la méthode toString pour carte de soin")
    public void testToStringSoin() {
        CarteOffensive carte = new CarteOffensive("Potion", "Une potion de soin", 5);
        
        String description = carte.toString();
        
        assertTrue(description.contains("Potion"));
        assertTrue(description.contains("Une potion de soin"));
        assertTrue(description.contains("Vie gagnée: 5"));
    }
    
    @Test
    @DisplayName("Test de la méthode toString pour coup spécial")
    public void testToStringCoupSpecial() {
        CarteOffensive carte = new CarteOffensive("Frappe Éclair", "Une attaque spéciale", 4, 2);
        
        String description = carte.toString();
        
        assertTrue(description.contains("Frappe Éclair"));
        assertTrue(description.contains("Une attaque spéciale"));
        assertTrue(description.contains("Valeur: 4"));
        assertTrue(description.contains("Coût spécial: 2"));
    }
}*/