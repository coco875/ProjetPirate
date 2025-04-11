package controllers;

import entities.Carte;

public class ControlMain {
	
	private ControlPioche cPioche;
	
	
	public Carte piocher() {
		Carte c = cPioche.piocher();
		return c;
	}
	
	
}
