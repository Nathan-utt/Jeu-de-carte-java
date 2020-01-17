package Model;

import java.io.Console;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;

import Enumeration.Couleur;
import Enumeration.Hauteur;
import Enumeration.Status;
import Enumeration.Variante;


/**
 * 
 * Cette m�thode permet la cration et le d�roulement de la partie en entier.
 * Cette classe utilise le design pattern Observable pour pouvoir l'observer dans les vues ce qui entra�nera ou non une mise � jour des vues.
 * Cette classe implemente Serializable afin de pouvoir cr�er une sauvegarde de la partie.
 * 
 * 
 * @author Guillaume et Nathan
 * 
 * @serial Serializable
 *
 */
public class MaitreDuJeu extends Observable implements Serializable{
	
	/**
	 * La liste des joueurs
	 */
	private ArrayList<Joueur> Players;
	/**
	 * La liste des joueurs restant � piocher
	 */
	private HashSet<Joueur> remainingPlayers;
	/**
	 * La liste des joueurs qui ont d�ja choisit
	 */
	private HashSet<Joueur> alreadyChoosePlayers;
	/**
	 * Une collection de toute les cartes du jeu
	 */
	private HashSet<Carte> jeuDeCarte;
	/**
	 * Le deck restant
	 */
	private Deck remainingDeck;
	/**
	 * Le deck de distribution
	 */
	private Deck distributionDeck;
	/**
	 * Le deck de troph�e
	 */
	private Deck trophees;
	/**
	 * Le joueur courant
	 */
	private Joueur currentPlayer;
	/**
	 * La variante utilis�e
	 */
	private Variante usedVariante;
	/**
	 * Si l'extension est utilis�e
	 */
	private Boolean playingWithExtension;
	/**
	 * Le visiteur de score
	 */
	private ScoreVisitor sv;
	/**
	 * L'etat du jeu
	 */
	private Status status;
	
	//Constructor
	
	/**
	 * 
	 */
	public MaitreDuJeu() {
		this.status = null;
		this.currentPlayer = null;
		this.Players = new ArrayList<Joueur>();
		this.jeuDeCarte = new HashSet<Carte>();
	}

	//Setter/Getter
	
	
	
	/**
	 * Lance le jeu en initialisant les tableaux et les variable selon les param�tres de d�marrage.
	 * @return void
	 */
	public void startGame() {
		this.clearAll();
		this.alreadyChoosePlayers = new HashSet<Joueur>();
		this.remainingPlayers = new HashSet<Joueur>(this.Players);
		this.sv = new ScoreVisitor(playingWithExtension,this.usedVariante == Variante.Reversed);
		this.setJeuDeCarte(this.usedVariante == Variante.Random ? true : false);
		this.remainingDeck = new Deck(this.playingWithExtension ? 29 : 17);
		this.setRemainingDeck();
		this.trophees = new Deck(2-this.Players.size()%3);
		this.setTrophees();
		this.distributionDeck = new Deck(this.Players.size()*2);
		this.setDistributionDeck();
	}
	
	/**
	 * Permet de r�initialiser le jeu
	 * @return void
	 */
	public void clearAll() {
		this.currentPlayer = null;
		this.jeuDeCarte = new HashSet<Carte>();
	}
	
	public Status getStatus() {
		return status;
	}
	
	/**
	 * Permet de changer l'�tat du jeu.
	 * Lors d'un changement d'�tat tout les observateur sont notifier.
	 * On effectue aussi les op�rations n�cessaires selon l'�tat.
	 * @return void
	 */
	public void setStatus(Status status) {	
		this.status = status;
		this.setChanged();
		this.notifyObservers(this.status);
		switch (status) {
		case start:
			this.startGame();
			this.setStatus(Status.distribution);
			break;
		case distribution:
			this.distribute();
			this.setStatus(Status.choosingForOffer);
			break;
		case choosingForOffer:
			Joueur j;
			if (remainingPlayers.iterator().hasNext()) {
				j = remainingPlayers.iterator().next();
				this.currentPlayer = j;
				this.remainingPlayers.remove(j);
				if (this.currentPlayer instanceof JoueurVirtuel) {
					((JoueurVirtuel)this.currentPlayer).makeOffer();
					this.setStatus(Status.choosingForOffer);
				} else {
					this.setStatus(Status.makingOffer);
				}
			} else {
				this.currentPlayer = null;
				this.remainingPlayers = new HashSet<Joueur>(this.Players);
				this.setStatus(Status.choosingForTaking);
			}
			break;
		case choosingForTaking:
			if (remainingPlayers.size() != 0) {
				if (currentPlayer == null || alreadyChoosePlayers.contains(currentPlayer)) {
					currentPlayer = this.comparePlayersOffer(remainingPlayers);
				}
				alreadyChoosePlayers.add(currentPlayer);
				if (this.currentPlayer instanceof JoueurVirtuel) {
					this.setCurrentPlayer(((JoueurVirtuel)currentPlayer).takeOffer(this.getRemainingPlayers()));
					this.setStatus(Status.choosingForTaking);
				} else {
					this.setStatus(Status.takingOffer);
				}
			} else {
				this.currentPlayer = null;
				this.alreadyChoosePlayers.clear();
				this.remainingPlayers = new HashSet<Joueur>(this.Players);
				this.setStatus(Status.checkForEnd);
			}
			break;
		case checkForEnd:
			if (this.getRemainingDeck().getDeck().isEmpty()) {
				this.getCardsRemainingToJest();
				this.setStatus(Status.awardTrophees);
			} else {
				this.getCardsBack();
				this.setDistributionDeck();
				this.setStatus(Status.distribution);
			}
			break;
		case awardTrophees:
			this.awardTrophees();
			this.setStatus(Status.determineWinner);
			break;
		default:
			break;
		}
	}

	public Joueur getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Joueur currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void setUsedVariante(Variante usedVariante) {
		this.usedVariante = usedVariante;
	}
	
	public Variante getUsedVariante() {
		return usedVariante;
	}

	public void setPlayingWithExtension(Boolean playingWithExtension) {
		this.playingWithExtension = playingWithExtension;
	}
	
	public Boolean getPlayingWithExtension() {
		return playingWithExtension;
	}

	public HashSet<Carte> getJeuDeCarte() {
		return jeuDeCarte;
	}
	
	public ScoreVisitor getSv() {
		return this.sv;
	}
	
	public void setRemainingDeck() {
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

	/**
	 * Permet d'initialiser le jeu de carte selon le mode de jeu
	 * @param random mode de jeu random utilis�?
	 * @return void
	 */
	public void setJeuDeCarte(Boolean random) {
		if (random) {
			ArrayList<Trophee> trophees = new ArrayList<Trophee>(Arrays.asList(new Trophee[] {
					new TropheeBest(sv),
					new TropheeJoker(),
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
			Random r = new Random();
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Joker,Couleur.Null,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.As,Couleur.Coeur,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Deux,Couleur.Coeur,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Trois,Couleur.Coeur,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Quatre,Couleur.Coeur,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.As,Couleur.Carreau,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Deux,Couleur.Carreau,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Trois,Couleur.Carreau,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Quatre,Couleur.Carreau,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.As,Couleur.Pique,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Deux,Couleur.Pique,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Trois,Couleur.Pique,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Quatre,Couleur.Pique,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.As,Couleur.Trefle,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Deux,Couleur.Trefle,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Trois,Couleur.Trefle,false));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Quatre,Couleur.Trefle,false));
			
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Cinq,Couleur.Coeur,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Six,Couleur.Coeur,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Sept,Couleur.Coeur,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Cinq,Couleur.Carreau,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.As,Couleur.Carreau,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Sept,Couleur.Carreau,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Cinq,Couleur.Pique,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Six,Couleur.Pique,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Sept,Couleur.Pique,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Cinq,Couleur.Trefle,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Six,Couleur.Trefle,true));
			this.jeuDeCarte.add(new CartesNumerotees(trophees.get(r.nextInt(trophees.size())),Hauteur.Sept,Couleur.Trefle,true));
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
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeMajority(Hauteur.Quatre),Hauteur.As,Couleur.Carreau,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeLowest(Couleur.Carreau),Hauteur.Sept,Couleur.Carreau,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeLowest(Couleur.Carreau),Hauteur.Cinq,Couleur.Pique,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeBestNoJoker(sv),Hauteur.Six,Couleur.Pique,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeHighest(Couleur.Trefle),Hauteur.Sept,Couleur.Pique,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeMajority(Hauteur.Trois),Hauteur.Cinq,Couleur.Trefle,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeMajority(Hauteur.Deux),Hauteur.Six,Couleur.Trefle,true));
			this.jeuDeCarte.add(new CartesNumerotees(new TropheeLowest(Couleur.Trefle),Hauteur.Sept,Couleur.Trefle,true));
		}	
		this.jeuDeCarte.add(new CarteRegle("Pique et Tr�fle rajoute des points correspondant � leur Hauteur. Un Pique et un Tr�fle de même Hauteur rajoute deux points. Les Carreaux enl�vent des points �gal � leur Hauteur. Le Joker et les quatres Coeurs ensemblent valent autant de point que la Hauteur des Coeurs, sinon ils enl�vent des points. Le joker sans aucun coeur rapporte quatre points.",false));
	}

	public ArrayList<Joueur> getPlayers() {
		return Players;
	}
	
	/**
	 * Ajoute un joueur � la liste des joueurs
	 * @param player le joueur � ajouter
	 * @return void
	 */
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
	

	public HashSet<Joueur> getRemainingPlayers() {
		return remainingPlayers;
	}
	
	public HashSet<Joueur> getAlreadyChoosePlayers() {
		return alreadyChoosePlayers;
	}
	
	//Function
	

	/**
	 * Distribue les cartes du paquets de distribution au diff�rents joueurs.
	 * @return void
	 */
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
	
	/**
	 * Permet de remettre les cartes restantes des offres dans le paquet de distribution
	 * @return void
	 */
	public void getCardsBack() {
		Iterator<Joueur> iteJoueur = this.Players.iterator();
		while (iteJoueur.hasNext()) {
			Joueur joueur = (Joueur) iteJoueur.next();
			Carte returnedCard = joueur.getOffer().getFirstCard();
			returnedCard.setVisible(false);
			this.distributionDeck.putCardFirst(returnedCard);
		}
	}
	
	/**
	 * Permet de remettre les cartes restantes des offres dans le jest de chaque joueur.
	 * @return void
	 */
	public void getCardsRemainingToJest() {
		Iterator<Joueur> iteJoueur = this.Players.iterator();
		while (iteJoueur.hasNext()) {
			Joueur joueur = (Joueur) iteJoueur.next();
			Carte returnedCard = joueur.getOffer().getFirstCard();
			returnedCard.setVisible(true);
			joueur.getJest().putCardFirst(returnedCard);
		}
	}
	
	/**
	 * Permet de choisir le prochain joueur qui doit choisir, selon les offres de chaque joueur.
	 * @param players les joueurs
	 * @return Le joueur qui peut choisir
	 */
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
	
	/**
	 * Permet d'attribuer les troph�es dans le deck de troph�e
	 * @return void
	 */
	public void awardTrophees() {
		Iterator<Carte> iteTrophees = this.trophees.getDeck().iterator();
		while (iteTrophees.hasNext()) {
			Iterator<Joueur> iteJoueur = this.Players.iterator();
			Carte carte = (Carte) iteTrophees.next();
			if (carte instanceof CartesNumerotees) {
				CartesNumerotees card =  (CartesNumerotees)carte;
				Joueur awardedPlayer = card.getTrophee().award(this.Players);
				awardedPlayer.getJest().putCardFirst(carte);
				this.setChanged();
				this.notifyObservers(carte);
			}
		}
	}
	
	/**
	 * Permet de d�terminer le gagnant gr�ce au visiteur de score.
	 * @return le gagnant
	 */
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

	/**
	 * Recharge le jeu gr�ce � l'objet de recharge et notifie les vues que le jeu a �t� charg�.
	 * @param savedata
	 * @return void
	 */
	public void reload(SaveData savedata) {
		this.Players = savedata.getPlayers();
		this.remainingPlayers = savedata.getRemainingPlayers();
		this.alreadyChoosePlayers = savedata.getAlreadyChoosePlayers();
		this.remainingDeck = savedata.getRemainingDeck();
		this.distributionDeck = savedata.getDistributionDeck();
		this.trophees = savedata.getTrophees();
		this.currentPlayer = savedata.getCurrentPlayer();
		this.usedVariante = savedata.getUsedVariante();
		this.playingWithExtension = savedata.getPlayingWithExtension();
		this.sv = savedata.getSv();
		this.status = savedata.getStatus();
		this.setChanged();
		this.notifyObservers(this.status);
	}
	
}
