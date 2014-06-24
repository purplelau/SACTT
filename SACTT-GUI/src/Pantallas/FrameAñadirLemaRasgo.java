package Pantallas;
/**FrameAñadirLemaRasgo: interfaz swing encargada de añadir una relación lema-rasgo
 * @author: Laura Asenjo, María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:ActionEvent,ItemEvent,ItemListener,Connection,ArrayList,JComboBox,JFrame,JPanel,EmptyBorder,swing*,categoriasLemas,autocomplete*area,categoria,conexion,idPalabra,lema,lemaRasgo,rasgocontenido,TextAutoCompleter,Toolkit
 */

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import categoriasLemas.categoriasLemas;
import org.jdesktop.swingx.autocomplete.*;
import Area.area;
import Categoria.categoria;
import IdPalabra.idPalabra;
import Lema.lema;
import LemaRasgo.lemaRasgo;
import RasgoContenido.rasgocontenido;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Toolkit;

public class FrameAñadirLemaRasgo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JButton btnCancelar;
	JButton btnAceptar;
	JComboBox<String> comboBoxLema;
	@SuppressWarnings("rawtypes")
	JComboBox comboBoxCategoria;
	@SuppressWarnings("rawtypes")
	JComboBox comboBoxArea;
	public  SwingAction action = new SwingAction();
	static Connection _conn;
	private JTextField textFieldRasgo;


	/**
	 * Crea el frame
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FrameAñadirLemaRasgo(Connection conn) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		setTitle("Asociar Lema a un Rasgo");
		_conn=conn;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 227);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		JLabel lblAsociarLema = new JLabel("Elegir Lema:");
		lblAsociarLema.setBounds(10, 75, 139, 14);
		contentPane.add(lblAsociarLema);
		
		JLabel lblAsociarRasgoDe = new JLabel("Asociar Rasgo de Contenido:");
		lblAsociarRasgoDe.setBounds(10, 112, 173, 14);
		contentPane.add(lblAsociarRasgoDe);
		
		comboBoxLema = new JComboBox<String>();
		comboBoxLema.setEditable(true);
		comboBoxLema.setBounds(209, 75, 215, 20);
		contentPane.add(comboBoxLema);
		AutoCompleteDecorator.decorate(comboBoxLema);
		
		 btnAceptar = new JButton("Aceptar");
		 btnAceptar.addActionListener(this.action);
		btnAceptar.setBounds(209, 155, 89, 23);
		contentPane.add(btnAceptar);
		
		 btnCancelar = new JButton("Cancelar");
		 btnCancelar.addActionListener(this.action);
		 btnCancelar.setBounds(335, 155, 89, 23);
		contentPane.add(btnCancelar);
		
		JLabel lblElegirrea = new JLabel("Elegir \u00C1rea:");
		lblElegirrea.setBounds(10, 21, 80, 14);
		contentPane.add(lblElegirrea);
		
		comboBoxCategoria = new JComboBox();
		comboBoxCategoria.setBounds(209, 49, 215, 20);
		comboBoxCategoria.setEditable(true);
		contentPane.add(comboBoxCategoria);
		AutoCompleteDecorator.decorate(comboBoxCategoria);
		
		
		comboBoxArea = new JComboBox();
		comboBoxArea.setBounds(209, 18, 215, 20);
		comboBoxArea.setEditable(true);
		contentPane.add(comboBoxArea);
		AutoCompleteDecorator.decorate(comboBoxArea);
		comboBoxArea.addItem("   ");
		
		textFieldRasgo = new JTextField();
		textFieldRasgo.setBounds(209, 109, 215, 20);
		contentPane.add(textFieldRasgo);
		textFieldRasgo.setColumns(10);
		textFieldRasgo.setEnabled(false);
		cargarComboArea();
		
		cargarTextFieldRasgos();
		
		/**
		 * Evento que carga el combobox de categorías cuando se elige un área
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		ItemListener itemListener = new ItemListener() {
			
			public void itemStateChanged(ItemEvent itemEvent) {
				
				if (comboBoxArea.getSelectedIndex() >= 0){
					
					categoria c = new categoria();
					ArrayList<idPalabra> cats = new ArrayList<idPalabra>(); 
					
					cats = c.consultarCategoriasConArea(comboBoxArea.getSelectedItem().toString(), _conn);
					
					if(cats.size()==0){
						comboBoxLema.removeAllItems();
						comboBoxCategoria.removeAllItems();
						comboBoxCategoria.setEnabled(false);
						comboBoxLema.setEnabled(false);
						textFieldRasgo.setEnabled(false);
						textFieldRasgo.setText(null);
					}
					else{
						
						comboBoxCategoria.setEnabled(true);
						comboBoxLema.setEnabled(false);
						comboBoxCategoria.removeAllItems();
						textFieldRasgo.setEnabled(false);
						textFieldRasgo.setText(null);
						comboBoxCategoria.addItem("   ");
						for(int i=0; i<cats.size(); i++){
							comboBoxCategoria.addItem(cats.get(i).getPalabra());
							
						}
					}
				}
			}
		};
		

		comboBoxArea.addItemListener(itemListener);
		
		JLabel lblElegirCategora = new JLabel("Elegir Categor\u00EDa:");
		lblElegirCategora.setBounds(10, 46, 96, 14);
		contentPane.add(lblElegirCategora);
		
	
		/**
		 * Evento que carga el combobox de lemas cuando se elige una categoría
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		ItemListener itemListener2 = new ItemListener() {
		
			public void itemStateChanged(ItemEvent itemEvent) {
			
			 if (comboBoxCategoria.getSelectedIndex() > 0){
				
					categoriasLemas lemaCateg = new categoriasLemas();
					categoria micategoria=new categoria(comboBoxCategoria.getSelectedItem().toString());
					int idCateg=micategoria.consultarUnaCategoria(_conn);
					ArrayList<Integer> lemas = new ArrayList<Integer>();
					lemas = lemaCateg.consultarLemasdeunaCategoria(idCateg, _conn);
					
					if(lemas.size()==0){
						comboBoxLema.removeAllItems();
						comboBoxLema.setEnabled(false);
						textFieldRasgo.setEnabled(false);
						textFieldRasgo.setText(null);
					}
					else{
						comboBoxLema.setEnabled(true);
						comboBoxLema.removeAllItems();
						textFieldRasgo.setEnabled(true);
						textFieldRasgo.setText(null);
						lema milema=new lema();
						comboBoxLema.addItem("  ");
						for(int i=0; i<lemas.size(); i++){
							comboBoxLema.addItem(milema.consultarUnLemaPorId(lemas.get(i),_conn).toString());
							
						}
					}	
				}
				
			}
		};
		
		comboBoxCategoria.addItemListener(itemListener2);

	}
	
	/**
	 * Función que carga el textbox de rasgos de contenido para la acción de autocompletar
	 * @param no recibe nada
	 * @return no devuelve nada
	 */	
	   public void cargarTextFieldRasgos(){
		   textFieldRasgo.setText("");
			TextAutoCompleter textAutoAcompleter = new TextAutoCompleter( textFieldRasgo );
			rasgocontenido mirasgo =new rasgocontenido();
			ArrayList<idPalabra> misRasgos=new ArrayList<idPalabra>();
			misRasgos=mirasgo.consultarTodosRasgos(_conn);
			 for(int i=0; i<misRasgos.size(); i++){
				 textAutoAcompleter.addItem(misRasgos.get(i).getPalabra());
		       	 }	
			
			textAutoAcompleter.setCaseSensitive(true); // Sensible a mayúsculas
			
			
		   
		   
		   
	   }
	
	   /**
		 * Función que carga el combobox de áreas
		 * @param no recibe nada
		 * @return no devuelve nada
		 */	
	@SuppressWarnings({ "unused", "unchecked" })
	public void cargarComboArea(){
	   		 area miArea =new area();
	       	 ArrayList<idPalabra>misAreas=new ArrayList<idPalabra>();
	       	misAreas=area.devolverAreas(_conn);
	       	 for(int i=0; i<misAreas.size(); i++){
	       		comboBoxArea.addItem(misAreas.get(i).getPalabra());
	       	 }	
	       	 
		}
	   

	private class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

			
			public SwingAction() {
				putValue(NAME, "SwingAction");
				putValue(SHORT_DESCRIPTION, "Some short description");
			}

			/**
			 * Evento que se encarga de añadir la nueva relación lema-rasgo introducida por el usuario
			 * @param recibe el evento
			 * @return no devuelve nada
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (e.getSource()==btnAceptar) {
					if(comboBoxLema.getSelectedIndex()==0)
						JOptionPane.showMessageDialog(null,"No se ha seleccionado Lema."); 
					else{       
							lemaRasgo miLemaRasgo =new lemaRasgo();	
							rasgocontenido mirasgo=new rasgocontenido(textFieldRasgo.getText());
							
							
							if(textFieldRasgo.getText().equalsIgnoreCase("")){
								JOptionPane.showMessageDialog(null,"La Asociacion no se ha podido realizar,falta el rasgo de contenido.");
							}
							else{
								mirasgo.añadirRasgo(_conn);
								if(miLemaRasgo.añadirLemaRasgo(_conn, comboBoxLema.getSelectedItem().toString(), textFieldRasgo.getText())){
									JOptionPane.showMessageDialog(null,"Asociacion Correcta. Lema asociado a un rasgo de contenido.");
									dispose();
								}
								else
									JOptionPane.showMessageDialog(null,"La Asociacion no se ha podido realizar");
							
							} 
								
						
					}
				}
				else if (e.getSource()==btnCancelar) {
					dispose();
		        }
			}
		}
}
