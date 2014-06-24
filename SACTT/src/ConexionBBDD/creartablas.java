	package ConexionBBDD;
/**creartablas: clase encargada de crear las tablas en la base de datos
 * @author: Laura Asenjo,María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see:sql 
 */
import java.sql.*;

public class creartablas {

	 Statement stmt = null;
	 /**
		 * Constructor que crea las tablas en la base de datos
		 * @param recibe la conexion
		 * @return no devuelve nada
		 */
	public creartablas(Connection miconexion) throws SQLException{
		
		  
	      stmt = miconexion.createStatement();
	      
	      String sqlLema = "CREATE TABLE IF NOT EXISTS LEMA " +
	    		  	   "(idLema INT NOT NULL AUTO_INCREMENT,"+
	                   "lemaPalabra VARCHAR(255), " +
	                   " PRIMARY KEY ( idLema ))ENGINE=innodb"; 

	      stmt.executeUpdate(sqlLema);
	      
	     String sqlArea = "CREATE TABLE IF NOT EXISTS AREA " +
                  "(idArea INT NOT NULL AUTO_INCREMENT,"+
	              "nombreArea VARCHAR(255), " +
                  " PRIMARY KEY ( idArea )) ENGINE=innodb"; 

	      stmt.executeUpdate(sqlArea);
	      
	      String sqlCategoria = "CREATE TABLE IF NOT EXISTS CATEGORIA " +
                  "(idCategoria INT NOT NULL AUTO_INCREMENT,"+
	              "categoriaPalabra VARCHAR(255), " +
	              "idA INT NOT NULL, " +
	              " CONSTRAINT fk_idA FOREIGN KEY (idA) REFERENCES AREA(idArea) ON UPDATE CASCADE ON DELETE RESTRICT,"+
                  " PRIMARY KEY ( idCategoria )) ENGINE=innodb"; 

	      stmt.executeUpdate(sqlCategoria);

	      
	      String sqlRasgoContenido = "CREATE TABLE IF NOT EXISTS RASGOCONTENIDO " +
	    		  "(idRasgoContenido INT NOT NULL AUTO_INCREMENT,"+
                  "RasgoContenido VARCHAR(255), " +
                  " PRIMARY KEY ( idRasgoContenido ))ENGINE=innodb"; 

	      stmt.executeUpdate(sqlRasgoContenido);
	      
	      String sqlLemaCategoria = "CREATE TABLE IF NOT EXISTS CATEGORIASLEMAS " +
                  "(idCategoria int not NULL, " +
                  "idLema int not NULL, " +
                  "PRIMARY KEY ( idCategoria,idLema ),"+
                  "FOREIGN KEY ( idCategoria) REFERENCES CATEGORIA(idCategoria),"+
                  "FOREIGN KEY ( idLema) REFERENCES LEMA(idLema))ENGINE=innodb"; 
	    
	      stmt.executeUpdate(sqlLemaCategoria);
	      
	     String sqlLemaRasgo = "CREATE TABLE IF NOT EXISTS LEMARASGO " +
                  "(idLema int not NULL, " +
                  "idRasgoContenido int not NULL, " +
                  "PRIMARY KEY ( idLema,idRasgoContenido ),"+
                  "FOREIGN KEY ( idLema) REFERENCES LEMA(idLema),"+
                  "FOREIGN KEY ( idRasgoContenido) REFERENCES RASGOCONTENIDO(idRasgoContenido))ENGINE=innodb"; 
	    
	      stmt.executeUpdate(sqlLemaRasgo);
	      
	      String sqlUsuarios = "CREATE TABLE IF NOT EXISTS USUARIOS " +
	    		  "(idUsuario INT NOT NULL AUTO_INCREMENT, " +
	    		  "usuario VARCHAR(32) NOT NULL, " +
	    		  "correo VARCHAR(255), " +
	    		  "clave VARCHAR(60) NOT NULL, " +
	    		  "permisos INT NOT NULL, " +
	    		  "PRIMARY KEY (idUsuario))ENGINE=innodb"; 

	      stmt.executeUpdate(sqlUsuarios);
	      
	      String sqlSugerencia = "CREATE TABLE IF NOT EXISTS SUGERENCIA " +
                  "(idSugerencia INT NOT NULL AUTO_INCREMENT,"+
	              "descripcion VARCHAR(255) NOT NULL, " +
	              "nombreUser VARCHAR(32) NOT NULL, " +
                  " PRIMARY KEY ( idSugerencia )) ENGINE=innodb"; 

	      stmt.executeUpdate(sqlSugerencia);
	      
	      //Insertamos el Súper-administrador si no existe ya 
	      ResultSet res = stmt.executeQuery("SELECT * FROM usuarios WHERE permisos =  '1'");
	      if (!res.next() ) {
	    	  stmt.executeUpdate("INSERT INTO `usuarios`(`usuario`,`clave`,`permisos`) VALUES ('sadmin', '1234', '1');");
	      }
	    
		
	}
}

		