package View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import Enumeration.Status;
import Enumeration.Variante;
import Model.Carte;
import Model.CartesNumerotees;
import Model.Joueur;
import Model.JoueurVirtuel;
import Model.MaitreDuJeu;

/**
 * La vue Console, elle permet de suivre l'évolution du jeu dans la console.
 * C'est un Thread à part afin de ne pas bloquer les autres vues.
 * Cette vue Observe le MDJ afin de changer lorsque le MDJ est modifier.
 * 
 * @author Guillaume, Nathan
 *
 */
public class ConsoleView implements Observer, Runnable {
	/**
	 * Une référence vers le moteur du jeu car cette vue ne dispose pas de controlleur.
	 */
	private MaitreDuJeu mdj;
	
	/**
	 * Commande par défaut pour Lancer
	 * @deprecated jamais utilisé
	 */
	public static String START = "Lancer";
	/**
	 * Commande par défaut pour quitter
	 * @deprecated jamais utilisé 
	 */
	public static String QUIT = "Quitter";
	/**
	 * Commande par défaut pour sauvegarder
	 * @deprecated jamais utilisé 
	 */
	public static String SAVE = "Sauvegarder";
	/**
	 * Commande par défaut pour charger
	 * @deprecated jamais utilisé 
	 */
	public static String LOAD = "Charger";
	/**
	 * Commande par défaut pour voir sa Main
	 * @deprecated jamais utilisé 
	 */
	public static String HAND = "Main";
	/**
	 * Commande par défaut pour voir son Jest
	 * @deprecated jamais utilisé 
	 */
	public static String JEST = "Jest";
	
	/**
	 * Le scanner utilisé pour lire dans la console
	 * @deprecated remplacé par le BufferedReader
	 */
	public static Scanner sc = new Scanner(System.in);
	/**
	 * Le bufferedReader utilisé pour lire dans la console
	 */
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	/**
	 * Contient le thread en cours, si un thread de lecture de console à été lancé.
	 */
	public Thread runningThread = null;
	
	/**
	 * Création de la vue avec le moteur de jeu.
	 * On ajoute cette vue en tant qu'observeur du mdj.
	 * 
	 * @param mdj
	 */
	public ConsoleView(MaitreDuJeu mdj) {
		this.mdj = mdj;
		this.mdj.addObserver(this);
	}
	
	/**
	 * Permet d'écrire une phrase dans la console avec un retour à la ligne.
	 * @param str la phrase à écrire
	 * @return void
	 */
	public void writeToConsole(String str) {
		System.out.println(str);
	}
	
	/**
	 * Permet d'écrire une phrase dans la console avec ou sans un retour à la ligne.
	 * 
	 * @param str la phrase à écrire
	 * @param newLine un booléen précisant si une nouvelle ligne doit être ajoutée.
	 * @return void
	 */
	public void writeToConsole(String str, Boolean newLine) {
		if (newLine) {
			System.out.println(str);
		} else {
			System.out.print(str);
		}
	}
	
	
	/**
	 * Méthode permettant la séparation entre deux écriture permettant une meilleure lisibilité.
	 * @return void
	 */
	public void clearConsole() {
	    this.writeToConsole("===========================================================================");
	    this.writeToConsole("===========================================================================");
	}
	
	/**
	 * Méthode permettant de demander un choix à l'utilisateur.
	 * Les choix sont renseigné et on regarde le choix final dans un nouveau Thread pour ne pas bloquer.
	 * 
	 * 
	 * @param choices Un tableau de String contenant les choix possibles pour l'utilisateur.
	 * @param addOption Un booléen précisant si on doit ajouter les options de base (sauvergarder...).
	 * @return Un String contenant le choix final de l'utilisateur. 
	 * @exception InterruptedException
	 * Intervient lorsque la vue graphique doit stopper l'entrée de nouvelle valeur car le choix à déjà était fait.
	 */
	public String askForChoice(ArrayList<String> choices, Boolean addOption) {
		if (addOption) {
			choices.add(QUIT);
			choices.add(SAVE);
			choices.add(LOAD);
			choices.add(START);
			if (this.mdj.getCurrentPlayer() != null) {
				choices.add(HAND);
				choices.add(JEST);
			}
		}
		HashSet<Integer> idMatched = null;
		do {
			if (idMatched != null && idMatched.isEmpty()) {
				writeToConsole("Aucune similarité avec les choix");
			}
			if (idMatched != null && idMatched.size() > 1) {
				writeToConsole("Similarité avec plusieurs choix");
			for (Integer id : idMatched) {
					writeToConsole(choices.get(id));
				}
			}
			idMatched = new HashSet<Integer>();
			writeToConsole("Choisis entre : ");
			for (String value : choices) {
				writeToConsole((value == choices.get(choices.size()-1)) ? value : value+", ", false);
				
			}
			writeToConsole("");
			
			String entered = "";
			try {
				while (!br.ready()  /*  ADD SHUTDOWN CHECK HERE */) {
			          Thread.sleep(200);
			        }
			    entered = br.readLine();
				//if (sc.hasNextLine()) entered = sc.nextLine();
			} catch (InterruptedException e) {
				return null;
			} catch (IOException e) {
			}
			
			
			for (int i=0; i<choices.size(); i++) {
				if (choices.get(i).toLowerCase().contains(entered.toLowerCase())) {
					idMatched.add(i);
				}
			}
			
		} while (idMatched.size() != 1);
		
		String finalChoice = null;
		for (Integer id : idMatched) {
			writeToConsole("Vous avez choisi : " + choices.get(id));
			finalChoice = choices.get(id);
		}
		return finalChoice;
	}

	/**
	 * La méthode exécuté lorsque l'on crée un Thread de choix.
	 * Selon l'étape du jeu, soit le statut du moteur de jeu, affiche un choix différent pour l'utilisateur.
	 */
	@Override
	public void run() {
		String choice;
		switch (mdj.getStatus()) {
		case playerChoice:
			Integer numberOfPlayers = 0;
			Integer computerCount = 1;
			String answer = this.askForChoice(new ArrayList<String>(Arrays.asList(new String[] {"3","4"})),false);
			if (answer != null) {
				numberOfPlayers = Integer.parseInt(answer); 
				outerloop:
				for (int pn = 1; pn <= numberOfPlayers; pn++) {
					this.writeToConsole("Le joueur "+pn+" est-il un joueur ou un ordinateur?");
					answer = this.askForChoice(new ArrayList<String>(Arrays.asList(new String[] {"Joueur","Ordinateur"})),false);
					if (answer != null) {
						if (answer == "Joueur") {
							String entered = "";
							Boolean flag = false;
							do {
								if	(flag) this.writeToConsole("Vous devez choisir un pseudo différent pour chaque joueur");
								this.writeToConsole("Comment voulez vous appeler le joueur "+pn+"? (de 2 à 16 charactères)");
								try {
									while (!br.ready()  /*  ADD SHUTDOWN CHECK HERE */) {
								          Thread.sleep(200);
								        }
								    entered = br.readLine();
									//if (sc.hasNextLine()) entered = sc.nextLine();
								} catch (InterruptedException e) {
									answer = null;
									break outerloop;
								} catch (IOException e) {
								}
								Iterator<Joueur> itePlayer = this.mdj.getPlayers().iterator();
								flag = false;
								while (itePlayer.hasNext()) {
									Joueur onePlayer = itePlayer.next();
									if (onePlayer.getPseudo().equals(entered)) flag = true;
								}
							} while (flag || entered.length() < 2 || entered.length() > 16);
							this.mdj.addPlayer(new Joueur(entered,pn));
						} else {
							this.mdj.addPlayer(new JoueurVirtuel("Ordinateur"+computerCount,pn));
							computerCount++;
						}
					} else break outerloop;
				}
				if (answer != null) mdj.setStatus(Status.modeChoice);
			}
			break;
		case modeChoice:
			choice = this.askForChoice(new ArrayList<String>(Arrays.asList(new String[] {"Normal","Reverse","Random Trophees"})),false);
			switch (choice) {
			case "Normal":
				this.mdj.setUsedVariante(Variante.Normal);
				break;
			case "Reverse":
				this.mdj.setUsedVariante(Variante.Reversed);
				break;
			case "Random Trophees":
				this.mdj.setUsedVariante(Variante.Random);
				break;
			default:
				break;
			}
			mdj.setStatus(Status.extensionChoice);
			break;	
		case extensionChoice:
			choice = this.askForChoice(new ArrayList<String>(Arrays.asList(new String[] {"Normale","Etendue"})),false);
			switch (choice) {
			case "Normale":
				this.mdj.setPlayingWithExtension(false);
				break;
			case "Etendue":
				this.mdj.setPlayingWithExtension(true);
				break;
			default:
				break;
			}
			mdj.setStatus(Status.start);
			break;
		case makingOffer:
			Joueur currPlayer = mdj.getCurrentPlayer();
			ArrayList<String> choices = new ArrayList<String>();
			choices.add(currPlayer.getHand().getDeck().get(0).getName());
			choices.add(currPlayer.getHand().getDeck().get(1).getName());
			choice = this.askForChoice(choices, false);
			if (choice != null) {
				Carte hiddenCard = currPlayer.getHand().getCard(choices.indexOf(choice));
				currPlayer.makeOffer(hiddenCard);
				mdj.setStatus(Status.choosingForOffer);
			}
			break;
		case takingOffer:
			Iterator<Joueur> itePlayers = mdj.getRemainingPlayers().iterator();
			choices = new ArrayList<String>();
			HashMap<String,Joueur> offers = new HashMap<String,Joueur>();
			while (itePlayers.hasNext()) {
				Joueur joueur = (Joueur) itePlayers.next();
				if (joueur != mdj.getCurrentPlayer() || mdj.getRemainingPlayers().size() == 1) {
					Iterator<Carte> iteDeck = joueur.getOffer().getDeck().iterator();
					while (iteDeck.hasNext()) {
						Carte carte = (Carte) iteDeck.next();
						offers.put((carte.isVisible() ? carte.getName() : joueur.getPseudo()+" Carte cachée"),joueur);
					}
					this.writeToConsole(joueur.getPseudo()+" propose "+joueur.seeOffer());
					choices.addAll(joueur.constructOffer());
				}
			}
			answer = this.askForChoice(choices,false);
			if (answer != null) {
				this.writeToConsole("debug : "+answer);
				Joueur chosenFrom = offers.get(answer);
				mdj.getRemainingPlayers().remove(chosenFrom);
				Carte chosen = (chosenFrom.getOffer().getDeck().get(1).getName() == answer) ? chosenFrom.getOffer().getCard(1) : chosenFrom.getOffer().getCard(0);
				mdj.getCurrentPlayer().takeOffer(chosen);
				mdj.setCurrentPlayer(chosenFrom);
				this.writeToConsole("Tu as obtenu : "+chosen);
				mdj.setStatus(Status.choosingForTaking);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Méthode s'éxécutant lorsque le moteur de jeu est update. On regarde alors le statut du mdj.
	 * Selon ce statut on va mettre à jour la vue en affichant des messages.
	 */
	@Override
	public void update(Observable instance, Object arg) {
		if (instance instanceof MaitreDuJeu) {
			MaitreDuJeu instanceMdj = (MaitreDuJeu) instance;
			switch (instanceMdj.getStatus()) {
			case playerChoice:
				this.clearConsole();
//				if (runningThread != null) {
//					runningThread.interrupt();
//				}
				this.writeToConsole("Avec combien de joueurs voulez vous jouer?");
				runningThread = new Thread(this);
				runningThread.start();
				break;
			case modeChoice:
				this.clearConsole();
				this.writeToConsole("Voulez vous jouer en mode normal ou reversed ou random trophees?");
				runningThread = new Thread(this);
				runningThread.start();
				break;	
			case extensionChoice:
				this.clearConsole();
				this.writeToConsole("Voulez vous jouer avec les cartes normales ou étendues?");
				runningThread = new Thread(this);
				runningThread.start();
				break;
			case start:
				this.clearConsole();
				if (this.runningThread != null) this.runningThread.interrupt();
				this.writeToConsole("Game is starting");
				break;
			case makingOffer:
				this.clearConsole();
				if (this.runningThread != null) this.runningThread.interrupt();
				Joueur currPlayer = instanceMdj.getCurrentPlayer();
				if (currPlayer != null) {
					this.writeToConsole("A toi "+currPlayer.getPseudo()+", choisit une carte.");
					ArrayList<String> choices = new ArrayList<String>();
					choices.add(currPlayer.getHand().getDeck().get(0).getName());
					choices.add(currPlayer.getHand().getDeck().get(1).getName());
					this.writeToConsole("Vous avez un "+choices.get(0)+" et un "+choices.get(1)+" dans votre main.");
					this.writeToConsole("Choississez la carte que vous souhaitez offrir face cachée, l'autre sera offerte face visible.");
					runningThread = new Thread(this);
					runningThread.start();
				}
				break;
			case takingOffer:
				this.clearConsole();
				if (this.runningThread != null) this.runningThread.interrupt();
				currPlayer = instanceMdj.getCurrentPlayer();
				if (currPlayer != null) {
					this.writeToConsole("A toi "+currPlayer.getPseudo()+", choisit une carte parmis toutes celles restantes.");
					runningThread = new Thread(this);
					runningThread.start();
				}
				break;
			case awardTrophees:
				this.clearConsole();
				if (this.runningThread != null) this.runningThread.interrupt();
				if (arg instanceof Carte) {
					Carte carte = (Carte) arg;
					Iterator<Joueur> iteJ = instanceMdj.getPlayers().iterator();
					Joueur awardedPlayer = null;
					while (iteJ.hasNext()) {
						Joueur joueur = (Joueur) iteJ.next();
						if (joueur.getJest().getDeck().contains(carte)) {
							awardedPlayer = joueur;
						}
					}
					this.writeToConsole(awardedPlayer.getPseudo()+" a recu le trophee "+carte+" -> "+((CartesNumerotees)carte).getTrophee().getName());
				}
				break;
			case determineWinner:
				this.clearConsole();
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
				}
				break;
			default:
				break;
			}
		}
	}
}
