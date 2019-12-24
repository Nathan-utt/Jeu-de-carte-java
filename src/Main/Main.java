package Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import Model.Carte;
import Model.CartesNumerotees;
import Model.Joueur;
import Model.MaitreDuJeu;

public class Main {

	public static void main(String[] args) {
		MaitreDuJeu mdj = new MaitreDuJeu();
		boolean flag = true;
		//Gestion d'une partie
		Iterator<Carte> iteTrophees = mdj.getTrophees().getDeck().iterator();
		System.out.println("Les trophées pour cette partie sont :");
		while (iteTrophees.hasNext()) {
			Carte carte = (Carte) iteTrophees.next();
			System.out.println("- "+((CartesNumerotees)carte).getName()+" -> "+((CartesNumerotees)carte).getTrophee());
		}
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
		
		
		
		mdj.awardTrophees();
		
		Joueur winner = mdj.getWinner();
		
		System.out.println("================================Recap Partie=========================================");
		System.out.println("=============================Winner : "+winner.getPseudo()+"======================================");
		System.out.println("--------------------------------Distribution deck------------------------------------");
		System.out.println(mdj.getDistributionDeck().getDeck());
		System.out.println("--------------------------------Remaining deck---------------------------------------");
		System.out.println(mdj.getRemainingDeck().getDeck());
		
		Iterator<Joueur> itePlayers = mdj.getPlayers().iterator();
		while (itePlayers.hasNext()) {
			Joueur joueur = (Joueur) itePlayers.next();
			System.out.println("----------------------------"+joueur.getPseudo()+" score "+mdj.getSv().getScore(joueur, true)+"-----------------------------------");
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
