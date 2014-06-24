package Pantallas;
/**launcherUser: interfaz swing encargada de editar usuarios
 * @author: Laura Asenjo, María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:Color,EventQueue,Toolkit,BorderFactory,ImageIcon,JFrame,JPanel,Border,EmptyBorder,JButton,conexion,usuarios,ActionListener,ActionEvent,JLabel,Font,LineBorder
 */

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import ConexionBBDD.conexion;
import OtrasFunciones.usuarios;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import java.awt.Font;
import java.sql.SQLException;

import javax.swing.border.LineBorder;


public class launcherUser extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static conexion miconexion;
	int idUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					launcherUser frame = new launcherUser("sadmin");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Crea el Frame
	 * @throws SQLException 
	 */
	public launcherUser(final String nombre) throws SQLException {
		setResizable(false);
		miconexion=new conexion();
		try {
			miconexion.iniciarBBDD();
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		usuarios usr = new usuarios(nombre);
		idUser = usr.getidUser(miconexion.getMiConexion());
		
		setTitle("SACTT ");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 419, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		
		JButton btnConsulta = new JButton("Consulta");
		btnConsulta.setForeground(new Color(0, 153, 153));
		btnConsulta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				consultaGUI window = null;
				try {
					window = new consultaGUI(miconexion.getMiConexion());
				} catch (InstantiationException e1) {
					
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();
				}
				window.frmConsulta.setVisible(true);
			}
		});
		btnConsulta.setBounds(25, 152, 147, 25);
		contentPane.add(btnConsulta);
		
		JButton btnEditorTexto = new JButton("Editor de Texto");
		btnEditorTexto.setForeground(new Color(255, 102, 0));
		btnEditorTexto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editorConsultaIntegrada editor = new editorConsultaIntegrada();
				editor.setVisible(true);
			}
		});
		btnEditorTexto.setBounds(25, 96, 147, 25);
		contentPane.add(btnEditorTexto);
		
		
		final JLabel nombreUser = new JLabel(nombre);
		nombreUser.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		nombreUser.setBounds(214, 11, 147, 16);
		contentPane.add(nombreUser);
		
		JButton btnEditarUsuario = new JButton("Editar usuario");
		btnEditarUsuario.setForeground(new Color(255, 0, 0));
		btnEditarUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarUser editUsr = new editarUser(idUser,nombreUser,miconexion.getMiConexion());
				editUsr.setVisible(true);
			}
		});
		btnEditarUsuario.setBounds(214, 45, 147, 25);
		contentPane.add(btnEditarUsuario);
		
		JButton btnBusquedaCiega = new JButton("B\u00FAsqueda ciega");
		btnBusquedaCiega.setForeground(new Color(0, 102, 255));
		btnBusquedaCiega.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameBusquedaCiega bciega = new FrameBusquedaCiega(miconexion.getMiConexion());
				bciega.setVisible(true);
			}
		});
		btnBusquedaCiega.setBounds(214, 96, 147, 25);
		contentPane.add(btnBusquedaCiega);
		
		JButton btnSugerencia = new JButton("Enviar sugerencia");
		btnSugerencia.setForeground(new Color(153, 0, 153));
		btnSugerencia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameSugerencias suger = new FrameSugerencias(miconexion.getMiConexion());
				suger.setVisible(true);
			}
		});
		btnSugerencia.setBounds(214, 152, 147, 25);
		contentPane.add(btnSugerencia);

		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				identificacion l = new identificacion();
				l.setVisible(true);
			}
		});
		btnVolver.setBounds(214, 211, 147, 25);
		contentPane.add(btnVolver);
		
		JLabel img = new JLabel(" ");
		img.setBounds(70, 11, 61, 53);
		contentPane.add(img);
				
		//BORDE PARA LA IMAGEN
		@SuppressWarnings("unused")
		Border border = BorderFactory.createLineBorder(Color.GREEN, 1);
		ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo111.png")));
		
		//Propiedades de la etiqueta 
		img.setIcon(image); 
		img.setSize(60,60);
		img.setBorder(new LineBorder(new Color(0, 255, 0), 0));
	}
}
