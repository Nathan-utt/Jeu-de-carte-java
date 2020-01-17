package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Enumeration.Hauteur;

/**
 * 
 * Cette classe d�fini les troph�s assign�s au joueur ayant le joker.
 * Cette classe h�rite de Trophee car elle reprend plusieur de ses caract�ristiques.
 * 
 * @author Guillaume et Nathan
 *
 */
public class TropheeJoker extends Trophee {
	
	/**
	 * 
	 */
	public TropheeJoker() {
		this.setName("Joker");
		this.setImg("/img/trophee/trophe-joker");
		this.setDescription("Joueur poss�dant le Joker");
	}
	
	/**
	 * On donne ce troph�e � la personne avec le joker
	 */
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
