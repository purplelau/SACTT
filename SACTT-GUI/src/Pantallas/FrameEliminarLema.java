package Pantallas;
/**FrameEliminarLema: interfaz swing encargada de eliminar un lema
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
import categoriasLemas.categoriasLemas;
import LemaRasgo.lemaRasgo;
import Lema.lema;

import javax.swing.JComboBox;

import org.jdesktop.swingx.autocomplete.*;

import java.awt.Font;
import java.io.IOException;

import javax.swing.JTextArea;
import java.awt.Toolkit;


public class FrameEliminarLema extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private JComboBox comboEliminarLema;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;
	@SuppressWarnings({ "rawtypes", "unused" })
	private JComboBox comboBox;
	private JLabel lblSeleccioneUnLema;



	/**
	 * Crea el frame
	 * @throws SQLException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public FrameEliminarLema(Connection conn) throws SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		setTitle("Eliminar lema");
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



		comboEliminarLema = new JComboBox();
		comboEliminarLema.setMaximumRowCount(5);
		comboEliminarLema.setEditable(true);
		comboEliminarLema.setBounds(199, 31, 288, 20);
		lema lemaAux = new lema();
		ArrayList<idPalabra> lemas = lemaAux.devolverLemas(_conn);
		comboEliminarLema.addItem("");
		for(int i=0; i<lemas.size(); i++){
			comboEliminarLema.addItem(lemas.get(i).getPalabra());
		}
		contentPane.add(comboEliminarLema);
		AutoCompleteDecorator.decorate(comboEliminarLema);

		lblSeleccioneUnLema = new JLabel("Seleccione un lema:");
		lblSeleccioneUnLema.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccioneUnLema.setBounds(10, 32, 141, 17);
		contentPane.add(lblSeleccioneUnLema);

		final JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(199, 74, 288, 72);
		contentPane.add(textArea);

		/**
		 * Evento que carga el textbox de las categorías a las que pertenece el lema elegido
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		ItemListener itemListener = new ItemListener() {
			int idLema = 0;
			public void itemStateChanged(ItemEvent itemEvent) {
				lema miLema =new lema(comboEliminarLema.getSelectedItem().toString());
				categoria catAux = new categoria();
				try {
					idLema= miLema.consultarUnLema(_conn);
					ArrayList<Integer> catsDeLema;
					categoriasLemas catLema = new categoriasLemas();
					catsDeLema = catLema.devolverCategoriasDeLema(idLema, _conn);
					System.out.println(catsDeLema.size());
					if(catsDeLema.size() > 0){
						String nombreCat="Pertenece a la/s categoría/s:\n";
						for(int i=0; i<catsDeLema.size(); i++){
							nombreCat +=" - "+catAux.consultarCategoriaPorId(catsDeLema.get(i),_conn)+"\n";
							textArea.setText(nombreCat);
						}
					}
					else
						textArea.setText("");

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};

		comboEliminarLema.addItemListener(itemListener);
	}

	private class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}

		/**
		 * Evento que se encarga de eliminar un lema existente elegido por el usuario
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent e) {
			boolean catLemaEliminada = false;
			boolean lemaRasgoEliminada = false;
			boolean lemaEliminado = false;
			boolean catEliminada = false;
			int opcion=0;
			int idLema=0;
			int idCat=0;
			//int existeCat = -1;

			if (e.getSource()==btnAceptar) {

				if(comboEliminarLema.getSelectedItem().toString().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No se ha introducido lema","ERROR", 2);
				else{
					try {
						lema miLema =new lema(comboEliminarLema.getSelectedItem().toString());
						idLema= miLema.consultarUnLema(_conn);
						
						if (comboEliminarLema.getSelectedIndex() > 0)
							//Devuelve yes=0 no=1
							opcion=JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este lema?","AVISO" ,0);

						ArrayList<Integer> catsDeLema;
						ArrayList<Integer> rasgosDeLema;
						lemaRasgo lemaRasgoAux = new lemaRasgo();
						categoriasLemas catLema = new categoriasLemas();
						if (opcion == 0){

							catsDeLema = catLema.devolverCategoriasDeLema(idLema, _conn);
							for(int i=0; i<catsDeLema.size(); i++){
								catLemaEliminada = categoriasLemas.eliminarLemaCategoria(catsDeLema.get(i),idLema,_conn);
							}

							rasgosDeLema = lemaRasgoAux.devolverRasgosDeLema(idLema, _conn);
							for(int k=0; k<rasgosDeLema.size(); k++){
								lemaRasgoEliminada = lemaRasgoAux.eliminarLemaRasgo(rasgosDeLema.get(k),idLema,_conn);
							}

							lemaEliminado = miLema.eliminarLema(_conn);	


							if (lemaEliminado)
								JOptionPane.showMessageDialog(null, "El lema se ha eliminado con éxito", "Eliminar lema", 1);
							else
								JOptionPane.showMessageDialog(null, "El lema no se ha podido eliminar", "ERROR", 0);
						}
						else
							JOptionPane.showMessageDialog(null, "El lema no se ha podido eliminar", "ERROR", 0);

					} catch (SQLException e1) {
	
						e1.printStackTrace();
					} catch (IOException e1) {
		
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
