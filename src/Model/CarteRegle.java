package Model;

/**
 * 
 * Cette classe hérite de la classe carte.
 * Elle permet la création d'une carte particulière qui est la carte règle.
 * Cette classe présente les règles du jeu.
 * 
 * @author Guillaume et Nathan
 *
 */
public class CarteRegle extends Carte{
	/**
	 * Les règles sous forme d'un String
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
		this.setName("Règles");
		this.setImg("/img/card/"+this.getName().replace(" ", "").toLowerCase());
		this.texte = texte;
	}
}
