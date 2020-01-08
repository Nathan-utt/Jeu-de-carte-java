package Model;

import java.io.Serializable;
import java.util.HashSet;

public abstract class Strategy implements Serializable {

	public abstract void makeOffer(Joueur player);
	public abstract Joueur takeOffer(HashSet<Joueur> players,Joueur player);
}
