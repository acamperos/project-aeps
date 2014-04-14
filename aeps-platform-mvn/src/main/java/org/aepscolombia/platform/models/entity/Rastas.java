package org.aepscolombia.platform.models.entity;
// Generated Apr 8, 2014 9:37:27 AM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Rastas generated by hbm2java
 */
@Entity
@Table(name="rastas"
    ,catalog="madr_bd13"
)
public class Rastas  implements java.io.Serializable {


     private Integer idRas;
     private Fields fields;
     private Date fechaRas;
     private int numeroCajuelaRas;
     private Integer altitudRas;
     private Double latitudRas;
     private Double longitudRas;
     private Double pendienteTerrenoRas;
     private String terrenoCircundanteRas;
     private String posicionPerfilRas;
     private Integer numeroCapasRas;
     private Double phRas;
     private String carbonatosRas;
     private Double profundidadCarbonatosRas;
     private String piedrasSuperficieRas;
     private String rocasSuperficieRas;
     private String piedrasPerfilRas;
     private String rocasPerfilRas;
     private Boolean horizontePedrogosoRocosoRas;
     private Double profundidadHorizontePedregosoRas;
     private Double profundidadPrimerasPiedrasRas;
     private Double espesorHorizontePedregosoRas;
     private Boolean capasEndurecidasRas;
     private Double prufundidadCapasRas;
     private String espesorCapaEndurecidaRas;
     private Boolean moteadosRas;
     private Double profundidadMoteadosRas;
     private Boolean moteadosMas70cmRas;
     private String estructuraRas;
     private Boolean erosionRas;
     private Boolean mohoRas;
     private String costrasDurasRas;
     private String exposicionSolRas;
     private String costrasBlancasRas;
     private String costrasNegrasRas;
     private Boolean regionSecaRas;
     private Boolean raicesVivasRas;
     private Double profundidadRaicesRas;
     private String plantasPequenasRas;
     private Boolean hojarascaRas;
     private Boolean sueloNegroBlandoRas;
     private Boolean cuchilloPrimerHorizonteRas;
     private Boolean cercaRiosQuebradasRas;
     private String recubrimientoVegetalRas;
     private String materiaOrganicaRas;
     private Double profundidadEfectivaRas;
     private String drenajeInternoRas;
     private String dranajeExternoRas;
     private Boolean salinidadRas;
     private Boolean sodicidadRas;
     private String idProyectoRas;
     private boolean status;
     private Integer createdBy;

    public Rastas() {
    }

    public Rastas(Integer idRas) {
        this.idRas = idRas;
    }
	
    public Rastas(Fields fields, Date fechaRas, int numeroCajuelaRas, boolean status) {
        this.fields = fields;
        this.fechaRas = fechaRas;
        this.numeroCajuelaRas = numeroCajuelaRas;
        this.status = status;
    }
    public Rastas(Fields fields, Date fechaRas, int numeroCajuelaRas, Integer altitudRas, Double latitudRas, Double longitudRas, Double pendienteTerrenoRas, String terrenoCircundanteRas, String posicionPerfilRas, Integer numeroCapasRas, Double phRas, String carbonatosRas, Double profundidadCarbonatosRas, String piedrasSuperficieRas, String rocasSuperficieRas, String piedrasPerfilRas, String rocasPerfilRas, Boolean horizontePedrogosoRocosoRas, Double profundidadHorizontePedregosoRas, Double profundidadPrimerasPiedrasRas, Double espesorHorizontePedregosoRas, Boolean capasEndurecidasRas, Double prufundidadCapasRas, String espesorCapaEndurecidaRas, Boolean moteadosRas, Double profundidadMoteadosRas, Boolean moteadosMas70cmRas, String estructuraRas, Boolean erosionRas, Boolean mohoRas, String costrasDurasRas, String exposicionSolRas, String costrasBlancasRas, String costrasNegrasRas, Boolean regionSecaRas, Boolean raicesVivasRas, Double profundidadRaicesRas, String plantasPequenasRas, Boolean hojarascaRas, Boolean sueloNegroBlandoRas, Boolean cuchilloPrimerHorizonteRas, Boolean cercaRiosQuebradasRas, String recubrimientoVegetalRas, String materiaOrganicaRas, Double profundidadEfectivaRas, String drenajeInternoRas, String dranajeExternoRas, Boolean salinidadRas, Boolean sodicidadRas, String idProyectoRas, boolean status, Integer createdBy) {
       this.fields = fields;
       this.fechaRas = fechaRas;
       this.numeroCajuelaRas = numeroCajuelaRas;
       this.altitudRas = altitudRas;
       this.latitudRas = latitudRas;
       this.longitudRas = longitudRas;
       this.pendienteTerrenoRas = pendienteTerrenoRas;
       this.terrenoCircundanteRas = terrenoCircundanteRas;
       this.posicionPerfilRas = posicionPerfilRas;
       this.numeroCapasRas = numeroCapasRas;
       this.phRas = phRas;
       this.carbonatosRas = carbonatosRas;
       this.profundidadCarbonatosRas = profundidadCarbonatosRas;
       this.piedrasSuperficieRas = piedrasSuperficieRas;
       this.rocasSuperficieRas = rocasSuperficieRas;
       this.piedrasPerfilRas = piedrasPerfilRas;
       this.rocasPerfilRas = rocasPerfilRas;
       this.horizontePedrogosoRocosoRas = horizontePedrogosoRocosoRas;
       this.profundidadHorizontePedregosoRas = profundidadHorizontePedregosoRas;
       this.profundidadPrimerasPiedrasRas = profundidadPrimerasPiedrasRas;
       this.espesorHorizontePedregosoRas = espesorHorizontePedregosoRas;
       this.capasEndurecidasRas = capasEndurecidasRas;
       this.prufundidadCapasRas = prufundidadCapasRas;
       this.espesorCapaEndurecidaRas = espesorCapaEndurecidaRas;
       this.moteadosRas = moteadosRas;
       this.profundidadMoteadosRas = profundidadMoteadosRas;
       this.moteadosMas70cmRas = moteadosMas70cmRas;
       this.estructuraRas = estructuraRas;
       this.erosionRas = erosionRas;
       this.mohoRas = mohoRas;
       this.costrasDurasRas = costrasDurasRas;
       this.exposicionSolRas = exposicionSolRas;
       this.costrasBlancasRas = costrasBlancasRas;
       this.costrasNegrasRas = costrasNegrasRas;
       this.regionSecaRas = regionSecaRas;
       this.raicesVivasRas = raicesVivasRas;
       this.profundidadRaicesRas = profundidadRaicesRas;
       this.plantasPequenasRas = plantasPequenasRas;
       this.hojarascaRas = hojarascaRas;
       this.sueloNegroBlandoRas = sueloNegroBlandoRas;
       this.cuchilloPrimerHorizonteRas = cuchilloPrimerHorizonteRas;
       this.cercaRiosQuebradasRas = cercaRiosQuebradasRas;
       this.recubrimientoVegetalRas = recubrimientoVegetalRas;
       this.materiaOrganicaRas = materiaOrganicaRas;
       this.profundidadEfectivaRas = profundidadEfectivaRas;
       this.drenajeInternoRas = drenajeInternoRas;
       this.dranajeExternoRas = dranajeExternoRas;
       this.salinidadRas = salinidadRas;
       this.sodicidadRas = sodicidadRas;
       this.idProyectoRas = idProyectoRas;
       this.status = status;
       this.createdBy = createdBy;
    }
   
    public Rastas(Date fechaRas, int numeroCajuelaRas, Integer altitudRas, Double latitudRas, Double longitudRas, Double pendienteTerrenoRas, String terrenoCircundanteRas, String posicionPerfilRas, Integer numeroCapasRas, Double phRas, String carbonatosRas, Double profundidadCarbonatosRas, String piedrasSuperficieRas, String rocasSuperficieRas, String piedrasPerfilRas, String rocasPerfilRas, Boolean horizontePedrogosoRocosoRas, Double profundidadHorizontePedregosoRas, Double profundidadPrimerasPiedrasRas, Double espesorHorizontePedregosoRas, Boolean capasEndurecidasRas, Double prufundidadCapasRas, String espesorCapaEndurecidaRas, Boolean moteadosRas, Double profundidadMoteadosRas, Boolean moteadosMas70cmRas, String estructuraRas, Boolean erosionRas, Boolean mohoRas, String costrasDurasRas, String exposicionSolRas, String costrasBlancasRas, String costrasNegrasRas, Boolean regionSecaRas, Boolean raicesVivasRas, Double profundidadRaicesRas, String plantasPequenasRas, Boolean hojarascaRas, Boolean sueloNegroBlandoRas, Boolean cuchilloPrimerHorizonteRas, Boolean cercaRiosQuebradasRas, String recubrimientoVegetalRas, String materiaOrganicaRas, Double profundidadEfectivaRas, String drenajeInternoRas, String dranajeExternoRas, Boolean salinidadRas, Boolean sodicidadRas, String idProyectoRas, boolean status) {
       this.fechaRas = fechaRas;
       this.numeroCajuelaRas = numeroCajuelaRas;
       this.altitudRas = altitudRas;
       this.latitudRas = latitudRas;
       this.longitudRas = longitudRas;
       this.pendienteTerrenoRas = pendienteTerrenoRas;
       this.terrenoCircundanteRas = terrenoCircundanteRas;
       this.posicionPerfilRas = posicionPerfilRas;
       this.numeroCapasRas = numeroCapasRas;
       this.phRas = phRas;
       this.carbonatosRas = carbonatosRas;
       this.profundidadCarbonatosRas = profundidadCarbonatosRas;
       this.piedrasSuperficieRas = piedrasSuperficieRas;
       this.rocasSuperficieRas = rocasSuperficieRas;
       this.piedrasPerfilRas = piedrasPerfilRas;
       this.rocasPerfilRas = rocasPerfilRas;
       this.horizontePedrogosoRocosoRas = horizontePedrogosoRocosoRas;
       this.profundidadHorizontePedregosoRas = profundidadHorizontePedregosoRas;
       this.profundidadPrimerasPiedrasRas = profundidadPrimerasPiedrasRas;
       this.espesorHorizontePedregosoRas = espesorHorizontePedregosoRas;
       this.capasEndurecidasRas = capasEndurecidasRas;
       this.prufundidadCapasRas = prufundidadCapasRas;
       this.espesorCapaEndurecidaRas = espesorCapaEndurecidaRas;
       this.moteadosRas = moteadosRas;
       this.profundidadMoteadosRas = profundidadMoteadosRas;
       this.moteadosMas70cmRas = moteadosMas70cmRas;
       this.estructuraRas = estructuraRas;
       this.erosionRas = erosionRas;
       this.mohoRas = mohoRas;
       this.costrasDurasRas = costrasDurasRas;
       this.exposicionSolRas = exposicionSolRas;
       this.costrasBlancasRas = costrasBlancasRas;
       this.costrasNegrasRas = costrasNegrasRas;
       this.regionSecaRas = regionSecaRas;
       this.raicesVivasRas = raicesVivasRas;
       this.profundidadRaicesRas = profundidadRaicesRas;
       this.plantasPequenasRas = plantasPequenasRas;
       this.hojarascaRas = hojarascaRas;
       this.sueloNegroBlandoRas = sueloNegroBlandoRas;
       this.cuchilloPrimerHorizonteRas = cuchilloPrimerHorizonteRas;
       this.cercaRiosQuebradasRas = cercaRiosQuebradasRas;
       this.recubrimientoVegetalRas = recubrimientoVegetalRas;
       this.materiaOrganicaRas = materiaOrganicaRas;
       this.profundidadEfectivaRas = profundidadEfectivaRas;
       this.drenajeInternoRas = drenajeInternoRas;
       this.dranajeExternoRas = dranajeExternoRas;
       this.salinidadRas = salinidadRas;
       this.sodicidadRas = sodicidadRas;
       this.idProyectoRas = idProyectoRas;
       this.status = status;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="id_ras", unique=true, nullable=false)
    public Integer getIdRas() {
        return this.idRas;
    }
    
    public void setIdRas(Integer idRas) {
        this.idRas = idRas;
    }
@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_lote_ras", nullable=false)
    public Fields getFields() {
        return this.fields;
    }
    
    public void setFields(Fields fields) {
        this.fields = fields;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="fecha_ras", nullable=false, length=19)
    public Date getFechaRas() {
        return this.fechaRas;
    }
    
    public void setFechaRas(Date fechaRas) {
        this.fechaRas = fechaRas;
    }
    
    @Column(name="numero_cajuela_ras", nullable=false)
    public int getNumeroCajuelaRas() {
        return this.numeroCajuelaRas;
    }
    
    public void setNumeroCajuelaRas(int numeroCajuelaRas) {
        this.numeroCajuelaRas = numeroCajuelaRas;
    }
    
    @Column(name="altitud_ras")
    public Integer getAltitudRas() {
        return this.altitudRas;
    }
    
    public void setAltitudRas(Integer altitudRas) {
        this.altitudRas = altitudRas;
    }
    
    @Column(name="latitud_ras", precision=22, scale=0)
    public Double getLatitudRas() {
        return this.latitudRas;
    }
    
    public void setLatitudRas(Double latitudRas) {
        this.latitudRas = latitudRas;
    }
    
    @Column(name="longitud_ras", precision=22, scale=0)
    public Double getLongitudRas() {
        return this.longitudRas;
    }
    
    public void setLongitudRas(Double longitudRas) {
        this.longitudRas = longitudRas;
    }
    
    @Column(name="pendiente_terreno_ras", precision=22, scale=0)
    public Double getPendienteTerrenoRas() {
        return this.pendienteTerrenoRas;
    }
    
    public void setPendienteTerrenoRas(Double pendienteTerrenoRas) {
        this.pendienteTerrenoRas = pendienteTerrenoRas;
    }
    
    @Column(name="terreno_circundante_ras", length=20)
    public String getTerrenoCircundanteRas() {
        return this.terrenoCircundanteRas;
    }
    
    public void setTerrenoCircundanteRas(String terrenoCircundanteRas) {
        this.terrenoCircundanteRas = terrenoCircundanteRas;
    }
    
    @Column(name="posicion_perfil_ras", length=22)
    public String getPosicionPerfilRas() {
        return this.posicionPerfilRas;
    }
    
    public void setPosicionPerfilRas(String posicionPerfilRas) {
        this.posicionPerfilRas = posicionPerfilRas;
    }
    
    @Column(name="numero_capas_ras")
    public Integer getNumeroCapasRas() {
        return this.numeroCapasRas;
    }
    
    public void setNumeroCapasRas(Integer numeroCapasRas) {
        this.numeroCapasRas = numeroCapasRas;
    }
    
    @Column(name="ph_ras", precision=22, scale=0)
    public Double getPhRas() {
        return this.phRas;
    }
    
    public void setPhRas(Double phRas) {
        this.phRas = phRas;
    }
    
    @Column(name="carbonatos_ras", length=17)
    public String getCarbonatosRas() {
        return this.carbonatosRas;
    }
    
    public void setCarbonatosRas(String carbonatosRas) {
        this.carbonatosRas = carbonatosRas;
    }
    
    @Column(name="profundidad_carbonatos_ras", precision=22, scale=0)
    public Double getProfundidadCarbonatosRas() {
        return this.profundidadCarbonatosRas;
    }
    
    public void setProfundidadCarbonatosRas(Double profundidadCarbonatosRas) {
        this.profundidadCarbonatosRas = profundidadCarbonatosRas;
    }
    
    @Column(name="piedras_superficie_ras", length=13)
    public String getPiedrasSuperficieRas() {
        return this.piedrasSuperficieRas;
    }
    
    public void setPiedrasSuperficieRas(String piedrasSuperficieRas) {
        this.piedrasSuperficieRas = piedrasSuperficieRas;
    }
    
    @Column(name="rocas_superficie_ras", length=10)
    public String getRocasSuperficieRas() {
        return this.rocasSuperficieRas;
    }
    
    public void setRocasSuperficieRas(String rocasSuperficieRas) {
        this.rocasSuperficieRas = rocasSuperficieRas;
    }
    
    @Column(name="piedras_perfil_ras", length=13)
    public String getPiedrasPerfilRas() {
        return this.piedrasPerfilRas;
    }
    
    public void setPiedrasPerfilRas(String piedrasPerfilRas) {
        this.piedrasPerfilRas = piedrasPerfilRas;
    }
    
    @Column(name="rocas_perfil_ras", length=10)
    public String getRocasPerfilRas() {
        return this.rocasPerfilRas;
    }
    
    public void setRocasPerfilRas(String rocasPerfilRas) {
        this.rocasPerfilRas = rocasPerfilRas;
    }
    
    @Column(name="horizonte_pedrogoso_rocoso_ras")
    public Boolean getHorizontePedrogosoRocosoRas() {
        return this.horizontePedrogosoRocosoRas;
    }
    
    public void setHorizontePedrogosoRocosoRas(Boolean horizontePedrogosoRocosoRas) {
        this.horizontePedrogosoRocosoRas = horizontePedrogosoRocosoRas;
    }
    
    @Column(name="profundidad_horizonte_pedregoso_ras", precision=22, scale=0)
    public Double getProfundidadHorizontePedregosoRas() {
        return this.profundidadHorizontePedregosoRas;
    }
    
    public void setProfundidadHorizontePedregosoRas(Double profundidadHorizontePedregosoRas) {
        this.profundidadHorizontePedregosoRas = profundidadHorizontePedregosoRas;
    }
    
    @Column(name="profundidad_primeras_piedras_ras", precision=22, scale=0)
    public Double getProfundidadPrimerasPiedrasRas() {
        return this.profundidadPrimerasPiedrasRas;
    }
    
    public void setProfundidadPrimerasPiedrasRas(Double profundidadPrimerasPiedrasRas) {
        this.profundidadPrimerasPiedrasRas = profundidadPrimerasPiedrasRas;
    }
    
    @Column(name="espesor_horizonte_pedregoso_ras", precision=22, scale=0)
    public Double getEspesorHorizontePedregosoRas() {
        return this.espesorHorizontePedregosoRas;
    }
    
    public void setEspesorHorizontePedregosoRas(Double espesorHorizontePedregosoRas) {
        this.espesorHorizontePedregosoRas = espesorHorizontePedregosoRas;
    }
    
    @Column(name="capas_endurecidas_ras")
    public Boolean getCapasEndurecidasRas() {
        return this.capasEndurecidasRas;
    }
    
    public void setCapasEndurecidasRas(Boolean capasEndurecidasRas) {
        this.capasEndurecidasRas = capasEndurecidasRas;
    }
    
    @Column(name="prufundidad_capas_ras", precision=22, scale=0)
    public Double getPrufundidadCapasRas() {
        return this.prufundidadCapasRas;
    }
    
    public void setPrufundidadCapasRas(Double prufundidadCapasRas) {
        this.prufundidadCapasRas = prufundidadCapasRas;
    }
    
    @Column(name="espesor_capa_endurecida_ras", length=45)
    public String getEspesorCapaEndurecidaRas() {
        return this.espesorCapaEndurecidaRas;
    }
    
    public void setEspesorCapaEndurecidaRas(String espesorCapaEndurecidaRas) {
        this.espesorCapaEndurecidaRas = espesorCapaEndurecidaRas;
    }
    
    @Column(name="moteados_ras")
    public Boolean getMoteadosRas() {
        return this.moteadosRas;
    }
    
    public void setMoteadosRas(Boolean moteadosRas) {
        this.moteadosRas = moteadosRas;
    }
    
    @Column(name="profundidad_moteados_ras", precision=22, scale=0)
    public Double getProfundidadMoteadosRas() {
        return this.profundidadMoteadosRas;
    }
    
    public void setProfundidadMoteadosRas(Double profundidadMoteadosRas) {
        this.profundidadMoteadosRas = profundidadMoteadosRas;
    }
    
    @Column(name="moteados_mas_70cm_ras")
    public Boolean getMoteadosMas70cmRas() {
        return this.moteadosMas70cmRas;
    }
    
    public void setMoteadosMas70cmRas(Boolean moteadosMas70cmRas) {
        this.moteadosMas70cmRas = moteadosMas70cmRas;
    }
    
    @Column(name="estructura_ras", length=16)
    public String getEstructuraRas() {
        return this.estructuraRas;
    }
    
    public void setEstructuraRas(String estructuraRas) {
        this.estructuraRas = estructuraRas;
    }
    
    @Column(name="erosion_ras")
    public Boolean getErosionRas() {
        return this.erosionRas;
    }
    
    public void setErosionRas(Boolean erosionRas) {
        this.erosionRas = erosionRas;
    }
    
    @Column(name="moho_ras")
    public Boolean getMohoRas() {
        return this.mohoRas;
    }
    
    public void setMohoRas(Boolean mohoRas) {
        this.mohoRas = mohoRas;
    }
    
    @Column(name="costras_duras_ras", length=13)
    public String getCostrasDurasRas() {
        return this.costrasDurasRas;
    }
    
    public void setCostrasDurasRas(String costrasDurasRas) {
        this.costrasDurasRas = costrasDurasRas;
    }
    
    @Column(name="exposicion_sol_ras", length=21)
    public String getExposicionSolRas() {
        return this.exposicionSolRas;
    }
    
    public void setExposicionSolRas(String exposicionSolRas) {
        this.exposicionSolRas = exposicionSolRas;
    }
    
    @Column(name="costras_blancas_ras", length=13)
    public String getCostrasBlancasRas() {
        return this.costrasBlancasRas;
    }
    
    public void setCostrasBlancasRas(String costrasBlancasRas) {
        this.costrasBlancasRas = costrasBlancasRas;
    }
    
    @Column(name="costras_negras_ras", length=13)
    public String getCostrasNegrasRas() {
        return this.costrasNegrasRas;
    }
    
    public void setCostrasNegrasRas(String costrasNegrasRas) {
        this.costrasNegrasRas = costrasNegrasRas;
    }
    
    @Column(name="region_seca_ras")
    public Boolean getRegionSecaRas() {
        return this.regionSecaRas;
    }
    
    public void setRegionSecaRas(Boolean regionSecaRas) {
        this.regionSecaRas = regionSecaRas;
    }
    
    @Column(name="raices_vivas_ras")
    public Boolean getRaicesVivasRas() {
        return this.raicesVivasRas;
    }
    
    public void setRaicesVivasRas(Boolean raicesVivasRas) {
        this.raicesVivasRas = raicesVivasRas;
    }
    
    @Column(name="profundidad_raices_ras", precision=22, scale=0)
    public Double getProfundidadRaicesRas() {
        return this.profundidadRaicesRas;
    }
    
    public void setProfundidadRaicesRas(Double profundidadRaicesRas) {
        this.profundidadRaicesRas = profundidadRaicesRas;
    }
    
    @Column(name="plantas_pequenas_ras", length=16)
    public String getPlantasPequenasRas() {
        return this.plantasPequenasRas;
    }
    
    public void setPlantasPequenasRas(String plantasPequenasRas) {
        this.plantasPequenasRas = plantasPequenasRas;
    }
    
    @Column(name="hojarasca_ras")
    public Boolean getHojarascaRas() {
        return this.hojarascaRas;
    }
    
    public void setHojarascaRas(Boolean hojarascaRas) {
        this.hojarascaRas = hojarascaRas;
    }
    
    @Column(name="suelo_negro_blando_ras")
    public Boolean getSueloNegroBlandoRas() {
        return this.sueloNegroBlandoRas;
    }
    
    public void setSueloNegroBlandoRas(Boolean sueloNegroBlandoRas) {
        this.sueloNegroBlandoRas = sueloNegroBlandoRas;
    }
    
    @Column(name="cuchillo_primer_horizonte_ras")
    public Boolean getCuchilloPrimerHorizonteRas() {
        return this.cuchilloPrimerHorizonteRas;
    }
    
    public void setCuchilloPrimerHorizonteRas(Boolean cuchilloPrimerHorizonteRas) {
        this.cuchilloPrimerHorizonteRas = cuchilloPrimerHorizonteRas;
    }
    
    @Column(name="cerca_rios_quebradas_ras")
    public Boolean getCercaRiosQuebradasRas() {
        return this.cercaRiosQuebradasRas;
    }
    
    public void setCercaRiosQuebradasRas(Boolean cercaRiosQuebradasRas) {
        this.cercaRiosQuebradasRas = cercaRiosQuebradasRas;
    }
    
    @Column(name="recubrimiento_vegetal_ras", length=13)
    public String getRecubrimientoVegetalRas() {
        return this.recubrimientoVegetalRas;
    }
    
    public void setRecubrimientoVegetalRas(String recubrimientoVegetalRas) {
        this.recubrimientoVegetalRas = recubrimientoVegetalRas;
    }
    
    @Column(name="materia_organica_ras", length=45)
    public String getMateriaOrganicaRas() {
        return this.materiaOrganicaRas;
    }
    
    public void setMateriaOrganicaRas(String materiaOrganicaRas) {
        this.materiaOrganicaRas = materiaOrganicaRas;
    }
    
    @Column(name="profundidad_efectiva_ras", precision=22, scale=0)
    public Double getProfundidadEfectivaRas() {
        return this.profundidadEfectivaRas;
    }
    
    public void setProfundidadEfectivaRas(Double profundidadEfectivaRas) {
        this.profundidadEfectivaRas = profundidadEfectivaRas;
    }
    
    @Column(name="drenaje_interno_ras", length=45)
    public String getDrenajeInternoRas() {
        return this.drenajeInternoRas;
    }
    
    public void setDrenajeInternoRas(String drenajeInternoRas) {
        this.drenajeInternoRas = drenajeInternoRas;
    }
    
    @Column(name="dranaje_externo_ras", length=45)
    public String getDranajeExternoRas() {
        return this.dranajeExternoRas;
    }
    
    public void setDranajeExternoRas(String dranajeExternoRas) {
        this.dranajeExternoRas = dranajeExternoRas;
    }
    
    @Column(name="salinidad_ras")
    public Boolean getSalinidadRas() {
        return this.salinidadRas;
    }
    
    public void setSalinidadRas(Boolean salinidadRas) {
        this.salinidadRas = salinidadRas;
    }
    
    @Column(name="sodicidad_ras")
    public Boolean getSodicidadRas() {
        return this.sodicidadRas;
    }
    
    public void setSodicidadRas(Boolean sodicidadRas) {
        this.sodicidadRas = sodicidadRas;
    }
    
    @Column(name="id_proyecto_ras", length=4)
    public String getIdProyectoRas() {
        return this.idProyectoRas;
    }
    
    public void setIdProyectoRas(String idProyectoRas) {
        this.idProyectoRas = idProyectoRas;
    }
    
    @Column(name="status", nullable=false)
    public boolean isStatus() {
        return this.status;
    }
    
    public void setStatus(boolean status) {
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


