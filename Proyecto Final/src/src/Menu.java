package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Menu extends JFrame implements Dificultad, ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JRadioButton principiante = new JRadioButton ("Principiante"),
				 intermedio = new JRadioButton ("Intermedio"),
				 avanzado = new JRadioButton ("Avanzado");
	ButtonGroup bg = new ButtonGroup();
	JLabel bienvenida = new JLabel ("Bienvenido a Buscaminas"),
		   dificultad = new JLabel ("Seleccione la dificultad"),
		   nom = new JLabel ("Nombre del jugador: ");
	JButton confirmar = new JButton ("Jugar");
	JPanel grid = new JPanel (),
		   menu = new JPanel (),
		   grid2 = new JPanel ();
	JTextField tf = new JTextField ();
	Font f = new Font ("Comic Sans", Font.ITALIC, 16),
			f2 = new Font ("Sans", Font.BOLD, 30);

	
	Menu (){
		super ();
		bienvenida.setForeground(Color.WHITE);
		bienvenida.setFont(f2);
		bienvenida.setHorizontalAlignment(0);
		nom.setForeground(Color.WHITE);
		nom.setFont(f2);
		nom.setHorizontalAlignment(0);
		grid2.setBackground(Color.BLACK);
		menu.setLayout(new BorderLayout());
		menu.setPreferredSize(new Dimension (550,550));
		grid.setLayout(new GridLayout(4,1));
		grid2.setLayout(new GridLayout(3,1));
		grid.setBackground(Color.BLACK);
		tf.setText("");
		tf.setMaximumSize((new Dimension (50,30)));
		tf.setHorizontalAlignment(0);
		grid2.add(bienvenida);
		grid2.add(nom);
		grid2.add(tf);
		menu.add(grid2, BorderLayout.NORTH);
		principiante.setBackground(null);
		intermedio.setBackground(null);
		avanzado.setBackground(null);
		principiante.setFont(f);
		intermedio.setFont(f);
		avanzado.setFont(f);
		principiante.setForeground(Color.white);
		intermedio.setForeground(Color.white);
		avanzado.setForeground(Color.white);
		principiante.setHorizontalAlignment(0); 
		intermedio.setHorizontalAlignment(0); 
		avanzado.setHorizontalAlignment(0); 
		dificultad.setForeground(Color.WHITE);
		dificultad.setFont(f2);
		dificultad.setHorizontalAlignment(0);
		
		bg.add(principiante);
		bg.add(intermedio);
		bg.add(avanzado);
		
		grid.add(dificultad);
		grid.add(principiante);
		grid.add(intermedio);
		grid.add(avanzado);
		menu.add(grid, BorderLayout.CENTER);
		
		confirmar.addActionListener(this);
		menu.add(confirmar, BorderLayout.SOUTH);
		this.add(menu);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (principiante.isSelected() && !tf.getText().equals("")) {
			try {
				this.setVisible(false);
				System.out.println(tf.getText());
				new Juego(prin_grid,prin_bomb, prin_bsize,tf.getText()).setVisible(true);;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (intermedio.isSelected() && !tf.getText().equals("")) {
			try {
				this.setVisible(false);
				new Juego(inter_grid,inter_bomb, inter_bsize,tf.getText()).setVisible(true);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (avanzado.isSelected() && !tf.getText().equals("")) {
			try {
				this.setVisible(false);
				new Juego(avan_grid,avan_bomb, avan_bsize,tf.getText()).setVisible(true);;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else {
			JOptionPane.showMessageDialog(this, "Complete todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	public static void main(String[] args) {
		new Menu ();
	}
}
