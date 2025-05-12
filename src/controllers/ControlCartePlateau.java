package controllers;

import carte.Carte;
import java.util.List;

import carte.CarteOffensive;
import carte.CarteStrategique;
import carte.Carte.EffetCarte;
import jeu.ZoneOffensive;
import jeu.ZoneStrategique;
import joueur.Joueur;

/**
 * Contrôleur pour gérer les cartes sur le plateau
 */
public class ControlCartePlateau {
    private ControlZoneJoueur zoneJoueur1;
    private ControlZoneJoueur zoneJoueur2;

    private ControlJoueur controlJoueur1;
    private ControlJoueur controlJoueur2;

    /**
     * Constructeur
     */
    public ControlCartePlateau(ControlJoueur controlJoueur1, ControlJoueur controlJoueur2, ControlZoneJoueur zoneJoueur1, ControlZoneJoueur zoneJoueur2) {
        this.zoneJoueur1 = zoneJoueur1;
        this.zoneJoueur2 = zoneJoueur2;
        this.controlJoueur1 = controlJoueur1;
        this.controlJoueur2 = controlJoueur2;
    }

    /**
     * Définit les joueurs pour ce tour
     */
    public void setJoueurs(ControlJoueur controlJoueur1, ControlJoueur controlJoueur2) {
        this.controlJoueur1 = controlJoueur1;
        this.controlJoueur2 = controlJoueur2;
    }

    private void appliquerEffetCarte(EffetCarte effet, ControlJoueur joueurAttaquant, ControlJoueur joueurDefenseur) {
        joueurAttaquant.perdrePointsDeVie(effet.degatsSubis);
        joueurDefenseur.perdrePointsDeVie(effet.degatsInfliges);
        joueurAttaquant.gagnerPopularite(effet.populariteGagnee);
        joueurAttaquant.gagnerPointsDeVie(effet.vieGagne);
        joueurAttaquant.gagnerOr(effet.orGagne);
    }

    public void appliquerEffetCarte() {
        // Appliquer l'effet de la carte offensive
        for (CarteOffensive carte : zoneJoueur1.getZoneOffensive().getCartesOffensives()) {
            EffetCarte effet = carte.effetCarte();
            appliquerEffetCarte(effet, controlJoueur1, controlJoueur2);
        }
        for (CarteOffensive carte : zoneJoueur2.getZoneOffensive().getCartesOffensives()) {
            EffetCarte effet = carte.effetCarte();
            appliquerEffetCarte(effet, controlJoueur2, controlJoueur1);
        }

        // Appliquer l'effet de la carte stratégique
        for (CarteStrategique carte : zoneJoueur1.getZoneStrategique().getCartesStrategiques()) {
            EffetCarte effet = carte.effetCarte();
            appliquerEffetCarte(effet, controlJoueur1, controlJoueur2);
        }
        for (CarteStrategique carte : zoneJoueur2.getZoneStrategique().getCartesStrategiques()) {
            EffetCarte effet = carte.effetCarte();
            appliquerEffetCarte(effet, controlJoueur2, controlJoueur1);
        }
    }

    /**
     * Défausse toutes les cartes du plateau
     */
    public void defausserCartesPlateau() {
        zoneJoueur1.viderZone();
        zoneJoueur2.viderZone();
    }

    /**
     * Récupère les contrôleurs des joueurs
     * @return Tableau des deux contrôleurs de joueurs [controlJoueur1, controlJoueur2]
     */
    public ControlJoueur[] getJoueurs() {
        return new ControlJoueur[] {controlJoueur1, controlJoueur2};
    }

    public ControlZoneJoueur getZoneJoueur1() {
        return zoneJoueur1;
    }

    public ControlZoneJoueur getZoneJoueur2() {
        return zoneJoueur2;
    }
}
