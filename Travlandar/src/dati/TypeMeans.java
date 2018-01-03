package dati;

/**
 * Enum that contains all means type
 */
public enum TypeMeans {
	driving, bicycling, walking, bus, subway, train, tram, rail;

	/**
	 * @return the {@link String} for the requests to the Google's API
	 */
	public String getTypeAPI() {
		switch (this) {
		case driving:
			return "mode=driving";
		case bicycling:
			return "mode=bicycling";
		case walking:
			return "mode=walking";
		case bus:
			return "mode=transit&transit_mode=bus";
		case subway:
			return "mode=transit&transit_mode=subway";
		case train:
			return "mode=transit&transit_mode=train";
		case tram:
			return "mode=transit&transit_mode=tram";
		case rail:
			return "mode=transit&transit_mode=rail";
		default:
			return "";
		}
	}
	
	public boolean isTransit() {
		if(this == bus || this == subway || this == train || this == tram || this == rail) {
			return true;
		}else {
			return false;
		}
	}

}
