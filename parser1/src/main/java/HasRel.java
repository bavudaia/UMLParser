
public class HasRel {
	public String fromClass,toClass,relMag, revRelMag;
	public boolean isBidirectional;
	
	public HasRel(){
		relMag = null;
		revRelMag = null;
		isBidirectional = false;
	}
	
	
	public HasRel(String fromClass, String toClass, String relMag, String revRelMag, boolean isBidirectional) {
		super();
		this.fromClass = fromClass;
		this.toClass = toClass;
		this.relMag = relMag;
		this.revRelMag = revRelMag;
		this.isBidirectional = isBidirectional;
	}


	public String getFromClass() {
		return fromClass;
	}

	public void setFromClass(String fromClass) {
		this.fromClass = fromClass;
	}

	public String getToClass() {
		return toClass;
	}

	public void setToClass(String toClass) {
		this.toClass = toClass;
	}

	public String getRelMag() {
		return relMag;
	}

	public void setRelMag(String relMag) {
		this.relMag = relMag;
	}

	public String getRevRelMag() {
		return revRelMag;
	}

	public void setRevRelMag(String revRelMag) {
		this.revRelMag = revRelMag;
	}

	public boolean isBidirectional() {
		return isBidirectional;
	}

	public void setBidirectional(boolean isBidirectional) {
		this.isBidirectional = isBidirectional;
	}
	
	
}
