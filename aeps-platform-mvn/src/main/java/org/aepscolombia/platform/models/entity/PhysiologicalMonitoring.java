package org.aepscolombia.platform.models.entity;
// Generated Apr 8, 2014 9:37:27 AM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PhysiologicalMonitoring generated by hbm2java
 */
@Entity
@Table(name="physiological_monitoring")
public class PhysiologicalMonitoring  implements java.io.Serializable {


     private Integer idPhyMon;
     private ProductionEvents productionEvents;
     private Monitoring monitoring;
     private Date emergencePhyMon;
     private Integer daysPopulationMonFis;
     private Date iniPrimorPhyMon;
     private Date floweringDatePhyMon;
     private Boolean status;
     private Integer createdBy;
     private Double percentageReseedingPhyMon;

    public PhysiologicalMonitoring() {
    }

    public PhysiologicalMonitoring(ProductionEvents productionEvents, Monitoring monitoring, Date emergencePhyMon, Integer daysPopulationMonFis, Date floweringDatePhyMon, Double percentageReseedingPhyMon, Boolean status, Integer createdBy) {
       this.productionEvents = productionEvents;
       this.monitoring = monitoring;
       this.emergencePhyMon = emergencePhyMon;
       this.daysPopulationMonFis = daysPopulationMonFis;
       this.floweringDatePhyMon = floweringDatePhyMon;
       this.percentageReseedingPhyMon = percentageReseedingPhyMon;
       this.status = status;
       this.createdBy = createdBy;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_phy_mon", unique=true, nullable=false)
    public Integer getIdPhyMon() {
        return this.idPhyMon;
    }
    
    public void setIdPhyMon(Integer idPhyMon) {
        this.idPhyMon = idPhyMon;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_production_event_phy_mon")
    public ProductionEvents getProductionEvents() {
        return this.productionEvents;
    }
    
    public void setProductionEvents(ProductionEvents productionEvents) {
        this.productionEvents = productionEvents;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_monitoring_phy_mon")
    public Monitoring getMonitoring() {
        return this.monitoring;
    }
    
    public void setMonitoring(Monitoring monitoring) {
        this.monitoring = monitoring;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="emergence_phy_mon", length=19)
    public Date getEmergencePhyMon() {
        return this.emergencePhyMon;
    }
    
    public void setEmergencePhyMon(Date emergencePhyMon) {
        this.emergencePhyMon = emergencePhyMon;
    }
    
    @Column(name="20_days_population_mon_fis")
    public Integer getDaysPopulationMonFis() {
        return this.daysPopulationMonFis;
    }
    
    public void setDaysPopulationMonFis(Integer daysPopulationMonFis) {
        this.daysPopulationMonFis = daysPopulationMonFis;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="flowering_date_phy_mon", length=19)
    public Date getFloweringDatePhyMon() {
        return this.floweringDatePhyMon;
    }
    
    public void setFloweringDatePhyMon(Date floweringDatePhyMon) {
        this.floweringDatePhyMon = floweringDatePhyMon;
    }
    
    @Column(name="percentage_reseeding_phy_mon", precision=22, scale=0)
    public Double getPercentageReseedingPhyMon() {
        return this.percentageReseedingPhyMon;
    }
    
    public void setPercentageReseedingPhyMon(Double percentageReseedingPhyMon) {
        this.percentageReseedingPhyMon = percentageReseedingPhyMon;
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
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ini_primor_phy_mon", length=19)
    public Date getIniPrimorPhyMon() {
        return this.iniPrimorPhyMon;
    }
    
    public void setIniPrimorPhyMon(Date iniPrimorPhyMon) {
        this.iniPrimorPhyMon = iniPrimorPhyMon;
    }


}


