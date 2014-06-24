package Pantallas;
/**editorConsultaIntegrada: interfaz swing que permite editar texto y acceder a la función de Consulta
 * @author: Laura Asenjo, María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:sql, area, categoría, idPalabra, consulta, rasgocontenido
 */

/** Basado en el código extraido del libro
* "Java Swing, 2nd Edition"
* Marc Loy, Robert Eckstein, Dave Wood, James Elliott, Brian Cole
* Editorial: O'Reilly 
*/


import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;





import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.JMenuItem;

import ConexionBBDD.conexion;


public class editorConsultaIntegrada extends JFrame {

	private static final long serialVersionUID = 1L;

static Connection _conn;

  private Action openAction = new OpenAction();

  private Action saveAction = new SaveAction();
  
  private Action LanzarConsultaGUI = new LanzarConsultaGUI(this);

  JTextComponent textComp;

  @SuppressWarnings({ "rawtypes", "unused" })
private Hashtable actionHash = new Hashtable();



  // Create an editor.
  public editorConsultaIntegrada() {
    super("Swing Editor");
    setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/logo1.png")));
    setResizable(false);
    setLocation(400,100);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setTitle("SACTT Text Editor");
    textComp = createTextComponent();
    makeActionsPretty();

    Container content = getContentPane();
    getContentPane().setLayout(null);
    content.add(textComp);
    setJMenuBar(createMenuBar());
    setSize(624, 506);
  }

  // Create the JTextComponent subclass.
  protected JTextComponent createTextComponent() {
    JTextArea ta = new JTextArea();
    ta.setBounds(0, 0, 618, 447);
    ta.setLineWrap(true);
    return ta;
  }

  // Add icons and friendly names to actions we care about.
  protected void makeActionsPretty() {
    Action a;
    a = textComp.getActionMap().get(DefaultEditorKit.cutAction);
    a.putValue(Action.SMALL_ICON, new ImageIcon("cut.gif"));
    a.putValue(Action.NAME, "Cut");

    a = textComp.getActionMap().get(DefaultEditorKit.copyAction);
    a.putValue(Action.SMALL_ICON, new ImageIcon("copy.gif"));
    a.putValue(Action.NAME, "Copy");

    a = textComp.getActionMap().get(DefaultEditorKit.pasteAction);
    a.putValue(Action.SMALL_ICON, new ImageIcon("paste.gif"));
    a.putValue(Action.NAME, "Paste");

    a = textComp.getActionMap().get(DefaultEditorKit.selectAllAction);
    a.putValue(Action.NAME, "Select All");
  }

  // Create a simple JToolBar with some buttons.
  /*protected JToolBar createToolBar() {
  }*/

  // Create a JMenuBar with file & edit menus.
  protected JMenuBar createMenuBar() {
    JMenuBar menubar = new JMenuBar();
    JMenu file = new JMenu("Archivo");
    JMenu edit = new JMenu("Editar");
    menubar.add(file);
    menubar.add(edit);

    JMenuItem menuItem = file.add(getOpenAction());
    menuItem.setText("Abrir");
    JMenuItem menuItem_1 = file.add(getSaveAction());
    menuItem_1.setText("Guardar");
    JMenuItem menuItem_2 = file.add(new ExitAction());
    menuItem_2.setText("Salir");
    JMenuItem mntmCortarAlPortapapeles = edit.add(textComp.getActionMap().get(DefaultEditorKit.cutAction));
    mntmCortarAlPortapapeles.setText("Cortar al portapapeles");
    JMenuItem menuItem_4 = edit.add(textComp.getActionMap().get(DefaultEditorKit.copyAction));
    menuItem_4.setText("Copiar al portapapeles");
    JMenuItem menuItem_3 = edit.add(textComp.getActionMap().get(DefaultEditorKit.pasteAction));
    menuItem_3.setText("Pegar");
    JMenuItem menuItem_5 = edit.add(textComp.getActionMap().get(DefaultEditorKit.selectAllAction));
    menuItem_5.setText("Seleccionar todo");
    
    
    JMenu mnSactt = new JMenu("SACTT");
    JMenuItem menuItemSACTT = mnSactt.add(getConsultaGUI());
    menuItemSACTT.setText("Consulta");
    menubar.add(mnSactt);
    
    
    
    
    return menubar;
  }
  protected Action getConsultaGUI() {
	    return LanzarConsultaGUI;
	  }
  

  // Subclass can override to use a different open action.
  protected Action getOpenAction() {
    return openAction;
  }

  // Subclass can override to use a different save action.
  protected Action getSaveAction() {
    return saveAction;
  }

  protected JTextComponent getTextComponent() {
    return textComp;
  }

  // ********** ACTION INNER CLASSES ********** //

  // A very simple exit action
  public class ExitAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public ExitAction() {
      super("Exit");
    }

    public void actionPerformed(ActionEvent ev) {
      System.exit(0);
    }
  }

  // An action that opens an existing file
  class OpenAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public OpenAction() {
      super("Open", new ImageIcon("icons/open.gif"));
    }

    // Query user for a filename and attempt to open and read the file into
    // the
    // text component.
    public void actionPerformed(ActionEvent ev) {
      JFileChooser chooser = new JFileChooser();
      if (chooser.showOpenDialog(editorConsultaIntegrada.this) != JFileChooser.APPROVE_OPTION)
        return;
      File file = chooser.getSelectedFile();
      if (file == null)
        return;

      FileReader reader = null;
      try {
        reader = new FileReader(file);
        textComp.read(reader, null);
        
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(editorConsultaIntegrada.this,
            "Archivo no encontrado", "ERROR", JOptionPane.ERROR_MESSAGE);
      } finally {
        if (reader != null) {
          try {
            reader.close();
          } catch (IOException x) {
          }
        }
      }
    }
  }

  // An action that saves the document to a file
  class SaveAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public SaveAction() {
      super("Save", new ImageIcon("icons/save.gif"));
    }

    // Query user for a filename and attempt to open and write the text
    // component's content to the file.
    public void actionPerformed(ActionEvent ev) {
      JFileChooser chooser = new JFileChooser();
      if (chooser.showSaveDialog(editorConsultaIntegrada.this) != JFileChooser.APPROVE_OPTION)
        return;
      File file = chooser.getSelectedFile();
      if (file == null)
        return;

      FileWriter writer = null;
      try {
        writer = new FileWriter(file);
        textComp.write(writer);
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(editorConsultaIntegrada.this,
            "No se guardó el archivo", "ERROR", JOptionPane.ERROR_MESSAGE);
      } finally {
        if (writer != null) {
          try {
            writer.close();
          } catch (IOException x) {
          }
        }
      }
    }
  }
  
  class LanzarConsultaGUI extends AbstractAction {

	private static final long serialVersionUID = 1L;
	editorConsultaIntegrada editor;
	public LanzarConsultaGUI(editorConsultaIntegrada edi){editor = edi;}

	@Override
	public void actionPerformed(ActionEvent e) {
		
			conexion miconexion = new conexion();
			try {
				miconexion.iniciarBBDD();
			} catch (SQLException | ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			consultaGUI consult = null;
			try {
				consult = new consultaGUI(miconexion.getMiConexion(), editor );
			} catch (InstantiationException e1) {
				
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				
				e1.printStackTrace();
			}
			consult.frmConsulta.setVisible(true);
			
		
	}
	  
  }
  
public void appendString(String pal){
	  Document doc = textComp.getDocument();

	  try {
		  Caret crt = textComp.getCaret();
		doc.insertString(crt.getDot(), pal, null);
	} catch (BadLocationException e) {
		e.printStackTrace();
	};
  }
}
