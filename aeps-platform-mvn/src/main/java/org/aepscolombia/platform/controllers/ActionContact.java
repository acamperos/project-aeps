package org.aepscolombia.platform.controllers;

import org.aepscolombia.platform.models.dao.EntitiesDao;
import org.aepscolombia.platform.models.dao.FarmsDao;
import org.aepscolombia.platform.models.dao.FieldsDao;
import org.aepscolombia.platform.models.dao.ProductionEventsDao;
import org.aepscolombia.platform.models.dao.RastasDao;


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
    
    public String chargeValues() {
        EntitiesDao entDao = new EntitiesDao();
        FarmsDao farDao    = new FarmsDao();
        FieldsDao fieDao   = new FieldsDao();
        ProductionEventsDao cropDao = new ProductionEventsDao();
        RastasDao rasDao   = new RastasDao();
        
        entDao.setInfoMongo();
        farDao.setInfoMongo();
        fieDao.setInfoMongo();
        cropDao.setInfoMongo();
        rasDao.setInfoMongo();
        
        return "states";
    }
}