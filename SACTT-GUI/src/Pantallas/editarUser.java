package Pantallas;
/**editarUser: interfaz swing encargada de editar usuarios
 * @author: Laura Asenjo, María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:EventQueue,Toolkit,JFrame,JPanel,EmptyBorder,JOptionPane,JTextField,JPasswordField,JComboBox,DefaultComboBoxModel,JLabel,JButton,conexion,usuarios,ActionListener,ActionEvent,Connection
 */

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JButton;

import ConexionBBDD.conexion;
import OtrasFunciones.usuarios;

import org.jdesktop.swingx.autocomplete.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class editarUser extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textEmail;
	private JPasswordField passwordField;
	private JLabel lblContrasea;
	private JLabel lblNewLabel;
	private JButton btnGCambios;
	private JButton btnCancelar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				conexion miconexion;
				miconexion=new conexion();
				try {
					miconexion.iniciarBBDD();
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					JLabel nombreUser = new JLabel("sadmin");
					editarUser frame = new editarUser(1,nombreUser,miconexion.getMiConexion());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	boolean sadmin;
	boolean admin;
	String nombreUsr;
	private JTextField textNombre;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public editarUser(final int idUsr,final JLabel nombreUser, final Connection conn) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		usuarios usr = new usuarios(idUsr);
		nombreUsr = usr.getUserfromId(conn);
		setTitle("Editar usuario");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 616, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		textEmail = new JTextField();
		textEmail.setEnabled(false);
		textEmail.setBounds(367, 78, 193, 22);
		contentPane.add(textEmail);
		textEmail.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setEnabled(false);
		passwordField.setBounds(367, 113, 193, 22);
		contentPane.add(passwordField);
		
		
		final JComboBox comboBox = new JComboBox();
		comboBox.setEnabled(false);
		comboBox.setBounds(367, 148, 193, 22);
		sadmin = usr.esSuperAdmin(conn);
		admin = usr.esAdmin(conn);
		if (sadmin){
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "Administrador", "Usuario"}));
		}else {comboBox.setModel(new DefaultComboBoxModel(new String[] {"Usuario"}));}
		contentPane.add(comboBox);
		
		
		final JComboBox comboBoxNombre = new JComboBox();
		comboBoxNombre.setBounds(36, 113, 155, 22);
		if (sadmin){
			comboBoxNombre.addItem("");
			ArrayList<String> listUsuarios = usr.allUsers(conn);
			for (int i=0; i<listUsuarios.size(); i++){
				comboBoxNombre.addItem(listUsuarios.get(i));
			}
		}else if (admin){
			ArrayList<String> listUsuarios = usr.basicUsers(conn);
			comboBoxNombre.addItem("");
			comboBoxNombre.addItem(nombreUsr);
			for (int i=0; i<listUsuarios.size(); i++){
				comboBoxNombre.addItem(listUsuarios.get(i));
			}
		}else{
			comboBoxNombre.addItem("");
			comboBoxNombre.addItem(nombreUsr);
		}
		comboBoxNombre.setEditable(true);
		AutoCompleteDecorator.decorate(comboBoxNombre);
		
		ItemListener itemListener = new ItemListener() {
			
			public void itemStateChanged(ItemEvent itemEvent) {
				usuarios usr = new usuarios(comboBoxNombre.getSelectedItem().toString());
				if(!comboBoxNombre.getSelectedItem().toString().equalsIgnoreCase("")){
					textNombre.setEnabled(true);
					textEmail.setEnabled(true);
					passwordField.setEnabled(true);
					comboBox.setEnabled(true);
					textNombre.setText(comboBoxNombre.getSelectedItem().toString());
					textEmail.setText(usr.getEmail(conn));
					passwordField.setText(usr.getClave(conn));
					if (sadmin){
						if (usr.getPermiso(conn) == 2){
							comboBox.setModel(new DefaultComboBoxModel(new String[] {"Administrador", "Usuario"}));
							comboBox.setSelectedItem("Administrador");
						}else if(usr.getPermiso(conn) == 3){
							comboBox.setModel(new DefaultComboBoxModel(new String[] {"Administrador", "Usuario"}));
							comboBox.setSelectedItem("Usuario");
						}else{
							comboBox.setModel(new DefaultComboBoxModel(new String[] {"Súper-administrador"}));
						}	
					}else{
						if (usr.getPermiso(conn) == 2){
							comboBox.setModel(new DefaultComboBoxModel(new String[] {"Administrador"}));
							comboBox.setSelectedItem("Administrador");
						}else{
							comboBox.setModel(new DefaultComboBoxModel(new String[] {"Usuario"}));
							comboBox.setSelectedItem("Usuario");
						}
					}
			}

			}
		};
		
		comboBoxNombre.addItemListener(itemListener);
		contentPane.add(comboBoxNombre);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(248, 49, 45, 16);
		contentPane.add(lblNombre);
		
		JLabel lblDireccinDeCorreo = new JLabel("Direcci\u00F3n de correo");
		lblDireccinDeCorreo.setBounds(246, 81, 134, 16);
		contentPane.add(lblDireccinDeCorreo);
		
		lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(248, 116, 70, 16);
		contentPane.add(lblContrasea);
		
		lblNewLabel = new JLabel("Tipo de usuario");
		lblNewLabel.setBounds(248, 151, 89, 16);
		contentPane.add(lblNewLabel);
		
		btnGCambios = new JButton("Guardar cambios");
		btnGCambios.setBounds(119, 247, 139, 25);
		btnGCambios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!comboBoxNombre.getSelectedItem().equals("") && !passwordField.getPassword().equals("")
						&& !comboBoxNombre.getSelectedItem().equals("")){
					usuarios usr = new usuarios(textNombre.getText().toString());
					if ((passwordField.getPassword().length>3) && ((textNombre.getText().equals(comboBoxNombre.getSelectedItem().toString())) || !(usr.usuarioExiste(conn)))){
						usr.editarUser(conn, comboBoxNombre.getSelectedItem().toString(), textNombre.getText().toString(),new String(passwordField.getPassword()), textEmail.getText(), permisosTextoANum(comboBox.getSelectedItem().toString()));
						JOptionPane.showMessageDialog(null, "El usuario se ha modificado correctamente");
						usr.getidUser(conn);
						nombreUser.setText(usr.getUserfromId(conn));
						dispose();
					}else{
						if ((passwordField.getPassword().length<=3)){
							JOptionPane.showMessageDialog(null, "La contraseña debe tener más de 3 caracteres");
						}else{
							JOptionPane.showMessageDialog(null, "El nombre de usuario ya existe");
						}
					}
				}
			}
		});
		contentPane.add(btnGCambios);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(311, 247, 139, 25);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		contentPane.add(btnCancelar);
		
		textNombre = new JTextField();
		textNombre.setEnabled(false);
		textNombre.setBounds(367, 46, 193, 22);
		contentPane.add(textNombre);
		textNombre.setColumns(10);
		
	}

	private int permisosTextoANum(String permiso){
		int ret = 0;
		switch (permiso) {
	        case "Súper-administrador":  ret = 1;
	                 break;
	        case "Administrador":  ret = 2;
	                 break;
	        case "Usuario":  ret = 3;
	        		 break;
		}
		
		return ret;
	}
	
}
