package org.aepscolombia.platform.models.entity;
// Generated Jan 21, 2014 11:35:29 AM by Hibernate Tools 3.2.1.GA


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Worldclim generated by hbm2java
 */
@Entity
@Table(name="worldclim"
    , uniqueConstraints = @UniqueConstraint(columnNames="id_field_wor") 
)
public class Worldclim  implements java.io.Serializable {


     private Integer idWor;
     private Fields fields;
     private Integer bio1;
     private Integer bio2;
     private Integer bio3;
     private Integer bio4;
     private Integer bio5;
     private Integer bio6;
     private Integer bio7;
     private Integer bio8;
     private Integer bio9;
     private Integer bio10;
     private Integer bio11;
     private Integer bio12;
     private Integer bio13;
     private Integer bio14;
     private Integer bio15;
     private Integer bio16;
     private Integer bio17;
     private Integer bio18;
     private Integer bio19;
     private Integer consMths;

    public Worldclim() {
    }

	
    public Worldclim(Fields fields) {
        this.fields = fields;
    }
    public Worldclim(Fields fields, Integer bio1, Integer bio2, Integer bio3, Integer bio4, Integer bio5, Integer bio6, Integer bio7, Integer bio8, Integer bio9, Integer bio10, Integer bio11, Integer bio12, Integer bio13, Integer bio14, Integer bio15, Integer bio16, Integer bio17, Integer bio18, Integer bio19, Integer consMths) {
       this.fields = fields;
       this.bio1 = bio1;
       this.bio2 = bio2;
       this.bio3 = bio3;
       this.bio4 = bio4;
       this.bio5 = bio5;
       this.bio6 = bio6;
       this.bio7 = bio7;
       this.bio8 = bio8;
       this.bio9 = bio9;
       this.bio10 = bio10;
       this.bio11 = bio11;
       this.bio12 = bio12;
       this.bio13 = bio13;
       this.bio14 = bio14;
       this.bio15 = bio15;
       this.bio16 = bio16;
       this.bio17 = bio17;
       this.bio18 = bio18;
       this.bio19 = bio19;
       this.consMths = consMths;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_wor", unique=true, nullable=false)
    public Integer getIdWor() {
        return this.idWor;
    }
    
    public void setIdWor(Integer idWor) {
        this.idWor = idWor;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_field_wor", unique=true, nullable=false)
    public Fields getFields() {
        return this.fields;
    }
    
    public void setFields(Fields fields) {
        this.fields = fields;
    }
    
    @Column(name="bio1")
    public Integer getBio1() {
        return this.bio1;
    }
    
    public void setBio1(Integer bio1) {
        this.bio1 = bio1;
    }
    
    @Column(name="bio2")
    public Integer getBio2() {
        return this.bio2;
    }
    
    public void setBio2(Integer bio2) {
        this.bio2 = bio2;
    }
    
    @Column(name="bio3")
    public Integer getBio3() {
        return this.bio3;
    }
    
    public void setBio3(Integer bio3) {
        this.bio3 = bio3;
    }
    
    @Column(name="bio4")
    public Integer getBio4() {
        return this.bio4;
    }
    
    public void setBio4(Integer bio4) {
        this.bio4 = bio4;
    }
    
    @Column(name="bio5")
    public Integer getBio5() {
        return this.bio5;
    }
    
    public void setBio5(Integer bio5) {
        this.bio5 = bio5;
    }
    
    @Column(name="bio6")
    public Integer getBio6() {
        return this.bio6;
    }
    
    public void setBio6(Integer bio6) {
        this.bio6 = bio6;
    }
    
    @Column(name="bio7")
    public Integer getBio7() {
        return this.bio7;
    }
    
    public void setBio7(Integer bio7) {
        this.bio7 = bio7;
    }
    
    @Column(name="bio8")
    public Integer getBio8() {
        return this.bio8;
    }
    
    public void setBio8(Integer bio8) {
        this.bio8 = bio8;
    }
    
    @Column(name="bio9")
    public Integer getBio9() {
        return this.bio9;
    }
    
    public void setBio9(Integer bio9) {
        this.bio9 = bio9;
    }
    
    @Column(name="bio10")
    public Integer getBio10() {
        return this.bio10;
    }
    
    public void setBio10(Integer bio10) {
        this.bio10 = bio10;
    }
    
    @Column(name="bio11")
    public Integer getBio11() {
        return this.bio11;
    }
    
    public void setBio11(Integer bio11) {
        this.bio11 = bio11;
    }
    
    @Column(name="bio12")
    public Integer getBio12() {
        return this.bio12;
    }
    
    public void setBio12(Integer bio12) {
        this.bio12 = bio12;
    }
    
    @Column(name="bio13")
    public Integer getBio13() {
        return this.bio13;
    }
    
    public void setBio13(Integer bio13) {
        this.bio13 = bio13;
    }
    
    @Column(name="bio14")
    public Integer getBio14() {
        return this.bio14;
    }
    
    public void setBio14(Integer bio14) {
        this.bio14 = bio14;
    }
    
    @Column(name="bio15")
    public Integer getBio15() {
        return this.bio15;
    }
    
    public void setBio15(Integer bio15) {
        this.bio15 = bio15;
    }
    
    @Column(name="bio16")
    public Integer getBio16() {
        return this.bio16;
    }
    
    public void setBio16(Integer bio16) {
        this.bio16 = bio16;
    }
    
    @Column(name="bio17")
    public Integer getBio17() {
        return this.bio17;
    }
    
    public void setBio17(Integer bio17) {
        this.bio17 = bio17;
    }
    
    @Column(name="bio18")
    public Integer getBio18() {
        return this.bio18;
    }
    
    public void setBio18(Integer bio18) {
        this.bio18 = bio18;
    }
    
    @Column(name="bio19")
    public Integer getBio19() {
        return this.bio19;
    }
    
    public void setBio19(Integer bio19) {
        this.bio19 = bio19;
    }
    
    @Column(name="cons_mths")
    public Integer getConsMths() {
        return this.consMths;
    }
    
    public void setConsMths(Integer consMths) {
        this.consMths = consMths;
    }




}


