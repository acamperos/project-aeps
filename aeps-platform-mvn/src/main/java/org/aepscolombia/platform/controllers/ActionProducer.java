
package org.aepscolombia.platform.controllers;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.opensymphony.xwork2.validator.annotations.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.aepscolombia.platform.models.dao.AssociationDao;

import org.aepscolombia.platform.models.dao.DocumentsTypesDao;

import org.aepscolombia.platform.models.entity.Departments;
import org.aepscolombia.platform.models.dao.DepartmentsDao;
import org.aepscolombia.platform.models.dao.EntitiesDao;
import org.aepscolombia.platform.models.dao.FarmsDao;
import org.aepscolombia.platform.models.dao.LogEntitiesDao;

import org.aepscolombia.platform.models.dao.MunicipalitiesDao;
import org.aepscolombia.platform.models.dao.ProducersDao;
import org.aepscolombia.platform.models.dao.SfGuardUserDao;
import org.aepscolombia.platform.models.dao.UsersDao;
import org.aepscolombia.platform.models.entity.EntitiesTypes;
import org.aepscolombia.platform.models.entity.DocumentsTypes;
import org.aepscolombia.platform.models.entity.Entities;
import org.aepscolombia.platform.models.entity.Producers;
import org.aepscolombia.platform.models.entity.Municipalities;
import org.aepscolombia.platform.models.entity.Users;
import org.aepscolombia.platform.models.entityservices.SfGuardUser;
import org.aepscolombia.platform.util.APConstants;
import org.aepscolombia.platform.util.HibernateUtil;
import org.aepscolombia.platform.util.ValidatorUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Clase ActionProducer
 *
 * Contiene los metodos para interactuar con el modulo de Productores
 *
 * @author Juan Felipe Rodriguez
 * @version 1.0
 */
public class ActionProducer extends BaseAction {

    /**
     * Atributos provenientes del formulario
     */
    private String typeIdent = "";
    private List<DocumentsTypes> type_ident_producer;
    private String num_ident_producer;
    private String dig_ver_producer;
    private String names_producer_1;
    private String names_producer_2;
    private String last_names_producer_1;
    private String last_names_producer_2;
    private String direction_producer;
    private String depPro = "";
    private String cityPro = "";
    private List<Departments> department_producer;
    private List<Municipalities> city_producer;
    private int idProducer = 0;
    private Integer telephone_producer;
    private Long celphone_producer;
    private String email_producer;
    private String identProducer;
    public List<HashMap> listProducers;
    private Users user;
    private Integer idEntSystem;
    private Integer idUsrSystem;
    private Integer searchFromProducer;
    private String search_producer;
    private String nameCompany;
    private String firstNameRep;
    private String secondNameRep;
    private String firstLastNameRep;
    private String secondLastNameRep;
    private String emailRes;
    private UsersDao usrDao;
    private List<Entities> list_agronomist;
    private AssociationDao assDao;
    private String coCode;

    /**
     * Metodos getter y setter por cada variable del formulario
     */
    public int getIdProducer() {
        return idProducer;
    }

    public void setIdProducer(int idProducer) {
        this.idProducer = idProducer;
    }

    public String getSearch_producer() {
        return search_producer;
    }

    public void setSearch_producer(String search_producer) {
        this.search_producer = search_producer;
    }

    public Integer getSearchFromProducer() {
        return searchFromProducer;
    }

    public void setSearchFromProducer(Integer searchFromProducer) {
        this.searchFromProducer = searchFromProducer;
    }

    public String getTypeIdent() {
        return typeIdent;
    }

    public void setTypeIdent(String typeIdent) {
        this.typeIdent = typeIdent;
    }

    public String getDepPro() {
        return depPro;
    }

    public void setDepPro(String depPro) {
        this.depPro = depPro;
    }

    public String getCityPro() {
        return cityPro;
    }

    public void setCityPro(String cityPro) {
        this.cityPro = cityPro;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getFirstNameRep() {
        return firstNameRep;
    }

    public void setFirstNameRep(String firstNameRep) {
        this.firstNameRep = firstNameRep;
    }

    public String getSecondNameRep() {
        return secondNameRep;
    }

    public void setSecondNameRep(String secondNameRep) {
        this.secondNameRep = secondNameRep;
    }

    public String getFirstLastNameRep() {
        return firstLastNameRep;
    }

    public void setFirstLastNameRep(String firstLastNameRep) {
        this.firstLastNameRep = firstLastNameRep;
    }

    public String getSecondLastNameRep() {
        return secondLastNameRep;
    }

    public void setSecondLastNameRep(String secondLastNameRep) {
        this.secondLastNameRep = secondLastNameRep;
    }

    public String getEmailRes() {
        return emailRes;
    }

    public void setEmailRes(String emailRes) {
        this.emailRes = emailRes;
    }

    public String getNum_ident_producer() {
        return num_ident_producer;
    }

    public void setNum_ident_producer(String num_ident_producer) {
        this.num_ident_producer = num_ident_producer;
    }

    public String getDig_ver_producer() {
        return dig_ver_producer;
    }

    public void setDig_ver_producer(String dig_ver_producer) {
        this.dig_ver_producer = dig_ver_producer;
    }

    public String getNames_producer_1() {
        return names_producer_1;
    }

    public void setNames_producer_1(String names_producer_1) {
        this.names_producer_1 = names_producer_1;
    }

    public String getNames_producer_2() {
        return names_producer_2;
    }

    public void setNames_producer_2(String names_producer_2) {
        this.names_producer_2 = names_producer_2;
    }

    public String getLast_names_producer_1() {
        return last_names_producer_1;
    }

    public void setLast_names_producer_1(String last_names_producer_1) {
        this.last_names_producer_1 = last_names_producer_1;
    }

    public String getLast_names_producer_2() {
        return last_names_producer_2;
    }

    public void setLast_names_producer_2(String last_names_producer_2) {
        this.last_names_producer_2 = last_names_producer_2;
    }

    public String getDirection_producer() {
        return direction_producer;
    }

    public void setDirection_producer(String direction_producer) {
        this.direction_producer = direction_producer;
    }

    public List<Entities> getList_agronomist() {
        return list_agronomist;
    }

    public void setList_agronomist(List<Entities> list_agronomist) {
        this.list_agronomist = list_agronomist;
    }

    public List<Departments> getDepartment_producer() {
        return department_producer;
    }

    public void setDepartment_producer(List<Departments> department_producer) {
        this.department_producer = department_producer;
    }

    public List<Municipalities> getCity_producer() {
        return city_producer;
    }

    public void setCity_producer(List<Municipalities> city_producer) {
        this.city_producer = city_producer;
    }

    public Integer getTelephone_producer() {
        return telephone_producer;
    }

    public void setTelephone_producer(Integer telephone_producer) {
        this.telephone_producer = telephone_producer;
    }

    public Long getCelphone_producer() {
        return celphone_producer;
    }

    public void setCelphone_producer(Long celphone_producer) {
        this.celphone_producer = celphone_producer;
    }

    public String getEmail_producer() {
        return email_producer;
    }

    @EmailValidator(message = "Se ingreso un email invalido")
    public void setEmail_producer(String email_producer) {
        this.email_producer = email_producer;
    }

    public void setType_ident_producer(List<DocumentsTypes> type_ident_producer) {
        this.type_ident_producer = type_ident_producer;
    }

    public List<DocumentsTypes> getType_ident_producer() {
//        ArrayList<TiposDocumentos> prueba = new ArrayList<TiposDocumentos>();
//        prueba.add(new TiposDocumentos("cc", "cedula"));
        return type_ident_producer;
    }

    public String getIdentProducer() {
        return identProducer;
    }

    public void setIdentProducer(String identProducer) {
        this.identProducer = identProducer;
    }

    public List<HashMap> getListProducers() {
        return listProducers;
    }

    public ActionProducer() {
//        user = (Users) this.getSession().get(APConstants.SESSION_USER);
//        idEntSystem = UsersDao.getEntitySystem(user.getIdUsr());
//        super();
    }

    /**
     * Atributos generales de clase
     */
    private HashMap additionals;
    private int page = 1, countTotal, maxResults = 10;

    private EntitiesDao entDao = new EntitiesDao();
    private ProducersDao proDao = new ProducersDao();
    private LogEntitiesDao logDao = new LogEntitiesDao();
    private String valId = "", valName = "", selected = "";
    private String state = "";
    private String info = "";

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

    public void setSelected(String selected) {
        this.selected = selected;
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

    public String getState() {
        return state;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public void prepare() throws Exception {
        user = (Users) this.getSession().get(APConstants.SESSION_USER);
        idEntSystem = UsersDao.getEntitySystem(user.getIdUsr());
//        coCode = user.getCountryUsr().getAcronymIdCo();
        coCode = (String) this.getSession().get(APConstants.COUNTRY_CODE);
        this.setType_ident_producer(new DocumentsTypesDao().findAll(coCode));
        this.setDepartment_producer(new DepartmentsDao().findAll(coCode));
        List<Municipalities> mun = new ArrayList<Municipalities>();
        mun.add(new Municipalities());
        this.setCity_producer(mun);
        usrDao = new UsersDao();
        idUsrSystem = user.getIdUsr();
        assDao = new AssociationDao();
//        user.setCountryUsr(null);
    }

    @Override
    public String execute() throws Exception {
//        this.setType_ident_producer(new DocumentsTypesDao().findAll());
//        this.setDepartment_producer(new DepartmentsDao().findAll());
        return SUCCESS;
    }

    /**
     * Metodo encargado de validar el formulario de Productores
     */
    @Override
    public void validate() {
        /*
         * Se evalua dependiendo a la accion realizada:
         * 1) create: Al momento de guardar un registro por primera ves
         * 2) modify: Al momento de modificar un registro
         * 3) delete: Al momento de borrar un registro
         */
        if (actExe.equals("create") || actExe.equals("modify")) {
            //        ArrayList[] errors = new ArrayList[10];
            //        if ($option=="modify") {
            // $fields = array("id", "code", "types_barcode", "handle_type");
//            Object required[] = {typeIdent, department_producer, city_producer, num_ident_producer, names_producer_1, last_names_producer_1};
            HashMap required = new HashMap();
            required.put("typeIdent", typeIdent);
            required.put("num_ident_producer", num_ident_producer);
            required.put("depPro", depPro);
            required.put("cityPro", cityPro);
            if (typeIdent.equals("NIT")) {
                required.put("nameCompany", nameCompany);
                required.put("firstNameRep", firstNameRep);
                required.put("firstLastNameRep", firstLastNameRep);
                required.put("dig_ver_producer", dig_ver_producer);
                required.put("emailRes", emailRes);
            } else {
                required.put("names_producer_1", names_producer_1);
                required.put("last_names_producer_1", last_names_producer_1);
            }
//            addActionMessage("Productor adicionado con exito");
//            addActionError("Se debe ingresar por lo menos alguno de los datos de contacto (Telefono fijo, Celular, Correo electrónico)");

//            intercept(ActionInvocation invocation);    
//            required.put("cityPro", cityPro);
            //        } else {
            //          $fields = array("type_ident_producer", "department_producer", "city_producer", "num_ident_producer", "names_producer_1", "last_names_producer_1");
            //        }
//            addFieldError("typeIdent", "El campo es requerido");
//            addFieldError("email_producer", "El campo es requerido");
            //        $fails = array();
            boolean enterFields = false;
            for (Iterator it = required.keySet().iterator(); it.hasNext();) {
                String sK = (String) it.next();
                String sV = (String) required.get(sK);
//                System.out.println(sK + " : " + sV);
//                addFieldError(sK, "El campo es requerido");
                if (StringUtils.trim(sV).equals("null") || StringUtils.trim(sV) == null || StringUtils.trim(sV).equals("") || sV.equals("-1")) {
//                if (StringUtils.trim(sV).equals("") || sV.equals("-1")) {
                    enterFields = true;
                    // if (trim($data[$name])=='') {
//                    System.out.println("name->" + sK);
//                    addFieldError(sK, "El campo " + sK + " es requerido");
                    addFieldError(sK, getText("message.fieldsrequired.producer"));
                }
            }

            if (enterFields) {
                addActionError(getText("message.missingfields.producer"));
            }

            if (this.getCelphone_producer() == null && this.getTelephone_producer() == null && this.getEmail_producer().equals("")) {
                addFieldError("celphone_producer", getText("message.oneofthemrequired.producer"));
                addFieldError("telephone_producer", getText("message.oneofthemrequired.producer"));
                addFieldError("email_producer", getText("message.oneofthemrequired.producer"));
                addActionError(getText("desc.oneofthemrequired.producer"));
            }

            //        System.out.println("errors"+getFieldErrors());            
            if (!this.getEmail_producer().equals("")) {
                if (!ValidatorUtil.validateEmail(this.getEmail_producer())) {
                    addFieldError("email_producer", getText("message.datainvalidemail.producer"));
                    addActionError(getText("desc.datainvalidemail.producer"));
                }
            }

            if (!this.getEmailRes().equals("")) {
                if (!ValidatorUtil.validateEmail(this.getEmailRes())) {
                    addFieldError("emailRes", getText("message.datainvalidemailagent.producer"));
                    addActionError(getText("desc.datainvalidemailagent.producer"));
                }
            }

            if (actExe.equals("create")) {
                if (this.getTypeIdent() != null && this.getNum_ident_producer() != null) {
                    Entities entReg = entDao.checkEntityIdent(this.getTypeIdent(), this.getNum_ident_producer());

                    if (entReg != null) {
                        addFieldError("num_ident_producer", getText("message.documentexists.producer"));
                        addActionError(getText("desc.documentexists.producer"));
                    }
                }
            }
        }
    }

    /**
     * Se obtiene la lista de opciones de cada uno de los tipos de documentos
     * registrados en BD
     *
     * @return lista de tipos de documentos
     */
    public String comboDocumentsTypes() {
        DocumentsTypesDao eventDao = new DocumentsTypesDao();
//        List<Object[]> events = eventDao.findByParams(new String[0]);
        type_ident_producer = eventDao.findAll(coCode);
        return "combo";
    }

    /**
     * Se obtiene la lista de opciones de cada uno de los departamentos
     * registrados en BD
     *
     * @return lista de departamentos
     */
    public String comboDepartments() {
        DepartmentsDao eventDao = new DepartmentsDao();
        department_producer = eventDao.findAll(coCode);
        return "combo";
    }

    /**
     * Se obtiene la lista de opciones de cada uno de los municipios registrados
     * en BD apartir de un departamento seleccionado previamente
     *
     * @param depId: Identificacion de un departamento
     * @return lista de municipios
     */
    public String comboMunicipalities() {
//        int depId = Integer.parseInt(depPro);
        String chain = "<option value=\"\">---</option>";
        if (!this.getRequest().getParameter("depId").equals(" ")) {
            int depId = Integer.parseInt(this.getRequest().getParameter("depId"));
            MunicipalitiesDao eventDao = new MunicipalitiesDao();
            city_producer = eventDao.findAll(depId);
            int i = 0;
            for (Municipalities data : city_producer) {
                //            System.out.println(data.toString());
                chain += "<option value=\"" + data.getIdMun() + "\">" + data.getNameMun() + "</option>";
            }
        }
        city_producer = null;
        state = "success";
        info = chain;
        return "combo";
    }

    private String name_agronomist;
    private String selectAllname_agronomist;
    private String selectItemname_agronomist;

    public String getName_agronomist() {
        return name_agronomist;
    }

    public void setName_agronomist(String name_agronomist) {
        this.name_agronomist = name_agronomist;
    }

    public String getSelectAllname_agronomist() {
        return selectAllname_agronomist;
    }

    public void setSelectAllname_agronomist(String selectAllname_agronomist) {
        this.selectAllname_agronomist = selectAllname_agronomist;
    }

    public String getSelectItemname_agronomist() {
        return selectItemname_agronomist;
    }

    public void setSelectItemname_agronomist(String selectItemname_agronomist) {
        this.selectItemname_agronomist = selectItemname_agronomist;
    }

    /**
     * Encargado de buscar las coincidencias de un formulario de busqueda, para
     * cada una de los productores registrados a un usuario
     *
     * @param valName: Nombre del valor a buscar
     * @param valId: Identificacion del valor a buscar
     * @param selected: Valor seleccionado
     * @return lista de productores
     */
    public String search() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "producer/list")) {
            return BaseAction.NOT_AUTHORIZED;
        }
//        additionals  = new ArrayList<HashMap<String, String>>();
//        HashMap mMap = new HashMap();
        String result = (String) this.getSession().get("result");
        valName = (String) (this.getRequest().getParameter("valName"));
        valId = (String) (this.getRequest().getParameter("valId"));
        selected = (String) (this.getRequest().getParameter("selected"));
        viewInfo = (String) (this.getRequest().getParameter("viewInfo"));
        String selAll = "false";
        if (selected == null) {
            selected = "producer";
            selAll = "true";
        }

        if (selectAllname_agronomist != null) {
            selAll = "true";
        }
        additionals = new HashMap();
        additionals.put("selected", selected);
//        additionals.add(mMap);
//        this.setType_ident_producer(new DocumentsTypesDao().findAll());        
        HashMap findParams = new HashMap();
        findParams.put("selAll", selAll);
        findParams.put("selItem", selectItemname_agronomist);
        Integer entTypeId = entDao.getEntityTypeId(user.getIdUsr());
        findParams.put("entType", entTypeId);
        list_agronomist = assDao.gelAllAgronomist(idEntSystem);
        if (searchFromProducer != null && searchFromProducer == 2) {
            search_producer = "";
        }
//        System.out.println("search_producer->"+search_producer);
        //                required.put("nameCompany", nameCompany);
//                required.put("firstNameRep", firstNameRep);
////                required.put("secondNameRep", secondNameRep);
//                required.put("firstLastNameRep", firstLastNameRep);
////                required.put("secondLastNameRep", secondLastNameRep);
//                required.put("dig_ver_producer", dig_ver_producer);
//                required.put("emailRes", emailRes);        
        findParams.put("idEntUser", idEntSystem);
        findParams.put("search_producer", search_producer);
        findParams.put("nameCompany", nameCompany);
        findParams.put("firstNameRep", firstNameRep);
        findParams.put("typeIdent", typeIdent);
        findParams.put("identProducer", num_ident_producer);
        findParams.put("names_producer_1", names_producer_1);
        findParams.put("last_names_producer_1", last_names_producer_1);
        findParams.put("direction_producer", direction_producer);
        findParams.put("email_producer", email_producer);
        findParams.put("depPro", depPro);
        findParams.put("cityPro", cityPro);
        int pageReq;
        if (this.getRequest().getParameter("page") == null) {
            pageReq = this.getPage();
        } else {
            pageReq = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter("page")));
        }
        findParams.put("pageNow", pageReq);
        findParams.put("maxResults", this.getMaxResults());
        ProducersDao eventDao = new ProducersDao();
//        System.out.println("entreeee");
        listProducers = eventDao.findByParams(findParams);
        Integer valResult = eventDao.countData(findParams);
//        this.setCountTotal(100);
        this.setCountTotal(Integer.parseInt(String.valueOf(listProducers.get(0).get("countTotal"))));
        this.setPage(page);
        listProducers.remove(0);
//        System.out.println("countTotal->"+this.getCountTotal());
        return SUCCESS;
    }

    /**
     * Bloque correspondiente al tratamiento de creacion y lectura de archivos
     *
     */
    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getReport() throws Exception {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "producer/list")) {
            return BaseAction.NOT_AUTHORIZED;
        }

        String selAll = "false";
        if (selectAllname_agronomist != null) {
            selAll = "true";
        }

        HashMap findParams = new HashMap();
        findParams.put("selAll", selAll);
        findParams.put("selItem", selectItemname_agronomist);
        Integer entTypeId = entDao.getEntityTypeId(user.getIdUsr());
        findParams.put("entType", entTypeId);
        findParams.put("idEntUser", idEntSystem);
        String fileName = "" + getText("file.docproducer");
//        String fileName  = "producersInfo.csv";

//        CSVWriter writer = new CSVWriter(new FileWriter(fileName), ';');
//        ResultSet proRes = proDao.getProducers(findParams, fileName);
        proDao.getProducers(findParams, fileName);
//        writer.writeAll(proRes, true);
//        writer.writeNext(entries);  
//        writer.close();  

        File f = new File(fileName);
        inputStream = new FileInputStream(f);
        f.delete();
        return "OUTPUTCSV";
    }

    /**
     * Encargado de mostrar en un formulario la informacion de un productor
     * apartir de la identificacion
     *
     * @param idPro: Identificacion del productor
     * @return Informacion del productor
     */
    public String show() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "producer/create") || !usrDao.getPrivilegeUser(idUsrSystem, "producer/modify")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        actExe = (String) (this.getRequest().getParameter("action"));
        viewInfo = (String) (this.getRequest().getParameter("viewInfo"));
        int pageReq;
        if (this.getRequest().getParameter("page") != null) {
            pageReq = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter("page")));
            this.setPage(pageReq);
        }
        try {
            this.setIdProducer(Integer.parseInt(this.getRequest().getParameter("idPro")));
        } catch (NumberFormatException e) {
            // If there was an error trying to parse the URL parameter
//            LOG.error("There was an error trying to parse the activityId parameter");
            // Set an invalid value to the activityId to prevent the page load in execute function
            this.setIdProducer(-1);
//            return;
        } catch (Exception ex) {
//            actExe = ("");
        }
//        System.out.println("coCode->"+coCode);
        this.setType_ident_producer(new DocumentsTypesDao().findAll(coCode));
        this.setDepartment_producer(new DepartmentsDao().findAll(coCode));

        if (this.getIdProducer() != -1) {
//            ProducersDao eventDao = new ProducersDao();
//            System.out.println("id_productor->"+this.getIdProductor());
            HashMap producerInfo = proDao.findById(this.getIdProducer());
//            System.out.println("valores->" + producerInfo);

            this.setTypeIdent(String.valueOf(producerInfo.get("type_document")));
            this.setNum_ident_producer(String.valueOf(producerInfo.get("document")));

            if (this.getTypeIdent().equals("NIT")) {
                this.setNameCompany(String.valueOf(producerInfo.get("name")));
                this.setDig_ver_producer(String.valueOf(producerInfo.get("digit")));
                this.setFirstNameRep(String.valueOf(producerInfo.get("name_1")));
                this.setSecondNameRep(String.valueOf(producerInfo.get("name_2")));
                this.setFirstLastNameRep(String.valueOf(producerInfo.get("last_name_1")));
                this.setSecondLastNameRep(String.valueOf(producerInfo.get("last_name_2")));
                this.setEmailRes(String.valueOf(producerInfo.get("e_mail_2")));
            } else {
                this.setNames_producer_1(String.valueOf(producerInfo.get("name_1")));
                this.setNames_producer_2(String.valueOf(producerInfo.get("name_2")));
                this.setLast_names_producer_1(String.valueOf(producerInfo.get("last_name_1")));
                this.setLast_names_producer_2(String.valueOf(producerInfo.get("last_name_2")));
            }
            this.setDirection_producer(String.valueOf(producerInfo.get("direction")));
            this.setDepPro(String.valueOf(producerInfo.get("id_dep")));
            this.setCityPro(String.valueOf(producerInfo.get("id_mun")));
            int telAss = (producerInfo.get("phone") != null) ? Integer.parseInt(String.valueOf(producerInfo.get("phone"))) : 0;

            long celAss = (producerInfo.get("cellphone") != null) ? Long.parseLong(String.valueOf(producerInfo.get("cellphone"))) : 0;
            this.setEmail_producer(String.valueOf(producerInfo.get("e_mail_1")));
            this.setTelephone_producer(telAss);
            this.setCelphone_producer(celAss);

            this.setCity_producer(new MunicipalitiesDao().findAll(Integer.parseInt(String.valueOf(producerInfo.get("id_dep")))));
//            this.setCity_producer(new MunicipiosDao().findAll(Integer.parseInt(""+productorInfo.get("id_dep"))));
        } else {
            List<Municipalities> mun = new ArrayList<Municipalities>();
            mun.add(new Municipalities());
            this.setCity_producer(mun);
        }
//        if (this.getIdentProductor()!=null) {        
//        }

        return SUCCESS;
    }

    /**
     * Encargado de guardar la informacion suministrada por el usuario para un
     * lote
     *
     * @return Estado del proceso
     */
    public String saveData() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "producer/create") || !usrDao.getPrivilegeUser(idUsrSystem, "producer/modify")) {
            return BaseAction.NOT_AUTHORIZED;
        }
//        setSelected("producer");
        String action = "";
        /*
         * Se evalua dependiendo a la accion realizada:
         * 1) save: Al momento de guardar un registro por primera ves
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

        try {
            tx = session.beginTransaction();
            SfGuardUserDao sfDao = new SfGuardUserDao();
            SfGuardUser sfUser = sfDao.getUserByLogin(user.getCreatedBy(), user.getNameUserUsr(), "");
            HashMap proData = proDao.findById(idProducer);
            int idEnt = 0;
//            System.out.println("values->"+proData);
            if (!proData.isEmpty() && proData.containsKey("id_entity")) {
                idEnt = ((String.valueOf(proData.get("id_entity"))) != null) ? (Integer.parseInt(String.valueOf(proData.get("id_entity")))) : null;
            }
//            System.out.println("idEnt->"+idEnt);
            int digVer = (dig_ver_producer.equals("")) ? -1 : Integer.parseInt(dig_ver_producer);
            Entities ent = null;
            if (idEnt <= 0) {
                ent = new Entities();
                ent.setIdEnt(null);
                ent.setEntitiesTypes(new EntitiesTypes(2));
            } else {
                ent = entDao.findById(idEnt);
            }

//            ent.setEntityTypeEnt(2);
//            ent.setDocumentsTypes(typeIdent);
            if (typeIdent.equals("NIT")) {
                if (digVer > -1) {
                    ent.setValidationNumberEnt(digVer);
                }
                ent.setNameEnt(nameCompany);
                ent.setAgentNameEnt(firstNameRep + " " + secondNameRep + " " + firstLastNameRep + " " + secondLastNameRep);
                ent.setEmail2Ent(emailRes);
                ent.setFirstName1Ent(firstNameRep);
                ent.setFirstName2Ent(secondNameRep);
                ent.setLastName1Ent(firstLastNameRep);
                ent.setLastName2Ent(secondLastNameRep);
            } else {
                ent.setNameEnt(names_producer_1 + " " + names_producer_2 + " " + last_names_producer_1 + " " + last_names_producer_2);
                ent.setFirstName1Ent(names_producer_1);
                ent.setFirstName2Ent(names_producer_2);
                ent.setLastName1Ent(last_names_producer_1);
                ent.setLastName2Ent(last_names_producer_2);
            }
            ent.setDocumentsTypes(new DocumentsTypes(typeIdent));
            ent.setDocumentNumberEnt(num_ident_producer);

            ent.setEmailEnt(email_producer);
            ent.setAddressEnt(direction_producer);
            ent.setMunicipalities(new Municipalities(Integer.parseInt(cityPro)));
            depPro = String.valueOf(MunicipalitiesDao.getDepartmentId(Integer.parseInt(cityPro)));
//            ent.setIdMunicipalityEnt(new Municipalities(Integer.parseInt(cityPro), new Departments(Integer.parseInt(depPro))));
            if (telephone_producer != null && telephone_producer > 0) {
                ent.setPhoneEnt(telephone_producer);
            }
            if (celphone_producer != null && celphone_producer > 0) {
                ent.setCellphoneEnt(celphone_producer);
            }
            ent.setStatus(true);
            Integer idUserMobile = null;
            if (sfUser != null) {
                idUserMobile = sfUser.getId().intValue();
            }
            ent.setCreatedBy(idUserMobile);
            session.saveOrUpdate(ent);

//            entDao.save(ent);          
//            System.out.println("pruebaCrea");
            if (idProducer <= 0) {
                Producers pro = new Producers();
                pro.setIdPro(null);
                pro.setEntities(ent);
                pro.setStatus(true);
                pro.setAddressPro(direction_producer);
                pro.setCreatedBy(idUserMobile);
                session.saveOrUpdate(pro);
//                proDao.save(pro);

                /*LogEntities logPro = new LogEntities();
                 logPro.setIdLogEnt(null);
                 logPro.setIdEntityLogEnt(idEntSystem); 
                 logPro.setIdObjectLogEnt(pro.getIdPro());
                 logPro.setTableLogEnt("producers");
                 logPro.setDateLogEnt(new Date());
                 logPro.setActionTypeLogEnt(action);
                 session.saveOrUpdate(logPro);
                 idProducer = pro.getIdPro();*/
//                logDao.save(logPro);
            }

            /*LogEntities log = new LogEntities();
             log.setIdLogEnt(null);
             log.setIdEntityLogEnt(idEntSystem); 
             log.setIdObjectLogEnt(ent.getIdEnt());
             log.setTableLogEnt("entities");
             log.setDateLogEnt(new Date());
             log.setActionTypeLogEnt(action);
             session.saveOrUpdate(log);*/
//            logDao.save(log);                   
//            HashMap producerInfo = proDao.findById(idProducer);
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
             */
            //Manejo para ingresar datos en MongoDB            
            HashMap valInfo = new HashMap();
            valInfo.put("entId", ent.getIdEnt());
            valInfo.put("prodId", idProducer);
            valInfo.put("docType", typeIdent);
            valInfo.put("docNum", ent.getDocumentNumberEnt());
            valInfo.put("firstName1", ent.getFirstName1Ent());
            valInfo.put("firstName2", ent.getFirstName2Ent());
            valInfo.put("lastName1", ent.getLastName1Ent());
            valInfo.put("lastName2", ent.getLastName2Ent());
            valInfo.put("direction", ent.getAddressEnt());
            valInfo.put("phone", telephone_producer);
            valInfo.put("cellphone", celphone_producer);
            valInfo.put("email", ent.getEmailEnt());
            valInfo.put("validation", digVer);
            valInfo.put("department", depPro);
            valInfo.put("municipality", cityPro);
            valInfo.put("userMobileId", idUserMobile);

            BasicDBObject query = new BasicDBObject();
            query.put("InsertedId", "" + ent.getIdEnt());
            query.put("form_id", "4");

            MongoClient mongo = null;
            try {
                mongo = new MongoClient("localhost", 27017);
            } catch (UnknownHostException ex) {
                Logger.getLogger(ActionField.class.getName()).log(Level.SEVERE, null, ex);
            }
            DB db = mongo.getDB("ciat");
            DBCollection col = db.getCollection("log_form_records");

            DBCursor cursor = col.find(query);
            WriteResult result = null;
            BasicDBObject jsonField = null;
//            jsonField = GlobalFunctions.generateJSONProducer(valInfo);

//            while(cursor.hasNext()) {
//                System.out.println(cursor.next());
//            }
            if (cursor.count() > 0) {
                System.out.println("actualizo mongo");
//                result = col.update(query, jsonField);
            } else {
                System.out.println("inserto mongo");
//                result = col.insert(jsonField);
            }

//            if (result.getError() != null) {
//                throw new HibernateException("");
//            }

            mongo.close();
            tx.commit();
            state = "success";
            if (action.equals("C")) {
                info = getText("message.successadd.producer");
//                return "list";
            } else if (action.equals("M")) {
                info = getText("message.successedit.producer");
//                return "list";
            }
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
//            System.out.println("error->"+e.getMessage());
            state = "failure";
            if (action.equals("C")) {
                info = getText("message.failadd.producer");
            } else if (action.equals("M")) {
                info = getText("message.failedit.producer");
            }
        } finally {
            session.close();
        }

        return "states";
//        return ERROR;
    }

    /**
     * Encargado de borrar la informacion de un productor apartir de su
     * identificacion
     *
     * @param idPro: Identificacion del productor
     * @return Estado del proceso
     */
    public String delete() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "producer/delete")) {
            return BaseAction.NOT_AUTHORIZED;
        }
//        System.out.println("idUsr->"+user.getIdUsr());
//        idEntSystem = UsersDao.getEntitySystem(user.getIdUsr());
        Integer idPro = 0;
        try {
            idPro = Integer.parseInt(this.getRequest().getParameter("idPro"));
        } catch (NumberFormatException e) {
            idPro = -1;
        }

        if (idPro == -1) {
            state = "failure";
            info = getText("message.failgetinfo.producer");
            return "states";
        }

        SessionFactory sessions = HibernateUtil.getSessionFactory();
        Session session = sessions.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            HashMap pro = proDao.findById(idPro);
//            System.out.println("dataPro->"+pro);
            Entities ent = entDao.findById(Integer.parseInt(String.valueOf(pro.get("id_entity"))));
//            System.out.println("idEnt->"+pro.get("id_entity"));
//            System.out.println("numEnt->"+ent.getDocumentNumberEnt());
            ent.setStatus(false);
            session.saveOrUpdate(ent);//Tambien si fuera el caso borrar datos de la tabla intermedia farms_producers y fields_producers
//            session.delete(ent);

//            Producers proObj = new Producers();
//            proObj.setIdPro(idPro);
//            proObj.setEntities(ent);
//            proObj.setStatus(false);     
//            session.saveOrUpdate(proObj);
//            LogEntities log = new LogEntities();
//            log.setIdLogEnt(null);
//            log.setIdEntityLogEnt(idEntSystem); //Colocar el usuario registrado en el sistema
//            log.setIdObjectLogEnt(ent.getIdEnt());
//            log.setTableLogEnt("entities");
//            log.setDateLogEnt(new Date());
//            log.setActionTypeLogEnt("D");
//            session.saveOrUpdate(log);
//            logDao.save(log);
//            
//            LogEntities logPro = new LogEntities();
//            logPro.setIdLogEnt(null);
//            logPro.setIdEntityLogEnt(idEntSystem);
//            logPro.setIdObjectLogEnt(idPro);
//            logPro.setTableLogEnt("producers");
//            logPro.setDateLogEnt(new Date());
//            logPro.setActionTypeLogEnt("D");
//            session.saveOrUpdate(logPro);
            BasicDBObject query = new BasicDBObject();
            query.put("InsertedId", "" + ent.getIdEnt());
            query.put("form_id", "4");

            MongoClient mongo = null;
            try {
                mongo = new MongoClient("localhost", 27017);
            } catch (UnknownHostException ex) {
                Logger.getLogger(ActionField.class.getName()).log(Level.SEVERE, null, ex);
            }
            DB db = mongo.getDB("ciat");
            DBCollection col = db.getCollection("log_form_records");
            WriteResult result = null;

            System.out.println("borro mongo");
            result = col.remove(query);

            if (result.getError() != null) {
                throw new HibernateException("");
            }
            mongo.close();

            FarmsDao farDao = new FarmsDao();
            farDao.deleteFarmsMongo(idPro);

//            logDao.save(logPro);
            tx.commit();
            state = "success";
            info  = getText("message.successdelete.producer");
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            state = "failure";
            info  = getText("message.faildelete.producer");
        } finally {
            session.close();
        }

        return "states";
//        return SUCCESS;
    }

    /**
     * Encargado de borrar la informacion de los productores que se han
     * seleccionado
     *
     * @param valSel: Valores que se han seleccionado para borrar
     * @return Estado del proceso
     */
    public String deleteAll() {
        if (!usrDao.getPrivilegeUser(idUsrSystem, "producer/delete")) {
            return BaseAction.NOT_AUTHORIZED;
        }
        String valSel = "";        
        try {
            valSel = String.valueOf(this.getRequest().getParameter("valSel"));
        } catch (NumberFormatException e) {
            valSel = "-1";
        }
        
        if (valSel.equals("-1")) {
            state = "failure";
            info  = getText("message.failgetinfo.producer");
            return "states";
        }
        
        state = proDao.deleteAllEntities(valSel);
        if (state.equals("success")) {
            info  = getText("message.successalldelete.producer");
        } else if (state.equals("failure")) {
            info  = getText("message.failalldelete.producer");
        }
        
//        state = "success";
        return "states";
    }

}
