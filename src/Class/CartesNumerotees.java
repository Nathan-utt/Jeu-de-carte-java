package Class;

import Enumeration.Couleur;
import Enumeration.Hauteur;
import Enumeration.TropheeEnum;

public class CartesNumerotees extends Carte {
	private TropheeDesc trophee;
	private Hauteur hauteur;
	private Couleur couleur;
	
	public TropheeDesc getTrophee() {
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
    
    public void setTrophee(TropheeDesc trophee) {
    	for (TropheeEnum t : TropheeEnum.values()) {
            if (t.name().equals(trophee.getTropheeDesc().name())) {
                this.trophee = trophee;
            }
        }
    }
	
	public CartesNumerotees(TropheeDesc trophee, Hauteur hauteur, Couleur couleur) {
		super();
		this.setCouleur(couleur);
		this.setHauteur(hauteur);
		this.name = this.getHauteur()+" de "+this.getCouleur();
		this.setTrophee(trophee);
	}
	
	@Override
	public String toString() {
		return name;
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