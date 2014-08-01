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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * CropsTypes generated by hbm2java
 */
@Entity
@Table(name="crops_types")
public class CropsTypes  implements java.io.Serializable {


     private Integer idCroTyp;
     private String nameCroTyp;
     private Boolean statusCroTyp;

    public CropsTypes() {
    }
    
    public CropsTypes(Integer idCroTyp) {
        this.idCroTyp = idCroTyp;
    }
    
    public CropsTypes(Integer idCroTyp, String nameCroTyp) {
       this.idCroTyp = idCroTyp;
       this.nameCroTyp = nameCroTyp;
    }

    public CropsTypes(String nameCroTyp, Boolean statusCroTyp) {
       this.nameCroTyp = nameCroTyp;
       this.statusCroTyp = statusCroTyp;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_cro_typ", unique=true, nullable=false)
    public Integer getIdCroTyp() {
        return this.idCroTyp;
    }
    
    public void setIdCroTyp(Integer idCroTyp) {
        this.idCroTyp = idCroTyp;
    }
    
    @Column(name="name_cro_typ", length=45)
    public String getNameCroTyp() {
        return this.nameCroTyp;
    }
    
    public void setNameCroTyp(String nameCroTyp) {
        this.nameCroTyp = nameCroTyp;
    }
    
    @Column(name="status_cro_typ")
    public Boolean getStatusCroTyp() {
        return this.statusCroTyp;
    }
    
    public void setStatusCroTyp(Boolean statusCroTyp) {
        this.statusCroTyp = statusCroTyp;
    }

}


