package Pantallas;
/**FrameA�adirLema: interfaz swing encargada de a�adir un lema
 * @author: Laura Asenjo, Mar�a de Valvanera Jim�nez, Silvia Cabezas
 * @version: 1.0
 * @see:ActionEvent,Connection,swing*,EmptyBorder,lema,Toolkit
 */

import java.awt.event.ActionEvent;
import java.sql.Connection;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import Lema.lema;
import java.awt.Toolkit;

public class FrameA�adirLema extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldA�adir;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;


	/**
	 * Crea el frame
	 */
	public FrameA�adirLema(Connection conn) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		setTitle("A\u00F1adir Lema");
		_conn=conn;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 124);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		JLabel lblNewLabel = new JLabel("A\u00F1adir Lema:");
		lblNewLabel.setBounds(10, 14, 144, 17);
		contentPane.add(lblNewLabel);
		
		textFieldA�adir = new JTextField();
		textFieldA�adir.setBounds(164, 11, 260, 20);
		contentPane.add(textFieldA�adir);
		textFieldA�adir.setColumns(10);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(this.action);

		btnAceptar.setBounds(164, 52, 131, 23);
		contentPane.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(305, 52, 119, 23);
		contentPane.add(btnCancelar);
	}
	
	private class SwingAction extends AbstractAction {
	
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		/**
		 * Evento que se encarga de a�adir el nuevo lema introducido por el usuario
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==btnAceptar) {
				if(textFieldA�adir.getText().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No se ha introducido lema"); 
				else{
			         lema milema =new lema(textFieldA�adir.getText());
						if(milema.a�adirLema(_conn))
							JOptionPane.showMessageDialog(null,"Se ha a�adido el lema correctamente!");
						else
							JOptionPane.showMessageDialog(null,"Ya existe el lema");
							
		          dispose();
				}
	        }
			else if (e.getSource()==btnCancelar) {
	            dispose();
	        }
		}
	}
}