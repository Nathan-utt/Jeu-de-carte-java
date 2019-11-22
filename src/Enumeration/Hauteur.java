package Enumeration;

public enum Hauteur {
	Joker("Joker"), As("As"), Deux("Deux"), Trois("Trois"), Quatre("Quatre");
	
private String desc ;  
    
    private Hauteur(String desc) {  
        this.desc = desc ;  
   }  
     
    public String getDesc() {  
        return this.desc ;  
   } 
}
