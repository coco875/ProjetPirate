package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import carte.Carte;
import carte.CarteAttaque;
import carte.CartePopularite;
import carte.CarteSpeciale;
import carte.ParserCarte;
import jeu.Pioche;

public class ControlPioche {
	private Pioche pioche;
	
	public ControlPioche() {
		List<Carte> list = new ArrayList<>();
		
		// Vérifier si le répertoire existe
		File resourceDir = new File("src/carte/resource");
		if (resourceDir.exists() && resourceDir.isDirectory()) {
			File[] files = resourceDir.listFiles();
			if (files != null) {
				for (File f : files) {
					try {
						list.add(ParserCarte.lireCarte(f.toString()));
					} catch (Exception e) {
						System.out.println("Erreur lors de la lecture du fichier " + f.getName() + ": " + e.getMessage());
					}
				}
			}
		}
		
		// Si aucune carte n'a été chargée, créer des cartes par défaut
		if (list.isEmpty()) {
			System.out.println("Aucune carte n'a été chargée depuis les fichiers. Création de cartes par défaut.");
			// Ajouter quelques cartes d'attaque par défaut
			list.add(new CarteAttaque("Épée", "Une épée tranchante", 2, 2));
			list.add(new CarteAttaque("Pistolet", "Un pistolet puissant", 3, 3));
			list.add(new CarteAttaque("Canon", "Un canon destructeur", 4, 4));
			
			// Ajouter quelques cartes de popularité par défaut
			list.add(new CartePopularite("Chanson", "Une chanson entraînante", 2, 2));
			list.add(new CartePopularite("Trésor", "Un trésor qui impressionne", 3, 3));
			list.add(new CartePopularite("Légende", "Une légende inspirante", 4, 4));
			
			// Ajouter quelques cartes spéciales par défaut
			list.add(new CarteSpeciale("Perroquet", "Un perroquet qui distrait l'adversaire", "Réduit les dégâts", 2));
			list.add(new CarteSpeciale("Carte au trésor", "Une carte au trésor mystérieuse", "Augmente l'or", 3));
		}
		
		this.pioche = new Pioche(list);
	}

	public Carte piocher() {
		return pioche.piocher();
	}
}