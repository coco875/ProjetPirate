package jeu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import carte.*;

public class Pioche implements Iterator<Carte>{
	private List<Carte> pioche = new ArrayList<>();
	
	
	public Pioche(List<Carte> listeCartes) {
		
		pioche = listeCartes;
		
	}
	
	public Carte piocher() {
		Random nombreRandom = new Random();
		Carte cartePiochee = pioche.get(nombreRandom.nextInt(0, pioche.size()));
		return cartePiochee;
	}
	
	
	@Override
	public boolean hasNext() {
		return !pioche.isEmpty();
	}

	@Override
	public Carte next() {
		return piocher();
	}
}
