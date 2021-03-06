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
 * SeedsColors generated by hbm2java
 */
@Entity
@Table(name="seeds_colors")
public class SeedsColors  implements java.io.Serializable {


     private Integer idSeeCol;
     private String colorSeeCol;
     private Boolean statusSeeCol;

    public SeedsColors() {
    }

    public SeedsColors(String colorSeeCol, Boolean statusSeeCol) {
       this.colorSeeCol = colorSeeCol;
       this.statusSeeCol = statusSeeCol;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_see_col", unique=true, nullable=false)
    public Integer getIdSeeCol() {
        return this.idSeeCol;
    }
    
    public void setIdSeeCol(Integer idSeeCol) {
        this.idSeeCol = idSeeCol;
    }
    
    @Column(name="color_see_col", length=45)
    public String getColorSeeCol() {
        return this.colorSeeCol;
    }
    
    public void setColorSeeCol(String colorSeeCol) {
        this.colorSeeCol = colorSeeCol;
    }
    
    @Column(name="status_see_col")
    public Boolean getStatusSeeCol() {
        return this.statusSeeCol;
    }
    
    public void setStatusSeeCol(Boolean statusSeeCol) {
        this.statusSeeCol = statusSeeCol;
    }

}


