package OtrasFunciones;
/**ReadExcel: clase encargada de leer un Excel y escribir en BBDD
 * @author: Laura Asenjo,María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:jxl,java.io,java.sql.Connection, java.sql.SQLException, java.util.ArrayList,ConexionBBDD, categoriasLemas,area,categoria,lema,lemaRasgo,rasgocontenido 
 */
import jxl.*; 
import java.io.*; 
import java.sql.Connection;
import java.util.ArrayList;
import categoriasLemas.categoriasLemas;
import Area.area;
import Categoria.categoria;
import Lema.lema;
import LemaRasgo.lemaRasgo;
import RasgoContenido.rasgocontenido;

public class ReadExcel { 
	//Atributos
	ArrayList<String> areas;
	ArrayList<String> dimension;
	ArrayList<String> lemas;
	ArrayList<String> rasgos;
	
	/*Metodos*/
	/**
	 * Constructor sin parametros de ReadExcel
	 * @return no devuelve nada
	 */
	public ReadExcel(){}
	
	/**
	 * Funcion que lee un archivo Excel y escribe en la BBDD
	 * @param recibe el archivo a leer y la conexion a la BBDD
	 * @return no devuelve nada
	 */
	public void leerArchivoExcel(String archivoDestino,Connection conn) { 
	areas=new ArrayList<String>();
	dimension=new ArrayList<String>();
	lemas=new ArrayList<String>();
	rasgos=new ArrayList<String>();
	
	try { 
			Workbook archivoExcel = Workbook.getWorkbook(new File( archivoDestino)); 
			 
			for (int sheetNo = 0; sheetNo < archivoExcel.getNumberOfSheets(); sheetNo++) // Recorre cada    hoja                                                                                                                                                       
			{ 
				Sheet hoja = archivoExcel.getSheet(sheetNo); 
				int numColumnas = hoja.getColumns(); 
				int numFilas = hoja.getRows(); 
				 
				for (int fila = 1; fila < numFilas; fila++) { // Recorre cada fila de la hoja
					                                                                         
						areas.add(hoja.getCell(0, fila).getContents());
						dimension.add(hoja.getCell(1, fila).getContents());
						lemas.add(hoja.getCell(2, fila).getContents());
												
						area nuevaArea =new area();
						nuevaArea.setArea(hoja.getCell(0, fila).getContents());
						if (nuevaArea.consultarUnAreaNombre(conn)==-1){
							nuevaArea.añadirArea(conn);	
						}
											
						categoria nuevaCategoria= new categoria();
						nuevaCategoria.setCategoria(hoja.getCell(1, fila).getContents());
						if(nuevaCategoria.consultarUnaCategoriaArea(conn, nuevaArea.getArea(), nuevaCategoria.getCategoria())==-1){							
							nuevaCategoria.añadirCategoria(conn, nuevaArea.getArea());							
						}
												
						lema miLema= new lema();
						miLema.setLema(hoja.getCell(2, fila).getContents());
						if(miLema.consultarUnLemaNombre(conn)==-1){
							miLema.añadirLema(conn);							
						}
						
						categoriasLemas categLema=new categoriasLemas();
						categLema.añadirLemaCategoria(conn, nuevaCategoria.getCategoria(), miLema.getLema());
						
						
						for (int columna = 3; columna < numColumnas; columna++) {
							
							rasgocontenido mirasgo= new rasgocontenido();
							mirasgo.setRasgo(hoja.getCell(columna, fila).getContents());
							if(!hoja.getCell(columna, fila).getContents().equals(""))
								if(mirasgo.consultarUnRasgo(conn)==-1){
									mirasgo.añadirRasgo(conn);
								}
									lemaRasgo milemarasgo = new lemaRasgo();
									milemarasgo.añadirLemaRasgo(conn, miLema.getLema(), mirasgo.getRasgo());
							
							
						}
						

						}
			
					
					} 
	
								
			
	} catch (Exception ioe) { 
			ioe.printStackTrace(); 
		} 
		
	} 
		

}