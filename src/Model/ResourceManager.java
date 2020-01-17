package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 
 * Cette classe permet de g�rer la sauvegarde et le rechargement des donn�es sauvegard�es.
 * 
 * @author Guillaume et Nathan
 *
 */
public class ResourceManager {
	
	/**
	 * Permet de sauvergarder l'application
	 * 
	 * @param data l'objet � sauvegarder
	 * @param fileName le lien vers le fichier de sauvergarde
	 * @throws Exception
	 */
	public static void save(Serializable data, String fileName) throws Exception {
		try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))){
			oos.writeObject(data);
		}
	}
	
	/**
	 * Permet de charger l'application
	 * return un Objet correspondant � l'objet sauvergarder
	 * @param fileName le lien vers le fichier de sauvergarde
	 * @throws Exception
	 */
	public static Object load(String fileName) throws Exception {
		try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))){
			return ois.readObject();
		}
	}
}
