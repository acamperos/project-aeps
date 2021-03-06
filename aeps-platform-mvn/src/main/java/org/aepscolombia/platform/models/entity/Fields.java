package org.aepscolombia.platform.models.entity;
// Generated Apr 8, 2014 9:37:27 AM by Hibernate Tools 3.2.1.GA

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.aepscolombia.platform.util.HibernateUtil;

/**
 * Fields generated by hbm2java
 */
@Entity
@Table(name="fields")
public class Fields  implements java.io.Serializable {


     private Integer idFie;
     private FieldTypes fieldTypes;
     private Farms farms;
     private String nameFie;
     private double altitudeFie;
     private double latitudeFie;
     private double longitudeFie;
     private double areaFie;
     private String measureUnitFie;
     private Boolean pestsControlFie;
     private Boolean diseasesControlFie;
     private boolean status;
     private String idProjectFie;
     private Integer createdBy;

    public Fields() {
    }

    public Fields(Integer idFie) {
        this.idFie=idFie;
    }
	
    public Fields(String nameFie, double altitudeFie, double latitudeFie, double longitudeFie, double areaFie, boolean status) {
        this.nameFie = nameFie;
        this.altitudeFie = altitudeFie;
        this.latitudeFie = latitudeFie;
        this.longitudeFie = longitudeFie;
        this.areaFie = areaFie;
        this.status = status;
    }
    public Fields(FieldTypes fieldTypes, Farms farms, String nameFie, double altitudeFie, double latitudeFie, double longitudeFie, double areaFie, String measureUnitFie, Boolean pestsControlFie, Boolean diseasesControlFie, boolean status, String idProjectFie, Integer createdBy) {
       this.fieldTypes = fieldTypes;
       this.farms = farms;
       this.nameFie = nameFie;
       this.altitudeFie = altitudeFie;
       this.latitudeFie = latitudeFie;
       this.longitudeFie = longitudeFie;
       this.areaFie = areaFie;
       this.measureUnitFie = measureUnitFie;
       this.pestsControlFie = pestsControlFie;
       this.diseasesControlFie = diseasesControlFie;
       this.status = status;
       this.idProjectFie = idProjectFie;
       this.createdBy = createdBy;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_fie", unique=true, nullable=false)
    public Integer getIdFie() {
        return this.idFie;
    }
    
    public void setIdFie(Integer idFie) {
        this.idFie = idFie;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="contract_type_fie")
    public FieldTypes getFieldTypes() {
        return this.fieldTypes;
    }
    
    public void setFieldTypes(FieldTypes fieldTypes) {
        this.fieldTypes = fieldTypes;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_farm_fie")
    public Farms getFarms() {
        return this.farms;
    }
    
    public void setFarms(Farms farms) {
        this.farms = farms;
    }
    
    @Column(name="name_fie", nullable=false, length=45)
    public String getNameFie() {
        return this.nameFie;
    }
    
    public void setNameFie(String nameFie) {
        this.nameFie = nameFie;
    }
    
    @Column(name="altitude_fie", nullable=false, precision=22, scale=0)
    public double getAltitudeFie() {
        return this.altitudeFie;
    }
    
    public void setAltitudeFie(double altitudeFie) {
        this.altitudeFie = altitudeFie;
    }
    
    @Column(name="latitude_fie", nullable=false, precision=22, scale=0)
    public double getLatitudeFie() {
        return this.latitudeFie;
    }
    
    public void setLatitudeFie(double latitudeFie) {
        this.latitudeFie = latitudeFie;
    }
    
    @Column(name="longitude_fie", nullable=false, precision=22, scale=0)
    public double getLongitudeFie() {
        return this.longitudeFie;
    }
    
    public void setLongitudeFie(double longitudeFie) {
        this.longitudeFie = longitudeFie;
    }
    
    @Column(name="area_fie", precision=22, scale=0)
    public double getAreaFie() {
        return this.areaFie;
    }
    
    public void setAreaFie(double areaFie) {
        this.areaFie = areaFie;
    }
    
    @Column(name="measure_unit_fie", length=10)
    public String getMeasureUnitFie() {
        return this.measureUnitFie;
    }
    
    public void setMeasureUnitFie(String measureUnitFie) {
        this.measureUnitFie = measureUnitFie;
    }
    
    @Column(name="pests_control_fie")
    public Boolean getPestsControlFie() {
        return this.pestsControlFie;
    }
    
    public void setPestsControlFie(Boolean pestsControlFie) {
        this.pestsControlFie = pestsControlFie;
    }
    
    @Column(name="diseases_control_fie")
    public Boolean getDiseasesControlFie() {
        return this.diseasesControlFie;
    }
    
    public void setDiseasesControlFie(Boolean diseasesControlFie) {
        this.diseasesControlFie = diseasesControlFie;
    }
    
    @Column(name="status", nullable=false)
    public boolean isStatus() {
        return this.status;
    }
    
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    @Column(name="id_project_fie", length=4)
    public String getIdProjectFie() {
        return this.idProjectFie;
    }
    
    public void setIdProjectFie(String idProjectFie) {
        this.idProjectFie = idProjectFie;
    }
    
    @Column(name="created_by")
    public Integer getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }   

}


