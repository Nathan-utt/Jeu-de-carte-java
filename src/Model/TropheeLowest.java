package Model;

import java.util.ArrayList;
import java.util.Iterator;

import Enumeration.Couleur;
import Enumeration.Hauteur;

public class TropheeLowest extends Trophee {
	
	private Couleur color;
	
	public TropheeLowest(Couleur color) {
		this.color = color;
		this.setImg("/img/trophee/trophe-petit-"+color.getDesc().toLowerCase());
		this.setName("Plus Basse");
		this.setDescription("Joueur avec la plus petite valeur de cette couleur");
	}
	
	@Override
	public Joueur award(ArrayList<Joueur> players) {
		Joueur awardedPlayer = null;
		Iterator<Joueur> iteJoueur = players.iterator();
		while (iteJoueur.hasNext()) {
			Joueur joueur = (Joueur) iteJoueur.next();
			CartesNumerotees lowestOfPlayer = joueur.getJest().getLowestOfColor(this.color);
			if (lowestOfPlayer != null) {
				if (awardedPlayer == null) {
					awardedPlayer = joueur;
				} else if (awardedPlayer.getJest().getLowestOfColor(this.color).isGreaterThan(lowestOfPlayer)) {
					awardedPlayer = joueur;
				}
			}
		}
		return awardedPlayer;
	}
}
