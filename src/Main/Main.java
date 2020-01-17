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
 * @author Guillaume et Nathan
 * 
 * Cette classe permet l'exécution et la compilation de notre code. 
 * Dans cette classe, on crée les vues console et graphique de notre jeu et on crée aussi une partie avec l'objet MaitreDuJeu.
 *
 */
public class Main {

	/**
	 * @param args
	 * @return
	 * @throws
	 * @exception
	 */
	public static void main(String[] args) {
		MaitreDuJeu mdj = new MaitreDuJeu();
		ConsoleView cv = new ConsoleView(mdj);
		GraphicView gv = new GraphicView(mdj);
		
		mdj.setStatus(Status.playerChoice);
	}
}
