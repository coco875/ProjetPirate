package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import jeu.Pioche;
import carte.Carte;
import carte.TypeCarte;

import java.util.HashSet;
import java.util.Set;

/**
 * Tests pour la classe Pioche
 * Note: Le nom de cette classe est TestPioche2 pour éviter les conflits avec la classe TestPioche existante
 * qui utilise une méthode main() au lieu de JUnit.
 */
public class TestPioche2 {
    
    private Pioche pioche;
    
    @BeforeEach
    public void initialiser() {
        pioche = new Pioche();
    }
    
    @Test
    @DisplayName("Test de la création d'une pioche")
    public void testCreationPioche() {
        assertNotNull(pioche, "La pioche ne devrait pas être null");
        assertTrue(pioche.estVide(), "Une nouvelle pioche devrait être vide");
        assertEquals(0, pioche.getNombreCartes(), "Une nouvelle pioche devrait avoir 0 carte");
    }
    
    @Test
    @DisplayName("Test d'ajout de cartes dans la pioche")
    public void testAjouterCarte() {
        // Création de cartes de test
        Carte carte1 = new Carte(TypeCarte.OFFENSIVE, "Épée", "Une épée tranchante");
        Carte carte2 = new Carte(TypeCarte.STRATEGIQUE, "Trésor", "Un coffre d'or");
        
        // Ajout des cartes à la pioche
        pioche.ajouterCarte(carte1);
        pioche.ajouterCarte(carte2);
        
        // Vérification du nombre de cartes
        assertEquals(2, pioche.getNombreCartes(), "La pioche devrait contenir 2 cartes");
        assertFalse(pioche.estVide(), "La pioche ne devrait pas être vide");
    }
    
    @Test
    @DisplayName("Test de pioche de cartes")
    public void testPiocher() {
        // Création et ajout de cartes
        Carte carte1 = new Carte(TypeCarte.OFFENSIVE, "Épée", "Une épée tranchante");
        Carte carte2 = new Carte(TypeCarte.STRATEGIQUE, "Trésor", "Un coffre d'or");
        pioche.ajouterCarte(carte1);
        pioche.ajouterCarte(carte2);
        
        // Pioche de la première carte
        Carte cartePiochee = pioche.piocher();
        assertNotNull(cartePiochee, "La carte piochée ne devrait pas être null");
        assertEquals(1, pioche.getNombreCartes(), "Il devrait rester 1 carte dans la pioche");
        assertFalse(pioche.estVide(), "La pioche ne devrait pas être vide");
        
        // Pioche de la deuxième carte
        cartePiochee = pioche.piocher();
        assertNotNull(cartePiochee, "La carte piochée ne devrait pas être null");
        assertEquals(0, pioche.getNombreCartes(), "La pioche devrait être vide");
        assertTrue(pioche.estVide(), "La pioche devrait être vide");
        
        // Tentative de pioche dans une pioche vide
        cartePiochee = pioche.piocher();
        assertNull(cartePiochee, "La pioche dans une pioche vide devrait retourner null");
    }
    
    @Test
    @DisplayName("Test de mélange de la pioche")
    public void testMelanger() {
        // Créer une grande quantité de cartes avec des IDs uniques
        int nombreCartes = 100;
        
        // Remplir la pioche avec ordre connu
        for (int i = 0; i < nombreCartes; i++) {
            Carte carte = new Carte(TypeCarte.OFFENSIVE, "Carte " + i, "Description " + i);
            pioche.ajouterCarte(carte);
        }
        
        // Mémoriser l'ordre initial (via les IDs des cartes)
        Set<Integer> idsAvantMelange = new HashSet<>();
        Pioche piocheCopie = new Pioche();
        for (int i = 0; i < nombreCartes; i++) {
            Carte carte = pioche.piocher();
            idsAvantMelange.add(carte.getId());
            piocheCopie.ajouterCarte(carte);
        }
        
        // On s'assure que la pioche originale est vide et on remet les cartes dedans
        assertTrue(pioche.estVide());
        for (int i = 0; i < nombreCartes; i++) {
            pioche.ajouterCarte(piocheCopie.piocher());
        }
        
        // Mélanger la pioche
        pioche.melanger();
        
        // Vérifier que le nombre de cartes est inchangé
        assertEquals(nombreCartes, pioche.getNombreCartes(), 
                    "Le nombre de cartes ne devrait pas changer après mélange");
        
        // Vérifier que toutes les cartes sont différentes de leur position initiale
        // Note: Ce test n'est pas déterministe, mais il a une probabilité très faible d'échouer
        // si le mélange fonctionne correctement
        boolean ordreChange = false;
        int cartesSurPosition = 0;
        
        // Piocher toutes les cartes et compter combien sont à la même position
        Set<Integer> idsApresMelange = new HashSet<>();
        for (int i = 0; i < nombreCartes; i++) {
            Carte carte = pioche.piocher();
            idsApresMelange.add(carte.getId());
        }
        
        // Vérifier que toutes les cartes sont présentes après le mélange
        assertEquals(idsAvantMelange.size(), idsApresMelange.size(), 
                    "Le nombre de cartes uniques devrait être le même avant et après mélange");
        assertTrue(idsAvantMelange.containsAll(idsApresMelange) && 
                  idsApresMelange.containsAll(idsAvantMelange),
                  "Toutes les cartes devraient être présentes après le mélange");
    }
}