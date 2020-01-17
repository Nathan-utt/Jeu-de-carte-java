package Model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 
 * Cette classe permet de gérer la sauvegarde et le rechargement des données sauvegardées.
 * 
 * @author Guillaume et Nathan
 *
 */
public class ResourceManager {
	
	/**
	 * @param data
	 * @param fileName
	 * @throws Exception
	 * @return
	 * @throws
	 * @exception
	 */
	public static void save(Serializable data, String fileName) throws Exception {
		try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))){
			oos.writeObject(data);
		}
	}
	
	/**
	 * @param fileName
	 * @return
	 * @throws Exception
	 * @return
	 * @throws
	 * @exception
	 */
	public static Object load(String fileName) throws Exception {
		try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))){
			return ois.readObject();
		}
	}
}
