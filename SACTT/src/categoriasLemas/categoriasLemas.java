package categoriasLemas;
/**categoriasLemas: clase encargada de crear, buscar, eliminar, actualizar la tabla categoriasLemas
 * @author: Laura Asenjo,Mar�a de Valvanera Jim�nez, Silvia Cabezas
 * @version: 1.0
 * @see:io.IOException,sql, ArrayList ,categoria,lema
 *  */
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import Categoria.categoria;
import Lema.lema;


public class categoriasLemas {
	//Atributos
	categoria _categoria;
	lema _lema;

	/*Metodos*/
	/**
	 * Constructor sin parametros de categoriasLemas
	 * @return no devuelve nada
	 */
	public categoriasLemas(){
		_categoria=new categoria();
		_lema=new lema();
	}
	
	/**
	 * Funcion que actualiza el atributo categor�a
	 * @param recibe la categor�a
	 * @return no devuelve nada
	 */
	public void setCategoria(categoria categ){
		_categoria=categ;
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
	 * Funci�n que asociad un lema a una categor�a en la BBDD
	 * @param recibe  la conexi�n a la base de datos, el nombre de la categor�a y el nombre del lema
	 * @return devuelve true si se ha insertado la categor�a junto con el lema en la bbdd, false en caso contrario
	 */
	public boolean a�adirLemaCategoria(Connection conn,String categoria, String lema){
		boolean a�adir=false;
		
		try{
			
			lema milema=new lema(lema);
			int idLema=milema.consultarUnLema(conn);
			categoria micategoria =new categoria(categoria);
			int idCategoria =micategoria.consultarUnaCategoria(conn);
	

				Statement st = conn.createStatement();	
				st.executeUpdate("INSERT INTO categoriaslemas (idCategoria, idLema) VALUES ('" + idCategoria + "'" +"," +"'" + idLema + "')");
				
				a�adir=true;
		
		} catch (Exception e){
			e.printStackTrace();
		}
		return a�adir;
	}

	
	//FUNCI�N STATIC, HABR�A QUE CAMBIARLO
	/**
	 * Funci�n que elimina la relaci�n entre un lema y una categor�a
	 * @param recibe  la conexi�n a la base de datos, el identificador de la categor�a y el identificador del lema
	 * @return devuelve true si se ha roto la relaci�n entre el lema y la categor�a en la bbdd, false en caso contrario
	 */
	public static boolean eliminarLemaCategoria(int idCat, int idLema, Connection conn) throws IOException, SQLException{
		boolean eliminado = false;
		
		if ((idCat != -1)&&(idLema != -1)){
			try{
				Statement st = conn.createStatement();	
				st.executeUpdate("DELETE FROM categoriaslemas " + " WHERE (idCategoria, idLema )=" + "('" + idCat + "'" +"," +"'" + idLema + "')");
				
				eliminado = true;
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		
		return eliminado;
	}


	
	/**
	 * Funci�n que dada una categor�a te devuelve todos los lemas asociados a la misma
	 * @param recibe  la conexi�n a la base de datos y el identificador de la categor�a
	 * @return devuelve el array con los lemas asociados a dicha categor�a
	 */
	public ArrayList<Integer> consultarLemasdeunaCategoria(int categoria, Connection conn){
		
		ArrayList<Integer> misLemas=new ArrayList<Integer>();
		try { 
			Statement st = conn.createStatement(); 
			ResultSet rs;
			
			rs = st.executeQuery("SELECT idLema FROM categoriaslemas WHERE categoriaslemas.idCategoria = '" + categoria + "'");
			while ( rs.next() ) {
				String id = rs.getString("idLema");
				misLemas.add(Integer.parseInt(id));
			}

		} catch (Exception e) { 
			
			e.printStackTrace();
		}

		return misLemas; 		
	}
	
	
	
	//FUNCI�N REPETIDA, IGUAL QUE LA ANTERIOR PERO STATIC, HABR�A QUE ELIMINAR UNA
	public ArrayList<Integer> devolverLemasDeCategoria(int idCat,Connection conn) throws SQLException{
		int existe=-1;
		Statement st = conn.createStatement(); 
		ResultSet rs;
		ArrayList<Integer> lemasCat=new ArrayList<Integer>();
		try { 
			rs = st.executeQuery("SELECT idLema FROM categoriaslemas WHERE idCategoria='"+idCat+"'");
			while(rs.next()){
				existe=rs.getInt(1);
				lemasCat.add(existe);
				
			}

		} catch (Exception e) { 
			
			e.printStackTrace();
		}
		return lemasCat; 		
	}
	
	/**
	 * Funci�n que dado el identificador de un lema, devuelve todas las categor�as asociadas a �l
	 * @param recibe  la conexi�n a la base de datos y el identificador del lema
	 * @return devuelve el array con todos los identificadores de las categor�as
	 */
	public ArrayList<Integer> devolverCategoriasDeLema(int idLema,Connection conn) throws SQLException{
		int existe=-1;
		Statement st = conn.createStatement(); 
		ResultSet rs;
		ArrayList<Integer> catsLema=new ArrayList<Integer>();
		try { 
			rs = st.executeQuery("SELECT idCategoria FROM categoriaslemas WHERE idLema='"+idLema+"'");
			while(rs.next()){
				existe=rs.getInt(1);
				catsLema.add(existe);
				
			}

		} catch (Exception e) { 
			
			e.printStackTrace();
		}
		return catsLema; 		
	}
	
	
	/**
	 * Funci�n que dado el identificador de un lema y el identificador de una categor�a cambia la relaci�n lema-categor�a
	 * @param recibe  la conexi�n a la base de datos, el id del lema  y el id de la categoria
	 * @return devuelve true si se ha modificado la relaci�n categor�a-lema correctamente, false en caso contrario
	 */
	public boolean modificarCategoriaLema(Connection conn, int idLema, int idNuevaCategoria) throws SQLException{
		boolean modificar = false;
		if(idLema!=-1){
				try{
					Statement st = conn.createStatement();
					st.executeUpdate("UPDATE categoriaslemas SET idCategoria='"+idNuevaCategoria+"' WHERE idLema ='"+idLema+"'");
					modificar=true;
					
				} catch (Exception e){
					e.printStackTrace();
				}
		}
	

		return modificar;

	}
	


}