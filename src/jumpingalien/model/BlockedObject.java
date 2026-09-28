	package jumpingalien.model;
	
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.List;
	
	import be.kuleuven.cs.som.annotate.Basic;
	import be.kuleuven.cs.som.annotate.Raw;
	
	public abstract class BlockedObject extends GameObject {
	
		
		/**
		 * checks if the object is in impassable terrain
		 * 
		 * @param rectanglePositionObject
		 * 		the given rectangele position of the object
		 * 
		 * @effect this function checks if the Blocked Object has collision with Impassable terrain.
		 *			|if (this.getWorld()!= null)
		 * 			|	int[] surroundingTiles= getSurroundingTilesOfBlockedObject(rectangleMazub, world)
		 *			|for (int i=surroundingTiles[2];i<=surroundingTiles[3];i+=world.getTileSize()) 
		 *			|for (int j=surroundingTiles[0];j<=surroundingTiles[1]; j+=world.getTileSize()) 
		 *			|if (world.getGeologicalFeatureOnFixedPosition(j, i)==1) 
		 *			|int[] rectanglePosition= {j,i, world.getTileSize(), world.getTileSize()}
		 *
		 * @return it returns true or false if he has collision with them or not
		 * 			|if(hasColissionWithinnerLayers(rectangleMazub, rectanglePosition)) 
		 *			|return true
		 *			|else
		 *			|return false
		 */
		
		public boolean ImpassableTerrain(int[] rectanglePositionObject) {
			if (this.getWorld()!= null) {
			World world=this.getWorld();
			int[] surroundingTiles= getSurroundingTilesOfBlockedObject(rectanglePositionObject, world);
			for (int i=surroundingTiles[2];i<=surroundingTiles[3];i+=world.getTileSize()) {
				for (int j=surroundingTiles[0];j<=surroundingTiles[1]; j+=world.getTileSize()) {
					GeologicalFeatures geologicalFeature=GeologicalFeatures.getGeologicalFeature(world.getGeologicalFeatureOnFixedPosition(j, i));
					if (impassableTerrainTile(geologicalFeature) ) {
						int[] rectanglePosition= {j,i, world.getTileSize(), world.getTileSize()};
						if(hasColissionWithinnerLayers(rectanglePositionObject, rectanglePosition)) {
							return true;
						}
				     }
			      }
			
			  }
		
			}
		
			return false;
		}
		
		/**
		 * returns if the object is impassable terrain
		 *  @param geologicalFeature
		 *  	the given geologicalFeature
		 * 
		 * @return
		 * 		result==geologicalFeature==GeologicalFeatures.SOLID_GROUND || geologicalFeature==GeologicalFeatures.ICE
		 */
		 public boolean impassableTerrainTile(GeologicalFeatures geologicalFeature) {
			 return geologicalFeature==GeologicalFeatures.SOLID_GROUND || geologicalFeature==GeologicalFeatures.ICE;
		 }
		
		/**
		 * returns the closest tiles of the given world surrounding this blockedobject
		 * @param rectangleBlockedObject
		 * 		the rectangle position of the object
		 * @param world
		 * 		the given world
		 * 
		 * @effect this is a function that calculates which tiles surround BlockedObject.
		 * 		|int minX = ((rectangleMazub[0]/ world.getTileSize())-1)*world.getTileSize()
		 *		|if(minX<0) 
		 *		|	minX=0
		 *		|int maxX= (((rectangleMazub[0]+rectangleMazub[2])/ world.getTileSize())+1)*world.getTileSize()
		 *		|int minY = ((rectangleMazub[1]/ world.getTileSize())-1)*world.getTileSize()
		 *		|if (minY<0) 
		 *		|	minY=0
		 *		|int maxY= (((rectangleMazub[1]+rectangleMazub[3])/ world.getTileSize())+1)*world.getTileSize()
		 * @return this returns which tiles surround BlockedObject.
		 * 		|new int[] {minX,maxX,minY,maxY}
		 */
		public static int[] getSurroundingTilesOfBlockedObject(int[] rectangleBlockedObject,World world) {
			int minX = ((rectangleBlockedObject[0]/ world.getTileSize())-1)*world.getTileSize();
			if(minX<0) {
				minX=0;
			}
			int maxX= (((rectangleBlockedObject[0]+rectangleBlockedObject[2])/ world.getTileSize())+1)*world.getTileSize();
			int minY = ((rectangleBlockedObject[1]/ world.getTileSize())-1)*world.getTileSize();
			if (minY<0) {
				minY=0;
			}
			int maxY= (((rectangleBlockedObject[1]+rectangleBlockedObject[3])/ world.getTileSize())+1)*world.getTileSize();
			return new int[] {minX,maxX,minY,maxY};
		}
		
		/**
		 * Checks if this BlockedObject has collsion with the top row of the surrounding tiles of the given geologicalfeature
		 * @param rectanglePosition
		 * 		the given rectangleposition
		 * @param world
		 * 		the given world
		 * @param geologicalfeature
		 * 		the given geologicalfeature
		 * 
		 * @effect this function checks if the blocked object has collision with the top of the surrounding tiles.
		 * 			|int[] surroundingTiles= getSurroundingTilesOfMazub(rectanglePosition, world)
		 *			|for (int i=surroundingTiles[2];i<=surroundingTiles[3];i+=world.getTileSize()) 
		 *			|	for (int j=surroundingTiles[0];j<=surroundingTiles[1]; j+=world.getTileSize()) 
		 *			|		if (world.getGeologicalFeatureOnFixedPosition(j, i)==geologicalfeature) 
		 *			|		int[] rectangleTilePosition= {j,i, world.getTileSize(), world.getTileSize()}
		 *			|		if(rectanglePosition[1]+rectanglePosition[3]-1==rectangleTilePosition[1]
		 *
		 * @return it returns true or false if he has collision with them or not
		 * 			|if(rectanglePosition[1]+rectanglePosition[3]-1==rectangleTilePosition[1]
		 *			|			&& rectangleTilePosition[0]+rectangleTilePosition[2]-1>rectanglePosition[0]
		 *			|			&& rectangleTilePosition[0]<rectanglePosition[0]+rectanglePosition[2]-1) 
		 * 			|	return true
		 *			|else
		 *			|	return false
		 */
	
	
	public static boolean hasCollisionWithTopTiles(int[] rectanglePosition,World world,int geologicalfeature){
		if (world!= null) {
			int[] surroundingTiles= getSurroundingTilesOfBlockedObject(rectanglePosition, world);
			for (int i=surroundingTiles[2];i<=surroundingTiles[3];i+=world.getTileSize()) {
				for (int j=surroundingTiles[0];j<=surroundingTiles[1]; j+=world.getTileSize()) {
					if (world.getGeologicalFeatureOnFixedPosition(j, i)==geologicalfeature) {
						int[] rectangleTilePosition= {j,i, world.getTileSize(), world.getTileSize()};
						if(rectangleTilePosition[1]+rectangleTilePosition[3]-1== rectanglePosition[1]
								//rectangleTilePosition[1]+rectangleTilePosition[3]== rectanglePosition[1]
								&& rectangleTilePosition[0]+rectangleTilePosition[2]-1>rectanglePosition[0]
								&& rectangleTilePosition[0]<rectanglePosition[0]+rectanglePosition[2]-1) {{
							return true;
						}
				     }
			      }
			
			  }
		
			}
		}
			return false;
		}
	
	
	//collision tiles voor shark, anders werkt het niet
	/**
	 * Checks if a shark has collision with the top row of his surrounfing tiles
	 * @param rectanglePosition
	 * 		the rectangleposition of the shark
	 * @param world
	 * 		the world
	 * @param geologicalfeature
	 * 		the geologicalfeature
	 * @return	returns true if he has collision with the top row of the given geological feature
	 */
	public static boolean hasCollisionWithTopTilesForAShark(int[] rectanglePosition,World world,int geologicalfeature){
		if (world!= null) {
			int[] surroundingTiles= getSurroundingTilesOfBlockedObject(rectanglePosition, world);
			for (int i=surroundingTiles[2];i<=surroundingTiles[3];i+=world.getTileSize()) {
				for (int j=surroundingTiles[0];j<=surroundingTiles[1]; j+=world.getTileSize()) {
					if (world.getGeologicalFeatureOnFixedPosition(j, i)==geologicalfeature) {
						int[] rectangleTilePosition= {j,i, world.getTileSize(), world.getTileSize()};
						if(rectangleTilePosition[1]+rectangleTilePosition[3]== rectanglePosition[1]
								&& rectangleTilePosition[0]+rectangleTilePosition[2]-1>rectanglePosition[0]
								&& rectangleTilePosition[0]<rectanglePosition[0]+rectanglePosition[2]-1) {{
							return true;
						}
				     }
			      }
			
			  }
		
			}
		}
			return false;
		}
	
	/**
	 * 
	 * @param rectanglePosition
	 * @param world
	 * 
	 * @effect this function checks if BlockedObject has collision with the tiles at the lowest position
	 * 			|int[] surroundingTiles= getSurroundingTilesOfMazub(rectanglePosition, world)
	 *			|for (int i=surroundingTiles[2];i<=surroundingTiles[3];i+=world.getTileSize()) 
	 *			|for (int j=surroundingTiles[0];j<=surroundingTiles[1]; j+=world.getTileSize()) 
	 *			|if (world.getGeologicalFeatureOnFixedPosition(j, i)==1) 
	 *			|int[] rectangleTilePosition= {j,i, world.getTileSize(), world.getTileSize()}
	 *
	 * @return it returns true or false if he has collision with them or not
	 * 			|if(rectanglePosition[1]+rectanglePosition[3]>rectangleTilePosition[1] 
	 *			|&& rectangleTilePosition[1]>rectanglePosition[1]
	 *			|&& rectanglePosition[0]+rectanglePosition[2]-1>rectangleTilePosition[0]
	 *			|&& rectanglePosition[0]<rectangleTilePosition[0]+rectangleTilePosition[2]-1) 
	 *			|return true
	 *			|else
	 *			|return false
	 */
	
	public static boolean hasCollisionWithBottomTiles(int[] rectanglePosition,World world, int geologicalfeature){
		if (world!= null) {
			int[] surroundingTiles= getSurroundingTilesOfBlockedObject(rectanglePosition, world);
			for (int i=surroundingTiles[2];i<=surroundingTiles[3];i+=world.getTileSize()) {
				for (int j=surroundingTiles[0];j<=surroundingTiles[1]; j+=world.getTileSize()) {
					if (world.getGeologicalFeatureOnFixedPosition(j, i)==geologicalfeature) {
						int[] rectangleTilePosition= {j,i, world.getTileSize(), world.getTileSize()};
						if(	rectanglePosition[1]+rectanglePosition[3]>rectangleTilePosition[1] 
								&& rectangleTilePosition[1]>rectanglePosition[1]
										&& rectanglePosition[0]+rectanglePosition[2]-1>rectangleTilePosition[0]
												&& rectanglePosition[0]<rectangleTilePosition[0]+rectangleTilePosition[2]-1) {
							return true;
						}
				     }
			      }
			
			  }
		
			}
		
			return false;
	}
	
	
	
	/**
	 * This method deals with each geologicalfeature 
	 * @param geologicalFeature
	 * 		the given geological feature which he has collision with
	 * @param rectanglePositionTiles
	 * 		the rectangleposition of the tile
	 * @param rectanglePositionThisObject
	 * 		the rectangleposition of the object
	 * @param world
	 * 		the given world
	 * @effect checks what happens if he has collision with air
	 * 		|case AIR: 
			|	this.collisionWithAir(rectanglePositionThisObject,rectanglePositionTiles)
			
	 * @effect  checks what happens if he has collision with solid ground
	 * 		|case SOLID_GROUND:
	 * 		|	this.collisionWithSolid_Ground(rectanglePositionThisObject,rectanglePositionTiles)
	 * 
	 * @effect checks what happens if he has collision with water
	 * 		|case WATER:
	 * 		|	this.collisionWithWater(rectanglePositionThisObject,rectanglePositionTiles)
	 * 
	 * @effect checks what happens if he has contact with magma
	 * 		|case MAGMA:
	 *		|		this.collisionWithMagma(rectanglePositionThisObject,rectanglePositionTiles)
	 * 
	 * @effect checks what happens if has contact with ice
	 * 		| case ICE: 
	 * 		|	this.collisionWithIce(rectanglePositionThisObject,rectanglePositionTiles)
	 * 
	 * @effect checks what happens if he has contact with gas
	 * 		|case GAS:
	 * 		|	this.collisionWithGas(rectanglePositionThisObject,rectanglePositionTiles)
	 * 
	 */
	public void collisionWithTiles(GeologicalFeatures geologicalFeature, int[] rectanglePositionTiles, int[] rectanglePositionThisObject,
			World world) {
		switch (geologicalFeature) {
		case AIR: 
			this.collisionWithAir(rectanglePositionThisObject,rectanglePositionTiles);
			break;
		case SOLID_GROUND:
			this.collisionWithSolid_Ground(rectanglePositionThisObject,rectanglePositionTiles);
			break;
		case WATER: 
			this.collisionWithWater(rectanglePositionThisObject,rectanglePositionTiles);
			break;
		case MAGMA:
			this.collisionWithMagma(rectanglePositionThisObject,rectanglePositionTiles);
			break;
		case ICE: 
			this.collisionWithIce(rectanglePositionThisObject,rectanglePositionTiles);
			break;
		case GAS:
			this.collisionWithGas(rectanglePositionThisObject,rectanglePositionTiles);
			break;
		}
		}
		
	
	/**
	 * Deals with the blocked object that has collision with gas
	 * @param rectanglePositionThisObject
	 * 		this object his rectangleposition
	 * @param rectanglePositionTiles
	 * 		this rectangle position of the tile
	 */
	protected void collisionWithGas(int[] rectanglePositionThisObject, int[] rectanglePositionTiles) {
		
	}
	
	
	/**
	 * Deals with the blocked object that has collision with Ice
	 * @param rectanglePositionThisObject
	 * 		this object his rectangleposition
	 * @param rectanglePositionTiles
	 * 		this rectangle position of the tile
	 * @effect the object has collision with impassable terrain
	 * 		|collisionWithImpassableTerrain(rectanglePositionThisObject,rectanglePositionTiles,1 )
	 */
	protected void collisionWithIce(int[] rectanglePositionThisObject, int[] rectanglePositionTiles) {
		collisionWithImpassableTerrain(rectanglePositionThisObject,rectanglePositionTiles,4 );
	}
	
	
	/**
	 * Deals with the blocked object that has collision with magma
	 * @param rectanglePositionThisObject
	 * 		this object his rectangleposition
	 * @param rectanglePositionTiles
	 * 		this rectangle position of the tile
	 */
	protected void collisionWithMagma(int[] rectanglePositionThisObject, int[] rectanglePositionTiles) {
		
	}
	
	
	/**
	 * Deals with the blocked object that has collision with water
	 * @param rectanglePositionThisObject
	 * 		this object his rectangleposition
	 * @param rectanglePositionTiles
	 * 		this rectangle position of the tile
	 */
	protected void collisionWithWater(int[] rectanglePositionThisObject, int[] rectanglePositionTiles) {
		
	}
	
	
	/**
	 * Deals with the blocked object that has collision with Ice
	 * @param rectanglePositionThisObject
	 * 		this object his rectangleposition
	 * @param rectanglePositionTiles
	 * 		this rectangle position of the tile
	 * @effect the object has collision with impassable terrain
	 * 		|collisionWithImpassableTerrain(rectanglePositionThisObject,rectanglePositionTiles,1 )
	 */
	protected void collisionWithSolid_Ground(int[] rectanglePositionThisObject, int[] rectanglePositionTiles) {
		collisionWithImpassableTerrain(rectanglePositionThisObject,rectanglePositionTiles,1 );
	}
	
	
	
	/**
	 * Deals with the blocked object that has collision with air
	 * @param rectanglePositionThisObject
	 * 		this object his rectangleposition
	 * @param rectanglePositionTiles
	 * 		this rectangle position of the tile
	 * @post normally nothing happens
	 */
	protected void collisionWithAir(int[] rectanglePositionThisObject, int[] rectanglePositionTiles) {
		
	}
	
	
	
	
	/**
	 * 
	 * @param rectangleBlockedObject
	 * 
	 * @effect this function checks if Mazub has collision with a tile
	 * 		|for int i=minY;i<=maxY;i+=world.getTileSize()
	 *		|for int j=minX;j<=maxX; j+=world.getTileSize()
	 *		|int[] rectanglePosition= {j,i, world.getTileSize(), world.getTileSize()}
	 *		|if hasColissionWithTiles(rectangleBlockedObject, rectanglePosition)
	 *		|this.collisionWithTiles(world.getGeologicalFeatureOnFixedPosition(j,i), rectanglePosition,rectangleBlockedObject,world)
	 */
	
	public void collisionWithAllPossibleTilesOfWorld(int[] rectangleBlockedObject) {
		World world=this.getWorld();
		int[] surroundingTiles= getSurroundingTilesOfBlockedObject(rectangleBlockedObject, world);
		for (int i=surroundingTiles[2];i<=surroundingTiles[3];i+=world.getTileSize()) {
			for (int j=surroundingTiles[0];j<=surroundingTiles[1]; j+=world.getTileSize()) {
				int[] rectanglePosition= {j,i, world.getTileSize(), world.getTileSize()};
				if(hasColissionWithTiles(rectangleBlockedObject, rectanglePosition)) {
					this.collisionWithTiles(GeologicalFeatures.getGeologicalFeature((world.getGeologicalFeatureOnFixedPosition(j,i))), rectanglePosition,rectangleBlockedObject,world);
				}
			}
		}
	}	
	
	/**
	 * checks if the top of the given rectangleposition of the blockedobject collides with the top of a tile with the 
	 * given geologicalFeature in the given world
	 * @param rectangleBlockedObject
	 * 		the given rectangleposition
	 * @param world
	 * 		the given world
	 * @param geologicalFeature
	 * 		the given geologicalfeature
	 * @return returns true if the top has collision with the top of the given geologicalfeature
	 */
	public boolean topWithTopCollisionofTiles(int[] rectangleBlockedObject,World world, int geologicalFeature) {
		if (world!= null) {
			rectangleBlockedObject[1]=rectangleBlockedObject[1]+rectangleBlockedObject[3];
			rectangleBlockedObject[3]=0;
			int[] surroundingTiles= getSurroundingTilesOfBlockedObject(rectangleBlockedObject, world);
			for (int i=surroundingTiles[2];i<=surroundingTiles[3];i+=world.getTileSize()) {
				for (int j=surroundingTiles[0];j<=surroundingTiles[1]; j+=world.getTileSize()) {
					if (world.getGeologicalFeatureOnFixedPosition(j, i)==geologicalFeature ) {
						int[] rectangleTilePosition= {j,i, world.getTileSize(), world.getTileSize()};
						if(hasColissionWithBlockedObject(rectangleBlockedObject, rectangleTilePosition)) {
							return true;
						}
				     }
			      }
			
			  }
		
			}
		
			return false;
	}
	
	/**
	 * Function that deals with the collision with other gameObjects
	 * @effect	if the object collides with an other blocked object
	 * this object stops his movement	
	 * 		| if(collidesWithObject(otherBlockingObject)) then
	 * 		| this.collisionWithImpassableTerrain(this.getRectanglePosition(), otherBlockingObject.getRectanglePosition(),0)
	 * @effect handles effects with specific other gameobjects
	 * 		|collisionExtraEffect( other)
	 * 			
	 */
	@Override
	public  <T> void collidesWithOther(T other) {
		if(other instanceof BlockedObject) {
			BlockedObject otherBlockingObject= (BlockedObject) other;
			if(collidesWithObject(otherBlockingObject)) {
				this.collisionWithImpassableTerrain(this.getRectanglePosition(), otherBlockingObject.getRectanglePosition(),0);
			}
		}
		collisionExtraEffect( other);
	}
	
	/**
	 * can do something extra outside the normal collision
	 * @param other
	 * 		the other object
	 */
	protected void collisionExtraEffect(Object other) {
		
	}
	//#########################################################################################
	
	/**
	 * calculate the collision of this blockedobject and add or remove the gameobject that are/were in collision
	 * with this object
	 * @effect ...
	 * 		|this.hasCollisionWithOtherGameObjects();
	 * 		|this.addSpecificValueToAllObject(-1);
	 * 		|this.removeAllObjectsWithValue(0);
	 * 		|this.collisionWithAllPossibleTilesOfWorld(this.getRectanglePosition())
	 */
	public void calculateCollisionWithImpactValue(){
		this.hasCollisionWithOtherGameObjects();
		//this.addSpecificValueToAllObject(-1);
		//this.removeAllObjectsWithValue(0);
		this.collisionWithAllPossibleTilesOfWorld(this.getRectanglePosition());
	}
	//#########################################################################################
	/**
	 * function that handels for each blocked object what has to happen if he or she collides with a blocked ibject
	 * 
	 * @param rectanglePosition
	 * 		the rectangle position of this object
	 * @param rectanglePosition2
	 * 		the rectangle position of the other object
	 * @param i
	 * 		add a 1 if this object has collision with a tile
	 */
	protected abstract void collisionWithImpassableTerrain(int[] rectanglePosition, int[] rectanglePosition2, int tiles);

//##############################################################################



	/**
	* Return the takeDamageFromGeologicalFeature of this Mazub.
	*/
	@Basic @Raw
	public GeologicalFeatures getTakeDamageFromGeologicalFeature() {
		return this.geologicalFeature;
	}

	/**
	* Check whether the given takeDamageFromGeologicalFeature is a valid takeDamageFromGeologicalFeature for
	* any BlockedObject.
	*  
	* @param  geologicalFeature
	*         The takeDamageFromGeologicalFeature to check.
	* @return 
	*       | result == 
	*/
	public static boolean isValidTakeDamageFromGeologicalFeature(GeologicalFeatures geologicalFeature) {
		return geologicalFeature==GeologicalFeatures.WATER|| geologicalFeature==GeologicalFeatures.GAS|| geologicalFeature==GeologicalFeatures.MAGMA;
	}

	public boolean canHaveAsTakeDamageFromGeologicalFeature(GeologicalFeatures geologicalFeature) {
		GeologicalFeatures initialGeologicalFeatures = this.getTakeDamageFromGeologicalFeature();
		if(initialGeologicalFeatures==null) {
			return true;
		}
		if(initialGeologicalFeatures==GeologicalFeatures.WATER) {
			return true;
		}
		if(initialGeologicalFeatures==GeologicalFeatures.GAS && geologicalFeature!=GeologicalFeatures.WATER) {
			return true;
		}
		return false;
	}

	/**
	* Set the takeDamageFromGeologicalFeature of this BlockedObject to the given takeDamageFromGeologicalFeature.
	* 
	* @param  geologicalFeature
	*         The new takeDamageFromGeologicalFeature for this BlockedObject.
	* @post   If the given takeDamageFromGeologicalFeature is a valid takeDamageFromGeologicalFeature for any Mazub,
	*         the takeDamageFromGeologicalFeature of this new BlockedObject is equal to the given
	*         mazubTakeDamageFromGeologicalFeature.
	*       | if (isValidtakeDamageFromGeologicalFeature(geologicalFeature))
	*       |   then new.gettakeDamageFromGeologicalFeature() == geologicalFeature
	*/
	@Raw
	public void setTakeDamageFromGeologicalFeature(GeologicalFeatures geologicalFeature) {
		if (isValidTakeDamageFromGeologicalFeature(geologicalFeature)) {
			if(canHaveAsTakeDamageFromGeologicalFeature(geologicalFeature)) {
				this.geologicalFeature = geologicalFeature;
				if(geologicalFeature!=this.getPreviousTakeDamageFromGeologicalFeature()) {
					this.setDamageTimer(0);
				}
			}
		}
		else {
			if(geologicalFeature==null) {
				this.geologicalFeature= null;
			}
		}
		
	}

	/**
	* Variable registering the takeDamageFromGeologicalFeature of this BlockedObject.
	*/
	private GeologicalFeatures geologicalFeature;

	//##############################################################################

	/**
	* Return the previousTakeDamageFromGeologicalFeature of this Mazub.
	*/
	@Basic @Raw
	public GeologicalFeatures getPreviousTakeDamageFromGeologicalFeature() {
		return this.previousGeologicalFeature;
	}

	/**
	* Check whether the given previousTakeDamageFromGeologicalFeature is a valid takeDamageFromGeologicalFeature for
	* any BlockedObject.
	*  
	* @param  geologicalFeature
	*         The takeDamageFromGeologicalFeature to check.
	* @return 
	*       | result == geologicalFeature!=null&& geologicalFeature.getValue()>=2&& geologicalFeature.getValue()!=4
	*/
	public static boolean isValidPreviousTakeDamageFromGeologicalFeature(GeologicalFeatures geologicalFeature) {
		return geologicalFeature!=null&& geologicalFeature.getValue()>=2&& geologicalFeature.getValue()!=4;
	}


	/**
	* Set the previousTakeDamageFromGeologicalFeature of this BlockedObject to the given PreviousTakeDamageFromGeologicalFeature.
	* 
	* @param  geologicalFeature
	*         The new previousTakeDamageFromGeologicalFeature  for this BlockedObject.
	* @post   If the given previousTakeDamageFromGeologicalFeature  is a valid previousTakeDamageFromGeologicalFeature  for any BlockedObject,
	*         the previousTakeDamageFromGeologicalFeature  of this new BlockedObject is equal to the given
	*         previousTakeDamageFromGeologicalFeature .
	*       | if (isValidPreviousTakeDamageFromGeologicalFeature(geologicalFeature))
	*       |   then new.getPreviousTakeDamageFromGeologicalFeature() == geologicalFeature
	*/
	@Raw
	public void setPreviousTakeDamageFromGeologicalFeature(GeologicalFeatures geologicalFeature) {
		if (isValidPreviousTakeDamageFromGeologicalFeature(geologicalFeature)) {
				this.previousGeologicalFeature = geologicalFeature;
		}
		else {
			if(geologicalFeature==null) {
				this.previousGeologicalFeature= null;
			}
		}
	}

	/**
	* Variable registering the previousMazubTakeDamageFromGeologicalFeature of this Mazub.
	*/
	private GeologicalFeatures previousGeologicalFeature;
	//##############################################################################
	public void resetTakeDamageFromGeologicalFeature() {
		this.setPreviousTakeDamageFromGeologicalFeature(this.getTakeDamageFromGeologicalFeature());
		this.setTakeDamageFromGeologicalFeature(null);
	}
	
	//##############################################################################
	
	/**
	 * @invar  The damageTimer of each BlockedObject must be a valid damageTimer for any
	 *         BlockedObject.
	 *       | isValidDamageTimer(getDamageTimer())
	 */



	/**
	 * Return the damageTimer of this BlockedObject.
	 */
	@Basic @Raw
	public double getDamageTimer() {
		return this.damageTileTimer;
	}

	/**
	 * Check whether the given damageTimer is a valid damageTimer for
	 * any BlockedObject.
	 *  
	 * @param  dt
 	*         The damageTimer to check.
 	* @return 
 	*       | result == true
 	*/
	public static boolean isValidDamageTimer(double dt) {
		return true;
	}

	/**
 	* Set the damageTimer of this BlockedObject to the given damageTimer.
 	* 
 	* @param  dt
 	*         The new damageTimer for this BlockedObject.
 	* @post   If the given damageTimer is a valid damageTimer for any BlockedObject,
 	*         the damageTimer of this new BlockedObject is equal to the given
 	*         damageTimer.
 	*       | if (isValidDamageTimer(dt))
 	*       |   then new.getDamageTimer() == dt
 	*/
	@Raw
	public void setDamageTimer(double dt) {
		if (isValidDamageTimer(dt))
			this.damageTileTimer = dt;
	}

	/**
 	* Variable registering the damageTimer of this BlockedObject.
 	*/
	private double damageTileTimer;

//#########################################################################################
	/**
	 * let the blockedobject takes damage from the geologicalfeature that he is in
	 * @param dt
	 * 		the given time
	 * @post add the time to the damagetimer
	 * 		|new.getDamageTimer==this.getDamageTimer()+dt
	 * @effect as the damagetimer  is greater than the limit, the blockedobject will take damage and
	 * damagetimer becomes less
	 * 		|while(this.getDamageTimer()>=this.getDamageTimerTileLimit())
	 * 		|	this.takesDamageFromGeologicalFeature()
	 * 		|	this.setDamageTimer(this.getDamageTimer()-this.getDamageTimerTileLimit())
	 */
	public void geologicalFeatureDamageTimer(double dt) {
		this.setDamageTimer(this.getDamageTimer()+dt);
		while(this.getDamageTimer()>=this.getDamageTimerTileLimit()) {
			this.takesDamageFromGeologicalFeature();
			this.setDamageTimer(this.getDamageTimer()-this.getDamageTimerTileLimit());
			
		}
	}
	//#########################################################################################

	/**
	 * Return the damageTimerTileLimit of this BlockedObject.
	 */
	@Basic @Raw
	public double getDamageTimerTileLimit() {
		return this.newTimeLimit;
	}
	
	/**
	 * Check whether the given damageTimerTileLimit is a valid damageTimerTileLimit for
	 * any BlockedObject.
	 *  
	 * @param  newTimeLimit
	 *         The damageTimerTileLimit to check.
	 * @return 
	 *       | result == true
	*/
	public static boolean isValidDamageTimerTileLimit(double newTimeLimit) {
		return true;
	}
	
	/**
	 * Set the damageTimerTileLimit of this BlockedObject to the given damageTimerTileLimit.
	 * 
	 * @param  newTimeLimit
	 *         The new damageTimerTileLimit for this BlockedObject.
	 * @post   If the given damageTimerTileLimit is a valid damageTimerTileLimit for any BlockedObject,
	 *         the damageTimerTileLimit of this new BlockedObject is equal to the given
	 *         damageTimerTileLimit.
	 *       | if (isValidDamageTimerTileLimit(newTimeLimit))
	 *       |   then new.getDamageTimerTileLimit() == newTimeLimit
	 */
	@Raw
	public void setDamageTimerTileLimit(double newTimeLimit) {
		if (isValidDamageTimerTileLimit(newTimeLimit))
			this.newTimeLimit = newTimeLimit;
	}
	
	/**
	 * Variable registering the damageTimerTileLimit of this BlockedObject.
	 */
	private double newTimeLimit=0.2;
	
		
	//#########################################################################################
	
	
	/**
	 * takes damage from the geological feature that this blockedobject is in
	 */
	protected  void takesDamageFromGeologicalFeature() {
		
	}
	/**
	 * changes the position of the GameObject to the given value
	 * @param newPosition
	 * 	 the new position of mazub, the first element is the Xposition
	 * 	 and the second is the Yposition
	 * @Post if the position is valid the new position is equal to the given position
	 * 		|new.getPosition() == newPosition
	 * @throws IllegalArgumentException 
	 * 		| (newPosition == null) ||(newPosition.length != 2) ||ImpassableTerrain(this.getRectanglePosition())
	 */
	public void changePosition(double[] newPosition)throws IllegalArgumentException,IllegalStateException { 
	if (newPosition == null) {
		throw new IllegalArgumentException("position can't be null");
	}
	if (newPosition.length != 2) {
		throw new IllegalArgumentException("you can only give 2 values");
	}
	if (ImpassableTerrain(this.getRectanglePosition())) {
		throw new IllegalStateException("BlockedObject in impassable terrain");
	}
	this.convertToPixel(newPosition[0], newPosition[1]);
	}


}
	
