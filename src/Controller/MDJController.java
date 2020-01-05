package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import Enumeration.Status;
import Enumeration.Variante;
import Model.Joueur;
import Model.JoueurVirtuel;
import Model.MaitreDuJeu;

public class MDJController {
	private MaitreDuJeu mdj;
	private JMenuItem launch;
	private JMenuItem load;
	private JMenuItem save;
	private JMenu modeMenu;
	private JMenuItem standard;
	private JMenuItem reversed;
	private JMenuItem random;
	private JMenu playerMenu;
	private JCheckBox extension;
	private ArrayList<JTextField> joueursText;
	private ArrayList<JCheckBox> joueursType;
	
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
		this.save = save;
		this.modeMenu = modeMenu;
		this.standard = standard;
		this.reversed = reversed;
		this.random = random;
		this.playerMenu = playerMenu;
		this.extension = extension;
		this.joueursText = joueursText;
		this.joueursType = joueursType;
	}

	public void launchGame() {
		if (!modeMenu.getText().equals("Mode") && !playerMenu.getText().equals("Joueur")) {
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
