package controllers;

import carte.Carte;
import java.util.List;

import carte.CarteOffensive;
import carte.CarteStrategique;
import jeu.Defausse;
import jeu.ZoneOffensive;
import jeu.ZoneStrategique;
import joueur.Joueur;

/**
 * Contrôleur pour gérer les cartes sur le plateau
 */
public class ControlCartePlateau {
    private ZoneOffensive zoneOffensiveJ1;
    private ZoneStrategique zoneStrategiqueJ1;
    private ZoneOffensive zoneOffensiveJ2;
    private ZoneStrategique zoneStrategiqueJ2;
    private ControlJoueur controlJoueur1;
    private ControlJoueur controlJoueur2;
    private Defausse defausse;

    /**
     * Constructeur
     */
    public ControlCartePlateau(ControlJoueur controlJoueur1, ControlJoueur controlJoueur2) {
        this.zoneOffensiveJ1 = new ZoneOffensive();
        this.zoneStrategiqueJ1 = new ZoneStrategique();
        this.zoneOffensiveJ2 = new ZoneOffensive();
        this.zoneStrategiqueJ2 = new ZoneStrategique();
        this.controlJoueur1 = controlJoueur1;
        this.controlJoueur2 = controlJoueur2;
        this.defausse = new Defausse();
    }

    /**
     * Définit les joueurs pour ce tour
     */
    public void setJoueurs(ControlJoueur controlJoueur1, ControlJoueur controlJoueur2) {
        this.controlJoueur1 = controlJoueur1;
        this.controlJoueur2 = controlJoueur2;
    }

    /**
     * Ajoute une carte offensive à la zone du joueur 1
     */
    public void ajouterCarteOffensiveJ1(CarteOffensive carte) {
        zoneOffensiveJ1.ajouterCarte(carte);
    }

    /**
     * Ajoute une carte offensive à la zone du joueur 2
     */
    public void ajouterCarteOffensiveJ2(CarteOffensive carte) {
        zoneOffensiveJ2.ajouterCarte(carte);
    }

    /**
     * Ajoute une carte stratégique à la zone du joueur 1
     */
    public void ajouterCarteStrategiqueJ1(CarteStrategique carte) {
        zoneStrategiqueJ1.ajouterCarte(carte);
    }

    /**
     * Ajoute une carte stratégique à la zone du joueur 2
     */
    public void ajouterCarteStrategiqueJ2(CarteStrategique carte) {
        zoneStrategiqueJ2.ajouterCarte(carte);
    }

    /**
     * Applique les effets des cartes offensives sur le plateau
     */
    public void appliquerEffetsCartesOffensives() {
        System.out.println("DEBUG - Début appliquerEffetsCartesOffensives()");
        
        // Récupérer les objets Joueur pour un accès direct
        Joueur joueur1 = controlJoueur1.getJoueur();
        Joueur joueur2 = controlJoueur2.getJoueur();
        
        // Calcul des effets par type pour chaque joueur
        int degatsJ1versJ2 = 0;
        int degatsSubisJ1 = 0;
        int soinsJ1 = 0;
        
        int degatsJ2versJ1 = 0;
        int degatsSubisJ2 = 0;
        int soinsJ2 = 0;
        
        // Calcul des effets des cartes du joueur 1
        System.out.println("DEBUG - Traitement des cartes offensives J1: " + zoneOffensiveJ1.getCartesOffensives().size());
        for (CarteOffensive carte : zoneOffensiveJ1.getCartesOffensives()) {
            System.out.println("DEBUG - J1 joue: " + carte.getNomCarte() + " de type " + carte.getTypeOffensif());
            Carte.EffetCarte effet = carte.effetCarte();
            
            switch (carte.getTypeOffensif()) {
                case ATTAQUE_DIRECTE:
                    degatsJ1versJ2 += effet.degatsInfliges;
                    degatsSubisJ1 += effet.degatsSubis;
                    System.out.println("DEBUG - Carte Attaque: ajout " + effet.degatsInfliges + 
                                     " dégâts vers J2, " + effet.degatsSubis + " dégâts subis par J1");
                    break;
                case SOIN:
                    soinsJ1 += effet.vieGagnee;
                    System.out.println("DEBUG - Carte Soin: ajout " + effet.vieGagnee + " PV pour J1");
                    break;
                default:
                    System.out.println("DEBUG - Type de carte non géré: " + carte.getTypeOffensif());
            }
        }
        
        // Calcul des effets des cartes du joueur 2
        System.out.println("DEBUG - Traitement des cartes offensives J2: " + zoneOffensiveJ2.getCartesOffensives().size());
        for (CarteOffensive carte : zoneOffensiveJ2.getCartesOffensives()) {
            System.out.println("DEBUG - J2 joue: " + carte.getNomCarte() + " de type " + carte.getTypeOffensif());
            Carte.EffetCarte effet = carte.effetCarte();
            
            switch (carte.getTypeOffensif()) {
                case ATTAQUE_DIRECTE:
                    degatsJ2versJ1 += effet.degatsInfliges;
                    degatsSubisJ2 += effet.degatsSubis;
                    System.out.println("DEBUG - Carte Attaque: ajout " + effet.degatsInfliges + 
                                     " dégâts vers J1, " + effet.degatsSubis + " dégâts subis par J2");
                    break;
                case SOIN:
                    soinsJ2 += effet.vieGagnee;
                    System.out.println("DEBUG - Carte Soin: ajout " + effet.vieGagnee + " PV pour J2");
                    break;
                default:
                    System.out.println("DEBUG - Type de carte non géré: " + carte.getTypeOffensif());
            }
        }
        
        System.out.println("DEBUG - Résumé des effets:");
        System.out.println("DEBUG - J1: " + degatsSubisJ1 + " dégâts subis, " + soinsJ1 + " soins");
        System.out.println("DEBUG - J2: " + degatsJ1versJ2 + " dégâts subis, " + soinsJ2 + " soins");
        
        // Application des effets calculés
        System.out.println("DEBUG - Avant application: J1 PV=" + joueur1.getPointsDeVie() + ", J2 PV=" + joueur2.getPointsDeVie());
        
        // Dégâts - utiliser perdrePointsDeVie directement au lieu de recevoirEffets
        joueur1.perdrePointsDeVie(degatsJ2versJ1 + degatsSubisJ1);
        joueur2.perdrePointsDeVie(degatsJ1versJ2 + degatsSubisJ2);
        
        System.out.println("DEBUG - Après dégâts: J1 PV=" + joueur1.getPointsDeVie() + ", J2 PV=" + joueur2.getPointsDeVie());
        
        // Soins - appliqués après les dégâts
        if (soinsJ1 > 0) {
            System.out.println("DEBUG - Application de " + soinsJ1 + " soins à J1");
            joueur1.gagnerPointsDeVie(soinsJ1);
            System.out.println("DEBUG - Après soins: J1 PV=" + joueur1.getPointsDeVie());
        }
        if (soinsJ2 > 0) {
            System.out.println("DEBUG - Application de " + soinsJ2 + " soins à J2");
            joueur2.gagnerPointsDeVie(soinsJ2);
            System.out.println("DEBUG - Après soins: J2 PV=" + joueur2.getPointsDeVie());
        }
        
        System.out.println("DEBUG - Final: J1 PV=" + joueur1.getPointsDeVie() + " Or=" + joueur1.getOr());
        System.out.println("DEBUG - Final: J2 PV=" + joueur2.getPointsDeVie() + " Or=" + joueur2.getOr());
    }

    /**
     * Applique les effets des cartes stratégiques sur les joueurs
     */
    public void appliquerEffetsCartesStrategiques() {
        List<CarteStrategique> cartesStrategiquesJ1 = zoneStrategiqueJ1.getCartesStrategiques();
        List<CarteStrategique> cartesStrategiquesJ2 = zoneStrategiqueJ2.getCartesStrategiques();
        
        for (CarteStrategique carte : cartesStrategiquesJ1) {
            Carte.EffetCarte effet = carte.effetCarte();
            
            switch (carte.getTypeStrategique()) {
                case POPULARITE:
                    controlJoueur1.gagnerPopularite(effet.populariteGagnee);
                    controlJoueur1.perdrePointsDeVie(effet.degatsSubis);
                    break;
                case TRESOR:
                    if (effet.orGagne > 0) {
                        controlJoueur1.getJoueur().gagnerOr(effet.orGagne);
                    }
                    if (effet.orPerdu > 0) {
                        controlJoueur1.getJoueur().perdreOr(effet.orPerdu);
                    }
                    break;
            }
        }

        for (CarteStrategique carte : cartesStrategiquesJ2) {
            Carte.EffetCarte effet = carte.effetCarte();
            
            switch (carte.getTypeStrategique()) {
                case POPULARITE:
                    controlJoueur2.gagnerPopularite(effet.populariteGagnee);
                    controlJoueur2.perdrePointsDeVie(effet.degatsSubis);
                    break;
                case TRESOR:
                    if (effet.orGagne > 0) {
                        controlJoueur2.getJoueur().gagnerOr(effet.orGagne);
                    }
                    if (effet.orPerdu > 0) {
                        controlJoueur2.getJoueur().perdreOr(effet.orPerdu);
                    }
                    break;
            }
        }
    }

    /**
     * Défausse toutes les cartes du plateau
     */
    public void defausserCartesPlateau() {
        // Défausser les cartes offensives
        defausserCartes(zoneOffensiveJ1.getCartesOffensives());
        zoneOffensiveJ1.viderZone();
        
        defausserCartes(zoneOffensiveJ2.getCartesOffensives());
        zoneOffensiveJ2.viderZone();
        
        // Défausser les cartes stratégiques
        defausserCartes(zoneStrategiqueJ1.getCartesStrategiques());
        zoneStrategiqueJ1.viderZone();
        
        defausserCartes(zoneStrategiqueJ2.getCartesStrategiques());
        zoneStrategiqueJ2.viderZone();
    }

    // Getters pour les zones de cartes
    
    public List<CarteOffensive> getCartesOffensivesJ1() {
        return zoneOffensiveJ1.getCartesOffensives();
    }

    public List<CarteOffensive> getCartesOffensivesJ2() {
        return zoneOffensiveJ2.getCartesOffensives();
    }

    public List<CarteStrategique> getCartesStrategiquesJ1() {
        return zoneStrategiqueJ1.getCartesStrategiques();
    }

    public List<CarteStrategique> getCartesStrategiquesJ2() {
        return zoneStrategiqueJ2.getCartesStrategiques();
    }

    public ZoneOffensive getZoneOffensiveJ1() {
        return zoneOffensiveJ1;
    }

    public ZoneOffensive getZoneOffensiveJ2() {
        return zoneOffensiveJ2;
    }

    public ZoneStrategique getZoneStrategiqueJ1() {
        return zoneStrategiqueJ1;
    }

    public ZoneStrategique getZoneStrategiqueJ2() {
        return zoneStrategiqueJ2;
    }

    public Defausse getDefausse() {
        return defausse;
    }

    private void defausserCartes(List<?> cartes) {
        for (Object carteObj : cartes) {
            if (carteObj instanceof Carte) { // Check if the object is a Carte
                defausse.ajouterCarte((Carte) carteObj); // Cast to Carte and add
            }
        }
    }
}
