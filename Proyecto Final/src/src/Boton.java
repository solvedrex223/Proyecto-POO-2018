package src;

import java.awt.Color;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Boton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean bomba;

	Boton(boolean b, int a, ImageIcon c) throws IOException {
		super();
		bomba = b;
		this.setIcon(c);
		this.setBackground(Color.GRAY);

	}
}
