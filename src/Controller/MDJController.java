package Controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import Enumeration.Status;
import Enumeration.Variante;
import Model.Joueur;
import Model.JoueurVirtuel;
import Model.MaitreDuJeu;
import Model.ResourceManager;
import Model.SaveData;

/**
 * Le MDJController sert � faire le lien entre la classe MaitreDuJeu et la vue graphique.
 * Permettant alors � la vue graphique d'interagir avec les donn�es du jeu.
 * 
 * Le controlleur poss�de des r�f�rences sur les attributs de la vue graphique. Il peut alors ajouter
 * des gestionnaire d'�v�nement sur ces objets.
 * 
 *  @author Guillaume, Nathan
 */
public class MDJController {
	/**
	 * Une r�f�rence sur l'objet MaitreDuJeu qui g�re le jeu
	 */
	private MaitreDuJeu mdj;
	/**
	 * Une r�f�rence sur le bouton lancer dans le menu du jeu (interface graphique)
	 */
	private JMenuItem launch;
	/**
	 * Une r�f�rence sur le bouton charger dans le menu du jeu (interface graphique)
	 */
	private JMenuItem load;
	/**
	 * Une r�f�rence sur le bouton sauvegarder dans le menu du jeu (interface graphique)
	 */
	private JMenuItem save;
	/**
	 * Une r�f�rence sur le bouton de choix de mode dans le menu du jeu (interface graphique)
	 */
	private JMenu modeMenu;
	/**
	 * Une r�f�rence sur le bouton mode standard dans le menu du jeu (interface graphique)
	 */
	private JMenuItem standard;
	/**
	 * Une r�f�rence sur le bouton mode standard dans le menu du jeu (interface graphique)
	 */
	private JMenuItem reversed;
	/**
	 * Une r�f�rence sur le bouton mode random dans le menu du jeu (interface graphique)
	 */
	private JMenuItem random;
	/**
	 * Une r�f�rence sur le bouton de choix du nombre de joueur dans le menu du jeu (interface graphique)
	 */
	private JMenu playerMenu;
	/**
	 * Une r�f�rence sur la checkbox d'extension dans le menu du jeu (interface graphique)
	 */
	private JCheckBox extension;
	/**
	 * Une liste des r�f�rence des inputbox des joueurs dans le menu du jeu (interface graphique)
	 */
	private ArrayList<JTextField> joueursText;
	/**
	 * Une liste des r�f�rence des checkbox des joueurs (ordinateur ou non) dans le menu du jeu (interface graphique)
	 */
	private ArrayList<JCheckBox> joueursType;
	
	/**
	 * Constructeur du controlleur affectant les r�f�rences � leur attribut respectif.
	 * Ce constructeur rajoute aussi les d�clencheurs sur les objets qui en on besoin.
	 * 
	 * @param mdj
	 * @param launch
	 * @param load
	 * @param save
	 * @param modeMenu
	 * @param standard
	 * @param reversed
	 * @param random
	 * @param playerMenu
	 * @param extension
	 * @param joueursText
	 * @param joueursType
	 */
	public MDJController(MaitreDuJeu mdj, JMenuItem launch, JMenuItem load, JMenuItem save, JMenu modeMenu,
			JMenuItem standard, JMenuItem reversed, JMenuItem random, JMenu playerMenu,
			JCheckBox extension, ArrayList<JTextField> joueursText, ArrayList<JCheckBox> joueursType) {
		this.mdj = mdj;
		this.launch = launch;
		launch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				launchGame();
			}
		});
		this.load = load;
		load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadGame();
			}
		});
		this.save = save;
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveGame();
			}
		});
		this.modeMenu = modeMenu;
		this.standard = standard;
		this.reversed = reversed;
		this.random = random;
		this.playerMenu = playerMenu;
		this.extension = extension;
		this.joueursText = joueursText;
		this.joueursType = joueursType;
	}
	
	/**
	 * 
	 * M�thode permettant la sauvegarde des donn�es actuelles du jeu afin de reprendre plus tard.
	 * 
	 * @return void
	 * @exception FileNotFound
	 */
	public void saveGame() {
		try {
			SaveData savedata = new SaveData(mdj.getPlayers(), mdj.getRemainingPlayers(), mdj.getAlreadyChoosePlayers(), mdj.getRemainingDeck(), mdj.getDistributionDeck(), mdj.getTrophees(), mdj.getCurrentPlayer(), mdj.getUsedVariante(), mdj.getPlayingWithExtension(), mdj.getSv(), mdj.getStatus());
			ResourceManager.save(savedata,"save.txt");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * M�thode chargeant le fichier de sauvegarde si il existe afin de reprendre une partie.
	 * 
	 * @return void
	 * @exception FileNotFound
	 */
	public void loadGame() {
		try {
			SaveData savedata = (SaveData) ResourceManager.load("save.txt");
			mdj.reload(savedata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Si les champs sont bien renseign� demande au MDJ de lancer la partie.
	 * 
	 * @return void
	 */
	public void launchGame() {
		if (!modeMenu.getText().equals("Mode") && !playerMenu.getText().equals("Joueur")) {
			Boolean abort = false;
			Iterator<JTextField> iteJtext = joueursText.iterator();
			while (iteJtext.hasNext()) {
				JTextField jTextField = (JTextField) iteJtext.next();
				if (jTextField.getText().equals("")) {
					abort = true;
					jTextField.setBackground(Color.red);
				}
				if (!jTextField.getText().equals("ordi")) {
				Iterator<JTextField> iteJtext2 = joueursText.iterator();
					while (iteJtext2.hasNext()) {
						JTextField jTextField2 = (JTextField) iteJtext2.next();
						if (jTextField != jTextField2 && jTextField.getText().equals(jTextField2.getText())) {
							abort = true;
							jTextField2.setBackground(Color.red);
						}
					}
				}
			}
			if (!abort) {
				int computerCount = 0;
				mdj.getPlayers().clear();
				for (int i = 0; i < joueursType.size(); i++) {
					if (joueursType.get(i).isSelected()) {
						mdj.addPlayer(new JoueurVirtuel("Ordinateur"+computerCount,i));
						computerCount++;
					} else {
						mdj.addPlayer(new Joueur(joueursText.get(i).getText(),i));
					}
				}
				switch (modeMenu.getText()) {
				case "Normal":
					mdj.setUsedVariante(Variante.Normal);
					break;
				case "Reversed":
					mdj.setUsedVariante(Variante.Reversed);
					break;
				case "Random":
					mdj.setUsedVariante(Variante.Random);
					break;
				default:
					break;
				}
				mdj.setPlayingWithExtension(extension.isSelected());
				mdj.setStatus(Status.start);
			}
		}
		
	}
	
}
