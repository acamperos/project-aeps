package org.aepscolombia.platform.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Properties;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.mail.Authenticator;
import javax.mail.BodyPart;

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

    /**
     * Encargado de enviar un correo al usuario
     *
     * @param toAdress Quien recibe el correo
     * @param fromAdress Quien escribe el correo
     * @param subject Asunto del correo
     * @param subjectMsg Descripcion general del correo
     */
    public static void sendEmail(String toAdress, String fromAdress, String fromAdressPass, String subject, String subjectMsg) {
//        String host = "localhost";
        Properties props = new Properties();
//        props.setProperty("mail.smtp.host", host); 
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
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
     * Añade el contenido base al multipart
     *
     * @param htmlText contenido html que se muestra en el mensaje de correo
     * @throws Exception Excepcion levantada en caso de error
     */
//    public void addContent(String htmlText, MimeMultipart multipart) throws Exception {
//// first part (the html)
//        BodyPart messageBodyPart = new MimeBodyPart();
//        messageBodyPart.setContent(htmlText, "text/html");
//// add it
//        this.multipart.addBodyPart(messageBodyPart);
//    }
// ----

    /**
     * Envia un correo multipart
     *
     * @throws Exception Excepcion levantada en caso de error
     */
//    public void sendMultipart() throws Exception {
////        Session mailSession = Session.getDefaultInstance(this.props, null);
////        mailSession.setDebug(true);
//        Transport transport = mailSession.getTransport();
//        MimeMessage message = new MimeMessage(mailSession);
//        message.setSubject(this.getSubject());
//        message.setFrom(new InternetAddress(this.getFrom()));
//        message.addRecipient(Message.RecipientType.TO,
//                new InternetAddress(this.getTo()));
//// put everything together
//        message.setContent(multipart);
//        transport.connect();
//        transport.sendMessage(message,
//                message.getRecipients(Message.RecipientType.TO));
//        transport.close();
//    }

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

    private static String getSecurePassword(String passwordToHash, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        char[] chars = passwordToHash.toCharArray();
//        byte[] saltR = salt.getBytes();
        byte[] saltR = null;

        PBEKeySpec spec = new PBEKeySpec(chars, saltR, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
//        return iterations + ":" + toHex(saltR) + ":" + toHex(hash);
        return iterations + ":" + toHex(hash);
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    public static String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }

    public static boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] parts = storedPassword.split(":");
        int iterations = Integer.parseInt(parts[0]);
//        byte[] salt = fromHex(parts[1]);
//        byte[] hash = fromHex(parts[2]);
        byte[] salt = null;
        byte[] hash = fromHex(parts[1]);

        PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = skf.generateSecret(spec).getEncoded();

        int diff = hash.length ^ testHash.length;
        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    private static byte[] fromHex(String hex) throws NoSuchAlgorithmException {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    /**
     * Encargado de generar la descripcion del correo en formato HTML, que va a
     * ser al usuario cuando este se crea por primera vez
     *
     * @param nameUser Nombre del usuario registrado en el sistema
     * @param codValidation Codigo de validacion generado por el sistema para
     * verificar un nuevo usuario
     * @return representacion del mensaje en HTML
     */
    public static String messageToNewUser(String nameUser, String codValidation) {
        String msg = "<html> \n"
//                + "<head> \n"
//                + "<title>Validación de usuario registrado</title> \n"
//                + "</head> \n"
                + "<body> \n"
                + "<h3>Hola Usuario: " + nameUser + "</h3> \n"
                + "<p>Bienvenido a la plataforma AEPS.</p> \n"
                + "<p>Para validar su registro por favor dar click en el siguiente enlace:</p> "
//                + "<a href='http://localhost:8083/aeps-plataforma-mvn/verifyUser.action?codVal=" + codValidation + "&nameUser=" + nameUser + "'> \n"
                + "http://localhost:8083/aeps-plataforma-mvn/verifyUser.action?codVal=" + codValidation + "&nameUser=" + nameUser + " \n"
                + "</body> \n"
                + "</html>";
        return msg;
    }
    
    /**
     * Encargado de generar la descripcion del correo en formato HTML, que va a
     * ser al usuario cuando se va a recuperar una contraseña
     *
     * @param nameUser Nombre del usuario registrado en el sistema
     * @param codValidation Codigo de validacion generado por el sistema para
     * restaurar un usuario
     * @return representacion del mensaje en HTML
     */
    public static String messageToRestoreUser(String nameUser, String codValidation) {
        String msg = "<html> \n"
//                + "<head> \n"
//                + "<title>Validación de usuario registrado</title> \n"
//                + "</head> \n"
                + "<body> \n"
                + "<h3>Hola Usuario: " + nameUser + "</h3> \n"
                + "<p>Para poder realizar el cambio de contraseña por favor dar click en el siguiente enlace:</p> "
                + "http://localhost:8083/aeps-plataforma-mvn/verifyUserToRestore.action?codVal=" + codValidation + "&nameUser=" + nameUser + " \n"
                + "</body> \n"
                + "</html>";
        return msg;
    }
}
