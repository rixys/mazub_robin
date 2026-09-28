package jumpingalien.model;

public class BasicCollision {
	
	
	public BasicCollision() {
		
	}
	/**
	 * Checks if the GameObject has collision with a tile
	 * @param rectanglePositionFirstObject
	 * 		the rectangle position of the first object
	 * @param rectanglePositionSecondObject
	 * 		the rectangle position of the second object
	 * @return	returns true if they have collision
	 */
	public  boolean  hasColissionWithTiles(int[] rectanglePositionFirstObject, int[]rectanglePositionSecondObject) {
		if (rectanglePositionFirstObject[0]+rectanglePositionFirstObject[2]< rectanglePositionSecondObject[0]) {
			return false;
		}
		if (rectanglePositionSecondObject[0]+rectanglePositionSecondObject[2] < rectanglePositionFirstObject[0]) {
			return false;
		}
		if(rectanglePositionFirstObject[1]+rectanglePositionFirstObject[3]<rectanglePositionSecondObject[1]) {
			return false;
		}
		if(rectanglePositionSecondObject[1]+rectanglePositionSecondObject[3]-1 < rectanglePositionFirstObject[1]) {
			return false;
		}

		return true;
	}
	
	/**
	 * Checks if the gameObject has collision with another gameobject (the top tile don't overlap)
	 * @param rectanglePositionFirstObject
	 * 		the rectangle position of the first object
	 * @param rectanglePositionSecondObject
	 * 		the rectangle position of the second object
	 * @return	returns true if they have collision
	 */
	public boolean hasColissionWithBlockedObject(int[] rectanglePositionFirstObject, int[]rectanglePositionSecondObject) {
		if (rectanglePositionFirstObject[0]+rectanglePositionFirstObject[2]< rectanglePositionSecondObject[0]) {
			return false;
		}
		if (rectanglePositionSecondObject[0]+rectanglePositionSecondObject[2] < rectanglePositionFirstObject[0]) {
			return false;
		}
		if(rectanglePositionFirstObject[1]+rectanglePositionFirstObject[3]<rectanglePositionSecondObject[1]) {
			return false;
		}
		if(rectanglePositionSecondObject[1]+rectanglePositionSecondObject[3] < rectanglePositionFirstObject[1]) {
			return false;
		}

		return true;
	}
	
	
	/**
	 * checks if an object has collision with the innerlayers of another onject
	 * @param rectanglePositionFirstObject
	 * 		the rectangle position of the first object
	 * @param rectanglePositionSecondObject
	 * 		the rectangle position of the second object
	 * @return returns true if their inner layers overlap
	 */
	public boolean  hasColissionWithinnerLayers(int[] rectanglePositionFirstObject, int[]rectanglePositionSecondObject) {
		if (rectanglePositionFirstObject[0]+rectanglePositionFirstObject[2]-1< rectanglePositionSecondObject[0]) {
			return false;
		}
		if (rectanglePositionSecondObject[0]+rectanglePositionSecondObject[2]-1 < rectanglePositionFirstObject[0]) {
			return false;
		}
		if(rectanglePositionFirstObject[1]+rectanglePositionFirstObject[3]-2<rectanglePositionSecondObject[1]) {
			return false;
		}
		if(rectanglePositionSecondObject[1]+rectanglePositionSecondObject[3]-2 < rectanglePositionFirstObject[1]) {
			return false;
		}

		return true;
	}
	
}
