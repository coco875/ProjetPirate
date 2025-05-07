package test;

import controllers.ControlPioche;
import carte.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class TestControlPioche {
    
    private ControlPioche controlPioche;
    
    @BeforeEach
    public void setUp() {
        controlPioche = new ControlPioche(); // Initialise et charge les cartes
    }

    @Test
    public void testCartesChargees() throws Exception {
        // Test spécifique pour quelques cartes clés
        testerCarte("src/carte/resource/attaque/attaque1.txt", "Canon Dévastateur", 2, 1, 0, 0, 0); // Attaque
        testerCarte("src/carte/resource/popularite/popularite1.txt", "Chansons de Marin", 0, 0, 2, 0, 0); // Popularité
        testerCarte("src/carte/resource/soin/soin1.txt", "Potion du Chirurgien", 0, 0, 0, 3, 0); // Soin
        testerCarte("src/carte/resource/tresor/tresor1.txt", "Coffre au Trésor", 0, 0, 0, 0, 10); // Trésor (gain or)
        testerCarte("src/carte/resource/tresor/tresor3.txt", "Vol de Butin", 0, 0, 0, 0, 0); // Anciennement vol d'or, maintenant attaque simple
    }
    
    @Test
    public void testPiocher() {
        // Test de la méthode piocher
        Carte cartePiochee = controlPioche.piocher();
        assertNotNull(cartePiochee, "La carte piochée ne devrait pas être null");
    }
    
    @Test
    public void testPiochesMultiples() {
        // Test de pioche multiple
        for (int i = 0; i < 5; i++) {
            Carte cartePiochee = controlPioche.piocher();
            assertNotNull(cartePiochee, "La carte piochée " + (i+1) + " ne devrait pas être null");
        }
    }

    // Méthode pour tester une carte spécifique chargée par le parseur
    private void testerCarte(String filePath, String expectedTitre, int expectedDegatsInfliges, int expectedDegatsSubis, int expectedPopularite, int expectedVie, int expectedOr) throws Exception {
        Carte carte = ParserCarte.lireCarte(filePath);
        assertNotNull(carte, "La carte ne devrait pas être null: " + filePath);
        assertEquals(expectedTitre, carte.getNomCarte(), "Le titre ne correspond pas pour " + filePath);

        Carte.EffetCarte effet = carte.effetCarte();
        
        if (carte instanceof CarteOffensive) {
            CarteOffensive co = (CarteOffensive) carte;
            if (co.getTypeOffensif() == CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE) {
                assertEquals(expectedDegatsInfliges, effet.degatsInfliges, "Dégâts infligés incorrects pour " + filePath);
                assertEquals(expectedDegatsSubis, effet.degatsSubis, "Dégâts subis incorrects pour " + filePath);
            } else if (co.getTypeOffensif() == CarteOffensive.TypeOffensif.SOIN) {
                assertEquals(expectedVie, effet.vieGagnee, "Vie gagnée incorrecte pour " + filePath);
            }
            // La vérification pour TRESOR_OFFENSIF a été supprimée
        } else if (carte instanceof CarteStrategique) {
            CarteStrategique cs = (CarteStrategique) carte;
             if (cs.getTypeStrategique() == CarteStrategique.TypeStrategique.POPULARITE) {
                assertEquals(expectedPopularite, effet.populariteGagnee, "Popularité gagnée incorrecte pour " + filePath);
                assertEquals(expectedDegatsSubis, effet.degatsSubis, "Dégâts subis (pop) incorrects pour " + filePath);
            } else if (cs.getTypeStrategique() == CarteStrategique.TypeStrategique.TRESOR) {
                 assertEquals(expectedOr, effet.orGagne, "Or gagné incorrect pour " + filePath);
                 // assertEquals(0, effet.orPerdu, "Or perdu incorrect pour " + filePath); // Le parseur met 0 pour l'instant
            }
            // Ajouter des vérifications pour SPECIALE et PASSIVE si nécessaire
        }
    }

    // Méthode utilitaire pour afficher les détails d'une carte (pour débogage si nécessaire)
    private void afficherDetailsCarte(Carte carte) {
        if (carte != null) {
            System.out.print(carte.getNomCarte() + " (" + carte.getType() + "): " + carte.getDescription());
            
            Carte.EffetCarte effet = carte.effetCarte();
            
            if (carte instanceof CarteOffensive) {
                CarteOffensive co = (CarteOffensive) carte;
                if (co.getTypeOffensif() == CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE) {
                    System.out.print(" [Dégâts: " + effet.degatsInfliges + ", Subis: " + effet.degatsSubis + "]");
                } else if (co.getTypeOffensif() == CarteOffensive.TypeOffensif.SOIN) {
                    System.out.print(" [Vie: " + effet.vieGagnee + "]");
                }
                // L'affichage pour TRESOR_OFFENSIF a été supprimé
            } else if (carte instanceof CarteStrategique) {
                CarteStrategique cs = (CarteStrategique) carte;
                 if (cs.getTypeStrategique() == CarteStrategique.TypeStrategique.POPULARITE) {
                    System.out.print(" [Popularité: " + effet.populariteGagnee + ", Subis: " + effet.degatsSubis + "]");
                } else if (cs.getTypeStrategique() == CarteStrategique.TypeStrategique.TRESOR) {
                    System.out.print(" [Or Gagné: " + effet.orGagne + "]");
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