package Model;

import java.util.ArrayList;
import java.util.Iterator;

import Enumeration.Couleur;

/**
 * 
 * Cette classe va décrire les trophé assigné au joueur ayant une carte de la plus grande valeur d'une famille défini.
 * Cette classe hérite de Trophee car elle reprend plusieur de ses caractéristiques.
 * 
 * @author Guillaume et Nathan
 *
 */
public class TropheeHighest extends Trophee {

	/**
	 * La couleur de comparaison
	 */
	private Couleur color;
	
	/**
	 * @param color
	 */
	public TropheeHighest(Couleur color) {
		this.color = color;
		this.setImg("/img/trophee/trophe-grand-"+color.getDesc().toLowerCase());
		this.setName("Plus Haute");
		this.setDescription("Joueur avec la plus haute valeur de cette couleur");
	}
	
	/**
	 * On donne ce trophée à la personne avec la plus haute carte d'une couleur
	 */
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
