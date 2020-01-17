package Model;

import java.util.HashSet;
import java.util.Random;

/**
 * 
 * Cette classe permet la création des joueurs virtuels. 
 * C'est à dire des ordinateurs qui vont joueur avec les vrais joueurs et à qui un stratégie va être aléatoirement assigné.
 * Cette classe hérite de Joueur car elle reprend une grande partie des mêmes caractéristiques.
 * 
 * @author Guillaume et Nathan
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
