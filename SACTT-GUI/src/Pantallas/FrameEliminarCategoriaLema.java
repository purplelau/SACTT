package Pantallas;
/**FrameEliminarCategoriaLema: interfaz swing encargada de eliminar una relación categoría-lema
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
import IdPalabra.idPalabra;
import Categoria.categoria;
import Lema.lema;
import categoriasLemas.categoriasLemas;
import javax.swing.JComboBox;
import org.jdesktop.swingx.autocomplete.*;
import java.awt.Font;
import java.io.IOException;
import java.awt.Toolkit;

public class FrameEliminarCategoriaLema extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;
	@SuppressWarnings({ "rawtypes", "unused" })
	private JComboBox comboBox;
	@SuppressWarnings("rawtypes")
	private JComboBox comboEliminarLema;
	private JLabel lblSeleccioneUnLema;
	@SuppressWarnings("rawtypes")
	private JComboBox comboEliminarCategoria;
	private JLabel lblNewLabel;




	/**
	 * Crea el frame
	 * @throws SQLException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FrameEliminarCategoriaLema(Connection conn) throws SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		setTitle("Eliminar relaci\u00F3n categor\u00EDa - lema");
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

		comboEliminarCategoria = new JComboBox();
		comboEliminarCategoria.setEditable(true);
		comboEliminarCategoria.setBounds(10, 61, 201, 20);
		categoria catAux = new categoria();
		ArrayList<idPalabra> cats = catAux.consultarTodasCategorias(_conn);
		comboEliminarCategoria.addItem("");
		for(int i=0; i<cats.size(); i++){
			comboEliminarCategoria.addItem(cats.get(i).getPalabra());
		}
		contentPane.add(comboEliminarCategoria);
		AutoCompleteDecorator.decorate(comboEliminarCategoria);

		comboEliminarLema = new JComboBox();
		comboEliminarLema.setEnabled(false);
		comboEliminarLema.setMaximumRowCount(5);
		comboEliminarLema.setEditable(true);
		comboEliminarLema.setBounds(236, 61, 201, 20);
		contentPane.add(comboEliminarLema);
		AutoCompleteDecorator.decorate(comboEliminarLema);

		/**
		 * Evento que carga el combobox de lemas cuando se elige una categoría
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		ItemListener itemListener = new ItemListener() {
			int idCat = 0;
			public void itemStateChanged(ItemEvent itemEvent) {
				if(comboEliminarCategoria.getSelectedItem()!=null)
				
				try {
					categoria miCate = new categoria(comboEliminarCategoria.getSelectedItem().toString());
					lema lemaAux = new lema();
					idCat = miCate.consultarUnaCategoria(_conn);
					categoriasLemas catLema = new categoriasLemas();
					ArrayList<Integer> lemasDeCat = catLema.devolverLemasDeCategoria(idCat, _conn);
					ArrayList<idPalabra> lemas = new ArrayList<idPalabra>();
					if ((comboEliminarCategoria.getSelectedIndex() > 0)&&(lemasDeCat.size() >0)){
						comboEliminarLema.setEnabled(true);
						for(int i=0; i<lemasDeCat.size(); i++){
							idPalabra palabra = new idPalabra();
							palabra.setIDPalabra(lemasDeCat.get(i), lemaAux.consultarUnLemaPorId(lemasDeCat.get(i), _conn));
							lemas.add(palabra);
						}

						comboEliminarLema.removeAllItems();
						comboEliminarLema.addItem("");
						comboEliminarLema.setEnabled(true);

						for(int j=0; j<lemas.size(); j++){
							comboEliminarLema.addItem(lemas.get(j).getPalabra().toString());
						}
					}
					else{
						comboEliminarLema.setEnabled(false);
					}

				} catch (SQLException e) {
					
					e.printStackTrace();
				}

			}
		};

		comboEliminarCategoria.addItemListener(itemListener);

		lblSeleccioneUnLema = new JLabel("Seleccione un lema:");
		lblSeleccioneUnLema.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccioneUnLema.setBounds(236, 22, 125, 14);
		contentPane.add(lblSeleccioneUnLema);



		lblNewLabel = new JLabel("Seleccione una categor\u00EDa: ");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 23, 152, 14);
		contentPane.add(lblNewLabel);


	}

	private class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		/**
		 * Evento que se encarga de eliminar la relación categoría-lema elegida por el usuario
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		public void actionPerformed(ActionEvent e) {
			boolean lemaCatEliminada = false;
			int opcion=0;
			int idCat = 0;
			int idLema=0;

			if (e.getSource()==btnAceptar) {

				if(comboEliminarCategoria.getSelectedItem()==null)
					JOptionPane.showMessageDialog(null,"No se ha introducido categoría y/o lema","AVISO", 2);
					else
					{
				
				if((comboEliminarCategoria.getSelectedItem().toString().equalsIgnoreCase(""))||(comboEliminarLema.getSelectedItem().toString().equalsIgnoreCase("")))
					JOptionPane.showMessageDialog(null,"No se ha introducido categoría y/o lema","AVISO", 2);	
					
				else{
					try {
						categoria miCat = new categoria(comboEliminarCategoria.getSelectedItem().toString());
						idCat = miCat.consultarUnaCategoria(_conn);

						lema miLema = new lema(comboEliminarLema.getSelectedItem().toString());
						idLema = miLema.consultarUnLema(_conn);

						if ((comboEliminarLema.getSelectedIndex() > 0)&&(comboEliminarCategoria.getSelectedIndex() > 0))
							//Devuelve yes=0 no=1
							opcion=JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar esta relación categoría - lema?","AVISO" ,0);

						if (opcion == 0){
							lemaCatEliminada = categoriasLemas.eliminarLemaCategoria(idCat, idLema, _conn);
							if (lemaCatEliminada)
								JOptionPane.showMessageDialog(null, "La relación se ha eliminado con éxito", "Eliminar lema", 1);
							else
								JOptionPane.showMessageDialog(null, "La relación no se ha podido eliminar", "ERROR", 0);
						}
						else
							JOptionPane.showMessageDialog(null, "La relación no se ha podido eliminar", "ERROR", 0);

					} catch (SQLException e1) {
						
						e1.printStackTrace();
					} catch (IOException e1) {
						
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
