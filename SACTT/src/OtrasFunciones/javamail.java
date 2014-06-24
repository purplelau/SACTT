package OtrasFunciones;
/**javamail: clase encargada de mandar un email
 * @author: Laura Asenjo,María de Valvanera Jiménez, Silvia Cabezas
 * @version: 1.0
 * @see: javax.mail.*, Properties
 */

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class javamail {
	
	/**
	 * Funcion que en envia un email desde la dirección de correo especificada
	 * @param recibe el contenido del mensaje, el asunto del mensaje y la dirección de correo de destino
	 * @return  devuelve un boolean indicando si el correo se ha enviado correctamente
	 */
	public boolean sendMessage(String mensaje,String subject, String correo){
					String dt = "hola";
			        boolean rpta=false;
			        Properties props = new Properties();
			        // Nombre del host de correo, es smtp.gmail.com
			        props.setProperty("mail.smtp.host", "smtp.gmail.com");
			        // TLS si está disponible
			        props.setProperty("mail.smtp.starttls.enable", "true");
			        // Puerto de gmail para envio de correos
			        props.setProperty("mail.smtp.port","587");
			        // Cuenta de correo en gmail
			        props.setProperty("mail.smtp.user", "sactttfg@gmail.com");
			        // Si requiere o no usuario y password para conectarse.
			        props.setProperty("mail.smtp.auth", "true");
			        Session session = Session.getDefaultInstance(props);
			        //Verificar el envio
			        session.setDebug(true);
			        MimeMessage  message = new MimeMessage(session);
			        try {
			            message.setSubject(subject);
			            message.setText(mensaje);
			         
			            message.setFrom(new InternetAddress(dt,"Servicio de email del SACTT"));
			            //La direccion de la persona a enviar
			         
			            message.addRecipient(Message.RecipientType.TO,new InternetAddress(correo,false));
			            Transport t = session.getTransport("smtp");
			            t.connect("sactttfg@gmail.com","trabajofindegrado");
			            t.sendMessage(message,message.getAllRecipients());
			            t.close();
			            rpta=true;
			        } catch (MessagingException ex) {
			            ex.printStackTrace();
			            return rpta;
			        } catch (UnsupportedEncodingException ex) {
			            ex.printStackTrace();
			            return rpta;
			        }
			        return rpta;
			}
}

