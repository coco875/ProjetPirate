package test;

import controllers.ControlJoueur;
import joueur.Joueur;

public class TestVieJoueur {
	
	public static void main(String[] args) {
		
		Joueur j = new Joueur("chef", null);
		ControlJoueur cj = new ControlJoueur(j, null, null);
		
		
		//j.setcJ(cj);
		
		System.out.println(j.getNom() + " possède " + j.getPointsDeVie() + " points de vie");
		System.out.println(j.getNom() + " possède " + j.getPopularite() + " points de popularité");
		System.out.println("");
		// 5 / 0
		
		// Un degats négatif signifie un gain de points de vie
		// Une popularité positive signifie un gain de popularité
		cj.recevoirEffets(-2, 1);
		
		System.out.println(j.getNom() + " possède " + j.getPointsDeVie() + " points de vie");
		System.out.println(j.getNom() + " possède " + j.getPopularite() + " points de popularité");
		System.out.println("");
		// 5 + 2 = 7 (limité à 5) / 0 + 1 = 1
		
		// Un degats négatif signifie un gain de points de vie
		// Une popularité positive signifie un gain de popularité
		cj.recevoirEffets(-4, 7);
		
		System.out.println(j.getNom() + " possède " + j.getPointsDeVie() + " points de vie");
		System.out.println(j.getNom() + " possède " + j.getPopularite() + " points de popularité");
		System.out.println("");
		// 5 (déjà au max) / 1 + 7 = 8 (limité à 5)
		
		// Un degats positif signifie une perte de points de vie
		// Une popularité négative signifie une perte de popularité
		cj.recevoirEffets(2, -6);
		System.out.println(j.getNom() + " possède " + j.getPointsDeVie() + " points de vie");
		System.out.println(j.getNom() + " possède " + j.getPopularite() + " points de popularité");
		System.out.println("");
		// 5 - 2 = 3 / 5 - 6 = 0 (limité à 0)
	}
}
