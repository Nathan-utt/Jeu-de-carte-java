package Enumeration;

/**
 * Les 3 variantes possibles pour le jeu ainsi qu'une description de celle-ci.
 * 
 * @author Guillaume
 *
 */
public enum Variante {
	Normal("Normal"),
	Reversed("Calcul de points inversé"),
	Random("Trophée aléatoire");
	
	/**
	 * Une description du mode de jeu.
	 */
	private String shortDesc;

    public String getShortDesc() {
		return shortDesc;
	}
    
	/**
	 * Lorsque l'énumération est créé affecte la description renseignée dans son champ description.
	 * 
	 * @param shortDesc
	 */
	private Variante(String shortDesc) {
        this.shortDesc = shortDesc;
    }  
}
