package org.aepscolombia.platform.controllers;

/**
 * Clase JavascriptHelper
 *
 * Contiene etiquetas de ayuda que se comportan como etiquetas adicionales para dar soporte a los soportados ya por JAVA
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class JavascriptHelper {

    /**
     * Crea una etiqueta de hipervinculo con una configuracion especifica
     * @param  name  Nombre que se le da al hipervinculo
     * @param  script  Opciones de javascript que contara el hipervinculo
     * @param  style_options  Opciones de estilo para el hipervinculo
     * @return representacion de la etiqueta como una cadena de texto
     */
    public static String link_to_remote(String name, String script, String style_options) {

        String val = "";
        val += "<a href=\"#\" onClick=\"" + script + "\" style=\"" + style_options + "\"><span>" + name + "</span></a>";
        return val;
    }

    /**
     * Crea una etiqueta para el manejo del paginado dentro de los modulos del sistema
     * @param  page  Numero de pagina en que se encuentra 
     * @param  countTotal  Numero total de registros encontrados
     * @param  maxResults  Maximo de registros a mostrar por pagina
     * @param  url  Enlace al cual debe dirigirse cuando se cambie de pagina
     * @param  divUpdate  Elemento (DIV) a actualizar cuando se pagine
     * @param  divMessage  Mensaje a ser mostrado en caso de fallar la carga de registros a paginar
     * @param  params  Parametros adicionales a considerar
     * @param  formId  Identificacion del formulario de busqueda
     * @return representacion en Html de la estructura de paginacion
     */
    public static String pager_params_ajax(int page, int countTotal, int maxResults, String url, String divUpdate, String divMessage, String params, String formId) {
        int linksTotals = (int) Math.ceil((double)countTotal / maxResults);
        
        String script = "changePage('/aeps-plataforma-mvn/buscarPersonas.action', 'page', '', 'divTabPersons', 'divMessage');";
        String navigation = "";
        navigation += "<div class=\"pagination pagination-centered\"><ul>";
        String links = "";
        // Primera y pagina previa
        if (page != 1) {
            navigation += "<li>" + link_to_remote("&laquo;", "changePage('" + url + "', 'page', '" + (page - 1) + "', '" + divUpdate + "', '" + formId + "', '" + divMessage + "');", "") + "</li>";
        } else {
            navigation += "<li class=\"disabled\"><span>&laquo;</span></li>";
        }
        
        if (linksTotals<7) {            
            // Paginas una por una
            for (int i = 1; i <= linksTotals; i++) {
                if (page == i) {
                    links += "<li class=\"active\"><a href=\"#\">" + page + "</a></li>";
                } else {
                    links += "<li>" + link_to_remote(String.valueOf(i), "changePage('" + url + "', 'page', '" + i + "', '" + divUpdate + "', '" + formId + "', '" + divMessage + "');", "") + "</li>";
                }
            }       
        } else {                
            int max = 7;
            int sp  = 0;
            if(page < max) {
                sp = 1;
            } else if(page >= (linksTotals - Math.floor(max / 2))) {
                sp = linksTotals - max + 1;
            } else if(page >= max) {
                sp =  page - ((int)Math.floor(max/2));
            }
            
            // Paginas una por una            
            for(int i = sp; i <= (sp + max -1); i++) {
                if (page == i) {
                    links += "<li class=\"active\"><a href=\"#\">" + page + "</a></li>";
                } else {
                    links += "<li>" + link_to_remote(String.valueOf(i), "changePage('" + url + "', 'page', '" + i + "', '" + divUpdate + "', '" + formId + "', '" + divMessage + "');", "") + "</li>";
                }
            }           
        }
        navigation += links;
        // Ultima y pagina siguiente
        if (page != linksTotals) {
            navigation += "<li>" + link_to_remote("&raquo;", "changePage('" + url + "', 'page', '" + (page + 1) + "', '" + divUpdate + "', '" + formId + "', '" + divMessage + "');", "") + "</li>";
        } else {
            navigation += "<li class=\"disabled\"><span>&raquo;</span></li>";
        }
        navigation += "</ul></div>";
        int numPage = page;
        int init = maxResults * (numPage - 1) + 1;
        int end  = maxResults * numPage;
        if (end > countTotal) {
            end  = countTotal;
        }
        String header  = "Resultados:"+" "+init+" a ";
        header += end+" de "+countTotal;
        return "<div style=\"font-weight: bold\">"+header+"</div>"+navigation;
//        return navigation;
    }
}
