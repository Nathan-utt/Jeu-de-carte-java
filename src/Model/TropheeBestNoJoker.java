package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author Guillaume
 *
 */
public class TropheeBestNoJoker extends Trophee{
	
	/**
	 * 
	 */
	private ScoreVisitor sv;
	
	/**
	 * @param sv
	 */
	public TropheeBestNoJoker(ScoreVisitor sv) {
		this.sv = sv;
		this.setImg("/img/trophee/trophe-premier-sans-joker");
		this.setName("Meilleur Jest Sans Joker");
		this.setDescription("Joueur avec le meilleur Jest sans le Joker");
	}
	
	/**
	 *
	 */
	@Override
	public Joueur award(ArrayList<Joueur> players) {
		HashMap<Joueur,Integer> awardedPlayer = new HashMap<Joueur,Integer>();
		Iterator<Joueur> iteJoueur = players.iterator();
		while (iteJoueur.hasNext()) {
			Joueur joueur = (Joueur) iteJoueur.next();
			if (awardedPlayer.isEmpty()) {
				awardedPlayer.put(joueur, this.sv.getScore(joueur,false));
			} else {
				if (this.sv.getScore(joueur,false) > (Integer)awardedPlayer.values().toArray()[0]) {
					awardedPlayer.clear();
					awardedPlayer.put(joueur, this.sv.getScore(joueur,false));
				} else if (this.sv.getScore(joueur,false) == (Integer)awardedPlayer.values().toArray()[0]) {
					awardedPlayer.put(joueur, this.sv.getScore(joueur,false));
				}
			}
		}
		return this.breakTies(awardedPlayer.keySet());
	}
}
