package categoriasLemas;
/**categoriasLemas: clase encargada de crear, buscar, eliminar, actualizar la tabla categoriasLemas
 * @author: Laura Asenjo,María de Valvanera Jiménez, Silvia Cabezas
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
	 * Funcion que actualiza el atributo categoría
	 * @param recibe la categoría
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
	 * Función que asociad un lema a una categoría en la BBDD
	 * @param recibe  la conexión a la base de datos, el nombre de la categoría y el nombre del lema
	 * @return devuelve true si se ha insertado la categoría junto con el lema en la bbdd, false en caso contrario
	 */
	public boolean añadirLemaCategoria(Connection conn,String categoria, String lema){
		boolean añadir=false;
		
		try{
			
			lema milema=new lema(lema);
			int idLema=milema.consultarUnLema(conn);
			categoria micategoria =new categoria(categoria);
			int idCategoria =micategoria.consultarUnaCategoria(conn);
	

				Statement st = conn.createStatement();	
				st.executeUpdate("INSERT INTO categoriaslemas (idCategoria, idLema) VALUES ('" + idCategoria + "'" +"," +"'" + idLema + "')");
				
				añadir=true;
		
		} catch (Exception e){
			e.printStackTrace();
		}
		return añadir;
	}

	
	//FUNCIÓN STATIC, HABRÍA QUE CAMBIARLO
	/**
	 * Función que elimina la relación entre un lema y una categoría
	 * @param recibe  la conexión a la base de datos, el identificador de la categoría y el identificador del lema
	 * @return devuelve true si se ha roto la relación entre el lema y la categoría en la bbdd, false en caso contrario
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
	 * Función que dada una categoría te devuelve todos los lemas asociados a la misma
	 * @param recibe  la conexión a la base de datos y el identificador de la categoría
	 * @return devuelve el array con los lemas asociados a dicha categoría
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
	
	
	
	//FUNCIÓN REPETIDA, IGUAL QUE LA ANTERIOR PERO STATIC, HABRÍA QUE ELIMINAR UNA
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
	 * Función que dado el identificador de un lema, devuelve todas las categorías asociadas a él
	 * @param recibe  la conexión a la base de datos y el identificador del lema
	 * @return devuelve el array con todos los identificadores de las categorías
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
	 * Función que dado el identificador de un lema y el identificador de una categoría cambia la relación lema-categoría
	 * @param recibe  la conexión a la base de datos, el id del lema  y el id de la categoria
	 * @return devuelve true si se ha modificado la relación categoría-lema correctamente, false en caso contrario
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