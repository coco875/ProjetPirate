package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import carte.*;

/**
 * Tests JUnit pour la classe CarteOffensive
 */
public class TestCarteOffensiveJUnit {
    
    @Test
    @DisplayName("Test des constructeurs de CarteOffensive")
    public void testConstructeurs() {
        // Test du constructeur standard
        CarteOffensive carteAttaque = new CarteOffensive("Épée", "Une épée tranchante", 
                                                    3, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        assertEquals("Épée", carteAttaque.getNomCarte());
        assertEquals("Une épée tranchante", carteAttaque.getDescription());
        assertEquals(3, carteAttaque.getValeur());
        assertEquals(1, carteAttaque.getValeurSecondaire());
        assertEquals(CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE, carteAttaque.getTypeOffensif());
        assertEquals(10, carteAttaque.getCout()); // Coût par défaut
        
        // Test du constructeur avec coût
        CarteOffensive carteAttaqueAvecCout = new CarteOffensive("Hache", "Une hache puissante", 
                                                            4, 2, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE, 5);
        assertEquals("Hache", carteAttaqueAvecCout.getNomCarte());
        assertEquals(4, carteAttaqueAvecCout.getValeur());
        assertEquals(2, carteAttaqueAvecCout.getValeurSecondaire());
        assertEquals(5, carteAttaqueAvecCout.getCout()); // Coût spécifié
        
        // Test du constructeur pour carte de soin
        CarteOffensive carteSoin = new CarteOffensive("Potion", "Une potion de soin", 3);
        assertEquals("Potion", carteSoin.getNomCarte());
        assertEquals(3, carteSoin.getValeur());
        assertEquals(CarteOffensive.TypeOffensif.SOIN, carteSoin.getTypeOffensif());
        
        // Test du constructeur pour coup spécial
        CarteOffensive carteCoupSpecial = new CarteOffensive("Coup Critique", "Un coup critique puissant", 5, 2);
        assertEquals("Coup Critique", carteCoupSpecial.getNomCarte());
        assertEquals(5, carteCoupSpecial.getValeur());
        assertEquals(2, carteCoupSpecial.getCoutSpecial());
        assertEquals(CarteOffensive.TypeOffensif.COUP_SPECIAL, carteCoupSpecial.getTypeOffensif());
    }
    
    @Test
    @DisplayName("Test des méthodes de conversion depuis les anciens types")
    public void testConversionDepuisAncienTypes() {
        // Test de conversion depuis CarteAttaque
        CarteAttaque carteAttaque = new CarteAttaque("Épée", "Une épée tranchante", 3, 1);
        carteAttaque.setCout(5);
        
        CarteOffensive carteOffensive = CarteOffensive.fromCarteAttaque(carteAttaque);
        assertEquals("Épée", carteOffensive.getNomCarte());
        assertEquals("Une épée tranchante", carteOffensive.getDescription());
        assertEquals(3, carteOffensive.getDegatsInfliges());
        assertEquals(1, carteOffensive.getDegatsSubis());
        assertEquals(CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE, carteOffensive.getTypeOffensif());
        assertEquals(5, carteOffensive.getCout());
        
        // Test de conversion depuis CarteSoin
        CarteSoin carteSoin = new CarteSoin("Potion", "Une potion de soin", 3);
        carteSoin.setCout(4);
        
        CarteOffensive carteOffensiveSoin = CarteOffensive.fromCarteSoin(carteSoin);
        assertEquals("Potion", carteOffensiveSoin.getNomCarte());
        assertEquals("Une potion de soin", carteOffensiveSoin.getDescription());
        assertEquals(3, carteOffensiveSoin.getVieGagnee());
        assertEquals(CarteOffensive.TypeOffensif.SOIN, carteOffensiveSoin.getTypeOffensif());
        assertEquals(4, carteOffensiveSoin.getCout());
        
        // Test de conversion depuis CarteCoupSpecial
        CarteCoupSpecial carteCoupSpecial = new CarteCoupSpecial("Coup Critique", "Un coup critique puissant", 5, 2);
        
        CarteOffensive carteOffensiveCoupSpecial = CarteOffensive.fromCarteCoupSpecial(carteCoupSpecial);
        assertEquals("Coup Critique", carteOffensiveCoupSpecial.getNomCarte());
        assertEquals("Un coup critique puissant", carteOffensiveCoupSpecial.getDescription());
        assertEquals(5, carteOffensiveCoupSpecial.getValeur());
        assertEquals(2, carteOffensiveCoupSpecial.getCoutSpecial());
        assertEquals(CarteOffensive.TypeOffensif.COUP_SPECIAL, carteOffensiveCoupSpecial.getTypeOffensif());
    }
    
    @Test
    @DisplayName("Test des getters et setters spécifiques")
    public void testGettersSetters() {
        CarteOffensive carte = new CarteOffensive("Épée", "Une épée tranchante", 
                                             3, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        
        // Test des setters
        carte.setTypeOffensif(CarteOffensive.TypeOffensif.COUP_SPECIAL);
        carte.setEstJouee(true);
        carte.setCoutSpecial(2);
        
        // Test des getters après modifications
        assertEquals(CarteOffensive.TypeOffensif.COUP_SPECIAL, carte.getTypeOffensif());
        assertTrue(carte.estJouee());
        assertEquals(2, carte.getCoutSpecial());
    }
    
    @Test
    @DisplayName("Test des méthodes de vérification de type")
    public void testVerificationType() {
        // Carte d'attaque directe
        CarteOffensive carteAttaque = new CarteOffensive("Épée", "Une épée tranchante", 
                                                    3, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        assertTrue(carteAttaque.estAttaqueDirecte());
        assertFalse(carteAttaque.estCoupSpecial());
        assertFalse(carteAttaque.estSoin());
        assertFalse(carteAttaque.estTresorOffensif()); // Fonction obsolète, toujours false
        
        // Carte de coup spécial
        CarteOffensive carteCoupSpecial = new CarteOffensive("Coup Critique", "Un coup critique", 5, 2);
        assertTrue(carteCoupSpecial.estCoupSpecial());
        assertFalse(carteCoupSpecial.estAttaqueDirecte());
        assertFalse(carteCoupSpecial.estSoin());
        
        // Carte de soin
        CarteOffensive carteSoin = new CarteOffensive("Potion", "Une potion de soin", 3);
        assertTrue(carteSoin.estSoin());
        assertFalse(carteSoin.estAttaqueDirecte());
        assertFalse(carteSoin.estCoupSpecial());
    }
    
    @Test
    @DisplayName("Test des accesseurs spécifiques selon le type")
    public void testAccesseursSpecifiques() {
        // Carte d'attaque
        CarteOffensive carteAttaque = new CarteOffensive("Épée", "Une épée tranchante", 
                                                    3, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        assertEquals(3, carteAttaque.getDegatsInfliges());
        assertEquals(1, carteAttaque.getDegatsSubis());
        assertEquals(0, carteAttaque.getVieGagnee()); // Pas un soin
        assertEquals(0, carteAttaque.getOrVole()); // Fonctionnalité supprimée
        assertEquals(0, carteAttaque.getOrGagne());
        assertEquals(0, carteAttaque.getOrPerdu());
        
        // Carte de soin
        CarteOffensive carteSoin = new CarteOffensive("Potion", "Une potion de soin", 3);
        assertEquals(3, carteSoin.getVieGagnee());
        assertEquals(0, carteSoin.getDegatsInfliges());
        
        // Carte coup spécial
        CarteOffensive carteCoupSpecial = new CarteOffensive("Coup Critique", "Un coup critique", 5, 2);
        assertEquals(5, carteCoupSpecial.getDegatsInfliges());
        assertEquals(2, carteCoupSpecial.getCoutSpecial());
    }
    
    @Test
    @DisplayName("Test de la méthode effetCarte")
    public void testEffetCarte() {
        // Carte d'attaque
        CarteOffensive carteAttaque = new CarteOffensive("Épée", "Une épée tranchante", 
                                                    3, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        Carte.EffetCarte effetAttaque = carteAttaque.effetCarte();
        assertEquals(3, effetAttaque.degatsInfliges);
        assertEquals(1, effetAttaque.degatsSubis);
        assertTrue(effetAttaque.estAttaque);
        assertFalse(effetAttaque.estSoin);
        
        // Carte de soin
        CarteOffensive carteSoin = new CarteOffensive("Potion", "Une potion de soin", 3);
        Carte.EffetCarte effetSoin = carteSoin.effetCarte();
        assertEquals(3, effetSoin.vieGagnee);
        assertTrue(effetSoin.estSoin);
        assertFalse(effetSoin.estAttaque);
        
        // Carte coup spécial
        CarteOffensive carteCoupSpecial = new CarteOffensive("Coup Critique", "Un coup critique", 5, 2);
        Carte.EffetCarte effetCoupSpecial = carteCoupSpecial.effetCarte();
        assertEquals(5, effetCoupSpecial.degatsInfliges);
        assertTrue(effetCoupSpecial.estAttaque);
        assertTrue(effetCoupSpecial.estSpeciale);
        assertEquals("Coup spécial", effetCoupSpecial.effetSpecial);
    }
    
    @Test
    @DisplayName("Test de la méthode toString")
    public void testToString() {
        // Carte d'attaque
        CarteOffensive carteAttaque = new CarteOffensive("Épée", "Une épée tranchante", 
                                                    3, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        String descAttaque = carteAttaque.toString();
        assertTrue(descAttaque.contains("Épée"));
        assertTrue(descAttaque.contains("Une épée tranchante"));
        assertTrue(descAttaque.contains("Dégâts infligés: 3"));
        assertTrue(descAttaque.contains("Dégâts subis: 1"));
        
        // Carte de soin
        CarteOffensive carteSoin = new CarteOffensive("Potion", "Une potion de soin", 3);
        String descSoin = carteSoin.toString();
        assertTrue(descSoin.contains("Potion"));
        assertTrue(descSoin.contains("Une potion de soin"));
        assertTrue(descSoin.contains("Vie gagnée: 3"));
        
        // Carte coup spécial
        CarteOffensive carteCoupSpecial = new CarteOffensive("Coup Critique", "Un coup critique", 5, 2);
        String descCoupSpecial = carteCoupSpecial.toString();
        assertTrue(descCoupSpecial.contains("Coup Critique"));
        assertTrue(descCoupSpecial.contains("Un coup critique"));
        assertTrue(descCoupSpecial.contains("Valeur: 5"));
        assertTrue(descCoupSpecial.contains("Coût spécial: 2"));
    }
}