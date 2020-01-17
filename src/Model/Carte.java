package Model;

import java.io.Serializable;

/**
 * 
 * 
 * Cette classe Carte va permettre de créer le model des carte utilisé dans le jeu JEST.
 * Elle implémente Serializable ce qui va permettre aux carte d'Ãªtre sauvegardées.
 * 
 * @serial Serializable
 * 
 * @author Guillaume et Nathan
 *
 */
public abstract class Carte implements Serializable {
	/**
	 * Le nom de la carte.
	 */
	private String name;
	/**
	 * Permet de savoir si la carte est face cachée ou non.
	 */
	private Boolean visible;
	/**
	 * Permet de savoir si la carte fait partie de l'extension.
	 */
	private Boolean isExtension;
	/**
	 * Le lien vers l'image de cette carte
	 */
	private String img;
	/**
	 * Le lien de l'image du dos de la carte
	 */
	private String backImg = "/img/card/back.png";
	
	public void setImg(String img) {
		this.img = img+".png";
	}
	
	public Boolean isVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsExtension() {
		return isExtension;
	}

	/**
	 * Crée une carte en precisant si elle fait partie de l'extension
	 * 
	 * @param extension
	 */
	public Carte(Boolean extension) {
		this.visible = false;
		this.isExtension = extension;
	}
	
	public String getImg(){
		if (this.visible) {
			return this.img;
		} else {
			return this.backImg;
		}
	}
	
	
}
