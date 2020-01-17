package Model;

import java.util.ArrayList;
import java.util.Iterator;

import Enumeration.Couleur;
import Enumeration.Hauteur;

/**
 * 
 * Cette classe d�crit les troph�s assign�s au joueur ayant la plus petite carte d'une famille d�fini.
 * Cette classe h�rite de Trophee car elle reprend plusieur de ses caract�ristiques.
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
	 * * On donne ce troph�e � la personne avec la plus petite carte d'une couleur
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
