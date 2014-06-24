package Categoria;
/**categoria: clase encargada de crear, buscar, eliminar y actualizar una categoría
 * @author: Laura Asenjo, María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:sql, area, ArrayList, idPalabra 
 */
import java.sql.*;
import java.util.ArrayList;
import Area.area;
import IdPalabra.idPalabra;

public class categoria {
	//Atributos
	String _categoria;
	
	/*Metodos*/
	/**
	 * Constructor sin parametros de categoría
	 * @return no devuelve nada
	 */
	public categoria(){		
		_categoria="";
	}
	
	/**
	 * Constructor con parametros de categoría
	 * @param recibe la categoría
	 * @return no devuelve nada
	 */
	public categoria(String cat){		
		_categoria=cat;
	}

	/**
	 * Funcion que actualiza el atributo categoría
	 * @param recibe la categoría
	 * @return no devuelve nada
	 */
	public void setCategoria(String categoria){
		_categoria=categoria;		
	}
	/**
	 * Funcion que devuelve la categoría actual
	 * @param no recibe parametros
	 * @return  devuelve la categoría
	 */
	public String getCategoria(){return _categoria;}

	
	
	/**
	 * Función que añade la categoría a la BBDD
	 * @param recibe  la conexión a la base de datos
	 * @return devuelve true si se ha insertado la categoría en la bbdd, false en caso contrario
	 */
	public boolean añadirCategoria(Connection conn,String miAreaElegida) throws SQLException{	
		boolean añadir=false;
		area miarea=new area();
		miarea.setArea(miAreaElegida);
	
		int miArea=miarea.consultarUnArea(conn);
			if(consultarUnaCategoriaConArea(miArea,conn)==-1){
				try { 

					Statement st = conn.createStatement(); 
					st.executeUpdate("INSERT INTO categoria (categoriaPalabra,idA)" +  "VALUES ('"+_categoria+"'"+ ", '"+miArea +"')" );
					añadir=true;
				
				} catch (Exception e) { 
					
					e.printStackTrace();
				} 
			}
			

		return añadir;
	}

	/**
	 * Función que elimina una categoría de la BBDD
	 * @param recibe  la conexión a la base de datos
	 * @return devuelve true si se ha eliminado la categoría de la bbdd, false en caso contrario
	 */
	public boolean eliminarCategoria( Connection conn, area miarea) throws SQLException{
		boolean eliminado = false;
	
		int miAreaElegida=miarea.consultarUnArea(conn);
		if(miAreaElegida!=-1){
			if(consultarUnaCategoriaConArea(miAreaElegida,conn)!=-1){
				try { 
					Statement st = conn.createStatement(); 
					st.executeUpdate( "DELETE FROM categoria " +
							"WHERE categoriaPalabra =\""+_categoria+"\" AND idA =\""+miAreaElegida+"\"");
					
					
					_categoria="";
					eliminado=true;

				} catch (Exception e) { 
					
					e.printStackTrace();
				} 	
			}
		}
			
	
		return eliminado;
	}


	/**
	 * Función que devuelve todas las categorías
	 * @param recibe  la conexión a la base de datos
	 * @return devuelve el ArrayList con todas las categorías de la base de datos
	 */
	public  ArrayList<idPalabra> consultarTodasCategorias(Connection conn){
		ArrayList<idPalabra> misCategorias=new ArrayList<idPalabra>();
		try{
				Statement st = conn.createStatement(); 
				ResultSet rs;
				rs = st.executeQuery("SELECT * FROM categoria");
				while ( rs.next() ) {
					String palabra = rs.getString("categoriaPalabra");
					String id = rs.getString("idCategoria");
					misCategorias.add(new idPalabra(Integer.parseInt(id),palabra));
					
				}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return misCategorias;
	}

	/**
	 * Función que devuelve el identificador de un area concreta, asociada a una categoría
	 * @param recibe  la conexión a la base de datos y el identificador del area
	 * @return devuelve el entero correspondiente al identificador área si existe, o -1 en caso contrario
	 */
	public int consultarUnaCategoriaConArea(int idArea,Connection conn) {
		int existe=-1;
		
		try { 
			Statement st = conn.createStatement(); 
			ResultSet rs;
			rs = st.executeQuery("SELECT idCategoria,categoriaPalabra,idA FROM categoria WHERE idA='"+idArea+"' AND categoriaPalabra='"+_categoria+"'");
			while(rs.next()){
				existe=rs.getInt(3);
				
			}

		} catch (Exception e) { 
			
			e.printStackTrace();
		}
		return existe; 		
	}
	
	/**
	 * Función que devuelve un array con todas las categorías asociadas a un área
	 * @param recibe  la conexión a la base de datos y el nombre del área
	 * @return devuelve el ArrayList con todas las categorías
	 */
	public ArrayList<idPalabra> consultarCategoriasConArea(String nomArea, Connection conn){
		
		ArrayList<idPalabra> misCategorias=new ArrayList<idPalabra>();
		try { 
			Statement st = conn.createStatement(); 
			ResultSet rs;
			
			rs = st.executeQuery("SELECT idCategoria,categoriaPalabra,idA FROM categoria INNER JOIN area ON (area.idArea = categoria.idA) WHERE area.nombreArea = '" + nomArea + "'");
			while ( rs.next() ) {
				String palabra = rs.getString("categoriaPalabra");
				String id = rs.getString("idCategoria");
				misCategorias.add(new idPalabra(Integer.parseInt(id),palabra));
				
			}

		} catch (Exception e) { 
			
			e.printStackTrace();
		}
		
		
		return misCategorias; 		
	}

	/**
	 * Función que devuelve el identificador de una categoría concreta
	 * @param recibe  la conexión a la base de datos
	 * @return devuelve el entero correspondiente al identificador de la categoría, en caso contrario devuelve -1
	 */
	public int consultarUnaCategoria( Connection conn){
		int existe=-1;
		
		try { 
			Statement st = conn.createStatement(); 
			ResultSet rs;
			rs = st.executeQuery("SELECT idCategoria FROM categoria WHERE categoriaPalabra='"+_categoria+"'");
			while(rs.next())
				existe=rs.getInt(1);

		} catch (Exception e) { 
			
			e.printStackTrace();
		}
		return existe; 		
	}

	/**
	 * Función que devuelve el identificador de una categoría concreta, según el área dada
	 * @param recibe  la conexión a la base de datos, el nombre del área y el nombre de la categoría
	 * @return devuelve el entero correspondiente al identificador de la categoría, en caso contrario devuelve -1
	 */
	public int consultarUnaCategoriaArea( Connection conn, String area, String cat) throws SQLException{
		int existe=-1;
		Statement st = conn.createStatement(); 
		ResultSet rs;
		try { 
			rs = st.executeQuery("SELECT categoria.idCategoria FROM (SELECT idArea FROM area WHERE nombreArea = '" +
						area + "') as areaSel INNER JOIN categoria WHERE ((categoria.idA = areaSel.idArea) " +
						"&& (categoria.categoriaPalabra = '" + cat +"'))");
			while(rs.next())
				existe=rs.getInt(1);

		} catch (Exception e) { 
			
			e.printStackTrace();
		}
		return existe; 		
	}
	
	/**
	 * Función que devuelve el nombre de una categoría, dado el identificador de la misma
	 * @param recibe  la conexión a la base de datos y el identificador del la categoría
	 * @return devuelve el nombre de la categoría en caso de que exista, en caso contrario devuelve blanco.
	 */
	public String consultarCategoriaPorId( int idCat, Connection conn) throws SQLException{
		String existe="";
		Statement st = conn.createStatement(); 
		ResultSet rs;
		try { 
			rs = st.executeQuery("SELECT categoriaPalabra FROM categoria WHERE idCategoria='"+idCat+"'");
			while(rs.next())
				existe=rs.getString(1);

		} catch (Exception e) { 
			
			e.printStackTrace();
		}
		return existe; 		
	}
	
	/**
	 * Función que modifica una categoría
	 * @param recibe  la conexión a la base de datos, el nombre de la categoría y el área
	 * @return devuelve true si se ha modificado correctamente, false en caso contrario
	 */
	public boolean modificarCategoriaNombre(String nuevaCategoria, Connection conn, area miarea) throws SQLException{
		boolean modificar = false;
		int miAreaElegida=miarea.consultarUnArea(conn);
		if(miAreaElegida!=-1){
			if(consultarUnaCategoriaConArea(miAreaElegida,conn)!=-1){
				try{
					Statement st = conn.createStatement();
					st.executeUpdate("UPDATE categoria SET categoriaPalabra='"+nuevaCategoria+"' WHERE categoriaPalabra ='"+_categoria+"' AND idA =\""+miAreaElegida+"\"" );
					modificar=true;
					
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
			

			return modificar;

	}
	
	
	/**
	 * Función que modifica el área a la que pertenece una categoría
	 * @param recibe  la conexión a la base de datos, el área y el id del nuevo área
	 * @return devuelve true si se ha modificado la relación área-categoría correctamente, false en caso contrario
	 */
	public boolean modificarAreaDeCategoria(Connection conn, area miarea, int idNuevaArea) throws SQLException{
		boolean modificar = false;
		int miAreaElegida=miarea.consultarUnArea(conn);
		if(miAreaElegida!=-1){
			if(consultarUnaCategoriaConArea(miAreaElegida,conn)!=-1){
				try{
					Statement st = conn.createStatement();
					st.executeUpdate("UPDATE categoria SET idA='"+idNuevaArea+"' WHERE categoriaPalabra ='"+_categoria+"' AND idA =\""+miAreaElegida+"\"" );
					modificar=true;
					
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		
		return modificar;

	}
	

	/**
	 * Función que consulta si existe una o varias categorias que contenga esa palabra
	 * @param recibe  la conexión a la base de datos y el nombre de la categoría
	 * @return devuelve array con el nombre de las categorías si las encuentra
	 */
	public  ArrayList<String> consultarUnaCategoriaCiega(Connection conn,String micategoria) throws SQLException{
		ArrayList<String> misCategorias=new ArrayList<String>();
		Statement st = conn.createStatement(); 
		ResultSet rs;
		try{
			
			rs=st.executeQuery("SELECT categoriaPalabra FROM categoria WHERE categoriaPalabra LIKE '%"+micategoria+"%'");	
			
			while ( rs.next() ) {   	            
				misCategorias.add(rs.getString(1));
	        }
			
		} catch (Exception e){

			e.printStackTrace();
		}
		return misCategorias;
	}


	
}