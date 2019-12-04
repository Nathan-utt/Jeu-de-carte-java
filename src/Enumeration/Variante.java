package Enumeration;

public enum Variante {
	Normal("Normal"),
	Reversed("Calcul de points invers�"),
	Random("Troph�e al�atoire");
	
	private String shortDesc;

    public String getShortDesc() {
		return shortDesc;
	}
    
	private Variante(String shortDesc) {
        this.shortDesc = shortDesc;
    }  
}
