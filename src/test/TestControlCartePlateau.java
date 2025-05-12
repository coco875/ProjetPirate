package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import carte.Carte;
import carte.CarteOffensive;
import carte.CarteStrategique;
import controllers.ControlCartePlateau;
import controllers.ControlJoueur;
import controllers.ControlPioche;
import controllers.ControlJeu;
import jeu.ZoneStrategique;
import joueur.Joueur;
import joueur.Pirate;

import java.util.List;

/*
@DisplayName("Tests du contrôleur ControlCartePlateau")
public class TestControlCartePlateau {
    
    private ControlPioche cPioche;
    private Pirate pirate1;
    private Pirate pirate2;
    private Joueur joueur1;
    private Joueur joueur2;
    private ControlJoueur cJoueur1;
    private ControlJoueur cJoueur2;
    private ControlCartePlateau cCartePlateau;
    
    @BeforeEach
    public void initialiser() {
        // Initialisation des objets nécessaires pour les tests
        cPioche = new ControlPioche();
        pirate1 = new Pirate("Jack Sparrow");
        pirate2 = new Pirate("Barbe Noire");
        joueur1 = new Joueur(pirate1);
        joueur2 = new Joueur(pirate2);
        cJoueur1 = new ControlJoueur(joueur1, null, cPioche);
        cJoueur2 = new ControlJoueur(joueur2, null, cPioche);
        cCartePlateau = new ControlCartePlateau(cJoueur1, cJoueur2);
        cJoueur1.setControlCartePlateau(cCartePlateau);
        cJoueur2.setControlCartePlateau(cCartePlateau);
    }
    
    @Test
    @DisplayName("Test de la création d'un contrôleur de carte plateau")
    public void testCreationControlCartePlateau() {
        // Vérification
        assertNotNull(cCartePlateau, "Le contrôleur de carte plateau ne devrait pas être null");
        assertEquals(cJoueur1, cCartePlateau.getJoueurs()[0], "Le contrôleur joueur 1 devrait être correctement assigné");
        assertEquals(cJoueur2, cCartePlateau.getJoueurs()[1], "Le contrôleur joueur 2 devrait être correctement assigné");
    }
    
    @Test
    @DisplayName("Test de l'ajout d'une carte offensive pour le joueur 1")
    public void testAjouterCarteOffensiveJ1() {
        // Création d'une carte offensive
        CarteOffensive carteOffensive = new CarteOffensive("Épée", "Une épée tranchante", 2, 2,
                                                         CarteOffensive.TypeOffensif.ATTAQUE);
        
        // Ajout de la carte pour joueur 1
        cCartePlateau.ajouterCarteOffensiveJ1(carteOffensive);
        assertEquals(1, cCartePlateau.getZoneOffensiveJ1().getCartesOffensives().size(), 
                    "La zone offensive J1 devrait contenir 1 carte");
        assertTrue(cCartePlateau.getZoneOffensiveJ1().getCartesOffensives().contains(carteOffensive),
                  "La zone offensive J1 devrait contenir la carte ajoutée");
        assertEquals(0, cCartePlateau.getZoneOffensiveJ2().getCartesOffensives().size(),
                    "La zone offensive J2 ne devrait pas contenir de carte");
    }
    
    @Test
    @DisplayName("Test de l'ajout d'une carte stratégique pour le joueur 1")
    public void testAjouterCarteStrategique() {
        // Création d'une carte stratégique
        CarteStrategique carteStrat = new CarteStrategique("Chanson", "Une chanson entrainante", 2, 2);
        
        // Ajout de la carte pour joueur 1
        cCartePlateau.ajouterCarteStrategiqueJ1(carteStrat);
        assertEquals(1, cCartePlateau.getZoneStrategiqueJ1().getCartesStrategiques().size(),
                    "La zone stratégique J1 devrait contenir 1 carte");
        assertTrue(cCartePlateau.getZoneStrategiqueJ1().getCartesStrategiques().contains(carteStrat),
                  "La zone stratégique J1 devrait contenir la carte ajoutée");
        assertEquals(0, cCartePlateau.getZoneStrategiqueJ2().getCartesStrategiques().size(),
                    "La zone stratégique J2 ne devrait pas contenir de carte");
    }
    
    @Test
    @DisplayName("Test des effets des cartes offensives")
    public void testAppliquerEffetsCartesOffensives() {
        // Valeurs initiales
        int vieInitialeJ1 = joueur1.getPointsDeVie();
        int vieInitialeJ2 = joueur2.getPointsDeVie();
        int orInitialJ1 = joueur1.getOr();
        int orInitialJ2 = joueur2.getOr();
        
        // Ajout de cartes offensives
        // J1 joue : Attaque (2 dégâts infligés, 1 subi), Soin (3 PV)
        CarteOffensive attaqueJ1 = new CarteOffensive("Canon", "", 2, 1, CarteOffensive.TypeOffensif.ATTAQUE);
        CarteOffensive soinJ1 = new CarteOffensive("Potion", "", 3); // Constructeur Soin
        CarteOffensive attaqueExtraJ1 = new CarteOffensive("Vol de Butin", "", 1, 0, CarteOffensive.TypeOffensif.ATTAQUE);
        cCartePlateau.ajouterCarteOffensiveJ1(attaqueJ1);
        cCartePlateau.ajouterCarteOffensiveJ1(soinJ1);
        cCartePlateau.ajouterCarteOffensiveJ1(attaqueExtraJ1);
        
        // J2 joue : Attaque (1 dégât infligé, 0 subi)
        CarteOffensive attaqueJ2 = new CarteOffensive("Mousquet", "", 1, 0, CarteOffensive.TypeOffensif.ATTAQUE);
        cCartePlateau.ajouterCarteOffensiveJ2(attaqueJ2);
        
        // Application des effets
        cCartePlateau.appliquerEffetsCartesOffensives();
        
        // Vérifications des dégâts et soins
        // J1 subit 1 (attaqueJ2) + 1 (attaqueJ1 self damage) = 2 dégâts, mais reçoit 3 soins
        assertEquals(Math.min(5, vieInitialeJ1 - 2 + 3), joueur1.getPointsDeVie(), "PV J1 incorrects");
        // J2 subit 2 (attaqueJ1) + 1 (attaqueExtraJ1) = 3 dégâts
        assertEquals(vieInitialeJ2 - 3, joueur2.getPointsDeVie(), "PV J2 incorrects");
        // L'or ne change pas
        assertEquals(orInitialJ1, joueur1.getOr(), "Or J1 incorrect");
        assertEquals(orInitialJ2, joueur2.getOr(), "Or J2 incorrect");
    }
    
    @Test
    @DisplayName("Test des effets des cartes stratégiques")
    public void testAppliquerEffetsCartesStrategiques() {
        // Valeurs initiales
        int popInitialeJ1 = joueur1.getPopularite();
        int popInitialeJ2 = joueur2.getPopularite();
        int orInitialJ1 = joueur1.getOr();
        int orInitialJ2 = joueur2.getOr();
        int vieInitialeJ1 = joueur1.getPointsDeVie();
        int vieInitialeJ2 = joueur2.getPointsDeVie();

        // Ajout de cartes stratégiques
        // J1 joue : Popularité (2 pop gagnée, 1 dégât subi), Trésor (10 or gagné)
        CarteStrategique popJ1 = new CarteStrategique("Chanson", "", 2, 1); // Constructeur Popularité
        CarteStrategique tresorJ1 = new CarteStrategique("Coffre", "", 10, true); // Constructeur Trésor - suppression du paramètre orPerdu
        cCartePlateau.ajouterCarteStrategiqueJ1(popJ1);
        cCartePlateau.ajouterCarteStrategiqueJ1(tresorJ1);
        
        // J2 joue : Popularité (1 pop gagnée, 0 dégât subi)
        CarteStrategique popJ2 = new CarteStrategique("Danse", "", 1, 0); // Constructeur Popularité
        cCartePlateau.ajouterCarteStrategiqueJ2(popJ2);
        
        // Application des effets
        cCartePlateau.appliquerEffetsCartesStrategiques();
        
        // Vérifications J1
        // Popularité gagnée: 2 (popJ1)
        assertEquals(popInitialeJ1 + 2, joueur1.getPopularite(), "Popularité J1 incorrecte");
        // Dégâts subis: 1 (popJ1)
        assertEquals(vieInitialeJ1 - 1, joueur1.getPointsDeVie(), "PV J1 incorrects");
        // Or gagné: 10 (tresorJ1)
        assertEquals(orInitialJ1 + 10, joueur1.getOr(), "Or J1 incorrect");

        // Vérifications J2
        // Popularité gagnée: 1 (popJ2)
        assertEquals(popInitialeJ2 + 1, joueur2.getPopularite(), "Popularité J2 incorrecte");
        // Dégâts subis: 0 (popJ2)
        assertEquals(vieInitialeJ2, joueur2.getPointsDeVie(), "PV J2 incorrects");
        // Or inchangé
        assertEquals(orInitialJ2, joueur2.getOr(), "Or J2 incorrect");
    }
    
    @Test
    @DisplayName("Test des cartes offensives avec différents types")
    public void testCartesOffensivesDifferentsTypes() {
        // Valeurs initiales
        int vieInitialeJ1 = joueur1.getPointsDeVie();
        int vieInitialeJ2 = joueur2.getPointsDeVie();
        
        // J1 joue une carte de soin
        CarteOffensive soin = new CarteOffensive("Potion", "Une potion de soin", 3) {
            @Override
            public CarteOffensive.TypeOffensif getTypeOffensif() {
                return CarteOffensive.TypeOffensif.SOIN;
            }
        };
        cCartePlateau.ajouterCarteOffensiveJ1(soin);
        
        // J2 joue une carte avec un type inexistant mais pas null
        // Pour éviter NullPointerException, on utilise un type connu
        CarteOffensive carteSpeciale = new CarteOffensive("Carte Spéciale", "Une carte avec type spécial", 1, 0, 
                                                         CarteOffensive.TypeOffensif.ATTAQUE) {
            @Override
            public CarteOffensive.TypeOffensif getTypeOffensif() {
                // Utiliser un type non reconnu dans le switch mais pas null
                return CarteOffensive.TypeOffensif.ATTAQUE;
            }
            
            @Override
            public EffetCarte effetCarte() {
                EffetCarte effet = super.effetCarte();
                // On simule le comportement d'une carte spéciale sans avoir d'impact
                return effet;
            }
        };
        cCartePlateau.ajouterCarteOffensiveJ2(carteSpeciale);
        
        // Application des effets - aucune exception ne devrait être levée
        cCartePlateau.appliquerEffetsCartesOffensives();
        
        // Le joueur 1 devrait avoir gagné des points de vie grâce au soin
        assertTrue(joueur1.getPointsDeVie() >= vieInitialeJ1, "J1 devrait avoir au moins autant de PV qu'au début");
    }
    
    @Test
    @DisplayName("Test des cartes stratégiques avec trésor perdu")
    public void testCartesStrategiquesAvecTresorPerdu() {
        // Valeurs initiales
        int orInitialJ1 = joueur1.getOr();
        int orInitialJ2 = joueur2.getOr();
        
        // Configurer un trésor avec perte d'or pour J1
        // Utilisation d'une classe anonyme pour surcharger effetCarte et simuler la perte d'or
        CarteStrategique tresorPerte = new CarteStrategique("Coffre Maudit", "Un coffre qui fait perdre de l'or", 0, true) {
            @Override
            public Carte.EffetCarte effetCarte() {
                Carte.EffetCarte effet = new Carte.EffetCarte();
                effet.orGagne = 0;
                effet.estTresor = true;
                effet.orPerdu = 2; // Simuler la perte d'or via orPerdu
                return effet;
            }
        };
        cCartePlateau.ajouterCarteStrategiqueJ1(tresorPerte);
        
        // Configurer un trésor avec gain d'or pour J2
        CarteStrategique tresorGain = new CarteStrategique("Coffre d'Or", "Un coffre qui donne de l'or", 5, true) {
            @Override
            public Carte.EffetCarte effetCarte() {
                Carte.EffetCarte effet = new Carte.EffetCarte();
                effet.orGagne = 5;
                effet.estTresor = true;
                return effet;
            }
        };
        cCartePlateau.ajouterCarteStrategiqueJ2(tresorGain);
        
        // Application des effets
        cCartePlateau.appliquerEffetsCartesStrategiques();
        
        // Vérifier que J1 a perdu de l'or
        assertEquals(Math.max(0, orInitialJ1 - 2), joueur1.getOr(), "J1 devrait avoir perdu 2 or");
        
        // Vérifier que J2 a gagné de l'or
        assertEquals(orInitialJ2 + 5, joueur2.getOr(), "J2 devrait avoir gagné 5 or");
    }
    
    @Test
    @DisplayName("Test de la défausse des cartes du plateau")
    public void testDefausserCartesPlateau() {
        // Ajout de cartes dans les différentes zones
        CarteOffensive carteOff1 = new CarteOffensive("Épée", "Une épée tranchante", 2, 2, CarteOffensive.TypeOffensif.ATTAQUE);
        CarteOffensive carteOff2 = new CarteOffensive("Mousquet", "Un mousquet puissant", 3, 0, CarteOffensive.TypeOffensif.ATTAQUE);
        CarteStrategique carteStrat1 = new CarteStrategique("Chanson", "Une chanson entraînante", 2, 2);
        CarteStrategique carteStrat2 = new CarteStrategique("Trésor", "Un coffre d'or", 5, true); // Suppression du paramètre orPerdu
        
        cCartePlateau.ajouterCarteOffensiveJ1(carteOff1);
        cCartePlateau.ajouterCarteOffensiveJ2(carteOff2);
        cCartePlateau.ajouterCarteStrategiqueJ1(carteStrat1);
        cCartePlateau.ajouterCarteStrategiqueJ2(carteStrat2);
        
        // Vérifier que les cartes ont bien été ajoutées
        assertEquals(1, cCartePlateau.getZoneOffensiveJ1().getCartesOffensives().size(), "Zone offensive J1 devrait contenir 1 carte");
        assertEquals(1, cCartePlateau.getZoneOffensiveJ2().getCartesOffensives().size(), "Zone offensive J2 devrait contenir 1 carte");
        assertEquals(1, cCartePlateau.getZoneStrategiqueJ1().getCartesStrategiques().size(), "Zone stratégique J1 devrait contenir 1 carte");
        assertEquals(1, cCartePlateau.getZoneStrategiqueJ2().getCartesStrategiques().size(), "Zone stratégique J2 devrait contenir 1 carte");
        
        // Défausser les cartes
        cCartePlateau.defausserCartesPlateau();
        
        // Vérifier que les zones sont maintenant vides
        assertEquals(0, cCartePlateau.getZoneOffensiveJ1().getCartesOffensives().size(), "Zone offensive J1 devrait être vide");
        assertEquals(0, cCartePlateau.getZoneOffensiveJ2().getCartesOffensives().size(), "Zone offensive J2 devrait être vide");
        assertEquals(0, cCartePlateau.getZoneStrategiqueJ1().getCartesStrategiques().size(), "Zone stratégique J1 devrait être vide");
        assertEquals(0, cCartePlateau.getZoneStrategiqueJ2().getCartesStrategiques().size(), "Zone stratégique J2 devrait être vide");
        
        // Vérifier que les cartes sont dans la défausse (4 au total)
        assertEquals(4, cCartePlateau.getDefausse().getCartes().size(), "La défausse devrait contenir 4 cartes");
        assertTrue(cCartePlateau.getDefausse().getCartes().contains(carteOff1), "La défausse devrait contenir carteOff1");
        assertTrue(cCartePlateau.getDefausse().getCartes().contains(carteOff2), "La défausse devrait contenir carteOff2");
        assertTrue(cCartePlateau.getDefausse().getCartes().contains(carteStrat1), "La défausse devrait contenir carteStrat1");
        assertTrue(cCartePlateau.getDefausse().getCartes().contains(carteStrat2), "La défausse devrait contenir carteStrat2");
    }
    
    @Test
    @DisplayName("Test de la méthode setJoueurs")
    public void testSetJoueurs() {
        // Créer de nouveaux contrôleurs de joueurs
        Joueur nouveauJoueur1 = new Joueur(new Pirate("Nouveau Pirate 1"));
        Joueur nouveauJoueur2 = new Joueur(new Pirate("Nouveau Pirate 2"));
        ControlJoueur nouveauCJoueur1 = new ControlJoueur(nouveauJoueur1, null, cPioche);
        ControlJoueur nouveauCJoueur2 = new ControlJoueur(nouveauJoueur2, null, cPioche);
        
        // Mettre à jour les joueurs dans le contrôleur de plateau
        cCartePlateau.setJoueurs(nouveauCJoueur1, nouveauCJoueur2);
        
        // Vérifier que les joueurs ont été mis à jour
        assertEquals(nouveauCJoueur1, cCartePlateau.getJoueurs()[0], "Le nouveau contrôleur joueur 1 devrait être assigné");
        assertEquals(nouveauCJoueur2, cCartePlateau.getJoueurs()[1], "Le nouveau contrôleur joueur 2 devrait être assigné");
    }
    
    @Test
    @DisplayName("Test des méthodes getter de listes de cartes")
    public void testGetters() {
        // Ajouter quelques cartes
        CarteOffensive carteOff1 = new CarteOffensive("Épée", "Une épée tranchante", 2, 2, CarteOffensive.TypeOffensif.ATTAQUE);
        CarteStrategique carteStrat1 = new CarteStrategique("Chanson", "Une chanson entraînante", 2, 2);
        
        cCartePlateau.ajouterCarteOffensiveJ1(carteOff1);
        cCartePlateau.ajouterCarteStrategiqueJ1(carteStrat1);
        
        // Tester getCartesOffensivesJ1 et getCartesStrategiquesJ1
        List<CarteOffensive> cartesOffensivesJ1 = cCartePlateau.getCartesOffensivesJ1();
        List<CarteStrategique> cartesStrategiquesJ1 = cCartePlateau.getCartesStrategiquesJ1();
        
        assertEquals(1, cartesOffensivesJ1.size(), "devrait renvoyer 1 carte offensive");
        assertEquals(carteOff1, cartesOffensivesJ1.get(0), "devrait renvoyer la carte offensive ajoutée");
        assertEquals(1, cartesStrategiquesJ1.size(), "devrait renvoyer 1 carte stratégique");
        assertEquals(carteStrat1, cartesStrategiquesJ1.get(0), "devrait renvoyer la carte stratégique ajoutée");
        
        // Tester getCartesOffensivesJ2 et getCartesStrategiquesJ2
        CarteOffensive carteOff2 = new CarteOffensive("Mousquet", "Un mousquet puissant", 3, 0, CarteOffensive.TypeOffensif.ATTAQUE);
        CarteStrategique carteStrat2 = new CarteStrategique("Trésor", "Un coffre d'or", 5, true); // Suppression du paramètre orPerdu
        
        cCartePlateau.ajouterCarteOffensiveJ2(carteOff2);
        cCartePlateau.ajouterCarteStrategiqueJ2(carteStrat2);
        
        List<CarteOffensive> cartesOffensivesJ2 = cCartePlateau.getCartesOffensivesJ2();
        List<CarteStrategique> cartesStrategiquesJ2 = cCartePlateau.getCartesStrategiquesJ2();
        
        assertEquals(1, cartesOffensivesJ2.size(), "devrait renvoyer 1 carte offensive pour J2");
        assertEquals(carteOff2, cartesOffensivesJ2.get(0), "devrait renvoyer la carte offensive ajoutée pour J2");
        assertEquals(1, cartesStrategiquesJ2.size(), "devrait renvoyer 1 carte stratégique pour J2");
        assertEquals(carteStrat2, cartesStrategiquesJ2.get(0), "devrait renvoyer la carte stratégique ajoutée pour J2");
    }
    
    @Test
    @DisplayName("Test de defausserCartes avec objet non-Carte")
    public void testDefausserCartesObjetNonCarte() {
        // Créer une liste avec un objet qui n'est pas une Carte
        class ObjetNonCarte {}
        List<Object> listeObjets = List.of(new ObjetNonCarte());
        
        // Vérifier que la méthode ne lève pas d'exception
        assertDoesNotThrow(() -> {
            // Utiliser la réflexion pour accéder à la méthode privée defausserCartes
            java.lang.reflect.Method method = ControlCartePlateau.class.getDeclaredMethod("defausserCartes", List.class);
            method.setAccessible(true);
            method.invoke(cCartePlateau, listeObjets);
        }, "La méthode defausserCartes ne devrait pas lever d'exception avec un objet non-Carte");
        
        // La défausse devrait toujours être vide
        assertEquals(0, cCartePlateau.getDefausse().getCartes().size(), "La défausse devrait être vide");
    }

    @Test
    @DisplayName("Test des soins pour le joueur 2 et des cartes avec des types particuliers")
    public void testSoinsJoueur2EtTypesParticuliers() {
        // Valeurs initiales
        int vieInitialeJ2 = joueur2.getPointsDeVie();
        int orInitialJ2 = joueur2.getOr();
        
        // Réduire les PV du joueur 2 pour pouvoir tester le soin
        joueur2.setVie(3);
        vieInitialeJ2 = joueur2.getPointsDeVie();
        assertEquals(3, vieInitialeJ2, "Les PV du joueur 2 devraient être à 3");
        
        // Créer une carte de soin pour le joueur 2
        CarteOffensive soinJ2 = new CarteOffensive("Potion J2", "Une potion pour J2", 3) {
            @Override
            public CarteOffensive.TypeOffensif getTypeOffensif() {
                return CarteOffensive.TypeOffensif.SOIN;
            }
            
            @Override
            public EffetCarte effetCarte() {
                // Créer directement un effet de soin
                EffetCarte effet = new EffetCarte();
                effet.vieGagnee = 3;
                return effet;
            }
        };
        cCartePlateau.ajouterCarteOffensiveJ2(soinJ2);
        
        // Créer une carte offensive sans effet spécial pour le joueur 1
        CarteOffensive carteTypeSpecial = new CarteOffensive("Type Spécial", "Une carte sans effet", 0, 0, 
                                                CarteOffensive.TypeOffensif.ATTAQUE);
        cCartePlateau.ajouterCarteOffensiveJ1(carteTypeSpecial);
        
        // Créer une carte stratégique qui fait perdre de l'or au joueur 2
        // On utilise une classe anonyme qui surcharge effetCarte pour simuler la perte d'or
        CarteStrategique tresorNegJ2 = new CarteStrategique("Coffre Maudit J2", "Un coffre qui fait perdre de l'or au J2", 0, true) {
            @Override
            public EffetCarte effetCarte() {
                // Créer un effet simple avec orPerdu
                EffetCarte effet = new EffetCarte();
                effet.estTresor = true;
                effet.orPerdu = 2; // Simuler la perte d'or via orPerdu
                return effet;
            }
            
            @Override
            public CarteStrategique.TypeStrategique getTypeStrategique() {
                return CarteStrategique.TypeStrategique.TRESOR;
            }
        };
        cCartePlateau.ajouterCarteStrategiqueJ2(tresorNegJ2);
        
        // Appliquer les effets
        cCartePlateau.appliquerEffetsCartesOffensives();
        cCartePlateau.appliquerEffetsCartesStrategiques();
        
        // Vérifier que le joueur 2 a bien reçu des soins
        assertTrue(joueur2.getPointsDeVie() > vieInitialeJ2, "Joueur 2 aurait dû recevoir des soins");
        
        // Les points de vie sont probablement limités à un maximum (par défaut 5)
        // Donc on vérifie que les PV sont soit au maximum, soit égaux à vieInitiale + 3
        int pvMaximum = 5; // Valeur maximale des points de vie
        assertEquals(Math.min(pvMaximum, vieInitialeJ2 + 3), joueur2.getPointsDeVie(), 
                    "Joueur 2 aurait dû gagner 3 PV, limité au maximum de " + pvMaximum);
        
        // Vérifier que joueur 2 a perdu de l'or
        assertEquals(Math.max(0, orInitialJ2 - 2), joueur2.getOr(), "Joueur 2 aurait dû perdre 2 or");
    }
}*/