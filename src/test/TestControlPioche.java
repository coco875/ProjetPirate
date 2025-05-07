package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import controllers.ControlPioche;
import carte.Carte;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests pour la classe ControlPioche
 */
public class TestControlPioche {
    
    private ControlPioche controlPioche;
    
    @BeforeEach
    public void initialiser() {
        controlPioche = new ControlPioche();
        controlPioche.initialiserPioche();
    }
    
    @Test
    @DisplayName("Test d'initialisation de la pioche")
    public void testInitialiserPioche() {
        // La pioche ne devrait pas être vide après l'initialisation
        assertFalse(controlPioche.estVide());
    }
    
    @Test
    @DisplayName("Test de pioche de cartes")
    public void testPiocher() {
        // Piocher une première carte
        Carte carte1 = controlPioche.piocher();
        assertNotNull(carte1, "La première carte piochée ne devrait pas être null");
        
        // Piocher plusieurs cartes consécutives
        List<Carte> cartesPiochees = new ArrayList<>();
        cartesPiochees.add(carte1);
        
        // On pioche toutes les cartes jusqu'à ce que la pioche soit vide
        Carte carte;
        int compteur = 0;
        int nombreMaxCartes = 100; // Limite pour éviter une boucle infinie en cas de bug
        
        while (!controlPioche.estVide() && compteur < nombreMaxCartes) {
            carte = controlPioche.piocher();
            assertNotNull(carte, "Une carte piochée ne devrait pas être null");
            
            // Vérifier que l'ID de chaque carte est unique
            for (Carte cartePrecedente : cartesPiochees) {
                assertNotEquals(cartePrecedente.getId(), carte.getId(), 
                               "Les IDs des cartes devraient être uniques");
            }
            
            cartesPiochees.add(carte);
            compteur++;
        }
        
        // Après avoir vidé la pioche, on devrait recevoir null
        assertNull(controlPioche.piocher(), "Piocher dans une pioche vide devrait retourner null");
        assertTrue(controlPioche.estVide(), "La pioche devrait être vide");
    }
    
    @Test
    @DisplayName("Test de pioche multiple avec réinitialisation")
    public void testPiochesMultiples() {
        // Vider complètement la première pioche
        while (!controlPioche.estVide()) {
            controlPioche.piocher();
        }
        
        assertTrue(controlPioche.estVide(), "La pioche devrait être vide");
        
        // Réinitialiser la pioche
        controlPioche = new ControlPioche();
        controlPioche.initialiserPioche();
        
        assertFalse(controlPioche.estVide(), "La pioche ne devrait pas être vide après réinitialisation");
        
        // Vérifier qu'on peut piocher à nouveau
        Carte carte = controlPioche.piocher();
        assertNotNull(carte, "On devrait pouvoir piocher après réinitialisation");
    }
    
    @Test
    @DisplayName("Test de la présence de différents types de cartes")
    public void testCartesChargees() {
        List<Carte> cartesPiochees = new ArrayList<>();
        
        // Piocher toutes les cartes pour les analyser
        while (!controlPioche.estVide()) {
            cartesPiochees.add(controlPioche.piocher());
        }
        
        // Vérifier qu'il y a au moins plusieurs cartes
        assertTrue(cartesPiochees.size() >= 5, "La pioche devrait contenir au moins 5 cartes");
        
        // Vérifier que les cartes ont des données valides
        for (Carte carte : cartesPiochees) {
            assertNotNull(carte.getNomCarte(), "Le nom de la carte ne devrait pas être null");
            assertFalse(carte.getNomCarte().isEmpty(), "Le nom de la carte ne devrait pas être vide");
            assertNotNull(carte.getDescription(), "La description ne devrait pas être null");
            assertNotNull(carte.getType(), "Le type de carte ne devrait pas être null");
        }
    }
}