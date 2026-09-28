package jumpingalien.model;

public enum GeologicalFeatures {
	AIR(0),
	SOLID_GROUND(1),
	WATER(2),
	MAGMA(3),
	ICE(4),
	GAS(5);
	
	private GeologicalFeatures(final int newValue) {
         value = newValue;
     }
	
	/**
	 * each geological feature has its own unique value
	 */
	private final int value;
	

	 
	 /**
	  * Returns the value of the geological feature
	  */
	 public int getValue() { 
		 return value;
	 }
	 
	 /**
	  * returns the geologicalfeature with the given value
	  * @param geologicalFeatureInteger
	  * 	the given integer value 
	  * @return returns the geological feature that has the given value
	  * 	|if(geologicalFeature.getValue()==geologicalFeatureInteger)
	  * 	|	result==geologicalFeature
	  * @return if the given interger doesn't exist return air
	  * 	result==AIR
	  */
	 public static GeologicalFeatures getGeologicalFeature(int geologicalFeatureInteger) {
		 for (GeologicalFeatures geologicalFeature: GeologicalFeatures.values()) {
			 if(geologicalFeature.getValue()==geologicalFeatureInteger) {
				 return geologicalFeature;
			 }
		 }
		 return AIR;
	 }
}
