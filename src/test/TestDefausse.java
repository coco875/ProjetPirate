package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
/*
import jeu.Defausse;*/
import carte.Carte;
import carte.TypeCarte;

import java.util.List;

/**
 * Tests pour la classe Defausse
 *//*
public class TestDefausse {
    
    private Defausse defausse;
    
    @BeforeEach
    public void initialiser() {
        defausse = new Defausse();
    }
    
    @Test
    @DisplayName("Test de la création d'une défausse")
    public void testCreationDefausse() {
        assertNotNull(defausse, "La défausse ne devrait pas être null");
        assertNotNull(defausse.getCartes(), "La liste de cartes ne devrait pas être null");
        assertTrue(defausse.getCartes().isEmpty(), "La défausse devrait être vide à la création");
    }
    
    @Test
    @DisplayName("Test d'ajout de cartes dans la défausse")
    public void testAjouterCarte() {
        // Créer quelques cartes de test
        Carte carte1 = new Carte(TypeCarte.OFFENSIVE, "Carte 1", "Description 1");
        Carte carte2 = new Carte(TypeCarte.STRATEGIQUE, "Carte 2", "Description 2");
        
        // Ajouter les cartes à la défausse
        defausse.ajouterCarte(carte1);
        defausse.ajouterCarte(carte2);
        
        // Vérifier que les cartes ont bien été ajoutées
        List<Carte> cartes = defausse.getCartes();
        assertEquals(2, cartes.size(), "La défausse devrait contenir 2 cartes");
        assertTrue(cartes.contains(carte1), "La défausse devrait contenir la carte 1");
        assertTrue(cartes.contains(carte2), "La défausse devrait contenir la carte 2");
    }
    
    @Test
    @DisplayName("Test d'ajout d'une carte null")
    public void testAjouterCarteNull() {
        // Ajouter une carte null
        defausse.ajouterCarte(null);
        
        // Vérifier que la carte n'a pas été ajoutée
        assertTrue(defausse.getCartes().isEmpty(), "La défausse devrait être vide");
    }
    
    @Test
    @DisplayName("Test de vidage de la défausse")
    public void testVider() {
        // Ajouter des cartes à la défausse
        Carte carte1 = new Carte(TypeCarte.OFFENSIVE, "Carte 1", "Description 1");
        Carte carte2 = new Carte(TypeCarte.STRATEGIQUE, "Carte 2", "Description 2");
        defausse.ajouterCarte(carte1);
        defausse.ajouterCarte(carte2);
        
        // Vérifier que la défausse n'est pas vide
        assertFalse(defausse.getCartes().isEmpty(), "La défausse ne devrait pas être vide");
        
        // Vider la défausse
        defausse.vider();
        
        // Vérifier que la défausse est vide
        assertTrue(defausse.getCartes().isEmpty(), "La défausse devrait être vide après vidage");
    }
}
*/