package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import Enumeration.Couleur;

/**
 * 
 * Cette classe défini les trophés assignés au joueur ayant le meilleur Jest avec le Joker.
 * Cette classe hérite de Trophee car elle reprend plusieur de ses caractéristiques.
 * 
 * @author Guillaume et Nathan
 * 
 */
public class TropheeBest extends Trophee {
	
	/**
	 * 
	 */
	private ScoreVisitor sv;
	
	/**
	 * @param sv
	 */
	public TropheeBest(ScoreVisitor sv) {
		this.sv = sv;
		this.setImg("/img/trophee/trophe-premier");
		this.setName("Meilleur Jest Avec Joker");
		this.setDescription("Joueur avec le meilleur Jest avec le Joker");
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
				awardedPlayer.put(joueur, this.sv.getScore(joueur,true));
			} else {
				if (this.sv.getScore(joueur,true) > (Integer)awardedPlayer.values().toArray()[0]) {
					awardedPlayer.clear();
					awardedPlayer.put(joueur, this.sv.getScore(joueur,true));
				} else if (this.sv.getScore(joueur,true) == (Integer)awardedPlayer.values().toArray()[0]) {
					awardedPlayer.put(joueur, this.sv.getScore(joueur,true));
				}
			}
		}
		return this.breakTies(awardedPlayer.keySet());
	}
}
