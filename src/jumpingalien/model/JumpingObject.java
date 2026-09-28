package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public abstract class JumpingObject extends BlockedObject {
	
	
	//##############################################################################
	/**
	 * Return the jumping of this Object.
	 */
	@Basic @Raw
	public boolean getJumping() {
		return this.jumping;
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
	public void setJumping(boolean newJumping) 
			throws IllegalArgumentException {
		if (! isValidJumping(newJumping))
			throw new IllegalArgumentException("invalid jump argument");
		this.jumping = newJumping;
	}
	
	/**
	 * Check whether the given jumping is a valid jumping for
	 * any Object.
	 *  
	 * @param  jumping
	 *         The jumping to check.
	 * @return 
	 *       | result == this.getJumping()!=newJumping
	*/
	public  boolean isValidJumping(boolean newJumping) {
		return this.getJumping()!=newJumping ;
	}
	
	/**
	 * Variable registering the jumping of this Object.
	 */
	protected boolean jumping=false;
	//##############################################################################
	
	/**
	 * Start the jump of the jumping object
	 */
	public abstract void startJump() throws IllegalStateException ;
	
	/**
	 * ends jump of the jumping object
	 */
	public abstract void endJump() throws IllegalStateException;
}
