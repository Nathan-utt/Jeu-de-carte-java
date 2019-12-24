package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import Enumeration.Couleur;
import Enumeration.Hauteur;
import Enumeration.Variante;

public class MaitreDuJeu {
	
	private ArrayList<Joueur> Players;
	private HashSet<Carte> jeuDeCarte;
	private Deck remainingDeck;
	private Deck distributionDeck;
	private Deck trophees;
	private Joueur currentPlayer;
	private Variante usedVariante;
	private Boolean playingWithExtension;
	private ScoreVisitor sv;
	
	public static Scanner sc = new Scanner(System.in);
	
	
	//Constructor
	
	public MaitreDuJeu() {
		super();
		this.setUsedVariante();
		this.setPlayingWithExtension();
		this.Players = new ArrayList<Joueur>();
		this.playerCreation();
		this.jeuDeCarte = new HashSet<Carte>();
		this.sv = new ScoreVisitor(playingWithExtension,this.usedVariante == Variante.Reversed);
		this.setJeuDeCarte(this.usedVariante == Variante.Random ? true : false);
		this.remainingDeck = new Deck(this.playingWithExtension ? 29 : 17);
		this.setRemainingDeck();
		this.trophees = new Deck(2-this.Players.size()%3);
		this.setTrophees();
		this.distributionDeck = new Deck(this.Players.size()*2);
		this.setDistributionDeck();
	}

	//Setter/Getter
	
	public void setUsedVariante() {
		System.out.println("Voulez vous jouer en mode normal ou reversed ou random trophees?");
		String choice = MaitreDuJeu.askForChoice(new ArrayList<String>(Arrays.asList(new String[] {"Normal","Reverse","Random Trophees"})));
		switch (choice) {
		case "Normal":
			this.usedVariante = Variante.Normal;
			break;
		case "Reverse":
			this.usedVariante = Variante.Reversed;
			break;
		case "Random Trophees":
			this.usedVariante = Variante.Random;
			break;
		default:
			break;
		}
	}
	
	public void setPlayingWithExtension() {
		System.out.println("Voulez vous jouer avec les cartes normales ou étendues?");
		String choice = MaitreDuJeu.askForChoice(new ArrayList<String>(Arrays.asList(new String[] {"Normale","Etendue"})));
		switch (choice) {
		case "Normale":
			this.playingWithExtension = false;
			break;
		case "Etendue":
			this.playingWithExtension = true;
			break;
		default:
			break;
		}
	}
	
	public HashSet<Carte> getJeuDeCarte() {
		return jeuDeCarte;
	}
	
	public ScoreVisitor getSv() {
		return this.sv;
	}
	
	public void setRemainingDeck() {
		System.out.println("jeu");
		Iterator<Carte> iteJeuDeCarte = this.jeuDeCarte.iterator();
		while (iteJeuDeCarte.hasNext()) {
			Carte carte = (Carte) iteJeuDeCarte.next();
			if (!playingWithExtension) {
				if (carte.getClass().equals(CartesNumerotees.class) && !carte.getIsExtension()) this.remainingDeck.putCardLast(carte);
			} else {
				if (carte.getClass().equals(CartesNumerotees.class)) this.remainingDeck.putCardLast(carte);
			}
		}
		this.remainingDeck.shuffleDeck(1000);
	}
	
	public void setTrophees() {
		while (this.trophees.getDeck().size() < this.trophees.getMaxCards()) {
			Carte carte = this.remainingDeck.getFirstCard();
			carte.setVisible(true);
			this.trophees.putCardFirst(carte);
		}
	}
	
	public void setDistributionDeck() {
		while (this.distributionDeck.getDeck().size() < this.distributionDeck.getMaxCards()) {
			this.distributionDeck.putCardFirst(this.remainingDeck.getFirstCard());
		}
	}

	public void setJeuDeCarte(Boolean random) {
		if (random) {
			ArrayList<Trophee> trophees = new ArrayList<Trophee>(Arrays.asList(new Trophee[] {
					new TropheeBest(sv),
					new TropheeJoker(),
					new TropheeMajority(Hauteur.As),
					new TropheeMajority(Hauteur.Deux),
					new TropheeMajority(Hauteur.Trois),
					new TropheeMajority(Hauteur.Quatre),
					new TropheeHighest(Couleur.Carreau),
					new TropheeHighest(Couleur.Coeur),
					new TropheeHighest(Couleur.Pique),
					new TropheeHighest(Couleur.Trefle),
					new TropheeLowest(Couleur.Carreau),
					new TropheeLowest(Couleur.Coeur),
					new TropheeLowest(Couleur.Pique),
					new TropheeLowest(Couleur.Trefle),
					new TropheeBestNoJoker(sv)
					}));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Joker,Couleur.Null,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.As,Couleur.Coeur,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Deux,Couleur.Coeur,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Trois,Couleur.Coeur,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Quatre,Couleur.Coeur,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.As,Couleur.Carreau,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Deux,Couleur.Carreau,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Trois,Couleur.Carreau,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Quatre,Couleur.Carreau,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.As,Couleur.Pique,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Deux,Couleur.Pique,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Trois,Couleur.Pique,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Quatre,Couleur.Pique,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.As,Couleur.Trefle,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Deux,Couleur.Trefle,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Trois,Couleur.Trefle,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Quatre,Couleur.Trefle,false));
			
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Cinq,Couleur.Coeur,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Six,Couleur.Coeur,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Sept,Couleur.Coeur,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Cinq,Couleur.Carreau,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.As,Couleur.Carreau,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Sept,Couleur.Carreau,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Cinq,Couleur.Pique,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Six,Couleur.Pique,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Sept,Couleur.Pique,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Cinq,Couleur.Trefle,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Six,Couleur.Trefle,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get((int)Math.random()*trophees.size()),Hauteur.Sept,Couleur.Trefle,true));
		} else {
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeBest(sv),Hauteur.Joker,Couleur.Null,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeJoker(),Hauteur.As,Couleur.Coeur,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeJoker(),Hauteur.Deux,Couleur.Coeur,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeJoker(),Hauteur.Trois,Couleur.Coeur,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeJoker(),Hauteur.Quatre,Couleur.Coeur,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeMajority(Hauteur.Quatre),Hauteur.As,Couleur.Carreau,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeHighest(Couleur.Carreau),Hauteur.Deux,Couleur.Carreau,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeLowest(Couleur.Carreau),Hauteur.Trois,Couleur.Carreau,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeBestNoJoker(sv),Hauteur.Quatre,Couleur.Carreau,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeHighest(Couleur.Trefle),Hauteur.As,Couleur.Pique,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeMajority(Hauteur.Trois),Hauteur.Deux,Couleur.Pique,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeMajority(Hauteur.Deux),Hauteur.Trois,Couleur.Pique,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeLowest(Couleur.Trefle),Hauteur.Quatre,Couleur.Pique,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeHighest(Couleur.Pique),Hauteur.As,Couleur.Trefle,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeLowest(Couleur.Coeur),Hauteur.Deux,Couleur.Trefle,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeHighest(Couleur.Coeur),Hauteur.Trois,Couleur.Trefle,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeLowest(Couleur.Pique),Hauteur.Quatre,Couleur.Trefle,false));
			
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeJoker(),Hauteur.Cinq,Couleur.Coeur,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeJoker(),Hauteur.Six,Couleur.Coeur,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeJoker(),Hauteur.Sept,Couleur.Coeur,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeHighest(Couleur.Carreau),Hauteur.Cinq,Couleur.Carreau,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeMajority(Hauteur.Six),Hauteur.As,Couleur.Carreau,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeLowest(Couleur.Carreau),Hauteur.Sept,Couleur.Carreau,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeLowest(Couleur.Carreau),Hauteur.Cinq,Couleur.Pique,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeBestNoJoker(sv),Hauteur.Six,Couleur.Pique,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeHighest(Couleur.Trefle),Hauteur.Sept,Couleur.Pique,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeMajority(Hauteur.Trois),Hauteur.Cinq,Couleur.Trefle,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeMajority(Hauteur.Deux),Hauteur.Six,Couleur.Trefle,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeLowest(Couleur.Trefle),Hauteur.Sept,Couleur.Trefle,true));
		}	
		this.jeuDeCarte.add(new CarteRegle("Pique et Trèfle rajoute des points correspondant à leur Hauteur. Un Pique et un Trèfle de même Hauteur rajoute deux points. Les Carreaux enlèvent des points égal à leur Hauteur. Le Joker et les quatres Coeurs ensemblent valent autant de point que la Hauteur des Coeurs, sinon ils enlèvent des points. Le joker sans aucun coeur rapporte quatre points.",false));
	}

	public ArrayList<Joueur> getPlayers() {
		return Players;
	}
	
	public void addPlayer(Joueur player) {
		Players.add(player);
	}
	
	public Deck getRemainingDeck() {
		return remainingDeck;
	}

	public Deck getDistributionDeck() {
		return distributionDeck;
	}
	
	public Deck getTrophees() {
		return trophees;
	}
	
	//Function
	
	public void distribute() {
		for (int i = 0; i < 2; i++) {
			Iterator<Joueur> iteJoueur = this.Players.iterator();
			while (iteJoueur.hasNext()) {
				Joueur joueur = (Joueur) iteJoueur.next();
				Carte distributedCard = this.distributionDeck.getFirstCard();
				distributedCard.setVisible(true);
				joueur.getHand().putCardLast(distributedCard);
			}
		}
	}
	
	public void getCardsBack() {
		Iterator<Joueur> iteJoueur = this.Players.iterator();
		while (iteJoueur.hasNext()) {
			Joueur joueur = (Joueur) iteJoueur.next();
			Carte returnedCard = joueur.getOffer().getFirstCard();
			returnedCard.setVisible(false);
			this.distributionDeck.putCardFirst(returnedCard);
		}
	}
	
	public void getCardsRemainingToJest() {
		Iterator<Joueur> iteJoueur = this.Players.iterator();
		while (iteJoueur.hasNext()) {
			Joueur joueur = (Joueur) iteJoueur.next();
			Carte returnedCard = joueur.getOffer().getFirstCard();
			returnedCard.setVisible(true);
			joueur.getJest().putCardFirst(returnedCard);
		}
	}

	public void playerCreation() {
		Integer numberOfPlayers = 0;
		Integer computerCount = 1;
		
		System.out.println("Avec combien de joueurs voulez vous jouer?");
		numberOfPlayers = Integer.parseInt(MaitreDuJeu.askForChoice(new ArrayList<String>(Arrays.asList(new String[] {"3","4"})))); 
			
		for (int pn = 1; pn <= numberOfPlayers; pn++) {
			System.out.println("Le joueur "+pn+" est-il un joueur ou un ordinateur?");
			if (MaitreDuJeu.askForChoice(new ArrayList<String>(Arrays.asList(new String[] {"Joueur","Ordinateur"}))) == "Joueur") {
				String entered = "";
				Boolean flag = false;
				do {
					if	(flag) System.out.println("Vous devez choisir un pseudo différent pour chaque joueur");
					System.out.println("Comment voulez vous appeler le joueur "+pn+"? (de 2 à 16 charactères)");
					entered = sc.nextLine();
					Iterator<Joueur> itePlayer = this.Players.iterator();
					flag = false;
					while (itePlayer.hasNext()) {
						Joueur onePlayer = itePlayer.next();
						if (onePlayer.getPseudo().equals(entered)) flag = true;
					}
				} while (flag || entered.length() < 2 || entered.length() > 16);
				this.addPlayer(new Joueur(entered,pn));
			} else {
				this.addPlayer(new JoueurVirtuel("Ordinateur"+computerCount,pn));
				computerCount++;
			}
		}
	}
	
	public static String askForChoice(ArrayList<String> choices) {
		HashSet<Integer> idMatched = null;
		do {
			if (idMatched != null && idMatched.isEmpty()) {
				System.out.println("Aucune similarité avec les choix");
			}
			if (idMatched != null && idMatched.size() > 1) {
				System.out.println("Similarité avec plusieurs choix");
			for (Integer id : idMatched) {
					System.out.println(choices.get(id));
				}
			}
			idMatched = new HashSet<Integer>();
			System.out.println("Choisis entre : ");
			for (String value : choices) {
				System.out.print((value == choices.get(choices.size()-1)) ? value : value+", ");
				
			}
			System.out.println();
			
			String entered = "";
			if (sc.hasNextLine()) entered = sc.nextLine();
			
			for (int i=0; i<choices.size(); i++) {
				if (choices.get(i).toLowerCase().contains(entered.toLowerCase())) {
					idMatched.add(i);
				}
			}
			
		} while (idMatched.size() != 1);
		
		String finalChoice = null;
		for (Integer id : idMatched) {
			System.out.println("Vous avez choisi : " + choices.get(id));
			finalChoice = choices.get(id);
		}
		return finalChoice;
	}
	
	public Joueur comparePlayersOffer(HashSet<Joueur> players) {
		Iterator<Joueur> itePlayers = players.iterator();
		Joueur maxJoueur = null;
		while (itePlayers.hasNext()) {
			Joueur joueur = (Joueur) itePlayers.next();
			if (maxJoueur == null) {
				maxJoueur = joueur;
			} else if (((CartesNumerotees) joueur.getOffer().getDeck().get(1)).isGreaterThan((CartesNumerotees) maxJoueur.getOffer().getDeck().get(1))) {
				maxJoueur = joueur;
			}
		}
		return maxJoueur;
	}
	
	public void takingOffers() {
		Joueur playerThatShouldChooseNow = null;
		HashSet<Joueur> remainingPlayers = new HashSet<Joueur>(this.Players);
		HashSet<Joueur> alreadyChoosePlayers = new HashSet<Joueur>();
		for (int i = 0; i < this.Players.size(); i++) {
			if (playerThatShouldChooseNow == null || alreadyChoosePlayers.contains(playerThatShouldChooseNow)) {
				playerThatShouldChooseNow = this.comparePlayersOffer(remainingPlayers);
			}
			alreadyChoosePlayers.add(playerThatShouldChooseNow);
			System.out.println("A toi "+playerThatShouldChooseNow.getPseudo()+", choisit une carte parmis toutes celles restantes.");
			playerThatShouldChooseNow = playerThatShouldChooseNow.takeOffer(remainingPlayers);
		}
	}
	
	public void makingOffers() {
		Iterator<Joueur> itePlayer = this.Players.iterator();
		while (itePlayer.hasNext()) {
			Joueur joueur = (Joueur) itePlayer.next();
			System.out.println("A toi "+joueur.getPseudo());
			joueur.makeOffer();
		}
	}
	
	public void awardTrophees() {
		Iterator<Carte> iteTrophees = this.trophees.getDeck().iterator();
		while (iteTrophees.hasNext()) {
			Iterator<Joueur> iteJoueur = this.Players.iterator();
			Carte carte = (Carte) iteTrophees.next();
			if (carte instanceof CartesNumerotees) {
				CartesNumerotees card =  (CartesNumerotees)carte;
				Joueur awardedPlayer = card.getTrophee().award(this.Players);
				awardedPlayer.getJest().putCardFirst(carte);
				System.out.println(awardedPlayer.getPseudo()+" a recu le trophee "+carte+" -> "+((CartesNumerotees)carte).getTrophee().getName());
			}
		}
	}
	
	public Joueur getWinner() {
		Joueur winner = null;
		Integer scoreWinner = 0;
		
		Iterator<Joueur> iteJoueur = this.Players.iterator();
		
		while (iteJoueur.hasNext()) {
			Joueur joueur = (Joueur) iteJoueur.next();
			if (winner == null) {
				winner = joueur;
				scoreWinner = sv.getScore(joueur, true);
			} else {
				if (sv.getScore(joueur, true) > scoreWinner) {
					winner = joueur;
					scoreWinner = sv.getScore(joueur, true);
				}
			}
		}
		
		return winner;
	}
	
}
