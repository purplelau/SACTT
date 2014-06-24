package Pantallas;
/**FrameAñadirLemaCategoria: interfaz swing encargada de añadir una relación lema-categoría
 * @author: Laura Asenjo, María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:ActionEvent,ItemEvent,ItemListener,Connection,ArrayList,JComboBox,JFrame,JPanel,EmptyBorder,swing*,autocomplete*,TextAutoCompleter,categoriasLemas,area,categoria,conexion,idPalabra,lema,Toolkit
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
import org.jdesktop.swingx.autocomplete.*;
import com.mxrck.autocompleter.TextAutoCompleter;
import categoriasLemas.categoriasLemas;
import Area.area;
import Categoria.categoria;
import IdPalabra.idPalabra;
import Lema.lema;
import java.awt.Toolkit;



public class FrameAñadirLemaCategoria extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JComboBox<String> comboBoxCategoria;
	JButton btnAsociar, btnCancelar;
	public  SwingAction action = new SwingAction();
	static Connection _conn;
	private JLabel lblElegirArea;
	private JComboBox<String> comboBoxArea;
	private JTextField textFieldLema;
	@SuppressWarnings({ "rawtypes", "unused" })
	private JList list_1;

	

	/**
	 * Crea el frame
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FrameAñadirLemaCategoria(Connection conn) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);

		setTitle("Asociar lema a categor\u00EDa");
		_conn=conn;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 282);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		JLabel lblAsociarLema = new JLabel("Asociar Lema:");
		lblAsociarLema.setBounds(26, 114, 89, 14);
		contentPane.add(lblAsociarLema);

		JLabel lblNewLabel = new JLabel("Asociar Categor\u00EDa:");
		lblNewLabel.setBounds(26, 67, 142, 14);
		contentPane.add(lblNewLabel);

		comboBoxCategoria = new JComboBox<String>();
		comboBoxCategoria.setBounds(159, 64, 265, 20);
		comboBoxCategoria.setEditable(true);
		contentPane.add(comboBoxCategoria);
		AutoCompleteDecorator.decorate(comboBoxCategoria);

		btnAsociar = new JButton("Asociar");
		btnAsociar.addActionListener(this.action);
		btnAsociar.setBounds(159, 210, 105, 23);
		contentPane.add(btnAsociar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(319, 210, 105, 23);
		contentPane.add(btnCancelar);

		lblElegirArea = new JLabel("Elegir Area:");
		lblElegirArea.setBounds(28, 22, 87, 14);
		contentPane.add(lblElegirArea);


		textFieldLema = new JTextField();
		textFieldLema.setBounds(159, 111, 265, 20);
		contentPane.add(textFieldLema);
		textFieldLema.setColumns(10);
		textFieldLema.setEnabled(false);

		comboBoxArea = new JComboBox();
		comboBoxArea.setBounds(159, 19, 265, 20);
		contentPane.add(comboBoxArea);
		comboBoxArea.setEditable(true);
		AutoCompleteDecorator.decorate(comboBoxArea);
		comboBoxArea.addItem("  ");
		cargarComboArea();
		cargarTextFieldLemas();

		/**
		 * Evento que carga el combobox de categorías cuando se elige un área
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		ItemListener itemListener = new ItemListener() {
			@SuppressWarnings("unused")
			int contador=0;
			public void itemStateChanged(ItemEvent itemEvent) {
				contador++;			
				comboBoxCategoria.removeAllItems();
				
				if (comboBoxArea.getSelectedIndex() > 0){					
					categoria c = new categoria();
					ArrayList<idPalabra> cats = new ArrayList<idPalabra>(); 
					System.out.println("pasa"+comboBoxArea.getSelectedItem().toString());
					cats = c.consultarCategoriasConArea(comboBoxArea.getSelectedItem().toString(), _conn);
					
					if(cats.size()==0){
						comboBoxCategoria.setEnabled(false);
						
						textFieldLema.setText(null);
					}
					else{
						textFieldLema.setText(null);
						textFieldLema.setEnabled(true);
						comboBoxCategoria.setEnabled(true);
						comboBoxCategoria.removeAllItems();
						comboBoxCategoria.addItem("  ");
						for(int i=0; i<cats.size(); i++){
							comboBoxCategoria.addItem(cats.get(i).getPalabra());

						}
					}
				}


			}
		};
		comboBoxArea.addItemListener(itemListener);



	}

	/**
	 * Función que carga el combobox de áreas
	 * @param no recibe nada
	 * @return no devuelve nada
	 */	
	@SuppressWarnings("unused")
	public void cargarComboArea(){
		area miArea =new area();
		ArrayList<idPalabra>misAreas=new ArrayList<idPalabra>();
		misAreas=area.devolverAreas(_conn);
		for(int i=0; i<misAreas.size(); i++){
			comboBoxArea.addItem(misAreas.get(i).getPalabra());
		}	

	}

	/**
	 * Función que carga el textbox de lemas para la acción de autocompletar
	 * @param no recibe nada
	 * @return no devuelve nada
	 */	
	public void cargarTextFieldLemas(){
		textFieldLema.setText(null);
		TextAutoCompleter textAutoAcompleter = new TextAutoCompleter( textFieldLema );
		lema miLema =new lema();
		ArrayList<idPalabra> mislemas=new ArrayList<idPalabra>();
		mislemas=miLema.devolverLemas(_conn);
		for(int i=0; i<mislemas.size(); i++){
			textAutoAcompleter.addItem(mislemas.get(i).getPalabra());
		}	
		


	}

	



	private class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		/**
		 * Evento que se encarga de añadir la nueva relación lema-categoría introducida por el usuario
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		public void actionPerformed(ActionEvent e) {	
			if (e.getSource()==btnAsociar) {

			

				if(textFieldLema.getText().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No hay lema, debe introducir uno");
				else{
					

					lema milema=new lema(textFieldLema.getText());
					milema.añadirLema(_conn);
					categoriasLemas miLemacategoria =new categoriasLemas();	
					if(miLemacategoria.añadirLemaCategoria(_conn, comboBoxCategoria.getSelectedItem().toString(),textFieldLema.getText() )){
						JOptionPane.showMessageDialog(null,"Asociacion Correcta. Lema asignado a una categoría.");
						
						dispose();
					}
					else
						JOptionPane.showMessageDialog(null,"La Asociacion no se ha podido realizar.");

					
				}

			}
			else if (e.getSource()==btnCancelar) {
				
				dispose();
			}
		}




	}		
}
			
			
