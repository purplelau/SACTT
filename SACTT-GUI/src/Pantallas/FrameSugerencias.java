package Pantallas;
/**FrameSugerencias: interfaz swing encargada de enviar/añadir sugerencias por parte de los usuarios
 * @author: Laura Asenjo, María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
* @see:JFrame,JPanel,EmptyBorder,AbstractAction,JLabel,JOptionPane,JButton,ActionEvent,Connection,SQLException,Action,javamail,sugerencias,conexion,Font,JEditorPane,Toolkit
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;


import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.Action;

import OtrasFunciones.javamail;
import OtrasFunciones.sugerencias;

import java.awt.Font;
import javax.swing.JEditorPane;
import java.awt.Toolkit;

public class FrameSugerencias extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JButton btnAceptar;
	JButton btnCancelar;
	private JEditorPane sugPanel;
	public  Action action = new SwingAction();
	static Connection _conn;
	private JTextField campoNombre;

	/**
	 * Crea el frame
	 */
	public FrameSugerencias(Connection conn) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		setTitle("Enviar sugerencia");
		_conn=conn;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 454, 249);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		btnAceptar = new JButton("Enviar");
		btnAceptar.addActionListener(this.action);

		btnAceptar.setBounds(164, 176, 131, 23);
		contentPane.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(305, 176, 119, 23);
		contentPane.add(btnCancelar);

		JLabel lblNewLabel = new JLabel("Sugerencia:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(30, 60, 98, 22);
		contentPane.add(lblNewLabel);

		sugPanel = new JEditorPane();
		sugPanel.setToolTipText("");
		sugPanel.setBounds(30, 93, 394, 72);
		contentPane.add(sugPanel);

		JLabel lblIntroduzcaSuNombre = new JLabel("Introduzca su nombre:");
		lblIntroduzcaSuNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblIntroduzcaSuNombre.setBounds(30, 27, 139, 22);
		contentPane.add(lblIntroduzcaSuNombre);

		campoNombre = new JTextField();
		campoNombre.setBounds(179, 29, 245, 20);
		contentPane.add(campoNombre);
		campoNombre.setColumns(10);
	}

	private class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		/**
		 * Evento que se encarga de enviar por email y añadir a la base de datos la sugerencia rellenada por el usuario
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		public void actionPerformed(ActionEvent e) {

			boolean sugIntroducida = false;	
			boolean emailEnviado = false;

			javamail email = new javamail();
			

			if (e.getSource()==btnAceptar) {

				if((sugPanel.getText().equalsIgnoreCase(""))||(campoNombre.getText().equalsIgnoreCase(""))){
					JOptionPane.showMessageDialog(null,"No se han completado todos los campos","AVISO", 2);
					campoNombre.setText("");
					sugPanel.setText("");
				}
				else{
					try {
						String nombre = campoNombre.getText();
						sugerencias texto = new sugerencias(sugPanel.getText());
						sugIntroducida = texto.añadirSugerencia(_conn, nombre);
						emailEnviado = email.sendMessage(texto.getSugerencia(), "Sugerencia SACTT de: "+nombre, "sactttfg@gmail.com");	
						
						if (emailEnviado)
							JOptionPane.showMessageDialog(null, "La sugerencia se ha enviado correctamente", "Enviar sugerencia", 1);
						else
							JOptionPane.showMessageDialog(null, "La sugerencia no se ha podido enviar", "ERROR", 0);

						if (sugIntroducida)
							JOptionPane.showMessageDialog(null, "La sugerencia se ha introducido correctamente en la base de datos", "Añadir sugerencia", 1);
						else{
							JOptionPane.showMessageDialog(null, "La sugerencia no se ha podido añadir a la base de datos", "ERROR", 0);
							
						}

					} catch (SQLException e1) {
						
						e1.printStackTrace();
					} 
					dispose();
				}
			}
			else if (e.getSource()==btnCancelar) {
				dispose();
			}
		}
	}
}
