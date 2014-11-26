package org.aepscolombia.platform.util;

import com.mongodb.BasicDBList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.aepscolombia.platform.controllers.ActionField;
import org.aepscolombia.platform.models.dao.ProductionEventsDao;
import org.hibernate.HibernateException;

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
        } catch (java.text.ParseException ex) {
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
        } catch (java.text.ParseException ex) {
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
        valuesForm.put("10","276");
        valuesForm.put("6","270");
        valuesForm.put("7","271");
        valuesForm.put("26","272");
        valuesForm.put("8","273");
        valuesForm.put("9","274");
        valuesForm.put("12","275");
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
        valuesForm.put("3","365");
        valuesForm.put("4","343");
        valuesForm.put("5","344");
        valuesForm.put("10","351");
        valuesForm.put("6","345");
        valuesForm.put("7","346");
        valuesForm.put("26","347");
        valuesForm.put("8","348");
        valuesForm.put("9","349");
        valuesForm.put("12","350");
        valuesForm.put("11","352");
        valuesForm.put("30","353");
        valuesForm.put("31","354");
        valuesForm.put("13","355");
        valuesForm.put("14","356");
        valuesForm.put("15","357");
        valuesForm.put("16","358");
        valuesForm.put("17","359");
        valuesForm.put("18","360");
        valuesForm.put("27","361");
        valuesForm.put("19","362");
        valuesForm.put("20","363");
        valuesForm.put("21","364");
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
        
        JSONObject obj = null;
        
        try {
            JSONParser parser = new JSONParser();
            obj = (JSONObject) parser.parse(new FileReader("/var/www/document/producerStructure.json"));
//            obj = (JSONObject) parser.parse(new FileReader("C:/producerStructure.json"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        

        BasicDBObject dataMain    = (BasicDBObject)JSON.parse(obj.toString());    
//        BasicDBObject dataForm    = (BasicDBObject) dataMain.get("form");    
//        BasicDBList dataQuestions = (BasicDBList) dataForm.get("Questions");    
//        BasicDBObject dataOther   = (BasicDBObject)JSON.parse(valAdd);
//        dataQuestions.add(dataOther);
        
//        dataForm.put("Questions", dataQuestions);
        
        dataMain.put("InsertedId", ""+data.get("entId"));
        dataMain.put("form_id", "4");
        dataMain.put("inserted_at", ""+dateNow);
        dataMain.put("user_id", ""+data.get("userMobileId"));
//        dataMain.put("form", dataForm);
        
                
        BasicDBObject insertIds = new BasicDBObject(); 
        insertIds.put("#main", ""+data.get("entId"));
        insertIds.put("producers", ""+data.get("prodId"));
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
        
        dataInfo.put("created_at", ""+dateIn);
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
        
        info.put("90", ""+data.get("docType"));
        info.put("91", ""+data.get("docNum"));
        info.put("92", ""+data.get("firstName1"));
        info.put("93", ""+data.get("firstName2"));
        info.put("94", ""+data.get("lastName1"));
        info.put("95", ""+data.get("lastName2"));
        info.put("96", ""+data.get("direction"));
        info.put("99", ""+data.get("phone"));
        info.put("251", ""+data.get("cellphone"));
        info.put("252", ""+data.get("email"));
        info.put("260", ""+data.get("validation"));
        info.put("261", ""+data.get("department"));
        
        String valMun = GlobalFunctions.getValuesMunicipality(String.valueOf(data.get("department")));
        info.put(valMun, data.get("municipality"));        
        dataInfo.put("survey_solution", info);
        dataMain.put("data", dataInfo);
//        System.out.println("json=>"+dataMain.toString());        
        return dataMain;
    }
  
    
    public static BasicDBObject generateJSONFarm(HashMap data)
    {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateNow = formateador.format(ahora);
        String valAdd  =  "{"+
        "			 'id': 'NumberInt(241)',"+
        "			 'name': 'Seleccione el productor asociado',"+
        "			 'help': '',"+
        "			 'subject': 'Finca',"+
        "			 'position': 'NumberInt(1)',"+
        "			 'is_required': true,"+
        "			 'is_filterable': false,"+
        "			 'data_type': 'NumberInt(10)',"+
        "			 'widget': 'NumberInt(1)',"+
        "			 'survey_subform_id': 'NumberInt(0)',"+
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
        "					 'position': 'NumberInt(1)'"+
        "				}"+
        "			]"+
        "		}";
        
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
        
        JSONObject obj = null;
        
        try {
            JSONParser parser = new JSONParser();
            obj = (JSONObject) parser.parse(new FileReader("/var/www/document/farmStructure.json"));
//            obj = (JSONObject) parser.parse(new FileReader("C:/farmStructure.json"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        
        BasicDBObject dataMain    = (BasicDBObject)JSON.parse(obj.toString());    
        BasicDBObject dataForm    = (BasicDBObject) dataMain.get("form");    
        BasicDBList dataQuestions = (BasicDBList) dataForm.get("Questions");    
        BasicDBObject dataOther   = (BasicDBObject)JSON.parse(valAdd);
        dataQuestions.add(dataOther);
        
        dataForm.put("Questions", dataQuestions);
        
        dataMain.put("InsertedId", ""+data.get("farmId"));
        dataMain.put("form_id", "3");
        dataMain.put("inserted_at", ""+dateNow);
        dataMain.put("user_id", ""+data.get("userMobileId"));
        dataMain.put("form", dataForm);  
                
        BasicDBObject insertIds = new BasicDBObject(); 
        insertIds.put("#main", ""+data.get("farmId"));
        insertIds.put("farms_producers", "0");
        dataMain.put("InsertIds", insertIds);
        
        BasicDBObject coords    = new BasicDBObject();    
        coords.put("lat", ""+data.get("lat"));
        coords.put("lng", ""+data.get("lng"));
        coords.put("alt", ""+data.get("alt"));
        dataMain.put("coords", coords);
        
        BasicDBObject dataInfo = new BasicDBObject();
        dataInfo.put("form_id", "3");
        dataInfo.put("isCompleted", "false");
        
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");        
        String dateIn = formatoDelTexto.format(ahora);       
        
        dataInfo.put("created_at", ""+dateIn);
        dataInfo.put("qid", "102");
        dataInfo.put("del", "false");
        dataInfo.put("id", "_nvMhoyhVymLD");
        
        info.put("102", ""+data.get("nameFarm"));
        info.put("103", data.get("lat")+","+data.get("lng")+","+data.get("alt"));
        info.put("108", ""+data.get("district"));
        info.put("105", ""+data.get("address"));
        info.put("241", ""+data.get("prodId"));
        info.put("336", ""+data.get("department"));
        String valMun = GlobalFunctions.getValuesMunicipalityFarm(String.valueOf(data.get("department")));
        info.put(valMun, ""+data.get("municipality"));        
        dataInfo.put("survey_solution", info);
        dataMain.put("data", dataInfo);
        return dataMain;
    }
    
    public static BasicDBObject generateJSONField(HashMap data)
    {        
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateNow = formateador.format(ahora);
        String valAdd  = "	 {"+
        "		 'id': 'NumberInt(85)',"+
        "		 'name': 'Finca asociada',"+
        "		 'help': 'Por favor seleccione la finca asociada',"+
        "		 'subject': 'Lote',"+
        "		 'position': 'NumberInt(5)',"+
        "		 'is_required': true,"+
        "		 'is_filterable': true,"+
        "		 'data_type': 'NumberInt(10)',"+
        "		 'widget': 'NumberInt(1)',"+
        "		 'survey_subform_id': 'NumberInt(0)',"+
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
        "				 'position': 'NumberInt(1)'"+
        "			}"+
        "		]"+
        "	}";       
        
        JSONObject obj = null;
        
        try {
            JSONParser parser = new JSONParser();
            obj = (JSONObject) parser.parse(new FileReader("/var/www/document/fieldStructure.json"));
//            obj = (JSONObject) parser.parse(new FileReader("C:/fieldStructure.json"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        
        BasicDBObject dataMain    = (BasicDBObject)JSON.parse(obj.toString());    
        BasicDBObject dataForm    = (BasicDBObject) dataMain.get("form");    
        BasicDBList dataQuestions = (BasicDBList) dataForm.get("Questions");    
        BasicDBObject dataOther   = (BasicDBObject)JSON.parse(valAdd);
        dataQuestions.add(dataOther);
        
        dataForm.put("Questions", dataQuestions);
        
        dataMain.put("InsertedId", ""+data.get("fieldId"));
        dataMain.put("form_id", "5");
        dataMain.put("inserted_at", ""+dateNow);
        dataMain.put("user_id", ""+data.get("userMobileId"));
        dataMain.put("form", dataForm);               
                
        BasicDBObject insertIds = new BasicDBObject(); 
        insertIds.put("#main", ""+data.get("fieldId"));
        dataMain.put("InsertIds", insertIds);
        
        BasicDBObject coords    = new BasicDBObject();    
        coords.put("lat", ""+data.get("lat"));
        coords.put("lng", ""+data.get("lng"));
        coords.put("alt", ""+data.get("alt"));
        dataMain.put("coords", coords);       
        
        BasicDBObject dataInfo = new BasicDBObject();
        dataInfo.put("form_id", "5");
        dataInfo.put("isCompleted", "false");
        
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");        
        String dateIn = formatoDelTexto.format(ahora);       
        
        dataInfo.put("created_at", ""+dateIn);
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
        
        info.put("85", ""+data.get("farmId"));
        info.put("86", ""+data.get("typeField"));
        info.put("87", ""+data.get("nameField"));
        info.put("88", data.get("lat")+","+data.get("lng")+","+data.get("alt"));
        info.put("89", ""+data.get("areaField"));
        dataInfo.put("survey_solution", info);
        dataMain.put("data", dataInfo);
        return dataMain;
    }
    
    public static BasicDBObject generateJSONSoil(HashMap data)
    {        
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateNow = formateador.format(ahora);
        String valAdd  = 
        "{"+
        "	'id': 'NumberInt(247)',"+
        "	'name': 'Seleccion el lote asociado',"+
        "	'help': '',"+
        "	'subject': 'Caracteristicas y Observaciones',"+
        "	'position': 'NumberInt(1)',"+
        "	'is_required': true,"+
        "	'is_filterable': false,"+
        "	'data_type': 'NumberInt(10)',"+
        "	'widget': 'NumberInt(1)',"+
        "	'survey_subform_id': 'NumberInt(0)',"+
        "	'restrictions': null,"+
        "	'Subform': null,"+
        "	'target': {"+
        "			'target_table': 'rastas',"+
        "			'target_column': 'id_lote_ras',"+
        "			'target_column_lng': '',"+
        "			'target_column_alt': '',"+
        "			'target_column_self': ''"+
        "	},"+
        "	'Choices': ["+
        "			{"+
        "					'id': '"+data.get("fieldId")+"',"+
        "					'name': '"+data.get("nameField")+"',"+
        "					'position': 'NumberInt(1)'"+
        "			}"+
        "	]"+
        "}";     
        
        JSONObject obj = null;
        
        try {
            JSONParser parser = new JSONParser();
            obj = (JSONObject) parser.parse(new FileReader("/var/www/document/soilStructure.json"));
//            obj = (JSONObject) parser.parse(new FileReader("C:/soilStructure.json"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        
        HashMap terreno = new HashMap();            
        terreno.put("plano o llano", "95");
        terreno.put("ondulado", "96");
        terreno.put("montañoso", "97");
        terreno.put("ondulado y montañoso", "98");
                
        HashMap posicion = new HashMap();
        posicion.put("meseta","99");
        posicion.put("cima","100");
        posicion.put("ladera convexa","101");
        posicion.put("ladera cóncava","102");
        posicion.put("ladera plana","103");
        posicion.put("plano","104");
        posicion.put("plano con ondulaciones","105");
        posicion.put("pie de una elevación","106");
        
        HashMap carbonatos = new HashMap();
        carbonatos.put("no tiene","125");
        carbonatos.put("bajos a muy bajos","126");
        carbonatos.put("medios","127");
        carbonatos.put("altos","128");
        
        HashMap pedreRocas = new HashMap();
        pedreRocas.put("sin rocas","129");
        pedreRocas.put("rocoso","130");
        pedreRocas.put("muy rocoso","131");
        
        HashMap pedrePiedra = new HashMap();
        pedrePiedra.put("sin piedras","132");
        pedrePiedra.put("pedregoso","133");
        pedrePiedra.put("muy pedregoso","134");
        
        HashMap pedrePerfilRocas = new HashMap();
        pedrePerfilRocas.put("sin rocas","135");
        pedrePerfilRocas.put("rocoso","136");
        pedrePerfilRocas.put("muy rocoso","137");
        
        HashMap pedrePerfilPiedra = new HashMap();
        pedrePerfilPiedra.put("sin piedras","138");
        pedrePerfilPiedra.put("pedregoso","139");
        pedrePerfilPiedra.put("muy pedregoso","140");
        
        HashMap decision = new HashMap();
        decision.put("true","1");
        decision.put("false","0");
                        
        HashMap estructura = new HashMap();
        estructura.put("granular","149");
        estructura.put("aterronada","150");
        estructura.put("prismática","151");
        estructura.put("columnar","152");
        estructura.put("laminar","153");
        estructura.put("suelta o polvosa","154");
        estructura.put("masiva","155");
        estructura.put("sin estructura","250");
        
        HashMap costrasDuras = new HashMap();
        costrasDuras.put("muy marcadas","160");
        costrasDuras.put("poco marcadas","161");
        costrasDuras.put("no hay","162");
        
        HashMap expSol = new HashMap();
        expSol.put("la mañana y la tarde","163");
        expSol.put("la mañana","164");
        expSol.put("la tarde","165");
        
        HashMap costrasBlancas = new HashMap();
        costrasBlancas.put("muy marcadas","166");
        costrasBlancas.put("poco marcadas","167");
        costrasBlancas.put("no hay","168");
        
        HashMap costrasNegras = new HashMap();
        costrasNegras.put("muy marcadas","169");
        costrasNegras.put("poco marcadas","170");
        costrasNegras.put("no hay","171");
        
        HashMap crecimiento = new HashMap();
        crecimiento.put("poco afectadas","176");
        crecimiento.put("muy afectadas","177");
        crecimiento.put("plantas normales","178");
        crecimiento.put("no hay cultivo","179");
        
        HashMap recubrimiento = new HashMap();
        recubrimiento.put("muy bueno","188");
        recubrimiento.put("bueno","189");
        recubrimiento.put("regular","190");
        recubrimiento.put("espaciado","191");
        recubrimiento.put("sin cobertura","192");
        
        BasicDBObject dataMain    = (BasicDBObject)JSON.parse(obj.toString());    
        BasicDBObject dataForm    = (BasicDBObject) dataMain.get("form");    
        BasicDBList dataQuestions = (BasicDBList) dataForm.get("Questions");    
        BasicDBObject dataOther   = (BasicDBObject)JSON.parse(valAdd);
        dataQuestions.add(dataOther);
        
        dataForm.put("Questions", dataQuestions);
        
        dataMain.put("InsertedId", ""+data.get("rastaId"));
        dataMain.put("form_id", "6");
        dataMain.put("inserted_at", ""+dateNow);
        dataMain.put("user_id", ""+data.get("userMobileId"));
        dataMain.put("form", dataForm);               
                
        BasicDBObject insertIds = new BasicDBObject(); 
        insertIds.put("#main", ""+data.get("rastaId"));
        dataMain.put("InsertIds", insertIds);
        
        BasicDBObject coords    = new BasicDBObject();    
        coords.put("lat", ""+data.get("lat"));
        coords.put("lng", ""+data.get("lng"));
        coords.put("alt", ""+data.get("alt"));
        dataMain.put("coords", coords);       
        
        BasicDBObject dataInfo = new BasicDBObject();
        dataInfo.put("form_id", "6");
        dataInfo.put("isCompleted", "false");
        
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");        
        String dateIn = formatoDelTexto.format(ahora);       
        
        dataInfo.put("created_at", ""+dateIn);
        dataInfo.put("qid", "109");
        dataInfo.put("del", "true");
        dataInfo.put("id", ""+data.get("rastaId"));
        
        BasicDBObject info = new BasicDBObject();
        info.put("109", "");
        info.put("112", "");
        info.put("113", "");
        info.put("114", "");
        info.put("115", "");
        info.put("121", "");
        info.put("122", "");
        info.put("123", "");
        info.put("124", "");
        info.put("125", "");
        info.put("126", "");
        info.put("127", "");
        info.put("128", "");
        info.put("129", "");
        info.put("130", "");
        info.put("131", "");
        info.put("132", "");
        info.put("133", "");
        info.put("134", "");
        info.put("135", "");
        info.put("136", "");
        info.put("137", "");
        info.put("138", "");
        info.put("139", "");
        info.put("140", "");
        info.put("141", "");
        info.put("142", "");
        info.put("143", "");
        info.put("144", "");
        info.put("145", "");
        info.put("146", "");
        info.put("147", "");
        info.put("148", "");
        info.put("149", "");
        info.put("150", "");
        info.put("151", "");
        info.put("152", "");
        info.put("153", "");
        info.put("247", "");
        info.put("110", "");        
        
        /*
        "109": "Fecha del Rasta" => dateRasta
        "110": "Coordenadas" Capturar posicion => lat, lng, alt
        "112": "Pendiente" => pend
        "113": "Terreno circundante" => terreno
        "114": "Posicion del perfil" => position
        "115": "Horizontes [[\"survey_solution[242]\":"Numero" => number,\"survey_solution[116]\":"Espesor (cm)" => espesor,\"survey_solution[117]\":"Color seco" => colSeco,\"survey_solution[118]\":"Color humedo" => colHum,\"survey_solution[119]\":"Textura" => textura,\"survey_solution[120]\":"Resistencia al rompimiento" => resistencia,\"subform_id\":\"30\",\"idx\":1]]"
        "121": "Caracteristicas y Observaciones" => caract
        "122": "Carbonatos" => carbonato
        "123": "Profundidad Carbonatos (cm)" => profCar
        "124": "Pedregosidad superficial rocas" => pedrSupRo
        "125": "Pedregosidad superficial piedras" => pedrSupPie
        "126": "Pedregosidad en el Perfil rocas" => pedrPerRo
        "127": "Pedregosidad en el Perfil piedras" => pedrPerPie
        "128": "Horizonte pedregoso o rocoso" => horPed
        "129": "Profundidad de horizonte pedregoso o rocoso (cm)" => profHorPedr
        "130": "Espesor de horizonte pedregoso o rocoso (cm)" => espHor
        "131": "Profundidad de primeras rocas o piedras (cm)" => profPri
        "132": "Capas endurecidas" => capasEnd
        "133": "Profundidad de capas endurecidas (cm)" => profCap
        "134": "Espesor de capas endurecidas (cm)" => espCap
        "135": "Moteados" => moteado
        "136": "Profundidad de moteados (cm)" => profMot
        "137": "Moteados mas bajo de 70cm" => moteadoBajo
        "138": "Estructura del suelo" => estSuelo
        "139": "Erosion" => erosion
        "140": "Moho" => moho
        "141": "Costras duras" => cosDur
        "142": "Sitio expuesto a sol" => sitioSol
        "143": "Costras blancas" => cosBlan
        "144": "Costras negras" => cosNeg
        "145": "Region seca" => regSeca
        "146": "Raices vivas" => raiViva
        "147": "Profundidad de raices vivas (cm)" => profRai
        "148": "Crecimiento de las plantas afectadas" => crecPlan
        "149": "Mucha hojarasca" => muchaHoja
        "150": "Suelo es muy negro, muy blando" => sueNegro
        "151": "Cuchillo entra sin ningún esfuerzo" => cuchilloEnt
        "152": "Cerca de agua subterránea muy superficial" => cercaAgua
        "153": "Recubrimiento vegetal del suelo" => recVeg
        "247": "Seleccion el lote asociado" => Seleccion => fieldId
        */
                
        info.put("109", ""+data.get("dateRasta"));
        info.put("112", ""+data.get("pend"));
        info.put("113", ""+terreno.get(data.get("terreno")));
        info.put("114", ""+posicion.get(data.get("position")));
        info.put("115", ""+data.get("valHorizons"));
        info.put("121", ""+data.get("pH"));
        info.put("122", ""+carbonatos.get(data.get("carbonato")));
        info.put("123", ""+data.get("profCar"));
        info.put("124", ""+pedreRocas.get(""+data.get("pedrSupRo")));
        info.put("125", ""+pedrePiedra.get(""+data.get("pedrSupPie")));
        info.put("126", ""+pedrePerfilRocas.get(""+data.get("pedrPerRo")));
        info.put("127", ""+pedrePerfilPiedra.get(""+data.get("pedrPerPie")));
        info.put("128", ""+decision.get(""+data.get("horPed")));
        info.put("129", ""+data.get("profHorPedr"));
        info.put("130", ""+data.get("espHor"));
        info.put("131", ""+data.get("profPri"));
        info.put("132", ""+decision.get(""+data.get("capasEnd")));
        info.put("133", ""+data.get("profCap"));
        info.put("134", ""+data.get("espCap"));
        info.put("135", ""+decision.get(""+data.get("moteado")));
        info.put("136", ""+data.get("profMot"));
        info.put("137", ""+data.get("moteadoBajo"));
        info.put("138", ""+estructura.get(data.get("estSuelo")));
        info.put("139", ""+decision.get(""+data.get("erosion")));
        info.put("140", ""+decision.get(""+data.get("moho")));
        info.put("141", ""+costrasDuras.get(data.get("cosDur")));
        info.put("142", ""+expSol.get(data.get("sitioSol")));
        info.put("143", ""+costrasBlancas.get(data.get("cosBlan")));
        info.put("144", ""+costrasNegras.get(data.get("cosNeg")));
        info.put("145", ""+decision.get(""+data.get("regSeca")));
        info.put("146", ""+decision.get(""+data.get("raiViva")));
        info.put("147", ""+data.get("profRai"));
        info.put("148", ""+crecimiento.get(data.get("crecPlan")));
        info.put("149", ""+decision.get(""+data.get("muchaHoja")));
        info.put("150", ""+decision.get(""+data.get("sueNegro")));
        info.put("151", ""+decision.get(""+data.get("cuchilloEnt")));
        info.put("152", ""+decision.get(""+data.get("cercaAgua")));
        info.put("153", ""+recubrimiento.get(data.get("recVeg")));
        info.put("247", ""+data.get("fieldId"));
        info.put("110", data.get("lat")+","+data.get("lng")+","+data.get("alt"));
        dataInfo.put("survey_solution", info);
        dataMain.put("data", dataInfo);
        return dataMain;
    }
    
    public static void sendInformationCrop(Integer idCrop, Integer typeCrop, long idUserMobile) throws HibernateException
    {
        ProductionEventsDao cropDao  = new ProductionEventsDao();
        HashMap valInfo = cropDao.getDataCropById(idCrop);            
        valInfo.put("userMobileId", idUserMobile);
        valInfo.put("idCrop", ""+idCrop);

        BasicDBObject query = new BasicDBObject();
        query.put("InsertedId", ""+idCrop);        
        if (typeCrop==1) {
            query.put("form_id", "16");
        } else if (typeCrop==2) {
            query.put("form_id", "39");
        }            

        MongoClient mongo = null;
        try {
            mongo = new MongoClient("localhost", 27017);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ActionField.class.getName()).log(Level.SEVERE, null, ex);
        }
        DB db = mongo.getDB("ciat");
        DBCollection col = db.getCollection("log_form_records");

        DBCursor cursor    = col.find(query);
        WriteResult result = null;
        BasicDBObject jsonField = null;
        if (typeCrop==1) {
            jsonField = GlobalFunctions.generateJSONCrop(valInfo);
        } else if (typeCrop==2) {
            jsonField = GlobalFunctions.generateJSONCropBeans(valInfo);
        }

        if (cursor.count()>0) {
            System.out.println("actualizo mongo");
            result = col.update(query, jsonField);
        } else {
            System.out.println("inserto mongo");
            result = col.insert(jsonField);
        }

        if (result.getError()!=null) {
            throw new HibernateException("");
        }            
        mongo.close();
    } 
    
    public static BasicDBObject generateJSONCrop(HashMap data)
    {        
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateNow = formateador.format(ahora);
        String valAdd  = 
        "{"+
        "	'id': 'NumberInt(240)',"+
        "	'name': 'Seleccion el lote asociado',"+
        "	'help': 'Nombre del lote en el cual se realizara el cultivo',"+
        "	'subject': 'Establecimiento del cultivo',"+
        "	'position': 'NumberInt(1)',"+
        "	'is_required': true,"+
        "	'is_filterable': false,"+
        "	'data_type': 'NumberInt(10)',"+
        "	'widget': 'NumberInt(1)',"+
        "	'survey_subform_id': 'NumberInt(0)',"+
        "	'restrictions': null,"+
        "	'Subform': null,"+
        "	'target': {"+
        "			'target_table': 'production_events',"+
        "			'target_column': 'id_field_pro_eve',"+
        "			'target_column_lng': '',"+
        "			'target_column_alt': '',"+
        "			'target_column_self': ''"+
        "	},"+
        "	'Choices': ["+
        "			{"+
        "					'id': '"+data.get("fieldId")+"',"+
        "					'name': '"+data.get("nameField")+"',"+
        "					'position': 'NumberInt(1)'"+
        "			}"+
        "	]"+
        "}";     
        
        JSONObject obj = null;
        
        try {
            JSONParser parser = new JSONParser();
            obj = (JSONObject) parser.parse(new FileReader("/var/www/document/cropStructure.json"));
//            obj = (JSONObject) parser.parse(new FileReader("C:/cropStructure.json"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }       
        
        HashMap decision = new HashMap();
        decision.put("true","1");
        decision.put("false","0");
                        
        BasicDBObject dataMain    = (BasicDBObject)JSON.parse(obj.toString());    
        BasicDBObject dataForm    = (BasicDBObject) dataMain.get("form");    
        BasicDBList dataQuestions = (BasicDBList) dataForm.get("Questions");    
        BasicDBObject dataOther   = (BasicDBObject)JSON.parse(valAdd);
        dataQuestions.add(dataOther);
        
        dataForm.put("Questions", dataQuestions);
        
        dataMain.put("InsertedId", ""+data.get("idCrop"));
        dataMain.put("form_id", "16");
        dataMain.put("inserted_at", ""+dateNow);
        dataMain.put("user_id", ""+data.get("userMobileId"));
        dataMain.put("form", dataForm);               
                
        BasicDBObject insertIds = new BasicDBObject(); 
        insertIds.put("#main", ""+data.get("idCrop"));
        insertIds.put("harvests", ""+data.get("idHar"));
        insertIds.put("sowing", ""+data.get("idSow"));
        insertIds.put("maize", ""+data.get("idMaize"));
        insertIds.put("physiological_monitoring", ""+data.get("idPhys"));
        dataMain.put("InsertIds", insertIds);
        
        BasicDBObject coords    = new BasicDBObject();    
        coords.put("lat", ""+data.get("lat"));
        coords.put("lng", ""+data.get("lng"));
        coords.put("alt", ""+data.get("alt"));
        dataMain.put("coords", coords);       
        
        BasicDBObject dataInfo = new BasicDBObject();
        dataInfo.put("form_id", "16");
        dataInfo.put("isCompleted", "true");
        
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");        
        String dateIn = formatoDelTexto.format(ahora);       
        
        dataInfo.put("created_at", ""+dateIn);
        dataInfo.put("qid", "205");
        dataInfo.put("del", "true");
        dataInfo.put("id", ""+data.get("idCrop"));
        
        BasicDBObject info = new BasicDBObject();
        info.put("191", "");
        info.put("192", "");
        info.put("193", "");
        info.put("194", "");
        info.put("195", "");
        info.put("199", "");
        info.put("205", "");
        info.put("206", "");
        info.put("207", "");
        info.put("208", "");
        info.put("211", "");
        info.put("212", "");
        info.put("213", "");
        info.put("214", "");
        info.put("215", "");
        info.put("220", "");
        info.put("226", "");
        info.put("227", "");
        info.put("230", "");
        info.put("231", "");
        info.put("232", "");
        info.put("233", "");
        info.put("234", "");
        info.put("235", "");
        info.put("240", "");
        info.put("371", "");
        info.put("372", "");
        info.put("373", "");
        info.put("374", "");
        info.put("375", "");
        info.put("379", "");
        info.put("386", "");
        info.put("387", "");
        info.put("388", "");
        info.put("389", "");
        info.put("390", "");
        info.put("394", "");        
        info.put("196", "");        
        
        /*
        "191": "Fecha de cosecha" => "dateHar"
        "192": "Método de cosecha" => "methodHar"
        "193": "Producto esperado" => "expectedProduct"
        "194": "% de Humedad de la cosecha" => "humPer"
        "195": "Rendimiento (kg/ha)" => "yield" 
        "199": "Preparación de la parcela" "[{\"survey_solution[200]\":\"Fecha de trabajo\",\"survey_solution[201]\":\"Tipo de preparación\",\"survey_solution[202]\":\"Otro tipo de preparación\",\"survey_solution[203]\":\"Profundidad de trabajo\",\"survey_solution[378]\":\"Numero de pases\",\"subform_id\":\"46\",\"idx\":1}]",
        "205": "Fecha de siembra" => "dateSow"
        "206": "Método de siembra" => "methodSow"
        "207": "Kilogramos de semilla sembrada X hectárea (kg/ha)" => "seedsNumber"
        "208": "Tipo de material" => "genotyteType"
        "211": "Material genético (Blanco)" => "genotypeWhite"
        "212": "Material genético (Amarillo)" => "genotypeYellow"
        "213": "Color del endospermo" => "grainColor"
        "214": "Semillas tratadas ?" => "treatedSeeds"
        "215": "Fertilización" "[{\"survey_solution[216]\":\"Fecha de aplicación\",\"survey_solution[217]\":\"Tipo de abono\",\"survey_solution[218]\":\"Cantidad aportada (kg/ha)\",\"subform_id\":\"47\",\"idx\":\"1\"}]",
        "220": "Monitoreo del cultivo" "[{\"survey_solution[221]\":\"Fecha del monitoreo\",\"survey_solution[223]\":\"Observo enfermedad ?\",\"survey_solution[228]\":\"Observo maleza ?\",\"survey_solution[229]\":\"Observo plaga ?\",\"subform_id\":\"48\",\"idx\":1}]",
        "226": "Cultivo anterior" => "formerCrop"
        "227": "Se hace drenaje en la parcela" => "drainingPro"
        "230": "Fecha de emergencia" => "emergencePhy"
        "231": "Poblacion a los 20 días" => "twentyDaysPopulation"
        "232": "Fecha de floración" => "floweringDate"
        "233": "Cantidad total (kg)" => "productionHar"
        "234": "Observaciones que afectaron la producción" => "commentHar"
        "235": "Manejo Fitosanitario" "[{\"survey_solution[236]\":\"Fecha del control\",\"survey_solution[237]\":\"Tipo de objetivo\",\"survey_solution[383]\":\"Objetivo del control (Plagas)\",\"survey_solution[384]\":\"Objetivo del control (Malezas)\",\"survey_solution[385]\":\"Objetivo del control (Enfermedades)\",\"survey_solution[239]\":\"Tipo de control\",\"subform_id\":\"49\",\"idx\":1}]",
        "240": "Seleccione el lote asociado" => "idField"
        "371": "Mejor rendimiento obtenido en el lote (kg/ha)" => "expectedProduction"
        "372": "Producto usado" => "usedChemical"
        "373": "Distancia entre surcos (m)" => "furrowsDistance"
        "374": "Distancia entre sitios (m)" => "sitesDistance"
        "375": "Numero de semillas por sitio" => "seedsNumberSite"
        "379": "Manejo de rastrojos" "[{\"survey_solution[380]\":\"Fecha del manejo del rastrojo\",\"survey_solution[381]\":\"Tipo de manejo del rastrojo\",\"survey_solution[382]\":\"Nuevo manejo del rastrojo\",\"subform_id\":\"52\",\"idx\":1}]",
        "386": "Porcentaje de resiembra" => "percentageRes"
        "387": "Numero de bulto (ha)" => "numberSacks"
        "388": "Peso promedio de un bulto (kg/bulto)" => "weightAvg"
        "389": "Peso promedio de la bolsa" => "weightAvgPouch"
        "390": "Riego" "[{\"survey_solution[391]\":\"Fecha del riego\",\"survey_solution[392]\":\"Tipo de riego\",\"survey_solution[393]\":\"Cantidad aportada por hectárea (metros cúbicos)\",\"subform_id\":\"53\",\"idx\":1}]",
        "394": "Observaciones" "[{\"survey_solution[395]\":\"Fecha de observacion\",\"survey_solution[396]\":\"Descripcion de la observacion\",\"subform_id\":\"54\",\"idx\":1}]"
        "196": "Almacenamiento en finca" => "storage"
        */
        
        info.put("191", ""+data.get("dateHar"));
        info.put("192", ""+data.get("methodHar"));
        info.put("193", ""+data.get("expectedProduct"));
        info.put("194", ""+data.get("humPer"));
        info.put("195", ""+data.get("yield" ));
        info.put("199", ""+data.get("preparations"));
        info.put("205", ""+data.get("dateSow"));
        info.put("206", ""+data.get("methodSow"));
        info.put("207", ""+data.get("seedsNumber"));
        info.put("208", ""+data.get("genotyteType"));
        info.put("211", ""+data.get("genotypeWhite"));
        info.put("212", ""+data.get("genotypeYellow"));
        info.put("213", ""+data.get("grainColor"));
        info.put("214", ""+decision.get(""+data.get("treatedSeeds")));
        info.put("215", ""+data.get("fertilizations"));
        info.put("220", ""+data.get("monitorings"));
        info.put("226", ""+data.get("formerCrop"));
        info.put("227", ""+decision.get(""+data.get("drainingPro")));
        info.put("230", ""+data.get("emergencePhy"));
        info.put("231", ""+data.get("twentyDaysPopulation"));
        info.put("232", ""+data.get("floweringDate"));
        info.put("233", ""+data.get("productionHar"));
        info.put("234", ""+data.get("commentHar"));
        info.put("235", ""+data.get("controls"));
        info.put("240", ""+data.get("idField"));
        info.put("371", ""+data.get("expectedProduction"));
        info.put("372", ""+data.get("usedChemical"));
        info.put("373", ""+data.get("furrowsDistance"));
        info.put("374", ""+data.get("sitesDistance"));
        info.put("375", ""+data.get("seedsNumberSite"));
        info.put("379", ""+data.get("residuals"));
        info.put("386", ""+data.get("percentageRes"));
        info.put("387", ""+data.get("numberSacks"));
        info.put("388", ""+data.get("weightAvg"));
        info.put("389", ""+data.get("weightAvgPouch"));
        info.put("390", ""+data.get("irrigations"));
        info.put("394", ""+data.get("observations"));
        info.put("196", ""+decision.get(""+data.get("storage")));
        
        dataInfo.put("survey_solution", info);
        dataMain.put("data", dataInfo);
        return dataMain;
    }
    
    public static BasicDBObject generateJSONCropBeans(HashMap data)
    {        
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateNow = formateador.format(ahora);
        String valAdd  = 
        "{"+
        "	'id': 'NumberInt(257)',"+
        "	'name': 'Seleccion el lote asociado',"+
        "	'help': '',"+
        "	'subject': 'Establecimiento del cultivo',"+
        "	'position': 'NumberInt(1)',"+
        "	'is_required': true,"+
        "	'is_filterable': false,"+
        "	'data_type': 'NumberInt(10)',"+
        "	'widget': 'NumberInt(1)',"+
        "	'survey_subform_id': 'NumberInt(0)',"+
        "	'restrictions': null,"+
        "	'Subform': null,"+
        "	'target': {"+
        "			'target_table': 'production_events',"+
        "			'target_column': 'id_field_pro_eve',"+
        "			'target_column_lng': '',"+
        "			'target_column_alt': '',"+
        "			'target_column_self': ''"+
        "	},"+
        "	'Choices': ["+
        "			{"+
        "					'id': '"+data.get("fieldId")+"',"+
        "					'name': '"+data.get("nameField")+"',"+
        "					'position': 'NumberInt(1)'"+
        "			}"+
        "	]"+
        "}";     
        
        JSONObject obj = null;
        
        try {
            JSONParser parser = new JSONParser();
            obj = (JSONObject) parser.parse(new FileReader("/var/www/document/cropBeanStructure.json"));
//            obj = (JSONObject) parser.parse(new FileReader("C:/cropBeanStructure.json"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }       
        
        HashMap decision = new HashMap();
        decision.put("true","1");
        decision.put("false","0");
                        
        BasicDBObject dataMain    = (BasicDBObject)JSON.parse(obj.toString());    
        BasicDBObject dataForm    = (BasicDBObject) dataMain.get("form");    
        BasicDBList dataQuestions = (BasicDBList) dataForm.get("Questions");    
        BasicDBObject dataOther   = (BasicDBObject)JSON.parse(valAdd);
        dataQuestions.add(dataOther);
        
        dataForm.put("Questions", dataQuestions);
        
        dataMain.put("InsertedId", ""+data.get("idCrop"));
        dataMain.put("form_id", "39");
        dataMain.put("inserted_at", ""+dateNow);
        dataMain.put("user_id", ""+data.get("userMobileId"));
        dataMain.put("form", dataForm);               
                
        BasicDBObject insertIds = new BasicDBObject(); 
        insertIds.put("#main", ""+data.get("idCrop"));
        insertIds.put("harvests", ""+data.get("idHar"));
        insertIds.put("sowing", ""+data.get("idSow"));
        insertIds.put("beans", ""+data.get("idBean"));
        insertIds.put("physiological_monitoring", ""+data.get("idPhys"));
        dataMain.put("InsertIds", insertIds);
        
        BasicDBObject coords    = new BasicDBObject();    
        coords.put("lat", ""+data.get("lat"));
        coords.put("lng", ""+data.get("lng"));
        coords.put("alt", ""+data.get("alt"));
        dataMain.put("coords", coords);       
        
        BasicDBObject dataInfo = new BasicDBObject();
        dataInfo.put("form_id", "39");
        dataInfo.put("isCompleted", "true");
        
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy");        
        String dateIn = formatoDelTexto.format(ahora);       
        
        dataInfo.put("created_at", ""+dateIn);
        dataInfo.put("qid", "410");
        dataInfo.put("del", "true");
        dataInfo.put("id", ""+data.get("idCrop"));
        
        BasicDBObject info = new BasicDBObject();
        info.put("420", "");
        info.put("421", "");
        info.put("422", "");
        info.put("423", "");
        info.put("463", "");
        info.put("465", "");
        info.put("466", "");
        info.put("414", "");
        info.put("257", "");
        info.put("397", "");
        info.put("398", "");
        info.put("403", "");
        info.put("410", "");
        info.put("411", "");
        info.put("412", "");
        info.put("413", "");
        info.put("415", "");
        info.put("416", "");
        info.put("417", "");
        info.put("418", "");
        info.put("419", "");
        info.put("426", "");
        info.put("431", "");
        info.put("435", "");
        info.put("442", "");
        info.put("448", "");
        info.put("452", "");
        info.put("453", "");
        info.put("454", "");
        info.put("455", "");
        info.put("456", "");
        info.put("457", "");
        info.put("458", "");
        info.put("459", "");
        info.put("460", "");
        info.put("461", "");
        info.put("462", "");
        info.put("464", "");       
        
        /*
        "420": "Procedencia" => "origin"
        "421": "Inoculación de semillas" => "seedsIn"
        "422": "Nueva inoculación de semillas" => "otherIn"
        "423": "Hábito de Crecimiento" => "growingEnv"
        "463": "Cargas por hectárea" => "loadHec"
        "465": "Material genético (Arbustivo)" => "genotypeArb"
        "466": "Material genético (Voluble)" => "genotypeVol"
        "414": "Cantidad de semillas en kilos/lote" => "seedsNumber"
        "257": "Seleccione el lote asociado" => "idField"
        "397": "Cultivo anterior" => "formerCrop"
        "398": "Manejo de Rastrojos" => "379"
        "403": "Preparación de la parcela" => "199"
        "410": "Fecha de siembra" => "dateSow"
        "411": "Metodo de siembra" => "methodSow"
        "412": "Mejor rendimiento obtenido en el lote (kg/ha)" => "expectedProduction"
        "413": "Se hace drenaje en la parcela" => "drainingPro"
        "415": "Semillas tratadas ?" => "treatedSeeds"
        "416": "Producto usado" => "usedChemical"
        "417": "Distancia entre surcos (m)" => "furrowsDistance"
        "418": "Distancia entre sitios (m)" => "sitesDistance"
        "419": "Numero de semillas por sitio" => "seedsNumberSite"
        "426": "Riego" => "390"
        "431": "Fertilización" => "215"
        "435": "Manejo Fitosanitario" => "235"
        "442": "Monitoreo del cultivo" => "220"
        "448": "Observaciones" => "394"
        "452": "Fecha de emergencia" => "emergencePhy"
        "453": "Poblacion a los 20 días" => "twentyDaysPopulation"
        "454": "Fecha de floración" => "floweringDate"
        "455": "Porcentaje de resiembra" => "percentageRes"
        "456": "Fecha de cosecha" => "dateHar"
        "457": "Método de cosecha" => "methodHar"
        "458": "Producto cosechado" => "expectedProduct"
        "459": "Rendimiento (kg/ha)" => "yield"
        "460": "Cantidad total (kg)" => "productionHar"
        "461": "% de Humedad de la cosecha" => "humPer"
        "462": "Almacenamiento en finca" => "storage"
        "464": "Observaciones que afectaron la producción" => "commentHar"
        */
        
        info.put("420", ""+data.get("origin"));
        info.put("421", ""+data.get("seedsIn"));
        info.put("422", ""+data.get("otherIn"));
        info.put("423", ""+data.get("growingEnv"));
        info.put("463", ""+data.get("loadHec"));
        info.put("465", ""+data.get("genotypeArb"));
        info.put("466", ""+data.get("genotypeVol"));
        info.put("414", ""+data.get("seedsNumber"));
        info.put("257", ""+data.get("idField"));
        info.put("397", ""+data.get("formerCrop"));
        info.put("398", ""+data.get("residuals"));
        info.put("403", ""+data.get("preparations"));
        info.put("410", ""+data.get("dateSow"));
        info.put("411", ""+data.get("methodSow"));
        info.put("412", ""+data.get("expectedProduction"));
        info.put("413", ""+decision.get(""+data.get("drainingPro")));
        info.put("415", ""+decision.get(""+data.get("treatedSeeds")));
        info.put("416", ""+data.get("usedChemical"));
        info.put("417", ""+data.get("furrowsDistance"));
        info.put("418", ""+data.get("sitesDistance"));
        info.put("419", ""+data.get("seedsNumberSite"));
        info.put("426", ""+data.get("irrigations"));
        info.put("431", ""+data.get("fertilizations"));
        info.put("435", ""+data.get("controls"));
        info.put("442", ""+data.get("monitorings"));
        info.put("448", ""+data.get("observations"));
        info.put("452", ""+data.get("emergencePhy"));
        info.put("453", ""+data.get("twentyDaysPopulation"));
        info.put("454", ""+data.get("floweringDate"));
        info.put("455", ""+data.get("percentageRes"));
        info.put("456", ""+data.get("dateHar"));
        info.put("457", ""+data.get("methodHar"));
        info.put("458", ""+data.get("expectedProduct"));
        info.put("459", ""+data.get("yield"));
        info.put("460", ""+data.get("productionHar"));
        info.put("461", ""+data.get("humPer"));
        info.put("462", ""+decision.get(""+data.get("storage")));
        info.put("464", ""+data.get("commentHar"));        
        dataInfo.put("survey_solution", info);
        dataMain.put("data", dataInfo);
        return dataMain;
    }
   
}


