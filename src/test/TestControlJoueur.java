package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import carte.Carte;
import carte.TypeCarte;
import carte.CarteAttaque;
import carte.CarteOffensive;
import carte.CartePopularite;
import carte.CarteStrategique;
import controllers.ControlJeu;
import controllers.ControlJoueur;
import controllers.ControlPioche;
import controllers.ControlZoneJoueur;
import jeu.ZoneOffensive;
import jeu.ZoneStrategique;
import joueur.Joueur;
import joueur.Pirate;

@DisplayName("Tests du contrôleur ControlJoueur")
public class TestControlJoueur {
    
    // Attributs pour les tests
    private ControlJeu controlJeu;
    private ControlPioche controlPioche;
    private Joueur joueur1, joueur2;
    private ControlJoueur controlJoueur1, controlJoueur2;
    private ControlZoneJoueur zoneJoueur1, zoneJoueur2;
    
    @BeforeEach
    public void initialiser() {
        // Création des objets de base pour les tests
        controlJeu = new ControlJeu();
        controlPioche = new ControlPioche();
        
        // Création des joueurs
        joueur1 = new Joueur(new Pirate("Jack Sparrow"));
        joueur2 = new Joueur(new Pirate("Barbe Noire"));
        
        // Création des zones de joueur
        zoneJoueur1 = new ControlZoneJoueur(new ZoneOffensive(), new ZoneStrategique());
        zoneJoueur2 = new ControlZoneJoueur(new ZoneOffensive(), new ZoneStrategique());
        
        // Création des contrôleurs
        controlJoueur1 = new ControlJoueur(joueur1, controlPioche, zoneJoueur1);
        controlJoueur2 = new ControlJoueur(joueur2, controlPioche, zoneJoueur2);
    }
    
    @Test
    @DisplayName("Test de la création d'un contrôleur de joueur")
    public void testCreationControlJoueur() {
        // Vérification
        assertNotNull(controlJoueur1, "Le contrôleur joueur ne devrait pas être null");
        assertEquals(joueur1, controlJoueur1.getJoueur(), "Le joueur devrait être correctement assigné");
        
        // Vérifier que la zone de joueur est bien assignée
        CarteOffensive carteAttaque = new CarteAttaque("Attaque", "Description", 2, 0);
        joueur1.ajouterCarte(carteAttaque);
        assertDoesNotThrow(() -> controlJoueur1.jouerCarte(0), 
                          "La carte devrait pouvoir être jouée");
    }
    
    @Test
    @DisplayName("Test de la pioche d'une carte")
    public void testPiocher() {
        // Taille initiale de la main
        int tailleInitiale = joueur1.getMain().size();

        // Pioche d'une carte
        Carte cartePiochee = controlJoueur1.piocher();

        // Vérifications
        assertNotNull(cartePiochee, "La carte piochée ne devrait pas être null");
        assertEquals(tailleInitiale + 1, joueur1.getMain().size(), "La taille de la main devrait avoir augmenté de 1");
        assertTrue(joueur1.getMain().contains(cartePiochee), "La main devrait contenir la carte piochée");
    }
    
    @Test
    @DisplayName("Test de la pioche d'une carte null")
    public void testPiocherCarteNull() {
        // Créer un mock de ControlPioche qui retourne null
        ControlPioche mockPioche = new ControlPioche() {
            @Override
            public Carte piocher() {
                return null; // Simuler une pioche vide
            }
        };
        
        // Créer un contrôleur avec le mock
        ControlJoueur controlJoueurMock = new ControlJoueur(joueur1, mockPioche, zoneJoueur1);
        
        // Taille initiale de la main
        int tailleInitiale = joueur1.getMain().size();
        
        // Pioche d'une carte (qui sera null)
        Carte cartePiochee = controlJoueurMock.piocher();
        
        // Vérifications
        assertNull(cartePiochee, "La carte piochée devrait être null");
        assertEquals(tailleInitiale, joueur1.getMain().size(), "La taille de la main ne devrait pas avoir changé");
    }
    
    @Test
    @DisplayName("Test de l'initialisation de la main")
    public void testInitialiserMain() {
        // Vérifier que la main est vide au départ
        assertEquals(0, joueur1.getMain().size(), "La main devrait être vide au départ");

        // Initialisation de la main
        controlJoueur1.initialiserMain();

        // Vérification - la méthode initialiserMain() pioche 1 carte spéciale + 4 cartes normales
        assertEquals(5, joueur1.getMain().size(), "La main devrait contenir 5 cartes après initialisation");
    }
    
    @Test
    @DisplayName("Test du jeu d'une carte par index")
    public void testJouerCarteParIndex() {
        // Ajouter une carte à la main du joueur
        CarteOffensive carteOffensive = new CarteAttaque("Épée", "Une épée tranchante", 2, 0);
        joueur1.ajouterCarte(carteOffensive);

        int tailleInitiale = joueur1.getMain().size();

        // Jouer la carte
        boolean resultat = controlJoueur1.jouerCarte(0);

        // Vérifications
        assertTrue(resultat, "La carte devrait pouvoir être jouée");
        assertEquals(tailleInitiale - 1, joueur1.getMain().size(), "La taille de la main devrait avoir diminué de 1");
        assertFalse(joueur1.getMain().contains(carteOffensive), "La carte ne devrait plus être dans la main du joueur");

        // Vérifier que la carte est bien dans la zone offensive du joueur
        List<CarteOffensive> cartesOffensives = zoneJoueur1.getZoneOffensive().getCartesOffensives();
        assertTrue(cartesOffensives.contains(carteOffensive), "La carte devrait être dans la zone offensive du joueur 1");
        assertEquals(1, cartesOffensives.size(), "La zone offensive devrait contenir exactement une carte");
    }
    
    @Test
    @DisplayName("Test du jeu d'une carte spécifique")
    public void testJouerCarteSpecifique() {
        // Ajouter deux cartes à la main du joueur
        CarteOffensive carte1 = new CarteAttaque("Épée", "Une épée tranchante", 2, 0);
        CarteStrategique carte2 = new CartePopularite("Chanson", "Une chanson entraînante", 1, 0);
        joueur1.ajouterCarte(carte1);
        joueur1.ajouterCarte(carte2);

        int tailleInitiale = joueur1.getMain().size();

        // Jouer la carte stratégique spécifiquement
        boolean resultat = controlJoueur1.jouerCarte(carte2);

        // Vérifications
        assertTrue(resultat, "La carte devrait pouvoir être jouée");
        assertEquals(tailleInitiale - 1, joueur1.getMain().size(), "La taille de la main devrait avoir diminué de 1");
        assertFalse(joueur1.getMain().contains(carte2), "La carte ne devrait plus être dans la main du joueur");
        assertTrue(joueur1.getMain().contains(carte1), "L'autre carte devrait toujours être dans la main");

        // Vérifier que la carte est bien dans la zone stratégique du joueur
        List<CarteStrategique> cartesStrategiques = zoneJoueur1.getZoneStrategique().getCartesStrategiques();
        assertTrue(cartesStrategiques.contains(carte2), "La carte devrait être dans la zone stratégique du joueur 1");
        assertEquals(1, cartesStrategiques.size(), "La zone stratégique devrait contenir exactement une carte");
    }
    
    @Test
    @DisplayName("Test de la défausse d'une carte")
    public void testDefausserCarte() {
        // Ajouter une carte à la main du joueur
        Carte carte = new CarteAttaque("Épée", "Une épée tranchante", 2, 0);
        joueur1.ajouterCarte(carte);

        int tailleInitiale = joueur1.getMain().size();

        // Défausser la carte
        boolean resultat = controlJoueur1.defausserCarte(0);

        // Vérifications
        assertTrue(resultat, "La carte devrait pouvoir être défaussée");
        assertEquals(tailleInitiale - 1, joueur1.getMain().size(), "La taille de la main devrait avoir diminué de 1");
        assertFalse(joueur1.getMain().contains(carte), "La carte ne devrait plus être dans la main du joueur");
    }
    
    @Test
    @DisplayName("Test de la défausse avec un index invalide")
    public void testDefausserCarteIndexInvalide() {
        // Ajouter une carte à la main du joueur
        joueur1.ajouterCarte(new CarteAttaque("Épée", "Une épée tranchante", 2, 0));

        // Tenter de défausser avec un index négatif
        boolean resultatNegatif = controlJoueur1.defausserCarte(-1);
        assertFalse(resultatNegatif, "La défausse avec un index négatif devrait échouer");
        
        // Tenter de défausser avec un index trop grand
        boolean resultatTropGrand = controlJoueur1.defausserCarte(10);
        assertFalse(resultatTropGrand, "La défausse avec un index trop grand devrait échouer");
    }
    
    @Test
    @DisplayName("Test du retrait d'une carte")
    public void testRetirerCarte() {
        // Ajouter une carte à la main du joueur
        Carte carte = new CarteAttaque("Épée", "Une épée tranchante", 2, 0);
        joueur1.ajouterCarte(carte);

        int tailleInitiale = joueur1.getMain().size();

        // Retirer la carte
        boolean resultat = controlJoueur1.retirerCarte(carte);

        // Vérifications
        assertTrue(resultat, "La carte devrait pouvoir être retirée");
        assertEquals(tailleInitiale - 1, joueur1.getMain().size(), "La taille de la main devrait avoir diminué de 1");
        assertFalse(joueur1.getMain().contains(carte), "La carte ne devrait plus être dans la main du joueur");
    }
    
    @Test
    @DisplayName("Test de l'affichage de la main")
    public void testAfficherMain() {
        // Ajouter quelques cartes à la main du joueur
        joueur1.ajouterCarte(new CarteAttaque("Épée", "Une épée tranchante", 2,0 ));
        joueur1.ajouterCarte(new CartePopularite("Chanson", "Une chanson entraînante", 1, 0));

        // Afficher la main
        List<Carte> cartes = controlJoueur1.afficherMain();

        // Vérifications
        assertNotNull(cartes, "La liste des cartes ne devrait pas être null");
        assertEquals(2, cartes.size(), "La liste devrait contenir 2 cartes");
        assertEquals(joueur1.getMain(), cartes, "La liste retournée devrait correspondre à la main du joueur");
    }
    
    @Test
    @DisplayName("Test de la perte de points de vie")
    public void testPerdrePointsDeVie() {
        // Points de vie initiaux
        int vieInitiale = joueur1.getPointsDeVie();

        // Perdre des points de vie
        controlJoueur1.perdrePointsDeVie(2);

        // Vérification
        assertEquals(vieInitiale - 2, joueur1.getPointsDeVie(), "Les points de vie devraient avoir diminué de 2");
    }
    
    @Test
    @DisplayName("Test du gain de points de vie")
    public void testGagnerPointsDeVie() {
        // Réduire les points de vie pour pouvoir les augmenter
        joueur1.perdrePointsDeVie(3);
        int vieInitiale = joueur1.getPointsDeVie();

        // Gagner des points de vie
        controlJoueur1.gagnerPointsDeVie(2);

        // Vérification
        assertEquals(vieInitiale + 2, joueur1.getPointsDeVie(), "Les points de vie devraient avoir augmenté de 2");
    }
    
    @Test
    @DisplayName("Test de la perte de popularité")
    public void testPerdrePopularite() {
        // Augmenter la popularité pour pouvoir la réduire
        joueur1.gagnerPopularite(3);
        int popInitiale = joueur1.getPopularite();

        // Perdre de la popularité
        controlJoueur1.perdrePopularite(2);

        // Vérification
        assertEquals(popInitiale - 2, joueur1.getPopularite(), "La popularité devrait avoir diminué de 2");
    }
    
    @Test
    @DisplayName("Test du gain de popularité")
    public void testGagnerPopularite() {
        // Popularité initiale
        int popInitiale = joueur1.getPopularite();

        // Gagner de la popularité
        controlJoueur1.gagnerPopularite(2);

        // Vérification
        assertEquals(popInitiale + 2, joueur1.getPopularite(), "La popularité devrait avoir augmenté de 2");
    }
    
    @Test
    @DisplayName("Test du gain et perte d'or")
    public void testGestionOr() {
        // Or initial
        int orInitial = joueur1.getOr();

        // Gagner de l'or
        controlJoueur1.gagnerOr(5);
        assertEquals(orInitial + 5, joueur1.getOr(), "L'or devrait avoir augmenté de 5");

        // Perdre de l'or
        boolean resultat = controlJoueur1.perdreOr(3);
        assertTrue(resultat, "La perte d'or devrait réussir");
        assertEquals(orInitial + 2, joueur1.getOr(), "L'or devrait avoir diminué de 3 après le gain de 5");

        // Tenter de perdre plus d'or que possédé
        resultat = controlJoueur1.perdreOr(orInitial + 10);
        assertFalse(resultat, "La perte d'une quantité excessive d'or devrait échouer");
    }
    
    @Test
    @DisplayName("Test des méthodes getter")
    public void testGetters() {
        // Préparation
        String nomPirateAttendu = "Jack Sparrow";
        int pointsDeVieAttendus = joueur1.getPointsDeVie();
        int populariteAttendue = joueur1.getPopularite();
        int orAttendu = joueur1.getOr();
        
        // Vérifications
        assertEquals(nomPirateAttendu, controlJoueur1.getNom(), "Le nom du pirate doit correspondre");
        assertEquals(pointsDeVieAttendus, controlJoueur1.getPointsDeVie(), "Les points de vie doivent correspondre");
        assertEquals(populariteAttendue, controlJoueur1.getPopularite(), "La popularité doit correspondre");
        assertEquals(orAttendu, controlJoueur1.getOr(), "L'or doit correspondre");
        
        // Vérifier que getPirate renvoie bien le pirate
        assertSame(joueur1.getPirate(), controlJoueur1.getPirate(), "Le pirate doit être le même");
        
        // Vérifier que getMain renvoie bien la main du joueur
        assertSame(joueur1.getMain(), controlJoueur1.getMain(), "La main doit être la même");
    }
    
    @Test
    @DisplayName("Test de la méthode setJoueur")
    public void testSetters() {
        // Test de setJoueur
        Joueur nouveauJoueur = new Joueur(new Pirate("Capitaine Crochet"));
        controlJoueur1.setJoueur(nouveauJoueur);
        assertSame(nouveauJoueur, controlJoueur1.getJoueur(), "Le joueur doit être modifié correctement");
        assertEquals("Capitaine Crochet", controlJoueur1.getNom(), "Le nom du nouveau pirate devrait être récupéré");
    }
    
    @Test
    @DisplayName("Test du jeu d'une carte avec index invalide")
    public void testJouerCarteIndexInvalide() {
        // Ajouter une carte à la main du joueur
        joueur1.ajouterCarte(new CarteAttaque("Épée", "Une épée tranchante", 2, 0));
        
        // Tester avec un index négatif
        boolean resultatNegatif = controlJoueur1.jouerCarte(-1);
        assertFalse(resultatNegatif, "Jouer une carte avec un index négatif devrait échouer");
        
        // Tester avec un index trop grand
        boolean resultatTropGrand = controlJoueur1.jouerCarte(10);
        assertFalse(resultatTropGrand, "Jouer une carte avec un index trop grand devrait échouer");
        
        // Vérifier que la main n'a pas changé
        assertEquals(1, joueur1.getMain().size(), "La taille de la main ne devrait pas avoir changé");
    }
    
    @Test
    @DisplayName("Test du jeu d'une carte avec différents types")
    public void testJouerDifferentsTypesCarte() {
        // Test pour joueur 1
        // Ajouter carte offensive pour joueur 1
        CarteOffensive carteOffensiveJ1 = new CarteAttaque("Épée J1", "Une épée tranchante", 2, 0);
        joueur1.ajouterCarte(carteOffensiveJ1);
        boolean resultatOffensiveJ1 = controlJoueur1.jouerCarte(0);
        assertTrue(resultatOffensiveJ1, "La carte offensive du joueur 1 devrait pouvoir être jouée");
        
        // Vérifier que la carte est bien dans la zone offensive du joueur 1
        List<CarteOffensive> cartesOffensivesJ1 = zoneJoueur1.getZoneOffensive().getCartesOffensives();
        assertTrue(cartesOffensivesJ1.contains(carteOffensiveJ1), "La carte devrait être dans la zone offensive du joueur 1");
        
        // Test pour joueur 2
        // Ajouter carte offensive pour joueur 2
        CarteOffensive carteOffensiveJ2 = new CarteAttaque("Épée J2", "Une épée tranchante", 2, 0);
        joueur2.ajouterCarte(carteOffensiveJ2);
        boolean resultatOffensiveJ2 = controlJoueur2.jouerCarte(0);
        assertTrue(resultatOffensiveJ2, "La carte offensive du joueur 2 devrait pouvoir être jouée");
        
        // Vérifier que la carte est bien dans la zone offensive du joueur 2
        List<CarteOffensive> cartesOffensivesJ2 = zoneJoueur2.getZoneOffensive().getCartesOffensives();
        assertTrue(cartesOffensivesJ2.contains(carteOffensiveJ2), "La carte devrait être dans la zone offensive du joueur 2");
        
        // Ajouter carte stratégique pour joueur 1
        CarteStrategique carteStrategiqueJ1 = new CartePopularite("Chanson J1", "Une chanson entraînante", 1,0);
        joueur1.ajouterCarte(carteStrategiqueJ1);
        boolean resultatStrategiqueJ1 = controlJoueur1.jouerCarte(0);
        assertTrue(resultatStrategiqueJ1, "La carte stratégique du joueur 1 devrait pouvoir être jouée");
        
        // Vérifier que la carte est bien dans la zone stratégique du joueur 1
        List<CarteStrategique> cartesStrategiquesJ1 = zoneJoueur1.getZoneStrategique().getCartesStrategiques();
        assertTrue(cartesStrategiquesJ1.contains(carteStrategiqueJ1), "La carte devrait être dans la zone stratégique du joueur 1");
        
        // Ajouter carte stratégique pour joueur 2
        CarteStrategique carteStrategiqueJ2 = new CartePopularite("Chanson J2", "Une chanson entraînante", 1, 0);
        joueur2.ajouterCarte(carteStrategiqueJ2);
        boolean resultatStrategiqueJ2 = controlJoueur2.jouerCarte(0);
        assertTrue(resultatStrategiqueJ2, "La carte stratégique du joueur 2 devrait pouvoir être jouée");
        
        // Vérifier que la carte est bien dans la zone stratégique du joueur 2
        List<CarteStrategique> cartesStrategiquesJ2 = zoneJoueur2.getZoneStrategique().getCartesStrategiques();
        assertTrue(cartesStrategiquesJ2.contains(carteStrategiqueJ2), "La carte devrait être dans la zone stratégique du joueur 2");
    }
}
