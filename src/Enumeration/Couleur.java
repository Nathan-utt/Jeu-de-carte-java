package Enumeration;

public enum Couleur {
	Coeur("Coeur"), Carreau("Carreau"), Trefle("Trèfle"), Pique("Pique"), Null("Null");
	
	private String desc ;  
    
    private Couleur(String desc) {  
        this.desc = desc ;  
   }  
     
    public String getDesc() {  
        return this.desc ;  
   } 
	
}
