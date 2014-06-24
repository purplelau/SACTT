package Pantallas;
/**FrameModificarArea: interfaz swing encargada de modificar el nombre de un área
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
import Area.area;
import IdPalabra.idPalabra;

import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.*;

import java.awt.Font;
import java.awt.Toolkit;

public class FrameModificarArea extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private JComboBox comboModificar;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;
	private JTextField nuevoArea;


	/**
	 * Crea el frame
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FrameModificarArea(Connection conn) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		setTitle("Modificar \u00E1rea");
		_conn=conn;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 454, 249);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		JLabel lblNewLabel = new JLabel("Seleccione un \u00E1rea:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 13, 125, 17);
		contentPane.add(lblNewLabel);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(this.action);

		btnAceptar.setBounds(164, 176, 131, 23);
		contentPane.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(305, 176, 119, 23);
		contentPane.add(btnCancelar);

		comboModificar = new JComboBox();
		comboModificar.setMaximumRowCount(5);
		comboModificar.setEditable(true);
		comboModificar.setToolTipText("");
		comboModificar.setBounds(164, 12, 260, 20);
		ArrayList<idPalabra> areas = area.devolverAreas(_conn);
		comboModificar.addItem("");
		for(int i=0; i<areas.size(); i++){
			comboModificar.addItem(areas.get(i).getPalabra());
		}
		contentPane.add(comboModificar);
		AutoCompleteDecorator.decorate(comboModificar);
		
		nuevoArea = new JTextField();
		nuevoArea.setBounds(164, 67, 260, 20);
		contentPane.add(nuevoArea);
		nuevoArea.setColumns(10);
		
		JLabel lblIntroduzcaNuevoNombre = new JLabel("Introduzca nuevo nombre:");
		lblIntroduzcaNuevoNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblIntroduzcaNuevoNombre.setBounds(10, 70, 156, 14);
		contentPane.add(lblIntroduzcaNuevoNombre);
	}

	private class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		/**
		 * Evento que se encarga de modificar el nombre del área existente elegida por el usuario
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		public void actionPerformed(ActionEvent e) {

			boolean areaModificada = false;
			String nuevoNombre="";


			if (e.getSource()==btnAceptar) {

				if(comboModificar.getSelectedItem().toString().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No se ha introducido area","ERROR", 2);
				else{
					try {
						area miarea =new area(comboModificar.getSelectedItem().toString());
						if(nuevoArea.getText().equalsIgnoreCase(""))
							JOptionPane.showMessageDialog(null,"No se ha introducido el nuevo nombre del área","ERROR", 2);
						else{
							nuevoNombre = nuevoArea.getText();
							area miNuevaArea =new area(nuevoNombre);
							if (miNuevaArea.consultarUnAreaNombre(_conn)==-1){
								areaModificada = miarea.modificarAreaNombre(nuevoNombre,_conn);
								if (areaModificada)
									JOptionPane.showMessageDialog(null, "El area se ha modificado correctamente", "Modificar área", 1);
								else
									JOptionPane.showMessageDialog(null, "El area no se ha podido modificar", "ERROR", 0);
							}
							else
								JOptionPane.showMessageDialog(null, "Ya existe un área con ese nombre", "ERROR", 0);
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
