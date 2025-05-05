package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import joueur.Joueur;
import joueur.Pirate;

public class TestJoueur {

	@Test
	public void testCreationJoueur() {
		Pirate pirate = new Pirate("Jack Sparrow");
		Joueur joueur = new Joueur("Joueur 1", pirate);
		
		// Vérifications
		assertEquals("Joueur 1", joueur.getNom(), "Le nom du joueur devrait être correctement initialisé");
		assertEquals("Jack Sparrow", joueur.getPirate().getNom(), "Le nom du pirate devrait être correctement initialisé");
		assertEquals(5, joueur.getPointsDeVie(), "Le joueur devrait avoir 5 points de vie initialement");
		assertEquals(0, joueur.getPopularite(), "Le joueur devrait avoir 0 point de popularité initialement");
		assertEquals(3, joueur.getOr(), "Le joueur devrait avoir 3 pièces d'or initialement");
	}
	
	@Test
	public void testGestionPointsDeVie() {
		Pirate pirate = new Pirate("Jack Sparrow");
		Joueur joueur = new Joueur("Joueur 1", pirate);
		
		// État initial
		int vieInitiale = joueur.getPointsDeVie();
		
		// Perdre des points de vie
		joueur.perdrePointsDeVie(2);
		assertEquals(vieInitiale - 2, joueur.getPointsDeVie(), "Le joueur devrait avoir perdu 2 points de vie");
		
		// Gagner des points de vie
		joueur.gagnerPointsDeVie(1);
		assertEquals(vieInitiale - 1, joueur.getPointsDeVie(), "Le joueur devrait avoir récupéré 1 point de vie");
		
		// Vérifier limite maximale (5 points de vie)
		joueur.setVie(4);
		joueur.gagnerPointsDeVie(2);
		assertEquals(5, joueur.getPointsDeVie(), "Le joueur ne devrait pas dépasser 5 points de vie");
		
		// Vérifier limite minimale (0 points de vie)
		joueur.setVie(1);
		joueur.perdrePointsDeVie(2);
		assertEquals(0, joueur.getPointsDeVie(), "Le joueur ne devrait pas avoir moins de 0 point de vie");
	}
	
	@Test
	public void testGestionPopularite() {
		Pirate pirate = new Pirate("Jack Sparrow");
		Joueur joueur = new Joueur("Joueur 1", pirate);
		
		// État initial
		int popInitiale = joueur.getPopularite();
		
		// Gagner de la popularité
		joueur.gagnerPopularite(2);
		assertEquals(popInitiale + 2, joueur.getPopularite(), "Le joueur devrait avoir gagné 2 points de popularité");
		
		// Perdre de la popularité
		joueur.perdrePopularite(1);
		assertEquals(popInitiale + 1, joueur.getPopularite(), "Le joueur devrait avoir perdu 1 point de popularité");
		
		// Vérifier limite maximale (5 points de popularité)
		joueur.setPopularite(4);
		joueur.gagnerPopularite(2);
		assertEquals(5, joueur.getPopularite(), "Le joueur ne devrait pas dépasser 5 points de popularité");
		
		// Vérifier limite minimale (0 points de popularité)
		joueur.setPopularite(1);
		joueur.perdrePopularite(2);
		assertEquals(0, joueur.getPopularite(), "Le joueur ne devrait pas avoir moins de 0 point de popularité");
	}
}