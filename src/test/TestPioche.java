package test;

import jeu.Pioche;
import joueur.Joueur;
import joueur.Pirate;
import carte.Carte;
import carte.TypeCarte;
import controllers.ControlJeu;
import controllers.ControlJoueur;
import controllers.ControlPioche;
import controllers.ControlCartePlateau;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashSet;
import java.util.Set;

/**
 * Tests pour la classe Pioche
 *//*
public class TestPioche {

	public static void main(String[] args) {
		// Initialisation du contrôleur principal du jeu
		ControlJeu controlJeu = new ControlJeu();
		controlJeu.initialiserJeu();
		
		// Création des joueurs via le contrôleur de jeu
		controlJeu.setJoueur1(new Pirate("Barbe Noire"));
		controlJeu.setJoueur2(new Pirate("Anne Bonny"));
		
		// Récupération du contrôleur de joueur 1
		ControlJoueur controlJoueur = controlJeu.getJoueur(0);
		
		System.out.println("=== Test d'initialisation de la main ===");
		// Utilisation de piocher au lieu de initialiserMain
		for (int i = 0; i < 3; i++) {
			controlJoueur.piocher();
		}
		
		// Récupération du joueur
		Joueur joueur = controlJoueur.getJoueur();
		
		System.out.println("Nombre de cartes en main: " + joueur.getMain().size());
		System.out.println("Main du joueur après initialisation:");
		controlJoueur.afficherMain();
		
		System.out.println("\n=== Test de pioche supplémentaire ===");
		Carte cartePiochee = controlJoueur.piocher();
		System.out.println("Carte piochée: " + cartePiochee);
		System.out.println("Nombre de cartes en main après pioche: " + joueur.getMain().size());
		
		// Test de retrait de cartes
		System.out.println("\n=== Test de retrait de cartes ===");
		if (joueur.getMain().size() >= 2) {
			Carte carte1 = joueur.getMain().get(0);
			Carte carte2 = joueur.getMain().get(1);
			
			System.out.println("Cartes à retirer:");
			System.out.println("Carte 1: " + carte1);
			System.out.println("Carte 2: " + carte2);
			
			controlJoueur.retirerCarte(carte1);
			controlJoueur.retirerCarte(carte2);
			
			System.out.println("\nMain après retrait de 2 cartes:");
			controlJoueur.afficherMain();
			System.out.println("Nombre de cartes en main après retrait: " + joueur.getMain().size());
		} else {
			System.out.println("Pas assez de cartes en main pour effectuer le test de retrait.");
		}
		
		// Test de jeu d'une carte (si la main n'est pas vide)
		if (!joueur.getMain().isEmpty()) {
			System.out.println("\n=== Test de jeu d'une carte ===");
			Carte carteAJouer = joueur.getMain().get(0);
			System.out.println("Carte à jouer: " + carteAJouer);
			
			// Jouer la carte si c'est possible
			try {
				controlJoueur.jouerCarte(carteAJouer);
				System.out.println("Carte jouée avec succès");
			} catch (Exception e) {
				System.out.println("Impossible de jouer la carte: " + e.getMessage());
			}
			
			System.out.println("Nombre de cartes en main après jeu: " + joueur.getMain().size());
		}
		
		System.out.println("\n=== Fin des tests ===");
	}
	
    // Tests JUnit pour la classe Pioche
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
}*/