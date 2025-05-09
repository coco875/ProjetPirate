package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.Arrays;

import carte.Carte;
import carte.CarteAttaque;
import carte.CartePopularite;
import carte.CarteSoin;
import carte.CarteSpeciale;
import carte.CarteTresor;
import carte.ParserCarte;
import jeu.Pioche;

/**
 * Contrôleur gérant la pioche de cartes
 */
public class ControlPioche {
	private Pioche pioche;
	
	public ControlPioche() {
		initialiserPioche();
	}
	
	/**
	 * Initialise la pioche avec les cartes depuis les fichiers ou par défaut
	 */
	public void initialiserPioche() {
		List<Carte> list = new ArrayList<>();
		
		// Vérifier si le répertoire existe
		File resourceDir = new File("src/ressources/cartes");
		if (resourceDir.exists() && resourceDir.isDirectory()) {
			// Charger les cartes du répertoire principal
			list.addAll(chargerCartesDepuisRepertoire(resourceDir));
			
			// Charger les cartes des sous-répertoires
			File attaqueDir = new File("src/ressources/cartes/attaque");
			if (attaqueDir.exists() && attaqueDir.isDirectory()) {
				list.addAll(chargerCartesDepuisRepertoire(attaqueDir));
			}
			
			File populariteDir = new File("src/ressources/cartes/popularite");
			if (populariteDir.exists() && populariteDir.isDirectory()) {
				list.addAll(chargerCartesDepuisRepertoire(populariteDir));
				}
			
			// Ajout des nouveaux sous-répertoires pour les cartes de trésor et de soin
			File tresorDir = new File("src/ressources/cartes/tresor");
			if (tresorDir.exists() && tresorDir.isDirectory()) {
				list.addAll(chargerCartesDepuisRepertoire(tresorDir));
			}
			
			File soinDir = new File("src/ressources/cartes/soin");
			if (soinDir.exists() && soinDir.isDirectory()) {
				list.addAll(chargerCartesDepuisRepertoire(soinDir));
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
			
			// Ajouter quelques cartes de trésor par défaut
			list.add(new CarteTresor("Coffre au trésor", "Un coffre rempli d'or", 10, 0, 0));
			list.add(new CarteTresor("Taxes portuaires", "Vous devez payer des taxes", 0, 5, 0));
			list.add(new CarteTresor("Pillage", "Volez de l'or à votre adversaire", 0, 0, 8));
			
			// Ajouter quelques cartes de soin par défaut
			list.add(new CarteSoin("Remède", "Un remède efficace contre les blessures", 2));
			list.add(new CarteSoin("Bandages", "Des bandages pour stopper l'hémorragie", 1));
		}
		
		// Correction: Instancier Pioche sans argument, puis ajouter les cartes et mélanger
		this.pioche = new Pioche(); 
		for (Carte carte : list) {
		    this.pioche.ajouterCarte(carte);
		}
		this.pioche.melanger();
	}
	
	/**
	 * Vérifie si la pioche est vide
	 */
	public boolean estVide() {
		return pioche.estVide();
	}
	
	/**
	 * Charge les cartes depuis un répertoire
	 */
	/*private void chargerCartesDepuisRepertoire(File repertoire, List<Carte> listCartes) {
		File[] files = repertoire.listFiles();
		if (files != null) {
			for (File f : files) {
				if (f.isFile()) {
					
					
					try {
						
						Carte carte = ParserCarte.lireCarte(f.toString());
						if (carte != null) {
							.add(carte);
						}
					} catch (Exception e) {
						System.out.println("Erreur lors de la lecture du fichier " + f.getName() + ": " + e.getMessage());
					}
				}
			}
		}
	}*/
	
	
	private List<Carte> chargerCartesDepuisRepertoire(File repertoire) {
		File[] files = repertoire.listFiles();
		if (files !=null) {
			return Arrays.stream(files)
										.filter(File::isFile)
										.map(File::toString)
										.filter(filePath -> filePath.endsWith(".txt"))
										.map(file -> {
											try {
												return ParserCarte.lireCarte(file);
											} catch (Exception e) {
												System.out.println("Erreur lors de la lecture du fichier " + file + ": " + e.getMessage());
											}
											return null;
										})
										.filter(carte -> carte != null)
										.toList();
			
			
		}
		
		return new ArrayList<Carte>();
	}

	/**
	 * Pioche une carte
	 * @return La carte piochée ou null si la pioche est vide
	 */
	public Carte piocher() {
		return pioche.estVide() ? null : pioche.piocher();
	}
}