package org.aepscolombia.platform.models.entity;
// Generated Apr 8, 2014 9:37:27 AM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * OptionTypes generated by hbm2java
 */
@Entity
@Table(name="option_types")
public class OptionTypes  implements java.io.Serializable {


     private Integer idOptTyp;
     private String nameOptTyp;

    public OptionTypes() {
    }

    public OptionTypes(String nameOptTyp) {
       this.nameOptTyp = nameOptTyp;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_opt_typ", unique=true, nullable=false)
    public Integer getIdOptTyp() {
        return this.idOptTyp;
    }
    
    public void setIdOptTyp(Integer idOptTyp) {
        this.idOptTyp = idOptTyp;
    }
    
    @Column(name="name_opt_typ", nullable=false, length=45)
    public String getNameOptTyp() {
        return this.nameOptTyp;
    }
    
    public void setNameOptTyp(String nameOptTyp) {
        this.nameOptTyp = nameOptTyp;
    }




}


