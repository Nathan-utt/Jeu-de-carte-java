package Model;

import java.util.ArrayList;
import java.util.Iterator;

import Enumeration.Couleur;

public class TropheeHighest extends Trophee {

	private Couleur color;
	
	public TropheeHighest(Couleur color) {
		this.color = color;
		this.setName("Plus Haute");
		this.setDescription("Joueur avec la plus haute valeur de cette couleur");
	}
	
	@Override
	public Joueur award(ArrayList<Joueur> players) {
		Joueur awardedPlayer = null;
		Iterator<Joueur> iteJoueur = players.iterator();
		while (iteJoueur.hasNext()) {
			Joueur joueur = (Joueur) iteJoueur.next();
			CartesNumerotees highestOfPlayer = joueur.getJest().getHighestOfColor(this.color);
			if (highestOfPlayer != null) {
				if (awardedPlayer == null) {
					awardedPlayer = joueur;
				} else if (highestOfPlayer.isGreaterThan(awardedPlayer.getJest().getHighestOfColor(this.color))) {
					awardedPlayer = joueur;
				}
			}
		}
		return awardedPlayer;
	}
	
}
