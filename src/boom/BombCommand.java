package boom;

import java.util.ArrayList;
import java.util.Iterator;

import abstractobjects.Direction;
import abstractobjects.Position;
import gameobjects.Bomb;
import gameobjects.Explosion;
import gameobjects.GameObject;
import gameobjects.ObjectType;
import gameobjects.Player;

public class BombCommand extends ActionCommand {
	private Player player;
	private Position pos;
	private Bomb bomb = null;
	private long bombstart;
	private BombStrategy strategy = new SetBomb();
	
	public BombCommand(Player player) {
		this.player = player;
		this.pos = player.getPosition();
	}
	
	public void accept(WorldManager worldmanager){
		System.out.println("Placed bomb command in the queue");
		worldmanager.queueCommand(this);
	}
	
	abstract class BombStrategy{
		public abstract void execute(WorldManager worldmanager);
	}
	class SetBomb extends BombStrategy{
		public void execute(WorldManager worldmanager){
			if(player.hasBombsLeft()){
				player.useBomb();
				bomb = new Bomb(5,3);
				worldmanager.addObject(pos, bomb);
				bombstart = System.currentTimeMillis();
				worldmanager.queueCommand(BombCommand.this);
				strategy = new CountDown();
			}
		}
	}
	class CountDown extends BombStrategy{
		public void execute(WorldManager worldmanager){
			if(System.currentTimeMillis()>=bombstart+bomb.getTimer()){
				strategy = new Exploding();
				strategy.execute(worldmanager);
				worldmanager.removeObject(bomb);
				player.addBomb();
			}
			worldmanager.queueCommand(BombCommand.this);
		}
	}
	
	class Exploding extends BombStrategy{
		class Dir2Power{
			public final Direction dir;
			public int powerused;
			public int maxpower;
			
			public Dir2Power(Direction dir, int maxpower){
				this.dir = dir;
				this.powerused = 0;
				this.maxpower = maxpower;
			}
		}
		private ArrayList<Dir2Power> dirpowers = new ArrayList<Dir2Power>();
		private ArrayList<Explosion> explosions = new ArrayList<Explosion>();
		private long explosionstart = System.currentTimeMillis();
		
		public Exploding(){
			for (Direction dir : Direction.values()) {
				dirpowers.add(new Dir2Power(dir, bomb.getKracht()));
			}
		}
		
		public boolean addExplosion(Direction dir, Position pos, WorldManager worldmanager){
			boolean halted = false;
			for(GameObject object: worldmanager.getObjects(pos)){
				if(object.getObjectType()==ObjectType.BLOCK){
					halted = true;
				}
			}
			
			Explosion explosion = new Explosion(dir);
			explosions.add(explosion);
			worldmanager.addObject(pos, explosion);
			
			return halted;
		}
		
		public boolean createExplosions(WorldManager worldmanager){
			boolean powerleft = false;
			for (Dir2Power dir2power: dirpowers) {
				if(dir2power.powerused<dir2power.maxpower){
					if(explosionstart+dir2power.powerused*500<System.currentTimeMillis()){
						dir2power.powerused++;
						Position newexpl = bomb.getPosition();
						for(int i=0; i<dir2power.powerused; ++i){
							newexpl = newexpl.add(dir2power.dir);
						}
						// TODO handle through what it cannot go?
						if(worldmanager.getBlock(newexpl)==null){
							dir2power.maxpower = 0;
							break;
						}
						boolean halted = addExplosion(dir2power.dir, newexpl, worldmanager);
						if(halted){
							dir2power.maxpower--;
						}
					}
					if(dir2power.powerused<dir2power.maxpower){
						powerleft = true;
					}
				}
			}
			return powerleft;
		}
		
		public boolean handleExplosions(WorldManager worldManager){
			for (Iterator<Explosion> explit = explosions.iterator(); explit.hasNext();) {
				Explosion explosion = (Explosion) explit.next();
				if(explosion.getEndTime()<System.currentTimeMillis()){
					worldManager.removeObject(explosion);
					explit.remove();
				}
			}
			return explosions.size()>0;
		}
		
		public void execute(WorldManager worldmanager){
			boolean queueagain = createExplosions(worldmanager);
			queueagain |= handleExplosions(worldmanager);
			if(queueagain){
				worldmanager.queueCommand(BombCommand.this);
			}
		}
	}

	public void execute(WorldManager worldmanager){
		strategy.execute(worldmanager);
	}
}
