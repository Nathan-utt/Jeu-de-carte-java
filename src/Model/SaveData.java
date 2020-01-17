package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import Enumeration.Status;
import Enumeration.Variante;

/**
 * 
 * Cette classe est une classe de sauvegarde qui va chercher les éléments à sauvegarder et les inscrire dans le fichier ou la sauvegarde est stockée.
 * 
 * @author Guillaume et Nathan
 *
 */

public class SaveData implements Serializable {
	/**
	 * La liste des joueurs
	 */
	private ArrayList<Joueur> Players;
	/**
	 * La liste des joueurs restant à piocher
	 */
	private HashSet<Joueur> remainingPlayers;
	/**
	 * La liste des joueurs qui ont déja choisit
	 */
	private HashSet<Joueur> alreadyChoosePlayers;
	/**
	 * Le deck restant
	 */
	private Deck remainingDeck;
	/**
	 * Le deck de distribution
	 */
	private Deck distributionDeck;
	/**
	 * Le deck de trophée
	 */
	private Deck trophees;
	/**
	 * Le joueur courant
	 */
	private Joueur currentPlayer;
	/**
	 * La variante utilisée
	 */
	private Variante usedVariante;
	/**
	 * Si l'extension est utilisée
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
	/**
	 * @param players
	 * @param remainingPlayers
	 * @param alreadyChoosePlayers
	 * @param remainingDeck
	 * @param distributionDeck
	 * @param trophees
	 * @param currentPlayer
	 * @param usedVariante
	 * @param playingWithExtension
	 * @param sv
	 * @param status
	 */
	public SaveData(ArrayList<Joueur> players, HashSet<Joueur> remainingPlayers, HashSet<Joueur> alreadyChoosePlayers,
			Deck remainingDeck, Deck distributionDeck, Deck trophees, Joueur currentPlayer, Variante usedVariante,
			Boolean playingWithExtension, ScoreVisitor sv, Status status) {
		super();
		Players = players;
		this.remainingPlayers = remainingPlayers;
		this.alreadyChoosePlayers = alreadyChoosePlayers;
		this.remainingDeck = remainingDeck;
		this.distributionDeck = distributionDeck;
		this.trophees = trophees;
		this.currentPlayer = currentPlayer;
		this.usedVariante = usedVariante;
		this.playingWithExtension = playingWithExtension;
		this.sv = sv;
		this.status = status;
	}
	public ArrayList<Joueur> getPlayers() {
		return Players;
	}
	public void setPlayers(ArrayList<Joueur> players) {
		Players = players;
	}
	public HashSet<Joueur> getRemainingPlayers() {
		return remainingPlayers;
	}
	public void setRemainingPlayers(HashSet<Joueur> remainingPlayers) {
		this.remainingPlayers = remainingPlayers;
	}
	public HashSet<Joueur> getAlreadyChoosePlayers() {
		return alreadyChoosePlayers;
	}
	public void setAlreadyChoosePlayers(HashSet<Joueur> alreadyChoosePlayers) {
		this.alreadyChoosePlayers = alreadyChoosePlayers;
	}
	public Deck getRemainingDeck() {
		return remainingDeck;
	}
	public void setRemainingDeck(Deck remainingDeck) {
		this.remainingDeck = remainingDeck;
	}
	public Deck getDistributionDeck() {
		return distributionDeck;
	}
	public void setDistributionDeck(Deck distributionDeck) {
		this.distributionDeck = distributionDeck;
	}
	public Deck getTrophees() {
		return trophees;
	}
	public void setTrophees(Deck trophees) {
		this.trophees = trophees;
	}
	public Joueur getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Joueur currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public Variante getUsedVariante() {
		return usedVariante;
	}
	public void setUsedVariante(Variante usedVariante) {
		this.usedVariante = usedVariante;
	}
	public Boolean getPlayingWithExtension() {
		return playingWithExtension;
	}
	public void setPlayingWithExtension(Boolean playingWithExtension) {
		this.playingWithExtension = playingWithExtension;
	}
	public ScoreVisitor getSv() {
		return sv;
	}
	public void setSv(ScoreVisitor sv) {
		this.sv = sv;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
