package Model;

import Enumeration.Couleur;
import Enumeration.Hauteur;

public class CartesNumerotees extends Carte {
	private Trophee trophee;
	private Hauteur hauteur;
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
	}
	
	
	public boolean isGreaterThan(CartesNumerotees carte) {
		if (this.hauteur.ordinal() > carte.getHauteur().ordinal()) {
			return true;
		} else if (this.hauteur.ordinal() == carte.getHauteur().ordinal() && this.couleur.ordinal() > carte.getCouleur().ordinal()) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		if (this.isVisible()) {
			return this.getName();
		} else {
			return "Carte cachée";
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
	
	
	
}
