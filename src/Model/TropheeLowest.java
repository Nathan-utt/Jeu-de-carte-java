package Model;

import java.util.ArrayList;
import java.util.Iterator;

import Enumeration.Couleur;
import Enumeration.Hauteur;

/**
 * 
 * Cette classe décrit les trophés assignés au joueur ayant la plus petite carte d'une famille défini.
 * Cette classe hérite de Trophee car elle reprend plusieur de ses caractéristiques.
 * 
 * @author Guillaume et Nathan
 *
 */
public class TropheeLowest extends Trophee {
	
	/**
	 * La couleur de comparaison
	 */
	private Couleur color;
	
	/**
	 * @param color
	 */
	public TropheeLowest(Couleur color) {
		this.color = color;
		this.setImg("/img/trophee/trophe-petit-"+color.getDesc().toLowerCase());
		this.setName("Plus Basse");
		this.setDescription("Joueur avec la plus petite valeur de cette couleur");
	}
	
	/**
	 * * On donne ce trophée à la personne avec la plus petite carte d'une couleur
	 */
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
