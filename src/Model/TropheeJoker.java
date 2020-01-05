package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Enumeration.Hauteur;

public class TropheeJoker extends Trophee {
	
	public TropheeJoker() {
		this.setName("Joker");
		this.setImg("/img/trophee/trophe-joker");
		this.setDescription("Joueur possédant le Joker");
	}
	
	@Override
	public Joueur award(ArrayList<Joueur> players) {
		Joueur awardedPlayer = null;
		Iterator<Joueur> iteJoueur = players.iterator();
		while (iteJoueur.hasNext()) {
			Joueur joueur = (Joueur) iteJoueur.next();
			Iterator<Carte> iteJest = joueur.getJest().getDeck().iterator();
			while (iteJest.hasNext()) {
				Carte carte = (Carte) iteJest.next();
				if (carte instanceof CartesNumerotees) {
					CartesNumerotees carteNum = (CartesNumerotees) carte;
					if (carteNum.getHauteur() == Hauteur.Joker) {
						awardedPlayer = joueur;
					}
				}
			}
		}
		return awardedPlayer;
	}
}
