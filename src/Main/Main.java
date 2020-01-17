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
 * @author Guillaume
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
