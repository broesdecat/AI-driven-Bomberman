package boom;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import abstractobjects.Position;
import gameobjects.GameObject;
import gameobjects.ObjectType;
import gameobjects.Player;

public class WorldManager {
	private World world;
	
	public WorldManager(World world){
		this.world = world;
	}
	
	public void addObject(Position p, GameObject object){
		// FIXME check position preconditions
		boolean cannotplace = false;
		cannotplace |= world.getBlock(p)==null;
		// FIXME but should be able to place a bomb on a player! => whether multiple objects can share a square?
		/*for(GameObject go: world.getRealBlock(p).getObjects()){
			if(go.getCrossProperty()!=CrossProperty.PASSABLE){ // FIXME could be a hard fail? If a bomb cant be placed, program will error with nullpointer later. Should at least be able to check whether adding was a success
				cannotplace = true; break;
			}
		}*/
		if(cannotplace){
			System.err.println("Cannot place " + object + " on " + p);
			return;
		}
		
		world.getRealBlock(p).addObject(object);
		if(object instanceof Player){//fixme should not be here
			world.addHumanPlayer((Player)object);
		}
		
		// TODO add placement effects (refactor real add method)
		Set<Player> playerstokill = new HashSet<Player>();
		if(object.getObjectType() == ObjectType.EXPLOSION){
			for(GameObject otherobject: getObjects(p)){
				if(otherobject.getObjectType() == ObjectType.PLAYER){
					playerstokill.add((Player)otherobject);
				}else if(otherobject.getObjectType() == ObjectType.BLOCK){
					// TODO check destructability
					removeObject(otherobject);
				}
			}
		}
		if(object.getObjectType() == ObjectType.PLAYER){
			for(GameObject otherobject: getObjects(p)){
				if(otherobject.getObjectType() == ObjectType.EXPLOSION){
					playerstokill.add((Player)otherobject);
					break;
				}
			}		
		}
		if(object.getObjectType() == ObjectType.BLOCK){
			for(GameObject otherobject: getObjects(p)){
				if(otherobject.getObjectType() == ObjectType.EXPLOSION){
					removeObject(object);
					break;
				}
			}			
		}
		if(object.getObjectType() == ObjectType.PLAYER && getWorld().getBlock(p).getTerrainType()==TerrainType.LAVA){
			playerstokill.add((Player)object);
		}
		for (Player player : playerstokill) {
			player.die();
			removeObject(player);
			if(player.getLives()>0){
				addObject(player.getStartPosition(), object);
			}
			// FIXME move this code to some logical place!
			// FIXME remove player from board, reduce lives, ... as flag in the engine?
		}
	}
	
	public void removeObject(GameObject object){
		world.getRealBlock(object.getPosition()).getObjects().remove(object);
	}

	private ArrayList<ActionCommand> commands = new ArrayList<ActionCommand>();
	public void queueCommand(ActionCommand comm){
		commands.add(comm);
	}
	
	public void handleQueue(){
		Deque<ActionCommand> tempqueue = new LinkedList<ActionCommand>();
		synchronized (this) {
			tempqueue.addAll(commands);
			commands.clear();
		}
		while(tempqueue.size()>0){
			ActionCommand d = tempqueue.pop();
			d.execute(this);
		}
	}
	
	private World getWorld(){
		return world;
	}
	public WorldI getWorldI(){
		return world;
	}

	public WorldBlockI getBlock(Position pos) {
		return getWorld().getBlock(pos);
	}

	public ArrayList<GameObject> getObjects(Position pos) {
		if(!getWorld().hasBlock(pos)){
			return new ArrayList<GameObject>();
		}
		return getWorld().getRealBlock(pos).getObjects();
	}
}