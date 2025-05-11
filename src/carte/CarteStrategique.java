package carte;

/**
 * Carte stratégique du jeu (popularité, passive, spéciale, trésor)
 */
public abstract class CarteStrategique extends Carte {
	
	private TypeStrategique typeStrategique;  // Type spécifique de carte stratégique
	
	/**
	 * Types spécifiques de cartes stratégiques
	 */
	public enum TypeStrategique {
		POPULARITE,     // Cartes augmentant la popularité
		TRESOR          // Cartes de trésor (or gagné)
	}

	public CarteStrategique(String nomCarte, String description, TypeStrategique typeStrategique, String cheminImage, int cout) {
		super(TypeCarte.STRATEGIQUE, nomCarte, description, cheminImage, cout);
		this.typeStrategique = typeStrategique;
	}

    public CarteStrategique(String nomCarte, String description, TypeStrategique typeOffensif, int cout) {
        this(nomCarte, description, typeOffensif, null, cout);
    }

    public CarteStrategique(String nomCarte, String description, TypeStrategique typeOffensif) {
        this(nomCarte, description, typeOffensif, null, 0);
    }

	// Getters et setters
	
	public TypeStrategique getTypeStrategique() {
		return typeStrategique;
	}
	
	public void setTypeStrategique(TypeStrategique typeStrategique) {
		this.typeStrategique = typeStrategique;
	}
	
	// Méthodes spécifiques
	
	// Méthodes de vérification du type
	public boolean estPopularite() {
		return this.typeStrategique == TypeStrategique.POPULARITE;
	}
	
	public boolean estTresor() {
		return this.typeStrategique == TypeStrategique.TRESOR;
	}
}