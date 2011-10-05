package boom;

import java.util.ArrayList;

import gameobjects.CrossProperty;
import gameobjects.GameObject;
import abstractobjects.Direction;
import abstractobjects.Position;

public class MoveCommand extends ActionCommand{
	public final Direction dir;
	public final GameObject object;
	public final MoveState state;
	
	public MoveCommand(GameObject object, Direction dir, MoveState state) {
		this.object = object;
		this.dir = dir;
		this.state = state;
	}
	
	public void accept(WorldManager worldmanager){
		if(state == MoveState.PRESS){
			object.move(this, worldmanager);
		}else{
			// TODO if released, delete the queued commands?
		}
	}
	
	public void execute(WorldManager worldmanager){
		object.moveUntilFinished(worldmanager);
	}
	
	public MoveResult moveObject(WorldManager worldmanager){
		WorldI world = worldmanager.getWorldI();
		if(!object.hasPosition()){
			System.err.println("Not moved because not position");
			return MoveResult.NOTMOVED;
		}
		Position nextpos = object.getPosition().add(dir);
		// TODO for stairs
		if(!world.hasBlock(nextpos)){
			System.err.println("Not moved because no next block");
			return MoveResult.NOTMOVED;
		}
		WorldBlockI nextblock = world.getBlock(nextpos);
		boolean cannotmove = nextblock.getTerrainType()==TerrainType.NONE;
		ArrayList<GameObject> objectlist = new ArrayList<GameObject>(nextblock.getObjects());
		for (GameObject object : objectlist) {
			if(cannotmove){
				break;
			}
			if(object.getCrossProperty() == CrossProperty.FIXED){
				cannotmove = true;
			}else if(object.getCrossProperty() == CrossProperty.PUSHABLE){
				cannotmove = new MoveCommand(object, dir, state).moveObject(worldmanager)!=MoveResult.MOVED;
			}
		}
		if(cannotmove){
			System.err.println("Not moved because no terrain or unpassable or could not be pushed");
			return MoveResult.NOTMOVED;
		}
		System.err.println("Really moved " + object + " to " + dir);
		worldmanager.removeObject(object);
		
		worldmanager.addObject(nextblock.getPosition(), object);
		return MoveResult.MOVED;
	}
}
