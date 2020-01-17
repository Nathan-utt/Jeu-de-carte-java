package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import Enumeration.Couleur;
import Enumeration.Hauteur;

/**
 * 
 * Cette classe permet la création de paquets de cartes qui vont être utilisé pour plusieurs choses.
 * Tel que la main des joueurs et le paquet de carte dujeu en lui-même.
 * Cette classe ilmplément Serializable ainsi les données qu'elle contient pourront être sauvegardées.
 * 
 * @author Guillaume et Nathan
 * 
 * @serial Serializable
 *
 */
public class Deck implements Serializable{
	/**
	 * 
	 */
	private ArrayList<Carte> deck;
	/**
	 * 
	 */
	private Integer maxCards;

	/**
	 * @return
	 * @return
	 * @throws
	 * @exception
	 */
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
	 * @param index
	 * @return
	 * @return
	 * @throws
	 * @exception
	 */
	public Carte getCard(int index) {
		return this.deck.remove(index);
	}
	
	/**
	 * @return
	 * @return
	 * @throws
	 * @exception
	 */
	public Carte getFirstCard() {
		return this.deck.remove(0);
	}
	
	/**
	 * @return
	 * @return
	 * @throws
	 * @exception
	 */
	public Carte getLastCard() {
		return this.deck.remove(this.deck.size()-1);
	}
	
	/**
	 * @param card
	 * @return
	 * @throws
	 * @exception
	 */
	public void putCardFirst(Carte card) {
		if (this.deck.size()+1 <= this.maxCards) {
			this.deck.add(0, card);
		}
	}
	
	/**
	 * @param card
	 * @return
	 * @throws
	 * @exception
	 */
	public void putCardLast(Carte card) {
		if (this.deck.size()+1 <= this.maxCards) {
			this.deck.add(card);
		}
	}
	
	/**
	 * @param cards
	 * @return
	 * @throws
	 * @exception
	 */
	public void putCardFirst(ArrayList<Carte> cards) {
		if (this.deck.size()+cards.size() <= this.maxCards) {
			this.deck.addAll(0, cards);
		}
	}
	
	/**
	 * @param cards
	 * @return
	 * @throws
	 * @exception
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
	 * @param time
	 * @return
	 * @throws
	 * @exception
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
	 * @return
	 * @return
	 * @throws
	 * @exception
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
	 * @param value
	 * @return
	 * @return
	 * @throws
	 * @exception
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
	 * @param color
	 * @return
	 * @return
	 * @throws
	 * @exception
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
	 * @param value
	 * @return
	 * @return
	 * @throws
	 * @exception
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
