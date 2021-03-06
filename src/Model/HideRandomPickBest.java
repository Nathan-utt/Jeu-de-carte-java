package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * 
 * Cette classe h�rite de Strategy, elle fait partie du Design Pattern Strategy. 
 * Il s'agit de l'une des trois start�gie que le joueur virtuel va être amen� � utiliser.
 * Lorsque le joueur virtuel va avoir cette strat�gie, il va cahcer une carte au hasard et choisir la meilleure carte visible sur le plateau de jeu.
 *
 * @author Guillaume et Nathan
 *
 */
public class HideRandomPickBest extends Strategy {

	/**
	 * Le bot cache une carte au hasard.
	 */
	@Override
	public void makeOffer(Joueur player) {
		ArrayList<String> choices = new ArrayList<String>();
		choices.add(player.getHand().getDeck().get(0).getName());
		choices.add(player.getHand().getDeck().get(1).getName());
		String answer;
		
		answer = player.getHand().getDeck().get((int) Math.round(Math.random())).getName();
		
		Carte hiddenCard = player.getHand().getCard(choices.indexOf(answer));
		hiddenCard.setVisible(false);
		Carte visibleCard = player.getHand().getFirstCard();
		visibleCard.setVisible(true);
		player.getOffer().putCardFirst(visibleCard);
		player.getOffer().putCardFirst(hiddenCard);
	}
	
	/**
	 * Le bot prend la meilleure carte disponible.
	 */
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
