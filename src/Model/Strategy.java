package Model;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Cette classe g�re le design pattern Strategy.
 * Elle impl�mente Serializable afin que la strat�gie des joueurs virtuels puisse �tre sauvegard�e.
 * 
 * @author Guillaume et Nathan
 * 
 * @serial Serializable
 * 
 */
public abstract class Strategy implements Serializable {

	/**
	 * R�implemente la fonction makeOffer selon la strat�gie
	 * @param player le joueur faisant l'offre
	 */
	public abstract void makeOffer(Joueur player);
	/**
	 * R�implemente la fonction takeOffer selon la strat�gie
	 * @param player le joueur faisant l'offre
	 * @param players les joueurs chez qui choisir
	 * return le joueur chez qui on a choisit
	 */
	public abstract Joueur takeOffer(HashSet<Joueur> players,Joueur player);
}
