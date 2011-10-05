package boom;

public abstract class ActionCommand {
	public abstract void execute(WorldManager worldmanager);
	public abstract void accept(WorldManager worldmanager);
}
