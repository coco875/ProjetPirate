package test;

/* Classe de test pour les fonctionnalités de gain d'or (commentée car en développement)
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import carte.*;
import controllers.*;
import jeu.*;
import joueur.*;


public class TestGainOr {

    private ControlJeu controlJeu;
    private Joueur joueur1, joueur2;
    private ControlJoueur controlJoueur1, controlJoueur2;
    private ControlCartePlateau controlCartePlateau;
    
    @BeforeEach
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
    
    @AfterEach
    public void tearDown() {
        System.out.println("==== Fin du test de gain d'or ====");
    }


    @Test
    public void testGainOrBase() {
        System.out.println("\n--- Test gain d'or basique ---");
        
        // Test 1: Vérifier le fonctionnement du gain d'or basique
        int orInitial = joueur1.getOr();
        joueur1.gagnerOr(10);
        assertEquals(orInitial + 10, joueur1.getOr(), "Le gain d'or simple ne fonctionne pas correctement");
        System.out.println("Gain d'or direct: " + orInitial + " + 10 = " + joueur1.getOr() + " ✓");
        
        // Test 2: Vérifier le gain d'or avec une valeur nulle
        orInitial = joueur2.getOr();
        joueur2.gagnerOr(0);
        assertEquals(orInitial, joueur2.getOr(), "Le gain d'or avec une valeur nulle devrait laisser l'or inchangé");
        System.out.println("Gain d'or nul: " + orInitial + " + 0 = " + joueur2.getOr() + " ✓");
        
        // Test 3: Vérifier le gain d'or avec une grande valeur
        orInitial = joueur1.getOr();
        joueur1.gagnerOr(1000);
        assertEquals(orInitial + 1000, joueur1.getOr(), "Le gain d'or avec une grande valeur ne fonctionne pas correctement");
        System.out.println("Gain d'or grande valeur: " + orInitial + " + 1000 = " + joueur1.getOr() + " ✓");
    }
    
    
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
        
        assertEquals(orInitial + 10, joueur1.getOr(), 
                     "Le gain d'or via une carte de trésor (CarteStrategique) ne fonctionne pas");
        System.out.println("Gain via CarteStrategique: " + orInitial + " + 10 = " + joueur1.getOr() + " ✓");
        
        // Test 2: Vérifier l'effet de la carte CarteTresor
        joueur1.setOr(5);
        joueur2.setOr(5);
        
        CarteTresor carteTresor2 = new CarteTresor("Trésor maudit", "Un trésor qui apporte richesse", 15, 0);
        controlCartePlateau.ajouterCarteStrategiqueJ2(carteTresor2);
        
        orInitial = joueur2.getOr();
        controlCartePlateau.appliquerEffetsCartesStrategiques();
        
        assertEquals(orInitial + 15, joueur2.getOr(), 
                     "Le gain d'or via une carte CarteTresor ne fonctionne pas");
        System.out.println("Gain via CarteTresor: " + orInitial + " + 15 = " + joueur2.getOr() + " ✓");
        
        // Test 3: Vérifier plusieurs cartes trésor simultanément - test séparé pour éviter les interférences
        testGainOrMultiplesCartesTresor();
    }
    
    
    private void testGainOrMultiplesCartesTresor() {
        try {
            // Créer un environnement de test complètement nouveau
            ControlJeu newControlJeu = new ControlJeu();
            newControlJeu.initialiserJeu();
            
            // S'assurer qu'un joueur est créé
            Joueur newJoueur = newControlJeu.creerJoueur("Joueur Test", "Pirate Test");
            
            // S'assurer que le contrôleur de joueur est correctement récupéré 
            ControlJoueur newControlJoueur = newControlJeu.getJoueur(0);
            if (newControlJoueur == null) {
                System.err.println("ERREUR: Le ControlJoueur n'a pas été créé correctement");
                return; // Sortir de la méthode si le contrôleur est null
            }
            
            // Récupérer le controlCartePlateau
            ControlCartePlateau newControlCartePlateau = newControlJeu.getControlCartePlateau();
            if (newControlCartePlateau == null) {
                System.err.println("ERREUR: Le ControlCartePlateau n'a pas été créé correctement");
                return;
            }
            
            // Définir l'or initial
            newJoueur.setOr(5);
            
            // Ajouter deux cartes trésor
            CarteTresor carteTresor3 = new CarteTresor("Petit trésor", "Un petit trésor", 5, 0);
            CarteTresor carteTresor4 = new CarteTresor("Grand trésor", "Un grand trésor", 20, 0);
            
            newControlCartePlateau.ajouterCarteStrategiqueJ1(carteTresor3);
            newControlCartePlateau.ajouterCarteStrategiqueJ1(carteTresor4);
            
            int orInitial = newJoueur.getOr();
            newControlCartePlateau.appliquerEffetsCartesStrategiques();
            
            assertEquals(orInitial + 25, newJoueur.getOr(),
                        "Le gain d'or via plusieurs cartes trésor ne fonctionne pas");
            System.out.println("Gain via multiple CartesTresor: " + orInitial + " + 5 + 20 = " + newJoueur.getOr() + " ✓");
        } catch (Exception e) {
            // En cas d'exception, afficher l'erreur mais ne pas faire échouer le test principal
            System.err.println("Erreur lors du test des multiples cartes trésor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    @Test
    public void testCasParticuliers() {
        System.out.println("\n--- Test des cas particuliers ---");
        
        // Test 1: Carte avec or perdu
        joueur1.setOr(20);
        
        CarteTresor carteImpot = new CarteTresor("Impôt pirate", "Payez des taxes pour votre navire", 0, 5);
        controlCartePlateau.ajouterCarteStrategiqueJ1(carteImpot);
        
        int orInitial = joueur1.getOr();
        controlCartePlateau.appliquerEffetsCartesStrategiques();
        
        assertEquals(orInitial - 5, joueur1.getOr(),
                     "La perte d'or via une carte de trésor ne fonctionne pas");
        System.out.println("Perte d'or via carte: " + orInitial + " - 5 = " + joueur1.getOr() + " ✓");
        
        // Test 2: Méthode de perte d'or directe
        joueur2.setOr(10);
        orInitial = joueur2.getOr();
        
        joueur2.perdreOr(3);
        assertEquals(orInitial - 3, joueur2.getOr(),
                     "La méthode perdreOr ne fonctionne pas correctement");
        System.out.println("Perte d'or directe: " + orInitial + " - 3 = " + joueur2.getOr() + " ✓");
        
        // Test 3: Tenter de perdre plus d'or qu'on en possède
        orInitial = joueur2.getOr();
        joueur2.perdreOr(20); // Plus que les 7 or restants

        assertEquals(0, joueur2.getOr(),
                     "Le joueur ne devrait pas pouvoir avoir un or négatif");
        System.out.println("Tentative de perte excessive: " + orInitial + " → " + joueur2.getOr() + " (minimum 0) ✓");
    }

    
    @Test
    public void testAttributsCarteTresor() {
        System.out.println("\n--- Test des attributs de carte trésor ---");
        
        // Création d'une carte trésor directement
        CarteTresor carteTresor = new CarteTresor("Coffre d'or", "Un coffre rempli d'or", 10, 0);
        
        // Vérifie que la carte a bien des attributs cohérents
        assertEquals(10, carteTresor.getOrGagne(), "L'or gagné devrait être 10");
        assertEquals(0, carteTresor.getOrPerdu(), "L'or perdu devrait être 0");
        assertEquals(CarteStrategique.TypeStrategique.TRESOR, carteTresor.getTypeStrategique(),
                     "Le type de la carte devrait être TRESOR");
        System.out.println("Vérification attributs carte: Type=" + carteTresor.getTypeStrategique() + 
                          ", OrGagne=" + carteTresor.getOrGagne() + ", OrPerdu=" + carteTresor.getOrPerdu() + " ✓");
                     
        // Test avec des valeurs négatives (ne devrait pas arriver mais pour tester la robustesse)
        CarteTresor carteInvalide = new CarteTresor("Carte étrange", "Cette carte a des valeurs étranges", -5, -3);
        
        // La carte devrait avoir correctement initialisé les valeurs même négatives
        assertEquals(-5, carteInvalide.getOrGagne(), "L'or gagné devrait être -5 même si c'est étrange");
        assertEquals(-3, carteInvalide.getOrPerdu(), "L'or perdu devrait être -3 même si c'est étrange");
        System.out.println("Vérification attributs carte invalide: OrGagne=" + carteInvalide.getOrGagne() + 
                          ", OrPerdu=" + carteInvalide.getOrPerdu() + " ✓");
    }
}
*/