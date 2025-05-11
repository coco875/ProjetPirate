package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import carte.Carte;
import carte.CarteAttaque;
import carte.CarteCoupSpecial;
import carte.CarteOffensive;
import carte.CarteSoin;
import carte.TypeCarte;

/**
 * Tests pour la classe CarteOffensive
 */
@DisplayName("Tests pour la classe CarteOffensive")
public class TestCarteOffensive {
    
    @Test
    @DisplayName("Test de la création d'une carte offensive d'attaque directe")
    public void testCreationCarteAttaqueDirecte() {
        CarteOffensive carte = new CarteOffensive("Épée", "Une épée tranchante", 3, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        
        assertEquals("Épée", carte.getNomCarte());
        assertEquals("Une épée tranchante", carte.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carte.getType());
        assertEquals(CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE, carte.getTypeOffensif());
        assertEquals(3, carte.getDegatsInfliges());
        assertEquals(1, carte.getDegatsSubis());
        assertEquals(0, carte.getVieGagnee());
        assertEquals(0, carte.getOrGagne());
        assertEquals(0, carte.getOrPerdu());
        assertFalse(carte.estJouee());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte offensive avec coût")
    public void testCreationCarteOffensiveAvecCout() {
        CarteOffensive carte = new CarteOffensive("Mousquet", "Un mousquet puissant", 4, 2, 
                                                 CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE, 15);
        
        assertEquals("Mousquet", carte.getNomCarte());
        assertEquals("Un mousquet puissant", carte.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carte.getType());
        assertEquals(CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE, carte.getTypeOffensif());
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
    @DisplayName("Test de la création d'une carte coup spécial")
    public void testCreationCarteCoupSpecial() {
        CarteOffensive carte = new CarteOffensive("Frappe Éclair", "Une attaque spéciale fulgurante", 3, 2);
        
        assertEquals("Frappe Éclair", carte.getNomCarte());
        assertEquals("Une attaque spéciale fulgurante", carte.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carte.getType());
        assertEquals(CarteOffensive.TypeOffensif.COUP_SPECIAL, carte.getTypeOffensif());
        assertEquals(3, carte.getValeur());
        assertEquals(2, carte.getCoutSpecial());
        assertFalse(carte.estJouee());
    }
    
    @Test
    @DisplayName("Test de conversion depuis CarteAttaque")
    public void testFromCarteAttaque() {
        CarteAttaque carteAttaque = new CarteAttaque("Hache", "Une hache redoutable", 5, 3, 12);
        CarteOffensive carteOffensive = CarteOffensive.fromCarteAttaque(carteAttaque);
        
        assertEquals("Hache", carteOffensive.getNomCarte());
        assertEquals("Une hache redoutable", carteOffensive.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carteOffensive.getType());
        assertEquals(CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE, carteOffensive.getTypeOffensif());
        assertEquals(5, carteOffensive.getDegatsInfliges());
        assertEquals(3, carteOffensive.getDegatsSubis());
        assertEquals(12, carteOffensive.getCout());
    }
    
    @Test
    @DisplayName("Test de conversion depuis CarteSoin")
    public void testFromCarteSoin() {
        CarteSoin carteSoin = new CarteSoin("Grande Potion", "Une grande potion de soin", 8);
        carteSoin.setCout(10);
        
        CarteOffensive carteOffensive = CarteOffensive.fromCarteSoin(carteSoin);
        
        assertEquals("Grande Potion", carteOffensive.getNomCarte());
        assertEquals("Une grande potion de soin", carteOffensive.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carteOffensive.getType());
        assertEquals(CarteOffensive.TypeOffensif.SOIN, carteOffensive.getTypeOffensif());
        assertEquals(8, carteOffensive.getVieGagnee());
        assertEquals(10, carteOffensive.getCout());
    }
    
    @Test
    @DisplayName("Test de conversion depuis CarteCoupSpecial")
    public void testFromCarteCoupSpecial() {
        CarteCoupSpecial carteCoupSpecial = new CarteCoupSpecial("Vengeance", "Une vengeance terrible", 6, 3);
        CarteOffensive carteOffensive = CarteOffensive.fromCarteCoupSpecial(carteCoupSpecial);
        
        assertEquals("Vengeance", carteOffensive.getNomCarte());
        assertEquals("Une vengeance terrible", carteOffensive.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carteOffensive.getType());
        assertEquals(CarteOffensive.TypeOffensif.COUP_SPECIAL, carteOffensive.getTypeOffensif());
        assertEquals(6, carteOffensive.getValeur());
        assertEquals(3, carteOffensive.getCoutSpecial());
    }
    
    @Test
    @DisplayName("Test des getters et setters")
    public void testGettersSetters() {
        CarteOffensive carte = new CarteOffensive("Test", "Carte de test", 1, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        
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
        CarteOffensive carteAttaque = new CarteOffensive("Épée", "Une épée tranchante", 3, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        CarteOffensive carteCoupSpecial = new CarteOffensive("Frappe Éclair", "Une attaque spéciale", 4, 2);
        CarteOffensive carteSoin = new CarteOffensive("Potion", "Une potion de soin", 5);
        
        // Tests pour carte Attaque
        assertTrue(carteAttaque.estAttaqueDirecte());
        assertFalse(carteAttaque.estCoupSpecial());
        assertFalse(carteAttaque.estSoin());
        assertFalse(carteAttaque.estTresorOffensif());
        
        // Tests pour carte Coup Spécial
        assertFalse(carteCoupSpecial.estAttaqueDirecte());
        assertTrue(carteCoupSpecial.estCoupSpecial());
        assertFalse(carteCoupSpecial.estSoin());
        assertFalse(carteCoupSpecial.estTresorOffensif());
        
        // Tests pour carte Soin
        assertFalse(carteSoin.estAttaqueDirecte());
        assertFalse(carteSoin.estCoupSpecial());
        assertTrue(carteSoin.estSoin());
        assertFalse(carteSoin.estTresorOffensif());
    }
    
    @Test
    @DisplayName("Test de la méthode effetCarte pour carte d'attaque directe")
    public void testEffetCarteAttaqueDirecte() {
        CarteOffensive carte = new CarteOffensive("Épée", "Une épée tranchante", 3, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        
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
    @DisplayName("Test de la méthode effetCarte pour coup spécial")
    public void testEffetCarteCoupSpecial() {
        CarteOffensive carte = new CarteOffensive("Frappe Éclair", "Une attaque spéciale", 4, 2);
        
        Carte.EffetCarte effet = carte.effetCarte();
        
        assertEquals(4, effet.degatsInfliges);
        assertTrue(effet.estSpeciale);
        assertTrue(effet.estAttaque);
        assertFalse(effet.estSoin);
        assertEquals("Coup spécial", effet.effetSpecial);
        assertEquals(0, effet.orGagne);
        assertEquals(0, effet.orPerdu);
    }
    
    @Test
    @DisplayName("Test de la méthode toString pour carte d'attaque")
    public void testToStringAttaqueDirecte() {
        CarteOffensive carte = new CarteOffensive("Épée", "Une épée tranchante", 3, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        
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
}