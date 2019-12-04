package Class;

import java.util.HashSet;

public class JoueurVirtuel extends Joueur{

	private Strategy strat;
	
	public JoueurVirtuel(String pseudo, Integer number) {
		super(pseudo, number);
		strat = new GoForHighest();
	}

	@Override
	public void makeOffer() {
		strat.makeOffer(this);
	}

	@Override
	public Joueur takeOffer(HashSet<Joueur> players) {
		return strat.takeOffer(players, this);
	}
	
	
}
