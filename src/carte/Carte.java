package carte;

/**
 * Classe représentant une carte du jeu des Pirates
 */
public abstract class Carte {
	
	private TypeCarte type;
	private String nomCarte;
	private String description;
	private int cout;               // Coût de la carte pour l'achat au marché
	
	// Données pour l'effet de la carte
	public static class EffetCarte {
		public int degatsInfliges = 0;
		public int degatsSubis = 0;
		public int populariteGagnee = 0;
		public int vieGagne = 0;
		public int orGagne = 0;
	}
	
	/**
	 * Constructeur complet
	 */
	public Carte(TypeCarte type, String nomCarte, String description, int cout) {
		this.type = type;
		this.nomCarte = nomCarte;
		this.description = description;
		this.cout = cout;
	}
	
	/**
	 * Constructeur sans coût
	 */
	public Carte(TypeCarte type, String nomCarte, String description) {
		this(type, nomCarte, description, 10); // Coût par défaut à 10
	}


	/**
	 * Méthode centrale pour obtenir tous les effets d'une carte
	 * Cette méthode sera redéfinie par les classes dérivées
	 * @return Un objet contenant tous les effets et caractéristiques de la carte
	 */
	public abstract EffetCarte effetCarte();

	// Getters et Setters de base
	
	public int getCout() {
		return cout;
	}
	
	public void setCout(int cout) {
		this.cout = cout;
	}
	
	/**
	 * Retourne une représentation textuelle de la carte
	 */
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append(this.nomCarte).append("\n").append(this.description);
	    
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
	    if (effet.vieGagne > 0) {
	        sb.append("\nVie gagnée: ").append(effet.vieGagne);
	    }
	    if (effet.orGagne > 0) {
	        sb.append("\nOr gagné: ").append(effet.orGagne);
	    }
	    
	    return sb.toString();
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
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
	
	/**
	 * Getter pour le chemin d'image
	 * @return Le chemin d'accès à l'image de la carte
	 */
	abstract public String getCheminImage();
}
