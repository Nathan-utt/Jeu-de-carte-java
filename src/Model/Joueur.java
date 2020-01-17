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
 * 
 * Cette classe permet la création des différents joueurs qui vont participer Ã  la partie.
 * Chaque joueur est décrit par plusieurs caractéristiques et plusieurs capacités.
 * Cette classe implémente Serializable ce qui va lui permettre de sauvegarder les données des joueurs.
 * 
 * @author Guillaume
 * 
 * @serial Serializable
 *
 */
public class Joueur implements Serializable {
	/**
	 * Le nom du joueur
	 */
	private String pseudo;
	/**
	 * Le numéro du joueur
	 */
	private Integer number;
	/**
	 * La main du joueur sous forme d'un deck
	 */
	private Deck hand;
	/**
	 * L'offre du joueur sous forme d'un deck
	 */
	private Deck offer;
	/**
	 * Le jest du joueur sous forme d'un deck
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
	 * Retourne l'offre du joueur
	 * 
	 * @return l'offre de ce joueur sous forme d'un String
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
	 * Retourne l'offre du joueur pour la fonction de demande de choix
	 * @return l'offre du joueur sous forme d'un tableau de String
	 */
	public ArrayList<String> constructOffer() {
		Iterator<Carte> iteCards = this.offer.getDeck().iterator();
		ArrayList<String> returnMsg = new ArrayList<String>();
		while (iteCards.hasNext()) {
			Carte carte = (Carte) iteCards.next();
			returnMsg.add((carte.isVisible()) ? carte.getName() : this.pseudo+" Carte cachÃ©e");
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
	 * Permet au joueur de faire une offre en choisissant sa carte face cachée
	 * 
	 * @param hiddenCard la carte à mettre face cachée
	 * @return void
	 */
	public void makeOffer(Carte hiddenCard) {
		hiddenCard.setVisible(false);
		Carte visibleCard = this.hand.getFirstCard();
		visibleCard.setVisible(true);
		this.offer.putCardFirst(visibleCard);
		this.offer.putCardFirst(hiddenCard);
	}
	
	/**
	 * Permet au joueur de choisir une carte parmis les offres
	 * 
	 * @param chosen la carte choisie
	 * @return void
	 */
	public void takeOffer(Carte chosen) {
		chosen.setVisible(true);
		this.jest.putCardFirst(chosen);
	}
	
	/**
	 * Cette fonction permet d'accepter la visite du visiteur de score.
	 * 
	 * @param sv le visiteur de score
	 * @param withJoker si l'on veut calculer le score avec ou sans le joker
	 * @return le score du joueur
	 */
	public Integer accept(ScoreVisitor sv,Boolean withJoker) {
		return sv.visit(this, withJoker);
	}
	
	/**
	 * Renvois le Joueur sous forme d'un String
	 */
	@Override
	public String toString() {
		return this.pseudo+" : "+this.number;
	}
	
}
