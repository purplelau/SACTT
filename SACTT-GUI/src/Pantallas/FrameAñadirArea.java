package Pantallas;
/**FrameA�adirArea: interfaz swing encargada de a�adir un �rea
 * @author: Laura Asenjo, Mar�a de Valvanera Jim�nez, Silvia Cabezas
 * @version: 1.0
 * @see:JFrame,JPanel,EmptyBorder,AbstractAction,JLabel,JOptionPane,JTextField,JButton,ActionEvent,Connection,SQLException,Action,area,conexion,Toolkit
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.Action;
import Area.area;
import java.awt.Toolkit;

public class FrameA�adirArea extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldA�adir;
	JButton btnAceptar;
	JButton btnCancelar;
	public  Action action = new SwingAction();
	static Connection _conn;
	

	/**
	 * Crea el frame
	 */
	public FrameA�adirArea(Connection conn) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
		setResizable(false);
		setTitle("A\u00F1adir \u00E1rea");
		_conn=conn;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 124);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocation(400,100);
		JLabel lblNewLabel = new JLabel("A\u00F1adir \u00C1rea:");
		lblNewLabel.setBounds(10, 14, 144, 17);
		contentPane.add(lblNewLabel);
		
		textFieldA�adir = new JTextField();
		textFieldA�adir.setBounds(164, 11, 260, 20);
		contentPane.add(textFieldA�adir);
		textFieldA�adir.setColumns(10);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(this.action);

		btnAceptar.setBounds(164, 52, 131, 23);
		contentPane.add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(this.action);
		btnCancelar.setBounds(305, 52, 119, 23);
		contentPane.add(btnCancelar);
	}
	
	private class SwingAction extends AbstractAction {
	
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		
		/**
		 * Evento que se encarga de a�adir el �rea introducida por el usuario
		 * @param recibe el evento
		 * @return no devuelve nada
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==btnAceptar) {
				if(textFieldA�adir.getText().equalsIgnoreCase(""))
					JOptionPane.showMessageDialog(null,"No se ha introducido area"); 
				else{
			         area miarea =new area(textFieldA�adir.getText());
			        
					try {
						if(miarea.a�adirArea(_conn))
							JOptionPane.showMessageDialog(null,"Se ha a�adido el area correctamente!");
						else
							JOptionPane.showMessageDialog(null,"Ya existe el �rea");
							
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
							
					dispose();
				}
	        }
			else if (e.getSource()==btnCancelar) {
				setVisible(false);
	        }
		}
	}
}