package Pantallas;
/**FrameAñadirCategoria: interfaz swing encargada de añadir una categoría
 * @author: Laura Asenjo, María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
* @see:JFrame,JPanel,EmptyBorder,AbstractAction,JLabel,JOptionPane,JTextField,JButton,ActionEvent,Connection,SQLException,Action,area,conexion,Toolkit,autocomplete*
 */

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import org.jdesktop.swingx.autocomplete.*;
import Area.area;
import Categoria.categoria;
import IdPalabra.idPalabra;
import java.awt.Toolkit;


public class FrameAñadirCategoria extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldCategoria;
	JComboBox<String> comboBoxAreas ;
	JButton btnAceptar, btnCancelar;
	public  SwingAction action = new SwingAction();
	static Connection _conn;

	/**
	 * Crea el frame
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FrameAñadirCategoria(Connection conn) {
		//setIconImage(Toolkit.getDefaultToolkit().getImage("logo1.png"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		_conn=conn;
		setTitle("A\u00F1adir Categor\u00EDa");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 203);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		JLabel lblNewLabel = new JLabel("A\u00F1adir Categor\u00EDa:");
		lblNewLabel.setBounds(21, 36, 134, 14);
		contentPane.add(lblNewLabel);
		
		textFieldCategoria = new JTextField();
		
		textFieldCategoria.setBounds(133, 33, 279, 20);
		textFieldCategoria.addActionListener(this.action);
		contentPane.add(textFieldCategoria);
		textFieldCategoria.setColumns(10);
		
		JLabel lblAsociarrea = new JLabel("Asociar \u00C1rea:");
		lblAsociarrea.setBounds(21, 78, 93, 14);
		contentPane.add(lblAsociarrea);
		
		comboBoxAreas = new JComboBox();
		comboBoxAreas.setBounds(133, 75, 279, 20);
		comboBoxAreas.setEditable(true);
		contentPane.add(comboBoxAreas);
		AutoCompleteDecorator.decorate(comboBoxAreas);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(this.action);
		btnAceptar.setBounds(133, 129, 123, 23);
		contentPane.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(289, 129, 123, 23);
		contentPane.add(btnCancelar);
		cargarComboAreas();
	}
		
	 	 
    	 
	/**
	 * Función que carga el combobox de áreas
	 * @param no recibe nada
	 * @return no devuelve nada
	 */	 
    public void cargarComboAreas(){

        	 ArrayList<idPalabra>misAreas=new ArrayList<idPalabra>();
        	 misAreas=area.devolverAreas(_conn);
        	 comboBoxAreas.addItem("");
        	 for(int i=0; i<misAreas.size(); i++){
        		 comboBoxAreas.addItem(misAreas.get(i).getPalabra());
        	 }	

    }
	
	
	private class SwingAction extends AbstractAction {
		
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		/**
		 * Evento que se encarga de añadir la nueva categoría introducida por el usuario
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		public void actionPerformed(ActionEvent e) {
			categoria micategoria;
			if(e.getSource()==textFieldCategoria){
				System.out.println("pasa");
				comboBoxAreas.setEnabled(true);
			}
			else if (e.getSource()==btnAceptar) {
				if(textFieldCategoria.getText().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No se ha introducido Categoría."); 
				else{
			        micategoria =new categoria(textFieldCategoria.getText());
			         
			         if(!textFieldCategoria.getText().equalsIgnoreCase("")){ 	       
							try {
								 micategoria =new categoria(textFieldCategoria.getText());
								if(micategoria.añadirCategoria(_conn,comboBoxAreas.getSelectedItem().toString()))
									JOptionPane.showMessageDialog(null,"Se ha añadido la categoria con su area"); 
								else
									JOptionPane.showMessageDialog(null,"No se ha podido añadir esa categoría"); 
							} 
							catch (SQLException e1) {	
							}
									
						dispose();

			         }
				}
	        }
			else if (e.getSource()==btnCancelar) {
				setVisible(false);
	        }
		}
	}
	
}
