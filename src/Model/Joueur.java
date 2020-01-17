package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import Enumeration.Couleur;
import Enumeration.Hauteur;

/**
 * @author Guillaume
 *
 */
public class Joueur implements Serializable {
	/**
	 * 
	 */
	private String pseudo;
	/**
	 * 
	 */
	private Integer number;
	/**
	 * 
	 */
	private Deck hand;
	/**
	 * 
	 */
	private Deck offer;
	/**
	 * 
	 */
	private Deck jest;
	
	//Getter/Setter
	
	public Deck getHand() {
		return hand;
	}
	
	public Deck getOffer() {
		return offer;
	}
	
	/**
	 * @return
	 * @return
	 * @throws
	 * @exception
	 */
	public String seeOffer() {
		Iterator<Carte> iteCards = this.offer.getDeck().iterator();
		String returnMsg = "|";
		while (iteCards.hasNext()) {
			Carte carte = (Carte) iteCards.next();
			returnMsg += (carte.isVisible()) ? carte.getName()+"|" : "Carte cachée|";
		}
		return returnMsg;
	}
	
	/**
	 * @return
	 * @return
	 * @throws
	 * @exception
	 */
	public ArrayList<String> constructOffer() {
		Iterator<Carte> iteCards = this.offer.getDeck().iterator();
		ArrayList<String> returnMsg = new ArrayList<String>();
		while (iteCards.hasNext()) {
			Carte carte = (Carte) iteCards.next();
			returnMsg.add((carte.isVisible()) ? carte.getName() : this.pseudo+" Carte cachée");
		}
		return returnMsg;
	}
	
	public Deck getJest() {
		return jest;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	
	public Integer getNumber() {
		return number;
	}
	
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	//Constructor
	
	/**
	 * @param pseudo
	 * @param number
	 */
	public Joueur(String pseudo, Integer number) {
		super();
		this.pseudo = pseudo;
		this.number = number;
		this.hand = new Deck(2);
		this.offer = new Deck(2);
		this.jest = new Deck(20);
	}
	
	//Function
	
	/**
	 * @param hiddenCard
	 * @return
	 * @throws
	 * @exception
	 */
	public void makeOffer(Carte hiddenCard) {
		hiddenCard.setVisible(false);
		Carte visibleCard = this.hand.getFirstCard();
		visibleCard.setVisible(true);
		this.offer.putCardFirst(visibleCard);
		this.offer.putCardFirst(hiddenCard);
	}
	
	/**
	 * @param chosen
	 * @return
	 * @throws
	 * @exception
	 */
	public void takeOffer(Carte chosen) {
		chosen.setVisible(true);
		this.jest.putCardFirst(chosen);
	}
	
	/**
	 * @param sv
	 * @param withJoker
	 * @return
	 * @return
	 * @throws
	 * @exception
	 */
	public Integer accept(ScoreVisitor sv,Boolean withJoker) {
		return sv.visit(this, withJoker);
	}
	
	/**
	 *
	 */
	@Override
	public String toString() {
		return this.pseudo+" : "+this.number;
	}
	
}
