package Model;

import java.io.Serializable;
import java.util.HashSet;

/**
 * @author Guillaume
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
