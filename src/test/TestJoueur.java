package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import joueur.Joueur;
import joueur.Pirate;
import carte.Carte;
import carte.CarteAttaque;
import carte.TypeCarte;

import java.util.List;


public class TestJoueur {
    
    private Joueur joueur;
    private Pirate pirate;
    
    @BeforeEach
    public void initialiser() {
        pirate = new Pirate("Jack Sparrow");
        joueur = new Joueur(pirate);
    }
    
    @Test
    @DisplayName("Test de la création d'un joueur")
    public void testCreationJoueur() {
        assertSame(pirate, joueur.getPirate());
        assertEquals(5, joueur.getPointsDeVie());
        assertEquals(0, joueur.getPopularite());
        assertEquals(3, joueur.getOr());
        assertEquals(0, joueur.getNbCartes());
        assertTrue(joueur.getMain().isEmpty());
    }
    
    @Test
    @DisplayName("Test de la gestion des points de vie")
    public void testGestionPointsDeVie() {
        // Test perte de points de vie
        joueur.perdrePointsDeVie(2);
        assertEquals(3, joueur.getPointsDeVie());
        
        // Test perte excessive de points de vie (ne devrait pas descendre en dessous de 0)
        joueur.perdrePointsDeVie(5);
        assertEquals(0, joueur.getPointsDeVie());
        
        // Test gain de points de vie
        joueur.gagnerPointsDeVie(3);
        assertEquals(3, joueur.getPointsDeVie());
        
        // Test gain excessif de points de vie (ne devrait pas dépasser 5)
        joueur.gagnerPointsDeVie(10);
        assertEquals(5, joueur.getPointsDeVie());
    }
    
    @Test
    @DisplayName("Test de la gestion de la popularité")
    public void testGestionPopularite() {
        // Test gain de popularité
        joueur.gagnerPopularite(2);
        assertEquals(2, joueur.getPopularite());
        
        // Test gain excessif de popularité (ne devrait pas dépasser 5)
        joueur.gagnerPopularite(10);
        assertEquals(5, joueur.getPopularite());
        
        // Test perte de popularité
        joueur.perdrePopularite(3);
        assertEquals(2, joueur.getPopularite());
        
        // Test perte excessive de popularité (ne devrait pas descendre en dessous de 0)
        joueur.perdrePopularite(5);
        assertEquals(0, joueur.getPopularite());
    }
    
    @Test
    @DisplayName("Test de la gestion de l'or")
    public void testGestionOr() {
        // Test gain d'or
        joueur.gagnerOr(5);
        assertEquals(8, joueur.getOr());
        
        // Test perte d'or
        boolean resultat = joueur.perdreOr(3);
        assertTrue(resultat);
        assertEquals(5, joueur.getOr());
        
        // Test perte excessive d'or
        resultat = joueur.perdreOr(10);
        assertFalse(resultat);
        assertEquals(0, joueur.getOr()); // L'or devrait être à 0 après une perte excessive
    }
    
    @Test
    @DisplayName("Test de la gestion des cartes en main")
    public void testGestionCartes() {
        // Créer des cartes d'attaque pour le test
        CarteAttaque carte1 = new CarteAttaque("Épée", "Une épée tranchante", 5, 3, 1);
        CarteAttaque carte2 = new CarteAttaque("Potion", "Une potion de soin", 3, 0, 0);
        
        // Ajouter des cartes à la main du joueur
        joueur.ajouterCarte(carte1);
        joueur.ajouterCarte(carte2);
        
        // Vérifier que les cartes ont bien été ajoutées
        List<Carte> main = joueur.getMain();
        assertEquals(2, main.size());
        assertEquals(2, joueur.getNbCartes());
        assertTrue(main.contains(carte1));
        assertTrue(main.contains(carte2));
        
        // Retirer une carte
        boolean resultat = joueur.retirerCarte(carte1);
        assertTrue(resultat);
        assertEquals(1, joueur.getNbCartes());
        assertFalse(main.contains(carte1));
        assertTrue(main.contains(carte2));
        
        // Tenter de retirer une carte inexistante
        resultat = joueur.retirerCarte(carte1);
        assertFalse(resultat);
        // La taille ne devrait pas changer car la carte n'est plus dans la main
        assertEquals(1, joueur.getNbCartes());
    }
    
    @Test
    @DisplayName("Test de l'accès au personnage")
    public void testAccesPersonnage() {
        assertSame(pirate, joueur.getPirate());
        assertSame(pirate, joueur.getPersonnage()); // Vérifier la méthode de compatibilité
    }
    
    @Test
    @DisplayName("Test des setters")
    public void testSetters() {
        joueur.setVie(3);
        assertEquals(3, joueur.getPointsDeVie());
        
        joueur.setPopularite(4);
        assertEquals(4, joueur.getPopularite());
        
        joueur.setOr(10);
        assertEquals(10, joueur.getOr());
        
        joueur.setNbCartes(5);
        assertEquals(5, joueur.getNbCartes());
    }
}