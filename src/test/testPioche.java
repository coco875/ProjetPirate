package test;
import controllers.*;
import joueur.Joueur;
import carte.*;

public class testPioche {

	public static void main(String[] args) {
		ControlPioche pioche = new ControlPioche();
		Joueur j = new Joueur("j", null);
		ControlJoueur cJ = new ControlJoueur(j, null, pioche);
		
		j.setcJ(cJ);
		
		cJ.initialiserMain();
		
		cJ.afficherMain();
		//affiche 4 cartes
		
		
		System.out.println("\n\n===============\n\n");
		
		Carte c1 = j.getMain().get(0);
		Carte c2 = j.getMain().get(1);
		
		cJ.retirerCarte(c1);
		cJ.retirerCarte(c2);
		
		cJ.afficherMain();
		//affiche 2 cartes
		
	}

}
