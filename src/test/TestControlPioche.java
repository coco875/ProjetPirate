package test;

import controllers.ControlPioche;
import carte.*;
import static org.junit.Assert.*; // Importer pour les assertions

public class TestControlPioche {

    public static void main(String[] args) throws Exception {
        System.out.println("Test de la classe ControlPioche");
        
        // Création des objets nécessaires pour le test
        ControlPioche controlPioche = new ControlPioche(); // Initialise et charge les cartes
        
        System.out.println("\n--- Vérification des valeurs des cartes chargées ---");
        
        // Test spécifique pour quelques cartes clés
        testerCarte("src/carte/resource/attaque/attaque1.txt", "Canon Dévastateur", 2, 1, 0, 0, 0); // Attaque
        testerCarte("src/carte/resource/popularite/popularite1.txt", "Chansons de Marin", 0, 0, 2, 0, 0); // Popularité
        testerCarte("src/carte/resource/soin/soin1.txt", "Potion du Chirurgien", 0, 0, 0, 3, 0); // Soin
        testerCarte("src/carte/resource/tresor/tresor1.txt", "Coffre au Trésor", 0, 0, 0, 0, 10); // Trésor (gain or)
        testerCarte("src/carte/resource/tresor/tresor3.txt", "Vol de Butin", 0, 0, 0, 0, 8); // Trésor (vol or -> maintenant attaque)

        System.out.println("\n--- Test de pioche standard ---");
        // Test de la méthode piocher (affiche les détails)
        System.out.println("Test de pioche de carte :");
        afficherDetailsCarte(controlPioche.piocher());
        
        // Test de pioche multiple (affiche les détails)
        System.out.println("\nTest de pioches multiples :");
        for (int i = 0; i < 5; i++) { // Piocher plus de cartes pour voir la variété
            System.out.print("Carte " + (i+1) + ": ");
            afficherDetailsCarte(controlPioche.piocher());
        }
        
        System.out.println("\nTests terminés.");
    }

    // Méthode pour tester une carte spécifique chargée par le parseur
    private static void testerCarte(String filePath, String expectedTitre, int expectedDegatsInfliges, int expectedDegatsSubis, int expectedPopularite, int expectedVie, int expectedOr) throws Exception {
        System.out.println("Test de chargement: " + filePath);
        Carte carte = ParserCarte.lireCarte(filePath);
        assertNotNull("La carte ne devrait pas être null: " + filePath, carte);
        assertEquals("Le titre ne correspond pas pour " + filePath, expectedTitre, carte.getNomCarte());

        if (carte instanceof CarteOffensive) {
            CarteOffensive co = (CarteOffensive) carte;
            if (co.getTypeOffensif() == CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE) {
                assertEquals("Dégâts infligés incorrects pour " + filePath, expectedDegatsInfliges, co.getDegatsInfliges());
                assertEquals("Dégâts subis incorrects pour " + filePath, expectedDegatsSubis, co.getDegatsSubis());
            } else if (co.getTypeOffensif() == CarteOffensive.TypeOffensif.SOIN) {
                assertEquals("Vie gagnée incorrecte pour " + filePath, expectedVie, co.getVieGagnee());
            } else if (co.getTypeOffensif() == CarteOffensive.TypeOffensif.TRESOR_OFFENSIF) { // Correction: VOL_OR -> TRESOR_OFFENSIF
                 assertEquals("Or volé incorrect pour " + filePath, expectedOr, co.getOrVole());
            }
        } else if (carte instanceof CarteStrategique) {
            CarteStrategique cs = (CarteStrategique) carte;
             if (cs.getTypeStrategique() == CarteStrategique.TypeStrategique.POPULARITE) {
                assertEquals("Popularité gagnée incorrecte pour " + filePath, expectedPopularite, cs.getPopulariteGagnee());
                assertEquals("Dégâts subis (pop) incorrects pour " + filePath, expectedDegatsSubis, cs.getDegatsSubis());
            } else if (cs.getTypeStrategique() == CarteStrategique.TypeStrategique.TRESOR) {
                 assertEquals("Or gagné incorrect pour " + filePath, expectedOr, cs.getOrGagne());
                 // assertEquals("Or perdu incorrect pour " + filePath, 0, cs.getOrPerdu()); // Le parseur met 0 pour l'instant
            }
            // Ajouter des vérifications pour SPECIALE et PASSIVE si nécessaire
        }
        System.out.println(" -> OK");
    }

    // Méthode pour afficher les détails d'une carte
    private static void afficherDetailsCarte(Carte carte) {
        if (carte != null) {
            System.out.print(carte.getNomCarte() + " (" + carte.getType() + "): " + carte.getDescription());
            if (carte instanceof CarteOffensive) {
                CarteOffensive co = (CarteOffensive) carte;
                if (co.getTypeOffensif() == CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE) {
                    System.out.print(" [Dégâts: " + co.getDegatsInfliges() + ", Subis: " + co.getDegatsSubis() + "]");
                } else if (co.getTypeOffensif() == CarteOffensive.TypeOffensif.SOIN) {
                    System.out.print(" [Vie: " + co.getVieGagnee() + "]");
                } else if (co.getTypeOffensif() == CarteOffensive.TypeOffensif.TRESOR_OFFENSIF) { // Correction: VOL_OR -> TRESOR_OFFENSIF
                    System.out.print(" [Or Volé: " + co.getOrVole() + "]");
                }
            } else if (carte instanceof CarteStrategique) {
                CarteStrategique cs = (CarteStrategique) carte;
                 if (cs.getTypeStrategique() == CarteStrategique.TypeStrategique.POPULARITE) {
                    System.out.print(" [Popularité: " + cs.getPopulariteGagnee() + ", Subis: " + cs.getDegatsSubis() + "]");
                } else if (cs.getTypeStrategique() == CarteStrategique.TypeStrategique.TRESOR) {
                    System.out.print(" [Or Gagné: " + cs.getOrGagne() + "]");
                } else if (cs.getTypeStrategique() == CarteStrategique.TypeStrategique.SPECIALE) {
                    System.out.print(" [Effet Spécial]"); // Affichage basique
                } else if (cs.getTypeStrategique() == CarteStrategique.TypeStrategique.PASSIVE) {
                    System.out.print(" [Effet Passif]"); // Affichage basique
                }
            }
            System.out.println(); // Nouvelle ligne après chaque carte
        } else {
            System.out.println("Aucune carte piochée (pioche vide ou erreur)");
        }
    }
}