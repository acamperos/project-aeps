package org.aepscolombia.platform.models.entity;
// Generated Apr 8, 2014 9:37:27 AM by Hibernate Tools 3.2.1.GA

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * IrrigationsTypes generated by hbm2java
 */
@Entity
@Table(name="irrigations_types")
public class IrrigationsTypes  implements java.io.Serializable {


     private Integer idIrrTyp;
     private String nameIrrTyp;
     private boolean statusIrrTyp;
     private IdiomCountry countryIrrTyp;

    public IrrigationsTypes() {
    }

	
    public IrrigationsTypes(String nameIrrTyp, boolean statusIrrTyp) {
        this.nameIrrTyp = nameIrrTyp;
        this.statusIrrTyp = statusIrrTyp;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_irr_typ", unique=true, nullable=false)
    public Integer getIdIrrTyp() {
        return this.idIrrTyp;
    }
    
    public void setIdIrrTyp(Integer idIrrTyp) {
        this.idIrrTyp = idIrrTyp;
    }
    
    @Column(name="name_irr_typ", nullable=false, length=45)
    public String getNameIrrTyp() {
        return this.nameIrrTyp;
    }
    
    public void setNameIrrTyp(String nameIrrTyp) {
        this.nameIrrTyp = nameIrrTyp;
    }
    
    @Column(name="status_irr_typ", nullable=false)
    public boolean isStatusIrrTyp() {
        return this.statusIrrTyp;
    }
    
    public void setStatusIrrTyp(boolean statusIrrTyp) {
        this.statusIrrTyp = statusIrrTyp;
    }
    
    @ManyToOne
    @JoinColumn(name="country_irr_typ")
    public IdiomCountry getCountryIrrTyp() {
        return this.countryIrrTyp;
    }
    
    public void setCountryIrrTyp(IdiomCountry countryIrrTyp) {
        this.countryIrrTyp = countryIrrTyp;
    }

}


