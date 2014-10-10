package org.aepscolombia.platform.util;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
            result = "'"+GlobalFunctions.remove(input)+"'";
        }        
        return result;
    }
    
    public static String getValuesMunicipality(String valFind)
    {
        HashMap valuesForm = new HashMap();
        valuesForm.put("29","263");
        valuesForm.put("1","264");
        valuesForm.put("25","265");
        valuesForm.put("28","266");
        valuesForm.put("2","267");
        valuesForm.put("3","290");
        valuesForm.put("4","268");
        valuesForm.put("5","269");
        valuesForm.put("6","270");
        valuesForm.put("7","271");
        valuesForm.put("26","272");
        valuesForm.put("8","273");
        valuesForm.put("9","274");
        valuesForm.put("12","275");
        valuesForm.put("10","276");
        valuesForm.put("11","277");
        valuesForm.put("30","278");
        valuesForm.put("31","279");
        valuesForm.put("13","280");
        valuesForm.put("14","281");
        valuesForm.put("15","282");
        valuesForm.put("16","283");
        valuesForm.put("17","284");
        valuesForm.put("18","285");
        valuesForm.put("27","286");
        valuesForm.put("19","287");
        valuesForm.put("20","288");
        valuesForm.put("21","289");
        valuesForm.put("22","291");
        valuesForm.put("23","292");
        valuesForm.put("24","262");
        valuesForm.put("32","293");
        valuesForm.put("33","294");
        return valuesForm.get(valFind).toString();
    }
    
    public static String getValuesMunicipalityFarm(String valFind)
    {
        HashMap valuesForm = new HashMap();
        valuesForm.put("29","338");
        valuesForm.put("1","339");
        valuesForm.put("25","340");
        valuesForm.put("28","341");
        valuesForm.put("2","342");
        valuesForm.put("3","343");
        valuesForm.put("4","344");
        valuesForm.put("5","345");
        valuesForm.put("6","346");
        valuesForm.put("7","347");
        valuesForm.put("26","348");
        valuesForm.put("8","349");
        valuesForm.put("9","350");
        valuesForm.put("12","351");
        valuesForm.put("10","352");
        valuesForm.put("11","353");
        valuesForm.put("30","354");
        valuesForm.put("31","355");
        valuesForm.put("13","356");
        valuesForm.put("14","357");
        valuesForm.put("15","358");
        valuesForm.put("16","359");
        valuesForm.put("17","360");
        valuesForm.put("18","361");
        valuesForm.put("27","362");
        valuesForm.put("19","363");
        valuesForm.put("20","364");
        valuesForm.put("21","365");
        valuesForm.put("22","366");
        valuesForm.put("23","367");
        valuesForm.put("24","368");
        valuesForm.put("32","369");
        valuesForm.put("33","370");
        return valuesForm.get(valFind).toString();
    }
    
    public static BasicDBObject generateJSONProducer(HashMap data)
    {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateNow = formateador.format(ahora);
        
        String json1 = "{'_id': 'ObjectId(5403b8de9859f75c6373b548)'}";
        /*String json1 = "{'_id': 'ObjectId(5403b8de9859f75c6373b"+data.get("entId")+")',"+
        " 'form': {"+
        "	 'id': 'NumberInt(4)',"+
        "	 'name': 'Productor',"+
        "	 'description': 'Formulario para agregar/editar productores al sistema',"+
        "	 'del': 'false',"+
        "	 'qid': '92',"+
        "	 'main_table': 'entities',"+
        "	 'HiddenFields': ["+
        "		 {"+
        "			 'id_survey_hidden_field': '6',"+
        "			 'target_table': 'entities',"+
        "			 'target_column': 'entity_type_ent',"+
        "			 'value': '2'"+
        "		},"+
        "		 {"+
        "			 'id_survey_hidden_field': '7',"+
        "			 'target_table': 'entities',"+
        "			 'target_column': 'status',"+
        "			 'value': '1'"+
        "		},"+
        "		 {"+
        "			 'id_survey_hidden_field': '8',"+
        "			 'target_table': 'producers',"+
        "			 'target_column': 'status',"+
        "			 'value': '1'"+
        "		}"+
        "	],"+
        "	 'Questions': ["+
        "		 {"+
        "			 'id': 'NumberInt(90)',"+
        "			 'name': 'Tipo de documento',"+
        "			 'help': 'Ingrese el tipo de documento del productor',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(1)',"+
        "			 'is_required': 'true',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(10)',"+
        "			 'widget': 'NumberInt(1)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'document_type_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			 },"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': 'CC',"+
        "					 'name': 'Cedula de Ciudadania',"+
        "					 'position': 'NumberInt(1)'"+
        "				},"+
        "				 {"+
        "					 'id': 'CE',"+
        "					 'name': 'Cedula de Extranjeria',"+
        "					 'position': 'NumberInt(2)'"+
        "				},"+
        "				 {"+
        "					 'id': 'NIT',"+
        "					 'name': 'Numero de Identificacion Tributaria',"+
        "					 'position': 'NumberInt(3)'"+
        "				},"+
        "				 {"+
        "					 'id': 'PS',"+
        "					 'name': 'Pasaporte',"+
        "					 'position': 'NumberInt(4)'"+
        "				},"+
        "				 {"+
        "					 'id': 'RC',"+
        "					 'name': 'Registro Civil',"+
        "					 'position': 'NumberInt(5)'"+
        "				}"+
        "			]"+
        "		}]"+*/
        /*"		 {"+
        "			 'id': 'NumberInt(91)',"+
        "			 'name': 'Numero de documento',"+
        "			 'help': 'Ingrese el numero de documento del productor',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(2)',"+
        "			 'is_required': 'true',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(1)',"+
        "			 'widget': 'NumberInt(6)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'document_'Number_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(260'),"+
        "			 'name': 'Dígito de verificacion',"+
        "			 'help': '',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(3)',"+
        "			 'is_required': 'true',"+
        "			 'is_filterable': 'false',"+
        "			 'data_type': 'NumberInt(2)',"+
        "			 'widget': 'NumberInt(6)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'validation_'Number_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(295'),"+
        "			 'name': 'Nombre de la empresa',"+
        "			 'help': '',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(4)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(1)',"+
        "			 'widget': 'NumberInt(6)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'name_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(92)',"+
        "			 'name': 'Primer nombre',"+
        "			 'help': 'Ingrese el primer nombre del productor',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(5)',"+
        "			 'is_required': 'true',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(1)',"+
        "			 'widget': 'NumberInt(6)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'first_name_1_ent',"+
        "				 'target_column_lng': 'null',"+
        "				 'target_column_alt': 'null',"+
        "				 'target_column_self': 'null'"+
        "			},"+
        "			 'Choices': ["+
        "				"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(93)',"+
        "			 'name': 'Segundo nombre',"+
        "			 'help': 'Ingrese el segundo nombre del productor',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(6)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'false',"+
        "			 'data_type': 'NumberInt(1)',"+
        "			 'widget': 'NumberInt(6)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'first_name_2_ent',"+
        "				 'target_column_lng': 'null',"+
        "				 'target_column_alt': 'null',"+
        "				 'target_column_self': 'null'"+
        "			},"+
        "			 'Choices': ["+
        "				"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(94)',"+
        "			 'name': 'Primer apellido',"+
        "			 'help': 'Ingrese el primer apellido del productor',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(6)',"+
        "			 'is_required': 'true',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(1)',"+
        "			 'widget': 'NumberInt(6)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'last_name_1_ent',"+
        "				 'target_column_lng': 'null',"+
        "				 'target_column_alt': 'null',"+
        "				 'target_column_self': 'null'"+
        "			},"+
        "			 'Choices': ["+
        "				"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(95)',"+
        "			 'name': 'Segundo apellido',"+
        "			 'help': 'Ingrese el segundo apellido del productor',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(7)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'false',"+
        "			 'data_type': 'NumberInt(1)',"+
        "			 'widget': 'NumberInt(6)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'last_name_2_ent',"+
        "				 'target_column_lng': 'null',"+
        "				 'target_column_alt': 'null',"+
        "				 'target_column_self': 'null'"+
        "			},"+
        "			 'Choices': ["+
        "				"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(99)',"+
        "			 'name': 'Telefono fijo',"+
        "			 'help': '',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(8)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'false',"+
        "			 'data_type': 'NumberInt(2)',"+
        "			 'widget': 'NumberInt(6)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': {"+
        "				 'min': 'NumberInt(1)',"+
        "				 'max': 'NumberInt(999'9999)"+
        "			},"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'phone_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(251'),"+
        "			 'name': 'Celular',"+
        "			 'help': '',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(9)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'false',"+
        "			 'data_type': 'NumberInt(2)',"+
        "			 'widget': 'NumberInt(6)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': {"+
        "				 'min': 'NumberInt(1)',"+
        "				 'max': 'NumberInt(141'0065407)"+
        "			},"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'cellphone_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(252'),"+
        "			 'name': 'Correo electrónico',"+
        "			 'help': '',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(10)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'false',"+
        "			 'data_type': 'NumberInt(4)',"+
        "			 'widget': 'NumberInt(6)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'email_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(261'),"+
        "			 'name': 'Departamento',"+
        "			 'help': '',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(11)',"+
        "			 'is_required': 'true',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(10)',"+
        "			 'widget': 'NumberInt(1)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'page_link_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '29',"+
        "					 'name': 'AMAZONAS',"+
        "					 'position': 'NumberInt(1)'"+
        "				},"+
        "				 {"+
        "					 'id': '1',"+
        "					 'name': 'ANTIOQUIA',"+
        "					 'position': 'NumberInt(2)'"+
        "				},"+
        "				 {"+
        "					 'id': '25',"+
        "					 'name': 'ARAUCA',"+
        "					 'position': 'NumberInt(3)'"+
        "				},"+
        "				 {"+
        "					 'id': '28',"+
        "					 'name': 'ARCHIPIÉLAGO DE SAN ANDRÉS',"+
        "					 'position': 'NumberInt(4)'"+
        "				},"+
        "				 {"+
        "					 'id': '2',"+
        "					 'name': 'ATLÁNTICO',"+
        "					 'position': 'NumberInt(5)'"+
        "				},"+
        "				 {"+
        "					 'id': '3',"+
        "					 'name': 'BOGOTÁ',"+
        "					 'position': 'NumberInt(6)'"+
        "				},"+
        "				 {"+
        "					 'id': '4',"+
        "					 'name': 'BOLÍVAR',"+
        "					 'position': 'NumberInt(7)'"+
        "				},"+
        "				 {"+
        "					 'id': '5',"+
        "					 'name': 'BOYACÁ',"+
        "					 'position': 'NumberInt(8)'"+
        "				},"+
        "				 {"+
        "					 'id': '6',"+
        "					 'name': 'CALDAS',"+
        "					 'position': 'NumberInt(9)'"+
        "				},"+
        "				 {"+
        "					 'id': '7',"+
        "					 'name': 'CAQUETÁ',"+
        "					 'position': 'NumberInt(10)'"+
        "				},"+
        "				 {"+
        "					 'id': '26',"+
        "					 'name': 'CASANARE',"+
        "					 'position': 'NumberInt(11)'"+
        "				},"+
        "				 {"+
        "					 'id': '8',"+
        "					 'name': 'CAUCA',"+
        "					 'position': 'NumberInt(12)'"+
        "				},"+
        "				 {"+
        "					 'id': '9',"+
        "					 'name': 'CESAR',"+
        "					 'position': 'NumberInt(13)'"+
        "				},"+
        "				 {"+
        "					 'id': '12',"+
        "					 'name': 'CHOCÓ',"+
        "					 'position': 'NumberInt(14)'"+
        "				},"+
        "				 {"+
        "					 'id': '10',"+
        "					 'name': 'CÓRDOBA',"+
        "					 'position': 'NumberInt(15)'"+
        "				},"+
        "				 {"+
        "					 'id': '11',"+
        "					 'name': 'CUNDINAMARCA',"+
        "					 'position': 'NumberInt(16)'"+
        "				},"+
        "				 {"+
        "					 'id': '30',"+
        "					 'name': 'GUAINÍA',"+
        "					 'position': 'NumberInt(17)'"+
        "				},"+
        "				 {"+
        "					 'id': '31',"+
        "					 'name': 'GUAVIARE',"+
        "					 'position': 'NumberInt(18)'"+
        "				},"+
        "				 {"+
        "					 'id': '13',"+
        "					 'name': 'HUILA',"+
        "					 'position': 'NumberInt(19)'"+
        "				},"+
        "				 {"+
        "					 'id': '14',"+
        "					 'name': 'LA GUAJIRA',"+
        "					 'position': 'NumberInt(20)'"+
        "				},"+
        "				 {"+
        "					 'id': '15',"+
        "					 'name': 'MAGDALENA',"+
        "					 'position': 'NumberInt(21)'"+
        "				},"+
        "				 {"+
        "					 'id': '16',"+
        "					 'name': 'META',"+
        "					 'position': 'NumberInt(22)'"+
        "				},"+
        "				 {"+
        "					 'id': '17',"+
        "					 'name': 'NARIÑO',"+
        "					 'position': 'NumberInt(23)'"+
        "				},"+
        "				 {"+
        "					 'id': '18',"+
        "					 'name': 'NORTE DE SANTANDER',"+
        "					 'position': 'NumberInt(24)'"+
        "				},"+
        "				 {"+
        "					 'id': '27',"+
        "					 'name': 'PUTUMAYO',"+
        "					 'position': 'NumberInt(25)'"+
        "				},"+
        "				 {"+
        "					 'id': '19',"+
        "					 'name': 'QUINDIO',"+
        "					 'position': 'NumberInt(26)'"+
        "				},"+
        "				 {"+
        "					 'id': '20',"+
        "					 'name': 'RISARALDA',"+
        "					 'position': 'NumberInt(27)'"+
        "				},"+
        "				 {"+
        "					 'id': '21',"+
        "					 'name': 'SANTANDER',"+
        "					 'position': 'NumberInt(28)'"+
        "				},"+
        "				 {"+
        "					 'id': '22',"+
        "					 'name': 'SUCRE',"+
        "					 'position': 'NumberInt(29)'"+
        "				},"+
        "				 {"+
        "					 'id': '23',"+
        "					 'name': 'TOLIMA',"+
        "					 'position': 'NumberInt(30)'"+
        "				},"+
        "				 {"+
        "					 'id': '24',"+
        "					 'name': 'VALLE DEL CAUCA',"+
        "					 'position': 'NumberInt(31)'"+
        "				},"+
        "				 {"+
        "					 'id': '32',"+
        "					 'name': 'VAUPÉS',"+
        "					 'position': 'NumberInt(32)'"+
        "				},"+
        "				 {"+
        "					 'id': '33',"+
        "					 'name': 'VICHADA',"+
        "					 'position': 'NumberInt(33)'"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(263'),"+
        "			 'name': 'Municipio (Amazonas)',"+
        "			 'help': 'Elige el municipio de Amazonas',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(12)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(10)',"+
        "			 'widget': 'NumberInt(1)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1089',"+
        "					 'name': 'LETICIA',"+
        "					 'position': 'NumberInt(1)'"+
        "				},"+
        "				 {"+
        "					 'id': '1090',"+
        "					 'name': 'EL ENCANTO',"+
        "					 'position': 'NumberInt(2)'"+
        "				},"+
        "				 {"+
        "					 'id': '1091',"+
        "					 'name': 'LA CHORRERA',"+
        "					 'position': 'NumberInt(3)'"+
        "				},"+
        "				 {"+
        "					 'id': '1092',"+
        "					 'name': 'LA PEDRERA',"+
        "					 'position': 'NumberInt(4)'"+
        "				},"+
        "				 {"+
        "					 'id': '1093',"+
        "					 'name': 'LA VICTORIA',"+
        "					 'position': 'NumberInt(5)'"+
        "				},"+
        "				 {"+
        "					 'id': '1094',"+
        "					 'name': 'MIRITÍ - PARANÁ',"+
        "					 'position': 'NumberInt(6)'"+
        "				},"+
        "				 {"+
        "					 'id': '1095',"+
        "					 'name': 'PUERTO ALEGRÍA',"+
        "					 'position': 'NumberInt(7)'"+
        "				},"+
        "				 {"+
        "					 'id': '1096',"+
        "					 'name': 'PUERTO ARICA',"+
        "					 'position': 'NumberInt(8)'"+
        "				},"+
        "				 {"+
        "					 'id': '1097',"+
        "					 'name': 'PUERTO NARIÑO',"+
        "					 'position': 'NumberInt(9)'"+
        "				},"+
        "				 {"+
        "					 'id': '1098',"+
        "					 'name': 'PUERTO SANTANDER',"+
        "					 'position': 'NumberInt(10)'"+
        "				},"+
        "				 {"+
        "					 'id': '1099',"+
        "					 'name': 'TARAPACÁ',"+
        "					 'position': 'NumberInt(11)'"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(264'),"+
        "			 'name': 'Municipio (Antioquia)',"+
        "			 'help': 'Elige el municipio de Antioquia',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(13)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(10)',"+
        "			 'widget': 'NumberInt(1)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1',"+
        "					 'name': 'MEDELLÍN',"+
        "					 'position': 'NumberInt(1)'"+
        "				},"+
        "				 {"+
        "					 'id': '2',"+
        "					 'name': 'ABEJORRAL',"+
        "					 'position': 'NumberInt(2)'"+
        "				},"+
        "				 {"+
        "					 'id': '3',"+
        "					 'name': 'ABRIAQUÍ',"+
        "					 'position': 'NumberInt(3)'"+
        "				},"+
        "				 {"+
        "					 'id': '4',"+
        "					 'name': 'ALEJANDRÍA',"+
        "					 'position': 'NumberInt(4)'"+
        "				},"+
        "				 {"+
        "					 'id': '5',"+
        "					 'name': 'AMAGÁ',"+
        "					 'position': 'NumberInt(5)'"+
        "				},"+
        "				 {"+
        "					 'id': '6',"+
        "					 'name': 'AMALFI',"+
        "					 'position': 'NumberInt(6)'"+
        "				},"+
        "				 {"+
        "					 'id': '7',"+
        "					 'name': 'ANDES',"+
        "					 'position': 'NumberInt(7)'"+
        "				},"+
        "				 {"+
        "					 'id': '8',"+
        "					 'name': 'ANGELÓPOLIS',"+
        "					 'position': 'NumberInt(8)'"+
        "				},"+
        "				 {"+
        "					 'id': '9',"+
        "					 'name': 'ANGOSTURA',"+
        "					 'position': 'NumberInt(9)'"+
        "				},"+
        "				 {"+
        "					 'id': '10',"+
        "					 'name': 'ANORÍ',"+
        "					 'position': 'NumberInt(10)'"+
        "				},"+
        "				 {"+
        "					 'id': '11',"+
        "					 'name': 'SANTA FÉ DE ANTIOQUIA',"+
        "					 'position': 'NumberInt(11)'"+
        "				},"+
        "				 {"+
        "					 'id': '12',"+
        "					 'name': 'ANZÁ',"+
        "					 'position': 'NumberInt(12)'"+
        "				},"+
        "				 {"+
        "					 'id': '13',"+
        "					 'name': 'APARTADÓ',"+
        "					 'position': 'NumberInt(13)'"+
        "				},"+
        "				 {"+
        "					 'id': '14',"+
        "					 'name': 'ARBOLETES',"+
        "					 'position': 'NumberInt(14)'"+
        "				},"+
        "				 {"+
        "					 'id': '15',"+
        "					 'name': 'ARGELIA',"+
        "					 'position': 'NumberInt(15)'"+
        "				},"+
        "				 {"+
        "					 'id': '16',"+
        "					 'name': 'ARMENIA',"+
        "					 'position': 'NumberInt(16)'"+
        "				},"+
        "				 {"+
        "					 'id': '17',"+
        "					 'name': 'BARBOSA',"+
        "					 'position': 'NumberInt(17)'"+
        "				},"+
        "				 {"+
        "					 'id': '18',"+
        "					 'name': 'BELMIRA',"+
        "					 'position': 'NumberInt(18)'"+
        "				},"+
        "				 {"+
        "					 'id': '19',"+
        "					 'name': 'BELLO',"+
        "					 'position': 'NumberInt(19)'"+
        "				},"+
        "				 {"+
        "					 'id': '20',"+
        "					 'name': 'BETANIA',"+
        "					 'position': 'NumberInt(20)'"+
        "				},"+
        "				 {"+
        "					 'id': '21',"+
        "					 'name': 'BETULIA',"+
        "					 'position': 'NumberInt(21)'"+
        "				},"+
        "				 {"+
        "					 'id': '22',"+
        "					 'name': 'CIUDAD BOLÍVAR',"+
        "					 'position': 'NumberInt(22)'"+
        "				},"+
        "				 {"+
        "					 'id': '23',"+
        "					 'name': 'BRICEÑO',"+
        "					 'position': 'NumberInt(23)'"+
        "				},"+
        "				 {"+
        "					 'id': '24',"+
        "					 'name': 'BURITICÁ',"+
        "					 'position': 'NumberInt(24)'"+
        "				},"+
        "				 {"+
        "					 'id': '25',"+
        "					 'name': 'CÁCERES',"+
        "					 'position': 'NumberInt(25)'"+
        "				},"+
        "				 {"+
        "					 'id': '26',"+
        "					 'name': 'CAICEDO',"+
        "					 'position': 'NumberInt(26)'"+
        "				},"+
        "				 {"+
        "					 'id': '27',"+
        "					 'name': 'CALDAS',"+
        "					 'position': 'NumberInt(27)'"+
        "				},"+
        "				 {"+
        "					 'id': '28',"+
        "					 'name': 'CAMPAMENTO',"+
        "					 'position': 'NumberInt(28)'"+
        "				},"+
        "				 {"+
        "					 'id': '29',"+
        "					 'name': 'CAÑASGORDAS',"+
        "					 'position': 'NumberInt(29)'"+
        "				},"+
        "				 {"+
        "					 'id': '30',"+
        "					 'name': 'CARACOLÍ',"+
        "					 'position': 'NumberInt(30)'"+
        "				},"+
        "				 {"+
        "					 'id': '31',"+
        "					 'name': 'CARAMANTA',"+
        "					 'position': 'NumberInt(31)'"+
        "				},"+
        "				 {"+
        "					 'id': '32',"+
        "					 'name': 'CAREPA',"+
        "					 'position': 'NumberInt(32)'"+
        "				},"+
        "				 {"+
        "					 'id': '33',"+
        "					 'name': 'EL CARMEN DE VIBORAL',"+
        "					 'position': 'NumberInt(33)'"+
        "				},"+
        "				 {"+
        "					 'id': '34',"+
        "					 'name': 'CAROLINA',"+
        "					 'position': 'NumberInt(34)'"+
        "				},"+
        "				 {"+
        "					 'id': '35',"+
        "					 'name': 'CAUCASIA',"+
        "					 'position': 'NumberInt(35)'"+
        "				},"+
        "				 {"+
        "					 'id': '36',"+
        "					 'name': 'CHIGORODÓ',"+
        "					 'position': 'NumberInt(36)'"+
        "				},"+
        "				 {"+
        "					 'id': '37',"+
        "					 'name': 'CISNEROS',"+
        "					 'position': 'NumberInt(37)'"+
        "				},"+
        "				 {"+
        "					 'id': '38',"+
        "					 'name': 'COCORNÁ',"+
        "					 'position': 'NumberInt(38)'"+
        "				},"+
        "				 {"+
        "					 'id': '39',"+
        "					 'name': 'CONCEPCIÓN',"+
        "					 'position': 'NumberInt(39)'"+
        "				},"+
        "				 {"+
        "					 'id': '40',"+
        "					 'name': 'CONCORDIA',"+
        "					 'position': 'NumberInt(40)'"+
        "				},"+
        "				 {"+
        "					 'id': '41',"+
        "					 'name': 'COPACABANA',"+
        "					 'position': 'NumberInt(41)'"+
        "				},"+
        "				 {"+
        "					 'id': '42',"+
        "					 'name': 'DABEIBA',"+
        "					 'position': 'NumberInt(42)'"+
        "				},"+
        "				 {"+
        "					 'id': '43',"+
        "					 'name': 'DONMATÍAS',"+
        "					 'position': 'NumberInt(43)'"+
        "				},"+
        "				 {"+
        "					 'id': '44',"+
        "					 'name': 'EBÉJICO',"+
        "					 'position': 'NumberInt(44)'"+
        "				},"+
        "				 {"+
        "					 'id': '45',"+
        "					 'name': 'EL BAGRE',"+
        "					 'position': 'NumberInt(45)'"+
        "				},"+
        "				 {"+
        "					 'id': '46',"+
        "					 'name': 'ENTRERRÍOS',"+
        "					 'position': 'NumberInt(46)'"+
        "				},"+
        "				 {"+
        "					 'id': '47',"+
        "					 'name': 'ENVIGADO',"+
        "					 'position': 'NumberInt(47)'"+
        "				},"+
        "				 {"+
        "					 'id': '48',"+
        "					 'name': 'FREDONIA',"+
        "					 'position': 'NumberInt(48)'"+
        "				},"+
        "				 {"+
        "					 'id': '49',"+
        "					 'name': 'FRONTINO',"+
        "					 'position': 'NumberInt(49)'"+
        "				},"+
        "				 {"+
        "					 'id': '50',"+
        "					 'name': 'GIRALDO',"+
        "					 'position': 'NumberInt(50)'"+
        "				},"+
        "				 {"+
        "					 'id': '51',"+
        "					 'name': 'GIRARDOTA',"+
        "					 'position': 'NumberInt(51)'"+
        "				},"+
        "				 {"+
        "					 'id': '52',"+
        "					 'name': 'GÓMEZ PLATA',"+
        "					 'position': 'NumberInt(52)'"+
        "				},"+
        "				 {"+
        "					 'id': '53',"+
        "					 'name': 'GRANADA',"+
        "					 'position': 'NumberInt(53)'"+
        "				},"+
        "				 {"+
        "					 'id': '54',"+
        "					 'name': 'GUADALUPE',"+
        "					 'position': 'NumberInt(54)'"+
        "				},"+
        "				 {"+
        "					 'id': '55',"+
        "					 'name': 'GUARNE',"+
        "					 'position': 'NumberInt(55)'"+
        "				},"+
        "				 {"+
        "					 'id': '56',"+
        "					 'name': 'GUATAPÉ',"+
        "					 'position': 'NumberInt(56)'"+
        "				},"+
        "				 {"+
        "					 'id': '57',"+
        "					 'name': 'HELICONIA',"+
        "					 'position': 'NumberInt(57)'"+
        "				},"+
        "				 {"+
        "					 'id': '58',"+
        "					 'name': 'HISPANIA',"+
        "					 'position': 'NumberInt(58)'"+
        "				},"+
        "				 {"+
        "					 'id': '59',"+
        "					 'name': 'ITAGÜÍ',"+
        "					 'position': 'NumberInt(59)'"+
        "				},"+
        "				 {"+
        "					 'id': '60',"+
        "					 'name': 'ITUANGO',"+
        "					 'position': 'NumberInt(60)'"+
        "				},"+
        "				 {"+
        "					 'id': '61',"+
        "					 'name': 'JARDÍN',"+
        "					 'position': 'NumberInt(61)'"+
        "				},"+
        "				 {"+
        "					 'id': '62',"+
        "					 'name': 'JERICÓ',"+
        "					 'position': 'NumberInt(62)'"+
        "				},"+
        "				 {"+
        "					 'id': '63',"+
        "					 'name': 'LA CEJA',"+
        "					 'position': 'NumberInt(63)'"+
        "				},"+
        "				 {"+
        "					 'id': '64',"+
        "					 'name': 'LA ESTRELLA',"+
        "					 'position': 'NumberInt(64)'"+
        "				},"+
        "				 {"+
        "					 'id': '65',"+
        "					 'name': 'LA PINTADA',"+
        "					 'position': 'NumberInt(65)'"+
        "				},"+
        "				 {"+
        "					 'id': '66',"+
        "					 'name': 'LA UNIÓN',"+
        "					 'position': 'NumberInt(66)'"+
        "				},"+
        "				 {"+
        "					 'id': '67',"+
        "					 'name': 'LIBORINA',"+
        "					 'position': 'NumberInt(67)'"+
        "				},"+
        "				 {"+
        "					 'id': '68',"+
        "					 'name': 'MACEO',"+
        "					 'position': 'NumberInt(68)'"+
        "				},"+
        "				 {"+
        "					 'id': '69',"+
        "					 'name': 'MARINILLA',"+
        "					 'position': 'NumberInt(69)'"+
        "				},"+
        "				 {"+
        "					 'id': '70',"+
        "					 'name': 'MONTEBELLO',"+
        "					 'position': 'NumberInt(70)'"+
        "				},"+
        "				 {"+
        "					 'id': '71',"+
        "					 'name': 'MURINDÓ',"+
        "					 'position': 'NumberInt(71)'"+
        "				},"+
        "				 {"+
        "					 'id': '72',"+
        "					 'name': 'MUTATÁ',"+
        "					 'position': 'NumberInt(72)'"+
        "				},"+
        "				 {"+
        "					 'id': '73',"+
        "					 'name': 'NARIÑO',"+
        "					 'position': 'NumberInt(73)'"+
        "				},"+
        "				 {"+
        "					 'id': '74',"+
        "					 'name': 'NECOCLÍ',"+
        "					 'position': 'NumberInt(74)'"+
        "				},"+
        "				 {"+
        "					 'id': '75',"+
        "					 'name': 'NECHÍ',"+
        "					 'position': 'NumberInt(75)'"+
        "				},"+
        "				 {"+
        "					 'id': '76',"+
        "					 'name': 'OLAYA',"+
        "					 'position': 'NumberInt(76)'"+
        "				},"+
        "				 {"+
        "					 'id': '77',"+
        "					 'name': 'PEÑOL',"+
        "					 'position': 'NumberInt(77)'"+
        "				},"+
        "				 {"+
        "					 'id': '78',"+
        "					 'name': 'PEQUE',"+
        "					 'position': 'NumberInt(78)'"+
        "				},"+
        "				 {"+
        "					 'id': '79',"+
        "					 'name': 'PUEBLORRICO',"+
        "					 'position': 'NumberInt(79)'"+
        "				},"+
        "				 {"+
        "					 'id': '80',"+
        "					 'name': 'PUERTO BERRÍO',"+
        "					 'position': 'NumberInt(80)'"+
        "				},"+
        "				 {"+
        "					 'id': '81',"+
        "					 'name': 'PUERTO NARE',"+
        "					 'position': 'NumberInt(81)'"+
        "				},"+
        "				 {"+
        "					 'id': '82',"+
        "					 'name': 'PUERTO TRIUNFO',"+
        "					 'position': 'NumberInt(82)'"+
        "				},"+
        "				 {"+
        "					 'id': '83',"+
        "					 'name': 'REMEDIOS',"+
        "					 'position': 'NumberInt(83)'"+
        "				},"+
        "				 {"+
        "					 'id': '84',"+
        "					 'name': 'RETIRO',"+
        "					 'position': 'NumberInt(84)'"+
        "				},"+
        "				 {"+
        "					 'id': '85',"+
        "					 'name': 'RIONEGRO',"+
        "					 'position': 'NumberInt(85)'"+
        "				},"+
        "				 {"+
        "					 'id': '86',"+
        "					 'name': 'SABANALARGA',"+
        "					 'position': 'NumberInt(86)'"+
        "				},"+
        "				 {"+
        "					 'id': '87',"+
        "					 'name': 'SABANETA',"+
        "					 'position': 'NumberInt(87)'"+
        "				},"+
        "				 {"+
        "					 'id': '88',"+
        "					 'name': 'SALGAR',"+
        "					 'position': 'NumberInt(88)'"+
        "				},"+
        "				 {"+
        "					 'id': '89',"+
        "					 'name': 'SAN ANDRÉS DE CUERQUÍA',"+
        "					 'position': 'NumberInt(89)'"+
        "				},"+
        "				 {"+
        "					 'id': '90',"+
        "					 'name': 'SAN CARLOS',"+
        "					 'position': 'NumberInt(90)'"+
        "				},"+
        "				 {"+
        "					 'id': '91',"+
        "					 'name': 'SAN FRANCISCO',"+
        "					 'position': 'NumberInt(91)'"+
        "				},"+
        "				 {"+
        "					 'id': '92',"+
        "					 'name': 'SAN JERÓNIMO',"+
        "					 'position': 'NumberInt(92)'"+
        "				},"+
        "				 {"+
        "					 'id': '93',"+
        "					 'name': 'SAN JOSÉ DE LA MONTAÑA',"+
        "					 'position': 'NumberInt(93)'"+
        "				},"+
        "				 {"+
        "					 'id': '94',"+
        "					 'name': 'SAN JUAN DE URABÁ',"+
        "					 'position': 'NumberInt(94)'"+
        "				},"+
        "				 {"+
        "					 'id': '95',"+
        "					 'name': 'SAN LUIS',"+
        "					 'position': 'NumberInt(95)'"+
        "				},"+
        "				 {"+
        "					 'id': '96',"+
        "					 'name': 'SAN PEDRO DE LOS MILAGROS',"+
        "					 'position': 'NumberInt(96)'"+
        "				},"+
        "				 {"+
        "					 'id': '97',"+
        "					 'name': 'SAN PEDRO DE URABÁ',"+
        "					 'position': 'NumberInt(97)'"+
        "				},"+
        "				 {"+
        "					 'id': '98',"+
        "					 'name': 'SAN RAFAEL',"+
        "					 'position': 'NumberInt(98)'"+
        "				},"+
        "				 {"+
        "					 'id': '99',"+
        "					 'name': 'SAN ROQUE',"+
        "					 'position': 'NumberInt(99)'"+
        "				},"+
        "				 {"+
        "					 'id': '100',"+
        "					 'name': 'SAN VICENTE FERRER',"+
        "					 'position': 'NumberInt(100')"+
        "				},"+
        "				 {"+
        "					 'id': '101',"+
        "					 'name': 'SANTA BÁRBARA',"+
        "					 'position': 'NumberInt(101')"+
        "				},"+
        "				 {"+
        "					 'id': '102',"+
        "					 'name': 'SANTA ROSA DE OSOS',"+
        "					 'position': 'NumberInt(102')"+
        "				},"+
        "				 {"+
        "					 'id': '103',"+
        "					 'name': 'SANTO DOMINGO',"+
        "					 'position': 'NumberInt(103')"+
        "				},"+
        "				 {"+
        "					 'id': '104',"+
        "					 'name': 'EL SANTUARIO',"+
        "					 'position': 'NumberInt(104')"+
        "				},"+
        "				 {"+
        "					 'id': '105',"+
        "					 'name': 'SEGOVIA',"+
        "					 'position': 'NumberInt(105')"+
        "				},"+
        "				 {"+
        "					 'id': '106',"+
        "					 'name': 'SONSÓN',"+
        "					 'position': 'NumberInt(106')"+
        "				},"+
        "				 {"+
        "					 'id': '107',"+
        "					 'name': 'SOPETRÁN',"+
        "					 'position': 'NumberInt(107')"+
        "				},"+
        "				 {"+
        "					 'id': '108',"+
        "					 'name': 'TÁMESIS',"+
        "					 'position': 'NumberInt(108')"+
        "				},"+
        "				 {"+
        "					 'id': '109',"+
        "					 'name': 'TARAZÁ',"+
        "					 'position': 'NumberInt(109')"+
        "				},"+
        "				 {"+
        "					 'id': '110',"+
        "					 'name': 'TARSO',"+
        "					 'position': 'NumberInt(110')"+
        "				},"+
        "				 {"+
        "					 'id': '111',"+
        "					 'name': 'TITIRIBÍ',"+
        "					 'position': 'NumberInt(111')"+
        "				},"+
        "				 {"+
        "					 'id': '112',"+
        "					 'name': 'TOLEDO',"+
        "					 'position': 'NumberInt(112')"+
        "				},"+
        "				 {"+
        "					 'id': '113',"+
        "					 'name': 'TURBO',"+
        "					 'position': 'NumberInt(113')"+
        "				},"+
        "				 {"+
        "					 'id': '114',"+
        "					 'name': 'URAMITA',"+
        "					 'position': 'NumberInt(114')"+
        "				},"+
        "				 {"+
        "					 'id': '115',"+
        "					 'name': 'URRAO',"+
        "					 'position': 'NumberInt(115')"+
        "				},"+
        "				 {"+
        "					 'id': '116',"+
        "					 'name': 'VALDIVIA',"+
        "					 'position': 'NumberInt(116')"+
        "				},"+
        "				 {"+
        "					 'id': '117',"+
        "					 'name': 'VALPARAÍSO',"+
        "					 'position': 'NumberInt(117')"+
        "				},"+
        "				 {"+
        "					 'id': '118',"+
        "					 'name': 'VEGACHÍ',"+
        "					 'position': 'NumberInt(118')"+
        "				},"+
        "				 {"+
        "					 'id': '119',"+
        "					 'name': 'VENECIA',"+
        "					 'position': 'NumberInt(119')"+
        "				},"+
        "				 {"+
        "					 'id': '120',"+
        "					 'name': 'VIGÍA DEL FUERTE',"+
        "					 'position': 'NumberInt(120')"+
        "				},"+
        "				 {"+
        "					 'id': '121',"+
        "					 'name': 'YALÍ',"+
        "					 'position': 'NumberInt(121')"+
        "				},"+
        "				 {"+
        "					 'id': '122',"+
        "					 'name': 'YARUMAL',"+
        "					 'position': 'NumberInt(122')"+
        "				},"+
        "				 {"+
        "					 'id': '123',"+
        "					 'name': 'YOLOMBÓ',"+
        "					 'position': 'NumberInt(123')"+
        "				},"+
        "				 {"+
        "					 'id': '124',"+
        "					 'name': 'YONDÓ',"+
        "					 'position': 'NumberInt(124')"+
        "				},"+
        "				 {"+
        "					 'id': '125',"+
        "					 'name': 'ZARAGOZA',"+
        "					 'position': 'NumberInt(125')"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(265'),"+
        "			 'name': 'Municipio (Arauca)',"+
        "			 'help': 'Elige el municipio de Arauca',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(14)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(10)',"+
        "			 'widget': 'NumberInt(1)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1048',"+
        "					 'name': 'ARAUCA',"+
        "					 'position': 'NumberInt(1)'"+
        "				},"+
        "				 {"+
        "					 'id': '1049',"+
        "					 'name': 'ARAUQUITA',"+
        "					 'position': 'NumberInt(2)'"+
        "				},"+
        "				 {"+
        "					 'id': '1050',"+
        "					 'name': 'CRAVO NORTE',"+
        "					 'position': 'NumberInt(3)'"+
        "				},"+
        "				 {"+
        "					 'id': '1051',"+
        "					 'name': 'FORTUL',"+
        "					 'position': 'NumberInt(4)'"+
        "				},"+
        "				 {"+
        "					 'id': '1052',"+
        "					 'name': 'PUERTO RONDÓN',"+
        "					 'position': 'NumberInt(5)'"+
        "				},"+
        "				 {"+
        "					 'id': '1053',"+
        "					 'name': 'SARAVENA',"+
        "					 'position': 'NumberInt(6)'"+
        "				},"+
        "				 {"+
        "					 'id': '1054',"+
        "					 'name': 'TAME',"+
        "					 'position': 'NumberInt(7)'"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(266'),"+
        "			 'name': 'Municipio (San Andrés, Providencia, Santa Catalina)',"+
        "			 'help': 'Elige el municipio de San Andrés, Providencia o Santa Catalina',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(15)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(10)',"+
        "			 'widget': 'NumberInt(1)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1087',"+
        "					 'name': 'SAN ANDRÉS',"+
        "					 'position': 'NumberInt(1)'"+
        "				},"+
        "				 {"+
        "					 'id': '1088',"+
        "					 'name': 'PROVIDENCIA',"+
        "					 'position': 'NumberInt(2)'"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(267'),"+
        "			 'name': 'Municipio (Atlántico)',"+
        "			 'help': 'Elige el municipio de Atlántico',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(16)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(10)',"+
        "			 'widget': 'NumberInt(1)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '126',"+
        "					 'name': 'BARRANQUILLA',"+
        "					 'position': 'NumberInt(1)'"+
        "				},"+
        "				 {"+
        "					 'id': '127',"+
        "					 'name': 'BARANOA',"+
        "					 'position': 'NumberInt(2)'"+
        "				},"+
        "				 {"+
        "					 'id': '128',"+
        "					 'name': 'CAMPO DE LA CRUZ',"+
        "					 'position': 'NumberInt(3)'"+
        "				},"+
        "				 {"+
        "					 'id': '129',"+
        "					 'name': 'CANDELARIA',"+
        "					 'position': 'NumberInt(4)'"+
        "				},"+
        "				 {"+
        "					 'id': '130',"+
        "					 'name': 'GALAPA',"+
        "					 'position': 'NumberInt(5)'"+
        "				},"+
        "				 {"+
        "					 'id': '131',"+
        "					 'name': 'JUAN DE ACOSTA',"+
        "					 'position': 'NumberInt(6)'"+
        "				},"+
        "				 {"+
        "					 'id': '132',"+
        "					 'name': 'LURUACO',"+
        "					 'position': 'NumberInt(7)'"+
        "				},"+
        "				 {"+
        "					 'id': '133',"+
        "					 'name': 'MALAMBO',"+
        "					 'position': 'NumberInt(8)'"+
        "				},"+
        "				 {"+
        "					 'id': '134',"+
        "					 'name': 'MANATÍ',"+
        "					 'position': 'NumberInt(9)'"+
        "				},"+
        "				 {"+
        "					 'id': '135',"+
        "					 'name': 'PALMAR DE VARELA',"+
        "					 'position': 'NumberInt(10)'"+
        "				},"+
        "				 {"+
        "					 'id': '136',"+
        "					 'name': 'PIOJÓ',"+
        "					 'position': 'NumberInt(11)'"+
        "				},"+
        "				 {"+
        "					 'id': '137',"+
        "					 'name': 'POLONUEVO',"+
        "					 'position': 'NumberInt(12)'"+
        "				},"+
        "				 {"+
        "					 'id': '138',"+
        "					 'name': 'PONEDERA',"+
        "					 'position': 'NumberInt(13)'"+
        "				},"+
        "				 {"+
        "					 'id': '139',"+
        "					 'name': 'PUERTO COLOMBIA',"+
        "					 'position': 'NumberInt(14)'"+
        "				},"+
        "				 {"+
        "					 'id': '140',"+
        "					 'name': 'REPELÓN',"+
        "					 'position': 'NumberInt(15)'"+
        "				},"+
        "				 {"+
        "					 'id': '141',"+
        "					 'name': 'SABANAGRANDE',"+
        "					 'position': 'NumberInt(16)'"+
        "				},"+
        "				 {"+
        "					 'id': '142',"+
        "					 'name': 'SABANALARGA',"+
        "					 'position': 'NumberInt(17)'"+
        "				},"+
        "				 {"+
        "					 'id': '143',"+
        "					 'name': 'SANTA LUCÍA',"+
        "					 'position': 'NumberInt(18)'"+
        "				},"+
        "				 {"+
        "					 'id': '144',"+
        "					 'name': 'SANTO TOMÁS',"+
        "					 'position': 'NumberInt(19)'"+
        "				},"+
        "				 {"+
        "					 'id': '145',"+
        "					 'name': 'SOLEDAD',"+
        "					 'position': 'NumberInt(20)'"+
        "				},"+
        "				 {"+
        "					 'id': '146',"+
        "					 'name': 'SUAN',"+
        "					 'position': 'NumberInt(21)'"+
        "				},"+
        "				 {"+
        "					 'id': '147',"+
        "					 'name': 'TUBARÁ',"+
        "					 'position': 'NumberInt(22)'"+
        "				},"+
        "				 {"+
        "					 'id': '148',"+
        "					 'name': 'USIACURÍ',"+
        "					 'position': 'NumberInt(23)'"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(268'),"+
        "			 'name': 'Municipio (Bolívar)',"+
        "			 'help': 'Elige el municipio de Bolívar',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(17)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(10)',"+
        "			 'widget': 'NumberInt(1)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '150',"+
        "					 'name': 'CARTAGENA DE INDIAS',"+
        "					 'position': 'NumberInt(1)'"+
        "				},"+
        "				 {"+
        "					 'id': '151',"+
        "					 'name': 'ACHÍ',"+
        "					 'position': 'NumberInt(2)'"+
        "				},"+
        "				 {"+
        "					 'id': '152',"+
        "					 'name': 'ALTOS DEL ROSARIO',"+
        "					 'position': 'NumberInt(3)'"+
        "				},"+
        "				 {"+
        "					 'id': '153',"+
        "					 'name': 'ARENAL',"+
        "					 'position': 'NumberInt(4)'"+
        "				},"+
        "				 {"+
        "					 'id': '154',"+
        "					 'name': 'ARJONA',"+
        "					 'position': 'NumberInt(5)'"+
        "				},"+
        "				 {"+
        "					 'id': '155',"+
        "					 'name': 'ARROYOHONDO',"+
        "					 'position': 'NumberInt(6)'"+
        "				},"+
        "				 {"+
        "					 'id': '156',"+
        "					 'name': 'BARRANCO DE LOBA',"+
        "					 'position': 'NumberInt(7)'"+
        "				},"+
        "				 {"+
        "					 'id': '157',"+
        "					 'name': 'CALAMAR',"+
        "					 'position': 'NumberInt(8)'"+
        "				},"+
        "				 {"+
        "					 'id': '158',"+
        "					 'name': 'CANTAGALLO',"+
        "					 'position': 'NumberInt(9)'"+
        "				},"+
        "				 {"+
        "					 'id': '159',"+
        "					 'name': 'CICUCO',"+
        "					 'position': 'NumberInt(10)'"+
        "				},"+
        "				 {"+
        "					 'id': '160',"+
        "					 'name': 'CÓRDOBA',"+
        "					 'position': 'NumberInt(11)'"+
        "				},"+
        "				 {"+
        "					 'id': '161',"+
        "					 'name': 'CLEMENCIA',"+
        "					 'position': 'NumberInt(12)'"+
        "				},"+
        "				 {"+
        "					 'id': '162',"+
        "					 'name': 'EL CARMEN DE BOLÍVAR',"+
        "					 'position': 'NumberInt(13)'"+
        "				},"+
        "				 {"+
        "					 'id': '163',"+
        "					 'name': 'EL GUAMO',"+
        "					 'position': 'NumberInt(14)'"+
        "				},"+
        "				 {"+
        "					 'id': '164',"+
        "					 'name': 'EL PEÑÓN',"+
        "					 'position': 'NumberInt(15)'"+
        "				},"+
        "				 {"+
        "					 'id': '165',"+
        "					 'name': 'HATILLO DE LOBA',"+
        "					 'position': 'NumberInt(16)'"+
        "				},"+
        "				 {"+
        "					 'id': '166',"+
        "					 'name': 'MAGANGUÉ',"+
        "					 'position': 'NumberInt(17)'"+
        "				},"+
        "				 {"+
        "					 'id': '167',"+
        "					 'name': 'MAHATES',"+
        "					 'position': 'NumberInt(18)'"+
        "				},"+
        "				 {"+
        "					 'id': '168',"+
        "					 'name': 'MARGARITA',"+
        "					 'position': 'NumberInt(19)'"+
        "				},"+
        "				 {"+
        "					 'id': '169',"+
        "					 'name': 'MARÍA LA BAJA',"+
        "					 'position': 'NumberInt(20)'"+
        "				},"+
        "				 {"+
        "					 'id': '170',"+
        "					 'name': 'MONTECRISTO',"+
        "					 'position': 'NumberInt(21)'"+
        "				},"+
        "				 {"+
        "					 'id': '171',"+
        "					 'name': 'MOMPÓS',"+
        "					 'position': 'NumberInt(22)'"+
        "				},"+
        "				 {"+
        "					 'id': '172',"+
        "					 'name': 'MORALES',"+
        "					 'position': 'NumberInt(23)'"+
        "				},"+
        "				 {"+
        "					 'id': '173',"+
        "					 'name': 'NOROSÍ',"+
        "					 'position': 'NumberInt(24)'"+
        "				},"+
        "				 {"+
        "					 'id': '174',"+
        "					 'name': 'PINILLOS',"+
        "					 'position': 'NumberInt(25)'"+
        "				},"+
        "				 {"+
        "					 'id': '175',"+
        "					 'name': 'REGIDOR',"+
        "					 'position': 'NumberInt(26)'"+
        "				},"+
        "				 {"+
        "					 'id': '176',"+
        "					 'name': 'RÍO VIEJO',"+
        "					 'position': 'NumberInt(27)'"+
        "				},"+
        "				 {"+
        "					 'id': '177',"+
        "					 'name': 'SAN CRISTÓBAL',"+
        "					 'position': 'NumberInt(28)'"+
        "				},"+
        "				 {"+
        "					 'id': '178',"+
        "					 'name': 'SAN ESTANISLAO',"+
        "					 'position': 'NumberInt(29)'"+
        "				},"+
        "				 {"+
        "					 'id': '179',"+
        "					 'name': 'SAN FERNANDO',"+
        "					 'position': 'NumberInt(30)'"+
        "				},"+
        "				 {"+
        "					 'id': '180',"+
        "					 'name': 'SAN JACINTO',"+
        "					 'position': 'NumberInt(31)'"+
        "				},"+
        "				 {"+
        "					 'id': '181',"+
        "					 'name': 'SAN JACINTO DEL CAUCA',"+
        "					 'position': 'NumberInt(32)'"+
        "				},"+
        "				 {"+
        "					 'id': '182',"+
        "					 'name': 'SAN JUAN NEPOMUCENO',"+
        "					 'position': 'NumberInt(33)'"+
        "				},"+
        "				 {"+
        "					 'id': '183',"+
        "					 'name': 'SAN MARTÍN DE LOBA',"+
        "					 'position': 'NumberInt(34)'"+
        "				},"+
        "				 {"+
        "					 'id': '184',"+
        "					 'name': 'SAN PABLO',"+
        "					 'position': 'NumberInt(35)'"+
        "				},"+
        "				 {"+
        "					 'id': '185',"+
        "					 'name': 'SANTA CATALINA',"+
        "					 'position': 'NumberInt(36)'"+
        "				},"+
        "				 {"+
        "					 'id': '186',"+
        "					 'name': 'SANTA ROSA',"+
        "					 'position': 'NumberInt(37)'"+
        "				},"+
        "				 {"+
        "					 'id': '187',"+
        "					 'name': 'SANTA ROSA DEL SUR',"+
        "					 'position': 'NumberInt(38)'"+
        "				},"+
        "				 {"+
        "					 'id': '188',"+
        "					 'name': 'SIMITÍ',"+
        "					 'position': 'NumberInt(39)'"+
        "				},"+
        "				 {"+
        "					 'id': '189',"+
        "					 'name': 'SOPLAVIENTO',"+
        "					 'position': 'NumberInt(40)'"+
        "				},"+
        "				 {"+
        "					 'id': '190',"+
        "					 'name': 'TALAIGUA NUEVO',"+
        "					 'position': 'NumberInt(41)'"+
        "				},"+
        "				 {"+
        "					 'id': '191',"+
        "					 'name': 'TIQUISIO',"+
        "					 'position': 'NumberInt(42)'"+
        "				},"+
        "				 {"+
        "					 'id': '192',"+
        "					 'name': 'TURBACO',"+
        "					 'position': 'NumberInt(43)'"+
        "				},"+
        "				 {"+
        "					 'id': '193',"+
        "					 'name': 'TURBANÁ',"+
        "					 'position': 'NumberInt(44)'"+
        "				},"+
        "				 {"+
        "					 'id': '194',"+
        "					 'name': 'VILLANUEVA',"+
        "					 'position': 'NumberInt(45)'"+
        "				},"+
        "				 {"+
        "					 'id': '195',"+
        "					 'name': 'ZAMBRANO',"+
        "					 'position': 'NumberInt(46)'"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(269'),"+
        "			 'name': 'Municipio (Boyaca)',"+
        "			 'help': 'Elige el municipio de Boyaca',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(18)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(10)',"+
        "			 'widget': 'NumberInt(1)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '196',"+
        "					 'name': 'TUNJA',"+
        "					 'position': 'NumberInt(1)'"+
        "				},"+
        "				 {"+
        "					 'id': '197',"+
        "					 'name': 'ALMEIDA',"+
        "					 'position': 'NumberInt(2)'"+
        "				},"+
        "				 {"+
        "					 'id': '198',"+
        "					 'name': 'AQUITANIA',"+
        "					 'position': 'NumberInt(3)'"+
        "				},"+
        "				 {"+
        "					 'id': '199',"+
        "					 'name': 'ARCABUCO',"+
        "					 'position': 'NumberInt(4)'"+
        "				},"+
        "				 {"+
        "					 'id': '200',"+
        "					 'name': 'BELÉN',"+
        "					 'position': 'NumberInt(5)'"+
        "				},"+
        "				 {"+
        "					 'id': '201',"+
        "					 'name': 'BERBEO',"+
        "					 'position': 'NumberInt(6)'"+
        "				},"+
        "				 {"+
        "					 'id': '202',"+
        "					 'name': 'BETÉITIVA',"+
        "					 'position': 'NumberInt(7)'"+
        "				},"+
        "				 {"+
        "					 'id': '203',"+
        "					 'name': 'BOAVITA',"+
        "					 'position': 'NumberInt(8)'"+
        "				},"+
        "				 {"+
        "					 'id': '204',"+
        "					 'name': 'BOYACÁ',"+
        "					 'position': 'NumberInt(9)'"+
        "				},"+
        "				 {"+
        "					 'id': '205',"+
        "					 'name': 'BRICEÑO',"+
        "					 'position': 'NumberInt(10)'"+
        "				},"+
        "				 {"+
        "					 'id': '206',"+
        "					 'name': 'BUENAVISTA',"+
        "					 'position': 'NumberInt(11)'"+
        "				},"+
        "				 {"+
        "					 'id': '207',"+
        "					 'name': 'BUSBANZÁ',"+
        "					 'position': 'NumberInt(12)'"+
        "				},"+
        "				 {"+
        "					 'id': '208',"+
        "					 'name': 'CALDAS',"+
        "					 'position': 'NumberInt(13)'"+
        "				},"+
        "				 {"+
        "					 'id': '209',"+
        "					 'name': 'CAMPOHERMOSO',"+
        "					 'position': 'NumberInt(14)'"+
        "				},"+
        "				 {"+
        "					 'id': '210',"+
        "					 'name': 'CERINZA',"+
        "					 'position': 'NumberInt(15)'"+
        "				},"+
        "				 {"+
        "					 'id': '211',"+
        "					 'name': 'CHINAVITA',"+
        "					 'position': 'NumberInt(16)'"+
        "				},"+
        "				 {"+
        "					 'id': '212',"+
        "					 'name': 'CHIQUINQUIRÁ',"+
        "					 'position': 'NumberInt(17)'"+
        "				},"+
        "				 {"+
        "					 'id': '213',"+
        "					 'name': 'CHISCAS',"+
        "					 'position': 'NumberInt(18)'"+
        "				},"+
        "				 {"+
        "					 'id': '214',"+
        "					 'name': 'CHITA',"+
        "					 'position': 'NumberInt(19)'"+
        "				},"+
        "				 {"+
        "					 'id': '215',"+
        "					 'name': 'CHITARAQUE',"+
        "					 'position': 'NumberInt(20)'"+
        "				},"+
        "				 {"+
        "					 'id': '216',"+
        "					 'name': 'CHIVATÁ',"+
        "					 'position': 'NumberInt(21)'"+
        "				},"+
        "				 {"+
        "					 'id': '217',"+
        "					 'name': 'CIÉNEGA',"+
        "					 'position': 'NumberInt(22)'"+
        "				},"+
        "				 {"+
        "					 'id': '218',"+
        "					 'name': 'CÓMBITA',"+
        "					 'position': 'NumberInt(23)'"+
        "				},"+
        "				 {"+
        "					 'id': '219',"+
        "					 'name': 'COPER',"+
        "					 'position': 'NumberInt(24)'"+
        "				},"+
        "				 {"+
        "					 'id': '220',"+
        "					 'name': 'CORRALES',"+
        "					 'position': 'NumberInt(25)'"+
        "				},"+
        "				 {"+
        "					 'id': '221',"+
        "					 'name': 'COVARACHÍA',"+
        "					 'position': 'NumberInt(26)'"+
        "				},"+
        "				 {"+
        "					 'id': '222',"+
        "					 'name': 'CUBARÁ',"+
        "					 'position': 'NumberInt(27)'"+
        "				},"+
        "				 {"+
        "					 'id': '223',"+
        "					 'name': 'CUCAITA',"+
        "					 'position': 'NumberInt(28)'"+
        "				},"+
        "				 {"+
        "					 'id': '224',"+
        "					 'name': 'CUÍTIVA',"+
        "					 'position': 'NumberInt(29)'"+
        "				},"+
        "				 {"+
        "					 'id': '225',"+
        "					 'name': 'CHÍQUIZA',"+
        "					 'position': 'NumberInt(30)'"+
        "				},"+
        "				 {"+
        "					 'id': '226',"+
        "					 'name': 'CHIVOR',"+
        "					 'position': 'NumberInt(31)'"+
        "				},"+
        "				 {"+
        "					 'id': '227',"+
        "					 'name': 'DUITAMA',"+
        "					 'position': 'NumberInt(32)'"+
        "				},"+
        "				 {"+
        "					 'id': '228',"+
        "					 'name': 'EL COCUY',"+
        "					 'position': 'NumberInt(33)'"+
        "				},"+
        "				 {"+
        "					 'id': '229',"+
        "					 'name': 'EL ESPINO',"+
        "					 'position': 'NumberInt(34)'"+
        "				},"+
        "				 {"+
        "					 'id': '230',"+
        "					 'name': 'FIRAVITOBA',"+
        "					 'position': 'NumberInt(35)'"+
        "				},"+
        "				 {"+
        "					 'id': '231',"+
        "					 'name': 'FLORESTA',"+
        "					 'position': 'NumberInt(36)'"+
        "				},"+
        "				 {"+
        "					 'id': '232',"+
        "					 'name': 'GACHANTIVÁ',"+
        "					 'position': 'NumberInt(37)'"+
        "				},"+
        "				 {"+
        "					 'id': '233',"+
        "					 'name': 'GÁMEZA',"+
        "					 'position': 'NumberInt(38)'"+
        "				},"+
        "				 {"+
        "					 'id': '234',"+
        "					 'name': 'GARAGOA',"+
        "					 'position': 'NumberInt(39)'"+
        "				},"+
        "				 {"+
        "					 'id': '235',"+
        "					 'name': 'GUACAMAYAS',"+
        "					 'position': 'NumberInt(40)'"+
        "				},"+
        "				 {"+
        "					 'id': '236',"+
        "					 'name': 'GUATEQUE',"+
        "					 'position': 'NumberInt(41)'"+
        "				},"+
        "				 {"+
        "					 'id': '237',"+
        "					 'name': 'GUAYATÁ',"+
        "					 'position': 'NumberInt(42)'"+
        "				},"+
        "				 {"+
        "					 'id': '238',"+
        "					 'name': 'GÜICÁN',"+
        "					 'position': 'NumberInt(43)'"+
        "				},"+
        "				 {"+
        "					 'id': '239',"+
        "					 'name': 'IZA',"+
        "					 'position': 'NumberInt(44)'"+
        "				},"+
        "				 {"+
        "					 'id': '240',"+
        "					 'name': 'JENESANO',"+
        "					 'position': 'NumberInt(45)'"+
        "				},"+
        "				 {"+
        "					 'id': '241',"+
        "					 'name': 'JERICÓ',"+
        "					 'position': 'NumberInt(46)'"+
        "				},"+
        "				 {"+
        "					 'id': '242',"+
        "					 'name': 'LABRANZAGRANDE',"+
        "					 'position': 'NumberInt(47)'"+
        "				},"+
        "				 {"+
        "					 'id': '243',"+
        "					 'name': 'LA CAPILLA',"+
        "					 'position': 'NumberInt(48)'"+
        "				},"+
        "				 {"+
        "					 'id': '244',"+
        "					 'name': 'LA VICTORIA',"+
        "					 'position': 'NumberInt(49)'"+
        "				},"+
        "				 {"+
        "					 'id': '245',"+
        "					 'name': 'LA UVITA',"+
        "					 'position': 'NumberInt(50)'"+
        "				},"+
        "				 {"+
        "					 'id': '246',"+
        "					 'name': 'VILLA DE LEYVA',"+
        "					 'position': 'NumberInt(51)'"+
        "				},"+
        "				 {"+
        "					 'id': '247',"+
        "					 'name': 'MACANAL',"+
        "					 'position': 'NumberInt(52)'"+
        "				},"+
        "				 {"+
        "					 'id': '248',"+
        "					 'name': 'MARIPÍ',"+
        "					 'position': 'NumberInt(53)'"+
        "				},"+
        "				 {"+
        "					 'id': '249',"+
        "					 'name': 'MIRAFLORES',"+
        "					 'position': 'NumberInt(54)'"+
        "				},"+
        "				 {"+
        "					 'id': '250',"+
        "					 'name': 'MONGUA',"+
        "					 'position': 'NumberInt(55)'"+
        "				},"+
        "				 {"+
        "					 'id': '251',"+
        "					 'name': 'MONGUÍ',"+
        "					 'position': 'NumberInt(56)'"+
        "				},"+
        "				 {"+
        "					 'id': '252',"+
        "					 'name': 'MONIQUIRÁ',"+
        "					 'position': 'NumberInt(57)'"+
        "				},"+
        "				 {"+
        "					 'id': '253',"+
        "					 'name': 'MOTAVITA',"+
        "					 'position': 'NumberInt(58)'"+
        "				},"+
        "				 {"+
        "					 'id': '254',"+
        "					 'name': 'MUZO',"+
        "					 'position': 'NumberInt(59)'"+
        "				},"+
        "				 {"+
        "					 'id': '255',"+
        "					 'name': 'NOBSA',"+
        "					 'position': 'NumberInt(60)'"+
        "				},"+
        "				 {"+
        "					 'id': '256',"+
        "					 'name': 'NUEVO COLÓN',"+
        "					 'position': 'NumberInt(61)'"+
        "				},"+
        "				 {"+
        "					 'id': '257',"+
        "					 'name': 'OICATÁ',"+
        "					 'position': 'NumberInt(62)'"+
        "				},"+
        "				 {"+
        "					 'id': '258',"+
        "					 'name': 'OTANCHE',"+
        "					 'position': 'NumberInt(63)'"+
        "				},"+
        "				 {"+
        "					 'id': '259',"+
        "					 'name': 'PACHAVITA',"+
        "					 'position': 'NumberInt(64)'"+
        "				},"+
        "				 {"+
        "					 'id': '260',"+
        "					 'name': 'PÁEZ',"+
        "					 'position': 'NumberInt(65)'"+
        "				},"+
        "				 {"+
        "					 'id': '261',"+
        "					 'name': 'PAIPA',"+
        "					 'position': 'NumberInt(66)'"+
        "				},"+
        "				 {"+
        "					 'id': '262',"+
        "					 'name': 'PAJARITO',"+
        "					 'position': 'NumberInt(67)'"+
        "				},"+
        "				 {"+
        "					 'id': '263',"+
        "					 'name': 'PANQUEBA',"+
        "					 'position': 'NumberInt(68)'"+
        "				},"+
        "				 {"+
        "					 'id': '264',"+
        "					 'name': 'PAUNA',"+
        "					 'position': 'NumberInt(69)'"+
        "				},"+
        "				 {"+
        "					 'id': '265',"+
        "					 'name': 'PAYA',"+
        "					 'position': 'NumberInt(70)'"+
        "				},"+
        "				 {"+
        "					 'id': '266',"+
        "					 'name': 'PAZ DE RÍO',"+
        "					 'position': 'NumberInt(71)'"+
        "				},"+
        "				 {"+
        "					 'id': '267',"+
        "					 'name': 'PESCA',"+
        "					 'position': 'NumberInt(72)'"+
        "				},"+
        "				 {"+
        "					 'id': '268',"+
        "					 'name': 'PISBA',"+
        "					 'position': 'NumberInt(73)'"+
        "				},"+
        "				 {"+
        "					 'id': '269',"+
        "					 'name': 'PUERTO BOYACÁ',"+
        "					 'position': 'NumberInt(74)'"+
        "				},"+
        "				 {"+
        "					 'id': '270',"+
        "					 'name': 'QUÍPAMA',"+
        "					 'position': 'NumberInt(75)'"+
        "				},"+
        "				 {"+
        "					 'id': '271',"+
        "					 'name': 'RAMIRIQUÍ',"+
        "					 'position': 'NumberInt(76)'"+
        "				},"+
        "				 {"+
        "					 'id': '272',"+
        "					 'name': 'RÁQUIRA',"+
        "					 'position': 'NumberInt(77)'"+
        "				},"+
        "				 {"+
        "					 'id': '273',"+
        "					 'name': 'RONDÓN',"+
        "					 'position': 'NumberInt(78)'"+
        "				},"+
        "				 {"+
        "					 'id': '274',"+
        "					 'name': 'SABOYÁ',"+
        "					 'position': 'NumberInt(79)'"+
        "				},"+
        "				 {"+
        "					 'id': '275',"+
        "					 'name': 'SÁCHICA',"+
        "					 'position': 'NumberInt(80)'"+
        "				},"+
        "				 {"+
        "					 'id': '276',"+
        "					 'name': 'SAMACÁ',"+
        "					 'position': 'NumberInt(81)'"+
        "				},"+
        "				 {"+
        "					 'id': '277',"+
        "					 'name': 'SAN EDUARDO',"+
        "					 'position': 'NumberInt(82)'"+
        "				},"+
        "				 {"+
        "					 'id': '278',"+
        "					 'name': 'SAN JOSÉ DE PARE',"+
        "					 'position': 'NumberInt(83)'"+
        "				},"+
        "				 {"+
        "					 'id': '279',"+
        "					 'name': 'SAN LUIS DE GACENO',"+
        "					 'position': 'NumberInt(84)'"+
        "				},"+
        "				 {"+
        "					 'id': '280',"+
        "					 'name': 'SAN MATEO',"+
        "					 'position': 'NumberInt(85)'"+
        "				},"+
        "				 {"+
        "					 'id': '281',"+
        "					 'name': 'SAN MIGUEL DE SEMA',"+
        "					 'position': 'NumberInt(86)'"+
        "				},"+
        "				 {"+
        "					 'id': '282',"+
        "					 'name': 'SAN PABLO DE BORBUR',"+
        "					 'position': 'NumberInt(87)'"+
        "				},"+
        "				 {"+
        "					 'id': '283',"+
        "					 'name': 'SANTANA',"+
        "					 'position': 'NumberInt(88)'"+
        "				},"+
        "				 {"+
        "					 'id': '284',"+
        "					 'name': 'SANTA MARÍA',"+
        "					 'position': 'NumberInt(89)'"+
        "				},"+
        "				 {"+
        "					 'id': '285',"+
        "					 'name': 'SANTA ROSA DE VITERBO',"+
        "					 'position': 'NumberInt(90)'"+
        "				},"+
        "				 {"+
        "					 'id': '286',"+
        "					 'name': 'SANTA SOFÍA',"+
        "					 'position': 'NumberInt(91)'"+
        "				},"+
        "				 {"+
        "					 'id': '287',"+
        "					 'name': 'SATIVANORTE',"+
        "					 'position': 'NumberInt(92)'"+
        "				},"+
        "				 {"+
        "					 'id': '288',"+
        "					 'name': 'SATIVASUR',"+
        "					 'position': 'NumberInt(93)'"+
        "				},"+
        "				 {"+
        "					 'id': '289',"+
        "					 'name': 'SIACHOQUE',"+
        "					 'position': 'NumberInt(94)'"+
        "				},"+
        "				 {"+
        "					 'id': '290',"+
        "					 'name': 'SOATÁ',"+
        "					 'position': 'NumberInt(95)'"+
        "				},"+
        "				 {"+
        "					 'id': '291',"+
        "					 'name': 'SOCOTÁ',"+
        "					 'position': 'NumberInt(96)'"+
        "				},"+
        "				 {"+
        "					 'id': '292',"+
        "					 'name': 'SOCHA',"+
        "					 'position': 'NumberInt(97)'"+
        "				},"+
        "				 {"+
        "					 'id': '293',"+
        "					 'name': 'SOGAMOSO',"+
        "					 'position': 'NumberInt(98)'"+
        "				},"+
        "				 {"+
        "					 'id': '294',"+
        "					 'name': 'SOMONDOCO',"+
        "					 'position': 'NumberInt(99)'"+
        "				},"+
        "				 {"+
        "					 'id': '295',"+
        "					 'name': 'SORA',"+
        "					 'position': 'NumberInt(100')"+
        "				},"+
        "				 {"+
        "					 'id': '296',"+
        "					 'name': 'SOTAQUIRÁ',"+
        "					 'position': 'NumberInt(101')"+
        "				},"+
        "				 {"+
        "					 'id': '297',"+
        "					 'name': 'SORACÁ',"+
        "					 'position': 'NumberInt(102')"+
        "				},"+
        "				 {"+
        "					 'id': '298',"+
        "					 'name': 'SUSACÓN',"+
        "					 'position': 'NumberInt(103')"+
        "				},"+
        "				 {"+
        "					 'id': '299',"+
        "					 'name': 'SUTAMARCHÁN',"+
        "					 'position': 'NumberInt(104')"+
        "				},"+
        "				 {"+
        "					 'id': '300',"+
        "					 'name': 'SUTATENZA',"+
        "					 'position': 'NumberInt(105')"+
        "				},"+
        "				 {"+
        "					 'id': '301',"+
        "					 'name': 'TASCO',"+
        "					 'position': 'NumberInt(106')"+
        "				},"+
        "				 {"+
        "					 'id': '302',"+
        "					 'name': 'TENZA',"+
        "					 'position': 'NumberInt(107')"+
        "				},"+
        "				 {"+
        "					 'id': '303',"+
        "					 'name': 'TIBANÁ',"+
        "					 'position': 'NumberInt(108')"+
        "				},"+
        "				 {"+
        "					 'id': '304',"+
        "					 'name': 'TIBASOSA',"+
        "					 'position': 'NumberInt(109')"+
        "				},"+
        "				 {"+
        "					 'id': '305',"+
        "					 'name': 'TINJACÁ',"+
        "					 'position': 'NumberInt(110')"+
        "				},"+
        "				 {"+
        "					 'id': '306',"+
        "					 'name': 'TIPACOQUE',"+
        "					 'position': 'NumberInt(111')"+
        "				},"+
        "				 {"+
        "					 'id': '307',"+
        "					 'name': 'TOCA',"+
        "					 'position': 'NumberInt(112')"+
        "				},"+
        "				 {"+
        "					 'id': '308',"+
        "					 'name': 'TOGÜÍ',"+
        "					 'position': 'NumberInt(113')"+
        "				},"+
        "				 {"+
        "					 'id': '309',"+
        "					 'name': 'TÓPAGA',"+
        "					 'position': 'NumberInt(114')"+
        "				},"+
        "				 {"+
        "					 'id': '310',"+
        "					 'name': 'TOTA',"+
        "					 'position': 'NumberInt(115')"+
        "				},"+
        "				 {"+
        "					 'id': '311',"+
        "					 'name': 'TUNUNGUÁ',"+
        "					 'position': 'NumberInt(116')"+
        "				},"+
        "				 {"+
        "					 'id': '312',"+
        "					 'name': 'TURMEQUÉ',"+
        "					 'position': 'NumberInt(117')"+
        "				},"+
        "				 {"+
        "					 'id': '313',"+
        "					 'name': 'TUTA',"+
        "					 'position': 'NumberInt(118')"+
        "				},"+
        "				 {"+
        "					 'id': '314',"+
        "					 'name': 'TUTAZÁ',"+
        "					 'position': 'NumberInt(119')"+
        "				},"+
        "				 {"+
        "					 'id': '315',"+
        "					 'name': 'ÚMBITA',"+
        "					 'position': 'NumberInt(120')"+
        "				},"+
        "				 {"+
        "					 'id': '316',"+
        "					 'name': 'VENTAQUEMADA',"+
        "					 'position': 'NumberInt(121')"+
        "				},"+
        "				 {"+
        "					 'id': '317',"+
        "					 'name': 'VIRACACHÁ',"+
        "					 'position': 'NumberInt(122')"+
        "				},"+
        "				 {"+
        "					 'id': '318',"+
        "					 'name': 'ZETAQUIRA',"+
        "					 'position': 'NumberInt(123')"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(270'),"+
        "			 'name': 'Municipio (Caldas)',"+
        "			 'help': 'Elige el municipio de Caldas',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(19)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(10)',"+
        "			 'widget': 'NumberInt(1)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '319',"+
        "					 'name': 'MANIZALES',"+
        "					 'position': 'NumberInt(1)'"+
        "				},"+
        "				 {"+
        "					 'id': '320',"+
        "					 'name': 'AGUADAS',"+
        "					 'position': 'NumberInt(2)'"+
        "				},"+
        "				 {"+
        "					 'id': '321',"+
        "					 'name': 'ANSERMA',"+
        "					 'position': 'NumberInt(3)'"+
        "				},"+
        "				 {"+
        "					 'id': '322',"+
        "					 'name': 'ARANZAZU',"+
        "					 'position': 'NumberInt(4)'"+
        "				},"+
        "				 {"+
        "					 'id': '323',"+
        "					 'name': 'BELALCÁZAR',"+
        "					 'position': 'NumberInt(5)'"+
        "				},"+
        "				 {"+
        "					 'id': '324',"+
        "					 'name': 'CHINCHINÁ',"+
        "					 'position': 'NumberInt(6)'"+
        "				},"+
        "				 {"+
        "					 'id': '325',"+
        "					 'name': 'FILADELFIA',"+
        "					 'position': 'NumberInt(7)'"+
        "				},"+
        "				 {"+
        "					 'id': '326',"+
        "					 'name': 'LA DORADA',"+
        "					 'position': 'NumberInt(8)'"+
        "				},"+
        "				 {"+
        "					 'id': '327',"+
        "					 'name': 'LA MERCED',"+
        "					 'position': 'NumberInt(9)'"+
        "				},"+
        "				 {"+
        "					 'id': '328',"+
        "					 'name': 'MANZANARES',"+
        "					 'position': 'NumberInt(10)'"+
        "				},"+
        "				 {"+
        "					 'id': '329',"+
        "					 'name': 'MARMATO',"+
        "					 'position': 'NumberInt(11)'"+
        "				},"+
        "				 {"+
        "					 'id': '330',"+
        "					 'name': 'MARQUETALIA',"+
        "					 'position': 'NumberInt(12)'"+
        "				},"+
        "				 {"+
        "					 'id': '331',"+
        "					 'name': 'MARULANDA',"+
        "					 'position': 'NumberInt(13)'"+
        "				},"+
        "				 {"+
        "					 'id': '332',"+
        "					 'name': 'NEIRA',"+
        "					 'position': 'NumberInt(14)'"+
        "				},"+
        "				 {"+
        "					 'id': '333',"+
        "					 'name': 'NORCASIA',"+
        "					 'position': 'NumberInt(15)'"+
        "				},"+
        "				 {"+
        "					 'id': '334',"+
        "					 'name': 'PÁCORA',"+
        "					 'position': 'NumberInt(16)'"+
        "				},"+
        "				 {"+
        "					 'id': '335',"+
        "					 'name': 'PALESTINA',"+
        "					 'position': 'NumberInt(17)'"+
        "				},"+
        "				 {"+
        "					 'id': '336',"+
        "					 'name': 'PENSILVANIA',"+
        "					 'position': 'NumberInt(18)'"+
        "				},"+
        "				 {"+
        "					 'id': '337',"+
        "					 'name': 'RIOSUCIO',"+
        "					 'position': 'NumberInt(19)'"+
        "				},"+
        "				 {"+
        "					 'id': '338',"+
        "					 'name': 'RISARALDA',"+
        "					 'position': 'NumberInt(20)'"+
        "				},"+
        "				 {"+
        "					 'id': '339',"+
        "					 'name': 'SALAMINA',"+
        "					 'position': 'NumberInt(21)'"+
        "				},"+
        "				 {"+
        "					 'id': '340',"+
        "					 'name': 'SAMANÁ',"+
        "					 'position': 'NumberInt(22)'"+
        "				},"+
        "				 {"+
        "					 'id': '341',"+
        "					 'name': 'SAN JOSÉ',"+
        "					 'position': 'NumberInt(23)'"+
        "				},"+
        "				 {"+
        "					 'id': '342',"+
        "					 'name': 'SUPÍA',"+
        "					 'position': 'NumberInt(24)'"+
        "				},"+
        "				 {"+
        "					 'id': '343',"+
        "					 'name': 'VICTORIA',"+
        "					 'position': 'NumberInt(25)'"+
        "				},"+
        "				 {"+
        "					 'id': '344',"+
        "					 'name': 'VILLAMARÍA',"+
        "					 'position': 'NumberInt(26)'"+
        "				},"+
        "				 {"+
        "					 'id': '345',"+
        "					 'name': 'VITERBO',"+
        "					 'position': 'NumberInt(27)'"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(271'),"+
        "			 'name': 'Municipio (Caqueta)',"+
        "			 'help': 'Elige el municipio de Caqueta',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(20)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(10)',"+
        "			 'widget': 'NumberInt(1)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '346',"+
        "					 'name': 'FLORENCIA',"+
        "					 'position': 'NumberInt(1)'"+
        "				},"+
        "				 {"+
        "					 'id': '347',"+
        "					 'name': 'ALBANIA',"+
        "					 'position': 'NumberInt(2)'"+
        "				},"+
        "				 {"+
        "					 'id': '348',"+
        "					 'name': 'BELÉN DE LOS ANDAQUÍES',"+
        "					 'position': 'NumberInt(3)'"+
        "				},"+
        "				 {"+
        "					 'id': '349',"+
        "					 'name': 'CARTAGENA DEL CHAIRÁ',"+
        "					 'position': 'NumberInt(4)'"+
        "				},"+
        "				 {"+
        "					 'id': '350',"+
        "					 'name': 'CURILLO',"+
        "					 'position': 'NumberInt(5)'"+
        "				},"+
        "				 {"+
        "					 'id': '351',"+
        "					 'name': 'EL DONCELLO',"+
        "					 'position': 'NumberInt(6)'"+
        "				},"+
        "				 {"+
        "					 'id': '352',"+
        "					 'name': 'EL PAUJÍL',"+
        "					 'position': 'NumberInt(7)'"+
        "				},"+
        "				 {"+
        "					 'id': '353',"+
        "					 'name': 'LA MONTAÑITA',"+
        "					 'position': 'NumberInt(8)'"+
        "				},"+
        "				 {"+
        "					 'id': '354',"+
        "					 'name': 'MILÁN',"+
        "					 'position': 'NumberInt(9)'"+
        "				},"+
        "				 {"+
        "					 'id': '355',"+
        "					 'name': 'MORELIA',"+
        "					 'position': 'NumberInt(10)'"+
        "				},"+
        "				 {"+
        "					 'id': '356',"+
        "					 'name': 'PUERTO RICO',"+
        "					 'position': 'NumberInt(11)'"+
        "				},"+
        "				 {"+
        "					 'id': '357',"+
        "					 'name': 'SAN JOSÉ DEL FRAGUA',"+
        "					 'position': 'NumberInt(12)'"+
        "				},"+
        "				 {"+
        "					 'id': '358',"+
        "					 'name': 'SAN VICENTE DEL CAGUÁN',"+
        "					 'position': 'NumberInt(13)'"+
        "				},"+
        "				 {"+
        "					 'id': '359',"+
        "					 'name': 'SOLANO',"+
        "					 'position': 'NumberInt(14)'"+
        "				},"+
        "				 {"+
        "					 'id': '360',"+
        "					 'name': 'SOLITA',"+
        "					 'position': 'NumberInt(15)'"+
        "				},"+
        "				 {"+
        "					 'id': '361',"+
        "					 'name': 'VALPARAÍSO',"+
        "					 'position': 'NumberInt(16)'"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(272'),"+
        "			 'name': 'Municipio (Casanare)',"+
        "			 'help': 'Elige el municipio de Casanare',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(21)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(10)',"+
        "			 'widget': 'NumberInt(1)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1055',"+
        "					 'name': 'YOPAL',"+
        "					 'position': 'NumberInt(1)'"+
        "				},"+
        "				 {"+
        "					 'id': '1056',"+
        "					 'name': 'AGUAZUL',"+
        "					 'position': 'NumberInt(2)'"+
        "				},"+
        "				 {"+
        "					 'id': '1057',"+
        "					 'name': 'CHÁMEZA',"+
        "					 'position': 'NumberInt(3)'"+
        "				},"+
        "				 {"+
        "					 'id': '1058',"+
        "					 'name': 'HATO COROZAL',"+
        "					 'position': 'NumberInt(4)'"+
        "				},"+
        "				 {"+
        "					 'id': '1059',"+
        "					 'name': 'LA SALINA',"+
        "					 'position': 'NumberInt(5)'"+
        "				},"+
        "				 {"+
        "					 'id': '1060',"+
        "					 'name': 'MANÍ',"+
        "					 'position': 'NumberInt(6)'"+
        "				},"+
        "				 {"+
        "					 'id': '1061',"+
        "					 'name': 'MONTERREY',"+
        "					 'position': 'NumberInt(7)'"+
        "				},"+
        "				 {"+
        "					 'id': '1062',"+
        "					 'name': 'NUNCHÍA',"+
        "					 'position': 'NumberInt(8)'"+
        "				},"+
        "				 {"+
        "					 'id': '1063',"+
        "					 'name': 'OROCUÉ',"+
        "					 'position': 'NumberInt(9)'"+
        "				},"+
        "				 {"+
        "					 'id': '1064',"+
        "					 'name': 'PAZ DE ARIPORO',"+
        "					 'position': 'NumberInt(10)'"+
        "				},"+
        "				 {"+
        "					 'id': '1065',"+
        "					 'name': 'PORE',"+
        "					 'position': 'NumberInt(11)'"+
        "				},"+
        "				 {"+
        "					 'id': '1066',"+
        "					 'name': 'RECETOR',"+
        "					 'position': 'NumberInt(12)'"+
        "				},"+
        "				 {"+
        "					 'id': '1067',"+
        "					 'name': 'SABANALARGA',"+
        "					 'position': 'NumberInt(13)'"+
        "				},"+
        "				 {"+
        "					 'id': '1068',"+
        "					 'name': 'SÁCAMA',"+
        "					 'position': 'NumberInt(14)'"+
        "				},"+
        "				 {"+
        "					 'id': '1069',"+
        "					 'name': 'SAN LUIS DE PALENQUE',"+
        "					 'position': 'NumberInt(15)'"+
        "				},"+
        "				 {"+
        "					 'id': '1070',"+
        "					 'name': 'TÁMARA',"+
        "					 'position': 'NumberInt(16)'"+
        "				},"+
        "				 {"+
        "					 'id': '1071',"+
        "					 'name': 'TAURAMENA',"+
        "					 'position': 'NumberInt(17)'"+
        "				},"+
        "				 {"+
        "					 'id': '1072',"+
        "					 'name': 'TRINIDAD',"+
        "					 'position': 'NumberInt(18)'"+
        "				},"+
        "				 {"+
        "					 'id': '1073',"+
        "					 'name': 'VILLANUEVA',"+
        "					 'position': 'NumberInt(19)'"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(273'),"+
        "			 'name': 'Municipio (Cauca)',"+
        "			 'help': 'Elige el municipio de Cauca',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(22)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(10)',"+
        "			 'widget': 'NumberInt(1)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '362',"+
        "					 'name': 'POPAYÁN',"+
        "					 'position': 'NumberInt(1)'"+
        "				},"+
        "				 {"+
        "					 'id': '363',"+
        "					 'name': 'ALMAGUER',"+
        "					 'position': 'NumberInt(2)'"+
        "				},"+
        "				 {"+
        "					 'id': '364',"+
        "					 'name': 'ARGELIA',"+
        "					 'position': 'NumberInt(3)'"+
        "				},"+
        "				 {"+
        "					 'id': '365',"+
        "					 'name': 'BALBOA',"+
        "					 'position': 'NumberInt(4)'"+
        "				},"+
        "				 {"+
        "					 'id': '366',"+
        "					 'name': 'BOLÍVAR',"+
        "					 'position': 'NumberInt(5)'"+
        "				},"+
        "				 {"+
        "					 'id': '367',"+
        "					 'name': 'BUENOS AIRES',"+
        "					 'position': 'NumberInt(6)'"+
        "				},"+
        "				 {"+
        "					 'id': '368',"+
        "					 'name': 'CAJIBÍO',"+
        "					 'position': 'NumberInt(7)'"+
        "				},"+
        "				 {"+
        "					 'id': '369',"+
        "					 'name': 'CALDONO',"+
        "					 'position': 'NumberInt(8)'"+
        "				},"+
        "				 {"+
        "					 'id': '370',"+
        "					 'name': 'CALOTO',"+
        "					 'position': 'NumberInt(9)'"+
        "				},"+
        "				 {"+
        "					 'id': '371',"+
        "					 'name': 'CORINTO',"+
        "					 'position': 'NumberInt(10)'"+
        "				},"+
        "				 {"+
        "					 'id': '372',"+
        "					 'name': 'EL TAMBO',"+
        "					 'position': 'NumberInt(11)'"+
        "				},"+
        "				 {"+
        "					 'id': '373',"+
        "					 'name': 'FLORENCIA',"+
        "					 'position': 'NumberInt(12)'"+
        "				},"+
        "				 {"+
        "					 'id': '374',"+
        "					 'name': 'GUACHENÉ',"+
        "					 'position': 'NumberInt(13)'"+
        "				},"+
        "				 {"+
        "					 'id': '375',"+
        "					 'name': 'GUAPÍ',"+
        "					 'position': 'NumberInt(14)'"+
        "				},"+
        "				 {"+
        "					 'id': '376',"+
        "					 'name': 'INZÁ',"+
        "					 'position': 'NumberInt(15)'"+
        "				},"+
        "				 {"+
        "					 'id': '377',"+
        "					 'name': 'JAMBALÓ',"+
        "					 'position': 'NumberInt(16)'"+
        "				},"+
        "				 {"+
        "					 'id': '378',"+
        "					 'name': 'LA SIERRA',"+
        "					 'position': 'NumberInt(17)'"+
        "				},"+
        "				 {"+
        "					 'id': '379',"+
        "					 'name': 'LA VEGA',"+
        "					 'position': 'NumberInt(18)'"+
        "				},"+
        "				 {"+
        "					 'id': '380',"+
        "					 'name': 'LÓPEZ DE MICAY',"+
        "					 'position': 'NumberInt(19)'"+
        "				},"+
        "				 {"+
        "					 'id': '381',"+
        "					 'name': 'MERCADERES',"+
        "					 'position': 'NumberInt(20)'"+
        "				},"+
        "				 {"+
        "					 'id': '382',"+
        "					 'name': 'MIRANDA',"+
        "					 'position': 'NumberInt(21)'"+
        "				},"+
        "				 {"+
        "					 'id': '383',"+
        "					 'name': 'MORALES',"+
        "					 'position': 'NumberInt(22)'"+
        "				},"+
        "				 {"+
        "					 'id': '384',"+
        "					 'name': 'PADILLA',"+
        "					 'position': 'NumberInt(23)'"+
        "				},"+
        "				 {"+
        "					 'id': '385',"+
        "					 'name': 'PÁEZ',"+
        "					 'position': 'NumberInt(24)'"+
        "				},"+
        "				 {"+
        "					 'id': '386',"+
        "					 'name': 'PATÍA',"+
        "					 'position': 'NumberInt(25)'"+
        "				},"+
        "				 {"+
        "					 'id': '387',"+
        "					 'name': 'PIAMONTE',"+
        "					 'position': 'NumberInt(26)'"+
        "				},"+
        "				 {"+
        "					 'id': '388',"+
        "					 'name': 'PIENDAMÓ',"+
        "					 'position': 'NumberInt(27)'"+
        "				},"+
        "				 {"+
        "					 'id': '389',"+
        "					 'name': 'PUERTO TEJADA',"+
        "					 'position': 'NumberInt(28)'"+
        "				},"+
        "				 {"+
        "					 'id': '390',"+
        "					 'name': 'PURACÉ',"+
        "					 'position': 'NumberInt(29)'"+
        "				},"+
        "				 {"+
        "					 'id': '391',"+
        "					 'name': 'ROSAS',"+
        "					 'position': 'NumberInt(30)'"+
        "				},"+
        "				 {"+
        "					 'id': '392',"+
        "					 'name': 'SAN SEBASTIÁN',"+
        "					 'position': 'NumberInt(31)'"+
        "				},"+
        "				 {"+
        "					 'id': '393',"+
        "					 'name': 'SANTANDER DE QUILICHAO',"+
        "					 'position': 'NumberInt(32)'"+
        "				},"+
        "				 {"+
        "					 'id': '394',"+
        "					 'name': 'SANTA ROSA',"+
        "					 'position': 'NumberInt(33)'"+
        "				},"+
        "				 {"+
        "					 'id': '395',"+
        "					 'name': 'SILVIA',"+
        "					 'position': 'NumberInt(34)'"+
        "				},"+
        "				 {"+
        "					 'id': '396',"+
        "					 'name': 'SOTARA',"+
        "					 'position': 'NumberInt(35)'"+
        "				},"+
        "				 {"+
        "					 'id': '397',"+
        "					 'name': 'SUÁREZ',"+
        "					 'position': 'NumberInt(36)'"+
        "				},"+
        "				 {"+
        "					 'id': '398',"+
        "					 'name': 'SUCRE',"+
        "					 'position': 'NumberInt(37)'"+
        "				},"+
        "				 {"+
        "					 'id': '399',"+
        "					 'name': 'TIMBÍO',"+
        "					 'position': 'NumberInt(38)'"+
        "				},"+
        "				 {"+
        "					 'id': '400',"+
        "					 'name': 'TIMBIQUÍ',"+
        "					 'position': 'NumberInt(39)'"+
        "				},"+
        "				 {"+
        "					 'id': '401',"+
        "					 'name': 'TORIBÍO',"+
        "					 'position': 'NumberInt(40)'"+
        "				},"+
        "				 {"+
        "					 'id': '402',"+
        "					 'name': 'TOTORÓ',"+
        "					 'position': 'NumberInt(41)'"+
        "				},"+
        "				 {"+
        "					 'id': '403',"+
        "					 'name': 'VILLA RICA',"+
        "					 'position': 'NumberInt(42)'"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': 'NumberInt(274'),"+
        "			 'name': 'Municipio (Cesar)',"+
        "			 'help': 'Elige el municipio de Cesar',"+
        "			 'subject': 'Productor',"+
        "			 'position': 'NumberInt(23)',"+
        "			 'is_required': 'false',"+
        "			 'is_filterable': 'true',"+
        "			 'data_type': 'NumberInt(10)',"+
        "			 'widget': 'NumberInt(1)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
        "			 'restrictions': 'null',"+
        "			 'Subform': 'null',"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '404',"+
        "					 'name': 'VALLEDUPAR',"+
        "					 'position': 'NumberInt(1)'"+
        "				},"+
        "				 {"+
        "					 'id': '405',"+
        "					 'name': 'AGUACHICA',"+
        "					 'position': 'NumberInt(2)'"+
        "				},"+
        "				 {"+
        "					 'id': '406',"+
        "					 'name': 'AGUSTÍN CODAZZI',"+
        "					 'position': 'NumberInt(3)'"+
        "				},"+
        "				 {"+
        "					 'id': '407',"+
        "					 'name': 'ASTREA',"+
        "					 'position': 'NumberInt(4)'"+
        "				},"+
        "				 {"+
        "					 'id': '408',"+
        "					 'name': 'BECERRIL',"+
        "					 'position': 'NumberInt(5)'"+
        "				},"+
        "				 {"+
        "					 'id': '409',"+
        "					 'name': 'BOSCONIA',"+
        "					 'position': 'NumberInt(6)'"+
        "				},"+
        "				 {"+
        "					 'id': '410',"+
        "					 'name': 'CHIMICHAGUA',"+
        "					 'position': 'NumberInt(7)'"+
        "				},"+
        "				 {"+
        "					 'id': '411',"+
        "					 'name': 'CHIRIGUANÁ',"+
        "					 'position': 'NumberInt(8)'"+
        "				},"+
        "				 {"+
        "					 'id': '412',"+
        "					 'name': 'CURUMANÍ',"+
        "					 'position': 'NumberInt(9)'"+
        "				},"+
        "				 {"+
        "					 'id': '413',"+
        "					 'name': 'EL COPEY',"+
        "					 'position': 'NumberInt(10)'"+
        "				},"+
        "				 {"+
        "					 'id': '414',"+
        "					 'name': 'EL PASO',"+
        "					 'position': 'NumberInt(11)'"+
        "				},"+
        "				 {"+
        "					 'id': '415',"+
        "					 'name': 'GAMARRA',"+
        "					 'position': 'NumberInt(12)'"+
        "				},"+
        "				 {"+
        "					 'id': '416',"+
        "					 'name': 'GONZÁLEZ',"+
        "					 'position': 'NumberInt(13)'"+
        "				},"+
        "				 {"+
        "					 'id': '417',"+
        "					 'name': 'LA GLORIA',"+
        "					 'position': 'NumberInt(14)'"+
        "				},"+
        "				 {"+
        "					 'id': '418',"+
        "					 'name': 'LA JAGUA DE IBIRICO',"+
        "					 'position': 'NumberInt(15)'"+
        "				},"+
        "				 {"+
        "					 'id': '419',"+
        "					 'name': 'MANAURE BALCÓN DEL CESAR',"+
        "					 'position': 'NumberInt(16)'"+
        "				},"+
        "				 {"+
        "					 'id': '420',"+
        "					 'name': 'PAILITAS',"+
        "					 'position': 'NumberInt(17)'"+
        "				},"+
        "				 {"+
        "					 'id': '421',"+
        "					 'name': 'PELAYA',"+
        "					 'position': 'NumberInt(18)'"+
        "				},"+
        "				 {"+
        "					 'id': '422',"+
        "					 'name': 'PUEBLO BELLO',"+
        "					 'position': 'NumberInt(19)'"+
        "				},"+
        "				 {"+
        "					 'id': '423',"+
        "					 'name': 'RÍO DE ORO',"+
        "					 'position': 'NumberInt(20)'"+
        "				},"+
        "				 {"+
        "					 'id': '424',"+
        "					 'name': 'LA PAZ',"+
        "					 'position': 'NumberInt(21)'"+
        "				},"+
        "				 {"+
        "					 'id': '425',"+
        "					 'name': 'SAN ALBERTO',"+
        "					 'position': 'NumberInt(22)'"+
        "				},"+
        "				 {"+
        "					 'id': '426',"+
        "					 'name': 'SAN DIEGO',"+
        "					 'position': 'NumberInt(23)'"+
        "				},"+
        "				 {"+
        "					 'id': '427',"+
        "					 'name': 'SAN MARTÍN',"+
        "					 'position': 'NumberInt(24)'"+
        "				},"+
        "				 {"+
        "					 'id': '428',"+
        "					 'name': 'TAMALAMEQUE',"+
        "					 'position': 'NumberInt(25)'"+
        "				}"+
        "			]"+*/
//        "		}}";
        /*String json2 =         "		 {"+
        "			 'id': NumberInt(275),"+
        "			 'name': 'Municipio (Choco)',"+
        "			 'help': 'Elige el municipio de Choco',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(24),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '575',"+
        "					 'name': 'QUIBDÓ',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '576',"+
        "					 'name': 'ACANDÍ',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '577',"+
        "					 'name': 'ALTO BAUDÓ',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '578',"+
        "					 'name': 'ATRATO',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '579',"+
        "					 'name': 'BAGADÓ',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '580',"+
        "					 'name': 'BAHÍA SOLANO',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '581',"+
        "					 'name': 'BAJO BAUDÓ',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '582',"+
        "					 'name': 'BOJAYÁ',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '583',"+
        "					 'name': 'EL CANTÓN DEL SAN PABLO',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '584',"+
        "					 'name': 'CARMEN DEL DARIÉN',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '585',"+
        "					 'name': 'CÉRTEGUI',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '586',"+
        "					 'name': 'CONDOTO',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '587',"+
        "					 'name': 'EL CARMEN DE ATRATO',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '588',"+
        "					 'name': 'EL LITORAL DEL SAN JUAN',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '589',"+
        "					 'name': 'ISTMINA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '590',"+
        "					 'name': 'JURADÓ',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '591',"+
        "					 'name': 'LLORÓ',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '592',"+
        "					 'name': 'MEDIO ATRATO',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '593',"+
        "					 'name': 'MEDIO BAUDÓ',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '594',"+
        "					 'name': 'MEDIO SAN JUAN',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '595',"+
        "					 'name': 'NÓVITA',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '596',"+
        "					 'name': 'NUQUÍ',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '597',"+
        "					 'name': 'RÍO IRÓ',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '598',"+
        "					 'name': 'RÍO QUITO',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '599',"+
        "					 'name': 'RIOSUCIO',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '600',"+
        "					 'name': 'SAN JOSÉ DEL PALMAR',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '601',"+
        "					 'name': 'SIPÍ',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '602',"+
        "					 'name': 'TADÓ',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '603',"+
        "					 'name': 'UNGUÍA',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '604',"+
        "					 'name': 'UNIÓN PANAMERICANA',"+
        "					 'position': NumberInt(30)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(276),"+
        "			 'name': 'Municipio (Córdoba)',"+
        "			 'help': 'Elige el municipio de Córdoba',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(25),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '429',"+
        "					 'name': 'MONTERÍA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '430',"+
        "					 'name': 'AYAPEL',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '431',"+
        "					 'name': 'BUENAVISTA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '432',"+
        "					 'name': 'CANALETE',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '433',"+
        "					 'name': 'CERETÉ',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '434',"+
        "					 'name': 'CHIMÁ',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '435',"+
        "					 'name': 'CHINÚ',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '436',"+
        "					 'name': 'CIÉNAGA DE ORO',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '437',"+
        "					 'name': 'COTORRA',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '438',"+
        "					 'name': 'LA APARTADA',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '439',"+
        "					 'name': 'LORICA',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '440',"+
        "					 'name': 'LOS CÓRDOBAS',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '441',"+
        "					 'name': 'MOMIL',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '442',"+
        "					 'name': 'MONTELÍBANO',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '443',"+
        "					 'name': 'MOÑITOS',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '444',"+
        "					 'name': 'PLANETA RICA',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '445',"+
        "					 'name': 'PUEBLO NUEVO',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '446',"+
        "					 'name': 'PUERTO ESCONDIDO',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '447',"+
        "					 'name': 'PUERTO LIBERTADOR',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '448',"+
        "					 'name': 'PURÍSIMA DE LA CONCEPCIÓN',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '449',"+
        "					 'name': 'SAHAGÚN',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '450',"+
        "					 'name': 'SAN ANDRÉS DE SOTAVENTO',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '451',"+
        "					 'name': 'SAN ANTERO',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '452',"+
        "					 'name': 'SAN BERNARDO DEL VIENTO',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '453',"+
        "					 'name': 'SAN CARLOS',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '454',"+
        "					 'name': 'SAN JOSÉ DE URÉ',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '455',"+
        "					 'name': 'SAN PELAYO',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '456',"+
        "					 'name': 'TIERRALTA',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '457',"+
        "					 'name': 'TUCHÍN',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '458',"+
        "					 'name': 'VALENCIA',"+
        "					 'position': NumberInt(30)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(277),"+
        "			 'name': 'Municipio (Cundinamarca)',"+
        "			 'help': 'Elige el municipio de Cundinamarca',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(26),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '459',"+
        "					 'name': 'AGUA DE DIOS',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '460',"+
        "					 'name': 'ALBÁN',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '461',"+
        "					 'name': 'ANAPOIMA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '462',"+
        "					 'name': 'ANOLAIMA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '463',"+
        "					 'name': 'ARBELÁEZ',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '464',"+
        "					 'name': 'BELTRÁN',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '465',"+
        "					 'name': 'BITUIMA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '466',"+
        "					 'name': 'BOJACÁ',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '467',"+
        "					 'name': 'CABRERA',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '468',"+
        "					 'name': 'CACHIPAY',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '469',"+
        "					 'name': 'CAJICÁ',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '470',"+
        "					 'name': 'CAPARRAPÍ',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '471',"+
        "					 'name': 'CÁQUEZA',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '472',"+
        "					 'name': 'CARMEN DE CARUPA',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '473',"+
        "					 'name': 'CHAGUANÍ',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '474',"+
        "					 'name': 'CHÍA',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '475',"+
        "					 'name': 'CHIPAQUE',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '476',"+
        "					 'name': 'CHOACHÍ',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '477',"+
        "					 'name': 'CHOCONTÁ',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '478',"+
        "					 'name': 'COGUA',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '479',"+
        "					 'name': 'COTA',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '480',"+
        "					 'name': 'CUCUNUBÁ',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '481',"+
        "					 'name': 'EL COLEGIO',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '482',"+
        "					 'name': 'EL PEÑÓN',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '483',"+
        "					 'name': 'EL ROSAL',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '484',"+
        "					 'name': 'FACATATIVÁ',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '485',"+
        "					 'name': 'FÓMEQUE',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '486',"+
        "					 'name': 'FOSCA',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '487',"+
        "					 'name': 'FUNZA',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '488',"+
        "					 'name': 'FÚQUENE',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '489',"+
        "					 'name': 'FUSAGASUGÁ',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '490',"+
        "					 'name': 'GACHALÁ',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '491',"+
        "					 'name': 'GACHANCIPÁ',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '492',"+
        "					 'name': 'GACHETÁ',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '493',"+
        "					 'name': 'GAMA',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '494',"+
        "					 'name': 'GIRARDOT',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '495',"+
        "					 'name': 'GRANADA',"+
        "					 'position': NumberInt(37)"+
        "				},"+
        "				 {"+
        "					 'id': '496',"+
        "					 'name': 'GUACHETÁ',"+
        "					 'position': NumberInt(38)"+
        "				},"+
        "				 {"+
        "					 'id': '497',"+
        "					 'name': 'GUADUAS',"+
        "					 'position': NumberInt(39)"+
        "				},"+
        "				 {"+
        "					 'id': '498',"+
        "					 'name': 'GUASCA',"+
        "					 'position': NumberInt(40)"+
        "				},"+
        "				 {"+
        "					 'id': '499',"+
        "					 'name': 'GUATAQUÍ',"+
        "					 'position': NumberInt(41)"+
        "				},"+
        "				 {"+
        "					 'id': '500',"+
        "					 'name': 'GUATAVITA',"+
        "					 'position': NumberInt(42)"+
        "				},"+
        "				 {"+
        "					 'id': '501',"+
        "					 'name': 'GUAYABAL DE SÍQUIMA',"+
        "					 'position': NumberInt(43)"+
        "				},"+
        "				 {"+
        "					 'id': '502',"+
        "					 'name': 'GUAYABETAL',"+
        "					 'position': NumberInt(44)"+
        "				},"+
        "				 {"+
        "					 'id': '503',"+
        "					 'name': 'GUTIÉRREZ',"+
        "					 'position': NumberInt(45)"+
        "				},"+
        "				 {"+
        "					 'id': '504',"+
        "					 'name': 'JERUSALÉN',"+
        "					 'position': NumberInt(46)"+
        "				},"+
        "				 {"+
        "					 'id': '505',"+
        "					 'name': 'JUNÍN',"+
        "					 'position': NumberInt(47)"+
        "				},"+
        "				 {"+
        "					 'id': '506',"+
        "					 'name': 'LA CALERA',"+
        "					 'position': NumberInt(48)"+
        "				},"+
        "				 {"+
        "					 'id': '507',"+
        "					 'name': 'LA MESA',"+
        "					 'position': NumberInt(49)"+
        "				},"+
        "				 {"+
        "					 'id': '508',"+
        "					 'name': 'LA PALMA',"+
        "					 'position': NumberInt(50)"+
        "				},"+
        "				 {"+
        "					 'id': '509',"+
        "					 'name': 'LA PEÑA',"+
        "					 'position': NumberInt(51)"+
        "				},"+
        "				 {"+
        "					 'id': '510',"+
        "					 'name': 'LA VEGA',"+
        "					 'position': NumberInt(52)"+
        "				},"+
        "				 {"+
        "					 'id': '511',"+
        "					 'name': 'LENGUAZAQUE',"+
        "					 'position': NumberInt(53)"+
        "				},"+
        "				 {"+
        "					 'id': '512',"+
        "					 'name': 'MACHETÁ',"+
        "					 'position': NumberInt(54)"+
        "				},"+
        "				 {"+
        "					 'id': '513',"+
        "					 'name': 'MADRID',"+
        "					 'position': NumberInt(55)"+
        "				},"+
        "				 {"+
        "					 'id': '514',"+
        "					 'name': 'MANTA',"+
        "					 'position': NumberInt(56)"+
        "				},"+
        "				 {"+
        "					 'id': '515',"+
        "					 'name': 'MEDINA',"+
        "					 'position': NumberInt(57)"+
        "				},"+
        "				 {"+
        "					 'id': '516',"+
        "					 'name': 'MOSQUERA',"+
        "					 'position': NumberInt(58)"+
        "				},"+
        "				 {"+
        "					 'id': '517',"+
        "					 'name': 'NARIÑO',"+
        "					 'position': NumberInt(59)"+
        "				},"+
        "				 {"+
        "					 'id': '518',"+
        "					 'name': 'NEMOCÓN',"+
        "					 'position': NumberInt(60)"+
        "				},"+
        "				 {"+
        "					 'id': '519',"+
        "					 'name': 'NILO',"+
        "					 'position': NumberInt(61)"+
        "				},"+
        "				 {"+
        "					 'id': '520',"+
        "					 'name': 'NIMAIMA',"+
        "					 'position': NumberInt(62)"+
        "				},"+
        "				 {"+
        "					 'id': '521',"+
        "					 'name': 'NOCAIMA',"+
        "					 'position': NumberInt(63)"+
        "				},"+
        "				 {"+
        "					 'id': '522',"+
        "					 'name': 'VENECIA',"+
        "					 'position': NumberInt(64)"+
        "				},"+
        "				 {"+
        "					 'id': '523',"+
        "					 'name': 'PACHO',"+
        "					 'position': NumberInt(65)"+
        "				},"+
        "				 {"+
        "					 'id': '524',"+
        "					 'name': 'PAIME',"+
        "					 'position': NumberInt(66)"+
        "				},"+
        "				 {"+
        "					 'id': '525',"+
        "					 'name': 'PANDI',"+
        "					 'position': NumberInt(67)"+
        "				},"+
        "				 {"+
        "					 'id': '526',"+
        "					 'name': 'PARATEBUENO',"+
        "					 'position': NumberInt(68)"+
        "				},"+
        "				 {"+
        "					 'id': '527',"+
        "					 'name': 'PASCA',"+
        "					 'position': NumberInt(69)"+
        "				},"+
        "				 {"+
        "					 'id': '528',"+
        "					 'name': 'PUERTO SALGAR',"+
        "					 'position': NumberInt(70)"+
        "				},"+
        "				 {"+
        "					 'id': '529',"+
        "					 'name': 'PULÍ',"+
        "					 'position': NumberInt(71)"+
        "				},"+
        "				 {"+
        "					 'id': '530',"+
        "					 'name': 'QUEBRADANEGRA',"+
        "					 'position': NumberInt(72)"+
        "				},"+
        "				 {"+
        "					 'id': '531',"+
        "					 'name': 'QUETAME',"+
        "					 'position': NumberInt(73)"+
        "				},"+
        "				 {"+
        "					 'id': '532',"+
        "					 'name': 'QUIPILE',"+
        "					 'position': NumberInt(74)"+
        "				},"+
        "				 {"+
        "					 'id': '533',"+
        "					 'name': 'APULO',"+
        "					 'position': NumberInt(75)"+
        "				},"+
        "				 {"+
        "					 'id': '534',"+
        "					 'name': 'RICAURTE',"+
        "					 'position': NumberInt(76)"+
        "				},"+
        "				 {"+
        "					 'id': '535',"+
        "					 'name': 'SAN ANTONIO DEL TEQUENDAMA',"+
        "					 'position': NumberInt(77)"+
        "				},"+
        "				 {"+
        "					 'id': '536',"+
        "					 'name': 'SAN BERNARDO',"+
        "					 'position': NumberInt(78)"+
        "				},"+
        "				 {"+
        "					 'id': '537',"+
        "					 'name': 'SAN CAYETANO',"+
        "					 'position': NumberInt(79)"+
        "				},"+
        "				 {"+
        "					 'id': '538',"+
        "					 'name': 'SAN FRANCISCO',"+
        "					 'position': NumberInt(80)"+
        "				},"+
        "				 {"+
        "					 'id': '539',"+
        "					 'name': 'SAN JUAN DE RIOSECO',"+
        "					 'position': NumberInt(81)"+
        "				},"+
        "				 {"+
        "					 'id': '540',"+
        "					 'name': 'SASAIMA',"+
        "					 'position': NumberInt(82)"+
        "				},"+
        "				 {"+
        "					 'id': '541',"+
        "					 'name': 'SESQUILÉ',"+
        "					 'position': NumberInt(83)"+
        "				},"+
        "				 {"+
        "					 'id': '542',"+
        "					 'name': 'SIBATÉ',"+
        "					 'position': NumberInt(84)"+
        "				},"+
        "				 {"+
        "					 'id': '543',"+
        "					 'name': 'SILVANIA',"+
        "					 'position': NumberInt(85)"+
        "				},"+
        "				 {"+
        "					 'id': '544',"+
        "					 'name': 'SIMIJACA',"+
        "					 'position': NumberInt(86)"+
        "				},"+
        "				 {"+
        "					 'id': '545',"+
        "					 'name': 'SOACHA',"+
        "					 'position': NumberInt(87)"+
        "				},"+
        "				 {"+
        "					 'id': '546',"+
        "					 'name': 'SOPÓ',"+
        "					 'position': NumberInt(88)"+
        "				},"+
        "				 {"+
        "					 'id': '547',"+
        "					 'name': 'SUBACHOQUE',"+
        "					 'position': NumberInt(89)"+
        "				},"+
        "				 {"+
        "					 'id': '548',"+
        "					 'name': 'SUESCA',"+
        "					 'position': NumberInt(90)"+
        "				},"+
        "				 {"+
        "					 'id': '549',"+
        "					 'name': 'SUPATÁ',"+
        "					 'position': NumberInt(91)"+
        "				},"+
        "				 {"+
        "					 'id': '550',"+
        "					 'name': 'SUSA',"+
        "					 'position': NumberInt(92)"+
        "				},"+
        "				 {"+
        "					 'id': '551',"+
        "					 'name': 'SUTATAUSA',"+
        "					 'position': NumberInt(93)"+
        "				},"+
        "				 {"+
        "					 'id': '552',"+
        "					 'name': 'TABIO',"+
        "					 'position': NumberInt(94)"+
        "				},"+
        "				 {"+
        "					 'id': '553',"+
        "					 'name': 'TAUSA',"+
        "					 'position': NumberInt(95)"+
        "				},"+
        "				 {"+
        "					 'id': '554',"+
        "					 'name': 'TENA',"+
        "					 'position': NumberInt(96)"+
        "				},"+
        "				 {"+
        "					 'id': '555',"+
        "					 'name': 'TENJO',"+
        "					 'position': NumberInt(97)"+
        "				},"+
        "				 {"+
        "					 'id': '556',"+
        "					 'name': 'TIBACUY',"+
        "					 'position': NumberInt(98)"+
        "				},"+
        "				 {"+
        "					 'id': '557',"+
        "					 'name': 'TIBIRITA',"+
        "					 'position': NumberInt(99)"+
        "				},"+
        "				 {"+
        "					 'id': '558',"+
        "					 'name': 'TOCAIMA',"+
        "					 'position': NumberInt(100)"+
        "				},"+
        "				 {"+
        "					 'id': '559',"+
        "					 'name': 'TOCANCIPÁ',"+
        "					 'position': NumberInt(101)"+
        "				},"+
        "				 {"+
        "					 'id': '560',"+
        "					 'name': 'TOPAIPÍ',"+
        "					 'position': NumberInt(102)"+
        "				},"+
        "				 {"+
        "					 'id': '561',"+
        "					 'name': 'UBALÁ',"+
        "					 'position': NumberInt(103)"+
        "				},"+
        "				 {"+
        "					 'id': '562',"+
        "					 'name': 'UBAQUE',"+
        "					 'position': NumberInt(104)"+
        "				},"+
        "				 {"+
        "					 'id': '563',"+
        "					 'name': 'VILLA DE SAN DIEGO DE UBATÉ',"+
        "					 'position': NumberInt(105)"+
        "				},"+
        "				 {"+
        "					 'id': '564',"+
        "					 'name': 'UNE',"+
        "					 'position': NumberInt(106)"+
        "				},"+
        "				 {"+
        "					 'id': '565',"+
        "					 'name': 'ÚTICA',"+
        "					 'position': NumberInt(107)"+
        "				},"+
        "				 {"+
        "					 'id': '566',"+
        "					 'name': 'VERGARA',"+
        "					 'position': NumberInt(108)"+
        "				},"+
        "				 {"+
        "					 'id': '567',"+
        "					 'name': 'VIANÍ',"+
        "					 'position': NumberInt(109)"+
        "				},"+
        "				 {"+
        "					 'id': '568',"+
        "					 'name': 'VILLAGÓMEZ',"+
        "					 'position': NumberInt(110)"+
        "				},"+
        "				 {"+
        "					 'id': '569',"+
        "					 'name': 'VILLAPINZÓN',"+
        "					 'position': NumberInt(111)"+
        "				},"+
        "				 {"+
        "					 'id': '570',"+
        "					 'name': 'VILLETA',"+
        "					 'position': NumberInt(112)"+
        "				},"+
        "				 {"+
        "					 'id': '571',"+
        "					 'name': 'VIOTÁ',"+
        "					 'position': NumberInt(113)"+
        "				},"+
        "				 {"+
        "					 'id': '572',"+
        "					 'name': 'YACOPÍ',"+
        "					 'position': NumberInt(114)"+
        "				},"+
        "				 {"+
        "					 'id': '573',"+
        "					 'name': 'ZIPACÓN',"+
        "					 'position': NumberInt(115)"+
        "				},"+
        "				 {"+
        "					 'id': '574',"+
        "					 'name': 'ZIPAQUIRÁ',"+
        "					 'position': NumberInt(116)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(278),"+
        "			 'name': 'Municipio (Guainia)',"+
        "			 'help': 'Elige el municipio de Guainia',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(27),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1100',"+
        "					 'name': 'INÍRIDA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1101',"+
        "					 'name': 'BARRANCO MINAS',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '1102',"+
        "					 'name': 'MAPIRIPANA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '1103',"+
        "					 'name': 'SAN FELIPE',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '1104',"+
        "					 'name': 'PUERTO COLOMBIA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '1105',"+
        "					 'name': 'LA GUADALUPE',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '1106',"+
        "					 'name': 'CACAHUAL',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '1107',"+
        "					 'name': 'PANA PANA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '1108',"+
        "					 'name': 'MORICHAL',"+
        "					 'position': NumberInt(9)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(279),"+
        "			 'name': 'Municipio (Guaviare)',"+
        "			 'help': 'Elige el municipio de Guaviare',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(28),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1109',"+
        "					 'name': 'SAN JOSÉ DEL GUAVIARE',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1110',"+
        "					 'name': 'CALAMAR',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '1111',"+
        "					 'name': 'EL RETORNO',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '1112',"+
        "					 'name': 'MIRAFLORES',"+
        "					 'position': NumberInt(4)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(280),"+
        "			 'name': 'Municipio (Huila)',"+
        "			 'help': 'Elige el municipio de Huila',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(29),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '605',"+
        "					 'name': 'NEIVA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '606',"+
        "					 'name': 'ACEVEDO',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '607',"+
        "					 'name': 'AGRADO',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '608',"+
        "					 'name': 'AIPE',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '609',"+
        "					 'name': 'ALGECIRAS',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '610',"+
        "					 'name': 'ALTAMIRA',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '611',"+
        "					 'name': 'BARAYA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '612',"+
        "					 'name': 'CAMPOALEGRE',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '613',"+
        "					 'name': 'COLOMBIA',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '614',"+
        "					 'name': 'ELÍAS',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '615',"+
        "					 'name': 'GARZÓN',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '616',"+
        "					 'name': 'GIGANTE',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '617',"+
        "					 'name': 'GUADALUPE',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '618',"+
        "					 'name': 'HOBO',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '619',"+
        "					 'name': 'ÍQUIRA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '620',"+
        "					 'name': 'ISNOS',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '621',"+
        "					 'name': 'LA ARGENTINA',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '622',"+
        "					 'name': 'LA PLATA',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '623',"+
        "					 'name': 'NÁTAGA',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '624',"+
        "					 'name': 'OPORAPA',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '625',"+
        "					 'name': 'PAICOL',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '626',"+
        "					 'name': 'PALERMO',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '627',"+
        "					 'name': 'PALESTINA',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '628',"+
        "					 'name': 'PITAL',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '629',"+
        "					 'name': 'PITALITO',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '630',"+
        "					 'name': 'RIVERA',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '631',"+
        "					 'name': 'SALADOBLANCO',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '632',"+
        "					 'name': 'SAN AGUSTÍN',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '633',"+
        "					 'name': 'SANTA MARÍA',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '634',"+
        "					 'name': 'SUAZA',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '635',"+
        "					 'name': 'TARQUI',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '636',"+
        "					 'name': 'TESALIA',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '637',"+
        "					 'name': 'TELLO',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '638',"+
        "					 'name': 'TERUEL',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '639',"+
        "					 'name': 'TIMANÁ',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '640',"+
        "					 'name': 'VILLAVIEJA',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '641',"+
        "					 'name': 'YAGUARÁ',"+
        "					 'position': NumberInt(37)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(281),"+
        "			 'name': 'Municipio (La Guajira)',"+
        "			 'help': 'Elige el municipio de La Guajira',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(29),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '642',"+
        "					 'name': 'RIOHACHA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '643',"+
        "					 'name': 'ALBANIA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '644',"+
        "					 'name': 'BARRANCAS',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '645',"+
        "					 'name': 'DIBULLA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '646',"+
        "					 'name': 'DISTRACCIÓN',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '647',"+
        "					 'name': 'EL MOLINO',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '648',"+
        "					 'name': 'FONSECA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '649',"+
        "					 'name': 'HATONUEVO',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '650',"+
        "					 'name': 'LA JAGUA DEL PILAR',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '651',"+
        "					 'name': 'MAICAO',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '652',"+
        "					 'name': 'MANAURE',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '653',"+
        "					 'name': 'SAN JUAN DEL CESAR',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '654',"+
        "					 'name': 'URIBIA',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '655',"+
        "					 'name': 'URUMITA',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '656',"+
        "					 'name': 'VILLANUEVA',"+
        "					 'position': NumberInt(15)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(282),"+
        "			 'name': 'Municipio (Magdalena)',"+
        "			 'help': 'Elige el municipio de Magdalena',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(30),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '657',"+
        "					 'name': 'SANTA MARTA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '658',"+
        "					 'name': 'ALGARROBO',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '659',"+
        "					 'name': 'ARACATACA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '660',"+
        "					 'name': 'ARIGUANÍ',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '661',"+
        "					 'name': 'CERRO DE SAN ANTONIO',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '662',"+
        "					 'name': 'CHIVOLO',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '663',"+
        "					 'name': 'CIÉNAGA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '664',"+
        "					 'name': 'CONCORDIA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '665',"+
        "					 'name': 'EL BANCO',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '666',"+
        "					 'name': 'EL PIÑÓN',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '667',"+
        "					 'name': 'EL RETÉN',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '668',"+
        "					 'name': 'FUNDACIÓN',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '669',"+
        "					 'name': 'GUAMAL',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '670',"+
        "					 'name': 'NUEVA GRANADA',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '671',"+
        "					 'name': 'PEDRAZA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '672',"+
        "					 'name': 'PIJIÑO DEL CARMEN',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '673',"+
        "					 'name': 'PIVIJAY',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '674',"+
        "					 'name': 'PLATO',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '675',"+
        "					 'name': 'PUEBLOVIEJO',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '676',"+
        "					 'name': 'REMOLINO',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '677',"+
        "					 'name': 'SABANAS DE SAN ÁNGEL',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '678',"+
        "					 'name': 'SALAMINA',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '679',"+
        "					 'name': 'SAN SEBASTIÁN DE BUENAVISTA',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '680',"+
        "					 'name': 'SAN ZENÓN',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '681',"+
        "					 'name': 'SANTA ANA',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '682',"+
        "					 'name': 'SANTA BÁRBARA DE PINTO',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '683',"+
        "					 'name': 'SITIONUEVO',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '684',"+
        "					 'name': 'TENERIFE',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '685',"+
        "					 'name': 'ZAPAYÁN',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '686',"+
        "					 'name': 'ZONA BANANERA',"+
        "					 'position': NumberInt(30)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(283),"+
        "			 'name': 'Municipio (Meta)',"+
        "			 'help': 'Elige el municipio del Meta',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(31),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '687',"+
        "					 'name': 'VILLAVICENCIO',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '688',"+
        "					 'name': 'ACACÍAS',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '689',"+
        "					 'name': 'BARRANCA DE UPÍA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '690',"+
        "					 'name': 'CABUYARO',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '691',"+
        "					 'name': 'CASTILLA LA NUEVA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '692',"+
        "					 'name': 'SAN LUIS DE CUBARRAL',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '693',"+
        "					 'name': 'CUMARAL',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '694',"+
        "					 'name': 'EL CALVARIO',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '695',"+
        "					 'name': 'EL CASTILLO',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '696',"+
        "					 'name': 'EL DORADO',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '697',"+
        "					 'name': 'FUENTE DE ORO',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '698',"+
        "					 'name': 'GRANADA',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '699',"+
        "					 'name': 'GUAMAL',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '700',"+
        "					 'name': 'MAPIRIPÁN',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '701',"+
        "					 'name': 'MESETAS',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '702',"+
        "					 'name': 'LA MACARENA',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '703',"+
        "					 'name': 'URIBE',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '704',"+
        "					 'name': 'LEJANÍAS',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '705',"+
        "					 'name': 'PUERTO CONCORDIA',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '706',"+
        "					 'name': 'PUERTO GAITÁN',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '707',"+
        "					 'name': 'PUERTO LÓPEZ',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '708',"+
        "					 'name': 'PUERTO LLERAS',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '709',"+
        "					 'name': 'PUERTO RICO',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '710',"+
        "					 'name': 'RESTREPO',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '711',"+
        "					 'name': 'SAN CARLOS DE GUAROA',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '712',"+
        "					 'name': 'SAN JUAN DE ARAMA',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '713',"+
        "					 'name': 'SAN JUANITO',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '714',"+
        "					 'name': 'SAN MARTÍN',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '715',"+
        "					 'name': 'VISTAHERMOSA',"+
        "					 'position': NumberInt(29)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(284),"+
        "			 'name': 'Municipio (Nariño)',"+
        "			 'help': 'Elige el municipio de Nariño',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(32),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '716',"+
        "					 'name': 'PASTO',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '717',"+
        "					 'name': 'ALBÁN',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '718',"+
        "					 'name': 'ALDANA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '719',"+
        "					 'name': 'ANCUYÁ',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '720',"+
        "					 'name': 'ARBOLEDA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '721',"+
        "					 'name': 'BARBACOAS',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '722',"+
        "					 'name': 'BELÉN',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '723',"+
        "					 'name': 'BUESACO',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '724',"+
        "					 'name': 'COLÓN',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '725',"+
        "					 'name': 'CONSACÁ',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '726',"+
        "					 'name': 'CONTADERO',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '727',"+
        "					 'name': 'CÓRDOBA',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '728',"+
        "					 'name': 'CUASPÚD',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '729',"+
        "					 'name': 'CUMBAL',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '730',"+
        "					 'name': 'CUMBITARA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '731',"+
        "					 'name': 'CHACHAGÜÍ',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '732',"+
        "					 'name': 'EL CHARCO',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '733',"+
        "					 'name': 'EL PEÑOL',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '734',"+
        "					 'name': 'EL ROSARIO',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '735',"+
        "					 'name': 'EL TABLÓN DE GÓMEZ',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '736',"+
        "					 'name': 'EL TAMBO',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '737',"+
        "					 'name': 'FUNES',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '738',"+
        "					 'name': 'GUACHUCAL',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '739',"+
        "					 'name': 'GUAITARILLA',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '740',"+
        "					 'name': 'GUALMATÁN',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '741',"+
        "					 'name': 'ILES',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '742',"+
        "					 'name': 'IMUÉS',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '743',"+
        "					 'name': 'IPIALES',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '744',"+
        "					 'name': 'LA CRUZ',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '745',"+
        "					 'name': 'LA FLORIDA',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '746',"+
        "					 'name': 'LA LLANADA',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '747',"+
        "					 'name': 'LA TOLA',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '748',"+
        "					 'name': 'LA UNIÓN',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '749',"+
        "					 'name': 'LEIVA',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '750',"+
        "					 'name': 'LINARES',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '751',"+
        "					 'name': 'LOS ANDES',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '752',"+
        "					 'name': 'MAGÜÍ',"+
        "					 'position': NumberInt(37)"+
        "				},"+
        "				 {"+
        "					 'id': '753',"+
        "					 'name': 'MALLAMA',"+
        "					 'position': NumberInt(38)"+
        "				},"+
        "				 {"+
        "					 'id': '754',"+
        "					 'name': 'MOSQUERA',"+
        "					 'position': NumberInt(39)"+
        "				},"+
        "				 {"+
        "					 'id': '755',"+
        "					 'name': 'NARIÑO',"+
        "					 'position': NumberInt(40)"+
        "				},"+
        "				 {"+
        "					 'id': '756',"+
        "					 'name': 'OLAYA HERRERA',"+
        "					 'position': NumberInt(41)"+
        "				},"+
        "				 {"+
        "					 'id': '757',"+
        "					 'name': 'OSPINA',"+
        "					 'position': NumberInt(42)"+
        "				},"+
        "				 {"+
        "					 'id': '758',"+
        "					 'name': 'FRANCISCO PIZARRO',"+
        "					 'position': NumberInt(43)"+
        "				},"+
        "				 {"+
        "					 'id': '759',"+
        "					 'name': 'POLICARPA',"+
        "					 'position': NumberInt(44)"+
        "				},"+
        "				 {"+
        "					 'id': '760',"+
        "					 'name': 'POTOSÍ',"+
        "					 'position': NumberInt(45)"+
        "				},"+
        "				 {"+
        "					 'id': '761',"+
        "					 'name': 'PROVIDENCIA',"+
        "					 'position': NumberInt(46)"+
        "				},"+
        "				 {"+
        "					 'id': '762',"+
        "					 'name': 'PUERRES',"+
        "					 'position': NumberInt(47)"+
        "				},"+
        "				 {"+
        "					 'id': '763',"+
        "					 'name': 'PUPIALES',"+
        "					 'position': NumberInt(48)"+
        "				},"+
        "				 {"+
        "					 'id': '764',"+
        "					 'name': 'RICAURTE',"+
        "					 'position': NumberInt(49)"+
        "				},"+
        "				 {"+
        "					 'id': '765',"+
        "					 'name': 'ROBERTO PAYÁN',"+
        "					 'position': NumberInt(50)"+
        "				},"+
        "				 {"+
        "					 'id': '766',"+
        "					 'name': 'SAMANIEGO',"+
        "					 'position': NumberInt(51)"+
        "				},"+
        "				 {"+
        "					 'id': '767',"+
        "					 'name': 'SANDONÁ',"+
        "					 'position': NumberInt(52)"+
        "				},"+
        "				 {"+
        "					 'id': '768',"+
        "					 'name': 'SAN BERNARDO',"+
        "					 'position': NumberInt(53)"+
        "				},"+
        "				 {"+
        "					 'id': '769',"+
        "					 'name': 'SAN LORENZO',"+
        "					 'position': NumberInt(54)"+
        "				},"+
        "				 {"+
        "					 'id': '770',"+
        "					 'name': 'SAN PABLO',"+
        "					 'position': NumberInt(55)"+
        "				},"+
        "				 {"+
        "					 'id': '771',"+
        "					 'name': 'SAN PEDRO DE CARTAGO',"+
        "					 'position': NumberInt(56)"+
        "				},"+
        "				 {"+
        "					 'id': '772',"+
        "					 'name': 'SANTA BÁRBARA',"+
        "					 'position': NumberInt(57)"+
        "				},"+
        "				 {"+
        "					 'id': '773',"+
        "					 'name': 'SANTACRUZ',"+
        "					 'position': NumberInt(58)"+
        "				},"+
        "				 {"+
        "					 'id': '774',"+
        "					 'name': 'SAPUYES',"+
        "					 'position': NumberInt(59)"+
        "				},"+
        "				 {"+
        "					 'id': '775',"+
        "					 'name': 'TAMINANGO',"+
        "					 'position': NumberInt(60)"+
        "				},"+
        "				 {"+
        "					 'id': '776',"+
        "					 'name': 'TANGUA',"+
        "					 'position': NumberInt(61)"+
        "				},"+
        "				 {"+
        "					 'id': '777',"+
        "					 'name': 'SAN ANDRÉS DE TUMACO',"+
        "					 'position': NumberInt(62)"+
        "				},"+
        "				 {"+
        "					 'id': '778',"+
        "					 'name': 'TÚQUERRES',"+
        "					 'position': NumberInt(63)"+
        "				},"+
        "				 {"+
        "					 'id': '779',"+
        "					 'name': 'YACUANQUER',"+
        "					 'position': NumberInt(64)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(285),"+
        "			 'name': 'Municipio (Norte de Santander)',"+
        "			 'help': 'Elige el municipio de Norte de Santander',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(33),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '780',"+
        "					 'name': 'CÚCUTA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '781',"+
        "					 'name': 'ÁBREGO',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '782',"+
        "					 'name': 'ARBOLEDAS',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '783',"+
        "					 'name': 'BOCHALEMA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '784',"+
        "					 'name': 'BUCARASICA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '785',"+
        "					 'name': 'CÁCOTA',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '786',"+
        "					 'name': 'CÁCHIRA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '787',"+
        "					 'name': 'CHINÁCOTA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '788',"+
        "					 'name': 'CHITAGÁ',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '789',"+
        "					 'name': 'CONVENCIÓN',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '790',"+
        "					 'name': 'CUCUTILLA',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '791',"+
        "					 'name': 'DURANIA',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '792',"+
        "					 'name': 'EL CARMEN',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '793',"+
        "					 'name': 'EL TARRA',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '794',"+
        "					 'name': 'EL ZULIA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '795',"+
        "					 'name': 'GRAMALOTE',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '796',"+
        "					 'name': 'HACARÍ',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '797',"+
        "					 'name': 'HERRÁN',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '798',"+
        "					 'name': 'LABATECA',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '799',"+
        "					 'name': 'LA ESPERANZA',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '800',"+
        "					 'name': 'LA PLAYA',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '801',"+
        "					 'name': 'LOS PATIOS',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '802',"+
        "					 'name': 'LOURDES',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '803',"+
        "					 'name': 'MUTISCUA',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '804',"+
        "					 'name': 'OCAÑA',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '805',"+
        "					 'name': 'PAMPLONA',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '806',"+
        "					 'name': 'PAMPLONITA',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '807',"+
        "					 'name': 'PUERTO SANTANDER',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '808',"+
        "					 'name': 'RAGONVALIA',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '809',"+
        "					 'name': 'SALAZAR',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '810',"+
        "					 'name': 'SAN CALIXTO',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '811',"+
        "					 'name': 'SAN CAYETANO',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '812',"+
        "					 'name': 'SANTIAGO',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '813',"+
        "					 'name': 'SARDINATA',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '814',"+
        "					 'name': 'SILOS',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '815',"+
        "					 'name': 'TEORAMA',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '816',"+
        "					 'name': 'TIBÚ',"+
        "					 'position': NumberInt(37)"+
        "				},"+
        "				 {"+
        "					 'id': '817',"+
        "					 'name': 'TOLEDO',"+
        "					 'position': NumberInt(38)"+
        "				},"+
        "				 {"+
        "					 'id': '818',"+
        "					 'name': 'VILLA CARO',"+
        "					 'position': NumberInt(39)"+
        "				},"+
        "				 {"+
        "					 'id': '819',"+
        "					 'name': 'VILLA DEL ROSARIO',"+
        "					 'position': NumberInt(40)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(286),"+
        "			 'name': 'Municipio (Putumayo)',"+
        "			 'help': 'Elige el municipio de Putumayo',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(34),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1074',"+
        "					 'name': 'MOCOA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1075',"+
        "					 'name': 'COLÓN',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '1076',"+
        "					 'name': 'ORITO',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '1077',"+
        "					 'name': 'PUERTO ASÍS',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '1078',"+
        "					 'name': 'PUERTO CAICEDO',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '1079',"+
        "					 'name': 'PUERTO GUZMÁN',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '1080',"+
        "					 'name': 'PUERTO LEGUÍZAMO',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '1081',"+
        "					 'name': 'SIBUNDOY',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '1082',"+
        "					 'name': 'SAN FRANCISCO',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '1083',"+
        "					 'name': 'SAN MIGUEL',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '1084',"+
        "					 'name': 'SANTIAGO',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '1085',"+
        "					 'name': 'VALLE DEL GUAMUEZ',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '1086',"+
        "					 'name': 'VILLAGARZÓN',"+
        "					 'position': NumberInt(13)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(287),"+
        "			 'name': 'Municipio (Quindio)',"+
        "			 'help': 'Elige el municipio de Quindio',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(34),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '820',"+
        "					 'name': 'ARMENIA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '821',"+
        "					 'name': 'BUENAVISTA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '822',"+
        "					 'name': 'CALARCÁ',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '823',"+
        "					 'name': 'CIRCASIA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '824',"+
        "					 'name': 'CÓRDOBA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '825',"+
        "					 'name': 'FILANDIA',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '826',"+
        "					 'name': 'GÉNOVA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '827',"+
        "					 'name': 'LA TEBAIDA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '828',"+
        "					 'name': 'MONTENEGRO',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '829',"+
        "					 'name': 'PIJAO',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '830',"+
        "					 'name': 'QUIMBAYA',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '831',"+
        "					 'name': 'SALENTO',"+
        "					 'position': NumberInt(12)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(288),"+
        "			 'name': 'Municipio (Risaralda)',"+
        "			 'help': 'Elige el municipio de Risaralda',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(34),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '832',"+
        "					 'name': 'PEREIRA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '833',"+
        "					 'name': 'APÍA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '834',"+
        "					 'name': 'BALBOA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '835',"+
        "					 'name': 'BELÉN DE UMBRÍA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '836',"+
        "					 'name': 'DOSQUEBRADAS',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '837',"+
        "					 'name': 'GUÁTICA',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '838',"+
        "					 'name': 'LA CELIA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '839',"+
        "					 'name': 'LA VIRGINIA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '840',"+
        "					 'name': 'MARSELLA',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '841',"+
        "					 'name': 'MISTRATÓ',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '842',"+
        "					 'name': 'PUEBLO RICO',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '843',"+
        "					 'name': 'QUINCHÍA',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '844',"+
        "					 'name': 'SANTA ROSA DE CABAL',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '845',"+
        "					 'name': 'SANTUARIO',"+
        "					 'position': NumberInt(14)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(289),"+
        "			 'name': 'Municipio (Santander)',"+
        "			 'help': 'Elige el municipio de Santander',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(35),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '846',"+
        "					 'name': 'BUCARAMANGA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '847',"+
        "					 'name': 'AGUADA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '848',"+
        "					 'name': 'ALBANIA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '849',"+
        "					 'name': 'ARATOCA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '850',"+
        "					 'name': 'BARBOSA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '851',"+
        "					 'name': 'BARICHARA',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '852',"+
        "					 'name': 'BARRANCABERMEJA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '853',"+
        "					 'name': 'BETULIA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '854',"+
        "					 'name': 'BOLÍVAR',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '855',"+
        "					 'name': 'CABRERA',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '856',"+
        "					 'name': 'CALIFORNIA',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '857',"+
        "					 'name': 'CAPITANEJO',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '858',"+
        "					 'name': 'CARCASÍ',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '859',"+
        "					 'name': 'CEPITÁ',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '860',"+
        "					 'name': 'CERRITO',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '861',"+
        "					 'name': 'CHARALÁ',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '862',"+
        "					 'name': 'CHARTA',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '863',"+
        "					 'name': 'CHIMA',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '864',"+
        "					 'name': 'CHIPATÁ',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '865',"+
        "					 'name': 'CIMITARRA',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '866',"+
        "					 'name': 'CONCEPCIÓN',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '867',"+
        "					 'name': 'CONFINES',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '868',"+
        "					 'name': 'CONTRATACIÓN',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '869',"+
        "					 'name': 'COROMORO',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '870',"+
        "					 'name': 'CURITÍ',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '871',"+
        "					 'name': 'EL CARMEN DE CHUCURÍ',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '872',"+
        "					 'name': 'EL GUACAMAYO',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '873',"+
        "					 'name': 'EL PEÑÓN',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '874',"+
        "					 'name': 'EL PLAYÓN',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '875',"+
        "					 'name': 'ENCINO',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '876',"+
        "					 'name': 'ENCISO',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '877',"+
        "					 'name': 'FLORIÁN',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '878',"+
        "					 'name': 'FLORIDABLANCA',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '879',"+
        "					 'name': 'GALÁN',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '880',"+
        "					 'name': 'GÁMBITA',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '881',"+
        "					 'name': 'GIRÓN',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '882',"+
        "					 'name': 'GUACA',"+
        "					 'position': NumberInt(37)"+
        "				},"+
        "				 {"+
        "					 'id': '883',"+
        "					 'name': 'GUADALUPE',"+
        "					 'position': NumberInt(38)"+
        "				},"+
        "				 {"+
        "					 'id': '884',"+
        "					 'name': 'GUAPOTÁ',"+
        "					 'position': NumberInt(39)"+
        "				},"+
        "				 {"+
        "					 'id': '885',"+
        "					 'name': 'GUAVATÁ',"+
        "					 'position': NumberInt(40)"+
        "				},"+
        "				 {"+
        "					 'id': '886',"+
        "					 'name': 'GÜEPSA',"+
        "					 'position': NumberInt(41)"+
        "				},"+
        "				 {"+
        "					 'id': '887',"+
        "					 'name': 'HATO',"+
        "					 'position': NumberInt(42)"+
        "				},"+
        "				 {"+
        "					 'id': '888',"+
        "					 'name': 'JESÚS MARÍA',"+
        "					 'position': NumberInt(43)"+
        "				},"+
        "				 {"+
        "					 'id': '889',"+
        "					 'name': 'JORDÁN',"+
        "					 'position': NumberInt(44)"+
        "				},"+
        "				 {"+
        "					 'id': '890',"+
        "					 'name': 'LA BELLEZA',"+
        "					 'position': NumberInt(45)"+
        "				},"+
        "				 {"+
        "					 'id': '891',"+
        "					 'name': 'LANDÁZURI',"+
        "					 'position': NumberInt(46)"+
        "				},"+
        "				 {"+
        "					 'id': '892',"+
        "					 'name': 'LA PAZ',"+
        "					 'position': NumberInt(47)"+
        "				},"+
        "				 {"+
        "					 'id': '893',"+
        "					 'name': 'LEBRIJA',"+
        "					 'position': NumberInt(48)"+
        "				},"+
        "				 {"+
        "					 'id': '894',"+
        "					 'name': 'LOS SANTOS',"+
        "					 'position': NumberInt(49)"+
        "				},"+
        "				 {"+
        "					 'id': '895',"+
        "					 'name': 'MACARAVITA',"+
        "					 'position': NumberInt(50)"+
        "				},"+
        "				 {"+
        "					 'id': '896',"+
        "					 'name': 'MÁLAGA',"+
        "					 'position': NumberInt(51)"+
        "				},"+
        "				 {"+
        "					 'id': '897',"+
        "					 'name': 'MATANZA',"+
        "					 'position': NumberInt(52)"+
        "				},"+
        "				 {"+
        "					 'id': '898',"+
        "					 'name': 'MOGOTES',"+
        "					 'position': NumberInt(53)"+
        "				},"+
        "				 {"+
        "					 'id': '899',"+
        "					 'name': 'MOLAGAVITA',"+
        "					 'position': NumberInt(54)"+
        "				},"+
        "				 {"+
        "					 'id': '900',"+
        "					 'name': 'OCAMONTE',"+
        "					 'position': NumberInt(55)"+
        "				},"+
        "				 {"+
        "					 'id': '901',"+
        "					 'name': 'OIBA',"+
        "					 'position': NumberInt(56)"+
        "				},"+
        "				 {"+
        "					 'id': '902',"+
        "					 'name': 'ONZAGA',"+
        "					 'position': NumberInt(57)"+
        "				},"+
        "				 {"+
        "					 'id': '903',"+
        "					 'name': 'PALMAR',"+
        "					 'position': NumberInt(58)"+
        "				},"+
        "				 {"+
        "					 'id': '904',"+
        "					 'name': 'PALMAS DEL SOCORRO',"+
        "					 'position': NumberInt(59)"+
        "				},"+
        "				 {"+
        "					 'id': '905',"+
        "					 'name': 'PÁRAMO',"+
        "					 'position': NumberInt(60)"+
        "				},"+
        "				 {"+
        "					 'id': '906',"+
        "					 'name': 'PIEDECUESTA',"+
        "					 'position': NumberInt(61)"+
        "				},"+
        "				 {"+
        "					 'id': '907',"+
        "					 'name': 'PINCHOTE',"+
        "					 'position': NumberInt(62)"+
        "				},"+
        "				 {"+
        "					 'id': '908',"+
        "					 'name': 'PUENTE NACIONAL',"+
        "					 'position': NumberInt(63)"+
        "				},"+
        "				 {"+
        "					 'id': '909',"+
        "					 'name': 'PUERTO PARRA',"+
        "					 'position': NumberInt(64)"+
        "				},"+
        "				 {"+
        "					 'id': '910',"+
        "					 'name': 'PUERTO WILCHES',"+
        "					 'position': NumberInt(65)"+
        "				},"+
        "				 {"+
        "					 'id': '911',"+
        "					 'name': 'RIONEGRO',"+
        "					 'position': NumberInt(66)"+
        "				},"+
        "				 {"+
        "					 'id': '912',"+
        "					 'name': 'SABANA DE TORRES',"+
        "					 'position': NumberInt(67)"+
        "				},"+
        "				 {"+
        "					 'id': '913',"+
        "					 'name': 'SAN ANDRÉS',"+
        "					 'position': NumberInt(68)"+
        "				},"+
        "				 {"+
        "					 'id': '914',"+
        "					 'name': 'SAN BENITO',"+
        "					 'position': NumberInt(69)"+
        "				},"+
        "				 {"+
        "					 'id': '915',"+
        "					 'name': 'SAN GIL',"+
        "					 'position': NumberInt(70)"+
        "				},"+
        "				 {"+
        "					 'id': '916',"+
        "					 'name': 'SAN JOAQUÍN',"+
        "					 'position': NumberInt(71)"+
        "				},"+
        "				 {"+
        "					 'id': '917',"+
        "					 'name': 'SAN JOSÉ DE MIRANDA',"+
        "					 'position': NumberInt(72)"+
        "				},"+
        "				 {"+
        "					 'id': '918',"+
        "					 'name': 'SAN MIGUEL',"+
        "					 'position': NumberInt(73)"+
        "				},"+
        "				 {"+
        "					 'id': '919',"+
        "					 'name': 'SAN VICENTE DE CHUCURÍ',"+
        "					 'position': NumberInt(74)"+
        "				},"+
        "				 {"+
        "					 'id': '920',"+
        "					 'name': 'SANTA BÁRBARA',"+
        "					 'position': NumberInt(75)"+
        "				},"+
        "				 {"+
        "					 'id': '921',"+
        "					 'name': 'SANTA HELENA DEL OPÓN',"+
        "					 'position': NumberInt(76)"+
        "				},"+
        "				 {"+
        "					 'id': '922',"+
        "					 'name': 'SIMACOTA',"+
        "					 'position': NumberInt(77)"+
        "				},"+
        "				 {"+
        "					 'id': '923',"+
        "					 'name': 'SOCORRO',"+
        "					 'position': NumberInt(78)"+
        "				},"+
        "				 {"+
        "					 'id': '924',"+
        "					 'name': 'SUAITA',"+
        "					 'position': NumberInt(79)"+
        "				},"+
        "				 {"+
        "					 'id': '925',"+
        "					 'name': 'SUCRE',"+
        "					 'position': NumberInt(80)"+
        "				},"+
        "				 {"+
        "					 'id': '926',"+
        "					 'name': 'SURATÁ',"+
        "					 'position': NumberInt(81)"+
        "				},"+
        "				 {"+
        "					 'id': '927',"+
        "					 'name': 'TONA',"+
        "					 'position': NumberInt(82)"+
        "				},"+
        "				 {"+
        "					 'id': '928',"+
        "					 'name': 'VALLE DE SAN JOSÉ',"+
        "					 'position': NumberInt(83)"+
        "				},"+
        "				 {"+
        "					 'id': '929',"+
        "					 'name': 'VÉLEZ',"+
        "					 'position': NumberInt(84)"+
        "				},"+
        "				 {"+
        "					 'id': '930',"+
        "					 'name': 'VETAS',"+
        "					 'position': NumberInt(85)"+
        "				},"+
        "				 {"+
        "					 'id': '931',"+
        "					 'name': 'VILLANUEVA',"+
        "					 'position': NumberInt(86)"+
        "				},"+
        "				 {"+
        "					 'id': '932',"+
        "					 'name': 'ZAPATOCA',"+
        "					 'position': NumberInt(87)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(290),"+
        "			 'name': 'Municipio (Bogota DC)',"+
        "			 'help': 'Elige el municipio de Bogota DC',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(36),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '149',"+
        "					 'name': 'BOGOTÁ',"+
        "					 'position': NumberInt(1)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(291),"+
        "			 'name': 'Municipio (Sucre)',"+
        "			 'help': 'Elige el municipio de Sucre',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(37),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '933',"+
        "					 'name': 'SINCELEJO',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '934',"+
        "					 'name': 'BUENAVISTA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '935',"+
        "					 'name': 'CAIMITO',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '936',"+
        "					 'name': 'COLOSO',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '937',"+
        "					 'name': 'COROZAL',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '938',"+
        "					 'name': 'COVEÑAS',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '939',"+
        "					 'name': 'CHALÁN',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '940',"+
        "					 'name': 'EL ROBLE',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '941',"+
        "					 'name': 'GALERAS',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '942',"+
        "					 'name': 'GUARANDA',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '943',"+
        "					 'name': 'LA UNIÓN',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '944',"+
        "					 'name': 'LOS PALMITOS',"+
        "					 'position': NumberInt(12)"+
        "				},";
        String json3 =         "				 {"+
        "					 'id': '945',"+
        "					 'name': 'MAJAGUAL',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '946',"+
        "					 'name': 'MORROA',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '947',"+
        "					 'name': 'OVEJAS',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '948',"+
        "					 'name': 'PALMITO',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '949',"+
        "					 'name': 'SAMPUÉS',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '950',"+
        "					 'name': 'SAN BENITO ABAD',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '951',"+
        "					 'name': 'SAN JUAN DE BETULIA',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '952',"+
        "					 'name': 'SAN MARCOS',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '953',"+
        "					 'name': 'SAN ONOFRE',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '954',"+
        "					 'name': 'SAN PEDRO',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '955',"+
        "					 'name': 'SAN LUIS DE SINCÉ',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '956',"+
        "					 'name': 'SUCRE',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '957',"+
        "					 'name': 'SANTIAGO DE TOLÚ',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '958',"+
        "					 'name': 'TOLÚ VIEJO',"+
        "					 'position': NumberInt(26)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(292),"+
        "			 'name': 'Municipio (Tolima)',"+
        "			 'help': 'Elige el municipio de Tolima',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(38),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '959',"+
        "					 'name': 'IBAGUÉ',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '960',"+
        "					 'name': 'ALPUJARRA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '961',"+
        "					 'name': 'ALVARADO',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '962',"+
        "					 'name': 'AMBALEMA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '963',"+
        "					 'name': 'ANZOÁTEGUI',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '964',"+
        "					 'name': 'ARMERO GUAYABAL',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '965',"+
        "					 'name': 'ATACO',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '966',"+
        "					 'name': 'CAJAMARCA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '967',"+
        "					 'name': 'CARMEN DE APICALÁ',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '968',"+
        "					 'name': 'CASABIANCA',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '969',"+
        "					 'name': 'CHAPARRAL',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '970',"+
        "					 'name': 'COELLO',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '971',"+
        "					 'name': 'COYAIMA',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '972',"+
        "					 'name': 'CUNDAY',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '973',"+
        "					 'name': 'DOLORES',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '974',"+
        "					 'name': 'ESPINAL',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '975',"+
        "					 'name': 'FALAN',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '976',"+
        "					 'name': 'FLANDES',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '977',"+
        "					 'name': 'FRESNO',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '978',"+
        "					 'name': 'GUAMO',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '979',"+
        "					 'name': 'HERVEO',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '980',"+
        "					 'name': 'HONDA',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '981',"+
        "					 'name': 'ICONONZO',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '982',"+
        "					 'name': 'LÉRIDA',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '983',"+
        "					 'name': 'LÍBANO',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '984',"+
        "					 'name': 'SAN SEBASTIÁN DE MARIQUITA',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '985',"+
        "					 'name': 'MELGAR',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '986',"+
        "					 'name': 'MURILLO',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '987',"+
        "					 'name': 'NATAGAIMA',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '988',"+
        "					 'name': 'ORTEGA',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '989',"+
        "					 'name': 'PALOCABILDO',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '990',"+
        "					 'name': 'PIEDRAS',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '991',"+
        "					 'name': 'PLANADAS',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '992',"+
        "					 'name': 'PRADO',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '993',"+
        "					 'name': 'PURIFICACIÓN',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '994',"+
        "					 'name': 'RIOBLANCO',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '995',"+
        "					 'name': 'RONCESVALLES',"+
        "					 'position': NumberInt(37)"+
        "				},"+
        "				 {"+
        "					 'id': '996',"+
        "					 'name': 'ROVIRA',"+
        "					 'position': NumberInt(38)"+
        "				},"+
        "				 {"+
        "					 'id': '997',"+
        "					 'name': 'SALDAÑA',"+
        "					 'position': NumberInt(39)"+
        "				},"+
        "				 {"+
        "					 'id': '998',"+
        "					 'name': 'SAN ANTONIO',"+
        "					 'position': NumberInt(40)"+
        "				},"+
        "				 {"+
        "					 'id': '999',"+
        "					 'name': 'SAN LUIS',"+
        "					 'position': NumberInt(41)"+
        "				},"+
        "				 {"+
        "					 'id': '1000',"+
        "					 'name': 'SANTA ISABEL',"+
        "					 'position': NumberInt(42)"+
        "				},"+
        "				 {"+
        "					 'id': '1001',"+
        "					 'name': 'SUÁREZ',"+
        "					 'position': NumberInt(43)"+
        "				},"+
        "				 {"+
        "					 'id': '1002',"+
        "					 'name': 'VALLE DE SAN JUAN',"+
        "					 'position': NumberInt(44)"+
        "				},"+
        "				 {"+
        "					 'id': '1003',"+
        "					 'name': 'VENADILLO',"+
        "					 'position': NumberInt(45)"+
        "				},"+
        "				 {"+
        "					 'id': '1004',"+
        "					 'name': 'VILLAHERMOSA',"+
        "					 'position': NumberInt(46)"+
        "				},"+
        "				 {"+
        "					 'id': '1005',"+
        "					 'name': 'VILLARRICA',"+
        "					 'position': NumberInt(47)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(262),"+
        "			 'name': 'Municipio (Valle del Cauca)',"+
        "			 'help': 'Elige el municipio del Valle del Cauca',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(39),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1006',"+
        "					 'name': 'CALI',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1007',"+
        "					 'name': 'ALCALÁ',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '1008',"+
        "					 'name': 'ANDALUCÍA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '1009',"+
        "					 'name': 'ANSERMANUEVO',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '1010',"+
        "					 'name': 'ARGELIA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '1011',"+
        "					 'name': 'BOLÍVAR',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '1012',"+
        "					 'name': 'BUENAVENTURA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '1013',"+
        "					 'name': 'GUADALAJARA DE BUGA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '1014',"+
        "					 'name': 'BUGALAGRANDE',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '1015',"+
        "					 'name': 'CAICEDONIA',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '1016',"+
        "					 'name': 'CALIMA',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '1017',"+
        "					 'name': 'CANDELARIA',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '1018',"+
        "					 'name': 'CARTAGO',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '1019',"+
        "					 'name': 'DAGUA',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '1020',"+
        "					 'name': 'EL ÁGUILA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '1021',"+
        "					 'name': 'EL CAIRO',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '1022',"+
        "					 'name': 'EL CERRITO',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '1023',"+
        "					 'name': 'EL DOVIO',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '1024',"+
        "					 'name': 'FLORIDA',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '1025',"+
        "					 'name': 'GINEBRA',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '1026',"+
        "					 'name': 'GUACARÍ',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '1027',"+
        "					 'name': 'JAMUNDÍ',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '1028',"+
        "					 'name': 'LA CUMBRE',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '1029',"+
        "					 'name': 'LA UNIÓN',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '1030',"+
        "					 'name': 'LA VICTORIA',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '1031',"+
        "					 'name': 'OBANDO',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '1032',"+
        "					 'name': 'PALMIRA',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '1033',"+
        "					 'name': 'PRADERA',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '1034',"+
        "					 'name': 'RESTREPO',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '1035',"+
        "					 'name': 'RIOFRÍO',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '1036',"+
        "					 'name': 'ROLDANILLO',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '1037',"+
        "					 'name': 'SAN PEDRO',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '1038',"+
        "					 'name': 'SEVILLA',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '1039',"+
        "					 'name': 'TORO',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '1040',"+
        "					 'name': 'TRUJILLO',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '1041',"+
        "					 'name': 'TULUÁ',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '1042',"+
        "					 'name': 'ULLOA',"+
        "					 'position': NumberInt(37)"+
        "				},"+
        "				 {"+
        "					 'id': '1043',"+
        "					 'name': 'VERSALLES',"+
        "					 'position': NumberInt(38)"+
        "				},"+
        "				 {"+
        "					 'id': '1044',"+
        "					 'name': 'VIJES',"+
        "					 'position': NumberInt(39)"+
        "				},"+
        "				 {"+
        "					 'id': '1045',"+
        "					 'name': 'YOTOCO',"+
        "					 'position': NumberInt(40)"+
        "				},"+
        "				 {"+
        "					 'id': '1046',"+
        "					 'name': 'YUMBO',"+
        "					 'position': NumberInt(41)"+
        "				},"+
        "				 {"+
        "					 'id': '1047',"+
        "					 'name': 'ZARZAL',"+
        "					 'position': NumberInt(42)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(293),"+
        "			 'name': 'Municipio (Vaupes)',"+
        "			 'help': 'Elige el municipio de Vaupes',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(40),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1113',"+
        "					 'name': 'MITÚ',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1114',"+
        "					 'name': 'CARURÚ',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '1115',"+
        "					 'name': 'PACOA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '1116',"+
        "					 'name': 'TARAIRA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '1117',"+
        "					 'name': 'PAPUNAUA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '1118',"+
        "					 'name': 'YAVARATÉ',"+
        "					 'position': NumberInt(6)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(294),"+
        "			 'name': 'Municipio (Vichada)',"+
        "			 'help': 'Elige el municipio de Vichada',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(41),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'entities',"+
        "				 'target_column': 'id_municipality_ent',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1119',"+
        "					 'name': 'PUERTO CARREÑO',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1120',"+
        "					 'name': 'LA PRIMAVERA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '1121',"+
        "					 'name': 'SANTA ROSALÍA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '1122',"+
        "					 'name': 'CUMARIBO',"+
        "					 'position': NumberInt(4)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(96),"+
        "			 'name': 'Direccion',"+
        "			 'help': '',"+
        "			 'subject': 'Productor',"+
        "			 'position': NumberInt(42),"+
        "			 'is_required': false,"+
        "			 'is_filterable': false,"+
        "			 'data_type': NumberInt(1),"+
        "			 'widget': NumberInt(6),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'producers',"+
        "				 'target_column': 'address_pro',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': 'id_entity_pro'"+
        "			},"+
        "			 'Choices': ["+
        "				"+
        "			]"+
        "		}"+
        "	]"+
        "},"+
        " 'form_id': '4',"+
        " 'inserted_at': '"+dateNow+"',"+
        " 'inserted_by': {"+
        "	 'user_id': '0',"+
        "	 'user_uid': '0',"+
        "	 'user_fullname': 'ANONYMUS',"+
        "	 'ip_address': '190.122.75.2'"+
        "},"+
        " 'user_id': '"+data.get("userMobileId")+"'}";
        */
        
      /*
        "90": "Tipo de documento" => docType
        "91": "Numero de documento" => docNum
        "92": "Primer nombre" => firstName1
        "93": "Segundo nombre" => firstName2
        "94": "Primer apellido" => lastName1
        "95": "Segundo apellido" => lastName2
        "96": "Direccion" => direction
        "99": "Telefono fijo" => phone
        "251": "Celular" => cellphone
        "252": "Correo electrónico" => email
        "260": "Dígito de verificacion" => validation
        "261": "Departamento" => department
        "262": "Municipio (Valle del Cauca)" => municipality
        "263": "Municipio (Amazonas)"
        "264": "Municipio (Antioquia)"
        "265": "Municipio (Arauca)"
        "266": "Municipio (San Andrés, Providencia, Santa Catalina)"
        "267": "Municipio (Atlántico)"
        "268": "Municipio (Bolívar)"
        "269": "Municipio (Boyaca)"
        "270": "Municipio (Caldas)"
        "271": "Municipio (Caqueta)"
        "272": "Municipio (Casanare)"
        "273": "Municipio (Cauca)"
        "274": "Municipio (Cesar)"
        "275": "Municipio (Choco)"
        "276": "Municipio (Córdoba)"
        "277": "Municipio (Cundinamarca)"
        "278": "Municipio (Guainia)"
        "279": "Municipio (Guaviare)"
        "280": "Municipio (Huila)"
        "281": "Municipio (La Guajira)"
        "282": "Municipio (Magdalena)"
        "283": "Municipio (Meta)"
        "284": "Municipio (Nariño)"
        "285": "Municipio (Norte de Santander)"
        "286": "Municipio (Putumayo)"
        "287": "Municipio (Quindio)"
        "288": "Municipio (Risaralda)"
        "289": "Municipio (Santander)"
        "290": "Municipio (Bogota DC)"
        "291": "Municipio (Sucre)"
        "292": "Municipio (Tolima)"
        "293": "Municipio (Vaupes)"
        "294": "Municipio (Vichada)"
        "295": "Nombre de la empresa"         
       */
//        String json = json1+json2+json3;
        String json = json1;
        
//        System.out.println("json=>"+json);
        
//        String json = "{'database' : 'mkyongDB','_id': ObjectId('5403b8de9859f75c6373b"+data.get("entId")+"'),'table' : 'hosting'," +
//            "'detail' : {'records' : 99, 'index' : 'vps_index1', 'active' : 'true'}}}";
        
        System.out.println("json=>"+json);

        BasicDBObject dataMain  = (BasicDBObject)JSON.parse(json);             
        dataMain.put("InsertedId", data.get("entId"));         
                
        BasicDBObject insertIds = new BasicDBObject(); 
        insertIds.put("#main", data.get("entId"));
        insertIds.put("producers", data.get("prodId"));
        dataMain.put("InsertIds", insertIds);
        
        BasicDBObject coords    = new BasicDBObject();    
        coords.put("lat", "0");
        coords.put("lng", "0");
        coords.put("alt", "0");
        dataMain.put("coords", coords);
        
        BasicDBObject dataInfo  = new BasicDBObject();
        dataInfo.put("form_id", "4");
        dataInfo.put("isCompleted", "false");
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");        
        String dateIn = formatoDelTexto.format(ahora);        
        
        dataInfo.put("created_at", '"'+dateIn+'"');
        dataInfo.put("qid", "92");
        dataInfo.put("del", "false");
        dataInfo.put("id", "_Yk3mwa929ytQ");
        
        BasicDBObject info = new BasicDBObject();
		info.put("90", "");
        info.put("91", "");
        info.put("92", "");
        info.put("93", "");
        info.put("94", "");
        info.put("95", "");
        info.put("96", "");
        info.put("99", "");
        info.put("251", "");
        info.put("252", "");
        info.put("260", "");
        info.put("261", "");
        info.put("262", "");
        info.put("263", "");
        info.put("264", "");
        info.put("265", "");
        info.put("266", "");
        info.put("267", "");
        info.put("268", "");
        info.put("269", "");
        info.put("270", "");
        info.put("271", "");
        info.put("272", "");
        info.put("273", "");
        info.put("274", "");
        info.put("275", "");
        info.put("276", "");
        info.put("277", "");
        info.put("278", "");
        info.put("279", "");
        info.put("280", "");
        info.put("281", "");
        info.put("282", "");
        info.put("283", "");
        info.put("284", "");
        info.put("285", "");
        info.put("286", "");
        info.put("287", "");
        info.put("288", "");
        info.put("289", "");
        info.put("290", "");
        info.put("291", "");
        info.put("292", "");
        info.put("293", "");
        info.put("294", "");
        info.put("295", "");
        
        info.put("90", data.get("docType"));
        info.put("91", data.get("docNum"));
        info.put("92", data.get("firstName1"));
        info.put("93", data.get("firstName2"));
        info.put("94", data.get("lastName1"));
        info.put("95", data.get("lastName2"));
        info.put("96", data.get("direction"));
        info.put("99", data.get("phone"));
        info.put("251", data.get("cellphone"));
        info.put("252", data.get("email"));
        info.put("260", data.get("validation"));
        info.put("261", data.get("department"));
        System.out.println("depId=>"+data.get("department"));
        
        String valMun = GlobalFunctions.getValuesMunicipality(String.valueOf(data.get("department")));
        info.put(valMun, data.get("municipality"));        
        dataInfo.put("survey_solution", info);
        dataMain.put("data", dataInfo);
        System.out.println("json=>"+dataMain.toString());
        
        return dataMain;
    }
  
    
    public static BasicDBObject generateJSONFarm(HashMap data)
    {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateNow = formateador.format(ahora);
        String json1 = "'_id': ObjectId('5403b9729859f73c6373b564'),"+                
        " 'form': {"+
        "	 'id': NumberInt(3),"+
        "	 'name': 'Finca',"+
        "	 'description': 'Formulario para agregar/editar finca al sistema',"+
        "	 'del': false,"+
        "	 'qid': '102',"+
        "	 'main_table': 'farms',"+
        "	 'HiddenFields': ["+
        "		 {"+
        "			 'id_survey_hidden_field': '4',"+
        "			 'target_table': 'farms',"+
        "			 'target_column': 'status',"+
        "			 'value': '1'"+
        "		},"+
        "		 {"+
        "			 'id_survey_hidden_field': '5',"+
        "			 'target_table': 'farms',"+
        "			 'target_column': 'georef_far',"+
        "			 'value': '1'"+
        "		}"+
        "	],"+
        "	 'Questions': ["+
        "		 {"+
        "			 'id': NumberInt(241),"+
        "			 'name': 'Seleccione el productor asociado',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(1),"+
        "			 'is_required': true,"+
        "			 'is_filterable': false,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms_producers',"+
        "				 'target_column': 'id_producer_far_pro',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': 'id_farm_far_pro'"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '"+data.get("prodId")+"',"+
        "					 'name': '"+data.get("nameProd")+"',"+
        "					 'position': NumberInt(1)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(102),"+
        "			 'name': 'Nombre de la finca',"+
        "			 'help': 'Ingrese el nombre de la finca',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(2),"+
        "			 'is_required': true,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(1),"+
        "			 'widget': NumberInt(6),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'name_far',"+
        "				 'target_column_lng': null,"+
        "				 'target_column_alt': null,"+
        "				 'target_column_self': null"+
        "			},"+
        "			 'Choices': ["+
        "				"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(103),"+
        "			 'name': 'Capturar posicion',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(3),"+
        "			 'is_required': true,"+
        "			 'is_filterable': false,"+
        "			 'data_type': NumberInt(5),"+
        "			 'widget': NumberInt(7),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'latitude_far',"+
        "				 'target_column_lng': 'longitude_far',"+
        "				 'target_column_alt': 'altitude_far',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(108),"+
        "			 'name': 'Vereda',"+
        "			 'help': 'Ingrese el nombre de la vereda',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(4),"+
        "			 'is_required': true,"+
        "			 'is_filterable': false,"+
        "			 'data_type': NumberInt(1),"+
        "			 'widget': NumberInt(6),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'name_commune_far',"+
        "				 'target_column_lng': null,"+
        "				 'target_column_alt': null,"+
        "				 'target_column_self': null"+
        "			},"+
        "			 'Choices': ["+
        "				"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(105),"+
        "			 'name': 'Indicación (como llegar)',"+
        "			 'help': 'Describe aquí como se llega fácilmente a la finca',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(5),"+
        "			 'is_required': false,"+
        "			 'is_filterable': false,"+
        "			 'data_type': NumberInt(1),"+
        "			 'widget': NumberInt(4),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'address_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(336),"+
        "			 'name': 'Departamento',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(117),"+
        "			 'is_required': true,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'phone_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '29',"+
        "					 'name': 'AMAZONAS',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1',"+
        "					 'name': 'ANTIOQUIA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '25',"+
        "					 'name': 'ARAUCA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '28',"+
        "					 'name': 'ARCHIPIÉLAGO DE SAN ANDRÉS',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '2',"+
        "					 'name': 'ATLÁNTICO',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '3',"+
        "					 'name': 'BOGOTÁ',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '4',"+
        "					 'name': 'BOLÍVAR',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '5',"+
        "					 'name': 'BOYACÁ',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '6',"+
        "					 'name': 'CALDAS',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '7',"+
        "					 'name': 'CAQUETÁ',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '26',"+
        "					 'name': 'CASANARE',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '8',"+
        "					 'name': 'CAUCA',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '9',"+
        "					 'name': 'CESAR',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '12',"+
        "					 'name': 'CHOCÓ',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '10',"+
        "					 'name': 'CÓRDOBA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '11',"+
        "					 'name': 'CUNDINAMARCA',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '30',"+
        "					 'name': 'GUAINÍA',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '31',"+
        "					 'name': 'GUAVIARE',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '13',"+
        "					 'name': 'HUILA',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '14',"+
        "					 'name': 'LA GUAJIRA',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '15',"+
        "					 'name': 'MAGDALENA',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '16',"+
        "					 'name': 'META',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '17',"+
        "					 'name': 'NARIÑO',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '18',"+
        "					 'name': 'NORTE DE SANTANDER',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '27',"+
        "					 'name': 'PUTUMAYO',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '19',"+
        "					 'name': 'QUINDIO',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '20',"+
        "					 'name': 'RISARALDA',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '21',"+
        "					 'name': 'SANTANDER',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '22',"+
        "					 'name': 'SUCRE',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '23',"+
        "					 'name': 'TOLIMA',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '24',"+
        "					 'name': 'VALLE DEL CAUCA',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '32',"+
        "					 'name': 'VAUPÉS',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '33',"+
        "					 'name': 'VICHADA',"+
        "					 'position': NumberInt(33)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(338),"+
        "			 'name': 'Municipio (Amazonas)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(118),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1089',"+
        "					 'name': 'LETICIA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1090',"+
        "					 'name': 'EL ENCANTO',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '1091',"+
        "					 'name': 'LA CHORRERA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '1092',"+
        "					 'name': 'LA PEDRERA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '1093',"+
        "					 'name': 'LA VICTORIA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '1094',"+
        "					 'name': 'MIRITÍ - PARANÁ',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '1095',"+
        "					 'name': 'PUERTO ALEGRÍA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '1096',"+
        "					 'name': 'PUERTO ARICA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '1097',"+
        "					 'name': 'PUERTO NARIÑO',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '1098',"+
        "					 'name': 'PUERTO SANTANDER',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '1099',"+
        "					 'name': 'TARAPACÁ',"+
        "					 'position': NumberInt(11)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(339),"+
        "			 'name': 'Municipio (Antioquia)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(119),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1',"+
        "					 'name': 'MEDELLÍN',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '2',"+
        "					 'name': 'ABEJORRAL',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '3',"+
        "					 'name': 'ABRIAQUÍ',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '4',"+
        "					 'name': 'ALEJANDRÍA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '5',"+
        "					 'name': 'AMAGÁ',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '6',"+
        "					 'name': 'AMALFI',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '7',"+
        "					 'name': 'ANDES',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '8',"+
        "					 'name': 'ANGELÓPOLIS',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '9',"+
        "					 'name': 'ANGOSTURA',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '10',"+
        "					 'name': 'ANORÍ',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '11',"+
        "					 'name': 'SANTA FÉ DE ANTIOQUIA',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '12',"+
        "					 'name': 'ANZÁ',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '13',"+
        "					 'name': 'APARTADÓ',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '14',"+
        "					 'name': 'ARBOLETES',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '15',"+
        "					 'name': 'ARGELIA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '16',"+
        "					 'name': 'ARMENIA',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '17',"+
        "					 'name': 'BARBOSA',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '18',"+
        "					 'name': 'BELMIRA',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '19',"+
        "					 'name': 'BELLO',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '20',"+
        "					 'name': 'BETANIA',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '21',"+
        "					 'name': 'BETULIA',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '22',"+
        "					 'name': 'CIUDAD BOLÍVAR',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '23',"+
        "					 'name': 'BRICEÑO',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '24',"+
        "					 'name': 'BURITICÁ',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '25',"+
        "					 'name': 'CÁCERES',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '26',"+
        "					 'name': 'CAICEDO',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '27',"+
        "					 'name': 'CALDAS',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '28',"+
        "					 'name': 'CAMPAMENTO',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '29',"+
        "					 'name': 'CAÑASGORDAS',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '30',"+
        "					 'name': 'CARACOLÍ',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '31',"+
        "					 'name': 'CARAMANTA',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '32',"+
        "					 'name': 'CAREPA',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '33',"+
        "					 'name': 'EL CARMEN DE VIBORAL',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '34',"+
        "					 'name': 'CAROLINA',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '35',"+
        "					 'name': 'CAUCASIA',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '36',"+
        "					 'name': 'CHIGORODÓ',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '37',"+
        "					 'name': 'CISNEROS',"+
        "					 'position': NumberInt(37)"+
        "				},"+
        "				 {"+
        "					 'id': '38',"+
        "					 'name': 'COCORNÁ',"+
        "					 'position': NumberInt(38)"+
        "				},"+
        "				 {"+
        "					 'id': '39',"+
        "					 'name': 'CONCEPCIÓN',"+
        "					 'position': NumberInt(39)"+
        "				},"+
        "				 {"+
        "					 'id': '40',"+
        "					 'name': 'CONCORDIA',"+
        "					 'position': NumberInt(40)"+
        "				},"+
        "				 {"+
        "					 'id': '41',"+
        "					 'name': 'COPACABANA',"+
        "					 'position': NumberInt(41)"+
        "				},"+
        "				 {"+
        "					 'id': '42',"+
        "					 'name': 'DABEIBA',"+
        "					 'position': NumberInt(42)"+
        "				},"+
        "				 {"+
        "					 'id': '43',"+
        "					 'name': 'DONMATÍAS',"+
        "					 'position': NumberInt(43)"+
        "				},"+
        "				 {"+
        "					 'id': '44',"+
        "					 'name': 'EBÉJICO',"+
        "					 'position': NumberInt(44)"+
        "				},"+
        "				 {"+
        "					 'id': '45',"+
        "					 'name': 'EL BAGRE',"+
        "					 'position': NumberInt(45)"+
        "				},"+
        "				 {"+
        "					 'id': '46',"+
        "					 'name': 'ENTRERRÍOS',"+
        "					 'position': NumberInt(46)"+
        "				},"+
        "				 {"+
        "					 'id': '47',"+
        "					 'name': 'ENVIGADO',"+
        "					 'position': NumberInt(47)"+
        "				},"+
        "				 {"+
        "					 'id': '48',"+
        "					 'name': 'FREDONIA',"+
        "					 'position': NumberInt(48)"+
        "				},"+
        "				 {"+
        "					 'id': '49',"+
        "					 'name': 'FRONTINO',"+
        "					 'position': NumberInt(49)"+
        "				},"+
        "				 {"+
        "					 'id': '50',"+
        "					 'name': 'GIRALDO',"+
        "					 'position': NumberInt(50)"+
        "				},"+
        "				 {"+
        "					 'id': '51',"+
        "					 'name': 'GIRARDOTA',"+
        "					 'position': NumberInt(51)"+
        "				},"+
        "				 {"+
        "					 'id': '52',"+
        "					 'name': 'GÓMEZ PLATA',"+
        "					 'position': NumberInt(52)"+
        "				},"+
        "				 {"+
        "					 'id': '53',"+
        "					 'name': 'GRANADA',"+
        "					 'position': NumberInt(53)"+
        "				},"+
        "				 {"+
        "					 'id': '54',"+
        "					 'name': 'GUADALUPE',"+
        "					 'position': NumberInt(54)"+
        "				},"+
        "				 {"+
        "					 'id': '55',"+
        "					 'name': 'GUARNE',"+
        "					 'position': NumberInt(55)"+
        "				},"+
        "				 {"+
        "					 'id': '56',"+
        "					 'name': 'GUATAPÉ',"+
        "					 'position': NumberInt(56)"+
        "				},"+
        "				 {"+
        "					 'id': '57',"+
        "					 'name': 'HELICONIA',"+
        "					 'position': NumberInt(57)"+
        "				},"+
        "				 {"+
        "					 'id': '58',"+
        "					 'name': 'HISPANIA',"+
        "					 'position': NumberInt(58)"+
        "				},"+
        "				 {"+
        "					 'id': '59',"+
        "					 'name': 'ITAGÜÍ',"+
        "					 'position': NumberInt(59)"+
        "				},"+
        "				 {"+
        "					 'id': '60',"+
        "					 'name': 'ITUANGO',"+
        "					 'position': NumberInt(60)"+
        "				},"+
        "				 {"+
        "					 'id': '61',"+
        "					 'name': 'JARDÍN',"+
        "					 'position': NumberInt(61)"+
        "				},"+
        "				 {"+
        "					 'id': '62',"+
        "					 'name': 'JERICÓ',"+
        "					 'position': NumberInt(62)"+
        "				},"+
        "				 {"+
        "					 'id': '63',"+
        "					 'name': 'LA CEJA',"+
        "					 'position': NumberInt(63)"+
        "				},"+
        "				 {"+
        "					 'id': '64',"+
        "					 'name': 'LA ESTRELLA',"+
        "					 'position': NumberInt(64)"+
        "				},"+
        "				 {"+
        "					 'id': '65',"+
        "					 'name': 'LA PINTADA',"+
        "					 'position': NumberInt(65)"+
        "				},"+
        "				 {"+
        "					 'id': '66',"+
        "					 'name': 'LA UNIÓN',"+
        "					 'position': NumberInt(66)"+
        "				},"+
        "				 {"+
        "					 'id': '67',"+
        "					 'name': 'LIBORINA',"+
        "					 'position': NumberInt(67)"+
        "				},"+
        "				 {"+
        "					 'id': '68',"+
        "					 'name': 'MACEO',"+
        "					 'position': NumberInt(68)"+
        "				},"+
        "				 {"+
        "					 'id': '69',"+
        "					 'name': 'MARINILLA',"+
        "					 'position': NumberInt(69)"+
        "				},"+
        "				 {"+
        "					 'id': '70',"+
        "					 'name': 'MONTEBELLO',"+
        "					 'position': NumberInt(70)"+
        "				},"+
        "				 {"+
        "					 'id': '71',"+
        "					 'name': 'MURINDÓ',"+
        "					 'position': NumberInt(71)"+
        "				},"+
        "				 {"+
        "					 'id': '72',"+
        "					 'name': 'MUTATÁ',"+
        "					 'position': NumberInt(72)"+
        "				},"+
        "				 {"+
        "					 'id': '73',"+
        "					 'name': 'NARIÑO',"+
        "					 'position': NumberInt(73)"+
        "				},"+
        "				 {"+
        "					 'id': '74',"+
        "					 'name': 'NECOCLÍ',"+
        "					 'position': NumberInt(74)"+
        "				},"+
        "				 {"+
        "					 'id': '75',"+
        "					 'name': 'NECHÍ',"+
        "					 'position': NumberInt(75)"+
        "				},"+
        "				 {"+
        "					 'id': '76',"+
        "					 'name': 'OLAYA',"+
        "					 'position': NumberInt(76)"+
        "				},"+
        "				 {"+
        "					 'id': '77',"+
        "					 'name': 'PEÑOL',"+
        "					 'position': NumberInt(77)"+
        "				},"+
        "				 {"+
        "					 'id': '78',"+
        "					 'name': 'PEQUE',"+
        "					 'position': NumberInt(78)"+
        "				},"+
        "				 {"+
        "					 'id': '79',"+
        "					 'name': 'PUEBLORRICO',"+
        "					 'position': NumberInt(79)"+
        "				},"+
        "				 {"+
        "					 'id': '80',"+
        "					 'name': 'PUERTO BERRÍO',"+
        "					 'position': NumberInt(80)"+
        "				},"+
        "				 {"+
        "					 'id': '81',"+
        "					 'name': 'PUERTO NARE',"+
        "					 'position': NumberInt(81)"+
        "				},"+
        "				 {"+
        "					 'id': '82',"+
        "					 'name': 'PUERTO TRIUNFO',"+
        "					 'position': NumberInt(82)"+
        "				},"+
        "				 {"+
        "					 'id': '83',"+
        "					 'name': 'REMEDIOS',"+
        "					 'position': NumberInt(83)"+
        "				},"+
        "				 {"+
        "					 'id': '84',"+
        "					 'name': 'RETIRO',"+
        "					 'position': NumberInt(84)"+
        "				},"+
        "				 {"+
        "					 'id': '85',"+
        "					 'name': 'RIONEGRO',"+
        "					 'position': NumberInt(85)"+
        "				},"+
        "				 {"+
        "					 'id': '86',"+
        "					 'name': 'SABANALARGA',"+
        "					 'position': NumberInt(86)"+
        "				},"+
        "				 {"+
        "					 'id': '87',"+
        "					 'name': 'SABANETA',"+
        "					 'position': NumberInt(87)"+
        "				},"+
        "				 {"+
        "					 'id': '88',"+
        "					 'name': 'SALGAR',"+
        "					 'position': NumberInt(88)"+
        "				},"+
        "				 {"+
        "					 'id': '89',"+
        "					 'name': 'SAN ANDRÉS DE CUERQUÍA',"+
        "					 'position': NumberInt(89)"+
        "				},"+
        "				 {"+
        "					 'id': '90',"+
        "					 'name': 'SAN CARLOS',"+
        "					 'position': NumberInt(90)"+
        "				},"+
        "				 {"+
        "					 'id': '91',"+
        "					 'name': 'SAN FRANCISCO',"+
        "					 'position': NumberInt(91)"+
        "				},"+
        "				 {"+
        "					 'id': '92',"+
        "					 'name': 'SAN JERÓNIMO',"+
        "					 'position': NumberInt(92)"+
        "				},"+
        "				 {"+
        "					 'id': '93',"+
        "					 'name': 'SAN JOSÉ DE LA MONTAÑA',"+
        "					 'position': NumberInt(93)"+
        "				},"+
        "				 {"+
        "					 'id': '94',"+
        "					 'name': 'SAN JUAN DE URABÁ',"+
        "					 'position': NumberInt(94)"+
        "				},"+
        "				 {"+
        "					 'id': '95',"+
        "					 'name': 'SAN LUIS',"+
        "					 'position': NumberInt(95)"+
        "				},"+
        "				 {"+
        "					 'id': '96',"+
        "					 'name': 'SAN PEDRO DE LOS MILAGROS',"+
        "					 'position': NumberInt(96)"+
        "				},"+
        "				 {"+
        "					 'id': '97',"+
        "					 'name': 'SAN PEDRO DE URABÁ',"+
        "					 'position': NumberInt(97)"+
        "				},"+
        "				 {"+
        "					 'id': '98',"+
        "					 'name': 'SAN RAFAEL',"+
        "					 'position': NumberInt(98)"+
        "				},"+
        "				 {"+
        "					 'id': '99',"+
        "					 'name': 'SAN ROQUE',"+
        "					 'position': NumberInt(99)"+
        "				},"+
        "				 {"+
        "					 'id': '100',"+
        "					 'name': 'SAN VICENTE FERRER',"+
        "					 'position': NumberInt(100)"+
        "				},"+
        "				 {"+
        "					 'id': '101',"+
        "					 'name': 'SANTA BÁRBARA',"+
        "					 'position': NumberInt(101)"+
        "				},"+
        "				 {"+
        "					 'id': '102',"+
        "					 'name': 'SANTA ROSA DE OSOS',"+
        "					 'position': NumberInt(102)"+
        "				},"+
        "				 {"+
        "					 'id': '103',"+
        "					 'name': 'SANTO DOMINGO',"+
        "					 'position': NumberInt(103)"+
        "				},"+
        "				 {"+
        "					 'id': '104',"+
        "					 'name': 'EL SANTUARIO',"+
        "					 'position': NumberInt(104)"+
        "				},"+
        "				 {"+
        "					 'id': '105',"+
        "					 'name': 'SEGOVIA',"+
        "					 'position': NumberInt(105)"+
        "				},"+
        "				 {"+
        "					 'id': '106',"+
        "					 'name': 'SONSÓN',"+
        "					 'position': NumberInt(106)"+
        "				},"+
        "				 {"+
        "					 'id': '107',"+
        "					 'name': 'SOPETRÁN',"+
        "					 'position': NumberInt(107)"+
        "				},"+
        "				 {"+
        "					 'id': '108',"+
        "					 'name': 'TÁMESIS',"+
        "					 'position': NumberInt(108)"+
        "				},"+
        "				 {"+
        "					 'id': '109',"+
        "					 'name': 'TARAZÁ',"+
        "					 'position': NumberInt(109)"+
        "				},"+
        "				 {"+
        "					 'id': '110',"+
        "					 'name': 'TARSO',"+
        "					 'position': NumberInt(110)"+
        "				},"+
        "				 {"+
        "					 'id': '111',"+
        "					 'name': 'TITIRIBÍ',"+
        "					 'position': NumberInt(111)"+
        "				},"+
        "				 {"+
        "					 'id': '112',"+
        "					 'name': 'TOLEDO',"+
        "					 'position': NumberInt(112)"+
        "				},"+
        "				 {"+
        "					 'id': '113',"+
        "					 'name': 'TURBO',"+
        "					 'position': NumberInt(113)"+
        "				},"+
        "				 {"+
        "					 'id': '114',"+
        "					 'name': 'URAMITA',"+
        "					 'position': NumberInt(114)"+
        "				},"+
        "				 {"+
        "					 'id': '115',"+
        "					 'name': 'URRAO',"+
        "					 'position': NumberInt(115)"+
        "				},"+
        "				 {"+
        "					 'id': '116',"+
        "					 'name': 'VALDIVIA',"+
        "					 'position': NumberInt(116)"+
        "				},"+
        "				 {"+
        "					 'id': '117',"+
        "					 'name': 'VALPARAÍSO',"+
        "					 'position': NumberInt(117)"+
        "				},"+
        "				 {"+
        "					 'id': '118',"+
        "					 'name': 'VEGACHÍ',"+
        "					 'position': NumberInt(118)"+
        "				},"+
        "				 {"+
        "					 'id': '119',"+
        "					 'name': 'VENECIA',"+
        "					 'position': NumberInt(119)"+
        "				},"+
        "				 {"+
        "					 'id': '120',"+
        "					 'name': 'VIGÍA DEL FUERTE',"+
        "					 'position': NumberInt(120)"+
        "				},"+
        "				 {"+
        "					 'id': '121',"+
        "					 'name': 'YALÍ',"+
        "					 'position': NumberInt(121)"+
        "				},"+
        "				 {"+
        "					 'id': '122',"+
        "					 'name': 'YARUMAL',"+
        "					 'position': NumberInt(122)"+
        "				},"+
        "				 {"+
        "					 'id': '123',"+
        "					 'name': 'YOLOMBÓ',"+
        "					 'position': NumberInt(123)"+
        "				},"+
        "				 {"+
        "					 'id': '124',"+
        "					 'name': 'YONDÓ',"+
        "					 'position': NumberInt(124)"+
        "				},"+
        "				 {"+
        "					 'id': '125',"+
        "					 'name': 'ZARAGOZA',"+
        "					 'position': NumberInt(125)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(340),"+
        "			 'name': 'Municipio (Arauca)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(120),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1048',"+
        "					 'name': 'ARAUCA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1049',"+
        "					 'name': 'ARAUQUITA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '1050',"+
        "					 'name': 'CRAVO NORTE',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '1051',"+
        "					 'name': 'FORTUL',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '1052',"+
        "					 'name': 'PUERTO RONDÓN',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '1053',"+
        "					 'name': 'SARAVENA',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '1054',"+
        "					 'name': 'TAME',"+
        "					 'position': NumberInt(7)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(341),"+
        "			 'name': 'Municipio (San Andrés, Providencia, Santa Catalina)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(121),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1087',"+
        "					 'name': 'SAN ANDRÉS',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1088',"+
        "					 'name': 'PROVIDENCIA',"+
        "					 'position': NumberInt(2)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(342),"+
        "			 'name': 'Municipio (Atlántico)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(122),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '126',"+
        "					 'name': 'BARRANQUILLA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '127',"+
        "					 'name': 'BARANOA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '128',"+
        "					 'name': 'CAMPO DE LA CRUZ',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '129',"+
        "					 'name': 'CANDELARIA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '130',"+
        "					 'name': 'GALAPA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '131',"+
        "					 'name': 'JUAN DE ACOSTA',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '132',"+
        "					 'name': 'LURUACO',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '133',"+
        "					 'name': 'MALAMBO',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '134',"+
        "					 'name': 'MANATÍ',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '135',"+
        "					 'name': 'PALMAR DE VARELA',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '136',"+
        "					 'name': 'PIOJÓ',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '137',"+
        "					 'name': 'POLONUEVO',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '138',"+
        "					 'name': 'PONEDERA',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '139',"+
        "					 'name': 'PUERTO COLOMBIA',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '140',"+
        "					 'name': 'REPELÓN',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '141',"+
        "					 'name': 'SABANAGRANDE',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '142',"+
        "					 'name': 'SABANALARGA',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '143',"+
        "					 'name': 'SANTA LUCÍA',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '144',"+
        "					 'name': 'SANTO TOMÁS',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '145',"+
        "					 'name': 'SOLEDAD',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '146',"+
        "					 'name': 'SUAN',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '147',"+
        "					 'name': 'TUBARÁ',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '148',"+
        "					 'name': 'USIACURÍ',"+
        "					 'position': NumberInt(23)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(343),"+
        "			 'name': 'Municipio (Bolívar)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(123),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '150',"+
        "					 'name': 'CARTAGENA DE INDIAS',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '151',"+
        "					 'name': 'ACHÍ',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '152',"+
        "					 'name': 'ALTOS DEL ROSARIO',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '153',"+
        "					 'name': 'ARENAL',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '154',"+
        "					 'name': 'ARJONA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '155',"+
        "					 'name': 'ARROYOHONDO',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '156',"+
        "					 'name': 'BARRANCO DE LOBA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '157',"+
        "					 'name': 'CALAMAR',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '158',"+
        "					 'name': 'CANTAGALLO',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '159',"+
        "					 'name': 'CICUCO',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '160',"+
        "					 'name': 'CÓRDOBA',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '161',"+
        "					 'name': 'CLEMENCIA',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '162',"+
        "					 'name': 'EL CARMEN DE BOLÍVAR',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '163',"+
        "					 'name': 'EL GUAMO',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '164',"+
        "					 'name': 'EL PEÑÓN',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '165',"+
        "					 'name': 'HATILLO DE LOBA',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '166',"+
        "					 'name': 'MAGANGUÉ',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '167',"+
        "					 'name': 'MAHATES',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '168',"+
        "					 'name': 'MARGARITA',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '169',"+
        "					 'name': 'MARÍA LA BAJA',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '170',"+
        "					 'name': 'MONTECRISTO',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '171',"+
        "					 'name': 'MOMPÓS',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '172',"+
        "					 'name': 'MORALES',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '173',"+
        "					 'name': 'NOROSÍ',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '174',"+
        "					 'name': 'PINILLOS',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '175',"+
        "					 'name': 'REGIDOR',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '176',"+
        "					 'name': 'RÍO VIEJO',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '177',"+
        "					 'name': 'SAN CRISTÓBAL',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '178',"+
        "					 'name': 'SAN ESTANISLAO',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '179',"+
        "					 'name': 'SAN FERNANDO',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '180',"+
        "					 'name': 'SAN JACINTO',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '181',"+
        "					 'name': 'SAN JACINTO DEL CAUCA',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '182',"+
        "					 'name': 'SAN JUAN NEPOMUCENO',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '183',"+
        "					 'name': 'SAN MARTÍN DE LOBA',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '184',"+
        "					 'name': 'SAN PABLO',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '185',"+
        "					 'name': 'SANTA CATALINA',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '186',"+
        "					 'name': 'SANTA ROSA',"+
        "					 'position': NumberInt(37)"+
        "				},"+
        "				 {"+
        "					 'id': '187',"+
        "					 'name': 'SANTA ROSA DEL SUR',"+
        "					 'position': NumberInt(38)"+
        "				},"+
        "				 {"+
        "					 'id': '188',"+
        "					 'name': 'SIMITÍ',"+
        "					 'position': NumberInt(39)"+
        "				},"+
        "				 {"+
        "					 'id': '189',"+
        "					 'name': 'SOPLAVIENTO',"+
        "					 'position': NumberInt(40)"+
        "				},"+
        "				 {"+
        "					 'id': '190',"+
        "					 'name': 'TALAIGUA NUEVO',"+
        "					 'position': NumberInt(41)"+
        "				},"+
        "				 {"+
        "					 'id': '191',"+
        "					 'name': 'TIQUISIO',"+
        "					 'position': NumberInt(42)"+
        "				},"+
        "				 {"+
        "					 'id': '192',"+
        "					 'name': 'TURBACO',"+
        "					 'position': NumberInt(43)"+
        "				},"+
        "				 {"+
        "					 'id': '193',"+
        "					 'name': 'TURBANÁ',"+
        "					 'position': NumberInt(44)"+
        "				},"+
        "				 {"+
        "					 'id': '194',"+
        "					 'name': 'VILLANUEVA',"+
        "					 'position': NumberInt(45)"+
        "				},"+
        "				 {"+
        "					 'id': '195',"+
        "					 'name': 'ZAMBRANO',"+
        "					 'position': NumberInt(46)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(344),"+
        "			 'name': 'Municipio (Boyaca)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(124),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '196',"+
        "					 'name': 'TUNJA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '197',"+
        "					 'name': 'ALMEIDA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '198',"+
        "					 'name': 'AQUITANIA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '199',"+
        "					 'name': 'ARCABUCO',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '200',"+
        "					 'name': 'BELÉN',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '201',"+
        "					 'name': 'BERBEO',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '202',"+
        "					 'name': 'BETÉITIVA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '203',"+
        "					 'name': 'BOAVITA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '204',"+
        "					 'name': 'BOYACÁ',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '205',"+
        "					 'name': 'BRICEÑO',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '206',"+
        "					 'name': 'BUENAVISTA',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '207',"+
        "					 'name': 'BUSBANZÁ',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '208',"+
        "					 'name': 'CALDAS',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '209',"+
        "					 'name': 'CAMPOHERMOSO',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '210',"+
        "					 'name': 'CERINZA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '211',"+
        "					 'name': 'CHINAVITA',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '212',"+
        "					 'name': 'CHIQUINQUIRÁ',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '213',"+
        "					 'name': 'CHISCAS',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '214',"+
        "					 'name': 'CHITA',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '215',"+
        "					 'name': 'CHITARAQUE',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '216',"+
        "					 'name': 'CHIVATÁ',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '217',"+
        "					 'name': 'CIÉNEGA',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '218',"+
        "					 'name': 'CÓMBITA',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '219',"+
        "					 'name': 'COPER',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '220',"+
        "					 'name': 'CORRALES',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '221',"+
        "					 'name': 'COVARACHÍA',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '222',"+
        "					 'name': 'CUBARÁ',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '223',"+
        "					 'name': 'CUCAITA',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '224',"+
        "					 'name': 'CUÍTIVA',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '225',"+
        "					 'name': 'CHÍQUIZA',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '226',"+
        "					 'name': 'CHIVOR',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '227',"+
        "					 'name': 'DUITAMA',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '228',"+
        "					 'name': 'EL COCUY',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '229',"+
        "					 'name': 'EL ESPINO',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '230',"+
        "					 'name': 'FIRAVITOBA',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '231',"+
        "					 'name': 'FLORESTA',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '232',"+
        "					 'name': 'GACHANTIVÁ',"+
        "					 'position': NumberInt(37)"+
        "				},"+
        "				 {"+
        "					 'id': '233',"+
        "					 'name': 'GÁMEZA',"+
        "					 'position': NumberInt(38)"+
        "				},"+
        "				 {"+
        "					 'id': '234',"+
        "					 'name': 'GARAGOA',"+
        "					 'position': NumberInt(39)"+
        "				},"+
        "				 {"+
        "					 'id': '235',"+
        "					 'name': 'GUACAMAYAS',"+
        "					 'position': NumberInt(40)"+
        "				},"+
        "				 {"+
        "					 'id': '236',"+
        "					 'name': 'GUATEQUE',"+
        "					 'position': NumberInt(41)"+
        "				},"+
        "				 {"+
        "					 'id': '237',"+
        "					 'name': 'GUAYATÁ',"+
        "					 'position': NumberInt(42)"+
        "				},"+
        "				 {"+
        "					 'id': '238',"+
        "					 'name': 'GÜICÁN',"+
        "					 'position': NumberInt(43)"+
        "				},"+
        "				 {"+
        "					 'id': '239',"+
        "					 'name': 'IZA',"+
        "					 'position': NumberInt(44)"+
        "				},"+
        "				 {"+
        "					 'id': '240',"+
        "					 'name': 'JENESANO',"+
        "					 'position': NumberInt(45)"+
        "				},"+
        "				 {"+
        "					 'id': '241',"+
        "					 'name': 'JERICÓ',"+
        "					 'position': NumberInt(46)"+
        "				},"+
        "				 {"+
        "					 'id': '242',"+
        "					 'name': 'LABRANZAGRANDE',"+
        "					 'position': NumberInt(47)"+
        "				},"+
        "				 {"+
        "					 'id': '243',"+
        "					 'name': 'LA CAPILLA',"+
        "					 'position': NumberInt(48)"+
        "				},"+
        "				 {"+
        "					 'id': '244',"+
        "					 'name': 'LA VICTORIA',"+
        "					 'position': NumberInt(49)"+
        "				},"+
        "				 {"+
        "					 'id': '245',"+
        "					 'name': 'LA UVITA',"+
        "					 'position': NumberInt(50)"+
        "				},"+
        "				 {"+
        "					 'id': '246',"+
        "					 'name': 'VILLA DE LEYVA',"+
        "					 'position': NumberInt(51)"+
        "				},"+
        "				 {"+
        "					 'id': '247',"+
        "					 'name': 'MACANAL',"+
        "					 'position': NumberInt(52)"+
        "				},"+
        "				 {"+
        "					 'id': '248',"+
        "					 'name': 'MARIPÍ',"+
        "					 'position': NumberInt(53)"+
        "				},"+
        "				 {"+
        "					 'id': '249',"+
        "					 'name': 'MIRAFLORES',"+
        "					 'position': NumberInt(54)"+
        "				},"+
        "				 {"+
        "					 'id': '250',"+
        "					 'name': 'MONGUA',"+
        "					 'position': NumberInt(55)"+
        "				},"+
        "				 {"+
        "					 'id': '251',"+
        "					 'name': 'MONGUÍ',"+
        "					 'position': NumberInt(56)"+
        "				},"+
        "				 {"+
        "					 'id': '252',"+
        "					 'name': 'MONIQUIRÁ',"+
        "					 'position': NumberInt(57)"+
        "				},"+
        "				 {"+
        "					 'id': '253',"+
        "					 'name': 'MOTAVITA',"+
        "					 'position': NumberInt(58)"+
        "				},"+
        "				 {"+
        "					 'id': '254',"+
        "					 'name': 'MUZO',"+
        "					 'position': NumberInt(59)"+
        "				},"+
        "				 {"+
        "					 'id': '255',"+
        "					 'name': 'NOBSA',"+
        "					 'position': NumberInt(60)"+
        "				},"+
        "				 {"+
        "					 'id': '256',"+
        "					 'name': 'NUEVO COLÓN',"+
        "					 'position': NumberInt(61)"+
        "				},"+
        "				 {"+
        "					 'id': '257',"+
        "					 'name': 'OICATÁ',"+
        "					 'position': NumberInt(62)"+
        "				},"+
        "				 {"+
        "					 'id': '258',"+
        "					 'name': 'OTANCHE',"+
        "					 'position': NumberInt(63)"+
        "				},"+
        "				 {"+
        "					 'id': '259',"+
        "					 'name': 'PACHAVITA',"+
        "					 'position': NumberInt(64)"+
        "				},"+
        "				 {"+
        "					 'id': '260',"+
        "					 'name': 'PÁEZ',"+
        "					 'position': NumberInt(65)"+
        "				},"+
        "				 {"+
        "					 'id': '261',"+
        "					 'name': 'PAIPA',"+
        "					 'position': NumberInt(66)"+
        "				},"+
        "				 {"+
        "					 'id': '262',"+
        "					 'name': 'PAJARITO',"+
        "					 'position': NumberInt(67)"+
        "				},"+
        "				 {"+
        "					 'id': '263',"+
        "					 'name': 'PANQUEBA',"+
        "					 'position': NumberInt(68)"+
        "				},"+
        "				 {"+
        "					 'id': '264',"+
        "					 'name': 'PAUNA',"+
        "					 'position': NumberInt(69)"+
        "				},"+
        "				 {"+
        "					 'id': '265',"+
        "					 'name': 'PAYA',"+
        "					 'position': NumberInt(70)"+
        "				},"+
        "				 {"+
        "					 'id': '266',"+
        "					 'name': 'PAZ DE RÍO',"+
        "					 'position': NumberInt(71)"+
        "				},"+
        "				 {"+
        "					 'id': '267',"+
        "					 'name': 'PESCA',"+
        "					 'position': NumberInt(72)"+
        "				},"+
        "				 {"+
        "					 'id': '268',"+
        "					 'name': 'PISBA',"+
        "					 'position': NumberInt(73)"+
        "				},"+
        "				 {"+
        "					 'id': '269',"+
        "					 'name': 'PUERTO BOYACÁ',"+
        "					 'position': NumberInt(74)"+
        "				},"+
        "				 {"+
        "					 'id': '270',"+
        "					 'name': 'QUÍPAMA',"+
        "					 'position': NumberInt(75)"+
        "				},"+
        "				 {"+
        "					 'id': '271',"+
        "					 'name': 'RAMIRIQUÍ',"+
        "					 'position': NumberInt(76)"+
        "				},"+
        "				 {"+
        "					 'id': '272',"+
        "					 'name': 'RÁQUIRA',"+
        "					 'position': NumberInt(77)"+
        "				},"+
        "				 {"+
        "					 'id': '273',"+
        "					 'name': 'RONDÓN',"+
        "					 'position': NumberInt(78)"+
        "				},"+
        "				 {"+
        "					 'id': '274',"+
        "					 'name': 'SABOYÁ',"+
        "					 'position': NumberInt(79)"+
        "				},"+
        "				 {"+
        "					 'id': '275',"+
        "					 'name': 'SÁCHICA',"+
        "					 'position': NumberInt(80)"+
        "				},"+
        "				 {"+
        "					 'id': '276',"+
        "					 'name': 'SAMACÁ',"+
        "					 'position': NumberInt(81)"+
        "				},"+
        "				 {"+
        "					 'id': '277',"+
        "					 'name': 'SAN EDUARDO',"+
        "					 'position': NumberInt(82)"+
        "				},"+
        "				 {"+
        "					 'id': '278',"+
        "					 'name': 'SAN JOSÉ DE PARE',"+
        "					 'position': NumberInt(83)"+
        "				},"+
        "				 {"+
        "					 'id': '279',"+
        "					 'name': 'SAN LUIS DE GACENO',"+
        "					 'position': NumberInt(84)"+
        "				},"+
        "				 {"+
        "					 'id': '280',"+
        "					 'name': 'SAN MATEO',"+
        "					 'position': NumberInt(85)"+
        "				},"+
        "				 {"+
        "					 'id': '281',"+
        "					 'name': 'SAN MIGUEL DE SEMA',"+
        "					 'position': NumberInt(86)"+
        "				},"+
        "				 {"+
        "					 'id': '282',"+
        "					 'name': 'SAN PABLO DE BORBUR',"+
        "					 'position': NumberInt(87)"+
        "				},"+
        "				 {"+
        "					 'id': '283',"+
        "					 'name': 'SANTANA',"+
        "					 'position': NumberInt(88)"+
        "				},"+
        "				 {"+
        "					 'id': '284',"+
        "					 'name': 'SANTA MARÍA',"+
        "					 'position': NumberInt(89)"+
        "				},"+
        "				 {"+
        "					 'id': '285',"+
        "					 'name': 'SANTA ROSA DE VITERBO',"+
        "					 'position': NumberInt(90)"+
        "				},"+
        "				 {"+
        "					 'id': '286',"+
        "					 'name': 'SANTA SOFÍA',"+
        "					 'position': NumberInt(91)"+
        "				},"+
        "				 {"+
        "					 'id': '287',"+
        "					 'name': 'SATIVANORTE',"+
        "					 'position': NumberInt(92)"+
        "				},"+
        "				 {"+
        "					 'id': '288',"+
        "					 'name': 'SATIVASUR',"+
        "					 'position': NumberInt(93)"+
        "				},"+
        "				 {"+
        "					 'id': '289',"+
        "					 'name': 'SIACHOQUE',"+
        "					 'position': NumberInt(94)"+
        "				},"+
        "				 {"+
        "					 'id': '290',"+
        "					 'name': 'SOATÁ',"+
        "					 'position': NumberInt(95)"+
        "				},"+
        "				 {"+
        "					 'id': '291',"+
        "					 'name': 'SOCOTÁ',"+
        "					 'position': NumberInt(96)"+
        "				},"+
        "				 {"+
        "					 'id': '292',"+
        "					 'name': 'SOCHA',"+
        "					 'position': NumberInt(97)"+
        "				},"+
        "				 {"+
        "					 'id': '293',"+
        "					 'name': 'SOGAMOSO',"+
        "					 'position': NumberInt(98)"+
        "				},"+
        "				 {"+
        "					 'id': '294',"+
        "					 'name': 'SOMONDOCO',"+
        "					 'position': NumberInt(99)"+
        "				},"+
        "				 {"+
        "					 'id': '295',"+
        "					 'name': 'SORA',"+
        "					 'position': NumberInt(100)"+
        "				},"+
        "				 {"+
        "					 'id': '296',"+
        "					 'name': 'SOTAQUIRÁ',"+
        "					 'position': NumberInt(101)"+
        "				},"+
        "				 {"+
        "					 'id': '297',"+
        "					 'name': 'SORACÁ',"+
        "					 'position': NumberInt(102)"+
        "				},"+
        "				 {"+
        "					 'id': '298',"+
        "					 'name': 'SUSACÓN',"+
        "					 'position': NumberInt(103)"+
        "				},"+
        "				 {"+
        "					 'id': '299',"+
        "					 'name': 'SUTAMARCHÁN',"+
        "					 'position': NumberInt(104)"+
        "				},"+
        "				 {"+
        "					 'id': '300',"+
        "					 'name': 'SUTATENZA',"+
        "					 'position': NumberInt(105)"+
        "				},"+
        "				 {"+
        "					 'id': '301',"+
        "					 'name': 'TASCO',"+
        "					 'position': NumberInt(106)"+
        "				},"+
        "				 {"+
        "					 'id': '302',"+
        "					 'name': 'TENZA',"+
        "					 'position': NumberInt(107)"+
        "				},"+
        "				 {"+
        "					 'id': '303',"+
        "					 'name': 'TIBANÁ',"+
        "					 'position': NumberInt(108)"+
        "				},"+
        "				 {"+
        "					 'id': '304',"+
        "					 'name': 'TIBASOSA',"+
        "					 'position': NumberInt(109)"+
        "				},"+
        "				 {"+
        "					 'id': '305',"+
        "					 'name': 'TINJACÁ',"+
        "					 'position': NumberInt(110)"+
        "				},"+
        "				 {"+
        "					 'id': '306',"+
        "					 'name': 'TIPACOQUE',"+
        "					 'position': NumberInt(111)"+
        "				},"+
        "				 {"+
        "					 'id': '307',"+
        "					 'name': 'TOCA',"+
        "					 'position': NumberInt(112)"+
        "				},"+
        "				 {"+
        "					 'id': '308',"+
        "					 'name': 'TOGÜÍ',"+
        "					 'position': NumberInt(113)"+
        "				},"+
        "				 {"+
        "					 'id': '309',"+
        "					 'name': 'TÓPAGA',"+
        "					 'position': NumberInt(114)"+
        "				},"+
        "				 {"+
        "					 'id': '310',"+
        "					 'name': 'TOTA',"+
        "					 'position': NumberInt(115)"+
        "				},"+
        "				 {"+
        "					 'id': '311',"+
        "					 'name': 'TUNUNGUÁ',"+
        "					 'position': NumberInt(116)"+
        "				},"+
        "				 {"+
        "					 'id': '312',"+
        "					 'name': 'TURMEQUÉ',"+
        "					 'position': NumberInt(117)"+
        "				},"+
        "				 {"+
        "					 'id': '313',"+
        "					 'name': 'TUTA',"+
        "					 'position': NumberInt(118)"+
        "				},"+
        "				 {"+
        "					 'id': '314',"+
        "					 'name': 'TUTAZÁ',"+
        "					 'position': NumberInt(119)"+
        "				},"+
        "				 {"+
        "					 'id': '315',"+
        "					 'name': 'ÚMBITA',"+
        "					 'position': NumberInt(120)"+
        "				},"+
        "				 {"+
        "					 'id': '316',"+
        "					 'name': 'VENTAQUEMADA',"+
        "					 'position': NumberInt(121)"+
        "				},"+
        "				 {"+
        "					 'id': '317',"+
        "					 'name': 'VIRACACHÁ',"+
        "					 'position': NumberInt(122)"+
        "				},"+
        "				 {"+
        "					 'id': '318',"+
        "					 'name': 'ZETAQUIRA',"+
        "					 'position': NumberInt(123)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(345),"+
        "			 'name': 'Municipio (Caldas)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(125),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '319',"+
        "					 'name': 'MANIZALES',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '320',"+
        "					 'name': 'AGUADAS',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '321',"+
        "					 'name': 'ANSERMA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '322',"+
        "					 'name': 'ARANZAZU',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '323',"+
        "					 'name': 'BELALCÁZAR',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '324',"+
        "					 'name': 'CHINCHINÁ',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '325',"+
        "					 'name': 'FILADELFIA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '326',"+
        "					 'name': 'LA DORADA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '327',"+
        "					 'name': 'LA MERCED',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '328',"+
        "					 'name': 'MANZANARES',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '329',"+
        "					 'name': 'MARMATO',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '330',"+
        "					 'name': 'MARQUETALIA',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '331',"+
        "					 'name': 'MARULANDA',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '332',"+
        "					 'name': 'NEIRA',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '333',"+
        "					 'name': 'NORCASIA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '334',"+
        "					 'name': 'PÁCORA',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '335',"+
        "					 'name': 'PALESTINA',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '336',"+
        "					 'name': 'PENSILVANIA',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '337',"+
        "					 'name': 'RIOSUCIO',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '338',"+
        "					 'name': 'RISARALDA',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '339',"+
        "					 'name': 'SALAMINA',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '340',"+
        "					 'name': 'SAMANÁ',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '341',"+
        "					 'name': 'SAN JOSÉ',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '342',"+
        "					 'name': 'SUPÍA',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '343',"+
        "					 'name': 'VICTORIA',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '344',"+
        "					 'name': 'VILLAMARÍA',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '345',"+
        "					 'name': 'VITERBO',"+
        "					 'position': NumberInt(27)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(346),"+
        "			 'name': 'Municipio (Caqueta)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(126),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '346',"+
        "					 'name': 'FLORENCIA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '347',"+
        "					 'name': 'ALBANIA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '348',"+
        "					 'name': 'BELÉN DE LOS ANDAQUÍES',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '349',"+
        "					 'name': 'CARTAGENA DEL CHAIRÁ',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '350',"+
        "					 'name': 'CURILLO',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '351',"+
        "					 'name': 'EL DONCELLO',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '352',"+
        "					 'name': 'EL PAUJÍL',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '353',"+
        "					 'name': 'LA MONTAÑITA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '354',"+
        "					 'name': 'MILÁN',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '355',"+
        "					 'name': 'MORELIA',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '356',"+
        "					 'name': 'PUERTO RICO',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '357',"+
        "					 'name': 'SAN JOSÉ DEL FRAGUA',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '358',"+
        "					 'name': 'SAN VICENTE DEL CAGUÁN',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '359',"+
        "					 'name': 'SOLANO',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '360',"+
        "					 'name': 'SOLITA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '361',"+
        "					 'name': 'VALPARAÍSO',"+
        "					 'position': NumberInt(16)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(347),"+
        "			 'name': 'Municipio (Casanare)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(127),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1055',"+
        "					 'name': 'YOPAL',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1056',"+
        "					 'name': 'AGUAZUL',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '1057',"+
        "					 'name': 'CHÁMEZA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '1058',"+
        "					 'name': 'HATO COROZAL',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '1059',"+
        "					 'name': 'LA SALINA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '1060',"+
        "					 'name': 'MANÍ',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '1061',"+
        "					 'name': 'MONTERREY',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '1062',"+
        "					 'name': 'NUNCHÍA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '1063',"+
        "					 'name': 'OROCUÉ',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '1064',"+
        "					 'name': 'PAZ DE ARIPORO',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '1065',"+
        "					 'name': 'PORE',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '1066',"+
        "					 'name': 'RECETOR',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '1067',"+
        "					 'name': 'SABANALARGA',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '1068',"+
        "					 'name': 'SÁCAMA',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '1069',"+
        "					 'name': 'SAN LUIS DE PALENQUE',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '1070',"+
        "					 'name': 'TÁMARA',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '1071',"+
        "					 'name': 'TAURAMENA',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '1072',"+
        "					 'name': 'TRINIDAD',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '1073',"+
        "					 'name': 'VILLANUEVA',"+
        "					 'position': NumberInt(19)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(348),"+
        "			 'name': 'Municipio (Cauca)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(128),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '362',"+
        "					 'name': 'POPAYÁN',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '363',"+
        "					 'name': 'ALMAGUER',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '364',"+
        "					 'name': 'ARGELIA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '365',"+
        "					 'name': 'BALBOA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '366',"+
        "					 'name': 'BOLÍVAR',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '367',"+
        "					 'name': 'BUENOS AIRES',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '368',"+
        "					 'name': 'CAJIBÍO',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '369',"+
        "					 'name': 'CALDONO',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '370',"+
        "					 'name': 'CALOTO',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '371',"+
        "					 'name': 'CORINTO',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '372',"+
        "					 'name': 'EL TAMBO',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '373',"+
        "					 'name': 'FLORENCIA',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '374',"+
        "					 'name': 'GUACHENÉ',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '375',"+
        "					 'name': 'GUAPÍ',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '376',"+
        "					 'name': 'INZÁ',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '377',"+
        "					 'name': 'JAMBALÓ',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '378',"+
        "					 'name': 'LA SIERRA',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '379',"+
        "					 'name': 'LA VEGA',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '380',"+
        "					 'name': 'LÓPEZ DE MICAY',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '381',"+
        "					 'name': 'MERCADERES',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '382',"+
        "					 'name': 'MIRANDA',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '383',"+
        "					 'name': 'MORALES',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '384',"+
        "					 'name': 'PADILLA',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '385',"+
        "					 'name': 'PÁEZ',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '386',"+
        "					 'name': 'PATÍA',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '387',"+
        "					 'name': 'PIAMONTE',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '388',"+
        "					 'name': 'PIENDAMÓ',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '389',"+
        "					 'name': 'PUERTO TEJADA',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '390',"+
        "					 'name': 'PURACÉ',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '391',"+
        "					 'name': 'ROSAS',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '392',"+
        "					 'name': 'SAN SEBASTIÁN',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '393',"+
        "					 'name': 'SANTANDER DE QUILICHAO',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '394',"+
        "					 'name': 'SANTA ROSA',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '395',"+
        "					 'name': 'SILVIA',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '396',"+
        "					 'name': 'SOTARA',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '397',"+
        "					 'name': 'SUÁREZ',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '398',"+
        "					 'name': 'SUCRE',"+
        "					 'position': NumberInt(37)"+
        "				},"+
        "				 {"+
        "					 'id': '399',"+
        "					 'name': 'TIMBÍO',"+
        "					 'position': NumberInt(38)"+
        "				},"+
        "				 {"+
        "					 'id': '400',"+
        "					 'name': 'TIMBIQUÍ',"+
        "					 'position': NumberInt(39)"+
        "				},"+
        "				 {"+
        "					 'id': '401',"+
        "					 'name': 'TORIBÍO',"+
        "					 'position': NumberInt(40)"+
        "				},"+
        "				 {"+
        "					 'id': '402',"+
        "					 'name': 'TOTORÓ',"+
        "					 'position': NumberInt(41)"+
        "				},"+
        "				 {"+
        "					 'id': '403',"+
        "					 'name': 'VILLA RICA',"+
        "					 'position': NumberInt(42)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(349),"+
        "			 'name': 'Municipio (Cesar)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(129),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '404',"+
        "					 'name': 'VALLEDUPAR',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '405',"+
        "					 'name': 'AGUACHICA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '406',"+
        "					 'name': 'AGUSTÍN CODAZZI',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '407',"+
        "					 'name': 'ASTREA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '408',"+
        "					 'name': 'BECERRIL',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '409',"+
        "					 'name': 'BOSCONIA',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '410',"+
        "					 'name': 'CHIMICHAGUA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '411',"+
        "					 'name': 'CHIRIGUANÁ',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '412',"+
        "					 'name': 'CURUMANÍ',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '413',"+
        "					 'name': 'EL COPEY',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '414',"+
        "					 'name': 'EL PASO',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '415',"+
        "					 'name': 'GAMARRA',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '416',"+
        "					 'name': 'GONZÁLEZ',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '417',"+
        "					 'name': 'LA GLORIA',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '418',"+
        "					 'name': 'LA JAGUA DE IBIRICO',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '419',"+
        "					 'name': 'MANAURE BALCÓN DEL CESAR',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '420',"+
        "					 'name': 'PAILITAS',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '421',"+
        "					 'name': 'PELAYA',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '422',"+
        "					 'name': 'PUEBLO BELLO',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '423',"+
        "					 'name': 'RÍO DE ORO',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '424',"+
        "					 'name': 'LA PAZ',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '425',"+
        "					 'name': 'SAN ALBERTO',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '426',"+
        "					 'name': 'SAN DIEGO',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '427',"+
        "					 'name': 'SAN MARTÍN',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '428',"+
        "					 'name': 'TAMALAMEQUE',"+
        "					 'position': NumberInt(25)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(350),"+
        "			 'name': 'Municipio (Choco)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(130),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '575',"+
        "					 'name': 'QUIBDÓ',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '576',"+
        "					 'name': 'ACANDÍ',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '577',"+
        "					 'name': 'ALTO BAUDÓ',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '578',"+
        "					 'name': 'ATRATO',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '579',"+
        "					 'name': 'BAGADÓ',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '580',"+
        "					 'name': 'BAHÍA SOLANO',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '581',"+
        "					 'name': 'BAJO BAUDÓ',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '582',"+
        "					 'name': 'BOJAYÁ',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '583',"+
        "					 'name': 'EL CANTÓN DEL SAN PABLO',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '584',"+
        "					 'name': 'CARMEN DEL DARIÉN',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '585',"+
        "					 'name': 'CÉRTEGUI',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '586',"+
        "					 'name': 'CONDOTO',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '587',"+
        "					 'name': 'EL CARMEN DE ATRATO',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '588',"+
        "					 'name': 'EL LITORAL DEL SAN JUAN',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '589',"+
        "					 'name': 'ISTMINA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '590',"+
        "					 'name': 'JURADÓ',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '591',"+
        "					 'name': 'LLORÓ',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '592',"+
        "					 'name': 'MEDIO ATRATO',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '593',"+
        "					 'name': 'MEDIO BAUDÓ',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '594',"+
        "					 'name': 'MEDIO SAN JUAN',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '595',"+
        "					 'name': 'NÓVITA',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '596',"+
        "					 'name': 'NUQUÍ',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '597',"+
        "					 'name': 'RÍO IRÓ',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '598',"+
        "					 'name': 'RÍO QUITO',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '599',"+
        "					 'name': 'RIOSUCIO',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '600',"+
        "					 'name': 'SAN JOSÉ DEL PALMAR',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '601',"+
        "					 'name': 'SIPÍ',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '602',"+
        "					 'name': 'TADÓ',"+
        "					 'position': NumberInt(28)";
        String json2 = "				},"+
        "				 {"+
        "					 'id': '603',"+
        "					 'name': 'UNGUÍA',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '604',"+
        "					 'name': 'UNIÓN PANAMERICANA',"+
        "					 'position': NumberInt(30)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(351),"+
        "			 'name': 'Municipio (Córdoba)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(131),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '429',"+
        "					 'name': 'MONTERÍA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '430',"+
        "					 'name': 'AYAPEL',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '431',"+
        "					 'name': 'BUENAVISTA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '432',"+
        "					 'name': 'CANALETE',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '433',"+
        "					 'name': 'CERETÉ',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '434',"+
        "					 'name': 'CHIMÁ',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '435',"+
        "					 'name': 'CHINÚ',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '436',"+
        "					 'name': 'CIÉNAGA DE ORO',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '437',"+
        "					 'name': 'COTORRA',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '438',"+
        "					 'name': 'LA APARTADA',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '439',"+
        "					 'name': 'LORICA',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '440',"+
        "					 'name': 'LOS CÓRDOBAS',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '441',"+
        "					 'name': 'MOMIL',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '442',"+
        "					 'name': 'MONTELÍBANO',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '443',"+
        "					 'name': 'MOÑITOS',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '444',"+
        "					 'name': 'PLANETA RICA',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '445',"+
        "					 'name': 'PUEBLO NUEVO',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '446',"+
        "					 'name': 'PUERTO ESCONDIDO',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '447',"+
        "					 'name': 'PUERTO LIBERTADOR',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '448',"+
        "					 'name': 'PURÍSIMA DE LA CONCEPCIÓN',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '449',"+
        "					 'name': 'SAHAGÚN',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '450',"+
        "					 'name': 'SAN ANDRÉS DE SOTAVENTO',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '451',"+
        "					 'name': 'SAN ANTERO',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '452',"+
        "					 'name': 'SAN BERNARDO DEL VIENTO',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '453',"+
        "					 'name': 'SAN CARLOS',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '454',"+
        "					 'name': 'SAN JOSÉ DE URÉ',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '455',"+
        "					 'name': 'SAN PELAYO',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '456',"+
        "					 'name': 'TIERRALTA',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '457',"+
        "					 'name': 'TUCHÍN',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '458',"+
        "					 'name': 'VALENCIA',"+
        "					 'position': NumberInt(30)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(352),"+
        "			 'name': 'Municipio (Cundinamarca)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(132),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '459',"+
        "					 'name': 'AGUA DE DIOS',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '460',"+
        "					 'name': 'ALBÁN',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '461',"+
        "					 'name': 'ANAPOIMA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '462',"+
        "					 'name': 'ANOLAIMA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '463',"+
        "					 'name': 'ARBELÁEZ',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '464',"+
        "					 'name': 'BELTRÁN',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '465',"+
        "					 'name': 'BITUIMA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '466',"+
        "					 'name': 'BOJACÁ',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '467',"+
        "					 'name': 'CABRERA',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '468',"+
        "					 'name': 'CACHIPAY',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '469',"+
        "					 'name': 'CAJICÁ',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '470',"+
        "					 'name': 'CAPARRAPÍ',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '471',"+
        "					 'name': 'CÁQUEZA',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '472',"+
        "					 'name': 'CARMEN DE CARUPA',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '473',"+
        "					 'name': 'CHAGUANÍ',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '474',"+
        "					 'name': 'CHÍA',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '475',"+
        "					 'name': 'CHIPAQUE',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '476',"+
        "					 'name': 'CHOACHÍ',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '477',"+
        "					 'name': 'CHOCONTÁ',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '478',"+
        "					 'name': 'COGUA',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '479',"+
        "					 'name': 'COTA',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '480',"+
        "					 'name': 'CUCUNUBÁ',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '481',"+
        "					 'name': 'EL COLEGIO',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '482',"+
        "					 'name': 'EL PEÑÓN',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '483',"+
        "					 'name': 'EL ROSAL',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '484',"+
        "					 'name': 'FACATATIVÁ',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '485',"+
        "					 'name': 'FÓMEQUE',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '486',"+
        "					 'name': 'FOSCA',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '487',"+
        "					 'name': 'FUNZA',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '488',"+
        "					 'name': 'FÚQUENE',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '489',"+
        "					 'name': 'FUSAGASUGÁ',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '490',"+
        "					 'name': 'GACHALÁ',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '491',"+
        "					 'name': 'GACHANCIPÁ',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '492',"+
        "					 'name': 'GACHETÁ',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '493',"+
        "					 'name': 'GAMA',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '494',"+
        "					 'name': 'GIRARDOT',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '495',"+
        "					 'name': 'GRANADA',"+
        "					 'position': NumberInt(37)"+
        "				},"+
        "				 {"+
        "					 'id': '496',"+
        "					 'name': 'GUACHETÁ',"+
        "					 'position': NumberInt(38)"+
        "				},"+
        "				 {"+
        "					 'id': '497',"+
        "					 'name': 'GUADUAS',"+
        "					 'position': NumberInt(39)"+
        "				},"+
        "				 {"+
        "					 'id': '498',"+
        "					 'name': 'GUASCA',"+
        "					 'position': NumberInt(40)"+
        "				},"+
        "				 {"+
        "					 'id': '499',"+
        "					 'name': 'GUATAQUÍ',"+
        "					 'position': NumberInt(41)"+
        "				},"+
        "				 {"+
        "					 'id': '500',"+
        "					 'name': 'GUATAVITA',"+
        "					 'position': NumberInt(42)"+
        "				},"+
        "				 {"+
        "					 'id': '501',"+
        "					 'name': 'GUAYABAL DE SÍQUIMA',"+
        "					 'position': NumberInt(43)"+
        "				},"+
        "				 {"+
        "					 'id': '502',"+
        "					 'name': 'GUAYABETAL',"+
        "					 'position': NumberInt(44)"+
        "				},"+
        "				 {"+
        "					 'id': '503',"+
        "					 'name': 'GUTIÉRREZ',"+
        "					 'position': NumberInt(45)"+
        "				},"+
        "				 {"+
        "					 'id': '504',"+
        "					 'name': 'JERUSALÉN',"+
        "					 'position': NumberInt(46)"+
        "				},"+
        "				 {"+
        "					 'id': '505',"+
        "					 'name': 'JUNÍN',"+
        "					 'position': NumberInt(47)"+
        "				},"+
        "				 {"+
        "					 'id': '506',"+
        "					 'name': 'LA CALERA',"+
        "					 'position': NumberInt(48)"+
        "				},"+
        "				 {"+
        "					 'id': '507',"+
        "					 'name': 'LA MESA',"+
        "					 'position': NumberInt(49)"+
        "				},"+
        "				 {"+
        "					 'id': '508',"+
        "					 'name': 'LA PALMA',"+
        "					 'position': NumberInt(50)"+
        "				},"+
        "				 {"+
        "					 'id': '509',"+
        "					 'name': 'LA PEÑA',"+
        "					 'position': NumberInt(51)"+
        "				},"+
        "				 {"+
        "					 'id': '510',"+
        "					 'name': 'LA VEGA',"+
        "					 'position': NumberInt(52)"+
        "				},"+
        "				 {"+
        "					 'id': '511',"+
        "					 'name': 'LENGUAZAQUE',"+
        "					 'position': NumberInt(53)"+
        "				},"+
        "				 {"+
        "					 'id': '512',"+
        "					 'name': 'MACHETÁ',"+
        "					 'position': NumberInt(54)"+
        "				},"+
        "				 {"+
        "					 'id': '513',"+
        "					 'name': 'MADRID',"+
        "					 'position': NumberInt(55)"+
        "				},"+
        "				 {"+
        "					 'id': '514',"+
        "					 'name': 'MANTA',"+
        "					 'position': NumberInt(56)"+
        "				},"+
        "				 {"+
        "					 'id': '515',"+
        "					 'name': 'MEDINA',"+
        "					 'position': NumberInt(57)"+
        "				},"+
        "				 {"+
        "					 'id': '516',"+
        "					 'name': 'MOSQUERA',"+
        "					 'position': NumberInt(58)"+
        "				},"+
        "				 {"+
        "					 'id': '517',"+
        "					 'name': 'NARIÑO',"+
        "					 'position': NumberInt(59)"+
        "				},"+
        "				 {"+
        "					 'id': '518',"+
        "					 'name': 'NEMOCÓN',"+
        "					 'position': NumberInt(60)"+
        "				},"+
        "				 {"+
        "					 'id': '519',"+
        "					 'name': 'NILO',"+
        "					 'position': NumberInt(61)"+
        "				},"+
        "				 {"+
        "					 'id': '520',"+
        "					 'name': 'NIMAIMA',"+
        "					 'position': NumberInt(62)"+
        "				},"+
        "				 {"+
        "					 'id': '521',"+
        "					 'name': 'NOCAIMA',"+
        "					 'position': NumberInt(63)"+
        "				},"+
        "				 {"+
        "					 'id': '522',"+
        "					 'name': 'VENECIA',"+
        "					 'position': NumberInt(64)"+
        "				},"+
        "				 {"+
        "					 'id': '523',"+
        "					 'name': 'PACHO',"+
        "					 'position': NumberInt(65)"+
        "				},"+
        "				 {"+
        "					 'id': '524',"+
        "					 'name': 'PAIME',"+
        "					 'position': NumberInt(66)"+
        "				},"+
        "				 {"+
        "					 'id': '525',"+
        "					 'name': 'PANDI',"+
        "					 'position': NumberInt(67)"+
        "				},"+
        "				 {"+
        "					 'id': '526',"+
        "					 'name': 'PARATEBUENO',"+
        "					 'position': NumberInt(68)"+
        "				},"+
        "				 {"+
        "					 'id': '527',"+
        "					 'name': 'PASCA',"+
        "					 'position': NumberInt(69)"+
        "				},"+
        "				 {"+
        "					 'id': '528',"+
        "					 'name': 'PUERTO SALGAR',"+
        "					 'position': NumberInt(70)"+
        "				},"+
        "				 {"+
        "					 'id': '529',"+
        "					 'name': 'PULÍ',"+
        "					 'position': NumberInt(71)"+
        "				},"+
        "				 {"+
        "					 'id': '530',"+
        "					 'name': 'QUEBRADANEGRA',"+
        "					 'position': NumberInt(72)"+
        "				},"+
        "				 {"+
        "					 'id': '531',"+
        "					 'name': 'QUETAME',"+
        "					 'position': NumberInt(73)"+
        "				},"+
        "				 {"+
        "					 'id': '532',"+
        "					 'name': 'QUIPILE',"+
        "					 'position': NumberInt(74)"+
        "				},"+
        "				 {"+
        "					 'id': '533',"+
        "					 'name': 'APULO',"+
        "					 'position': NumberInt(75)"+
        "				},"+
        "				 {"+
        "					 'id': '534',"+
        "					 'name': 'RICAURTE',"+
        "					 'position': NumberInt(76)"+
        "				},"+
        "				 {"+
        "					 'id': '535',"+
        "					 'name': 'SAN ANTONIO DEL TEQUENDAMA',"+
        "					 'position': NumberInt(77)"+
        "				},"+
        "				 {"+
        "					 'id': '536',"+
        "					 'name': 'SAN BERNARDO',"+
        "					 'position': NumberInt(78)"+
        "				},"+
        "				 {"+
        "					 'id': '537',"+
        "					 'name': 'SAN CAYETANO',"+
        "					 'position': NumberInt(79)"+
        "				},"+
        "				 {"+
        "					 'id': '538',"+
        "					 'name': 'SAN FRANCISCO',"+
        "					 'position': NumberInt(80)"+
        "				},"+
        "				 {"+
        "					 'id': '539',"+
        "					 'name': 'SAN JUAN DE RIOSECO',"+
        "					 'position': NumberInt(81)"+
        "				},"+
        "				 {"+
        "					 'id': '540',"+
        "					 'name': 'SASAIMA',"+
        "					 'position': NumberInt(82)"+
        "				},"+
        "				 {"+
        "					 'id': '541',"+
        "					 'name': 'SESQUILÉ',"+
        "					 'position': NumberInt(83)"+
        "				},"+
        "				 {"+
        "					 'id': '542',"+
        "					 'name': 'SIBATÉ',"+
        "					 'position': NumberInt(84)"+
        "				},"+
        "				 {"+
        "					 'id': '543',"+
        "					 'name': 'SILVANIA',"+
        "					 'position': NumberInt(85)"+
        "				},"+
        "				 {"+
        "					 'id': '544',"+
        "					 'name': 'SIMIJACA',"+
        "					 'position': NumberInt(86)"+
        "				},"+
        "				 {"+
        "					 'id': '545',"+
        "					 'name': 'SOACHA',"+
        "					 'position': NumberInt(87)"+
        "				},"+
        "				 {"+
        "					 'id': '546',"+
        "					 'name': 'SOPÓ',"+
        "					 'position': NumberInt(88)"+
        "				},"+
        "				 {"+
        "					 'id': '547',"+
        "					 'name': 'SUBACHOQUE',"+
        "					 'position': NumberInt(89)"+
        "				},"+
        "				 {"+
        "					 'id': '548',"+
        "					 'name': 'SUESCA',"+
        "					 'position': NumberInt(90)"+
        "				},"+
        "				 {"+
        "					 'id': '549',"+
        "					 'name': 'SUPATÁ',"+
        "					 'position': NumberInt(91)"+
        "				},"+
        "				 {"+
        "					 'id': '550',"+
        "					 'name': 'SUSA',"+
        "					 'position': NumberInt(92)"+
        "				},"+
        "				 {"+
        "					 'id': '551',"+
        "					 'name': 'SUTATAUSA',"+
        "					 'position': NumberInt(93)"+
        "				},"+
        "				 {"+
        "					 'id': '552',"+
        "					 'name': 'TABIO',"+
        "					 'position': NumberInt(94)"+
        "				},"+
        "				 {"+
        "					 'id': '553',"+
        "					 'name': 'TAUSA',"+
        "					 'position': NumberInt(95)"+
        "				},"+
        "				 {"+
        "					 'id': '554',"+
        "					 'name': 'TENA',"+
        "					 'position': NumberInt(96)"+
        "				},"+
        "				 {"+
        "					 'id': '555',"+
        "					 'name': 'TENJO',"+
        "					 'position': NumberInt(97)"+
        "				},"+
        "				 {"+
        "					 'id': '556',"+
        "					 'name': 'TIBACUY',"+
        "					 'position': NumberInt(98)"+
        "				},"+
        "				 {"+
        "					 'id': '557',"+
        "					 'name': 'TIBIRITA',"+
        "					 'position': NumberInt(99)"+
        "				},"+
        "				 {"+
        "					 'id': '558',"+
        "					 'name': 'TOCAIMA',"+
        "					 'position': NumberInt(100)"+
        "				},"+
        "				 {"+
        "					 'id': '559',"+
        "					 'name': 'TOCANCIPÁ',"+
        "					 'position': NumberInt(101)"+
        "				},"+
        "				 {"+
        "					 'id': '560',"+
        "					 'name': 'TOPAIPÍ',"+
        "					 'position': NumberInt(102)"+
        "				},"+
        "				 {"+
        "					 'id': '561',"+
        "					 'name': 'UBALÁ',"+
        "					 'position': NumberInt(103)"+
        "				},"+
        "				 {"+
        "					 'id': '562',"+
        "					 'name': 'UBAQUE',"+
        "					 'position': NumberInt(104)"+
        "				},"+
        "				 {"+
        "					 'id': '563',"+
        "					 'name': 'VILLA DE SAN DIEGO DE UBATÉ',"+
        "					 'position': NumberInt(105)"+
        "				},"+
        "				 {"+
        "					 'id': '564',"+
        "					 'name': 'UNE',"+
        "					 'position': NumberInt(106)"+
        "				},"+
        "				 {"+
        "					 'id': '565',"+
        "					 'name': 'ÚTICA',"+
        "					 'position': NumberInt(107)"+
        "				},"+
        "				 {"+
        "					 'id': '566',"+
        "					 'name': 'VERGARA',"+
        "					 'position': NumberInt(108)"+
        "				},"+
        "				 {"+
        "					 'id': '567',"+
        "					 'name': 'VIANÍ',"+
        "					 'position': NumberInt(109)"+
        "				},"+
        "				 {"+
        "					 'id': '568',"+
        "					 'name': 'VILLAGÓMEZ',"+
        "					 'position': NumberInt(110)"+
        "				},"+
        "				 {"+
        "					 'id': '569',"+
        "					 'name': 'VILLAPINZÓN',"+
        "					 'position': NumberInt(111)"+
        "				},"+
        "				 {"+
        "					 'id': '570',"+
        "					 'name': 'VILLETA',"+
        "					 'position': NumberInt(112)"+
        "				},"+
        "				 {"+
        "					 'id': '571',"+
        "					 'name': 'VIOTÁ',"+
        "					 'position': NumberInt(113)"+
        "				},"+
        "				 {"+
        "					 'id': '572',"+
        "					 'name': 'YACOPÍ',"+
        "					 'position': NumberInt(114)"+
        "				},"+
        "				 {"+
        "					 'id': '573',"+
        "					 'name': 'ZIPACÓN',"+
        "					 'position': NumberInt(115)"+
        "				},"+
        "				 {"+
        "					 'id': '574',"+
        "					 'name': 'ZIPAQUIRÁ',"+
        "					 'position': NumberInt(116)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(353),"+
        "			 'name': 'Municipio (Guainia)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(133),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1100',"+
        "					 'name': 'INÍRIDA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1101',"+
        "					 'name': 'BARRANCO MINAS',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '1102',"+
        "					 'name': 'MAPIRIPANA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '1103',"+
        "					 'name': 'SAN FELIPE',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '1104',"+
        "					 'name': 'PUERTO COLOMBIA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '1105',"+
        "					 'name': 'LA GUADALUPE',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '1106',"+
        "					 'name': 'CACAHUAL',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '1107',"+
        "					 'name': 'PANA PANA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '1108',"+
        "					 'name': 'MORICHAL',"+
        "					 'position': NumberInt(9)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(354),"+
        "			 'name': 'Municipio (Guaviare)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(134),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1109',"+
        "					 'name': 'SAN JOSÉ DEL GUAVIARE',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1110',"+
        "					 'name': 'CALAMAR',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '1111',"+
        "					 'name': 'EL RETORNO',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '1112',"+
        "					 'name': 'MIRAFLORES',"+
        "					 'position': NumberInt(4)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(355),"+
        "			 'name': 'Municipio (Huila)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(135),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '605',"+
        "					 'name': 'NEIVA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '606',"+
        "					 'name': 'ACEVEDO',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '607',"+
        "					 'name': 'AGRADO',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '608',"+
        "					 'name': 'AIPE',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '609',"+
        "					 'name': 'ALGECIRAS',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '610',"+
        "					 'name': 'ALTAMIRA',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '611',"+
        "					 'name': 'BARAYA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '612',"+
        "					 'name': 'CAMPOALEGRE',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '613',"+
        "					 'name': 'COLOMBIA',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '614',"+
        "					 'name': 'ELÍAS',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '615',"+
        "					 'name': 'GARZÓN',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '616',"+
        "					 'name': 'GIGANTE',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '617',"+
        "					 'name': 'GUADALUPE',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '618',"+
        "					 'name': 'HOBO',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '619',"+
        "					 'name': 'ÍQUIRA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '620',"+
        "					 'name': 'ISNOS',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '621',"+
        "					 'name': 'LA ARGENTINA',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '622',"+
        "					 'name': 'LA PLATA',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '623',"+
        "					 'name': 'NÁTAGA',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '624',"+
        "					 'name': 'OPORAPA',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '625',"+
        "					 'name': 'PAICOL',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '626',"+
        "					 'name': 'PALERMO',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '627',"+
        "					 'name': 'PALESTINA',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '628',"+
        "					 'name': 'PITAL',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '629',"+
        "					 'name': 'PITALITO',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '630',"+
        "					 'name': 'RIVERA',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '631',"+
        "					 'name': 'SALADOBLANCO',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '632',"+
        "					 'name': 'SAN AGUSTÍN',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '633',"+
        "					 'name': 'SANTA MARÍA',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '634',"+
        "					 'name': 'SUAZA',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '635',"+
        "					 'name': 'TARQUI',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '636',"+
        "					 'name': 'TESALIA',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '637',"+
        "					 'name': 'TELLO',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '638',"+
        "					 'name': 'TERUEL',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '639',"+
        "					 'name': 'TIMANÁ',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '640',"+
        "					 'name': 'VILLAVIEJA',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '641',"+
        "					 'name': 'YAGUARÁ',"+
        "					 'position': NumberInt(37)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(356),"+
        "			 'name': 'Municipio (La Guajira)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(136),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '642',"+
        "					 'name': 'RIOHACHA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '643',"+
        "					 'name': 'ALBANIA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '644',"+
        "					 'name': 'BARRANCAS',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '645',"+
        "					 'name': 'DIBULLA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '646',"+
        "					 'name': 'DISTRACCIÓN',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '647',"+
        "					 'name': 'EL MOLINO',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '648',"+
        "					 'name': 'FONSECA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '649',"+
        "					 'name': 'HATONUEVO',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '650',"+
        "					 'name': 'LA JAGUA DEL PILAR',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '651',"+
        "					 'name': 'MAICAO',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '652',"+
        "					 'name': 'MANAURE',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '653',"+
        "					 'name': 'SAN JUAN DEL CESAR',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '654',"+
        "					 'name': 'URIBIA',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '655',"+
        "					 'name': 'URUMITA',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '656',"+
        "					 'name': 'VILLANUEVA',"+
        "					 'position': NumberInt(15)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(357),"+
        "			 'name': 'Municipio (Magdalena)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(137),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '657',"+
        "					 'name': 'SANTA MARTA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '658',"+
        "					 'name': 'ALGARROBO',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '659',"+
        "					 'name': 'ARACATACA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '660',"+
        "					 'name': 'ARIGUANÍ',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '661',"+
        "					 'name': 'CERRO DE SAN ANTONIO',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '662',"+
        "					 'name': 'CHIVOLO',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '663',"+
        "					 'name': 'CIÉNAGA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '664',"+
        "					 'name': 'CONCORDIA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '665',"+
        "					 'name': 'EL BANCO',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '666',"+
        "					 'name': 'EL PIÑÓN',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '667',"+
        "					 'name': 'EL RETÉN',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '668',"+
        "					 'name': 'FUNDACIÓN',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '669',"+
        "					 'name': 'GUAMAL',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '670',"+
        "					 'name': 'NUEVA GRANADA',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '671',"+
        "					 'name': 'PEDRAZA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '672',"+
        "					 'name': 'PIJIÑO DEL CARMEN',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '673',"+
        "					 'name': 'PIVIJAY',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '674',"+
        "					 'name': 'PLATO',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '675',"+
        "					 'name': 'PUEBLOVIEJO',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '676',"+
        "					 'name': 'REMOLINO',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '677',"+
        "					 'name': 'SABANAS DE SAN ÁNGEL',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '678',"+
        "					 'name': 'SALAMINA',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '679',"+
        "					 'name': 'SAN SEBASTIÁN DE BUENAVISTA',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '680',"+
        "					 'name': 'SAN ZENÓN',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '681',"+
        "					 'name': 'SANTA ANA',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '682',"+
        "					 'name': 'SANTA BÁRBARA DE PINTO',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '683',"+
        "					 'name': 'SITIONUEVO',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '684',"+
        "					 'name': 'TENERIFE',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '685',"+
        "					 'name': 'ZAPAYÁN',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '686',"+
        "					 'name': 'ZONA BANANERA',"+
        "					 'position': NumberInt(30)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(358),"+
        "			 'name': 'Municipio (Meta)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(138),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '687',"+
        "					 'name': 'VILLAVICENCIO',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '688',"+
        "					 'name': 'ACACÍAS',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '689',"+
        "					 'name': 'BARRANCA DE UPÍA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '690',"+
        "					 'name': 'CABUYARO',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '691',"+
        "					 'name': 'CASTILLA LA NUEVA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '692',"+
        "					 'name': 'SAN LUIS DE CUBARRAL',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '693',"+
        "					 'name': 'CUMARAL',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '694',"+
        "					 'name': 'EL CALVARIO',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '695',"+
        "					 'name': 'EL CASTILLO',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '696',"+
        "					 'name': 'EL DORADO',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '697',"+
        "					 'name': 'FUENTE DE ORO',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '698',"+
        "					 'name': 'GRANADA',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '699',"+
        "					 'name': 'GUAMAL',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '700',"+
        "					 'name': 'MAPIRIPÁN',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '701',"+
        "					 'name': 'MESETAS',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '702',"+
        "					 'name': 'LA MACARENA',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '703',"+
        "					 'name': 'URIBE',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '704',"+
        "					 'name': 'LEJANÍAS',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '705',"+
        "					 'name': 'PUERTO CONCORDIA',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '706',"+
        "					 'name': 'PUERTO GAITÁN',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '707',"+
        "					 'name': 'PUERTO LÓPEZ',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '708',"+
        "					 'name': 'PUERTO LLERAS',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '709',"+
        "					 'name': 'PUERTO RICO',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '710',"+
        "					 'name': 'RESTREPO',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '711',"+
        "					 'name': 'SAN CARLOS DE GUAROA',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '712',"+
        "					 'name': 'SAN JUAN DE ARAMA',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '713',"+
        "					 'name': 'SAN JUANITO',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '714',"+
        "					 'name': 'SAN MARTÍN',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '715',"+
        "					 'name': 'VISTAHERMOSA',"+
        "					 'position': NumberInt(29)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(359),"+
        "			 'name': 'Municipio (Nariño)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(139),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '716',"+
        "					 'name': 'PASTO',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '717',"+
        "					 'name': 'ALBÁN',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '718',"+
        "					 'name': 'ALDANA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '719',"+
        "					 'name': 'ANCUYÁ',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '720',"+
        "					 'name': 'ARBOLEDA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '721',"+
        "					 'name': 'BARBACOAS',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '722',"+
        "					 'name': 'BELÉN',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '723',"+
        "					 'name': 'BUESACO',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '724',"+
        "					 'name': 'COLÓN',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '725',"+
        "					 'name': 'CONSACÁ',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '726',"+
        "					 'name': 'CONTADERO',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '727',"+
        "					 'name': 'CÓRDOBA',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '728',"+
        "					 'name': 'CUASPÚD',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '729',"+
        "					 'name': 'CUMBAL',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '730',"+
        "					 'name': 'CUMBITARA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '731',"+
        "					 'name': 'CHACHAGÜÍ',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '732',"+
        "					 'name': 'EL CHARCO',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '733',"+
        "					 'name': 'EL PEÑOL',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '734',"+
        "					 'name': 'EL ROSARIO',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '735',"+
        "					 'name': 'EL TABLÓN DE GÓMEZ',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '736',"+
        "					 'name': 'EL TAMBO',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '737',"+
        "					 'name': 'FUNES',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '738',"+
        "					 'name': 'GUACHUCAL',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '739',"+
        "					 'name': 'GUAITARILLA',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '740',"+
        "					 'name': 'GUALMATÁN',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '741',"+
        "					 'name': 'ILES',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '742',"+
        "					 'name': 'IMUÉS',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '743',"+
        "					 'name': 'IPIALES',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '744',"+
        "					 'name': 'LA CRUZ',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '745',"+
        "					 'name': 'LA FLORIDA',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '746',"+
        "					 'name': 'LA LLANADA',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '747',"+
        "					 'name': 'LA TOLA',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '748',"+
        "					 'name': 'LA UNIÓN',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '749',"+
        "					 'name': 'LEIVA',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '750',"+
        "					 'name': 'LINARES',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '751',"+
        "					 'name': 'LOS ANDES',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '752',"+
        "					 'name': 'MAGÜÍ',"+
        "					 'position': NumberInt(37)"+
        "				},"+
        "				 {"+
        "					 'id': '753',"+
        "					 'name': 'MALLAMA',"+
        "					 'position': NumberInt(38)"+
        "				},"+
        "				 {"+
        "					 'id': '754',"+
        "					 'name': 'MOSQUERA',"+
        "					 'position': NumberInt(39)"+
        "				},"+
        "				 {"+
        "					 'id': '755',"+
        "					 'name': 'NARIÑO',"+
        "					 'position': NumberInt(40)"+
        "				},"+
        "				 {"+
        "					 'id': '756',"+
        "					 'name': 'OLAYA HERRERA',"+
        "					 'position': NumberInt(41)"+
        "				},"+
        "				 {"+
        "					 'id': '757',"+
        "					 'name': 'OSPINA',"+
        "					 'position': NumberInt(42)"+
        "				},"+
        "				 {"+
        "					 'id': '758',"+
        "					 'name': 'FRANCISCO PIZARRO',"+
        "					 'position': NumberInt(43)"+
        "				},"+
        "				 {"+
        "					 'id': '759',"+
        "					 'name': 'POLICARPA',"+
        "					 'position': NumberInt(44)"+
        "				},"+
        "				 {"+
        "					 'id': '760',"+
        "					 'name': 'POTOSÍ',"+
        "					 'position': NumberInt(45)"+
        "				},"+
        "				 {"+
        "					 'id': '761',"+
        "					 'name': 'PROVIDENCIA',"+
        "					 'position': NumberInt(46)"+
        "				},"+
        "				 {"+
        "					 'id': '762',"+
        "					 'name': 'PUERRES',"+
        "					 'position': NumberInt(47)"+
        "				},"+
        "				 {"+
        "					 'id': '763',"+
        "					 'name': 'PUPIALES',"+
        "					 'position': NumberInt(48)"+
        "				},"+
        "				 {"+
        "					 'id': '764',"+
        "					 'name': 'RICAURTE',"+
        "					 'position': NumberInt(49)"+
        "				},"+
        "				 {"+
        "					 'id': '765',"+
        "					 'name': 'ROBERTO PAYÁN',"+
        "					 'position': NumberInt(50)"+
        "				},"+
        "				 {"+
        "					 'id': '766',"+
        "					 'name': 'SAMANIEGO',"+
        "					 'position': NumberInt(51)"+
        "				},"+
        "				 {"+
        "					 'id': '767',"+
        "					 'name': 'SANDONÁ',"+
        "					 'position': NumberInt(52)"+
        "				},"+
        "				 {"+
        "					 'id': '768',"+
        "					 'name': 'SAN BERNARDO',"+
        "					 'position': NumberInt(53)"+
        "				},"+
        "				 {"+
        "					 'id': '769',"+
        "					 'name': 'SAN LORENZO',"+
        "					 'position': NumberInt(54)"+
        "				},"+
        "				 {"+
        "					 'id': '770',"+
        "					 'name': 'SAN PABLO',"+
        "					 'position': NumberInt(55)"+
        "				},"+
        "				 {"+
        "					 'id': '771',"+
        "					 'name': 'SAN PEDRO DE CARTAGO',"+
        "					 'position': NumberInt(56)"+
        "				},"+
        "				 {"+
        "					 'id': '772',"+
        "					 'name': 'SANTA BÁRBARA',"+
        "					 'position': NumberInt(57)"+
        "				},"+
        "				 {"+
        "					 'id': '773',"+
        "					 'name': 'SANTACRUZ',"+
        "					 'position': NumberInt(58)"+
        "				},"+
        "				 {"+
        "					 'id': '774',"+
        "					 'name': 'SAPUYES',"+
        "					 'position': NumberInt(59)"+
        "				},"+
        "				 {"+
        "					 'id': '775',"+
        "					 'name': 'TAMINANGO',"+
        "					 'position': NumberInt(60)"+
        "				},"+
        "				 {"+
        "					 'id': '776',"+
        "					 'name': 'TANGUA',"+
        "					 'position': NumberInt(61)"+
        "				},"+
        "				 {"+
        "					 'id': '777',"+
        "					 'name': 'SAN ANDRÉS DE TUMACO',"+
        "					 'position': NumberInt(62)"+
        "				},"+
        "				 {"+
        "					 'id': '778',"+
        "					 'name': 'TÚQUERRES',"+
        "					 'position': NumberInt(63)"+
        "				},"+
        "				 {"+
        "					 'id': '779',"+
        "					 'name': 'YACUANQUER',"+
        "					 'position': NumberInt(64)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(360),"+
        "			 'name': 'Municipio (Norte de Santander)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(140),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '780',"+
        "					 'name': 'CÚCUTA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '781',"+
        "					 'name': 'ÁBREGO',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '782',"+
        "					 'name': 'ARBOLEDAS',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '783',"+
        "					 'name': 'BOCHALEMA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '784',"+
        "					 'name': 'BUCARASICA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '785',"+
        "					 'name': 'CÁCOTA',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '786',"+
        "					 'name': 'CÁCHIRA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '787',"+
        "					 'name': 'CHINÁCOTA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '788',"+
        "					 'name': 'CHITAGÁ',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '789',"+
        "					 'name': 'CONVENCIÓN',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '790',"+
        "					 'name': 'CUCUTILLA',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '791',"+
        "					 'name': 'DURANIA',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '792',"+
        "					 'name': 'EL CARMEN',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '793',"+
        "					 'name': 'EL TARRA',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '794',"+
        "					 'name': 'EL ZULIA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '795',"+
        "					 'name': 'GRAMALOTE',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '796',"+
        "					 'name': 'HACARÍ',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '797',"+
        "					 'name': 'HERRÁN',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '798',"+
        "					 'name': 'LABATECA',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '799',"+
        "					 'name': 'LA ESPERANZA',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '800',"+
        "					 'name': 'LA PLAYA',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '801',"+
        "					 'name': 'LOS PATIOS',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '802',"+
        "					 'name': 'LOURDES',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '803',"+
        "					 'name': 'MUTISCUA',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '804',"+
        "					 'name': 'OCAÑA',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '805',"+
        "					 'name': 'PAMPLONA',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '806',"+
        "					 'name': 'PAMPLONITA',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '807',"+
        "					 'name': 'PUERTO SANTANDER',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '808',"+
        "					 'name': 'RAGONVALIA',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '809',"+
        "					 'name': 'SALAZAR',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '810',"+
        "					 'name': 'SAN CALIXTO',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '811',"+
        "					 'name': 'SAN CAYETANO',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '812',"+
        "					 'name': 'SANTIAGO',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '813',"+
        "					 'name': 'SARDINATA',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '814',"+
        "					 'name': 'SILOS',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '815',"+
        "					 'name': 'TEORAMA',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '816',"+
        "					 'name': 'TIBÚ',"+
        "					 'position': NumberInt(37)"+
        "				},"+
        "				 {"+
        "					 'id': '817',"+
        "					 'name': 'TOLEDO',"+
        "					 'position': NumberInt(38)"+
        "				},"+
        "				 {"+
        "					 'id': '818',"+
        "					 'name': 'VILLA CARO',"+
        "					 'position': NumberInt(39)"+
        "				},"+
        "				 {"+
        "					 'id': '819',"+
        "					 'name': 'VILLA DEL ROSARIO',"+
        "					 'position': NumberInt(40)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(361),"+
        "			 'name': 'Municipio (Putumayo)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(141),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1074',"+
        "					 'name': 'MOCOA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1075',"+
        "					 'name': 'COLÓN',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '1076',"+
        "					 'name': 'ORITO',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '1077',"+
        "					 'name': 'PUERTO ASÍS',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '1078',"+
        "					 'name': 'PUERTO CAICEDO',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '1079',"+
        "					 'name': 'PUERTO GUZMÁN',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '1080',"+
        "					 'name': 'PUERTO LEGUÍZAMO',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '1081',"+
        "					 'name': 'SIBUNDOY',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '1082',"+
        "					 'name': 'SAN FRANCISCO',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '1083',"+
        "					 'name': 'SAN MIGUEL',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '1084',"+
        "					 'name': 'SANTIAGO',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '1085',"+
        "					 'name': 'VALLE DEL GUAMUEZ',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '1086',"+
        "					 'name': 'VILLAGARZÓN',"+
        "					 'position': NumberInt(13)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(362),"+
        "			 'name': 'Municipio (Quindio)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(142),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '820',"+
        "					 'name': 'ARMENIA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '821',"+
        "					 'name': 'BUENAVISTA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '822',"+
        "					 'name': 'CALARCÁ',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '823',"+
        "					 'name': 'CIRCASIA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '824',"+
        "					 'name': 'CÓRDOBA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '825',"+
        "					 'name': 'FILANDIA',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '826',"+
        "					 'name': 'GÉNOVA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '827',"+
        "					 'name': 'LA TEBAIDA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '828',"+
        "					 'name': 'MONTENEGRO',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '829',"+
        "					 'name': 'PIJAO',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '830',"+
        "					 'name': 'QUIMBAYA',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '831',"+
        "					 'name': 'SALENTO',"+
        "					 'position': NumberInt(12)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(363),"+
        "			 'name': 'Municipio (Risaralda)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(143),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '832',"+
        "					 'name': 'PEREIRA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '833',"+
        "					 'name': 'APÍA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '834',"+
        "					 'name': 'BALBOA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '835',"+
        "					 'name': 'BELÉN DE UMBRÍA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '836',"+
        "					 'name': 'DOSQUEBRADAS',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '837',"+
        "					 'name': 'GUÁTICA',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '838',"+
        "					 'name': 'LA CELIA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '839',"+
        "					 'name': 'LA VIRGINIA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '840',"+
        "					 'name': 'MARSELLA',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '841',"+
        "					 'name': 'MISTRATÓ',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '842',"+
        "					 'name': 'PUEBLO RICO',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '843',"+
        "					 'name': 'QUINCHÍA',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '844',"+
        "					 'name': 'SANTA ROSA DE CABAL',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '845',"+
        "					 'name': 'SANTUARIO',"+
        "					 'position': NumberInt(14)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(364),"+
        "			 'name': 'Municipio (Santander)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(144),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '846',"+
        "					 'name': 'BUCARAMANGA',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '847',"+
        "					 'name': 'AGUADA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '848',"+
        "					 'name': 'ALBANIA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '849',"+
        "					 'name': 'ARATOCA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '850',"+
        "					 'name': 'BARBOSA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '851',"+
        "					 'name': 'BARICHARA',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '852',"+
        "					 'name': 'BARRANCABERMEJA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '853',"+
        "					 'name': 'BETULIA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '854',"+
        "					 'name': 'BOLÍVAR',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '855',"+
        "					 'name': 'CABRERA',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '856',"+
        "					 'name': 'CALIFORNIA',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '857',"+
        "					 'name': 'CAPITANEJO',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '858',"+
        "					 'name': 'CARCASÍ',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '859',"+
        "					 'name': 'CEPITÁ',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '860',"+
        "					 'name': 'CERRITO',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '861',"+
        "					 'name': 'CHARALÁ',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '862',"+
        "					 'name': 'CHARTA',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '863',"+
        "					 'name': 'CHIMA',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '864',"+
        "					 'name': 'CHIPATÁ',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '865',"+
        "					 'name': 'CIMITARRA',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '866',"+
        "					 'name': 'CONCEPCIÓN',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '867',"+
        "					 'name': 'CONFINES',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '868',"+
        "					 'name': 'CONTRATACIÓN',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '869',"+
        "					 'name': 'COROMORO',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '870',"+
        "					 'name': 'CURITÍ',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '871',"+
        "					 'name': 'EL CARMEN DE CHUCURÍ',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '872',"+
        "					 'name': 'EL GUACAMAYO',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '873',"+
        "					 'name': 'EL PEÑÓN',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '874',"+
        "					 'name': 'EL PLAYÓN',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '875',"+
        "					 'name': 'ENCINO',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '876',"+
        "					 'name': 'ENCISO',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '877',"+
        "					 'name': 'FLORIÁN',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '878',"+
        "					 'name': 'FLORIDABLANCA',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '879',"+
        "					 'name': 'GALÁN',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '880',"+
        "					 'name': 'GÁMBITA',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '881',"+
        "					 'name': 'GIRÓN',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '882',"+
        "					 'name': 'GUACA',"+
        "					 'position': NumberInt(37)"+
        "				},"+
        "				 {"+
        "					 'id': '883',"+
        "					 'name': 'GUADALUPE',"+
        "					 'position': NumberInt(38)"+
        "				},"+
        "				 {"+
        "					 'id': '884',"+
        "					 'name': 'GUAPOTÁ',"+
        "					 'position': NumberInt(39)"+
        "				},"+
        "				 {"+
        "					 'id': '885',"+
        "					 'name': 'GUAVATÁ',"+
        "					 'position': NumberInt(40)"+
        "				},"+
        "				 {"+
        "					 'id': '886',"+
        "					 'name': 'GÜEPSA',"+
        "					 'position': NumberInt(41)"+
        "				},"+
        "				 {"+
        "					 'id': '887',"+
        "					 'name': 'HATO',"+
        "					 'position': NumberInt(42)"+
        "				},"+
        "				 {"+
        "					 'id': '888',"+
        "					 'name': 'JESÚS MARÍA',"+
        "					 'position': NumberInt(43)"+
        "				},"+
        "				 {"+
        "					 'id': '889',"+
        "					 'name': 'JORDÁN',"+
        "					 'position': NumberInt(44)"+
        "				},"+
        "				 {"+
        "					 'id': '890',"+
        "					 'name': 'LA BELLEZA',"+
        "					 'position': NumberInt(45)"+
        "				},"+
        "				 {"+
        "					 'id': '891',"+
        "					 'name': 'LANDÁZURI',"+
        "					 'position': NumberInt(46)"+
        "				},"+
        "				 {"+
        "					 'id': '892',"+
        "					 'name': 'LA PAZ',"+
        "					 'position': NumberInt(47)"+
        "				},"+
        "				 {"+
        "					 'id': '893',"+
        "					 'name': 'LEBRIJA',"+
        "					 'position': NumberInt(48)"+
        "				},"+
        "				 {"+
        "					 'id': '894',"+
        "					 'name': 'LOS SANTOS',"+
        "					 'position': NumberInt(49)"+
        "				},"+
        "				 {"+
        "					 'id': '895',"+
        "					 'name': 'MACARAVITA',"+
        "					 'position': NumberInt(50)"+
        "				},"+
        "				 {"+
        "					 'id': '896',"+
        "					 'name': 'MÁLAGA',"+
        "					 'position': NumberInt(51)"+
        "				},"+
        "				 {"+
        "					 'id': '897',"+
        "					 'name': 'MATANZA',"+
        "					 'position': NumberInt(52)"+
        "				},"+
        "				 {"+
        "					 'id': '898',"+
        "					 'name': 'MOGOTES',"+
        "					 'position': NumberInt(53)"+
        "				},"+
        "				 {"+
        "					 'id': '899',"+
        "					 'name': 'MOLAGAVITA',"+
        "					 'position': NumberInt(54)"+
        "				},"+
        "				 {"+
        "					 'id': '900',"+
        "					 'name': 'OCAMONTE',"+
        "					 'position': NumberInt(55)"+
        "				},"+
        "				 {"+
        "					 'id': '901',"+
        "					 'name': 'OIBA',"+
        "					 'position': NumberInt(56)"+
        "				},"+
        "				 {"+
        "					 'id': '902',"+
        "					 'name': 'ONZAGA',"+
        "					 'position': NumberInt(57)"+
        "				},"+
        "				 {"+
        "					 'id': '903',"+
        "					 'name': 'PALMAR',"+
        "					 'position': NumberInt(58)"+
        "				},"+
        "				 {"+
        "					 'id': '904',"+
        "					 'name': 'PALMAS DEL SOCORRO',"+
        "					 'position': NumberInt(59)"+
        "				},"+
        "				 {"+
        "					 'id': '905',"+
        "					 'name': 'PÁRAMO',"+
        "					 'position': NumberInt(60)"+
        "				},"+
        "				 {"+
        "					 'id': '906',"+
        "					 'name': 'PIEDECUESTA',"+
        "					 'position': NumberInt(61)"+
        "				},"+
        "				 {"+
        "					 'id': '907',"+
        "					 'name': 'PINCHOTE',"+
        "					 'position': NumberInt(62)"+
        "				},"+
        "				 {"+
        "					 'id': '908',"+
        "					 'name': 'PUENTE NACIONAL',"+
        "					 'position': NumberInt(63)"+
        "				},"+
        "				 {"+
        "					 'id': '909',"+
        "					 'name': 'PUERTO PARRA',"+
        "					 'position': NumberInt(64)"+
        "				},"+
        "				 {"+
        "					 'id': '910',"+
        "					 'name': 'PUERTO WILCHES',"+
        "					 'position': NumberInt(65)"+
        "				},"+
        "				 {"+
        "					 'id': '911',"+
        "					 'name': 'RIONEGRO',"+
        "					 'position': NumberInt(66)"+
        "				},"+
        "				 {"+
        "					 'id': '912',"+
        "					 'name': 'SABANA DE TORRES',"+
        "					 'position': NumberInt(67)"+
        "				},"+
        "				 {"+
        "					 'id': '913',"+
        "					 'name': 'SAN ANDRÉS',"+
        "					 'position': NumberInt(68)"+
        "				},"+
        "				 {"+
        "					 'id': '914',"+
        "					 'name': 'SAN BENITO',"+
        "					 'position': NumberInt(69)"+
        "				},"+
        "				 {"+
        "					 'id': '915',"+
        "					 'name': 'SAN GIL',"+
        "					 'position': NumberInt(70)"+
        "				},"+
        "				 {"+
        "					 'id': '916',"+
        "					 'name': 'SAN JOAQUÍN',"+
        "					 'position': NumberInt(71)"+
        "				},"+
        "				 {"+
        "					 'id': '917',"+
        "					 'name': 'SAN JOSÉ DE MIRANDA',"+
        "					 'position': NumberInt(72)"+
        "				},"+
        "				 {"+
        "					 'id': '918',"+
        "					 'name': 'SAN MIGUEL',"+
        "					 'position': NumberInt(73)"+
        "				},"+
        "				 {"+
        "					 'id': '919',"+
        "					 'name': 'SAN VICENTE DE CHUCURÍ',"+
        "					 'position': NumberInt(74)"+
        "				},"+
        "				 {"+
        "					 'id': '920',"+
        "					 'name': 'SANTA BÁRBARA',"+
        "					 'position': NumberInt(75)"+
        "				},"+
        "				 {"+
        "					 'id': '921',"+
        "					 'name': 'SANTA HELENA DEL OPÓN',"+
        "					 'position': NumberInt(76)"+
        "				},"+
        "				 {"+
        "					 'id': '922',"+
        "					 'name': 'SIMACOTA',"+
        "					 'position': NumberInt(77)"+
        "				},"+
        "				 {"+
        "					 'id': '923',"+
        "					 'name': 'SOCORRO',"+
        "					 'position': NumberInt(78)"+
        "				},"+
        "				 {"+
        "					 'id': '924',"+
        "					 'name': 'SUAITA',"+
        "					 'position': NumberInt(79)"+
        "				},"+
        "				 {"+
        "					 'id': '925',"+
        "					 'name': 'SUCRE',"+
        "					 'position': NumberInt(80)"+
        "				},"+
        "				 {"+
        "					 'id': '926',"+
        "					 'name': 'SURATÁ',"+
        "					 'position': NumberInt(81)"+
        "				},"+
        "				 {"+
        "					 'id': '927',"+
        "					 'name': 'TONA',"+
        "					 'position': NumberInt(82)"+
        "				},"+
        "				 {"+
        "					 'id': '928',"+
        "					 'name': 'VALLE DE SAN JOSÉ',"+
        "					 'position': NumberInt(83)"+
        "				},"+
        "				 {"+
        "					 'id': '929',"+
        "					 'name': 'VÉLEZ',"+
        "					 'position': NumberInt(84)"+
        "				},"+
        "				 {"+
        "					 'id': '930',"+
        "					 'name': 'VETAS',"+
        "					 'position': NumberInt(85)"+
        "				},"+
        "				 {"+
        "					 'id': '931',"+
        "					 'name': 'VILLANUEVA',"+
        "					 'position': NumberInt(86)"+
        "				},"+
        "				 {"+
        "					 'id': '932',"+
        "					 'name': 'ZAPATOCA',"+
        "					 'position': NumberInt(87)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(365),"+
        "			 'name': 'Municipio (Bogota DC)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(145),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '149',"+
        "					 'name': 'BOGOTÁ',"+
        "					 'position': NumberInt(1)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(366),"+
        "			 'name': 'Municipio (Sucre)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(146),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '933',"+
        "					 'name': 'SINCELEJO',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '934',"+
        "					 'name': 'BUENAVISTA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '935',"+
        "					 'name': 'CAIMITO',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '936',"+
        "					 'name': 'COLOSO',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '937',"+
        "					 'name': 'COROZAL',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '938',"+
        "					 'name': 'COVEÑAS',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '939',"+
        "					 'name': 'CHALÁN',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '940',"+
        "					 'name': 'EL ROBLE',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '941',"+
        "					 'name': 'GALERAS',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '942',"+
        "					 'name': 'GUARANDA',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '943',"+
        "					 'name': 'LA UNIÓN',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '944',"+
        "					 'name': 'LOS PALMITOS',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '945',"+
        "					 'name': 'MAJAGUAL',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '946',"+
        "					 'name': 'MORROA',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '947',"+
        "					 'name': 'OVEJAS',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '948',"+
        "					 'name': 'PALMITO',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '949',"+
        "					 'name': 'SAMPUÉS',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '950',"+
        "					 'name': 'SAN BENITO ABAD',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '951',"+
        "					 'name': 'SAN JUAN DE BETULIA',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '952',"+
        "					 'name': 'SAN MARCOS',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '953',"+
        "					 'name': 'SAN ONOFRE',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '954',"+
        "					 'name': 'SAN PEDRO',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '955',"+
        "					 'name': 'SAN LUIS DE SINCÉ',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '956',"+
        "					 'name': 'SUCRE',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '957',"+
        "					 'name': 'SANTIAGO DE TOLÚ',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '958',"+
        "					 'name': 'TOLÚ VIEJO',"+
        "					 'position': NumberInt(26)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(367),"+
        "			 'name': 'Municipio (Tolima)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(147),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '959',"+
        "					 'name': 'IBAGUÉ',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '960',"+
        "					 'name': 'ALPUJARRA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '961',"+
        "					 'name': 'ALVARADO',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '962',"+
        "					 'name': 'AMBALEMA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '963',"+
        "					 'name': 'ANZOÁTEGUI',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '964',"+
        "					 'name': 'ARMERO GUAYABAL',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '965',"+
        "					 'name': 'ATACO',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '966',"+
        "					 'name': 'CAJAMARCA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '967',"+
        "					 'name': 'CARMEN DE APICALÁ',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '968',"+
        "					 'name': 'CASABIANCA',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '969',"+
        "					 'name': 'CHAPARRAL',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '970',"+
        "					 'name': 'COELLO',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '971',"+
        "					 'name': 'COYAIMA',"+
        "					 'position': NumberInt(13)"+
        "				},";
        String json3 = "				 {"+
        "					 'id': '972',"+
        "					 'name': 'CUNDAY',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '973',"+
        "					 'name': 'DOLORES',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '974',"+
        "					 'name': 'ESPINAL',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '975',"+
        "					 'name': 'FALAN',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '976',"+
        "					 'name': 'FLANDES',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '977',"+
        "					 'name': 'FRESNO',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '978',"+
        "					 'name': 'GUAMO',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '979',"+
        "					 'name': 'HERVEO',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '980',"+
        "					 'name': 'HONDA',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '981',"+
        "					 'name': 'ICONONZO',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '982',"+
        "					 'name': 'LÉRIDA',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '983',"+
        "					 'name': 'LÍBANO',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '984',"+
        "					 'name': 'SAN SEBASTIÁN DE MARIQUITA',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '985',"+
        "					 'name': 'MELGAR',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '986',"+
        "					 'name': 'MURILLO',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '987',"+
        "					 'name': 'NATAGAIMA',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '988',"+
        "					 'name': 'ORTEGA',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '989',"+
        "					 'name': 'PALOCABILDO',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '990',"+
        "					 'name': 'PIEDRAS',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '991',"+
        "					 'name': 'PLANADAS',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '992',"+
        "					 'name': 'PRADO',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '993',"+
        "					 'name': 'PURIFICACIÓN',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '994',"+
        "					 'name': 'RIOBLANCO',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '995',"+
        "					 'name': 'RONCESVALLES',"+
        "					 'position': NumberInt(37)"+
        "				},"+
        "				 {"+
        "					 'id': '996',"+
        "					 'name': 'ROVIRA',"+
        "					 'position': NumberInt(38)"+
        "				},"+
        "				 {"+
        "					 'id': '997',"+
        "					 'name': 'SALDAÑA',"+
        "					 'position': NumberInt(39)"+
        "				},"+
        "				 {"+
        "					 'id': '998',"+
        "					 'name': 'SAN ANTONIO',"+
        "					 'position': NumberInt(40)"+
        "				},"+
        "				 {"+
        "					 'id': '999',"+
        "					 'name': 'SAN LUIS',"+
        "					 'position': NumberInt(41)"+
        "				},"+
        "				 {"+
        "					 'id': '1000',"+
        "					 'name': 'SANTA ISABEL',"+
        "					 'position': NumberInt(42)"+
        "				},"+
        "				 {"+
        "					 'id': '1001',"+
        "					 'name': 'SUÁREZ',"+
        "					 'position': NumberInt(43)"+
        "				},"+
        "				 {"+
        "					 'id': '1002',"+
        "					 'name': 'VALLE DE SAN JUAN',"+
        "					 'position': NumberInt(44)"+
        "				},"+
        "				 {"+
        "					 'id': '1003',"+
        "					 'name': 'VENADILLO',"+
        "					 'position': NumberInt(45)"+
        "				},"+
        "				 {"+
        "					 'id': '1004',"+
        "					 'name': 'VILLAHERMOSA',"+
        "					 'position': NumberInt(46)"+
        "				},"+
        "				 {"+
        "					 'id': '1005',"+
        "					 'name': 'VILLARRICA',"+
        "					 'position': NumberInt(47)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(368),"+
        "			 'name': 'Municipio (Valle del Cauca)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(148),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1006',"+
        "					 'name': 'CALI',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1007',"+
        "					 'name': 'ALCALÁ',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '1008',"+
        "					 'name': 'ANDALUCÍA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '1009',"+
        "					 'name': 'ANSERMANUEVO',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '1010',"+
        "					 'name': 'ARGELIA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '1011',"+
        "					 'name': 'BOLÍVAR',"+
        "					 'position': NumberInt(6)"+
        "				},"+
        "				 {"+
        "					 'id': '1012',"+
        "					 'name': 'BUENAVENTURA',"+
        "					 'position': NumberInt(7)"+
        "				},"+
        "				 {"+
        "					 'id': '1013',"+
        "					 'name': 'GUADALAJARA DE BUGA',"+
        "					 'position': NumberInt(8)"+
        "				},"+
        "				 {"+
        "					 'id': '1014',"+
        "					 'name': 'BUGALAGRANDE',"+
        "					 'position': NumberInt(9)"+
        "				},"+
        "				 {"+
        "					 'id': '1015',"+
        "					 'name': 'CAICEDONIA',"+
        "					 'position': NumberInt(10)"+
        "				},"+
        "				 {"+
        "					 'id': '1016',"+
        "					 'name': 'CALIMA',"+
        "					 'position': NumberInt(11)"+
        "				},"+
        "				 {"+
        "					 'id': '1017',"+
        "					 'name': 'CANDELARIA',"+
        "					 'position': NumberInt(12)"+
        "				},"+
        "				 {"+
        "					 'id': '1018',"+
        "					 'name': 'CARTAGO',"+
        "					 'position': NumberInt(13)"+
        "				},"+
        "				 {"+
        "					 'id': '1019',"+
        "					 'name': 'DAGUA',"+
        "					 'position': NumberInt(14)"+
        "				},"+
        "				 {"+
        "					 'id': '1020',"+
        "					 'name': 'EL ÁGUILA',"+
        "					 'position': NumberInt(15)"+
        "				},"+
        "				 {"+
        "					 'id': '1021',"+
        "					 'name': 'EL CAIRO',"+
        "					 'position': NumberInt(16)"+
        "				},"+
        "				 {"+
        "					 'id': '1022',"+
        "					 'name': 'EL CERRITO',"+
        "					 'position': NumberInt(17)"+
        "				},"+
        "				 {"+
        "					 'id': '1023',"+
        "					 'name': 'EL DOVIO',"+
        "					 'position': NumberInt(18)"+
        "				},"+
        "				 {"+
        "					 'id': '1024',"+
        "					 'name': 'FLORIDA',"+
        "					 'position': NumberInt(19)"+
        "				},"+
        "				 {"+
        "					 'id': '1025',"+
        "					 'name': 'GINEBRA',"+
        "					 'position': NumberInt(20)"+
        "				},"+
        "				 {"+
        "					 'id': '1026',"+
        "					 'name': 'GUACARÍ',"+
        "					 'position': NumberInt(21)"+
        "				},"+
        "				 {"+
        "					 'id': '1027',"+
        "					 'name': 'JAMUNDÍ',"+
        "					 'position': NumberInt(22)"+
        "				},"+
        "				 {"+
        "					 'id': '1028',"+
        "					 'name': 'LA CUMBRE',"+
        "					 'position': NumberInt(23)"+
        "				},"+
        "				 {"+
        "					 'id': '1029',"+
        "					 'name': 'LA UNIÓN',"+
        "					 'position': NumberInt(24)"+
        "				},"+
        "				 {"+
        "					 'id': '1030',"+
        "					 'name': 'LA VICTORIA',"+
        "					 'position': NumberInt(25)"+
        "				},"+
        "				 {"+
        "					 'id': '1031',"+
        "					 'name': 'OBANDO',"+
        "					 'position': NumberInt(26)"+
        "				},"+
        "				 {"+
        "					 'id': '1032',"+
        "					 'name': 'PALMIRA',"+
        "					 'position': NumberInt(27)"+
        "				},"+
        "				 {"+
        "					 'id': '1033',"+
        "					 'name': 'PRADERA',"+
        "					 'position': NumberInt(28)"+
        "				},"+
        "				 {"+
        "					 'id': '1034',"+
        "					 'name': 'RESTREPO',"+
        "					 'position': NumberInt(29)"+
        "				},"+
        "				 {"+
        "					 'id': '1035',"+
        "					 'name': 'RIOFRÍO',"+
        "					 'position': NumberInt(30)"+
        "				},"+
        "				 {"+
        "					 'id': '1036',"+
        "					 'name': 'ROLDANILLO',"+
        "					 'position': NumberInt(31)"+
        "				},"+
        "				 {"+
        "					 'id': '1037',"+
        "					 'name': 'SAN PEDRO',"+
        "					 'position': NumberInt(32)"+
        "				},"+
        "				 {"+
        "					 'id': '1038',"+
        "					 'name': 'SEVILLA',"+
        "					 'position': NumberInt(33)"+
        "				},"+
        "				 {"+
        "					 'id': '1039',"+
        "					 'name': 'TORO',"+
        "					 'position': NumberInt(34)"+
        "				},"+
        "				 {"+
        "					 'id': '1040',"+
        "					 'name': 'TRUJILLO',"+
        "					 'position': NumberInt(35)"+
        "				},"+
        "				 {"+
        "					 'id': '1041',"+
        "					 'name': 'TULUÁ',"+
        "					 'position': NumberInt(36)"+
        "				},"+
        "				 {"+
        "					 'id': '1042',"+
        "					 'name': 'ULLOA',"+
        "					 'position': NumberInt(37)"+
        "				},"+
        "				 {"+
        "					 'id': '1043',"+
        "					 'name': 'VERSALLES',"+
        "					 'position': NumberInt(38)"+
        "				},"+
        "				 {"+
        "					 'id': '1044',"+
        "					 'name': 'VIJES',"+
        "					 'position': NumberInt(39)"+
        "				},"+
        "				 {"+
        "					 'id': '1045',"+
        "					 'name': 'YOTOCO',"+
        "					 'position': NumberInt(40)"+
        "				},"+
        "				 {"+
        "					 'id': '1046',"+
        "					 'name': 'YUMBO',"+
        "					 'position': NumberInt(41)"+
        "				},"+
        "				 {"+
        "					 'id': '1047',"+
        "					 'name': 'ZARZAL',"+
        "					 'position': NumberInt(42)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(369),"+
        "			 'name': 'Municipio (Vaupes)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(149),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1113',"+
        "					 'name': 'MITÚ',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1114',"+
        "					 'name': 'CARURÚ',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '1115',"+
        "					 'name': 'PACOA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '1116',"+
        "					 'name': 'TARAIRA',"+
        "					 'position': NumberInt(4)"+
        "				},"+
        "				 {"+
        "					 'id': '1117',"+
        "					 'name': 'PAPUNAUA',"+
        "					 'position': NumberInt(5)"+
        "				},"+
        "				 {"+
        "					 'id': '1118',"+
        "					 'name': 'YAVARATÉ',"+
        "					 'position': NumberInt(6)"+
        "				}"+
        "			]"+
        "		},"+
        "		 {"+
        "			 'id': NumberInt(370),"+
        "			 'name': 'Municipio (Vichada)',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': NumberInt(150),"+
        "			 'is_required': false,"+
        "			 'is_filterable': true,"+
        "			 'data_type': NumberInt(10),"+
        "			 'widget': NumberInt(1),"+
        "			 'survey_subform_id': NumberInt(0),"+
        "			 'restrictions': null,"+
        "			 'Subform': null,"+
        "			 'target': {"+
        "				 'target_table': 'farms',"+
        "				 'target_column': 'id_municipipality_far',"+
        "				 'target_column_lng': '',"+
        "				 'target_column_alt': '',"+
        "				 'target_column_self': ''"+
        "			},"+
        "			 'Choices': ["+
        "				 {"+
        "					 'id': '1119',"+
        "					 'name': 'PUERTO CARREÑO',"+
        "					 'position': NumberInt(1)"+
        "				},"+
        "				 {"+
        "					 'id': '1120',"+
        "					 'name': 'LA PRIMAVERA',"+
        "					 'position': NumberInt(2)"+
        "				},"+
        "				 {"+
        "					 'id': '1121',"+
        "					 'name': 'SANTA ROSALÍA',"+
        "					 'position': NumberInt(3)"+
        "				},"+
        "				 {"+
        "					 'id': '1122',"+
        "					 'name': 'CUMARIBO',"+
        "					 'position': NumberInt(4)"+
        "				}"+
        "			]"+
        "		}"+
        "	]"+
        "},"+
        " 'form_id': '3',"+
        " 'inserted_at': '"+dateNow+"',"+
        " 'inserted_by': {"+
        "	 'user_id': '0',"+
        "	 'user_uid': '0',"+
        "	 'user_fullname': 'ANONYMUS',"+
        "	 'ip_address': '190.122.75.2'"+
        "},"+
        " 'user_id': ''"+data.get("userMobileId")+"'";
        
      /*       
        "102": "Nombre de la finca" => nameFarm
        "103": "-30.98664622,-64.10017675,601" Capturar posicion => lat, lng, alt
        "105": "Vereda" => district
        "108": "Indicación (como llegar)" => address
        "241": "Seleccione el productor asociado" => Seleccion (solo dato seleccionado) => producer
        "336": "Departamento" => department
        "338": "Municipio (Amazonas)" => municipality
        "339": "Municipio (Antioquia)"
        "340": "Municipio (Arauca)"
        "341": "Municipio (San Andrés, Providencia, Santa Catalina)"
        "342": "Municipio (Atlántico)"
        "343": "Municipio (Bolívar)"
        "344": "Municipio (Boyaca)"
        "345": "Municipio (Caldas)"
        "346": "Municipio (Caqueta)"
        "347": "Municipio (Casanare)"
        "348": "Municipio (Cauca)"
        "349": "Municipio (Cesar)"
        "350": "Municipio (Choco)"
        "351": "Municipio (Córdoba)"
        "352": "Municipio (Cundinamarca)"
        "353": "Municipio (Guainia)"
        "354": "Municipio (Guaviare)"
        "355": "Municipio (Huila)"
        "356": "Municipio (La Guajira)"
        "357": "Municipio (Magdalena)"
        "358": "Municipio (Meta)"
        "359": "Municipio (Nariño)"
        "360": "Municipio (Norte de Santander)"
        "361": "Municipio (Putumayo)"
        "362": "Municipio (Quindio)"
        "363": "Municipio (Risaralda)"
        "364": "Municipio (Santander)"
        "365": "Municipio (Bogota DC)"
        "366": "Municipio (Sucre)"
        "367": "Municipio (Tolima)"
        "368": "Municipio (Valle del Cauca)"
        "369": "Municipio (Vaupes)"
        "370": "Municipio (Vichada)"    
       */
        
        BasicDBObject info = new BasicDBObject();
		info.put("102", "");
        info.put("103", "");
        info.put("105", "");
        info.put("108", "");
        info.put("241", "");
        info.put("336", "");
        info.put("338", "");
        info.put("339", "");
        info.put("340", "");
        info.put("341", "");
        info.put("342", "");
        info.put("343", "");
        info.put("344", "");
        info.put("345", "");
        info.put("346", "");
        info.put("347", "");
        info.put("348", "");
        info.put("349", "");
        info.put("350", "");
        info.put("351", "");
        info.put("352", "");
        info.put("353", "");
        info.put("354", "");
        info.put("355", "");
        info.put("356", "");
        info.put("357", "");
        info.put("358", "");
        info.put("359", "");
        info.put("360", "");
        info.put("361", "");
        info.put("362", "");
        info.put("363", "");
        info.put("364", "");
        info.put("365", "");
        info.put("366", "");
        info.put("367", "");
        info.put("368", "");
        info.put("369", "");
        info.put("370", "");
        
        /*
        "102": "Nombre de la finca" => nameFarm
        "103": "-30.98664622,-64.10017675,601" Capturar posicion => lat, lng, alt
        "105": "Vereda" => district
        "108": "Indicación (como llegar)" => address
        "241": "Seleccione el productor asociado" => Seleccion (solo dato seleccionado) => prodId
        "336": "Departamento" => department
        "338": "Municipio (Amazonas)" => municipality
        */
        
        String json = json1+json2+json3;
        BasicDBObject dataMain = (BasicDBObject)JSON.parse(json);                
        dataMain.put("InsertedId", data.get("farmId"));         
                
        BasicDBObject insertIds = new BasicDBObject(); 
        insertIds.put("#main", data.get("farmId"));
        insertIds.put("farms_producers", "0");
        dataMain.put("InsertIds", insertIds);
        
        BasicDBObject coords    = new BasicDBObject();    
        coords.put("lat", data.get("lat"));
        coords.put("lng", data.get("lng"));
        coords.put("alt", data.get("alt"));
        dataMain.put("coords", coords);
        
        BasicDBObject dataInfo = new BasicDBObject();
        dataInfo.put("form_id", "3");
        dataInfo.put("isCompleted", "false");
        
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");        
        String dateIn = formatoDelTexto.format(ahora);       
        
        dataInfo.put("created_at", '"'+dateIn+'"');
        dataInfo.put("qid", "102");
        dataInfo.put("del", "false");
        dataInfo.put("id", "_nvMhoyhVymLD");
        
        info.put("102", data.get("nameFarm"));
        info.put("103", data.get("lat")+","+data.get("lng")+","+data.get("alt"));
        info.put("105", data.get("district"));
        info.put("108", data.get("address"));
        info.put("241", data.get("prodId"));
        info.put("336", data.get("department"));
        String valMun = GlobalFunctions.getValuesMunicipalityFarm(String.valueOf(data.get("municipality")));
        info.put(valMun, data.get("municipality"));        
        dataInfo.put("survey_solution", info);
        dataMain.put("data", dataInfo);
        return dataMain;
    }
    
    public static BasicDBObject generateJSONField(HashMap data)
    {
        
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateNow = formateador.format(ahora);
        String json = "'_id': ObjectId('53d64dae9859f7062a54bdd0'),"+
        "'InsertIds': {"+
        " '#main': '590'"+
        "},"+
        "'InsertedId': '590',"+
        "'coords': {"+
        " 'lat': '3.5018000000000002',"+
        " 'lng': '-76.35476666666666',"+
        " 'alt': '744.4'"+
        "},"+        
        "'form': {"+
        " 'id': NumberInt(5),"+
        " 'name': 'Lote',"+
        " 'description': 'Formulario para agregar/editar lotes al sistema',"+
        " 'del': false,"+
        " 'qid': '87',"+
        " 'main_table': 'fields',"+
        " 'HiddenFields': ["+
        "	 {"+
        "		 'id_survey_hidden_field': '10',"+
        "		 'target_table': 'fields',"+
        "		 'target_column': 'pests_control_fie',"+
        "		 'value': '1'"+
        "	},"+
        "	 {"+
        "		 'id_survey_hidden_field': '11',"+
        "		 'target_table': 'fields',"+
        "		 'target_column': 'status',"+
        "		 'value': '1'"+
        "	},"+
        "	 {"+
        "		 'id_survey_hidden_field': '12',"+
        "		 'target_table': 'fields',"+
        "		 'target_column': 'diseases_control_fie',"+
        "		 'value': '1'"+
        "	}"+
        "],"+
        " 'Questions': ["+
        "	 {"+
        "		 'id': NumberInt(85),"+
        "		 'name': 'Finca asociada',"+
        "		 'help': 'Por favor seleccione la finca asociada',"+
        "		 'subject': 'Lote',"+
        "		 'position': NumberInt(5),"+
        "		 'is_required': true,"+
        "		 'is_filterable': true,"+
        "		 'data_type': NumberInt(10),"+
        "		 'widget': NumberInt(1),"+
        "		 'survey_subform_id': NumberInt(0),"+
        "		 'restrictions': null,"+
        "		 'Subform': null,"+
        "		 'target': {"+
        "			 'target_table': 'fields',"+
        "			 'target_column': 'id_farm_fie',"+
        "			 'target_column_lng': '',"+
        "			 'target_column_alt': '',"+
        "			 'target_column_self': ''"+
        "		},"+
        "		 'Choices': ["+
        "			 {"+
        "				 'id': '"+data.get("farmId")+"',"+
        "				 'name': '"+data.get("nameFarm")+"',"+
        "				 'position': NumberInt(1)"+
        "			}"+
        "		]"+
        "	},"+
        "	 {"+
        "		 'id': NumberInt(86),"+
        "		 'name': 'Seleccione el tipo de lote',"+
        "		 'help': 'Seleccione su tipo de lote',"+
        "		 'subject': 'Lote',"+
        "		 'position': NumberInt(6),"+
        "		 'is_required': true,"+
        "		 'is_filterable': false,"+
        "		 'data_type': NumberInt(10),"+
        "		 'widget': NumberInt(1),"+
        "		 'survey_subform_id': NumberInt(0),"+
        "		 'restrictions': null,"+
        "		 'Subform': null,"+
        "		 'target': {"+
        "			 'target_table': 'fields',"+
        "			 'target_column': 'contract_type_fie',"+
        "			 'target_column_lng': '',"+
        "			 'target_column_alt': '',"+
        "			 'target_column_self': ''"+
        "		},"+
        "		 'Choices': ["+
        "			 {"+
        "				 'id': '1',"+
        "				 'name': 'No aplica',"+
        "				 'position': NumberInt(1)"+
        "			},"+
        "			 {"+
        "				 'id': '2',"+
        "				 'name': 'Propio',"+
        "				 'position': NumberInt(2)"+
        "			},"+
        "			 {"+
        "				 'id': '3',"+
        "				 'name': 'Arrendado',"+
        "				 'position': NumberInt(3)"+
        "			},"+
        "			 {"+
        "				 'id': '4',"+
        "				 'name': 'Uso sin contrato',"+
        "				 'position': NumberInt(4)"+
        "			}"+
        "		]"+
        "	},"+
        "	 {"+
        "		 'id': NumberInt(87),"+
        "		 'name': 'Nombre de Lote',"+
        "		 'help': 'Escriba el nombre del lote',"+
        "		 'subject': 'Lote',"+
        "		 'position': NumberInt(7),"+
        "		 'is_required': true,"+
        "		 'is_filterable': true,"+
        "		 'data_type': NumberInt(1),"+
        "		 'widget': NumberInt(6),"+
        "		 'survey_subform_id': NumberInt(0),"+
        "		 'restrictions': {"+
        "			 'minlength': NumberInt(1),"+
        "			 'maxlength': NumberInt(60),"+
        "			 'pattern': ''"+
        "		},"+
        "		 'Subform': null,"+
        "		 'target': {"+
        "			 'target_table': 'fields',"+
        "			 'target_column': 'name_fie',"+
        "			 'target_column_lng': '',"+
        "			 'target_column_alt': '',"+
        "			 'target_column_self': ''"+
        "		},"+
        "		 'Choices': ["+
        "			"+
        "		]"+
        "	},"+
        "	 {"+
        "		 'id': NumberInt(88),"+
        "		 'name': 'Capturar posicion',"+
        "		 'help': '',"+
        "		 'subject': 'Lote',"+
        "		 'position': NumberInt(8),"+
        "		 'is_required': true,"+
        "		 'is_filterable': false,"+
        "		 'data_type': NumberInt(5),"+
        "		 'widget': NumberInt(7),"+
        "		 'survey_subform_id': NumberInt(0),"+
        "		 'restrictions': null,"+
        "		 'Subform': null,"+
        "		 'target': {"+
        "			 'target_table': 'fields',"+
        "			 'target_column': 'latitude_fie',"+
        "			 'target_column_lng': 'longitude_fie',"+
        "			 'target_column_alt': 'altitude_fie',"+
        "			 'target_column_self': ''"+
        "		},"+
        "		 'Choices': ["+
        "			"+
        "		]"+
        "	},"+
        "	 {"+
        "		 'id': NumberInt(89),"+
        "		 'name': 'Área del Lote (ha)',"+
        "		 'help': 'Ingrese aquí el área de su lote en hectáreas',"+
        "		 'subject': 'Lote',"+
        "		 'position': NumberInt(9),"+
        "		 'is_required': false,"+
        "		 'is_filterable': false,"+
        "		 'data_type': NumberInt(8),"+
        "		 'widget': NumberInt(6),"+
        "		 'survey_subform_id': NumberInt(0),"+
        "		 'restrictions': {"+
        "			 'min': 0.001,"+
        "			 'max': NumberInt(400)"+
        "		},"+
        "		 'Subform': null,"+
        "		 'target': {"+
        "			 'target_table': 'fields',"+
        "			 'target_column': 'area_fie',"+
        "			 'target_column_lng': '',"+
        "			 'target_column_alt': '',"+
        "			 'target_column_self': ''"+
        "		},"+
        "		 'Choices': ["+
        "			"+
        "		]"+
        "	}"+
        "]"+
        "},"+
        "'form_id': '5',"+
        "'inserted_at': '"+dateNow+"',"+
        "'inserted_by': {"+
        " 'user_id': '0',"+
        " 'user_uid': '0',"+
        " 'user_fullname': 'ANONYMUS',"+
        " 'ip_address': '181.118.144.200'"+
        "},"+
        "'user_id': '"+data.get("userMobileId")+"'";
        
        BasicDBObject dataMain  = (BasicDBObject)JSON.parse(json);
        dataMain.put("InsertedId", data.get("fieldId"));         
                
        BasicDBObject insertIds = new BasicDBObject(); 
        insertIds.put("#main", data.get("fieldId"));
        dataMain.put("InsertIds", insertIds);
        
        BasicDBObject coords    = new BasicDBObject();    
        coords.put("lat", data.get("lat"));
        coords.put("lng", data.get("lng"));
        coords.put("alt", data.get("alt"));
        dataMain.put("coords", coords);       
        
        BasicDBObject dataInfo = new BasicDBObject();
        dataInfo.put("form_id", "5");
        dataInfo.put("isCompleted", "false");
        
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");        
        String dateIn = formatoDelTexto.format(ahora);       
        
        dataInfo.put("created_at", '"'+dateIn+'"');
        dataInfo.put("qid", "87");
        dataInfo.put("del", "false");
        dataInfo.put("id", "_apNG0sJ0y7BE");
        
        BasicDBObject info = new BasicDBObject();
		info.put("85", "");
        info.put("86", "");
        info.put("87", "");
        info.put("88", "");
        info.put("89", "");
        
        /*
        "85": "Finca asociada" => Seleccion (solo dato seleccionado) => farmId
        "86": "Seleccione el tipo de lote" => Seleccion => typeField
        "87": "Nombre de Lote" => nameField
        "88": "-30.9866133,-64.10017753,596" Capturar posicion => lat, lng, alt
        "89": "Área del Lote" => areaField
        */
        
        //Limite 3100 Lineas
        
        info.put("85", data.get("farmId"));
        info.put("86", data.get("typeField"));
        info.put("87", data.get("nameField"));
        info.put("88", data.get("lat")+","+data.get("lng")+","+data.get("alt"));
        info.put("89", data.get("areaField"));
        dataInfo.put("survey_solution", info);
        dataMain.put("data", dataInfo);
        return dataMain;
    }
   
}


