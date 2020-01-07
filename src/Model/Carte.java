package Model;

import java.io.Serializable;

public abstract class Carte implements Serializable {
	private String name;
	private Boolean visible;
	private Boolean isExtension;
	private String img;
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
