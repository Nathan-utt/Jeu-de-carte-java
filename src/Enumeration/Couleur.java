package Enumeration;

/**
 * @author Guillaume
 *
 */
public enum Couleur {
	Coeur("Coeur"), Carreau("Carreau"), Trefle("Trèfle"), Pique("Pique"), Null("Null");
	
	/**
	 * 
	 */
	private String desc ;  
    
    /**
     * @param desc
     */
    private Couleur(String desc) {  
        this.desc = desc ;  
   }  
     
    public String getDesc() {  
        return this.desc ;  
   } 
	
}
