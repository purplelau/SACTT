package OtrasFunciones;
/**EscribirExcel: clase encargada de crear un archivo Excel con toda la información de la BBDD
 * @author: Laura Asenjo,María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:io*,sql*, ArrayList,categoria,area,idPalabra,lema,lemaRasgo,rasgocontenido,categoriasLemas
 */
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

import categoriasLemas.categoriasLemas;
import Area.area;
import Categoria.categoria;
import IdPalabra.idPalabra;
import Lema.lema;
import LemaRasgo.lemaRasgo;
import RasgoContenido.rasgocontenido;

 
public class EscribirExcel {
	/**
	 *  Funcion que crea un archivo Excel con toda la información almacenada en la BBDD
	 * @param recibe el nombre del archivo a exportar y la conexión a la base de datos
	 * @return  no devuelve nada
	 */
    @SuppressWarnings("static-access")
	public void escribirExcel(String nombreFichero,java.sql.Connection connection) throws SQLException, IOException{    	
    	File file = new File(nombreFichero);
    	FileWriter salida = new FileWriter(file);
    	 ArrayList<idPalabra> misAreasAux=new ArrayList<idPalabra>();
		  String areaAct="";
		  area misAreas=new area();
		  misAreasAux=misAreas.devolverAreas(connection);
		  Integer lemAct;
		  	salida.write("Área");
			 salida.write("\t"); //pasamos a la siguiente columna
			 salida.write("Categoría");
			 salida.write("\t");
			 salida.write("Lema");
			 salida.write("\t");
			 salida.write("Rasgos de Contenido");
			 salida.write("\n");
          //SE RECORREN TODAS LAS AREAS
			 for(int i=0; i<misAreasAux.size();i++){
				 //SE OBTIENE EL AREA ACTUAL
				 areaAct=misAreasAux.get(i).getPalabra();	
				 ArrayList<idPalabra> misCategorias= new ArrayList<idPalabra>();
				 categoria micategoria = new categoria();
				 misCategorias=micategoria.consultarCategoriasConArea(areaAct, connection);
				 //Si es 0 es que no hay categorias
				 if(misCategorias.size()==0){
					 salida.write(areaAct);
					
				 } 
				 else{
					 
					//esto es que si tengo categorias
					for(int j=0; j<misCategorias.size();j++){
						 String catAct=misCategorias.get(j).getPalabra();	
						//para cada categoria tengo que ver si tiene lemas
						ArrayList<Integer> lemasAct=new ArrayList<Integer>();
						categoriasLemas misLemas=new categoriasLemas();
						lemasAct=misLemas.consultarLemasdeunaCategoria(misCategorias.get(j).getId(), connection);
						//Si es 0 es que no tengo lemas asociados a dicha categoria
						if(lemasAct.size()==0){
					
							 salida.write(areaAct);
							 salida.write("\t"); //pasamos a la siguiente columna
							 salida.write(catAct);
							
						}
						else{
							//SI TIENE VARIOS LEMAS ASOCIADOS
							for(int x=0; x<lemasAct.size();x++){
								lema miLema =new lema();
								 lemAct=lemasAct.get(x);	
								lemaRasgo misLemaRasgo= new lemaRasgo();
								ArrayList<Integer> rasgos=new ArrayList<Integer>();
								  
								rasgos= misLemaRasgo.devolverRasgosDeLema(lemasAct.get(x), connection);
								//NO TIENE RASGOS
								if(rasgos.size()==0){
									
									 salida.write(areaAct);
									 salida.write("\t"); //pasamos a la siguiente columna
									 salida.write(catAct);
									 salida.write("\t");
									 salida.write(miLema.consultarUnLemaPorId(lemAct,connection));
									
									 salida.write("\n");
									
								}
									
								//SI TIENE RASGOS
								else{
									
									salida.write(areaAct);
									 salida.write("\t"); //pasamos a la siguiente columna
									 salida.write(catAct);
									 salida.write("\t");
									 salida.write(miLema.consultarUnLemaPorId(lemAct,connection));
									 salida.write("\t");

									for(int k=0; k<rasgos.size();k++){						
										rasgocontenido mirasgo =new rasgocontenido();
										salida.write(mirasgo.consultarRasgoPorId(rasgos.get(k),connection));
										salida.write("\t");
										
									}
									salida.write("\n");
									
		
								}//if rasgos
							}//if lemas
						}
								
					}//if categorias
							
	
	        
				 }
			 }
			 salida.close();
				
    }
    

   
}

