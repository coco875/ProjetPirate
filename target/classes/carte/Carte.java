package carte;

/**
 * Classe représentant une carte du jeu des Pirates
 */
public class Carte {
	
	private TypeCarte type;
	private String nomCarte;
	private String Description;
	private int valeur;             // Valeur principale (points d'attaque ou de popularité)
	private int valeurSecondaire;   // Valeur secondaire (dégâts subis ou effet secondaire)
	private int id;                 // Identifiant unique de la carte
	private int cout;               // Coût de la carte pour l'achat au marché
	private int orGagne;            // Or gagné en jouant cette carte
	private int orPerdu;            // Or perdu en jouant cette carte
	private int orVole;             // Or volé à l'adversaire en jouant cette carte
	private int vieGagne;           // Points de vie gagnés en jouant cette carte

	private static int compteurId = 0;
	
	// Données pour l'effet de la carte
	public static class EffetCarte {
		public int degatsInfliges = 0;
		public int degatsSubis = 0;
		public int populariteGagnee = 0;
		public int vieGagnee = 0;
		public int orGagne = 0;
		public int orPerdu = 0;
		public int orVole = 0;
		public String effetSpecial = null;
		public int dureeEffet = 0;
		public boolean estAttaque = false;
		public boolean estPopularite = false;
		public boolean estSpeciale = false;
		public boolean estPassive = false;
		public boolean estTresor = false;
		public boolean estSoin = false;
	}
	
	/**
	 * Constructeur complet
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
	 * Constructeur sans coût
	 */
	public Carte(TypeCarte type, String nomCarte, String description, int valeur, int valeurSecondaire) {
		this(type, nomCarte, description, valeur, valeurSecondaire, 10); // Coût par défaut à 10
	}
	
	/**
	 * Constructeur simplifié
	 */
	public Carte(TypeCarte type, String nomCarte, String description) {
		this(type, nomCarte, description, 0, 0, 10); // Valeurs par défaut à 0, coût à 10
	}
	
	/**
	 * Constructeur pour carte créée depuis un JSON
	 */
	public Carte(String jsonCarte, int idCarte, TypeCarte type) {
		this(type, jsonCarte, "ID: " + idCarte, 0, 0, 10); // Valeurs et coût par défaut
		this.id = idCarte;
	}

	/**
	 * Méthode centrale pour obtenir tous les effets d'une carte
	 * Cette méthode remplace toutes les méthodes spécifiques
	 * @return Un objet contenant tous les effets et caractéristiques de la carte
	 */
	public EffetCarte effetCarte() {
		EffetCarte effet = new EffetCarte();
		
		// Calcul des effets en fonction du type de carte
		if (this instanceof CarteOffensive) {
			CarteOffensive carteOff = (CarteOffensive) this;
			if (carteOff.estAttaqueDirecte()) {
				effet.degatsInfliges = this.valeur;
				effet.degatsSubis = this.valeurSecondaire;
				effet.estAttaque = true;
			} else if (carteOff.estSoin()) {
				effet.vieGagnee = this.vieGagne;
				effet.estSoin = true;
			} else if (carteOff.estTresorOffensif()) {
				effet.orVole = this.orVole;
			}
		} else if (this instanceof CarteStrategique) {
			CarteStrategique carteStrat = (CarteStrategique) this;
			if (carteStrat.estPopularite()) {
				effet.populariteGagnee = this.valeur;
				effet.degatsSubis = this.valeurSecondaire;
				effet.estPopularite = true;
			} else if (carteStrat.estTresor()) {
				effet.orGagne = this.orGagne;
				effet.orPerdu = this.orPerdu;
				effet.estTresor = true;
			} else if (carteStrat.estSpeciale()) {
				effet.effetSpecial = carteStrat.getTypeEffet();
				effet.dureeEffet = 1; // Par défaut, effet immédiat
				effet.estSpeciale = true;
			} else if (carteStrat.estPassive()) {
				effet.effetSpecial = carteStrat.getTypeEffet();
				effet.dureeEffet = carteStrat.getDuree();
				effet.estPassive = true;
			}
		}
		
		return effet;
	}

	// Getters et Setters de base
	
	public int getValeur() {
		return valeur;
	}
	
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	
	public int getValeurSecondaire() {
		return valeurSecondaire;
	}
	
	public void setValeurSecondaire(int valeurSecondaire) {
		this.valeurSecondaire = valeurSecondaire;
	}
	
	public int getCout() {
		return cout;
	}
	
	public void setCout(int cout) {
		this.cout = cout;
	}
	
	public int getId() {
		return id;
	}
	
	/**
	 * Retourne une représentation textuelle de la carte
	 */
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append(this.nomCarte).append("\n").append(this.Description);
	    
	    EffetCarte effet = effetCarte();
	    
	    if (effet.degatsInfliges > 0) {
	        sb.append("\nDégâts infligés: ").append(effet.degatsInfliges);
	    }
	    if (effet.degatsSubis > 0) {
	        sb.append("\nDégâts subis: ").append(effet.degatsSubis);
	    }
	    if (effet.populariteGagnee > 0) {
	        sb.append("\nPopularité: ").append(effet.populariteGagnee);
	    }
	    if (effet.vieGagnee > 0) {
	        sb.append("\nVie gagnée: ").append(effet.vieGagnee);
	    }
	    if (effet.orGagne > 0) {
	        sb.append("\nOr gagné: ").append(effet.orGagne);
	    }
	    if (effet.orPerdu > 0) {
	        sb.append("\nOr perdu: ").append(effet.orPerdu);
	    }
	    if (effet.orVole > 0) {
	        sb.append("\nOr volé: ").append(effet.orVole);
	    }
	    if (effet.effetSpecial != null) {
	        sb.append("\nEffet spécial: ").append(effet.effetSpecial);
	        if (effet.dureeEffet > 1) {
	            sb.append(" (Durée: ").append(effet.dureeEffet).append(" tours)");
	        }
	    }
	    
	    return sb.toString();
	}
	
	public void setDescription(String description) {
		Description = description;
	}
	
	public String getDescription() {
		return Description;
	}
	
	public TypeCarte getType() {
		return type;
	}
	
	public void setNomCarte(String nomCarte) {
		this.nomCarte = nomCarte;
	}
	
	public String getNomCarte() {
		return nomCarte;
	}
	
	// Méthodes d'accès simplifiées utilisant effetCarte()
	
	public int getDegats() {
		return effetCarte().degatsInfliges;
	}
	
	public int getDegatsInfliges() {
		return effetCarte().degatsInfliges;
	}
	
	public int getDegatsSubis() {
		return effetCarte().degatsSubis;
	}
	
	public int getPopularite() {
		return effetCarte().populariteGagnee;
	}
	
	public int getDegatsSubisPopularite() {
		return effetCarte().estPopularite ? effetCarte().degatsSubis : 0;
	}
	
	// Getters et Setters pour les attributs monétaires et de vie
	
	public int getOrGagne() {
		return orGagne;
	}
	
	public void setOrGagne(int orGagne) {
		this.orGagne = orGagne;
	}
	
	public int getOrPerdu() {
		return orPerdu;
	}
	
	public void setOrPerdu(int orPerdu) {
		this.orPerdu = orPerdu;
	}
	
	public int getOrVole() {
		return orVole;
	}
	
	public void setOrVole(int orVole) {
		this.orVole = orVole;
	}
	
	public int getVieGagne() {
		return vieGagne;
	}
	
	public void setVieGagne(int vieGagne) {
		this.vieGagne = vieGagne;
	}
}
