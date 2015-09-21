package org.aepscolombia.platform.models.entity;
// Generated Apr 8, 2014 9:37:27 AM by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ChemicalFertilizations generated by hbm2java
 */
@Entity
@Table(name="chemical_fertilizations")
public class ChemicalFertilizations  implements java.io.Serializable {


     private Integer idCheFer;
     private ChemicalFertilizers chemicalFertilizers;
     private ApplicationTypes applicationTypes;
     private Fertilizations fertilizations;
     private Integer unitCheFer;
     private String otherProductCheFer;
     private Boolean status;
     private Integer createdBy;
     private Double amountProductUsedCheFer;
     private BigDecimal costProductCheFer;
     private Integer costFormAppCheFer;
     private BigDecimal costAppCheFer;
     
    
    public ChemicalFertilizations() {
    }

	
    public ChemicalFertilizations(Fertilizations fertilizations) {
        this.fertilizations = fertilizations;
    }
    public ChemicalFertilizations(ChemicalFertilizers chemicalFertilizers, ApplicationTypes applicationTypes, Fertilizations fertilizations, Integer unitCheFer, String otherProductCheFer, Double amountProductUsedCheFer, Boolean status, Integer createdBy, BigDecimal costProductCheFer, Integer costFormAppCheFer, BigDecimal costAppCheFer) {
       this.chemicalFertilizers = chemicalFertilizers;
       this.applicationTypes = applicationTypes;
       this.fertilizations = fertilizations;
       this.unitCheFer = unitCheFer;
       this.otherProductCheFer = otherProductCheFer;
       this.amountProductUsedCheFer = amountProductUsedCheFer;       
       this.costProductCheFer   = costProductCheFer;
       this.costFormAppCheFer   = costFormAppCheFer;
       this.costAppCheFer   =   costAppCheFer;       
       this.status = status;
       this.createdBy = createdBy;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_che_fer", unique=true, nullable=false)
    public Integer getIdCheFer() {
        return this.idCheFer;
    }
    
    public void setIdCheFer(Integer idCheFer) {
        this.idCheFer = idCheFer;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_product_che_fer")
    public ChemicalFertilizers getChemicalFertilizers() {
        return this.chemicalFertilizers;
    }
    
    public void setChemicalFertilizers(ChemicalFertilizers chemicalFertilizers) {
        this.chemicalFertilizers = chemicalFertilizers;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="application_type_che_fer")
    public ApplicationTypes getApplicationTypes() {
        return this.applicationTypes;
    }
    
    public void setApplicationTypes(ApplicationTypes applicationTypes) {
        this.applicationTypes = applicationTypes;
    }
    
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_fertilization_che_fer", nullable=false)
    public Fertilizations getFertilizations() {
        return this.fertilizations;
    }
    
    public void setFertilizations(Fertilizations fertilizations) {
        this.fertilizations = fertilizations;
    }
    
    @Column(name="unit_che_fer")
    public Integer getUnitCheFer() {
        return this.unitCheFer;
    }
    
    public void setUnitCheFer(Integer unitCheFer) {
        this.unitCheFer = unitCheFer;
    }
    
    @Column(name="amount_product_used_che_fer", precision=22, scale=0)
    public Double getAmountProductUsedCheFer() {
        return this.amountProductUsedCheFer;
    }
    
    public void setAmountProductUsedCheFer(Double amountProductUsedCheFer) {
        this.amountProductUsedCheFer = amountProductUsedCheFer;
    }
    
    @Column(name="other_product_che_fer", length=65535)
    public String getOtherProductCheFer() {
        return this.otherProductCheFer;
    }
    
    public void setOtherProductCheFer(String otherProductCheFer) {
        this.otherProductCheFer = otherProductCheFer;
    }
    
    
    @Column(name="cost_product_che_fer")
    public BigDecimal getCostProductCheFer() {
        return this.costProductCheFer;
    }
    
    public void setCostProductCheFer(BigDecimal costProductCheFer) {
        this.costProductCheFer = costProductCheFer;
    }
    
    @Column(name="cost_form_app_che_fer")
    public Integer getCostFormAppCheFer() {
        return this.costFormAppCheFer;
    }
    
    public void setCostFormAppCheFer(Integer costFormAppCheFer) {
        this.costFormAppCheFer = costFormAppCheFer;
    }
     
    @Column(name="cost_app_che_fer")
    public BigDecimal getCostAppCheFer() {
        return this.costAppCheFer;
    }
    
    public void setCostAppCheFer(BigDecimal costAppCheFer) {
        this.costAppCheFer = costAppCheFer;
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


