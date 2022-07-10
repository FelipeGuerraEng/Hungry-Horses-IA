package hungryHorses;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class Dificultad extends JFrame {
	
	private Container contenedor;
	private JPanel pDif;
	private JLabel lDificultad;
	private JButton btnOK;
	private JComboBox<String> cbElige;
	private int eleccion;

	
	public Dificultad(){

		initGUI(); 

		pack();
		setTitle("Hungry Horses");
		//setSize(300, 350);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

	}

	private void initGUI() {
		
		pDif =  new JPanel();
		eleccion = 0;
		
		lDificultad = new JLabel("   Choose a difficulty level before starting   ");
		lDificultad.setFont(new Font("Tahoma", Font.BOLD, 26)); 
		lDificultad.setHorizontalAlignment(SwingConstants.CENTER);
		
		btnOK = new JButton("OK");
		btnOK.setFont(new Font("Tahoma", Font.PLAIN, 22)); 
		btnOK.setOpaque(false);
		btnOK.setContentAreaFilled(false);

		cbElige = new JComboBox();
		cbElige.setFont(new Font("Tahoma", Font.BOLD, 22));
		cbElige.setSelectedItem("");
		cbElige.setEditable(false);
		((JLabel)cbElige.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
		cbElige.addItem("");
		cbElige.addItem("Beginner");
		cbElige.addItem("Amateur");
		cbElige.addItem("Expert"); 
		
		pDif.setLayout(new GridLayout(3,1));
		pDif.setBorder(BorderFactory.createLineBorder(Color.BLACK,3));
		pDif.add(lDificultad);
		pDif.add(cbElige);
		pDif.add(btnOK);


		contenedor = getContentPane();
        contenedor.setLayout(new GridLayout(1, 1));
        contenedor.add(pDif);
        
        ManejaEventos eventos = new ManejaEventos();
        btnOK.addActionListener(eventos);
        addWindowListener(eventos);
		
	}
	
	
	class ManejaEventos extends WindowAdapter implements  ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			
			if (event.getSource() == btnOK) {
				
				Object item = cbElige.getSelectedItem();

				if (item.toString().equalsIgnoreCase("Beginner")) {

					eleccion = 2;

				} else if (item.toString().equalsIgnoreCase("Amateur")) {

					eleccion = 4;


				} else if (item.toString().equalsIgnoreCase("Expert")) {

					eleccion = 6;

				}else{
	                JOptionPane.showMessageDialog(null, "Choose an option", "Difficulty", JOptionPane.INFORMATION_MESSAGE);
	            }
				
				
				if (eleccion != 0) {
				
				Vista myView =	new Vista(eleccion);
				myView.setVisible(true);
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
		
	}

}
