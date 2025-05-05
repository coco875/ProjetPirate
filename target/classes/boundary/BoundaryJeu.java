package boundary;

import java.util.List;
import java.util.Scanner;

import carte.Carte;
import carte.CarteOffensive;
import carte.CarteStrategique;
import controllers.ControlJeu;
import controllers.ControlJoueur;
import controllers.ControlPioche;
import controllers.ControlMarche;
import jeu.Pioche;
import joueur.Joueur;

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
		// Initialisation des contrôleurs
		controlJeu.initialiserJeu();
		
		// Premier joueur
		String nomJoueur1 = demanderNomJoueur(1);
		String nomPirate1 = demanderNomPirate(1);
		controlJeu.creerJoueur(nomJoueur1, nomPirate1);
		
		// Deuxième joueur
		String nomJoueur2 = demanderNomJoueur(2);
		String nomPirate2 = demanderNomPirate(2);
		controlJeu.creerJoueur(nomJoueur2, nomPirate2);
		
		// Distribution des cartes initiales
		controlJeu.distribuerCartesInitiales();
		
		// Démarrage de la partie
		jouerPartie();
	}
	
	/**
	 * @brief Demande le nom d'un joueur
	 * 
	 * @param numeroJoueur Numéro du joueur
	 * @return Nom du joueur
	 */
	protected String demanderNomJoueur(int numeroJoueur) {
		System.out.print("Nom du joueur " + numeroJoueur + " : ");
		return scan.nextLine();
	}
	
	/**
	 * @brief Demande le nom du pirate d'un joueur
	 * 
	 * @param numeroJoueur Numéro du joueur
	 * @return Nom du pirate
	 */
	protected String demanderNomPirate(int numeroJoueur) {
		System.out.print("Nom du pirate du joueur " + numeroJoueur + " : ");
		return scan.nextLine();
	}
	
	/**
	 * @brief Gère le déroulement d'une partie
	 */
	public void jouerPartie() {
		boolean finPartie = false;
		int tourJoueur = 0;
		boolean continuerIteration = true;
		
		while (!finPartie && continuerIteration) {
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
				
				jouerCarte(carteChoisie, joueurActif);
				
				// Retirer la carte de la main
				joueurActif.retirerCarte(carteChoisie);
			} else {
				System.out.println("Vous passez votre tour.");
			}
			
			// Appliquer les effets des cartes sur le plateau
			controlJeu.appliquerEffetsCartes();
			
			// Vérifier si la partie est terminée
			finPartie = controlJeu.verifierFinPartie();
			
			// Défausser les cartes du plateau à la fin du tour
			controlJeu.defausserCartesPlateau();
			
			// Passer au joueur suivant
			tourJoueur++;
			
			// Si ce n'est pas la fin de partie, demander si on continue l'itération
			if (!finPartie && (tourJoueur % 2 == 0)) { // À chaque fin d'itération (après que les deux joueurs ont joué)
				continuerIteration = demanderContinuerIteration();
			}
		}
		
		// Afficher le résultat final
		if (finPartie) {
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
	private void jouerCarte(Carte carte, ControlJoueur joueurActif) {
		System.out.println("Description : " + carte.getDescription());
		
		// Détermine le type de carte et agit en conséquence
		if (carte instanceof CarteOffensive) {
			CarteOffensive carteOff = (CarteOffensive) carte;
			
			if (carteOff.estAttaqueDirecte()) {
				System.out.println("Vous attaquez ! Dégâts infligés : " + carteOff.getDegatsInfliges());
				System.out.println("Vous subissez " + carteOff.getDegatsSubis() + " points de dégâts en retour.");
			} else if (carteOff.estSoin()) {
				System.out.println("Vous vous soignez ! Points de vie gagnés : " + carteOff.getVieGagne());
			} else if (carteOff.estCoupSpecial()) {
				System.out.println("Vous utilisez un coup spécial ! Effet : " + carteOff.getValeur());
				System.out.println("Coût : " + carteOff.getCoutSpecial() + " or");
			} else if (carteOff.estTresorOffensif()) {
				System.out.println("Vous volez " + carteOff.getOrVole() + " or à votre adversaire !");
			}
			
			controlJeu.ajouterCarteOffensive(carteOff);
		} else if (carte instanceof CarteStrategique) {
			CarteStrategique carteStrat = (CarteStrategique) carte;
			
			if (carteStrat.estPopularite()) {
				System.out.println("Vous gagnez en popularité ! Points gagnés : " + carteStrat.getPopulariteGagnee());
				System.out.println("Vous subissez " + carteStrat.getDegatsSubis() + " points de dégâts.");
			} else if (carteStrat.estPassive()) {
				System.out.println("Vous utilisez une carte passive ! Effet : " + carteStrat.getTypeEffet());
				System.out.println("Durée : " + carteStrat.getDuree() + " tours");
			} else if (carteStrat.estSpeciale()) {
				System.out.println("Vous utilisez une carte spéciale ! Effet : " + carteStrat.getEffetSpecial());
			} else if (carteStrat.estTresor()) {
				if (carteStrat.getOrGagne() > 0) {
					System.out.println("Vous gagnez " + carteStrat.getOrGagne() + " or !");
				}
				if (carteStrat.getOrPerdu() > 0) {
					System.out.println("Vous perdez " + carteStrat.getOrPerdu() + " or !");
				}
			}
			
			controlJeu.ajouterCarteStrategique(carteStrat);
		} else {
			System.out.println("Type de carte non reconnu !");
		}
	}
	
	/**
	 * @brief Affiche le résultat final de la partie
	 */
	private void afficherResultatFinal() {
		Joueur vainqueur = controlJeu.determinerVainqueur();
		System.out.println("\n=== Fin de la partie ===");
		
		if (vainqueur != null) {
			System.out.println("Le vainqueur est : " + vainqueur.getNom() + " (" + vainqueur.getPersonnage().getNom() + ")");
		} else {
			System.out.println("Match nul !");
		}
		
		// Afficher les statistiques des joueurs
		for (int i = 0; i < 2; i++) {
			Joueur j = controlJeu.getJoueur(i).getJoueur();
			System.out.println(j.getNom() + " (" + j.getPersonnage().getNom() + "):");
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
