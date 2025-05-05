package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import carte.*;
import controllers.*;
import jeu.*;
import joueur.*;

/**
 * Classe de test pour vérifier le fonctionnement du gain d'or dans le jeu des Pirates.
 * Ces tests vérifient différentes façons de gagner de l'or :
 * - Gain direct via la méthode gagnerOr()
 * - Gain via une carte de trésor (CarteStrategique)
 * - Gain via une carte spécialisée (CarteTresor)
 * - Cas limites (valeurs négatives, nulles, grandes valeurs)
 */
public class TestGainOr {

    private ControlJeu controlJeu;
    private Joueur joueur1, joueur2;
    private ControlJoueur controlJoueur1, controlJoueur2;
    private ControlCartePlateau controlCartePlateau;
    
    /**
     * Initialisation des objets nécessaires avant chaque test
     */
    @Before
    public void setUp() {
        System.out.println("\n==== Début du test de gain d'or ====");
        controlJeu = new ControlJeu();
        controlJeu.initialiserJeu();
        
        joueur1 = controlJeu.creerJoueur("Capitaine Trésor", "Pirate Or");
        joueur2 = controlJeu.creerJoueur("Adversaire", "Pirate Rival");
        
        controlJoueur1 = controlJeu.getJoueur(0);
        controlJoueur2 = controlJeu.getJoueur(1);
        controlCartePlateau = controlJeu.getControlCartePlateau();
        
        // Initialiser les valeurs d'or des joueurs
        joueur1.setOr(5);
        joueur2.setOr(5);
        
        System.out.println("Joueurs créés avec 5 or chacun");
    }
    
    /**
     * Nettoyage après les tests
     */
    @After
    public void tearDown() {
        System.out.println("==== Fin du test de gain d'or ====");
    }

    /**
     * Test de la méthode gagnerOr() de la classe Joueur
     */
    @Test
    public void testGainOrBase() {
        System.out.println("\n--- Test gain d'or basique ---");
        
        // Test 1: Vérifier le fonctionnement du gain d'or basique
        int orInitial = joueur1.getOr();
        joueur1.gagnerOr(10);
        assertEquals("Le gain d'or simple ne fonctionne pas correctement", (long)(orInitial + 10), (long)joueur1.getOr());
        System.out.println("Gain d'or direct: " + orInitial + " + 10 = " + joueur1.getOr() + " ✓");
        
        // Test 2: Vérifier le gain d'or avec une valeur nulle
        orInitial = joueur2.getOr();
        joueur2.gagnerOr(0);
        assertEquals("Le gain d'or avec une valeur nulle devrait laisser l'or inchangé", (long)orInitial, (long)joueur2.getOr());
        System.out.println("Gain d'or nul: " + orInitial + " + 0 = " + joueur2.getOr() + " ✓");
        
        // Test 3: Vérifier le gain d'or avec une grande valeur
        orInitial = joueur1.getOr();
        joueur1.gagnerOr(1000);
        assertEquals("Le gain d'or avec une grande valeur ne fonctionne pas correctement", (long)(orInitial + 1000), (long)joueur1.getOr());
        System.out.println("Gain d'or grande valeur: " + orInitial + " + 1000 = " + joueur1.getOr() + " ✓");
    }
    
    /**
     * Test du gain d'or via les cartes de trésor normales et spécialisées
     */
    @Test
    public void testGainOrCarteTresor() {
        System.out.println("\n--- Test gain d'or via cartes trésor ---");
        
        // Réinitialiser les valeurs d'or
        joueur1.setOr(5);
        joueur2.setOr(5);
        
        // Test 1: Vérifier la carte de trésor (CarteStrategique)
        CarteStrategique carteTresor = new CarteStrategique("Coffre d'or", "Un coffre rempli d'or", 10, 0, true);
        controlCartePlateau.ajouterCarteStrategiqueJ1(carteTresor);
        
        int orInitial = joueur1.getOr();
        controlCartePlateau.appliquerEffetsCartesStrategiques();
        
        assertEquals("Le gain d'or via une carte de trésor (CarteStrategique) ne fonctionne pas", 
                     (long)(orInitial + 10), (long)joueur1.getOr());
        System.out.println("Gain via CarteStrategique: " + orInitial + " + 10 = " + joueur1.getOr() + " ✓");
        
        // Test 2: Vérifier l'effet de la carte CarteTresor
        joueur1.setOr(5);
        joueur2.setOr(5);
        
        CarteTresor carteTresor2 = new CarteTresor("Trésor maudit", "Un trésor qui apporte richesse", 15, 0, 0);
        controlCartePlateau.ajouterCarteStrategiqueJ2(carteTresor2);
        
        orInitial = joueur2.getOr();
        controlCartePlateau.appliquerEffetsCartesStrategiques();
        
        assertEquals("Le gain d'or via une carte CarteTresor ne fonctionne pas", 
                     (long)(orInitial + 15), (long)joueur2.getOr());
        System.out.println("Gain via CarteTresor: " + orInitial + " + 15 = " + joueur2.getOr() + " ✓");
        
        // Test 3: Vérifier plusieurs cartes trésor simultanément
        joueur1.setOr(5);
        controlCartePlateau = controlJeu.getControlCartePlateau(); // Reset le plateau
        
        CarteTresor carteTresor3 = new CarteTresor("Petit trésor", "Un petit trésor", 5, 0, 0);
        CarteTresor carteTresor4 = new CarteTresor("Grand trésor", "Un grand trésor", 20, 0, 0);
        controlCartePlateau.ajouterCarteStrategiqueJ1(carteTresor3);
        controlCartePlateau.ajouterCarteStrategiqueJ1(carteTresor4);
        
        orInitial = joueur1.getOr();
        controlCartePlateau.appliquerEffetsCartesStrategiques();
        
        assertEquals("Le gain d'or via plusieurs cartes trésor ne fonctionne pas", 
                     (long)(orInitial + 25), (long)joueur1.getOr());
        System.out.println("Gain via multiple CartesTresor: " + orInitial + " + 5 + 20 = " + joueur1.getOr() + " ✓");
    }

    /**
     * Test des cas particuliers (perte d'or, carte avec or perdu)
     */
    @Test
    public void testCasParticuliers() {
        System.out.println("\n--- Test des cas particuliers ---");
        
        // Test 1: Carte avec or perdu
        joueur1.setOr(20);
        
        CarteTresor carteImpot = new CarteTresor("Impôt pirate", "Payez des taxes pour votre navire", 0, 5, 0);
        controlCartePlateau.ajouterCarteStrategiqueJ1(carteImpot);
        
        int orInitial = joueur1.getOr();
        controlCartePlateau.appliquerEffetsCartesStrategiques();
        
        assertEquals("La perte d'or via une carte de trésor ne fonctionne pas", 
                     (long)(orInitial - 5), (long)joueur1.getOr());
        System.out.println("Perte d'or via carte: " + orInitial + " - 5 = " + joueur1.getOr() + " ✓");
        
        // Test 2: Méthode de perte d'or directe
        joueur2.setOr(10);
        orInitial = joueur2.getOr();
        
        joueur2.perdreOr(3);
        assertEquals("La méthode perdreOr ne fonctionne pas correctement",
                     (long)(orInitial - 3), (long)joueur2.getOr());
        System.out.println("Perte d'or directe: " + orInitial + " - 3 = " + joueur2.getOr() + " ✓");
        
        // Test 3: Tenter de perdre plus d'or qu'on en possède
        orInitial = joueur2.getOr();
        joueur2.perdreOr(20); // Plus que les 7 or restants

        assertEquals("Le joueur ne devrait pas pouvoir avoir un or négatif",
                     (long)0, (long)joueur2.getOr());
        System.out.println("Tentative de perte excessive: " + orInitial + " → " + joueur2.getOr() + " (minimum 0) ✓");
    }

    /**
     * Test des attributs de la carte trésor
     */
    @Test
    public void testAttributsCarteTresor() {
        System.out.println("\n--- Test des attributs de carte trésor ---");
        
        // Création d'une carte trésor directement
        CarteTresor carteTresor = new CarteTresor("Coffre d'or", "Un coffre rempli d'or", 10, 0, 0);
        
        // Vérifie que la carte a bien des attributs cohérents
        assertEquals("L'or gagné devrait être 10", (int)10, (int)carteTresor.getOrGagne());
        assertEquals("L'or perdu devrait être 0", (int)0, (int)carteTresor.getOrPerdu());
        assertEquals("Le type de la carte devrait être TRESOR", 
                     CarteStrategique.TypeStrategique.TRESOR, carteTresor.getTypeStrategique());
        System.out.println("Vérification attributs carte: Type=" + carteTresor.getTypeStrategique() + 
                          ", OrGagne=" + carteTresor.getOrGagne() + ", OrPerdu=" + carteTresor.getOrPerdu() + " ✓");
                     
        // Test avec des valeurs négatives (ne devrait pas arriver mais pour tester la robustesse)
        CarteTresor carteInvalide = new CarteTresor("Carte étrange", "Cette carte a des valeurs étranges", -5, -3, 0);
        
        // La carte devrait avoir correctement initialisé les valeurs même négatives
        assertEquals("L'or gagné devrait être -5 même si c'est étrange", (int)-5, (int)carteInvalide.getOrGagne());
        assertEquals("L'or perdu devrait être -3 même si c'est étrange", (int)-3, (int)carteInvalide.getOrPerdu());
        System.out.println("Vérification attributs carte invalide: OrGagne=" + carteInvalide.getOrGagne() + 
                          ", OrPerdu=" + carteInvalide.getOrPerdu() + " ✓");
    }
}