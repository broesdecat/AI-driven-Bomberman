package gameobjects;

import java.util.LinkedList;
import java.util.Queue;

import boom.MoveCommand;
import boom.MoveResult;
import boom.WorldManager;
import abstractobjects.Position;

public class GameObject {
	private static int maxID = 0;
	private int ID = maxID++;
	private ObjectType type;
	private Position pos = null;
	private long minmoveinterval = 0;
	private int weight = 0;
	private CrossProperty crossproperty;
	
	public int getWeight() { return weight; }
	public CrossProperty getCrossProperty() { return crossproperty; }
	
	public GameObject(ObjectType type, int weight, CrossProperty crossproperty){
		this.type = type;
		this.weight = weight;
		this.crossproperty = crossproperty;
	}

	public int getID() {
		return ID;
	}
	
	public ObjectType getObjectType(){
		return type;
	}
	public void setObjectType(ObjectType type){
		this.type = type;
	}
	
	public boolean hasPosition() { return pos!=null; }
	public Position getPosition(){ return pos; }	
	public void setPosition(Position pos) { this.pos = pos; }

	public void setMinMoveInterval(long interval){
		minmoveinterval = interval;
	}
	public boolean hasMinMoveInterval() {
		return minmoveinterval>0;
	}
	public long getMinMoveInterval() {
		return minmoveinterval;
	}
	
	private Queue<MoveCommand> movequeue = new LinkedList<MoveCommand>();
	private Long lastmove = null;
	
	public synchronized void move(MoveCommand m, WorldManager worldmanager){
		if(movequeue.size()==0){
			worldmanager.queueCommand(m);
		}
		movequeue.add(m);
	}
	
	public void moveUntilFinished(WorldManager worldmanager){
		Queue<MoveCommand> tempqueue;
		synchronized (this) {
			tempqueue = new LinkedList<MoveCommand>(movequeue);
		}
		
		MoveResult result = MoveResult.MOVED;
		while(result==MoveResult.MOVED && tempqueue.size()>0){
			if(lastmove!=null && hasMinMoveInterval() && System.currentTimeMillis()-lastmove<getMinMoveInterval()){
				System.err.println("Not moved because too early since last move");
				result = MoveResult.TOOEARLY;
			}
			result = tempqueue.remove().moveObject(worldmanager);
			if(result==MoveResult.MOVED){
				lastmove = System.currentTimeMillis();
			}
		}
		if(result==MoveResult.NOTMOVED){
			synchronized (this) {
				movequeue.clear();
			}
		}else{
			synchronized (this) {
				movequeue.clear();
				movequeue.addAll(tempqueue);
				if(movequeue.size()>0){
					worldmanager.queueCommand(movequeue.peek());
				}
			}
		}
	}
}
