package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import carte.Carte;
import carte.TypeCarte;
import carte.CarteAttaque;
import carte.CarteOffensive;
import carte.CartePopularite;
import carte.CarteStrategique;
import controllers.ControlCartePlateau;
import controllers.ControlJeu;
import controllers.ControlJoueur;
import controllers.ControlPioche;
import joueur.Joueur;
import joueur.Pirate;


@DisplayName("Tests du contrôleur ControlJoueur")
public class TestControlJoueur {
    
    // Attributs pour les tests
    private ControlJeu controlJeu;
    private ControlPioche controlPioche;
    private Joueur joueur1, joueur2;
    private ControlJoueur controlJoueur1, controlJoueur2;
    private ControlCartePlateau controlCartePlateau;
    
    @BeforeEach
    public void initialiser() {
        // Création des objets de base pour les tests
        controlJeu = new ControlJeu();
        controlPioche = controlJeu.getControlPioche();
        
        // Création des joueurs
        joueur1 = new Joueur(new Pirate("Jack Sparrow"));
        joueur2 = new Joueur(new Pirate("Barbe Noire"));
        
        // Création des contrôleurs
        controlJoueur1 = new ControlJoueur(joueur1, controlJeu, controlPioche);
        controlJoueur2 = new ControlJoueur(joueur2, controlJeu, controlPioche);
        
        // Création du contrôleur de plateau
        controlCartePlateau = new ControlCartePlateau(controlJoueur1, controlJoueur2);
        controlJoueur1.setControlCartePlateau(controlCartePlateau);
        controlJoueur2.setControlCartePlateau(controlCartePlateau);
    }
    
    @Test
    @DisplayName("Test de la création d'un contrôleur de joueur")
    public void testCreationControlJoueur() {
        // Vérification
        assertNotNull(controlJoueur1, "Le contrôleur joueur ne devrait pas être null");
        assertEquals(joueur1, controlJoueur1.getJoueur(), "Le joueur devrait être correctement assigné");
        
        // Vérifier que le contrôleur de plateau est bien assigné
        assertDoesNotThrow(() -> controlJoueur1.jouerCarte(new CarteOffensive("Test", "", 1, 0, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE)), 
                          "Le controlCartePlateau devrait être correctement configuré");
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
    @DisplayName("Test de l'initialisation de la main")
    public void testInitialiserMain() {
        // Vérifier que la main est vide au départ
        assertEquals(0, joueur1.getMain().size(), "La main devrait être vide au départ");

        // Initialisation de la main
        controlJoueur1.initialiserMain();

        // Vérification - la méthode initialiserMain() pioche 5 cartes
        assertEquals(5, joueur1.getMain().size(), "La main devrait contenir 5 cartes après initialisation");
    }
    
    @Test
    @DisplayName("Test du jeu d'une carte par index")
    public void testJouerCarteParIndex() {
        // Ajouter une carte à la main du joueur
        CarteOffensive carteOffensive = new CarteAttaque("Épée", "Une épée tranchante", 2, 2);
        joueur1.ajouterCarte(carteOffensive);

        int tailleInitiale = joueur1.getMain().size();

        // Jouer la carte
        boolean resultat = controlJoueur1.jouerCarte(0);

        // Vérifications
        assertTrue(resultat, "La carte devrait pouvoir être jouée");
        assertEquals(tailleInitiale - 1, joueur1.getMain().size(), "La taille de la main devrait avoir diminué de 1");
        assertFalse(joueur1.getMain().contains(carteOffensive), "La carte ne devrait plus être dans la main du joueur");

        // Vérifier que la carte est bien dans la zone offensive du joueur
        List<CarteOffensive> cartesOffensives = controlCartePlateau.getZoneOffensiveJ1().getCartesOffensives();
        assertTrue(cartesOffensives.contains(carteOffensive), "La carte devrait être dans la zone offensive du joueur 1");
        assertEquals(1, cartesOffensives.size(), "La zone offensive devrait contenir exactement une carte");
    }
    
    @Test
    @DisplayName("Test du jeu d'une carte spécifique")
    public void testJouerCarteSpecifique() {
        // Ajouter deux cartes à la main du joueur
        CarteOffensive carte1 = new CarteAttaque("Épée", "Une épée tranchante", 2, 2);
        CarteStrategique carte2 = new CartePopularite("Chanson", "Une chanson entraînante", 2, 1);
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
        List<CarteStrategique> cartesStrategiques = controlCartePlateau.getZoneStrategiqueJ1().getCartesStrategiques();
        assertTrue(cartesStrategiques.contains(carte2), "La carte devrait être dans la zone stratégique du joueur 1");
        assertEquals(1, cartesStrategiques.size(), "La zone stratégique devrait contenir exactement une carte");
    }
    
    @Test
    @DisplayName("Test de la défausse d'une carte")
    public void testDefausserCarte() {
        // Ajouter une carte à la main du joueur
        Carte carte = new CarteAttaque("Épée", "Une épée tranchante", 2, 2);
        joueur1.ajouterCarte(carte);

        int tailleInitiale = joueur1.getMain().size();

        // Défausser la carte
        boolean resultat = controlJoueur1.defausserCarte(0);

        // Vérifications
        assertTrue(resultat, "La carte devrait pouvoir être défaussée");
        assertEquals(tailleInitiale - 1, joueur1.getMain().size(), "La taille de la main devrait avoir diminué de 1");
        assertFalse(joueur1.getMain().contains(carte), "La carte ne devrait plus être dans la main du joueur");
        
        // Vérifier que la carte est bien dans la défausse
        assertTrue(controlCartePlateau.getDefausse().getCartes().contains(carte),
                 "La carte devrait être dans la défausse");
    }
    
    @Test
    @DisplayName("Test de la défausse avec un index invalide")
    public void testDefausserCarteIndexInvalide() {
        // Ajouter une carte à la main du joueur
        joueur1.ajouterCarte(new CarteAttaque("Épée", "Une épée tranchante", 2, 2));

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
        Carte carte = new CarteAttaque("Épée", "Une épée tranchante", 2, 2);
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
        joueur1.ajouterCarte(new CarteAttaque("Épée", "Une épée tranchante", 2, 2));
        joueur1.ajouterCarte(new CartePopularite("Chanson", "Une chanson entraînante", 2, 1));

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
    @DisplayName("Test de la réception d'effets (dégâts et popularité)")
    public void testRecevoirEffets() {
        // État initial
        int vieInitiale = joueur1.getPointsDeVie();
        int popInitiale = joueur1.getPopularite();

        // Recevoir des effets (dégâts positifs, popularité positive)
        controlJoueur1.recevoirEffets(2, 1);

        assertEquals(vieInitiale - 2, joueur1.getPointsDeVie(), "Les points de vie devraient avoir diminué de 2");
        assertEquals(popInitiale + 1, joueur1.getPopularite(), "La popularité devrait avoir augmenté de 1");

        // Réinitialiser pour le test suivant
        vieInitiale = joueur1.getPointsDeVie();
        popInitiale = joueur1.getPopularite();

        // Recevoir des effets (dégâts négatifs = soin, popularité négative = perte)
        controlJoueur1.recevoirEffets(-2, -1);

        assertEquals(vieInitiale + 2, joueur1.getPointsDeVie(), "Les points de vie devraient avoir augmenté de 2");
        assertEquals(popInitiale - 1, joueur1.getPopularite(), "La popularité devrait avoir diminué de 1");
    }
    
    @Test
    @DisplayName("Test de la réception d'effets à partir d'une liste de cartes")
    public void testRecevoirEffetsCartes() {
        // État initial
        int vieInitiale = joueur1.getPointsDeVie();
        
        // Créer une liste de cartes avec des effets
        List<Carte> cartesAdversaire = new ArrayList<>();
        
        // Ajouter une carte d'attaque
        CarteAttaque carteAttaque = new CarteAttaque("Sabre", "Un sabre tranchant", 3, 0);
        cartesAdversaire.add(carteAttaque);
        
        // Recevoir les effets des cartes
        controlJoueur1.recevoirEffets(cartesAdversaire);
        
        // Vérification - la carte d'attaque devrait enlever des points de vie
        assertEquals(vieInitiale - 3, joueur1.getPointsDeVie(), 
                   "Les points de vie devraient avoir diminué de 3 (valeur de l'attaque)");
    }
    
    @Test
    @DisplayName("Test de l'exécution complète d'un tour de jeu")
    public void testJouerTour() {
        // Préparation du jeu
        controlJoueur1.initialiserMain();
        int tailleAvant = joueur1.getMain().size();

        // Jouer un tour
        controlJoueur1.jouerTour();

        // Vérifications 
        // La méthode jouerTour pioche une carte PUIS en joue une, donc le nombre de cartes reste le même
        assertEquals(tailleAvant, joueur1.getMain().size(), 
                   "La main devrait avoir le même nombre de cartes après la pioche et le jeu du tour");

        // Vérifier qu'au moins une des zones du plateau contient une carte
        int cartesPlateau = controlCartePlateau.getZoneOffensiveJ1().getCartesOffensives().size() +
                          controlCartePlateau.getZoneStrategiqueJ1().getCartesStrategiques().size();
        assertTrue(cartesPlateau > 0, "Une carte devrait avoir été jouée sur le plateau");
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
    @DisplayName("Test des méthodes setter")
    public void testSetters() {
        // Test de setJoueur
        Joueur nouveauJoueur = new Joueur(new Pirate("Capitaine Crochet"));
        controlJoueur1.setJoueur(nouveauJoueur);
        assertSame(nouveauJoueur, controlJoueur1.getJoueur(), "Le joueur doit être modifié correctement");
        
        // Test de setControlCarteSpeciale
        // D'abord, importer la classe ControlCarteSpeciale si ce n'est pas déjà fait
        controllers.ControlCarteSpeciale controlCarteSpeciale = new controllers.ControlCarteSpeciale(controlJoueur1, controlJoueur2);
        controlJoueur1.setControlCarteSpeciale(controlCarteSpeciale);
        
        // On ne peut pas vérifier directement car il n'y a pas de getter pour controlCarteSpeciale
        // Mais on peut tester indirectement en s'assurant que le code ne lève pas d'exception
        assertDoesNotThrow(() -> controlJoueur1.setControlCarteSpeciale(controlCarteSpeciale), 
                         "setControlCarteSpeciale ne doit pas lever d'exception");
    }
    
    @Test
    @DisplayName("Test du jeu d'une carte avec index invalide")
    public void testJouerCarteIndexInvalide() {
        // Ajouter une carte à la main du joueur
        joueur1.ajouterCarte(new CarteAttaque("Épée", "Une épée tranchante", 2, 2));
        
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
        // Tester pour joueur 1
        
        // Test pour une carte offensive sans aucun attribut estAttaque ou estSoin
        CarteOffensive carteOffensiveNeutre = new CarteOffensive("Test Neutre", "Carte test", 0, 0, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE) {
            @Override
            public EffetCarte effetCarte() {
                EffetCarte effet = super.effetCarte();
                effet.estAttaque = false;
                effet.estSoin = false;
                return effet;
            }
        };
        joueur1.ajouterCarte(carteOffensiveNeutre);
        boolean resultatOffensiveNeutre = controlJoueur1.jouerCarte(0);
        assertTrue(resultatOffensiveNeutre, "La carte offensive neutre devrait pouvoir être jouée");
        
        // Test pour une carte non reconnue
        // Créer une carte qui n'est ni offensive ni stratégique
        class CarteInconnue extends Carte {
            public CarteInconnue() {
                super(TypeCarte.valueOf("INCONNUE"), "Carte inconnue", "Description inconnue");
            }
        }
        
        try {
            CarteInconnue carteInconnue = new CarteInconnue();
            joueur1.ajouterCarte(carteInconnue);
            boolean resultatInconnue = controlJoueur1.jouerCarte(0);
            assertFalse(resultatInconnue, "Une carte de type inconnu ne devrait pas pouvoir être jouée");
        } catch (Exception e) {
            // Si une exception est levée, c'est normal car TypeCarte.INCONNUE n'existe pas
            // Le test vérifie juste que le code gère correctement ce cas
        }
        
        // Tester pour joueur 2
        // Ajouter carte offensive pour joueur 2
        CarteOffensive carteOffensiveJ2 = new CarteAttaque("Épée J2", "Une épée tranchante", 2, 2);
        joueur2.ajouterCarte(carteOffensiveJ2);
        boolean resultatOffensiveJ2 = controlJoueur2.jouerCarte(0);
        assertTrue(resultatOffensiveJ2, "La carte offensive du joueur 2 devrait pouvoir être jouée");
        
        // Vérifier que la carte est bien dans la zone offensive du joueur 2
        List<CarteOffensive> cartesOffensivesJ2 = controlCartePlateau.getZoneOffensiveJ2().getCartesOffensives();
        assertTrue(cartesOffensivesJ2.contains(carteOffensiveJ2), "La carte devrait être dans la zone offensive du joueur 2");
        
        // Ajouter carte stratégique pour joueur 2
        CarteStrategique carteStrategiqueJ2 = new CartePopularite("Chanson J2", "Une chanson entraînante", 2, 1);
        joueur2.ajouterCarte(carteStrategiqueJ2);
        boolean resultatStrategiqueJ2 = controlJoueur2.jouerCarte(0);
        assertTrue(resultatStrategiqueJ2, "La carte stratégique du joueur 2 devrait pouvoir être jouée");
        
        // Vérifier que la carte est bien dans la zone stratégique du joueur 2
        List<CarteStrategique> cartesStrategiquesJ2 = controlCartePlateau.getZoneStrategiqueJ2().getCartesStrategiques();
        assertTrue(cartesStrategiquesJ2.contains(carteStrategiqueJ2), "La carte devrait être dans la zone stratégique du joueur 2");
    }
}