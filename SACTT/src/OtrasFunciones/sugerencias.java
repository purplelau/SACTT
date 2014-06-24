package OtrasFunciones;
/**sugerencias: clase encargada de a�adir una sugerencia en la base de datos
 * @author: Laura Asenjo, Mar�a de Valvanera Jim�nez, Silvia Cabezas
 * @version: 1.0
 * @see:sql
 */


import java.sql.*;


public class sugerencias {
	//Atributos
		String _sugerencia;
	
	/*Metodos*/
		/**
		 * Constructor sin parametros de sugerencia
		 * @return no devuelve nada
		 */
		public sugerencias(){		
			_sugerencia="";
		}

		/**
		 * Constructor con parametros de sugerencia
		 * @param recibe la descripci�n de la sugerencia
		 * @return no devuelve nada
		 */
		public sugerencias(String descripcion){		
			_sugerencia=descripcion;
		}

		/**
		 * Funcion que actualiza el atributo sugerencia
		 * @param recibe la descripci�n de la sugerencia
		 * @return no devuelve nada
		 */
		public void setSugerencia(String descripcion){
			_sugerencia=descripcion;		
		}
		/**
		 * Funcion que devuelve la sugerencia actual
		 * @param no recibe parametros
		 * @return  devuelve la sugerencia
		 */
		public String getSugerencia(){
			return _sugerencia;	
		}
		
		/**
		 * Funci�n que a�ade la sugerencia a la BBDD
		 * @param recibe  la conexi�n a la base de datos y el nombre de usuario
		 * @return devuelve true si se ha insertado la sugerencia en la bbdd, false en caso contrario
		 */
		public boolean a�adirSugerencia(Connection conn,String usuario) throws SQLException{	
			boolean creada = false;
			try { 
				Statement st = conn.createStatement(); 
				st.executeUpdate("INSERT INTO sugerencia (descripcion,nombreUser) VALUES ('"+_sugerencia+"'"+ ", '"+usuario+"')" );
				creada=true;

			} catch (Exception e) { 
				e.printStackTrace();
			}
			
			return creada;
		}
		
		/**
		 * Funci�n que devuelve el identificador de una sugerencia concreta
		 * @param recibe  la conexi�n a la base de datos
		 * @return devuelve el entero correspondiente al identificador de la sugerencia, en caso contrario devuelve -1
		 */
		public int consultarUnaSugerencia( Connection conn){
			int existe=-1;
			try { 
				Statement st = conn.createStatement(); 
				ResultSet rs;
				rs = st.executeQuery("SELECT idSugerencia FROM sugerencia WHERE descripcion='"+_sugerencia+"'");
				while(rs.next())
					existe=rs.getInt(1);

			} catch (Exception e) { 
				e.printStackTrace();
			}
			return existe; 		
		}

}
