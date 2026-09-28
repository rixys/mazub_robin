package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * @invar  Each jumpingSpeed can have its jumpingSpeed as jumpingSpeed .
 *       | canHaveAsjumpingSpeed(this.getjumpingSpeed())
 */

@Value
public class VerticalJumpingConstant implements Comparable<VerticalJumpingConstant> {


/**
 * Initialize this new jumpingSpeed with given jumpingSpeed.
 * 
 * @param  newJumpingSpeed
 *         The jumpingSpeed for this new jumpingSpeed.
 * @post   If the given jumpingSpeed is a valid jumpingSpeed for any jumpingSpeed,
 *         the jumpingSpeed of this new jumpingSpeed is equal to the given
 *         jumpingSpeed. Otherwise, the jumpingSpeed of this new jumpingSpeed is equal
 *         to 0.
 *       | if (isValidjumpingSpeed(newJumpingSpeed))
 *       |   then new.getjumpingSpeed() == newJumpingSpeed
 *       |   else new.getjumpingSpeed() == 0
 */
public VerticalJumpingConstant(double newJumpingSpeed) {
	if (! canHaveAsjumpingSpeed(newJumpingSpeed))
		newJumpingSpeed = 0;
	this.newJumpingSpeed = newJumpingSpeed;
}

/**
 * Return the jumpingSpeed of this jumpingSpeed.
 */
@Basic @Raw @Immutable
public double getjumpingSpeed() {
	return this.newJumpingSpeed;
}

/**
 * Check whether this jumpingSpeed can have the given jumpingSpeed as its jumpingSpeed.
 *  
 * @param  newJumpingSpeed
 *         The jumpingSpeed to check.
 * @return 
 *       | result == newJumpingSpeed>0
*/
@Raw
public boolean canHaveAsjumpingSpeed(double newJumpingSpeed) {
	return newJumpingSpeed>0;
}

/**
 * Variable registering the jumpingSpeed of this jumpingSpeed.
 */
private final double newJumpingSpeed;

@Override
public int compareTo(VerticalJumpingConstant other) throws ClassCastException {
	if(other==null) {
		throw new ClassCastException("not a valid VerticalJumpingConstant");
	}
	return Double.compare(this.getjumpingSpeed(), other.getjumpingSpeed());
}


}
