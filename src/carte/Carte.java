package carte;

/**
 * @brief Classe représentant une carte du jeu des Pirates
 * 
 * Cette classe représente les données d'une carte, conformément au modèle ECB.
 */
public class Carte {
	
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

	// Note: Les constructeurs spécifiques comme celui pour les cartes de trésor ou de soin
	// ont été supprimés car ils utilisaient les anciennes valeurs d'énumération.
	// Ces constructeurs sont maintenant implémentés dans les classes dérivées.
	
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
	    
	    if (this instanceof CarteOffensive) {
	        CarteOffensive carteOffensive = (CarteOffensive) this;
	        if (carteOffensive.estAttaqueDirecte()) {
	            sb.append("\nDégâts infligés: ").append(this.valeur);
	            sb.append("\nDégâts subis: ").append(this.valeurSecondaire);
	        } else if (carteOffensive.estSoin()) {
	            sb.append("\nVie gagnée: ").append(this.vieGagne);
	        }
	    } else if (this instanceof CarteStrategique) {
	        CarteStrategique carteStrat = (CarteStrategique) this;
	        if (carteStrat.estPopularite()) {
	            sb.append("\nPopularité: ").append(this.valeur);
	            sb.append("\nDégâts subis: ").append(this.valeurSecondaire);
	        } else if (carteStrat.estTresor()) {
	            if (this.orGagne > 0) sb.append("\nOr gagné: ").append(this.orGagne);
	            if (this.orPerdu > 0) sb.append("\nOr perdu: ").append(this.orPerdu);
	        }
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
	 * @brief Vérifie si la carte est une carte offensive (pour compatibilité)
	 */
	public boolean estCarteAttaque() {
		return this instanceof CarteOffensive && ((CarteOffensive) this).estAttaqueDirecte();
	}

	/**
	 * @brief Vérifie si la carte est une carte de popularité (pour compatibilité)
	 */
	public boolean estCartePopularite() {
		return this instanceof CarteStrategique && ((CarteStrategique) this).estPopularite();
	}

	/**
	 * @brief Vérifie si la carte est une carte spéciale (pour compatibilité)
	 */
	public boolean estCarteSpeciale() {
		return this instanceof CarteStrategique && ((CarteStrategique) this).estSpeciale();
	}

	/**
	 * @brief Vérifie si la carte est une carte passive (pour compatibilité)
	 */
	public boolean estCartePassive() {
		return this instanceof CarteStrategique && ((CarteStrategique) this).estPassive();
	}
	
	/**
	 * @brief Vérifie si la carte est une carte de trésor (pour compatibilité)
	 */
	public boolean estCarteTresor() {
		return this instanceof CarteStrategique && ((CarteStrategique) this).estTresor();
	}
	
	/**
	 * @brief Vérifie si la carte est une carte de soin (pour compatibilité)
	 */
	public boolean estCarteSoin() {
		return this instanceof CarteOffensive && ((CarteOffensive) this).estSoin();
	}
	
	/**
	 * @brief Récupère les points de dégâts (pour compatibilité)
	 */
	public int getDegats() {
		if (this instanceof CarteOffensive && ((CarteOffensive) this).estAttaqueDirecte()) {
			return this.valeur;
		}
		return 0;
	}
	
	/**
	 * @brief Récupère les points de dégâts infligés (pour compatibilité)
	 */
	public int getDegatsInfliges() {
		if (this instanceof CarteOffensive && ((CarteOffensive) this).estAttaqueDirecte()) {
			return this.valeur;
		}
		return 0;
	}
	
	/**
	 * @brief Récupère les points de dégâts subis (pour compatibilité)
	 */
	public int getDegatsSubis() {
		if (this instanceof CarteOffensive && ((CarteOffensive) this).estAttaqueDirecte()) {
			return this.valeurSecondaire;
		}
		return 0;
	}
	
	/**
	 * @brief Récupère les points de popularité (pour compatibilité)
	 */
	public int getPopularite() {
		if (this instanceof CarteStrategique && ((CarteStrategique) this).estPopularite()) {
			return this.valeur;
		}
		return 0;
	}
	
	/**
	 * @brief Récupère les points de dégâts subis par le joueur (pour compatibilité)
	 */
	public int getDegatsSubisPopularite() {
		if (this instanceof CarteStrategique && ((CarteStrategique) this).estPopularite()) {
			return this.valeurSecondaire;
		}
		return 0;
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
