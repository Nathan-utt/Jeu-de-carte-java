package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

public class HideBestPickRandom extends Strategy {
	
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
		
		Random r = new Random();
		int rand = r.nextInt(offers.size());
		Carte chosen = (Carte) offers.keySet().toArray()[rand];
		Joueur chosenFrom = offers.get(chosen);
		players.remove(chosenFrom);
		Carte finalchoice = (chosenFrom.getOffer().getDeck().get(1).getName() == chosen.getName()) ? chosenFrom.getOffer().getCard(1) : chosenFrom.getOffer().getCard(0);
		finalchoice.setVisible(true);
		player.getJest().putCardFirst(finalchoice);
		return chosenFrom;
	}
}
