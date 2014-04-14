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
 * GenotypesSowing generated by hbm2java
 */
@Entity
@Table(name="genotypes_sowing"
    ,catalog="madr_bd13"
)
public class GenotypesSowing  implements java.io.Serializable {


     private Integer idGenSow;
     private String nameGenSow;
     private boolean statusGenSow;

    public GenotypesSowing() {
    }

	
    public GenotypesSowing(String nameGenSow, boolean statusGenSow) {
        this.nameGenSow = nameGenSow;
        this.statusGenSow = statusGenSow;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_gen_sow", unique=true, nullable=false)
    public Integer getIdGenSow() {
        return this.idGenSow;
    }
    
    public void setIdGenSow(Integer idGenSow) {
        this.idGenSow = idGenSow;
    }
    
    @Column(name="name_gen_sow", nullable=false, length=45)
    public String getNameGenSow() {
        return this.nameGenSow;
    }
    
    public void setNameGenSow(String nameGenSow) {
        this.nameGenSow = nameGenSow;
    }
    
    @Column(name="status_gen_sow", nullable=false)
    public boolean isStatusGenSow() {
        return this.statusGenSow;
    }
    
    public void setStatusGenSow(boolean statusGenSow) {
        this.statusGenSow = statusGenSow;
    }

}


