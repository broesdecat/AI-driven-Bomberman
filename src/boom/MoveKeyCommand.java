package boom;

import gameobjects.Player;
import abstractobjects.Direction;

public class MoveKeyCommand extends KeyBoardCommand {
	private Direction dir;
	private MoveState state;

	public MoveKeyCommand(Player player, Direction dir, MoveState state) {
		super(player);
		this.dir = dir;
		this.state = state;
	}
	
	@Override
	public void reallyExecute() {
		Engine.getEngine().addAction(new MoveCommand(getPlayer(), dir, state));
	}
}
