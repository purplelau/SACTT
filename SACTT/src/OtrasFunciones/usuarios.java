package OtrasFunciones;
/**usuarios: clase encargada de las funcionalidades de usuarios
 * @author: Laura Asenjo,María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:sql*, ArrayList
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class usuarios {
	
	/*public static void main(String[] args) {
		conexion miconexion;
		miconexion=new conexion();
		try {
			miconexion.iniciarBBDD();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		//usuarios usr = new usuarios();
		
	}*/
	
	String usuario;
	String contraseña;
	String email;
	int idUsr;
	
	/**
	 * Constructor de usuarios sin parámetros
	 * @param no recibe nada
	 * @return no devuelve nada
	 */
	public usuarios(){
	}
	
	/**
	 * Constructor de usuarios con nombre de usuario
	 * @param recibe un string con el nombre del usuario
	 * @return no devuelve nada
	 */
	public usuarios(String usr){
		usuario = usr;
	}
	
	/**
	 * Constructor de usuarios con nombre de usuario
	 * @param recibe un string con el nombre del usuario
	 * @return no devuelve nada
	 */
	public usuarios(int id){
		idUsr = id;
	}
	
	/**
	 * Constructor de usuarios con nombre de usuario y contraseña
	 * @param recibe un string con el nombre del usuario y la contraseña
	 * @return no devuelve nada
	 */
	public usuarios(String usr, String passwd){
		usuario = usr;
		contraseña = passwd;
	}
	
	/**
	 * Función que comprueba si existe o no el usuario
	 * @param recibe un string con el nombre del usuario
	 * @return devuelve un boolean que indica si existe o no el usuario
	 */
	public boolean usuarioExiste(Connection conn){
		Statement st;
		boolean encontrado = false;
		try {
			st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM usuarios WHERE '"  + usuario + "'= usuario");
			if (res.next()){
				encontrado = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encontrado;
	}
	
	/**
	 * Función que comprueba si la contraseña es válida o no para el usuario
	 * @param recibe la conexión a la base de datos
	 * @return devuelve un boolean que indica si la contraseña es válida o no lo es
	 */
	public boolean contraseñaValida(Connection conn){
		Statement st;
		boolean valida = false;
		try {
			st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM usuarios WHERE ('" +usuario + "'= usuario && '"  +contraseña +"'= clave)");
			if (res.next()){
				valida = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valida;
	}
	
	/**
	 * Función que comprueba si el usuario tiene permisos de Administrador
	 * @param recibe la conexión a la base de datos
	 * @return devuelve un boolean que indica si el usuario es administrador o no
	 */
	public boolean esAdmin(Connection conn){
		Statement st;
		boolean esadmin = false;
		try {
			st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT permisos FROM usuarios WHERE ('" +usuario + "'= usuario)");
			if (res.next()){
				//Si el user tiene permisos 2 significa que es Admin
				if  (res.getInt(1) == 2){esadmin = true;}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return esadmin;
	}
	
	/**
	 * Función que comprueba si el usuario tiene permisos de Súper-administrador
	 * @param recibe la conexión a la base de datos
	 * @return devuelve un boolean que indica si el usuario es súper-administrador o no
	 */
	public boolean esSuperAdmin(Connection conn){
		Statement st;
		boolean esSadmin = false;
		try {
			st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT permisos FROM usuarios WHERE ('" +usuario + "'= usuario)");
			if (res.next()){
				//Si el user tiene permisos 2 significa que es Admin
				if  (res.getInt(1) == 1){esSadmin = true;}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return esSadmin;
	}
	
	/**
	 * Función que introduce un nuevo usuario en la base de datos con email
	 * @param recibe la conexión a la base de datos, y el nombre, la contraseña, el email y los permisos del usuario
	 * @return devuelve un boolean que indica si se creó correctamente
	 */
	public boolean crearUser(Connection conn, String usr, String passwd, String email, int permisos){
		Statement st;
		boolean ok = false;
		try {
			st = conn.createStatement();
			this.usuario = usr;
			this.contraseña = passwd;
			//Si no existe el usuario, se crea y se devuelve true. Si existe, se devuelve false.
			if (!usuarioExiste(conn)){
				//String prueba = "INSERT INTO `usuarios`(`usuario`,`correo`,`clave`,`permisos`) VALUES ('" + usr + "'," + "'"+ email +"','"+ email +"','"+ passwd +"','" + permisos + "')";
				st.executeUpdate("INSERT INTO `usuarios`(`usuario`,`correo`,`clave`,`permisos`) VALUES ('" + usr + "'," + "'"+ email +"','"+ passwd +"','" + permisos + "')");
				ok=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok;
	}
	
	/**
	 * Función que introduce un nuevo usuario en la base de datos sin email
	 * @param recibe la conexión a la base de datos, y el nombre, la contraseña, y los permisos del usuario
	 * @return devuelve un boolean que indica si se creó correctamente
	 */
	public boolean crearUser(Connection conn, String usr, String passwd, int permisos){
		Statement st;
		boolean ok = false;
		try {
			st = conn.createStatement();
			//Si no existe el usuario, se crea y se devuelve true. Si existe, se devuelve false.
			if (!usuarioExiste(conn)){
				st.executeUpdate("INSERT INTO `usuarios`(`usuario`,`correo`,`clave`,`permisos`) VALUES ('" + usr + "'," + "'"+ email +"','"+ passwd +"','" + permisos + "')");
				ok=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok;
	}
	
	/**
	 * Función que elimina un usuario de la base de datos
	 * @param recibe la conexión a la base de datos, y el nombre del usuario
	 * @return devuelve un boolean que indica si se eliminó correctamente
	 */
	public boolean eliminarUser(Connection conn, String usr){
		Statement st;
		boolean ok = false;
		try {
			st = conn.createStatement();
			//Si existe el usuario, se elimina y se devuelve true. Si no existe, se devuelve false.
			if (usuarioExiste(conn)){
				st.executeUpdate("DELETE FROM `usuarios` WHERE usuario='" + usr + "'");
				ok=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ok;
	}
	
	/**
	 * Función que devuelve una lista con todos los usuarios registrados en el sistema
	 * @param recibe la conexión a la base de datos
	 * @return devuelve una lista con todos los usuarios
	 */
	public ArrayList<String> allUsers(Connection conn){
		ArrayList<String> ret = new ArrayList<String>();
		Statement st;
		ResultSet res;
		try {
			st = conn.createStatement();
			res = st.executeQuery("SELECT * FROM usuarios");
			while (res.next()){
				ret.add(res.getString(2));
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * Función que devuelve una lista con todos los usuarios que no tienen permisos de Administrador 
	 * o Súper-administrador.
	 * @param recibe la conexión a la base de datos
	 * @return devuelve una lista con todos los usuarios que no son administrado o súper-administrador
	 */
	public ArrayList<String> basicUsers(Connection conn){
		ArrayList<String> ret = new ArrayList<String>();
		Statement st;
		ResultSet res;
		try {
			st = conn.createStatement();
			res = st.executeQuery("SELECT * FROM usuarios WHERE permisos = 3");
			while (res.next()){
				ret.add(res.getString(2));
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * Función que edita un usuario exitente en la base de datos
	 * @param recibe la conexión a la base de datos, el nombre actual, y los nuevos nombre, contraseña, correo
	 * y permisos
	 * @return no devuelve nada
	 */
	public void editarUser(Connection conn, String user, String nNombre,String passwd, String correo, int perm ){
		Statement st;
		try {
			st = conn.createStatement();
			st.executeUpdate("UPDATE `usuarios` SET usuario='" + nNombre + "', correo = '"+ correo +"', "
					+ "clave ='"+ passwd +"', permisos ='" + perm + "' WHERE usuario='" + user +"'");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Función que devuelve el email del usuario
	 * @param recibe la conexión a la base de datos
	 * @return devuelve un string con el email del usuario
	 */
	public String getEmail(Connection conn){
		Statement st;
		String correo = null;
		try {
			st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT correo FROM usuarios WHERE ('" +usuario + "'= usuario)");
			if (res.next()){
				correo = res.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return correo;
	}
	
	/**
	 * Función que devuelve el identificador de permisos
	 * @param recibe la conexión a la base de datos
	 * @return devuelve un int que indica el nivel de los permisos del usuario 
	 * (1: Súper-administrador; 2: Administrador; 3:Usuario)
	 */
	public int getPermiso(Connection conn){
		Statement st;
		int permisos = 0;
		try {
			st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT permisos FROM usuarios WHERE ('" +usuario + "'= usuario)");
			if (res.next()){
				permisos = res.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return permisos;
	}
	
	/**
	 * Función que devuelve la clave del usuario
	 * @param recibe la conexión a la base de datos
	 * @return devuelve un string con la clave del usuario
	 */
	public String getClave(Connection conn){
		Statement st;
		String clave = null;
		try {
			st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT clave FROM usuarios WHERE ('" +usuario + "'= usuario)");
			if (res.next()){
				clave = res.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clave;
	}
	
	/**
	 * Función que devuelve el identificador del usuario. Además, actualiza el atributo usuario con el identificador del usuario.
	 * @param recibe la conexión a la base de datos
	 * @return devuelve un int con el identificador del usuario
	 */
	public int getidUser(Connection conn){
		Statement st;
		int id = 0;
		try {
			st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT idUsuario FROM usuarios WHERE ('" +usuario + "'= usuario)");
			if (res.next()){
				id = res.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.idUsr = id;
		return id;
	}
	
	/**
	 * Función que devuelve el nombre de usuario a partir de su identificador. Además, actualiza el atributo usuario con 
	 * el nombre del usuario.
	 * @param recibe la conexión a la base de datos
	 * @return devuelve un string con el nombre de usuario
	 */
	public String getUserfromId(Connection conn){
		Statement st;
		String usr = null;
		try {
			st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT usuario FROM usuarios WHERE ('" +idUsr + "'= idUsuario)");
			if (res.next()){
				usr = res.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		usuario = usr;
		return usr;
	}
}


