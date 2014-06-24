package Pantallas;
/**FrameBusquedaCiega: interfaz swing encargada de realizar la búsqueda ciega
 * @author: Laura Asenjo, María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:JFrame,JPanel,EmptyBorder,JOptionPane,JScrollPane,JTextField,JLabel,JButton,area,categoria,conexion,lema,rasgocontenido,ActionListener,ActionEvent,Connection,SQLException,ArrayList,Toolkit
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import Area.area;
import Categoria.categoria;
import Lema.lema;
import RasgoContenido.rasgocontenido;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.Toolkit;




public class FrameBusquedaCiega extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	
	

	/**
	 * Create the frame.
	 */
	public FrameBusquedaCiega(final Connection conn) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		setAutoRequestFocus(false);
		setTitle("B\u00FAsqueda Ciega");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 625, 391);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		textField = new JTextField();
		textField.setBounds(182, 21, 428, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblIntroduzcaPalabraA = new JLabel("Introduzca palabra a buscar:");
		lblIntroduzcaPalabraA.setBounds(10, 17, 179, 29);
		contentPane.add(lblIntroduzcaPalabraA);
		
		final JLabel lblResultadosEncontrados = new JLabel("Resultados encontrados:");
		lblResultadosEncontrados.setBounds(10, 97, 155, 14);
		contentPane.add(lblResultadosEncontrados);
		lblResultadosEncontrados.setVisible(false);
		
		final JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);

			}
		});
		btnAceptar.setBounds(465, 332, 122, 20);
		contentPane.add(btnAceptar);
		btnAceptar.setVisible(false);
		
		final JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(10, 122,600, 125);
		getContentPane().add(scrollPane2);
		final JLabel lblNewLabel = new JLabel("");
		scrollPane2.setViewportView(lblNewLabel);
		
		
		scrollPane2.setVisible(false);
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String miPalabra=textField.getText().toString();
				ArrayList<String> resul = new ArrayList<String>();
				ArrayList<String>miRasgo = new ArrayList<String>();
				ArrayList<String>miArea = new ArrayList<String>();
				ArrayList<String> miCategoria = new ArrayList<String>();
				ArrayList<String> miLema = new ArrayList<String>();
				String texto="";
				if(textField.getText().equals("")){
					  lblResultadosEncontrados.setVisible(false);
					  lblNewLabel.setVisible(false);
					
					  btnAceptar.setVisible(false);
					  scrollPane2.setVisible(false); 
					  JOptionPane.showMessageDialog(null,"No hay elementos para buscar");
					  setVisible(false);
				}
				else{
					area miarea = new area();
					try {
						
						miArea=miarea.consultarUnAreaCiega(conn,miPalabra);
						
						if(miArea.size() >0){
							lblResultadosEncontrados.setVisible(true);
							lblNewLabel.setVisible(true);
							btnAceptar.setVisible(true);
							scrollPane2.setVisible(true); 
							for(int i=0; i<miArea.size();i++)	{
								
								texto=texto+"<p>Está en un área, o es un área: "+"\""+miArea.get(i)+"\""+".</p>";
								resul.add("Está en un área, o es un área: "+"\""+miArea.get(i)+"\""+".");
							}
						}
						else{
							textField.setText("");
							
						}
					
							
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					
					categoria micategoria = new categoria();
					try {
						
						
						miCategoria=micategoria.consultarUnaCategoriaCiega(conn,miPalabra);
						
						if(miCategoria.size()> 0){
							lblResultadosEncontrados.setVisible(true);
							lblNewLabel.setVisible(true);
							btnAceptar.setVisible(true);
							scrollPane2.setVisible(true); 
							for(int i=0; i<miCategoria.size();i++)	{
								texto=texto +"<p>Está en una categoría, o es una categoria: "+"\""+miCategoria.get(i)+"\""+".</p>";
								resul.add("Está en una categoría, o es una categoria: "+"\""+miCategoria.get(i)+"\""+".");
							}
						}
						else{
							textField.setText("");
							
						}
					
					
							
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					
					lema milema = new lema();
					try {
						
						miLema=milema.consultarUnLemaCiega(conn,miPalabra);
						
						if(miLema.size()>0){
							lblResultadosEncontrados.setVisible(true);
							lblNewLabel.setVisible(true);
							btnAceptar.setVisible(true);
							scrollPane2.setVisible(true); 
							for(int i=0; i<miLema.size();i++){	
								texto=texto +"<p>Está en un lema, o es un lema: "+"\""+miLema.get(i)+"\""+".\n</p>";
							resul.add("Está en un lema, o es un lema: "+"\""+miLema.get(i)+"\""+".\n");
							}
						}
						else{
							textField.setText("");
							
						}
					
					
							
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					
					rasgocontenido mirasgo = new rasgocontenido();
					try {
						
						miRasgo=mirasgo.consultarUnRasgoCiega(conn,miPalabra);
						
						if(miRasgo.size()>0){
							lblResultadosEncontrados.setVisible(true);
							lblNewLabel.setVisible(true);
							btnAceptar.setVisible(true);
							scrollPane2.setVisible(true); 
							for(int i=0; i<miRasgo.size();i++)	{
								texto=texto +"<p>Está en un rasgo de contenido, o es un rasgo de contenido: "+"\""+miRasgo.get(i)+"\""+".\n</p>";
							resul.add("Está en un rasgo de contenido, o es un rasgo de contenido: "+"\""+miRasgo.get(i)+"\""+".\n");
							}
						}
						else{
							textField.setText("");
							
						}
					
					
							
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					lblNewLabel.setText("<html>"+texto+"</html>");
				
				
				}
			}
			
			
		});
		btnBuscar.setBounds(380, 65, 89, 23);
		contentPane.add(btnBuscar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
			
		});
		btnCancelar.setBounds(505, 65, 89, 23);
		contentPane.add(btnCancelar);
	
	}
}
