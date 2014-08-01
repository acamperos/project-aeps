/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.controllers;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import javax.script.*;
import org.renjin.sexp.ListVector;


/**
 * Clase ActionContact
 *
 * Contiene los metodos necesarios para enviar una solicitud por correo
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionContact extends BaseAction {
    
    public ActionContact() {
//        super();
    }

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
    
    public String getReport() {        
        ScriptEngineManager manager = new ScriptEngineManager();
        // create a Renjin engine:
        ScriptEngine engine = manager.getEngineByName("Renjin");
        // check if the engine has loaded correctly:
               
        if(engine == null) {
            throw new RuntimeException("Renjin Script Engine not found on the classpath.");
        }
        try {
//                engine.eval("df <- data.frame(x=1:10, y=(1:10)+rnorm(n=10))");
//                engine.eval("print(df)");
//                engine.eval("print(lm(y ~ x, df))");
//                engine.eval("print('Hello, World')");
            engine.eval("vec <- data.frame(971,1057,27,\"ONDULADO\",\"LADERA CONVEXA\",2,\"18,52\",\"3,35\",\"ND,ND\",\"FL,FAr\",\"BLANDO,DURO\",4.5,\n" +
                    "\"NO TIENE\",NA,\"SIN PIEDRAS\",\"SIN ROCAS\",\"SIN PIEDRAS\",\"SIN ROCAS\",\"NO\",NA,NA,NA,\"SI\",26,30,\"NO\",NA,\n" +
                    "\"NO\",\"GRANULAR\",\"NO\",\"NO\",\"NO HAY\",\"LA MANANA Y LA TARDE\",\"NO HAY\",\"NO HAY\",\"NO\",\"SI\",22,\n" +
                    "\"PLANTAS NORMALES\",\"NO\",\"NO\",\"SI\",\"NO\",\"BUENO\")");
//                engine.eval("load(\"funciones_AESCE.RData\")");
            ListVector res = (ListVector)engine.eval(new java.io.FileReader("inferidas.R"));
            System.out.println("The result of a*b is: " + res);
            // determine the Java class of the result:
//                Class objectType = res.getClass();
//                objectType.
            String depthEffective  = res.getElementAsString(0);
            String organicMaterial = res.getElementAsString(1);
            String internalDrain   = res.getElementAsString(2);
            String externalDrain   = res.getElementAsString(3);
            Integer error = null;
            
            if (depthEffective.equals("Error") || depthEffective.equals("Error.nd") || depthEffective.equals("ERROR.ND") || depthEffective.equals("NO CLASIFICADO")) {
                error = 1;
            }
            
            String[] infoMaterials = organicMaterial.split(",");
            for (int i = 0; i < infoMaterials.length; i++) {
                String temp = infoMaterials[i];
                if (temp.equals("EE.ND") || internalDrain.equals("NO CLASIFICADA")) {
                    error = 2;
                }
            }            
            
            if (internalDrain.equals("ERROR.ND") || internalDrain.equals("NO CLASIFICADO estruc") || internalDrain.equals("NO CLASIFICADO bueno") || internalDrain.equals("NO CLASIFICADO excesivo")) {
                error = 3;
            }
            
            if (externalDrain.equals("ERROR.ND") || externalDrain.equals("NO CLASIFICADO estruc") || externalDrain.equals("NO CLASIFICADO bueno") || externalDrain.equals("NO CLASIFICADO excesivo")) {
                error = 4;
            }
            
            if (error!=null) {
                if (error==1)
                    addActionError("Se obtuvo un error al momento de obtener la profundidad efectiva");
                if (error==2)
                    addActionError("Se obtuvo un error al momento de obtener la materia organica");
                if (error==3)
                    addActionError("Se obtuvo un error al momento de obtener el drenaje interno");
                if (error==4)
                    addActionError("Se obtuvo un error al momento de obtener el drenaje externo");
            }
            
            
            System.out.println("Java class of 'res' is: " + res.getElementAsString(1));
            // use the getTypeName() method of the SEXP object to get R's type name:
//                System.out.println("In R, typeof(res) would give '" + res.getTypeName() + "'");
//                System.out.println("In R, typeof(res) would give '" + objectType.getEnumConstants()+ "'");          
//                engine.eval("library(RMySQL)");
//                engine.eval("canal <- odbcConnect('R-MYSQL', uid = 'root')");
//                engine.eval("prueba <- sqlQuery(canal, 'SELECT * FROM entities')");
//                engine.eval("head(prueba)");
        } catch (ScriptException ex) {
            System.out.println("Error mostrando la informacion");
//                Logger.getLogger(ActionContact.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            System.out.println("Error leyendo el archivo");
//                Logger.getLogger(ActionContact.class.getName()).log(Level.SEVERE, null, ex);
        }

        return SUCCESS;       
    }
    
}