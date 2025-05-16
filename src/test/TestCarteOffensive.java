package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import carte.Carte;
import carte.CarteAttaque;
import carte.CarteOffensive;
import carte.CarteOffensive.TypeOffensif;
import carte.CarteSoin;
import carte.TypeCarte;

/**
 * Tests pour les classes héritant de CarteOffensive
 * Note: La classe CarteOffensive est maintenant abstraite, ce test utilise les classes concrètes
 */
@DisplayName("Tests pour les cartes offensives")
public class TestCarteOffensive {
    
    @Test
    @DisplayName("Test de la création d'une carte d'attaque directe")
    public void testCreationCarteAttaqueDirecte() {
        CarteAttaque carte = new CarteAttaque("Épée", "Une épée tranchante", 10, 3, 1);
        
        assertEquals("Épée", carte.getNomCarte());
        assertEquals("Une épée tranchante", carte.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carte.getType());
        assertEquals(TypeOffensif.ATTAQUE, carte.getTypeOffensif());
        assertEquals(3, carte.effetCarte().degatsInfliges);
        assertEquals(1, carte.effetCarte().degatsSubis);
    }

    @Test
    @DisplayName("Test de la création d'une carte avec l'autre constructeur")
    public void testCreationCarteAutreConstructeur() {
        CarteOffensive carte = new CarteOffensive("Épée", "Une épée tranchante", TypeOffensif.ATTAQUE) {
            @Override
            public EffetCarte effetCarte() {
                return new EffetCarte();
            }

            @Override
            public String getCheminImage() {
                return "chemin/image.png";
            }
        };
        
        assertEquals("Épée", carte.getNomCarte());
        assertEquals("Une épée tranchante", carte.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carte.getType());
        assertEquals(TypeOffensif.ATTAQUE, carte.getTypeOffensif());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte offensive avec coût")
    public void testCreationCarteOffensiveAvecCout() {
        CarteAttaque carte = new CarteAttaque("Mousquet", "Un mousquet puissant", 15, 4, 2);
        
        assertEquals("Mousquet", carte.getNomCarte());
        assertEquals("Un mousquet puissant", carte.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carte.getType());
        assertEquals(TypeOffensif.ATTAQUE, carte.getTypeOffensif());
        assertEquals(4, carte.effetCarte().degatsInfliges);
        assertEquals(2, carte.effetCarte().degatsSubis);
        assertEquals(15, carte.getCout());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte de soin")
    public void testCreationCarteSoin() {
        CarteSoin carte = new CarteSoin("Potion", "Une potion de soin", 5);
        
        assertEquals("Potion", carte.getNomCarte());
        assertEquals("Une potion de soin", carte.getDescription());
        assertEquals(TypeCarte.OFFENSIVE, carte.getType());
        assertEquals(TypeOffensif.SOIN, carte.getTypeOffensif());
        assertEquals(5, carte.effetCarte().vieGagne);
    }
    
    @Test
    @DisplayName("Test des getters et setters")
    public void testGettersSetters() {
        CarteAttaque carte = new CarteAttaque("Test", "Carte de test", 10, 3, 1);
        
        // Test setters
        carte.setNomCarte("Test modifié");
        carte.setDescription("Description modifiée");
        carte.setCout(15);
        
        // Test getters
        assertEquals("Test modifié", carte.getNomCarte());
        assertEquals("Description modifiée", carte.getDescription());
        assertEquals(15, carte.getCout());
    }
    
    @Test
    @DisplayName("Test des types de cartes offensives")
    public void testTypesCarteOffensive() {
        CarteAttaque carteAttaque = new CarteAttaque("Épée", "Une épée tranchante", 10, 3, 1);
        CarteSoin carteSoin = new CarteSoin("Potion", "Une potion de soin", 5);
        
        // Tests pour carte Attaque
        assertEquals(TypeOffensif.ATTAQUE, carteAttaque.getTypeOffensif());
        
        // Tests pour carte Soin
        assertEquals(TypeOffensif.SOIN, carteSoin.getTypeOffensif());
    }
    
    @Test
    @DisplayName("Test de la méthode effetCarte pour carte d'attaque directe")
    public void testEffetCarteAttaqueDirecte() {
        CarteAttaque carte = new CarteAttaque("Épée", "Une épée tranchante", 10, 3, 1);
        
        Carte.EffetCarte effet = carte.effetCarte();
        
        assertEquals(3, effet.degatsInfliges);
        assertEquals(1, effet.degatsSubis);
    }
    
    @Test
    @DisplayName("Test de la méthode effetCarte pour carte de soin")
    public void testEffetCarteSoin() {
        CarteSoin carte = new CarteSoin("Potion", "Une potion de soin", 5);
        
        Carte.EffetCarte effet = carte.effetCarte();
        
        assertEquals(5, effet.vieGagne);
        assertEquals(0, effet.degatsInfliges);
        assertEquals(0, effet.degatsSubis);
    }
    
    @Test
    @DisplayName("Test de la méthode toString")
    public void testToString() {
        // Test toString pour carte d'attaque
        CarteAttaque carteAttaque = new CarteAttaque("Épée", "Une épée tranchante", 10, 3, 1);
        String descriptionAttaque = carteAttaque.toString();
        
        assertTrue(descriptionAttaque.contains("Épée"));
        assertTrue(descriptionAttaque.contains("Une épée tranchante"));
        
        // Test toString pour carte de soin
        CarteSoin carteSoin = new CarteSoin("Potion", "Une potion de soin", 5);
        String descriptionSoin = carteSoin.toString();
        
        assertTrue(descriptionSoin.contains("Potion"));
        assertTrue(descriptionSoin.contains("Une potion de soin"));
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