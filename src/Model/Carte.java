package Model;

import java.io.Serializable;

/**
 * 
 * 
 * Cette classe Carte va permettre de créer le model des carte utilisé dans le jeu JEST.
 * Elle implémente Serializable ce qui va permettre aux carte d'être sauvegardées.
 * 
 * @serial Serializable
 * 
 * @author Guillaume et Nathan
 *
 */
public abstract class Carte implements Serializable {
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private Boolean visible;
	/**
	 * 
	 */
	private Boolean isExtension;
	/**
	 * 
	 */
	private String img;
	/**
	 * 
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
