 package Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import Enumeration.Couleur;
import Enumeration.Hauteur;

/**
 * 
 * Cette classe décrit les trophés assignés au joueur ayant le plus de carte d'une famille défini.
 * Cette classe hérite de Trophee car elle reprend plusieur de ses caractéristiques.
 * 
 * @author Guillaume et Nathan
 *
 */
public class TropheeMajority extends Trophee {
	
	/**
	 * La valeur de comparaison
	 */
	private Hauteur value;
	
	/**
	 * @param value
	 */
	public TropheeMajority(Hauteur value) {
		this.value = value;
		this.setImg("/img/trophee/trophe-majorite-"+value.getDesc());
		this.setName("Majorité");
		this.setDescription("Joueur avec la plus grande quantité de cette hauteur");
	}
	
	/**
	 * On donne ce trophée à la personne avec le plus de carte d'une valeur.
	 */
	@Override
	public Joueur award(ArrayList<Joueur> players) {
		HashSet<Joueur> awardedPlayer = new HashSet<Joueur>();
		Iterator<Joueur> iteJoueur = players.iterator();
		while (iteJoueur.hasNext()) {
			Joueur joueur = (Joueur) iteJoueur.next();
			Integer numberOfCardsForPlayer = joueur.getJest().countValue(this.value);
			if (awardedPlayer.isEmpty()) {
				awardedPlayer.add(joueur);
			} else {
				if (numberOfCardsForPlayer > ((Joueur) awardedPlayer.toArray()[0]).getJest().countValue(this.value)) {
					awardedPlayer.clear();
					awardedPlayer.add(joueur);
				} else if (numberOfCardsForPlayer == ((Joueur) awardedPlayer.toArray()[0]).getJest().countValue(this.value)) {
					awardedPlayer.add(joueur);
				}		
			}
		}
		return this.breakTies(awardedPlayer, this.value);
	}
}
