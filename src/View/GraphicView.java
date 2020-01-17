package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JSplitPane;

import Controller.MDJController;
import Enumeration.Status;
import Enumeration.Variante;
import Model.Carte;
import Model.CartesNumerotees;
import Model.Joueur;
import Model.JoueurVirtuel;
import Model.MaitreDuJeu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * La vue Graphique, elle permet de suivre l'évolution du jeu dans une fenêtre graphique.
 * C'est un Thread à part afin de ne pas bloquer les autres vues.
 * Cette vue Observe le MDJ afin de changer lorsque le MDJ est modifier.
 * 
 * @author Guillaume, Nathan
 *
 */
public class GraphicView implements Observer {
	
	/**
	 * Les coordonnées par défaut de la première carte du Jest
	 */
	public static Integer[] FIRSTJESTPOSITION = {10,50};
	/**
	 * Les coordonnées par défaut de la première carte de la Main
	 */
	public Integer[] FIRSTHANDPOSITION = {0,0};
	/**
	 * Les coordonnées par défaut de la première carte Trophée
	 */
	public Integer[] TROPHEEPOSITION = {0,50};
	/**
	 * Les coordonnées par défaut des différentes carte des offres
	 */
	public Integer[][] PLAYEROFFER = {{10,400},{400,400},{800,400}};

	/**
	 * La fenêtre principale
	 */
	private JFrame frame;
	/**
	 * Le Menu item de lancement du jeu
	 */
	private JMenuItem launch;
	/**
	 * Le Menu item de chargement du jeu
	 */
	private JMenuItem load;
	/**
	 * Le Menu item de sauvegarde du jeu
	 */
	private JMenuItem save;
	/**
	 * Le Menu de mode du jeu
	 */
	private JMenu modeMenu;
	/**
	 * Le Menu item de mode standard du jeu
	 */
	private JMenuItem standard;
	/**
	 * Le Menu item de mode reversed du jeu
	 */
	private JMenuItem reversed;
	/**
	 * Le Menu item de mode random du jeu
	 */
	private JMenuItem random;
	/**
	 * Le Menu du nombre de joueur du jeu
	 */
	private JMenu playerMenu;
	/**
	 * Le Menu item de 3 joueurs du jeu
	 */
	private JMenuItem p3;
	/**
	 * Le Menu item de 4 joueurs du jeu
	 */
	private JMenuItem p4;
	/**
	 * La checkbox d'extension du jeu
	 */
	private JCheckBox extension;
	/**
	 * La barre de menu
	 */
	private JMenuBar menuBar;
	/**
	 * Le Panel principal du jeu
	 */
	private JPanel mainGame;
	
	/**
	 * Une liste des texte input pour les noms des joueurs
	 */
	private ArrayList<JTextField> joueursText;
	/**
	 * Une liste de checkbox pour les types des joueurs
	 */
	private ArrayList<JCheckBox> joueursType;
	/**
	 * Une map reliant Une carte à son image dans la fenêtre.
	 * Correspondant à la main courante.
	 */
	private HashMap<JLabel,Carte> currentHand;
	/**
	 * Une map reliant Une carte à son image dans la fenêtre.
	 * Correspondant au trophée courant. 
	 */
	private HashMap<JLabel,Carte> currentTrophees;
	/**
	 * Une map reliant Une carte à son image dans la fenêtre.
	 * Correspondant au Jest courant. 
	 */
	private HashMap<JLabel,Carte> currentJest;
	/**
	 * Une map reliant Un joueur à son image dans la fenêtre.
	 * Correspondant aux offres courantes. 
	 */
	private HashMap<JLabel,Joueur> currentOffers;

	private JLabel label_1;

	/**
	 * Une méthode pour ajouter un certain nombre de case pour tapper les noms et types des joueurs.
	 * 
	 * @param number le nombre de joueur à ajouter
	 * @return void
	 */
	public void setPlayers(int number) {
		Iterator<JTextField> iteText = this.joueursText.iterator();
		Iterator<JCheckBox> iteType = this.joueursType.iterator();
		
		while (iteText.hasNext()) {
			JTextField jTextField = (JTextField) iteText.next();
			this.menuBar.remove(jTextField);
		}
		
		while (iteType.hasNext()) {
			JCheckBox jCheckBox = (JCheckBox) iteType.next();
			this.menuBar.remove(jCheckBox);
		}
		
		this.joueursText.clear();
		this.joueursType.clear();
		
		for (int i = 0; i < number; i++) {
			JTextField textField = new JTextField();
			textField.setColumns(1);
			menuBar.add(textField);
			
			JCheckBox box = new JCheckBox("Ordinateur");
			box.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					for (int j = 0; j < joueursType.size(); j++) {			
						if (joueursType.get(j).isSelected()) {
							joueursText.get(j).setText("ordi");
							joueursText.get(j).setEnabled(false);
						} else {
							if (joueursText.get(j).getText().equals("ordi")) joueursText.get(j).setText("");
							joueursText.get(j).setEnabled(true);
						}
					}
				}
			});
			menuBar.add(box);
			
			this.joueursText.add(textField);
			this.joueursType.add(box);
		}
	};
	
	/**
	 * Launch the application.
	 * 
	 * @deprecated just for testing
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MaitreDuJeu mdj = new MaitreDuJeu();
					//mdj.startGame();
					GraphicView window = new GraphicView(mdj);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Création de la vue graphique on passe le moteur de jeu en paramètre afin de créer le controlleur.
	 * 
	 * @param mdj moteur de jeu
	 */
	public GraphicView(MaitreDuJeu mdj) {
		initialize();
		this.joueursText = new ArrayList<JTextField>();
		this.joueursType = new ArrayList<JCheckBox>();
		this.currentHand = new HashMap<JLabel,Carte>();
		this.currentJest = new HashMap<JLabel,Carte>();
		this.currentTrophees = new HashMap<JLabel,Carte>();
		this.currentOffers = new HashMap<JLabel,Joueur>();
		mdj.addObserver(this);
		MDJController mdjController = new MDJController(mdj, launch, load, save, modeMenu, standard, reversed, random, playerMenu, extension, joueursText, joueursType);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		Dimension ss = Toolkit.getDefaultToolkit ().getScreenSize ();
		frame = new JFrame();
		frame.setBounds(0, 0, ss.width, ss.height);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		this.FIRSTHANDPOSITION[0] = frame.getWidth()/2-180;
		this.FIRSTHANDPOSITION[1] = frame.getHeight()-260;
		this.TROPHEEPOSITION[0] = frame.getWidth()-180;
		
		mainGame = new JPanel();
		frame.getContentPane().add(mainGame, BorderLayout.CENTER);
		mainGame.setLayout(null);
			
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);
		
		launch = new JMenuItem("Lancer");
		mnGame.add(launch);
		
		load = new JMenuItem("Charger");
		mnGame.add(load);
		
		save = new JMenuItem("Sauvegarder");
		mnGame.add(save);
		
		playerMenu = new JMenu("Joueur");
		menuBar.add(playerMenu);
		
		p3 = new JMenuItem("3 Joueur");
		p3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!playerMenu.getText().equals(p3.getText())) {
					playerMenu.setText(p3.getText());
					setPlayers(3);
				}
			}
		});
		playerMenu.add(p3);
		
		p4 = new JMenuItem("4 Joueur");
		p4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!playerMenu.getText().equals(p4.getText())) {
					playerMenu.setText(p4.getText());
					setPlayers(4);
				}
			}
		});
		playerMenu.add(p4);
		
		modeMenu = new JMenu("Mode");
		menuBar.add(modeMenu);
		
		standard = new JMenuItem("Normal");
		standard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modeMenu.setText(standard.getText());
			}
		});
		modeMenu.add(standard);
		
		reversed = new JMenuItem("Reversed");
		reversed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modeMenu.setText(reversed.getText());
			}
		});
		modeMenu.add(reversed);
		
		random = new JMenuItem("Random");
		random.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				modeMenu.setText(random.getText());
			}
		});
		modeMenu.add(random);
		
		extension = new JCheckBox("Extension");
		menuBar.add(extension);
	}
	
	/**
	 * Retourne la vue graphique à l'état par défaut
	 * 
	 * @return void
	 */
	public void clearAll() {
		mainGame.removeAll();
		mainGame.repaint();
		currentHand.clear();
		currentJest.clear();
		currentOffers.clear();
	}
	
	/**
	 * Mets les éléments par défaut sur la vue graphique.
	 * 
	 * @return void
	 */
	public void baseSetup() {
		JLabel lblNewLabel = new JLabel("Ton Jest");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 10, 90, 40);
		mainGame.add(lblNewLabel);
		
		lblNewLabel = new JLabel("TrophÃ©es");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(TROPHEEPOSITION[0], 10, 90, 40);
		mainGame.add(lblNewLabel);
	}

	/**
	 * Méthode s'éxécutant lorsque le moteur de jeu est update. On regarde alors le statut du mdj.
	 * Selon ce statut on va mettre à jour la vue.
	 */
	@Override
	public void update(Observable inst, Object arg) {
		if (inst instanceof MaitreDuJeu) {
			MaitreDuJeu instanceMdj = (MaitreDuJeu) inst;
			if (arg instanceof Status) {
				switch ((Status)arg) {
				case modeChoice:
					int numberPlayer = instanceMdj.getPlayers().size();
					setPlayers(numberPlayer);
					for (int i = 0; i < instanceMdj.getPlayers().size(); i++) {
						if (instanceMdj.getPlayers().get(i) instanceof JoueurVirtuel) {
							joueursType.get(i).setSelected(true);
						} else {
							joueursText.get(i).setText(instanceMdj.getPlayers().get(i).getPseudo());
						}
					}
					if (numberPlayer == 3) {
						playerMenu.setText("3 Joueur");
					} else {
						playerMenu.setText("4 Joueur");
					}
					break;	
				case extensionChoice:
					modeMenu.setText(instanceMdj.getUsedVariante().toString());
					break;
				case start:
					extension.setSelected(instanceMdj.getPlayingWithExtension());
					break;
				case makingOffer:
					this.clearAll();
					this.baseSetup();
					
					JLabel lblPlayer = new JLabel("Choisis une carte "+instanceMdj.getCurrentPlayer().getPseudo()+" : ");
					lblPlayer.setFont(new Font("Tahoma", Font.PLAIN, 20));
					lblPlayer.setBounds(FIRSTHANDPOSITION[0]+180-150, FIRSTHANDPOSITION[1]-40, 300, 40);
					mainGame.add(lblPlayer);
					
					Joueur currPlayer = instanceMdj.getCurrentPlayer();
					if (currPlayer != null) {
						Iterator<Carte> iteHand = instanceMdj.getCurrentPlayer().getHand().getDeck().iterator();
						
						Integer number = 0;
						while (iteHand.hasNext()) {
							Carte carte = (Carte) iteHand.next();
							CartesNumerotees cartenum = (CartesNumerotees) carte;
							JLabel lbl = new JLabel("");
							lbl.setIcon(new ImageIcon(cartenum.getWholeImage()));
							lbl.setBounds(FIRSTHANDPOSITION[0]+number*190, FIRSTHANDPOSITION[1], 170, 240);
							lbl.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent e) {
									JLabel lbl = (JLabel) e.getSource();
									Carte hiddenCard = instanceMdj.getCurrentPlayer().getHand().getCard(instanceMdj.getCurrentPlayer().getHand().getDeck().indexOf(currentHand.get(lbl)));
									instanceMdj.getCurrentPlayer().makeOffer(hiddenCard);
									instanceMdj.setStatus(Status.choosingForOffer);
								}
								@Override
								public void mouseEntered(MouseEvent e) {
									JLabel lbl = (JLabel) e.getSource();
									lbl.setIcon(new ImageIcon(getClass().getResource("/img/card/back.png")));
								}
								@Override
								public void mouseExited(MouseEvent e) {
									JLabel lbl = (JLabel) e.getSource();
									CartesNumerotees card = (CartesNumerotees) currentHand.get(lbl);
									lbl.setIcon(new ImageIcon(card.getWholeImage()));
								}
							});
							currentHand.put(lbl,carte);
							mainGame.add(lbl);
							number++;
						}
						
						Iterator<Carte> iteJest = instanceMdj.getCurrentPlayer().getJest().getDeck().iterator();
						number = 0;
						while (iteJest.hasNext()) {
							Carte carte = (Carte) iteJest.next();
							CartesNumerotees cartenum = (CartesNumerotees) carte;
							JLabel lbl = new JLabel("");
							lbl.setIcon(new ImageIcon(cartenum.getWholeImage().getScaledInstance(100, 140, java.awt.Image.SCALE_SMOOTH)));
							lbl.setBounds(FIRSTJESTPOSITION[0]+number*120, FIRSTJESTPOSITION[1], 100, 140);
							currentJest.put(lbl,carte);
							mainGame.add(lbl);
							number++;
						}
						
						Iterator<Carte> iteTrophee = instanceMdj.getTrophees().getDeck().iterator();
						number = 0;
						while (iteTrophee.hasNext()) {
							Carte carte = (Carte) iteTrophee.next();
							CartesNumerotees cartenum = (CartesNumerotees) carte;
							JLabel lbl = new JLabel("");
							lbl.setIcon(new ImageIcon(cartenum.getWholeImage().getScaledInstance(100, 140, java.awt.Image.SCALE_SMOOTH)));
							lbl.setBounds(TROPHEEPOSITION[0]-number*120, TROPHEEPOSITION[1], 100, 140);
							lbl.setToolTipText(cartenum.getTrophee().getDescription());
							currentJest.put(lbl,carte);
							mainGame.add(lbl);
							number++;
						}
					}
					mainGame.repaint();
					break;
				case takingOffer:
					currPlayer = instanceMdj.getCurrentPlayer();
					if (currPlayer != null) {
						this.clearAll();
						this.baseSetup();
						
						Iterator<Carte> iteJest = instanceMdj.getCurrentPlayer().getJest().getDeck().iterator();
						
						lblPlayer = new JLabel("Choisis une carte "+instanceMdj.getCurrentPlayer().getPseudo()+" : ");
						lblPlayer.setFont(new Font("Tahoma", Font.PLAIN, 20));
						lblPlayer.setBounds(FIRSTHANDPOSITION[0]+180-150, FIRSTHANDPOSITION[1]+200, 300, 40);
						mainGame.add(lblPlayer);
						
						Iterator<Joueur> itePlayers = instanceMdj.getRemainingPlayers().iterator();
						int numberp = 0;
						while (itePlayers.hasNext()) {
							Joueur joueur = (Joueur) itePlayers.next();
							if (joueur != instanceMdj.getCurrentPlayer() || instanceMdj.getRemainingPlayers().size() == 1) {
								Iterator<Carte> iteDeck = joueur.getOffer().getDeck().iterator();
								
								lblPlayer = new JLabel(joueur.getPseudo()+" : ");
								lblPlayer.setFont(new Font("Tahoma", Font.PLAIN, 20));
								lblPlayer.setBounds(PLAYEROFFER[numberp][0], PLAYEROFFER[numberp][1]-40, 200, 40);
								mainGame.add(lblPlayer);
								
								int numberc = 0;
								while (iteDeck.hasNext()) {
									Carte carte = (Carte) iteDeck.next();
									
									CartesNumerotees cartenum = (CartesNumerotees) carte;
									
									JLabel lbl = new JLabel("");
									lbl.setIcon(new ImageIcon(cartenum.getWholeImage()));
									lbl.setBounds(PLAYEROFFER[numberp][0]+numberc*190, PLAYEROFFER[numberp][1], 170, 240);
									lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
									lbl.addMouseListener(new MouseAdapter() {
										@Override
										public void mouseClicked(MouseEvent e) {
											JLabel lbl = (JLabel) e.getSource();
											Joueur chosenFrom = currentOffers.get(lbl);
											instanceMdj.getRemainingPlayers().remove(chosenFrom);
											Carte chosen = (chosenFrom.getOffer().getDeck().get(1).getName() == currentHand.get(lbl).getName()) ? chosenFrom.getOffer().getCard(1) : chosenFrom.getOffer().getCard(0);
											instanceMdj.getCurrentPlayer().takeOffer(chosen);
											instanceMdj.setCurrentPlayer(chosenFrom);
											instanceMdj.setStatus(Status.choosingForTaking);
										}
									});
									currentHand.put(lbl,carte);
									currentOffers.put(lbl,joueur);
									mainGame.add(lbl);
									numberc++;
								}
								numberp++;
							}
						}
						
						int number = 0;
						while (iteJest.hasNext()) {
							Carte carte = (Carte) iteJest.next();
							CartesNumerotees cartenum = (CartesNumerotees) carte;
							JLabel lbl = new JLabel("");
							lbl.setIcon(new ImageIcon(cartenum.getWholeImage().getScaledInstance(100, 140, java.awt.Image.SCALE_SMOOTH)));
							lbl.setBounds(FIRSTJESTPOSITION[0]+number*120, FIRSTJESTPOSITION[1], 100, 140);
							currentJest.put(lbl,carte);
							mainGame.add(lbl);
							number++;
						}
						
						Iterator<Carte> iteTrophee = instanceMdj.getTrophees().getDeck().iterator();
						number = 0;
						while (iteTrophee.hasNext()) {
							Carte carte = (Carte) iteTrophee.next();
							CartesNumerotees cartenum = (CartesNumerotees) carte;
							JLabel lbl = new JLabel("");
							lbl.setIcon(new ImageIcon(cartenum.getWholeImage().getScaledInstance(100, 140, java.awt.Image.SCALE_SMOOTH)));
							lbl.setBounds(TROPHEEPOSITION[0]-number*120, TROPHEEPOSITION[1], 100, 140);
							lbl.setToolTipText(cartenum.getTrophee().getDescription());
							currentJest.put(lbl,carte);
							mainGame.add(lbl);
							number++;
						}
					}
					mainGame.repaint();
					break;
				case determineWinner:
					this.clearAll();
					Joueur winner = instanceMdj.getWinner();
					
					Iterator<Joueur> itePlayers = instanceMdj.getRemainingPlayers().iterator();
					int numberp = 0;
					while (itePlayers.hasNext()) {
						Joueur joueur = (Joueur) itePlayers.next();
						Iterator<Carte> iteJest = joueur.getJest().getDeck().iterator();
						int number = 0;
						
						lblPlayer = new JLabel(joueur.getPseudo()+" (Score : "+instanceMdj.getSv().getScore(joueur, true)+") : ");
						lblPlayer.setFont(new Font("Tahoma", Font.PLAIN, 20));
						lblPlayer.setBounds(10, 5+numberp*150, 400, 40);
						mainGame.add(lblPlayer);
						
						
						while (iteJest.hasNext()) {
							Carte carte = (Carte) iteJest.next();
							CartesNumerotees cartenum = (CartesNumerotees) carte;
							JLabel lbl = new JLabel("");
							lbl.setIcon(new ImageIcon(cartenum.getWholeImage().getScaledInstance(100, 140, java.awt.Image.SCALE_SMOOTH)));
							lbl.setBounds(FIRSTJESTPOSITION[0]+number*120, 10+numberp*150, 100, 140);
							if (joueur == winner) {
								lbl.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5));
							}
							currentJest.put(lbl,carte);
							mainGame.add(lbl);
							number++;
						}
						numberp++;
					}
					mainGame.repaint();
					break;
				default:
					break;
				}
			}
		}
	}
}
