package jumpingalien.model;

import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import be.kuleuven.cs.som.annotate.*;

/**
 *
 * @invar  The mazubTakeDamageFromGeologicalFeature of each Mazub must be a valid mazubTakeDamageFromGeologicalFeature for any
 *         Mazub.
 *       | isValidMazubTakeDamageFromGeologicalFeature(getMazubTakeDamageFromGeologicalFeature())
 * @invar  The Xposition of each Mazub must be a valid Xposition for any
 *         Mazub.
 *       | isValidpixelPositionXas(getPixelPositionXas())
 * @invar  The pixelPositionYas of each Mazub must be a valid pixelPositionYas for any
 *         Mazub.
 *       | isValidpixelPositionYas(getpixelPositionYas())
 * @invar  The Orientation of each Mazub must be a valid Orientation for any
 *         Mazub.
 *       | isValidOrientation(getOrientation())
 * @invar  The horizontalVelocity of each Mazub must be a valid horizontalVelocity for any
 *         Mazub.
 *       | isValidHorizontalVelocity(getHorizontalVelocity())
 * @invar  The verticalVelocity of each Mazub must be a valid verticalVelocity for any
 *         Mazub.
 *       | isValidVerticalVelocity(getVerticalVelocity())
 * @invar The Sprites of each Mazub must be  valid Sprites for any Mazub
 * 		| isValidSprites(getVerticalVelocity())
 * 
*/	

public class Mazub extends JumpingObject implements horizontalMovingObject,verticalMovingObject {
	
	public static final double V_ACCELERATION_JUMPING = -10.0;
	public static final int MAX_HEALTH=500;
	
	//##############################################################################
	/**
	 * 
	 * @param positionX
	 * The starting x-position of Mazub in meters
	 * @param positionY
	 * The starting y-position of Mazub in meters
	 * @param inSprites
	 * The array of sprites
	 * 
	 * @post initialize a new Mazub with the given x and y position 
	 * 		| new.getPixelPositionXas() == actualToPixel(positionX)
	 * 		| new.getPixelPositionYas() == actualToPixel(positionY)
	 *   	| new.getActualPositionXas() == positionX
	 *		| new.getActualPositionYas() == positionY
	 *
	 *@post initialize the immutable variable of Mazub
	 *		|new.getMaxSpeedHorizontal==3
	 *		|new.getMaxSpeedHorizontalDucking==1
	 *		|new.getConstantHorizontalAcceleration ==0.9
	 *		|new.getMinimalHorizontalVelocity == 1.0
	 *
	 *@post initialize a new Mazub with the given sprites	
	 * 		| new.getSprite==sprites
	 * 
	 * @throws IllegalArgumentException
	 * 		| inSprites==null
	 */
	public Mazub(int positionX, int positionY, Sprite[] inSprites)throws IllegalArgumentException,IllegalStateException {
		this.setPixelPositionXas(positionX);
		this.setPixelPositionYas(positionY);
		this.setActualPositionX(pixelToActual(positionX));
		this.setActualPositionY(pixelToActual(positionY));
		Sprite[] sprites= Arrays.copyOf(inSprites, inSprites.length);
		this.setSprite(sprites);
		this.setCurrentSpriteNumber(0);
		this.setHitpoints(100);
		this.setV_VELOCITYJUMPING(new VerticalJumpingConstant(8));
		this.maxSpeedHorizontal=3;
		this.maxSpeedHorizontalDucking=1;
		this.constantHorizontalAcceleration=0.9;
		this.minimalHorizontalVelocity=1.0;
		

	}
	
	//##############################################################################
	


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
	
	//##############################################################################
	/**
	 * handles the hitpoints of mazub if he has collision with a plant
	 * @param valueOfPlant
	 * 		the amount of hitpoints the plant adds to Mazub
	 * @effect
	 * changeActualHitpoints(valueOfPlant);
	 */
	public void mazubCollisionWithPlant(int valueOfPlant) {
		addHitpoints(valueOfPlant);
	}
	
	
	//##############################################################################
	
	/**
	 * Return the Xposition of this Mazub.
	 */
	@Basic @Raw
	public int getPixelPositionXas() {
		return this.pixelPositionXas;
	}
	/**
	 * Set the Xposition of this Mazub to the given Xposition.
	 * 
	 * @param  newXposition
	 *         The new Xposition for this Mazub.
	 * @post   The Xposition of this new Mazub is equal to
	 *         the given Xposition.
	 *       | new.getPixelPositionXas() == newXposition
	 */
	@Raw
	public void setPixelPositionXas(int xas)  {
		this.pixelPositionXas = xas;	
	}

	/**
	 * Variable registering the Xposition of this Mazub.
	 */
	private int pixelPositionXas=0;
	
	//##############################################################################
	
	/**
	 * Return the pixelPositionYas of this Mazub.
	 */
	@Basic @Raw
	public int getPixelPositionYas() {
		return pixelPositionYas;
	}
	
	/**
	 * Set the pixelPositionYas of this Mazub to the given pixelPositionYas.
	 * 
	 * @param  newYposition
	 *         The new pixelPositionYas for this Mazub.
	 * @post   The pixelPositionYas of this new Mazub is equal to
	 *         the given pixelPositionYas.
	 *       | new.getpixelPositionYas() == newYposition

	 */
	@Raw
	public void setPixelPositionYas(int yas){
		this.pixelPositionYas = yas;
	}
	

	/**
	 * Variable registering the pixelPositionYas of this Mazub.
	 */
	private int pixelPositionYas=0;
	
	//##############################################################################

	/**
	 * Set the Xposition of this Mazub to the given Xposition.
	 * 
	 * @param  Xposition
	 *         The new Xposition for this Mazub(in meters).
	 * @post   I, the new Xposition of this new Mazub is equal to
	 *         the given Xposition. 
	 *         |	new.getActualPositionXas() == Xposition
	 *         
	 *  @Post If it is less than 0 or greater than the world width it is
	 *         added to the terminated objects
	 *       | if (Xposition<0||Xposition>((this.getWorld()).getnbTilesX())*(this.getWorld()).getTileSize()) then
	 *       |		this.getWorld().addAsTerminatedObjects(this);
	 */
	@Override
	@Raw
	public void setActualPositionX(double Xposition)throws IllegalArgumentException {
		if (this.getWorld()!= null) {
			if(Xposition<0||Xposition>pixelToActual(((this.getWorld()).getnbTilesX())*(this.getWorld()).getTileSize())) {
				this.getWorld().addAsTerminatedObjects(this);
			}
		}	
		else if(Xposition<0) {
			throw new IllegalArgumentException("Outside boundaries");
		}
		this.actualPositionX = Xposition;
	}

	//##############################################################################

	/**
	 * Set the Yposition of this Mazub to the given Xposition.
	 * 
	 * @param  Yposition
	 *         The new Yposition for this Mazub(in meters).
	 * @post   If the Yposition is valid, the new Yposition of this new Mazub is equal to
	 *         the given Yposition. OtherWise if it is less than 0 or greater
	 *         than the world Heigth  it is added to the terminated objects
	 *       | if(Yposition<0||Yposition>pixelToActual((this.getWorld()).getnbTilesY())*(this.getWorld()).getTileSize()) then
	 *       |		this.getWorld().addAsTerminatedObjects(this);
	 * @post the new Yposition is equal to the given y position
	 *       |	new.getActualPositionYas() == Yposition
	 * @throws IllegalArgumentException
	 * 		|(this.getWorld()==null &&Yposition<0)
	 */
	@Override
	@Raw
	public void setActualPositionY(double Yposition)throws IllegalArgumentException {
		if (this.getWorld()!= null) {
			if(Yposition<0||Yposition>pixelToActual((this.getWorld()).getnbTilesY())*(this.getWorld()).getTileSize()) {
				this.getWorld().addAsTerminatedObjects(this);
				
			}
		}
		else if(Yposition<0) {
			throw new IllegalArgumentException("Outside boundaries");
		}
		this.actualPositionY = Yposition;
	}
	
	
	//##############################################################################

	/**
	 * Set the Orientation of this Mazub to the given Orientation.
	 * 
	 * @param  newOrientation
	 *         The new Orientation for this Mazub.
	 * @pre    The given Orientation must be a valid Orientation for any
	 *         Mazub.
	 *       | this.canHaveAsOrientation(newOrientation)
	 * @post   The Orientation of this Mazub is equal to the given
	 *         Orientation.
	 *       | new.getOrientation() == newOrientation
	 */
	@Raw
	public void setOrientation(int newOrientation) {
		assert this.canHaveAsOrientation(newOrientation)&& isValidOrientation(newOrientation);
		this.orientation=newOrientation;
	}
	
	/**
	 * Check whether the given Orientation is a valid Orientation for
	 * any Mazub.
	 *  
	 * @param  Orientation
	 *         The Orientation to check.
	 * @return 
	 *       | result == (newOrientation>=-1|| newOrientation<=1)
	*/
	public static boolean isValidOrientation(int newOrientation) {
		return (newOrientation>=-1|| newOrientation<=1);
	}
	
	/**
	 * Check wethever the given orientation is valid for this Mazub
	 * @param newOrientation
	 * 		the orientation to check
	 * @return
	 * 		if(isValidOrientation(newOrientation) then
	 * 		result == (newOrientation!=0 || this.getNotMovingTimer() >=1)
	 * 		else then
	 * 		result == false
	 */
	public boolean canHaveAsOrientation(int newOrientation) {
		if (isValidOrientation(newOrientation)) {
			return (newOrientation!=0 || this.getNotMovingTimer() >=1);
		}
		return false;
	}
	
	
	//##############################################################################
	
	
	/**
	 * Return the horizontalVelocity of this Mazub.
	 */
	@Basic @Raw
	public double getHorizontalVelocity() {
		return this.horizontalVelocity;
	}
	
	/**
	 * Initialize this new Mazub with given horizontalVelocity.
	 * 
	 * @param  horizontalVelocity
	 *         The horizontalVelocity for this new Mazub.
	 *         
	 *@post   If the given horizontalVelocity is a valid horizontalVelocity for any Mazub,
	 *         the horizontalVelocity of this new Mazub is equal to the given
	 *         horizontalVelocity.
	 *         | if (isValidHorizontalVelocity(horizontalVelocity))
	 *         |   then new.getHorizontalVelocity() == horizontalVelocity
	 *                  
	 * @post   If the given horizontalVelocity is a valid horizontalVelocity for any Mazub,
	 *         the horizontalVelocity of this new Mazub is equal to the given
	 *         horizontalVelocity. Otherwise, if the horizontalVelocity of this new Mazub is greater
	 *         than the this.getMaxSpeedHorizontal() the velocity is set to the this.getMaxSpeedHorizontal().
	 *         | if (isValidHorizontalVelocity(horizontalVelocity))
	 *         |   then new.getHorizontalVelocity() == horizontalVelocity
	 *         | else if( horizontalVelocity>this.getMaxSpeedHorizontal())
	 *         |		new.getHorizontalVelocity() ==this.getMaxSpeedHorizontal()
	 *         
	 * @post   If the given horizontalVelocity is a valid horizontalVelocity for any Mazub,
	 *         the horizontalVelocity of this new Mazub is equal to the given
	 *         horizontalVelocity. Otherwise, if the horizontalVelocity of this new Mazub is smaller
	 *         than this.getMinimalHorizontalVelocity() the velocity is set to the this.getMinimalHorizontalVelocity().
	 *         
	 *       | if (isValidHorizontalVelocity(horizontalVelocity))
	 *       |   then new.getHorizontalVelocity() == horizontalVelocity
	 *       | else if (horizontalVelocity<this.getMinimalHorizontalVelocity())
	 *       |		new.getHorizontalVelocity() == this.getMinimalHorizontalVelocity()
	 */
	public void setHorizontalVelocity(double horizontalVelocity) {
		if (! isValidHorizontalVelocity(horizontalVelocity))
		{
			if (horizontalVelocity>this.getMaxSpeedHorizontal()) {
				this.setHorizontalVelocity(this.getMaxSpeedHorizontal()) ;
			}
			if(horizontalVelocity<this.getMinimalHorizontalVelocity()){
				this.setHorizontalVelocity(this.getMinimalHorizontalVelocity());
			}
			
		}
		this.horizontalVelocity=horizontalVelocity;
	}
	
	
	
	/**
	 * Check whether the given horizontalVelocity is a valid horizontalVelocity for
	 * any Mazub.
	 *  
	 * @param  horizontalVelocity
	 *         The horizontalVelocity to check.
	 * @return 
	 *       | result == this.getHorizontalVelocity<=this.getMaxSpeedHorizontal()||this.getHorizontalVelocity>=this.getMinimalHorizontalVelocity()
	*/
	public boolean isValidHorizontalVelocity(double horizontalVelocity) {
		return (this.getHorizontalVelocity()<=getMaxSpeedHorizontal()||this.getHorizontalVelocity()>=this.getMinimalHorizontalVelocity());
	}
	
	/**
	 * Variable registering the horizontalVelocity of this Mazub.
	 */
	private double horizontalVelocity = 0;
	
	//##############################################################################
	
	/**
	 * returns the max speed horizontal of this Mazub
	 */
	@Basic 
	@Immutable
	public double getMaxSpeedHorizontal(){
		return this.maxSpeedHorizontal;
	}
	
	/**
	 * variable registering the maxspeed of this Mazub
	 */
	private final double maxSpeedHorizontal ;
	//##############################################################################
	/**
	 * returns the max speed horizontal ducking of this Mazub
	 */
	@Basic 
	@Immutable
	public double getMaxSpeedHorizontalDucking(){
		return this. maxSpeedHorizontalDucking;
	}
	
	/**
	 * variable registering the maxspeed ducking of this Mazub
	 */
	private final double maxSpeedHorizontalDucking ;
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
	//##############################################################################
	/**
	 * return the minimal horizontalVelocity of this Mazub
	 */
	@Basic 
	@Immutable
	public double getMinimalHorizontalVelocity(){
		return this.minimalHorizontalVelocity;
	}
	
	/**
	 * variable registering the minimalHorizontalVelocity of this Mazub
	 */
	private final double minimalHorizontalVelocity ;
	//##############################################################################
	/**
	 * Return the verticalVelocity of this Mazub.
	 */
	@Basic @Raw
	public double getVerticalVelocity() {
		return this.verticalVelocity;
	}
	/**
	 * Check whether the given verticalVelocity is a valid verticalVelocity for
	 * any Mazub.
	 *  
	 * @param  newVerticalVelocity
	 *         The verticalVelocity to check.
	 * @return 
	 *       | result == newVerticalVelocity<V_VELOCITY_JUMPING
	*/
	public  boolean isValidVerticalVelocity(double newVerticalVelocity) {
		return (newVerticalVelocity<=this.V_VELOCITY_JUMPING.getjumpingSpeed());
	}
	/**
	 * Initialize this new Mazub with given verticalVelocity.
	 * 
	 * @param  newVerticalVelocity
	 *         The verticalVelocity for this new Mazub.
	 * @post   If the given verticalVelocity is a valid verticalVelocity for any Mazub,
	 *         the verticalVelocity of this new Mazub is equal to the given
	 *         verticalVelocity. Otherwise, the verticalVelocity of this new Mazub is equal
	 *         to 8.
	 *       | if (isValidVerticalVelocity(newVerticalVelocity))
	 *       |   then new.getVerticalVelocity() == newVerticalVelocity
	 *       |   else new.getVerticalVelocity() == 8
	 */
	public void setVerticalVelocity(double newVerticalVelocity) {
		if (! isValidVerticalVelocity(newVerticalVelocity)) {
			this.verticalVelocity = 8;
		}
		else {
			this.verticalVelocity = newVerticalVelocity;
		}
	}
	
	/**
	 * Variable registering the verticalVelocity of this Mazub.
	 */
	private double verticalVelocity = 0;
	
	//##############################################################################
	/**
	 * Return both the horizontalVelocity and the verticalVelocity of this Mazub in that order.
	 * @return
	 * 		|result== {this.getHorizontalVelocity(),this.getVerticalVelocity()}
	 */
	public double[] getVelocity() {
		double[] velocity= {this.getHorizontalVelocity(),this.getVerticalVelocity()};
		return velocity ;
	}
	/**
	 * set both the horizontalVelocity and the vertical velocity to the given veloctiy
	 * 
	 * @param newVelocityHorizontal
	 * 			The verticalVelocity for this new Mazub.
	 * @param newVelocityVertical
	 * 			The horizontalVelocity for this new Mazub.
	 * @effect
	 * 		|this.setHorizontalVelocity(newVelocityHorizontal);
	 *		|this.setVerticalVelocity(newVelocityVertical);
	 * 
	 */
	public void setVelocity(double newVelocityHorizontal,double newVelocityVertical) {
		this.setHorizontalVelocity(newVelocityHorizontal);
		this.setVerticalVelocity(newVelocityVertical);
	}
	//##############################################################################
	
	
	/**
	 * Returns a boolean of Mazub if he is moving or not
	 */
	@Basic
	public boolean getMoving() {
		return this.moving;
	}
	
	/**
	 * Set the moving of this Mazub to the given moving.
	 * 
	 * @param  moving
	 *         The new moving for this Mazub.
	 * @pre    The given moving must be a valid moving for any
	 *         Mazub.
	 *       | isValidmoving(moving)
	 * @post   The moving of this Mazub is equal to the given
	 *         moving.
	 *       | new.getmoving() == moving
	 */
	@Raw
	public void setMoving(boolean moving) {
		assert isValidmoving(moving);
		this.moving = moving;
	}
	
	/**
	 * Check whether the given moving is a valid moving for
	 * any Mazub.
	 *  
	 * @param  moving
	 *         The moving to check.
	 * @return 
	 *       | result == this.getMoving()!=moving
	*/
	public boolean isValidmoving(boolean moving) {
		return this.getMoving()!=moving;
	}
	
	/**
	 * Variable registering the moving of this Mazub.
	 */
	private boolean moving=false;
	
	//##############################################################################
	/**
	 * Set all values correct when Mazub start's moving left
	 * @pre Mazub is not already moving before you call this method
	 * 		|this.getMoving()==false
	 * @post
	 * 		|new.getMoving==this.setMoving(true);
	 *		|new.getOrientation ==this.setOrientation(-1);
	 *		|new.getHorizontalVelocity() == this.setHorizontalVelocity(this.getOrientation()*MINIMUM_HORIZONTAL_VELOCITY);
	 *		|new.getHorizontalAcceleration()== this.setHorizontalAcceleration(HORIZONTAL_ACCELERATION*this.getOrientation());
	 *@effect use the correct sprite for this Mazub
	 *		|this.useCorrectSprite();
	 */
	public void startMoveLeft() {
		assert(this.getMoving()==false && this.isDeadGameobject()==false);
		this.setMoving(true);
		this.setOrientation(-1);
		this.useCorrectSprite();
		this.setHorizontalVelocity(this.getOrientation()*this.getMinimalHorizontalVelocity());
		this.setHorizontalAcceleration(this.getConstantHorizontalAcceleration()*this.getOrientation());
		
	}
	
	
	/**
	 * Set all values correct when Mazub start's moving rigth
	 * 
	 * @pre Mazub is not already moving before you call this method
	 * 		|this.getMoving()==false
	 * @post
	 * 		|new.getMoving==this.setMoving(true);
	 *		|new.getOrientation == this.setOrientation(1);
	 *		|new.getHorizontalVelocity() == this.setHorizontalVelocity(this.getOrientation()*MINIMUM_HORIZONTAL_VELOCITY);
	 *		|new.getHorizontalAcceleration()==this.setHorizontalAcceleration(HORIZONTAL_ACCELERATION*this.getOrientation());
	 *
	 *@effect use the correct sprite
	 *		|this.useCorrectSprite();
	 *@throws ModelException
	 *		if you are already moving, you can't start moving again.
	 *		| getMoving() != false
	 */
	public void startMoveRigth() {
		assert(this.getMoving()==false&& this.isDeadGameobject()==false);
		this.setMoving(true);
		this.setOrientation(1);
		this.useCorrectSprite();
		this.setHorizontalVelocity(this.getOrientation()*this.getMinimalHorizontalVelocity());
		this.setHorizontalAcceleration(this.getConstantHorizontalAcceleration()*this.getOrientation());
	}
	
	
	/**
	 * End the move of Mazub
	 * @pre The Mazub is moving while calling this method
	 * 		|this.getMoving()==true
	 * @post Set moving to false , velocity and acceleration to 0
	 * 		| new.getMoving==false
	 * 		| new.getHorizontalVelocity() == 0
	 * 		| new.getHorizontalAcceleration() == 0
	 * 
	 * @effect use the correct Sprite for this Mazub
	 * 		|this.useCorrectSprite()
	 */
	public void endMove() {
		assert (getMoving() == true);
		this.setMoving(false);
		this.setHorizontalVelocity(0);
		this.setHorizontalAcceleration(0);
		this.useCorrectSprite();
	}
	

	//##############################################################################
	
	/**
	 * Start the jump of Mazub
	 * 
	 * @post set the jumping of this Mazub to true and if it is on the ground 
	 * his velocity becomes 8 and his vertical acceleration -10, also it will 
	 * use the jumping sprite for Mazub
	 * 
	 * 		| new.getJumping==true
	 * 		|if(this.getPixelPositionYas()<=0){ 
	 * 		| new.getVerticalVelocity == V_VELOCITY_JUMPING
	 * 		| new.getVerticalAcceleration == V_ACCELERATION_JUMPING
	 * 		| new.sprites[new.getCurrentSpriteNumber] == this.useCorrectSprite() }
	 * @throws IllegalStateException
	 * 		if Mazub is already jumping the method will throw a IllegalStateException
	 * 		|(! isValidJumping(true))
	 */
	@Override
	public void startJump() throws IllegalStateException {
		if (! isValidJumping(true) || this.isDeadGameobject()) {
			throw new IllegalStateException("Cannot startJump while  jumping");
		}
		this.setJumping(true);
		this.setVerticalVelocity(V_VELOCITY_JUMPING.getjumpingSpeed());
		this.setVerticalAcceleration(V_ACCELERATION_JUMPING);
		this.useCorrectSprite();

	}
	/**
	 * End the jump of Mazub
	 * @post set the jumping of this Mazub to false and his vertical velocity to 0
	 * 		|new.getJumping == false
	 * 		|new.getVerticalVelocity == 0
	 * @throws IllegalStateException
	 * 	if Mazub is not jumping the method will throw a IllegalStateException
	 * 		|(! isValidJumping(true))
	 */
	@Override
	public void endJump() throws IllegalStateException{
		if (! isValidJumping(false)) {
			throw new ModelException("Cannot endJump while not jumping");
		}
			this.setJumping(false);
			this.useCorrectSprite();
			if(this.getVerticalVelocity()>0) {
				this.setVerticalVelocity(0);
			}
	}
	
	
	//##############################################################################
	
	/**
	 * Return the ducking of this Mazub.
	 */
	@Basic @Raw
	public boolean getDucking() {
		return this.ducking;
	}
	
	/**
	 * Initialize this new Mazub with given ducking.
	 * 
	 * @param  ducking
	 *         The ducking for this new Mazub.
	 * @post   If the given ducking is a valid ducking for any Mazub,
	 *         the ducking of this new Mazub is equal to the given
	 *         ducking. Otherwise, the ducking of this new Mazub is equal
	 *         to his old ducking value.
	 *       | if (isValidDucking(ducking))
	 *       |   then new.getDucking() == newDucking
	 *       |   else new.getDucking() == this.getDucking()
	 */
	public void setDucking(boolean newDucking) {
		if (! isValidDucking(ducking))
			this.ducking = this.getDucking();
		this.ducking=newDucking;
	}
	
	/**
	 * Check whether the given ducking is a valid ducking for
	 * any Mazub.
	 *  
	 * @param  ducking
	 *         The ducking to check.
	 * @return 
	 *       | result == this.getDucking()!=ducking
	*/
	public  boolean isValidDucking(boolean ducking) {
		return this.getDucking()!=ducking;
	}
	
	//##############################################################################
	/**
	 * Let Mazub duck
	 * @effect set the ducking to true and use the correct sprite,...
	 * 		|new.getDucking() == true
	 * 		|this.useCorrectSprite()
	 * 		|this.setHorizontalAcceleration(0);
	 * 		|if(this.getMoving()==true) then
	 * 		|	new.getHorizontalVelocity()==1*getOrientation()
	 * 
	 */
	public void startDuck() {
		this.setDucking(true);
		this.useCorrectSprite();
		this.setHorizontalAcceleration(0);
		if (this.getMoving() == true) {
			setHorizontalVelocity(1*getOrientation());
		}
	}
	/**
	 * Let Mazub stop  duck
	 * @effect checks if it is possible to stop ducking and if it isn't Mazub will 
	 * keep his old sprite
	 * 		|if(this.getDucking()==true) then
	 * 		|	int oldSprite= this.getCurrentSpriteNumber();
	 * 		|	this.setDucking(false);
	 * 		|	this.useCorrectSprite();
	 * 		|	if(this.getCurrentSpriteNumber()==oldSprite|| this.hasCollisionWithBottomOtherObject()) then
	 *		|		this.setDucking(true);
	 *		|		this.setCurrentSpriteNumber(oldSprite);
	 *		|else then
	 *		|	this.setHorizontalAcceleration(this.getConstantHorizontalAcceleration()*this.getOrientation())
	 * 
	 */
	public void endDuck(){
		if(this.getDucking()==true) {
			int oldSprite= this.getCurrentSpriteNumber();
			this.setDucking(false);
			this.useCorrectSprite();
			if(this.getCurrentSpriteNumber()==oldSprite|| this.hasCollisionWithBottomOtherObject()) {
				//this.setmazubWantToStandUp(true);
				this.setDucking(true);
				this.setCurrentSpriteNumber(oldSprite);
			}
			else {
				//this.setmazubWantToStandUp(false);
				this.setHorizontalAcceleration(this.getConstantHorizontalAcceleration()*this.getOrientation());
			}
		}
	}
	
	/**
	 * Variable registering the ducking of this Mazub.
	 */
	private boolean ducking=false;
	
	//##############################################################################


	
	/**
	 * Return the mazubWantToStandUp of this Mazub.
	 */
	@Basic @Raw
	public boolean getmazubWantToStandUp() {
		return this.mazubWantToStandUp;
	}
	
	/**
	 * Check whether the given mazubWantToStandUp is a valid mazubWantToStandUp for
	 * any Mazub.
	 *  
	 * @param  mazubWantToStandUp
	 *         The mazubWantToStandUp to check.
	 * @return 
	 *       | result == this.hasCollisionwithTopTiles(this.getRectanglePosition())
	*/
	public  boolean isValidmazubWantToStandUp(boolean mazubWantToStandUp, int[] rectanglePosition,World world) {
		return true;
	}
	
	/**
	 * Set the mazubWantToStandUp of this Mazub to the given mazubWantToStandUp.
	 * 
	 * @param  mazubWantToStandUp
	 *         The new mazubWantToStandUp for this Mazub.
	 * @post   If the given mazubWantToStandUp is a valid mazubWantToStandUp for any Mazub,
	 *         the mazubWantToStandUp of this new Mazub is equal to the given
	 *         mazubWantToStandUp.
	 *       | if (isValidmazubWantToStandUp(mazubWantToStandUp))
	 *       |   then new.getmazubWantToStandUp() == mazubWantToStandUp
	 */
	@Raw
	public void setmazubWantToStandUp(boolean mazubWantToStandUp) {
		if (isValidmazubWantToStandUp(mazubWantToStandUp,this.getRectanglePosition(),this.getWorld()))
			this.mazubWantToStandUp = mazubWantToStandUp;
		
	}
	
	/**
	 * Variable registering the mazubWantToStandUp of this Mazub.
	 */
	private boolean mazubWantToStandUp;

	//##############################################################################
	
	/**
	 * Set the Xposition of this Mazub to the given Xposition.
	 * 
	 * @param xas
	 * The new Xposition for this Mazub.
	 * 
	 * @post   the pixelpositionX of this Mazub is equal to the given position
	 * 		| new.getPixelPositionXas()== xas
	 */
	public void setMovingPositionXas(int xas) {
		this.setPixelPositionXas(xas);
	}
	/**
	 * Set the Yposition of this Mazub to the given Yposition.
	 * 
	 * @param yas
	 * The new Yposition for this Mazub.
	 * 
	 * @post   the pixelpositionY of this Mazub is equal to the given position
	 * 		| new.getPixelPositionYas()== yas
	 */
	public void setMovingPositionYas(int yas) {
			this.setPixelPositionYas(yas);
	}
	
	//##############################################################################
	/**
	 * Calculate the new Xposition in function of the time ,velocity and acceleration
	 * @param dt
	 * the time that elapsed
	 * @effect the new Xposition is equal to 1/2*dt^2*horizontal acceleration+dt*horizontal velocity+Xposition
	 * 		|new.getPixelPositionX()== actualToPixel( 0.5*this.getAcceleration()[0]*Math.pow(dt, 2)+ this.getVelocity()[0] *dt +this.getActualPositionX())
	 * 		|new.getActualPositionX()==  0.5*this.getAcceleration()[0]*Math.pow(dt, 2)+ this.getVelocity()[0] *dt +this.getActualPositionX()
	 * 
	 */
	public void velocityChangeXPosition(double dt) {
		double newPosition= 0.5*this.getAcceleration()[0]*Math.pow(dt, 2)+ this.getVelocity()[0] *dt +this.getActualPositionX() ;
		this.setActualPositionX(newPosition);
		this.setMovingPositionXas(actualToPixel(newPosition));
		
	}
	
	/**
	 * Calculate the new Yposition in function of the time, velocity and acceleration
	 * @param dt
	 * the time that elapsed
	 * @effect the new Yposition is equal to 1/2*dt^2*vertical acceleration+dt*vertical velocity+Xposition
	 * 		| new.getPixelPositionY() == actualToPixel(0.5* this.getAcceleration()[1]*Math.pow(dt, 2)+ this.getVelocity()[1] *dt +this.getActualPositionY()
	 * 		| new.getActualPositionY() == 0.5* this.getAcceleration()[1]*Math.pow(dt, 2)+ this.getVelocity()[1] *dt +this.getActualPositionY()
	 */
	public void velocityChangeYPosition(double dt) {
		double newPosition= 0.5* this.getAcceleration()[1]*Math.pow(dt, 2)+ this.getVelocity()[1] *dt +this.getActualPositionY() ;
		this.setActualPositionY(newPosition);
		 if(! hasCollisionWithTopTiles(this.getRectanglePosition(),this.getWorld(),1)) {
			 this.setVerticalAcceleration(V_ACCELERATION_JUMPING);
		 }
		this.setMovingPositionYas(actualToPixel(newPosition));
	}
	
	//##############################################################################
	/**
	 * calculates the new velocity in function of the time,acceleration and the velocity
	 * @param dt
	 * the time that elapsed
	 * 
	 * @post if the new horizontal velocity is legal, the new horizontal velocity is equal to 
	 * horizontal acceleration*dt + horizontal velocity
	 * otherwise, if the horizontal velocity is greater then this.getMaxSpeedHorizontal() 
	 * then it becomes equal to the this.getMaxSpeedHorizontal()
	 * else if Mazub is ducking his horizontal velocity is equal to  this.getMaxSpeedHorizontalDucking()
	 * 		|if (isValidCalcultateHorizontalVelocity(Math.abs((this.horizontalVelocity + getAcceleration()[0]*dt)*this.getOrientation()))
	 * 		| then new.getHorizontalVelocity()== (this.horizontalVelocity + getAcceleration()[0]*dt)*this.getOrientation())
	 * 		| else if(Math.abs((this.horizontalVelocity + getAcceleration()[0]*dt)*this.getOrientation())>this.getMaxSpeedHorizontal())
	 * 		| then new.getHorizontalVelocity()==this.getMaxSpeedHorizontal()
	 * 		| if (this.getDucking() == true)
	 * 		| then new.getHorizontalVelocity()==this.getMaxSpeedHorizontalDucking()
	 * 
	 * @post the new vertical velocity is equal to
	 * vertical acceleration *dt +vertical velocity
	 * 		| new.getVerticalVelocity()== this.getVerticalVelocity() + getAcceleration()[1]*dt
	 * 
	 * @post if Mazub is not moving his horizontal velocity is equal to 0
	 * 		|if (this.getMoving() == false)
	 * 		|new.getHorizontalVelocity()== 0
	 * 
	 */
	public void calculateVelocity(double dt) {
		double absoluteWaardeVelocity ;
		double hVelocity;
		hVelocity=(this.getHorizontalVelocity() + getAcceleration()[0]*dt);
		absoluteWaardeVelocity= Math.abs(hVelocity);
		if(!isValidCalcultateHorizontalVelocity(absoluteWaardeVelocity)) {
			if (this.getDucking() == false) {
				if (absoluteWaardeVelocity > this.getMaxSpeedHorizontal()) {
					this.setHorizontalVelocity(this.getMaxSpeedHorizontal() * this.getOrientation()) ;
				}
			}
			if (this.getDucking() == true) {
				if (absoluteWaardeVelocity > this.getMaxSpeedHorizontalDucking()) {
					this.setHorizontalVelocity(this.getMaxSpeedHorizontalDucking() * this.getOrientation());
				}
			}
		}
		else {
			this.setHorizontalVelocity(hVelocity);
		}
		this.setVerticalVelocity(this.getVerticalVelocity() + getAcceleration()[1]*dt);
		if (this.getMoving() == false) {
			this.setHorizontalVelocity(0);
		}
	}
	
	/**
	 * Check whether the given velocity is a valid velocity for
	 * any Mazub.
	 *  
	 * @param  newVelocity
	 *         The velocity to check.
	 * @return 
	 *       | result == (this.getDucking()==false && newVelocity< this.getMaxSpeedHorizontal())
	*/
	public boolean isValidCalcultateHorizontalVelocity(double newVelocity) {
		return (this.getDucking()==false && newVelocity< this.getMaxSpeedHorizontal());
	}
	
	//##############################################################################

	/**
	 * Check whether the given sprites are valid for any Mazub
	 *  
	 * @param  sprites
	 *         The new Sprites to check.
	 * @return returns true if the total number of sprites is even and greater or equal to 10
	 *       | result == ((newSprites.length%2)==0 &&(newSprites.length>=10)&& newSprites does not contain null )
	*/
	@Override
	public boolean isValidSprites(Sprite[] sprites) {
		if(super.isValidSprites(sprites)) {
			return ( ((sprites.length)%2)==0 &&(sprites.length>=10) );
		}
		return false;
	}

	//##############################################################################

	/**
	 *  changes Mazub position to the given position
	 * 
	 * @param newPosition An array of the x and y position in meters
	 * 
	 * @effect  Mazub new position will be the new position
	 * 		|this.convertToPixel(newPosition[0], newPosition[1]);
	 * @effect terminate all the gameObjects that are in terminatedObjects
	 * 		|  this.terminator
	 * @effect if Mazub is not on the top of an object his vertical acceleration will be set to -10
	 * 	otherwise it becomes 0
	 * 		|if (! hasCollisionWithTopTiles(this.getRectanglePosition(),this.getWorld())) then
	 * 		|	this.setVerticalAcceleration(V_ACCELERATION_JUMPING);
	 * 		|else then
	 * 		|	this.setVerticalAcceleration(0);
	 * @throws IllegalArgumentException
	 * 		if you give more than 2 values or if you give null as value it throws an exception
	 * 		| if (newPostion == null || newPosition.length !2)
	 * @throws IllegalStateException
	 * 		| ImpassableTerrain(this.getRectanglePosition())
	 */
	@Override
	public void changePosition(double[] newPosition) throws IllegalArgumentException,IllegalStateException {
		if (newPosition == null) {
			throw new IllegalArgumentException("position can't be null");
		}
		if (newPosition.length != 2) {
			throw new IllegalArgumentException("you can only give 2 values");
		}
		this.convertToPixel(newPosition[0], newPosition[1]);
		if (! hasCollisionWithTopTiles(this.getRectanglePosition(),this.getWorld(),1)) {
			this.setVerticalAcceleration(V_ACCELERATION_JUMPING);
		}
		else {
			this.setVerticalAcceleration(0);
		}
		if (ImpassableTerrain(this.getRectanglePosition())) {
			throw new IllegalStateException("Mazub in impassable terrain");
		}
		this.terminator(this.getWorld());
	}
	
	//##############################################################################
	
	/**
	 * Return the notMovingTimer of this Mazub
	 */
	@Basic
	public double getNotMovingTimer() {
		return this.notMovingTimer;
	}
	/**
	 * Set the notMovingTimer to the new value
	 * @param time
	 * the new time
	 * @Post
	 * if the given time is valid  the notMovingTimer becomes equal to Time
	 * 		| new.getNotMovingTimer== time
	 */
	public void setNotMovingTimer(double time) {

		this.notMovingTimer=time;
	}
	
	/**
	 * Variable registering the time that passed while Mazub isn't moving
	 */
	private double notMovingTimer = 0;
	//##############################################################################
	
	/**
	 * change a consecutively flow of sprites
	 */
	private void makeSpriteRun(int startingSprite,int endingSprite) {
			if (this.getCurrentSpriteNumber()<startingSprite || this.getCurrentSpriteNumber()>=endingSprite) {
				this.setCurrentSpriteNumber(startingSprite);
			}
			else {
				this.setCurrentSpriteNumber(this.getCurrentSpriteNumber()+1);
			}
		
	}
	/**
	 * This Method decides which Sprite to  use in which situation
	 * @post if Mazub is ducking and standing still the correct sprite is used
	 * otherwise, the ducking sprite in the correct orientation is used
	 * 		| if(this.getDucking()==true) then
	 * 		| 	if(this.getOrientation()==-1) then
	 * 		|		new.getCurrentSprite() == this.sprites[7]
	 *   	| 	if(this.getOrientation()==0) then
	 * 		|		new.getCurrentSprite() == this.sprites[1]
	 *    	|	 if(this.getOrientation()==1) then
	 * 		|		new.getCurrentSprite() == this.sprites[6]
	 * 
	 * @post if Mazub is jumping and standing still the correct sprite is used
	 * otherwise, the jumping sprite in the correct orientation is used
	 * 		|else  if(this.getJumping() == true) then
	 * 		|	if(this.getOrientation()==-1) then
	 * 		|			new.getCurrentSprite() == this.sprites[5]
	 * 		|	if(this.getOrientation()==0) then
	 * 		|		new.getCurrentSprite() == this.sprites[0]
	 *    	|	if(this.getOrientation()==1) then
	 *    	|		new.getCurrentSprite() == this.sprites[4]
	 *    
	 * @Post if Mazub just stopped moving he will be oriented to the correct orientation
	 * 		| else if (this.getMoving()==false && this.getOrientation()!=0) then
	 * 		|	if(this.getOrientation()==-1) then
	 * 		|			new.getCurrentSprite() == this.sprites[3]
	 *    	|	if(this.getOrientation()==1) then
	 *    	|		new.getCurrentSprite() == this.sprites[2]
	 *    
	 * @Post use the correct loop of sprites while Mazub is running to the correct orientation
	 *    	| else if(this.getMoving()==true) then
	 *    	|		if(this.getOrientation()==-1)
	 *    	|			if(this.getCurrentSpriteNumber<=sprites.length-1 && this.getSpriteNumber>=9+((sprites.length-8)/2)-1)
	 *    	|					then new.getCurrentSpriteNumber() == this.getCurrentSpriteNumber()+1
	 *    	|			else then new.getCurrentSpriteNumber() == 9+((sprites.length-8)/2)-1
	 *    	|		if(this.getOrientation()==1) then
	 *   	|			 if(this.getCurrentSpriteNumber()<=((sprites.length-8)/2)+7 && this.getSpriteNumber()>=8
	 *    	|					then new.getCurrentSpriteNumber() == this.getCurrentSpriteNumber()+1
	 *    	|			else then new.getCurrentSpriteNumber() == 8
	 *    
	 * @Post if Mazub is none of the above, he is standing still
	 *    	|else the
	 *    	| new.getCurrentSpriteNumber() == 0			
	 * @throws ModelException
	 */
	public void useCorrectSprite() {
		if (this.getDucking()==true ) {
			switch(this.getOrientation()) {
			case -1:this.setCurrentSpriteNumber(7);
					break;
			case 0: this.setCurrentSpriteNumber(1);
					break;
			case 1: this.setCurrentSpriteNumber(6);
					break;
			}
		}
		else if (this.getJumping() ==true) {
				switch(this.getOrientation()) {
				case -1:this.setCurrentSpriteNumber(5);
						break;
				case 0 : this.setCurrentSpriteNumber(0);
						break;
				case 1: this.setCurrentSpriteNumber(4);
						break;
				}
			}
		else if (this.getMoving()==false && this.getOrientation()!=0) {
			if (this.getOrientation()==-1) {
				this.setCurrentSpriteNumber(3);
			}
			if (this.getOrientation()==1) {
				this.setCurrentSpriteNumber(2);
			}
		}
		else if (this.getMoving()==true) {
			switch(this.getOrientation()) {
			case -1: this.makeSpriteRun(9+((sprites.length-8)/2)-1,sprites.length-1);
					 break;
			case 1: this.makeSpriteRun(8,((sprites.length-8)/2)+7);
					break;
			}
		}
		else {
			this.setCurrentSpriteNumber(0);
		}
	}
	/**
	 * Uses the correct Sprites of Mazub in function of the total time that passed
	 * @param dt
	 * 	The time that passed
	 * @effect use the correct sprite every 75ms if mazub is standing straigth and is moving, change the not moving timer
	 * 	|this.setSpriteTime(dt+this.getSpriteTime());
	 * 	|if (this.getMoving()==true&&!( this.getDucking()==true || this.getJumping()==true)) then
	 *  |	while(this.getSpriteTime()>0.075) then
	 *	|		this.useCorrectSprite();
	 *	|		this.setSpriteTime(this.getSpriteTime()-0.075);	
	 *  |new.getSprite == this.useCorrectSprite();
	 * 	|	if (this.getMoving()==false && this.getOrientation()!=0) then
	 * 	|		new.getNotMovingTimer == this.getNotMovingTimer()+dt
	 * 	|		if (this.getNotMovingTimer()>=1) then
	 * 	|			new.getCurrentSpriteNumber==0
	 *  |			new.getOrientation == 0
	 *  |			new.getMovingTimer == 0
	 *  |	else then
	 *  |		new.getNotMovingTimer==0
	 */
	public void advanceSprites(double dt) {
		this.setSpriteTime(dt+this.getSpriteTime());
		if (this.getMoving()==true&&!( this.getDucking()==true || this.getJumping()==true)) {
			while(this.getSpriteTime()>0.075) {
				this.useCorrectSprite();
				this.setSpriteTime(this.getSpriteTime()-0.075);
			}
		}
		else {
			this.useCorrectSprite();
			this.setSpriteTime(0);
		}
		
		if (this.getMoving()==false && this.getOrientation()!=0) {
			this.setNotMovingTimer(this.getNotMovingTimer()+dt);
			if (this.getNotMovingTimer()>=1) {
				if(this.getDucking()) {
					this.setCurrentSpriteNumber(1);
				}
				else {
					this.setCurrentSpriteNumber(0);
				}
				this.setOrientation(0);
				this.setNotMovingTimer(0);
			}
		}
		else {
			this.setNotMovingTimer(0);
		}
	}
	

	//##############################################################################
	/**	 
	 * advance the time for this Mazub
	 * @param dt
	 * 		The time that passed
	 * @effect if the given mazub is a dead object then the deadtimer will be advanced
	 * 		|if(this.isDeadGameobject()) then
	 * 		| 	advanceDeadTimer(dt);
	 * @effect if the given mazub is still alive then  living mazub methods will be called
	 * 		|	this.advanceTimeLivingMazub(dt, world);
	 * @throws IllegalArgumentException
	 * 		|	Double.isNaN(dt)
	 */
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
				this.advanceTimeLivingMazub(dt, world);
			}	
			
		}
	}
	
//##############################################################################
	/**
	 * handels the collision of Mazub
	 * @param dt
	 * 		the time
	 * @effect if this Mazub has a world
	 *  this function calculates everything collision-based involving this Mazub
	 * 		|this.collisionWithAllPossibleTilesOfWorld(this.getRectanglePosition());
	 *		|this.MazubHasFallenInMagmaChecker();
	 *		|this.collisionWithElement(dt);
	 *		|this.hasCollisionWithOtherGameObjects();
	 *		|this.hasCollisionWithTargetTile()
	 */
	public void advanceCollisionMazub(double dt) {
		if (this.getWorld()!=null) {
			resetTakeDamageFromGeologicalFeature();
			this.timeRemoveContact(dt);
			this.calculateCollisionWithImpactValue();
			this.geologicalFeatureDamageTimer(dt);
			this.hasCollisionWithTargetTile();
		}
	}
	/**
	 * calulates the x , yposition of Mazub and the velocity
	 * @param dt
	 * 		the time that passed
	 * @effect this function calculates anything velocity-based of this Mazub.
	 * 			|this.velocityChangeXPosition(dt);
	 *			|this.velocityChangeYPosition(dt);
	 *			|this.calculateVelocity(dt);
	 */
	public void advanceVelocityAndPositionMazub(double dt) {
		this.velocityChangeXPosition(dt);
		this.velocityChangeYPosition(dt);
		this.calculateVelocity(dt);
	}
	
	/**
	 * This function divides the time pieces 
	 * so that in every time interval Mazub only moves 1 pixel
	 * @param dt
	 *  it returns the timer if Mazub is not moving
	 * 		|if(this.getHorizontalVelocity()==0 && this.getVerticalVelocity()==0&& this.getHorizontalAcceleration()==0&& this.getVerticalAcceleration()==0) == true
	 * 		|	return dt
	 * 		|else
	 * 		|	return getTimeDiference(dt)
	 */
	
	public double calculateCollisionTimer(double dt) {
		if(this.getHorizontalVelocity()==0 && this.getVerticalVelocity()==0&& this.getHorizontalAcceleration()==0&& this.getVerticalAcceleration()==0) {
			 return dt;
		}
		else {
			 return getTimeDiference(dt);
		}
	}
	
	/**
	 * The methods used for mazub  while advancing time
	 * @param dt
	 * 		the given time
	 * @effect this function calculates the sprites and the position/velocity of Mazub
	 * 			|this.advanceSprites(dt)
	 *			|this.advanceVelocityAndPositionMazub(dt)
	 *@effect if this Mazub has a world he also checks if he has collision, centers the visual window
	 *	and terminate itself when he is out of the borders
	 *			|if(this.getWorld()!=null) 
	 *			|	this.advanceCollisionMazub(dt)
	 *			|	this.centerVisualWindow()
	 *			|	this.terminateOutOfBorders()
	 */
	
	public void methodsForAdvanceTime(double dt) {
		this.advanceSprites(dt);
//		this.mazubWantToStandStraigthCeiling();
		this.advanceVelocityAndPositionMazub(dt);
		if(this.getWorld()!=null) {
			this.advanceCollisionMazub(dt);
			this.centerVisualWindow();
			this.terminateOutOfBorders();
		}

	}
	/**
	 * Advances time for Mazub when he is dead
	 * @param dt
	 * 		the time that passed
	 * @effect this function removes dead objects after 0.6 sec and adds them to the terminated objects
	 *			|this.new.getHasBeenDeadFor == dt+this.getHasBeenDeadFor()
	 *			|if(this.getHasBeenDeadFor()>=0.6) 
	 *			|	this.getWorld().addAsTerminatedObjects(this)
	 */
	
	public void advanceDeadTimer(double dt) {
		
			this.setHasBeenDeadFor(dt+this.getHasBeenDeadFor());
			if(this.getHasBeenDeadFor()>=0.6) {
				this.getWorld().addAsTerminatedObjects(this);
			}
	}
	
	/**
	 * Advances time for a living Mazub
	 * @param dt
	 * 		the given time
	 * @param world
	 * 		the world of Mazub
	 * @effect if this Mazub has a world he first checks if he has collision with another object or any tiles
	 * and checks if mazub is standing on the ground
	 * 			|if (this.getWorld()!=null) then
	 *			|	if(!hasCollisionWithTopTiles(this.getRectanglePosition(), this.getWorld(),1))
	 *			| 		new.getVerticalAcceleration==V_ACCELERATION_JUMPING
	 *			|	this.calculateCollisionWithImpactValue()
	 * 
	 * @effect this function advancestime for a libing mazub by first cutting the dt in smaller intervals
	 *			|for(double i =collisionTimer;i<=dt; i+=collisionTimer ) 
	 *			|	this.methodsForAdvanceTime(collisionTimer);
	 *			|	remainder=remainder-collisionTimer;
	 *			|if (remainder>0) then
	 *			|	this.methodsForAdvanceTime(remainder)
	 */
	public void advanceTimeLivingMazub(double dt , World world) {
		double remainder=dt; 
		double collisionTimer;
		if (this.getWorld()!=null) {
			if(!hasCollisionWithTopTiles(this.getRectanglePosition(), this.getWorld(),1)) {
				this.setVerticalAcceleration(V_ACCELERATION_JUMPING);
			}
			this.calculateCollisionWithImpactValue();
			
		}
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
	
	//##############################################################################
	
	/**
	 * This Mazub takes damage from the geological feature that he is in.
	 * 
	 * @effect if the Mazub has collision with water and with no other damaging tile he
	 * takes 2 damage
	 * 		| if(this.getTakeDamageFromGeologicalFeature()==GeologicalFeatures.WATER) then
	 * 		|		this.addHitpoints(-2)
	 * 
	 * @effect if the Mazub has collision with gas and not with magma he takes 4 damage
	 * 		|if(this.getTakeDamageFromGeologicalFeature()==GeologicalFeatures.GAS)  then
	 * 		|	this.addHitpoints(-4)
	 * 
	 * @effect if the Mazub has collision with magma he takes 50 damage
	 * 		|if(this.getTakeDamageFromGeologicalFeature()==GeologicalFeatures.MAGMA)
	 * 		|	this.addHitpoints(-50)
	 */ 
	@Override
	public void takesDamageFromGeologicalFeature() {
		if(this.getTakeDamageFromGeologicalFeature()==GeologicalFeatures.WATER) {
			this.addHitpoints(-2);
		}
		if(this.getTakeDamageFromGeologicalFeature()==GeologicalFeatures.GAS) {
			this.addHitpoints(-4);
		}
		if(this.getTakeDamageFromGeologicalFeature()==GeologicalFeatures.MAGMA) {
			this.addHitpoints(-50);
		}
	}

	//##############################################################################
	
	
	/**
	 *change the current sprite to the given value
	 *
	 * @param spriteNumber
	 *  the index of the new actual sprite
	 *  @post change the new currentSpriteNumber to the given value if the sprite is valid
	 *  	|	new.getCurrentSpriteNumber=spriteNumber
	 *  @post if the sprite overlaps with impassable terrain then Mazub keeps his oldSprite
	 *  	| if(this.ImpassableTerrain(this.getRectanglePosition())|| this.collisionWithOtherGameObjects(this.getRectanglePosition())) then
	 *  	|	new.currentSpriteNumber=oldSpriteNumber
	 *  @throws IllegalArgumentException
	 *  	if the given SpriteNumber is out of range of the array this throws an exception
	 *  
	 */
	@Raw
	public void setCurrentSpriteNumber(int spriteNumber) throws IllegalArgumentException{
		if(sprites.length<spriteNumber && spriteNumber<0) {
			throw new IllegalArgumentException("CurrentSpriteNumber out of Range");
		}
		int oldSpriteNumber= this.getCurrentSpriteNumber();
		this.currentSpriteNumber=spriteNumber;
		if(this.ImpassableTerrain(this.getRectanglePosition())|| this.collisionWithOtherGameObjects(this.getRectanglePosition())) {
			this.currentSpriteNumber=oldSpriteNumber;
		}
	}
	/**
	 * returns the index of the current Sprite of Mazub
	 */
	@Basic @Raw
	public int getCurrentSpriteNumber() {
		return this.currentSpriteNumber;
	}
	/**
	 * Returns the CurrentSprite of Mazub
	 */
	@Override
	public Sprite getCurrentSprite() {
		return this.sprites[this.getCurrentSpriteNumber()];
	}
	
	/**
	 * variable registering the current index of Mazub's sprites
	 */
	private int currentSpriteNumber;
	//##############################################################################

	/**
	 * Returns the spritetime of Mazub
	 */
	@Basic @Raw
	public double getSpriteTime() {
		return this.spriteTime;
	}
	/**
	 * Set the spriteTime to the new value
	 * @param time
	 * the new time
	 * @Post
	 * if the given time is valid  the spriteTime becomes equal to Time
	 * 		| new.getNotMovingTimer== time
	 * @throws throws an exception if the timer is negative
	 * 		(time<0)
	 */
	 @Raw
	public void setSpriteTime(double time) throws ModelException{
		if (time<0) {
			throw new ModelException("Time can't be negative");
		}
		this.spriteTime =time;
	}
	
	/**
	 * Variabele registering the time that has passed in function of calculating Mazub's current Sprite
	 */
	private double spriteTime=0;

	//##############################################################################
	
	/**
	* Set the hitpoints of the gameobject to the given value
	* @param hitpoints
	* 	the new hitpoints of this GameObject
	* @post if the hitpoints are above 500 they will be equal to 500
	* 		|if(hitpoints>500) then
	* 		|	this.hitpoints=500
	 */
	@Override
	public void setHitpoints(int hitpoints) {
		super.setHitpoints(hitpoints);
		if(hitpoints>MAX_HEALTH) {
			this.hitpoints=MAX_HEALTH;
		}
		
	}
	

	//##############################################################################
	
	/**
	 * this is a function that divides the time that has been given into smaller parts so it's more accurate
	 * @param dt
	 * 		the given time
	 * @post use the formula  to calculate new time intervals
	 * 		|double collisionTimer= 0.01/
	 *		|	(Math.sqrt(Math.pow(this.getHorizontalVelocity(), 2)+Math.pow(this.getVerticalVelocity(), 2))
	 *		|	+(Math.sqrt(Math.pow(getHorizontalAcceleration(),2)+Math.pow(getVerticalAcceleration(),2) ))*dt)
	 * @return 
	 * 		result==collisiontimer
	 */
	
	public double getTimeDiference(double dt) {
		double collisionTimer= 0.01/
				(Math.sqrt(Math.pow(this.getHorizontalVelocity(), 2)+Math.pow(this.getVerticalVelocity(), 2))
				+(Math.sqrt(Math.pow(getHorizontalAcceleration(),2)+Math.pow(getVerticalAcceleration(),2) ))*dt);
		return collisionTimer;
	}
	

	/**
	 * handels the collision of mazub when he collides with other gameobjects
	 * 
	 * @effect if the other gameobject is a shark, and both the shark and Mazub
	 * are still alive Mazub loses 50 hitpoints. If Mazub has impact with the given object.
	 * 		|if(gameObject instanceof Shark) then
	 * 		|	if(shark.getHitPoints()>0 &&this.getHitPoints()>0)then
	 * 		|		this.addHitpoints(-50)
	 * 
	 * @effect if the other gameobject is a slime and both the slime and Mazub are still alive
	 * Mazub loses 20 hitpoints.If Mazub has impact with the given object.
	 * 		|if(gameObject instanceof Slime) then
	 * 		|	if(slime.getHitPoints()>0&& this.getHitPoints()>0)
	 * 		|		this.addHitpoints(-20)
	 * 
	 * @effect if the other gameobject is a plant then Mazub will eat the plant.If Mazub has impact with the given object.
	 * 		|if(gameObject instanceof Plant) 
	 * 		|	collisionWithPlant(plant)
	 * 
	 * @effect add the given object to the given the impact set
	 * 		|	this.changeImpactValue(gameObject, 2)
	 */	
	@Override
	protected void collisionExtraEffect(Object other) {
		if(other instanceof GameObject) {
			GameObject gameObject= (GameObject) other;
			if(! hasImpactWithGameObject(gameObject)) {
					if(gameObject instanceof Shark) {
						Shark shark = (Shark) gameObject;
						if(shark.getHitPoints()>0 &&this.getHitPoints()>0) {
							this.addHitpoints(-50);
						}
					}
				if(gameObject instanceof Slime) {
					Slime slime = (Slime) gameObject;
					if(slime.getHitPoints()>0&& this.getHitPoints()>0) {
						this.addHitpoints(-20);
					}
				}
				if(gameObject instanceof Plant) {
					Plant plant= (Plant) other;
						collisionWithPlant(plant);
				}
				this.changeImpactValue(gameObject, 2);
			}
			else {
				this.changeImpactValue(gameObject, 2);
			}
		}
	}

	
	
	/**
	 * Deals with Mazub that has collision with water
	 * @param rectanglePositionThisObject
	 * 		this Mazub his rectangleposition
	 * @param rectanglePositionTiles
	 * 		this rectangle position of the tile
	 * @effect if Mazub is not only colliding the tile with his top perimeter, the water could be the damaging
	 * tile of mazub (if there are no other damaging tiles higher in the hierarchy)
	 * 		|if(! hasOnlyCollisionWithTop(rectanglePositionThisObject, rectanglePositionTiles)) then
	 * 		|	this.setTakeDamageFromGeologicalFeature(GeologicalFeatures.WATER)
	 */
	@Override
	protected void collisionWithWater(int[] rectanglePositionThisObject, int[] rectanglePositionTiles) {
		if(! hasOnlyCollisionWithTop(rectanglePositionThisObject, rectanglePositionTiles)) {
			this.setTakeDamageFromGeologicalFeature(GeologicalFeatures.WATER);
		}
		
	}
	
	/**
	 * Deals with Mazub that has collision with magma
	 * @param rectanglePositionThisObject
	 * 		this Mazub his rectangleposition
	 * @param rectanglePositionTiles
	 * 		this rectangle position of the tile
	 * @effect if Mazub is not only colliding the tile with his top perimeter, the magma becomes the damage
	 * tile of mazub (if there are no other damaging tiles higher in the hierarchy). Mazub will lose also takes 50 damage
	 * if he wasn't already standing in magma
	 * 		|if(! hasOnlyCollisionWithTop(rectanglePositionThisObject, rectanglePositionTiles)) then
	 * 		|	if(this.getPreviousTakeDamageFromGeologicalFeature()!=GeologicalFeatures.MAGMA&&
	 * 		|	this.getTakeDamageFromGeologicalFeature()!=GeologicalFeatures.MAGMA) then	
	 * 		|		this.addHitpoints(-50)
	 * 		|	new.getTakeDamageFromGeologicalFeature==GeologicalFeatures.MAGMA
	 */
	@Override
	protected void collisionWithMagma(int[] rectanglePositionThisObject, int[] rectanglePositionTiles) {
		if(! hasOnlyCollisionWithTop(rectanglePositionThisObject, rectanglePositionTiles)) {
			if(this.getPreviousTakeDamageFromGeologicalFeature()!=GeologicalFeatures.MAGMA&&
				this.getTakeDamageFromGeologicalFeature()!=GeologicalFeatures.MAGMA) {
				this.addHitpoints(-50);
			}
			this.setTakeDamageFromGeologicalFeature(GeologicalFeatures.MAGMA);
		}
	}
	
	/**
	 * Deals with Mazub that has collision with gas
	 * @param rectanglePositionThisObject
	 * 		this Mazub his rectangleposition
	 * @param rectanglePositionTiles
	 * 		this rectangle position of the tile
	 * @effect if Mazub is not only colliding the tile with his top perimeter, the gas could be the damaging
	 * tile of mazub (if there are no other damaging tiles higher in the hierarchy). If this Mazub wasn't already standing in Gas
	 * he will take 4 damage.
	 * 		|if(! hasOnlyCollisionWithTop(rectanglePositionThisObject, rectanglePositionTiles)) then
	 * 		|	if(this.getPreviousTakeDamageFromGeologicalFeature()!=GeologicalFeatures.GAS&&
	 * 		|	this.getTakeDamageFromGeologicalFeature()!=GeologicalFeatures.GAS)
	 * 		|		this.addHitpoints(-4)
	 * 		|	this.setTakeDamageFromGeologicalFeature(GeologicalFeatures.GAS)
	 */
	@Override
	protected void collisionWithGas(int[] rectanglePositionThisObject, int[] rectanglePositionTiles) {
		if(! hasOnlyCollisionWithTop(rectanglePositionThisObject, rectanglePositionTiles)) {
			if(this.getPreviousTakeDamageFromGeologicalFeature()!=GeologicalFeatures.GAS&&
					this.getTakeDamageFromGeologicalFeature()!=GeologicalFeatures.GAS) {
				this.addHitpoints(-4);
			}
			this.setTakeDamageFromGeologicalFeature(GeologicalFeatures.GAS);
		}
	}


	//##############################################################################
	/**
	 *  this function centers the visual window of the world around this Mazub if he
	 *  is the playable mazub
	 *  	
	 *  @post function that centers the window around the playable current Mazub.
	 *  	|if (this == world.getMazub()) then
	 *		|	int [] visualWindowPosition =  calculateWindowPosition(world)
	 *		|	world.getVisualWindowPosition()==visualWindowPosition
	 */
	public void centerVisualWindow() {
		World world=this.getWorld();
		if (this == world.getMazub()) {
			int [] visualWindowPosition =  calculateWindowPosition(world);
			world.setVisualWindowPosition(visualWindowPosition);
		}
		
	}
	
	/**
	 * calculates the optimas window position arround Mazub
	 * @param world
	 * 		the given world
	 * @effect this function calculates the position of the window in the current world
	 * 
	 * 		|int[] rectanglePosition= this.getRectanglePosition();
	 *		|int centerWidth= ((rectanglePosition[0]+(rectanglePosition[2]/2))-(world.getVisualWindowWidth()/2))
	 *		|if (centerWidth<0) then
	 *		|	centerWidth=0
	 *		|if(centerWidth>world.getnbTilesX()*world.getTileSize()-world.getVisualWindowWidth()) then
	 *		|	centerWidth= world.getnbTilesX()*world.getTileSize()-world.getVisualWindowWidth()
	 *		|int centerHeigth = ((rectanglePosition[1]+(rectanglePosition[3]/2))-(world.getVisualWindowHeigth()/2))
	 *		|if (centerHeigth<0) then
	 *		|	centerHeigth=0
	 *		|if(centerHeigth>world.getnbTilesY()*world.getTileSize()-world.getVisualWindowHeigth()) then
	 *		|	centerHeigth= world.getnbTilesY()*world.getTileSize()-world.getVisualWindowHeigth()
	 *
	 * @return the position of the window
	 * 		|return new int[] {centerWidth, centerHeigth}
	 */
	
	public int[] calculateWindowPosition(World world) {
		int[] rectanglePosition= this.getRectanglePosition();
		int centerWidth= ((rectanglePosition[0]+(rectanglePosition[2]/2))-(world.getVisualWindowWidth()/2));
		if (centerWidth<0) {
			centerWidth=0;
		}
		if(centerWidth>world.getnbTilesX()*world.getTileSize()-world.getVisualWindowWidth()) {
			centerWidth= world.getnbTilesX()*world.getTileSize()-world.getVisualWindowWidth();
		}
		int centerHeigth = ((rectanglePosition[1]+(rectanglePosition[3]/2))-(world.getVisualWindowHeigth()/2));
		if (centerHeigth<0) {
			centerHeigth=0;
		}
		if(centerHeigth>world.getnbTilesY()*world.getTileSize()-world.getVisualWindowHeigth()) {
			centerHeigth= world.getnbTilesY()*world.getTileSize()-world.getVisualWindowHeigth();
		}
		return new int[] {centerWidth, centerHeigth};
	}
	//##############################################################################



	

	/**
	 * Deals with Mazub who has collision with an impassable object
	 * @param rectanglePosition1
	 * 		the rectangleposition of Mazub
	 * @param rectanglePosition2
	 * 		the rectangle position of the other object
	 * @param tiles
	 * 	is 1 when Mazub has collision with a tile, otherwise it is 0
	 * 
	 * @effect this function checks if Mazub has collision with impassable terrain below him and sets his vertical velocity to 0 if he has.
	 *			|if (rectanglePosition2[1]+rectanglePosition2[3]-tiles==rectanglePosition1[1] 
	 *			|&& rectanglePosition2[0]+rectanglePosition2[2]-tiles>rectanglePosition1[0]
	 *			|&& rectanglePosition2[0]<rectanglePosition1[0]+rectanglePosition1[2]-tiles)  
	 *			|if (this.getVerticalVelocity()<0) 
	 *			|this.new.getVerticalVelocity == 0
	 *			|if(this.getVerticalAcceleration()<0) 
	 *			|this.new.getVerticalAcceleration == 0
	 *
	 *@effect this function checks if Mazub has collision with impassable terrain above him and sets his vertical velocity to 0 if he has.
	 *			|if (rectanglePosition1[1]+rectanglePosition1[3]==rectanglePosition2[1])
	 *			|if (this.getVerticalVelocity()>0) 
	 *			|this.new.getVerticalVelocity == 0
	 *
	 *@effect this function checks if Mazub has collision with impassable terrain behind him and sets his horizontal velocity to 0 if he has.
	 *			|if(rectanglePosition2[0]+rectanglePosition2[2]==rectanglePosition1[0]
	 *			|&& rectanglePosition2[1]+rectanglePosition2[3]-tiles>rectanglePosition1[1])
	 *			|if (this.getOrientation()==-1) 
	 *			|this.new.getHorizontalVelocity == 0
	 *			|this.new.getHorizontalAcceleration == 0
	 *			|if( this.getMoving()==true) 
	 *			|this.endMove()
	 *
	 *@effect this function checks if Mazub has collision with impassable terrain in front of him and sets his horizontal velocity to 0 if he has.
	 * 			|if (rectanglePosition2[0]==rectanglePosition1[0]+rectanglePosition1[2]
	 *			|&&rectanglePosition2[1]+rectanglePosition2[3]-tiles>rectanglePosition1[1] ) 
	 *			|if (this.getOrientation()==1) 
	 *			|this.new.getHorizontalVelocity == 0
	 *			|this.new.getHorizontalAcceleration == 0
	 *			|if( this.getMoving()==true) 
	 *			|this.endMove()
	 */
	public void collisionWithImpassableTerrain(int[]rectanglePosition1, int[] rectanglePosition2, int tiles) {
		
		if (rectanglePosition2[1]+rectanglePosition2[3]-tiles==rectanglePosition1[1] 
				&& rectanglePosition2[0]+rectanglePosition2[2]>rectanglePosition1[0]
						&& rectanglePosition2[0]<rectanglePosition1[0]+rectanglePosition1[2])  {
			if (this.getVerticalVelocity()<0) {
				this.setVerticalVelocity(0);
			}
			if(this.getVerticalAcceleration()<0) {
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
				if( this.getMoving()==true) {
					this.endMove();
				}
			}
		}
		else if (rectanglePosition2[0]==rectanglePosition1[0]+rectanglePosition1[2]
				&&rectanglePosition2[1]+rectanglePosition2[3]-tiles>rectanglePosition1[1] ) {
			if (this.getOrientation()==1) {
				this.setHorizontalVelocity(0);
				this.setHorizontalAcceleration(0);
				if( this.getMoving()==true) {
					this.endMove();
				}
			}
		}
	}
	
	
	
	//##############################################################################
	
	/**
	 * Deals with Mazub who has collision with a plant
	 * @param plant
	 * 		the given plant
	 * @effect if the plant is not terminated , mazub his hitpoints aren't greater then 500 and 
	 * Mazub doesn't have any impact with the given plant, Mazub will gain 50 hitpoints and the plant loses 1
	 * 		|if(! plant.getWorld().hasAsTerminatedObjects(plant)&& ! (this.getHitPoints()==500)&& !hasImpactWithGameObject(plant)) then
	 * 		|	if(! plant.isDeadGameobject()) then
	 * 		|		this.mazubCollisionWithPlant(50);
	 * 		|		plant.setHitpoints(plant.getHitPoints()-1)
	 * 		|		if (plant.getHitPoints()<=0) then
	 * 		|			plant.getWorld().addAsTerminatedObjects(plant)
	 * 
	 * @effect if the plant is dead mazub will lose 20 hitpoints instead
	 * 		|else
	 * 		|	this.mazubCollisionWithPlant(-20)
	 * 		|	plant.setHitpoints(plant.getHitPoints()-1)
	 * 		|	if (plant.getHitPoints()<=0) then
	 * 		|		plant.getWorld().addAsTerminatedObjects(plant)
	 * 
	 * 
	 * 
	 *
	 */
	
	public void collisionWithPlant(Plant plant) {
		if(! plant.getWorld().hasAsTerminatedObjects(plant)&& ! (this.getHitPoints()==500)&& !hasImpactWithGameObject(plant)) {
			if(! plant.isDeadGameobject()) {
				this.mazubCollisionWithPlant(50);
				plant.setHitpoints(plant.getHitPoints()-1);
				if (plant.getHitPoints()<=0) {
					plant.getWorld().addAsTerminatedObjects(plant);
				}
			}
			else  {
				this.mazubCollisionWithPlant(-20);
				plant.setHitpoints(plant.getHitPoints()-1);
				if (plant.getHitPoints()<=0) {
					plant.getWorld().addAsTerminatedObjects(plant);
				}
		}
		}
	
	}
	//##############################################################################
	/**
	 * If Mazub stopped ducking while he can't he will automaticaly stands up when he can
	 * 
	 * @post this checks if Mazubs wants to stand up and if he does, then he stops ducking.
	 * 		|if(this.getmazubWantToStandUp()) 
	 *		|this.endDuck()
	 *		
	 */
	
	public void mazubWantToStandStraigthCeiling() {
		if(this.getmazubWantToStandUp()) {
			this.endDuck();
		}
	}
	//##############################################################################
	
	/**
	 * checks if this Mazub has collision with the bottom of another object
	 * 
	 * @effect this function checks if Mazub has collision with the bottom of an other object
	 * 			|if (gameObject!=this ) 
	 *			|if(collisionWithBottom(this.getRectanglePosition(), gameObject.getRectanglePosition())) 
	 *
	 * @return it returns true or false based on if the collision is true or not
	 * 			|if(collisionWithBottom(this.getRectanglePosition(), gameObject.getRectanglePosition()) == true) 
	 *			|return true
	 *			|
	 *			|else
	 *			|return false
	 */
	
	public boolean hasCollisionWithBottomOtherObject() {
		World world= this.getWorld();
	
		for (GameObject gameObject:world.getGameObjects()) {
			if (gameObject!=this ) {
				if(collisionWithBottom(this.getRectanglePosition(), gameObject.getRectanglePosition())) {
					return true;
				}
	
			}
		}
		return false;
	}
	
	/**
	 * Checks if the first object has collision with the bottom of the other object
	 *
	 * @return this function returns if Mazub has collision with the bottom of an other object or not.
	 *			|result ==(rectanglePosition1[1]+rectanglePosition1[3]-1 >= rectanglePosition2[1] 
	 *			|&& rectanglePosition2[1]>rectanglePosition1[1]
	 *			|&& rectanglePosition1[0]+rectanglePosition1[2]-1>rectanglePosition2[0]
	 *			|&& rectanglePosition1[0]<rectanglePosition2[0]+rectanglePosition2[2]-1) 
	 *
	 */
	
	public boolean collisionWithBottom(int[] rectanglePosition1, int[]rectanglePosition2) {
		int i= rectanglePosition1[1]+4;
		return (rectanglePosition1[1]+rectanglePosition1[3]-1 >= rectanglePosition2[1] 
				&& rectanglePosition2[1]>rectanglePosition1[1]
						&& rectanglePosition1[0]+rectanglePosition1[2]-1>rectanglePosition2[0]
								&& rectanglePosition1[0]<rectanglePosition2[0]+rectanglePosition2[2]-1) ;
	}
	
	//##############################################################################
	
	/**
	 * 
	 * @param rectanglePosition
	 * 
	 * 
	 * @effect this function checks if Mazub has collision with an other game object or not.
	 * 			|if (gameObject!=this && gameObject instanceof Mazub ) 
	 *			|if( ! hasColissionWithinnerLayers(rectanglePosition, this.getRectanglePosition()))
	 * 
	 * @return this returns true or false based on if there is collision or not
	 * 			|if (gameObject!=this && gameObject instanceof Mazub ) 
	 *			|if( ! hasColissionWithinnerLayers(rectanglePosition, this.getRectanglePosition())) 
	 *			|return true
	 *			|
	 *			|else
	 *			|return false
	 */
	public boolean collisionWithOtherGameObjects(int[] rectanglePosition) {
		World world= this.getWorld();
		if(world!=null) {
			for (GameObject gameObject:world.getGameObjects()) {
				if (gameObject!=this && gameObject instanceof Mazub ) {
					if( ! hasColissionWithinnerLayers(rectanglePosition, this.getRectanglePosition())) {
						return true;
					}
				}
			}
			return false;
		}return false;
	}
	//##############################################################################
	
	/**
	 * Checks if Mazub has collision withe the target tile, if he is he will warn
	 * the world
	 * 
	 * @effect this function checks if Mazub has collision with the targetTile.
	 * 			|if (this.getWorld().getMazub()==this) {
	 *			|int[] rectangleTargetTile= new int[] { this.getWorld().getTargetTile()[0]*this.getWorld().getTileSize(), this.getWorld().getTargetTile()[1]*this.getWorld().getTileSize(),this.getWorld().getTileSize(),this.getWorld().getTileSize()};
	 *			|if (hasColissionWithTiles(this.getRectanglePosition(),rectangleTargetTile)){
	 *			|this.getWorld().new.getMazubHasCollisionWithTargetTile == true
	 *
	 */
	public void hasCollisionWithTargetTile() {
		if (this.getWorld().getMazub()==this) {
			int[] rectangleTargetTile= new int[] { this.getWorld().getTargetTile()[0]*this.getWorld().getTileSize(), this.getWorld().getTargetTile()[1]*this.getWorld().getTileSize(),this.getWorld().getTileSize(),this.getWorld().getTileSize()};
			if (hasColissionWithTiles(this.getRectanglePosition(),rectangleTargetTile)){
				this.getWorld().setMazubHasCollisionWithTargetTile(true);
			}
		}
	}
	
	
	
	//##############################################################################
	}