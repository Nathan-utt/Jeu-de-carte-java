package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import Enumeration.Hauteur;


/**
 * Cette classe est la classe décrivant les trophés en générale.
 * De cette classe plusieurs vont hérité pour spécifier les trophés plus précisement.
 * Cette classe implémente Serializable afin de sauvegarder les trophés.
 * 
 * @author Guillaume et Nathan
 * 
 * @serial Serializable.
 *
 */
public class Trophee implements Serializable {
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String description;
	/**
	 * 
	 */
	private String img;

	public String getName() {
		return name;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	public String getImg() {
		return img+".png";
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	/**
	 * @param players
	 * @return
	 * @return
	 * @throws
	 * @exception
	 */
	public Joueur award(ArrayList<Joueur> players) {
		return players.get(0);	
	}
	
	/**
	 * @param players
	 * @return
	 * @return
	 * @throws
	 * @exception
	 */
	public Joueur breakTies(Set<Joueur> players) {
		Joueur awardedPlayer = null;
		Iterator<Joueur> iteJoueur = players.iterator();
		while (iteJoueur.hasNext()) {
			Joueur joueur = (Joueur) iteJoueur.next();
			if (awardedPlayer == null) {
				awardedPlayer = joueur;
			} else if (joueur.getJest().getHighest().isGreaterThan(awardedPlayer.getJest().getHighest())) {
				awardedPlayer = joueur;
			}
		}
		return awardedPlayer;
	}
	
	/**
	 * @param players
	 * @param value
	 * @return
	 * @return
	 * @throws
	 * @exception
	 */
	public Joueur breakTies(HashSet<Joueur> players, Hauteur value) {
		Joueur awardedPlayer = null;
		Iterator<Joueur> iteJoueur = players.iterator();
		while (iteJoueur.hasNext()) {
			Joueur joueur = (Joueur) iteJoueur.next();
			if (awardedPlayer == null) {
				awardedPlayer = joueur;
			} else if (joueur.getJest().getHighestOfValue(value).isGreaterThan(awardedPlayer.getJest().getHighestOfValue(value))) {
				awardedPlayer = joueur;
			}
		}
		return awardedPlayer;
	}
}
