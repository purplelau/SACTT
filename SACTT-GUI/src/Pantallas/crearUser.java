package Pantallas;
/**crearUser: interfaz swing encargada de crear usuarios
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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class crearUser extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNombre;
	private JTextField textEmail;
	private JPasswordField passwordField;
	private JLabel lblContrasea;
	private JLabel lblNewLabel;
	private JButton btnCrear;
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
					crearUser frame = new crearUser(true, miconexion.getMiConexion());
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public crearUser(final boolean sadmin, final Connection conn) {
		//setIconImage(Toolkit.getDefaultToolkit().getImage("logo1.png"));
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		setTitle("Alta de usuario");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 401, 283);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		textNombre = new JTextField();
		textNombre.setBounds(176, 33, 149, 22);
		contentPane.add(textNombre);
		textNombre.setColumns(10);
		
		textEmail = new JTextField();
		textEmail.setBounds(176, 68, 149, 22);
		contentPane.add(textEmail);
		textEmail.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(176, 103, 149, 22);
		contentPane.add(passwordField);

		final JComboBox comboBox = new JComboBox();
		if (sadmin){
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "Administrador", "Usuario"}));
		}else {
			comboBox.setModel(new DefaultComboBoxModel(new String[] {"Usuario"}));
		}
		comboBox.setBounds(176, 138, 149, 22);
		contentPane.add(comboBox);
		
		JLabel lblNombre = new JLabel("Nombre *");
		lblNombre.setBounds(107, 36, 57, 16);
		contentPane.add(lblNombre);
		
		JLabel lblDireccinDeCorreo = new JLabel("Direcci\u00F3n de correo");
		lblDireccinDeCorreo.setBounds(51, 71, 113, 16);
		contentPane.add(lblDireccinDeCorreo);
		
		lblContrasea = new JLabel("Contrase\u00F1a *");
		lblContrasea.setBounds(84, 106, 80, 16);
		contentPane.add(lblContrasea);
		
		lblNewLabel = new JLabel("Tipo de usuario *");
		lblNewLabel.setBounds(62, 141, 102, 16);
		contentPane.add(lblNewLabel);
		
		btnCrear = new JButton("Crear");
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textNombre.getText().equals("") && !passwordField.getPassword().equals("")
						&& !comboBox.getSelectedItem().equals("")){
					usuarios usr = new usuarios(textNombre.getText());
					if ((passwordField.getPassword().length>3) && !(usr.usuarioExiste(conn))){
						if(sadmin){
							usr.crearUser(conn, textNombre.getText(), new String(passwordField.getPassword()),textEmail.getText() ,comboBox.getSelectedIndex() +1);
						}else{
							usr.crearUser(conn, textNombre.getText(), new String(passwordField.getPassword()), textEmail.getText(),3);
						}
						JOptionPane.showMessageDialog(null, "El usuario se ha creado correctamente");
						dispose();
					}else{
						if ((passwordField.getPassword().length<=3)){
							JOptionPane.showMessageDialog(null, "La contraseña debe tener más de 3 caracteres");
						}else{
							JOptionPane.showMessageDialog(null, "El nombre de usuario ya existe");
						}
					}
				}else{
					JOptionPane.showMessageDialog(null, "Es necesario introducir todos los campos obligatorios");
				}
			}
		});
		btnCrear.setBounds(77, 200, 97, 25);
		contentPane.add(btnCrear);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancelar.setBounds(228, 200, 97, 25);
		contentPane.add(btnCancelar);
	}
}
