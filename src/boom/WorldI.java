package boom;

import gameobjects.Player;

import java.util.Set;

import abstractobjects.Position;

// TODO: this is an unsafe way to prevent changed the world outside of the world manager (by casting, it can still be changed!)
public interface WorldI {

	public abstract int getTopBound();

	public abstract int getBottomBound();

	public abstract int getRightBound();

	public abstract int getLeftBound();

	public abstract int getHeight();

	public abstract int getWidth();
	
	public abstract Set<Player> getHumanPlayers();
	
	public abstract boolean hasBlock(Position p);
	public abstract WorldBlockI getBlock(Position p);
}