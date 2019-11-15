package Class;

import Enumeration.TropheeEnum;

public class TropheeDesc {
	private TropheeEnum tropheeDesc;
	private Enum infoSupp;

	public TropheeEnum getTropheeDesc() {
		return tropheeDesc;
	}
	
	public Enum getInfoSupp() {
		return infoSupp;
	}

	public void setTrophee(TropheeEnum tropheeDesc) {
		this.tropheeDesc = tropheeDesc;
	}

	public TropheeDesc(TropheeEnum tropheeDesc,Enum infoSupp) {
		super();
		this.tropheeDesc = tropheeDesc;
		this.infoSupp = infoSupp;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TropheeDesc other = (TropheeDesc) obj;
		if (infoSupp == null) {
			if (other.infoSupp != null)
				return false;
		} else if (!infoSupp.equals(other.infoSupp))
			return false;
		if (tropheeDesc != other.tropheeDesc)
			return false;
		return true;
	}
	
	
}
