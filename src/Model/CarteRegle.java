package Model;

/**
 * 
 * Cette classe h�rite de la classe carte.
 * Elle permet la cr�ation d'une carte particuli�re qui est la carte r�gle.
 * Cette classe pr�sente les r�gles du jeu.
 * 
 * @author Guillaume et Nathan
 *
 */
public class CarteRegle extends Carte{
	/**
	 * Les r�gles sous forme d'un String
	 */
	private String texte;

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	/**
	 * @param texte
	 * @param extension
	 */
	public CarteRegle(String texte,Boolean extension) {
		super(extension);
		this.setName("R�gles");
		this.setImg("/img/card/"+this.getName().replace(" ", "").toLowerCase());
		this.texte = texte;
	}
}
