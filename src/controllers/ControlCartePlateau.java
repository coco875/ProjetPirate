package controllers;

import carte.Carte;
import java.util.List;

import carte.CarteOffensive;
import carte.CarteStrategique;
import jeu.Defausse;
import jeu.ZoneOffensive; // Renamed from ZoneAttaque
import jeu.ZoneStrategique;
import joueur.Joueur; // Import manquant pour la classe Joueur

/**
 * @brief Contrôleur pour gérer les cartes sur le plateau
 */
public class ControlCartePlateau {
    private ZoneOffensive zoneOffensiveJ1; // Renamed from ZoneAttaque
    private ZoneStrategique zoneStrategiqueJ1;
    private ZoneOffensive zoneOffensiveJ2; // Renamed from ZoneAttaque
    private ZoneStrategique zoneStrategiqueJ2;
    private ControlJoueur controlJoueur1;
    private ControlJoueur controlJoueur2;
    private Defausse defausse;

    /**
     * @brief Constructeur
     * @param controlJoueur1 Contrôleur du joueur 1
     * @param controlJoueur2 Contrôleur du joueur 2
     */
    public ControlCartePlateau(ControlJoueur controlJoueur1, ControlJoueur controlJoueur2) {
        this.zoneOffensiveJ1 = new ZoneOffensive(); // Renamed from ZoneAttaque
        this.zoneStrategiqueJ1 = new ZoneStrategique();
        this.zoneOffensiveJ2 = new ZoneOffensive(); // Renamed from ZoneAttaque
        this.zoneStrategiqueJ2 = new ZoneStrategique();
        this.controlJoueur1 = controlJoueur1;
        this.controlJoueur2 = controlJoueur2;
        this.defausse = new Defausse();
    }

    /**
     * @brief Définit les joueurs pour ce tour
     * @param controlJoueur1 Contrôleur du joueur 1
     * @param controlJoueur2 Contrôleur du joueur 2
     */
    public void setJoueurs(ControlJoueur controlJoueur1, ControlJoueur controlJoueur2) {
        this.controlJoueur1 = controlJoueur1;
        this.controlJoueur2 = controlJoueur2;
    }

    /**
     * @brief Ajoute une carte offensive à la zone d'attaque du joueur 1
     * @param carte Carte offensive à ajouter
     */
    public void ajouterCarteOffensiveJ1(CarteOffensive carte) {
        zoneOffensiveJ1.ajouterCarte(carte);
    }

    /**
     * @brief Ajoute une carte offensive à la zone d'attaque du joueur 2
     * @param carte Carte offensive à ajouter
     */
    public void ajouterCarteOffensiveJ2(CarteOffensive carte) {
        zoneOffensiveJ2.ajouterCarte(carte);
    }

    /**
     * @brief Ajoute une carte stratégique à la zone stratégique du joueur 1
     * @param carte Carte stratégique à ajouter
     */
    public void ajouterCarteStrategiqueJ1(CarteStrategique carte) {
        zoneStrategiqueJ1.ajouterCarte(carte);
    }

    /**
     * @brief Ajoute une carte stratégique à la zone stratégique du joueur 2
     * @param carte Carte stratégique à ajouter
     */
    public void ajouterCarteStrategiqueJ2(CarteStrategique carte) {
        zoneStrategiqueJ2.ajouterCarte(carte);
    }

    /**
     * @brief Applique les effets des cartes offensives sur le plateau
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
        int orVoleJ1aJ2 = 0;
        
        int degatsJ2versJ1 = 0;
        int degatsSubisJ2 = 0;
        int soinsJ2 = 0;
        int orVoleJ2aJ1 = 0;
        
        // Calcul des effets des cartes du joueur 1
        System.out.println("DEBUG - Traitement des cartes offensives J1: " + zoneOffensiveJ1.getCartesOffensives().size());
        for (CarteOffensive carte : zoneOffensiveJ1.getCartesOffensives()) {
            System.out.println("DEBUG - J1 joue: " + carte.getNomCarte() + " de type " + carte.getTypeOffensif());
            switch (carte.getTypeOffensif()) {
                case ATTAQUE_DIRECTE:
                    degatsJ1versJ2 += carte.getDegatsInfliges();
                    degatsSubisJ1 += carte.getDegatsSubis();
                    System.out.println("DEBUG - Carte Attaque: ajout " + carte.getDegatsInfliges() + 
                                     " dégâts vers J2, " + carte.getDegatsSubis() + " dégâts subis par J1");
                    break;
                case SOIN:
                    soinsJ1 += carte.getVieGagnee();
                    System.out.println("DEBUG - Carte Soin: ajout " + carte.getVieGagnee() + " PV pour J1");
                    break;
                case TRESOR_OFFENSIF:
                    orVoleJ1aJ2 += carte.getOrVole();
                    System.out.println("DEBUG - Carte Trésor: ajout " + carte.getOrVole() + " Or volé à J2");
                    break;
                default:
                    System.out.println("DEBUG - Type de carte non géré: " + carte.getTypeOffensif());
            }
        }
        
        // Calcul des effets des cartes du joueur 2
        System.out.println("DEBUG - Traitement des cartes offensives J2: " + zoneOffensiveJ2.getCartesOffensives().size());
        for (CarteOffensive carte : zoneOffensiveJ2.getCartesOffensives()) {
            System.out.println("DEBUG - J2 joue: " + carte.getNomCarte() + " de type " + carte.getTypeOffensif());
            switch (carte.getTypeOffensif()) {
                case ATTAQUE_DIRECTE:
                    degatsJ2versJ1 += carte.getDegatsInfliges();
                    degatsSubisJ2 += carte.getDegatsSubis();
                    System.out.println("DEBUG - Carte Attaque: ajout " + carte.getDegatsInfliges() + 
                                     " dégâts vers J1, " + carte.getDegatsSubis() + " dégâts subis par J2");
                    break;
                case SOIN:
                    soinsJ2 += carte.getVieGagnee();
                    System.out.println("DEBUG - Carte Soin: ajout " + carte.getVieGagnee() + " PV pour J2");
                    break;
                case TRESOR_OFFENSIF:
                    orVoleJ2aJ1 += carte.getOrVole();
                    System.out.println("DEBUG - Carte Trésor: ajout " + carte.getOrVole() + " Or volé à J1");
                    break;
                default:
                    System.out.println("DEBUG - Type de carte non géré: " + carte.getTypeOffensif());
            }
        }
        
        System.out.println("DEBUG - Résumé des effets:");
        System.out.println("DEBUG - J1: " + degatsSubisJ1 + " dégâts subis, " + soinsJ1 + " soins, " + orVoleJ1aJ2 + " or volé");
        System.out.println("DEBUG - J2: " + degatsJ1versJ2 + " dégâts subis, " + soinsJ2 + " soins, " + orVoleJ2aJ1 + " or volé");
        
        // Application des effets calculés
        System.out.println("DEBUG - Avant application: J1 PV=" + joueur1.getPointsDeVie() + ", J2 PV=" + joueur2.getPointsDeVie());
        
        // Dégâts - utiliser perdrePV directement au lieu de recevoirEffets
        joueur1.perdrePV(degatsJ2versJ1 + degatsSubisJ1);
        joueur2.perdrePV(degatsJ1versJ2 + degatsSubisJ2);
        
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
        
        // Vol d'or
        if (orVoleJ1aJ2 > 0) {
            int orDisponible = Math.min(orVoleJ1aJ2, joueur2.getOr());
            joueur2.perdreOr(orDisponible);
            joueur1.gagnerOr(orDisponible);
            System.out.println("DEBUG - J1 vole " + orDisponible + " or à J2");
        }
        if (orVoleJ2aJ1 > 0) {
            int orDisponible = Math.min(orVoleJ2aJ1, joueur1.getOr());
            joueur1.perdreOr(orDisponible);
            joueur2.gagnerOr(orDisponible);
            System.out.println("DEBUG - J2 vole " + orDisponible + " or à J1");
        }
        
        System.out.println("DEBUG - Final: J1 PV=" + joueur1.getPointsDeVie() + " Or=" + joueur1.getOr());
        System.out.println("DEBUG - Final: J2 PV=" + joueur2.getPointsDeVie() + " Or=" + joueur2.getOr());
    }

    /**
     * @brief Applique les effets des cartes stratégiques sur les joueurs
     */
    public void appliquerEffetsCartesStrategiques() {
        List<CarteStrategique> cartesStrategiquesJ1 = zoneStrategiqueJ1.getCartesStrategiques();
        List<CarteStrategique> cartesStrategiquesJ2 = zoneStrategiqueJ2.getCartesStrategiques();
        
        for (CarteStrategique carte : cartesStrategiquesJ1) {
            switch (carte.getTypeStrategique()) {
                case POPULARITE:
                    int populariteGagnee = carte.getPopulariteGagnee();
                    int degatsSubis = carte.getDegatsSubis();
                    controlJoueur1.gagnerPopularite(populariteGagnee);
                    controlJoueur1.recevoirEffets(degatsSubis, 0);
                    break;
                case TRESOR:
                    int orGagne = carte.getOrGagne();
                    int orPerdu = carte.getOrPerdu();
                    if (orGagne > 0) {
                        controlJoueur1.getJoueur().gagnerOr(orGagne);
                    }
                    if (orPerdu > 0) {
                        controlJoueur1.getJoueur().perdreOr(orPerdu);
                    }
                    break;
                // Gérer d'autres types si nécessaire
                // case SPECIALE:
                // case PASSIVE:
            }
        }

        for (CarteStrategique carte : cartesStrategiquesJ2) {
            switch (carte.getTypeStrategique()) {
                case POPULARITE:
                    int populariteGagnee = carte.getPopulariteGagnee();
                    int degatsSubis = carte.getDegatsSubis();
                    controlJoueur2.gagnerPopularite(populariteGagnee);
                    controlJoueur2.recevoirEffets(degatsSubis, 0);
                    break;
                case TRESOR:
                    int orGagne = carte.getOrGagne();
                    int orPerdu = carte.getOrPerdu();
                    if (orGagne > 0) {
                        controlJoueur2.getJoueur().gagnerOr(orGagne);
                    }
                    if (orPerdu > 0) {
                        controlJoueur2.getJoueur().perdreOr(orPerdu);
                    }
                    break;
                // Gérer d'autres types si nécessaire
                // case SPECIALE:
                // case PASSIVE:
            }
        }
    }

    /**
     * @brief Défausse toutes les cartes du plateau
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

    /**
     * @brief Récupère la liste des cartes offensives du joueur 1
     * @return Liste des cartes offensives
     */
    public List<CarteOffensive> getCartesOffensivesJ1() {
        return zoneOffensiveJ1.getCartesOffensives();
    }

    /**
     * @brief Récupère la liste des cartes offensives du joueur 2
     * @return Liste des cartes offensives
     */
    public List<CarteOffensive> getCartesOffensivesJ2() {
        return zoneOffensiveJ2.getCartesOffensives();
    }

    /**
     * @brief Récupère la liste des cartes stratégiques du joueur 1
     * @return Liste des cartes stratégiques
     */
    public List<CarteStrategique> getCartesStrategiquesJ1() {
        return zoneStrategiqueJ1.getCartesStrategiques();
    }

    /**
     * @brief Récupère la liste des cartes stratégiques du joueur 2
     * @return Liste des cartes stratégiques
     */
    public List<CarteStrategique> getCartesStrategiquesJ2() {
        return zoneStrategiqueJ2.getCartesStrategiques();
    }

    /**
     * @brief Récupère la zone offensive du joueur 1
     * @return La zone offensive
     */
    public ZoneOffensive getZoneOffensiveJ1() {
        return zoneOffensiveJ1;
    }

    /**
     * @brief Récupère la zone offensive du joueur 2
     * @return La zone offensive
     */
    public ZoneOffensive getZoneOffensiveJ2() {
        return zoneOffensiveJ2;
    }

    /**
     * @brief Récupère la zone stratégique du joueur 1
     * @return La zone stratégique
     */
    public ZoneStrategique getZoneStrategiqueJ1() {
        return zoneStrategiqueJ1;
    }

    /**
     * @brief Récupère la zone stratégique du joueur 2
     * @return La zone stratégique
     */
    public ZoneStrategique getZoneStrategiqueJ2() {
        return zoneStrategiqueJ2;
    }

    /**
     * @brief Récupère la défausse
     * @return La défausse
     */
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
