package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.io.TempDir;

import controllers.ControlCartePlateau;
import controllers.ControlJeu;
import controllers.ControlPioche;
import controllers.ControlZoneJoueur;
import carte.Carte;
import carte.CarteAttaque;
import carte.CarteOffensive;
import carte.CartePopularite;
import carte.CarteSoin;
import carte.CarteStrategique;
import carte.CarteTresor;
import carte.TypeCarte;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jeu.Jeu;
import jeu.Pioche;
import joueur.Pirate;

@DisplayName("Tests du contrôleur ControlZoneJoueur")
class TestControlZoneJoueur {

	private ControlJeu cJeu;
	private ControlZoneJoueur cZJ1;
	private ControlZoneJoueur cZJ2;
	
	@BeforeEach
	public void initialiser() {
		Pirate pirate1 = new Pirate("a");
		Pirate pirate2 = new Pirate("b");
		
		cJeu = new ControlJeu();
		cJeu.initialiserJeu(pirate1, pirate2);
		
		ControlCartePlateau cCartePlateau = cJeu.getControlCartePlateau();
		
		 cZJ1 = cCartePlateau.getZoneJoueur1();
		 cZJ2 = cCartePlateau.getZoneJoueur2();
	}
	
	@Test
    @DisplayName("Test d'ajouts de carte aux zones joueur")
    public void testAjoutCartes() {

		
		//Ajout de cartes aux zones offensives
		
		CarteAttaque carteAttaqueJ1 = new CarteAttaque("Épée", "Une épée tranchante", 3, 2, 1);
		CarteSoin carteSoinJ2 = new CarteSoin("Potion", "Une potion magique", 2);
		
		cZJ1.ajouterCarteOffensive(carteAttaqueJ1);
		cZJ2.ajouterCarteOffensive(carteSoinJ2);
		
		List<CarteOffensive> listeZO1 = cZJ1.getZoneOffensive().getCartesOffensives();
		List<CarteOffensive> listeZO2 = cZJ2.getZoneOffensive().getCartesOffensives();
		
		assertEquals(false, listeZO1.isEmpty(), "La Zone Offensive du J1 ne devrait pas être vide");
		assertEquals(false, listeZO2.isEmpty(), "La Zone Offensive du J2 ne devrait pas être vide");
		
		
		
		//Ajout de cartes aux zones stratégiques
		
		CartePopularite cartePopulariteJ1 = new CartePopularite("Discours", "Un discours motivant", 2, 0);
		CarteTresor carteTresorJ2 = new CarteTresor("Coffre", "Un coffre plein d'or", 5);
		
		cZJ1.ajouterCarteStrategique(cartePopulariteJ1);
		cZJ2.ajouterCarteStrategique(carteTresorJ2);
		
		List<CarteStrategique> listeZS1 = cZJ1.getZoneStrategique().getCartesStrategiques();
		List<CarteStrategique> listeZS2 = cZJ2.getZoneStrategique().getCartesStrategiques();
		
		assertEquals(false, listeZS1.isEmpty(), "La Zone Stratégique du J1 ne devrait pas être vide");
		assertEquals(false, listeZS2.isEmpty(), "La Zone Stratégique du J2 ne devrait pas être vide");
	}
	
	@Test
	@DisplayName("Test de vidanges des zones joueur")
	public void TestVidangeCartes() {
		
		//Ajout de cartes aux zones offensives
		
		CarteAttaque carteAttaqueJ1 = new CarteAttaque("Épée", "Une épée tranchante", 3, 2, 1);
		CarteSoin carteSoinJ2 = new CarteSoin("Potion", "Une potion magique", 2);
		
		cZJ1.ajouterCarteOffensive(carteAttaqueJ1);
		cZJ2.ajouterCarteOffensive(carteSoinJ2);
		
		
		//Ajout de cartes aux zones stratégiques
		
		CartePopularite cartePopulariteJ1 = new CartePopularite("Discours", "Un discours motivant", 2, 0);
		CarteTresor carteTresorJ2 = new CarteTresor("Coffre", "Un coffre plein d'or", 5);
		
		cZJ1.ajouterCarteStrategique(cartePopulariteJ1);
		cZJ2.ajouterCarteStrategique(carteTresorJ2);
		
		
		//Récupération du contenu des zones
		List<CarteOffensive> listeZO1 = cZJ1.getZoneOffensive().getCartesOffensives();
		List<CarteOffensive> listeZO2 = cZJ2.getZoneOffensive().getCartesOffensives();
		
		List<CarteStrategique> listeZS1 = cZJ1.getZoneStrategique().getCartesStrategiques();
		List<CarteStrategique> listeZS2 = cZJ2.getZoneStrategique().getCartesStrategiques();
		
		
		//Vidange des zones
		cZJ1.viderZone();
		cZJ2.viderZone();
		
		assertEquals(true, listeZO1.isEmpty(), "La Zone Offensive du J1 devrait être vide");
		assertEquals(true, listeZO2.isEmpty(), "La Zone Offensive du J2 devrait être vide");
		assertEquals(true, listeZS1.isEmpty(), "La Zone Stratégique du J1 devrait être vide");
		assertEquals(true, listeZS2.isEmpty(), "La Zone Stratégique du J2 devrait être vide");
		
	}
	
		
	

}
