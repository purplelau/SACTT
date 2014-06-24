package Pantallas;
/**FrameModificarRelacionAreaCategoria: interfaz swing encargada de modificar la relación entre un área y una categoría
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
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.Action;
import Area.area;
import IdPalabra.idPalabra;
import Categoria.categoria;

import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.*;

import java.awt.Font;
import java.awt.Toolkit;

public class FrameModificarRelacionAreaCategoria extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private JComboBox comboModificarArea;
	@SuppressWarnings("rawtypes")
	private JComboBox comboModificarCategoria;
	@SuppressWarnings("rawtypes")
	private JComboBox comboNuevaArea;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;



	/**
	 * Crea el frame
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FrameModificarRelacionAreaCategoria(Connection conn) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		setTitle("Modificar relaci\u00F3n \u00E1rea-categor\u00EDa");
		_conn=conn;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 512, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		JLabel lblNewLabel = new JLabel("Seleccione un \u00E1rea:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 13, 141, 17);
		contentPane.add(lblNewLabel);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(this.action);

		btnAceptar.setBounds(198, 259, 131, 23);
		contentPane.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(367, 259, 119, 23);
		contentPane.add(btnCancelar);
		
		comboModificarArea = new JComboBox();
		comboModificarArea.setMaximumRowCount(5);
		comboModificarArea.setEditable(true);
		comboModificarArea.setToolTipText("");
		comboModificarArea.setBounds(10, 54, 179, 20);
		ArrayList<idPalabra> areas = area.devolverAreas(_conn);
		comboModificarArea.addItem("");
		for(int i=0; i<areas.size(); i++){
			comboModificarArea.addItem(areas.get(i).getPalabra());
		}
		contentPane.add(comboModificarArea);
		AutoCompleteDecorator.decorate(comboModificarArea);
		
		comboModificarCategoria = new JComboBox();
		comboModificarCategoria.setMaximumRowCount(5);
		comboModificarCategoria.setEnabled(false);
		comboModificarArea.setEditable(true);
		comboModificarCategoria.setBounds(307, 54, 179, 20);
		contentPane.add(comboModificarCategoria);
		AutoCompleteDecorator.decorate(comboModificarCategoria);
		
		
		/**
		 * Evento que carga el combobox de categorías cuando se elige un área
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				ArrayList<idPalabra> cats = new ArrayList<idPalabra>(); 
				categoria catAux = new categoria();
				if(comboModificarArea.getSelectedItem()!=null){
					cats = catAux.consultarCategoriasConArea(comboModificarArea.getSelectedItem().toString(), _conn);
					if ((comboModificarArea.getSelectedIndex() > 0)&&(cats.size()>0)){
						comboModificarCategoria.setEnabled(true);
						comboModificarCategoria.removeAllItems();
						comboModificarCategoria.addItem("");
						comboModificarCategoria.setEnabled(true);
						for(int i=0; i<cats.size(); i++){
							comboModificarCategoria.addItem(cats.get(i).getPalabra());
						}
					}
					else{
						comboModificarCategoria.setEnabled(false);
					}
				}
			}
		};
		
		comboModificarArea.addItemListener(itemListener);
		
		JLabel lblSeleccioneUnaCategora = new JLabel("Seleccione una categor\u00EDa:");
		lblSeleccioneUnaCategora.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccioneUnaCategora.setBounds(307, 13, 179, 17);
		contentPane.add(lblSeleccioneUnaCategora);
		
		JLabel lblSeleccioneElNuevo = new JLabel("Seleccione el nuevo \u00E1rea al que pertenece la categor\u00EDa:");
		lblSeleccioneElNuevo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccioneElNuevo.setBounds(10, 126, 335, 14);
		contentPane.add(lblSeleccioneElNuevo);
		
		comboNuevaArea = new JComboBox();
		comboNuevaArea.setEnabled(false);
		comboNuevaArea.setBounds(10, 171, 179, 20);
		contentPane.add(comboNuevaArea);
		AutoCompleteDecorator.decorate(comboNuevaArea);
		
		/**
		 * Evento que carga el combobox de nuevas áreas cuando se elige una categoría
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		ItemListener itemListener2 = new ItemListener() {
			@SuppressWarnings({ "static-access" })
			public void itemStateChanged(ItemEvent itemEvent) {
				ArrayList<idPalabra> nuevasAreas = new ArrayList<idPalabra>(); 
				area areaAux = new area();
			
				nuevasAreas = areaAux.devolverAreas(_conn);
				if ((comboModificarCategoria.getSelectedIndex() > 0)&&(nuevasAreas.size()>0)){
					comboNuevaArea.setEnabled(true);
					comboNuevaArea.removeAllItems();
					comboNuevaArea.addItem("");
					comboNuevaArea.setEnabled(true);
					for(int i=0; i<nuevasAreas.size(); i++){
						if(!nuevasAreas.get(i).getPalabra().equalsIgnoreCase(comboModificarArea.getSelectedItem().toString()))
							comboNuevaArea.addItem(nuevasAreas.get(i).getPalabra());							
					}
				}
				else{
					comboNuevaArea.setEnabled(false);
				}
			}
		};
		
		comboModificarCategoria.addItemListener(itemListener2);
	}
	
	private class SwingAction extends AbstractAction {
	
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		/**
		 * Evento que se encarga de modificar la relación entre un área y una categoría elegidas por el usuario
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent e) {
			boolean catAreaModificada = false;
			int opcion=0;
			int idArea=0;
			int idNuevaArea=0;
			int idCat=0;
			
			if (e.getSource()==btnAceptar) {
				
				if((comboModificarArea.getSelectedItem().toString().equalsIgnoreCase(""))||(comboModificarCategoria.getSelectedItem().toString().equalsIgnoreCase("")))						
					JOptionPane.showMessageDialog(null,"No se han introducido el área y la categoría","AVISO", 2);
				else{
					try {
						area miarea =new area(comboModificarArea.getSelectedItem().toString());
						idArea= miarea.consultarUnArea(_conn);

						categoria miCat = new categoria(comboModificarCategoria.getSelectedItem().toString());
						idCat = miCat.consultarUnaCategoria(_conn);
					
						if (!comboNuevaArea.getSelectedItem().toString().equalsIgnoreCase("")){
							area nuevaArea =new area(comboNuevaArea.getSelectedItem().toString());
							idNuevaArea= nuevaArea.consultarUnArea(_conn);
							if(miCat.consultarUnaCategoriaArea(_conn, nuevaArea.getArea(), miCat.getCategoria())==-1){
								catAreaModificada = miCat.modificarAreaDeCategoria(_conn,miarea,idNuevaArea);
								if (catAreaModificada){
									JOptionPane.showMessageDialog(null, "El área de la categoría escogida se ha modificado con éxito", "Modificar relación área-categoría", 1);
									dispose();
								}
								else
									JOptionPane.showMessageDialog(null, "El área de la categoría escogida no se ha podido modificar", "ERROR", 0);			
							}
							else
								JOptionPane.showMessageDialog(null, "Ya existe una categoría con ese nombre dentro de ese área", "ERROR", 0);
						}
						else
							JOptionPane.showMessageDialog(null, "La categoría no se ha podido modificar", "ERROR", 0);
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}

				}
	        }
			else if (e.getSource()==btnCancelar) {
				dispose();
	        }
		}
	}
}
