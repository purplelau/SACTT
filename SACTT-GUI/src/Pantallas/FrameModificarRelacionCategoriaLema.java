package Pantallas;
/**FrameModificarRelacionCategoriaLema: interfaz swing encargada de modificar la relación entre una categoría y un lema
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
import categoriasLemas.categoriasLemas;
import Lema.lema;

import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.*;

import java.awt.Font;
import java.awt.Toolkit;

public class FrameModificarRelacionCategoriaLema extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private JComboBox comboModificarArea;
	@SuppressWarnings("rawtypes")
	private JComboBox comboModificarCategoria;
	@SuppressWarnings("rawtypes")
	private JComboBox comboModificarLema;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;
	@SuppressWarnings("rawtypes")
	private JComboBox comboNuevaArea;
	@SuppressWarnings("rawtypes")
	private JComboBox comboNuevaCategoria;


	/**
	 * Crea el frame
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FrameModificarRelacionCategoriaLema(Connection conn) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		setTitle("Modificar relaci\u00F3n categor\u00EDa-lema");
		_conn=conn;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 514, 471);
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

		btnAceptar.setBounds(214, 398, 131, 23);
		contentPane.add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(367, 398, 119, 23);
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
		comboModificarCategoria.setBounds(10, 125, 179, 20);
		contentPane.add(comboModificarCategoria);
		AutoCompleteDecorator.decorate(comboModificarCategoria);



		JLabel lblSeleccioneUnaCategora = new JLabel("Seleccione una categor\u00EDa:");
		lblSeleccioneUnaCategora.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccioneUnaCategora.setBounds(10, 97, 179, 17);
		contentPane.add(lblSeleccioneUnaCategora);

		JLabel lblSeleccioneElNuevo = new JLabel("Seleccione las nuevas \u00E1rea y categor\u00EDa a las que pertenece el lema:");
		lblSeleccioneElNuevo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccioneElNuevo.setBounds(10, 228, 413, 14);
		contentPane.add(lblSeleccioneElNuevo);

		JLabel lblSeleccioneUnLema = new JLabel("Seleccione un lema:");
		lblSeleccioneUnLema.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccioneUnLema.setBounds(299, 15, 124, 14);
		contentPane.add(lblSeleccioneUnLema);

		comboModificarLema = new JComboBox();
		comboModificarLema.setEnabled(false);
		comboModificarLema.setBounds(299, 54, 179, 20);
		contentPane.add(comboModificarLema);
		AutoCompleteDecorator.decorate(comboModificarLema);

		JLabel lblrea = new JLabel("\u00C1rea");
		lblrea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblrea.setBounds(80, 277, 37, 14);
		contentPane.add(lblrea);

		JLabel lblCategora = new JLabel("Categor\u00EDa");
		lblCategora.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCategora.setBounds(354, 273, 66, 23);
		contentPane.add(lblCategora);

		comboNuevaArea = new JComboBox();
		comboNuevaArea.setEnabled(false);
		comboNuevaArea.setBounds(10, 315, 179, 20);
		contentPane.add(comboNuevaArea);
		AutoCompleteDecorator.decorate(comboNuevaArea);

		comboNuevaCategoria = new JComboBox();
		comboNuevaCategoria.setEnabled(false);
		comboNuevaCategoria.setBounds(299, 315, 179, 20);
		contentPane.add(comboNuevaCategoria);
		AutoCompleteDecorator.decorate(comboNuevaCategoria);

		/**
		 * Evento que carga el combobox de categorías cuando se elige un área
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				ArrayList<idPalabra> cats = new ArrayList<idPalabra>(); 
				categoria catAux = new categoria();
				if(!comboModificarArea.getSelectedItem().toString().equalsIgnoreCase(null)){
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
			}
			
		};

		comboModificarArea.addItemListener(itemListener);

		/**
		 * Evento que carga el combobox de lemas cuando se elige una categoría
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		ItemListener itemListener2 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				System.out.println("entraaa222"+comboModificarArea.getSelectedItem());
				if(comboModificarCategoria.getSelectedItem()!=null){
					if(comboModificarArea.getSelectedItem()!=null){
						try {
							if (comboModificarCategoria.getSelectedIndex() > 0){
								categoriasLemas catLema = new categoriasLemas();
		
								categoria catAux2 = new categoria(comboModificarCategoria.getSelectedItem().toString());
								int idCat = catAux2.consultarUnaCategoria(_conn);
		
								ArrayList<Integer> lemasDeCat = catLema.devolverLemasDeCategoria(idCat, _conn);
								ArrayList<idPalabra> lemas = new ArrayList<idPalabra>();
								lema lemaAux = new lema();
		
								if ((comboModificarCategoria.getSelectedIndex() > 0)&&(lemasDeCat.size()>0)){
									comboModificarLema.setEnabled(true);
									comboModificarLema.removeAllItems();
									comboModificarLema.addItem("");
									comboModificarLema.setEnabled(true);
									for(int i=0; i<lemasDeCat.size(); i++){
										idPalabra palabra = new idPalabra();
										palabra.setIDPalabra(lemasDeCat.get(i), lemaAux.consultarUnLemaPorId(lemasDeCat.get(i), _conn));
										lemas.add(palabra);
									}
		
									for(int j=0; j<lemas.size(); j++){
										comboModificarLema.addItem(lemas.get(j).getPalabra().toString());
									}
								}
								else{
									comboModificarLema.setEnabled(false);
								}
							}
						} catch (SQLException e) {
							
							e.printStackTrace();
						}
					}
				}
			}
		};

		comboModificarCategoria.addItemListener(itemListener2);

		/**
		 * Evento que carga el combobox de nuevas áreas cuando se elige un lema
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		ItemListener itemListener3 = new ItemListener() {
			@SuppressWarnings("static-access")
			public void itemStateChanged(ItemEvent itemEvent) {
				ArrayList<idPalabra> nuevasAreas = new ArrayList<idPalabra>(); 
				area areaAux = new area();
				System.out.println("entraaa"+comboModificarArea.getSelectedItem());
			
				if(comboModificarCategoria.getSelectedItem()!=null){
				if(comboModificarArea.getSelectedItem()!=null){
					nuevasAreas = areaAux.devolverAreas(_conn);
					if ((comboModificarLema.getSelectedIndex() > 0)&&(nuevasAreas.size()>0)){
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
			}
			}
		};

		comboModificarLema.addItemListener(itemListener3);

		/**
		 * Evento que carga el combobox de nuevas categorías cuando se elige un área de las nuevas áreas
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		ItemListener itemListener4 = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				System.out.println("entraaa3333"+comboModificarArea.getSelectedItem());
				if(comboModificarArea.getSelectedItem()!=null){
					if(comboModificarCategoria.getSelectedItem()!=null){
						if(comboNuevaArea.getSelectedIndex() > 0){
						ArrayList<idPalabra> nuevasCats = new ArrayList<idPalabra>(); 
						categoria catAux = new categoria();
						nuevasCats = catAux.consultarCategoriasConArea(comboNuevaArea.getSelectedItem().toString(), _conn);
						if ((comboNuevaArea.getSelectedIndex() > 0)&&(nuevasCats.size()>0)){
							comboNuevaCategoria.setEnabled(true);
							comboNuevaCategoria.removeAllItems();
							comboNuevaCategoria.addItem("");
							comboNuevaCategoria.setEnabled(true);
							for(int i=0; i<nuevasCats.size(); i++){
								comboNuevaCategoria.addItem(nuevasCats.get(i).getPalabra());
							}
						}
						else{
							comboNuevaCategoria.setEnabled(false);
						}
					
					}
				}
				}
			}
		};

		comboNuevaArea.addItemListener(itemListener4);
	}

	private class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}


		/**
		 * Evento que se encarga de modificar la relación entre un lema y una categoría elegidas por el usuario
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		public void actionPerformed(ActionEvent e) {
			boolean catLemaModificada = false;
			int idLema=0;
			int idCat=0;

			if (e.getSource()==btnAceptar) {

				
				if((comboModificarArea.getSelectedItem()!=null)){
					if((comboModificarArea.getSelectedItem().toString().equalsIgnoreCase(""))||(comboModificarCategoria.getSelectedItem().toString().equalsIgnoreCase(""))||(comboModificarLema.getSelectedItem().toString().equalsIgnoreCase(""))||(comboNuevaArea.getSelectedItem().toString().equalsIgnoreCase("")))
						JOptionPane.showMessageDialog(null,"No se han introducido todos los elementos","AVISO", 2);
					else{
						try {
							categoria miCat = new categoria(comboNuevaCategoria.getSelectedItem().toString());
							idCat = miCat.consultarUnaCategoria(_conn);
	
							lema miLema = new lema(comboModificarLema.getSelectedItem().toString());
							idLema = miLema.consultarUnLema(_conn);
	
							categoriasLemas lemaCatAux = new categoriasLemas(); 
	
							if (!comboNuevaArea.getSelectedItem().toString().equalsIgnoreCase("")){
								catLemaModificada = lemaCatAux.modificarCategoriaLema(_conn,idLema,idCat);
								if (catLemaModificada)
									JOptionPane.showMessageDialog(null, "La relación categoría-lema se ha modificado con éxito", "Modificar relación categoría-lema", 1);
								else
									JOptionPane.showMessageDialog(null, "La relación categoría-lema no se ha podido modificar", "ERROR", 0);
							}
							else
								JOptionPane.showMessageDialog(null, "La relación lema-categoría no se ha podido modificar", "ERROR", 0);
						} catch (SQLException e1) {
							
							e1.printStackTrace();
						}
	
						dispose();
					}
				}
			}
			else if (e.getSource()==btnCancelar) {
				dispose();
			}
		}
	}
}
