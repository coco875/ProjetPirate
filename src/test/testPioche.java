package test;
import controllers.*;
import carte.*;

public class testPioche {

	public static void main(String[] args) {
		ControlPioche pioche = new ControlPioche();
		Carte carte1 = pioche.piocher();
		System.out.println(carte1);
		Carte carte2 = pioche.piocher();
		System.out.println(carte2);
	}

}
