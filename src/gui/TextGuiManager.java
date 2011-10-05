package gui;

import gameobjects.Player;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import abstractobjects.Position;
import boom.Engine;
import boom.KeyboardEventHandler;
import boom.WorldI;

public class TextGuiManager extends GuiManager {
	WorldI world = null;
	TextPositionVisitor positionvisitor = new TextPositionVisitor();
	private JFrame window = null;
	JTextArea gamewindow = null;
	JTextField livesplayerone = null, livesplayertwo = null;
	ArrayList<Player> players;
	
	public TextGuiManager(WorldI w){
		this.world = w;
		window = new JFrame();
		//window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setSize(500, 800);
		window.setTitle("Boom");
		window.setLayout(new FlowLayout());
		gamewindow = new JTextArea(w.getHeight(), w.getWidth());
		gamewindow.setEditable(false);
		Font f = new Font(Font.MONOSPACED, Font.BOLD, 18);
		gamewindow.setFont(f);
		players = new ArrayList<Player>(w.getHumanPlayers());//FIXME setting this in wrong place
		KeyListener keylis = new KeyboardEventHandler(players);
		gamewindow.addKeyListener(keylis);
		window.add(gamewindow);
		livesplayerone = new JTextField();
		livesplayertwo = new JTextField();
		JPanel panel = new JPanel();
		panel.add(livesplayerone);
		panel.add(livesplayertwo);
		window.add(panel);
		window.addWindowListener(new WindowEventHandler(Engine.getEngine()));
		window.setVisible(true);
	}
	
	public void endGame(){
		if(window!=null){
			window.setVisible(false);
			gamewindow.removeAll();
			window.removeAll();
			window.dispose();
		}	
	}
	
	public void repaint() {
		StringBuilder textbuilder = new StringBuilder();
		// FIXME iteration over z?
		Position temp = new Position(world.getLeftBound(), world.getTopBound(), 0);
		while(temp.y<=world.getBottomBound()){
			textbuilder.append("|");
			while(temp.x<=world.getRightBound()){
				if(world.hasBlock(temp)){
					textbuilder.append(positionvisitor.toText(world.getBlock(temp)));
				}else{
					textbuilder.append(" "); // Fixme should be in position visitor?
				}
				temp = new Position(temp.x+1, temp.y, 0);
			}
			textbuilder.append("|\n");
			temp = new Position(world.getLeftBound(), temp.y+1, 0);
		}
		gamewindow.setText(textbuilder.toString());
		livesplayerone.setText("Lives " + players.get(0) + " = " + players.get(0).getLives());
		livesplayertwo.setText("Lives " + players.get(1) + " = " + players.get(1).getLives());
	}
}