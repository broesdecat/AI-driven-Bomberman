package gameobjects;

import abstractobjects.Direction;

public class Explosion extends GameObject{
	private Direction richting;
	private long endTime = System.currentTimeMillis()+1500;
	
	public Explosion(Direction richting){
		super(ObjectType.EXPLOSION, 0, CrossProperty.PASSABLE);
		this.richting = richting;
	}
	public Direction getDirection() {
		return richting;
	}
	public long getEndTime(){
		return endTime;
	}
}
