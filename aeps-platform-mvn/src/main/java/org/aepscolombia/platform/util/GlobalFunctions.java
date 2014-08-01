package org.aepscolombia.platform.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Clase GlobalFunctions
 *
 * Contiene funciones globales que van a ser empleadas por todos los procesos
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class GlobalFunctions {
    
    public static Integer check_in_range(Date start, Date end, Date evaluame, Integer whatIs) 
    {
        Integer result = 2;        
        if(evaluame.after(start) && evaluame.before(end)){
            result = 1;
        }
        /*
         * whatIs=
         * 1: Before Sowing
         * 2: After Sowing
         */
        if (whatIs==1) {
            if (evaluame.equals(end)) result = 1;
        } else if (whatIs==2) {
            if (evaluame.equals(start)) result = 1;
        }
        return result;

//        if(start.before(end)){
//            System.out.println("Date1 is before Date2");
//        }
//
//        if(start.equals(end)){
//            System.out.println("Date1 is equal Date2");
//        }
        
//        $start_ts = strtotime($start_date);
//        $end_ts   = strtotime($end_date);
//        $user_ts  = strtotime($evaluame);
//        if (($user_ts >= $start_ts) && ($user_ts <= $end_ts)) {
//            return 1;
//        } else {
//            return 2;
//        }
    }
    
    public static Integer compareDateBeforeSowing(Date date1, Date date2)
    {        
        Date dateOld = (Date)date2.clone();
        Double numYearsMore =  Math.abs(Math.floor((dateOld.getMonth() - 10) / 12));
        dateOld.setMonth((dateOld.getMonth() - 1 - 6) % 12 + 1);
        dateOld.setYear(dateOld.getYear()-numYearsMore.intValue());
        String dateAsign = "";
        Date dateBefore = null;
        try {
            dateAsign  = new SimpleDateFormat("yyyy-MM-dd").format(dateOld);
            dateBefore = new SimpleDateFormat("yyyy-MM-dd").parse(dateAsign);
        } catch (IllegalArgumentException ex) {
        } catch (ParseException ex) {
        }
//        Date dateBefore = new Date(dateAsign);		
        return check_in_range(dateBefore, date2, date1, 1);
//		return self::check_in_range($dateBefore, $date2, $date1);
	}
    
    public static Integer compareDateAfterSowing(Date date1, Date date2, Integer tipoCul)
    {	
        Date dateOld = (Date)date2.clone();
        Double numYearsMore =  Math.floor((dateOld.getMonth() + 10) / 12);
		if (tipoCul==3) {
            dateOld.setMonth((dateOld.getMonth() - 1 + 18) % 12 + 1);
		} else {
            dateOld.setMonth((dateOld.getMonth() - 1 + 10) % 12 + 1);
		}	
        dateOld.setYear(dateOld.getYear()+numYearsMore.intValue());
//        System.out.println("numYearsMore->"+numYearsMore);
//        System.out.println("dateConvert->"+date2);
        String dateAsign = "";
        Date dateAfter = null;
        try {
            dateAsign = new SimpleDateFormat("yyyy-MM-dd").format(dateOld);
            dateAfter = new SimpleDateFormat("yyyy-MM-dd").parse(dateAsign);
            dateAsign = new SimpleDateFormat("yyyy-MM-dd").format(date2);
            date2     = new SimpleDateFormat("yyyy-MM-dd").parse(dateAsign);
        } catch (IllegalArgumentException ex) {
        } catch (ParseException ex) {
        }
//        System.out.println("date23->"+date2);
//        System.out.println("dateAfter->"+dateAfter);
//        System.out.println("date1->"+date1);
//        Date dateAfter = new Date(dateAsign);
        return check_in_range(date2, dateAfter, date1, 2);
        
//		$dateAfter = ''.$dateAfter->format('Y-m-d');        
//		return self::check_in_range($date2, $dateAfter, $date1);
    }

    /**
     * Encargado de enviar un correo al usuario
     *
     * @param toAdress Quien recibe el correo
     * @param fromAdress Quien escribe el correo
     * @param fromAdressPass Contraseña de quien escribe el correo
     * @param subject Asunto del correo
     * @param subjectMsg Descripcion general del correo
     */
    public static void sendEmail(String toAdress, String fromAdress, String fromAdressPass, String subject, String subjectMsg) {
//        String host = "localhost";
        Properties props = new Properties();
//        props.setProperty("mail.smtp.host", host); 
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
//        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
//        props.put("mail.transport.protocol", "smtps");
//        Session session  = Session.getDefaultInstance(props, null);
        final String fromAdressVal = fromAdress;
        final String fromAdressPassVal = fromAdressPass;

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromAdressVal, fromAdressPassVal);
            }
        });

        String msgBody = subjectMsg;

        try {
            MimeMultipart multipart  = new MimeMultipart("related");
            MimeBodyPart messageBodyPart = new MimeBodyPart();
//            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(msgBody,"UTF-8","html");
            multipart.addBodyPart(messageBodyPart);
            
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromAdress, ""));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(toAdress, ""));
            msg.setSubject(subject);
//            msg.setContent(multipart, "text/html; charset=utf-8"); 
//            msg.setContent(multipart, "text/html"); 
            msg.setContent(multipart); 
            Transport.send(msg);
            
//            Message msg = new MimeMessage(session);
//            msg.setFrom(new InternetAddress(fromAdress, ""));
//            msg.addRecipient(Message.RecipientType.TO,
//                    new InternetAddress(toAdress, ""));
//            msg.setSubject(subject);
//            msg.setText(msgBody);
//            Transport.send(msg);

        } catch (AddressException e) {
            e.printStackTrace();
            // ...
        } catch (MessagingException e) {
            e.printStackTrace();
            // ...
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // ...
        }
    }

    /**
     * Encargado de generar transformar la contraseña en codigo MD5
     *
     * @param password Password en formato original
     * @return contraseña transformado en formato MD5
     */
    public static String generateMD5(String password) {
        String passwordToHash = password;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
    
    /**
     * Encargado de generar transformar la contraseña en codigo SHA1
     *
     * @param password Password en formato original
     * @param salt Semilla ingresada
     * @return contraseña transformado en formato MD5
     */
    public static String generateSHA1(String password) {
        String passwordToHash = password;
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
//            md.update(salt.getBytes());
            md.update(passwordToHash.getBytes());
//            byte[] bytes = md.digest(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public static String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }
    

    /**
     * Encargado de generar la descripcion del correo en formato HTML, que va a
     * ser al usuario cuando es un agronomo o gremio
     *
     * @param host Nombre del host en que se encuentra el usuario
     * @param nameUser Nombre del usuario registrado en el sistema
     * verificar un nuevo usuario
     * @return representacion del mensaje en HTML
     */
    public static String messageToValidateUser(String host, String nameUser) {
        String msg = "<html> \n"
                + "<body> \n"   
                + "<h3>El Usuario: " + nameUser + "</h3> \n"
                + "<p>Se requiere validar para el ingreso a la plataforma AEPS.</p> \n"
                + "<p>Proceder con la respectiva validacion y enviar una respuesta oportuna al usuario.</p> "
                + "</body> \n"
                + "</html>";
        return msg;
    }
    
    
    /**
     * Encargado de generar la descripcion del correo en formato HTML, que va a
     * ser al usuario cuando este se crea por primera vez
     *
     * @param host Nombre del host en que se encuentra el usuario
     * @param nameUser Nombre del usuario registrado en el sistema
     * @param codValidation Codigo de validacion generado por el sistema para
     * verificar un nuevo usuario
     * @return representacion del mensaje en HTML
     */
    public static String messageToNewUser(String host, String nameUser, String codValidation) {
        String msg = "<html> \n"
//                + "<head> \n"
//                + "<title>Validación de usuario registrado</title> \n"
//                + "</head> \n"
                + "<body> \n"   
                + "<h3>Hola Usuario: " + nameUser + "</h3> \n"
                + "<p>Bienvenido a la plataforma AEPS.</p> \n"
                + "<p>Para validar su registro por favor dar click en el siguiente enlace:</p> "
                + "<a href='http://"+host+":8080/verifyUser.action?codVal=" + codValidation + "&nameUser=" + nameUser + "'>http://"+host+":8080/verifyUser.action?codVal=" + codValidation + "&nameUser=" + nameUser + "</a>\n"
//                + "http://"+host+":8083/verifyUser.action?codVal=" + codValidation + "&nameUser=" + nameUser + " \n"
                + "<p>Si usted no se ha registrado a este sistema por favor ignorar este mensaje</p> "
                + "</body> \n"
                + "</html>";
        return msg;
    }
    
    /**
     * Encargado de generar la descripcion del correo en formato HTML, que va a
     * ser al usuario cuando se va a recuperar una contraseña
     *
     * @param host Nombre del host en que se encuentra el usuario
     * @param nameUser Nombre del usuario registrado en el sistema
     * @param codValidation Codigo de validacion generado por el sistema para
     * restaurar un usuario
     * @return representacion del mensaje en HTML
     */
    public static String messageToRestoreUser(String host, String nameUser, String codValidation) {
        String msg = "<html> \n"
//                + "<head> \n"
//                + "<title>Validación de usuario registrado</title> \n"
//                + "</head> \n"
                + "<body> \n"
                + "<h3>Hola Usuario: " + nameUser + "</h3> \n"
                + "<p>Para poder realizar el cambio de contraseña por favor dar click en el siguiente enlace:</p> "
                + "<a href='http://"+host+":8080/verifyUserToRestore.action?codVal=" + codValidation + "&nameUser=" + nameUser + "'>http://"+host+":8080/verifyUserToRestore.action?codVal=" + codValidation + "&nameUser=" + nameUser + "</a> \n"
                + "</body> \n"
                + "</html>";
        return msg;
    }
    
    /**
     * Encargado de enviar al correo del administrador las inquietudes generadas por el usuario
     *
     * @param nameUser Nombre del usuario que ha enviado la solicitud
     * @param emailUser Email del usuario que ha enviado la solicitud
     * @param textMessageUser Cuerpo del mensaje enviado por el usuario
     * restaurar un usuario
     * @return representacion del mensaje en HTML
     */
    public static String messageToSendContact(String nameUser, String emailUser, String textMessageUser) {
        String msg = "<html> \n"
                + "<body> \n"
                + "<h3>El correo: " + emailUser + "</h3> \n"
                + "<p>Perteneciente a: " + nameUser + "</p> \n"
                + "<p>Envia lo siguiente: " + textMessageUser + "</p> \n"
                + "</body> \n"
                + "</html>";
        return msg;
    }
    
    /**
     * Encargado de enviar al correo del administrador los errores encontrados por el usuario
     *
     * @param nameUser Nombre del usuario que ha enviado la solicitud
     * @param emailUser Email del usuario que ha enviado la solicitud
     * @param subMessageUser Asunto del mensaje enviado por el usuario
     * @param textMessageUser Cuerpo del mensaje enviado por el usuario
     * restaurar un usuario
     * @return representacion del mensaje en HTML
     */
    public static String reportToSend(String nameUser, String emailUser, String subMessageUser, String textMessageUser) {
        String msg = "<html> \n"
                + "<body> \n"
                + "<h3>El usuario: <b>" + nameUser + "</b></h3> \n"
                + "<p>Que cuenta con el correo: " + emailUser + "</p> \n"
                + "<p>Tiene el asunto: <b> " + subMessageUser + "</b></p> \n"
                + "<p>Con lo siguiente: <br /> " + textMessageUser + "</p> \n"
                + "</body> \n"
                + "</html>";
        return msg;
    }
    
    
    /**
    * Función que elimina acentos y caracteres especiales de
    * una cadena de texto.
    * @param input
    * @return cadena de texto limpia de acentos y caracteres especiales.
    */
    public static String remove(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i = 0; i < input.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }
        return output;
    }    
    
    public static String checkDataRasta(String input) {
        String result = "";
        if (input.matches("[0-9]*") || input.equals("ND") || input.equals("NA")) {
            if (input.matches("[0-9]*")) {
                input = input.replace(",", ".");
            }            
            result = input;
        } else {
            result = "\""+GlobalFunctions.remove(input)+"\"";
        }        
        return result;
    }
    
}
