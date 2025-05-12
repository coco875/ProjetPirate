package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controllers.ControlMarche;
import controllers.ControlJoueur;
import controllers.ControlPioche;
import controllers.ControlCartePlateau;
import controllers.ControlJeu;
import carte.*;
import joueur.Joueur;
import joueur.Pirate;

import java.util.List;
/*
@DisplayName("Tests du contrôleur ControlMarche")
public class TestControlMarche {

    // Attributs pour les tests
    private ControlJeu controlJeu;
    private ControlPioche controlPioche;
    private ControlJoueur controlJoueur1, controlJoueur2;
    private ControlCartePlateau controlCartePlateau;
    private ControlMarche controlMarche;
    
    @BeforeEach
    public void initialiser() {
        // Création des objets nécessaires pour les tests
        controlJeu = new ControlJeu();
        controlPioche = new ControlPioche();
        controlPioche.initialiserPioche();
        
        // Création des pirates et joueurs
        Pirate pirate1 = new Pirate("Barbe Noire");
        Pirate pirate2 = new Pirate("Jack Sparrow");
        
        // Initialiser les contrôleurs de joueur via ControlJeu
        controlJeu.setJoueur1(pirate1);
        controlJeu.setJoueur2(pirate2);
        
        // Récupérer les contrôleurs de joueur maintenant qu'ils sont créés
        controlJoueur1 = controlJeu.getJoueur(0);
        controlJoueur2 = controlJeu.getJoueur(1);
        
        // Création du contrôleur de plateau de cartes et mise à jour des contrôleurs de joueurs
        controlCartePlateau = new ControlCartePlateau(controlJoueur1, controlJoueur2);
        controlJoueur1.setControlCartePlateau(controlCartePlateau);
        controlJoueur2.setControlCartePlateau(controlCartePlateau);

        // Création du contrôleur de marché
        controlMarche = new ControlMarche(controlJoueur1, controlJoueur2, controlPioche, controlJeu);
    }
    
    @Test
    @DisplayName("Test de l'initialisation du marché")
    public void testInitialisationMarche() {
        // Vérifier que le marché contient bien des cartes après l'initialisation
        List<Carte> cartesDisponibles = controlMarche.getCartesDisponibles();
        assertFalse(cartesDisponibles.isEmpty(), "Le marché devrait contenir des cartes après l'initialisation");
    }
    
    @Test
    @DisplayName("Test du rafraîchissement du marché")
    public void testRafraichirMarche() {
        // Récupérer les cartes initiales
        List<Carte> cartesInitiales = List.copyOf(controlMarche.getCartesDisponibles());
        
        // Rafraîchir le marché
        controlMarche.rafraichirMarche();
        
        // Récupérer les nouvelles cartes
        List<Carte> nouvellesCartes = controlMarche.getCartesDisponibles();
        
        // Vérifier que le marché contient des cartes après le rafraîchissement
        assertFalse(nouvellesCartes.isEmpty(), "Le marché devrait contenir des cartes après le rafraîchissement");
    }
    
    @Test
    @DisplayName("Test d'achat de carte avec succès")
    public void testAcheterCarteAvecSucces() {
        // Simuler le tour du joueur 1 pour l'achat
        controlJeu.setJoueurActif(0);
        
        // S'assurer que le joueur a assez d'or
        controlJoueur1.getJoueur().setOr(10);
        
        // S'assurer qu'il y a des cartes dans le marché
        if (controlMarche.getCartesDisponibles().isEmpty()) {
            controlMarche.rafraichirMarche();
        }
        
        // Récupérer la taille initiale de la main du joueur
        int tailleInitialeMain = controlJoueur1.getJoueur().getMain().size();
        
        // Acheter la première carte
        boolean resultat = controlMarche.acheterCarte(0);
        
        // Vérifier que l'achat a réussi
        assertTrue(resultat, "L'achat de la carte devrait réussir avec suffisamment d'or");
        
        // Vérifier que la main du joueur a augmenté d'une carte
        assertEquals(tailleInitialeMain + 1, controlJoueur1.getJoueur().getMain().size(), 
                    "La main du joueur devrait avoir une carte de plus après l'achat");
    }
    
    @Test
    @DisplayName("Test d'achat de carte sans assez d'or")
    public void testAcheterCarte_SansAssezOr() {
        // Simuler le tour du joueur 2 pour l'achat
        controlJeu.setJoueurActif(1);
        
        // S'assurer que le joueur n'a pas assez d'or (0)
        controlJoueur2.getJoueur().setOr(0);
        
        // S'assurer qu'il y a des cartes dans le marché
        if (controlMarche.getCartesDisponibles().isEmpty()) {
            controlMarche.rafraichirMarche();
        }
        
        // Récupérer le coût de la carte à acheter (et s'assurer qu'il est > 0)
        Carte carteAcheter = controlMarche.getCartesDisponibles().get(0);
        if (carteAcheter.getCout() == 0) {
            // Si la carte est gratuite, on va augmenter artificiellement son coût pour le test
            // (ça ne devrait pas arriver en réalité, mais c'est pour garantir le test)
            CarteAttaque carteCouteuse = new CarteAttaque("Carte Coûteuse", "Une carte très chère", 5, 3);
            controlMarche.getCartesDisponibles().set(0, carteCouteuse);
        }
        
        // Tenter l'achat
        boolean resultat = controlMarche.acheterCarte(0);
        
        // Vérifier que l'achat a échoué
        assertFalse(resultat, "L'achat devrait échouer sans assez d'or");
    }
    
    @Test
    @DisplayName("Test d'achat de carte avec index invalide")
    public void testAcheterCarte_IndexInvalide() {
        // Index négatif
        boolean resultatNegatif = controlMarche.acheterCarte(-1);
        assertFalse(resultatNegatif, "L'achat avec un index négatif devrait échouer");
        
        // Index trop grand
        boolean resultatTropGrand = controlMarche.acheterCarte(100);
        assertFalse(resultatTropGrand, "L'achat avec un index trop grand devrait échouer");
    }
    
    @Test
    @DisplayName("Test de vente de carte avec succès")
    public void testVendreCarteAvecSucces() {
        // Ajouter une carte à la main du joueur 1
        Carte carte = new CarteAttaque("Épée", "Une épée tranchante", 2, 2);
        controlJoueur1.getJoueur().ajouterCarte(carte);
        
        // Récupérer l'or initial
        int orInitial = controlJoueur1.getJoueur().getOr();
        
        // Vendre la carte (index 0 dans la main)
        boolean resultat = controlMarche.vendreCarte(1, 0);
        
        // Vérifier que la vente a réussi
        assertTrue(resultat, "La vente de la carte devrait réussir");
        
        // Vérifier que le joueur a reçu de l'or
        assertTrue(controlJoueur1.getJoueur().getOr() > orInitial, 
                 "Le joueur devrait avoir plus d'or après avoir vendu une carte");
    }
    
    @Test
    @DisplayName("Test de vente de carte avec index invalide")
    public void testVendreCarte_IndexInvalide() {
        // Index négatif
        boolean resultatNegatif = controlMarche.vendreCarte(1, -1);
        assertFalse(resultatNegatif, "La vente avec un index négatif devrait échouer");
        
        // Index trop grand
        boolean resultatTropGrand = controlMarche.vendreCarte(1, 100);
        assertFalse(resultatTropGrand, "La vente avec un index trop grand devrait échouer");
    }
    
    @Test
    @DisplayName("Test de vente de carte avec ID joueur invalide")
    public void testVendreCarte_JoueurInvalide() {
        // ID joueur invalide
        boolean resultat = controlMarche.vendreCarte(3, 0);
        assertFalse(resultat, "La vente avec un ID joueur invalide devrait échouer");
    }
    
    @Test
    @DisplayName("Test de vente de carte avec valeur nulle")
    public void testVendreCarte_ValeurNulle() {
        // Créer une carte avec une valeur nulle
        Carte carteZero = new CarteAttaque("Carte zéro", "Une carte sans valeur", 0, 0);
        controlJoueur1.getJoueur().ajouterCarte(carteZero);
        
        int orInitial = controlJoueur1.getJoueur().getOr();
        
        // Vendre la carte
        boolean resultat = controlMarche.vendreCarte(1, 0);
        
        // Vérifier que la vente a réussi
        assertTrue(resultat, "La vente d'une carte sans valeur devrait quand même réussir");
        
        // Vérifier que le joueur a reçu au moins 1 or (minimum)
        assertEquals(orInitial + 1, controlJoueur1.getJoueur().getOr(), 
                    "Le joueur devrait recevoir au moins 1 or pour une carte sans valeur");
    }
    
    @Test
    @DisplayName("Test de rafraîchissement automatique du marché")
    public void testRafraichissementAutomatique() {
        // Vider le marché d'abord
        controlMarche.getCartesDisponibles().clear();
        
        // Demander les cartes disponibles - devrait déclencher un rafraîchissement
        List<Carte> cartes = controlMarche.getCartesDisponibles();
        
        // Vérifier que le marché n'est pas vide
        assertFalse(cartes.isEmpty(), "Le marché devrait être automatiquement rafraîchi s'il est vide");
    }
    
    @Test
    @DisplayName("Test d'achat de la dernière carte")
    public void testAchatDerniereCarte() {
        // Vider le marché sauf une carte
        while (controlMarche.getCartesDisponibles().size() > 1) {
            controlMarche.getCartesDisponibles().remove(0);
        }
        
        // S'assurer que le joueur a assez d'or
        controlJeu.setJoueurActif(0);
        controlJoueur1.getJoueur().setOr(10);
        
        // Acheter la dernière carte
        boolean resultat = controlMarche.acheterCarte(0);
        
        // Vérifier que l'achat a réussi
        assertTrue(resultat, "L'achat de la dernière carte devrait réussir");
        
        // Vérifier que le marché a été rafraîchi
        assertFalse(controlMarche.getCartesDisponibles().isEmpty(), 
                   "Le marché devrait être rafraîchi après l'achat de la dernière carte");
    }
    
    @Test
    @DisplayName("Test de rafraîchissement avec pioche vide")
    public void testRafraichirAvecPiocheVide() {
        // Vider la pioche
        while (!controlPioche.estVide()) {
            controlPioche.piocher();
        }
        
        // Vider le marché
        controlMarche.getCartesDisponibles().clear();
        
        // Rafraîchir le marché
        controlMarche.rafraichirMarche();
        
        // Vérifier que le marché contient des cartes (cartes par défaut)
        assertFalse(controlMarche.getCartesDisponibles().isEmpty(), 
                   "Le marché devrait contenir des cartes par défaut même avec une pioche vide");
    }
    
    @Test
    @DisplayName("Test de vente de carte avec joueur 2")
    public void testVendreCarteJoueur2() {
        // Ajouter une carte à la main du joueur 2
        Carte carte = new CarteAttaque("Épée", "Une épée tranchante", 2, 2);
        controlJoueur2.getJoueur().ajouterCarte(carte);
        
        // Récupérer l'or initial
        int orInitial = controlJoueur2.getJoueur().getOr();
        
        // Vendre la carte (index 0 dans la main du joueur 2)
        boolean resultat = controlMarche.vendreCarte(2, 0);
        
        // Vérifier que la vente a réussi
        assertTrue(resultat, "La vente de la carte pour le joueur 2 devrait réussir");
        
        // Vérifier que le joueur a reçu de l'or
        assertTrue(controlJoueur2.getJoueur().getOr() > orInitial, 
                 "Le joueur 2 devrait avoir plus d'or après avoir vendu une carte");
    }
    
    @Test
    @DisplayName("Test de remplirMarcheInitial avec pioche vide")
    public void testRemplirMarcheInitialPiocheVide() throws Exception {
        // Créer un mock de ControlPioche qui retourne null
        ControlPioche mockPioche = new ControlPioche() {
            @Override
            public Carte piocher() {
                return null; // Simuler une pioche vide
            }
        };
        
        // Créer une nouvelle instance de ControlMarche avec la pioche mock
        ControlMarche controlMarcheMock = new ControlMarche(controlJoueur1, controlJoueur2, mockPioche, controlJeu);
        
        // Le constructeur appelle remplirMarcheInitial qui devrait gérer le cas où piocher() retourne null
        // Vérifions que le marché est vide (puisque toutes les cartes piochées étaient null)
        assertTrue(controlMarcheMock.getCartesDisponibles().size() <= 3,
                  "Le marché devrait contenir au maximum 3 cartes si la pioche est vide");
    }
    
    public static void main(String[] args) throws Exception {
        System.out.println("Test de la classe ControlMarche - version simple");
        // Créer une instance de test et exécuter quelques méthodes de test
        TestControlMarche test = new TestControlMarche();
        test.initialiser();
        test.testInitialisationMarche();
        test.testAcheterCarteAvecSucces();
        test.testVendreCarteAvecSucces();
    }
}*/