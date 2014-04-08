/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.aepscolombia.platform.controllers;

import java.lang.String;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.aepscolombia.platform.models.dao.LogEntitiesDao;
import org.aepscolombia.platform.models.dao.FieldsDao;
import org.aepscolombia.platform.models.dao.RastasDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.entity.Fields;

import org.aepscolombia.platform.models.entity.HorizontesRasta;
import org.aepscolombia.platform.models.entity.LogEntities;
import org.aepscolombia.platform.models.entity.Rastas;
import org.aepscolombia.platform.models.entity.Users;
import org.aepscolombia.platform.util.APConstants;
import org.aepscolombia.platform.util.HibernateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Clase ActionFarm
 *
 * Contiene los metodos para interactuar con el modulo de Rasta
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionRasta extends BaseAction {
    
    //Atributos del formulario 
    /**
     * Atributos provenientes del formulario
     */
    private int idProducer;
    private int idFarm;
    private int idField;
    private int idRasta;
    private String nameField;
    
    private String search_soil; 
    private String num_rasta;
    private String date;
    private String pendant;
    private String altitude;
    private String latitude;
    private String length;
    private String ground;
    private String position;
    private String ph;
    private String carbonates;
    private List<HashMap> listSoils;
    private Integer searchFrom;
    
    private Users user;
    private Integer idEntSystem;    
    private Rastas rasta = new Rastas();

    

    //Metodos getter y setter por cada variable del formulario 
    /**
     * Metodos getter y setter por cada variable del formulario
     */

    public Rastas getRasta() {
        return rasta;
    }

    public void setRasta(Rastas rasta) {
        this.rasta = rasta;
    }

    public String getNameField() {
        return nameField;
    }

    public void setNameField(String nameField) {
        this.nameField = nameField;
    } 

    public Integer getSearchFrom() {
        return searchFrom;
    }

    public void setSearchFrom(Integer searchFrom) {
        this.searchFrom = searchFrom;
    }   
    
    public int getIdProducer() {
        return idProducer;
    }

    public void setIdProducer(int idProducer) {
        this.idProducer = idProducer;
    }

    public int getIdRasta() {
        return idRasta;
    }

    public void setIdRasta(int idRasta) {
        this.idRasta = idRasta;
    }   
    
    public int getIdFarm() {
        return idFarm;
    }

    public void setIdFarm(int idFarm) {
        this.idFarm = idFarm;
    }

    public int getIdField() {
        return idField;
    }

    public void setIdField(int idField) {
        this.idField = idField;
    }

    public String getSearch_soil() {
        return search_soil;
    }

    public void setSearch_soil(String search_soil) {
        this.search_soil = search_soil;
    }

    public String getNum_rasta() {
        return num_rasta;
    }

    public void setNum_rasta(String num_rasta) {
        this.num_rasta = num_rasta;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPendant() {
        return pendant;
    }

    public void setPendant(String pendant) {
        this.pendant = pendant;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getGround() {
        return ground;
    }

    public void setGround(String ground) {
        this.ground = ground;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public String getCarbonates() {
        return carbonates;
    }

    public void setCarbonates(String carbonates) {
        this.carbonates = carbonates;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }    

    public List<HashMap> getListSoils() {
        return listSoils;
    }
    
    
    
    
    //Atributos generales de clase
    /**
     * Atributos generales de clase
     */
    private HashMap additionals;
    private int page = 1, countTotal, maxResults = 10;
    private int option_geo_lot;
    
    private FieldsDao lotDao      = new FieldsDao();
    private RastasDao rastaDao    = new RastasDao();
    private LogEntitiesDao logDao = new LogEntitiesDao();
    
    private String state = "";
    private String info  = "";
    private String valId ="", valName="", selected="";
    
    //Metodos getter y setter por cada variable general de la clase
    /**
     * Metodos getter y setter por cada variable general de la clase
     */
    public String getValId() {
        return valId;
    }

    public String getValName() {
        return valName;
    }

    public String getSelected() {
        return selected;
    }

    public String getState() {
        return state;
    }

//    @Override
    public String getInfo() {
        return info;
    }
    
//    @Override
//    public void setInfo(String info) {
//        this.info = info;
//    }

    public LogEntitiesDao getLogDao() {
        return logDao;
    }

    public void setLogDao(LogEntitiesDao logDao) {
        this.logDao = logDao;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setCountTotal(int countTotal) {
        this.countTotal = countTotal;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public int getPage() {
        return page;
    }

    public int getCountTotal() {
        return countTotal;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public HashMap getAdditionals() {
        return additionals;
    }

    public int getOption_geo_lot() {
        return option_geo_lot;
    }

    public void setOption_geo_lot(int option_geo_lot) {
        this.option_geo_lot = option_geo_lot;
    }        
    
    private List<HorizontesRasta> additionalsAtrib;

    public List<HorizontesRasta> getAdditionalsAtrib() {
        return additionalsAtrib;
    }

    public void setAdditionalsAtrib(List<HorizontesRasta> additionalsAtrib) {
        this.additionalsAtrib = additionalsAtrib;
    }
    
//    private Map<String, String[]> paraMap;
//    
//    @Override
//    public void setParameters(Map<String, String[]> parameters) {
//        this.paraMap= parameters;
//    }
    
    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }   
    
    
    @Override
    public void prepare() throws Exception {
        user = (Users) this.getSession().get(APConstants.SESSION_USER);
        idEntSystem = UsersDao.getEntitySystem(user.getIdUsr());
        rasta.setLatitudRas(4.567);
//        if (user.getIdUsr()!=null) idEntSystem = UsersDao.getEntitySystem(user.getIdUsr());
        additionalsAtrib = new ArrayList<HorizontesRasta>();        
//        additionalsAtrib.add(1,new HorizontesRasta());
    }
    
    
    /**
     * Metodo encargado de validar el formulario de Suelos
     */
    @Override
    public void validate() {       
//        super.validate();
        /*
         * Se evalua dependiendo a la accion realizada:
         * 1) create: Al momento de guardar un registro por primera ves
         * 2) modify: Al momento de modificar un registro
         * 3) delete: Al momento de borrar un registro
         */
//        for (Iterator<String> it = paraMap.keySet().iterator(); it.hasNext();){
////            Map map;
//            String key = it.next();
//            if (key.startsWith("additionalsAtrib") && (paraMap.get(key)!=null)) {
//                System.out.println("val->"+paraMap.get(key));
////                 String[] stuff = map.get(key);
//                 // do magic
//            }
//        }
//        String[] checkedIds = this.getRequest().getParameterValues("additionalsAtrib");
//        additionalsAtrib = new ArrayList<HashMap>(request.getParameterValues("firstnames"));
//        if (actExe.equals("create") || actExe.equals("modify")) {
//            System.out.println("data->"+additionalsAtrib);
//            for (HorizontesRasta data : additionalsAtrib) {
//                System.out.println("horizonte->"+data.getColorHumedoHorRas());
//            }
//            System.out.println("data1->"+paraMap);
//            System.out.println("data2->"+this.getRequest().getParameter("additionalsAtrib"));
//        }
//        System.out.println("fehca->"+rasta.getFechaRas());
//        this.getRequest().getLocale();
//        NumberFormat numberFormatter;
//        String quantityOut;
//
//        numberFormatter = NumberFormat.getNumberInstance(new Locale("en_US"));
//        quantityOut = numberFormatter.format(rasta.getLatitudRas());
//        System.out.println("entreeeeeeeeeeeeeeeeeeeee->"+quantityOut);
        if (actExe.equals("create") || actExe.equals("modify")) {
//            System.out.println("entreeeeeeeeeeeeeeeeeeeee");
            HashMap required = new HashMap();
            required.put("nameField", nameField);
            required.put("rasta.fechaRas", rasta.getFechaRas());
            required.put("rasta.altitudRas", rasta.getAltitudRas());
            required.put("rasta.latitudRas", rasta.getLatitudRas());
            required.put("rasta.longitudRas", rasta.getLongitudRas());
            required.put("rasta.pendienteTerrenoRas", rasta.getPendienteTerrenoRas());
            required.put("rasta.terrenoCircundanteRas", rasta.getTerrenoCircundanteRas());//
            required.put("rasta.posicionPerfilRas", rasta.getPosicionPerfilRas());//
            required.put("rasta.phRas", rasta.getPhRas());
            required.put("rasta.carbonatosRas", rasta.getCarbonatosRas());//
            required.put("rasta.piedrasSuperficieRas", rasta.getPiedrasSuperficieRas());
            required.put("rasta.rocasSuperficieRas", rasta.getRocasSuperficieRas());
            required.put("rasta.piedrasPerfilRas", rasta.getPiedrasPerfilRas());
            required.put("rasta.rocasPerfilRas", rasta.getRocasPerfilRas());
            required.put("rasta.estructuraRas", rasta.getEstructuraRas());//
            required.put("rasta.costrasDurasRas", rasta.getCostrasDurasRas());//            
            required.put("rasta.exposicionSolRas", rasta.getExposicionSolRas());//
            required.put("rasta.costrasBlancasRas", rasta.getCostrasBlancasRas());//
            required.put("rasta.costrasNegrasRas", rasta.getCostrasNegrasRas());//
            required.put("rasta.plantasPequenasRas", rasta.getPlantasPequenasRas());//
            required.put("rasta.recubrimientoVegetalRas", rasta.getRecubrimientoVegetalRas());//
            
            if (!rasta.getCarbonatosRas().equals("no tiene") && !rasta.getCarbonatosRas().equals("-1")) { 
                required.put("rasta.profundidadCarbonatosRas", rasta.getProfundidadCarbonatosRas());//
//                addFieldError("rasta.profundidadCarbonatosRas", "Dato requerido");                
            }

            if (rasta.getHorizontePedrogosoRocosoRas()) {  
                required.put("rasta.profundidadHorizontePedregosoRas", rasta.getProfundidadHorizontePedregosoRas());//
                required.put("rasta.espesorHorizontePedregosoRas", rasta.getEspesorHorizontePedregosoRas());//
                required.put("rasta.profundidadPrimerasPiedrasRas", rasta.getProfundidadPrimerasPiedrasRas());//
                
//                addFieldError("rasta.profundidadHorizontePedregosoRas", "Dato requerido");
//                addFieldError("rasta.espesorHorizontePedregosoRas", "Dato requerido");
//                addFieldError("rasta.profundidadPrimerasPiedrasRas", "Dato requerido");
            }

            if (rasta.getCapasEndurecidasRas()) {
                required.put("rasta.prufundidadCapasRas", rasta.getPrufundidadCapasRas());//
                required.put("rasta.espesorCapaEndurecidaRas", rasta.getEspesorCapaEndurecidaRas());//
                
//                addFieldError("rasta.prufundidadCapasRas", "Dato requerido");
//                addFieldError("rasta.espesorCapaEndurecidaRas", "Dato requerido");
            }

            if (rasta.getMoteadosRas()) {
                required.put("rasta.profundidadMoteadosRas", rasta.getProfundidadMoteadosRas());//
//                addFieldError("rasta.profundidadMoteadosRas", "Dato requerido");
            }

            if (rasta.getRaicesVivasRas()) {
                required.put("rasta.profundidadRaicesRas", rasta.getProfundidadRaicesRas());//
//                addFieldError("rasta.profundidadRaicesRas", "Dato requerido");
            }
//            
//            if (option_geo_lot == 1) {
//                required.put("latitude_property", latitude_lot);
//                required.put("length_property", length_lot);
//            } else if (option_geo_lot == 2) {
//                required.put("latitude_degrees_property", latitude_degrees_lot);
//                required.put("latitude_minutes_property", latitude_minutes_lot);
//                required.put("latitude_seconds_property", latitude_seconds_lot);
//                required.put("length_degrees_property", length_degrees_lot);
//                required.put("length_minutes_property", length_minutes_lot);
//                required.put("length_seconds_property", length_seconds_lot);
//            }
//            
////            System.out.println("required->"+required);
//            
            for (Iterator it = required.keySet().iterator(); it.hasNext();) {
                String sK = (String) it.next();
                String sV = String.valueOf(required.get(sK));
//                System.out.println(sK + " : " + sV);
                if (StringUtils.trim(sV).equals("null") || StringUtils.trim(sV)==null || StringUtils.trim(sV).equals("") || sV.equals("-1")) {
                    addFieldError(sK, "El campo es requerido");
                }
            }
//            
            
            if (rasta.getLatitudRas()==null) {
                addFieldError("rasta.latitudRas", "Debe ingresar alguno de estos datos");
                addFieldError("latitude_degrees", " ");
                addFieldError("latitude_minutes", " ");
                addFieldError("latitude_seconds", " ");
                addActionError("Debe ingresar la latitud en Decimales o en Grados");
            }
            
            if (rasta.getLongitudRas()==null) {
                addFieldError("rasta.longitudRas", "Debe ingresar alguno de estos datos");
                addFieldError("length_degrees", " ");
                addFieldError("length_minutes", " ");
                addFieldError("length_seconds", " ");
                addActionError("Debe ingresar la longitud en Decimales o en Grados");
            }

            if (rasta.getAltitudRas()!=null && (rasta.getAltitudRas()<0 || rasta.getAltitudRas()>9000)) {
                addFieldError("rasta.altitudRas", "Dato invalido");
                addActionError("Se ingreso una altitud invalida, por favor ingresar un valor entre 0 y 9000");
            }

            if (rasta.getLatitudRas()!=null && (rasta.getLatitudRas()<(-4.3) || rasta.getLatitudRas()>(13.5))) {
                addFieldError("rasta.latitudRas", "Dato invalido");
                addActionError("Se ingreso una latitud invalida, por favor ingresar un valor entre -4.3 y 13.5");
            }

            if (rasta.getLongitudRas()!=null && (rasta.getLongitudRas()<(-81.8) || rasta.getLongitudRas()>(-66))) {
                addFieldError("rasta.longitudRas", "Dato invalido");
                addActionError("Se ingreso una longitud invalida, por favor ingresar un valor entre -81.8 y -66");
            }

            if (rasta.getPendienteTerrenoRas()!=null && (rasta.getPendienteTerrenoRas()<0 || rasta.getPendienteTerrenoRas()>100)) {
                addFieldError("rasta.pendienteTerrenoRas", "Dato invalido");
                addActionError("Se ingreso una pendiente invalida, por favor ingresar un valor entre 0 y 100");
            }

            if (rasta.getPhRas()!=null && (rasta.getPhRas()<0 || rasta.getPhRas()>14)) {
                addFieldError("rasta.phRas", "Dato invalido");
                addActionError("Se ingreso un PH invalido, por favor ingresar un valor entre 0 y 14");
            }

            Boolean entry      = true;
            Boolean errorEsp   = false;
            Boolean errorClSe  = false;
            Boolean errorClHm  = false;
            int sumEspesor = 0;
//            for ($params['horizontes'] as $index => $horizonte) {
            int cont = 1;
            for (HorizontesRasta horizon : additionalsAtrib) {
                // print_r($horizonte);
//                System.out.println("horizon->"+horizon);
//                if ((horizon.getNumeroHorizonteHorRas()<0) && (horizon.getEspesorHorRas()<0) && (horizon.getColorSecoHorRas()<0) && (horizon.getColorHumedoHorRas()<0) && (horizon.getTexturesId()<0) && (horizon.getResistenciasRompimientoId()<0)) {
                if ((horizon.getNumeroHorizonteHorRas()>0) && (horizon.getEspesorHorRas()!=null && horizon.getEspesorHorRas()>0) && (horizon.getColorSecoHorRas()!=null && horizon.getColorSecoHorRas()>0) && (horizon.getColorHumedoHorRas()!=null && horizon.getColorHumedoHorRas()>0) && (horizon.getTextures()!=null) && (horizon.getResistenciasRompimiento()!=null)) {
                    entry = false;					
                }
//                System.out.println("horizon.getColorSecoHorRas()->"+horizon.getColorSecoHorRas());

                if (horizon.getEspesorHorRas()!=null) {    
                    if (horizon.getEspesorHorRas()<0 || horizon.getEspesorHorRas()>150) {
                        addFieldError("additionalsAtrib["+cont+"].espesorHorRas", "Dato invalido");
//                        $fails[]  = $prefix.'horizontes_'.$index.'_espesor';
                        errorEsp = true;
                    } else {
                        sumEspesor += horizon.getEspesorHorRas();
                    }
                }

                if (horizon.getColorSecoHorRas()!=null) {    
                    if (horizon.getColorSecoHorRas()<1 || horizon.getColorSecoHorRas()>54) {
                        addFieldError("additionalsAtrib["+cont+"].colorSecoHorRas", "Dato invalido");
//                        $fails[]  = $prefix.'horizontes_'.$index.'_color_seco';
                        errorClSe = true;
                    }
                }

                if (horizon.getColorHumedoHorRas()!=null) {    
                    if (horizon.getColorHumedoHorRas()<1 || horizon.getColorHumedoHorRas()>54) {
                        addFieldError("additionalsAtrib["+cont+"].colorHumedoHorRas", "Dato invalido");
//                        $fails[]  = $prefix.'horizontes_'.$index.'_color_humedo';
                        errorClHm = true;
                    }
                }
                cont++;
            }

            if (errorEsp) {
                addActionError("Se ingresaron espesores por fuera del rango, por favor ingresar valores entre 0 y 150");
            }

            if (errorClSe) {
                addActionError("Se ingresaron colores secos por fuera del rango, por favor ingresar valores entre 1 y 54");
            }

            if (errorClHm) {
                addActionError("Se ingresaron colores humedos por fuera del rango, por favor ingresar valores entre 1 y 54");
            }


            if (rasta.getProfundidadCarbonatosRas()!=null && (rasta.getProfundidadCarbonatosRas()<0 || rasta.getProfundidadCarbonatosRas()>sumEspesor)) {
                addFieldError("rasta.profundidadCarbonatosRas", "Dato invalido");
                addActionError("Se ingreso una profundidad de carbonatos invalida, por favor ingresar un valor entre 0 y "+sumEspesor+" que corresponde al total de espesor por todas las capas");
            }

            if (rasta.getProfundidadHorizontePedregosoRas()!=null && (rasta.getProfundidadHorizontePedregosoRas()<0 || rasta.getProfundidadHorizontePedregosoRas()>sumEspesor)) {            
                addFieldError("rasta.profundidadHorizontePedregosoRas", "Dato invalido");
                addActionError("Se ingreso una profundidad de horizonte pedregoso invalido, por favor ingresar un valor entre 0 y "+sumEspesor+" que corresponde al total de espesor por todas las capas");            
            }

            if (rasta.getEspesorHorizontePedregosoRas()!=null && (rasta.getEspesorHorizontePedregosoRas()<0 || rasta.getEspesorHorizontePedregosoRas()>sumEspesor)) {
                addFieldError("rasta.espesorHorizontePedregosoRas", "Dato invalido");
                addActionError("Se ingreso un espesor de horizonte pedregoso invalido, por favor ingresar un valor entre 0 y "+sumEspesor+" que corresponde al total de espesor por todas las capas");            
            }

            if (rasta.getProfundidadPrimerasPiedrasRas()!=null && (rasta.getProfundidadPrimerasPiedrasRas()<0 || rasta.getProfundidadPrimerasPiedrasRas()>sumEspesor)) {
                addFieldError("rasta.profundidadPrimerasPiedrasRas", "Dato invalido");
                addActionError("Se ingreso una profundidad de primeras rocas o piedras invalida, por favor ingresar un valor entre 0 y "+sumEspesor+" que corresponde al total de espesor por todas las capas");            
            }

            if (rasta.getPrufundidadCapasRas()!=null && (rasta.getPrufundidadCapasRas()<0 || rasta.getPrufundidadCapasRas()>sumEspesor)) {
                addFieldError("rasta.prufundidadCapasRas", "Dato invalido");
                addActionError("Se ingreso una profundidad de capas endurecidas invalido, por favor ingresar un valor entre 0 y "+sumEspesor+" que corresponde al total de espesor por todas las capas");            
            }
                        
            
            if (rasta.getEspesorCapaEndurecidaRas()!=null && !rasta.getEspesorCapaEndurecidaRas().equals("")) {
                int espCapa = Integer.parseInt(rasta.getEspesorCapaEndurecidaRas());
                if (espCapa<0 || espCapa>sumEspesor) {
                    addFieldError("rasta.espesorCapaEndurecidaRas", "Dato invalido");
                    addActionError("Se ingreso un espesor de capas endurecidas invalido, por favor ingresar un valor entre 0 y "+sumEspesor+" que corresponde al total de espesor por todas las capas");            
                }
            }

            if (rasta.getProfundidadMoteadosRas()!=null && (rasta.getProfundidadMoteadosRas()<0 || rasta.getProfundidadMoteadosRas()>sumEspesor)) {
                addFieldError("rasta.profundidadMoteadosRas", "Dato invalido");
                addActionError("Se ingreso una profundidad de moteados invalida, por favor ingresar un valor entre 0 y "+sumEspesor+" que corresponde al total de espesor por todas las capas");            
            }

            if (rasta.getProfundidadRaicesRas()!=null && (rasta.getProfundidadRaicesRas()<0 || rasta.getProfundidadRaicesRas()>sumEspesor)) {
                addFieldError("rasta.profundidadMoteadosRas", "Dato invalido");
                addActionError("Se ingreso una profundidad de raices vivas invalida, por favor ingresar un valor entre 0 y "+sumEspesor+" que corresponde al total de espesor por todas las capas");            
            }

            if (entry) {
//                cont = 1;
//                for (HorizontesRasta horizon : additionalsAtrib) {
                for (int i=1; i<=additionalsAtrib.size(); i++) {                    
                    addFieldError("additionalsAtrib["+i+"].numeroHorizonteHorRas", "Dato invalido");
                    addFieldError("additionalsAtrib["+i+"].espesorHorRas", "Dato invalido");
                    addFieldError("additionalsAtrib["+i+"].colorSecoHorRas", "Dato invalido");
                    addFieldError("additionalsAtrib["+i+"].colorHumedoHorRas", "Dato invalido");
                    addFieldError("additionalsAtrib["+i+"].textures", "Dato invalido");
                    addFieldError("additionalsAtrib["+i+"].resistenciasRompimiento", "Dato invalido");            
//                    $fails[] = $prefix.'horizontes_'.$index.'_num_horizonte';				
//                    cont++;
                }
                addActionError("Se debe ingresar por lo menos alguna capa");    
            }

            if (sumEspesor>150) {
                for (int i=1; i<=additionalsAtrib.size(); i++) {
                    addFieldError("additionalsAtrib["+i+"].numeroHorizonteHorRas", "Dato invalido");
                    addFieldError("additionalsAtrib["+i+"].espesorHorRas", "Dato invalido");
                    addFieldError("additionalsAtrib["+i+"].colorSecoHorRas", "Dato invalido");
                    addFieldError("additionalsAtrib["+i+"].colorHumedoHorRas", "Dato invalido");
                    addFieldError("additionalsAtrib["+i+"].textures", "Dato invalido");
                    addFieldError("additionalsAtrib["+i+"].resistenciasRompimiento", "Dato invalido");         		
                }
                addActionError("Se debe ingresar espesores de maximo 150 centimetros");   
            }
        }
    }
    
    private int numRows=0;

    public Integer getNumRows() {
        return numRows;
    }

    public void setNumRows(Integer numRows) {
        this.numRows = numRows;
    }
    
    
    private Integer newReq=0;

    public Integer getNewRow() {
        return newReq;
    }
    
    public void setNewReq(Integer newReq) {
        this.newReq = newReq;
    }
    
            
  
    
    /**
    * Accion que permite cargar una nueva fila en el panel de campos adicionales de un documento
    *
    */
    public String showRowAdditional()
    {     
        actExe = (String)(this.getRequest().getParameter("action"));
//        newRow = Integer.parseInt(this.getRequest().getParameter("newRow"));
        this.setNumRows(Integer.parseInt((this.getRequest().getParameter("numRows")))+1);
//        this.setNumRows(Integer.parseInt((this.getRequest().getParameter("numRows"))));
        HorizontesRasta horizon = new HorizontesRasta();
//        horizon.setNumeroHorizonteHorRas(0);
        additionalsAtrib.add(this.getNumRows()-1,horizon);
//        this.setFormDocument("formRasta[additionalsAtrib]["+numRows+"]");
//      $formDocument  = new FormComCatStoreDocumentExtra(array(), array());  
//      return $this->renderPartial('item/atribAddItem', array('formDocument' => $formDocument, 'numRows' => $numRows+1, 'actionOpt' => 'create', 'message' => 'messageWind'));    
        return SUCCESS;
    }

    /**
     * Encargado de buscar las coincidencias de un formulario de busqueda, para cada una de los
     * rastas registrados a un usuario
     * @param valName:  Nombre del valor a buscar
     * @param valId:    Identificacion del valor a buscar
     * @param selected: Valor seleccionado
     * @return lista de rastas
     */
    public String search() {
        valName     = (String)(this.getRequest().getParameter("valName"));
        valId       = (String)(this.getRequest().getParameter("valId"));
        selected    = (String)(this.getRequest().getParameter("selected"));
        if(selected==null) selected="rasta";
        
        additionals = new HashMap();
        additionals.put("selected", selected);
        HashMap findParams = new HashMap();
        
//        if (searchFrom!=null && searchFrom==2) {
//            num_rasta = date = pendant = altitude = latitude = length = ground = position = ph = carbonates = search_soil;            
//        } else if(searchFrom!=null && searchFrom==1) {
        if(searchFrom!=null && searchFrom==1) {
            search_soil = "";
        }
        
        findParams.put("idEntUser", idEntSystem);
        findParams.put("search_soil", search_soil);
        findParams.put("num_rasta", num_rasta);
        findParams.put("date", date);
        findParams.put("pendant", pendant);
        findParams.put("altitude", altitude);
        findParams.put("latitude", latitude);
        findParams.put("length", length);
        findParams.put("ground", ground);
        findParams.put("position", position);
        findParams.put("ph", ph);
        findParams.put("carbonates", carbonates);
        
        int pageReq;
        if (this.getRequest().getParameter("page") == null) {
            pageReq = this.getPage();
        } else {
            pageReq = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter("page")));
        }
        findParams.put("pageNow", pageReq);
        findParams.put("maxResults", this.getMaxResults());
        listSoils = rastaDao.findByParams(findParams);
        this.setCountTotal(Integer.parseInt(String.valueOf(listSoils.get(0).get("countTotal"))));
        this.setPage(page);
        listSoils.remove(0);
//        System.out.println("countTotal->"+this.getCountTotal());
        return SUCCESS;
    }
    

    /**
     * Encargado de mostrar en un formulario la informacion de un rasta apartir de la identificacion
     * @param idRasta:  Identificacion del rasta
     * @return Informacion del rasta
     */
    public String show() {
        actExe = (String)(this.getRequest().getParameter("action"));
        int pageReq;
        if (this.getRequest().getParameter("page") != null) {
            pageReq = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter("page")));
            this.setPage(pageReq);
        } else {
//            this.setPage(1);
        }
        try {
            this.setIdRasta(Integer.parseInt(this.getRequest().getParameter("idRasta")));
        } catch (NumberFormatException e) {
//            LOG.error("There was an error trying to parse the activityId parameter");
            this.setIdRasta(-1);
        }

//        this.setType_property_lot(new FieldTypesDao().findAll());
        if (this.getIdRasta()!= -1) {
            rasta      = rastaDao.objectById(this.getIdRasta());
            idField    = rasta.getFields().getIdFie();
            Fields fie = lotDao.objectById(idField);
            nameField  = fie.getNameFie();
//            nameField = rasta.getFields().getNameFie();            
//            String dateActual = new SimpleDateFormat("dd/MM/yyyy").format(rasta.getFechaRas());   
//            rasta.setFechaRas(new Date(dateActual));
//            Date asignRasta = null;
//            Date asignRasta = rasta.getFechaRas();
//            System.out.println("date->"+dateActual);
//            try {
////                System.out.println("date->"+new SimpleDateFormat("yyyy-MM-dd").parse(dateActual));
//                asignRasta = new SimpleDateFormat("dd/MM/yyyy").parse(dateActual);
//            } catch (ParseException ex) {
////                System.out.println("errorConversion->"+ex.getMessage());
//            }
//            rasta.setFechaRas(asignRasta);
            additionalsAtrib = rastaDao.getHorizonRasta(this.getIdRasta());
            newReq = (additionalsAtrib.size());
//            System.out.println("size->"+newReq);
//            for (HorizontesRasta horizon : additionalsAtrib) {
//                System.out.println("additionalsAtrib->"+horizon.getColorSecoHorRas());        
//            }
//            System.out.println("valores->" + rasta);
        } else {
//            HorizontesRasta hor  = new HorizontesRasta(new ResistenciasRompimiento(1), new Textures(1), 1, 1.0, 1, 3);
            additionalsAtrib.add(0,new HorizontesRasta());
        }
        return SUCCESS;
    }    

    /**
     * Encargado de guardar la informacion suministrada por el usuario para un rasta
     * @return Estado del proceso
     */
    public String saveData() {
        String action = "";
//        System.out.println("Entre a guardar la info");
        /*
         * Se evalua dependiendo a la accion realizada:
         * 1) create: Al momento de guardar un registro por primera ves
         * 2) modify: Al momento de modificar un registro
         * 3) delete: Al momento de borrar un registro
         */
        if (actExe.equals("create")) {
            action = "C";
        } else if (actExe.equals("modify")) {
            action = "M";
        }
        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;
//        info = "El rasta ha sido modificado con exito";
        

        try {
//            int idProOld = 0;
            tx = session.beginTransaction();
//            Fields lot = null;
//            if (rasta.getIdRas()<=0) {
//                lot = new Fields();
//                lot.setIdFie(null);
//                lot.setIdProjectFie("1");
//                lot.setStatusFie(true); 
//                lot.setMeasureUnitFie("1");
//            } else {
//                HashMap fieldInfo = lotDao.findById(idField);
//                idProOld = Integer.parseInt(String.valueOf(fieldInfo.get("id_producer")));
//                lot = lotDao.objectById(idField);
//            }
////            lot.setIdFarmFie(idFarm);
//            if(idFarm>0) lot.setFarms(new Farms(idFarm));
//            lot.setNameFie(name_lot);
//            lot.setAltitudeFie(altLot);
//            lot.setLatitudeFie(latLot);
//            lot.setLongitudeFie(lonLot);
//            lot.setAreaFie(areaLot);            
            if (rasta.getIdRas()!=null) rastaDao.deleteHorizonte(rasta.getIdRas());
            
            String dmy     = new SimpleDateFormat("yyyy-MM-dd").format(rasta.getFechaRas());
            Date dateRasta = new SimpleDateFormat("yyyy-MM-dd").parse(dmy);
            rasta.setFields(new Fields(idField));
            rasta.setNumeroCapasRas(additionalsAtrib.size());
            rasta.setFechaRas(dateRasta);            
            rasta.setEstadoRas(true);
            session.saveOrUpdate(rasta);
            
            rasta.setNumeroCajuelaRas(rasta.getIdRas());
            session.saveOrUpdate(rasta);
            
//            rastaDao.save(lot);
            
            //Agregar los horizontes            
            for (HorizontesRasta horizon : additionalsAtrib) {
                horizon.setRastas(rasta);
//                horizon.setResistenciasRompimiento(new ResistenciasRompimiento(horizon.getResistenciasRompimientoId()));
//                horizon.setTextures(new Textures(horizon.getTexturesId()));
                session.saveOrUpdate(horizon);
            }
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem); //Colocar el usuario registrado en el sistema
//            log.setIdEntityLogEnt(1);
            log.setIdObjectLogEnt(rasta.getIdRas());
            log.setTableLogEnt("rastas");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt(action);
            session.saveOrUpdate(log);
//            logDao.save(log);            
            
            tx.commit();           
            state = "success";
            if (action.equals("C")) {
                info = "El rasta ha sido agregado con exito";
            } else if (action.equals("M")) {
                info = "El rasta ha sido modificado con exito";
            }            
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = "Fallo al momento de agregar el rasta";
        } catch (ParseException e) { 
        
        } finally {
            session.close();
        }       
        
//        return ERROR;
        return "states";
    }

    /**
     * Encargado de borrar la informacion de un rasta apartir de su identificacion
     * @param idRasta:  Identificacion del rasta
     * @return Estado del proceso
     */
    public String delete() {
        Integer idRasta = 0;
        try {
            idRasta = Integer.parseInt(this.getRequest().getParameter("idRasta"));
        } catch (NumberFormatException e) {
            idRasta = -1;
        }
        
        if (idRasta==-1) {
            state = "failure";
            info  = "Fallo al momento de obtener la informacion a borrar";
            return "states";
        }
        
        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
//            rastaDao.deleteHorizonte(idRasta);
            
            Rastas ras = rastaDao.objectById(idRasta);      
            session.delete(ras);            
            
            LogEntities log = new LogEntities();
            log.setIdLogEnt(null);
            log.setIdEntityLogEnt(idEntSystem); //Colocar el usuario registrado en el sistema
//            log.setIdEntityLogEnt(1); //Colocar el usuario registrado en el sistema
            log.setIdObjectLogEnt(ras.getIdRas());
            log.setTableLogEnt("rastas");
            log.setDateLogEnt(new Date());
            log.setActionTypeLogEnt("D");
            session.saveOrUpdate(log);
//            logDao.save(log);
            tx.commit();         
            state = "success";
            info  = "El rasta ha sido borrado con exito";
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = "Fallo al momento de borrar un rasta";
        } finally {
            session.close();
        }      
        
        return "states";
//        return SUCCESS;
    }
}