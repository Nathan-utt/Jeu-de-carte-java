package Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import Class.Joueur;
import Class.MaitreDuJeu;

public class Main {

	public static void main(String[] args) {
		MaitreDuJeu mdj = new MaitreDuJeu();
		boolean flag = true;
		//Gestion d'une partie
		while (flag) {
			mdj.distribute();
			mdj.makingOffers();
			mdj.takingOffers();
			if (mdj.getRemainingDeck().getDeck().isEmpty()) {
				mdj.getCardsRemainingToJest();
				flag = false;
			} else {
				mdj.getCardsBack();
				mdj.setDistributionDeck();
			}
		}
		
		System.out.println("================================Recap Partie=========================================");
		System.out.println("--------------------------------Distribution deck------------------------------------");
		System.out.println(mdj.getDistributionDeck().getDeck());
		System.out.println("--------------------------------Remaining deck---------------------------------------");
		System.out.println(mdj.getRemainingDeck().getDeck());
		
		Iterator<Joueur> itePlayers = mdj.getPlayers().iterator();
		while (itePlayers.hasNext()) {
			Joueur joueur = (Joueur) itePlayers.next();
			System.out.println("--------------------------------"+joueur.getPseudo()+" jest---------------------------------------");
			System.out.println(joueur.getJest().getDeck());
			System.out.println("--------------------------------"+joueur.getPseudo()+" hand---------------------------------------");
			System.out.println(joueur.getHand().getDeck());
			System.out.println("--------------------------------"+joueur.getPseudo()+" offer---------------------------------------");
			System.out.println(joueur.getOffer().getDeck());
		}
		
		//Game setup
		
		//Play all turns
		
		//Distribute trophees
		
		//Determine winner
		
		
	}

}
