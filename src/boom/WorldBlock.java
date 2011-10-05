package boom;

import gameobjects.GameObject;

import java.util.ArrayList;

import abstractobjects.Position;

public class WorldBlock implements WorldBlockI {
	private TerrainType type;
	private Position position;
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	
	WorldBlock(TerrainType type, Position position){
		this.type = type;
		this.position = position;
	}

	/* (non-Javadoc)
	 * @see boom.WorldBlockI#getTerrainType()
	 */
	@Override
	public TerrainType getTerrainType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see boom.WorldBlockI#getPosition()
	 */
	@Override
	public Position getPosition() {
		return position;
	}

	/* (non-Javadoc)
	 * @see boom.WorldBlockI#getObjects()
	 */
	@Override
	public final ArrayList<GameObject> getObjects() {
		return objects;
	}
	
	public void removeObject(GameObject object){
		objects.remove(object);
		object.setPosition(null);
	}
	
	public void addObject(GameObject object){
		objects.add(object);
		object.setPosition(getPosition());
	}

	@Override
	public void setTerrainType(TerrainType type) {
		// FIXME check objects on the block!
		this.type = type;
	}
}
