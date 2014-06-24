package ConexionBBDD;
import java.awt.EventQueue;
/**conexion: clase encargada de crear y cerrar la conexi�n a la bbdd
 * @author: Laura Asenjo,Mar�a de Valvanera Jim�nez, Silvia Cabezas
 * @version: 1.0
 * @see:sqL
 */
import java.sql.*;


public class conexion
{
	//Atributos
	   static String bd ;
	   static String login;
	   static String password;
	   static String url;
	   static String url2;
	   static Connection conn ;
	   
	   
   /*Metodos*/
	/**
	 * Constructor sin par�metros de conexi�n
	 * @return no devuelve nada
	 */   
	public conexion(){
		 bd = "sactt";
		 login = "root";
		 password = "";
		 url2 = "jdbc:mysql://localhost/"+bd;
		 url = "jdbc:mysql://localhost/";
		 conn=null;
	}
	
	/**
	 * Funcion que devuelve la conexion
	 * @param no recibe parametros
	 * @return  devuelve la conexion actual
	 */
	public Connection getMiConexion(){
		
		return conn;
	}
	
	public static void crearBBDD(Connection miconexion) {
		try {
			conn = DriverManager.getConnection(url,login,password);
			Statement stmt = null;
	   	  	stmt = conn.createStatement();
	   	  	String sql = "CREATE DATABASE IF NOT EXISTS sactt";
	        stmt.executeUpdate(sql);
	        conn.close();
	        
	        Class.forName("com.mysql.jdbc.Driver");
	        conn = DriverManager.getConnection(url2,login,password);
	        new creartablas(conn);
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Funcion que inicia la conexion a la base de datos
	 * @param no recibe parametros
	 * @return  no devuelve nada
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
   public void iniciarBBDD() throws SQLException, ClassNotFoundException {

		//obtenemos el driver de para mysql
		Class.forName("com.mysql.jdbc.Driver");
		//obtenemos la conexi�n
		conn = DriverManager.getConnection(url,login,password);
		crearBBDD(conn);
		conn.close();
		conn =  DriverManager.getConnection(url2,login,password);
   }
   /**
	 * Funcion que cierra la conexi�n a la base de datos
	 * @param no recibe parametros
	 * @return no devuelve nada
	 */
   public void cerrarBBDD() throws SQLException{
	   if (!conn.isClosed()){
		   conn.close();
	   }
   }

   public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				 conexion miconexion;
				
				miconexion=new conexion();
				try {
					miconexion.iniciarBBDD();
					System.out.println("hola");
				
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}
}

