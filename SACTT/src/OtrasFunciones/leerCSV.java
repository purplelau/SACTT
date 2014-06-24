package OtrasFunciones;
/**leerCSV: clase encargada de leer un archivo CSV y cargar toda la información en la base de datos
 * @author: Laura Asenjo,María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:io*,sql*, CsvReader,categoria,area,lema,lemaRasgo,rasgocontenido,categoriasLemas
 */
import java.io.*;
import java.sql.*;
import com.csvreader.CsvReader;
import categoriasLemas.categoriasLemas;
import Area.area;
import Categoria.categoria;
import Lema.lema;
import LemaRasgo.lemaRasgo;
import RasgoContenido.rasgocontenido;

public class leerCSV {

	
public  leerCSV(){}
/**
 *  Funcion que lee un archivo csv y añade toda la información a la BBDD
 * @param recibe el nombre del archivo a importar y la conexión a la base de datos
 * @return  no devuelve nada
 */
public void leerUnCSV(String miCSV,Connection conn) throws IOException, SQLException {
		CsvReader reader;
		try {
			
			
			
			reader = new CsvReader(miCSV);
	
			
			
			while (reader.readRecord())
			{
				String area = reader.get(0);
				String dimension = reader.get(1);
				String lema = reader.get(2);
				
				area nuevaArea =new area();
				nuevaArea.setArea(area);
				if (nuevaArea.consultarUnAreaNombre(conn)==-1){
					nuevaArea.añadirArea(conn);	
				}
									
				categoria nuevaCategoria= new categoria();
				nuevaCategoria.setCategoria(dimension);
				if(nuevaCategoria.consultarUnaCategoriaArea(conn, nuevaArea.getArea(), nuevaCategoria.getCategoria())==-1){							
					nuevaCategoria.añadirCategoria(conn, nuevaArea.getArea());							
				}
										
				lema miLema= new lema();
				miLema.setLema(lema);
				if(miLema.consultarUnLemaNombre(conn)==-1){
					miLema.añadirLema(conn);							
				}
				
				categoriasLemas categLema=new categoriasLemas();
				categLema.añadirLemaCategoria(conn, nuevaCategoria.getCategoria(), miLema.getLema());
				
				int i=3;
				String rasgos = reader.get(i);
				while(rasgos!=""){					
					if(rasgos.equals(""))
						i=3;
					else{	
							
						rasgocontenido mirasgo= new rasgocontenido();
						mirasgo.setRasgo(rasgos);
						if(mirasgo.consultarUnRasgo(conn)==-1){
							mirasgo.añadirRasgo(conn);
						}
						lemaRasgo milemarasgo = new lemaRasgo();
						milemarasgo.añadirLemaRasgo(conn, miLema.getLema(), mirasgo.getRasgo());
						
					}
				i++;
				rasgos = reader.get(i);
			
			} 
	
				
				

			}
			reader.close();
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		}
	
}
	

}