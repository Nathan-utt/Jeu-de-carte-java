package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import Enumeration.Status;
import Enumeration.Variante;

public class SaveData implements Serializable {
	/**
	 * 
	 */
	private ArrayList<Joueur> Players;
	/**
	 * 
	 */
	private HashSet<Joueur> remainingPlayers;
	/**
	 * 
	 */
	private HashSet<Joueur> alreadyChoosePlayers;
	/**
	 * 
	 */
	private Deck remainingDeck;
	/**
	 * 
	 */
	private Deck distributionDeck;
	/**
	 * 
	 */
	private Deck trophees;
	/**
	 * 
	 */
	private Joueur currentPlayer;
	/**
	 * 
	 */
	private Variante usedVariante;
	/**
	 * 
	 */
	private Boolean playingWithExtension;
	/**
	 * 
	 */
	private ScoreVisitor sv;
	/**
	 * 
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
