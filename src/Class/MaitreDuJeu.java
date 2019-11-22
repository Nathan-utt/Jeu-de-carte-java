package Class;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import Enumeration.Couleur;
import Enumeration.Hauteur;
import Enumeration.TropheeEnum;

public class MaitreDuJeu {
	
	private ArrayList<Joueur> Players;
	public Scanner sc = new Scanner(System.in);
	private HashSet<Carte> jeuDeCarte;
	
	//Constructor
	
	public MaitreDuJeu() {
		super();
		Players = new ArrayList<Joueur>();
	}

	//Setter/Getter
	
	public HashSet<Carte> getJeuDeCarte() {
		return jeuDeCarte;
	}

	public void setJeuDeCarte() {
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.bestJest,null),Hauteur.Joker,Couleur.Null));
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.joker,null),Hauteur.As,Couleur.Coeur));
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.joker,null),Hauteur.Deux,Couleur.Coeur));
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.joker,null),Hauteur.Trois,Couleur.Coeur));
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.joker,null),Hauteur.Quatre,Couleur.Coeur));
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.majorite,Hauteur.Quatre),Hauteur.As,Couleur.Carreau));
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusGrand,Couleur.Carreau),Hauteur.Deux,Couleur.Carreau));
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusPetit,Couleur.Carreau),Hauteur.Trois,Couleur.Carreau));
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.bestJestNoJoker,null),Hauteur.Quatre,Couleur.Carreau));
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusGrand,Couleur.Trefle),Hauteur.As,Couleur.Pique));
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.majorite,Hauteur.Trois),Hauteur.Deux,Couleur.Pique));
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.majorite,Hauteur.Deux),Hauteur.Trois,Couleur.Pique));
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusPetit,Couleur.Trefle),Hauteur.Quatre,Couleur.Pique));
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusGrand,Couleur.Pique),Hauteur.As,Couleur.Trefle));
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusPetit,Couleur.Coeur),Hauteur.Deux,Couleur.Trefle));
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusGrand,Couleur.Coeur),Hauteur.Trois,Couleur.Trefle));
		this.jeuDeCarte.add(new CartesNumerotees(new TropheeDesc(TropheeEnum.plusPetit,Couleur.Pique),Hauteur.Quatre,Couleur.Trefle));
		this.jeuDeCarte.add(new CarteRegle("Pique et Trèfle rajoute des points correspondant à leur Hauteur. Un Pique et un Trèfle de même Hauteur rajoute deux points. Les Carreaux enlèvent des points égal à leur Hauteur. Le Joker et les quatres Coeurs ensemblent valent autant de point que la Hauteur des Coeurs, sinon ils enlèvent des points. Le joker sans aucun coeur rapporte quatre points."));
	}

	public ArrayList<Joueur> getPlayers() {
		return Players;
	}

	public void setPlayers(ArrayList<Joueur> players) {
		Players = players;
	}
	
	public void addPlayer(Joueur player) {
		Players.add(player);
	}
	
	//Function

	public void playerCreation() {
		Integer numberOfPlayers = 0;
		Integer computerCount = 1;
		
		System.out.println("Avec combien de joueurs voulez vous jouer?");
		numberOfPlayers = Integer.parseInt(this.askForChoice(new ArrayList<String>(Arrays.asList(new String[] {"3","4"})))); 
			
		for (int pn = 1; pn <= numberOfPlayers; pn++) {
			System.out.println("Le joueur "+pn+" est-il un joueur ou un ordinateur?");
			if (this.askForChoice(new ArrayList<String>(Arrays.asList(new String[] {"Joueur","Ordinateur"}))) == "Joueur") {
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
	
	public String askForChoice(ArrayList<String> choices) {
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
}
