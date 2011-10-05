package boom;

import gameobjects.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import abstractobjects.Direction;

/**
 * NOTE: typed and pressed events are always given at the same times.
 * When keeping a button pressed, there is some delay between the first and second events, then it goes fast.
 * The release event does not trigger waking up the thread as fast as the other ones, so cannot really use it.
 */
public class KeyboardEventHandler implements KeyListener {
	Map<Character, KeyBoardCommand> keypressedmap = new HashMap<Character, KeyBoardCommand>();
	Map<Character, KeyBoardCommand> keyreleasedmap = new HashMap<Character, KeyBoardCommand>();

	// TODO turn into strategy depending on game state
	public KeyboardEventHandler(ArrayList<Player> mens) {
		if(mens.size()>0){
			keypressedmap.put('g', new BombKeyCommand(mens.get(0)));
			keypressedmap.put('q', new MoveKeyCommand(mens.get(0), Direction.WEST, MoveState.PRESS));
			keypressedmap.put('s', new MoveKeyCommand(mens.get(0), Direction.SOUTH, MoveState.PRESS));
			keypressedmap.put('d', new MoveKeyCommand(mens.get(0), Direction.EAST, MoveState.PRESS));
			keypressedmap.put('z', new MoveKeyCommand(mens.get(0), Direction.NORTH, MoveState.PRESS));
			keyreleasedmap.put('q', new MoveKeyCommand(mens.get(0), Direction.WEST, MoveState.RELEASE));
			keyreleasedmap.put('s', new MoveKeyCommand(mens.get(0), Direction.SOUTH, MoveState.RELEASE));
			keyreleasedmap.put('d', new MoveKeyCommand(mens.get(0), Direction.EAST, MoveState.RELEASE));
			keyreleasedmap.put('z', new MoveKeyCommand(mens.get(0), Direction.NORTH, MoveState.RELEASE));
		}
		if(mens.size()>1){
			keypressedmap.put('0', new BombKeyCommand(mens.get(1)));
			keypressedmap.put('1', new MoveKeyCommand(mens.get(1), Direction.WEST, MoveState.PRESS));
			keypressedmap.put('2', new MoveKeyCommand(mens.get(1), Direction.SOUTH, MoveState.PRESS));
			keypressedmap.put('3', new MoveKeyCommand(mens.get(1), Direction.EAST, MoveState.PRESS));
			keypressedmap.put('5', new MoveKeyCommand(mens.get(1), Direction.NORTH, MoveState.PRESS));			
			keyreleasedmap.put('1', new MoveKeyCommand(mens.get(1), Direction.WEST, MoveState.RELEASE));
			keyreleasedmap.put('2', new MoveKeyCommand(mens.get(1), Direction.SOUTH, MoveState.RELEASE));
			keyreleasedmap.put('3', new MoveKeyCommand(mens.get(1), Direction.EAST, MoveState.RELEASE));
			keyreleasedmap.put('5', new MoveKeyCommand(mens.get(1), Direction.NORTH, MoveState.RELEASE));
		}
	}

	public void keyPressed(KeyEvent arg0) {
		//System.out.println("Pressed " + arg0.getKeyChar());
	}

	public void keyReleased(KeyEvent arg0) {
		System.out.println("Released " + arg0.getKeyChar());
		/*char key = arg0.getKeyChar();
		if(keyreleasedmap.containsKey(key)){
			keyreleasedmap.get(key).execute();
		}*/
	}

	public void keyTyped(KeyEvent arg0) {
		//System.out.println("Typed " + arg0.getKeyChar());
		char key = arg0.getKeyChar();
		if(keypressedmap.containsKey(key)){
			keypressedmap.get(key).execute();
		}
	}
}