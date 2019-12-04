package Class;

import java.util.HashSet;

public abstract class Strategy {

	public abstract void makeOffer(Joueur player);
	public abstract Joueur takeOffer(HashSet<Joueur> players,Joueur player);
}
