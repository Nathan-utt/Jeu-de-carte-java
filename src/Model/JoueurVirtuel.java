package Model;

import java.util.HashSet;
import java.util.Random;

/**
 * 
 * Cette classe permet la création des joueurs virtuels. 
 * C'est Ã  dire des ordinateurs qui vont joueur avec les vrais joueurs et à qui un stratégie va être aléatoirement assigné.
 * Cette classe hérite de Joueur car elle reprend une grande partie des mÃªmes caractéristiques.
 * 
 * @author Guillaume et Nathan
 *
 */
public class JoueurVirtuel extends Joueur{

	/**
	 * La stratégie du joueur virtuel
	 */
	private Strategy strat;
	
	/**
	 * On attribue une stratégie aléatoire au joueur virtuel
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
	 *Le joueur virtuel reimplemente la fonction makeOffer pour appeler celle de sa stratégie.
	 */
	public void makeOffer() {
		strat.makeOffer(this);
	}

	/**
	 * Le joueur virtuel reimplemente la fonction takeOffer pour appeler celle de sa stratégie.
	 * @param players les joueurs restant
	 * @return le joueur chez qui on a choisit
	 * 
	 */
	public Joueur takeOffer(HashSet<Joueur> players) {
		return strat.takeOffer(players, this);
	}
	
	
}
