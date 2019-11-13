package Class;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Scanner;

public class MaitreDuJeu {
	
	
	
	public String askForChoice(String[] choices) {
		Scanner sc = new Scanner(System.in);
		HashSet<Integer> idMatched = null;
		do {
			if (idMatched != null && idMatched.isEmpty()) {
				System.out.println("Aucune similarité avec les choix");
			}
			if (idMatched != null && idMatched.size() > 1) {
				System.out.println("Similarité avec plusieurs choix");
			for (Integer id : idMatched) {
					System.out.println(choices[id]);
				}
			}
			idMatched = new HashSet<Integer>();
			System.out.println("Choisis entre : ");
			for (String value : choices) {
				System.out.print(value+", ");
			}
			System.out.println();
			String entered = sc.nextLine();
			
			for (int i=0; i<choices.length; i++) {
				if (choices[i].toLowerCase().contains(entered.toLowerCase())) {
					idMatched.add(i);
				}
			}
			
		} while (idMatched.size() != 1);
		
		String finalChoice = null;
		for (Integer id : idMatched) {
			System.out.println("Vous avez choisi : " + choices[id]);
			finalChoice = choices[id];
		}
		sc.close();
		return finalChoice;
	}
}
