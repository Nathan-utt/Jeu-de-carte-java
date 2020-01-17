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
 * @author Guillaume
 *
 */
public class MDJController {
	/**
	 * 
	 */
	private MaitreDuJeu mdj;
	/**
	 * 
	 */
	private JMenuItem launch;
	/**
	 * 
	 */
	private JMenuItem load;
	/**
	 * 
	 */
	private JMenuItem save;
	/**
	 * 
	 */
	private JMenu modeMenu;
	/**
	 * 
	 */
	private JMenuItem standard;
	/**
	 * 
	 */
	private JMenuItem reversed;
	/**
	 * 
	 */
	private JMenuItem random;
	/**
	 * 
	 */
	private JMenu playerMenu;
	/**
	 * 
	 */
	private JCheckBox extension;
	/**
	 * 
	 */
	private ArrayList<JTextField> joueursText;
	/**
	 * 
	 */
	private ArrayList<JCheckBox> joueursType;
	
	/**
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
	 * @return
	 * @throws
	 * @exception
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
	 * 
	 * @return
	 * @throws
	 * @exception
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
	 * 
	 * @return
	 * @throws
	 * @exception
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
