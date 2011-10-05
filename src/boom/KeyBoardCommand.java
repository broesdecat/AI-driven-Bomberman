package boom;

import gameobjects.Player;

public abstract class KeyBoardCommand {
	private final Player player;
	
	KeyBoardCommand(Player player){
		this.player = player;
	}
	
	public Player getPlayer(){
		return player;
	}
	
	public void execute(){
		if(player.isAlive()){
			reallyExecute();
		}
	}
	protected abstract void reallyExecute();
}
