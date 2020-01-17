package Enumeration;

/**
 * Une �num�ration qui contient les diff�rentes valeur de couleur possible pour les cartes.
 * Elle contient une description comprenant des accents.
 * 
 * @author Guillaume, Nathan
 *
 */
public enum Couleur {
	Coeur("Coeur"), Carreau("Carreau"), Trefle("Trèfle"), Pique("Pique"), Null("Null");
	
	/**
	 * Le nom de la couleur mais avec des accents.
	 */
	private String desc ;  
    
    /**
     * Lorsque l'�num�ration est cr�� affecte la description renseign�e dans son champ description.
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
