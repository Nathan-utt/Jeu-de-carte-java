package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

public class Best extends Strategy {

	@Override
	public void makeOffer(Joueur player) {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(player.getHand().getDeck().get(0).getName());
		choices.add(player.getHand().getDeck().get(1).getName());
		String answer;
		
		CartesNumerotees highestCard = (CartesNumerotees) player.getHand().getFirstCard();
		CartesNumerotees lowestCard = (CartesNumerotees) player.getHand().getFirstCard();
		CartesNumerotees temp = null;
		
		if (lowestCard.isGreaterThan(highestCard)) {
			temp = highestCard;
			highestCard = lowestCard;
			lowestCard = temp;
		}
		
		
		
		Carte hiddenCard = highestCard;
		hiddenCard.setVisible(false);
		Carte visibleCard = lowestCard;
		visibleCard.setVisible(true);
		player.getOffer().putCardFirst(visibleCard);
		player.getOffer().putCardFirst(hiddenCard);
	}
	
	@Override
	public Joueur takeOffer(HashSet<Joueur> players,Joueur player) {
		Iterator<Joueur> itePlayers = players.iterator();
		HashMap<Carte,Joueur> offers = new HashMap<Carte,Joueur>();
		while (itePlayers.hasNext()) {
			Joueur joueur = (Joueur) itePlayers.next();
			if (joueur != player || players.size() == 1) {
				Iterator<Carte> iteDeck = joueur.getOffer().getDeck().iterator();
				while (iteDeck.hasNext()) {
					Carte carte = (Carte) iteDeck.next();
					if (carte.isVisible()) offers.put(carte,joueur);
				}
			}
		}
		
		CartesNumerotees highestCard = null;
		Iterator<Entry<Carte,Joueur>> iteChoices = offers.entrySet().iterator();
		
		while (iteChoices.hasNext()) {
			Entry<Carte, Joueur> entry = (Entry<Carte, Joueur>) iteChoices.next();
			CartesNumerotees carte = (CartesNumerotees)entry.getKey();
			if (highestCard == null) {
				highestCard = carte;
			} else {
				if (carte.isGreaterThan(highestCard)) {
					highestCard = carte;
				}
			}
		}
		
		
		
		Joueur chosenFrom = offers.get(highestCard);
		players.remove(chosenFrom);
		Carte chosen = (chosenFrom.getOffer().getDeck().get(1).getName() == highestCard.getName()) ? chosenFrom.getOffer().getCard(1) : chosenFrom.getOffer().getCard(0);
		highestCard.setVisible(true);
		player.getJest().putCardFirst(chosen);
		return chosenFrom;
	}
}
