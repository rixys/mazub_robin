package jumpingalien.model;

import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

public class Skullcab extends Plant{

	public Skullcab(int positionX, int positionY, Sprite[] inSprites) throws ModelException {
		super(positionX, positionY, inSprites);
		this.setActualPositionX(pixelToActual(positionX));
		this.setPixelPositionXas(positionX);
		this.setActualPositionY(pixelToActual(positionY));
		this.setPixelPositionYas(positionY);
		this.setHorizontalVelocity(0);
		this.setVerticalVelocity(0.5);
		this.setOrientation(1);
		this.setMaxLivingTime(12);
		this.setHitpoints(3);
		this.setSprite(inSprites);
		this.setCurrentsprite(this.getSprites()[0]);
		
	}
	/**
	 * Uses the correct sprite for this skullcab
	 * @post if the orientation is positive the current sprite becomes the first sprite in sprites
	 * otherwise it becomes the second sprite
	 * 		|if (this.getOrientation()>0) then
	 * 		|	new.getCurrentSprite()==this.getSprites()[0]
	 * 		|else
	 * 		|	new.getCurrentSprite()==this.getSprites()[1]
	 */
	@Override
	public void UseCorrectSprite() {
		if (this.getOrientation()>0) {
			this.setCurrentsprite(this.getSprites()[0]);
		}
		else {
			this.setCurrentsprite(this.getSprites()[1]);
		}
	}
	
	

}
