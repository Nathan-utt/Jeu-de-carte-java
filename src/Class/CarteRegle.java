package Class;

public class CarteRegle extends Carte{
	private String texte;

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public CarteRegle(String texte) {
		super();
		this.setName("r�gles");
		this.texte = texte;
	}
}
