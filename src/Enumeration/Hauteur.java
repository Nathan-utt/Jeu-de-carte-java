package Enumeration;

public enum Hauteur {
	Joker("Joker"), As("As"), Deux("Deux"), Trois("Trois"), Quatre("Quatre"), Cinq("Cinq"), Six("Six"), Sept("Sept");
	
private String desc ;  
    
    private Hauteur(String desc) {  
        this.desc = desc ;  
   }  
     
    public String getDesc() {  
        return this.desc ;  
   } 
}
