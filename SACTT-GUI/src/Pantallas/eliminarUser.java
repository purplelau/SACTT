package Pantallas;
/**eliminarUser: interfaz swing encargada de eliminar usuarios
 * @author: Laura Asenjo, María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
* @see:EventQueue,Toolkit,JFrame,JPanel,EmptyBorder,JOptionPane,JTextField,JPasswordField,JComboBox,DefaultComboBoxModel,JLabel,JButton,conexion,usuarios,ActionListener,ActionEvent,Connection
 */


import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.Connection;
import org.jdesktop.swingx.autocomplete.*;
import OtrasFunciones.usuarios;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class eliminarUser extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 * Crea el frame.
	 */
	@SuppressWarnings("unchecked")
	public eliminarUser(String nombreUsr, final Connection conn) {
		//setIconImage(Toolkit.getDefaultToolkit().getImage("logo1.png"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setTitle("Eliminar usuario");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 349, 205);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		@SuppressWarnings("rawtypes")
		final JComboBox comboBox = new JComboBox();
		comboBox.setBounds(79, 56, 163, 22);
		usuarios usr = new usuarios(nombreUsr);
		comboBox.addItem("");
		if (usr.esSuperAdmin(conn)){
			ArrayList<String> listUsuarios = usr.allUsers(conn);
			for (int i=0; i<listUsuarios.size(); i++){
			comboBox.addItem(listUsuarios.get(i));
			}
		}else{
			ArrayList<String> listUsuarios = usr.basicUsers(conn);
			for (int i=0; i<listUsuarios.size(); i++){
			comboBox.addItem(listUsuarios.get(i));
			}
		}
		comboBox.setEditable(true);
		contentPane.add(comboBox);
		AutoCompleteDecorator.decorate(comboBox);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usuarios usr = new usuarios(comboBox.getSelectedItem().toString());
				if (usr.esSuperAdmin(conn)){
					JOptionPane.showMessageDialog(null, "No se puede eliminar el usuario Súper-administrador");
				}else if (!comboBox.getSelectedItem().equals("")){
					usr.eliminarUser(conn, comboBox.getSelectedItem().toString());
					JOptionPane.showMessageDialog(null, "El usuario se ha eliminado correctamente");
					dispose();
				}else{
					JOptionPane.showMessageDialog(null, "Seleccione el usuario que desea eliminar");
				}

			}
		});
		btnEliminar.setBounds(49, 104, 97, 25);
		contentPane.add(btnEliminar);
		
		JButton Cancelar = new JButton("Cancelar");
		Cancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		Cancelar.setBounds(184, 104, 97, 25);
		contentPane.add(Cancelar);
		
		JLabel lblSeleccioneElUsuario = new JLabel("Seleccione el usuario a eliminar.");
		lblSeleccioneElUsuario.setBounds(69, 27, 214, 16);
		contentPane.add(lblSeleccioneElUsuario);
	}
}
