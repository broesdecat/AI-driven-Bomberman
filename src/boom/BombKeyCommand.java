package boom;

import gameobjects.Player;

public class BombKeyCommand extends KeyBoardCommand {
	public BombKeyCommand(Player player) {
		super(player);
	}
	
	@Override
	public void reallyExecute() {
		Engine.getEngine().addAction(new BombCommand(getPlayer()));
	}
}
