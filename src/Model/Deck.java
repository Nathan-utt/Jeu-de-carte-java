package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import Enumeration.Couleur;
import Enumeration.Hauteur;

/**
 * 
 * Cette classe permet la cr�ation de paquets de cartes qui vont être utilis� pour plusieurs choses.
 * Tel que la main des joueurs et le paquet de carte dujeu en lui-même.
 * Cette classe impl�ment Serializable ainsi les donn�es qu'elle contient pourront être sauvegard�es.
 * 
 * @author Guillaume et Nathan
 * 
 * @serial Serializable
 *
 */
public class Deck implements Serializable{
	/**
	 * Une liste de carte ordonn�e
	 */
	private ArrayList<Carte> deck;
	/**
	 * Un entier repr�sentant la taille maximale du deck
	 */
	private Integer maxCards;

	public Integer getMaxCards() {
		return maxCards;
	}

	public void setMaxCards(Integer maxCards) {
		this.maxCards = maxCards;
	}

	public ArrayList<Carte> getDeck() {
		return deck;
	}

	public void setDeck(ArrayList<Carte> deck) {
		this.deck = deck;
	}
	
	/**
	 * R�cup�re une carte selon son index, en l'enlevant du deck.
	 * 
	 * @param index l'index de la carte � r�cup�rer
	 * @return La carte r�cup�rer
	 */
	public Carte getCard(int index) {
		return this.deck.remove(index);
	}
	
	/**
	 * R�cup�re la premi�re carte du deck, en l'enlevant du deck.
	 * @return La carte r�cup�rer
	 */
	public Carte getFirstCard() {
		return this.deck.remove(0);
	}
	
	/**
	 * R�cup�re la derni�re carte du deck, en l'enlevant du deck.
	 * @return La carte r�cup�rer
	 */
	public Carte getLastCard() {
		return this.deck.remove(this.deck.size()-1);
	}
	
	/**
	 * Place une carte en premi�re dans le deck, en v�rifiant la taille du deck.
	 * @param card La carte � placer
	 * @return void
	 */
	public void putCardFirst(Carte card) {
		if (this.deck.size()+1 <= this.maxCards) {
			this.deck.add(0, card);
		}
	}
	
	/**
	 * Place une carte en derni�re dans le deck, en v�rifiant la taille du deck.
	 * @param card La carte � placer
	 * @return void
	 */
	public void putCardLast(Carte card) {
		if (this.deck.size()+1 <= this.maxCards) {
			this.deck.add(card);
		}
	}
	
	/**
	 * Place une liste de carte devant le deck, en v�rifiant la taille du deck.
	 * @param cards Les cartes � placer
	 * @return void
	 */
	public void putCardFirst(ArrayList<Carte> cards) {
		if (this.deck.size()+cards.size() <= this.maxCards) {
			this.deck.addAll(0, cards);
		}
	}
	
	/**
	 * Place une liste de carte derri�re le deck, en v�rifiant la taille du deck.
	 * @param cards Les cartes � placer
	 * @return void
	 */
	public void putCardLast(ArrayList<Carte> cards) {
		if (this.deck.size()+cards.size() <= this.maxCards) {
			this.deck.addAll(cards);
		}
	}

	/**
	 * @param maxCards
	 */
	public Deck(Integer maxCards) {
		super();
		this.deck = new ArrayList<Carte>();
		this.maxCards = maxCards;
	}
	
	/**
	 * M�lange le paquet selon un algorythme simple.
	 * Pour time fois,
	 * On prend deux �l�ments dans le deck et on �change leur place.
	 * 
	 * @param time le nombre de m�lange � faire
	 * @return void
	 */
	public void shuffleDeck(Integer time) {
		for (int i = 0; i < time; i++) {
			Integer firstCard = (int) (Math.random()*this.deck.size());
			Integer secondCard;
			do {
				secondCard = (int) (Math.random()*this.deck.size());
			} while(secondCard == firstCard);
			
			Carte temp = this.deck.get(firstCard);
			this.deck.set(firstCard, this.deck.get(secondCard));
			this.deck.set(secondCard, temp);
		}
	}
	
	/**
	 * Permet de r�cuperer la carte la plus haute d'un deck.
	 * 
	 * @return la carte la plus haute
	 */
	public CartesNumerotees getHighest() {
		CartesNumerotees returned = null;
		Iterator<Carte> iteDeck = deck.iterator();
		while (iteDeck.hasNext()) {
			Carte carte = (Carte) iteDeck.next();
			if (carte instanceof CartesNumerotees) {
				CartesNumerotees card = (CartesNumerotees) carte;
				if (returned == null) {
							returned = card;
				} else if (card.isGreaterThan(returned)) {
					returned = card;
				}
			}
		}
		return returned;
	}
	
	/**
	 * Permet de r�cuperer la carte la plus haute d'un deck selon une valeur.
	 * @param value la valeur des cartes � comparer
	 * @return la carte la plus haute d'une valeur
	 */
	public CartesNumerotees getHighestOfValue(Hauteur value) {
		CartesNumerotees returned = null;
		Iterator<Carte> iteDeck = deck.iterator();
		while (iteDeck.hasNext()) {
			Carte carte = (Carte) iteDeck.next();
			if (carte instanceof CartesNumerotees) {
				CartesNumerotees card = (CartesNumerotees) carte;
				if (card.getHauteur().ordinal() == value.ordinal()) {
					if (returned == null) {
						returned = card;
					} else {
						if (card.isGreaterThan(returned)) {
							returned = card;
						}
					}
				}
			}
		}
		return returned;
	}
	
	/**
	 * Permet de r�cuperer la carte la plus haute d'un deck selon une couleur.
	 * @param color la couleur des cartes � comparer
	 * @return la carte la plus haute d'une couleur
	 */
	public CartesNumerotees getHighestOfColor(Couleur color) {
		CartesNumerotees returned = null;
		Iterator<Carte> iteDeck = deck.iterator();
		while (iteDeck.hasNext()) {
			Carte carte = (Carte) iteDeck.next();
			if (carte instanceof CartesNumerotees) {
				CartesNumerotees card = (CartesNumerotees) carte;
				if (card.getCouleur().equals(color)) {
					if (returned == null) {
						returned = card;
					} else {
						if (card.isGreaterThan(returned)) {
							returned = card;
						}
					}
				}
			}
		}
		return returned;
	}
	
	/**
	 * Permet de r�cuperer la carte la plus basse d'un deck selon une couleur.
	 * @param color la couleur des cartes � comparer
	 * @return la carte la plus basse d'une couleur
	 *//**
	 * @param color
	 * @return
	 * @return
	 * @throws
	 * @exception
	 */
	public CartesNumerotees getLowestOfColor(Couleur color) {
		CartesNumerotees returned = null;
		Iterator<Carte> iteDeck = deck.iterator();
		while (iteDeck.hasNext()) {
			Carte carte = (Carte) iteDeck.next();
			if (carte instanceof CartesNumerotees) {
				CartesNumerotees card = (CartesNumerotees) carte;
				if (card.getCouleur().equals(color)) {
					if (returned == null) {
						returned = card;
					} else {
						if (returned.isGreaterThan(card)) {
							returned = card;
						}
					}
				}
			}
		}
		return returned;
	}
	
	/**
	 * Permet de r�cuperer le nombre de carte d'une valeur d'un deck.
	 * @param value la valeur des cartes � compter.
	 * @return Le nombre de carte d'une valeur.
	 */
	public Integer countValue(Hauteur value) {
		Integer count = 0;
		Iterator<Carte> iteDeck = deck.iterator();
		while (iteDeck.hasNext()) {
			Carte carte = (Carte) iteDeck.next();
			if (carte instanceof CartesNumerotees) {
				CartesNumerotees card = (CartesNumerotees) carte;
				if (card.getHauteur().ordinal() == value.ordinal()) {
					count ++;
				}
			}
		}
		return count;
	}
}
