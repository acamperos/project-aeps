package org.aepscolombia.platform.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;


/**
 * Clase ValidatorUtil
 *
 * Contiene funciones globales de validacion que van a ser empleadas por todos los procesos
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ValidatorUtil {

    private static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Encargado de validar si una direccion correo electronico tiene la estructura de un correo electronico
     * @param email Direccion de correo electronico a validar
     * @return <ul>
     *          <li>true: Caso de exito de verificacion</li>
     *          <li>false: Caso de falla de verificacion</li>
     *         </ul>
     */
    public static boolean validateEmail(String email) {
        try {
            Pattern pattern = Pattern.compile(PATTERN_EMAIL);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }   
    
    public static boolean verifyCaptcha(String remoteAddr, String recaptChallenge, String recaptResponse) {
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
//        reCaptcha.setPublicKey("6Lfh2O0SAAAAANN4PftAGB-KQF26H4qUoyUMH69F");
        reCaptcha.setPrivateKey("66Lfh2O0SAAAAANtqQ1zF9uKjryu-9EZZnlCU_d76");
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, recaptChallenge, recaptResponse);
        return reCaptchaResponse.isValid();
    }
}
