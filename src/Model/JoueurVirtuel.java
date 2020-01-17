package Model;

import java.util.HashSet;
import java.util.Random;

/**
 * 
 * Cette classe permet la cr�ation des joueurs virtuels. 
 * C'est à dire des ordinateurs qui vont joueur avec les vrais joueurs et � qui un strat�gie va �tre al�atoirement assign�.
 * Cette classe h�rite de Joueur car elle reprend une grande partie des mêmes caract�ristiques.
 * 
 * @author Guillaume et Nathan
 *
 */
public class JoueurVirtuel extends Joueur{

	/**
	 * La strat�gie du joueur virtuel
	 */
	private Strategy strat;
	
	/**
	 * On attribue une strat�gie al�atoire au joueur virtuel
	 * 
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
	 *Le joueur virtuel reimplemente la fonction makeOffer pour appeler celle de sa strat�gie.
	 */
	public void makeOffer() {
		strat.makeOffer(this);
	}

	/**
	 * Le joueur virtuel reimplemente la fonction takeOffer pour appeler celle de sa strat�gie.
	 * @param players les joueurs restant
	 * @return le joueur chez qui on a choisit
	 * 
	 */
	public Joueur takeOffer(HashSet<Joueur> players) {
		return strat.takeOffer(players, this);
	}
	
	
}
