package Model;

import java.util.HashSet;
import java.util.Random;

/**
 * @author Guillaume
 *
 */
public class JoueurVirtuel extends Joueur{

	/**
	 * 
	 */
	private Strategy strat;
	
	/**
	 * @param pseudo
	 * @param number
	 */
	public JoueurVirtuel(String pseudo, Integer number) {
		super(pseudo, number);
		Strategy[] strategies = {new HideRandomPickBest(), new HideBestPickRandom(), new Best()};
		Random r = new Random();
		strat = strategies[r.nextInt(strategies.length)]; 
	}

	/**
	 * 
	 * @return
	 * @throws
	 * @exception
	 */
	public void makeOffer() {
		strat.makeOffer(this);
	}

	/**
	 * @param players
	 * @return
	 * @return
	 * @throws
	 * @exception
	 */
	public Joueur takeOffer(HashSet<Joueur> players) {
		return strat.takeOffer(players, this);
	}
	
	
}
