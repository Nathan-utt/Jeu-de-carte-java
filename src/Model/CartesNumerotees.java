package Model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Enumeration.Couleur;
import Enumeration.Hauteur;

/**
 * 
 * Cette classe h�rite de la classe Carte.
 * Elle permet la cr�ation de toutes les cartes du JEST.
 * 
 * @author Guillaume et Nathan
 * 
 *
 */
public class CartesNumerotees extends Carte {
	/**
	 * Le trophee associ� � cette carte
	 */
	private Trophee trophee;
	/**
	 * La hauteur de cette carte
	 */
	private Hauteur hauteur;
	/**
	 * La couleur de cette carte
	 */
	private Couleur couleur;
	
	//Getter/Setter
	
	public Trophee getTrophee() {
		return trophee;
	}
	
	public Hauteur getHauteur() {
		return hauteur;
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
	
    public void setCouleur(Couleur couleur) {
    	for (Couleur c : Couleur.values()) {
            if (c.name().equals(couleur.name())) {
                this.couleur = couleur;
            }
        }
    }
    
    public void setHauteur(Hauteur hauteur) {
    	for (Hauteur h : Hauteur.values()) {
            if (h.name().equals(hauteur.name())) {
                this.hauteur = hauteur;
            }
        }
    }
    
    public void setTrophee(Trophee trophee) {
    	this.trophee = trophee;
    }
    
    //Constructor
	
	/**
	 * @param trophee
	 * @param hauteur
	 * @param couleur
	 * @param extension
	 */
	public CartesNumerotees(Trophee trophee, Hauteur hauteur, Couleur couleur,Boolean extension) {
		super(extension);
		this.setCouleur(couleur);
		this.setHauteur(hauteur);
		if (this.getHauteur().equals(Hauteur.Joker)) {
			this.setName(this.getHauteur().toString());
		} else {
			this.setName(this.getHauteur()+" de "+this.getCouleur());
		}
		this.setTrophee(trophee);
		this.setImg("/img/card/"+this.getName().replace(" ", "").toLowerCase());
	}
	
	
	/**
	 * Permet de savoir si cette carte est plus grande que la carte pass�e en param�tre.
	 * @param carte la carte � compar�
	 * @return Un bool�en signifiant si la carte est plus grande ou non
	 */
	public boolean isGreaterThan(CartesNumerotees carte) {
		if (this.hauteur.ordinal() > carte.getHauteur().ordinal()) {
			return true;
		} else if (this.hauteur.ordinal() == carte.getHauteur().ordinal() && this.couleur.ordinal() > carte.getCouleur().ordinal()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Retourne la carte sous forme d'un String
	 */
	@Override
	public String toString() {
		if (this.isVisible()) {
			return this.getName();
		} else {
			return "Carte cach�e";
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartesNumerotees other = (CartesNumerotees) obj;
		if (couleur != other.couleur)
			return false;
		if (hauteur != other.hauteur)
			return false;
		if (trophee == null) {
			if (other.trophee != null)
				return false;
		} else if (!trophee.equals(other.trophee))
			return false;
		return true;
	}
	
	/**
	 * Cette m�thode renvois une image construite � partir de ces liens.
	 * 
	 * @return Une image en buffer cr�er � partir de l'image du troph�e et de la carte.
	 * @exception FileNotFound fichier non trouv�
	 */
	public BufferedImage getWholeImage() {
		if (this.isVisible()) {
			BufferedImage imageCard = null;
			try {
				imageCard = ImageIO.read(getClass().getResource(this.getImg()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedImage imageTrophee = null;
			try {
				imageTrophee = ImageIO.read(getClass().getResource(this.getTrophee().getImg()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int w = Math.max(imageCard.getWidth(), imageTrophee.getWidth());
			int h = Math.max(imageCard.getHeight(), imageTrophee.getHeight());
			BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			
			Graphics g = combined.getGraphics();
			g.drawImage(imageCard, 0, 0, null);
			g.drawImage(imageTrophee, 0, 0, null);
			
			return combined;
		} else {
			try {
				return ImageIO.read(getClass().getResource(this.getImg()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		return null;
	}
	
}
