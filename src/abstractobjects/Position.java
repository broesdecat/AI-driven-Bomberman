package abstractobjects;

public class Position {
	public final int x, y, z;
	
	public Position(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Position(Position pos){
		this(pos.x, pos.y, pos.z);
	}
	
	@Override
	public String toString(){
		return "["+x+","+y+","+z+"]";
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Position)){
			return false;
		}
		Position pos = (Position) obj; 
		return x==pos.x && y==pos.y && z==pos.z;
	}

	@Override
	public int hashCode() {
		return (x*10000+y*100+z)*23+7;
	}
	
	public Position add(Direction dir){
		int x2=x, y2=y;
		switch(dir){
		case EAST: x2++; break;
		case NORTH: y2--; break;
		case WEST: x2--; break;
		case SOUTH: y2++; break;
		}
		return new Position(x2, y2, z);
	}
}