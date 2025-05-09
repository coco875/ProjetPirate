package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.*;
import carte.*;
import joueur.*;
import java.util.List;

/**
 * Tests unitaires pour la classe ControlMarche
 */
public class TestControlMarcheJUnit {
    
    private ControlMarche controlMarche;
    private ControlJoueur controlJoueur1;
    private ControlJoueur controlJoueur2;
    private ControlPioche controlPioche;
    private ControlJeu controlJeu;
    private Joueur joueur1;
    private Joueur joueur2;
    
    @BeforeEach
    public void setUp() {
        // Création des objets nécessaires pour les tests
        controlPioche = new ControlPioche();
        
        // Création des pirates et joueurs
        Pirate pirate1 = new Pirate("Barbe Noire");
        Pirate pirate2 = new Pirate("Jack Sparrow");
        joueur1 = new Joueur("Joueur1", pirate1);
        joueur2 = new Joueur("Joueur2", pirate2);
        
        // Attribution d'or initial pour les tests
        joueur1.setOr(20);
        joueur2.setOr(20);
        
        // Création du contrôleur de jeu
        controlJeu = new ControlJeu();
        controlJeu.setJoueur1("Joueur1", pirate1);
        controlJeu.setJoueur2("Joueur2", pirate2);
        
        // Création des contrôleurs de joueurs
        controlJoueur1 = new ControlJoueur(joueur1, null, controlPioche);
        controlJoueur2 = new ControlJoueur(joueur2, null, controlPioche);
        
        // Création du contrôleur de plateau de cartes et mise à jour des contrôleurs de joueurs
        ControlCartePlateau controlCartePlateau = new ControlCartePlateau(controlJoueur1, controlJoueur2);
        controlJoueur1.setControlCartePlateau(controlCartePlateau);
        controlJoueur2.setControlCartePlateau(controlCartePlateau);
        
        // Création du contrôleur de marché
        controlMarche = new ControlMarche(controlJoueur1, controlJoueur2, controlPioche, controlJeu);
    }
    
    @Test
    public void testGetCartesDisponibles() {
        // Vérifier que getCartesDisponibles retourne une liste non null et non vide
        List<Carte> cartes = controlMarche.getCartesDisponibles();
        assertNotNull(cartes, "La liste des cartes disponibles ne devrait pas être null");
        assertFalse(cartes.isEmpty(), "La liste des cartes disponibles ne devrait pas être vide");
    }
    
    @Test
    public void testAcheterCarteSucces() {
        // Configurer le joueur actif
        controlJeu.setJoueurActif(0); // Joueur 1 est actif
        
        // Assurer que le joueur a suffisamment d'or
        int orInitial = joueur1.getOr();
        
        // Récupérer la première carte et son coût
        Carte premiereCarte = controlMarche.getCartesDisponibles().get(0);
        int coutCarte = premiereCarte.getCout();
        
        // Assurer que le joueur a assez d'or
        if (orInitial < coutCarte) {
            joueur1.setOr(coutCarte);
            orInitial = coutCarte;
        }
        
        // Vérifier que l'achat réussit
        assertTrue(controlMarche.acheterCarte(0), "L'achat de la carte devrait réussir");
        
        // Vérifier que l'or a été déduit
        assertEquals(orInitial - coutCarte, joueur1.getOr(), "L'or devrait être réduit après achat");
        
        // Vérifier que la carte est maintenant dans la main du joueur
        List<Carte> mainJoueur = joueur1.getMain();
        boolean carteEnMain = false;
        for (Carte c : mainJoueur) {
            if (c.getNomCarte().equals(premiereCarte.getNomCarte())) {
                carteEnMain = true;
                break;
            }
        }
        assertTrue(carteEnMain, "La carte achetée devrait être dans la main du joueur");
    }
    
    @Test
    public void testAcheterCartePasAssezOr() {
        // Configurer le joueur actif
        controlJeu.setJoueurActif(0); // Joueur 1 est actif
        
        // Récupérer la première carte et son coût
        Carte premiereCarte = controlMarche.getCartesDisponibles().get(0);
        int coutCarte = premiereCarte.getCout();
        
        // Assurer que le joueur n'a pas assez d'or
        joueur1.setOr(coutCarte - 1);
        
        // Vérifier que l'achat échoue
        assertFalse(controlMarche.acheterCarte(0), "L'achat devrait échouer si le joueur n'a pas assez d'or");
        
        // Vérifier que l'or n'a pas été déduit
        assertEquals(coutCarte - 1, joueur1.getOr(), "L'or ne devrait pas être modifié après un achat échoué");
    }
    
    @Test
    public void testAcheterCarteIndexInvalide() {
        // Configurer le joueur actif
        controlJeu.setJoueurActif(0); // Joueur 1 est actif
        
        // Essayer d'acheter avec un index négatif
        assertFalse(controlMarche.acheterCarte(-1), "L'achat avec un index négatif devrait échouer");
        
        // Essayer d'acheter avec un index trop grand
        int indexTropGrand = controlMarche.getCartesDisponibles().size() + 10;
        assertFalse(controlMarche.acheterCarte(indexTropGrand), "L'achat avec un index trop grand devrait échouer");
    }
    
    @Test
    public void testRafraichirMarche() {
        // Noter le contenu initial du marché
        List<Carte> cartesInitiales = controlMarche.getCartesDisponibles();
        String[] nomsCartesInitiales = new String[cartesInitiales.size()];
        for (int i = 0; i < cartesInitiales.size(); i++) {
            nomsCartesInitiales[i] = cartesInitiales.get(i).getNomCarte();
        }
        
        // Rafraîchir le marché
        controlMarche.rafraichirMarche();
        
        // Vérifier que le marché n'est pas vide après rafraîchissement
        List<Carte> nouvellesCartes = controlMarche.getCartesDisponibles();
        assertFalse(nouvellesCartes.isEmpty(), "Le marché ne devrait pas être vide après rafraîchissement");
    }
    
    @Test
    public void testVendreCarteSucces() {
        // Ajouter une carte dans la main du joueur 1
        CarteAttaque carteTest = new CarteAttaque("Épée", "Une épée tranchante", 4, 2);
        joueur1.ajouterCarte(carteTest);
        
        // Noter l'or initial
        int orInitial = joueur1.getOr();
        
        // Calculer la valeur de vente attendue (moitié de la valeur, minimum 1)
        int valeurVente = Math.max(carteTest.getValeur() / 2, 1);
        
        // Vendre la carte
        assertTrue(controlMarche.vendreCarte(1, 0), "La vente de carte devrait réussir");
        
        // Vérifier que l'or a été ajouté
        assertEquals(orInitial + valeurVente, joueur1.getOr(), "L'or devrait être augmenté après la vente");
        
        // Vérifier que la carte n'est plus dans la main
        List<Carte> mainJoueur = joueur1.getMain();
        boolean carteToujours = false;
        for (Carte c : mainJoueur) {
            if (c.equals(carteTest)) {
                carteToujours = true;
                break;
            }
        }
        assertFalse(carteToujours, "La carte vendue ne devrait plus être dans la main du joueur");
    }
    
    @Test
    public void testVendreCarteIndicesInvalides() {
        // Essayer de vendre avec un joueur invalide
        assertFalse(controlMarche.vendreCarte(0, 0), "La vente avec un joueur invalide devrait échouer");
        assertFalse(controlMarche.vendreCarte(3, 0), "La vente avec un joueur invalide devrait échouer");
        
        // Essayer de vendre avec un index de carte invalide
        joueur1.ajouterCarte(new CarteAttaque("Test", "Carte de test", 1, 0));
        assertFalse(controlMarche.vendreCarte(1, -1), "La vente avec un index négatif devrait échouer");
        assertFalse(controlMarche.vendreCarte(1, 10), "La vente avec un index trop grand devrait échouer");
    }
    
    @Test
    public void testVendreCarteMinimumOr() {
        // Ajouter une carte avec une valeur de 0 dans la main du joueur
        CarteAttaque carteTest = new CarteAttaque("Carte Faible", "Une carte de valeur nulle", 0, 0);
        joueur1.ajouterCarte(carteTest);
        
        // Noter l'or initial
        int orInitial = joueur1.getOr();
        
        // Vendre la carte
        assertTrue(controlMarche.vendreCarte(1, 0), "La vente de carte devrait réussir même avec une valeur nulle");
        
        // Vérifier que l'or a été augmenté d'au moins 1
        assertEquals(orInitial + 1, joueur1.getOr(), "L'or devrait être augmenté d'au moins 1 après la vente");
    }
}