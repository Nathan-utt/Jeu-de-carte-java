package Model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

import Enumeration.Couleur;
import Enumeration.Hauteur;

/**
 * @author Guillaume
 *
 */
public class ScoreVisitor implements Serializable{
	/**
	 * 
	 */
	private Boolean extension;
	/**
	 * 
	 */
	private Boolean reversed;
	
	/**
	 * @param extension
	 * @param reversed
	 */
	public ScoreVisitor(Boolean extension, Boolean reversed) {
		this.extension = extension;
		this.reversed = reversed;
	}
	
	/**
	 * @param player
	 * @param withJoker
	 * @return
	 * @return
	 * @throws
	 * @exception
	 */
	public Integer getScore(Joueur player,Boolean withJoker) {
		return player.accept(this, withJoker);
	}
	
	/**
	 * @param joueur
	 * @param withJoker
	 * @return
	 * @return
	 * @throws
	 * @exception
	 */
	public Integer visit(Joueur joueur, Boolean withJoker) {
		Iterator<Carte> iteCards = joueur.getJest().getDeck().iterator();
		Integer score = 0;
		Integer countHeart = 0;
		Boolean hasJoker = false;
		HashSet<Hauteur> alreadyEncounteredHauteur = new HashSet<Hauteur>();
		Integer[] colorCount = {0,0,0,0};
		HashSet<Couleur> aceEncountered = new HashSet<Couleur>();
		while (iteCards.hasNext()) {
			Carte carte = (Carte) iteCards.next();
			if (carte.getClass().equals(CartesNumerotees.class)) {
				CartesNumerotees card =  (CartesNumerotees)carte;
				switch (card.getCouleur()) {
				case Coeur :
					if (card.getHauteur() == Hauteur.As) {
						aceEncountered.add(card.getCouleur());
					}
					colorCount[card.getCouleur().ordinal()] += 1;
					countHeart += card.getHauteur().ordinal();
					break;
				case Pique :
					if (card.getHauteur() == Hauteur.As) {
						aceEncountered.add(card.getCouleur());
					}
					if (alreadyEncounteredHauteur.contains(card.getHauteur())) {
						if (reversed) {
							score -= 2;
						} else {
							score += 2;
						}
					} else {
						alreadyEncounteredHauteur.add(card.getHauteur());
					}
					colorCount[card.getCouleur().ordinal()] += 1;
					if (reversed) {
						score -= card.getHauteur().ordinal();
					} else {
						score += card.getHauteur().ordinal();
					}
					break;
				case Trefle:
					if (card.getHauteur() == Hauteur.As) {
						aceEncountered.add(card.getCouleur());
					}
					if (alreadyEncounteredHauteur.contains(card.getHauteur())) {
						if (reversed) {
							score -= 2;
						} else {
							score += 2;
						}
					} else {
						alreadyEncounteredHauteur.add(card.getHauteur());
					}
					colorCount[card.getCouleur().ordinal()] += 1;
					if (reversed) {
						score -= card.getHauteur().ordinal();
					} else {
						score += card.getHauteur().ordinal();
					}
					break;	
				case Carreau:
					if (card.getHauteur() == Hauteur.As) {
						aceEncountered.add(card.getCouleur());
					}
					colorCount[card.getCouleur().ordinal()] += 1;
					if (reversed) {
						score += card.getHauteur().ordinal();
					} else {
						score -= card.getHauteur().ordinal();
					}
					break;
				default:
					hasJoker = true;
					break;
				}
			}
		}
		
		
		
		Iterator<Couleur> iteAs = aceEncountered.iterator();
		while (iteAs.hasNext()) {
			Couleur couleur = (Couleur) iteAs.next();
			if (colorCount[couleur.ordinal()] == 1 && (couleur == Couleur.Pique || couleur == Couleur.Trefle)) score += extension ? reversed ? -7 : 7 : reversed ? -4 : 4;
			if (colorCount[couleur.ordinal()] == 1 && couleur == Couleur.Carreau) score -= extension ? reversed ? -7 : 7 : reversed ? -4 : 4;
			if (colorCount[couleur.ordinal()] == 1 && couleur == Couleur.Coeur) countHeart += extension ? 7 : 4;
		}
		
		
		if (hasJoker && withJoker) {
			if ((countHeart == 1+2+3+4+5+6+7 && extension) || (countHeart == 1+2+3+4 && !extension)) {
				score += reversed ? -countHeart : countHeart;
			} else {
				score -= reversed ? -countHeart : countHeart;
			}
		}
		
		return score; 
	}
}
