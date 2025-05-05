package carte;

/**
 * Carte stratégique du jeu (popularité, passive, spéciale, trésor)
 */
public class CarteStrategique extends Carte {
	
	private TypeStrategique typeStrategique;  // Type spécifique de carte stratégique
	private int duree;                        // Durée d'effet en nombre de tours pour les cartes passives
	private String effetSpecial;              // Description de l'effet spécial pour les cartes spéciales
	private boolean estReutilisable;          // Indique si la carte peut être réutilisée
	
	/**
	 * Types spécifiques de cartes stratégiques
	 */
	public enum TypeStrategique {
		POPULARITE,     // Cartes augmentant la popularité
		PASSIVE,        // Cartes à effet passif sur plusieurs tours
		SPECIALE,       // Cartes à effets spéciaux uniques
		TRESOR          // Cartes de trésor (or gagné/perdu)
	}
	
	// Constructeurs
	
	/**
	 * Constructeur pour carte de popularité
	 */
	public CarteStrategique(String nomCarte, String description, int populariteGagnee, int degatsSubis) {
		super(TypeCarte.STRATEGIQUE, nomCarte, description, populariteGagnee, degatsSubis);
		this.typeStrategique = TypeStrategique.POPULARITE;
		this.duree = 1; // Par défaut, effet sur 1 tour
		this.estReutilisable = false;
	}
	
	/**
	 * Constructeur pour carte de popularité avec coût
	 */
	public CarteStrategique(String nomCarte, String description, int populariteGagnee, 
						int degatsSubis, int cout) {
		super(TypeCarte.STRATEGIQUE, nomCarte, description, populariteGagnee, degatsSubis, cout);
		this.typeStrategique = TypeStrategique.POPULARITE;
		this.duree = 1; // Par défaut, effet sur 1 tour
		this.estReutilisable = false;
	}
	
	/**
	 * Constructeur pour carte passive
	 */
	public CarteStrategique(String nomCarte, String description, int valeur, 
						int duree, String typeEffet) {
		super(TypeCarte.STRATEGIQUE, nomCarte, description, valeur, 0);
		this.typeStrategique = TypeStrategique.PASSIVE;
		this.duree = duree;
		this.effetSpecial = typeEffet;
		this.estReutilisable = false;
	}
	
	/**
	 * Constructeur pour carte passive avec coût
	 */
	public CarteStrategique(String nomCarte, String description, int valeur, 
						int duree, String typeEffet, int cout) {
		super(TypeCarte.STRATEGIQUE, nomCarte, description, valeur, 0, cout);
		this.typeStrategique = TypeStrategique.PASSIVE;
		this.duree = duree;
		this.effetSpecial = typeEffet;
		this.estReutilisable = false;
	}
	
	/**
	 * Constructeur pour carte spéciale
	 */
	public CarteStrategique(String nomCarte, String description, String effetSpecial, int valeur) {
		super(TypeCarte.STRATEGIQUE, nomCarte, description, valeur, 0);
		this.typeStrategique = TypeStrategique.SPECIALE;
		this.effetSpecial = effetSpecial;
		this.estReutilisable = false;
	}
	
	/**
	 * Constructeur pour carte spéciale avec coût
	 */
	public CarteStrategique(String nomCarte, String description, String effetSpecial, 
						int valeur, int cout) {
		super(TypeCarte.STRATEGIQUE, nomCarte, description, valeur, 0, cout);
		this.typeStrategique = TypeStrategique.SPECIALE;
		this.effetSpecial = effetSpecial;
		this.estReutilisable = false;
	}
	
	/**
	 * Constructeur pour carte de trésor
	 */
	public CarteStrategique(String nomCarte, String description, int orGagne, int orPerdu, boolean estTresor) {
		super(TypeCarte.STRATEGIQUE, nomCarte, description, 0, 0);
		this.typeStrategique = TypeStrategique.TRESOR;
		setOrGagne(orGagne);
		setOrPerdu(orPerdu);
	}
	
	/**
	 * Constructeur pour carte de trésor avec coût
	 */
	public CarteStrategique(String nomCarte, String description, int orGagne, int orPerdu, 
						boolean estTresor, int cout) {
		super(TypeCarte.STRATEGIQUE, nomCarte, description, 0, 0, cout);
		this.typeStrategique = TypeStrategique.TRESOR;
		setOrGagne(orGagne);
		setOrPerdu(orPerdu);
	}
	
	// Méthodes de conversion depuis les anciens types
	
	public static CarteStrategique fromCartePopularite(CartePopularite cartePopularite) {
		return new CarteStrategique(
			cartePopularite.getNomCarte(),
			cartePopularite.getDescription(),
			cartePopularite.getPopularite(),
			cartePopularite.getDegatsSubisPopularite(),
			cartePopularite.getCout()
		);
	}
	
	public static CarteStrategique fromCartePassive(CartePassive cartePassive) {
		return new CarteStrategique(
			cartePassive.getNomCarte(),
			cartePassive.getDescription(),
			cartePassive.getValeur(),
			cartePassive.getDuree(),
			cartePassive.getTypeEffet(),
			cartePassive.getCout()
		);
	}
	
	public static CarteStrategique fromCarteSpeciale(CarteSpeciale carteSpeciale) {
		return new CarteStrategique(
			carteSpeciale.getNomCarte(),
			carteSpeciale.getDescription(),
			carteSpeciale.getEffetSpecial(),
			carteSpeciale.getValeur(),
			carteSpeciale.getCout()
		);
	}
	
	public static CarteStrategique fromCarteTresor(CarteTresor carteTresor) {
		CarteStrategique carte = new CarteStrategique(
			carteTresor.getNomCarte(),
			carteTresor.getDescription(),
			carteTresor.getOrGagne(),
			carteTresor.getOrPerdu(),
			true
		);
		carte.setCout(carteTresor.getCout());
		return carte;
	}

	// Getters et setters
	
	public TypeStrategique getTypeStrategique() {
		return typeStrategique;
	}
	
	public void setTypeStrategique(TypeStrategique typeStrategique) {
		this.typeStrategique = typeStrategique;
	}
	
	public int getDuree() {
		return duree;
	}
	
	public void setDuree(int duree) {
		this.duree = duree;
	}
	
	public String getEffetSpecial() {
		return effetSpecial;
	}
	
	public void setEffetSpecial(String effetSpecial) {
		this.effetSpecial = effetSpecial;
	}
	
	public boolean estReutilisable() {
		return estReutilisable;
	}
	
	public void setReutilisable(boolean estReutilisable) {
		this.estReutilisable = estReutilisable;
	}
	
	// Méthodes spécifiques
	
	/**
	 * Diminue la durée de l'effet passif
	 */
	public boolean reduireDuree() {
		duree--;
		return duree > 0;
	}
	
	// Méthodes de vérification du type
	public boolean estPopularite() {
		return this.typeStrategique == TypeStrategique.POPULARITE;
	}
	
	public boolean estPassive() {
		return this.typeStrategique == TypeStrategique.PASSIVE;
	}
	
	public boolean estSpeciale() {
		return this.typeStrategique == TypeStrategique.SPECIALE;
	}
	
	public boolean estTresor() {
		return this.typeStrategique == TypeStrategique.TRESOR;
	}
	
	// Accesseurs spécifiques selon le type
	
	public int getPopulariteGagnee() {
		return getValeur();
	}
	
	public int getDegatsSubis() {
		return getValeurSecondaire();
	}
	
	public String getTypeEffet() {
		return this.effetSpecial;
	}
	
	/**
	 * Représentation textuelle de la carte
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getNomCarte()).append("\n").append(getDescription());
		
		switch (typeStrategique) {
			case POPULARITE:
				sb.append("\nPopularité gagnée: ").append(getPopulariteGagnee());
				sb.append("\nDégâts subis: ").append(getDegatsSubis());
				break;
			case PASSIVE:
				sb.append("\nValeur: ").append(getValeur());
				sb.append("\nDurée: ").append(getDuree()).append(" tours");
				sb.append("\nType d'effet: ").append(getEffetSpecial());
				break;
			case SPECIALE:
				sb.append("\nValeur: ").append(getValeur());
				sb.append("\nEffet spécial: ").append(getEffetSpecial());
				sb.append("\nRéutilisable: ").append(estReutilisable() ? "Oui" : "Non");
				break;
			case TRESOR:
				if (getOrGagne() > 0) sb.append("\nOr gagné: ").append(getOrGagne());
				if (getOrPerdu() > 0) sb.append("\nOr perdu: ").append(getOrPerdu());
				break;
		}
		
		return sb.toString();
	}
}