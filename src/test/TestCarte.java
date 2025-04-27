package test;

import carte.*;

public class TestCarte {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Carte c =new CarteAttaque("d","vd",2,3);
		c.setDescription("a");
		c.setNomCarte("b");
		c.getNomCarte();
		c.getType();
		c.getDescription();
		System.out.println(c.toString());
		Carte d= new CartePopularite("titre", "de", 0, 1);
		System.out.println(d.toString());
		Carte e= ParserCarte.lireCarte("src/carte/resource/templateAttaque.txt");
		System.out.println(e.toString());

	}

}