package joueur;

import java.util.ArrayList;
import java.util.List;
import carte.Carte;

/**
 * @brief Classe représentant un joueur dans le jeu des Pirates
 * 
 * Cette classe représente les données d'un joueur, conformément au modèle ECB.
 */
public class Joueur {

	/** Nom du joueur */
	private String nom;
	/** Pirate associé au joueur */
	private Pirate pirate;
	/** Points de vie du joueur (max 5) */
	private Integer vie;
	/** Points de popularité du joueur (max 5) */
	private Integer popularite;
	/** Quantité d'or possédée par le joueur */
	private Integer or;
	/** Nombre de cartes en main */
	private Integer nbCartes;
	/** Main du joueur (cartes détenues) */
	private List<Carte> main;
	
	/**
	 * @brief Constructeur d'un joueur
	 * 
	 * @param nom Nom du joueur
	 * @param pirate Pirate associé au joueur
	 */
	public Joueur(String nom, Pirate pirate) {
		this.nom = nom;
		this.pirate = pirate;
		
		this.vie = 5;
		this.popularite = 0;
		this.or = 0;
		this.nbCartes = 0;
		this.main = new ArrayList<Carte>(4);
	}

	/**
	 * @brief Récupère les points de vie du joueur
	 * @return Points de vie
	 */
	public int getPointsDeVie() {
		return vie;
	}

	/**
	 * @brief Définit les points de vie du joueur
	 * @param vie Nouveaux points de vie
	 */
	public void setVie(Integer vie) {
		this.vie = vie;
	}

	/**
	 * @brief Récupère les points de popularité du joueur
	 * @return Points de popularité
	 */
	public int getPopularite() {
		return popularite;
	}

	/**
	 * @brief Définit les points de popularité du joueur
	 * @param popularite Nouveaux points de popularité
	 */
	public void setPopularite(Integer popularite) {
		this.popularite = popularite;
	}

	/**
	 * @brief Récupère la quantité d'or du joueur
	 * @return Quantité d'or
	 */
	public Integer getOr() {
		return or;
	}

	/**
	 * @brief Définit la quantité d'or du joueur
	 * @param or Nouvelle quantité d'or
	 */
	public void setOr(Integer or) {
		this.or = or;
	}

	/**
	 * @brief Récupère le nombre de cartes en main
	 * @return Nombre de cartes
	 */
	public Integer getNbCartes() {
		return nbCartes;
	}

	/**
	 * @brief Définit le nombre de cartes en main
	 * @param nbCartes Nouveau nombre de cartes
	 */
	public void setNbCartes(Integer nbCartes) {
		this.nbCartes = nbCartes;
	}

	/**
	 * @brief Récupère le nom du joueur
	 * @return Nom du joueur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @brief Récupère le pirate associé au joueur
	 * @return Pirate du joueur
	 */
	public Pirate getPirate() {
		return pirate;
	}
	
	/**
	 * @brief Le joueur perd des points de vie
	 * @param points Nombre de points à perdre
	 */
	public void perdrePV(int points) {
		this.vie -= points;
		if (this.vie < 0) {
			this.vie = 0;
		}
	}
	
	/**
	 * @brief Le joueur gagne des points de vie
	 * @param points Nombre de points à gagner
	 */
	public void gagnerPointsDeVie(int points) {
		this.vie += points;
		if (this.vie > 5) {
			this.vie = 5;
		}
	}
	
	/**
	 * @brief Le joueur perd des points de popularité
	 * @param points Nombre de points à perdre
	 */
	public void perdrePopularite(int points) {
		this.popularite -= points;
		if (this.popularite < 0) {
			this.popularite = 0;
		}
	}
	
	/**
	 * @brief Le joueur gagne des points de popularité
	 * @param points Nombre de points à gagner
	 */
	public void gagnerPopularite(int points) {
		this.popularite += points;
		if (this.popularite > 5) {
			this.popularite = 5;
		}
	}
	
	/**
	 * @brief Le joueur perd de l'or
	 * @param montant Montant d'or à perdre
	 * @return true si le joueur avait assez d'or, false sinon
	 */
	public boolean perdreOr(int montant) {
		if (this.or >= montant) {
			this.or -= montant;
			return true;
		}
		return false;
	}
	
	/**
	 * @brief Le joueur gagne de l'or
	 * @param montant Montant d'or à gagner
	 */
	public void gagnerOr(int montant) {
		this.or += montant;
	}
	
	/**
	 * @brief Méthode pour accéder au personnage (pour compatibilité)
	 * @return Le pirate du joueur
	 */
	public Pirate getPersonnage() {
		return this.pirate;
	}
	
	/**
	 * @brief Ajoute une carte à la main du joueur
	 * @param carte Carte à ajouter
	 */
	public void ajouterCarte(Carte carte) {
		main.add(carte);
		nbCartes = main.size();
	}
	
	/**
	 * @brief Retire une carte de la main du joueur
	 * 
	 * @param carte Carte à retirer
	 * @return true si la carte a été retirée, false sinon
	 */
	public boolean retirerCarte(Carte carte) {
		if (main.contains(carte)) {
			main.remove(carte);
			nbCartes = main.size();
			return true;
		}
		return false;
	}
	
	/**
	 * @brief Récupère la main du joueur
	 * @return Liste des cartes en main
	 */
	public List<Carte> getMain() {
		return main;
	}
}
