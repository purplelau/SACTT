package OtrasFunciones;
/**usuarios: clase encargada de las funcionalidades de usuarios
 * @author: Laura Asenjo,Mar�a de Valvanera Jim�nez, Silvia Cabezas
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
	String contrase�a;
	String email;
	int idUsr;
	
	/**
	 * Constructor de usuarios sin par�metros
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
	 * Constructor de usuarios con nombre de usuario y contrase�a
	 * @param recibe un string con el nombre del usuario y la contrase�a
	 * @return no devuelve nada
	 */
	public usuarios(String usr, String passwd){
		usuario = usr;
		contrase�a = passwd;
	}
	
	/**
	 * Funci�n que comprueba si existe o no el usuario
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
	 * Funci�n que comprueba si la contrase�a es v�lida o no para el usuario
	 * @param recibe la conexi�n a la base de datos
	 * @return devuelve un boolean que indica si la contrase�a es v�lida o no lo es
	 */
	public boolean contrase�aValida(Connection conn){
		Statement st;
		boolean valida = false;
		try {
			st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM usuarios WHERE ('" +usuario + "'= usuario && '"  +contrase�a +"'= clave)");
			if (res.next()){
				valida = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valida;
	}
	
	/**
	 * Funci�n que comprueba si el usuario tiene permisos de Administrador
	 * @param recibe la conexi�n a la base de datos
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
	 * Funci�n que comprueba si el usuario tiene permisos de S�per-administrador
	 * @param recibe la conexi�n a la base de datos
	 * @return devuelve un boolean que indica si el usuario es s�per-administrador o no
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
	 * Funci�n que introduce un nuevo usuario en la base de datos con email
	 * @param recibe la conexi�n a la base de datos, y el nombre, la contrase�a, el email y los permisos del usuario
	 * @return devuelve un boolean que indica si se cre� correctamente
	 */
	public boolean crearUser(Connection conn, String usr, String passwd, String email, int permisos){
		Statement st;
		boolean ok = false;
		try {
			st = conn.createStatement();
			this.usuario = usr;
			this.contrase�a = passwd;
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
	 * Funci�n que introduce un nuevo usuario en la base de datos sin email
	 * @param recibe la conexi�n a la base de datos, y el nombre, la contrase�a, y los permisos del usuario
	 * @return devuelve un boolean que indica si se cre� correctamente
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
	 * Funci�n que elimina un usuario de la base de datos
	 * @param recibe la conexi�n a la base de datos, y el nombre del usuario
	 * @return devuelve un boolean que indica si se elimin� correctamente
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
	 * Funci�n que devuelve una lista con todos los usuarios registrados en el sistema
	 * @param recibe la conexi�n a la base de datos
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
	 * Funci�n que devuelve una lista con todos los usuarios que no tienen permisos de Administrador 
	 * o S�per-administrador.
	 * @param recibe la conexi�n a la base de datos
	 * @return devuelve una lista con todos los usuarios que no son administrado o s�per-administrador
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
	 * Funci�n que edita un usuario exitente en la base de datos
	 * @param recibe la conexi�n a la base de datos, el nombre actual, y los nuevos nombre, contrase�a, correo
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
	 * Funci�n que devuelve el email del usuario
	 * @param recibe la conexi�n a la base de datos
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
	 * Funci�n que devuelve el identificador de permisos
	 * @param recibe la conexi�n a la base de datos
	 * @return devuelve un int que indica el nivel de los permisos del usuario 
	 * (1: S�per-administrador; 2: Administrador; 3:Usuario)
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
	 * Funci�n que devuelve la clave del usuario
	 * @param recibe la conexi�n a la base de datos
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
	 * Funci�n que devuelve el identificador del usuario. Adem�s, actualiza el atributo usuario con el identificador del usuario.
	 * @param recibe la conexi�n a la base de datos
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
	 * Funci�n que devuelve el nombre de usuario a partir de su identificador. Adem�s, actualiza el atributo usuario con 
	 * el nombre del usuario.
	 * @param recibe la conexi�n a la base de datos
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


