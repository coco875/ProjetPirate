package jeu;

public class Jeu {
	private Pioche pioche = new Pioche();
	private Joueur joueur1;
	private Joueur joueur2;
	
	
	
	public void initialiserJeu(Joueur joueur1, Joueur joueur2) {
		this.joueur1 = joueur1;
		this.joueur2 = joueur2;
		
	}
}
