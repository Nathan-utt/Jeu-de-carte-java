package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import Enumeration.Hauteur;


/**
 * Cette classe est la classe d�crivant les troph�s en g�n�rale.
 * De cette classe plusieurs vont h�rit� pour sp�cifier les troph�s plus pr�cisement.
 * Cette classe impl�mente Serializable afin de sauvegarder les troph�s.
 * 
 * @author Guillaume et Nathan
 * 
 * @serial Serializable.
 *
 */
public class Trophee implements Serializable {
	/**
	 * Le nom du troph�e
	 */
	private String name;
	/**
	 * Sa description
	 */
	private String description;
	/**
	 * Le lien vers l'image du troph�e
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
	 * Permet d'attribuer le troph�e au joueur le m�ritant
	 * @param players les joueurs
	 * @return le joueur choisit
	 */
	public Joueur award(ArrayList<Joueur> players) {
		return players.get(0);	
	}
	
	/**
	 * Permet de casser les �galit� sur l'attribution d'un troph�e
	 * @param players les joueurs
	 * @return le joueur choisit
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
	 * Permet de casser les �galit� sur l'attribution d'un troph�e
	 * @param players les joueurs
	 * @param value la hauteur sur laquelle comparer
	 * @return le joueur choisit
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
