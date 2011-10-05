package boom;

import gui.GuiManager;
import gui.TextGuiManager;

import java.io.File;

public class Engine {
	private boolean endgame;
	private GuiManager gui = null;
	private WorldManager worldmanager = null;
	
	private static Engine engine = null;
	public static Engine getEngine(){
		if(engine==null){
			engine = new Engine();
		}
		return engine; 
	}
	
	public Engine() { }

	public static void main(String[] args) {
		Engine boem = Engine.getEngine();
		//JFileChooser chooselevel = new JFileChooser();
		//chooselevel.showOpenDialog(null);
		boem.startGame(/*chooselevel.getSelectedFile()*/null);
	}
	
	private void startGame(File leveldata) {
		endgame = false;
		worldmanager = WorldFactory.createWorld(leveldata);
		gui = new TextGuiManager(worldmanager.getWorldI());
		newGame();
	}
	
	private void newGame(){
		gameLoop();
	}
	
	private void gameLoop(){
		while(!shouldEndGame()){
			//synchronized (this) {
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) { System.err.println("Sleep failed"); }				
			//}
			worldmanager.handleQueue();
			gui.repaint();
		}
		gui.endGame();
	}
	
	public synchronized boolean shouldEndGame(){
		return endgame;
	}
	
	public synchronized void endGame() {
		endgame = true;
	}
	
	public void addAction(ActionCommand action) {
		action.accept(worldmanager);
	}
}
