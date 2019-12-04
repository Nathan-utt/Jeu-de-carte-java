package Class;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import Enumeration.Couleur;
import Enumeration.Hauteur;
import Enumeration.TropheeEnum;

public class Joueur {
	private String pseudo;
	private Integer number;
	private Deck hand;
	private Deck offer;
	private Deck jest;
	
	//Getter/Setter
	
	public Deck getHand() {
		return hand;
	}
	
	public Deck getOffer() {
		return offer;
	}
	
	public String seeOffer() {
		Iterator<Carte> iteCards = this.offer.getDeck().iterator();
		String returnMsg = "|";
		while (iteCards.hasNext()) {
			Carte carte = (Carte) iteCards.next();
			returnMsg += (carte.isVisible()) ? carte.getName()+"|" : "Carte cachée|";
		}
		return returnMsg;
	}
	
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
	
	public Joueur(String pseudo, Integer number) {
		super();
		this.pseudo = pseudo;
		this.number = number;
		this.hand = new Deck(2);
		this.offer = new Deck(2);
		this.jest = new Deck(20);
	}
	
	//Function
	
	public void makeOffer() {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(this.hand.getDeck().get(0).getName());
		choices.add(this.hand.getDeck().get(1).getName());
		System.out.println("Vous avez un "+choices.get(0)+" et un "+choices.get(1)+" dans votre main.");
		System.out.println("Choississez la carte que vous souhaitez offrir face cachée, l'autre sera offerte face visible.");
		String answer = MaitreDuJeu.askForChoice(choices);
		Carte hiddenCard = this.hand.getCard(choices.indexOf(answer));
		hiddenCard.setVisible(false);
		Carte visibleCard = this.hand.getFirstCard();
		visibleCard.setVisible(true);
		this.offer.putCardFirst(visibleCard);
		this.offer.putCardFirst(hiddenCard);
	}
	
	public Joueur takeOffer(HashSet<Joueur> players) {
		Iterator<Joueur> itePlayers = players.iterator();
		ArrayList<String> choices = new ArrayList<String>();
		HashMap<String,Joueur> offers = new HashMap<String,Joueur>();
		while (itePlayers.hasNext()) {
			Joueur joueur = (Joueur) itePlayers.next();
			if (joueur != this || players.size() == 1) {
				Iterator<Carte> iteDeck = joueur.getOffer().getDeck().iterator();
				while (iteDeck.hasNext()) {
					Carte carte = (Carte) iteDeck.next();
					offers.put((carte.isVisible() ? carte.getName() : joueur.pseudo+" Carte cachée"),joueur);
				}
				System.out.println(joueur.getPseudo()+" propose "+joueur.seeOffer());
				choices.addAll(joueur.constructOffer());
			}
		}
		String answer = MaitreDuJeu.askForChoice(choices);
		Joueur chosenFrom = offers.get(answer);
		players.remove(chosenFrom);
		Carte chosen = (chosenFrom.getOffer().getDeck().get(1).getName() == answer) ? chosenFrom.getOffer().getCard(1) : chosenFrom.getOffer().getCard(0);
		chosen.setVisible(true);
		System.out.println("Tu as obtenu : "+chosen);
		this.jest.putCardFirst(chosen);
		return chosenFrom;
	}
	
	public Integer calculateScoreBase(Boolean withJoker,Boolean extension) {
		Iterator<Carte> iteCards = this.jest.getDeck().iterator();
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
						score += 2;
					} else {
						alreadyEncounteredHauteur.add(card.getHauteur());
					}
					colorCount[card.getCouleur().ordinal()] += 1;
					score += card.getHauteur().ordinal();
					break;
				case Trefle:
					if (card.getHauteur() == Hauteur.As) {
						aceEncountered.add(card.getCouleur());
					}
					if (alreadyEncounteredHauteur.contains(card.getHauteur())) {
						score += 2;
					} else {
						alreadyEncounteredHauteur.add(card.getHauteur());
					}
					colorCount[card.getCouleur().ordinal()] += 1;
					score += card.getHauteur().ordinal();
					break;	
				case Carreau:
					if (card.getHauteur() == Hauteur.As) {
						aceEncountered.add(card.getCouleur());
					}
					colorCount[card.getCouleur().ordinal()] += 1;
					score -= card.getHauteur().ordinal();
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
			if (colorCount[couleur.ordinal()] == 1 && (couleur == Couleur.Pique || couleur == Couleur.Trefle)) score += extension ? 6 : 4;
			if (colorCount[couleur.ordinal()] == 1 && couleur == Couleur.Carreau) score -= extension ? 6 : 4;
			if (colorCount[couleur.ordinal()] == 1 && couleur == Couleur.Coeur) countHeart += extension ? 6 : 4;
		}
		
		
		if (hasJoker && withJoker) {
			if (countHeart == 1+2+3+4) {
				score += countHeart;
			} else {
				score -= countHeart;
			}
		}
		
		return score; 
	}
	
	public Integer calculateScoreReversed(Boolean withJoker,Boolean extension) {
		Iterator<Carte> iteCards = this.jest.getDeck().iterator();
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
						score -= 2;
					} else {
						alreadyEncounteredHauteur.add(card.getHauteur());
					}
					colorCount[card.getCouleur().ordinal()] += 1;
					score -= card.getHauteur().ordinal();
					break;
				case Trefle:
					if (card.getHauteur() == Hauteur.As) {
						aceEncountered.add(card.getCouleur());
					}
					if (alreadyEncounteredHauteur.contains(card.getHauteur())) {
						score -= 2;
					} else {
						alreadyEncounteredHauteur.add(card.getHauteur());
					}
					colorCount[card.getCouleur().ordinal()] += 1;
					score -= card.getHauteur().ordinal();
					break;	
				case Carreau:
					if (card.getHauteur() == Hauteur.As) {
						aceEncountered.add(card.getCouleur());
					}
					colorCount[card.getCouleur().ordinal()] += 1;
					score += card.getHauteur().ordinal();
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
			if (colorCount[couleur.ordinal()] == 1 && (couleur == Couleur.Pique || couleur == Couleur.Trefle)) score += extension ? 6 : 4;
			if (colorCount[couleur.ordinal()] == 1 && couleur == Couleur.Carreau) score -= extension ? 6 : 4;
			if (colorCount[couleur.ordinal()] == 1 && couleur == Couleur.Coeur) countHeart += extension ? 6 : 4;
		}
		
		
		if (hasJoker && withJoker) {
			if (countHeart == 1+2+3+4) {
				score -= countHeart;
			} else {
				score += countHeart;
			}
		}
		
		return score; 
	}
	
	public Integer getInfoForThrophees(TropheeEnum trophee,Enum infoSupp){
		CartesNumerotees toReturn = null;
		Integer count = 0;
		Iterator<Carte> iteJest = this.jest.getDeck().iterator();
		
		switch (trophee) {
		case plusGrand:
			while (iteJest.hasNext()) {
				Carte carte = (Carte) iteJest.next();
				if (carte.getClass().equals(CartesNumerotees.class)) {
					CartesNumerotees card = (CartesNumerotees) carte;
					if (card.getCouleur() == (Couleur)infoSupp) {
						if (toReturn == null) {
							toReturn = card;
						} else {
							if (card.isGreaterThan(toReturn)) {
								toReturn = card;
							}
						}
					}
				}
			}
			count = toReturn == null ? 0 : toReturn.getHauteur().ordinal();
			break;
		case plusPetit:
			while (iteJest.hasNext()) {
				Carte carte = (Carte) iteJest.next();
				if (carte.getClass().equals(CartesNumerotees.class)) {
					CartesNumerotees card = (CartesNumerotees) carte;
					if (card.getCouleur() == (Couleur)infoSupp) {
						if (toReturn == null) {
							toReturn = card;
						} else {
							if (toReturn.isGreaterThan(card)) {
								toReturn = card;
							}
						}
					}
				}
			}
			count = toReturn == null ? 0 : toReturn.getHauteur().ordinal();
			break;
		case majorite:
			while (iteJest.hasNext()) {
				Carte carte = (Carte) iteJest.next();
				if (carte.getClass().equals(CartesNumerotees.class)) {
					CartesNumerotees card = (CartesNumerotees) carte;
					if (card.getHauteur().equals((Hauteur)infoSupp)) {
						count += 1;
					}
				}
			}
			break;
		case joker:
			while (iteJest.hasNext()) {
				Carte carte = (Carte) iteJest.next();
				if (carte.getClass().equals(CartesNumerotees.class)) {
					CartesNumerotees card = (CartesNumerotees) carte;
					if (card.getHauteur() == Hauteur.Joker) {
						count += 1;
					}
				}
			}
			break;
		}
		
		return count;
	}
	
	@Override
	public String toString() {
		return this.pseudo+" : "+this.number;
	}
	
}
