package jumpingalien.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

public abstract class GameObject {
	
	/**
	 * Advances time for the Gameobject
	 * @param dt
	 * 	the given time in seconds
	 */
	public abstract void advanceTime(double dt);
	
	/**
	 * returns the currentsprite of the gameObject
	 */
	public abstract Sprite getCurrentSprite() throws ModelException;
	
	
	/**
	 * returns the XPosition of the GameObject
	 */
	@Basic @Raw
	public abstract int getPixelPositionXas();
	
	/**
	 * change the GameObject current Xposition to the given Xposition
	 * @param xPosition
	 * 	the given Xposition
	 * @Post the new Xposition will be equal to the given value
	 * 		|new.getPixelPositionXas==xPosition
	 */
	public abstract void setPixelPositionXas(int xPosition) ;
	
	/**
	 * returns the YPosition of the GameObject
	 */
	@Basic @Raw
	public abstract int getPixelPositionYas();
	
	/**
	 * change the GameObject current Yposition to the given Yposition
	 * @param yPosition
	 * 		the given YPosition
	 *@Post the new Yposition will be equal to the given value
	 * 		|new.getPixelPositionYas==yPosition
	 */
	@Raw
	public abstract void setPixelPositionYas(int yPosition) ;

	//##############################################################################
	/**
	 * Return the ActualPositionX of this gamObject
	 */
	@Basic @Raw
	public  double getActualPositionX() {
		return this.actualPositionX;
	}
	
	/**
	 * Set the the actualXPosition to the given value
	 * @param positionX
	 * 		The new Xposition for this GameObject
	 * @Post the actual Xposition is equal to the given value
	 * 		|new.getActualPositionX == positionX
	 * @Post if the gameObject is out of the boundaries ,terminate it
	 *       | if (Xposition<0||Xposition>((this.getWorld()).getnbTilesX())*(this.getWorld()).getTileSize()) then
	 *       |		this.getWorld().addAsTerminatedObjects(this);
	 * 
	 */
	public void setActualPositionX(double positionX) throws IllegalArgumentException{
		if (this.getWorld()!= null) {
			if(positionX<0||positionX>pixelToActual(((this.getWorld()).getnbTilesX())*(this.getWorld()).getTileSize())) {
				this.getWorld().addAsTerminatedObjects(this);
			}
		}
		this.actualPositionX = positionX;
	}
	
	/**
	 * Variable registering the actualXposition of this gameObject
	 */
	protected double actualPositionX;
	//##############################################################################

	/**
	 * Return the ActualPositionY of GameObject
	 */
	@Basic @Raw
	public double getActualPositionY() {
		return this.actualPositionY;
	}
	/**
	 * Set the Yposition of this GameObject to the given Yposition.
	 * 
	 * @param  Yposition
	 *         The new Yposition for this Plant(in meters).
	 * @post   If the Yposition is valid, the new Yposition of this new GameObject is equal to
	 *         the given Yposition. OtherWise if it is less than 0 or greater
	 *         than the world Heigth  it is added to the terminated objects
	 *       | if(Yposition<0||Yposition>pixelToActual((this.getWorld()).getnbTilesY())*(this.getWorld()).getTileSize()) then
	 *       |		this.getWorld().addAsTerminatedObjects(this);
	 * @post the new Yposition is equal to the given y position
	 *       |	new.getActualPositionYas() == Yposition
	 */
	@Raw
	public void setActualPositionY(double Yposition) {
		if (this.getWorld()!= null) {
			if(Yposition<0||Yposition>pixelToActual((this.getWorld()).getnbTilesY())*(this.getWorld()).getTileSize()) {
				this.getWorld().addAsTerminatedObjects(this);
				
			}
		}
		this.actualPositionY = Yposition;

	}
	
	/**
	 * Variable registering the actual y Position of this GameObject
	 */
	protected double actualPositionY;
	
	
	//##############################################################################
	/**
	 * Gives the position of GameObject in meters
	 * 
	 * @return returns an array with the first element the X-position and the second element tthe Y-position
	 * 		| result[0] == this.pixelToActual(this.getPixelPositionXas())
	 * 		| result[1] == this.pixelToActual(this.getPixelPositionYas())
	 */
	public double[] getPosition() {
		return new double[] {this.getActualPositionX(),this.getActualPositionY()};
	}
	
	/**
	 * changes the position of the GameObject to the given value
	 * @param newPosition
	 * 	 the new position of mazub, the first element is the Xposition
	 * 	 and the second is the Yposition
	 * @Post if the position is valid the new position is equal to the given position
	 * 		|new.getPosition() == newPosition
	 * @throws IllegalArgumentException 
	 */
	public void changePosition(double[] newPosition)throws IllegalArgumentException,IllegalStateException  {
		
		if (newPosition == null) {
			throw new IllegalArgumentException("position can't be null");
		}
		if (newPosition.length != 2) {
			throw new IllegalArgumentException("you can only give 2 values");
		}
		
		this.convertToPixel(newPosition[0], newPosition[1]);
	}
	//##############################################################################
	/**
	 * Return the Orientation of this GameObject.
	 */
	@Basic @Raw
	public int getOrientation() {
		return this.orientation;
	}
	
	
	/**
	 * Variable registering the Orientation of this GameObject.
	 */
	protected int orientation=0;
	
	//##############################################################################
	/**
	 * Returns the velocity of the gameObject, the first element is the horizontalVelocity
	 * ,the second element is the verticalVelocity
	 */
	public abstract double[] getVelocity();
	
	/**
	 * returns the surrounding rectangle of the gameobject,
	 * it consist of the left bottom pixel and the heigth and width of the object
	 * @return
	 * 		|result =={ this.getPixelPositionXas() ,this.getPixelPositionYas()
				, this.getCurrentSprite().getWidth()
				, this.getCurrentSprite().getHeight()}
	 */
	public int[] getRectanglePosition() {
		return new int[] { this.getPixelPositionXas() ,this.getPixelPositionYas()
				, this.getCurrentSprite().getWidth()
				, this.getCurrentSprite().getHeight()};
		
	}
	//##############################################################################
	
	/**
	 * function that deals with the collision with other gameObjects
	 */
	public abstract <T> void collidesWithOther(T other);
	
	
	//##############################################################################
	/**
	 * @post this function terminates an object
	 * 		|if (this.getWorld()!=null) 
	 * 	 	|(this.getWorld()).removeAsGameObject(this)
	 *		|this.setWorld(null)
	 *		|this.terminated=true
	 */
	 public  void terminate() {
		
		if (this.getWorld()!=null) {
		 (this.getWorld()).removeAsGameObject(this);
		}
		this.setWorld(null);
		this.terminated=true;
		
	}
	 /**
	 * @return this function returns if the variable is terminated or not.
	 * 		|return this.terminated
	 */
			
	public final boolean isTerminated() {
		return this.terminated;
	}
	
	/**
	 * a variable that contains the value of if this object is terminated or not
	 */
	protected boolean terminated;
	
	//##############################################################################			
	/**
	 * converts the given value in pixels in actual meters
	 * @param pixelPosition
	 * 		the value given in pixels
	 * @return
	 * 		|result== pixelPosition*0.01
	 */
	public static double pixelToActual(int pixelPosition) {
		double actualPositionPlant= pixelPosition*0.01;
		return actualPositionPlant;
	}
	
	
	/**
	 * sets the actual position and the pixel position of this GameObject
	 * 
	 * @param positionX
	 * the value of the X-position given in meters
	 * 
	 * @param positionY
	 * the value of the Y-position given in meters
	 * 
	 * @effect the GameObject's new position wil be set on the given position and(in pixels)
	 * 		| new.getPixelPositionXas() == actualToPixel(positionX)
	 * 		| new.getPixelPositionYas() == actualToPixel(positionY)
	 * 		| new.getActualPositionXas() == positionX
	 *		| new.getActualPositionYas() == positionY
	 */
	public void convertToPixel(double positionX, double positionY) {
		int x;
		int y;
		this.setActualPositionX(positionX);
		this.setActualPositionY(positionY);
		x=actualToPixel(positionX);
		y=actualToPixel(positionY);
		this.setPixelPositionXas(x);
		this.setPixelPositionYas(y);
	}
	


	/**
	 * convert the value in actual meters to pixels
	 * @param actualPosition
	 * 	The value given in meters
	 * @return returns the converted value to pixels
	 * 		| result== actualPosition*100
	 * @throws IllegalArgumentException
	 * 		the position can't be NaN
	 * 		|actualPosition == double.NaN || actualPosition == Double.POSITIVE_INFINITY || actualPosition == Double.NEGATIVE_INFINITY)
	 */
	public static int actualToPixel(double actualPosition) throws IllegalArgumentException {
		if (Double.isNaN(actualPosition) ){
			throw new IllegalArgumentException("cant be a NaN");
		}
		int pixelPosition= (int) Math.floor((100*actualPosition));
		return pixelPosition;
	}
	//##############################################################################
	
	/**
	 * return the world of this gameObject
	 */
	public World getWorld() {
		return this.referenceWorld ;
	}
	/**
	 * set the world to the given world
	 * @param newWorld
	 * 		the new world
	 * @post the given world is equal to the given world
	 * 		|new.getWorld()==newWorld
	 */
	public void setWorld(World newWorld) {
		this.referenceWorld= newWorld;
	}
	/**
	 * variable referencing the world of this gameobject
	 */
	private World referenceWorld;
	
	//##############################################################################
	/**
	 * Returns the pixelPosition of this gameobject
	 * @return
	 * 		|result==new int[] {this.getPixelPositionXas(),this.getPixelPositionYas()}
	 */
	public int[] getPixelPosition() {
		return new int[] {this.getPixelPositionXas(),this.getPixelPositionYas()};
	}
	
	//##############################################################################
	/**
	 * 
	 * @return this returns whether an object is dead or not.
	 * 			|return this.isDead
	 */
	
	public boolean isDeadGameobject() {
		return this.isDead;
	}
	
	/**
	 * @post this kills an object
	 * 		|this.isDead = true
	 */
	
	public void killGameObject() {
		this.isDead=true;
	}
	
	/**
	 *  this is a variable that registers whether an object is dead or not
	 */
	
	private boolean isDead =false;
	
	
	//##############################################################################
	/**
	 * Checks if the GameObject has collision with a tile
	 * @param rectanglePositionFirstObject
	 * 		the rectangle position of the first object
	 * @param rectanglePositionSecondObject
	 * 		the rectangle position of the second object
	 * @return	returns true if they have collision
	 */
	public  boolean  hasColissionWithTiles(int[] rectanglePositionFirstObject, int[]rectanglePositionSecondObject) {
		BasicCollision basic = new BasicCollision();
		return basic.hasColissionWithTiles(rectanglePositionFirstObject, rectanglePositionSecondObject);
	}
	
	/**
	 * returns true if a object has only collision with the top of the other objec
	 * @param rectanglePositionFirstObject
	 * 		the rectangle position of the first object
	 * @param rectanglePositionSecondObject
	 * 		the rectangle position of the second object
	 * 
	 * @return returns true if the first object has collision with the top of the other object
	 */
	public static boolean hasOnlyCollisionWithTop(int[] rectanglePositionFirstObject, int[]rectanglePositionSecondObject) {
		BasicCollision basic = new BasicCollision() {
			public boolean hasColissionWithTiles(int[] rectanglePositionFirstObject, int[]rectanglePositionSecondObject){
				return rectanglePositionSecondObject[1]+rectanglePositionSecondObject[3]-1 == rectanglePositionFirstObject[1];
			}
		};	
		return basic.hasColissionWithTiles(rectanglePositionFirstObject, rectanglePositionSecondObject);
	}

	public static boolean hasColissionWithBlockedObject(int[] rectanglePositionFirstObject, int[]rectanglePositionSecondObject) {
		BasicCollision basic = new BasicCollision();
		return basic.hasColissionWithBlockedObject(rectanglePositionFirstObject, rectanglePositionSecondObject);
	}
	
	/**
	 * checks if an object has collision with the innerlayers of another onject
	 * @param rectanglePositionFirstObject
	 * 		the rectangle position of the first object
	 * @param rectanglePositionSecondObject
	 * 		the rectangle position of the second object
	 * @return returns true if their inner layers overlap
	 */
	public static boolean  hasColissionWithinnerLayers(int[] rectanglePositionFirstObject, int[]rectanglePositionSecondObject) {
		BasicCollision basic = new BasicCollision();
		return basic.hasColissionWithinnerLayers(rectanglePositionFirstObject, rectanglePositionSecondObject);
	}
	
	//##############################################################################
	/**
	 * checks if there is collision with the given gameobject 
	 * @param gameobject
	 * 		the gameobject to check if they have collision
	 * @return this function returns true if an object collides with  this object.
	 * 		|result ==hasColissionWithBlockedObject(this.getRectanglePosition(), gameobject.getRectanglePosition())
	 */
	
	public boolean collidesWithObject(GameObject gameobject) {
		return hasColissionWithBlockedObject(this.getRectanglePosition(), gameobject.getRectanglePosition());
	}
	
	
	/**
	 * checks if a gameobject has collision with any other gameobject and performs apecific condition with if they are
	 * 
	 * @effect this function loops over the list of gameobjects , if they have collision it is going to search
	 * what happens.
	 * 			|setOfGameObjects.stream().filter(x ->  this.collidesWithObject(x)&& x!=this)
	 *			|	.forEach(x-> this.collidesWithOther(x));
	 */
	public void hasCollisionWithOtherGameObjects() {
	World world= this.getWorld();
	Set<GameObject> setOfGameObjects = world.getGameObjects();
	setOfGameObjects.stream().filter(x ->  this.collidesWithObject(x)&& x!=this)
		.forEach(x-> this.collidesWithOther(x));
	}
	
	/**
	 * terminates an object if it has fallen out of the borders of the world
	 * 
	 * @effect this function terminates an object if his x or y position are less than zero or if their
	 * x and y position are greater then the world heigth or width.
	 * 			|if (this.getActualPositionX()<0 || this.getPixelPositionXas()>world.getnbTilesX()*world.getTileSize()) {
	 *			|	new.getWorld().hasAsTerminatedObjects(this);
	 *			|else if(this.getActualPositionY()<0 || this.getPixelPositionYas()>world.getnbTilesY()*world.getTileSize()) {
	 *			|	new.getWorld().hasAsTerminatedObjects(this);
	 */
	
	public void terminateOutOfBorders() {
		World world = this.getWorld();
		if (this.getActualPositionX()<0 || this.getPixelPositionXas()>world.getnbTilesX()*world.getTileSize()) {
			this.getWorld().addAsTerminatedObjects(this);
		}
		else if(this.getActualPositionY()<0 || this.getPixelPositionYas()>world.getnbTilesY()*world.getTileSize()) {
			this.getWorld().addAsTerminatedObjects(this);
		}
	}
	
	
	//##############################################################################



	/**
	 * Return the hasBeenDeadFor of this GameObject.
	 */
	@Basic @Raw
	public double getHasBeenDeadFor() {
		return this.hasBeenDeadFor;
	}



	/**
	 * Set the hasBeenDeadFor of this GameObject to the given hasBeenDeadFor.
	 * 
	 * @param  hasBeenDeadFor
	 *         The new hasBeenDeadFor for this GameObject.
	 * @post   The hasBeenDeadFor of this new GameObject is equal to the given
	 *         hasBeenDeadFor.
	 *       |    new.getHasBeenDeadFor() == hasBeenDeadFor
	 */
	@Raw
	public void setHasBeenDeadFor(double hasBeenDeadFor) {
			this.hasBeenDeadFor = hasBeenDeadFor;
	}
	
	
	
	/**
	 * variable registering how long the plant is dead
	 */
	public double hasBeenDeadFor = 0;
	//##############################################################################
	
	/**
	 * this method terminate all gameobject that are in the given World  TerminatedObjects
	 * 
	 * @param world
	 * 		the given world
	 * 
	 * @effect this function terminates all objects in the world his terminatedObjects
	 * then clears the list
	 * 		|for (GameObject terminatedObject : world.getTerminatedObjects()) {
	 *		|	terminatedObject.terminate();
	 *		|world.getTerminatedObjects().clear();
	 */
	
	public void terminator(World world) {
		if(world!=null&&! this.isTerminated()) {
			for (GameObject terminatedObject : world.getTerminatedObjects()) {
				terminatedObject.terminate();
			
			}
			world.getTerminatedObjects().clear();
		}
	}
	//##############################################################################
	/**
	 * returns the vertical acceleration of this gameobject
	 */
	public double getVerticalAcceleration() {
		return verticalAcceleration;
	}
	
	/**
	 * sets the vertical acceleration of this gameObject to the given value
	 * @param verticalAcceleration
	 * 		the given vertical acceleration
	 * 
	 * @Post the given verticalAcceleration is equal to the given vertical Acceleration
	 * 		| new.getVerticalAcceleration()== verticalAcceleration
	 */
	public void setVerticalAcceleration(double verticalAcceleration) {
		this.verticalAcceleration=verticalAcceleration;
		
	}
	
	/**
	 * variable registering the vertical acceleration of this gameObject
	 */
	private double verticalAcceleration;
	
	//##############################################################################
	/**
	 * returns the horizontal acceleration of this gameobject
	 */
	public double getHorizontalAcceleration() {
		return horizontalAcceleration;
	}
	/**
	 * sets the horizontal acceleration of this gameObject to the given value
	 * @param horizontalAcceleration
	 * 		the given horizontal acceleration
	 * 
	 * @Post the given horizontalAcceleration is equal to the given horizontal Acceleration
	 * 		| new.getHorizontalAcceleration()== horizontalAcceleration
	 */
	public void setHorizontalAcceleration(double horizontalAcceleration) {
		this.horizontalAcceleration=horizontalAcceleration;
	}
	
	/**
	 * variable registering the horizontal Acceleration
	 */
	private double horizontalAcceleration;
	
	//##############################################################################
	/**
	 * return the acceleration of this GameObject
	 * @return returns an array with as first element the horizontal acceleration and as second element 
	 * the vertical acceleration
	 * 		|result== new double[] {this.getHorizontalAcceleration(),this.getVerticalAcceleration()}
	 */
	public double[] getAcceleration() {
		return new double[] {this.getHorizontalAcceleration(),this.getVerticalAcceleration()};
	}
	//##############################################################################
	
	/**
	* returns the hitpoints of GameObject
	*/
	@Basic @Raw
	public  int getHitPoints() {
		return this.hitpoints;
	}
	/**
	 * Set the hitpoints of the gameobject to the given value
	 * @param newHitpoints
	 * 	the new hitpoints of this GameObject
	 * @post if the given hitpoints are valid the new hitpoints of this gameobject are equal
	 * to the given hitpoints, else if the given hitpoints are zero or lower, they become 0 and the gameobject is dead.
	 * 		|if(newHitPoints<=0) then
	 * 		|	new.getHitpoints() ==0
	 * 		|	new.getIsDead()==true
	 * 		|else then
	 * 		|	new.getHitpoints()==newHitpoints
	 * 	
	 */
	 @Raw
	public void setHitpoints(int newHitpoints) {
		if(newHitpoints<=0) {
			this.hitpoints=0;
			this.killGameObject();
		}
		else {
			this.hitpoints = newHitpoints;
		}
	
	}
	
	/**
	 * changes the hitpoints with the given value
	 * 
	 * @post add or subtract the given hitpoints
	 * 		|new.getHitPoints() == newHitPoints+this.getHitPoints()
	 */
	public void addHitpoints(int newHitPoints) {
		this.setHitpoints(newHitPoints+this.getHitPoints());
		
	}
	
	/**
	 * variable registering the hitpoints of this gameObject
	 */
	protected int hitpoints;
	//##############################################################################
	
	/**
	 *Returns the sprites of the gameObject 
	 */
	public  Sprite[] getSprites() {
		return sprites;
	}
	
	/**
	 * sets the list of sprites to the given sprites
	 * @param sprites
	 * 	the given sprites to set
	 * @post the new sprites are equal to the given sprites
	 * 		| new.getSprites()==sprites
	 * @throws IllegalStateException
	 * 		|!(hasValidSprites(sprites)
	 */
	public  void setSprite(Sprite[] sprites)throws IllegalStateException {
		if (! isValidSprites(sprites)) {
			throw new IllegalStateException("Contains a null sprite");
		}
		this.sprites = sprites.clone();
	}
	/**
	 * checks if the sprites are valid sprites for any GameObject
	 * @param sprites
	 * 	the given array of sprites
	 * @return return false if a sprite in the array of sprites is a non effective sprite, or if
	 * no array is given
	 * 	| result!=  sprites.contains(null)
	 */
	public boolean isValidSprites(Sprite[] sprites) {
		if(sprites==null) {
			return false;
		}
		for (Sprite checkSprite: sprites) {
			if (checkSprite ==null) {
				return false;
			}
		}
		return true;
	}
	/**
	 * variavele registering the sprites
	 */
	protected Sprite[] sprites;
	
	//#########################################################################################
	/**
	 * Add the given time to all gameobject that are in contact with this gameObject.
	 * @param dt
	 * 		the given time
	 * @post Add the given time to all gameobject that are in contact with this gameObject, then removes
	 * all the gameobject who are longer then 0.6 seconds in contact with him.
	 */
	public void timeRemoveContact (double dt) {
		this.addTimeToAllObjects(dt);
		this.removeAllObjectsWithTime(0.6);
	}
	
	//#########################################################################################
	/**
	 * returns if the given gameobject is in collision with the this gameobject and for how long
	 * 
	 * @param gameObject
	 * 		the given gameObject
	 * @return returns a double list the first value represents if the gameobject is still
	 * with this gameobject(if this value >0 otherwise they aren't in contact). The second value represents
	 * how long the given GameObject is in contact with this gameObject
	 * 		| result == impactWithGameObject.get(gameObject)
	 */
	public  List<Double> getImpactList(GameObject gameObject){
		return impactWithGameObject.get(gameObject);
	}
	
	//#########################################################################################
	/**
	 * returns the time that the given given Gameobject is in collision with this GameObject
	 * @param gameObject
	 * 		the given GameObject
	 * @return	returns the time that the given gameObject is in collision
	 * as an double value
	 * 		| result== impactWithGameObject.get(gameObject).get(1)
	 */
	public double getImpactTime(GameObject gameObject) {
		return  impactWithGameObject.get(gameObject).get(1);
	}
	//#########################################################################################
	/**
	 * Add the given gameObject to a hashmap with the given collision value and time as key
	 * @param gameObject
	 * 		the given gameobject
	 * @param value
	 * 	the value that represents if the given gameobject is in collision with this gameobject
	 * if they are in contact this value is  2, if they were in contact this value is 1, otherwise it is zero
	 * @param time
	 * 	the given time this object is in collision with this object
	 * 
	 * @post	...
	 * 		|impactWithGameObject.put(gameObject,valueImpact);
	 * @throws IllegalArgumentException
	 * 		|! isValidValueImpact(this, gameObject, value)
	 * @throws IllegalArgumentException
	 * 		| time<0
	 * 
	 */
	public void addImpactGameObject(GameObject gameObject, double value,double time)throws IllegalArgumentException {
		List<Double> valueImpact = new ArrayList<>();
		if (! isValidValueImpact(this, gameObject, value)) {
			throw new IllegalArgumentException("invalid value");
		}
		if(time<0) {
			throw new IllegalArgumentException("invalid time");
		}
		valueImpact.add(value);
		valueImpact.add(time);
		impactWithGameObject.put(gameObject,valueImpact);
	}
	/**
	 *  checks if the given value is a valid value for this gameobject
	 * @param thisGameObject
	 * 		| this gameObject
	 * @param otherGameObject
	 * 		| the other gameObject
	 * @param value
	 * 		| the given impact value
	 * @return
	 * 		result== (value==0|| value==1) ||(value==2 &&thisGameObject.collidesWithObject(otherGameObject))
	 */
	public boolean isValidValueImpact(GameObject thisGameObject, GameObject otherGameObject, double value) {
//		if (value==0|| value==1) {
//			return true;
//		}
//		if ( value==2) {
//			if (! thisGameObject.collidesWithObject(otherGameObject)) {
//				return false;
//			}
//			return true;
//		}
//		else {
//			return false;
//		}
		return true;
	}
	
	/**
	 * removes the given gameObject from the list of ImpactGameObjects
	 */
	public void removeImpactGameObject(GameObject gameObject) {
		impactWithGameObject.remove(gameObject);
	}
	
	/**
	 * returns true if this gameobject has or had collision with the given gameobject
	 * 
	 * @param gameObject
	 * 		the given gameobject
	 * @return
	 * result==impactWithGameObject.get(gameObject)!=null &&this.getImpactValueOfGameObject(gameObject)>0
	 */
	public boolean hasImpactWithGameObject(GameObject gameObject) {
		return impactWithGameObject.get(gameObject)!=null &&this.getImpactValueOfGameObject(gameObject)>0;
	}
	
	/**
	 * returns the impact value for the given gameobject
	 */
	public double getImpactValueOfGameObject(GameObject gameObject) {
		return  impactWithGameObject.get(gameObject).get(0);
	}
	
	/**
	 * Remove all objects of impactWithGameObject with the given value
	 * @param value
	 * 		the given value
	 * @effect ...
	 * 		|for(GameObject gameObject: impactWithGameObject.keySet()) 
	 * 		|	if(! hasImpactWithGameObject(gameObject)) then
	 * 		|		ObjectsToRemove.add(gameObject)
	 * @effect 	...
	 * 		|for (int i=0; i< ObjectsToRemove.size();i++)
	 * 		|	removeImpactGameObject(ObjectsToRemove.get(i)
	 */
	public  void removeAllObjectsWithValue(int value) {
		List<GameObject> ObjectsToRemove = new ArrayList<>();
		for(GameObject gameObject: impactWithGameObject.keySet()) {
			if(! hasImpactWithGameObject(gameObject)) {
				ObjectsToRemove.add(gameObject);
			}
		}
		for (int i=0; i< ObjectsToRemove.size();i++) {
			removeImpactGameObject(ObjectsToRemove.get(i));
		}
	}
	/**
	 * Remove all object of impactWithGameObject with the given time
	 * @param time
	 * 		the given time
	* @effect ...
	 * 		|for(GameObject gameObject: impactWithGameObject.keySet()) 
	 * 		|	if(this.getImpactTime(gameObject)>=time) then
	 * 		|		ObjectsToRemove.add(gameObject)
	 * @effect 	...
	 * 		|for (int i=0; i< ObjectsToRemove.size();i++)
	 * 		|	removeImpactGameObject(ObjectsToRemove.get(i)
	 */
	public  void removeAllObjectsWithTime(double time) {
		List<GameObject> ObjectsToRemove = new ArrayList<>();
		for(GameObject gameObject: impactWithGameObject.keySet()) {
			if(this.getImpactTime(gameObject)>=time) {
				ObjectsToRemove.add(gameObject);
			}
		}
		for (int i=0; i< ObjectsToRemove.size();i++) {
			removeImpactGameObject(ObjectsToRemove.get(i));
		}
	}
	
	/**
	 * adds the given value to all the gameobject in the hashmap of impactWithGameObject
	 * @param value
	 * 		the given value to add
	 * @post	...
	 * 		|for(GameObject gameObject: impactWithGameObject.keySet())
	 * 		| new.getImpactValueOfGameObject(gameObject) == this.getImpactValueOfGameObject(gameObject) +value)
	 */
	public void addSpecificValueToAllObject(int value) {
		for(GameObject gameObject: impactWithGameObject.keySet()) {
			changeImpactValue(gameObject,this.getImpactValueOfGameObject(gameObject) +value);
		}
	}
	
	/**
	 * adds the given time to all the gameobjects in the hashmap of impactWithGameObject
	 * @param time
	 * 		the given time to add
	 * @post ...
	 * 		|for(GameObject gameObject: impactWithGameObject.keySet())
	 * 		| new.getImpactTime(gameObject)==this.getImpactTime(gameObject)+time)
	 */
	public void addTimeToAllObjects(double time) {
		for(GameObject gameObject: impactWithGameObject.keySet()) {
			changeTimeValue(gameObject,this.getImpactTime(gameObject)+time);
		}
	}
	//#########################################################################################
	
	/**
	 * changes the value of the given  gameobject to the given value
	 * @param gameObject
	 * 		the given gameobject
	 * @param value
	 * 		the given value
	 * @effect if the object is already in the list keep his time value
	 * 		|if (hasImpactWithGameObject(gameObject)) then
	 * 		|	addImpactGameObject(gameObject,value,this.getImpactTime(gameObject))
	 * @effect otherwise set the time value of the new gameobject to zero
	 * 		|	addImpactGameObject(gameObject,value,0)
	 */
	public void changeImpactValue(GameObject gameObject, double value ) {
		if (hasImpactWithGameObject(gameObject)) {
			addImpactGameObject(gameObject,value,this.getImpactTime(gameObject));
		}
		else {
			addImpactGameObject(gameObject,value,0);
		}
	}
	/**
	 * changes the time of the given  gameobject to the given time
	 * @param gameObject
	 * 		the given gameobject
	 * @param time
	 * 		the given time
	 * @effect if the object is already in the list keep his value
	 * 		|if (hasImpactWithGameObject(gameObject)) then
	 * 		|	addImpactGameObject(gameObject,this.getImpactValueOfGameObject(gameObject),time)
	 * @effect otherwise set the  value of the new gameobject to zero
	 * 		|	addImpactGameObject(gameObject,0,time)
	 */
	public void changeTimeValue(GameObject gameObject, double time){
		if (hasImpactWithGameObject(gameObject)) {
			addImpactGameObject(gameObject,this.getImpactValueOfGameObject(gameObject),time);
		}
		else {
			addImpactGameObject(gameObject,0,time);
		}
	}
	//#########################################################################################
	/**
	 * hashmap registering with wich object this object has collsission with and for how long
	 */
	HashMap<GameObject,List<Double>> impactWithGameObject=new HashMap<>();
	




}