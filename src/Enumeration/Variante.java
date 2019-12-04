package Enumeration;

public enum Variante {
	Normal("Normal"),
	Reversed("Calcul de points inversé"),
	Random("Trophée aléatoire");
	
	private String shortDesc;

    public String getShortDesc() {
		return shortDesc;
	}
    
	private Variante(String shortDesc) {
        this.shortDesc = shortDesc;
    }  
}
