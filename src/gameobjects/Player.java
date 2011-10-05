package gameobjects;

import abstractobjects.Position;

public class Player extends GameObject {
	private int strength, bombsleft, lives;
	private Position startPosition;
	//private ArrayList<GameObject> inventory = new ArrayList<GameObject>();
	
	public Player(Position startposition){
		super(ObjectType.PLAYER, 0, CrossProperty.FIXED);
		setMinMoveInterval(100);
		this.startPosition = startposition;
		this.lives = 3;
		this.bombsleft = 3;
		this.strength = 300;
	}
	public void die(){
		lives--;
	}
	public int getLives() {
		return lives;
	}
	public int getStrength() { return strength; }
	public Position getStartPosition() {
		return startPosition;
	}
	public boolean hasBombsLeft(){
		return bombsleft>0;
	}
	public void useBomb(){
		bombsleft--;
	}
	public void addBomb(){
		bombsleft++;
	}
	public boolean isAlive() {
		return lives>0;
	}
}