package Enumeration;

public enum TropheeEnum {
	plusGrand("Plus Haute","Joeueur avec la plus haute valeur de cette couleur"),
	plusPetit("Plus Basse","Joueur avec la plus petite valeur de cette couleur"),
	majorite("Majorité","Joueur avec la plus petite valeur de cette couleur"),
	joker("Joker","Joueur possédant le Joker"),
	bestJest("Meilleur Jest","Joueur avec le meilleur Jest avec le Joker"),
	bestJestNoJoker("Meilleur Jest Sans Joker","Joueur avec le meilleur Jest sans le Joker");
    
	private String shortDesc;
	private String longDesc;
     
    public String getLongDesc() {
		return longDesc;
	}

    public String getShortDesc() {
		return shortDesc;
	}
    
	private TropheeEnum(String shortDesc,String longDesc) {
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
    }  
}
