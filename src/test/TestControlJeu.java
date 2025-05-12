package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import carte.Carte;
import carte.CarteOffensive;
import carte.CarteStrategique;
import controllers.ControlJeu;
import controllers.ControlJoueur;
import joueur.Joueur;
import joueur.Pirate;

@DisplayName("Tests du contrôleur ControlJeu")
public class TestControlJeu {
    
    private ControlJeu controlJeu;
    private Pirate pirate1;
    private Pirate pirate2;
    
    @BeforeEach
    public void initialiser() {
        controlJeu = new ControlJeu();
        pirate1 = new Pirate("Barbe Noire");
        pirate2 = new Pirate("Anne Bonny");
        controlJeu.setJoueur1(pirate1);
        controlJeu.setJoueur2(pirate2);
    }

	@Test
	@DisplayName("Test de l'initialisation des joueurs")
	public void testInitialisationJoueurs() {
		// Vérifications
		assertNotNull(controlJeu.getJoueur(0), "Le joueur 1 devrait être initialisé");
		assertNotNull(controlJeu.getJoueur(1), "Le joueur 2 devrait être initialisé");
		
		// Vérification des noms de pirates
		assertEquals("Barbe Noire", controlJeu.getJoueur(0).getJoueur().getPirate().getNom(), "Le nom du pirate 1 est incorrect");
		assertEquals("Anne Bonny", controlJeu.getJoueur(1).getJoueur().getPirate().getNom(), "Le nom du pirate 2 est incorrect");
	}
	
	@Test
	@DisplayName("Test de l'initialisation du jeu")
	public void testInitialiserJeu() {
	    // Initialiser le jeu
	    controlJeu.initialiserJeu();
	    
	    // Vérifier que les composants du jeu sont initialisés
	    assertNotNull(controlJeu.getControlPioche(), "Le contrôleur de pioche devrait être initialisé");
	    assertNotNull(controlJeu.getControlCartePlateau(), "Le contrôleur de plateau devrait être initialisé");
	    assertNotNull(controlJeu.getControlMarche(), "Le contrôleur de marché devrait être initialisé");
	    
	    // Vérifier que les joueurs ont des cartes dans leur main
	    assertTrue(controlJeu.getJoueur(0).getJoueur().getMain().size() > 0, "Le joueur 1 devrait avoir des cartes");
	    assertTrue(controlJeu.getJoueur(1).getJoueur().getMain().size() > 0, "Le joueur 2 devrait avoir des cartes");
	}
	
	@Test
	@DisplayName("Test de la méthode creerJoueur")
	public void testCreerJoueur() {
	    // Créer un nouveau ControlJeu sans joueurs
	    ControlJeu nouveauControlJeu = new ControlJeu();
	    
	    // Créer un joueur
	    Pirate nouveauPirate = new Pirate("Jack Sparrow");
	    nouveauControlJeu.creerJoueur(nouveauPirate);
	    
	    // Vérifier que le joueur a été créé à l'index 0
	    assertNotNull(nouveauControlJeu.getJoueur(0), "Le joueur devrait être créé à l'index 0");
	    assertEquals("Jack Sparrow", nouveauControlJeu.getJoueur(0).getJoueur().getPirate().getNom(), 
	                "Le nom du pirate est incorrect");
	    
	    // Créer un deuxième joueur
	    Pirate autrePirate = new Pirate("Barbe Rouge");
	    nouveauControlJeu.creerJoueur(autrePirate);
	    
	    // Vérifier que le deuxième joueur a été créé à l'index 1
	    assertNotNull(nouveauControlJeu.getJoueur(1), "Le joueur devrait être créé à l'index 1");
	    assertEquals("Barbe Rouge", nouveauControlJeu.getJoueur(1).getJoueur().getPirate().getNom(), 
	                "Le nom du pirate est incorrect");
	}
	
	@Test
	@DisplayName("Test de la méthode distribuerCartesInitiales")
	public void testDistribuerCartesInitiales() {
	    // S'assurer que les mains sont vides au début
	    assertEquals(0, controlJeu.getJoueur(0).getJoueur().getMain().size(), "La main du joueur 1 devrait être vide");
	    assertEquals(0, controlJeu.getJoueur(1).getJoueur().getMain().size(), "La main du joueur 2 devrait être vide");
	    
	    // Distribuer les cartes
	    controlJeu.distribuerCartesInitiales();
	    
	    // Vérifier que chaque joueur a 3 cartes
	    assertEquals(3, controlJeu.getJoueur(0).getJoueur().getMain().size(), "Le joueur 1 devrait avoir 3 cartes");
	    assertEquals(3, controlJeu.getJoueur(1).getJoueur().getMain().size(), "Le joueur 2 devrait avoir 3 cartes");
	}
	
	@Test
	@DisplayName("Test des méthodes d'ajout de cartes")
	public void testAjoutDeCartes() {
	    // Initialiser le jeu pour avoir un plateau
	    controlJeu.initialiserJeu();
	    
	    // Créer une carte offensive et stratégique pour les tests
	    CarteOffensive carteOff = new CarteOffensive("Épée", "Une épée tranchante", 2, 1, CarteOffensive.TypeOffensif.ATTAQUE);
	    CarteStrategique carteStrat = new CarteStrategique("Chanson", "Une chanson entrainante", 2, 1);
	    
	    // Joueur 1 actif - ajouter les cartes
	    controlJeu.setJoueurActif(0);
	    controlJeu.ajouterCarteOffensive(carteOff);
	    controlJeu.ajouterCarteStrategique(carteStrat);
	    
	    // Vérifier que les cartes sont ajoutées aux zones du joueur 1
	    assertEquals(1, controlJeu.getControlCartePlateau().getCartesOffensivesJ1().size(), 
	                "La carte offensive devrait être ajoutée à la zone du joueur 1");
	    assertEquals(1, controlJeu.getControlCartePlateau().getCartesStrategiquesJ1().size(), 
	                "La carte stratégique devrait être ajoutée à la zone du joueur 1");
	    
	    // Joueur 2 actif - ajouter d'autres cartes
	    CarteOffensive carteOff2 = new CarteOffensive("Canon", "Un canon puissant", 3, 0, CarteOffensive.TypeOffensif.ATTAQUE);
	    CarteStrategique carteStrat2 = new CarteStrategique("Trésor", "Un coffre d'or", 5, 0, true);
	    
	    controlJeu.setJoueurActif(1);
	    controlJeu.ajouterCarteOffensive(carteOff2);
	    controlJeu.ajouterCarteStrategique(carteStrat2);
	    
	    // Vérifier que les cartes sont ajoutées aux zones du joueur 2
	    assertEquals(1, controlJeu.getControlCartePlateau().getCartesOffensivesJ2().size(), 
	                "La carte offensive devrait être ajoutée à la zone du joueur 2");
	    assertEquals(1, controlJeu.getControlCartePlateau().getCartesStrategiquesJ2().size(), 
	                "La carte stratégique devrait être ajoutée à la zone du joueur 2");
	}
	
	@Test
	@DisplayName("Test des méthodes de gestion du tour de jeu")
	public void testGestionTourDeJeu() {
	    // Vérifier le joueur actif initial
	    assertEquals(0, controlJeu.getJoueurActif(), "Le joueur 1 devrait être actif au début");
	    
	    // Passer au joueur suivant
	    controlJeu.passerAuJoueurSuivant();
	    assertEquals(1, controlJeu.getJoueurActif(), "Le joueur 2 devrait être actif après changement");
	    
	    // Passer au joueur suivant (revient au joueur 1)
	    controlJeu.passerAuJoueurSuivant();
	    assertEquals(0, controlJeu.getJoueurActif(), "Le joueur 1 devrait être actif après second changement");
	    
	    // Définir explicitement le joueur actif
	    controlJeu.setJoueurActif(1);
	    assertEquals(1, controlJeu.getJoueurActif(), "Le joueur 2 devrait être actif après définition explicite");
	    
	    // Tester avec un index invalide (ne devrait pas changer)
	    controlJeu.setJoueurActif(5);
	    assertEquals(1, controlJeu.getJoueurActif(), "Le joueur actif ne devrait pas changer avec un index invalide");
	}
	
	@Test
	@DisplayName("Test de la méthode appliquerEffetsCartes")
	public void testAppliquerEffetsCartes() {
	    // Initialiser le jeu
	    controlJeu.initialiserJeu();
	    
	    // Créer et ajouter des cartes avec des effets mesurables
	    CarteOffensive carteAttaque = new CarteOffensive("Épée", "Une épée tranchante", 2, 0, CarteOffensive.TypeOffensif.ATTAQUE);
	    controlJeu.setJoueurActif(0);
	    controlJeu.ajouterCarteOffensive(carteAttaque);
	    
	    // Enregistrer les points de vie avant application
	    int pvInitialJ2 = controlJeu.getJoueur(1).getPointsDeVie();
	    
	    // Appliquer les effets
	    controlJeu.appliquerEffetsCartes();
	    
	    // Vérifier que les points de vie du joueur 2 ont diminué
	    assertTrue(controlJeu.getJoueur(1).getPointsDeVie() < pvInitialJ2, 
	              "Les points de vie du joueur 2 devraient diminuer après l'application des effets");
	}
	
	@Test
	@DisplayName("Test de la méthode defausserCartesPlateau")
	public void testDefausserCartesPlateau() {
	    // Initialiser le jeu
	    controlJeu.initialiserJeu();
	    
	    // Ajouter des cartes sur le plateau
	    CarteOffensive carteOff1 = new CarteOffensive("Épée", "Une épée tranchante", 2, 1, CarteOffensive.TypeOffensif.ATTAQUE);
	    CarteStrategique carteStrat1 = new CarteStrategique("Chanson", "Une chanson entrainante", 2, 1);
	    
	    controlJeu.setJoueurActif(0);
	    controlJeu.ajouterCarteOffensive(carteOff1);
	    controlJeu.ajouterCarteStrategique(carteStrat1);
	    
	    // Vérifier que les cartes sont sur le plateau
	    assertEquals(1, controlJeu.getControlCartePlateau().getCartesOffensivesJ1().size(), "Une carte offensive devrait être sur le plateau");
	    assertEquals(1, controlJeu.getControlCartePlateau().getCartesStrategiquesJ1().size(), "Une carte stratégique devrait être sur le plateau");
	    
	    // Défausser les cartes
	    controlJeu.defausserCartesPlateau();
	    
	    // Vérifier que les zones sont vides
	    assertEquals(0, controlJeu.getControlCartePlateau().getCartesOffensivesJ1().size(), "La zone offensive devrait être vide");
	    assertEquals(0, controlJeu.getControlCartePlateau().getCartesStrategiquesJ1().size(), "La zone stratégique devrait être vide");
	    
	    // Vérifier que les cartes sont dans la défausse
	    assertTrue(controlJeu.getControlCartePlateau().getDefausse().getCartes().size() >= 2, 
	              "Les cartes devraient être dans la défausse");
	}
	
	@Test
	@DisplayName("Test des méthodes de vérification de fin de partie")
	public void testVerificationFinDePartie() {
	    // Cas où un joueur n'a plus de points de vie
	    controlJeu.getJoueur(0).getJoueur().setVie(0);
	    assertTrue(controlJeu.verifierFinPartie(), "La partie devrait être terminée si un joueur n'a plus de PV");
	    
	    // Réinitialiser les points de vie
	    controlJeu.getJoueur(0).getJoueur().setVie(5);
	    
	    // Cas où un joueur a atteint le maximum de popularité
	    controlJeu.getJoueur(1).gagnerPopularite(5);
	    assertTrue(controlJeu.verifierFinPartie(), "La partie devrait être terminée si un joueur a 5+ de popularité");
	    
	    // Réinitialiser
	    controlJeu = new ControlJeu();
	    controlJeu.setJoueur1(pirate1);
	    controlJeu.setJoueur2(pirate2);
	    
	    // Cas normal
	    assertFalse(controlJeu.verifierFinPartie(), "La partie ne devrait pas être terminée dans des conditions normales");
	}
	
	@Test
	@DisplayName("Test de la méthode determinerVainqueur")
	public void testDeterminerVainqueur() {
	    // Cas où joueur 1 est mort
	    controlJeu.getJoueur(0).getJoueur().setVie(0);
	    assertEquals(controlJeu.getJoueur(1).getJoueur(), controlJeu.determinerVainqueur(), 
	                "Joueur 2 devrait gagner si joueur 1 est mort");
	    
	    // Réinitialiser
	    controlJeu = new ControlJeu();
	    controlJeu.setJoueur1(pirate1);
	    controlJeu.setJoueur2(pirate2);
	    
	    // Cas où joueur 2 est mort
	    controlJeu.getJoueur(1).getJoueur().setVie(0);
	    assertEquals(controlJeu.getJoueur(0).getJoueur(), controlJeu.determinerVainqueur(), 
	                "Joueur 1 devrait gagner si joueur 2 est mort");
	    
	    // Réinitialiser
	    controlJeu = new ControlJeu();
	    controlJeu.setJoueur1(pirate1);
	    controlJeu.setJoueur2(pirate2);
	    
	    // Cas où joueur 1 a max popularité
	    controlJeu.getJoueur(0).gagnerPopularite(5);
	    assertEquals(controlJeu.getJoueur(0).getJoueur(), controlJeu.determinerVainqueur(), 
	                "Joueur 1 devrait gagner s'il a atteint le max de popularité");
	    
	    // Réinitialiser
	    controlJeu = new ControlJeu();
	    controlJeu.setJoueur1(pirate1);
	    controlJeu.setJoueur2(pirate2);
	    
	    // Cas où joueur 2 a max popularité
	    controlJeu.getJoueur(1).gagnerPopularite(5);
	    assertEquals(controlJeu.getJoueur(1).getJoueur(), controlJeu.determinerVainqueur(), 
	                "Joueur 2 devrait gagner s'il a atteint le max de popularité");
	    
	    // Réinitialiser
	    controlJeu = new ControlJeu();
	    controlJeu.setJoueur1(pirate1);
	    controlJeu.setJoueur2(pirate2);
	    
	    // Cas où joueur 1 a plus de points
	    controlJeu.getJoueur(0).getJoueur().gagnerOr(10);  // +5 points
	    controlJeu.getJoueur(0).gagnerPopularite(1);       // +1 point
	    assertEquals(controlJeu.getJoueur(0).getJoueur(), controlJeu.determinerVainqueur(), 
	                "Joueur 1 devrait gagner s'il a plus de points");
	    
	    // Réinitialiser
	    controlJeu = new ControlJeu();
	    controlJeu.setJoueur1(pirate1);
	    controlJeu.setJoueur2(pirate2);
	    
	    // Cas d'égalité
	    assertNull(controlJeu.determinerVainqueur(), "Devrait retourner null en cas d'égalité");
	}
	
	@Test
	@DisplayName("Test des méthodes de jeu de carte")
	public void testJouerCarte() {
	    // Initialiser une carte dans la main du joueur
	    controlJeu.initialiserJeu();
	    
	    // Essayer de jouer une carte à un index invalide
	    assertFalse(controlJeu.jouerCarte(10), "Jouer une carte avec un index invalide devrait retourner false");
	    
	    // Essayer de jouer une carte réelle (ajouter une carte à la main du joueur)
	    CarteOffensive carte = new CarteOffensive("Épée Test", "Une épée pour test", 1, 0, CarteOffensive.TypeOffensif.ATTAQUE);
	    controlJeu.getJoueur(0).getJoueur().ajouterCarte(carte);
	    int indexCarte = controlJeu.getJoueur(0).getJoueur().getMain().size() - 1;
	    
	    // Jouer la carte
	    boolean resultat = controlJeu.jouerCarte(indexCarte);
	    assertTrue(resultat, "Jouer une carte valide devrait retourner true");
	}
	
	@Test
	@DisplayName("Test de la méthode defausserCarte")
	public void testDefausserCarte() {
	    // Initialiser le jeu
	    controlJeu.initialiserJeu();
	    
	    // Essayer de défausser une carte à un index invalide
	    assertFalse(controlJeu.defausserCarte(10), "Défausser une carte avec un index invalide devrait retourner false");
	    
	    // Défausser une carte valide
	    int tailleMainInitiale = controlJeu.getJoueur(0).getJoueur().getMain().size();
	    if (tailleMainInitiale > 0) {
	        boolean resultat = controlJeu.defausserCarte(0);
	        assertTrue(resultat, "Défausser une carte valide devrait retourner true");
	        assertEquals(tailleMainInitiale - 1, controlJeu.getJoueur(0).getJoueur().getMain().size(), 
	                    "La main du joueur devrait avoir une carte de moins");
	    }
	}
	
	@Test
	@DisplayName("Test de la méthode piocherCarte")
	public void testPiocherCarte() {
	    // Initialiser le jeu
	    controlJeu.initialiserJeu();
	    
	    // Vider la main du joueur actif
	    int nombreCartes = controlJeu.getJoueur(0).getJoueur().getMain().size();
	    for (int i = 0; i < nombreCartes; i++) {
	        controlJeu.defausserCarte(0);
	    }
	    
	    // Vérifier que la main est vide
	    assertEquals(0, controlJeu.getJoueur(0).getJoueur().getMain().size(), "La main du joueur devrait être vide");
	    
	    // Piocher une carte
	    Carte cartePiochee = controlJeu.piocherCarte();
	    
	    // Vérifier qu'une carte a été piochée
	    assertNotNull(cartePiochee, "Une carte devrait être piochée");
	    assertEquals(1, controlJeu.getJoueur(0).getJoueur().getMain().size(), 
	                "La main du joueur devrait avoir une carte");
	}
	
	@Test
	@DisplayName("Test de la méthode terminerIteration")
	public void testTerminerIteration() {
	    // Initialiser le jeu
	    controlJeu.initialiserJeu();
	    
	    // Cas où les joueurs ne souhaitent pas continuer
	    assertFalse(controlJeu.terminerIteration(false), "Devrait retourner false si les joueurs ne souhaitent pas continuer");
	    
	    // Cas où la partie est terminée (un joueur n'a plus de PV)
	    controlJeu.getJoueur(0).getJoueur().setVie(0);
	    assertFalse(controlJeu.terminerIteration(true), "Devrait retourner false si la partie est terminée");
	    
	    // Réinitialiser
	    controlJeu = new ControlJeu();
	    controlJeu.setJoueur1(pirate1);
	    controlJeu.setJoueur2(pirate2);
	    controlJeu.initialiserJeu();
	    
	    // Noter le nombre de cartes dans la main des joueurs
	    int cartesJ1 = controlJeu.getJoueur(0).getJoueur().getMain().size();
	    int cartesJ2 = controlJeu.getJoueur(1).getJoueur().getMain().size();
	    
	    // Ajouter des cartes sur le plateau pour vérifier la défausse
	    CarteOffensive carteOff = new CarteOffensive("Test", "Carte de test", 1, 0, CarteOffensive.TypeOffensif.ATTAQUE);
	    controlJeu.setJoueurActif(0);
	    controlJeu.ajouterCarteOffensive(carteOff);
	    
	    // Terminer l'itération
	    boolean resultat = controlJeu.terminerIteration(true);
	    
	    // Vérifier que l'itération continue
	    assertTrue(resultat, "Devrait retourner true si la partie continue");
	    
	    // Vérifier que le plateau est défaussé
	    assertEquals(0, controlJeu.getControlCartePlateau().getCartesOffensivesJ1().size(), "Le plateau devrait être défaussé");
	    
	    // Vérifier que les joueurs ont pioché une carte
	    assertEquals(cartesJ1 + 1, controlJeu.getJoueur(0).getJoueur().getMain().size(), "Joueur 1 devrait avoir pioché une carte");
	    assertEquals(cartesJ2 + 1, controlJeu.getJoueur(1).getJoueur().getMain().size(), "Joueur 2 devrait avoir pioché une carte");
	    
	    // Vérifier que le joueur actif est réinitialisé au joueur 1
	    assertEquals(0, controlJeu.getJoueurActif(), "Le joueur actif devrait être réinitialisé à 0");
	}
	
	@Test
	@DisplayName("Test de la méthode initialiserMainJoueur")
	public void testInitialiserMainJoueur() {
	    // Initialiser le jeu sans distribuer de cartes
	    controlJeu = new ControlJeu();
	    controlJeu.setJoueur1(pirate1);
	    controlJeu.setJoueur2(pirate2);
	    
	    // Vérifier que les mains sont vides
	    assertEquals(0, controlJeu.getJoueur(0).getJoueur().getMain().size(), "La main du joueur 1 devrait être vide");
	    assertEquals(0, controlJeu.getJoueur(1).getJoueur().getMain().size(), "La main du joueur 2 devrait être vide");
	    
	    // Initialiser la main du joueur 1
	    controlJeu.initialiserMainJoueur(0);
	    
	    // Vérifier que le joueur 1 a des cartes
	    assertEquals(3, controlJeu.getJoueur(0).getJoueur().getMain().size(), "Le joueur 1 devrait avoir 3 cartes");
	    assertEquals(0, controlJeu.getJoueur(1).getJoueur().getMain().size(), "La main du joueur 2 devrait rester vide");
	    
	    // Tester avec un index invalide
	    int tailleMainJ1 = controlJeu.getJoueur(0).getJoueur().getMain().size();
	    controlJeu.initialiserMainJoueur(-1); // Index invalide
	    assertEquals(tailleMainJ1, controlJeu.getJoueur(0).getJoueur().getMain().size(), "La main du joueur 1 ne devrait pas changer");
	    
	    // Tester avec un index hors limites
	    controlJeu.initialiserMainJoueur(5); // Index hors limites
	    assertEquals(tailleMainJ1, controlJeu.getJoueur(0).getJoueur().getMain().size(), "La main du joueur 1 ne devrait pas changer");
	}
	
	@Test
	@DisplayName("Test de la méthode verifierVictoire")
	public void testVerifierVictoire() {
	    // Cas normal - pas de vainqueur
	    assertEquals(0, controlJeu.verifierVictoire(), "Devrait retourner 0 s'il n'y a pas de vainqueur");
	    
	    // Cas où joueur 1 gagne
	    controlJeu.getJoueur(0).getJoueur().setVie(0); // Pour déclencher la fin de partie
	    controlJeu.getJoueur(1).getJoueur().setVie(5); // Le joueur 2 est vivant et gagne
	    assertEquals(2, controlJeu.verifierVictoire(), "Devrait retourner 2 si le joueur 2 gagne");
	    
	    // Réinitialiser
	    controlJeu = new ControlJeu();
	    controlJeu.setJoueur1(pirate1);
	    controlJeu.setJoueur2(pirate2);
	    
	    // Cas où joueur 1 gagne par popularité
	    controlJeu.getJoueur(0).gagnerPopularite(5);
	    assertEquals(1, controlJeu.verifierVictoire(), "Devrait retourner 1 si le joueur 1 gagne");
	}
}