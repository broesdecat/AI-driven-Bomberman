package boom;

import gameobjects.GameObject;

import java.util.ArrayList;

import abstractobjects.Position;

public interface WorldBlockI {

	public abstract TerrainType getTerrainType();

	public abstract Position getPosition();

	public abstract ArrayList<GameObject> getObjects();

	public abstract void setTerrainType(TerrainType type);
}