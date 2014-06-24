package Pantallas;
/**consultaGUI: interfaz swing encargada de realizar una consulta
 * @author: Laura Asenjo, María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:Toolkit,Connection,SQLException,ArrayList,List,JFrame, JComboBox, JLabel,JButton,area,categoria,idPalabra,consulta,rasgocontenido,ActionListener,ActionEvent,ItemEvent,ItemListener,MouseAdapter,MouseEvent,JList,JScrollPane,ListSelectionEvent,Font,ListSelectionListener
 */

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import Area.area;
import Categoria.categoria;
import IdPalabra.idPalabra;
import OtrasFunciones.consulta;
import RasgoContenido.rasgocontenido;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Font;


public class consultaGUI{

	JFrame frmConsulta;
	static Connection _conn;
	String seleccionado;
	editorConsultaIntegrada editor = null;


	/**
	 * Lanza la aplicación
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					conexion miconexion=new conexion();
					miconexion.iniciarBBDD();
					consultaGUI window = new consultaGUI(miconexion.getMiConexion());
					window.frmConsulta.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Crea la aplicación
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public consultaGUI(Connection conn) throws InstantiationException, IllegalAccessException {
		_conn = conn;
		initialize();
	}
	/**
	 * Consulta en la aplicación
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public consultaGUI(Connection conn, editorConsultaIntegrada edi) throws InstantiationException, IllegalAccessException {
		_conn = conn;
		initialize();
		editor = edi;
	}

	/**
	 * Inicializa el contenido del frame
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings("unchecked")
	private void initialize() throws InstantiationException, IllegalAccessException {
		
		frmConsulta = new JFrame();
		//frmConsulta.setIconImage(Toolkit.getDefaultToolkit().getImage("logo1.png"));
		frmConsulta.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		frmConsulta.setTitle("Consulta");
		frmConsulta.setBounds(100, 100, 513, 493);
		frmConsulta.setLocation(400,100);
		frmConsulta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		@SuppressWarnings("rawtypes")
		final JComboBox comboCats = new JComboBox();
		comboCats.setBounds(48, 123, 400, 22);
		frmConsulta.getContentPane().setLayout(null);
		@SuppressWarnings("rawtypes")
		final JComboBox comboAreas = new JComboBox();
		comboAreas.setBounds(48, 55, 400, 22);
		frmConsulta.getContentPane().add(comboAreas);
		final String[] prueba = {""};
		@SuppressWarnings({ "rawtypes"})
		final JList list = new JList(prueba);
		list.setBounds(0, 4, 316, 288);
		list.setEnabled(false);
		frmConsulta.getContentPane().add(list);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(48, 323, 400, 75);
		frmConsulta.getContentPane().add(scrollPane2);
		
		@SuppressWarnings("rawtypes")
		final JList listResul = new JList();
		listResul.setToolTipText("En caso de que se encuentre en el editor de texto y quiera a\u00F1adir un t\u00E9rmino haga doble clic para insertarlo.");
		scrollPane2.setViewportView(listResul);
		listResul.setEnabled(false);
		
		listResul.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        @SuppressWarnings("rawtypes")
				JList listResul = (JList)evt.getSource();
		       if (evt.getClickCount() > 1) {   // Double-click
		    	   if (editor!=null){
		    		   	//int index = listResul.locationToIndex(evt.getPoint());
		            	Object selec = listResul.getSelectedValue();
		        		editor.appendString(selec.toString());
			            frmConsulta.dispose();
		           }
		        }
		    }
		});
		
		//area a = new area();
		ArrayList<idPalabra> areas = area.devolverAreas(_conn);
		comboAreas.addItem("   ");
		for(int i=0; i<areas.size(); i++){
			comboAreas.addItem(areas.get(i).getPalabra());
		}
		
		
		/**
		 * Evento que carga el combobox de categorías cuando se elige un área
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				categoria c = new categoria();
				ArrayList<idPalabra> cats = new ArrayList<idPalabra>(); 
				cats = c.consultarCategoriasConArea(comboAreas.getSelectedItem().toString(), _conn);
				if ((comboAreas.getSelectedIndex() > 0)&&(cats.size() > 0)){
					comboCats.setEnabled(true);
					comboCats.removeAllItems();
					comboCats.addItem("   ");
					comboCats.setEnabled(true);
					for(int i=0; i<cats.size(); i++){
						comboCats.addItem(cats.get(i).getPalabra());
					}
					ArrayList<String> aux = new ArrayList<String>();
					list.setListData(aux.toArray());
					listResul.setListData(aux.toArray());
				}else{
					ArrayList<String> aux = new ArrayList<String>();
					comboCats.removeAllItems();
					list.setListData(aux.toArray());
					listResul.setListData(aux.toArray());
					comboCats.setEnabled(false);
				}
			}
		 };
		 comboAreas.addItemListener(itemListener);

		
		
		comboCats.setEnabled(false);
		frmConsulta.getContentPane().add(comboCats);
		
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setBounds(48, 216, 400, 75);
		frmConsulta.getContentPane().add(scrollPane);
		

		
		listResul.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        @SuppressWarnings("rawtypes")
				JList listResul = (JList)evt.getSource();
		        if (evt.getClickCount() > 1) {   // Triple-click
		            @SuppressWarnings("unused")
					int index = listResul.locationToIndex(evt.getPoint());
		            @SuppressWarnings("unused")
					Object selec = listResul.getSelectedValue();
		        }
		    }
		});
		
		/**
		 * Evento que carga el textbox de rasgos cuando se elige una categoría
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		ItemListener itemListener2 = new ItemListener() {
			
			public void itemStateChanged(ItemEvent itemEvent) {
				if ((comboCats.getSelectedIndex() > 0) && (comboCats.isEnabled()) && (comboAreas.isEnabled())){
					@SuppressWarnings("unused")
					String[] prueba = {"uno", "dos"};
					ArrayList<String> misRasgos = new ArrayList<String>();
					list.setEnabled(true);
					rasgocontenido r = new rasgocontenido();
					categoria c = new categoria();
					int indCat;
					try {
						indCat = c.consultarUnaCategoriaArea(_conn, comboAreas.getSelectedItem().toString(), comboCats.getSelectedItem().toString());
			            misRasgos = r.consultaRasgosCat(_conn, indCat);
					} catch (SQLException e) {
						e.printStackTrace();
					}

					ArrayList<String> aux = new ArrayList<String>();
					listResul.setListData(aux.toArray());
					list.setListData(misRasgos.toArray());
				}else{
					comboCats.setEnabled(false); 
					ArrayList<String> aux = new ArrayList<String>();
					list.setListData(aux.toArray());
					listResul.setListData(aux.toArray());
				}
			}
		};
		comboCats.addItemListener(itemListener2); 
		
		final JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(193, 412, 97, 22);
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("rawtypes")
				List listRasgos = list.getSelectedValuesList();
				ArrayList<String> rsgs = new ArrayList<String>();
				ArrayList<String> resul = new ArrayList<String>();
				for (int i=0; i<listRasgos.size(); i++){
					rsgs.add((String) listRasgos.get(i));
				}
				consulta c = new consulta(rsgs,comboAreas.getSelectedItem().toString(), comboCats.getSelectedItem().toString() );
				c.rasgosAIndices(_conn);
				resul = c.consultar(_conn);
				
				
				listResul.setEnabled(true);
				listResul.removeAll();
				listResul.setListData(resul.toArray());
				
				
			}
		});
		btnConsultar.setEnabled(false);
		frmConsulta.getContentPane().add(btnConsultar);

		
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent evt) {
				
				int[] indices = list.getSelectedIndices();
				if ((indices.length >0) && (comboCats.isEnabled()) && (comboAreas.isEnabled())){
					btnConsultar.setEnabled(true);
				}else{btnConsultar.setEnabled(false);}
			}
		});
		
		
		JLabel lblSeleccioneUnrea = new JLabel("Seleccione un \u00E1rea");
		lblSeleccioneUnrea.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSeleccioneUnrea.setBounds(48, 37, 151, 16);
		frmConsulta.getContentPane().add(lblSeleccioneUnrea);
		
		JLabel lblNewLabel = new JLabel("Seleccione una categor\u00EDa");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(48, 101, 199, 20);
		frmConsulta.getContentPane().add(lblNewLabel);
		

		
		JLabel lblRasgos = new JLabel("Seleccione al menos un rasgo");
		lblRasgos.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblRasgos.setBounds(48, 168, 296, 22);
		frmConsulta.getContentPane().add(lblRasgos);
		
		JLabel lblMantengaPulsadoCtrl = new JLabel("Mantenga pulsado CTRL para selecci\u00F3n m\u00FAltiple");
		lblMantengaPulsadoCtrl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblMantengaPulsadoCtrl.setBounds(49, 187, 370, 22);
		frmConsulta.getContentPane().add(lblMantengaPulsadoCtrl);
		
		JLabel labelResul = new JLabel("Resultado(s) de la consulta:");
		labelResul.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelResul.setBounds(48, 304, 222, 16);
		frmConsulta.getContentPane().add(labelResul);
		
		JButton btnSalir = new JButton("Cancelar");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmConsulta.dispose();
			}
		});
		btnSalir.setBounds(351, 411, 97, 25);
		frmConsulta.getContentPane().add(btnSalir);
		

	}
}