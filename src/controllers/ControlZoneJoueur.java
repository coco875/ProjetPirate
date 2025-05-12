package controllers;

import carte.CarteOffensive;
import carte.CarteStrategique;
import jeu.ZoneOffensive;
import jeu.ZoneStrategique;

public class ControlZoneJoueur {
    private ZoneOffensive zoneOffensive;
    private ZoneStrategique zoneStrategique;

    public ControlZoneJoueur(ZoneOffensive zoneOffensive, ZoneStrategique zoneStrategique) {
        this.zoneOffensive = zoneOffensive;
        this.zoneStrategique = zoneStrategique;
    }

    public ZoneOffensive getZoneOffensive() {
        return zoneOffensive;
    }

    public ZoneStrategique getZoneStrategique() {
        return zoneStrategique;
    }

    public void ajouterCarteOffensive(CarteOffensive carte) {
        zoneOffensive.ajouterCarte(carte);
    }

    public void ajouterCarteStrategique(CarteStrategique carte) {
        zoneStrategique.ajouterCarte(carte);
    }

    public void viderZone() {
        zoneOffensive.viderZone();
        zoneStrategique.viderZone();
    }

}
