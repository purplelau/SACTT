package Pantallas;
/**gestionUsuarios: interfaz swing que permite acceder a las tres funcionalidades de edición de ususarios 
 * (añadir, editar, y eliminar)
 * @author: Laura Asenjo, María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:Toolkit,JFrame,JLabel,JPanel,EmptyBorder,JButton,Connection,conexion,usuarios,ActionListener,ActionEvent,Color
 */

import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.sql.Connection;
import ConexionBBDD.conexion;
import OtrasFunciones.usuarios;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class gestionUsuarios extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static conexion miconexion;


	/**
	 * Crea el frame
	 */
	public gestionUsuarios(final int idUsr, final JLabel nombreUser ,final Connection conn) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		usuarios usr = new usuarios(idUsr);
		final String nombre = usr.getUserfromId(conn);
		
		setTitle("Gesti\u00F3n de usuarios");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 317, 283);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		JButton btnAdd = new JButton("A\u00F1adir usuario");
		btnAdd.setForeground(Color.BLUE);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usuarios usr = new usuarios(nombre);
				crearUser crearUsr = new crearUser(usr.esSuperAdmin(conn),conn);
				crearUsr.setVisible(true);
			}
		});
		btnAdd.setBounds(79, 40, 129, 25);
		contentPane.add(btnAdd);
		
		JButton btnDel = new JButton("Eliminar usuario");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarUser elimUsr = new eliminarUser(nombre, conn);
				elimUsr.setVisible(true);
			}
		});
		btnDel.setForeground(new Color(165, 42, 42));
		btnDel.setBounds(79, 88, 129, 25);
		contentPane.add(btnDel);
		
		JButton btnEdit = new JButton("Editar usuario");
		btnEdit.setForeground(new Color(0, 128, 128));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarUser editUsr = new editarUser(idUsr,nombreUser ,conn);
				editUsr.setVisible(true);
			}
		});
		btnEdit.setBounds(79, 139, 129, 25);
		contentPane.add(btnEdit);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setBounds(79, 199, 129, 25);
		contentPane.add(btnSalir);
	}
}
