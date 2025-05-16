package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import carte.Carte;
import carte.CartePopularite;
import carte.CarteStrategique;
import carte.CarteTresor;
import carte.TypeCarte;
import carte.CarteStrategique.TypeStrategique;

/**
 * Tests pour les classes dérivées de CarteStrategique (CarteTresor, CartePopularite)
 * La classe CarteStrategique est maintenant abstraite, les tests ont été adaptés
 * pour utiliser les classes concrètes
 */
@DisplayName("Tests pour les classes dérivées de CarteStrategique")
public class TestCartesStrategiques {
    
    @Test
    @DisplayName("Test de la création d'une carte de popularité")
    public void testCreationCartePopularite() {
        CartePopularite carte = new CartePopularite("Chanson", "Une chanson entrainante", 2, 1);
        
        assertEquals("Chanson", carte.getNomCarte());
        assertEquals("Une chanson entrainante", carte.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carte.getType());
        assertEquals(2, carte.effetCarte().populariteGagnee);
        assertEquals(1, carte.effetCarte().degatsSubis);
        assertTrue(carte.estPopularite());
        assertFalse(carte.estTresor());
    }

    @Test
    @DisplayName("Test de la création d'une carte de popularité avec l'autre constructeur")
    public void testCreationCartePopulariteConstructeurAutre() {
        CarteStrategique carte = new CarteStrategique("Chanson", "Une chanson entrainante", TypeStrategique.POPULARITE) {
            @Override
            public EffetCarte effetCarte() {
                return new EffetCarte();
            }

            @Override
            public String getCheminImage() {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'getCheminImage'");
            }
        };
        
        assertEquals("Chanson", carte.getNomCarte());
        assertEquals("Une chanson entrainante", carte.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carte.getType());
        assertEquals(TypeStrategique.POPULARITE, carte.getTypeStrategique());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte de popularité avec coût")
    public void testCreationCartePopulariteAvecCout() {
        CartePopularite carte = new CartePopularite("Chanson", "Une chanson entrainante", 10, 2, 1);
        
        assertEquals("Chanson", carte.getNomCarte());
        assertEquals("Une chanson entrainante", carte.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carte.getType());
        assertEquals(2, carte.effetCarte().populariteGagnee);
        assertEquals(1, carte.effetCarte().degatsSubis);
        assertEquals(10, carte.getCout());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte trésor")
    public void testCreationCarteTresor() {
        CarteTresor carte = new CarteTresor("Coffre", "Un coffre au trésor", 10);
        
        assertEquals("Coffre", carte.getNomCarte());
        assertEquals("Un coffre au trésor", carte.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carte.getType());
        assertEquals(10, carte.effetCarte().orGagne);
        assertFalse(carte.estPopularite());
        assertTrue(carte.estTresor());
    }
    
    @Test
    @DisplayName("Test de la création d'une carte trésor avec coût")
    public void testCreationCarteTresorAvecCout() {
        CarteTresor carte = new CarteTresor("Coffre", "Un coffre au trésor", 25, 10);
        
        assertEquals("Coffre", carte.getNomCarte());
        assertEquals("Un coffre au trésor", carte.getDescription());
        assertEquals(TypeCarte.STRATEGIQUE, carte.getType());
        assertEquals(10, carte.effetCarte().orGagne);
        assertEquals(25, carte.getCout());
    }
    
    @Test
    @DisplayName("Test des effets de cartes stratégiques par type")
    public void testEffetsCarteStrategique() {
        // Carte de popularité
        CartePopularite cartePopularite = new CartePopularite("Chanson", "Une chanson entrainante", 2, 1);
        Carte.EffetCarte effetPopularite = cartePopularite.effetCarte();
        
        assertEquals(2, effetPopularite.populariteGagnee);
        assertEquals(1, effetPopularite.degatsSubis);
        assertEquals(0, effetPopularite.orGagne);
        
        // Carte trésor
        CarteTresor carteTresor = new CarteTresor("Coffre", "Un coffre au trésor", 10);
        Carte.EffetCarte effetTresor = carteTresor.effetCarte();
        
        assertEquals(10, effetTresor.orGagne);
        assertEquals(0, effetTresor.populariteGagnee);
        assertEquals(0, effetTresor.degatsSubis);
    }
    
    @Test
    @DisplayName("Test de la méthode toString pour tous les types")
    public void testToString() {
        // Carte de popularité
        CartePopularite cartePopularite = new CartePopularite("Chanson", "Une chanson entrainante", 2, 1);
        String descriptionPopularite = cartePopularite.toString();
        
        assertTrue(descriptionPopularite.contains("Chanson"));
        assertTrue(descriptionPopularite.contains("Une chanson entrainante"));
        assertTrue(descriptionPopularite.contains("Popularité"));
        assertTrue(descriptionPopularite.contains("2"));
        assertTrue(descriptionPopularite.contains("Dégâts subis"));
        assertTrue(descriptionPopularite.contains("1"));
        
        // Carte trésor avec or gagné
        CarteTresor carteTresorGain = new CarteTresor("Coffre", "Un coffre au trésor", 10);
        String descriptionTresorGain = carteTresorGain.toString();
        
        assertTrue(descriptionTresorGain.contains("Coffre"));
        assertTrue(descriptionTresorGain.contains("Un coffre au trésor"));
        assertTrue(descriptionTresorGain.contains("Or gagné"));
        assertTrue(descriptionTresorGain.contains("10"));
        
        // Carte trésor sans gain
        CarteTresor carteTresorVide = new CarteTresor("Coffre Vide", "Un coffre vide", 0);
        String descriptionTresorVide = carteTresorVide.toString();
        
        assertTrue(descriptionTresorVide.contains("Coffre Vide"));
        assertTrue(descriptionTresorVide.contains("Un coffre vide"));
        assertFalse(descriptionTresorVide.contains("Or gagné"));
        
        // Carte trésor avec or gagné
        CarteTresor carteTresorRiche = new CarteTresor("Coffre Riche", "Un coffre très riche", 8);
        String descriptionTresorRiche = carteTresorRiche.toString();
        
        assertTrue(descriptionTresorRiche.contains("Coffre Riche"));
        assertTrue(descriptionTresorRiche.contains("Un coffre très riche"));
        assertTrue(descriptionTresorRiche.contains("Or gagné"));
        assertTrue(descriptionTresorRiche.contains("8"));
    }
}
