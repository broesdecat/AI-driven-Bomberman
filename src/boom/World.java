package boom;

import gameobjects.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import abstractobjects.Position;

public class World implements WorldI {
	//TODO refactor INT into class
	private int leftbound=0, rightbound=0, topbound=0, bottombound=0;
	private Set<Player> humanplayers = new HashSet<Player>(); 
	
	public World(){	}
	
	private Map<Position, WorldBlock> pos2block = new HashMap<Position, WorldBlock>();
	
	public void addBlock(TerrainType type, Position pos){
		if(pos2block.containsKey(pos)){
			throw new IllegalArgumentException("There are two blocks at position " + pos);
		}
		pos2block.put(pos, new WorldBlock(type, pos));
		if(!pos2block.containsKey(pos)){
			System.out.println("Problem");
		}
		if(!pos2block.containsKey(new Position(pos))){
			System.out.println("Problem");
		}
		if(pos2block.size()==1){
			leftbound = pos.x;
			rightbound = pos.x;
			topbound = pos.y;
			bottombound = pos.y;
		}else{
			if(pos.x<leftbound){ leftbound = pos.x; }
			if(rightbound<pos.x){ rightbound = pos.x; }
			if(pos.y<topbound){ topbound = pos.y; }
			if(bottombound<pos.y){ bottombound = pos.y; }
		}
	}
	
	@Override
	public int getTopBound(){
		return topbound;
	}
	
	@Override
	public int getBottomBound(){
		return bottombound;
	}
	
	@Override
	public int getRightBound(){
		return rightbound;
	}
	
	@Override
	public int getLeftBound(){
		return leftbound;
	}
	
	@Override
	public int getHeight(){
		return Math.abs(getBottomBound()-getTopBound());
	}
	
	@Override
	public int getWidth(){
		return Math.abs(getLeftBound()-getRightBound());
	}
	
	public boolean hasBlock(Position p){
		return getBlock(p)!=null;
	}
	public WorldBlockI getBlock(Position p){
		return pos2block.get(p);
	}
	public WorldBlock getRealBlock(Position p){
		return pos2block.get(p);
	}
	
	public void addHumanPlayer(Player p){
		humanplayers.add(p);
	}
	public Set<Player> getHumanPlayers(){
		return humanplayers;
	}
}
