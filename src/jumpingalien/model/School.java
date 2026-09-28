package jumpingalien.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
* @invar	The slimes associated with each School must bea valid slime for 
* that school
* 		|this.IsValid(slimes)
* 
* @invar Each gameObject references this world as the world that it is attached
* 		| for each GameObject in gameObjects:
* 		|	(GameObject.getWorld()==this
*/
public class School {
	
	/**
	 * 
	 * @param world
	 * 
	 * @post this initializes a new school in the given world
	 * 		|new.getWorldOfThisSchool == world
	 * 		|if (world != null) 
	 *		|world.addASchoolToTheWorld(this)
	 *
	 */
	public School(World world) {
		setTheWorldOfThisSchool(world);
		if (world != null) {
			world.addASchoolToTheWorld(this);
		}
	}
	
	/**
	 * 
	 * @param world
	 * 
	 * @post this function sets the world of this school
	 * 		|new.getWorldOfThisSchool == world
	 */
	
	public void setTheWorldOfThisSchool(World world) {
		theWorldOfThisSchool = world;
	}
	
	/**
	 * 
	 * @return this function returns the world of this school
	 */
	
	public World getWorldOfThisSchool() {
		return theWorldOfThisSchool;
	}
	
	/**
	 * variable returning the world of this school
	 */
	private World theWorldOfThisSchool;
	
	/**
	 * 
	 * @param slime
	 * 
	 * @post this function checks if a certain slime is in the school
	 * 		|if	(this.getSizeOfTheSchool()==0) 
	 *		|return false
	 *		|int index= this.findTheGivenElement(slime.getID());
	 * @return this function returns whether or not the slime is in the school, 
	 * it returns false if the school contains no slimes
	 * 		|return index>=0
	 */
	
	public boolean hasAsSlime(Slime slime) {
		if(this.getSizeOfTheSchool()==0) {
			return false;
		}
		int index= this.findTheGivenElement(slime.getID());
		return index>=0;
	}
	/**
	 * 
	 * @param slime
	 * @throws IllegalArgumentException
	 * 		| (! isValidSlime(slime)
	 *
	 *@post this function adds a slime to this school
	 *		|new.slime.getSchool == this
	 *		|this.getTheSlimesInThisSchool().add(slime);
	 *		|int newID= slime.getID()
	 *	 	|int j=this.getSizeOfTheSchool()-2
	 *		|while (j>=0 && newID< this.getTheSlimesInThisSchool().get(j).getID()) 
	 *		|this.getTheSlimesInThisSchool().set(j+1, this.getTheSlimesInThisSchool().get(j))
	 *		|j--
	 *		|this.getTheSlimesInThisSchool().set(j+1, slime)
	 */
	public void addAsSlime(Slime slime) throws IllegalArgumentException {
		if (! isValidSlime(slime)) {
			
			throw new IllegalArgumentException("slime can't be added");
		}
		//if (slime.getWorld() != null) {
		//	throw new IllegalArgumentException("this slime already has a world"); 
		//}
		slime.setSchool(this);
		this.getTheSlimesInThisSchool().add(slime);
		int newID= slime.getID();
		int j=this.getSizeOfTheSchool()-2;
		while (j>=0 && newID< this.getTheSlimesInThisSchool().get(j).getID()) {
			this.getTheSlimesInThisSchool().set(j+1, this.getTheSlimesInThisSchool().get(j));
			j--;
		}
		this.getTheSlimesInThisSchool().set(j+1, slime);
	}
	
	/**
	 * Checks if the given SLime is a valid Slime for this school
	 * @param slime
	 * 		the given slime
	 * @return
	 * 		result==(slime!=null&&(slime.getSchool()==null||slime.getSchool()==this)&& 
	 *			! this.isTerminated()&& !slime.isTerminated()))
	 */
	public boolean isValidSlime(Slime slime) {
		return	(slime!=null&&(slime.getSchool()==null||slime.getSchool()==this)&& 
				! this.isTerminated()&& !slime.isTerminated());
	}
	
	/**
	 * 
	 * @param slime
	 * @throws IllegalStateException
	 * 		|if(this.getSizeOfTheSchool()==0)
	 * 		|if(index<0) 
	 * 
	 * @post this function removes a slime from the current school.
	 * 		|int index=this.findTheGivenElement(slime.getID())
	 *		|Slime slimeToRemove=this.getTheSlimesInThisSchool().get(index)
	 *		|new.slimeToRemove.getSchool == null
	 *		|this.getTheSlimesInThisSchool().remove(index)
	 */
	
	public void removeAsSlime(Slime slime) throws IllegalStateException  {
		if(this.getSizeOfTheSchool()==0) {
			throw new IllegalStateException("EmptySchool");
		}
		int index=this.findTheGivenElement(slime.getID());
		if(index<0) {
			throw new IllegalStateException("No slimes to remove");
		}
		Slime slimeToRemove=this.getTheSlimesInThisSchool().get(index);
		slimeToRemove.setSchool(null);
		this.getTheSlimesInThisSchool().remove(index);
	}
	
	/**
	 * 
	 * @param ID
	 * 
	 * @post this function finds a certain element in all the slimes.
	 * 		|int a=0
	 *		|int b= this.getSizeOfTheSchool()-1
	 *		|while (a<b) 
	 *		|int m = (int) ((a+b)/2)
	 *		|if (this.getTheSlimesInThisSchool().get(m).getID()<ID) 
	 *		|a=m+1
	 *		|else 
	 *		|b=m
	 *
	 * @return this function returns if it is in here or not.
	 * 		|if(this.getTheSlimesInThisSchool().get(b).getID()!=ID) {
	 *		|return -1
	 *		|}
	 *		|return b
	 *
	 * @throws IllegalStateException
	 */
	
	public int findTheGivenElement(int ID)throws IllegalStateException  {
		int a=0;
		int b= this.getSizeOfTheSchool()-1;
		while (a<b) {
			int m = (int) ((a+b)/2);
			if (this.getTheSlimesInThisSchool().get(m).getID()<ID) {
				a=m+1;
			}
			else {
				b=m;
			}
		}
		if(this.getTheSlimesInThisSchool().get(b).getID()!=ID) {
			return -1;
		}
		return b;
	}
	/**
	 * 
	 * @return this function returns all the slimes in this school.
	 * 
	 */
	public List<Slime> getTheSlimesInThisSchool() {
		return slimesInThisSchool;
	}
	
	/**
	 * 
	 * @return this function returns a copy of the collection with all slimes in this school
	 */
	
	public List<Slime> getCopyOfSlimesInThisSchool(){
		return makeCopyOfList(this.getTheSlimesInThisSchool());
	}
	
	/**
	 * 
	 * @param listToCopy
	 * 
	 * @post this function makes a copy of the collection with all slimes in this school
	 * 		|List<T> copyOfList= new ArrayList<T>()
	 *		|for (int i=0; i< listToCopy.size(); i++) 
	 *		|copyOfList.add(listToCopy.get(i))
	 *
	 * @return this function returns a copy of the collection with all slimes in this school
	 * 		|return copyOfList
	 */
	
	public static <T> List<T> makeCopyOfList(List<T> listToCopy) {
		List<T> copyOfList= new ArrayList<T>();
		for (int i=0; i< listToCopy.size(); i++) {
			copyOfList.add(listToCopy.get(i));
		}
		return copyOfList;
	}
	
	/**
	 * a collection of slimes in this school
	 */
	
	public List<Slime> slimesInThisSchool= new ArrayList<Slime>();

	/**
	 * 
	 * @return this function returns the amount of slimes in this school
	 */
	
	public int getSizeOfTheSchool() {
		return slimesInThisSchool.size();
	}
	
	/**
	 * 
	 * @param hitpoints
	 * @param slime
	 * 
	 * @post this function adds hitpoints to all the slimes in the same school as the slime that is given.
	 * 		|for(int i=0; i<this.getSizeOfTheSchool();i++ ) 
	 *		|if(slime != this.getTheSlimesInThisSchool().get(i)) 
	 *		|this.getTheSlimesInThisSchool().get(i).addHitpoints(hitpoints)
	 *		|totalHitpointslost-=hitpoints
	 *
	 * @return this function returns the amount of hitpoints that have been lost or given.
	 * 		|return totalHitpointslost
	 * 
	 */
	
	public int groupGiveHitpoints(int hitpoints, Slime slime) {
		int totalHitpointslost=0;
		for(int i=0; i<this.getSizeOfTheSchool();i++ ) {
			if(slime != this.getTheSlimesInThisSchool().get(i)) {
				this.getTheSlimesInThisSchool().get(i).addHitpoints(hitpoints);
				totalHitpointslost-=hitpoints;
			}
		}
		return totalHitpointslost;
	}
	
	/**
	 * 
	 * @param slime
	 * 
	 * @post this function removes a hitpoint from all the slimes in the same school as the one that is given.
	 * 		|groupGiveHitpoints(-1, slime)
	 */
	
	public void groupSharePain( Slime slime) {
		groupGiveHitpoints(-1, slime);
	}

	/**
	 * @post this function removes all the slimes from this school.
	 * 		|for(int i=0; i<this.getSizeOfTheSchool();i++ ) 
	 *		|new.this.getTheSlimesInThisSchool().get(i).getSchool == null
	 */
	
	public void removeAllSlimes() {
		for(int i=0; i<this.getSizeOfTheSchool();i++ ) {
			this.getTheSlimesInThisSchool().get(i).setSchool(null);
		}
	}
	/**
	 * @post this terminates this school from the world
	 * 		|new.this.getTheWorldOfThisSchool == null
	 *		|this.removeAllSlimes()
	 *		|this.terminated=true
	 */
	public final void terminate() {
		
//		if (this.getTheWorldOfThisSchool()!=null) {
//			(this.getTheWorldOfThisSchool()).removeAsGameObject(this);
//			}
		this.setTheWorldOfThisSchool(null);
		this.removeAllSlimes();
		this.terminated=true;
	}
	
	 /**
	 * @return this function returns if the variable is terminated or not.
	 * 		|return this.terminated
	 */
				
	public final boolean isTerminated() {
		return this.terminated;
	}
	
	/**
	 * variable containing if this school is terminated or not
	 */
	
	public boolean terminated;

	
}
