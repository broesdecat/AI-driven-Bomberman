package gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import boom.Engine;

public class WindowEventHandler implements WindowListener{
	private Engine boem;
	
	public WindowEventHandler(Engine boem){
		this.boem=boem;
	}
	
	public void windowClosing(WindowEvent arg0) {
		 boem.endGame();
	}

	public void windowOpened(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowActivated(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
}
