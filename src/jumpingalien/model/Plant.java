package jumpingalien.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;



public class Plant extends GameObject implements horizontalMovingObject,verticalMovingObject  {
	


	
	
	
	//##############################################################################
	public Plant(int positionX, int positionY, Sprite[] inSprites)throws ModelException {
		this.setPixelPositionXas(positionX);
		this.setPixelPositionYas(positionY);
		this.setActualPositionX(pixelToActual(positionX));
		this.setActualPositionY(pixelToActual(positionY));
		this.setSprite(inSprites);
		this.setCurrentsprite(this.getSprites()[0]);
		this.setHorizontalVelocity(-0.5);
		this.setMaxLivingTime(10);
		this.setHitpoints(1);
		this.setOrientation(-1);
	}
	

	
	//##############################################################################

	/**
	 * Return the Xposition of this Plant.
	 */
	@Basic @Raw
	public int getPixelPositionXas() {
		return this.pixelPositionXas;
	}
	/**
	* Set the Xposition of this Plant to the given Xposition.
	* 
	* @param  newXposition
	*         The new Xposition for this Plant.
	* @post   The Xposition of this new Plant is equal to
	*         the given Xposition.
	*       | new.getPixelPositionXas() == newXposition
	* @throws ModelException
	*         The given Xposition is not a valid Xposition for any
	*         Plant.
	*       | ! isValidpixelPositionXas(getPixelPositionXas())
	*/
	@Raw
	public void setPixelPositionXas(int xas) throws IllegalArgumentException {

	if ( ! isValidpixelPositionXas(xas)) {
		throw new ModelException("Out of borders");
		}
	this.pixelPositionXas = xas;	
	}
			
	/**
	 * Check whether the given Xposition is a valid Xposition for
	 * any Plant.
	 *  
	 * @param  Xposition
	 *         The Xposition to check.
	 * @return 
	 *       | result ==  newXposition <0 && newXposition>WORLD_WIDTH
	 */
	public static boolean isValidpixelPositionXas(int newXposition) {
		return true; 
				
		}
	
	/**
	 * Variable registering the Xposition of this Plant.
	 */
	private int pixelPositionXas= 0;
//##############################################################################
			
			/**
			 * Return the pixelPositionYas of this plant.
			 */
			@Basic @Raw
			public int getPixelPositionYas(){
				return pixelPositionYas;
			}
			
			/**
			 * Set the pixelPositionYas of this Plant to the given pixelPositionYas.
			 * 
			 * @param  newYposition
			 *         The new pixelPositionYas for this Plant
			 * @post   The pixelPositionYas of this new Plant is equal to
			 *         the given pixelPositionYas.
			 *       | new.getpixelPositionYas() == newYposition
			 */
			@Raw
			public void setPixelPositionYas(int yas)  {
					this.pixelPositionYas = yas;
			}
			
			/**
			 * Variable registering the pixelPositionYas of this Plant.
			 */
			private int pixelPositionYas = 0;
			
			//##############################################################################			
			public static double pixelToActual(int pixelPosition) {
				double actualPositionPlant= pixelPosition*0.01;
				return actualPositionPlant;
			}
			
			
			/**
			 * 
			 * @param positionX
			 * the value of the X-position given in meters
			 * 
			 * @param positionY
			 * the value of the Y-position given in meters
			 * 
			 * @effect the Plants new position wil be set on the given position(in pixels)
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
			 * 
			 * @param actualPosition
			 * The value given in meters
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
			 * changes the position of the GameObject to the given value
			 * @param newPosition
			 * 	 the new position of Plant, the first element is the Xposition
			 * 	 and the second is the Yposition
			 * @Post if the position is valid the new position is equal to the given position
			 * 		|new.getPosition() == newPosition
			 * @throws IllegalArgumentException 
			 * 		| newPosition == null || newPosition.length != 2
			 */
			@Override
			public void changePosition(double[] newPosition) {
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
		 * Returns the current Sprite of this Plant
		 */
		@Override
		public Sprite getCurrentSprite() throws ModelException {
			return this.currentSprite;
		}
		/**
		 * Sets the current sprite to the given sprite of this plant
		 * @param sprite
		 * 		the given sprite
		 * 	@post	...
		 * 		| new.getCurrentSprite()==sprite
		 */
		public void setCurrentsprite(Sprite sprite) {
			this.currentSprite= sprite;
		}
		
		/**
		 * Variable registering the currentSprite
		 */
		private Sprite currentSprite;
		
		//##############################################################################
		/**
		 * Sets the orientation of this plant to the given orientation
		 * @param newOrientation
		 * 		the given orientation
		 *  @post ...
		 *  new.getOrientation== newOrientation
		 */
		@Raw
		public void setOrientation(int newOrientation) {
			this.orientation=newOrientation;
		}

		//##############################################################################
		
		/**
		 * Return the velocity of this Plant
		 * 
		 * @return
		 * 		|result == new double[] {this.getHorizontalVelocity(),this.getVerticalVelocity()}
		 */
		@Override
		public double[] getVelocity() {
			// TODO Auto-generated method stub
			return new double[] {this.getHorizontalVelocity(),this.getVerticalVelocity()};
		}
		//##############################################################################
		
		
		/**
		 * Check whether the given sprites are valid for any Plant
		 *  
		 * @param  sprites
		 *         The new Sprites to check.
		 * @return returns true if the total number of sprites is  equal to 2
		 * 		|result sprites.length==2
		 */
		@Override
		public boolean isValidSprites(Sprite[] sprites) {
			if(super.isValidSprites(sprites)) {
				return (sprites.length==2 );
			}
			return false;
		}
		

		

		//##############################################################################
		

	
	
	/**
	 * Return the horizontalVelocity of this plant.
	 */
	@Basic @Raw
	public double getHorizontalVelocity() {
		return this.horizontalVelocity;
	}
	
	/**
	 * Check whether the given horizontalVelocity is a valid horizontalVelocity for
	 * any plant.
	 *  
	 * @param  newHorizontalVelocity
	 *         The horizontalVelocity to check.
	 * @return 
	 *       | result == true
	*/
	public static boolean isValidHorizontalVelocity(double newHorizontalVelocity) {
		return true;
	}
	
	/**
	 * Set the horizontalVelocity of this plant to the given horizontalVelocity.
	 * 
	 * @param  newHorizontalVelocity
	 *         The new horizontalVelocity for this plant.
	 * @post   If the given horizontalVelocity is a valid horizontalVelocity for any plant,
	 *         the horizontalVelocity of this new plant is equal to the given
	 *         horizontalVelocity.
	 *       | if (isValidHorizontalVelocity(newHorizontalVelocity))
	 *       |   then new.getHorizontalVelocity() == newHorizontalVelocity
	 */
	@Raw
	public void setHorizontalVelocity(double newHorizontalVelocity) {
			this.horizontalVelocity = newHorizontalVelocity;
	}
	
	/**
	 * Variable registering the horizontalVelocity of this plant.
	 */
	private double horizontalVelocity =0.5;
	
	
	//##############################################################################
	/**
	 * Return the verticalVelocity of this plant.
	 */
	@Basic @Raw
	public double getVerticalVelocity() {
		return this.verticalVelocity;
	}
	
	/**
	 * Check whether the given horizontalVelocity is a valid horizontalVelocity for
	 * any plant.
	 *  
	 * @param  newHorizontalVelocity
	 *         The horizontalVelocity to check.
	 * @return 
	 *       | result == true
	*/
	public static boolean isValidVerticalVelocity(double newHorizontalVelocity) {
		return true;
	}
	
	/**
	 * Set the verticalVelocity of this plant to the given verticalVelocity.
	 * 
	 * @param  newVerticalVelocity
	 *         The new verticalVelocity for this plant.
	 * @post   If the given verticalVelocity is a valid horizontalVelocity for any plant,
	 *         the verticalVelocity of this new plant is equal to the given
	 *         verticalVelocity.
	 *       | if (isValidHorizontalVelocity(newHorizontalVelocity))
	 *       |   then new.getHorizontalVelocity() == newHorizontalVelocity
	 */
	@Raw
	public void setVerticalVelocity(double newVerticalVelocity) {
			this.verticalVelocity = newVerticalVelocity;
	}
	
	/**
	 * Variable registering the verticalVelocity of this plant.
	 */
	private double verticalVelocity =0;
	
	
	//##############################################################################
	
	
	/**
	 * Return the timeDirection of this plant.
	 */
	@Basic @Raw
	public double getTimeDirection() {
		return this.timeDirection;
	}
	
	/**
	 * Check whether the given timeDirection is a valid timeDirection for
	 * any plant.
	 *  
	 * @param  timeDirection
	 *         The timeDirection to check.
	 * @return 
	 *       | result == true
	*/
	public static boolean isValidTimeDirection(double timeDirection) {
		return true;
	}
	
	/**
	 * Set the timeDirection of this plant to the given timeDirection.
	 * 
	 * @param  timeDirection
	 *         The new timeDirection for this plant.
	 * @post   If the given timeDirection is a valid timeDirection for any plant,
	 *         the timeDirection of this new plant is equal to the given
	 *         timeDirection.
	 *       | if (isValidTimeDirection(timeDirection))
	 *       |   then new.getTimeDirection() == timeDirection
	 */
	@Raw
	public void setTimeDirection(double timeDirection) {
		if (isValidTimeDirection(timeDirection))
			this.timeDirection = timeDirection;
	}
	
	/**
	 * Variable registering the timeDirection of this plant.
	 */
	private double timeDirection=0;
	
	
	//##############################################################################
	
	/**
	 * Calculates when the plant has to switch and the position of the plant
	 */
	private void calculateTimeDirectionAndPosition(double dt) {
		double time=dt;
		while (this.getTimeDirection()+time>=0.5) {
			this.setHorizontalVelocity(Math.abs(this.getHorizontalVelocity())*this.getOrientation());
			this.setVerticalVelocity((Math.abs(this.getVerticalVelocity())*this.getOrientation()));
			this.calculatePosition(0.5-this.getTimeDirection());
			time=time-(0.5-this.getTimeDirection());
			this.setOrientation(this.getOrientation()*-1);
			this.setTimeDirection(0);
		}
		this.setHorizontalVelocity(Math.abs(this.getHorizontalVelocity())*this.getOrientation());
		this.setVerticalVelocity(Math.abs(this.getVerticalVelocity())*this.getOrientation());
		calculatePosition(time);
		this.setTimeDirection(this.getTimeDirection()+time);
	}
	//##############################################################################
	
	/**
	 * Calculates the new position of the plant
	 * @post	...
	 * 		|new.getActualPositionX==this.getHorizontalVelocity()*dt+this.getActualPositionX()
	 * 		|new.getPixelPositionXas == actualToPixel(this.getActualPositionX())
	 * 		|new.getActualPositionY == this.getVerticalVelocity()*dt+this.getActualPositionY()
	 * 		|new.getPixelPositionYas == actualToPixel(this.getActualPositionY())
	 */
	public void calculatePosition(double dt) {
		this.setActualPositionX(this.getHorizontalVelocity()*dt+this.getActualPositionX());
		this.setPixelPositionXas(actualToPixel(this.getActualPositionX()));
		this.setActualPositionY(this.getVerticalVelocity()*dt+this.getActualPositionY());
		this.setPixelPositionYas(actualToPixel(this.getActualPositionY()));
	}
	//##############################################################################
	
	
	/**
	 * Return the livingTime of this Plant.
	 */
	@Basic @Raw
	public double getLivingTime() {
		return this.livingTime;
	}
	
	/**
	 * Check whether the given livingTime is a valid livingTime for
	 * any Plant.
	 *  
	 * @param  livingTime
	 *         The livingTime to check.
	 * @return 
	 *       | result == 
	*/
	public static boolean isValidLivingTime(double livingTime) {
		return true;
	}
	
	/**
	 * Set the livingTime of this Plant to the given livingTime.
	 * 
	 * @param  livingTime
	 *         The new livingTime for this Plant.
	 * @post   The livingTime of this Plant is equal to the given
	 *         livingTime.
	 *       | new.getLivingTime() == livingTime
	 */
	@Raw
	public void setLivingTime(double livingTime) {
		this.livingTime = livingTime;
	}
	
	/**
	 * Variable registering the livingTime of this Plant.
	 */
	private double livingTime=0;
	
	//##############################################################################
	
	/**
	 * Advances the given time for this Plant
	 * @param dt	
	 * 		the given time
	 * @effect cut the time in pieces so that the plant only moves 1 pixel at a time
	 * and do different effect for when he is alive or dead
	 *		|for(double i =collisionTimer;i<=dt; i+=collisionTimer ) 
	 *		|	this.methodsForAdvanceTime(collisionTimer);
	 *		|	remainder=remainder-collisionTimer;
	 *		|if (remainder>0) then
	 *		|	this.methodsForAdvanceTime(remainder)
	 */
	public void advanceTime(double dt) {
		double collisionTimer= this.collisionTimer(dt);
		double remainder=dt;
		for(double i =collisionTimer;i<=dt&& ! (collisionTimer==0); i+=collisionTimer ) {
			if(this.isDeadGameobject()) {
				advanceDeadTimer(collisionTimer);
			}
			else {
				this.advanceTimeMethodsPlant(collisionTimer);
			}
			remainder=remainder-collisionTimer;
			collisionTimer= this.collisionTimer(remainder);
		}
		if (remainder>=0) {
			if(this.isDeadGameobject()) {
				advanceDeadTimer(remainder);
			}
			else {
				this.advanceTimeMethodsPlant(remainder);
			}
		}
	}
	
	/**
	 * Advance time for a dead plant
	 * @param dt
	 * 		the given time
	 * @post	...
	 * 		|new.getHasBeenDeadFor()==dt+this.getHasBeenDeadFor()
	 * 		|if(this.getHasBeenDeadFor()>=0.6) then
	 * 		|	this.getWorld().addAsTerminatedObjects(this)
	 */
	public void advanceDeadTimer(double dt) {
		this.setHasBeenDeadFor(dt+this.getHasBeenDeadFor());
		if(this.getHasBeenDeadFor()>=0.6 && this.getWorld()!=null) {
			this.getWorld().addAsTerminatedObjects(this);
		}
	}
	
	/**
	 * Perform all the methods of a Plant
	 * @param dt
	 * 		the given time
	 * @effect	...
	 * 		|this.calculateTimeDirectionAndPosition(dt);
	 * 		|this.hasCollisionWithOtherGameObjects();
	 * 		|this.addSpecificValueToAllObject(-1);
	 * 		|this.removeAllObjectsWithValue(0);
	 * 		|this.timeRemoveContact(dt);
	 * 		|this.setLivingTime(this.getLivingTime()+dt);
	 * 		|if (this.getLivingTime()>=this.getMaxLivingTime()) then
	 * 		|	this.setHitpoints
	 */
	public void advanceTimeMethodsPlant(double dt) {
		this.calculateTimeDirectionAndPosition(dt);
		this.hasCollisionWithOtherGameObjects();
		this.addSpecificValueToAllObject(-1);
		this.removeAllObjectsWithValue(0);
		this.timeRemoveContact(dt);
		this.setLivingTime(this.getLivingTime()+dt);
		if (this.getLivingTime()>=this.getMaxLivingTime()) {
			this.setHitpoints(0);
		}
		terminateOutOfBorders();
		this.UseCorrectSprite();}
	
	
	/**
	 * Uses the correctsprite for this plant
	 * 
	 * @Post The plant wil use different sprites according to his orientation
	 * 		|if (this.getOrientation()>0)  then
	 * 		|	 new.getCurrentSprite == othersprite
	 * 		| else then
	 * 		| 	new.getCurrentSprite != othersprite
	 */
	public  void UseCorrectSprite() {
		if (this.getOrientation()>0) {
			this.setCurrentsprite(this.getSprites()[1]);
		}
		else {
			this.setCurrentsprite(this.getSprites()[0]);
		}
	}
	
	//##############################################################################
	/**
	 * function that deals with the collision with Mazub
	 * 
	 * @Post 
	 * 		| if(other instanceof Mazub) then
	 * 		|		if(! hasImpactWithGameObject(otherMazub)) then
	 * 		|			other.collisionWithPlant(this)
	 * 		|		else then
	 * 		|			this.changeImpactValue(otherMazub, 2);
	 */
	@Override
	public  void collidesWithOther(Object other) {
		if(other instanceof Mazub) {
			Mazub otherMazub= (Mazub) other;
			if(! hasImpactWithGameObject(otherMazub)) {
				otherMazub.collisionWithPlant(this);
			}
			this.changeImpactValue(otherMazub, 2);
			
		}
		
		
	}
	


	//##############################################################################
	/**
	 * retuns  the time in time intervals that the plant moves maximal 1 pixel
	 * @param dt
	 * 		the given time
	 * @return
	 * 		result==0.01/Math.sqrt(Math.pow(this.getHorizontalVelocity(), 2)+Math.pow(this.getVerticalVelocity(), 2))
	 */
	public double collisionTimer(double dt) {
		return (0.01/Math.sqrt(Math.pow(this.getHorizontalVelocity(), 2)+Math.pow(this.getVerticalVelocity(), 2)));
		
	}
	//##############################################################################
	
	
	/**
	 * Return the hasBeenDeadFor of this Plant.
	 */
	@Basic @Raw
	public double getHasBeenDeadFor() {
		return this.hasBeenDeadFor;
	}
	
	
	
	/**
	 * Set the hasBeenDeadFor of this Plant to the given hasBeenDeadFor.
	 * 
	 * @param  hasBeenDeadFor
	 *         The new hasBeenDeadFor for this Plant.
	 * @post   If the given hasBeenDeadFor is a valid hasBeenDeadFor for any Plant,
	 *         the hasBeenDeadFor of this new Plant is equal to the given
	 *         hasBeenDeadFor.
	 *       | if (isValidHasBeenDeadFor(hasBeenDeadFor))
	 *       |   then new.getHasBeenDeadFor() == hasBeenDeadFor
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
	 * Return the maxLivingTime of this Plant.
	 */
	@Basic @Raw @Immutable
	public double getMaxLivingTime() {
		return this.maxLivingTime;
	}
	/**
	 * Set the hasBeenDeadFor of this Plant to the given hasBeenDeadFor.
	 * 
	 * @param  maxLivingTime
	 *         The new maxLivingTime for this Plant.
	 * @post   the new maxLivingTime is equal to the given maxLivingTime
	 * 		| new.getMaxLivingTime= maxLivingTime
	 */
	@Raw
	public void setMaxLivingTime(double maxLivingTime) {
			this.maxLivingTime = maxLivingTime;
	}
	
	
	
	/**
	 * variable registering how long the plant can live
	 */
	public  double maxLivingTime ;
	
	
	
}