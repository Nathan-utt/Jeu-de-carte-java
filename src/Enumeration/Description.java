package Enumeration;

public enum Description {
	plusGrand("Joeueur avec la plus haute valeur de cette couleur"), plusPetit("Joueur avec la plus petite valeur de cette couleur"), majorite("Joueur avec le plus de carte de cette valeur"), joker("Joueur possédant le Joker"), bestJest("Joueur avec le meilleur Jest avec le Joker"), bestJestNoJoker("Joueur avec le meilleur Jest sans le Joker");
    
	private String description;
     
    public String getDescription() {
		return description;
	}

	private Description(String description) {
        this.description = description;
    }    
}
