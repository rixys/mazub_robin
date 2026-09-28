package jumpingalien.model;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;
import jdk.jshell.spi.SPIResolutionException;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

public class Slime extends BlockedObject implements  horizontalMovingObject,Comparable<Slime> {
	
	/**
	 * 
	 * @param id
	 * @param positionX
	 * @param positionY
	 * @param school
	 * @param inSprites
	 * @post this function creates a slime
	 * 		|this.new.getPixelPositionXas == positionX 
	 * 		|this.new.getPixelPositionYas == positionY
	 *		|this.new.getActualPositionX == pixelToActual(positionX)
	 *		|this.new.getActualPositionY == pixelToActual(positionY)
	 *		|this.constantHorizontalAcceleration=0.7
	 *		|this.new.getHorizontalAcceleration == this.getConstantHorizontalAcceleration()
	 *		|this.new.getSprite== inSprites
	 *		|this.new.getCurrentSprite == this.getSprites()[0]
	 *		|this.new.getHitpoints == 100
	 *		|int intID= (int) id
	 *		|this.ID= intID
	 *		|addSlimeID(intID)
	 *		|if (school != null) 
	 *		|school.addAsSlime(this)
	 *		|this.new.getSchool == school
	 *		|this.new.getOrientation == 1
	 * @throws IllegalArgumentException
	 * 		|if (Slime.slimesInTheSchoolID.contains(intID) || intID<0)
	 */
	
	public Slime(long id,int positionX, int positionY,School school, Sprite[] inSprites)throws IllegalArgumentException {
		this.setPixelPositionXas(positionX);
		this.setPixelPositionYas(positionY);
		this.setActualPositionX(pixelToActual(positionX));
		this.setActualPositionY(pixelToActual(positionY));
		this.constantHorizontalAcceleration=0.7;
		this.setHorizontalAcceleration(this.getConstantHorizontalAcceleration());
		this.setSprite(inSprites);
		this.setCurrentSprite(this.getSprites()[0]);
		this.setHitpoints(100);
		int intID= (int) id;
		if (Slime.slimesInTheSchoolID.contains(intID) || intID<0) {
			throw new IllegalArgumentException();
		}
		this.ID= intID;
		addSlimeID(intID);
		if (school != null) {
			school.addAsSlime(this);
		}
		this.setSchool(school);
		this.setOrientation(1);
		//int amountOfSlimes = school.slimesInTheSchoolID.size();
		//int teller = 1;
		//while (teller< amountOfSlimes) {
			//if (school.slimesInTheSchoolID.get(teller) == id ) {
				//throw new ModelException("not an unique Slime ID");
			//}
		//}
	}
	
	//##############################################################################
	/**
	 * 
	 * @return this returns the ID of the slime
	 */
	@Basic @Raw @Immutable
	public int getID() {
		return ID;
	}
	/**
	 * 
	 * @param ID
	 * @return this returns whether or not the given ID is a valid ID
	 */
	public boolean isValidID(int ID) {
		return true;
	}

	/**
	 * variable containing the ID of this slime
	 */
	private final int ID;
	
	

	//##############################################################################
	
	
	/**
	 * 
	 * @return this returns the amount of slimes that are in the same school as this slime
	 */
	public int getSizeOfTheID() {
		return slimesInTheSchoolID.size();
	}
	/**
	 * this is a collection of all the ID's in the current school
	 */
	public static ArrayList<Integer> slimesInTheSchoolID = new ArrayList<Integer>();
	/**
	 * @post this function removes all the ID's from this school
	 * 		|slimesInTheSchoolID.clear()
	 */
	public static void clearAllSlimesIDS() {
		slimesInTheSchoolID.clear();
	}
	/**
	 * 
	 * @param id
	 * @post this function adds an ID to the collection of ID's of the current school
	 */
	public static void addSlimeID(int id) {
		slimesInTheSchoolID.add(id);
	}
	//##############################################################################
	/**
	 * 
	 * @return this returns the school of this slime
	 */
	public School getSchool() {
		return schoolOfThisSlime;
	}
	/**
	 * 
	 * @param schoolOfSlime
	 * @post this function sets the school of the current slime
	 * 		|new.getschool == schoolOfSlime
	 */
	public void setSchool(School schoolOfSlime) {
		schoolOfThisSlime = schoolOfSlime;
	}
	/**
	 * variable containing the school of this slime
	 */
	private School schoolOfThisSlime;
	/**
	 * 
	 * @param school
	 * @post this function changes the school of the current slime
	 * 		|this.slimeMovingOutOfSchool();
	 *		|this.new.getSchool == school
	 *		|this.slimeEnteringSchool();
	 * @throws IllegalStateException
	 * 		|if (this.getSchool()==null || school==null || school.isTerminated() )
	 */
	public void switchSchool(School school)throws IllegalStateException {
		if (this.getSchool()==null || school==null || school.isTerminated() ) {
			throw new IllegalStateException("invalid school to switch");
		}
		this.slimeMovingOutOfSchool();
		this.setSchool(school);
		this.slimeEnteringSchool();
	}
	/**
	 * @post this function does what happens when the current slime gets removed from the current school
	 * 		|this.addHitpoints(this.getSchool().groupGiveHitpoints(1, this));
	 *		|this.getSchool().removeAsSlime(this);
	 */
	public void slimeMovingOutOfSchool() {
		this.addHitpoints(this.getSchool().groupGiveHitpoints(1, this));
		this.getSchool().removeAsSlime(this);
	}
	/**
	 * @post this function does what happens when the current slime gets added to a new school
	 * 		|this.addHitpoints(this.getSchool().groupGiveHitpoints(-1, this));
	 *		|this.getSchool().removeAsSlime(this);
	 */
	public void slimeEnteringSchool() {
		this.addHitpoints(this.getSchool().groupGiveHitpoints(-1, this));
		this.getSchool().addAsSlime(this);
	}
	
	/**
	 * @return this returns the horizontal velocity of the current slime
	 */
	
	@Override
	public double getHorizontalVelocity() {
		return this.horizontalVelocityOfTheSlime;
	}
	/**
	 * 
	 * @param horizontalVelocity
	 * @return this returns a valid horizontal velocity
	 */
	public boolean isValidHorizontalVelocity(double horizontalVelocity) {
		return horizontalVelocity>=-2.5 &&horizontalVelocity<=2.5;
	}
	/**
	 * @param horizontalVelocity
	 * @post this function sets the current horizontal velocity
	 * 		|if(isValidHorizontalVelocity(horizontalVelocity)) 
	 *		|horizontalVelocityOfTheSlime = horizontalVelocity
	 *		|else
	 *		|if (horizontalVelocity>2.5) 
	 *		|this.new.getHorizontalVelocity ==2.5
	 *		|else 
	 *		|this.new.getHorizontalVelocity == -2.5
	 */
	@Override
	public void setHorizontalVelocity(double horizontalVelocity) {
		if(isValidHorizontalVelocity(horizontalVelocity)) {
			horizontalVelocityOfTheSlime = horizontalVelocity;
		}
		else {
			if (horizontalVelocity>2.5) {
				this.setHorizontalVelocity(2.5);
			}
			else {
				this.setHorizontalVelocity(-2.5);
			}
		}
	}
	/**
	 * variable containing the horizontal velocity of the slime
	 */
	private double horizontalVelocityOfTheSlime = 0;


	/**
	 * advance the time for this slime
	 * @param dt
	 * 
	 * @effect this checks whether the slime is dead or alive and then executes other function based on that.
	 * 			|if(!this.isTerminated()) 
	 *			|World world= this.getWorld()
	 *			|if(this.isDeadGameobject()) 
	 *			|advanceDeadTimer(dt)
	 *			|else 
	 *			|this.advanceTimeLivingSlime(dt, world)
	 * @throws IllegalArgumentException
	 * 			|if (Double.isNaN(dt))
	 */
	@Override
	public void advanceTime(double dt)throws IllegalArgumentException {
		if(!this.isTerminated()) {
			World world= this.getWorld();
			if (Double.isNaN(dt)) {
				throw new IllegalArgumentException();
			}
			if(this.isDeadGameobject()) {
				advanceDeadTimer(dt);
			}
			else {
				this.advanceTimeLivingSlime(dt, world);
			}	
			
		}
		
	}
	/**
	 * 
	 * @param dt
	 * 
	 * @post this function terminates the slime if he is dead for more than 0.6 seconds.
	 * 		|this.setHasBeenDeadFor(dt+this.getHasBeenDeadFor())
	 *		|if(this.getHasBeenDeadFor()>=0.6) 
	 *		|this.getWorld().addAsTerminatedObjects(this)
	 */
	public void advanceDeadTimer(double dt) {
		this.setHasBeenDeadFor(dt+this.getHasBeenDeadFor());
		if(this.getHasBeenDeadFor()>=0.6) {
			this.getWorld().addAsTerminatedObjects(this);
		}
	}
	/**
	 * 
	 * @param dt
	 * @param world
	 * @post this function advances all the methods for a living slime
	 * 		|double remainder=dt
	 *		|double collisionTimer
	 *		|this.new.getHorizontalAcceleration == this.getConstantHorizontalAcceleration()*this.getOrientation()
	 *		|this.resetTakeDamageFromGeologicalFeature()
	 *		|this.calculateCollisionWithImpactValue()
	 *		|collisionTimer= this.calculateCollisionTimer(dt)
	 *		|for(double i =collisionTimer;i<=dt&& ! (collisionTimer==0); i+=collisionTimer ) 
	 *		|this.methodsForAdvanceTime(collisionTimer)
	 *		|remainder=remainder-collisionTimer
	 *		|collisionTimer= this.calculateCollisionTimer(remainder)
	 *	 	|if (remainder>0) 
	 *		|this.methodsForAdvanceTime(remainder)
	 */
	public void advanceTimeLivingSlime(double dt, World world) {
		double remainder=dt; 
		double collisionTimer;
		this.setHorizontalAcceleration(this.getConstantHorizontalAcceleration()*this.getOrientation());
		this.resetTakeDamageFromGeologicalFeature();
		this.calculateCollisionWithImpactValue();
		collisionTimer= this.calculateCollisionTimer(dt);
		for(double i =collisionTimer;i<=dt&& ! (collisionTimer==0); i+=collisionTimer ) {
			this.methodsForAdvanceTime(collisionTimer);
			remainder=remainder-collisionTimer;
			collisionTimer= this.calculateCollisionTimer(remainder);
		}
		if (remainder>0) {
			this.methodsForAdvanceTime(remainder);
		}
	}
	/**
	 * 
	 * @param dt
	 * 
	 * @effect this function handles all the methods for a living slime
	 * 		|this.advanceDynamics(dt)
	 *		|this.resetTakeDamageFromGeologicalFeature()
	 *		|this.setHorizontalAcceleration(this.getConstantHorizontalAcceleration()*this.getOrientation())
	 *		|this.calculateCollisionWithImpactValue()
	 *		|this.geologicalFeatureDamageTimer(dt)
	 *		|this.timeRemoveContact(dt)
	 *		|this.useCorrectSprite()
	 *		|this.terminateOutOfBorders()
	 */
	public void methodsForAdvanceTime(double dt) {
		this.advanceDynamics(dt);
		this.resetTakeDamageFromGeologicalFeature();
		this.setHorizontalAcceleration(this.getConstantHorizontalAcceleration()*this.getOrientation());
		this.calculateCollisionWithImpactValue();
		this.geologicalFeatureDamageTimer(dt);
		this.timeRemoveContact(dt);
		this.useCorrectSprite();
		this.terminateOutOfBorders();
	}
	/**
	 * 
	 * @param dt
	 * @effect this function gets a more precise timer than the one used normally.
	 * 		|if(this.getHorizontalVelocity()==0 && this.getVerticalVelocity()==0&& this.getHorizontalAcceleration()==0&& this.getVerticalAcceleration()==0) 
	 * @return it returns the timer if this is necessary
	 * 		|if(this.getHorizontalVelocity()==0 && this.getVerticalVelocity()==0&& this.getHorizontalAcceleration()==0&& this.getVerticalAcceleration()==0) == true
	 * 		|return dt
	 * 		|else
	 * 		|return getTimeDiference(dt)
	 */
	
	public double calculateCollisionTimer(double dt) {
		if(this.getHorizontalVelocity()==0 && this.getHorizontalAcceleration()==0) {
			 return dt;
		}
		else {
			 return getTimeDiference(dt);
		}
	}
	/**
	 * 
	 * @param dt
	 * @return this function returns the collisiontimer.
	 * 		|double collisionTimer= 0.01/
	 *			(Math.sqrt(Math.pow(this.getHorizontalVelocity(), 2))
	 *			+(Math.sqrt(Math.pow(getHorizontalAcceleration(),2)))*dt);
	 *		|return collisionTimer;
	 */
	public double getTimeDiference(double dt) {
		double collisionTimer= 0.01/
				(Math.sqrt(Math.pow(this.getHorizontalVelocity(), 2))
				+(Math.sqrt(Math.pow(getHorizontalAcceleration(),2)))*dt);
		return collisionTimer;
	}
	/**
	 * 
	 * @param dt
	 * @post this function recalculates the position and the velocity of this slime
	 * 		|this.advanceVelocityAndPosition(dt);
	 */
	public void advanceDynamics(double dt) {
		//if (checkIfTheSlimeIsAtALedge() == true) {
		//	setOrientation(getOrientation()*-1);
		//	setHorizontalAcceleration(getHorizontalAcceleration()*getOrientation());
		//	setHorizontalVelocity(0);
		//}
		//this.setHorizontalAcceleration(this.getHorizontalAcceleration()*this.getOrientation());
		this.advanceVelocityAndPosition(dt);
//		if (getOrientation() == -1 ) {
//			setCurrentSprite(getSprites()[1]);
//			if (getHorizontalVelocity() > -2.5 && getCollisionWithWall()== false) { 
//			//setHorizontalVelocity(getHorizontalVelocity() - getHorizontalAcceleration()*dt);
//				AdvanceVelocityAndPosition(dt);
//			if (getHorizontalVelocity() < -2.5) {
//				setHorizontalVelocity(-2.5);
//			}
//			}
//		} else if (getOrientation() == 1 ) {
//			setCurrentSprite(getSprites()[0]);
//			if (getHorizontalVelocity() < 2.5 && getCollisionWithWall()== false) {
//			//setHorizontalVelocity(getHorizontalVelocity() + getHorizontalAcceleration()*dt);
//				AdvanceVelocityAndPosition(dt);
//			if (getHorizontalVelocity() > 2.5) {
//				setHorizontalVelocity(2.5);
//			}
//			}
//		}
	}
	/**
	 * @post this function makes sure the slimes uses the correct sprite.
	 * 		|if (this.getOrientation() <0 ) {
	 *		|this.new.getCurrentSprite == this.getSprites()[1]
	 *		|else 
	 *		|this.new.GetCurrentSprite==this.getSprites()[0]
	 */
	public void useCorrectSprite() {
		if (this.getOrientation() <0 ) {
			this.setCurrentSprite(this.getSprites()[1]);
		}
		else {
			this.setCurrentSprite(this.getSprites()[0]);
		}
	}

	//public boolean checkIfTheSlimeIsAtALedge() {
	//	int checkerOfEdge = 0;
	//	if (getOrientation() == 1) {
	//	checkerOfEdge = getPixelPositionXas()+getCurrentSprite().getWidth()+1;
	//	} else {
	//	checkerOfEdge = getPixelPositionXas()-getCurrentSprite().getWidth()-1;
	//	}
	//	if (collisionWithEdge(checkerOfEdge) == true) {
	//		return true;
	//	} else {
	//		return false;
	//	}
	//}
	
	//public boolean collisionWithEdge(int checker) {
	//	int[] rectanglePosition = {checker,pixelPositionYas,0,0};
	//	if (hasCollisionWithTopTiles(rectanglePosition, this.getWorld(), 0) == true) {
	//		return true;
	//	} else {
	//		return false;
	//	}
		
	//}
	/**
	 * 
	 * @param dt
	 * @post this function recalculates the position and velocity of this slime
	 * 		|this.advancePosition(dt)
	 *		|this.advanceVelocity(dt)
	 */
	public void advanceVelocityAndPosition(double dt) {
			this.advancePosition(dt);
			this.advanceVelocity(dt);
	}
	/**
	 * 
	 * @param dt
	 * @post this function recalculates the position of this slime
	 * 		|double newXposition= Math.pow(dt,2 )*0.5*this.getHorizontalAcceleration()+this.getHorizontalVelocity()*dt+this.getActualPositionX();
	 *		|this.new.getActualPositionX(newXposition)
	 * 		|this.new.getPixelPositionXas(actualToPixel(getActualPositionX()))
	 */
	public void advancePosition(double dt) {
		double newXposition= Math.pow(dt,2 )*0.5*this.getHorizontalAcceleration()+this.getHorizontalVelocity()*dt+this.getActualPositionX();
		this.setActualPositionX(newXposition);
		this.setPixelPositionXas(actualToPixel(getActualPositionX()));
	}
	/**
	 * 
	 * @param dt
	 * @post this function recalculates the velocity of this slime.
	 * 		|double newHorizontalVelocity=this.getHorizontalAcceleration()*dt+this.getHorizontalVelocity();
	 *		|this.new.getHorizontalVelocity(newHorizontalVelocity)
	 */
	public void advanceVelocity(double dt) {
		double newHorizontalVelocity=this.getHorizontalAcceleration()*dt+this.getHorizontalVelocity();
		this.setHorizontalVelocity(newHorizontalVelocity);
	}
	/**
	 * @return this returns the current sprite of the slime
	 */
	@Override
	public Sprite getCurrentSprite() throws ModelException {
		return currentSprite;
	}

	//##############################################################################

	
	/**
	 * Return the Xposition of this Slime.
	 */
	@Override
	@Basic @Raw
	public int getPixelPositionXas() {
		return this.pixelPositionXas;
	}

	
	/**
	* Set the Xposition of this Slime to the given Xposition.
	* 
	* @param  newXposition
	*         The new Xposition for this Slime.
	* @post   The Xposition of this new Slime is equal to
	*         the given Xposition.
	*       | new.getPixelPositionXas() == newXposition
	*/
	@Override @Raw
	public void setPixelPositionXas(int xas) {
		this.pixelPositionXas = xas;	
	}
	/**
	 * Variable registering the Xposition of this Slime.
	 */
	private int pixelPositionXas= 0;
	//##############################################################################
	
	/**
	* Return the pixelPositionYas of this Slime.
	*/
	@Override
	@Basic @Raw
	public int getPixelPositionYas(){
		return pixelPositionYas;
	}

	
	/**
	* Set the pixelPositionYas of this Slime to the given pixelPositionYas.
	* 
	* @param  newYposition
	*         The new pixelPositionYas for this Slime
	* @post   The pixelPositionYas of this new Slime is equal to
	*         the given pixelPositionYas.
	*       | new.getpixelPositionYas() == newYposition
	*/
	@Override
	@Raw
	public void setPixelPositionYas(int yas)  {
			this.pixelPositionYas = yas;
		}
	/**
	 * variable registering the y-as of this slime
	 */
	int pixelPositionYas = 0;
	//##############################################################################


	/**
	 * 
	 * @param newOrientation
	 * 
	 * @post this function sets the current orientation.
	 * 		|new.getorientation == neworientation
	 */
	public void setOrientation(int newOrientation) {
		if(isValidOrientation(newOrientation)) {
			orientation = newOrientation;
		}	
	}
	/**
	 * 
	 * @param Orientation
	 * @return this returns a valid orientation
	 * 		|return Orientation>=-1 && Orientation<=1
	 */
	public static boolean isValidOrientation(int Orientation) {
		return Orientation>=-1 && Orientation<=1;
	}
	//##############################################################################
	/**
	 * @return this returns the current velocity of the slime.
	 */
	@Override
	public double[] getVelocity() {
		double[] horizontal = {getHorizontalVelocity(),0};
		return horizontal;
	}


	/**
	 * Check whether the given sprites are valid for any Slime
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
	
	/**
	* Check whether the given Xposition is a valid Xposition for
	* any Slime.
	*  
	* @param  Xposition
	*         The Xposition to check.
	* @return 
	*       | result ==  newXposition <0 && newXposition>WORLD_WIDTH
	*/
	public static boolean isValidpixelPositionXas(int newXposition) {
		return true; 
		}
		
	//##############################################################################
	
	/**
	* Check whether the given pixelPositionYas is a valid pixelPositionYas for
	* any Slime.
	*  
	* @param  pixelPositionYas
	*         The pixelPositionYas to check.
	* @return 
	*       | result == newYposition <0 &&newYposition>WORLD_HEIGHT
	*/
	public static boolean isValidpixelPositionYas(int newYposition) {
		return true;
	}
				
		
	
		//##############################################################################

		@Override
		public void collisionWithImpassableTerrain(int[]rectanglePosition1, int[] rectanglePosition2, int tiles) {
			
			
			 if(rectanglePosition2[0]+rectanglePosition2[2]==rectanglePosition1[0]
					&& rectanglePosition2[1]+rectanglePosition2[3]-tiles>rectanglePosition1[1]){
				if (this.getOrientation()==-1) {
					//setOrientation(1);
					//setCollisionWithWall(true);
					setHorizontalVelocity(0);
					this.setHorizontalAcceleration(0);
					//setHorizontalAcceleration(getHorizontalAcceleration()*getOrientation());
				}
			}
			else if (rectanglePosition2[0]==rectanglePosition1[0]+rectanglePosition1[2]
					&&rectanglePosition2[1]+rectanglePosition2[3]-tiles>rectanglePosition1[1] ) {
				if (this.getOrientation()==1) {
					//setOrientation(-1);
					setHorizontalVelocity(0);
					this.setHorizontalAcceleration(0);
					//setVelocity(0)
					//setCollisionWithWall(true);
					//setHorizontalAcceleration(getHorizontalAcceleration()*getOrientation());
				}
			}
		}
	
		@Override
		protected void collisionWithGas(int[] rectanglePositionThisObject, int[] rectanglePositionTiles) {
			this.setDamageTimerTileLimit(0.3);
			this.setTakeDamageFromGeologicalFeature(GeologicalFeatures.GAS);
			
		}
		
		protected void collisionWithMagma(int[] rectanglePositionThisObject, int[] rectanglePositionTiles) {
			this.lowerHitpointsOfSlime(-1*this.getHitPoints());
			this.setTakeDamageFromGeologicalFeature(GeologicalFeatures.MAGMA);
		}
		
		
		protected void collisionWithWater(int[] rectanglePositionThisObject, int[] rectanglePositionTiles) {
			this.setTakeDamageFromGeologicalFeature(GeologicalFeatures.WATER);
			this.setDamageTimerTileLimit(0.4);
		}
		
		
		@Override
		protected void collisionExtraEffect(Object other) {
			if(other instanceof BlockedObject) {
				BlockedObject blockedObject= (BlockedObject) other;
				if(! hasImpactWithGameObject(blockedObject)) {
						if(blockedObject instanceof Shark) {
							Shark shark = (Shark) blockedObject;
							if(shark.getHitPoints()>0 &&this.getHitPoints()>0) {
								this.lowerHitpointsOfSlime(-1*this.getHitPoints());
							}
						}
					if(blockedObject instanceof Slime) {
						Slime slime = (Slime) blockedObject;
						if(slime.getHitPoints()>0&& this.getHitPoints()>0) {
							this.slimeContactWithSlime(slime);
							if(this.getSchool()!=null && slime.getSchool()!=null) {
								if (this.getSchool().getSizeOfTheSchool() < slime.getSchool().getSizeOfTheSchool()) {
									this.slimeCollisionWithLargerGroup(slime);
								}
							}
						}
					}
					if(blockedObject instanceof Mazub) {
						Mazub mazub = (Mazub) blockedObject;
						if(mazub.getHitPoints()>0&& this.getHitPoints()>0) {
							this.lowerHitpointsOfSlime(-30);
						}
					}
					this.changeImpactValue(blockedObject, 2);
				}
				else {
					this.changeImpactValue(blockedObject, 2);
				}
			}
		}
		
		public void slimeContactWithSlime(Slime otherSlime) {
			this.slimeCollidesInOppositeDirection(otherSlime);
			this.setHorizontalVelocity(0);
		}
		
		public void slimeCollisionWithLargerGroup(Slime otherSlime) {
			this.switchSchool(otherSlime.getSchool());
		}
		
		//##############################################################################
		public void slimeCollidesInOppositeDirection(Slime otherSlime) {
			int[] rectanglePosition1=this.getRectanglePosition();
			int[] rectanglePosition2=otherSlime.getRectanglePosition();
			if(rectanglePosition2[0]+rectanglePosition2[2]==rectanglePosition1[0]
					&& rectanglePosition2[1]+rectanglePosition2[3]>rectanglePosition1[1]){
				if (this.getOrientation()==-1) {
					this.setOrientation(1);
					this.setHorizontalAcceleration(this.getConstantHorizontalAcceleration()*this.getOrientation());
				}
			}
			else if (rectanglePosition2[0]==rectanglePosition1[0]+rectanglePosition1[2]
					&&rectanglePosition2[1]+rectanglePosition2[3]>rectanglePosition1[1] ) {
				if (this.getOrientation()==1) {
					this.setOrientation(-1);
					this.setHorizontalAcceleration(this.getConstantHorizontalAcceleration()*this.getOrientation());
				}
			}
		}
		
		//##############################################################################
		/**
		 * @post this part checks if the slime has to take dmg from water and then lowers the hitpoints if this is true.
		 * 		|if(this.getTakeDamageFromGeologicalFeature()==GeologicalFeatures.WATER) 
		 *		|this.lowerHitpointsOfSlime(-4)
		 * @post this part checks if the slime has to gain health from gas and then ups the hitpoints if this is true.
		 * 		|if(this.getTakeDamageFromGeologicalFeature()==GeologicalFeatures.GAS) 
		 *		|this.addHitpoints(2)
		 * @post this part checks if the slime has to take dmg from magma and then kills the slime if this is true.
		 * 		|if(this.getTakeDamageFromGeologicalFeature()==GeologicalFeatures.MAGMA) 
		 *		|this.lowerHitpointsOfSlime(-1* this.getHitPoints())
		 */
		@Override
		public void takesDamageFromGeologicalFeature() {
			if(this.getTakeDamageFromGeologicalFeature()==GeologicalFeatures.WATER) {
				this.lowerHitpointsOfSlime(-4);
			}
			if(this.getTakeDamageFromGeologicalFeature()==GeologicalFeatures.GAS) {
				this.addHitpoints(2);
			}
			if(this.getTakeDamageFromGeologicalFeature()==GeologicalFeatures.MAGMA) {
				this.lowerHitpointsOfSlime(-1* this.getHitPoints());;
			}
		}
		//##############################################################################
		/**
		 * 
		 * @param hitpoints
		 * 
		 * @post this function lowers the slime's hitpoints with the amount that comes in and it does the same for the slimes in the group of this slime.
		 * 		|this.addHitpoints(hitpoints)
		 *		|if(this.getSchool()!=null) 
		 *	 	|this.getSchool().groupSharePain(this)
		 *
		 */
		public void lowerHitpointsOfSlime(int hitpoints) {
			this.addHitpoints(hitpoints);
			if(this.getSchool()!=null) {
				this.getSchool().groupSharePain(this);
			}
		}
		
		//##############################################################################
		
		/**
		 * 
		 * @param currentSpriteOfThisFunction
		 * 
		 * @post this function sets the current sprite of this slime.
		 * 		|new.getcurrentSprite == currentSpriteOfThisFunction
		 */
		public void setCurrentSprite(Sprite currentSpriteOfThisFunction) {
			currentSprite = currentSpriteOfThisFunction;
		}
		 /**
		  * variable containing the current sprite of this function
		  */
		private Sprite currentSprite; 
		
		//##############################################################################
		/**
		 * @post this functon terminates the current slime.
		 * 		|if (this.getWorld()!=null) 
		 *  	|(this.getWorld()).removeAsGameObject(this)
		 *		|this.new.getWorld == null
		 *		|if(this.getSchool()!=null) 
		 *		|this.getSchool().removeAsSlime(this)
		 *		|this.new.getSchool == null
		 *		|this.terminated=true
		 */
		@Override
		public  void terminate() {
			
			if (this.getWorld()!=null) {
			 (this.getWorld()).removeAsGameObject(this);
			}
			this.setWorld(null);
			if(this.getSchool()!=null) {
				this.getSchool().removeAsSlime(this);
				this.setSchool(null);
			}
			this.terminated=true;
			
		}
		/**
		 * @return this function compares the id of this slime to an other slime.
		 * 		|return this.getID() - otherSlime.getID()
		 */
		@Override
		public int compareTo(Slime otherSlime) {
			return this.getID() - otherSlime.getID();
		}

		//##############################################################################

		
		/**
		 * returns the constant of horizontal acceleration of Mazub
		 */
		@Basic 
		@Immutable
		public double getConstantHorizontalAcceleration(){
			return this.constantHorizontalAcceleration;
		}
		
		/**
		 * variable registering the constantHorizontalAcceleration of this Mazub
		 */
		private final double constantHorizontalAcceleration ;
}
		