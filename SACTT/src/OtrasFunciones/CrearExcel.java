package OtrasFunciones;
/**EscribirCSV: clase encargada de crear un excel con los datos que se encuentran en BBDD
 * @author: Laura Asenjo,María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:FileOutputStream, SQLException, ArrayList, org.apache.poi.hssf.usermodel,area,categoria,idPalabra,lema,lemaRasgo,rasgocontenido,categoriasLemas
 */

import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.*;


import Area.area;
import Categoria.categoria;
import IdPalabra.idPalabra;
import Lema.lema;
import LemaRasgo.lemaRasgo;
import RasgoContenido.rasgocontenido;
import categoriasLemas.categoriasLemas;

/**
 * Crea una hoja Excel con POI
 *
 */
public class CrearExcel {

    /**
     * Crea una hoja Excel y la guarda.
     * @param nombre del fichero a rear, connection conexion a la base de datos
     * @throws SQLException 
     */
    @SuppressWarnings({ "deprecation", "static-access" })
	public void crearExcel(String nombreFichero,java.sql.Connection connection) throws SQLException {
    	int contador=0;
    	
        // Se crea el libro
        HSSFWorkbook libro = new HSSFWorkbook();

        // Se crea una hoja dentro del libro
        HSSFSheet hoja = libro.createSheet();

        // Se crea una fila dentro de la hoja
        HSSFRow fila = hoja.createRow(contador);

        // Se crea una celda dentro de la fila
        HSSFCell celda = fila.createCell((short) 0);
        // Se crea una celda dentro de la fila
        HSSFCell celda2 = fila.createCell((short) 1 );
     // Se crea una celda dentro de la fila
        HSSFCell celda3 = fila.createCell((short) 2 );
        // Se crea una celda dentro de la fila
        HSSFCell celda4 = fila.createCell((short) 3 );
        
        // Se crea el contenido de la celda y se mete en ella.
        HSSFRichTextString texto = new HSSFRichTextString("Área");
        celda.setCellValue(texto);

        HSSFRichTextString texto2 = new HSSFRichTextString("Dimensión");
        celda2.setCellValue(texto2);
        
        HSSFRichTextString texto3 = new HSSFRichTextString("Lema");
        celda3.setCellValue(texto3);
        
        HSSFRichTextString texto4 = new HSSFRichTextString("Rasgos de Contenido");
        celda4.setCellValue(texto4);
      
        ArrayList<idPalabra> misAreasAux=new ArrayList<idPalabra>();
		  String areaAct="";
		  area misAreas=new area();
		  misAreasAux=misAreas.devolverAreas(connection);
		  Integer lemAct;
		  
        //SE RECORREN TODAS LAS AREAS
		 for(int i=0; i<misAreasAux.size();i++){
			
			 //SE OBTIENE EL AREA ACTUAL
			 areaAct=misAreasAux.get(i).getPalabra();	
			 ArrayList<idPalabra> misCategorias= new ArrayList<idPalabra>();
			 categoria micategoria = new categoria();
			 misCategorias=micategoria.consultarCategoriasConArea(areaAct, connection);
			 //Si es 0 es que no hay categorias
			 if(misCategorias.size()==0){
				 contador=contador+1;
				  fila = hoja.createRow(contador);
				  celda = fila.createCell((short) 0);
				  celda.setCellValue(areaAct);
				
			 } 
			 else{
				
				 
				//esto es que si tengo categorias
				for(int j=0; j<misCategorias.size();j++){
					
					 String catAct=misCategorias.get(j).getPalabra();	
					//para cada categoria tengo que ver si tiene lemas
					ArrayList<Integer> lemasAct=new ArrayList<Integer>();
					categoriasLemas misLemas=new categoriasLemas();
					lemasAct=misLemas.consultarLemasdeunaCategoria(misCategorias.get(j).getId(),connection);
					//Si es 0 es que no tengo lemas asociados a dicha categoria
					if(lemasAct.size()==0){
						 contador=contador+1;
						  fila = hoja.createRow(contador);
						  celda = fila.createCell((short) 0);
						  celda.setCellValue(areaAct);
						  celda = fila.createCell((short) 1);
						  celda.setCellValue(catAct);
						
					
					}
					else{
						//SI TIENE VARIOS LEMAS ASOCIADOS
						for(int x=0; x<lemasAct.size();x++){
							lema miLema =new lema();
							 lemAct=lemasAct.get(x);	
							lemaRasgo misLemaRasgo= new lemaRasgo();
							ArrayList<Integer> rasgos=new ArrayList<Integer>();
							  
							rasgos= misLemaRasgo.devolverRasgosDeLema(lemasAct.get(x),connection);
							//NO TIENE RASGOS
							if(rasgos.size()==0){
								contador=contador+1;
								  fila = hoja.createRow(contador);
								 celda = fila.createCell((short) 0);
								  celda.setCellValue(areaAct);
								  celda = fila.createCell((short) 1);
								  celda.setCellValue(catAct);
								  celda = fila.createCell((short) 2);
								  celda.setCellValue(miLema.consultarUnLemaPorId(lemAct,connection));
								
								
							}
								
							//SI TIENE RASGOS
							else{
								contador++;
								 fila = hoja.createRow(contador);
								celda = fila.createCell((short) 0);
								  celda.setCellValue(areaAct);
								  celda = fila.createCell((short) 1);
								  celda.setCellValue(catAct);
								  celda = fila.createCell((short) 2);
								  celda.setCellValue(miLema.consultarUnLemaPorId(lemAct,connection));
								
								  int cont=3;
								for(int k=0; k<rasgos.size();k++){		
									
									rasgocontenido mirasgo =new rasgocontenido();
									celda = fila.createCell((short) cont);
									celda.setCellValue(mirasgo.consultarRasgoPorId(rasgos.get(k),connection));
									cont++;
									
									
								}
								
	
							}//if rasgos
						}//if lemas
					}
							
				}//if categorias
						

       
			 }
		 }
        
        // Se salva el libro.
        try {
            FileOutputStream elFichero = new FileOutputStream(nombreFichero);
            libro.write(elFichero);
            elFichero.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

