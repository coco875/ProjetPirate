package boundary;

import java.util.List;
import java.util.Scanner;
import java.io.File;

import carte.Carte;
import carte.CarteOffensive;
import carte.CarteStrategique;
import controllers.ControlJeu;
import controllers.ControlJoueur;
import controllers.ControlMarche;
import controllers.ControlPioche;
import joueur.Joueur;
import joueur.Pirate;
import joueur.ParserPirate;

/**
 * @brief Classe boundary pour l'interface en mode console
 */
public class BoundaryJeu {
	protected ControlJeu controlJeu;
	protected Scanner scan;
	protected ControlMarche controlMarche;
	
	public BoundaryJeu(ControlJeu controlJeu, ControlMarche controlMarche) {
		this.controlJeu = controlJeu;
		this.controlMarche = controlMarche;
		scan = new Scanner(System.in);
	}
	
	/**
	 * @brief Affiche un message d'accueil
	 */
	public void afficherMessage() {
		System.out.println("Bienvenue dans le jeu des Pirates !");
		System.out.println("----------------------------------");
	}
	
	/**
	 * @brief Lance le jeu
	 */
	public void lancerJeu() {
		afficherMessage();
		afficherRegles();
		demarrerJeu();
	}
	
	/**
	 * @brief Affiche les règles du jeu
	 */
	public void afficherRegles() {
		System.out.println("\n=== Règles du jeu ===");
		System.out.println("Le jeu se déroule au tour par tour entre deux joueurs.");
		System.out.println("Chaque joueur possède 5 points de vie, 0 point de popularité et 3 pièces d'or au départ.");
		System.out.println("À chaque tour, vous piochez une carte et choisissez une carte à jouer parmi votre main.");
		System.out.println("Les cartes peuvent être offensives (attaque, soin) ou stratégiques (popularité, effet passif).");
		System.out.println("Le premier joueur qui arrive à 0 point de vie perd la partie.");
		System.out.println("Bon jeu !\n");
	}
	
	/**
	 * @brief Démarre le jeu
	 */
	public void demarrerJeu() {
		// Premier joueur
		Pirate nomPirate1 = demanderPirate(1);
		// Deuxième joueur
		Pirate nomPirate2 = demanderPirate(2);
		controlJeu.initialiserJeu(nomPirate1, nomPirate2);
		
		// Démarrage de la partie
		jouerPartie();
	}
	
	/**
	 * @brief Demande le nom du pirate d'un joueur
	 * 
	 * @param numeroJoueur Numéro du joueur
	 * @return Nom du pirate
	 */
	protected Pirate demanderPirate(int numeroJoueur) {
		// Afficher la liste des pirates disponibles
		List<Pirate> piratesDisponibles = getPiratesDisponibles();
		
		if (!piratesDisponibles.isEmpty()) {
			System.out.println("\n=== Pirates disponibles ===");
			for (int i = 0; i < piratesDisponibles.size(); i++) {
				Pirate pirate = piratesDisponibles.get(i);
				System.out.println((i + 1) + ") " + pirate.getNom());
				System.out.println("   " + pirate.getDescription());
				System.out.println("   Popularité: " + pirate.getPopularite() + ", Vie: " + pirate.getVie());
				System.out.println();
			}
			
			System.out.print("Choisissez un pirate pour le joueur " + numeroJoueur + " (1-" + piratesDisponibles.size() + ") : ");
			int choix = -1;
			while (choix < 1 || choix > piratesDisponibles.size()) {
				try {
					choix = Integer.parseInt(scan.nextLine().trim());
					if (choix < 1 || choix > piratesDisponibles.size()) {
						System.out.print("Choix invalide. Veuillez réessayer (1-" + piratesDisponibles.size() + ") : ");
					}
				} catch (NumberFormatException e) {
					System.out.print("Veuillez entrer un nombre entre 1 et " + piratesDisponibles.size() + " : ");
				}
			}
			
			return piratesDisponibles.get(choix - 1);
		} else {
			// Si aucun pirate n'est disponible, revenir à la méthode originale
			System.out.print("Nom du pirate du joueur " + numeroJoueur + " : ");
			//return scan.nextLine();
			return null;
		}
	}
	
	/**
	 * @brief Récupère la liste des pirates disponibles
	 * 
	 * @return Liste des pirates disponibles
	 */
	public static List<Pirate> getPiratesDisponibles() {
		File repertoirePirates = new File("src/ressources/pirates");
		return ParserPirate.chargerPirates(repertoirePirates);
	}
	
	/**
	 * @brief Gère le déroulement d'une partie
	 */
	public void jouerPartie() {
		boolean finPartie = false;
		int tourJoueur = 0;
		boolean continuerIteration = true;
		int nbTour = 0;
		int nbToursMax = 20;
		
		while (!finPartie && continuerIteration) {
			nbTour++;
			
			// Déterminer le joueur actif
			ControlJoueur joueurActif = controlJeu.getJoueur(tourJoueur % 2);
			Joueur j = joueurActif.getJoueur();
			
			// Afficher l'état du jeu
			System.out.println("\n=== Tour de " + j.getNom() + " ===");
			System.out.println("Vie: " + j.getPointsDeVie() + "/5, Popularité: " + j.getPopularite() + "/5, Or: " + j.getOr());
			
			// Piocher une carte
			Carte cartePiochee = joueurActif.piocher();
			System.out.println("Vous avez pioché : " + cartePiochee);
			
			// Afficher la main du joueur
			System.out.println("\nVotre main :");
			List<Carte> main = joueurActif.afficherMain();
			
			// Demander quelle carte jouer
			System.out.println("\nQuelle carte voulez-vous jouer ? (1-" + main.size() + ", 0 pour passer)");
			int choix = scan.nextInt();
			scan.nextLine(); // Consommer la nouvelle ligne
			
			if (choix > 0 && choix <= main.size()) {
				// Jouer la carte choisie
				Carte carteChoisie = main.get(choix - 1);
				System.out.println("Vous jouez : " + carteChoisie);
				
				jouerCarte(carteChoisie);
				
				// Retirer la carte de la main
				joueurActif.retirerCarte(carteChoisie);
			} else {
				System.out.println("Vous passez votre tour.");
			}
			
			// Appliquer les effets des cartes sur le plateau
			controlJeu.appliquerEffetsCartes();
			
			
			
			// Vérifier si la partie est terminée
			finPartie = controlJeu.verifierFinPartie();
			
			if (!finPartie) {
				finPartie = controlJeu.estNbToursMaxAtteint(nbTour, nbToursMax);
			}
			
			
			// Défausser les cartes du plateau à la fin du tour
			controlJeu.defausserCartesPlateau();
			
			// Passer au joueur suivant
			tourJoueur++;
			controlJeu.passerAuJoueurSuivant();
			
			// Remplir la pioche si elle est vide
			controlJeu.verifierPiocheNonVide();
			
			// Si ce n'est pas la fin de partie, demander si on continue l'itération
			if (!finPartie && (tourJoueur % 2 == 0)) { // À chaque fin d'itération (après que les deux joueurs ont joué)
				continuerIteration = demanderContinuerIteration();
			}
		}
		
		// Afficher le résultat final
		if (finPartie) {
			if (controlJeu.estNbToursMaxAtteint(nbTour, nbToursMax)) {
				System.out.println("\n\n\n== Nombre de tours maximum (" + nbToursMax + ") atteint ==");
			}
			afficherResultatFinal();
		} else {
			System.out.println("\n=== Partie interrompue ===");
			System.out.println("Vous avez choisi d'arrêter la partie.");
		}
	}
	
	/**
	 * @brief Demande à l'utilisateur s'il souhaite continuer l'itération
	 * 
	 * @return true si l'utilisateur souhaite continuer, false sinon
	 */
	private boolean demanderContinuerIteration() {
		System.out.println("\n=== Fin d'itération ===");
		System.out.println("Continuer l'itération ? (O/N)");
		String reponse = scan.nextLine().trim().toUpperCase();
		return reponse.equals("O") || reponse.equals("OUI");
	}
	
	/**
	 * @brief Joue une carte en fonction de son type
	 * 
	 * @param carte La carte à jouer
	 * @param joueurActif Contrôleur du joueur actif
	 */
	private void jouerCarte(Carte carte) {
		System.out.println("Description : " + carte.getDescription());
		
		 // Utiliser effetCarte pour déterminer le type et les effets de la carte
		Carte.EffetCarte effet = carte.effetCarte();
		
		if (effet.degatsInfliges > 0) {
			System.out.println("Vous attaquez ! Dégâts infligés : " + effet.degatsInfliges);
			if (effet.degatsSubis > 0) {
				System.out.println("Vous subissez " + effet.degatsSubis + " points de dégâts en retour.");
			}
		} else if (effet.vieGagne > 0) {
			System.out.println("Vous vous soignez ! Points de vie gagnés : " + effet.vieGagne);
		} else if (effet.populariteGagnee > 0) {
			System.out.println("Vous gagnez en popularité ! Points gagnés : " + effet.populariteGagnee);
			if (effet.degatsSubis > 0) {
				System.out.println("Vous subissez " + effet.degatsSubis + " points de dégâts.");
			}
		} else if (effet.orGagne > 0) {
			System.out.println("Vous gagnez " + effet.orGagne + " or !");
		} else {
			System.out.println("Type de carte non reconnu !");
		}

		controlJeu.jouerCarte(carte);
	}
	
	/**
	 * @brief Affiche le résultat final de la partie
	 */
	private void afficherResultatFinal() {
		Joueur vainqueur = controlJeu.determinerVainqueur();
		System.out.println("\n=== Fin de la partie ===");
		
		if (vainqueur != null) {
			System.out.println("Le vainqueur est : " + vainqueur.getNom() + " (" + vainqueur.getNom() + ")");
		} else {
			System.out.println("Match nul !");
		}
		
		// Afficher les statistiques des joueurs
		for (int i = 0; i < 2; i++) {
			Joueur j = controlJeu.getJoueur(i).getJoueur();
			System.out.println(j.getNom() + " (" + j.getNom() + "):");
			System.out.println("  Vie: " + j.getPointsDeVie() + "/5");
			System.out.println("  Popularité: " + j.getPopularite() + "/5");
			System.out.println("  Or: " + j.getOr());
		}
	}
	
	/**
	 * @brief Ferme les ressources utilisées
	 */
	public void fermer() {
		if (scan != null) {
			scan.close();
		}
	}
}
