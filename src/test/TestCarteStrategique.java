package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import carte.Carte;
import carte.CartePopularite;
import carte.CarteTresor;
import carte.CarteStrategique;
import carte.TypeCarte;

/**
 * Tests pour la classe CarteStrategique
 */
@DisplayName("Tests pour la classe CarteStrategique")
public class TestCarteStrategique {
    
    @Test
    @DisplayName("Test de la création d'une carte stratégique de popularité")
    public void testCreationCartePopularite() {
        CarteStrategique carte = new CarteStrategique("Chanson", "Une chanson entrainante", 2, 1);
        
        assertEquals("Chanson", carte.getNomCarte());
        assertEquals("Une chanson entrainante", carte.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carte.getType());
        assertEquals(CarteStrategique.TypeStrategique.POPULARITE, carte.getTypeStrategique());
        assertEquals(2, carte.getPopulariteGagnee());
        assertEquals(1, carte.getDegatsSubis());
        assertTrue(carte.estPopularite());
        assertFalse(carte.estPassive());
        assertFalse(carte.estSpeciale());
        assertFalse(carte.estTresor());
        assertFalse(carte.estReutilisable());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte stratégique de popularité avec coût")
    public void testCreationCartePopulariteAvecCout() {
        CarteStrategique carte = new CarteStrategique("Chanson", "Une chanson entrainante", 2, 1, 10);
        
        assertEquals("Chanson", carte.getNomCarte());
        assertEquals("Une chanson entrainante", carte.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carte.getType());
        assertEquals(CarteStrategique.TypeStrategique.POPULARITE, carte.getTypeStrategique());
        assertEquals(2, carte.getPopulariteGagnee());
        assertEquals(1, carte.getDegatsSubis());
        assertEquals(10, carte.getCout());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte stratégique passive")
    public void testCreationCartePassive() {
        CarteStrategique carte = new CarteStrategique("Bouclier", "Un bouclier protecteur", 3, 2, "protection");
        
        assertEquals("Bouclier", carte.getNomCarte());
        assertEquals("Un bouclier protecteur", carte.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carte.getType());
        assertEquals(CarteStrategique.TypeStrategique.PASSIVE, carte.getTypeStrategique());
        assertEquals(3, carte.getValeur());
        assertEquals(2, carte.getDuree());
        assertEquals("protection", carte.getEffetSpecial());
        assertEquals("protection", carte.getTypeEffet());
        assertFalse(carte.estPopularite());
        assertTrue(carte.estPassive());
        assertFalse(carte.estSpeciale());
        assertFalse(carte.estTresor());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte stratégique passive avec coût")
    public void testCreationCartePassiveAvecCout() {
        CarteStrategique carte = new CarteStrategique("Bouclier", "Un bouclier protecteur", 3, 2, "protection", 15);
        
        assertEquals("Bouclier", carte.getNomCarte());
        assertEquals("Un bouclier protecteur", carte.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carte.getType());
        assertEquals(CarteStrategique.TypeStrategique.PASSIVE, carte.getTypeStrategique());
        assertEquals(3, carte.getValeur());
        assertEquals(2, carte.getDuree());
        assertEquals("protection", carte.getEffetSpecial());
        assertEquals(15, carte.getCout());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte stratégique spéciale")
    public void testCreationCarteSpeciale() {
        CarteStrategique carte = new CarteStrategique("Tempête", "Déclenche une tempête", "tempête", 5);
        
        assertEquals("Tempête", carte.getNomCarte());
        assertEquals("Déclenche une tempête", carte.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carte.getType());
        assertEquals(CarteStrategique.TypeStrategique.SPECIALE, carte.getTypeStrategique());
        assertEquals(5, carte.getValeur());
        assertEquals("tempête", carte.getEffetSpecial());
        assertFalse(carte.estPopularite());
        assertFalse(carte.estPassive());
        assertTrue(carte.estSpeciale());
        assertFalse(carte.estTresor());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte stratégique spéciale avec coût")
    public void testCreationCarteSpecialeAvecCout() {
        CarteStrategique carte = new CarteStrategique("Tempête", "Déclenche une tempête", "tempête", 5, 20);
        
        assertEquals("Tempête", carte.getNomCarte());
        assertEquals("Déclenche une tempête", carte.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carte.getType());
        assertEquals(CarteStrategique.TypeStrategique.SPECIALE, carte.getTypeStrategique());
        assertEquals(5, carte.getValeur());
        assertEquals("tempête", carte.getEffetSpecial());
        assertEquals(20, carte.getCout());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte stratégique trésor")
    public void testCreationCarteTresor() {
        CarteStrategique carte = new CarteStrategique("Coffre", "Un coffre au trésor", 10, true);
        
        assertEquals("Coffre", carte.getNomCarte());
        assertEquals("Un coffre au trésor", carte.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carte.getType());
        assertEquals(CarteStrategique.TypeStrategique.TRESOR, carte.getTypeStrategique());
        assertEquals(10, carte.getOrGagne());
        assertFalse(carte.estPopularite());
        assertFalse(carte.estPassive());
        assertFalse(carte.estSpeciale());
        assertTrue(carte.estTresor());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte stratégique trésor avec coût")
    public void testCreationCarteTresorAvecCout() {
        CarteStrategique carte = new CarteStrategique("Coffre", "Un coffre au trésor", 10, true, 25);
        
        assertEquals("Coffre", carte.getNomCarte());
        assertEquals("Un coffre au trésor", carte.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carte.getType());
        assertEquals(CarteStrategique.TypeStrategique.TRESOR, carte.getTypeStrategique());
        assertEquals(10, carte.getOrGagne());
        assertEquals(25, carte.getCout());
    }
    
    @Test
    @DisplayName("Test des setters")
    public void testSetters() {
        CarteStrategique carte = new CarteStrategique("Test", "Carte de test", 1, 1);
        
        // Test des setters
        carte.setTypeStrategique(CarteStrategique.TypeStrategique.PASSIVE);
        carte.setDuree(3);
        carte.setEffetSpecial("test");
        carte.setReutilisable(true);
        
        // Test des getters correspondants
        assertEquals(CarteStrategique.TypeStrategique.PASSIVE, carte.getTypeStrategique());
        assertEquals(3, carte.getDuree());
        assertEquals("test", carte.getEffetSpecial());
        assertTrue(carte.estReutilisable());
    }
    
    @Test
    @DisplayName("Test de la méthode reduireDuree")
    public void testReduireDuree() {
        CarteStrategique carte = new CarteStrategique("Bouclier", "Un bouclier protecteur", 3, 3, "protection");
        
        // La durée initiale est 3, après réduction elle sera 2
        assertTrue(carte.reduireDuree(), "Après la première réduction, il devrait rester des tours");
        assertEquals(2, carte.getDuree(), "La durée devrait être de 2 après la première réduction");
        
        // Après la deuxième réduction, il reste 1 tour
        assertTrue(carte.reduireDuree(), "Après la deuxième réduction, il devrait rester 1 tour");
        assertEquals(1, carte.getDuree(), "La durée devrait être de 1 après la deuxième réduction");
        
        // Après la troisième réduction, il ne reste plus de tours
        assertFalse(carte.reduireDuree(), "Après la troisième réduction, il ne devrait plus rester de tours");
        assertEquals(0, carte.getDuree(), "La durée devrait être de 0 après la troisième réduction");
    }
    
    @Test
    @DisplayName("Test des effets de carte stratégique par type")
    public void testEffetsCarteStrategique() {
        // Carte de popularité
        CarteStrategique cartePopularite = new CarteStrategique("Chanson", "Une chanson entrainante", 2, 1);
        Carte.EffetCarte effetPopularite = cartePopularite.effetCarte();
        
        assertEquals(2, effetPopularite.populariteGagnee);
        assertEquals(1, effetPopularite.degatsSubis);
        assertTrue(effetPopularite.estPopularite);
        assertFalse(effetPopularite.estPassive);
        assertFalse(effetPopularite.estSpeciale);
        assertFalse(effetPopularite.estTresor);
        
        // Carte passive
        CarteStrategique cartePassive = new CarteStrategique("Bouclier", "Un bouclier protecteur", 3, 2, "protection");
        Carte.EffetCarte effetPassive = cartePassive.effetCarte();
        
        assertEquals(3, effetPassive.populariteGagnee);
        assertEquals(2, effetPassive.dureeEffet);
        assertEquals("protection", effetPassive.effetSpecial);
        assertFalse(effetPassive.estPopularite);
        assertTrue(effetPassive.estPassive);
        assertFalse(effetPassive.estSpeciale);
        assertFalse(effetPassive.estTresor);
        
        // Carte spéciale
        CarteStrategique carteSpeciale = new CarteStrategique("Tempête", "Déclenche une tempête", "tempête", 5);
        Carte.EffetCarte effetSpeciale = carteSpeciale.effetCarte();
        
        assertEquals(5, effetSpeciale.populariteGagnee);
        assertEquals("tempête", effetSpeciale.effetSpecial);
        assertFalse(effetSpeciale.estPopularite);
        assertFalse(effetSpeciale.estPassive);
        assertTrue(effetSpeciale.estSpeciale);
        assertFalse(effetSpeciale.estTresor);
        
        // Carte trésor
        CarteStrategique carteTresor = new CarteStrategique("Coffre", "Un coffre au trésor", 10, true);
        Carte.EffetCarte effetTresor = carteTresor.effetCarte();
        
        assertEquals(10, effetTresor.orGagne);
        assertEquals(0, effetTresor.orPerdu, "L'attribut orPerdu dans EffetCarte devrait toujours être 0 maintenant");
        assertFalse(effetTresor.estPopularite);
        assertFalse(effetTresor.estPassive);
        assertFalse(effetTresor.estSpeciale);
        assertTrue(effetTresor.estTresor);
    }
    
    @Test
    @DisplayName("Test de la méthode toString pour tous les types")
    public void testToString() {
        // Carte de popularité
        CarteStrategique cartePopularite = new CarteStrategique("Chanson", "Une chanson entrainante", 2, 1);
        String descriptionPopularite = cartePopularite.toString();
        
        assertTrue(descriptionPopularite.contains("Chanson"));
        assertTrue(descriptionPopularite.contains("Une chanson entrainante"));
        assertTrue(descriptionPopularite.contains("Popularité gagnée: 2"));
        assertTrue(descriptionPopularite.contains("Dégâts subis: 1"));
        
        // Carte passive
        CarteStrategique cartePassive = new CarteStrategique("Bouclier", "Un bouclier protecteur", 3, 2, "protection");
        String descriptionPassive = cartePassive.toString();
        
        assertTrue(descriptionPassive.contains("Bouclier"));
        assertTrue(descriptionPassive.contains("Un bouclier protecteur"));
        assertTrue(descriptionPassive.contains("Valeur: 3"));
        assertTrue(descriptionPassive.contains("Durée: 2 tours"));
        assertTrue(descriptionPassive.contains("Type d'effet: protection"));
        
        // Carte spéciale
        CarteStrategique carteSpeciale = new CarteStrategique("Tempête", "Déclenche une tempête", "tempête", 5);
        carteSpeciale.setReutilisable(true);
        String descriptionSpeciale = carteSpeciale.toString();
        
        assertTrue(descriptionSpeciale.contains("Tempête"));
        assertTrue(descriptionSpeciale.contains("Déclenche une tempête"));
        assertTrue(descriptionSpeciale.contains("Valeur: 5"));
        assertTrue(descriptionSpeciale.contains("Effet spécial: tempête"));
        assertTrue(descriptionSpeciale.contains("Réutilisable: Oui"));
        
        // Carte trésor avec or gagné
        CarteStrategique carteTresorGain = new CarteStrategique("Coffre", "Un coffre au trésor", 10, true);
        String descriptionTresorGain = carteTresorGain.toString();
        
        assertTrue(descriptionTresorGain.contains("Coffre"));
        assertTrue(descriptionTresorGain.contains("Un coffre au trésor"));
        assertTrue(descriptionTresorGain.contains("Or gagné: 10"));
        assertFalse(descriptionTresorGain.contains("Or perdu"));
        
        // Carte trésor sans gain ni perte
        CarteStrategique carteTresorVide = new CarteStrategique("Coffre Vide", "Un coffre vide", 0, true);
        String descriptionTresorVide = carteTresorVide.toString();
        
        assertTrue(descriptionTresorVide.contains("Coffre Vide"));
        assertTrue(descriptionTresorVide.contains("Un coffre vide"));
        assertFalse(descriptionTresorVide.contains("Or gagné"));
        assertFalse(descriptionTresorVide.contains("Or perdu"));
        
        // Carte trésor avec or gagné
        CarteStrategique carteTresorRiche = new CarteStrategique("Coffre Riche", "Un coffre très riche", 8, true);
        String descriptionTresorRiche = carteTresorRiche.toString();
        
        assertTrue(descriptionTresorRiche.contains("Coffre Riche"));
        assertTrue(descriptionTresorRiche.contains("Un coffre très riche"));
        assertTrue(descriptionTresorRiche.contains("Or gagné: 8"));
    }
}
