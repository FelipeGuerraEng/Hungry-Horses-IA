package hungryHorses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;



public class Vista extends JFrame {
	
	private JPanel pVarios, pButtons, pTablero, pInfo, pNumeros, pLetras, pMov, pExtra;
	private JLabel lJugador1, lScoreJugador1, lJugador2, lScoreJugador2,lPosicion;
	private JComboBox<Object> cbMovimiento;
	private JButton bIniciar, bMover;
	private Container contenedor;
	private JMenuBar barra;
	public static JMenu opciones;
	private JMenuItem salirItem, reiniciarItem;
	private JLabel[][] gridLetras;
	private JLabel[][] gridNum;
	private JLabel[][] grid;
	private int[][] tablero;
	public int fila_seleccionada;
	public int columna_seleccionada;
	private int choice;
	public Game g;
	public Minimax minimax;
	public Response respuesta;
	public Par posNegro;
	public int initgame;
	private ManejaEventos eventos;
	
	List<Par> listaPos;
	
	Par randomPos;

	public Vista(int eleccion) {

		initGUI(eleccion);

		setTitle("Hungry Horses");
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		setSize(940, 1010);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	private void initGUI(int eleccion) {
		
		g = new Game();
		initgame=0;
		minimax = new Minimax();
		tablero = new int[12][12];
		randomPos = new Par();
		posNegro = new Par();
		listaPos = new ArrayList<>();
		fila_seleccionada = 0;
		columna_seleccionada = 0;
		choice = eleccion;

		pTablero = new JPanel();
		pVarios = new JPanel();
		pButtons = new JPanel();
		pInfo = new JPanel();
		pNumeros = new JPanel();
		pLetras = new JPanel();
		pMov = new JPanel();
		pExtra = new JPanel();

		bIniciar = new JButton("Start");
		bIniciar.setFont(new Font("Tahoma", Font.BOLD, 16));
		bIniciar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		bIniciar.setEnabled(true);

		bMover = new JButton("Move");
		bMover.setFont(new Font("Tahoma", Font.BOLD, 16));
		bMover.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		bMover.setEnabled(false);

		lJugador1 = new JLabel("Human: ");
    	lJugador1.setFont(new Font("Tahoma", Font.BOLD, 16)); 
    	lJugador1.setHorizontalAlignment(SwingConstants.CENTER);
    	
    	lJugador2 = new JLabel("PC: ");
    	lJugador2.setFont(new Font("Tahoma", Font.BOLD, 16)); 
    	lJugador2.setHorizontalAlignment(SwingConstants.CENTER);

    	lScoreJugador1 = new JLabel(" 0 ");
    	lScoreJugador1.setFont(new Font("Tahoma", Font.BOLD, 16)); 
    	lScoreJugador1.setHorizontalAlignment(SwingConstants.LEFT);
		
    	lScoreJugador2 = new JLabel(" 0 ");
    	lScoreJugador2.setFont(new Font("Tahoma", Font.BOLD, 16)); 
    	lScoreJugador2.setHorizontalAlignment(SwingConstants.LEFT);

    	lPosicion = new JLabel("Position: ");
    	lPosicion.setFont(new Font("Tahoma", Font.BOLD, 16)); 
    	lPosicion.setHorizontalAlignment(SwingConstants.CENTER);

		opciones = new JMenu("Options");
		salirItem = new JMenuItem("Quit");
		reiniciarItem = new JMenuItem("Restart");

		opciones.add(reiniciarItem);
		opciones.addSeparator();
		opciones.add(salirItem);

		barra = new JMenuBar();
		setJMenuBar(barra);
		barra.add(opciones);

		cbMovimiento = new JComboBox();
		cbMovimiento.setFont(new Font("Tahoma", Font.BOLD, 16));
		cbMovimiento.setSelectedItem("");
		cbMovimiento.setEditable(false);
		((JLabel)cbMovimiento.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);


		pButtons.setLayout(new GridLayout(1, 2));
		pButtons.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		pButtons.add(bMover);
		pButtons.add(bIniciar);

		pInfo.setLayout(new GridLayout(1, 4));
		pInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		pInfo.add(lJugador1);
		pInfo.add(lScoreJugador1);
		pInfo.add(lJugador2);
		pInfo.add(lScoreJugador2);
		
		pMov.setLayout(new GridLayout(1, 3));
		pMov.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		pMov.add(lPosicion);
		pMov.add(cbMovimiento);
		
		pExtra.setLayout(new GridLayout(1, 3));
		pExtra.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		pExtra.add(pInfo);
		pExtra.add(pMov);
		pExtra.add(pButtons);

		pTablero.setLayout(new GridLayout(8, 8));
		pTablero.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

		grid = new JLabel[8][8];
		Dimension size = new Dimension(100, 100);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				grid[i][j] = new JLabel();
				grid[i][j].setBorder(new LineBorder(Color.BLACK));
				grid[i][j].setPreferredSize(size);
				grid[i][j].setOpaque(true);
				pTablero.add(grid[i][j]);
			}
		}

		pTablero.setOpaque(false);
		
		pNumeros.setLayout(new GridLayout(1, 9));

		gridNum = new JLabel[1][9];
		
		for (int i = 0; i < 1; i++) {
			for (int j = 0; j < 9; j++) {
				gridNum[i][j] = new JLabel();
				gridNum[i][j].setBorder(new LineBorder(Color.BLACK));
				gridNum[i][j].setPreferredSize(size);
				gridNum[i][j].setFont(new Font("Tahoma", Font.BOLD, 22)); 
				gridNum[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				gridNum[i][j].setOpaque(true);
				pNumeros.add(gridNum[i][j]);
			}
		}
		gridNum[0][1].setText("1");gridNum[0][2].setText("2");gridNum[0][3].setText("3");gridNum[0][4].setText("4");
		gridNum[0][5].setText("5");gridNum[0][6].setText("6");gridNum[0][7].setText("7");gridNum[0][8].setText("8");
		
		pNumeros.setOpaque(false);
		
		pLetras.setLayout(new GridLayout(8, 1));
		pLetras.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		gridLetras = new JLabel[8][1];
		//Dimension sizeLetras = new Dimension(111, 80);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 1; j++) {
				gridLetras[i][j] = new JLabel();
				gridLetras[i][j].setBorder(new LineBorder(Color.BLACK));
				gridLetras[i][j].setPreferredSize(size);
				gridLetras[i][j].setFont(new Font("Tahoma", Font.BOLD, 22)); 
				gridLetras[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				gridLetras[i][j].setOpaque(true);
				pLetras.add(gridLetras[i][j]);
			}
		}

		gridLetras[0][0].setText("A");gridLetras[1][0].setText("B");gridLetras[2][0].setText("C");gridLetras[3][0].setText("D");
		gridLetras[4][0].setText("E");gridLetras[5][0].setText("F");gridLetras[6][0].setText("G");gridLetras[7][0].setText("H");
		
		pLetras.setOpaque(false);

		pVarios.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		pVarios.setLayout(new GridLayout(2, 1));
		Dimension sizeVarios = new Dimension(900, 200);
		pVarios.setPreferredSize(sizeVarios);
		pVarios.add(pExtra);
		pVarios.add(pNumeros);
		//pLetras.setPreferredSize(new Dimension(80, 1600));

		contenedor = getContentPane();
		contenedor.setLayout(new BorderLayout());
		contenedor.add(pTablero, BorderLayout.CENTER);
		contenedor.add(pVarios, BorderLayout.NORTH);
		contenedor.add(pLetras, BorderLayout.WEST);

		eventos = new ManejaEventos();
		bIniciar.addActionListener(eventos);
		bMover.addActionListener(eventos);
		salirItem.addActionListener(eventos);
		reiniciarItem.addActionListener(eventos);
		cbMovimiento.addMouseListener(eventos);
		addWindowListener(eventos);

	}

	private void pintar() {
		for(int i = 0 ; i < 8 ; ++i) {
			for(int j = 0 ; j < 8 ; ++j) {

				grid[i][j].setIcon(new ImageIcon(getClass().getResource("../imagenes/empty.png")));

			}
		}
		for(int i = 0 ; i < 8 ; ++i) {
			for(int j = 0 ; j < 8 ; ++j) {
				if(g.getGrid()[i][j] == ElementsOfGame.CABALLO_BLANCO) {
					grid[i][j].setIcon(new ImageIcon(getClass().getResource("../imagenes/Caballo_Blanco.png")));
				}
			}
		}

		for(int i = 0 ; i < 8 ; ++i) {
			for(int j = 0 ; j < 8 ; ++j) {
				if(g.getGrid()[i][j] == ElementsOfGame.CABALLO_NEGRO) {
					grid[i][j].setIcon(new ImageIcon(getClass().getResource("../imagenes/Caballo_negro.png")));
					posNegro.setI(i);
					posNegro.setJ(j);
				}
			}
		}

		for(int i = 0 ; i < 8 ; ++i) {
			for(int j = 0 ; j < 8 ; ++j) {
				if(g.getGrid()[i][j] == ElementsOfGame.CESPED) {
					grid[i][j].setIcon(new ImageIcon(getClass().getResource("../imagenes/grass.png")));
				}
			}
		}

		for(int i = 0 ; i < 8 ; ++i) {
			for(int j = 0 ; j < 8 ; ++j) {
				if(g.getGrid()[i][j] == ElementsOfGame.FLORES) {
					grid[i][j].setIcon(new ImageIcon(getClass().getResource("../imagenes/sunflower.png")));
				}
			}
		}

		for(int i = 0 ; i < 8 ; ++i) {
			for(int j = 0 ; j < 8 ; ++j) {
				if(g.getGrid()[i][j] == ElementsOfGame.MANZANAS) {
					grid[i][j].setIcon(new ImageIcon(getClass().getResource("../imagenes/apple.png")));
				}
			}
		}

		lScoreJugador1.setText(""+g.getPlayers()[1]);
		lScoreJugador2.setText(""+g.getPlayers()[0]);

		int auxConteoPuntos = 0;

		for(int i = 0 ; i < 8 ; ++i) {
			for(int j = 0 ; j < 8 ; ++j) {
				if(g.getGrid()[i][j] == ElementsOfGame.MANZANAS) {

					auxConteoPuntos += 5;
				}
				if(g.getGrid()[i][j] == ElementsOfGame.FLORES) {
					auxConteoPuntos += 3;
				}
				if(g.getGrid()[i][j] == ElementsOfGame.CESPED) {
					auxConteoPuntos += 1;
				}
			}
		}

		if ((auxConteoPuntos + g.getPlayers()[1]) < g.getPlayers()[0]) {
			
			JOptionPane.showMessageDialog(null,"YOU LOSE!", "Game Over",JOptionPane.INFORMATION_MESSAGE);
			initgame = 2;
			
				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {

						Dificultad myView =	new Dificultad();

					}
				});

				dispose();

		} else if ((auxConteoPuntos + g.getPlayers()[0]) < g.getPlayers()[1]) {

			JOptionPane.showMessageDialog(null,"YOU WIN!", "Game Over",JOptionPane.INFORMATION_MESSAGE);
			initgame = 2;

				EventQueue.invokeLater(new Runnable() {
					@Override
					public void run() {

						Dificultad myView =	new Dificultad();

					}
				});

				dispose();
		}

	}
	
	
	public class ManejaEventos extends WindowAdapter implements ActionListener, MouseListener {

		@Override
		public void actionPerformed(ActionEvent event) {

			if (event.getSource() == bMover) {
				String s = (String) cbMovimiento.getSelectedItem();
				System.out.println(s);
				System.out.print("\n Row: " + fila_seleccionada + " Col: "+ columna_seleccionada);
				
				
				Par nuevaPosNegro;
				Game[] games = g.possiblePlays(1);
				for(int i = 0 ; i < 8 ; i++) {
					if(games[i] != null) {
						nuevaPosNegro = new Par(posNegro.getI() +Game.getPlaysI()[i], posNegro.getJ() +Game.getPlaysJ()[i]);
						char c = (char) ('A'+nuevaPosNegro.getI());
						String option = c+"" + (nuevaPosNegro.getJ()+1);
						if(option.equals(s)) {
							g = games[i];
							break;
						}
						
					}
				}
				
				
				 pintar();
				
				 g = minimax.minimaxRunning(g, choice).getState();
				 
				 if (initgame == 1) {
					 
					 delayMove();
					 
				 }
					
				

			}

			if (event.getSource() == bIniciar) {
				
				bMover.setEnabled(true);
				bIniciar.setEnabled(false);
				removeArrow(cbMovimiento);
				initgame = 1;
				
				g.initGame();

				pintar();
				
				JOptionPane.showMessageDialog(null,"White horse moves first!", "Start",JOptionPane.INFORMATION_MESSAGE);
				
				g = minimax.minimaxRunning(g, choice).getState();
				
					 delayMove();
			}
			
			

			if (event.getSource() == salirItem) {

				int respuestaBox = JOptionPane.showConfirmDialog(null,
						"Quit?",
						"Do you want to quit Hungry Horses?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (respuestaBox == JOptionPane.YES_OPTION) {

					System.exit(0);
				}

			}

			if (event.getSource() == reiniciarItem) {

				int respuestaCaja = JOptionPane.showConfirmDialog(null,
						"Do you want to restart the game?",
						"Restart",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);

				if (respuestaCaja == JOptionPane.YES_OPTION) {

					EventQueue.invokeLater(new Runnable() {
						@Override
						public void run() {

							Dificultad myView =	new Dificultad();

						}
					});

					dispose();
				}

			}

		}

		public void windowClosing(WindowEvent e) {

			int respuestaBox = JOptionPane.showConfirmDialog(null,
					"Do you want to quit Hungry Horses?",
					"Quit?",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);

			if (respuestaBox == JOptionPane.YES_OPTION) {

				System.exit(0);
			}

		}
		
		
		@Override
		public void mouseClicked(MouseEvent eventoMouse) {
			
			
		}

		@Override
		public void mouseEntered(MouseEvent eventoMouse) {
			
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent eventoMouse) {
			if (eventoMouse.getSource() == cbMovimiento) {

				if (initgame != 0) {

					cbMovimiento.removeAllItems();
					Par nuevaPosNegro;
					Game[] games = g.possiblePlays(1);
					System.out.println(posNegro.getI());
					System.out.println(posNegro.getJ());
					for(int i = 0 ; i < 8 ; i++) {
						if(games[i] != null) {
							nuevaPosNegro = new Par(posNegro.getI() +Game.getPlaysI()[i], posNegro.getJ() +Game.getPlaysJ()[i]);
							char c = (char) ('A'+nuevaPosNegro.getI());
							cbMovimiento.addItem( c+"" + (nuevaPosNegro.getJ()+1));

						}
					}

				}

			}
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		
	}
	
	public void delayMove() {

    	Timer temporizador = new Timer();

    	TimerTask tarea = new TimerTask() {
    		@Override
    		public void run() {
    			
    			pintar();

				temporizador.cancel();
    			temporizador.purge();
    		}
    	};
    	temporizador.schedule(tarea, 2000);
    }
	
	private static void removeArrow(Container container) {
	      Component[] c = container.getComponents();
	      for (Component res : c) {
	         if (res instanceof AbstractButton) {
	            container.remove(res);
	         }
	      }
	   }
    


}
