package Class;

public class Joueur {
	private String pseudo;
	private Integer number;
	
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public Joueur(String pseudo, Integer number) {
		super();
		this.pseudo = pseudo;
		this.number = number;
	}
	
	@Override
	public String toString() {
		return this.pseudo+" : "+this.number;
	}
	
}
