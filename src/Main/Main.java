package Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import Enumeration.Status;
import Model.Carte;
import Model.CartesNumerotees;
import Model.Joueur;
import Model.MaitreDuJeu;
import View.ConsoleView;
import View.GraphicView;

/**
 * La fonction principale lancée au début du programme.
 * Elle crée un moteur de Jeu et lance le jeu dans les différentes vues.
 * 
 * @author Guillaume, Nathan
 *
 */
public class Main {

	/**
	 * Crée les deux vues, un moteur de jeu et lance la partie.
	 * 
	 * @param args
	 * @return void
	 */
	public static void main(String[] args) {
		MaitreDuJeu mdj = new MaitreDuJeu();
		ConsoleView cv = new ConsoleView(mdj);
		GraphicView gv = new GraphicView(mdj);
		
		mdj.setStatus(Status.playerChoice);
		
		
		
	}

}
