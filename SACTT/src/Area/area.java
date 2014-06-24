package Area;
/**Area: clase encargada de crear, buscar, eliminar y actualizar un área
 * @author: Laura Asenjo,María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:sql, ArrayList, idPalabra 
 */
import java.sql.*;
import java.util.ArrayList;
import IdPalabra.idPalabra;

public class area {
	//Atributos
	String _area;

	/*Metodos*/
	/**
	 * Constructor sin parametros de area
	 * @return no devuelve nada
	 */
	public area(){
		_area="";
	}
	/**
	 * Constructor con parametros de area
	 * @param recibe el area
	 * @return no devuelve nada
	 */
	public area(String area){_area=area;}
	
	/**
	 * Funcion que actualiza el atributo area
	 * @param recibe el area
	 * @return no devuelve nada
	 */
	public void setArea(String area){
		_area=area;
	}
	/**
	 * Funcion que devuelve el area actual
	 * @param no recibe parametros
	 * @return  devuelve el área
	 */
	public String getArea(){return _area;}

	/**
	 * Función que añade el area a la BBDD
	 * @param recibe  la conexión a la base de datos
	 * @return devuelve true si se ha insertado el area en la bbdd, false en caso contrario
	 */
	public boolean añadirArea(Connection conn) throws SQLException{
		boolean añadir=false;
		if(consultarUnAreaNombre(conn)==-1){
			try{
				Statement st = conn.createStatement();
				st.executeUpdate("INSERT INTO area (nombreArea) VALUES ('" + _area + "')");
				añadir=true;
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		
		return añadir;
		
	}
	
	/**
	 * Función que elimina un area de la BBDD
	 * @param recibe  la conexión a la base de datos
	 * @return devuelve true si se ha eliminado el area de la bbdd, false en caso contrario
	 */
	public boolean eliminarArea(Connection conn) throws SQLException{
		boolean eliminado = false;
		if(consultarUnAreaNombre(conn)!=-1){
			try{
				Statement st = conn.createStatement();
				st.executeUpdate("DELETE FROM area " + " WHERE nombreArea =\"" + _area + "\"");
				_area="";
				eliminado = true;
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		
		return eliminado;

	}

	/**
	 * Función que devuelve todas las areas
	 * @param recibe  la conexión a la base de datos
	 * @return devuelve el ArrayList con todas las areas de la base de datos
	 */
	public static ArrayList<idPalabra> devolverAreas(Connection conn){
		ArrayList<idPalabra> misAreas=new ArrayList<idPalabra>();
		try{
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM area");
			while ( res.next() ) {
				String palabra = res.getString("nombreArea");
				String id = res.getString("idArea");
				misAreas.add(new idPalabra(Integer.parseInt(id),palabra));
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		return misAreas;
	}

	/**
	 * Función que devuelve el identificador de un area concreta
	 * @param recibe  la conexión a la base de datos
	 * @return devuelve el entero correspondiente al identificador del area
	 */
	public int consultarUnArea(Connection conn) throws SQLException{
		int existe=-1;
		Statement st = conn.createStatement(); 
		ResultSet rs;
		try{
			rs = st.executeQuery("SELECT idArea FROM area WHERE nombreArea='"+_area+"'");
			if(rs.next())
				existe=rs.getInt(1);
		} catch (Exception e){

			e.printStackTrace();
		}
		return existe;
	}
	
	/**
	 * Función que consulta un area por nombre
	 * @param recibe  la conexión a la base de datos
	 * @return devuelve el entero correspondiente al identificador del area
	 */
	public int consultarUnAreaNombre(Connection conn) throws SQLException{
		int existe=-1;
		Statement st = conn.createStatement(); 
		ResultSet rs;
		try{
			rs = st.executeQuery("SELECT * FROM area WHERE nombreArea='"+_area+"'");
			if(rs.next()){
				existe=rs.getInt(1);
			}
			

		} catch (Exception e){
			e.printStackTrace();
		}
		return existe;
	}
	
	/**
	 * Función que modifica un area por nombre
	 * @param recibe  la conexión a la base de datos y el nuevo area.
	 * @return devuelve true si se ha modificado correctamente, false en caso contrario
	 */
	public boolean modificarAreaNombre(String nuevoArea, Connection conn) throws SQLException{
		boolean modificar=false;
		if(consultarUnAreaNombre(conn)!=-1){
			try{
				Statement st = conn.createStatement();
				st.executeUpdate("UPDATE area SET nombreArea='"+nuevoArea+"' WHERE nombreArea ='"+_area+"'" );
				modificar=true;
				
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		return modificar;
		
	}
	

	
	/**
	 * Función que consulta si existe una o varias areas que contenga/n esa palabra
	 * @param recibe  la conexión a la base de datos y el nombre de la palbra
	 * @return devuelve array con el nombre de las areas si las encuentra
	 */
	public  ArrayList<String> consultarUnAreaCiega(Connection conn,String miarea) throws SQLException{
		ArrayList<String> misAreas=new ArrayList<String>();
		Statement st = conn.createStatement(); 
		ResultSet rs;
		try{
			
			rs=st.executeQuery("SELECT nombreArea FROM area WHERE nombreArea LIKE '%"+miarea+"%'");		
			
			while ( rs.next() ) {   	            
				misAreas.add(rs.getString(1));
	          
	        }
			
		} catch (Exception e){

			e.printStackTrace();
		}
		return misAreas;
	}
	

}
