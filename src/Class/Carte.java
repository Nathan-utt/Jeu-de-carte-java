package Class;

public abstract class Carte {
	private String name;
	private Boolean visible;
	
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

	public Carte() {
		this.visible = false;
	}
	
}
