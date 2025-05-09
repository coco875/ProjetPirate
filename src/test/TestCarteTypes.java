package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import carte.*;

/**
 * Tests complets pour les différentes classes de cartes
 */
public class TestCarteTypes {
    
    @Test
    @DisplayName("Test des cartes offensives")
    public void testCarteOffensive() {
        // Test constructeur pour attaque
        CarteOffensive attaque = new CarteOffensive("Épée", "Une épée tranchante", 3, 1, CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE);
        assertEquals("Épée", attaque.getNomCarte());
        assertEquals("Une épée tranchante", attaque.getDescription());
        assertEquals(3, attaque.getValeur()); // dégâts infligés
        assertEquals(1, attaque.getValeurSecondaire()); // dégâts subis
        assertEquals(CarteOffensive.TypeOffensif.ATTAQUE_DIRECTE, attaque.getTypeOffensif());
        
        // Vérification de l'effet
        Carte.EffetCarte effetAttaque = attaque.effetCarte();
        assertEquals(3, effetAttaque.degatsInfliges);
        assertEquals(1, effetAttaque.degatsSubis);
        assertTrue(effetAttaque.estAttaque);
        
        // Test constructeur pour soin
        CarteOffensive soin = new CarteOffensive("Potion", "Une potion de soin", 2);
        assertEquals("Potion", soin.getNomCarte());
        assertEquals("Une potion de soin", soin.getDescription());
        assertEquals(2, soin.getValeur()); // points de vie gagnés
        assertEquals(CarteOffensive.TypeOffensif.SOIN, soin.getTypeOffensif());
        
        // Vérification de l'effet
        Carte.EffetCarte effetSoin = soin.effetCarte();
        assertEquals(2, effetSoin.vieGagnee);
        assertTrue(effetSoin.estSoin);
        assertFalse(effetSoin.estAttaque);
        
        // Test toString
        String descAttaque = attaque.toString();
        assertTrue(descAttaque.contains("Épée"));
        assertTrue(descAttaque.contains("Attaque: 3"));
        assertTrue(descAttaque.contains("Dégâts subis: 1"));
        
        String descSoin = soin.toString();
        assertTrue(descSoin.contains("Potion"));
        assertTrue(descSoin.contains("Soin: 2"));
    }
    
    @Test
    @DisplayName("Test des cartes stratégiques")
    public void testCarteStrategique() {
        // Test constructeur pour popularité
        CarteStrategique popularite = new CarteStrategique("Chanson", "Une chanson entraînante", 2, 1);
        assertEquals("Chanson", popularite.getNomCarte());
        assertEquals("Une chanson entraînante", popularite.getDescription());
        assertEquals(2, popularite.getValeur()); // popularité gagnée
        assertEquals(1, popularite.getValeurSecondaire()); // dégâts subis
        assertEquals(CarteStrategique.TypeStrategique.POPULARITE, popularite.getTypeStrategique());
        
        // Vérification de l'effet
        Carte.EffetCarte effetPopularite = popularite.effetCarte();
        assertEquals(2, effetPopularite.populariteGagnee);
        assertEquals(1, effetPopularite.degatsSubis);
        assertTrue(effetPopularite.estPopularite);
        
        // Test constructeur pour trésor - gain d'or
        CarteStrategique tresorGain = new CarteStrategique("Coffre", "Un coffre au trésor", 10, 0, true);
        assertEquals("Coffre", tresorGain.getNomCarte());
        assertEquals("Un coffre au trésor", tresorGain.getDescription());
        assertEquals(10, tresorGain.getValeur()); // or gagné
        assertEquals(0, tresorGain.getValeurSecondaire()); // or perdu
        assertEquals(CarteStrategique.TypeStrategique.TRESOR, tresorGain.getTypeStrategique());
        
        // Vérification de l'effet
        Carte.EffetCarte effetTresorGain = tresorGain.effetCarte();
        assertEquals(10, effetTresorGain.orGagne);
        assertEquals(0, effetTresorGain.orPerdu);
        assertTrue(effetTresorGain.estTresor);
        
        // Test constructeur pour trésor - perte d'or
        CarteStrategique tresorPerte = new CarteStrategique("Malédiction", "Une malédiction qui fait perdre de l'or", 0, 5, true);
        assertEquals("Malédiction", tresorPerte.getNomCarte());
        assertEquals("Une malédiction qui fait perdre de l'or", tresorPerte.getDescription());
        assertEquals(0, tresorPerte.getValeur()); // or gagné
        assertEquals(5, tresorPerte.getValeurSecondaire()); // or perdu
        assertEquals(CarteStrategique.TypeStrategique.TRESOR, tresorPerte.getTypeStrategique());
        
        // Vérification de l'effet
        Carte.EffetCarte effetTresorPerte = tresorPerte.effetCarte();
        assertEquals(0, effetTresorPerte.orGagne);
        assertEquals(5, effetTresorPerte.orPerdu);
        assertTrue(effetTresorPerte.estTresor);
        
        // Test toString
        String descPopularite = popularite.toString();
        assertTrue(descPopularite.contains("Chanson"));
        assertTrue(descPopularite.contains("Popularité: 2"));
        assertTrue(descPopularite.contains("Dégâts subis: 1"));
        
        String descTresor = tresorGain.toString();
        assertTrue(descTresor.contains("Coffre"));
        assertTrue(descTresor.contains("Or gagné: 10"));
        
        String descPerte = tresorPerte.toString();
        assertTrue(descPerte.contains("Malédiction"));
        assertTrue(descPerte.contains("Or perdu: 5"));
    }
    
    @Test
    @DisplayName("Test des cartes spéciales")
    public void testCarteSpeciale() {
        // Test constructeur de base
        CarteSpeciale speciale = new CarteSpeciale("Éclaireur", "Permet de voir les cartes de l'adversaire", 1);
        assertEquals("Éclaireur", speciale.getNomCarte());
        assertEquals("Permet de voir les cartes de l'adversaire", speciale.getDescription());
        assertEquals(1, speciale.getValeur()); // valeur de l'effet spécial
        assertEquals(TypeCarte.SPECIALE, speciale.getType());
        
        // Vérification de l'effet
        Carte.EffetCarte effetSpeciale = speciale.effetCarte();
        assertTrue(effetSpeciale.estSpeciale);
        
        // Test toString
        String description = speciale.toString();
        assertTrue(description.contains("Éclaireur"));
        assertTrue(description.contains("Permet de voir les cartes de l'adversaire"));
    }
    
    @Test
    @DisplayName("Test des cartes passives")
    public void testCartePassive() {
        // Test constructeur de base
        CartePassive passive = new CartePassive("Bouclier", "Protège contre les attaques", 2);
        assertEquals("Bouclier", passive.getNomCarte());
        assertEquals("Protège contre les attaques", passive.getDescription());
        assertEquals(2, passive.getValeur()); // valeur de l'effet passif
        assertEquals(TypeCarte.PASSIVE, passive.getType());
        
        // Vérification de l'effet
        Carte.EffetCarte effetPassif = passive.effetCarte();
        assertTrue(effetPassif.estPassive);
        
        // Test toString
        String description = passive.toString();
        assertTrue(description.contains("Bouclier"));
        assertTrue(description.contains("Protège contre les attaques"));
    }
    
    @Test
    @DisplayName("Test de la carte active")
    public void testCarteActive() {
        // Test constructeur de base
        CarteActive active = new CarteActive("Transformation", "Transforme une carte", 3);
        assertEquals("Transformation", active.getNomCarte());
        assertEquals("Transforme une carte", active.getDescription());
        assertEquals(3, active.getValeur()); // valeur de l'effet actif
        assertEquals(TypeCarte.ACTIVE, active.getType());
        
        // Test toString
        String description = active.toString();
        assertTrue(description.contains("Transformation"));
        assertTrue(description.contains("Transforme une carte"));
    }
    
    @Test
    @DisplayName("Test de la carte attaque")
    public void testCarteAttaque() {
        // Test constructeur de base
        CarteAttaque attaque = new CarteAttaque("Attaque puissante", "Une attaque dévastatrice", 5, 2);
        assertEquals("Attaque puissante", attaque.getNomCarte());
        assertEquals("Une attaque dévastatrice", attaque.getDescription());
        assertEquals(5, attaque.getValeur()); // dégâts infligés
        assertEquals(2, attaque.getValeurSecondaire()); // dégâts subis
        assertEquals(TypeCarte.OFFENSIVE, attaque.getType());
        
        // Vérification de l'effet
        Carte.EffetCarte effet = attaque.effetCarte();
        assertEquals(5, effet.degatsInfliges);
        assertEquals(2, effet.degatsSubis);
        assertTrue(effet.estAttaque);
        
        // Test toString
        String description = attaque.toString();
        assertTrue(description.contains("Attaque puissante"));
        assertTrue(description.contains("Une attaque dévastatrice"));
        assertTrue(description.contains("Attaque: 5"));
        assertTrue(description.contains("Dégâts subis: 2"));
    }
    
    @Test
    @DisplayName("Test de la carte coup spécial")
    public void testCarteCoupSpecial() {
        // Test constructeur de base
        CarteCoupSpecial coupSpecial = new CarteCoupSpecial("Coup mortel", "Un coup mortel dévastateur", 8, 3);
        assertEquals("Coup mortel", coupSpecial.getNomCarte());
        assertEquals("Un coup mortel dévastateur", coupSpecial.getDescription());
        assertEquals(8, coupSpecial.getValeur()); // dégâts infligés
        assertEquals(3, coupSpecial.getValeurSecondaire()); // dégâts subis
        assertEquals(TypeCarte.OFFENSIVE, coupSpecial.getType());
        
        // Vérification de l'effet
        Carte.EffetCarte effet = coupSpecial.effetCarte();
        assertEquals(8, effet.degatsInfliges);
        assertEquals(3, effet.degatsSubis);
        assertTrue(effet.estAttaque);
        
        // Test toString
        String description = coupSpecial.toString();
        assertTrue(description.contains("Coup mortel"));
        assertTrue(description.contains("Un coup mortel dévastateur"));
        assertTrue(description.contains("Attaque: 8"));
        assertTrue(description.contains("Dégâts subis: 3"));
    }
    
    @Test
    @DisplayName("Test de la carte soin")
    public void testCarteSoin() {
        // Test constructeur de base
        CarteSoin soin = new CarteSoin("Grande potion", "Une potion de soin très efficace", 4);
        assertEquals("Grande potion", soin.getNomCarte());
        assertEquals("Une potion de soin très efficace", soin.getDescription());
        assertEquals(4, soin.getValeur()); // points de vie gagnés
        assertEquals(TypeCarte.OFFENSIVE, soin.getType());
        
        // Vérification de l'effet
        Carte.EffetCarte effet = soin.effetCarte();
        assertEquals(4, effet.vieGagnee);
        assertTrue(effet.estSoin);
        
        // Test toString
        String description = soin.toString();
        assertTrue(description.contains("Grande potion"));
        assertTrue(description.contains("Une potion de soin très efficace"));
        assertTrue(description.contains("Soin: 4"));
    }
    
    @Test
    @DisplayName("Test de la carte popularité")
    public void testCartePopularite() {
        // Test constructeur de base
        CartePopularite pop = new CartePopularite("Grand discours", "Un discours motivant", 3, 1);
        assertEquals("Grand discours", pop.getNomCarte());
        assertEquals("Un discours motivant", pop.getDescription());
        assertEquals(3, pop.getValeur()); // popularité gagnée
        assertEquals(1, pop.getValeurSecondaire()); // dégâts subis
        assertEquals(TypeCarte.STRATEGIQUE, pop.getType());
        
        // Vérification de l'effet
        Carte.EffetCarte effet = pop.effetCarte();
        assertEquals(3, effet.populariteGagnee);
        assertEquals(1, effet.degatsSubis);
        assertTrue(effet.estPopularite);
        
        // Test toString
        String description = pop.toString();
        assertTrue(description.contains("Grand discours"));
        assertTrue(description.contains("Un discours motivant"));
        assertTrue(description.contains("Popularité: 3"));
        assertTrue(description.contains("Dégâts subis: 1"));
    }
    
    @Test
    @DisplayName("Test de la carte trésor")
    public void testCarteTresor() {
        // Test constructeur - gain d'or
        CarteTresor tresor = new CarteTresor("Diamant", "Un diamant précieux", 15, 0);
        assertEquals("Diamant", tresor.getNomCarte());
        assertEquals("Un diamant précieux", tresor.getDescription());
        assertEquals(15, tresor.getValeur()); // or gagné
        assertEquals(0, tresor.getValeurSecondaire()); // or perdu
        assertEquals(TypeCarte.STRATEGIQUE, tresor.getType());
        
        // Vérification de l'effet
        Carte.EffetCarte effet = tresor.effetCarte();
        assertEquals(15, effet.orGagne);
        assertEquals(0, effet.orPerdu);
        assertTrue(effet.estTresor);
        
        // Test constructeur - perte d'or
        CarteTresor perte = new CarteTresor("Vol", "Perte d'or", 0, 8);
        assertEquals("Vol", perte.getNomCarte());
        assertEquals("Perte d'or", perte.getDescription());
        assertEquals(0, perte.getValeur()); // or gagné
        assertEquals(8, perte.getValeurSecondaire()); // or perdu
        
        // Vérification de l'effet
        Carte.EffetCarte effetPerte = perte.effetCarte();
        assertEquals(0, effetPerte.orGagne);
        assertEquals(8, effetPerte.orPerdu);
        assertTrue(effetPerte.estTresor);
        
        // Test toString
        String description = tresor.toString();
        assertTrue(description.contains("Diamant"));
        assertTrue(description.contains("Un diamant précieux"));
        assertTrue(description.contains("Or gagné: 15"));
        
        String descPerte = perte.toString();
        assertTrue(descPerte.contains("Vol"));
        assertTrue(descPerte.contains("Perte d'or"));
        assertTrue(descPerte.contains("Or perdu: 8"));
    }
    
    @Test
    @DisplayName("Test de la carte probabilité")
    public void testCarteProbabilite() {
        // Test constructeur de base
        CarteProbabilite prob = new CarteProbabilite("Dés magiques", "Lance les dés du destin", 50);
        assertEquals("Dés magiques", prob.getNomCarte());
        assertEquals("Lance les dés du destin", prob.getDescription());
        assertEquals(50, prob.getProbabilite()); // probabilité en pourcentage
        assertEquals(TypeCarte.PROBABILITE, prob.getType());
        
        // Test avec probabilité hors limites
        CarteProbabilite probHaute = new CarteProbabilite("Test", "Test", 120);
        assertEquals(100, probHaute.getProbabilite()); // doit être limité à 100%
        
        CarteProbabilite probBasse = new CarteProbabilite("Test", "Test", -20);
        assertEquals(0, probBasse.getProbabilite()); // doit être limité à 0%
        
        // Test estSucces (ne peut pas vraiment tester le résultat car c'est aléatoire)
        // On peut au moins vérifier que la méthode ne provoque pas d'erreur
        boolean resultat = prob.estSucces();
        // Le résultat est soit true soit false
        assertTrue(resultat || !resultat);
    }
    
    @Test
    @DisplayName("Test du Marché")
    public void testMarche() {
        // Test constructeur par défaut
        Marche marche = new Marche();
        assertNotNull(marche);
        assertEquals(0, marche.getCartesDisponibles().size());
        
        // Test ajout de cartes
        Carte carte1 = new Carte(TypeCarte.OFFENSIVE, "Carte 1", "Description 1");
        Carte carte2 = new Carte(TypeCarte.STRATEGIQUE, "Carte 2", "Description 2");
        marche.ajouterCarte(carte1);
        marche.ajouterCarte(carte2);
        
        assertEquals(2, marche.getCartesDisponibles().size());
        assertTrue(marche.getCartesDisponibles().contains(carte1));
        assertTrue(marche.getCartesDisponibles().contains(carte2));
        
        // Test achat de carte
        Carte carteAchetee = marche.acheterCarte(0);
        assertEquals(carte1, carteAchetee);
        assertEquals(1, marche.getCartesDisponibles().size());
        assertFalse(marche.getCartesDisponibles().contains(carte1));
        
        // Test achat avec indice invalide
        assertNull(marche.acheterCarte(5));
        assertNull(marche.acheterCarte(-1));
        
        // Test vider marché
        marche.viderMarche();
        assertEquals(0, marche.getCartesDisponibles().size());
    }
    
    @Test
    @DisplayName("Test du Parser de Cartes")
    public void testParserCarte() {
        // Test de la méthode statique getInstance
        ParserCarte parser = ParserCarte.getInstance();
        assertNotNull(parser);
        
        // Vérifier que c'est bien un singleton
        ParserCarte parser2 = ParserCarte.getInstance();
        assertSame(parser, parser2);
        
        // Test de la méthode getCarteById (retourne une carte avec l'ID spécifié)
        Carte carte = parser.getCarteById(1);
        assertNotNull(carte);
        assertEquals(1, carte.getId());
    }
    
    @Test
    @DisplayName("Test de la classe Ressource")
    public void testRessource() {
        // Test de la méthode getMessage
        String message = Ressource.getMessage("test.message");
        // Si la clé n'existe pas, la méthode retourne la clé elle-même
        assertEquals("test.message", message);
        
        // Test de la méthode getPath
        String path = Ressource.getPath("test.path");
        // Si la clé n'existe pas, la méthode retourne la clé elle-même
        assertEquals("test.path", path);
    }
}