package Pantallas;
/**identificacion: interfaz swing encargada solicitar el usuario y la contraseña y de dar acceso a las funcionalidades 
 * correspondientes según se trate de un usuario, un administrador, o el súper-administrador.
 * @author: Laura Asenjo, María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:EventQueue,JFrame,JPanel,Border,EmptyBorder,BorderFactory,JOptionPane,JTextField,JPasswordField,JButton,JLabel,ImageIcon,conexion,usuarios,ActionListener,ActionEvent,SQLException,Font,Color,Toolkit,LineBorder
 */
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ImageIcon; 

import ConexionBBDD.conexion;
import ConexionBBDD.creartablas;
import OtrasFunciones.usuarios;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.border.LineBorder;

public class identificacion extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nombreField;
	private JPasswordField passwordField;
	static conexion miconexion;
	private JButton btnVolver;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					identificacion frame = new identificacion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				miconexion=new conexion();
				try {
					miconexion.iniciarBBDD();
				} catch (ClassNotFoundException e2) {
					e2.printStackTrace();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				@SuppressWarnings("unused")
				creartablas mistablas;
				 try {
					mistablas = new creartablas(miconexion.getMiConexion());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public identificacion() {

		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		
		miconexion=new conexion();
		try {
			miconexion.iniciarBBDD();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
		setResizable(false);
		setTitle("SACTT-Identificaci\u00F3n Usuario");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 464, 490);
		setLocation(400,100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		nombreField = new JTextField();
		nombreField.setBounds(136, 299, 312, 22);
		contentPane.add(nombreField);
		nombreField.setColumns(10);
	
		passwordField = new JPasswordField();
		passwordField.setBounds(136, 345, 312, 22);
		contentPane.add(passwordField);
		
		JButton btnAcceder = new JButton("Acceder");
		btnAcceder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nombre = nombreField.getText();
				char[] passwd = passwordField.getPassword();
				if (!(nombreField.equals(null)) && !(passwordField.equals(null))){
					usuarios usr = new usuarios(nombre, new String(passwd));
					if (usr.contraseñaValida(miconexion.getMiConexion())){
						if (usr.esAdmin(miconexion.getMiConexion()) || usr.esSuperAdmin(miconexion.getMiConexion())){
							launcher l = new launcher(nombre);
							l.setVisible(true);
							
							dispose();
						}else{
							launcherUser l = null;
							try {
								l = new launcherUser(nombre);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							l.setVisible(true);
							dispose();
						}
					}else{
						JOptionPane.showMessageDialog(null, "El usuario o la contraseña no son válidos.");
						nombreField.setText("");
						passwordField.setText("");
					}
				}
			}
		});
		btnAcceder.setBounds(244, 407, 97, 25);
		contentPane.add(btnAcceder);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(42, 302, 56, 16);
		contentPane.add(lblNombre);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a");
		lblContrasea.setBounds(42, 348, 84, 16);
		contentPane.add(lblContrasea);
		
		btnVolver = new JButton("Salir");
		btnVolver.setBounds(351, 407, 97, 25);
		contentPane.add(btnVolver);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		JLabel img = new JLabel(" ");
		ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png"))); 
		img.setBounds(164, 80, 351, 209);
		contentPane.add(img);

		//BORDE PARA LA IMAGEN
		@SuppressWarnings("unused")
		Border border = BorderFactory.createLineBorder(Color.GREEN, 1);
		//Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		//Propiedades de la etiqueta 
		img.setIcon(image); 
		img.setSize(135,135); 
		img.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		 
		
		JLabel lblNewLabel = new JLabel("Bienvenido a SACTT");
		lblNewLabel.setForeground(new Color(165, 42, 42));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel.setBounds(116, 26, 252, 29);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Introduzca su usuario para comenzar la sesi\u00F3n:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblNewLabel_1.setBounds(136, 264, 260, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel img2 = new JLabel(" ");
		img2.setBounds(23, 20, 56, 52);
		contentPane.add(img2);
		ImageIcon image2 = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/ucm.gif")));
		contentPane.add(img2);
		
		//BORDE PARA LA IMAGEN
		@SuppressWarnings("unused")
		Border border2 = BorderFactory.createLineBorder(Color.RED, 1);
		//Propiedades de la etiqueta 
		img2.setIcon(image2); 
		img2.setSize(50,50);
		img2.setBorder(new LineBorder(new Color(0, 0, 0), 0));
		
	
	}
}
