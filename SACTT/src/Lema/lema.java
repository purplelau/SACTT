package Lema;
/**lema: clase encargada de crear, buscar, eliminar y actualizar un lema
 * @author: Laura Asenjo,María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:sql, ArrayList, idPalabra 
 */
import java.sql.*;
import java.util.ArrayList;
import IdPalabra.idPalabra;

public class lema {
	//Atributos
	String _lema;
	
	/*Metodos*/
	/**
	 * Constructor sin parametros de lema
	 * @return no devuelve nada
	 */
	public lema(){_lema="";}
	/**
	 * Constructor con parametros de lema
	 * @param recibe el lema
	 * @return no devuelve nada
	 */
	public lema(String lema){_lema=lema;}
	
	/**
	 * Funcion que actualiza el atributo lema
	 * @param recibe el lema
	 * @return no devuelve nada
	 */
	public void setLema(String Lema){_lema=Lema;}
	/**
	 * Funcion que devuelve el lema actual
	 * @param no recibe parametros
	 * @return  devuelve el lema
	 */
	public String getLema(){return _lema;}
	
	/**
	 * Función que añade el lema a la BBDD
	 * @param recibe  la conexión a la base de datos
	 * @return devuelve true si se ha insertado el lema en la bbdd, false en caso contrario
	 */
	public boolean añadirLema( Connection conn){
		boolean añadir=false;
		if(consultarUnLemaNombre(conn)==-1){
			try { 
	          
	            Statement st = conn.createStatement(); 
	            st.executeUpdate("INSERT INTO lema (lemaPalabra) " + 
	                "VALUES ('"+_lema+"')"); 
	            añadir=true;
	           
	            
	            
	        } catch (Exception e) { 
	            
	        	e.printStackTrace(); 
	        } 	
		}
		
		return añadir;
	}
	
	/**
	 * Función que elimina un lema de la BBDD
	 * @param recibe  la conexión a la base de datos
	 * @return devuelve true si se ha eliminado el lema de la bbdd, false en caso contrario
	 */
	public boolean eliminarLema( Connection conn) throws SQLException{
		boolean eliminado = false;
		if(consultarUnLema(conn)!=-1){
			try { 
				Statement st = conn.createStatement(); 
				st.executeUpdate( "DELETE FROM lema " +
						"WHERE lemaPalabra =\""+_lema+"\"");

				
				eliminado = true;

			} catch (Exception e) { 
				
				e.printStackTrace();
			} 	
		}
		
		return eliminado;
	}
	
	/**
	 * Función que devuelve todas los lemas que hay en la bbdd
	 * @param recibe  la conexión a la base de datos
	 * @return devuelve el ArrayList con todas los lemas de la base de datos
	 */
	public  ArrayList<idPalabra> devolverLemas(Connection conn){
		ArrayList<idPalabra> misAreas=new ArrayList<idPalabra>();
		try{
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM lema");
			while ( res.next() ) {
				String palabra = res.getString("lemaPalabra");
				int id = res.getInt("idLema");
				misAreas.add(new idPalabra(id,palabra));
				
			}
		} catch (Exception e){
			e.printStackTrace();
		}

		return misAreas;
	}
	
	
	
	/**
	 * Función que devuelve el nombre de un lema concreto
	 * @param recibe  la conexión a la base de datos y el identificador del elma
	 * @return devuelve el string correspondiente al nombre del lema
	 */
	public String consultarUnLemaPorId(int id, Connection conn) {
		String miLema="";
		try { 
			Statement st = conn.createStatement(); 
			ResultSet rs;
			
			rs = st.executeQuery("SELECT lemaPalabra FROM lema WHERE idLema='"+id+"'");
			if(rs.next())
				miLema=rs.getString(1);
		}

		catch (Exception e) { 
		
			e.printStackTrace();
		} 	
        return miLema;
	}
	/**
	 * Función que devuelve el identificador de un lema
	 * @param recibe  la conexión a la base de datos
	 * @return devuelve el entero correspondiente al identificador del lema
	 */
	public int consultarUnLema( Connection conn) throws SQLException{
		int existe=-1;
		Statement st = conn.createStatement(); 
		ResultSet rs;
		
		rs = st.executeQuery("SELECT idLema FROM lema WHERE lemaPalabra='"+_lema+"'");
		if(rs.next())
			existe=rs.getInt(1);
        return existe;
	}
	
	/**
	 * Función que devuelve el identificador de un lema
	 * @param recibe  la conexión a la base de datos
	 * @return devuelve el entero correspondiente al identificador del lema
	 */
	public int consultarUnLemaNombre(Connection conn){
		int existe=-1;
		
		try {
			Statement st;
			st = conn.createStatement();
			ResultSet rs;			
			rs = st.executeQuery("SELECT * FROM lema WHERE lemaPalabra='"+_lema+"'");
			if(rs.next())
				existe=rs.getInt(1);
		} catch (SQLException e) {
		
			e.printStackTrace();
		} 

		
		return existe;
	}
	
	/**
	 * Función que modifica el nombre de un lema
	 * @param recibe  la conexión a la base de datos y el nombre del nuevo lema
	 * @return devuelve true si se ha modificado correctamente, false en caso contrario
	 */
	public boolean modificarLemaNombre(String nuevoLema, Connection conn) throws SQLException{
		boolean modificar=false;
		if(consultarUnLemaNombre(conn)!=-1){
			try{
				Statement st = conn.createStatement();
				st.executeUpdate("UPDATE lema SET lemaPalabra='"+nuevoLema+"' WHERE lemaPalabra ='"+_lema+"'" );
				modificar=true;
				
			} catch (Exception e){
				e.printStackTrace();
				
			}
		}
		
		
		return modificar;
		
	}
	


	/**
	 * Función que consulta si existe un rasgo de contenido que contenga esa palabra
	 * @param recibe  la conexión a la base de datos y el nombre de la categoría
	 * @return devuelve array con el nombre del rasgo de contenido  si los encuentra
	 */
	public  ArrayList<String> consultarUnLemaCiega(Connection conn,String milema) throws SQLException{
		ArrayList<String> misLemas=new ArrayList<String>();
		Statement st = conn.createStatement(); 
		ResultSet rs;
		try{
			
			rs=st.executeQuery("SELECT lemaPalabra FROM lema WHERE lemaPalabra LIKE '%"+milema+"%'");	
			
			while ( rs.next() ) {   	            
				misLemas.add(rs.getString(1));
	          
	        }
			
		} catch (Exception e){
			e.printStackTrace();
			
		}
		return misLemas;
	}
}
