package Pantallas;
/**launcher: interfaz swing que permite acceder a todas las funcionalidades permitidas para el Súper-administrador
 * y los administradores
 * @author: Laura Asenjo, María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:EventQueue,Toolkit,BorderFactory,ImageIcon,JFileChooser,JFrame,JOptionPane,JPanel,Border,EmptyBorder,JButton,conexion,CrearExcel,EscribirCSV,ReadExcel,leerCSV,usuarios,ActionListener,ActionEvent,Color,JLabel,Font,File,FileReader,IOException,SQLException,UIManager,LineBorder
 */
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import ConexionBBDD.conexion;
import OtrasFunciones.CrearExcel;
import OtrasFunciones.EscribirCSV;
import OtrasFunciones.ReadExcel;
import OtrasFunciones.leerCSV;
import OtrasFunciones.usuarios;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.UIManager;
import javax.swing.border.LineBorder;




public class launcher extends JFrame {

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

				launcher frame = new launcher("sadmin");
				frame.setVisible(true);


				miconexion=new conexion();

				try {
					miconexion.iniciarBBDD();
				} catch (SQLException | ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	

		});
	}

	/**
	 * Crea el frame
	 */
	public launcher(final String nombreUsr) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		
		miconexion=new conexion();

		try {
			miconexion.iniciarBBDD();
		} catch (SQLException | ClassNotFoundException e2) {
			e2.printStackTrace();
		}
		usuarios usr = new usuarios(nombreUsr);
		idUser = usr.getidUser(miconexion.getMiConexion());
		setResizable(false);

		setTitle("SACTT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 519, 660);
		contentPane = new JPanel();
		contentPane.setForeground(Color.MAGENTA);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		JButton btnAArea = new JButton("\u00C1rea");
		btnAArea.setForeground(Color.BLUE);
		btnAArea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameAñadirArea aarea = new FrameAñadirArea(miconexion.getMiConexion());
				aarea.setVisible(true);
			}
		});
		btnAArea.setBounds(57, 71, 165, 25);
		contentPane.add(btnAArea);

		JButton btnACat = new JButton("Categor\u00EDa");
		btnACat.setForeground(Color.BLUE);
		btnACat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameAñadirCategoria acategoria = new FrameAñadirCategoria(miconexion.getMiConexion());
				acategoria.setVisible(true);
			}
		});
		btnACat.setBounds(57, 109, 165, 25);
		contentPane.add(btnACat);

		JButton btnALema = new JButton("Lema");
		btnALema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameAñadirLema frame = new FrameAñadirLema(miconexion.getMiConexion());
				frame.setVisible(true);
			}
		});
		btnALema.setForeground(Color.BLUE);
		btnALema.setBounds(57, 147, 165, 25);
		contentPane.add(btnALema);

		JButton btnEArea = new JButton("\u00C1rea");
		btnEArea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	
				FrameEliminarArea frame = new FrameEliminarArea(miconexion.getMiConexion());
				frame.setVisible(true);
			}
		});
		btnEArea.setForeground(new Color(204, 51, 0));
		btnEArea.setBounds(57, 338, 165, 25);
		contentPane.add(btnEArea);

		JButton btnECat = new JButton("Categor\u00EDa");
		btnECat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameEliminarCategoria frame = new FrameEliminarCategoria(miconexion.getMiConexion());
				frame.setVisible(true);
			}
		});
		btnECat.setForeground(new Color(204, 51, 0));
		btnECat.setBounds(57, 374, 165, 25);
		contentPane.add(btnECat);

		JButton btnELema = new JButton("Lema");
		btnELema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameEliminarLema frame;
				try {
					frame = new FrameEliminarLema(miconexion.getMiConexion());
					frame.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnELema.setForeground(new Color(204, 51, 0));
		btnELema.setBounds(57, 410, 165, 25);
		contentPane.add(btnELema);

		JButton btnARasgo = new JButton("Rasgo");
		btnARasgo.setForeground(Color.BLUE);
		btnARasgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameAñadirRasgo frame = new FrameAñadirRasgo(miconexion.getMiConexion());
				frame.setVisible(true);
			}
		});
		btnARasgo.setBounds(57, 185, 165, 25);
		contentPane.add(btnARasgo);

		JButton btnERasgo = new JButton("Rasgo");
		btnERasgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameEliminarRasgo frame;
				try {
					frame = new FrameEliminarRasgo(miconexion.getMiConexion());
					frame.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnERasgo.setForeground(new Color(204, 51, 0));
		btnERasgo.setBounds(57, 446, 165, 25);
		contentPane.add(btnERasgo);

		JButton btnMRasgo = new JButton("Rasgo");
		btnMRasgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameModificarRasgo mrasgo;
				try {
					mrasgo = new FrameModificarRasgo(miconexion.getMiConexion());
					mrasgo.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnMRasgo.setForeground(new Color(139, 0, 139));
		btnMRasgo.setBounds(298, 185, 165, 25);
		contentPane.add(btnMRasgo);

		JButton btnMLema = new JButton("Lema");
		btnMLema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameModificarLema mlema;
				try {
					mlema = new FrameModificarLema(miconexion.getMiConexion());
					mlema.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnMLema.setForeground(new Color(139, 0, 139));
		btnMLema.setBounds(298, 147, 165, 25);
		contentPane.add(btnMLema);

		JButton btnMCat = new JButton(" Categor\u00EDa");
		btnMCat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameModificarCategoria mcat = new FrameModificarCategoria(miconexion.getMiConexion());
				mcat.setVisible(true);
			}
		});
		btnMCat.setForeground(new Color(139, 0, 139));
		btnMCat.setBounds(298, 109, 165, 25);
		contentPane.add(btnMCat);

		JButton btnMArea = new JButton("\u00C1rea");
		btnMArea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameModificarArea marea = new FrameModificarArea(miconexion.getMiConexion());
				marea.setVisible(true);
			}
		});
		btnMArea.setForeground(new Color(139, 0, 139));
		btnMArea.setBounds(298, 71, 165, 25);
		contentPane.add(btnMArea);

		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setToolTipText("Consulta");
		btnConsultar.setFont(UIManager.getFont("Button.font"));
		btnConsultar.setForeground(new Color(0, 128, 128));
		btnConsultar.addActionListener(new ActionListener() {
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
		btnConsultar.setBounds(298, 338, 165, 25);
		contentPane.add(btnConsultar);

		JButton btnBusquedaCiega = new JButton("B\u00FAsqueda ciega");
		btnBusquedaCiega.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameBusquedaCiega frame = new FrameBusquedaCiega(miconexion.getMiConexion());
				frame.setVisible(true);
			}
		});
		btnBusquedaCiega.setEnabled(true);
		btnBusquedaCiega.setForeground(new Color(0, 128, 128));
		btnBusquedaCiega.setBounds(298, 374, 165, 25);
		contentPane.add(btnBusquedaCiega);

		JButton btnALemaCat = new JButton("Lema a Categor\u00EDa");
		btnALemaCat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameAñadirLemaCategoria frame = new FrameAñadirLemaCategoria(miconexion.getMiConexion());
				frame.setVisible(true);
			}
		});
		btnALemaCat.setForeground(Color.BLUE);
		btnALemaCat.setBounds(57, 227, 165, 25);
		contentPane.add(btnALemaCat);

		JLabel lblAadir = new JLabel("A\u00F1adir");
		lblAadir.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAadir.setForeground(Color.BLUE);
		lblAadir.setBounds(57, 42, 56, 16);
		contentPane.add(lblAadir);

		JLabel lblModificar = new JLabel("Modificar");
		lblModificar.setForeground(new Color(139, 0, 139));
		lblModificar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblModificar.setBounds(298, 42, 109, 16);
		contentPane.add(lblModificar);

		JLabel lblEliminar = new JLabel("Eliminar");
		lblEliminar.setForeground(new Color(204, 51, 0));
		lblEliminar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblEliminar.setBounds(57, 309, 109, 16);
		contentPane.add(lblEliminar);

		JButton btnARasgoLema = new JButton("Rasgo a Lema");
		btnARasgoLema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameAñadirLemaRasgo frame = new FrameAñadirLemaRasgo(miconexion.getMiConexion());
				frame.setVisible(true);
			}
		});
		btnARasgoLema.setForeground(Color.BLUE);
		btnARasgoLema.setBounds(57, 265, 165, 25);
		contentPane.add(btnARasgoLema);

		JButton btnELemaCat = new JButton("Lema-Categor\u00EDa");
		btnELemaCat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameEliminarCategoriaLema frame;
				try {
					frame = new FrameEliminarCategoriaLema(miconexion.getMiConexion());
					frame.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnELemaCat.setForeground(new Color(204, 51, 0));
		btnELemaCat.setBounds(57, 482, 165, 25);
		contentPane.add(btnELemaCat);

		JButton btnELemaRasgo = new JButton("Lema-Rasgo");
		btnELemaRasgo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameEliminarLemaRasgo frame;
				try {
					frame = new FrameEliminarLemaRasgo(miconexion.getMiConexion());
					frame.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnELemaRasgo.setForeground(new Color(204, 51, 0));
		btnELemaRasgo.setBounds(57, 518, 165, 25);
		contentPane.add(btnELemaRasgo);
		
		final JLabel nombreUser = new JLabel(nombreUsr);
		nombreUser.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		nombreUser.setBounds(34, 11, 188, 16);
		contentPane.add(nombreUser);

		JButton btnNewButton = new JButton("Gestionar usuarios");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestionUsuarios gestionUsr = new gestionUsuarios(idUser, nombreUser,miconexion.getMiConexion());
				gestionUsr.setVisible(true);
			}
		});
		btnNewButton.setForeground(new Color(123, 104, 238));
		btnNewButton.setBounds(298, 518, 165, 25);
		contentPane.add(btnNewButton);

		//botón importar Excel o CSV
		JButton btnImportarExcel = new JButton("Importar Excel o CSV");
		btnImportarExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFileChooser chooser = new JFileChooser();
				if (chooser.showSaveDialog(launcher.this) != JFileChooser.APPROVE_OPTION)
					return;
				File file = chooser.getSelectedFile();
				if (file == null)
					return;

				FileReader reader = null;
				try {

					reader = new FileReader(file);


					String nombre=file.getName();
					int index = nombre.lastIndexOf('.');
					if (index == -1) {
						System.out.println("No tiene extension");
					} else {
						if((!nombre.substring(index + 1).equals("xls")) && (!nombre.substring(index + 1).equals("csv")))
							JOptionPane.showMessageDialog(null,"Tiene que ser un Excel, con extensión .xls o bien un CSV");
						else{
							if(nombre.substring(index + 1).equals("xls")){
								ReadExcel miExcel = new ReadExcel();
								miExcel.leerArchivoExcel(file.getAbsolutePath(), miconexion.getMiConexion());
								JOptionPane.showMessageDialog(null,"Se ha realizado la importación a la Base de datos correctamente.");
							}
							else{


								leerCSV micsv=new leerCSV();
								micsv.leerUnCSV(file.getAbsolutePath(),miconexion.getMiConexion());
								JOptionPane.showMessageDialog(null,"Se ha realizado la importación a la Base de datos correctamente.");

							}
						}

					}


				} catch (IOException ex) {
					JOptionPane.showMessageDialog(launcher.this,
							"Archivo no encontrado", "ERROR", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException x) {
						}
					}
				}
			}


		});
		btnImportarExcel.setForeground(new Color(255, 153, 51));
		btnImportarExcel.setBounds(298, 410, 165, 25);
		contentPane.add(btnImportarExcel);

		//Botón Exportar Excel o CSV
		JButton btnExportarExcelO = new JButton("Exportar Excel o CSV");
		btnExportarExcelO.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try{

					JFileChooser file=new JFileChooser();
					file.showSaveDialog(launcher.this);
					File guarda =file.getSelectedFile();

					if(guarda !=null)
					{

						if((!guarda.toString().contains(".csv")) && (!guarda.toString().contains(".xls"))){
							JOptionPane.showMessageDialog(null,
									"Solo pueden ser extensiones CSV y XLS",
									"Información",JOptionPane.INFORMATION_MESSAGE);

						}
						else{
							if(guarda.toString().contains(".csv")){

								EscribirCSV micsv= new EscribirCSV();
								micsv.escribirCSV(guarda.toString(), miconexion.getMiConexion());

								JOptionPane.showMessageDialog(null,
										"El archivo se a guardado Exitosamente",
										"Información",JOptionPane.INFORMATION_MESSAGE);

							}
							else{


								CrearExcel miExcel= new CrearExcel();
								miExcel.crearExcel(guarda.toString(), miconexion.getMiConexion());

								JOptionPane.showMessageDialog(null,
										"El archivo se a guardado Exitosamente",
										"Información",JOptionPane.INFORMATION_MESSAGE);

							}

						}
					}

				}
				catch (SQLException e) {

					e.printStackTrace();
				} 
			}

		});
		btnExportarExcelO.setForeground(new Color(255, 153, 51));
		btnExportarExcelO.setBounds(298, 446, 165, 25);
		contentPane.add(btnExportarExcelO);

		JButton btnMAreaCat = new JButton("\u00C1rea - Categor\u00EDa");
		btnMAreaCat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameModificarRelacionAreaCategoria mAreaCat = new FrameModificarRelacionAreaCategoria(miconexion.getMiConexion());
				mAreaCat.setVisible(true);
			}
		});
		btnMAreaCat.setForeground(new Color(255, 0, 0));
		btnMAreaCat.setBounds(298, 228, 165, 23);
		contentPane.add(btnMAreaCat);		

		JButton btnMCatLema = new JButton("Categor\u00EDa - Lema");
		btnMCatLema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameModificarRelacionCategoriaLema mAreaCat = new FrameModificarRelacionCategoriaLema(miconexion.getMiConexion());
				mAreaCat.setVisible(true);
			}
		});
		btnMCatLema.setForeground(new Color(255, 0, 0));
		btnMCatLema.setBounds(298, 266, 165, 23);
		contentPane.add(btnMCatLema);



		JButton btnSalir = new JButton("Salir");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnSalir.setForeground(Color.BLACK);
		btnSalir.setBounds(189, 581, 165, 25);
		contentPane.add(btnSalir);

		JLabel lblOtrasFuncionalidades = new JLabel("Otras funcionalidades");
		lblOtrasFuncionalidades.setForeground(new Color(0, 128, 128));
		lblOtrasFuncionalidades.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblOtrasFuncionalidades.setBounds(294, 309, 179, 16);
		contentPane.add(lblOtrasFuncionalidades);
		
		//BORDE PARA LA IMAGEN
		@SuppressWarnings("unused")
		Border border = BorderFactory.createLineBorder(Color.GREEN, 1);
		      
		JLabel img = new JLabel(" ");
		img.setBackground(Color.WHITE);
		img.setForeground(Color.BLACK);
		img.setBounds(458, 11, 45, 49);
		ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo11.png"))); 
		contentPane.add(img);
		
		//Propiedades de la etiqueta 
		img.setIcon(image); 
		img.setSize(40,40);
		img.setBorder(new LineBorder(new Color(0, 255, 0), 0));
		
		JButton btnEditorT = new JButton("Editor de Texto");
		btnEditorT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editorConsultaIntegrada editor = new editorConsultaIntegrada();
				editor.setVisible(true);
			}
		});
		btnEditorT.setForeground(new Color(0, 100, 0));
		btnEditorT.setBounds(298, 482, 165, 25);
		contentPane.add(btnEditorT);
		btnAArea.setToolTipText("A\u00F1adir un \u00E1rea.");
		 		btnACat.setToolTipText("A\u00F1adir una categor\u00EDa, y asociarla a un \u00E1rea.");
		 		btnEArea.setToolTipText("Eliminar un \u00E1rea.");
		 		btnECat.setToolTipText("Eliminar una categor\u00EDa.");
		 		btnELema.setToolTipText("Eliminar un lema.");
		 		btnARasgo.setToolTipText("A\u00F1adir un rasgo.");
		 		btnERasgo.setToolTipText("Eliminar un rasgo.");
		 		btnMRasgo.setToolTipText("Modificar el nombre de un rasgo.");
		 		btnMLema.setToolTipText("Modificar el nombre de un lema.");
		 		btnMCat.setToolTipText("Modificar el nombre de una categoría.");
		 		btnMArea.setToolTipText("Modificar el nombre de un \u00E1rea.");
		 		btnConsultar.setToolTipText("Consulta inversa de t\u00E9rminos.");
		 		btnBusquedaCiega.setToolTipText("B\u00FAsqueda de un t\u00E9rmino en toda la base de datos.");
				btnALemaCat.setToolTipText("Asociar un lema a una categor\u00EDa.");
				btnARasgoLema.setToolTipText("Asociar un rasgo a un lema.");
		 		btnELemaCat.setToolTipText("Eliminar la relaci\u00F3n entre un lema y una categor\u00EDa.");
		 		btnELemaRasgo.setToolTipText("Eliminar la relaci\u00F3n entre un rasgo y un lema.");
		 		btnNewButton.setToolTipText("Acceder al panel de gesti\u00F3n de usuarios (altas, bajas, y modificaciones).");
		 		btnImportarExcel.setToolTipText("Importal desde un .xls o un .csv");
				btnExportarExcelO.setToolTipText("Exportar a un .xls o un .csv");
			btnMAreaCat.setToolTipText("Cambiar el \u00E1rea al que pertenece una categor\u00EDa.");
			btnMCatLema.setToolTipText("Cambiar la categor\u00EDa a la que pertenece un lema.");

	}
}
