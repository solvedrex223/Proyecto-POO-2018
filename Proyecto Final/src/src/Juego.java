package src;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.commons.lang3.time.StopWatch;

public class Juego extends JFrame implements MouseListener, Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Random r = new Random(System.currentTimeMillis());
	Boton[][] botones;
	JPanel grid = new JPanel(),
			panel = new JPanel ();
	JOptionPane op;
	int bombas = 0, ganar, cont = 0;
	Object[] opciones = { "Si", "No" };
	ImageIcon[] img = new ImageIcon[8];
	Font prin = new Font("Arial", Font.PLAIN, 1);
	JLabel nombre = new JLabel(), cronometro = new JLabel();
	boolean tiempo = false;
	StopWatch sw = new StopWatch();
	Thread hilo = new Thread(this);

	Juego(int a, int b, int d, String c) throws IOException {
		super();
		hilo.start();
		nombre.setText(c);
		cronometro.setText("0.0");
		cronometro.setFont(new Font ("Sans", Font.ITALIC, 20));
		cronometro.setHorizontalAlignment(0);
		cronometro.setBackground(null);
		cronometro.setForeground(Color.WHITE);
		nombre.setHorizontalAlignment(0);
		nombre.setFont(new Font("Sans", Font.ITALIC, 30));
		nombre.setForeground(Color.WHITE);
		img[0] = new ImageIcon("tile_p.jpg");
		img[1] = new ImageIcon("tile_i.jpg");
		img[2] = new ImageIcon("tile_a.jpg");
		img[3] = new ImageIcon("bandera_p.jpg");
		img[4] = new ImageIcon("bandera_i.jpg");
		img[5] = new ImageIcon("bandera_a.jpg");
		img[6] = new ImageIcon("Mina_p.png");
		img[7] = new ImageIcon("Mina_i.png");
		op = new JOptionPane();
		ganar = (a * a) - b;
		grid.setLayout(new GridLayout(a, a));
		grid.setBackground(Color.BLACK);
		grid.setPreferredSize(new Dimension(495, 495));
		botones = new Boton[a][a];
		for (byte i = 0; i < a; i++) {
			for (byte j = 0; j < a; j++) {
				if (bombas == b) {
					switch (a) {
					case 9:
						botones[i][j] = new Boton(false, a, img[0]);
						break;
					case 12:
						botones[i][j] = new Boton(false, a, img[1]);
						break;
					case 15:
						botones[i][j] = new Boton(false, a, img[1]);
						botones[i][j].setFont(prin);
						break;
					}
					botones[i][j].setPreferredSize(new Dimension(d, d));
					botones[i][j].addMouseListener(this);
					grid.add(botones[i][j]);
				} else {
					switch (a) {
					case 9:
						botones[i][j] = new Boton(this.randomBmb(a), a, img[0]);
						break;
					case 12:
						botones[i][j] = new Boton(this.randomBmb(a), a, img[1]);
						break;
					case 15:
						botones[i][j] = new Boton(this.randomBmb(a), a, img[2]);
						grid.setPreferredSize(new Dimension(615, 615));
						break;
					}
					botones[i][j].setPreferredSize(new Dimension(d, d));
					botones[i][j].addMouseListener(this);
					if (botones[i][j].bomba) {
						bombas++;
						//botones[i][j].setText("b");
					}
				}
			}
		}
		while (bombas < b) {
			for (byte i = 0; i < a; i++) {
				for (byte j = 0; j < a; j++) {
					if (botones[i][j].bomba) {
						continue;
					}
					if (botones[i][j].bomba == false) {
						switch (a) {
						case 9:
							botones[i][j] = new Boton(this.randomBmb(a), a, img[0]);
							botones[i][j].setPreferredSize(new Dimension(d, d));
							botones[i][j].addMouseListener(this);
							break;
						case 12:
							botones[i][j] = new Boton(this.randomBmb(a), a, img[1]);
							botones[i][j].setPreferredSize(new Dimension(d, d));
							botones[i][j].addMouseListener(this);
							break;
						case 15:
							botones[i][j] = new Boton(this.randomBmb(a), a, img[2]);
							botones[i][j].setPreferredSize(new Dimension(d, d));
							botones[i][j].addMouseListener(this);
							break;
						}
					}
					if (botones[i][j].bomba) {
						bombas++;
						//botones[i][j].setText("b");
					}
					if (i + 1 == a && bombas < b) {
						i = 0;
					} else if (bombas >= b) {
						break;
					}

				}
				if (bombas >= b) {
					break;
				}
			}
		}
		for (byte i = 0; i < a; i++) {
			for (byte j = 0; j < a; j++) {
				grid.add(botones[i][j]);
			}
		}
		panel.setLayout(new BorderLayout());
		panel.add(nombre, BorderLayout.NORTH);
		panel.add(cronometro, BorderLayout.SOUTH);
		panel.add(grid, BorderLayout.CENTER);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		panel.setBackground(Color.BLACK);
		this.add(panel);
		this.pack();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Checa el input del boton y si se toca una bomba, abre un menu en donde se
		// cierra el juego o se vuelve al menu principal.
		if (tiempo != true) {
			sw.start();
			tiempo = true;
		}
		if (e.getButton() == MouseEvent.BUTTON1) {
			for (byte i = 0; i < botones.length; i++) {
				for (byte j = 0; j < botones[i].length; j++) {
					if (e.getSource() == botones[i][j]) {
						if (botones[i][j].bomba) {
							sw.suspend();
							switch (botones.length) {
							case 9:
								botones[i][j].setIcon(img[6]);
								botones[i][j].setBackground(Color.RED);
								break;
							case 12:
								botones[i][j].setIcon(img[7]);
								botones[i][j].setBackground(Color.RED);
								break;
							case 15:
								botones[i][j].setIcon(img[7]);
								botones[i][j].setBackground(Color.RED);
								break;
							}
							for (int x = 0; x < botones.length; x++) {
								for (int y = 0; y < botones.length; y++) {
									if (botones[x][y].bomba) {
										switch (botones.length) {
										case 9:
											botones[x][y].setIcon(img[6]);
											break;
										case 12:
											botones[x][y].setIcon(img[7]);
											break;
										case 15:
											botones[x][y].setIcon(img[7]);
											break;
										}
									}
								}
							}
							if (JOptionPane.showOptionDialog(this, "Has perdido \n Quieres reintentarlo?", "Game Over",
									JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,
									opciones[1]) == 0) {
								this.setVisible(false);
								new Menu();
								sw.stop();
							} else {
								System.exit(0);
							}
						} else {
							// Pone el numero y el color en el boton dependiendo del numero de bombas que
							// tiene cerca.
							this.control(i, j);
							switch (this.loop(i, j)) {
							case 1:
								botones[i][j].setSelected(true);
								botones[i][j].setFocusable(false);
								botones[i][j].setIcon(null);
								botones[i][j].setText("1");
								botones[i][j].setForeground(Color.BLUE);
								break;
							case 2:
								botones[i][j].setSelected(true);
								botones[i][j].setFocusable(false);
								botones[i][j].setIcon(null);
								botones[i][j].setText("2");
								botones[i][j].setForeground(Color.getHSBColor((float) .34, (float) .77, (float) .42));
								break;
							case 3:
								botones[i][j].setSelected(true);
								botones[i][j].setFocusable(false);
								botones[i][j].setIcon(null);
								botones[i][j].setText("3");
								botones[i][j].addMouseListener(null);
								botones[i][j].setForeground(Color.getHSBColor((float) .06, (float) .77, (float) .42));
								break;
							case 4:
								botones[i][j].setSelected(true);
								botones[i][j].setFocusable(false);
								botones[i][j].setIcon(null);
								botones[i][j].setText("4");
								botones[i][j].setForeground(Color.getHSBColor((float) .34, (float) .93, (float) .96));
								break;
							case 5:
								botones[i][j].setSelected(true);
								botones[i][j].setFocusable(false);
								botones[i][j].setIcon(null);
								botones[i][j].setText("5");
								botones[i][j].setForeground(Color.getHSBColor((float) .34, (float) .93, (float) .96));
								break;
							case 6:
								botones[i][j].setSelected(true);
								botones[i][j].setFocusable(false);
								botones[i][j].setIcon(null);
								botones[i][j].setText("6");
								botones[i][j].setForeground(Color.getHSBColor((float) .569, (float) .55, (float) .80));
								break;
							case 7:
								botones[i][j].setSelected(true);
								botones[i][j].setFocusable(false);
								botones[i][j].setIcon(null);
								botones[i][j].setText("7");
								botones[i][j].setForeground(Color.getHSBColor((float) .8, (float) .71, (float) .55));
								break;
							case 8:
								botones[i][j].setSelected(true);
								botones[i][j].setFocusable(false);
								botones[i][j].setIcon(null);
								botones[i][j].setText("8");
								botones[i][j].setForeground(Color.getHSBColor((float) .34, (float) .93, (float) .96));
								break;
							default:
								botones[i][j].setSelected(true);
								botones[i][j].setFocusable(false);
								botones[i][j].setIcon(null);
								this.checkBtn(i, j);
							}
							this.victoria();
						}
					}
				}
			}
			// Esta parte obtiene el input del click derecho para poner una bandera, y si ya
			// tiene bandera, se la quita.
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			for (int i = 0; i < botones.length; i++) {
				for (int j = 0; j < botones[i].length; j++) {
					if (e.getSource() == botones[i][j]) {
						switch (botones.length) {
						case 9:
							if (botones[i][j].getIcon() == img[0]) {
								botones[i][j].setIcon(img[3]);
							} else {
								botones[i][j].setIcon(img[0]);
							}
							break;
						case 12:
							if (botones[i][j].getIcon() == img[1]) {
								botones[i][j].setIcon(img[4]);
							} else {
								botones[i][j].setIcon(img[1]);
							}
							break;
						case 15:
							if (botones[i][j].getIcon() == img[1]) {
								botones[i][j].setIcon(img[5]);
							} else {
								botones[i][j].setIcon(img[1]);
							}
							break;
						}
					}
				}
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	// Este metodo genera las bombas con una probabilidad menor para distribuir
	// mejor las mismas.
	private boolean randomBmb(int b) {
		boolean a = false;
		switch (b) {
		case 9:
			if (r.nextInt(100) <= 5) {
				a = true;
			} else {
				a = false;
			}
			break;
		case 12:
			if (r.nextInt(100) <= 5) {
				a = true;
			} else {
				a = false;
			}
			break;
		case 15:
			if (r.nextInt(100) <= 5) {
				a = true;
			} else {
				a = false;
			}
			break;
		}
		return a;
	}

	// Este metodo busca las casillas que no tienen minas cerca y las activa.
	private void checkBtn(int a, int b) {
		for (int i = a; i < botones.length; i++) {
			for (int j = b; j < botones[j].length;) {
				if (i != 0 && botones[i - 1][j].isFocusable() == true && this.loop(i - 1, j) == 0) {
					this.control(i - 1, j);
					botones[i - 1][j].setFocusable(false);
					botones[i - 1][j].setSelected(true);
					botones[i - 1][j].addMouseListener(null);
					botones[i - 1][j].setFocusable(false);
					botones[i - 1][j].setIcon(null);
					this.victoria();
					this.checkBtn(i - 1, j);
				} else if (j != 0 && botones[i][j - 1].isFocusable() == true && this.loop(i, j - 1) == 0) {

					this.control(i, j - 1);
					botones[i][j - 1].setFocusable(false);
					botones[i][j - 1].setSelected(true);
					botones[i][j - 1].addMouseListener(null);
					botones[i][j - 1].setFocusable(false);
					botones[i][j - 1].setIcon(null);
					this.victoria();
					this.checkBtn(i, j - 1);
				} else if (i != botones.length - 1 && botones[i + 1][j].isFocusable() == true
						&& this.loop(i + 1, j) == 0) {
					this.control(i + 1, j);
					botones[i + 1][j].setFocusable(false);
					botones[i + 1][j].setSelected(true);
					botones[i + 1][j].addMouseListener(null);
					botones[i + 1][j].setFocusable(false);
					botones[i + 1][j].setIcon(null);
					this.victoria();
					this.checkBtn(i + 1, j);
				} else if (j != botones.length - 1 && botones[i][j + 1].isFocusable() == true
						&& this.loop(i, j + 1) == 0) {
					this.control(i, j + 1);
					botones[i][j + 1].setFocusable(false);
					botones[i][j + 1].setSelected(true);
					botones[i][j + 1].addMouseListener(null);
					botones[i][j + 1].setFocusable(false);
					botones[i][j + 1].setIcon(null);
					this.victoria();
					this.checkBtn(i, j + 1);

				} else {
					i = botones.length;
					break;
				}
				break;
			}
		}
	}

	// Este metodo checa las minas cerca de la casilla seleccionada.
	private int loop(int i, int j) {
		int bmb = 0;
		for (int k = i - 1; k < i + 2; k++) {
			for (int l = j - 1; l < j + 2; l++) {
				if (k < 0 || l < 0 || k == botones.length || l == botones.length) {

				} else if (botones[k][l].bomba) {
					bmb++;
				}
			}
		}
		return bmb;
	}

	private void victoria() {
		if (cont == ganar) {
			if (JOptionPane.showOptionDialog(this, "Has ganado! \n Quieres reintentarlo?", "Victoria",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]) == 0) {
				this.setVisible(false);
				new Menu();
			} else {
				System.exit(0);
			}
		}
		System.out.println(cont);
	}

	private void control(int i, int j) {
		if (botones[i][j].isFocusable()) {
			cont++;
		}
	}

	@Override
	public void run() {

		while (true) {
			System.out.println("bug");
			if (tiempo) {
				cronometro.setText(String.valueOf((double)sw.getTime(TimeUnit.MILLISECONDS)/ 1000));
			}
			try {
				Thread.sleep((long) 1.0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
