package OtrasFunciones;
/**EscribirCSV: clase encargada de las funciones de consulta
 * @author: Laura Asenjo,Mar�a de Valvanera Jim�nez, Silvia Cabezas
 * @version: 1.0
 * @see:Connection,sql*, ArrayList, rasgocontenido
 */
import java.sql.*;
import java.util.ArrayList;
import java.sql.Connection;
import RasgoContenido.rasgocontenido;

public class consulta {
	private String Area, Categoria;
	private ArrayList<String> Rasgos;
	private ArrayList<Integer> IndRasgos = new ArrayList<Integer>();
	
	/**
	 * Constructor de consulta
	 * @param recibe la lista de rasgos a consultar, el nombre del �rea, y la categor�a
	 * @return no devuelve nada
	 */
	public consulta(ArrayList<String> rasgos, String nomArea, String nomCat ){
		Rasgos = new ArrayList<String>(rasgos);
		Area = nomArea;
		Categoria = nomCat;
	}
	
	/**
	 * Funci�n consultar
	 * @param recibe la conexi�n a la base de datos
	 * @return devuelve la lista con los lemas que coinciden con los par�metros de b�squeda
	 */
	public ArrayList<String> consultar(Connection conn){
		Statement st;
		String cadenaRasgos = "";
		for (int i=0; i<IndRasgos.size(); i++){
			cadenaRasgos = cadenaRasgos + "idRasgoContenido = " + IndRasgos.get(i);
			if (i < IndRasgos.size()-1){
				cadenaRasgos = cadenaRasgos + " OR ";
			}
		}
		
		ArrayList<String> ret = new ArrayList<String>();
		
		try {
			st = conn.createStatement();
			
			int idcat = this.idCategoria(conn);
			ResultSet res = st.executeQuery("SELECT idLema, COUNT( * ) "
					+ "FROM (SELECT lemarasgo.idLema, lemarasgo.idRasgocontenido FROM lemarasgo, "
					+ "(SELECT idLema FROM categoriaslemas WHERE idCategoria =" + idcat 
					+ " ) AS sub2 WHERE lemarasgo.idLema = sub2.idLema) AS sub1 WHERE ("
					+ cadenaRasgos + ") " + "GROUP BY idLema");
			
			int ind = -1, count = 0;
			ArrayList<Integer> indRes = new ArrayList<Integer>();
			while (res.next()){
				//Buscamos el registro de mayor count y lo a�adimos
				if (res.getInt(2) > count){
					ind = res.getInt(1);
					indRes.clear();
					indRes.add(ind);
					count = res.getInt(2);
				}else if(res.getInt(2) == count){
					//Si encontramos otro registro con igual count, lo a�adimos tambien
					ind = res.getInt(1);
					indRes.add(ind);
				}
			}
			
			
			
			if (Rasgos.size()>count){
				
				ret.add("Ning�n resultado para los rasgos introducidos.");
			}else{
				
				for (int i = 0; i<indRes.size(); i++){
					ResultSet lemaRes = st.executeQuery("SELECT lemaPalabra FROM lema WHERE "
							+ "( idLema = " + indRes.get(i) + ")");
					lemaRes.next();
					
					ret.add(lemaRes.getString(1));
				}
			}

			
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
		return ret;
		
	}
	
	/**
	 * Funcion que busca el identificador de cada uno de los rasgos en la base de datos y los almacena en 
	 * el atributo IndRasgos
	 * @param recibe la conexi�n a la base de datos
	 * @return no devuelve nada
	 */
	public void rasgosAIndices(Connection conn){
		for (int i=0; i<Rasgos.size(); i++){
			IndRasgos.add(new rasgocontenido(Rasgos.get(i)).consultarUnRasgo(conn));
		}
	}
	
	/**
	 * Funcion que devuelve el identificador de la categor�a en la base de datos
	 * @param recibe la conexi�n a la base de datos
	 * @return devuelve el identificador de la categor�a
	 */
	private int idCategoria(Connection conn){
		ResultSet rs;
		int idcat = -1;
		try {
			Statement st = conn.createStatement();
			rs = st.executeQuery("SELECT idCategoria FROM categoria WHERE ((categoriaPalabra = '" + Categoria + "') "
					+ "AND idA IN (SELECT idArea FROM area WHERE (nombreArea ='"  + Area + "') ))");
			rs.next();
			idcat = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return idcat;
	}

}
