package Enumeration;

/**
 * Une �num�ration qui contient les diff�rentes valeur de couleur possible pour les cartes.
 * 
 * @author Guillaume
 *
 */
public enum Hauteur {
	Joker("Joker"), As("As"), Deux("Deux"), Trois("Trois"), Quatre("Quatre"), Cinq("Cinq"), Six("Six"), Sept("Sept");
	
/**
 * Une description de la Hauteur
 */
private String desc ;  
    
	/**
	 * Lorsque l'�num�ration est cr�� affecte la description renseign�e dans son champ description.
	 * 
	 * @param desc
	 */
    private Hauteur(String desc) {  
        this.desc = desc ;  
   }  
     
    public String getDesc() {  
        return this.desc ;  
   } 
}
