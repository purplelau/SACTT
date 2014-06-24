package Pantallas;
/**FrameModificarLema: interfaz swing encargada de modificar el nombre de un lema
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
import Lema.lema;

import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.*;

import java.awt.Font;
import java.awt.Toolkit;

public class FrameModificarLema extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private JComboBox comboModificarLema;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;
	@SuppressWarnings({ "rawtypes", "unused" })
	private JComboBox comboBox;
	private JLabel lblSeleccioneUnLema;
	private JTextField nuevoLema;
	private JLabel lblNewLabel;



	/**
	 * Crea el frame
	 * @throws SQLException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FrameModificarLema(Connection conn) throws SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		setTitle("Modificar lema");
		_conn=conn;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 514, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(this.action);

		btnAceptar.setBounds(199, 177, 131, 23);
		contentPane.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(368, 177, 119, 23);
		contentPane.add(btnCancelar);



		comboModificarLema = new JComboBox();
		comboModificarLema.setMaximumRowCount(5);
		comboModificarLema.setEditable(true);
		comboModificarLema.setBounds(199, 31, 288, 20);
		lema lemaAux = new lema();
		ArrayList<idPalabra> lemas = lemaAux.devolverLemas(_conn);
		comboModificarLema.addItem("");
		for(int i=0; i<lemas.size(); i++){
			comboModificarLema.addItem(lemas.get(i).getPalabra());
		}
		contentPane.add(comboModificarLema);
		AutoCompleteDecorator.decorate(comboModificarLema);

		lblSeleccioneUnLema = new JLabel("Seleccione un lema:");
		lblSeleccioneUnLema.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccioneUnLema.setBounds(10, 32, 141, 17);
		contentPane.add(lblSeleccioneUnLema);
		
		nuevoLema = new JTextField();
		nuevoLema.setBounds(199, 84, 288, 20);
		contentPane.add(nuevoLema);
		nuevoLema.setColumns(10);
		
		lblNewLabel = new JLabel("Introduzca el nuevo nombre:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 87, 179, 14);
		contentPane.add(lblNewLabel);

	}

	private class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		
		/**
		 * Evento que se encarga de modificar el nombre del lema existente elegido por el usuario
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		public void actionPerformed(ActionEvent e) {
			boolean lemaModificado = false;
			@SuppressWarnings("unused")
			int idLema=0;

			if (e.getSource()==btnAceptar) {

				if(comboModificarLema.getSelectedItem().toString().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No se ha introducido lema","ERROR", 2);
				else{
					try {
						lema miLema =new lema(comboModificarLema.getSelectedItem().toString());
						idLema= miLema.consultarUnLema(_conn);

						if (!nuevoLema.getText().equalsIgnoreCase("")){
							lema miNuevoLema = new lema(nuevoLema.getText());
							if (miNuevoLema.consultarUnLema(_conn)==-1){
								lemaModificado = miLema.modificarLemaNombre(nuevoLema.getText(), _conn);	
								if (lemaModificado)
									JOptionPane.showMessageDialog(null, "El lema se ha modificado con éxito", "Modificar lema", 1);
								else
									JOptionPane.showMessageDialog(null, "El lema no se ha podido modificar", "ERROR", 0);
							}
							else
								JOptionPane.showMessageDialog(null, "Ya existe un lema con ese nombre", "ERROR", 0);
						}
						else
							JOptionPane.showMessageDialog(null, "El lema no se ha podido modificar", "ERROR", 0);

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
