package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import jeu.ZoneStrategique;
import carte.CarteStrategique;
import carte.CarteStrategique.TypeStrategique;

import java.util.List;

/**
 * Tests pour la classe ZoneStrategique
 *//*
public class TestZoneStrategique {
    
    private ZoneStrategique zoneStrategique;
    
    @BeforeEach
    public void initialiser() {
        zoneStrategique = new ZoneStrategique();
    }
    
    @Test
    @DisplayName("Test de la création d'une zone stratégique")
    public void testCreationZoneStrategique() {
        assertNotNull(zoneStrategique, "La zone stratégique ne devrait pas être null");
        assertNotNull(zoneStrategique.getCartesStrategiques(), "La liste de cartes ne devrait pas être null");
        assertTrue(zoneStrategique.getCartesStrategiques().isEmpty(), "La zone stratégique devrait être vide à la création");
    }
    
    @Test
    @DisplayName("Test d'ajout de cartes dans la zone stratégique")
    public void testAjouterCarte() {
        // Créer quelques cartes de test - Mise à jour du constructeur pour enlever orPerdu
        CarteStrategique carte1 = new CarteStrategique("Coffre", "Un coffre au trésor", 10, true);
        CarteStrategique carte2 = new CarteStrategique("Chanson", "Une chanson pirate", 3, 1);
        
        // Ajouter les cartes à la zone
        zoneStrategique.ajouterCarte(carte1);
        zoneStrategique.ajouterCarte(carte2);
        
        // Vérifier que les cartes ont bien été ajoutées
        List<CarteStrategique> cartes = zoneStrategique.getCartesStrategiques();
        assertEquals(2, cartes.size(), "La zone stratégique devrait contenir 2 cartes");
        assertTrue(cartes.contains(carte1), "La zone stratégique devrait contenir la carte 1");
        assertTrue(cartes.contains(carte2), "La zone stratégique devrait contenir la carte 2");
    }
    
    @Test
    @DisplayName("Test de vidage de la zone stratégique")
    public void testViderZone() {
        // Ajouter des cartes à la zone - Mise à jour du constructeur pour enlever orPerdu
        CarteStrategique carte1 = new CarteStrategique("Coffre", "Un coffre au trésor", 10, true);
        CarteStrategique carte2 = new CarteStrategique("Chanson", "Une chanson pirate", 3, 1);
        zoneStrategique.ajouterCarte(carte1);
        zoneStrategique.ajouterCarte(carte2);
        
        // Vérifier que la zone n'est pas vide
        assertFalse(zoneStrategique.getCartesStrategiques().isEmpty(), "La zone stratégique ne devrait pas être vide");
        
        // Vider la zone
        zoneStrategique.viderZone();
        
        // Vérifier que la zone est vide
        assertTrue(zoneStrategique.getCartesStrategiques().isEmpty(), "La zone stratégique devrait être vide après vidage");
    }
    
    @Test
    @DisplayName("Test avec différents types de cartes stratégiques")
    public void testDifferentsTypesCartes() {
        // Créer des cartes de différents types stratégiques - Mise à jour du constructeur de trésor
        CarteStrategique carteTresor = new CarteStrategique("Coffre", "Un coffre au trésor", 10, true);
        CarteStrategique cartePopularite = new CarteStrategique("Chanson", "Une chanson pirate", 3, 1);
        CarteStrategique cartePassive = new CarteStrategique("Bouclier", "Protection contre attaques", 2, 3, "Protection");
        CarteStrategique carteSpeciale = new CarteStrategique("Malédiction", "Maudit l'adversaire", "Réduction dégâts", 3);
        
        // Ajouter les cartes à la zone
        zoneStrategique.ajouterCarte(carteTresor);
        zoneStrategique.ajouterCarte(cartePopularite);
        zoneStrategique.ajouterCarte(cartePassive);
        zoneStrategique.ajouterCarte(carteSpeciale);
        
        // Vérifier que toutes les cartes ont été ajoutées
        List<CarteStrategique> cartes = zoneStrategique.getCartesStrategiques();
        assertEquals(4, cartes.size(), "La zone stratégique devrait contenir 4 cartes");
        
        // Vérifier que les types de cartes sont corrects
        assertEquals(TypeStrategique.TRESOR, carteTresor.getTypeStrategique());
        assertEquals(TypeStrategique.POPULARITE, cartePopularite.getTypeStrategique());
        assertEquals(TypeStrategique.PASSIVE, cartePassive.getTypeStrategique());
        assertEquals(TypeStrategique.SPECIALE, carteSpeciale.getTypeStrategique());
    }
}
*/