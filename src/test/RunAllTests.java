package test;

/**
 * Classe principale exécutant l'ensemble des tests unitaires du projet
 */
public class RunAllTests {
    public static void main(String[] args) throws Exception {
        System.out.println("Lancement de tous les tests...");

        // Test de la classe Carte
        System.out.println("\nTestCarte:");
        TestCarte.main(args);

        // Test de la classe Pioche
        System.out.println("\nTestPioche:");
        TestPioche.main(args);

        // Test de la classe Joueur
        System.out.println("\nTestJoueur:");
        // TestJoueur.main(args);

        // Test de la classe ControlJeu
        System.out.println("\nTestControlJeu:");
        // TestControlJeu.main(args);
        
        // Test de la classe ControlJoueur
        System.out.println("\nTestControlJoueur:");
        // TestControlJoueur.main(args);
        
        // Test de la classe ControlCartePlateau
        System.out.println("\nTestControlCartePlateau:");
        // TestControlCartePlateau.main(args);
        
        // Test de la classe ControlCarteSpeciale
        System.out.println("\nTestControlCarteSpeciale:");
        // TestControlCarteSpeciale.main(args);
        
        // Test de la classe ControlMarche
        System.out.println("\nTestControlMarche:");
        TestControlMarche.main(args);
        
        // Test de la classe ControlPioche
        System.out.println("\nTestControlPioche:");
        // TestControlPioche.main(args);
        
        // Test de la classe BoundaryJeu
        System.out.println("\nTestBoundaryJeu:");
        TestBoundaryJeu.main(args);

        System.out.println("\nTous les tests ont été exécutés.");
    }
}