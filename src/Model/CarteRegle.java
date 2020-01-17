package Model;

public class CarteRegle extends Carte{
	private String texte;

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public CarteRegle(String texte,Boolean extension) {
		super(extension);
		this.setName("Règles");
		this.setImg("/img/card/"+this.getName().replace(" ", "").toLowerCase());
		this.texte = texte;
	}
}
