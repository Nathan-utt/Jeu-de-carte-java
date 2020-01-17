package Enumeration;

/**
 * Une énumération qui contient les différentes valeur de couleur possible pour les cartes.
 * Elle contient une description comprenant des accents.
 * 
 * @author Guillaume, Nathan
 *
 */
public enum Couleur {
	Coeur("Coeur"), Carreau("Carreau"), Trefle("TrÃ¨fle"), Pique("Pique"), Null("Null");
	
	/**
	 * Le nom de la couleur mais avec des accents.
	 */
	private String desc ;  
    
    /**
     * Lorsque l'énumération est créé affecte la description renseignée dans son champ description.
     * 
     * @param desc
     */
    private Couleur(String desc) {  
        this.desc = desc ;  
   }  
     
    public String getDesc() {  
        return this.desc ;  
   } 
	
}
