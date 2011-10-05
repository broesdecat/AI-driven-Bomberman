package gameobjects;

public class Bomb extends GameObject{
	private int kracht; 
	private int timersec;
	
	public Bomb(int kracht, int timersec) {
		super(ObjectType.BOMB, 0, CrossProperty.PUSHABLE);
		this.kracht = kracht;
		this.timersec = timersec;
	}

	public int getKracht() {
		return kracht;
	}
	public long getTimer() {
		return timersec*1000;
	}
}
