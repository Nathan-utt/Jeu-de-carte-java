package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ThinkABit extends Strategy {
	
	@Override
	public void makeOffer(Joueur player) {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(player.getHand().getDeck().get(0).getName());
		choices.add(player.getHand().getDeck().get(1).getName());
		String answer;
		
		if (((CartesNumerotees)player.getHand().getDeck().get(0)).isGreaterThan(((CartesNumerotees)player.getHand().getDeck().get(1)))) {
			answer = player.getHand().getDeck().get(0).getName();
		} else {
			answer = player.getHand().getDeck().get(1).getName();
		}
		
		Carte hiddenCard = player.getHand().getCard(choices.indexOf(answer));
		hiddenCard.setVisible(false);
		Carte visibleCard = player.getHand().getFirstCard();
		visibleCard.setVisible(true);
		player.getOffer().putCardFirst(visibleCard);
		player.getOffer().putCardFirst(hiddenCard);
	}
	
	public Joueur takeOffer(HashSet<Joueur> players,Joueur player) {
		Iterator<Joueur> itePlayers = players.iterator();
		ArrayList<String> choices = new ArrayList<String>();
		HashMap<String,Joueur> offers = new HashMap<String,Joueur>();
		while (itePlayers.hasNext()) {
			Joueur joueur = (Joueur) itePlayers.next();
			if (joueur != player || players.size() == 1) {
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
}
