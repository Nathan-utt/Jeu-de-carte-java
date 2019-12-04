package Class;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import Enumeration.Couleur;
import Enumeration.Hauteur;
import Enumeration.TropheeEnum;
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
	
	public static Scanner sc = new Scanner(System.in);
	
	
	//Constructor
	
	public MaitreDuJeu() {
		super();
		this.setUsedVariante();
		this.setPlayingWithExtension();
		this.Players = new ArrayList<Joueur>();
		this.playerCreation();
		this.jeuDeCarte = new HashSet<Carte>();
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
		System.out.println("Voulez vous jouer avec les cartes normales ou �tendues?");
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
			ArrayList<TropheeDesc> trophees = new ArrayList<TropheeDesc>(Arrays.asList(new TropheeDesc[] {
					new TropheeDesc(TropheeEnum.bestJest,null),
					new TropheeDesc(TropheeEnum.joker,null),
					new TropheeDesc(TropheeEnum.majorite,Hauteur.As),
					new TropheeDesc(TropheeEnum.majorite,Hauteur.Deux),
					new TropheeDesc(TropheeEnum.majorite,Hauteur.Trois),
					new TropheeDesc(TropheeEnum.majorite,Hauteur.Quatre),
					new TropheeDesc(TropheeEnum.plusGrand,Couleur.Carreau),
					new TropheeDesc(TropheeEnum.plusGrand,Couleur.Coeur),
					new TropheeDesc(TropheeEnum.plusGrand,Couleur.Pique),
					new TropheeDesc(TropheeEnum.plusGrand,Couleur.Trefle),
					new TropheeDesc(TropheeEnum.plusPetit,Couleur.Carreau),
					new TropheeDesc(TropheeEnum.plusPetit,Couleur.Coeur),
					new TropheeDesc(TropheeEnum.plusPetit,Couleur.Pique),
					new TropheeDesc(TropheeEnum.plusPetit,Couleur.Trefle),
					new TropheeDesc(TropheeEnum.bestJestNoJoker,null)
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
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.bestJest,null),Hauteur.Joker,Couleur.Null,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.joker,null),Hauteur.As,Couleur.Coeur,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.joker,null),Hauteur.Deux,Couleur.Coeur,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.joker,null),Hauteur.Trois,Couleur.Coeur,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.joker,null),Hauteur.Quatre,Couleur.Coeur,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.majorite,Hauteur.Quatre),Hauteur.As,Couleur.Carreau,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusGrand,Couleur.Carreau),Hauteur.Deux,Couleur.Carreau,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusPetit,Couleur.Carreau),Hauteur.Trois,Couleur.Carreau,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.bestJestNoJoker,null),Hauteur.Quatre,Couleur.Carreau,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusGrand,Couleur.Trefle),Hauteur.As,Couleur.Pique,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.majorite,Hauteur.Trois),Hauteur.Deux,Couleur.Pique,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.majorite,Hauteur.Deux),Hauteur.Trois,Couleur.Pique,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusPetit,Couleur.Trefle),Hauteur.Quatre,Couleur.Pique,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusGrand,Couleur.Pique),Hauteur.As,Couleur.Trefle,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusPetit,Couleur.Coeur),Hauteur.Deux,Couleur.Trefle,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusGrand,Couleur.Coeur),Hauteur.Trois,Couleur.Trefle,false));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusPetit,Couleur.Pique),Hauteur.Quatre,Couleur.Trefle,false));
			
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.joker,null),Hauteur.Cinq,Couleur.Coeur,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.joker,null),Hauteur.Six,Couleur.Coeur,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.joker,null),Hauteur.Sept,Couleur.Coeur,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusGrand,Couleur.Carreau),Hauteur.Cinq,Couleur.Carreau,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.majorite,Hauteur.Six),Hauteur.As,Couleur.Carreau,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusPetit,Couleur.Carreau),Hauteur.Sept,Couleur.Carreau,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusPetit,Couleur.Carreau),Hauteur.Cinq,Couleur.Pique,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.bestJestNoJoker,null),Hauteur.Six,Couleur.Pique,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusGrand,Couleur.Trefle),Hauteur.Sept,Couleur.Pique,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.majorite,Hauteur.Trois),Hauteur.Cinq,Couleur.Trefle,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.majorite,Hauteur.Deux),Hauteur.Six,Couleur.Trefle,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusPetit,Couleur.Trefle),Hauteur.Sept,Couleur.Trefle,true));
		}	
		this.jeuDeCarte.add(new CarteRegle("Pique et Tr�fle rajoute des points correspondant � leur Hauteur. Un Pique et un Tr�fle de m�me Hauteur rajoute deux points. Les Carreaux enl�vent des points �gal � leur Hauteur. Le Joker et les quatres Coeurs ensemblent valent autant de point que la Hauteur des Coeurs, sinon ils enl�vent des points. Le joker sans aucun coeur rapporte quatre points.",false));
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
					if	(flag) System.out.println("Vous devez choisir un pseudo diff�rent pour chaque joueur");
					System.out.println("Comment voulez vous appeler le joueur "+pn+"? (de 2 � 16 charact�res)");
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
				System.out.println("Aucune similarit� avec les choix");
			}
			if (idMatched != null && idMatched.size() > 1) {
				System.out.println("Similarit� avec plusieurs choix");
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
	
	public Integer getScore(Joueur player,Boolean withJoker) {
		if (this.usedVariante == Variante.Reversed) {
			return player.calculateScoreReversed(withJoker,this.playingWithExtension);
		} else {
			return player.calculateScoreBase(withJoker,this.playingWithExtension);
		}
	}
	
	public void awardTrophees() {
		Joueur[] awardedPlayer = {null,null};
		Integer[] valuePlayer = {0,0};
		
		Integer count = 0;
		
		
		Iterator<Carte> iteTrophees = this.trophees.getDeck().iterator();
		while (iteTrophees.hasNext()) {
			Iterator<Joueur> iteJoueur = this.Players.iterator();
			Carte carte = (Carte) iteTrophees.next();
			if (carte.getClass().equals(CartesNumerotees.class)) {
				CartesNumerotees card =  (CartesNumerotees)carte;
				switch (card.getTrophee().getTropheeDesc()) {
				case bestJest:
					while (iteJoueur.hasNext()) {
						Joueur joueur = (Joueur) iteJoueur.next();
						if (awardedPlayer[count] == null) {
							awardedPlayer[count] = joueur;
							valuePlayer[count] = this.getScore(joueur,true);
						} else {
							if (this.getScore(joueur,true) > valuePlayer[count]) {
								awardedPlayer[count] = joueur;
								valuePlayer[count] = this.getScore(joueur,true);
							}
						}
					}
					break;
				case bestJestNoJoker:
					while (iteJoueur.hasNext()) {
						Joueur joueur = (Joueur) iteJoueur.next();
						if (awardedPlayer[count] == null) {
							awardedPlayer[count] = joueur;
							valuePlayer[count] = this.getScore(joueur,false);
						} else {
							if (this.getScore(joueur,true) > valuePlayer[count]) {
								awardedPlayer[count] = joueur;
								valuePlayer[count] = this.getScore(joueur,false);
							}
						}
					}
					break;
				default:
					while (iteJoueur.hasNext()) {
						Joueur joueur = (Joueur) iteJoueur.next();
						if (awardedPlayer[count] == null) {
							awardedPlayer[count] = joueur;
							valuePlayer[count] = joueur.getInfoForThrophees(card.getTrophee().getTropheeDesc(), card.getTrophee().getInfoSupp());
						} else {
							if (joueur.getInfoForThrophees(card.getTrophee().getTropheeDesc(), card.getTrophee().getInfoSupp()) > valuePlayer[count]) {
								awardedPlayer[count] = joueur;
								valuePlayer[count] = joueur.getInfoForThrophees(card.getTrophee().getTropheeDesc(), card.getTrophee().getInfoSupp());
							}
						}
					}
					break;
				}
			}
			count += 1;
		}
		
		iteTrophees = this.trophees.getDeck().iterator();
		Integer playerNumber = 0;
		while (iteTrophees.hasNext()) {
			Carte carte = (Carte) iteTrophees.next();
			awardedPlayer[playerNumber].getJest().putCardFirst(carte);
			System.out.println(awardedPlayer[playerNumber].getPseudo()+" a recu le trophee "+carte+" -> "+((CartesNumerotees)carte).getTrophee());
			playerNumber++;
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
				scoreWinner = this.getScore(joueur, true);
			} else {
				if (this.getScore(joueur, true) > scoreWinner) {
					winner = joueur;
					scoreWinner = this.getScore(joueur, true);
				}
			}
		}
		
		return winner;
	}
	
}
