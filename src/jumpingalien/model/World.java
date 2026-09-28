	package jumpingalien.model;
	
	import be.kuleuven.cs.som.annotate.*;
	import jumpingalien.util.ModelException;
	
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.HashMap;
	import java.util.HashSet;
	import java.util.Set;
	/**
	 * @invar	The GameObjects associated with each World must be proper Gameobjects for 
	 * that world
	 * 		|hasProperGameObjects()
	 * 
	 * @invar each element in gameObjects is effective and is a valid gameObject
	 * 		|canHaveAsGameObject(GameObject)
	 * 
	 * @invar Each gameObject references this world as the world that it is attached
	 * 		| for each GameObject in gameObjects:
	 * 		|	(GameObject.getWorld()==this
	 */
	/**
	* @invar  Each tileSize can have its tileSize as tileSize .
	*       | canHaveAsTileSize(this.getTileSize())
	*/
	
	/**
	 * @invar  The nbTilesX of each World must be a valid nbTilesX for any
	 *         World.
	 *       | isValidnbTilesX(getnbTilesX())
	 */
	
	/**
	 * @invar  The nbTilesY of each World must be a valid nbTilesY for any
	 *         World.
	 *       | isValidnbTilesY(getnbTilesY())
	 */
	
	/**
	 * @invar  The targetTileCoordinate of each World must be a valid targetTileCoordinate for any
	 *         World.
	 *       | isValidTargetTileCoordinate(getTargetTileCoordinate())
	 */
	
	/** 
	 * @invar  The visualWindonwWidth of each World must be a valid visualWindonwWidth for any
	 *         World.
	 *       | isValidVisualWindowWidth(getVisualWindowWidth())
	 */
	
	/** 
	 * @invar  The visualWindowHeigth of each World must be a valid visualWindowHeigth for any
	 *         World.
	 *       | isValidVisualWindowHeigth(getVisualWindowHeigth())
	 */
	
	/**
	 * @invar  The geologicalFeatures of each World must be a valid geologicalFeatures for any
	 *         World.
	 *       | isValidGeologicalFeatures(getGeologicalFeatures())
	 */
	
	/** 
	 * @invar  The gameObjects of each world must be a valid gameObjects for any
	 *         world.
	 *       | isValidGameObjects(getGameObjects())
	 */
	
	/**
	 * @invar  The targetTile of each world must be a valid targetTile for any
	 *         world.
	 *       | isValidTargetTile(getTargetTile())
	 */
	
	/** 
	 * @invar  The Mazub of each world must be a valid Mazub for any
	 *         world.
	 *       | isValidMazub(getMazub())
	 */
	
	/** 
	 * @invar  The visualWindowPosition of each world must be a valid visualWindowPosition for any
	 *         world.
	 *       | isValidVisualWindowPosition(getVisualWindowPosition())
	 */
	/**
	 * @invar  The gameIsStarted of each Mazub must be a valid gameIsStarted for any
	 *         Mazub.
	 *       | isValidgameIsStarted(getgameIsStarted())
	 */
	public class World {
		
		
		//##############################################################################
	/**
	 * create a new world with the given arguments
	 * @param tileSize
	 * 	the size of each tile
	 * @param nbTilesX
	 * 	the number of tiles in the x-direction
	 * @param nbTilesY
	 * 	the number of tiles in the y-direction
	 * @param targetTileCoordinate
	 * 	the position of the target tile 
	 * @param visibleWindowWidth
	 * 	the width of the visual window
	 * @param visibleWindowHeight
	 * 	the heigth of the visual window
	 * @param geologicalFeatures
	 * 	the geologicalfeatures of the terrain of this world, 0==AIR
	 * 1==Solid_ground, 2==water,3==magma
	 * 
	 * @post initialize the given variables
	 * 		|new.getTileSize()==tilesize
	 * 		|new.getnbTilesX==nbTilesX
	 * 		|new.getnbTilesY==nbTilesY
	 * 		|new.getTargetTile==targetTileCoordinate
	 * 		|new.getVisualWindowWidth()==visibleWindowWidth
	 * 		|new.getVisualWindowHeigth()==visibleWindowHeight
	 * 		|new.getGeologicalFeatures()==geologicalFeatures
	 * 
	 * @effect initialize the geological features on the correct position
	 * 		|	this.initializeGeologicalFeatures();
	 * 
	 */
		public World(int tileSize, int nbTilesX, int nbTilesY, int[] targetTileCoordinate,
				int visibleWindowWidth, int visibleWindowHeight, int[] geologicalFeatures) {
			this.setTileSize(tileSize);
			this.setnbTilesX(nbTilesX);
			this.setnbTilesY(nbTilesY);
			this.setTargetTile(targetTileCoordinate);
			this.setVisualWindowWidth(visibleWindowWidth);
			this.setVisualWindowHeigth(visibleWindowHeight);
			this.setGeologicalFeatures(geologicalFeatures);
			this.initializeGeologicalFeatures();
		}
		
		//##############################################################################
		public static double[] locationGameObjectsX = {};
		public static double[] locationGameObjectsY = {};
		//##############################################################################
		public void terminate() {
			for (GameObject gameobjects : gameObjects) {
				this.addAsTerminatedObjects(gameobjects);
			}
			for (GameObject terminatedObject : this.getTerminatedObjects()) {
				terminatedObject.terminate();
				}
				
			this.getTerminatedObjects().clear();
			
			this.terminated=true;
		}
		
		public boolean isTerminated() {
			return this.terminated;
		}
		
		private boolean terminated;
		
		//##############################################################################
	
		/**
		 * calculate the self made hashcode for geologicalFeatures, be aware that it does 
		 * not make an unique hash code for every item but it almost does
		 * @param positions
		 * 		the given  positions of the geological feature ,the first element is the x position
		 * 		the second element is the y position
		 * @return TODO
		 * 		result== (positions[0]*10<<16)+ positions[1]
		 */
		private long calculateOwnHashCode(int[] positions)throws IllegalArgumentException {
			if(positions[0]>=(Math.pow(10, 5))) {
				throw new IllegalArgumentException();
			}
			long hashCode=(long) ((positions[0]*Math.pow(10, 5))+ positions[1]);
			return  hashCode;
		}
		
		public void initializeGeologicalFeatures() {
			int i;
			int j;
			int indexCalculator=0;
			for (i=0;i<=this.getTileSize()*this.getnbTilesY();i+=this.tileSize) {
				for (j=0;j<this.getTileSize()*this.getnbTilesX(); j+=this.tileSize) {
					int[] positions = {j,i};
					if (indexCalculator> this.getGeologicalFeatures().length-1) {
						addElementofTile(positions, 0);
					}
					else {
						addElementofTile(positions, this.getGeologicalFeatures()[indexCalculator]);
						indexCalculator++;
					}
				}
			}
		}
		
		public boolean isValidElement(int element) {
			return element>=0 && element<=5;
		}
		
		public void addElementofTile(int[] position, int element ){
			long hashCode =calculateOwnHashCode(position);
			if(! isValidElement( element)) {
				this.ElementOfTile.put(hashCode, 0);
			}
			else {
				this.ElementOfTile.put(hashCode, element);
			}
		}
		
		public void removeElementofTile(int[] position) {
			long hashCode =calculateOwnHashCode(position);
			this.ElementOfTile.remove(hashCode);
		}
			
		private HashMap< Long, Integer > ElementOfTile = new HashMap<>(); 
		
		//##############################################################################
	
		public int getGeologicalFeatureOnFixedPosition(int xPosition, int yPosition) {
			int[] position = {xPosition,yPosition};
			long hashCode = this.calculateOwnHashCode(position);
			if(this.ElementOfTile.containsKey(hashCode)) {
				return this.ElementOfTile.get(hashCode);
			}
			else {
				return 0;
			}
			
		}
		
		public void setGeologicalFeatureOnFixedPosition(int xPosition, int yPosition, int geologicalFeature) {
			int tileXPosition=xPosition/this.getTileSize();
			int tileYPosition= yPosition/this.getTileSize();
			int[] position = {tileXPosition*this.getTileSize(),tileYPosition*this.getTileSize()};
			this.addElementofTile(position, geologicalFeature);
			
		}
		
		/**
		 * Variable registering the geologicalFeatures of this World.
		 */
		private int[] GeologicalFeatures;
	
		
		//##############################################################################
	
	
	
	/**
	 * Return the tileSize of this tileSize.
	 */
	@Basic @Raw @Immutable
	public int getTileSize() {
		return this.tileSize;
	}
	
	/**
	 * Check whether this tileSize can have the given tileSize as its tileSize.
	 *  
	 * @param  newTileSize
	 *         The tileSize to check.
	 * @return 
	 *       | result == newTileSize!=0
	*/
	@Raw
	public boolean canHaveAsTileSize(int newTileSize) {
		return newTileSize>0;
	}
	
	/**
	 * Initialize this new tileSize with given tileSize.
	 * 
	 * @param  newTileSize
	 *         The tileSize for this new tileSize.
	 * @post   If the given tileSize is a valid tileSize for any tileSize,
	 *         the tileSize of this new tileSize is equal to the given
	 *         tileSize. Otherwise, the tileSize of this new tileSize is equal
	 *         to default_value_Java.
	 *       | if (isValidTileSize(newTileSize))
	 *       |   then new.getTileSize() == newTileSize
	 *       |   else new.getTileSize() == this.getTileSize()
	 */
	private void setTileSize(int newTileSize) {
		if (! canHaveAsTileSize(newTileSize))
			newTileSize = 1;
		this.tileSize = newTileSize;
	}
	
	/**
	 * Variable registering the tileSize of this World.
	 */
	private int tileSize;
	//##############################################################################
	
	/**
	 * Return the nbTilesX of this World.
	 */
	@Basic @Raw
	public int getnbTilesX() {
		return this.nbTilesX;
	}
	
	/**
	 * Check whether the given nbTilesX is a valid nbTilesX for
	 * any World.
	 *  
	 * @param  nbTilesX
	 *         The nbTilesX to check.
	 * @return 
	 *       | result == WORLD_WIDTH % nbTilesX ==0
	*/
	public static boolean isValidnbTilesX(int nbTilesX) {
		return nbTilesX>0;
	}
	
	/**
	 * Set the nbTilesX of this World to the given nbTilesX.
	 * 
	 * @param  nbTilesX
	 *         The new nbTilesX for this World.
	 * @post   If the given nbTilesX is a valid nbTilesX for any World,
	 *         the nbTilesX of this new World is equal to the given
	 *         nbTilesX.
	 *       | if (isValidnbTilesX(nbTilesX))
	 *       |   then new.getnbTilesX() == nbTilesX
	 */
	@Raw
	public void setnbTilesX(int nbTilesX) {
		if (! isValidnbTilesX(nbTilesX))
			this.nbTilesX=100;
		else{
			this.nbTilesX = nbTilesX;
		}
	}
	
	/**
	 * Variable registering the nbTilesX of this World.
	 */
	private int nbTilesX;
	//##############################################################################
	
	
	/**
	 * Return the nbTilesY of this World.
	 */
	@Basic @Raw
	public int getnbTilesY() {
		return this.nbTilesY;
	}
	
	/**
	 * Check whether the given nbTilesY is a valid nbTilesX for
	 * any World.
	 *  
	 * @param  nbTilesY
	 *         The nbTilesY to check.
	 * @return 
	 *       | result == WORLD_HEIGHT % nbTilesY ==0
	*/
	public static boolean isValidnbTilesY(int nbTilesY) {
		return nbTilesY>0;
	}
	
	/**
	 * Set the nbTilesY of this World to the given nbTilesY.
	 * 
	 * @param  nbTilesY
	 *         The new nbTilesY for this World.
	 * @post   If the given nbTilesY is a valid nbTilesY for any World,
	 *         the nbTilesY of this new World is equal to the given
	 *         nbTilesY.
	 *       | if (isValidnbTilesY(nbTilesY))
	 *       |   then new.getnbTilesY() == nbTilesY
	 */
	@Raw
	public void setnbTilesY(int nbTilesY) {
		if (! isValidnbTilesY(nbTilesY)) {
			this.nbTilesY=100;
		}
		else {
			this.nbTilesY = nbTilesY;
		}
	}
	
	/**
	 * Variable registering the nbTilesY of this World.
	 */
	private int nbTilesY;
	//##############################################################################
	
	
	/**
	 * Return the visualWindonwWidth of this World.
	 */
	@Basic @Raw
	public int getVisualWindowWidth() {
		return this.visualWindowWidth;
	}
	
	/**
	 * Check whether the given visualWindonwWidth is a valid visualWindonwWidth for
	 * any World.
	 *  
	 * @param  visualWindonwWidth
	 *         The visualWindonwWidth to check.
	 * @return 
	 *       | result == true
	*/
	public  boolean isValidVisualWindowWidth(int visualWindowWidth) {
		return this.getTileSize()*this.getnbTilesX()>=visualWindowWidth;
	}
	
	/**
	 * Set the visualWindowWidth of this World to the given visualWindonwWidth.
	 * 
	 * @param  visualWindowWidth
	 *         The new visualWindowWidth for this World.
	 * @post   The visualWindonwWidth of this new World is equal to
	 *         the given visualWindowWidth.
	 *       | new.getVisualWindowWidth() == visualWindowWidth
	 * @throws ModelException
	 *         The given visualWindonwWidth is not a valid visualWindowWidth for any
	 *         World.
	 *       | ! isValidVisualWindowWidth(getVisualWindowWidth())
	 */
	@Raw
	public void setVisualWindowWidth(int visualWindowWidth) 
			throws ModelException {
		if (! isValidVisualWindowWidth(visualWindowWidth))
			throw new ModelException("illigal window width");
		this.visualWindowWidth = visualWindowWidth;
	}
	
	/**
	 * Variable registering the visualWindonwWidth of this World.
	 */
	private int visualWindowWidth;
	
	//##############################################################################
	
	
	
	/**
	 * Return the visualWindowHeigth of this World.
	 */
	@Basic @Raw
	public int getVisualWindowHeigth() {
		return this.visualWindowHeigth;
	}
	
	/**
	 * Check whether the given visualWindowHeigth is a valid visualWindowHeigth for
	 * any World.
	 *  
	 * @param  visualWindowHeigth
	 *         The visualWindowHeigth to check.
	 * @return 
	 *       | result == 
	*/
	public  boolean isValidVisualWindowHeigth(int visualWindowHeigth) {
		return this.getTileSize()*this.getnbTilesY()>=visualWindowHeigth ;
	}
	
	/**
	 * Set the visualWindowHeigth of this World to the given visualWindowHeigth.
	 * 
	 * @param  visualWindowHeigth
	 *         The new visualWindowHeigth for this World.
	 * @post   The visualWindowHeigth of this new World is equal to
	 *         the given visualWindowHeigth.
	 *       | new.getVisualWindowHeigth() == visualWindowHeigth
	 * @throws ModelException
	 *         The given visualWindowHeigth is not a valid visualWindowHeigth for any
	 *         World.
	 *       | ! isValidVisual(getVisualWindowHeigth())
	 */
	@Raw
	public void setVisualWindowHeigth(int visualWindowHeigth) 
			throws ModelException {
		if (! isValidVisualWindowHeigth(visualWindowHeigth))
			throw new ModelException("illigal visual window heigth argument");
		this.visualWindowHeigth = visualWindowHeigth;
	}
	
	/**
	 * Variable registering the visualWindowHeigth of this World.
	 */
	private int visualWindowHeigth;
	
	//##############################################################################
	public int[] getVisibleWindowDimension() {
		int[] dimension= {this.getVisualWindowWidth(),this.getVisualWindowHeigth()};
		return dimension;
	}
	//##############################################################################
	/**
	 * Return the geologicalFeatures of this World.
	 */
	@Basic @Raw
	public int[] getGeologicalFeatures() {
		return this.GeologicalFeatures;
	}
	
	/**
	 * Check whether the given geologicalFeatures is a valid geologicalFeatures for
	 * any World.
	 *  
	 * @param  GeologicalFeatures
	 *         The geologicalFeatures to check.
	 * @return 
	 *       | result == true
	*/
	public static boolean isValidGeologicalFeatures(int[] geologicalFeatures)
	{
		return geologicalFeatures!=null;
	}
	
	/**
	 * Set the geologicalFeatures of this World to the given geologicalFeatures.
	 * 
	 * @param  GeologicalFeatures
	 *         The new geologicalFeatures for this World.
	 * @post   If the given geologicalFeatures is a valid geologicalFeatures for any World,
	 *         the geologicalFeatures of this new World is equal to the given
	 *         geologicalFeatures.
	 *       | if (isValidGeologicalFeatures(GeologicalFeatures))
	 *       |   then new.getGeologicalFeatures() == GeologicalFeatures
	 */
	@Raw
	public void setGeologicalFeatures(int[] GeologicalFeatures) {
		if (isValidGeologicalFeatures(GeologicalFeatures))
			this.GeologicalFeatures = GeologicalFeatures;
		else {
			throw new IllegalArgumentException("No Geological features given");
		}
	}
	
	//##############################################################################
	
	/**
	 * Check whether this world has the given gameObject as one of his gameObjects
	 * @param gameObject
	 * 		the GameObject to check
	 * @return
	 * 		|result == this.gameObjects.contains(gameObject)
	 */
	@Basic @Raw
	public boolean hasAsGameObject(Object gameObject) {
		return this.gameObjects.contains(gameObject);
	}
	/**
	 * Checks wether this world can have the given gameObject as one of its gameObjects
	 * @param gameObject
	 * 		the gameobject to check
	 * @return	False if the gameobject is not effective
	 * 		|if (gameobjects == null && !(gameObject instanceof GameObject))
	 * 		|	then result == false
	 * @return true if and only if this world is not yet terminated and it has less objects than the
	 * Maximum game objects or the given gameObject is also terminated
	 * 		| else result ==
	 * 		|		(!this.isTerminated() && this.getGameObjects.size()  || gameObject.isTerminated() )
	 */
	public boolean canHaveAsGameObject(Object gameObject) {
		if (gameObject == null || !(gameObject instanceof GameObject)) {
			return false;
		}
		else {
			GameObject newGameObject = (GameObject) gameObject;
			return ((! this.isTerminated() && !newGameObject.isTerminated() )&& (this.getNbGameObjects()<100|| gameObject instanceof Mazub)&& ! this.getGameIsStarted());
		}
	}
	/**
	 * Check whether this world has proper gameobjects attached to it.
	 * 
	 * @return True if and only if this  world can have each of its gameobjects
	 * as a GameObject, and if each of this gameobjects references this world as their world
	 * 
	 * 		|result ==
	 * 		| for each gameObject in this.getGameObjects():
	 * 		|		
	 * 		|   		result= hasAsGameObject(gameObject)
	 * 		|				&& hasAsGameObject.getWorld() == this)
	 */
	@Raw
	public boolean hasProperGameObjects() {
		boolean hasProper=true;
		for(GameObject gameObject : this.getGameObjects()) {
				if (this.hasAsGameObject(gameObject)&& gameObject.getWorld()==this) {
				}
				else {
					hasProper=false;
				}
		}
		return hasProper;
	}
	/**
	 * return the number of gameObjects attached to this world
	 * @return	the number of gameobjects attached to this world
	 * 		|result==this.getGameObjects().size()
	 */
	public int getNbGameObjects() {
		return this.getGameObjects().size();
	}
	
	/**
	 * Add the given gameObject to the set of GameObjects attached to this world
	 * 
	 * @param gameObject
	 * the gameObject to be added
	 * @post this world has the given gameObject as one of its gameObject
	 * 		| new.hasAsGameObject (gameObject)
	 * @post the given gameObject references this world as his world
	 * 		| (new gameObject).getWorld() == this
	 * @post if this world has'nt any Mazub yet and the gameobject is a Mazub it becomes the Mazub of this world
	 * 		|if (this.getMazub()==null&& gameObject instanceof Mazub)
	 * 		| this.getMazub() ==gameObject 
	 * @throws IllegalArgumentException
	 * 			this world cannot have the given gameObject as one of his gameObjects
	 * 			| ! canHaveAsGameObject(gameObject)
	 * @throws IllegalArgumetException
	 * 			the given world is already attached to a world
	 * 			| (gameObject != null) && (gameObject.getWorld() !=null)
	 */
	public void addAsGameObject(Object object) throws IllegalArgumentException {
		if(! canHaveAsGameObject(object)) {
			throw new IllegalArgumentException("not a valid GameObject");
		}
		GameObject gameObject= (GameObject) object;
		if((gameObject.getWorld() !=null && gameObject.getWorld()!=this)) {
			throw new IllegalArgumentException("the gameObject already has another world");
		}
		gameObject.setWorld(this);
		if (this.getMazub()==null&& gameObject instanceof Mazub) {
			Mazub playableMazub = (Mazub) gameObject;
			this.setMazub(playableMazub);
		}
		else if(this.getMazub()!=null&& gameObject instanceof Mazub) {
			throw new IllegalArgumentException("World can only have one Mazub");
		}
		if(gameObject.getPixelPositionXas()>=this.getnbTilesX()*this.getTileSize() || 
				gameObject.getPixelPositionYas()>=this.getnbTilesY()*this.getTileSize()) {
			throw new IllegalArgumentException("Out of borders");
		}
	
		if(gameObject instanceof BlockedObject) {
			BlockedObject blockedObject= (BlockedObject) gameObject;
			if (overlappingWithOtherObject(gameObject)) {
				throw new IllegalArgumentException();
			}
			if(blockedObject.ImpassableTerrain(blockedObject.getRectanglePosition())) {
				throw new IllegalArgumentException();
			}
		}
			
		this.getGameObjects().add(gameObject);
	}
	
	/**
	 * Remove the given gameObject form the set of GameObjects attached to this world
	 * @param gameObject
	 * 		the gameObject to be removed
	 * @post this world does not have the given gameObject as one of its gameObjects
	 * 		| !new.hasAsGameObject(gameObject)
	 * @post If this world has the given gameObject as one of its gameObjects, the given gameObject
	 * is no longer attached to any world
	 * 		 | if (hasAsGameObject(gameObject)
	 * 		 | 	then (new gameObject).getWorld() ==null
	 * 
	 * @ throws IllegalArgumentException
	 * 		|!hasAsGameObject(gameObject)
	 * 
	 */
	public void removeAsGameObject(Object gameObject) throws IllegalArgumentException {
		if(! hasAsGameObject(gameObject)) {
			throw new IllegalArgumentException("Object not available");
		}
		this.getGameObjects().remove(gameObject);
		GameObject newGameObject= (GameObject) gameObject;
		newGameObject.setWorld(null);
		if(newGameObject==this.getMazub()) {
			this.setMazub(null);
		}
		//TODO references
	}
	
	
	/**
	 * Returns a copy gameObjects of this world.
	 */
	@Basic @Raw
	public Set<Object> getAllGameObjects() {
		Set<Object> newSet = new HashSet<Object>();
		newSet.addAll(this.getGameObjects());
		return newSet;
	}
	
	/**
	 * Return the gameObjects of this world.
	 */
	@Basic @Raw
	 Set<GameObject> getGameObjects() {
		return this.gameObjects;
	}
	
	/**
	 * Variable registering the gameObjects of this world.
	 */
	private Set<GameObject> gameObjects = new HashSet<GameObject>();
	//##############################################################################
	
	
	/**
	 * Return the targetTile of this world.
	 */
	@Basic @Raw
	public int[] getTargetTile() {
		return this.newTargetTile;
	}
	
	/**
	 * Check whether the given targetTile is a valid targetTile for
	 * any world.
	 *  
	 * @param  targetTile
	 *         The targetTile to check.
	 * @return 
	 *       | result == newTargetTile!= null && newTargetTile.length ==2
	*/
	public static boolean isValidTargetTile(int[] newTargetTile) {
		return (newTargetTile!= null && newTargetTile.length ==2);
	}
	
	/**
	 * Set the targetTile of this world to the given targetTile.
	 * 
	 * @param  newTargetTile
	 *         The new targetTile for this world.
	 * @pre    The given targetTile must be a valid targetTile for any
	 *         world.
	 *       | isValidTargetTile(newTargetTile)
	 * @post   The targetTile of this world is equal to the given
	 *         targetTile.
	 *       | new.getTargetTile() == newTargetTile
	 */
	@Raw
	public void setTargetTile(int[] newTargetTile) {
		assert isValidTargetTile(newTargetTile);
		int []cloneNewTargetTile=newTargetTile.clone();
		this.newTargetTile = cloneNewTargetTile;
	}
	
	/**
	 * Variable registering the targetTile of this world.
	 */
	private int[] newTargetTile;
	
	//##############################################################################
	
	/**
	 * 
	 * @return the size of the world in pixels.
	 * 
	 */
	
	public int[] getWorldSizeInPixels() {
		int worldWidth= this.getTileSize() * this.getnbTilesX();
		int worldHeigth = this.getTileSize() * this.getnbTilesY();
		int[] worldSize= {worldWidth, worldHeigth};
		return worldSize;
	}
	
	
	//##############################################################################
	
	
	
	/**
	 * Return the Mazub of this world.
	 */
	@Basic @Raw
	public Mazub getMazub() {
		return this.playableMazub;
	}
	
	/**
	 * Check whether the given Mazub is a valid Mazub for
	 * any world.
	 *  
	 * @param  Mazub
	 *         The Mazub to check.
	 * @return 
	 *       | result == true
	*/
	public static boolean isValidMazub(Mazub playableMazub) {
		return true;
	}
	
	/**
	 * Set the Mazub of this world to the given Mazub.
	 * 
	 * @param  playableMazub
	 *         The new Mazub for this world.
	 * @post   The Mazub of this new world is equal to
	 *         the given Mazub.
	 *       | new.getMazub() == playableMazub
	 * @throws IllegalArgumentException
	 *         The given Mazub is not a valid Mazub for any
	 *         world.
	 *       | ! isValidMazub(getMazub())
	 */
	@Raw
	public void setMazub(Mazub playableMazub) 
			throws IllegalArgumentException {
		if (! isValidMazub(playableMazub))
			throw new IllegalArgumentException();
		this.playableMazub = playableMazub;
		if(playableMazub!=null) {
			playableMazub.centerVisualWindow();
		}
	}
	
	/**
	 * Variable registering the Mazub of this world.
	 */
	private  Mazub playableMazub;
	
	
	//##############################################################################
	
	
	
	/**
	 * Return the visualWindowPosition of this world.
	 */
	@Basic @Raw
	public int[] getVisualWindowPosition() {
		return this.visualWindowPosition;
	}
	
	/**
	 * Check whether the given visualWindowPosition is a valid visualWindowPosition for
	 * any world.
	 *  
	 * @param  visualWindowPosition
	 *         The visualWindowPosition to check.
	 * @return 
	 *       | result == true
	*/
	public  boolean isValidVisualWindowPosition(int[] visualWindowPosition) {
		return visualWindowPosition.length==2;
	}
	
	/**
	 * Set the visualWindowPosition of this world to the given visualWindowPosition.
	 * 
	 * @param  visualWindowPosition
	 *         The new visualWindowPosition for this world.
	 * @post   The visualWindowPosition of this new world is equal to
	 *         the given visualWindowPosition.
	 *       | new.getVisualWindowPosition() == visualWindowPosition
	 * @throws IllegalArgumentException
	 *         The given visualWindowPosition is not a valid visualWindowPosition for any
	 *         world.
	 *       | ! isValidVisualWindowPosition(getVisualWindowPosition())
	 */
	@Raw
	public void setVisualWindowPosition(int[] visualWindowPosition) 
			throws IllegalArgumentException {
		if (! isValidVisualWindowPosition(visualWindowPosition))
			throw new IllegalArgumentException();
		this.visualWindowPosition = visualWindowPosition;
	}
	
	/**
	 * Variable registering the visualWindowPosition of this world.
	 */
	private int[] visualWindowPosition={0,0};
	//##############################################################################
	public void advanceWorldTime(double dt)throws IllegalArgumentException {
		if (dt<0 || dt>0.2) {
			throw new IllegalArgumentException("Illegal Time");
		}
		if(this.getMazub()!=null) {
			this.getMazub().advanceTime(dt);
		}
		for (GameObject terminatedObject : this.getTerminatedObjects()) {
			terminatedObject.terminate();
			}
			
		this.getTerminatedObjects().clear();
		
		for (GameObject gameObject:this.getGameObjects()) {
			if(gameObject!= this.getMazub()) {
				gameObject.advanceTime(dt);
			}
		}
		for (GameObject terminatedObject : this.getTerminatedObjects()) {
			terminatedObject.terminate();
				
		}
		this.getTerminatedObjects().clear();
		
	}
	//##############################################################################
	
	/**
	 * Check whether this world has the given deadObject as one of his gameObjects
	 * @param gameObject
	 * 		the GameObject to check
	 * @return
	 * 		|result == this.gameObjects.contains(gameObject)
	 */
	@Basic @Raw
	public boolean hasAsTerminatedObjects(Object gameObject) {
		return this.terminatedObjects.contains(gameObject);
	}
	
	/**
	 * Checks whether this world can have the given TerminatedObjects as one of its TerminatedObjects
	 * @param gameObject
	 * 		the gameobject to check
	 * @return	False if the TerminatedObjects is not effective
	 * 		|if (deadObject == null && !(gameObject instanceof GameObject))
	 * 		|	then result == false
	 * @return true if and only if this the object is still effective  and is not already terminated and this world is not yet been terminated
	 * 		| else result ==
	 * 		|		(!this.isTerminated()
	 */
	public boolean canHaveAsTerminatedObjects(Object gameObject) {
		if (gameObject == null || !(gameObject instanceof GameObject)) {
			return false;
		}
		else {
			GameObject newGameObject = (GameObject) gameObject;
			return ((! this.isTerminated() && !newGameObject.isTerminated()));
		}
	}
	
	/**
	 * Check whether this world has proper TerminatedObjects attached to it.
	 * 
	 * @return True if and only if this  world can have each of its TerminatedObjects
	 * as a TerminatedObjects, and if each of this TerminatedObjects references this world as their world
	 * 
	 * 		|result ==
	 * 		| for each gameObject in this.getGameObjects():
	 * 		|		
	 * 		|   		result== ( hasAsGameObject(gameObject)
	 * 		|				&& hasAsGameObject.getWorld() == this)
	 */
	@Raw
	public boolean hasProperTerminatedObjects() {
		boolean hasProper=true;
		for(GameObject gameObject : this.getGameObjects()) {
				if (this.hasAsTerminatedObjects(gameObject)&& gameObject.getWorld()==this) {
				}
				else {
					hasProper=false;
				}
		}
		return hasProper;
	}
	
	/**
	 * return the number of TerminatedObjects attached to this world
	 * @return	the number of TerminatedObjects attached to this world
	 * 		|result==this.getTerminatedObjects().size()
	 */
	public int getNbTerminatedObjects() {
		return this.getTerminatedObjects().size();
	}
	
	
	/**
	 * Add the given TerminatedObjects to the set of TerminatedObjects attached to this world
	 * 
	 * @param gameObject
	 * the gameObject to be added
	 * @post this world has the given TerminatedObjects as one of its TerminatedObjects
	 * 		| new.hasAsDeadGameObject (gameObject)
	 * @throws IllegalArgumentException
	 * 			this world cannot have the given TerminatedObjects as one of his TerminatedObjects
	 * 			| ! canHaveAsGameObject(gameObject)
	 * @throws IllegalArgumentException
	 * 			the given world is already attached to a world
	 * 			| (gameObject != null) && (gameObject.getWorld() !=null)
	 */
	public void addAsTerminatedObjects(Object object) throws IllegalArgumentException {
		if(! canHaveAsTerminatedObjects(object)) {
			throw new IllegalArgumentException();
		}
		GameObject gameObject= (GameObject) object;
		this.getTerminatedObjects().add(gameObject);
	}
	
	/**
	 * Remove the given TerminatedObjects form the set of TerminatedObjects attached to this world
	 * @param gameObject
	 * 		the gameObject to be removed
	 * @post this world does not have the given TerminatedObjects as one of its TerminatedObjects
	 * 		| !new.hasAsTerminatedObjects(gameObject)
	 * @ throws IllegalArgumentException
	 * 		|!hasAsDeadGameObject(gameObject)
	 * 
	 */
	public void removeAsTerminatedObjects(Object gameObject) throws IllegalArgumentException {
		if(! hasAsTerminatedObjects(gameObject)) {
			throw new IllegalArgumentException("Object not available");
		}
		this.getTerminatedObjects().remove(gameObject);
		//TODO references
	}
	
	
	/**
	 * Returns a copy TerminatedObjects of this world.
	 */
	@Basic @Raw
	public Set<Object> getAllTerminatedObjectsGameObjects() {
		Set<Object> newSet = new HashSet<Object>();
		newSet.addAll(this.getTerminatedObjects());
		return newSet;
	}
	
	
	/**
	 * Return the deadGameObjects of this world.
	 */
	@Basic @Raw
	public Set<GameObject> getTerminatedObjects() {
		return this.terminatedObjects;
	}
	
	
	Set<GameObject> terminatedObjects = new HashSet<GameObject>();
	
	//##############################################################################
	/**
	 * checks it 2 object their innerlayers overlap
	 */
	public boolean overlappingWithOtherObject(GameObject newGameObject) {
		for (GameObject gameObject: this.getGameObjects()) {
			if (gameObject instanceof Mazub){
				if(GameObject.hasColissionWithinnerLayers(newGameObject.getRectanglePosition(),gameObject.getRectanglePosition())) {
					return true;
				}
			}
		}
		return false;
		
	}
	
	//##############################################################################
	
	/**
	 * 
	 * @return this function returns if Mazub won the game or not.
	 * 			|this.getMazubHasCollisionWithTargetTile()
	 */
	
	public boolean didMazubWinTheGame() {
	return this.getMazubHasCollisionWithTargetTile();
		}
	/**
	 * 
	 * @return this function returns if the game is over or not
	 * 			|(this.getMazub()==null && this.getGameIsStarted()||this.didMazubWinTheGame())
	 */
	public boolean isGameOver() {
		return (this.getMazub()==null && this.getGameIsStarted()||this.didMazubWinTheGame());
	}
	
	
	//##############################################################################
	
	
	
	/**
	* Return the gameIsStarted of this Mazub.
	*/
	@Basic @Raw
	public boolean getGameIsStarted() {
		return gameIsStarted;
	}
	
	/**
	* Check whether the given gameIsStarted is a valid gameIsStarted for
	* any Mazub.
	*  
	* @param  gameIsStarted
	*         The gameIsStarted to check.
	* @return 
	*       | result == true
	*/
	public boolean isValidgameIsStarted(boolean gameIsStarted) {
		return true;
	}
	
	/**
	* Set the gameIsStarted of this Mazub to the given gameIsStarted.
	* 
	* @param  gameIsStarted
	*         The new gameIsStarted for this Mazub.
	* @post   The gameIsStarted of this Mazub is equal to the given
	*         gameIsStarted.
	*       | new.getgameIsStarted() == gameIsStarted
	*/
	@Raw
	public void setgameIsStarted(boolean gameIsStarted) {
	this.gameIsStarted = gameIsStarted;
	}
	
	/**
	* Variable registering the gameIsStarted of this Mazub.
	*/
	private boolean gameIsStarted = false;
	
	//##############################################################################
	/**
	* Return the mazubHasCollisionWithTargetTile of this Mazub.
	*/
	@Basic @Raw
	public boolean getMazubHasCollisionWithTargetTile() {
		return mazubHasCollisionWithTargetTile;
	}
	
	/**
	* Check whether the given mazubHasCollisionWithTargetTile is a valid mazubHasCollisionWithTargetTile for
	* any Mazub.
	*  
	* @param  mazubHasCollisionWithTargetTile
	*         The mazubHasCollisionWithTargetTile to check.
	* @return 
	*       | result == true
	*/
	public static boolean isValidMazubHasCollisionWithTargetTile(boolean mazubHasCollisionWithTargetTile) {
		return true;
	}
	
	/**
	* Set the mazubHasCollisionWithTargetTile of this Mazub to the given mazubHasCollisionWithTargetTile.
	* 
	* @param  mazubHasCollisionWithTargetTile
	*         The new mazubHasCollisionWithTargetTile for this Mazub.
	* @post   The mazubHasCollisionWithTargetTile of this Mazub is equal to the given
	*         mazubHasCollisionWithTargetTile.
	*       | new.getMazubHasCollisionWithTargetTile() == mazubHasCollisionWithTargetTile
	*/
	@Raw
	public void setMazubHasCollisionWithTargetTile(boolean mazubHasCollisionWithTargetTile) {
		this.mazubHasCollisionWithTargetTile = mazubHasCollisionWithTargetTile;
	}
	
	/**
	* Variable registering the mazubHasCollisionWithTargetTile of this Mazub.
	*/
	private boolean mazubHasCollisionWithTargetTile;
	
	//##############################################################################
	/**
	 * 
	 * @param world
	 * @effect this function starts the game
	 *			|new.getgameIsStarted == true
	 *
	 * @throws IllegalArgumentException
	 * 			|if (! isValidWorld(world))
	 */
	public void startGame(World world) throws IllegalArgumentException {
			if (! isValidWorld(world)) {
				throw new IllegalArgumentException("not a valid World");
			}
			setgameIsStarted(true);
		
	}
	/**
	 * 
	 * @param world
	 * @return this returns whether or not this is a valid world
	 * 			|return world!=null && this.getMazub()!= null;
	 */
	public boolean isValidWorld(World world) {
		return world!=null && this.getMazub()!= null;
	}
	
	//##############################################################################
	/**
	 * adds the given school to the school of this world
	 * @param school
	 * 		the given school
	 * @Post	...
	 * 		|	schoolsInTheWorld.add(school)
	 * @throws IllegalStateException
	 * 		| if(this.getAllTheSchoolsInThisWorld().size()>=10)
	 */
	public void addASchoolToTheWorld(School school)throws IllegalStateException {
		if(this.getAllTheSchoolsInThisWorld().size()>=10) {
			throw new IllegalStateException("Too many schools");
		}
		schoolsInTheWorld.add(school);
	}
	/**
	 * returns the set of school of this world
	 */
	public Set<School> getAllTheSchoolsInThisWorld() {
		return schoolsInTheWorld;
	}
	
	/**
	 * the set of schools for this world
	 */
	Set<School> schoolsInTheWorld= new HashSet<School>();
	}
