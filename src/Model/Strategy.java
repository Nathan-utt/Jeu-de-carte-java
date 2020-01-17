package Model;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Cette classe gère le design pattern Strategy.
 * Elle implémente Serializable afin que la stratégie des joueurs virtuels puisse être sauvegardée.
 * 
 * @author Guillaume et Nathan
 * 
 * @serial Serializable
 * 
 */
public abstract class Strategy implements Serializable {

	/**
	 * Réimplemente la fonction makeOffer selon la stratégie
	 * @param player le joueur faisant l'offre
	 */
	public abstract void makeOffer(Joueur player);
	/**
	 * Réimplemente la fonction takeOffer selon la stratégie
	 * @param player le joueur faisant l'offre
	 * @param players les joueurs chez qui choisir
	 * return le joueur chez qui on a choisit
	 */
	public abstract Joueur takeOffer(HashSet<Joueur> players,Joueur player);
}
