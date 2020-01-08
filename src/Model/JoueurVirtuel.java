package Model;

import java.util.HashSet;
import java.util.Random;

public class JoueurVirtuel extends Joueur{

	private Strategy strat;
	
	public JoueurVirtuel(String pseudo, Integer number) {
		super(pseudo, number);
		Strategy[] strategies = {new HideRandomPickBest(), new HideBestPickRandom(), new Best()};
		Random r = new Random();
		strat = strategies[r.nextInt(strategies.length)]; 
	}

	public void makeOffer() {
		strat.makeOffer(this);
	}

	public Joueur takeOffer(HashSet<Joueur> players) {
		return strat.takeOffer(players, this);
	}
	
	
}
