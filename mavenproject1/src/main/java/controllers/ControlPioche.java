package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import carte.Carte;
import carte.ParserCarte;
import jeu.Pioche;

public class ControlPioche {
	private Pioche pioche;
	
	public ControlPioche() {
		List<Carte> list = new ArrayList<>();
		for (File f : (new File("src/carte/resource")).listFiles()) {
			try {
			list.add(ParserCarte.lireCarte(f.toString()));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		this.pioche = new Pioche(list);
	}

	public Carte piocher() {
		return pioche.piocher();

	}
	
	
}