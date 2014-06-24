package RasgoContenido;
/**RasgoContenido: clase encargada de a�adir, eliminar, consultar, modificar un Rasgo de Contenido
 * @author: Laura Asenjo,Mar�a de Valvanera Jim�nez, Silvia Cabezas
 * @version: 1.0
 * @see:sql*, ArrayList,idPalabra
 */

import java.sql.*;
import java.util.ArrayList;
import IdPalabra.idPalabra;

public class rasgocontenido {
	//Atributos
	String _rasgo;

	/*Metodos*/
	/**
	 * Constructor sin parametros de rasgo de contenido
	 * @return no devuelve nada
	 */
	public rasgocontenido(){_rasgo="";}
	
	/**
	 * Constructor con parametros de rasgo de contenido
	 * @param recibe el rasgo de contenido
	 * @return no devuelve nada
	 */
	public rasgocontenido(String rasgo){_rasgo=rasgo;}
	
	/**
	 * Funcion que actualiza el atributo rasgo
	 * @param recibe el rasgo
	 * @return no devuelve nada
	 */
	public void setRasgo(String rasgo){_rasgo=rasgo;}
	
	/**
	 * Funcion que devuelve el rasgo de contenido actual
	 * @param no recibe parametros
	 * @return  devuelve el rasgo de contenido
	 */
	public String getRasgo(){return _rasgo;}
	
	/**
	 * Funcion que a�ade un rasgo de contenido a la base de datos
	 * @param recibe la conexion actual
	 * @return  devuelve true si se ha a�adido correctamente, false en caso contrario
	 */
	public boolean a�adirRasgo( Connection conn){
		boolean a�adir=false;
		if(consultarUnRasgo(conn)==-1){
			try { 
	           
	            Statement st = conn.createStatement(); 
	            st.executeUpdate("INSERT INTO rasgocontenido (RasgoContenido) " + 
	                "VALUES ('"+_rasgo+"')"); 
	            a�adir=true;
	           
	            
	            	            
	        } catch (Exception e) { 
	            e.printStackTrace();
	        } 		
		}
		
		
		return a�adir;
	}
	
	/**
	 * Funcion que elimina un rasgo de contenido de la base de datos
	 * @param recibe la conexion actual
	* @return  devuelve true si se ha a�adido correctamente, false en caso contrario
	 */
	public boolean eliminarRasgo(Connection conn) throws SQLException{
		boolean eliminado = false;
		if(consultarUnRasgo(conn)!=-1){
			try { 
	            Statement st = conn.createStatement(); 
	            st.executeUpdate( "DELETE FROM rasgocontenido " +
	                    "WHERE RasgoContenido =\""+_rasgo+"\"");
	            _rasgo="";
	           
	            eliminado = true;
	        } catch (Exception e) { 
	            e.printStackTrace();
	        } 
		}
		
		return eliminado;
	}

	/**
	 * Funci�n que devuelve todas los rasgos de contenido que hay en la bbdd
	 * @param recibe  la conexi�n a la base de datos
	 * @return devuelve el ArrayList con todas los rasgos de contenido de la base de datos
	 */
	public ArrayList<idPalabra> consultarTodosRasgos(Connection conn) {
		ArrayList<idPalabra> misRasgos=new ArrayList<idPalabra>();
		try { 
			Statement st = conn.createStatement(); 
			ResultSet rs;
			rs = st.executeQuery("SELECT * FROM rasgocontenido");
	        while ( rs.next() ) {
	            String palabra = rs.getString("RasgoContenido");
	            String id = rs.getString("idRasgoContenido");
	            misRasgos.add(new idPalabra(Integer.parseInt(id),palabra));
	            
	        }
		} catch (Exception e) { 
         e.printStackTrace();
      } 
        return misRasgos;
	}
	
	/**
	 * Funci�n que consulta el identificador de un rasgo de contenido
	 * @param recibe  la conexi�n a la base de datos
	 * @return devuelve si existe,el identificador del rasgo de contenido, -1 en caso contrario
	 */
	public int consultarUnRasgo(Connection conn){
		int existe=-1;
		try { 
			Statement st = conn.createStatement(); 
			ResultSet rs;
			
			rs = st.executeQuery("SELECT idRasgoContenido FROM rasgocontenido WHERE RasgoContenido='"+_rasgo+"'");
			if(rs.next())
				existe=rs.getInt(1);
		} catch (Exception e) { 
	         e.printStackTrace();
	      } 
      return existe;
	}

	
	public ArrayList<String> consultaRasgosCat(Connection conn, int cat){
		Statement st;
        ArrayList<String> misRasgos = new ArrayList<String>();
		try {
			st = conn.createStatement();
			ResultSet rs;
			rs = st.executeQuery("SELECT RasgoContenido FROM (SELECT DISTINCT lemarasgo.idRasgocontenido " +
						"FROM lemarasgo, (SELECT idLema FROM categoriaslemas WHERE idCategoria ='" + cat +"') AS sub2 "+
						"WHERE lemarasgo.idLema = sub2.idLema)AS sub1, rasgocontenido "+
						"WHERE rasgocontenido.idRasgoContenido = sub1.idRasgocontenido");
			
			while ( rs.next() ) {
		            String palabra = rs.getString(1);
					misRasgos.add(palabra);
		       }
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return misRasgos;
	}
	/**
	 * Funci�n que devuelve el nombre de un rasgo de contenido
	 * @param recibe  la conexi�n a la base de datos y el identificador del rasgo
	 * @return devuelve si existe,el nombre del rasgo de contenido, blanco en caso contrario
	 */
	public String consultarRasgoPorId(int idRasgo,Connection conn) throws SQLException{
		
		String existe="";
		Statement st = conn.createStatement(); 
		ResultSet rs;
		
		rs = st.executeQuery("SELECT RasgoContenido FROM rasgocontenido WHERE idRasgoContenido='"+idRasgo+"'");
		if(rs.next())
			existe=rs.getString(1);
      return existe;
	}
	/**
	 * Funci�n que modifica el nombre de un rasgo de contenido
	 * @param recibe  la conexi�n a la base de datos y el nombre del rasgo
	 * @return devuelve true si se ha modificado correctamente, false en caso contrario
	 */
	public boolean modificarRasgoNombre(String nuevoRasgo, Connection conn) throws SQLException{
		boolean modificar=false;
		if(consultarUnRasgo(conn)!=-1){
			try{
				Statement st = conn.createStatement();
				st.executeUpdate("UPDATE rasgocontenido SET RasgoContenido='"+nuevoRasgo+"' WHERE RasgoContenido ='"+_rasgo+"'" );
				modificar=true;
				
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		
		return modificar;
		
	}
	
	/**
	 * Funci�n que consulta si existe un rasgo de contenido que contenga esa palabra
	 * @param recibe  la conexi�n a la base de datos y el nombre de la categor�a
	 * @return devuelve array con el nombre del rasgo de contenido  si los encuentra
	 */
	public  ArrayList<String> consultarUnRasgoCiega(Connection conn,String mirasgo) throws SQLException{
		ArrayList<String> misRasgos=new ArrayList<String>();
		Statement st = conn.createStatement(); 
		ResultSet rs;
		try{
			
			rs=st.executeQuery("SELECT RasgoContenido FROM rasgocontenido WHERE RasgoContenido LIKE '%"+mirasgo+"%'");
			
			while ( rs.next() ) {   	            
				misRasgos.add(rs.getString(1));
	          
	        }
			
		} catch (Exception e){

			e.printStackTrace();
		}
		return misRasgos;
	}
	
}
