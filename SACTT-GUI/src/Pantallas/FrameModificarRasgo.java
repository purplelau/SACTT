package Pantallas;
/**FrameModificarRasgo: interfaz swing encargada de modificar el nombre de un rasgo de contenido
 * @author: Laura Asenjo, María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
* @see:JFrame,JPanel,EmptyBorder,AbstractAction,JLabel,JOptionPane,JButton,ActionEvent,Connection,SQLException,ArrayList,Action,area,conexion,idPalabra,categoria,categoriasLemas,lemaRasgo,lema,JComboBox,autocomplete*,Font,IOException,Toolkit
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
import java.util.ArrayList;

import javax.swing.Action;
import IdPalabra.idPalabra;
import RasgoContenido.rasgocontenido;

import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.*;

import java.awt.Font;
import java.awt.Toolkit;

public class FrameModificarRasgo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private JComboBox comboModificarRasgo;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;
	@SuppressWarnings({ "rawtypes", "unused" })
	private JComboBox comboBox;
	private JLabel lblSeleccioneUnRasgo;
	private JTextField nuevoRasgo;
	private JLabel lblNewLabel;





	/**
	 * Crea el frame
	 * @throws SQLException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FrameModificarRasgo(Connection conn) throws SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		setTitle("Modificar rasgo de contenido");
		_conn=conn;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 463, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(this.action);

		btnAceptar.setBounds(177, 177, 131, 23);
		contentPane.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(318, 177, 119, 23);
		contentPane.add(btnCancelar);



		comboModificarRasgo = new JComboBox();
		comboModificarRasgo.setMaximumRowCount(5);
		comboModificarRasgo.setEditable(true);
		comboModificarRasgo.setBounds(221, 31, 216, 20);
		rasgocontenido rasgoAux = new rasgocontenido();
		ArrayList<idPalabra> rasgos = rasgoAux.consultarTodosRasgos(_conn);
		comboModificarRasgo.addItem("");
		for(int i=0; i<rasgos.size(); i++){
			comboModificarRasgo.addItem(rasgos.get(i).getPalabra());
		}
		contentPane.add(comboModificarRasgo);
		AutoCompleteDecorator.decorate(comboModificarRasgo);

		lblSeleccioneUnRasgo = new JLabel("Seleccione un rasgo de contenido:");
		lblSeleccioneUnRasgo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccioneUnRasgo.setBounds(10, 32, 201, 17);
		contentPane.add(lblSeleccioneUnRasgo);
		
		nuevoRasgo = new JTextField();
		nuevoRasgo.setBounds(222, 80, 215, 20);
		contentPane.add(nuevoRasgo);
		nuevoRasgo.setColumns(10);
		
		lblNewLabel = new JLabel("Introduzca nuevo nombre:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 83, 160, 14);
		contentPane.add(lblNewLabel);


	}

	private class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		/**
		 * Evento que se encarga de modificar el nombre del rasgo de contenido existente elegido por el usuario
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent e) {
			boolean rasgoModificado = false;
			int idRasgo = 0;

			if (e.getSource()==btnAceptar) {

				if(comboModificarRasgo.getSelectedItem().toString().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No se ha introducido rasgo de contenido","ERROR", 2);
				else{
					try {
						rasgocontenido miRasgo = new rasgocontenido(comboModificarRasgo.getSelectedItem().toString());
						idRasgo = miRasgo.consultarUnRasgo(_conn);
						
						if (!nuevoRasgo.getText().equalsIgnoreCase("")){	
							rasgocontenido miNuevoRasgo = new rasgocontenido(nuevoRasgo.getText());
							if(miNuevoRasgo.consultarUnRasgo(_conn)==-1){
								rasgoModificado = miRasgo.modificarRasgoNombre(nuevoRasgo.getText(), _conn);
								if (rasgoModificado)
									JOptionPane.showMessageDialog(null, "El rasgo de contenido se ha modificado con éxito", "Modificar rasgo de contenido", 1);
								else
									JOptionPane.showMessageDialog(null, "El rasgo de contenido no se ha podido modificar", "ERROR", 0);
							}
							else
								JOptionPane.showMessageDialog(null, "Ya existe un rasgo de contenido con ese nombre", "ERROR", 0);
						}
						else
							JOptionPane.showMessageDialog(null, "El rasgo de contenido no se ha podido modificar", "ERROR", 0);

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

