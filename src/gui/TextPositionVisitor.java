package gui;

import gameobjects.Explosion;
import gameobjects.GameObject;
import abstractobjects.Direction;
import boom.WorldBlockI;

public class TextPositionVisitor {
	String toText(WorldBlockI block){
		if(block.getObjects().size()>0){
			for (GameObject object : block.getObjects()) {
				// TODO order of objects?
				switch (object.getObjectType()) {
					case PLAYER: return "P";
					case BOMB: return "B";
					case EXPLOSION:
						Explosion expl = (Explosion)object;
						if(expl.getDirection()==Direction.EAST || expl.getDirection()==Direction.WEST){
							return "-";
						}else{
							return "|";
						}
					case BLOCK: return "X";
				}
			}
		}
		switch(block.getTerrainType()){
			case NONE: return " ";
			case STAIRS: return "=";
			case LAVA: return "A";
			case PLAIN: return "_";
			default: return " ";
		}
	}
}
