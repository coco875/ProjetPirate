package carte;

/**
 * @brief Classe représentant une carte du jeu des Pirates
 * 
 * Cette classe représente les données d'une carte, conformément au modèle ECB.
 */
public class Carte {

	/**
	 * @brief Types de cartes disponibles dans le jeu
	 */
	public enum TypeCarte {
		POPULAIRE, ATTAQUE, SPECIALE, PASSIVE, TRESOR, SOIN;
	}
	
	/** Type de la carte */
	private TypeCarte type;
	/** Nom de la carte */
	private String nomCarte;
	/** Description de la carte */
	private String Description;
	/** Valeur principale de la carte (points d'attaque ou de popularité) */
	private int valeur;
	/** Valeur secondaire de la carte (dégâts subis ou effet secondaire) */
	private int valeurSecondaire;
	/** Identifiant unique de la carte */
	private int id;
	/** Coût de la carte pour l'achat au marché */
	private int cout;
	/** Or gagné en jouant cette carte */
	private int orGagne;
	/** Or perdu en jouant cette carte */
	private int orPerdu;
	/** Or volé à l'adversaire en jouant cette carte */
	private int orVole;
	/** Points de vie gagnés en jouant cette carte */
	private int vieGagne;

	/** Compteur statique pour générer les IDs uniques */
	private static int compteurId = 0;
	
	/**
	 * @brief Constructeur complet pour une carte
	 */
	public Carte(TypeCarte type, String nomCarte, String description, int valeur, int valeurSecondaire, int cout) {
		this.type = type;
		this.nomCarte = nomCarte;
		this.Description = description;
		this.valeur = valeur;
		this.valeurSecondaire = valeurSecondaire;
		this.cout = cout;
		 // Valeurs par défaut pour les nouveaux attributs
		this.orGagne = 0;
		this.orPerdu = 0;
		this.orVole = 0;
		this.vieGagne = 0;
		// Générer un ID unique par incrémentation
		this.id = ++compteurId;
	}
	
	/**
	 * @brief Constructeur sans coût pour une carte
	 */
	public Carte(TypeCarte type, String nomCarte, String description, int valeur, int valeurSecondaire) {
		this(type, nomCarte, description, valeur, valeurSecondaire, 10); // Coût par défaut à 10
	}
	
	/**
	 * @brief Constructeur simplifié pour une carte
	 */
	public Carte(TypeCarte type, String nomCarte, String description) {
		this(type, nomCarte, description, 0, 0, 10); // Valeurs par défaut à 0, coût à 10
	}
	
	/**
	 * @brief Constructeur pour carte créée depuis un JSON
	 */
	public Carte(String jsonCarte, int idCarte, TypeCarte type) {
		this(type, jsonCarte, "ID: " + idCarte, 0, 0, 10); // Valeurs et coût par défaut
		this.id = idCarte;
	}
	
	/**
	 * @brief Constructeur pour une carte de trésor
	 */
	public Carte(String nomCarte, String description, int orGagne, int orPerdu, int orVole) {
		this(TypeCarte.TRESOR, nomCarte, description, 0, 0, 10);
		this.orGagne = orGagne;
		this.orPerdu = orPerdu;
		this.orVole = orVole;
	}
	
	/**
	 * @brief Constructeur pour une carte de soin
	 */
	public Carte(String nomCarte, String description, int vieGagne) {
		this(TypeCarte.SOIN, nomCarte, description, 0, 0, 10);
		this.vieGagne = vieGagne;
	}
	
	/**
	 * @brief Récupère la valeur principale de la carte
	 */
	public int getValeur() {
		return valeur;
	}
	
	/**
	 * @brief Définit la valeur principale de la carte
	 */
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	
	/**
	 * @brief Récupère la valeur secondaire de la carte
	 */
	public int getValeurSecondaire() {
		return valeurSecondaire;
	}
	
	/**
	 * @brief Définit la valeur secondaire de la carte
	 */
	public void setValeurSecondaire(int valeurSecondaire) {
		this.valeurSecondaire = valeurSecondaire;
	}
	
	/**
	 * @brief Récupère le coût de la carte
	 */
	public int getCout() {
		return cout;
	}
	
	/**
	 * @brief Définit le coût de la carte
	 */
	public void setCout(int cout) {
		this.cout = cout;
	}
	
	/**
	 * @brief Récupère l'identifiant unique de la carte
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @brief Retourne une représentation textuelle de la carte
	 */
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append(this.nomCarte).append("\n").append(this.Description);
	    
	    if (this.estCarteAttaque()) {
	        sb.append("\nDégâts infligés: ").append(this.valeur);
	        sb.append("\nDégâts subis: ").append(this.valeurSecondaire);
	    } else if (this.estCartePopularite()) {
	        sb.append("\nPopularité: ").append(this.valeur);
	        sb.append("\nDégâts subis: ").append(this.valeurSecondaire);
	    } else if (this.estCarteTresor()) {
	        if (this.orGagne > 0) sb.append("\nOr gagné: ").append(this.orGagne);
	        if (this.orPerdu > 0) sb.append("\nOr perdu: ").append(this.orPerdu);
	        if (this.orVole > 0) sb.append("\nOr volé: ").append(this.orVole);
	    } else if (this.estCarteSoin()) {
	        sb.append("\nVie gagnée: ").append(this.vieGagne);
	    } else if (this.estCarteSpeciale()) {
	        sb.append("\nValeur: ").append(this.valeur);
	    }
	    
	    return sb.toString();
	}
	
	/**
	 * @brief Définit la description de la carte
	 */
	public void setDescription(String description) {
		Description = description;
	}
	
	/**
	 * @brief Récupère la description de la carte
	 */
	public String getDescription() {
		return Description;
	}
	
	/**
	 * @brief Récupère le type de la carte
	 */
	public TypeCarte getType() {
		return type;
	}
	
	/**
	 * @brief Définit le nom de la carte
	 */
	public void setNomCarte(String nomCarte) {
		this.nomCarte = nomCarte;
	}
	
	/**
	 * @brief Récupère le nom de la carte
	 */
	public String getNomCarte() {
		return nomCarte;
	}
	
	/**
	 * @brief Vérifie si la carte est une carte d'attaque
	 */
	public boolean estCarteAttaque() {
		return this.type == TypeCarte.ATTAQUE;
	}

	/**
	 * @brief Vérifie si la carte est une carte de popularité
	 */
	public boolean estCartePopularite() {
		return this.type == TypeCarte.POPULAIRE;
	}

	/**
	 * @brief Vérifie si la carte est une carte spéciale
	 */
	public boolean estCarteSpeciale() {
		return this.type == TypeCarte.SPECIALE;
	}

	/**
	 * @brief Vérifie si la carte est une carte passive
	 */
	public boolean estCartePassive() {
		return this.type == TypeCarte.PASSIVE;
	}
	
	/**
	 * @brief Vérifie si la carte est une carte de trésor
	 */
	public boolean estCarteTresor() {
		return this.type == TypeCarte.TRESOR;
	}
	
	/**
	 * @brief Vérifie si la carte est une carte de soin
	 */
	public boolean estCarteSoin() {
		return this.type == TypeCarte.SOIN;
	}
	
	/**
	 * @brief Récupère les points de dégâts si c'est une carte d'attaque
	 */
	public int getDegats() {
		if (this.type != TypeCarte.ATTAQUE) {
			return 0;
		}
		return this.valeur;
	}
	
	/**
	 * @brief Récupère les points de dégâts infligés si c'est une carte d'attaque
	 */
	public int getDegatsInfliges() {
		if (this.type != TypeCarte.ATTAQUE) {
			return 0;
		}
		return this.valeur;
	}
	
	/**
	 * @brief Récupère les points de dégâts subis si c'est une carte d'attaque
	 */
	public int getDegatsSubis() {
		if (this.type != TypeCarte.ATTAQUE) {
			return 0;
		}
		return this.valeurSecondaire;
	}
	
	/**
	 * @brief Récupère les points de popularité si c'est une carte de popularité
	 */
	public int getPopularite() {
		if (this.type != TypeCarte.POPULAIRE) {
			return 0;
		}
		return this.valeur;
	}
	
	/**
	 * @brief Récupère les points de dégâts subis par le joueur si c'est une carte de popularité
	 */
	public int getDegatsSubisPopularite() {
		if (this.type != TypeCarte.POPULAIRE) {
			return 0;
		}
		return this.valeurSecondaire;
	}
	
	/**
	 * @brief Récupère l'or gagné par la carte
	 */
	public int getOrGagne() {
		return orGagne;
	}
	
	/**
	 * @brief Définit l'or gagné par la carte
	 */
	public void setOrGagne(int orGagne) {
		this.orGagne = orGagne;
	}
	
	/**
	 * @brief Récupère l'or perdu par la carte
	 */
	public int getOrPerdu() {
		return orPerdu;
	}
	
	/**
	 * @brief Définit l'or perdu par la carte
	 */
	public void setOrPerdu(int orPerdu) {
		this.orPerdu = orPerdu;
	}
	
	/**
	 * @brief Récupère l'or volé par la carte
	 */
	public int getOrVole() {
		return orVole;
	}
	
	/**
	 * @brief Définit l'or volé par la carte
	 */
	public void setOrVole(int orVole) {
		this.orVole = orVole;
	}
	
	/**
	 * @brief Récupère les points de vie gagnés par la carte
	 */
	public int getVieGagne() {
		return vieGagne;
	}
	
	/**
	 * @brief Définit les points de vie gagnés par la carte
	 */
	public void setVieGagne(int vieGagne) {
		this.vieGagne = vieGagne;
	}
}
