 package Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import Enumeration.Couleur;
import Enumeration.Hauteur;

/**
 * 
 * Cette classe d�crit les troph�s assign�s au joueur ayant le plus de carte d'une famille d�fini.
 * Cette classe h�rite de Trophee car elle reprend plusieur de ses caract�ristiques.
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
		this.setName("Majorit�");
		this.setDescription("Joueur avec la plus grande quantit� de cette hauteur");
	}
	
	/**
	 * On donne ce troph�e � la personne avec le plus de carte d'une valeur.
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
