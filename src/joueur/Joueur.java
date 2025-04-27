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
	public Integer getPointsDeVie() {
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
	public Integer getPopularite() {
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
	 * @throws IllegalStateException si la carte n'est pas dans la main
	 */
	public void retirerCarte(Carte carte) throws IllegalStateException {
		if (main.contains(carte)) {
			main.remove(carte);
			nbCartes = main.size();
		} else {
			throw new IllegalStateException("Carte absente dans la main");
		}
	}
	
	/**
	 * @brief Récupère la main du joueur
	 * @return Liste des cartes en main
	 */
	public List<Carte> getMain() {
		return main;
	}
}
