package org.aepscolombia.platform.models.entity;
// Generated Jan 21, 2014 11:35:29 AM by Hibernate Tools 3.2.1.GA

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="farms_producers"
    ,catalog="madr_bd11"
)
public class FarmsProducers  implements java.io.Serializable {


    private FarmsProducersId id;
    private Farms farms;
    private Producers producers;

    public FarmsProducers() {
    }
    
    public FarmsProducers(FarmsProducersId id, Farms farms, Producers producers) {
        this.id = id;
        this.farms = farms;
        this.producers = producers;
    }
    
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="idFarmFarPro", column=@Column(name="id_farm_far_pro", nullable=false) ), 
        @AttributeOverride(name="idProducerFarPro", column=@Column(name="id_producer_far_pro", nullable=false) ) } )
    public FarmsProducersId getId() {
        return this.id;
    }
    
    public void setId(FarmsProducersId id) {
        this.id = id;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_farm_far_pro", nullable=false, insertable=false, updatable=false)
    public Farms getFarms() {
        return this.farms;
    }
    
    public void setFarms(Farms farms) {
        this.farms = farms;
    }
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_producer_far_pro", nullable=false, insertable=false, updatable=false)
    public Producers getProducers() {
        return this.producers;
    }
    
    public void setProducers(Producers producers) {
        this.producers = producers;
    }
    
}


