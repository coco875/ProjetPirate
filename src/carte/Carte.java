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
		POPULAIRE, ATTAQUE, SPECIALE, PASSIVE;
	}
	
	/** Type de la carte */
	private TypeCarte type;
	/** Nom de la carte */
	private String nomCarte;
	/** Description de la carte */
	private String Description;
	/** Valeur générique de la carte (peut représenter des points d'attaque ou de popularité) */
	private int valeur;
	/** Identifiant unique de la carte */
	private int id;
	/** Coût de la carte pour l'achat au marché */
	private int cout;

	/** Compteur statique pour générer les IDs uniques */
	private static int compteurId = 0;
	
	/**
	 * @brief Constructeur complet pour une carte
	 */
	public Carte(TypeCarte type, String nomCarte, String description, int valeur, int cout) {
		this.type = type;
		this.nomCarte = nomCarte;
		this.Description = description;
		this.valeur = valeur;
		this.cout = cout;
		// Générer un ID unique par incrémentation
		this.id = ++compteurId;
	}
	
	/**
	 * @brief Constructeur sans coût pour une carte
	 */
	public Carte(TypeCarte type, String nomCarte, String description, int valeur) {
		this(type, nomCarte, description, valeur, 10); // Coût par défaut à 10
	}
	
	/**
	 * @brief Constructeur simplifié pour une carte
	 */
	public Carte(TypeCarte type, String nomCarte, String description) {
		this(type, nomCarte, description, 0, 10); // Valeur par défaut à 0, coût à 10
	}
	
	/**
	 * @brief Constructeur pour carte créée depuis un JSON
	 */
	public Carte(String jsonCarte, int idCarte, TypeCarte type) {
		this(type, jsonCarte, "ID: " + idCarte, 0, 10); // Valeur et coût par défaut
		this.id = idCarte;
	}
	
	/**
	 * @brief Récupère la valeur de la carte
	 */
	public int getValeur() {
		return valeur;
	}
	
	/**
	 * @brief Définit la valeur de la carte
	 */
	public void setValeur(int valeur) {
		this.valeur = valeur;
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
		return this.nomCarte + "\n" + this.Description;
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
}
