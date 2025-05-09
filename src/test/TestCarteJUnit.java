package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import carte.Carte;
import carte.TypeCarte;

/**
 * Tests JUnit pour la classe Carte
 */
public class TestCarteJUnit {
    
    @Test
    @DisplayName("Test des constructeurs de carte")
    public void testConstructeurs() {
        // Test du constructeur complet
        Carte carteComplete = new Carte(TypeCarte.OFFENSIVE, "Épée", "Une épée tranchante", 3, 1, 5);
        assertEquals(TypeCarte.OFFENSIVE, carteComplete.getType());
        assertEquals("Épée", carteComplete.getNomCarte());
        assertEquals("Une épée tranchante", carteComplete.getDescription());
        assertEquals(3, carteComplete.getValeur());
        assertEquals(1, carteComplete.getValeurSecondaire());
        assertEquals(5, carteComplete.getCout());
        assertTrue(carteComplete.getId() > 0);
        
        // Test du constructeur sans coût
        Carte carteSansCout = new Carte(TypeCarte.STRATEGIQUE, "Chant", "Un chant motivant", 2, 0);
        assertEquals(TypeCarte.STRATEGIQUE, carteSansCout.getType());
        assertEquals("Chant", carteSansCout.getNomCarte());
        assertEquals("Un chant motivant", carteSansCout.getDescription());
        assertEquals(2, carteSansCout.getValeur());
        assertEquals(0, carteSansCout.getValeurSecondaire());
        assertEquals(10, carteSansCout.getCout()); // Coût par défaut à 10
        assertTrue(carteSansCout.getId() > 0);
        
        // Test du constructeur simplifié
        Carte carteSimplifie = new Carte(TypeCarte.OFFENSIVE, "Dague", "Une dague pointue");
        assertEquals(TypeCarte.OFFENSIVE, carteSimplifie.getType());
        assertEquals("Dague", carteSimplifie.getNomCarte());
        assertEquals("Une dague pointue", carteSimplifie.getDescription());
        assertEquals(0, carteSimplifie.getValeur()); // Valeur par défaut
        assertEquals(0, carteSimplifie.getValeurSecondaire()); // Valeur par défaut
        assertEquals(10, carteSimplifie.getCout()); // Coût par défaut
        assertTrue(carteSimplifie.getId() > 0);
        
        // Test du constructeur JSON
        Carte carteJson = new Carte("CarteJson", 42, TypeCarte.STRATEGIQUE);
        assertEquals(TypeCarte.STRATEGIQUE, carteJson.getType());
        assertEquals("CarteJson", carteJson.getNomCarte());
        assertEquals("ID: 42", carteJson.getDescription());
        assertEquals(42, carteJson.getId()); // ID spécifié
    }
    
    @Test
    @DisplayName("Test des getters et setters")
    public void testGettersSetters() {
        Carte carte = new Carte(TypeCarte.OFFENSIVE, "Épée", "Une épée tranchante", 3, 1, 5);
        
        // Test setters
        carte.setNomCarte("Épée magique");
        carte.setDescription("Une épée magique brillante");
        carte.setValeur(5);
        carte.setValeurSecondaire(2);
        carte.setCout(8);
        carte.setCheminImage("chemin/personnalisé/épée.png");
        
        // Test getters après les modifications
        assertEquals("Épée magique", carte.getNomCarte());
        assertEquals("Une épée magique brillante", carte.getDescription());
        assertEquals(5, carte.getValeur());
        assertEquals(2, carte.getValeurSecondaire());
        assertEquals(8, carte.getCout());
        assertEquals("chemin/personnalisé/épée.png", carte.getCheminImage());
    }
    
    @Test
    @DisplayName("Test de la méthode effetCarte par défaut")
    public void testEffetCarteDefaut() {
        Carte carte = new Carte(TypeCarte.OFFENSIVE, "Épée", "Une épée tranchante", 3, 1, 5);
        
        // Test de la méthode effetCarte sans redéfinition
        Carte.EffetCarte effet = carte.effetCarte();
        
        // Vérifier que tous les effets sont à leurs valeurs par défaut
        assertEquals(0, effet.degatsInfliges);
        assertEquals(0, effet.degatsSubis);
        assertEquals(0, effet.populariteGagnee);
        assertEquals(0, effet.vieGagnee);
        assertEquals(0, effet.orGagne);
        assertEquals(0, effet.orPerdu);
        assertNull(effet.effetSpecial);
        assertEquals(0, effet.dureeEffet);
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
        // Création d'une carte test avec redéfinition de effetCarte
        Carte carte = new Carte(TypeCarte.OFFENSIVE, "Épée", "Une épée tranchante", 3, 1, 5) {
            @Override
            public EffetCarte effetCarte() {
                EffetCarte effet = super.effetCarte();
                effet.degatsInfliges = 3;
                effet.degatsSubis = 1;
                return effet;
            }
        };
        
        String description = carte.toString();
        
        // Vérifier que la description contient les informations attendues
        assertTrue(description.contains("Épée"));
        assertTrue(description.contains("Une épée tranchante"));
        assertTrue(description.contains("Dégâts infligés: 3"));
        assertTrue(description.contains("Dégâts subis: 1"));
        
        // Test avec d'autres effets
        Carte carteTresor = new Carte(TypeCarte.STRATEGIQUE, "Trésor", "Un coffre au trésor", 10, 0, 5) {
            @Override
            public EffetCarte effetCarte() {
                EffetCarte effet = super.effetCarte();
                effet.orGagne = 10;
                effet.effetSpecial = "Richesse";
                effet.dureeEffet = 2;
                return effet;
            }
        };
        
        String descriptionTresor = carteTresor.toString();
        assertTrue(descriptionTresor.contains("Trésor"));
        assertTrue(descriptionTresor.contains("Un coffre au trésor"));
        assertTrue(descriptionTresor.contains("Or gagné: 10"));
        assertTrue(descriptionTresor.contains("Effet spécial: Richesse"));
        assertTrue(descriptionTresor.contains("(2 tours)"));
    }
    
    @Test
    @DisplayName("Test de l'unicité des identifiants")
    public void testUniciteId() {
        // Création de plusieurs cartes et vérification que leurs IDs sont différents
        Carte carte1 = new Carte(TypeCarte.OFFENSIVE, "Carte1", "Description1");
        Carte carte2 = new Carte(TypeCarte.OFFENSIVE, "Carte2", "Description2");
        Carte carte3 = new Carte(TypeCarte.OFFENSIVE, "Carte3", "Description3");
        
        assertNotEquals(carte1.getId(), carte2.getId());
        assertNotEquals(carte1.getId(), carte3.getId());
        assertNotEquals(carte2.getId(), carte3.getId());
    }
    
    @Test
    @DisplayName("Test du chemin d'image par défaut")
    public void testCheminImageParDefaut() {
        // Vérification que le chemin d'image par défaut est correctement généré
        Carte carte = new Carte(TypeCarte.OFFENSIVE, "Épée Magique", "Une épée magique");
        assertEquals("images/cartes/épée_magique.png", carte.getCheminImage());
        
        // Vérification avec un nom plus complexe
        Carte carteComplexe = new Carte(TypeCarte.STRATEGIQUE, "Grand Coffre Au Trésor", "Un coffre");
        assertEquals("images/cartes/grand_coffre_au_trésor.png", carteComplexe.getCheminImage());
    }
}