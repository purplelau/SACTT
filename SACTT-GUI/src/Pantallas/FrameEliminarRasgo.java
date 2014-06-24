package Pantallas;
/**FrameEliminarRasgo: interfaz swing encargada de eliminar un rasgo de contenido
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
import IdPalabra.idPalabra;
import LemaRasgo.lemaRasgo;
import RasgoContenido.rasgocontenido;
import javax.swing.JComboBox;
import org.jdesktop.swingx.autocomplete.*;
import java.awt.Font;
import java.io.IOException;
import java.awt.Toolkit;

public class FrameEliminarRasgo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	@SuppressWarnings("rawtypes")
	private JComboBox comboEliminarRasgo;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;
	@SuppressWarnings({ "rawtypes", "unused" })
	private JComboBox comboBox;
	private JLabel lblSeleccioneUnRasgo;



	/**
	 * Crea el frame
	 * @throws SQLException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FrameEliminarRasgo(Connection conn) throws SQLException {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		setTitle("Eliminar rasgo de contenido");
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

		comboEliminarRasgo = new JComboBox();
		comboEliminarRasgo.setMaximumRowCount(5);
		comboEliminarRasgo.setEditable(true);
		comboEliminarRasgo.setBounds(221, 31, 216, 20);
		rasgocontenido rasgoAux = new rasgocontenido();
		ArrayList<idPalabra> rasgos = rasgoAux.consultarTodosRasgos(_conn);
		comboEliminarRasgo.addItem("");
		for(int i=0; i<rasgos.size(); i++){
			comboEliminarRasgo.addItem(rasgos.get(i).getPalabra());
		}
		contentPane.add(comboEliminarRasgo);
		AutoCompleteDecorator.decorate(comboEliminarRasgo);

		lblSeleccioneUnRasgo = new JLabel("Seleccione un rasgo de contenido:");
		lblSeleccioneUnRasgo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSeleccioneUnRasgo.setBounds(10, 32, 201, 17);
		contentPane.add(lblSeleccioneUnRasgo);


	}

	private class SwingAction extends AbstractAction {

		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		/**
		 * Evento que se encarga de eliminar el rasgo de contenido existente elegido por el usuario
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent e) {
			boolean lemaRasgoEliminada = false;
			boolean rasgoEliminado = false;
			int opcion=0;
			int idRasgo = 0;
			int idLema=0;

			if (e.getSource()==btnAceptar) {

				if(comboEliminarRasgo.getSelectedItem().toString().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No se ha introducido rasgo de contenido","AVISO", 2);
				else{
					try {
						rasgocontenido miRasgo = new rasgocontenido(comboEliminarRasgo.getSelectedItem().toString());
						idRasgo = miRasgo.consultarUnRasgo(_conn);
						
						if (comboEliminarRasgo.getSelectedIndex() > 0)
							//Devuelve yes=0 no=1
							opcion=JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este rasgo de contenido?","AVISO" ,0);

						ArrayList<Integer> lemasDeRasgo;
						lemaRasgo lemaRasgoAux = new lemaRasgo();

						if (opcion == 0){

							lemasDeRasgo = lemaRasgoAux.devolverLemasDeRasgo(idRasgo, _conn);
							for(int k=0; k<lemasDeRasgo.size(); k++){
								lemaRasgoEliminada = lemaRasgoAux.eliminarLemaRasgo(idRasgo,lemasDeRasgo.get(k),_conn);
							}
							
							rasgoEliminado = miRasgo.eliminarRasgo(_conn);

							if (rasgoEliminado)
								JOptionPane.showMessageDialog(null, "El rasgo de contenido se ha eliminado con éxito", "Eliminar lema", 1);
							else
								JOptionPane.showMessageDialog(null, "El rasgo de contenido no se ha podido eliminar", "ERROR", 0);
						}
						else
							JOptionPane.showMessageDialog(null, "El rasgo de contenido no se ha podido eliminar", "ERROR", 0);

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

