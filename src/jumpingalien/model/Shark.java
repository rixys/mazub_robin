package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

public class Shark extends JumpingObject implements horizontalMovingObject,verticalMovingObject {

	
	private static final double V_ACCELERATION_JUMPING = -10.0;
	private static final double HORIZONTAL_ACCELERATION = 1.5;
	
	
	
	public Shark(int pixelLeftX, int pixelBottomY, Sprite... sprites) throws IllegalArgumentException {
		this.setPixelPositionXas(pixelLeftX);
		this.setPixelPositionYas(pixelBottomY);
		this.setSprite(sprites);
		this.setCurrentSprite(sprites[0]);
		this.setHitpoints(100);
		this.setOrientation(-1);
		this.setHorizontalAcceleration(HORIZONTAL_ACCELERATION*this.getOrientation());
		this.setCurrentPeriod(2);
		this.setMaxTimePeriodSwitch(0);
		this.setV_VELOCITYJUMPING(new VerticalJumpingConstant(2));
		this.setJumping(false);
		this.sharkOutOfWater();
	}
	
	
	/**
	 * advance the time for this shark
	 *  @param dt
	 * 		The time that passed
	 * @effect checks if the Shark is in water or on top of the ground  then advance the sharks time with 
	 * active and passive periods
	 * 		|this.sharkOutOfWater()
	 * 		|this.advanceTimeWithPeriods(dt)
	 * @throws IllegalArgumentException
	 * 		|	Double.isNaN(dt)
	 */
	@Override
	public void advanceTime(double dt)throws IllegalArgumentException {
		if(!this.isTerminated()) {
			World world= this.getWorld();
			if (Double.isNaN(dt)) {
				throw new IllegalArgumentException();
			}
			this.sharkOutOfWater();
			this.advanceTimeWithPeriods(dt);
			
		}
		
	}
	/*
	 * @effect if the given Shark is a dead object then the deadtimer will be advanced
	 * 		|if(this.isDeadGameobject()) then
	 * 		| 	advanceDeadTimer(dt);
	 * @effect if the given shark is still alive then  living shark methods will be called
	 * 		|	this.advanceTimeLivingShark(dt, world);
*/
	//########################################################################################

	
	/**
	 * Advance time for a dead shark
	 * @param dt
	 * 		the given time
	 * @post	...
	 * 		|new.getHasBeenDeadFor()==dt+this.getHasBeenDeadFor()
	 * 		|if(this.getHasBeenDeadFor()>=0.6) then
	 * 		|	this.getWorld().addAsTerminatedObjects(this)
	 */
	public void advanceDeadTimer(double dt) {
		this.setHasBeenDeadFor(dt+this.getHasBeenDeadFor());
		if(this.getHasBeenDeadFor()>=0.6) {
			this.getWorld().addAsTerminatedObjects(this);
		}
	}
	


	/**
	 * handels all the methods for advance time of a shark
	 * @param dt
	 * 		the given time
	 * @effect	...
	 * 		|this.setCollisionWithWater(false);
	 * 		|this.sharkOutOfWater();
	 * 		|this.calculateCollisionWithImpactValue();
	 * 		|this.calculateDynamics(dt);
	 * 		|this.checkSharkInWater(dt);
	 * 		|this.timeRemoveContact(dt);
	 * 		|this.useCorrectSprite();
	 * 		|this.terminateOutOfBorders();
	 */
	public void methodsForAdvanceTime(double dt) {
		this.setCollisionWithWater(false);
		this.calculateCollisionWithImpactValue();
		this.calculateDynamics(dt);
		this.sharkOutOfWater();
		this.checkSharkInWater(dt);
		this.timeRemoveContact(dt);
		this.useCorrectSprite();
		this.terminateOutOfBorders();
	}
	//#########################################################################################
	
	/**
	 * Checks if the shark is in water otherwise if he is on land for too long he will dry out
	 * 
	 * @param dt
	 * 		the given time
	 * @effect	...
	 * 		|if(!this.getCollisionWithWater()) then
	 * 		|	this.sharkDryOut(dt)
	 * @effect	...
	 * 		|else then
	 * 		|	this.setHasBeenOnLandFor(0)
	 */
	public void checkSharkInWater(double dt) {
		if(!this.getCollisionWithWater()) {
			this.sharkDryOut(dt);
		}
		else {
			this.setHasBeenOnLandFor(0);
		}
	}
	
	/**
	 * Let the shark dry out for a given time, if he is out of water for too long
	 * he will take damage
	 * @param dt
	 * 		the given time
	 * @post	...
	 * 		new.getHasBeenOnlandFor()==dt+this.getHasBeenOnLandFor()
	 * @effect	...
	 * 		|while(getHasBeenOnLandFor()>=0.2)
	 * 		|	this.addHitpoints(-6)
	 * 		|	this.setHasBeenOnLandFor(this.getHasBeenOnLandFor()-0.2)
	 */
	public void sharkDryOut(double dt) {
		this.setHasBeenOnLandFor(dt+this.getHasBeenOnLandFor());
		while(getHasBeenOnLandFor()>=0.2) {
			this.addHitpoints(-6);
			this.setHasBeenOnLandFor(this.getHasBeenOnLandFor()-0.2);
		}
	}
	
	
	//#########################################################################################


	/**
	 * Return the hasBeenOnLandFor of this shark.
	 */
	@Basic @Raw
	public double getHasBeenOnLandFor() {
		return this.hasBeenOnLandFor;
	}
	
	/**
	 * Check whether the given hasBeenOnLandFor is a valid hasBeenOnLandFor for
	 * any shark.
	 *  
	 * @param  hasBeenOnLandFor
	 *         The hasBeenOnLandFor to check.
	 * @return 
	 *       | result == true
	*/
	public static boolean isValidHasBeenOnLandFor(double hasBeenOnLandFor) {
		return true;
	}
	
	/**
	 * Set the hasBeenOnLandFor of this shark to the given hasBeenOnLandFor.
	 * 
	 * @param  hasBeenOnLandFor
	 *         The new hasBeenOnLandFor for this shark.
	 * @post   If the given hasBeenOnLandFor is a valid hasBeenOnLandFor for any shark,
	 *         the hasBeenOnLandFor of this new shark is equal to the given
	 *         hasBeenOnLandFor.
	 *       | if (isValidHasBeenOnLandFor(hasBeenOnLandFor))
	 *       |   then new.getHasBeenOnLandFor() == hasBeenOnLandFor
	 */
	@Raw
	public void setHasBeenOnLandFor(double hasBeenOnLandFor) {
		if (isValidHasBeenOnLandFor(hasBeenOnLandFor))
			this.hasBeenOnLandFor = hasBeenOnLandFor;
	}
	
	/**
	 * Variable registering the hasBeenOnLandFor of this shark.
	 */
	private double hasBeenOnLandFor;

	//#########################################################################################
	/**
	 * Uses the correct sprite for this shark
	 * 
	 * @post ...
	 * 		|if(this.isDeadGameobject())
	 * 		|	new.getCurrentSprite()==this.getSprites()[0]
	 * 
	 * @post	...
	 * 		|if(this.getOrientation()==1)  then
	 * 		|	new.getCurrentSprite()==this.getSprites()[2]
	 * 		|else if(this.getOrientation()==-1) then
	 * 		|	new.getCurrentSprite()==this.getSprites()[1]
	 * 		|else  then
	 * 		|	new.getCurrentSprite()==this.getSprites()[0]
	 */		
	public void useCorrectSprite() {
		if(this.isDeadGameobject()) {
			this.setCurrentSprite(this.getSprites()[0]);
		}
		else {
			if(this.getOrientation()==1) {
				this.setCurrentSprite(this.getSprites()[2]);
			}
			else if(this.getOrientation()==-1) {
				this.setCurrentSprite(this.getSprites()[1]);
			}
			else {
				this.setCurrentSprite(this.getSprites()[0]);
			}
		}
	}
	//#########################################################################################
	
	/**
	 * Switch periods of the shark that he is active or not, if he is active he will start jumping
	 * if he changes from status he will stop jumping. The shark only moves while he is active.
	 * @param dt
	 * 		the time that passed
	 * @effect if the time exceeds the period switch timer, the shark will still advances time  with remainder
	 * of the previous period and then changes his period
	 * 		|while(this.getPeriodSwitchTimer()+time>=this.getMaxTimePeriodSwitch()) then
	 * 		|	this.advanceTimeShark(this.getMaxTimePeriodSwitch()-this.getPeriodSwitchTimer())
	 * 
	 * @effect the shark will changes his period from active to passive and the other way around if the
	 * time exceed the switch timer
	 * 		|while(this.getPeriodSwitchTimer()+time>=this.getMaxTimePeriodSwitch()) then
	 * 		|	this.endJump()
	 * 		|	if(this.getCurrentPeriod()==2) then
	 * 		|		new.getCurrentPeriod()==-1
	 * 		|	else then
	 * 		|		new.getCurrentPeriod() == this.getCurrentPeriod()+1
	 * 		|	this.periodAction()
	 * 		|	if (this.getCurrentPeriod()==-1 || this.getCurrentPeriod()==1) then
	 * 		|		startJump()
	 * 		|	new.getPeriodSwitchTimer==0
	 * 
	 * @effect the shark will advances his time and update the period switch timer
	 * 		|this.advanceTimeShark(time)
	 * 		|new.getPeriodSwitchTimer==this.getPeriodSwitchTimer()+time
	 * 
	 */
	public void advanceTimeWithPeriods(double dt) {
		double time=dt;
		while(this.getPeriodSwitchTimer()+time>=this.getMaxTimePeriodSwitch()) {
			this.endJump();
			//this.calculateDynamics(this.getMaxTimePeriodSwitch()-this.getPeriodSwitchTimer());
			this.advanceTimeShark(this.getMaxTimePeriodSwitch()-this.getPeriodSwitchTimer());
			time=time-(this.getMaxTimePeriodSwitch()-this.getPeriodSwitchTimer());
			if(this.getCurrentPeriod()==2) {
				this.setCurrentPeriod(-1);
			}
			else {
				this.setCurrentPeriod(this.getCurrentPeriod()+1);
			}
			this.periodAction();
			if (this.getCurrentPeriod()==-1 || this.getCurrentPeriod()==1) {
				startJump();
			}
			this.setPeriodSwitchTimer(0);
		}
		//this.calculateDynamics(time);
		this.advanceTimeShark(time);
		this.setPeriodSwitchTimer(this.getPeriodSwitchTimer()+time);
		
	}
	//#########################################################################################
	/**
	 * Advances the given time for this shark
	 * @param dt	
	 * 		the given time
	 * @effect cut the time in pieces so that the shark only moves 1 pixel at a time
	 * and do different effect for when he is alive or dead
	 *		|for(double i =collisionTimer;i<=dt; i+=collisionTimer ) 
	 *		|	this.methodsForAdvanceTime(collisionTimer);
	 *		|	remainder=remainder-collisionTimer;
	 *		|if (remainder>0) then
	 *		|	this.methodsForAdvanceTime(remainder)
	 *
	 */
	public void advanceTimeShark(double dt) {
		double collisionTimer= this.calculateCollisionTimer(dt);
		double remainder=dt;
		for(double i =collisionTimer;i<=dt&& ! (collisionTimer==0); i+=collisionTimer ) {
			if(this.isDeadGameobject()) {
				advanceDeadTimer(collisionTimer);
			}
			else {
				this.methodsForAdvanceTime(collisionTimer);
			}
			remainder=remainder-collisionTimer;
			collisionTimer= this.calculateCollisionTimer(remainder);
		}
		if (remainder>=0) {
			if(this.isDeadGameobject()) {
				advanceDeadTimer(remainder);
			}
			else {
				this.methodsForAdvanceTime(remainder);
			}
		}
	}
	
	
	/**
	 * calculates the dynamics of this shark
	 * @param dt
	 * 		the given time
	 * @effect	...
	 * 		|this.calculatePosition(dt)
	 * 		|this.calculateVelocity(dt)
	 */
	public void calculateDynamics(double dt) {
		this.calculatePosition(dt);
		this.calculateVelocity(dt);
	}
	
	/**
	 * Calculates the new position for the given shark
	 * @param dt
	 * 		the given time
	 * @post	...
	 * 		|new.getActualPositionX == Math.pow(dt,2 )*0.5*this.getHorizontalAcceleration()+this.getHorizontalVelocity()*dt+this.getActualPositionX()
	 * 		|new.getActualPositionY == Math.pow(dt,2 )*0.5*this.getVerticalAcceleration()+this.getVerticalVelocity()*dt+this.getActualPositionY()
	 */
	public void calculatePosition(double dt) {
		double newXposition= Math.pow(dt,2 )*0.5*this.getHorizontalAcceleration()+this.getHorizontalVelocity()*dt+this.getActualPositionX();
		this.setActualPositionX(newXposition);
		double newYPosition=Math.pow(dt,2 )*0.5*this.getVerticalAcceleration()+this.getVerticalVelocity()*dt+this.getActualPositionY();
		this.setActualPositionY(newYPosition);
	}
	
	/**
	 * Calculates the new velocity for this shark
	 * @param dt
	 * 		the given time
	 * @post	...
	 * 		|new.getHorizontalVelocity()==this.getHorizontalAcceleration()*dt+this.getHorizontalVelocity()
	 * 		|new.getVerticalVelocity()==this.getVerticalAcceleration()*dt+this.getVerticalVelocity()
	 */
	public void calculateVelocity(double dt) { 
		double newHorizontalVelocity=this.getHorizontalAcceleration()*dt+this.getHorizontalVelocity();
		this.setHorizontalVelocity(newHorizontalVelocity);
		double newVerticalVelocity =this.getVerticalAcceleration()*dt+this.getVerticalVelocity();
		this.setVerticalVelocity(newVerticalVelocity);
	}
	
	
	//#########################################################################################
	
	/**
	 * Handels the correct action of the period the shark is current in, -1 and 1 are active periods
	 * 0 and 2 are passive periods
	 * 	
	 * @post ...
	 * 		|case -1 then
	 * 		|	new.getOrientation()==-1
	 * 		|	new.getHorizontalAcceleration==HORIZONTAL_ACCELERATION*this.getOrientation()
	 * 		|	new.getMaxTimePeriodSwitch()==0.5
	 * 
	 * @post	...
	 * 		|case 0 then
	 * 		|	new.getOrientation==0
	 * 		|	new.getHorizontalAcceleration==0
	 * 		|	new.getHorizontalVelocity == 0
	 * 		|	new.getMaxTimePeriodSwitch == 1
	 * 
	 * @post ...
	 * 		|case 1 then
	 * 		|	new.getOrientation()==1
	 * 		|	new.getHorizontalAcceleration==HORIZONTAL_ACCELERATION*this.getOrientation()
	 * 		|	new.getMaxTimePeriodSwitch()==0.5
	 * 
	 * @post	...
	 * 		|case 2 then
	 * 		|	new.getOrientation==0
	 * 		|	new.getHorizontalAcceleration==0
	 * 		|	new.getHorizontalVelocity == 0
	 * 		|	new.getMaxTimePeriodSwitch == 1
	 * 
	 */
	public void periodAction() {
		int currentPeriod= this.getCurrentPeriod();
		switch (currentPeriod) {
		
		case -1: this.setOrientation(-1);
				this.setHorizontalAcceleration(HORIZONTAL_ACCELERATION*this.getOrientation());
				this.setMaxTimePeriodSwitch(0.5);
				break;
				
		case 0: this.setOrientation(0);
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(0);
				this.setMaxTimePeriodSwitch(1);
				break;
				
		case 1:	this.setOrientation(1);
				this.setHorizontalAcceleration(HORIZONTAL_ACCELERATION*this.getOrientation());
				this.setMaxTimePeriodSwitch(0.5);
				break;
			
		case 2:	this.setOrientation(0);
				this.setHorizontalAcceleration(0);
				this.setHorizontalVelocity(0);
				this.setMaxTimePeriodSwitch(1);
				break;
		
		}
		
	}
	//#########################################################################################
	/**
	 * returns the periodSwitchTimer of this shark
	 */
	public double getPeriodSwitchTimer() {
		return periodSwitchTimer;
	}
	
	/**
	 * Set the period switch timer to the given time
	 * @param time
	 * 		the given time
	 *  @post	...
	 *  	| new.getPeriodSwitchTimer()==time
	 */
	public void setPeriodSwitchTimer(double time) {
		this.periodSwitchTimer=time;
	}
	
	/**
	 * Variable registering the periodSwitchTimer of this shark
	 */
	private double periodSwitchTimer;
	//#########################################################################################
	/**
	 * Returns the maxTimePeriodSwitch of this shark
	 */
	public double getMaxTimePeriodSwitch() {
		return maxTimePeriodSwitch;
	}
	
	/**
	 * set the maxTimePeriodSwitch to the given time
	 * 
	 * @post ...
	 * 		| set the maxTimePeriodSwitch to the given time
	 */
	public void setMaxTimePeriodSwitch(double time) {
		this.maxTimePeriodSwitch=time;
	}
	
	/**
	 * Variable registering the maxTimePeriodSwitch for this shark
	 */
	private double maxTimePeriodSwitch;
	//#########################################################################################
	
	/**
	 * returns the currentPeriod for this shark
	 */
	public int getCurrentPeriod() {
		return this.currentPeriod;
	}
	
	/**
	 * checks if the given period is a valid period
	 * @param newPeriod
	 * 		the given period
	 * @return
	 * 		|result== newPeriod>=-1 && newPeriod<=2
	 */
	public static boolean isValidPeriod(int newPeriod) {
		return newPeriod>=-1 && newPeriod<=2;
	}
	
	/**
	 * Sets the newPeriod to the given Period
	 * @param newPeriod
	 * 		the given period
	 * @post if the given period is valid , the new currentPeriod is equal to the given
	 * period otherwise it becomes -1
	 * 		|if(isValidPeriod(newPeriod)) then
	 * 		|	new.getCurrentPeriod()==newPeriod
	 * 		|else then
	 * 		|	new.getCurrentPeriod()==-1
	 */
	public void setCurrentPeriod(int newPeriod) {
		if(isValidPeriod(newPeriod)) {
			this.currentPeriod=newPeriod;
		}
		else {
			this.currentPeriod=-1;
		}
	}
	
	/**
	 * variable registering the currentPeriod of this shark
	 */
	private int currentPeriod;
	//#########################################################################################
	/**
	 This function divides the time pieces 
	 * so that in every time interval the Shark only moves 1 pixel
	 * @param dt
	 * @effect this function gets a more precise timer than the one used normally.
	 * 		|if(this.getHorizontalVelocity()==0 && this.getVerticalVelocity()==0&& this.getHorizontalAcceleration()==0&& this.getVerticalAcceleration()==0) 
	 * @return it returns the timer if this is necessary
	 * 		|if(this.getHorizontalVelocity()==0 && this.getVerticalVelocity()==0&& this.getHorizontalAcceleration()==0&& this.getVerticalAcceleration()==0) == true
	 * 		|	return dt
	 * 		|else
	 * 		|	return getTimeDiference(dt)
	 */
	
	public double calculateCollisionTimer(double dt) {
		if((this.getHorizontalVelocity()==0 && this.getVerticalVelocity()==0&& this.getHorizontalAcceleration()==0&& this.getVerticalAcceleration()==0)||dt==0) {
			 return dt;
		}
		else {
			 return getTimeDiference(dt);
		}
	}
	
	/**
	  * this is a function that divides the time that has been given into smaller parts so it's more accurate
	 * @param dt
	 * 		the givenTime
	 * @post this is a function that divides the time that has been given into smaller parts so it's more accurate
	 * 		|double collisionTimer= 0.01/
				(Math.sqrt(Math.pow(this.getHorizontalVelocity(), 2)+Math.pow(this.getVerticalVelocity(), 2))
				+(Math.sqrt(Math.pow(getHorizontalAcceleration(),2)+Math.pow(getVerticalAcceleration(),2) ))*dt)
	 * @return collisiontimer
	 */
	
	public double getTimeDiference(double dt) {
		double collisionTimer= 0.01/
				(Math.sqrt(Math.pow(this.getHorizontalVelocity(), 2)+Math.pow(this.getVerticalVelocity(), 2))
				+(Math.sqrt(Math.pow(getHorizontalAcceleration(),2)+Math.pow(getVerticalAcceleration(),2) ))*dt);
		return collisionTimer;
	}
	
	
	//#########################################################################################
	
	/**
	 * Check whether the given sprites are valid for any Shark
	 *  
	 * @param  sprites
	 *         The new Sprites to check.
	 * @return returns true if the total number of sprites is  equal to 3
	 * 		|result== (sprites.length==3)
	 */
	@Override
	public boolean isValidSprites(Sprite[] sprites) {
		if(super.isValidSprites(sprites)) {
			return (sprites.length==3 );
		}
		return false;
	}
	

	//#########################################################################################
	/**
	 * Returns the current Sprite of this Shark
	 */
	@Override
	public Sprite getCurrentSprite() throws ModelException {
		return this.currentSprite;
	}
	
	/**
	 * Sets the current sprite to the given sprite of this shark
	 * @param sprite
	 * 		the given sprite
	 * 	@post	...
	 * 		| new.getCurrentSprite()==sprite
	 */
	public void setCurrentSprite(Sprite sprite) {
		this.currentSprite=sprite;
	}

	/**
	 * Variable registering the currentSprite
	 */
	private Sprite currentSprite;
	//#########################################################################################
	/**
	 * return the Xposition of this shark
	 */
	@Override 
	@Basic @Raw
	public int getPixelPositionXas() {
		return actualToPixel(this.getActualPositionX());
	}
	/**
	* Set the Xposition of this Shark to the given Xposition.
	* 
	* @param  newXposition
	*         The new Xposition for this Shark.
	* @post   The Xposition of this new Shark is equal to
	*         the given Xposition.
	*       | new.getPixelPositionXas() == newXposition
	*/
	@Override
	@Raw
	public void setPixelPositionXas(int xPosition)  {
		this.setActualPositionX(pixelToActual(xPosition));
	}
	
//#########################################################################################
	/**
	 * Return the pixelPositionYas of this Shark.
	 */
	@Override
	@Basic @Raw
	public int getPixelPositionYas() {
		return actualToPixel(this.getActualPositionY());
	}
	
	/**
	* Set the pixelPositionYas of this Shark to the given pixelPositionYas.
	* 
	* @param  newYposition
	*         The new pixelPositionYas for this Shark
	* @post   The pixelPositionYas of this new Shark is equal to
	*         the given pixelPositionYas.
	*       | new.getpixelPositionYas() == newYposition
	*/
	@Override
	@Raw
	public void setPixelPositionYas(int yPosition) {
		this.setActualPositionY(pixelToActual(yPosition));
	}

	//#########################################################################################

	/**
	 * checks if this orientation is a valid orientation for this shark
	 * @param orientation
	 * 		the given orientation
	 * @return
	 * 		result==orientation>=-1 && orientation<=1
	 */
	public static boolean isValidOrientation(int orientation) {
		return orientation>=-1 && orientation<=1;
	}
	
	/**
	 * Sets the orientation to the given orientation
	 * 
	 * @param newOrientation
	 * 		the given Orientation
	 * @post	...
	 * 		|if(isValidOrientation(newOrientation)) then
	 * 		|	this.orientation=newOrientation
	 */
	public void setOrientation(int newOrientation) {
		if(isValidOrientation(newOrientation)) {
			this.orientation=newOrientation;
		}
	}
	
	
	//#########################################################################################
	/**
	 * Return the velocity of this shark
	 * 
	 * @return
	 * 		|result == new double[] {this.getHorizontalVelocity(),this.getVerticalVelocity()}
	 */
	@Override
	public double[] getVelocity() {
		return new double[] {this.getHorizontalVelocity(),this.getVerticalVelocity()};
	}



	//#########################################################################################
	
	
	@Override
	public double getVerticalVelocity() {
		return verticalVelocity;
	}

	@Override
	public void setVerticalVelocity(double newVerticalVelocity) {
		this.verticalVelocity= newVerticalVelocity;
	}
	
	private double verticalVelocity;
	

	//#########################################################################################
	@Override
	public double getHorizontalVelocity() {
		return horizontalVelocity;
	}

	@Override
	public void setHorizontalVelocity(double newHorizontalVelocity) {
		this.horizontalVelocity=newHorizontalVelocity;
	}
	
	private double horizontalVelocity;
	//#########################################################################################
	
	/**
	 * start the jump of this shark
	 * 
	 * @post if the shark is on the top of a tile or in water  he will jump
	 * 		|if(this.getCollisionWithWater()|| hasCollisionWithTopTilesForAShark(this.getRectanglePosition(), this.getWorld(), 1)) then
	 * 		|	new.getVerticalVelocity()==this.V_VELOCITY_JUMPING.getjumpingSpeed()
	 * 		|	new.getJumping()==true
	 */
	@Override
	public void startJump() throws IllegalStateException {
		if(this.getCollisionWithWater()|| hasCollisionWithTopTilesForAShark(this.getRectanglePosition(), this.getWorld(), 1)) {
			this.setVerticalVelocity(this.V_VELOCITY_JUMPING.getjumpingSpeed());
			this.setVerticalAcceleration(V_ACCELERATION_JUMPING);
			this.setJumping(true);
		}

	}


	/**
	 * ends the jump of this shark
	 * @post	...
	 * 		| if (this.verticalVelocity>0)
	 * 		|		new.getVerticalVelocity==0
	 * 		| new.getJumping==false
	 */
	@Override
	public void endJump() throws IllegalStateException {
		if(this.verticalVelocity>0) {
			this.setVerticalVelocity(0);
		}
		this.setJumping(false);
	}

	/**
	 * Set the jumping of this Object to the given jumping.
	 * 
	 * @param  newJumping
	 *         The new jumping for this Object.
	 * @post   The jumping of this new Object is equal to
	 *         the given jumping.
	 *       | new.getJumping() == newJumping
	 * @throws IllegalArgumentException
	 *         The given jumping is not a valid jumping for any
	 *         Object.
	 *       | ! isValidJumping(getJumping())
	 */
	@Raw
	@Override
	public void setJumping(boolean newJumping) 
			throws IllegalArgumentException {
		this.jumping = newJumping;
	}
	//#########################################################################################

	/**
	 * if the shark has collision with water, set the collision with water to true
	 */
	@Override
	protected void collisionWithWater(int[] rectanglePositionThisObject, int[] rectanglePositionTiles) {
		this.setCollisionWithWater(true);
	}
	//#########################################################################################
	//This function provides the impact effects en set the value back to 2
	// if the value will be lowered a second time it has no collision anymore
	/**
	 * provides the extra effect with collision of a shark
	 * @effect if the other gameobject is a Mazub and both the Mazub and Shark are still alive
	 * the Shark loses 50 hitpoints.If the shark has not yet impact with the given object.	
	 * 		|if(gameObject instanceof Mazub) then
	 * 		|	if(mazub.getHitPoints()>0 &&this.getHitPoints()>0)then
	 * 		|		this.addHitpoints(-50)
	 * 
	  * @effect if the other gameobject is a Slime and both the Slime and Shark are still alive
	 * the Shark gain 10 hitpoints.If the shark has not yet impact with the given object.	
	 * 		|if(blockedObject instanceof Slime) then
	 * 		|	if(slime.getHitPoints()>0 &&this.getHitPoints()>0)then
	 * 		|		this.addHitpoints(10)
	 * 
	 */
	@Override
	protected void collisionExtraEffect(Object other) {
		if(other instanceof BlockedObject) {
			BlockedObject blockedObject= (BlockedObject) other;
			if(! hasImpactWithGameObject(blockedObject)) {
					if(blockedObject instanceof Mazub) {
						Mazub mazub = (Mazub) blockedObject;
						if(mazub.getHitPoints()>0 &&this.getHitPoints()>0) {
							this.addHitpoints(-50);
						}
					}
				if(blockedObject instanceof Slime) {
					Slime slime = (Slime) blockedObject;
					if(slime.getHitPoints()>0&& this.getHitPoints()>0) {
						this.addHitpoints(10);
					}
				}
				this.changeImpactValue(blockedObject, 2);
			}
			else {
				this.changeImpactValue(blockedObject, 2);
			}
		}
	}


	
	//#########################################################################################
	
	
	/**
	 * Return the collisionWithWater of this Shark.
	 */
	@Basic @Raw
	public boolean getCollisionWithWater() {
		return this.hasCollisionWithWater;
	}
	
	/**
	 * Check whether the given collisionWithWater is a valid collisionWithWater for
	 * any Shark.
	 *  
	 * @param  hasCollisionWithWater
	 *         The collisionWithWater to check.
	 * @return 
	 *       | result == true
	*/
	public static boolean isValidCollisionWithWater(boolean hasCollisionWithWater) {
		return true;
	}
	
	/**
	 * Set the collisionWithWater of this Shark to the given collisionWithWater.
	 * 
	 * @param  hasCollisionWithWater
	 *         The new collisionWithWater for this Shark.
	 * @post   If the given collisionWithWater is a valid collisionWithWater for any Shark,
	 *         the collisionWithWater of this new Shark is equal to the given
	 *         collisionWithWater.
	 *       | if (isValidCollisionWithWater(hasCollisionWithWater))
	 *       |   then new.getCollisionWithWater() == hasCollisionWithWater
	 */
	@Raw
	public void setCollisionWithWater(boolean hasCollisionWithWater) {
		if (isValidCollisionWithWater(hasCollisionWithWater))
			this.hasCollisionWithWater = hasCollisionWithWater;
	}
	
	/**
	 * Variable registering the collisionWithWater of this Shark.
	 */
	private boolean hasCollisionWithWater;

	//#########################################################################################
	
	/**
	 * Checks if the shark his top has collision with water, otherwise the shark will fall
	 * 
	 * @Post	...
	 * 		|if(this.topWithTopCollisionofTiles(getRectanglePosition(), getWorld(), 2)) then
	 * 		|	new.getVerticalAcceleration()==0
	 * 		|	if(this.getVerticalVelocity()<0) then
	 * 		|		new.getVerticalVerlocity()==0
	 * 		|else then
	 * 		|	new.getVerticalAcceleration()==V_ACCELERATION_JUMPING
	 * 		|
	 */
	public void sharkOutOfWater() {

		if(this.topWithTopCollisionofTiles(getRectanglePosition(), getWorld(), 2)) {//&&! getJumping()) { hiermee werkt de test wel
			if(this.getVerticalVelocity()<0) {
				this.setVerticalVelocity(0);
			}
			this.setVerticalAcceleration(0);
		}
		else {
			this.setVerticalAcceleration(V_ACCELERATION_JUMPING);
			
		}
		
		
	}
	
	//#########################################################################################

	/**
	 * Handels the collision with impassble terrain for a shark
	 */
	@Override
	public void collisionWithImpassableTerrain(int[]rectanglePosition1, int[] rectanglePosition2, int tiles) {
		
		if (rectanglePosition2[1]+rectanglePosition2[3]-tiles==rectanglePosition1[1] 
				&& rectanglePosition2[0]+rectanglePosition2[2]>rectanglePosition1[0]
						&& rectanglePosition2[0]<rectanglePosition1[0]+rectanglePosition1[2])  {
			if (this.getVerticalVelocity()<0) {
				this.setVerticalVelocity(0);
			}
			if(this.getVerticalAcceleration()<0 ) {
				this.setVerticalAcceleration(0);
			}
		}
		else if (rectanglePosition1[1]+rectanglePosition1[3]==rectanglePosition2[1]
				&& rectanglePosition2[0]+rectanglePosition2[2]>rectanglePosition1[0]
						&& rectanglePosition2[0]<rectanglePosition1[0]+rectanglePosition1[2]) 
		{
			if (this.getVerticalVelocity()>0) {
				this.setVerticalVelocity(0);
			}
		}
		else if(rectanglePosition2[0]+rectanglePosition2[2]==rectanglePosition1[0]
				&& rectanglePosition2[1]+rectanglePosition2[3]-tiles>rectanglePosition1[1]){
			if (this.getOrientation()==-1) {
				this.setHorizontalVelocity(0);
				this.setHorizontalAcceleration(0);

			}
		}
		else if (rectanglePosition2[0]==rectanglePosition1[0]+rectanglePosition1[2]
				&&rectanglePosition2[1]+rectanglePosition2[3]-tiles>rectanglePosition1[1] ) {
			if (this.getOrientation()==1) {
				this.setHorizontalVelocity(0);
				this.setHorizontalAcceleration(0);

			}
		}
	}

	
	//#########################################################################################
/**
 * Return the V_VELOCITY_JUMPING of this JumpingObject.
 */
@Basic @Raw
public VerticalJumpingConstant getV_VELOCITYJUMPING() {
	return this.V_VELOCITY_JUMPING;
}



/**
 * Set the V_VELOCITY_JUMPING of this JumpingObject to the given V_VELOCITY_JUMPING.
 * 
 * @param  jumpingSpeed
 *         The new V_VELOCITY_JUMPING for this JumpingObject.
 * @post   The V_VELOCITY_JUMPING of this new JumpingObject is equal to the given
 *         V_VELOCITY_JUMPING.
 *       |   then new.getV_VELOCITYJUMPING() == jumpingSpeed
 */
@Raw
public void setV_VELOCITYJUMPING(VerticalJumpingConstant jumpingSpeed) {
	this.V_VELOCITY_JUMPING = jumpingSpeed;
}

/**
 * Variable registering the V_VELOCITY_JUMPING of this JumpingObject.
 */
private VerticalJumpingConstant V_VELOCITY_JUMPING;
	//#########################################################################################
}
