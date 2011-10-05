package boom;

import gameobjects.Block;
import gameobjects.Player;

import java.io.File;

import abstractobjects.Position;

public class WorldFactory {
	public static WorldManager createWorld(File leveldata){
		// TODO introduce leveldata
		//return new World();
		return createLevel2();
	}
	
	public static WorldManager createLevel1(){
		World w = new World();
		w.addBlock(TerrainType.PLAIN, new Position(1,1,0));
		w.addBlock(TerrainType.PLAIN, new Position(1,2,0));
		w.addBlock(TerrainType.PLAIN, new Position(2,1,0));
		w.addBlock(TerrainType.PLAIN, new Position(1,3,0));
		WorldManager manager = new WorldManager(w);
		manager.addObject(new Position(2,2,0), new Block());
		manager.addObject(new Position(1,2,0),new Player(new Position(1,2,0)));
		return manager;
	}
	
	public static WorldManager createLevel2(){
		World w = new World();
		for(int i=0; i<30; i++){
			for(int j=0; j<20; j++){
				w.addBlock(TerrainType.PLAIN, new Position(i,j,0));
			}
		}
		WorldManager manager = new WorldManager(w);
		for(int i=1; i<29; i++){
			manager.addObject(new Position(i,2,0), new Block());
			manager.addObject(new Position(i,6,0), new Block());
			manager.addObject(new Position(i,7,0), new Block());
			w.getBlock(new Position(i,16,0)).setTerrainType(TerrainType.LAVA);
		}
		manager.addObject(new Position(1,3,0), new Block());
		//manager.addObject(new Position(1,2,0),new Player(new Position(1,2,0)));
		manager.addObject(new Position(9,3,0),new Player(new Position(9,3,0)));
		manager.addObject(new Position(20,15,0),new Player(new Position(20,15,0)));
		return manager;
	}
}
