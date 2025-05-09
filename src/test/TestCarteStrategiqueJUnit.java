package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import carte.*;

/**
 * Tests JUnit pour la classe CarteStrategique
 */
public class TestCarteStrategiqueJUnit {
    
    @Test
    @DisplayName("Test des constructeurs pour carte de popularité")
    public void testConstructeurPopularite() {
        // Test du constructeur de base pour popularité
        CarteStrategique cartePopularite = new CarteStrategique(
            "Chanson", "Une chanson qui augmente la popularité", 3, 1);
        
        assertEquals("Chanson", cartePopularite.getNomCarte());
        assertEquals("Une chanson qui augmente la popularité", cartePopularite.getDescription());
        assertEquals(3, cartePopularite.getValeur()); // popularité gagnée
        assertEquals(1, cartePopularite.getValeurSecondaire()); // dégâts subis
        assertEquals(CarteStrategique.TypeStrategique.POPULARITE, cartePopularite.getTypeStrategique());
        assertTrue(cartePopularite.estPopularite());
        
        // Test du constructeur avec coût
        CarteStrategique cartePopulariteAvecCout = new CarteStrategique(
            "Danse", "Une danse qui augmente la popularité", 4, 2, 5);
        
        assertEquals("Danse", cartePopulariteAvecCout.getNomCarte());
        assertEquals(4, cartePopulariteAvecCout.getValeur());
        assertEquals(2, cartePopulariteAvecCout.getValeurSecondaire());
        assertEquals(5, cartePopulariteAvecCout.getCout());
    }
    
    @Test
    @DisplayName("Test des constructeurs pour carte passive")
    public void testConstructeurPassive() {
        // Test du constructeur de base pour passive
        CarteStrategique cartePassive = new CarteStrategique(
            "Bouclier", "Un bouclier qui protège", 2, 3, "Protection");
        
        assertEquals("Bouclier", cartePassive.getNomCarte());
        assertEquals("Un bouclier qui protège", cartePassive.getDescription());
        assertEquals(2, cartePassive.getValeur());
        assertEquals(3, cartePassive.getDuree());
        assertEquals("Protection", cartePassive.getEffetSpecial());
        assertEquals(CarteStrategique.TypeStrategique.PASSIVE, cartePassive.getTypeStrategique());
        assertTrue(cartePassive.estPassive());
        
        // Test du constructeur avec coût
        CarteStrategique cartePassiveAvecCout = new CarteStrategique(
            "Armure", "Une armure qui protège", 3, 4, "Protection renforcée", 6);
        
        assertEquals("Armure", cartePassiveAvecCout.getNomCarte());
        assertEquals(3, cartePassiveAvecCout.getValeur());
        assertEquals(4, cartePassiveAvecCout.getDuree());
        assertEquals(6, cartePassiveAvecCout.getCout());
    }
    
    @Test
    @DisplayName("Test des constructeurs pour carte spéciale")
    public void testConstructeurSpeciale() {
        // Test du constructeur de base pour spéciale
        CarteStrategique carteSpeciale = new CarteStrategique(
            "Vision", "Permet de voir les cartes adverses", "Espionnage", 1);
        
        assertEquals("Vision", carteSpeciale.getNomCarte());
        assertEquals("Permet de voir les cartes adverses", carteSpeciale.getDescription());
        assertEquals("Espionnage", carteSpeciale.getEffetSpecial());
        assertEquals(1, carteSpeciale.getValeur());
        assertEquals(CarteStrategique.TypeStrategique.SPECIALE, carteSpeciale.getTypeStrategique());
        assertTrue(carteSpeciale.estSpeciale());
        
        // Test du constructeur avec coût
        CarteStrategique carteSpecialeAvecCout = new CarteStrategique(
            "Boule de cristal", "Permet de voir l'avenir", "Prédiction", 2, 7);
        
        assertEquals("Boule de cristal", carteSpecialeAvecCout.getNomCarte());
        assertEquals("Prédiction", carteSpecialeAvecCout.getEffetSpecial());
        assertEquals(2, carteSpecialeAvecCout.getValeur());
        assertEquals(7, carteSpecialeAvecCout.getCout());
    }
    
    @Test
    @DisplayName("Test des constructeurs pour carte trésor")
    public void testConstructeurTresor() {
        // Test du constructeur de base pour trésor
        CarteStrategique carteTresor = new CarteStrategique(
            "Coffre", "Un coffre au trésor", 10, 0, true);
        
        assertEquals("Coffre", carteTresor.getNomCarte());
        assertEquals("Un coffre au trésor", carteTresor.getDescription());
        assertEquals(10, carteTresor.getOrGagne());
        assertEquals(0, carteTresor.getOrPerdu());
        assertEquals(CarteStrategique.TypeStrategique.TRESOR, carteTresor.getTypeStrategique());
        assertTrue(carteTresor.estTresor());
        
        // Test du constructeur avec coût
        CarteStrategique carteTresorAvecCout = new CarteStrategique(
            "Diamant", "Un diamant précieux", 15, 0, true, 8);
        
        assertEquals("Diamant", carteTresorAvecCout.getNomCarte());
        assertEquals(15, carteTresorAvecCout.getOrGagne());
        assertEquals(8, carteTresorAvecCout.getCout());
        
        // Test d'une carte trésor qui fait perdre de l'or
        CarteStrategique cartePerte = new CarteStrategique(
            "Malédiction", "Une malédiction qui fait perdre de l'or", 0, 5, true);
        
        assertEquals("Malédiction", cartePerte.getNomCarte());
        assertEquals(0, cartePerte.getOrGagne());
        assertEquals(5, cartePerte.getOrPerdu());
    }
    
    @Test
    @DisplayName("Test des méthodes de vérification de type")
    public void testVerificationType() {
        // Carte de popularité
        CarteStrategique cartePopularite = new CarteStrategique(
            "Chanson", "Une chanson populaire", 3, 1);
        assertTrue(cartePopularite.estPopularite());
        assertFalse(cartePopularite.estPassive());
        assertFalse(cartePopularite.estSpeciale());
        assertFalse(cartePopularite.estTresor());
        
        // Carte passive
        CarteStrategique cartePassive = new CarteStrategique(
            "Bouclier", "Un bouclier protecteur", 2, 3, "Protection");
        assertTrue(cartePassive.estPassive());
        assertFalse(cartePassive.estPopularite());
        assertFalse(cartePassive.estSpeciale());
        assertFalse(cartePassive.estTresor());
        
        // Carte spéciale
        CarteStrategique carteSpeciale = new CarteStrategique(
            "Vision", "Permet de voir les cartes", "Vision", 1);
        assertTrue(carteSpeciale.estSpeciale());
        assertFalse(carteSpeciale.estPopularite());
        assertFalse(carteSpeciale.estPassive());
        assertFalse(carteSpeciale.estTresor());
        
        // Carte trésor
        CarteStrategique carteTresor = new CarteStrategique(
            "Coffre", "Un coffre au trésor", 10, 0, true);
        assertTrue(carteTresor.estTresor());
        assertFalse(carteTresor.estPopularite());
        assertFalse(carteTresor.estPassive());
        assertFalse(carteTresor.estSpeciale());
    }
    
    @Test
    @DisplayName("Test des accesseurs spécifiques selon le type")
    public void testAccesseursSpecifiques() {
        // Carte de popularité
        CarteStrategique cartePopularite = new CarteStrategique(
            "Chanson", "Une chanson populaire", 3, 1);
        assertEquals(3, cartePopularite.getPopulariteGagnee());
        assertEquals(1, cartePopularite.getDegatsSubis());
        
        // Carte passive
        CarteStrategique cartePassive = new CarteStrategique(
            "Bouclier", "Un bouclier protecteur", 2, 3, "Protection");
        assertEquals(2, cartePassive.getValeur());
        assertEquals(3, cartePassive.getDuree());
        assertEquals("Protection", cartePassive.getTypeEffet());
        
        // Carte trésor
        CarteStrategique carteTresor = new CarteStrategique(
            "Coffre", "Un coffre au trésor", 10, 5, true);
        assertEquals(10, carteTresor.getOrGagne());
        assertEquals(5, carteTresor.getOrPerdu());
    }
    
    @Test
    @DisplayName("Test de la méthode reduireDuree")
    public void testReduireDuree() {
        // Carte passive avec durée de 3 tours
        CarteStrategique cartePassive = new CarteStrategique(
            "Bouclier", "Un bouclier protecteur", 2, 3, "Protection");
        
        // Réduire une fois
        assertTrue(cartePassive.reduireDuree());
        assertEquals(2, cartePassive.getDuree());
        
        // Réduire une deuxième fois
        assertTrue(cartePassive.reduireDuree());
        assertEquals(1, cartePassive.getDuree());
        
        // Réduire une troisième fois - la durée devient 0
        assertFalse(cartePassive.reduireDuree());
        assertEquals(0, cartePassive.getDuree());
    }
    
    @Test
    @DisplayName("Test de la méthode effetCarte")
    public void testEffetCarte() {
        // Carte de popularité
        CarteStrategique cartePopularite = new CarteStrategique(
            "Chanson", "Une chanson populaire", 3, 1);
        Carte.EffetCarte effetPopularite = cartePopularite.effetCarte();
        assertEquals(3, effetPopularite.populariteGagnee);
        assertEquals(1, effetPopularite.degatsSubis);
        assertTrue(effetPopularite.estPopularite);
        
        // Carte passive
        CarteStrategique cartePassive = new CarteStrategique(
            "Bouclier", "Un bouclier protecteur", 2, 3, "Protection");
        Carte.EffetCarte effetPassif = cartePassive.effetCarte();
        assertEquals(2, effetPassif.populariteGagnee); // La valeur est stockée dans populariteGagnee
        assertEquals(3, effetPassif.dureeEffet);
        assertEquals("Protection", effetPassif.effetSpecial);
        assertTrue(effetPassif.estPassive);
        
        // Carte spéciale
        CarteStrategique carteSpeciale = new CarteStrategique(
            "Vision", "Permet de voir les cartes", "Espionnage", 1);
        Carte.EffetCarte effetSpecial = carteSpeciale.effetCarte();
        assertEquals(1, effetSpecial.populariteGagnee); // La valeur est stockée dans populariteGagnee
        assertEquals("Espionnage", effetSpecial.effetSpecial);
        assertTrue(effetSpecial.estSpeciale);
        
        // Carte trésor
        CarteStrategique carteTresor = new CarteStrategique(
            "Coffre", "Un coffre au trésor", 10, 5, true);
        Carte.EffetCarte effetTresor = carteTresor.effetCarte();
        assertEquals(10, effetTresor.orGagne);
        assertEquals(5, effetTresor.orPerdu);
        assertTrue(effetTresor.estTresor);
    }
    
    @Test
    @DisplayName("Test des getters et setters")
    public void testGettersSetters() {
        CarteStrategique carte = new CarteStrategique(
            "Test", "Carte de test", 2, 3, "Test");
        
        // Test des setters
        carte.setTypeStrategique(CarteStrategique.TypeStrategique.SPECIALE);
        carte.setDuree(5);
        carte.setEffetSpecial("Nouveau test");
        carte.setReutilisable(true);
        carte.setCout(8);
        
        // Test des getters après modifications
        assertEquals(CarteStrategique.TypeStrategique.SPECIALE, carte.getTypeStrategique());
        assertEquals(5, carte.getDuree());
        assertEquals("Nouveau test", carte.getEffetSpecial());
        assertTrue(carte.estReutilisable());
        assertEquals(8, carte.getCout());
    }
    
    @Test
    @DisplayName("Test de la méthode toString")
    public void testToString() {
        // Carte de popularité
        CarteStrategique cartePopularite = new CarteStrategique(
            "Chanson", "Une chanson populaire", 3, 1);
        String descPopularite = cartePopularite.toString();
        assertTrue(descPopularite.contains("Chanson"));
        assertTrue(descPopularite.contains("Une chanson populaire"));
        assertTrue(descPopularite.contains("Popularité gagnée: 3"));
        assertTrue(descPopularite.contains("Dégâts subis: 1"));
        
        // Carte passive
        CarteStrategique cartePassive = new CarteStrategique(
            "Bouclier", "Un bouclier protecteur", 2, 3, "Protection");
        String descPassive = cartePassive.toString();
        assertTrue(descPassive.contains("Bouclier"));
        assertTrue(descPassive.contains("Un bouclier protecteur"));
        assertTrue(descPassive.contains("Valeur: 2"));
        assertTrue(descPassive.contains("Durée: 3 tours"));
        assertTrue(descPassive.contains("Type d'effet: Protection"));
        
        // Carte spéciale
        CarteStrategique carteSpeciale = new CarteStrategique(
            "Vision", "Permet de voir les cartes", "Espionnage", 1);
        carteSpeciale.setReutilisable(true);
        String descSpeciale = carteSpeciale.toString();
        assertTrue(descSpeciale.contains("Vision"));
        assertTrue(descSpeciale.contains("Permet de voir les cartes"));
        assertTrue(descSpeciale.contains("Valeur: 1"));
        assertTrue(descSpeciale.contains("Effet spécial: Espionnage"));
        assertTrue(descSpeciale.contains("Réutilisable: Oui"));
        
        // Carte trésor - gain d'or
        CarteStrategique carteTresor = new CarteStrategique(
            "Coffre", "Un coffre au trésor", 10, 0, true);
        String descTresor = carteTresor.toString();
        assertTrue(descTresor.contains("Coffre"));
        assertTrue(descTresor.contains("Un coffre au trésor"));
        assertTrue(descTresor.contains("Or gagné: 10"));
        
        // Carte trésor - perte d'or
        CarteStrategique cartePerte = new CarteStrategique(
            "Malédiction", "Une malédiction qui fait perdre de l'or", 0, 5, true);
        String descPerte = cartePerte.toString();
        assertTrue(descPerte.contains("Malédiction"));
        assertTrue(descPerte.contains("Une malédiction qui fait perdre de l'or"));
        assertTrue(descPerte.contains("Or perdu: 5"));
    }
}