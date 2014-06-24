package OtrasFunciones;
/**EscribirCSV: clase encargada de crear un archivo CSV con toda la información de la BBDD
 * @author: Laura Asenjo,María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:io*,sql*, ArrayList,area, categoria,idPalabra,lema,lemaRasgo,rasgocontenido,categoriasLemas,CsvWriter
 */
import java.io.*;
import java.sql.*;
import java.util.ArrayList; 
import Area.area;
import Categoria.categoria;
import IdPalabra.idPalabra;
import Lema.lema;
import LemaRasgo.lemaRasgo;
import RasgoContenido.rasgocontenido;
import categoriasLemas.categoriasLemas;
import com.csvreader.CsvWriter;
 
 
public class EscribirCSV {
	/*Metodos*/
	/**
	 * Constructor sin parametros de area
	 * @return no devuelve nada
	 */
	public EscribirCSV(){}
	
	/**
	 * Funcion que crea un archivo CSV con toda la información almacenada en la BBDD
	 * @param recibe el nombre del archivo a exportar y la conexión a la base de datos
	 * @return  no devuelve nada
	 */
	@SuppressWarnings("static-access")
	public void escribirCSV(String nombreArchivo,Connection conn) throws SQLException{
		  ArrayList<idPalabra> misAreasAux=new ArrayList<idPalabra>();
		  String areaAct="";
		  area misAreas=new area();
		  misAreasAux=misAreas.devolverAreas(conn);
		  Integer lemAct;
		//NOMBRE DEL ARCHIVO .csv  
		  	String outputFile = nombreArchivo;
	        boolean alreadyExists = new File(outputFile).exists();
	         
	        if(alreadyExists){
	            File ficheroDatos = new File(outputFile);
	            ficheroDatos.delete();
	        }  
	        
	      try {
			CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
			
            //SE RECORREN TODAS LAS AREAS
			 for(int i=0; i<misAreasAux.size();i++){
				 //SE OBTIENE EL AREA ACTUAL
				 areaAct=misAreasAux.get(i).getPalabra();	
				 ArrayList<idPalabra> misCategorias= new ArrayList<idPalabra>();
				 categoria micategoria = new categoria();
				 misCategorias=micategoria.consultarCategoriasConArea(areaAct, conn);
				 //Si es 0 es que no hay categorias
				 if(misCategorias.size()==0){
					 csvOutput.write(areaAct);
					 csvOutput.endRecord();	
				 } 
				 else{
					 
					//esto es que si tengo categorias
					for(int j=0; j<misCategorias.size();j++){
						 String catAct=misCategorias.get(j).getPalabra();	
						//para cada categoria tengo que ver si tiene lemas
						ArrayList<Integer> lemasAct=new ArrayList<Integer>();
						categoriasLemas misLemas=new categoriasLemas();
						lemasAct=misLemas.consultarLemasdeunaCategoria(misCategorias.get(j).getId(), conn);
						//Si es 0 es que no tengo lemas asociados a dicha categoria
						if(lemasAct.size()==0){
							csvOutput.write(areaAct);
							csvOutput.write(catAct);	
							csvOutput.endRecord();	
							
						}
						else{
							//SI TIENE VARIOS LEMAS ASOCIADOS
							for(int x=0; x<lemasAct.size();x++){
								lema miLema =new lema();
								 lemAct=lemasAct.get(x);	
								lemaRasgo misLemaRasgo= new lemaRasgo();
								ArrayList<Integer> rasgos=new ArrayList<Integer>();
								  
								rasgos= misLemaRasgo.devolverRasgosDeLema(lemasAct.get(x), conn);
								//NO TIENE RASGOS
								if(rasgos.size()==0){
									csvOutput.write(areaAct);
									csvOutput.write(catAct);	
									csvOutput.write(miLema.consultarUnLemaPorId(lemAct,conn));
									csvOutput.endRecord();	
									
								}
								//SI TIENE RASGOS
								else{
									csvOutput.write(areaAct);
									csvOutput.write(catAct);	
									csvOutput.write(miLema.consultarUnLemaPorId(lemAct,conn));
									for(int k=0; k<rasgos.size();k++){
										
									
										rasgocontenido mirasgo =new rasgocontenido();
										csvOutput.write(mirasgo.consultarRasgoPorId(rasgos.get(k),conn));	
										
									
									}
									csvOutput.endRecord();	
									
								}
								
								
							}
							
							
							
						}
						
						
					}
					 
					 
					 
				 }
				
				  
			  }
			 csvOutput.close();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		 
	
	}
	

}