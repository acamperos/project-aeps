package org.aepscolombia.platform.models.entity;
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

@Entity
@Table(name="cost_indirect_production_event")
public class CostIndirectProductionEvent  implements java.io.Serializable {

    private Integer idCostIndPro;
    private ProductionEvents productionEvents;
    
    private BigDecimal costVigilantPro;
    private BigDecimal costRentPro;
    private BigDecimal costTechnicalAssistancePro;
    private BigDecimal costImpuestoPro;
    private BigDecimal costAdministrationPro;
    private BigDecimal costOthersPro;
    private BigDecimal costInterestsPro;
    
    
    private Boolean status;
    private Integer createdBy;

    public CostIndirectProductionEvent() {
    }
	
    public CostIndirectProductionEvent(ProductionEvents productionEvents) {
        this.productionEvents = productionEvents;
        
    }
    
    public CostIndirectProductionEvent(Integer idCostIndPro, ProductionEvents productionEvents, BigDecimal costVigilantPro, BigDecimal costRentPro, BigDecimal costTechnicalAssistancePro, BigDecimal costImpuestoPro, BigDecimal costAdministrationPro, BigDecimal costOthersPro, BigDecimal costInterestsPro, Boolean status, Integer createdBy) {
        this.idCostIndPro = idCostIndPro;
        this.productionEvents = productionEvents;
        this.costVigilantPro = costVigilantPro;
        this.costRentPro = costRentPro;
        this.costTechnicalAssistancePro = costTechnicalAssistancePro;
        this.costImpuestoPro = costImpuestoPro;
        this.costAdministrationPro = costAdministrationPro;
        this.costOthersPro = costOthersPro;
        this.costInterestsPro = costInterestsPro;
        this.status = status;
        this.createdBy = createdBy;
    }
       
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_cost_ind_pro", unique=true, nullable=false)
    public Integer getIdCostIndPro() {
        return this.idCostIndPro;
    }
    
    public void setIdCostIndPro(Integer idCostIndPro) {
        this.idCostIndPro = idCostIndPro;
    }    
        
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_production_event_cost_ind", nullable=false)
    public ProductionEvents getProductionEvents() {
        return this.productionEvents;
    }
    
    public void setProductionEvents(ProductionEvents productionEvents) {
        this.productionEvents = productionEvents;
    }
    
    @Column(name="cost_vigilant_pro")
    public BigDecimal getCostVigilantPro(){
            return this.costVigilantPro;
    }
    
    public void setCostVigilantPro(BigDecimal costVigilantPro){
            this.costVigilantPro=costVigilantPro;
    
    }
    
    @Column(name="cost_rent_pro")
    public BigDecimal getCostRentPro(){
            return this.costRentPro;
    }
    
    public void setCostRentPro(BigDecimal costRentPro){
            this.costRentPro=costRentPro;
    
    }
    
     @Column(name="cost_technical_assistance_pro")
    public BigDecimal getCostTechnicalAssistancePro(){
            return this.costTechnicalAssistancePro;
    }
    
    public void setCostTechnicalAssistancePro(BigDecimal costTechnicalAssistancePro){
            this.costTechnicalAssistancePro=costTechnicalAssistancePro;
    
    }
    
     @Column(name="cost_impuesto_pro")
    public BigDecimal getCostImpuestoPro(){
            return this.costImpuestoPro;
    }
    
    public void setCostImpuestoPro(BigDecimal costImpuestoPro){
            this.costImpuestoPro=costImpuestoPro;
    
    }
    
     @Column(name="cost_administration_pro")
    public BigDecimal getCostAdministrationPro(){
            return this.costAdministrationPro;
    }
    
    public void setCostAdministrationPro(BigDecimal costAdministrationPro){
            this.costAdministrationPro=costAdministrationPro;
    
    }
    
     @Column(name="cost_others_pro")
    public BigDecimal getCostOthersPro(){
            return this.costOthersPro;
    }
    
    public void setCostOthersPro(BigDecimal costOthersPro){
            this.costOthersPro=costOthersPro;
    
    }
    
     @Column(name="cost_interests_pro")
    public BigDecimal getCostInterestsPro(){
            return this.costInterestsPro;
    }
    
    public void setCostInterestsPro(BigDecimal costInterestsPro){
            this.costInterestsPro=costInterestsPro;
    
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


