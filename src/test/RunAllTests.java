package test;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.launcher.TestPlan;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

/**
 * Classe principale exécutant l'ensemble des tests unitaires du projet
 */
public class RunAllTests {
    public static void main(String[] args) throws Exception {
        System.out.println("Lancement de tous les tests JUnit...");

        // Créer un lanceur JUnit
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectPackage("test"))
                .build();

        Launcher launcher = LauncherFactory.create();
        TestPlan testPlan = launcher.discover(request);
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        // Obtenir et afficher le résumé d'exécution
        TestExecutionSummary summary = listener.getSummary();
        System.out.println("\nRésultats des tests :");
        System.out.println("Tests exécutés : " + summary.getTestsFoundCount());
        System.out.println("Tests réussis : " + summary.getTestsSucceededCount());
        System.out.println("Tests échoués : " + summary.getTestsFailedCount());
        
        // Exécuter les anciens tests (s'ils ont une méthode main)
        try {
            System.out.println("\nLancement des tests avec méthode main :");
            
            System.out.println("\nTestPioche:");
            TestPioche.main(args);
            
            System.out.println("\nTestControlMarche:");
            TestControlMarche.main(args);
        } catch (Exception e) {
            System.err.println("Erreur lors de l'exécution des tests avec méthode main : " + e.getMessage());
        }

        System.out.println("\nTous les tests ont été exécutés.");
    }
}