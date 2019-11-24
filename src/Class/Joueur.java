package Class;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

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
		this.jest = new Deck(16);
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
		System.out.println("debug" +this.offer.getDeck());
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
		this.jest.putCardFirst(chosen);
		return chosenFrom;
	}
	
	@Override
	public String toString() {
		return this.pseudo+" : "+this.number;
	}
	
}
