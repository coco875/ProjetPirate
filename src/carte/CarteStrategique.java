package carte;

/**
 * Carte stratégique du jeu (popularité, passive, spéciale, trésor)
 */
public class CarteStrategique extends Carte {
	
	private TypeStrategique typeStrategique;  // Type spécifique de carte stratégique
	private int duree;                        // Durée d'effet en nombre de tours pour les cartes passives
	private String effetSpecial;              // Description de l'effet spécial pour les cartes spéciales
	private boolean estReutilisable;          // Indique si la carte peut être réutilisée
	// Attributs spécifiques aux cartes trésor
	private int tresorOrGagne;
	
	/**
	 * Types spécifiques de cartes stratégiques
	 */
	public enum TypeStrategique {
		POPULARITE,     // Cartes augmentant la popularité
		PASSIVE,        // Cartes à effet passif sur plusieurs tours
		SPECIALE,       // Cartes à effets spéciaux uniques
		TRESOR          // Cartes de trésor (or gagné)
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
	public CarteStrategique(String nomCarte, String description, int orGagne, boolean estTresor) {
		super(TypeCarte.STRATEGIQUE, nomCarte, description, 0, 0);
		this.typeStrategique = TypeStrategique.TRESOR;
		this.tresorOrGagne = orGagne;
	}
	
	/**
	 * Constructeur pour carte de trésor avec coût
	 */
	public CarteStrategique(String nomCarte, String description, int orGagne, 
						boolean estTresor, int cout) {
		super(TypeCarte.STRATEGIQUE, nomCarte, description, 0, 0, cout);
		this.typeStrategique = TypeStrategique.TRESOR;
		this.tresorOrGagne = orGagne;
	}
	
	/**
	 * Constructeur pour carte de trésor avec paramètre orPerdu (maintenu pour compatibilité)
	 * @param nomCarte Nom de la carte
	 * @param description Description de la carte
	 * @param orGagne Or gagné en jouant la carte
	 * @param orPerdu Ignoré, maintenu pour compatibilité
	 * @param estTresor Indique si la carte est de type trésor
	 * @deprecated Utilisez le constructeur sans le paramètre orPerdu à la place
	 */
	public CarteStrategique(String nomCarte, String description, int orGagne, int orPerdu, boolean estTresor) {
		super(TypeCarte.STRATEGIQUE, nomCarte, description, 0, 0);
		this.typeStrategique = TypeStrategique.TRESOR;
		this.tresorOrGagne = orGagne;
		// Le paramètre orPerdu est ignoré
	}
	
	/**
	 * Constructeur pour carte de trésor avec coût et paramètre orPerdu (maintenu pour compatibilité)
	 * @param nomCarte Nom de la carte
	 * @param description Description de la carte
	 * @param orGagne Or gagné en jouant la carte
	 * @param orPerdu Ignoré, maintenu pour compatibilité
	 * @param estTresor Indique si la carte est de type trésor
	 * @param cout Coût d'achat de la carte
	 * @deprecated Utilisez le constructeur sans le paramètre orPerdu à la place
	 */
	public CarteStrategique(String nomCarte, String description, int orGagne, int orPerdu, 
						boolean estTresor, int cout) {
		super(TypeCarte.STRATEGIQUE, nomCarte, description, 0, 0, cout);
		this.typeStrategique = TypeStrategique.TRESOR;
		this.tresorOrGagne = orGagne;
		// Le paramètre orPerdu est ignoré
	}
	
	// Méthodes de conversion depuis les anciens types
	
	public static CarteStrategique fromCartePopularite(CartePopularite cartePopularite) {
		return new CarteStrategique(
			cartePopularite.getNomCarte(),
			cartePopularite.getDescription(),
			cartePopularite.getValeur(),  // Utilisation de getValeur() au lieu de getPopularite()
			cartePopularite.getValeurSecondaire(),  // Utilisation de getValeurSecondaire() au lieu de getDegatsSubisPopularite()
			cartePopularite.getCout()
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
		return new CarteStrategique(
			carteTresor.getNomCarte(),
			carteTresor.getDescription(),
			carteTresor.getOrGagne(),
			true,
			carteTresor.getCout()  // Utilisation du constructeur avec coût plutôt que setCout
		);
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
	
	public int getOrGagne() {
		return this.tresorOrGagne;
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
	
	/**
	 * Redéfinition de la méthode effetCarte pour les cartes stratégiques
	 */
	@Override
	public EffetCarte effetCarte() {
		EffetCarte effet = new EffetCarte();
		
		switch (typeStrategique) {
			case POPULARITE:
				effet.populariteGagnee = getValeur();
				effet.degatsSubis = getValeurSecondaire();
				effet.estPopularite = true;
				break;
			case PASSIVE:
				// Correction: valeur n'existe pas dans EffetCarte, on utilise populariteGagnee
				effet.populariteGagnee = getValeur();
				effet.dureeEffet = getDuree();
				effet.effetSpecial = getEffetSpecial();
				effet.estPassive = true;
				break;
			case SPECIALE:
				// Correction: valeur n'existe pas dans EffetCarte, on utilise populariteGagnee
				effet.populariteGagnee = getValeur();
				effet.effetSpecial = getEffetSpecial();
				effet.estSpeciale = true;
				break;
			case TRESOR:
				effet.orGagne = this.tresorOrGagne;
				effet.estTresor = true;
				break;
		}
		
		return effet;
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
				break;
		}
		
		return sb.toString();
	}
}