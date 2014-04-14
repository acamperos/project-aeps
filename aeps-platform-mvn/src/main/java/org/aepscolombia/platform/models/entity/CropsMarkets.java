package org.aepscolombia.platform.models.entity;
// Generated Apr 8, 2014 9:37:27 AM by Hibernate Tools 3.2.1.GA


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * CropsMarkets generated by hbm2java
 */
@Entity
@Table(name="crops_markets"
    ,catalog="madr_bd13"
)
public class CropsMarkets  implements java.io.Serializable {


     private CropsMarketsId id;
     private Markets markets;
     private CropsTypes cropsTypes;
     private Boolean status;
     private Integer createdBy;

    public CropsMarkets() {
    }

	
    public CropsMarkets(CropsMarketsId id, Markets markets, CropsTypes cropsTypes) {
        this.id = id;
        this.markets = markets;
        this.cropsTypes = cropsTypes;
    }
    public CropsMarkets(CropsMarketsId id, Markets markets, CropsTypes cropsTypes, Boolean status, Integer createdBy) {
       this.id = id;
       this.markets = markets;
       this.cropsTypes = cropsTypes;
       this.status = status;
       this.createdBy = createdBy;
    }
   
     @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idCropTypeCroMar", column=@Column(name="id_crop_type_cro_mar", nullable=false) ), 
        @AttributeOverride(name="idMarketCroMar", column=@Column(name="id_market_cro_mar", nullable=false) ) } )
    public CropsMarketsId getId() {
        return this.id;
    }
    
    public void setId(CropsMarketsId id) {
        this.id = id;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_market_cro_mar", nullable=false, insertable=false, updatable=false)
    public Markets getMarkets() {
        return this.markets;
    }
    
    public void setMarkets(Markets markets) {
        this.markets = markets;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_crop_type_cro_mar", nullable=false, insertable=false, updatable=false)
    public CropsTypes getCropsTypes() {
        return this.cropsTypes;
    }
    
    public void setCropsTypes(CropsTypes cropsTypes) {
        this.cropsTypes = cropsTypes;
    }
    
    @Column(name="status")
    public Boolean getStatus() {
        return this.status;
    }
    
    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    @Column(name="created_by")
    public Integer getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }




}


