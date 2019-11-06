package Enumeration;

public enum Trophee {
	plusGrand("Plus Haute"), plusPetit("Plus Basse"), majorite("Majorité"), joker("Joker"), bestJest("Meilleur Jest"), bestJestNoJoker("Meilleur Jest Sans Joker");
    
	private String description;
     
    public String getDescription() {
		return description;
	}

	private Trophee(String description) {
        this.description = description;
    }  
}
