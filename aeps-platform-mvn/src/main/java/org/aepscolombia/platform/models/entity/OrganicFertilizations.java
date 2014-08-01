package org.aepscolombia.platform.models.entity;
// Generated Apr 8, 2014 9:37:27 AM by Hibernate Tools 3.2.1.GA


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
 * OrganicFertilizations generated by hbm2java
 */
@Entity
@Table(name="organic_fertilizations")
public class OrganicFertilizations  implements java.io.Serializable {


     private Integer idOrgFer;
     private OrganicFertilizers organicFertilizers;
     private Fertilizations fertilizations;
     private String otherProductOrgFer;
     private Boolean status;
     private Integer createdBy;
     private Double amountProductUsedOrgFer;

    public OrganicFertilizations() {
    }

	
    public OrganicFertilizations(Fertilizations fertilizations) {
        this.fertilizations = fertilizations;
    }
    public OrganicFertilizations(OrganicFertilizers organicFertilizers, Fertilizations fertilizations, String otherProductOrgFer, Double amountProductUsedOrgFer, Boolean status, Integer createdBy) {
       this.organicFertilizers = organicFertilizers;
       this.fertilizations = fertilizations;
       this.otherProductOrgFer = otherProductOrgFer;
       this.amountProductUsedOrgFer = amountProductUsedOrgFer;
       this.status = status;
       this.createdBy = createdBy;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_org_fer", unique=true, nullable=false)
    public Integer getIdOrgFer() {
        return this.idOrgFer;
    }
    
    public void setIdOrgFer(Integer idOrgFer) {
        this.idOrgFer = idOrgFer;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_product_org_fer")
    public OrganicFertilizers getOrganicFertilizers() {
        return this.organicFertilizers;
    }
    
    public void setOrganicFertilizers(OrganicFertilizers organicFertilizers) {
        this.organicFertilizers = organicFertilizers;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_fertilization_org_fer", nullable=false)
    public Fertilizations getFertilizations() {
        return this.fertilizations;
    }
    
    public void setFertilizations(Fertilizations fertilizations) {
        this.fertilizations = fertilizations;
    }
    
    @Column(name="amount_product_used_org_fer", nullable=false, precision=22, scale=0)
    public Double getAmountProductUsedOrgFer() {
        return this.amountProductUsedOrgFer;
    }
    
    public void setAmountProductUsedOrgFer(Double amountProductUsedOrgFer) {
        this.amountProductUsedOrgFer = amountProductUsedOrgFer;
    }
    
    @Column(name="other_product_org_fer", length=65535)
    public String getOtherProductOrgFer() {
        return this.otherProductOrgFer;
    }
    
    public void setOtherProductOrgFer(String otherProductOrgFer) {
        this.otherProductOrgFer = otherProductOrgFer;
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


