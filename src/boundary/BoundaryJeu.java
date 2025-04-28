package boundary;

import java.util.List;
import java.util.Scanner;

import carte.Carte;
import controllers.ControlJeu;
import controllers.ControlMarche;
import joueur.Pirate;

/**
 * @brief Frontière gérant les interactions avec l'utilisateur
 * 
 * Cette classe implémente l'interface utilisateur du jeu
 * conformément au modèle ECB.
 */
public class BoundaryJeu {
	private ControlJeu controlJeu;
	private ControlMarche controlMarche;
	private Scanner scanner;

	public BoundaryJeu(ControlJeu controlJeu, ControlMarche controlMarche) {
		this.controlJeu = controlJeu;
		this.controlMarche = controlMarche;
		this.scanner = new Scanner(System.in);
	}

    // Remplacer finalize par une méthode de fermeture explicite
    public void fermerScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }

	/**
	 * @brief Affiche les règles du jeu
	 */
	public void afficherRegles() {
		System.out.println("\n=== Règles du Jeu des Pirates ===");
		System.out.println("- Deux joueurs s'affrontent en utilisant des cartes");
		System.out.println("- Chaque joueur commence avec 5 points de vie et 0 point de popularité");
		System.out.println("- À chaque tour, un joueur pioche une carte et peut jouer jusqu'à 2 cartes");
		System.out.println("- Les cartes d'attaque permettent de réduire les points de vie de l'adversaire");
		System.out.println("- Les cartes de popularité augmentent la popularité du joueur");
		System.out.println("- Les cartes spéciales ont des effets uniques liés au capitaine");
		System.out.println("- Les joueurs peuvent gagner de l'or et l'utiliser au marché");
		System.out.println("- Objectif: atteindre 5 points de popularité OU réduire les points de vie de l'adversaire à 0");
		System.out.println("- Après chaque tour, le joueur doit avoir exactement 4 cartes en main");
		System.out.println("=================================\n");
	}

	/**
	 * @brief Configure les joueurs en début de partie
	 */
	public void configurerJoueurs() {
		System.out.println("=== Configuration des joueurs ===");
		
		// Configuration du joueur 1
		System.out.println("\nCréation du pirate 1");
		System.out.print("Entrez votre nom : ");
		String nomPirate1 = scanner.nextLine();
		
		System.out.println("Choisissez votre capitaine : ");
		afficherCapitainesDisponibles();
		int idPirate1 = scanner.nextInt();
		scanner.nextLine(); // Consommer le saut de ligne
		while (idPirate1 < 1 || idPirate1 > 4) {
			System.out.println("Entrez à nouveau le numéro de votre capitaine (1-4) :");
			idPirate1 = scanner.nextInt();
			scanner.nextLine(); // Consommer le saut de ligne
		}

		// Créer le pirate 1
		Pirate pirate1 = creerCapitaine(idPirate1);
		
		// Configuration du joueur 2
		System.out.println("\nCréation du pirate 2");
		System.out.print("Entrez votre nom : ");
		String nomPirate2 = scanner.nextLine();
		
		System.out.println("Choisissez votre capitaine : ");
		afficherCapitainesDisponibles();
		int idPirate2 = scanner.nextInt();
		scanner.nextLine(); // Consommer le saut de ligne
		while ((idPirate2 < 1 || idPirate2 > 4) || idPirate1 == idPirate2) {
			if (idPirate2 < 1 || idPirate2 > 4) {
				System.out.println("Entrez à nouveau le numéro de votre capitaine (1-4)");
			} else if (idPirate1 == idPirate2) {
				System.out.println("Veuillez choisir un capitaine différent");
			}
			idPirate2 = scanner.nextInt();
			scanner.nextLine(); // Consommer le saut de ligne
		}

		// Créer le pirate 2
		Pirate pirate2 = creerCapitaine(idPirate2);

		// Configurer les joueurs
		controlJeu.setJoueur1(nomPirate1, pirate1);
		controlJeu.setJoueur2(nomPirate2, pirate2);
		
		System.out.println("\nJoueurs configurés avec succès!");
	}
	
	/**
	 * @brief Affiche les capitaines disponibles
	 */
	private void afficherCapitainesDisponibles() {
		System.out.println("1: Barbe Noire (bonus aux cartes d'attaque)");
		System.out.println("2: Jack Sparrow (bonus à l'or et aux cartes spéciales)");
		System.out.println("3: Anne Bonny (bonus aux cartes de popularité)");
		System.out.println("4: Barbe Rouge (équilibré)");
	}
	
	/**
	 * @brief Crée un capitaine selon l'ID choisi
	 */
	private Pirate creerCapitaine(int id) {
		switch(id) {
			case 1:
				return new Pirate("Barbe Noire");
			case 2:
				return new Pirate("Jack Sparrow");
			case 3:
				return new Pirate("Anne Bonny");
			case 4:
			default:
				return new Pirate("Barbe Rouge");
		}
	}

	/**
	 * @brief Initialise les mains des joueurs au début de la partie
	 */
	public void initialiserMains() {
		System.out.println("\n=== Initialisation des mains ===");
		controlJeu.initialiserMainJoueur(1);
		controlJeu.initialiserMainJoueur(2);
		System.out.println("Mains des joueurs initialisées avec 4 cartes chacun.");
	}

	/**
	 * @brief Affiche l'état actuel d'un joueur (vie, popularité, or)
	 */
	public void afficherEtatJoueur(int joueurId) {
		System.out.println("\n=== État du joueur " + joueurId + " ===");
		System.out.println("Nom: " + controlJeu.getJoueur(joueurId).getNom());
		System.out.println("Capitaine: " + controlJeu.getJoueur(joueurId).getPirate().getNom());
		System.out.println("Points de vie: " + controlJeu.getJoueur(joueurId).getPointsDeVie());
		System.out.println("Popularité: " + controlJeu.getJoueur(joueurId).getPopularite());
		System.out.println("Or: " + controlJeu.getJoueur(joueurId).getOr());
	}

	/**
	 * @brief Affiche la main d'un joueur
	 */
	public void afficherMain(int joueurId) {
		System.out.println("\n=== Main du joueur " + joueurId + " ===");
		List<Carte> main = controlJeu.afficherMain(joueurId);
		
		if (main.isEmpty()) {
			System.out.println("La main est vide!");
			return;
		}
		
		for (int i = 0; i < main.size(); i++) {
			Carte carte = main.get(i);
			System.out.println(i + ": " + carte.getNomCarte() + " (" + carte.getType() + ", valeur: " + carte.getValeur() + ")");
			System.out.println("   Description: " + carte.getDescription());
		}
	}

	/**
	 * @brief Gère le marché pour un joueur
	 */
	public void gererMarche(int joueurId) {
		System.out.println("\n=== Marché ===");
		System.out.println("Bienvenue au marché, " + controlJeu.getJoueur(joueurId).getNom() + "!");
		System.out.println("Vous avez " + controlJeu.getJoueur(joueurId).getOr() + " pièces d'or.");
		
		if (controlJeu.getJoueur(joueurId).getOr() < 1) {
			System.out.println("Vous n'avez pas assez d'or pour acheter quoi que ce soit.");
			return;
		}
		
		// Afficher les cartes disponibles
		System.out.println("\nCartes disponibles à l'achat:");
		List<Carte> cartesMarche = controlMarche.getCartesDisponibles();
		
		if (cartesMarche.isEmpty()) {
			System.out.println("Aucune carte n'est disponible pour le moment.");
			return;
		}
		
		for (int i = 0; i < cartesMarche.size(); i++) {
			Carte carte = cartesMarche.get(i);
			System.out.println(i + ": " + carte.getNomCarte() + " (" + carte.getType() + ", coût: " + carte.getCout() + ")");
			System.out.println("   Description: " + carte.getDescription());
		}
		
		// Proposer d'acheter une carte
		System.out.println("\nVoulez-vous acheter une carte ? (o/n)");
		String reponse = scanner.nextLine();
		
		if (reponse.equalsIgnoreCase("o")) {
			System.out.print("Entrez l'index de la carte à acheter : ");
			int indexCarte = scanner.nextInt();
			scanner.nextLine(); // Consommer le saut de ligne
			
			// Vérifier que l'index est valide
			while (indexCarte < 0 || indexCarte >= cartesMarche.size()) {
				System.out.print("Index invalide. Entrez l'index de la carte à acheter (0-" + 
						(cartesMarche.size() - 1) + ") : ");
				indexCarte = scanner.nextInt();
				scanner.nextLine(); // Consommer le saut de ligne
			}
			
			// Acheter la carte
			boolean achatReussi = controlMarche.acheterCarte(joueurId, indexCarte);
			
			if (achatReussi) {
				System.out.println("Achat réussi !");
			} else {
				System.out.println("Achat échoué. Vous n'avez peut-être pas assez d'or.");
			}
		}
	}

	/**
	 * @brief Gère le tour d'un joueur
	 */
	public void jouerTour(int joueurId) {
		System.out.println("\n=== Tour du joueur " + joueurId + " ===");
		
		// Afficher l'état du joueur
		afficherEtatJoueur(joueurId);
		
		// Piocher une carte
		System.out.println("\nVous piochez une carte...");
		controlJeu.piocherCarte(joueurId);
		System.out.println("Carte piochée avec succès!");
		
		// Afficher la main mise à jour
		afficherMain(joueurId);
		
		// Proposer de visiter le marché (1 chance sur 3)
		if (Math.random() < 0.33) {
			System.out.println("\nUn marché est disponible!");
			System.out.println("Voulez-vous visiter le marché ? (o/n)");
			String reponseMarche = scanner.nextLine();
			
			if (reponseMarche.equalsIgnoreCase("o")) {
				gererMarche(joueurId);
				// Afficher la main mise à jour après le marché
				afficherMain(joueurId);
			}
		}
		
		// Permettre de jouer jusqu'à 2 cartes
		int cartesJouees = 0;
		boolean continuerAJouer = true;
		
		while (cartesJouees < 2 && continuerAJouer && !controlJeu.getJoueur(joueurId).getMain().isEmpty()) {
			System.out.println("\nVoulez-vous jouer une carte ? (" + (cartesJouees + 1) + "/2) (o/n)");
			String reponse = scanner.nextLine();
			
			if (reponse.equalsIgnoreCase("o")) {
				System.out.print("Choisissez une carte à jouer (index) : ");
				int indexCarte = scanner.nextInt();
				scanner.nextLine(); // Consommer le saut de ligne
				
				// Vérifier que l'index est valide
				while (indexCarte < 0 || indexCarte >= controlJeu.getJoueur(joueurId).getMain().size()) {
					System.out.print("Index invalide. Choisissez une carte à jouer (0-" + 
							(controlJeu.getJoueur(joueurId).getMain().size() - 1) + ") : ");
					indexCarte = scanner.nextInt();
					scanner.nextLine(); // Consommer le saut de ligne
				}
				
				// Jouer la carte
				controlJeu.jouerCarte(joueurId, indexCarte);
				System.out.println("Carte jouée avec succès!");
				cartesJouees++;
				
				// Afficher l'état du joueur et sa main après avoir joué la carte
				afficherEtatJoueur(joueurId);
				afficherMain(joueurId);
			} else {
				continuerAJouer = false;
			}
		}
		
		// Gérer la taille de la main (doit être exactement 4 cartes)
		gererTailleMain(joueurId);
		
		System.out.println("\nFin du tour du joueur " + joueurId);
	}
	
	/**
	 * @brief Gère la taille de la main (doit être exactement 4 cartes)
	 */
	private void gererTailleMain(int joueurId) {
		int tailleCourante = controlJeu.getJoueur(joueurId).getMain().size();
		
		// Si le joueur a plus de 4 cartes, il doit en défausser
		if (tailleCourante > 4) {
			System.out.println("\nVous avez " + tailleCourante + " cartes en main, mais le maximum est de 4.");
			System.out.println("Vous devez défausser " + (tailleCourante - 4) + " carte(s).");
			
			for (int i = 0; i < tailleCourante - 4; i++) {
				afficherMain(joueurId);
				System.out.print("Choisissez une carte à défausser (index) : ");
				int indexDefausse = scanner.nextInt();
				scanner.nextLine(); // Consommer le saut de ligne
				
				// Vérifier que l'index est valide
				while (indexDefausse < 0 || indexDefausse >= controlJeu.getJoueur(joueurId).getMain().size()) {
					System.out.print("Index invalide. Choisissez une carte à défausser (0-" + 
							(controlJeu.getJoueur(joueurId).getMain().size() - 1) + ") : ");
					indexDefausse = scanner.nextInt();
					scanner.nextLine(); // Consommer le saut de ligne
				}
				
				// Défausser la carte
				controlJeu.defausserCarte(joueurId, indexDefausse);
				System.out.println("Carte défaussée.");
			}
		}
		// Si le joueur a moins de 4 cartes, il pioche jusqu'à en avoir 4
		else if (tailleCourante < 4) {
			System.out.println("\nVous avez " + tailleCourante + " cartes en main, le minimum est de 4.");
			System.out.println("Vous piochez " + (4 - tailleCourante) + " carte(s) supplémentaire(s).");
			
			for (int i = 0; i < 4 - tailleCourante; i++) {
				controlJeu.piocherCarte(joueurId);
			}
			
			afficherMain(joueurId);
		}
	}

	/**
	 * @brief Affiche les résultats de la partie
	 */
	public void afficherResultats() {
		System.out.println("\n=== Résultats de la partie ===");
		
		// Afficher l'état final des deux joueurs
		afficherEtatJoueur(1);
		afficherEtatJoueur(2);
		
		// Déterminer le gagnant
		String gagnant = "";
		String raisonVictoire = "";
		
		if (controlJeu.getJoueur(2).getPointsDeVie() <= 0 || controlJeu.getJoueur(1).getPopularite() >= 5) {
			gagnant = controlJeu.getJoueur(1).getNom();
			if (controlJeu.getJoueur(2).getPointsDeVie() <= 0) {
				raisonVictoire = "a réduit les points de vie de son adversaire à 0";
			} else {
				raisonVictoire = "a atteint 5 points de popularité";
			}
		} else if (controlJeu.getJoueur(1).getPointsDeVie() <= 0 || controlJeu.getJoueur(2).getPopularite() >= 5) {
			gagnant = controlJeu.getJoueur(2).getNom();
			if (controlJeu.getJoueur(1).getPointsDeVie() <= 0) {
				raisonVictoire = "a réduit les points de vie de son adversaire à 0";
			} else {
				raisonVictoire = "a atteint 5 points de popularité";
			}
		}
		
		System.out.println("\nLe gagnant est : " + gagnant + " qui " + raisonVictoire + " !");
		System.out.println("Félicitations !");
	}

	/**
	 * @brief Lance le jeu
	 */
	public void lancerJeu() {
		System.out.println("\n=== Le jeu des Pirates commence ! ===");
		
		// Afficher les règles
		afficherRegles();
		
		// Configuration des joueurs
		configurerJoueurs();
		
		// Initialiser les mains des joueurs
		initialiserMains();
		
		// Boucle principale du jeu
		boolean partieEnCours = true;
		while (partieEnCours) {
			jouerTour(1);
			if (controlJeu.verifierVictoire()) {
				partieEnCours = false;
				break;
			}

			jouerTour(2);
			if (controlJeu.verifierVictoire()) {
				partieEnCours = false;
			}
		}
		
		// Afficher les résultats
		afficherResultats();
		
		System.out.println("\n=== La partie est terminée ! ===");
	}
}
