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
		
		
		
	}

}
