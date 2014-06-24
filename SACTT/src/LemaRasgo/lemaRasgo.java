package LemaRasgo;
/**lemaRasgo: clase encargada de asociar, eliminar, consultar, modificar un Lema con Rasgos de Contenido
 * @author: Laura Asenjo,María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:io.IOException,sql*, ArrayList, lema, rasgocontenido
 */
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import Lema.lema;
import RasgoContenido.rasgocontenido;

public class lemaRasgo {
	//Atributos
	lema _lema;
	rasgocontenido _rasgo;
	
	
	/*Metodos*/
	/**
	 * Constructor sin parametros de lemaRasgo
	 * @return no devuelve nada
	 */
	public lemaRasgo(){
		_lema=new lema();
		_rasgo=new rasgocontenido();
	}
	/*Metodos*/
	/**
	 * Constructor con parametros de lemaRasgo
	 *  @param recibe el lema y el rasgo de contenido
	 * @return no devuelve nada
	 */
	public lemaRasgo (lema milema,  rasgocontenido mirasgo){
		
		_lema=milema;
		_rasgo=mirasgo;
	}
	
	/**
	 * Funcion que actualiza el atributo rasgo
	 * @param recibe el rasgo
	 * @return no devuelve nada
	 */
	public void setRasgo(rasgocontenido rasgo){
		_rasgo=rasgo;
	}
	/**
	 * Funcion que actualiza el atributo lema
	 * @param recibe el lema
	 * @return no devuelve nada
	 */
	public void setLema(lema lem){
		_lema=lem;
		
	}
	
	/**
	 * Función que asocia el lema a un Rasgo a la BBDD
	 * @param recibe  la conexión a la base de datos, el nombre del lema y el del rasgo
	 * @return devuelve true si se ha insertado el lema en la bbdd, false en caso contrario
	 */
	public boolean añadirLemaRasgo(Connection conn,String lema, String rasgo){
		boolean añadir=false;
		try{
			lema milema=new lema(lema);
			int idLema=milema.consultarUnLema(conn);
			rasgocontenido mirasgo =new rasgocontenido(rasgo);
			int idRasgo =mirasgo.consultarUnRasgo(conn);
	
			
			Statement st = conn.createStatement();	
			st.executeUpdate("INSERT INTO lemarasgo (idLema, idRasgoContenido) VALUES ('" + idLema + "'" +"," +"'" + idRasgo + "')");
			añadir=true;
			
		} catch (Exception e){
			e.printStackTrace(); 
	    }
		return añadir;
	}
	/**
	 * Función que elimina la relación entre el lema y el rasgo
	 * @param recibe  la conexión a la base de datos, el identificador del rasgo y el identificador del lema
	 * @return devuelve true si se ha insertado el lema en la bbdd, false en caso contrario
	 */
	public  boolean eliminarLemaRasgo(int idRasgo, int idLema,Connection conn) throws IOException, SQLException{

		boolean eliminado = false;
		if ((idRasgo != -1)&&(idLema != -1)){
			try{
				Statement st = conn.createStatement();
				st.executeUpdate("DELETE FROM lemarasgo " + " WHERE (idLema, idRasgoContenido )=" + "('" + idLema + "'" +"," +"'" + idRasgo + "')");
				
				eliminado = true;
		    } catch (Exception e){
		    	e.printStackTrace(); 
		    }
		}
	
		
		return eliminado;
	}
	/**
	 * Función que devuelve en un Array los identificadores de los rasgos asociados a un lema
	 * @param recibe  la conexión a la base de datos y el identificador del lema
	 * @return devuelve el Array con los identificadores de los rasgos
	 */
	public  ArrayList<Integer> devolverRasgosDeLema(int idLema,Connection conn) throws SQLException{
		int existe=-1;
		Statement st = conn.createStatement(); 
		ResultSet rs;
		ArrayList<Integer> rasgosLema=new ArrayList<Integer>();
		try { 
			rs = st.executeQuery("SELECT idRasgoContenido FROM lemarasgo WHERE idLema='"+idLema+"'");
			while(rs.next()){
				existe=rs.getInt(1);
				rasgosLema.add(existe);
				
			}

		} catch (Exception e) { 
			e.printStackTrace(); 
		}
		return rasgosLema; 		
	}
	
	/**
	 * Función que devuelve los identificadores de lemas asociados a un rasgo
	 * @param recibe  la conexión a la base de datos y el identificador del rasgo
	 * @return devuelve el Array con los identificadores de los lemas
	 */
	public  ArrayList<Integer> devolverLemasDeRasgo(int idRasgo,Connection conn) throws SQLException{
		int existe=-1;
		Statement st = conn.createStatement(); 
		ResultSet rs;
		ArrayList<Integer> lemasRasgo=new ArrayList<Integer>();
		try { 
			rs = st.executeQuery("SELECT idLema FROM lemarasgo WHERE idRasgoContenido='"+idRasgo+"'");
			while(rs.next()){
				existe=rs.getInt(1);
				lemasRasgo.add(existe);
				
			}

		} catch (Exception e) { 
			e.printStackTrace(); 
		}
		return lemasRasgo; 		
	}
	
	

}