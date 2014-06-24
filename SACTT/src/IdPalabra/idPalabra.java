package IdPalabra;
/**IdPalabra: clase que guarda un tipo de dato, identificador y palabra
 * @author: Laura Asenjo,María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 */

public class idPalabra {
	//Atributos
	int _id;
	String _palabra;
	
	
	//Funciones
	/**
	 * Constructor sin parametros de categoría
	 * @return no devuelve nada
	 */
	public idPalabra(){
		_id=0;
		_palabra="";	
	}
	/**
	 * Constructor con parametros de idPalabra
	  * @param recibe el identificador y la palabra
	 * @return no devuelve nada
	 */
	public idPalabra(int id, String palabra){
		_id=id;
		_palabra=palabra;
	}
	
	/**
	 * Funcion que actualiza los atributos de identificador y palabra
	 * @param recibe la palabra y el identificador
	 * @return no devuelve nada
	 */
	public void setIDPalabra(int id, String palabra){
		_id=id;
		_palabra=palabra;
		
	}
	/**
	 * Funcion que devuelve el identificador
	 * @param no recibe nada
	 * @return devuelve el identificador
	 */
	public int getId(){return _id;}
	/**
	 * Funcion que devuelve la palabra
	 * @param no recibe nada
	 * @return devuelve la palabra
	 */
	public String getPalabra(){return _palabra;}
}
