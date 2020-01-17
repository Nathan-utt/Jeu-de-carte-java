package Model;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Cette classe gére le design pattern Strategy.
 * Elle implémente Serializable afin que la stratégie des joueurs virtuels puisse être sauvegardée.
 * 
 * @author Guillaume et Nathan
 * 
 * @serial Serializable
 * 
 */
public abstract class Strategy implements Serializable {

	/**
	 * @param player
	 * @return
	 * @throws
	 * @exception
	 */
	public abstract void makeOffer(Joueur player);
	/**
	 * @param players
	 * @param player
	 * @return
	 * @return
	 * @throws
	 * @exception
	 */
	public abstract Joueur takeOffer(HashSet<Joueur> players,Joueur player);
}
