package Model;

import java.util.HashSet;

public class JoueurVirtuel extends Joueur{

	private Strategy strat;
	
	public JoueurVirtuel(String pseudo, Integer number) {
		super(pseudo, number);
		strat = new GoForHighest();
	}

	public void makeOffer() {
		strat.makeOffer(this);
	}

	public Joueur takeOffer(HashSet<Joueur> players) {
		return strat.takeOffer(players, this);
	}
	
	
}
