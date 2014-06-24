package Pantallas;
/**FrameEliminarArea: interfaz swing encargada de eliminar un área y todo su contenido
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.Action;
import Area.area;
import IdPalabra.idPalabra;
import Categoria.categoria;
import categoriasLemas.categoriasLemas;
import LemaRasgo.lemaRasgo;
import Lema.lema;
import javax.swing.JComboBox;
import org.jdesktop.swingx.autocomplete.*;
import java.awt.Font;
import java.io.IOException;
import java.awt.Toolkit;

public class FrameEliminarArea extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private JComboBox comboEliminar;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;


	/**
	 * Crea el frame
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FrameEliminarArea(Connection conn) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		setTitle("Eliminar \u00E1rea");
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

		comboEliminar = new JComboBox();
		comboEliminar.setMaximumRowCount(5);
		comboEliminar.setEditable(true);
		comboEliminar.setToolTipText("");
		comboEliminar.setBounds(164, 12, 260, 20);
		ArrayList<idPalabra> areas = area.devolverAreas(_conn);
		comboEliminar.addItem("");
		for(int i=0; i<areas.size(); i++){
			comboEliminar.addItem(areas.get(i).getPalabra());
		}
		contentPane.add(comboEliminar);
		AutoCompleteDecorator.decorate(comboEliminar);
	}

	private class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		/**
		 * Evento que se encarga de eliminar el área existente elegida por el usuario
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent e) {
			boolean areaEliminada = false;
			boolean catEliminada = false;
			int opcion=0;
			int idArea=0;
			int idCat=0;
			int existeCat = -1;

			if (e.getSource()==btnAceptar) {

				if(comboEliminar.getSelectedItem().toString().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No se ha introducido area","ERROR", 2);
				else{
					try {
						area miarea =new area(comboEliminar.getSelectedItem().toString());
						idArea= miarea.consultarUnArea(_conn);
						categoria miCat = new categoria();
						ArrayList<idPalabra> categorias = miCat.consultarTodasCategorias(_conn);


						for(int i=0; i<categorias.size(); i++){
							miCat.setCategoria(categorias.get(i).getPalabra());
							existeCat = miCat.consultarUnaCategoriaConArea(idArea,_conn);
							if (existeCat != -1)
								break;
						}
						
						if (existeCat != -1)
							//Devuelve yes=0 no=1
							opcion=JOptionPane.showConfirmDialog(null, "Si elimina este área se eliminarán todas las categorías pertenecientes a la misma y sus lemas asociados, ¿está seguro de eliminar este área?","AVISO" ,0);

						ArrayList<Integer> lemasDeCat;
						lema miLema = new lema();
						String lem;

						
						ArrayList<Integer> rasgosDeLema;
						categoria miCat2 = new categoria();
						categoriasLemas catLem = new categoriasLemas();
						lemaRasgo lemaRasgoAux = new lemaRasgo();
						lema lema1 = new lema();
						if (opcion == 0){
							ArrayList<idPalabra> catsArea = miCat2.consultarCategoriasConArea(miarea.getArea(),_conn);
							for(int i=0; i<catsArea.size(); i++){
								miCat.setCategoria(catsArea.get(i).getPalabra());

								idCat = miCat.consultarUnaCategoria(_conn);
								lemasDeCat = catLem.devolverLemasDeCategoria(idCat, _conn);
								for(int j=0; j<lemasDeCat.size(); j++){
									lem = lema1.consultarUnLemaPorId(lemasDeCat.get(j), _conn);
									miLema.setLema(lem);
									categoriasLemas.eliminarLemaCategoria(idCat,lemasDeCat.get(j),_conn);

									rasgosDeLema = lemaRasgoAux.devolverRasgosDeLema(lemasDeCat.get(j), _conn);
									for(int k=0; k<rasgosDeLema.size(); k++){
										lemaRasgoAux.eliminarLemaRasgo(rasgosDeLema.get(k),lemasDeCat.get(j),_conn);
									}

									miLema.eliminarLema(_conn);	
								}
								catEliminada = miCat.eliminarCategoria(_conn, miarea);

							}
							areaEliminada = miarea.eliminarArea(_conn);
							if (areaEliminada)
								JOptionPane.showMessageDialog(null, "El area se ha eliminado con éxito", "Eliminar área", 1);
							else
								JOptionPane.showMessageDialog(null, "El area no se ha podido eliminar", "ERROR", 0);
						}
						else
							JOptionPane.showMessageDialog(null, "El area no se ha podido eliminar", "ERROR", 0);

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
