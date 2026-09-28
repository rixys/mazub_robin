package jumpingalien.facade;

import java.util.Set;

import java.util.Arrays;
import java.util.Collection;

import jumpingalien.model.GameObject;
import jumpingalien.model.Mazub;
import jumpingalien.model.Plant;
import jumpingalien.model.School;
import jumpingalien.model.Shark;
import jumpingalien.model.Skullcab;
import jumpingalien.model.Slime;
import jumpingalien.model.Sneezewort;
import jumpingalien.model.World;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

public class Facade implements IFacade {

	@Override
	public boolean isTeamSolution() {
		
		return true;
	}

	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite... sprites) throws ModelException {
		try {
		return new Mazub(pixelLeftX,pixelBottomY,sprites);
		}
		catch (Exception e) {
			throw new ModelException("not a valid Mazub");
		}
		
		
	}

	@Override
	public double[] getActualPosition(Mazub alien) throws ModelException {
		
		return alien.getPosition();
	}
	@Override
	public void changeActualPosition(Mazub alien, double[] newPosition) throws ModelException {
		try {
		alien.changePosition(newPosition);
		} catch (Exception e) {
			throw new ModelException("not a valid position");
		}
	}

	@Override
	public int[] getPixelPosition(Mazub alien) throws ModelException {
		
		return alien.getPixelPosition();
	}

	@Override
	public int getOrientation(Mazub alien) throws ModelException {
		
		return alien.getOrientation();
	}
	
	@Override
	public Sprite[] getSprites(Mazub alien) throws ModelException{
		try {
		return Arrays.copyOf(alien.getSprites(), alien.getSprites().length);
		} catch (IllegalArgumentException e) {
			throw new ModelException("this is not a valid sprite");
		}
	}
	@Override
	public double[] getVelocity(Mazub alien) throws ModelException {
		
		return alien.getVelocity();
	}

	@Override
	public double[] getAcceleration(Mazub alien) throws ModelException {
		
		return alien.getAcceleration();
	}

	@Override
	public boolean isMoving(Mazub alien) throws ModelException {
		
		return alien.getMoving();
	}

	@Override
	public void startMoveLeft(Mazub alien) throws ModelException {
		try {
		alien.startMoveLeft();
	}
		catch(AssertionError e) {
			throw new ModelException("Illigal state moving");
		}
			
		}

	@Override
	public void startMoveRight(Mazub alien) throws ModelException {
		try {
		alien.startMoveRigth();
		}
		catch(AssertionError e) {
			throw new ModelException("Illigal state moving");
		}
		
	}

	@Override
	public void endMove(Mazub alien) throws ModelException {
		try {
		alien.endMove();
		}
		catch(AssertionError e) {
			throw new ModelException("cannot end move while not moving");
		}
	}

	@Override
	public boolean isJumping(Mazub alien) throws ModelException {
		try {
		return alien.getJumping();
		} catch (IllegalArgumentException e) {
			throw new ModelException("not a valid boolean");
		}
	}

	@Override
	public void startJump(Mazub alien) throws ModelException {
		try {
		alien.startJump();
		} catch (IllegalStateException e) {
			throw new ModelException("not a valid state");
		}
		
	}

	@Override
	public void endJump(Mazub alien) throws ModelException {
		try {
			alien.endJump();
			} catch (IllegalStateException e) {
				throw new ModelException("not a valid state");
			}
			
	}

	@Override
	public boolean isDucking(Mazub alien) throws ModelException {
		
		return alien.getDucking();
	}

	@Override
	public void startDuck(Mazub alien) throws ModelException {
		
		alien.startDuck();
	}

	@Override
	public void endDuck(Mazub alien) throws ModelException {
		
		alien.endDuck();
		
	}
	@Override
	public Sprite getCurrentSprite(Mazub alien) throws ModelException{
		return alien.getCurrentSprite();
	}


	@Override
	public World createWorld(int tileSize, int nbTilesX, int nbTilesY, int[] targetTileCoordinate,
			int visibleWindowWidth, int visibleWindowHeight, int... geologicalFeatures) throws ModelException {
	try {
		return new World( tileSize,  nbTilesX,  nbTilesY,  targetTileCoordinate,
			 visibleWindowWidth,  visibleWindowHeight,  geologicalFeatures);
	}
	catch(Exception w) {
		throw new ModelException("Illigal World");
	}
	catch(Error e) {
		throw new ModelException("Illigal World");
	}
	
	}

	@Override
	public void terminateWorld(World world) throws ModelException {
		
		world.terminate();
	}

	@Override
	public int[] getSizeInPixels(World world) throws ModelException {
		
		return world.getWorldSizeInPixels();
	}

	@Override
	public int getTileLength(World world) throws ModelException {
		
		return world.getTileSize();
	}

	@Override
	public int getGeologicalFeature(World world, int pixelX, int pixelY) throws ModelException {
		
		return world.getGeologicalFeatureOnFixedPosition(pixelX, pixelY);
	}

	@Override
	public void setGeologicalFeature(World world, int pixelX, int pixelY, int geologicalFeature) throws ModelException {
		
		world.setGeologicalFeatureOnFixedPosition(pixelX, pixelY, geologicalFeature);
	}

	@Override
	public int[] getVisibleWindowDimension(World world) throws ModelException {
		
		return world.getVisibleWindowDimension();
	}
	@Override
	public int[] getVisibleWindowPosition(World world) throws ModelException{
		return  world.getVisualWindowPosition();
	}

	@Override
	public boolean hasAsGameObject(Object object, World world) throws ModelException {
		
		return world.hasAsGameObject(object);
	}

	@Override
	public Set<Object> getAllGameObjects(World world) throws ModelException {
		
		return world.getAllGameObjects();
	}

	@Override
	public Mazub getMazub(World world) throws ModelException {
		
		return world.getMazub();
	}

	@Override
	public void addGameObject(Object object, World world) throws ModelException {
		try {
			world.addAsGameObject(object);
		}
		catch(Exception e) {
			throw new ModelException("not a valid gameobject");
		}
	}

	@Override
	public void removeGameObject(Object object, World world) throws ModelException {
		try {
			world.removeAsGameObject(object);
		}
		catch(IllegalArgumentException e) {
			throw new ModelException("Element not in world");
		}
	}

	@Override
	public int[] getTargetTileCoordinate(World world) throws ModelException {
		
		return world.getTargetTile();
	}

	@Override
	public void setTargetTileCoordinate(World world, int[] tileCoordinate) throws ModelException {
		
		world.setTargetTile(tileCoordinate);
	}

	@Override
	public void startGame(World world) throws ModelException {
		try {
			world.startGame(world);
		}
		catch(Exception e) {
			throw new ModelException("cannot Start game");
		}
	}

	@Override
	public boolean isGameOver(World world) throws ModelException {
		
		return world.isGameOver();
	}

	@Override
	public boolean didPlayerWin(World world) throws ModelException {
		
		return world.didMazubWinTheGame();
	}

	@Override
	public void advanceWorldTime(World world, double dt) throws ModelException {
		try {
			world.advanceWorldTime(dt);
		}
		catch(Exception e) {
			throw new ModelException("IllegalTime");
		}
	}

	/*@Override
	public Plant createPlant(int pixelLeftX, int pixelBottomY, Sprite... sprites) throws ModelException {
		try {
			return new Plant(pixelLeftX,pixelBottomY,sprites);
		}
		catch(Exception e) {
			throw new ModelException("Not a valid Plant");
		}
	}*/

	@Override
	public void terminateGameObject(Object gameObject) throws ModelException {
		GameObject newGameObject= (GameObject) gameObject;
		newGameObject.terminate();
		
	}

	@Override
	public boolean isTerminatedGameObject(Object gameObject) throws ModelException {
		GameObject newGameObject= (GameObject) gameObject;
		return newGameObject.isTerminated();
	}

	@Override
	public boolean isDeadGameObject(Object gameObject) throws ModelException {
		GameObject newGameObject= (GameObject) gameObject;
		return newGameObject.isDeadGameobject();
	}

	@Override
	public double[] getActualPosition(Object gameObject) throws ModelException {
		GameObject newGameObject= (GameObject) gameObject;
		return newGameObject.getPosition();
	}

	@Override
	public void changeActualPosition(Object gameObject, double[] newPosition) throws ModelException {
		
		GameObject newGameObject= (GameObject) gameObject;
		newGameObject.changePosition(newPosition);
	}

	@Override
	public int[] getPixelPosition(Object gameObject) throws ModelException {
		
		GameObject newGameObject= (GameObject) gameObject;
		return newGameObject.getPixelPosition();
	}

	@Override
	public int getOrientation(Object gameObject) throws ModelException {
		
		GameObject newGameObject= (GameObject) gameObject;
		return newGameObject.getOrientation();
	}

	@Override
	public double[] getVelocity(Object gameObject) throws ModelException {
		GameObject newGameObject= (GameObject) gameObject;
		return newGameObject.getVelocity();
	}

	@Override
	public World getWorld(Object object) throws ModelException {
		if(object instanceof GameObject) {
			GameObject newGameObject= (GameObject) object;
			return newGameObject.getWorld();
		}
		else if (object instanceof School) {
			School school = (School) object;
			return school.getWorldOfThisSchool();
		}
		else {
			return null;
		}
	}

	@Override
	public int getHitPoints(Object object) throws ModelException {
		GameObject newGameObject= (GameObject) object;
		return newGameObject.getHitPoints();
		
	}

	@Override
	public Sprite[] getSprites(Object gameObject) throws ModelException {
		GameObject newGameObject= (GameObject) gameObject;
		return newGameObject.getSprites();
	}
	public Sprite getCurrentSprite(Object gameObject) throws ModelException {
		GameObject newGameObject= (GameObject) gameObject;
		return newGameObject.getCurrentSprite();
	}

	@Override
	public void advanceTime(Object gameObject, double dt) throws ModelException {
		try {
		GameObject newGameObject= (GameObject) gameObject;
		newGameObject.advanceTime(dt);
		newGameObject.terminator(newGameObject.getWorld());
		}
		catch(Exception e) {
			throw new ModelException("Not a valid time");
		}
	}

	@Override
	public double[] getAcceleration(Object gameObject) throws ModelException {
		GameObject newGameObject= (GameObject) gameObject;	
		return newGameObject.getAcceleration();
	}

	@Override
	public Sneezewort createSneezewort(int pixelLeftX, int pixelBottomY, Sprite... sprites) throws ModelException {
		try {
			return new Sneezewort(pixelLeftX,pixelBottomY,sprites);
		}
		catch(Exception e) {
			throw new ModelException("Illegal SneezeWort");
		}
	}

	@Override
	public Skullcab createSkullcab(int pixelLeftX, int pixelBottomY, Sprite... sprites) throws ModelException {
		try {
			return new Skullcab(pixelLeftX,pixelBottomY,sprites);
		}
		catch(Exception e) {
			throw new ModelException("Illegal SneezeWort");
		}
	}

	@Override
	public Slime createSlime(long id, int pixelLeftX, int pixelBottomY, School school, Sprite... sprites)
			throws ModelException {
		try {
			return new Slime(id,pixelLeftX, pixelBottomY, school, sprites);
		}
		catch(Exception e) {
			throw new ModelException("Illegal Slime");
		}
	}

	@Override
	public long getIdentification(Slime slime) throws ModelException {
		return slime.getID();
	}

	@Override
	public School createSchool(World world) throws ModelException {
		try {
			return new School(world);
		}
		catch(Exception e) {
			throw new ModelException("Illegal school");
		}
	}

	@Override
	public boolean hasAsSlime(School school, Slime slime) throws ModelException {
		return school.hasAsSlime(slime);
	}

	@Override
	public Collection<? extends Slime> getAllSlimes(School school) {
		
		return school.getCopyOfSlimesInThisSchool();
	}

	@Override
	public void addAsSlime(School school, Slime slime) throws ModelException {
		try {
			school.addAsSlime(slime);
		}
		catch(IllegalArgumentException e) {
			throw new ModelException("School can't be added");
		}
	}

	@Override
	public void removeAsSlime(School school, Slime slime) throws ModelException {
		try {
			school.removeAsSlime( slime);
		}
		catch(IllegalStateException e) {
			throw new ModelException("can't remove element out of school");
		}
	}
	
	@Override
	public void switchSchool(School newSchool, Slime slime) throws ModelException {
		try {
			slime.switchSchool(newSchool);
		}
		catch(IllegalStateException e) {
			throw new ModelException("no school to switch");
		}
	}

	@Override
	public School getSchool(Slime slime) throws ModelException {
		return slime.getSchool();
	}

	@Override
	public Set<School> getAllSchools(World world) throws ModelException {
		return world.getAllTheSchoolsInThisWorld();
	}

	@Override
	public void terminateSchool(School school) throws ModelException {
		school.terminate();
	}

	@Override
	public boolean isLateTeamSplit() {
		return false;
	}

	@Override
	public void cleanAllSlimeIds() {
		Slime.clearAllSlimesIDS();
	}
	
	@Override
	public Shark createShark(int pixelLeftX, int pixelBottomY, Sprite... sprites) throws ModelException {
		try{
			return new Shark(pixelLeftX,pixelBottomY,sprites);
		}
		catch(Exception e) {
			throw new ModelException("Invalid shark");
		}
	}

	@Override
	public boolean hasImplementedWorldWindow() {
		return true;
	}

}
