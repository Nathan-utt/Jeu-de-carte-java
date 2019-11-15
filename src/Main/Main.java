package Main;

import java.util.ArrayList;

import Class.MaitreDuJeu;

public class Main {

	public static void main(String[] args) {
		MaitreDuJeu mdj = new MaitreDuJeu();
		String[] choices = {"As de coeur","As de pique","Deux de carreau","Carte 1","Carte 2","Carte 3"};
		mdj.askForChoice(choices);
	}

}
