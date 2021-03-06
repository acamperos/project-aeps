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
 * ChemicalFertilizerComposition generated by hbm2java
 */
@Entity
@Table(name="chemical_fertilizer_composition")
public class ChemicalFertilizerComposition  implements java.io.Serializable {


     private Integer idCheFerCom;
     private ChemicalElements chemicalElements;
     private ChemicalFertilizers chemicalFertilizers;
     private double percentageCheFerCom;
     private Double rawElementQuantityCheFerCom;

    public ChemicalFertilizerComposition() {
    }

	
    public ChemicalFertilizerComposition(ChemicalElements chemicalElements, ChemicalFertilizers chemicalFertilizers, double percentageCheFerCom) {
        this.chemicalElements = chemicalElements;
        this.chemicalFertilizers = chemicalFertilizers;
        this.percentageCheFerCom = percentageCheFerCom;
    }
    public ChemicalFertilizerComposition(ChemicalElements chemicalElements, ChemicalFertilizers chemicalFertilizers, double percentageCheFerCom, Double rawElementQuantityCheFerCom) {
       this.chemicalElements = chemicalElements;
       this.chemicalFertilizers = chemicalFertilizers;
       this.percentageCheFerCom = percentageCheFerCom;
       this.rawElementQuantityCheFerCom = rawElementQuantityCheFerCom;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_che_fer_com", unique=true, nullable=false)
    public Integer getIdCheFerCom() {
        return this.idCheFerCom;
    }
    
    public void setIdCheFerCom(Integer idCheFerCom) {
        this.idCheFerCom = idCheFerCom;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_elements_che_fer_com", nullable=false)
    public ChemicalElements getChemicalElements() {
        return this.chemicalElements;
    }
    
    public void setChemicalElements(ChemicalElements chemicalElements) {
        this.chemicalElements = chemicalElements;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_chemical_fertilizer_che_fer_com", nullable=false)
    public ChemicalFertilizers getChemicalFertilizers() {
        return this.chemicalFertilizers;
    }
    
    public void setChemicalFertilizers(ChemicalFertilizers chemicalFertilizers) {
        this.chemicalFertilizers = chemicalFertilizers;
    }
    
    @Column(name="percentage_che_fer_com", nullable=false, precision=22, scale=0)
    public double getPercentageCheFerCom() {
        return this.percentageCheFerCom;
    }
    
    public void setPercentageCheFerCom(double percentageCheFerCom) {
        this.percentageCheFerCom = percentageCheFerCom;
    }
    
    @Column(name="raw_element_quantity_che_fer_com", precision=22, scale=0)
    public Double getRawElementQuantityCheFerCom() {
        return this.rawElementQuantityCheFerCom;
    }
    
    public void setRawElementQuantityCheFerCom(Double rawElementQuantityCheFerCom) {
        this.rawElementQuantityCheFerCom = rawElementQuantityCheFerCom;
    }




}


