<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<!DOCTYPE html>
<html>
    <head></head>
    <body>
        <div class="container">
            <div class="panel">
                <div class="panel-body">
                    <div class="row-fluid">
                        <div class="span12">
<!--                            <h3><s:property value="getText('text.mision.aboutus')" /></h3>
                            <hr>
                            <p><s:property value="getText('desc.mision.aboutus')" /></p>
                            
                            <h3><s:property value="getText('text.vision.aboutus')" /></h3>
                            <hr>
                            <p><s:property value="getText('desc.vision.aboutus')" /></p>-->
                            <div class="tabbable tabs-left tabbable-bordered">
                                <ul class="nav nav-tabs">
                                    <li class="active"><a href="#tb3_a" data-toggle="tab"><s:property value="getText('text.mision.aboutus')" /></a></li>
                                    <li><a href="#tb3_b" data-toggle="tab"><s:property value="getText('text.vision.aboutus')" /></a></li>
                                    <li><a href="#tb3_c" data-toggle="tab">Proyectos</a></li>
                                    <li><a href="#tb3_d" data-toggle="tab">Nuestro Equipo</a></li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane active" id="tb3_a">
                                        <p><strong><s:property value="getText('text.mision.aboutus')" /></strong></p>
                                        <hr>
                                        <p><s:property value="getText('desc.mision.aboutus')" /></p>
                                    </div>
                                    <div class="tab-pane" id="tb3_b">
                                        <p><strong><s:property value="getText('text.vision.aboutus')" /></strong></p>
                                        <hr>
                                        <p><s:property value="getText('desc.vision.aboutus')" /></p>
                                    </div>
                                    <div class="tab-pane" id="tb3_c">
                                        <p><strong>Proyectos</strong></p>
                                        <hr>
                                        <!--<p><s:property value="getText('desc.vision.aboutus')" /></p>-->
                                        <p>
                                            Agricultura especifica por sitio compartiendo experiencias (AESCE) aplicada la producción de frutales en Colombia.<br />
                                            <strong>Cofinanciado por:</strong> Fondo Nacional de Fomento Hortofrutícola / Asohofrucol / CIAT<br />
                                            <strong>Socios:</strong> MADR - Secretarios Nacionales de Cadenas, FundaCIAT, comites departamentales de Asohofrucol. Proyecto ECAs - Asohofrucol.<br />
                                            <strong>Período:</strong> junio 2010 a junio 2013.<br />
                                            <strong>Cultivos analizados:</strong> cítricos (naranja, limón, mandarina), Mango, Aguacate (verdes y Hass) y Plátano.<br />
                                            <strong>Logros:</strong><br />
                                            * Identificación de la situación productiva de cuatro frutales en el país.<br />
                                            * Generación de zonas homogéneas de clima y grupos de suelo para cuatro cultivos.<br />
                                            * Identificación de condiciones de clima y suelo determinantes para buenos rendimientos de cuatro cultivos.<br />
                                            * 45 profesionales y técnicos formados en Agricultura Específica por sitio.<br />
                                            * Cerca de 4000 lotes caracterizados en el país.<br />
                                            * Mas de 30.000 productores informados sobre Agricultura Específica Por Sitio Compartiendo Experiencias.<br />
                                            * Generación de una página para el intercambio de información www.frutisitio.org.<br />
                                            * Generación de una plataforma para administración de información de productores de frutas del país.<br />
                                            * Generación de capacidades dentro del gremio para administrar el sistema.<br /> 
                                            
                                            Análisis Integral De Sistemas Productivos En Colombia Para La Adaptación Al Cambio Climático - Componente 
                                            2: Contribuir al cierre de brechas productivas aplicando Agricultura Específica por Sitio, para sub-sectores 
                                            priorizados.<br /><br />

                                            <strong>Cofinanciado por:</strong> Ministerio de Agricultura y Desarrollo Rural / CIAT<br />
                                            <strong>Socios:</strong> FENALCE, FEDEARROZ, Corporación Biotec.<br />
                                            <strong>Período:</strong> Diciembre 2012 a Junio 2014.<br />
                                            <strong>Cultivos analizados:</strong> Maíz, Arroz, Yuca, Aguacate Hass y Plátano.<br />
                                            <strong>Logros:</strong><br /> 
                                            * Identificación de la situación productiva de los cultivos de estudio en el país.<br />
                                            * Identificación de factores de clima, suelo y manejo del cultivo determinantes del rendimientos.<br />
                                            * 25 profesionales y técnicos formados en Agricultura Específica por sitio.<br />
                                            * Mas de 8000 eventos productivos analizados en el país.<br />
                                            * Generación de una plataforma web y móvil para administración de información de productores del país.<br />
                                            * Generación de capacidades dentro de los gremios para administrar el sistema.<br />
                                        </p>                                        
                                    </div>
                                    <div class="tab-pane" id="tb3_d">
                                        <p><strong>Nuestro Equipo</strong></p>
                                        <hr>
                                        <p>
                                            <strong>Daniel Jiménez Rodas</strong><br />
                                            <strong>Centro Internacional de Agricultura Tropical  (CIAT), Decision and Policy Analysis (DAPA).</strong> d.jimenez@cgiar.org<br />
                                            Ingeniero Agrónomo. Basó su doctorado en el tema de Agricultura Específica por Sitio contextualizado a los países tropicales. Cuenta con más de 5 años de experiencia en este tema, con énfasis en el análisis de la información de esta metodología,  con la que ha realizado varias publicaciones y participado en congresos internacionales.<br />
                                            Durante su carrera, Daniel, ha trabajado como investigador en instituciones como Bioversity International, el CIAT,  
                                            la Alta Escuela de Ingeniera y Gestión del Cantón de Vaud en Suiza (HEIG-VD) y ha sido consultor para el Centro 
                                            Francés de Cooperación Internacional en Investigación en Agronomía (CIRAD). En el 2010, su trabajo de investigación 
                                            fue uno de los 40 seleccionados, entre más de 400 en todo el mundo, para participar en el primer seminario de jóvenes 
                                            investigadores trabajando en países en vía de desarrollo.<br />
                                            Daniel Jiménez es el Coordinador de este Proyecto celebrado entre CIAT y Asohofrucol.<br />
                                            <strong>Algunas de sus publicaciones:</strong><br />
                                            A Survey of Artificial Neural Network-Based Modeling in Agroecology. (2008)<br />
                                            Analysis of Andean blackberry (Rubus glaucus) production models obtained by means of artificial neural
                                            networks exploiting information collected by small-scale growers in Colombia and publicly available meteorological data. (2009)<br />
                                            Interpretation of commercial production information: A case study of lulo (Solanum quitoense), an under-researched Andean fruit.  (2011)<br />
                                            Enhancing Decision-Making Processes of Small Farmers in Tropical Crops by Means of Machine Learning Models. (2012)<br /><br />
                                            
                                            <strong>Andrew Jarvis</strong><br />
                                            <strong>Centro Internacional de Agricultura Tropical  (CIAT), Decision and Policy Analysis (DAPA)</strong><br />
                                            Es el líder del Programa Análisis de Políticas (DAPA)  del Centro Internacional de Agricultura Tropical (CIAT).  Además, es el líder temático en el 
                                            Programa Global de Cambio Climático, Agricultura y Seguridad Alimentaria (CCAFS).  Andy tiene más de 10 años de experiencia en investigación, trabajando en 
                                            los retos de reducción de la  pobreza rural en países en vía de desarrollo y en la protección del medio ambiente.<br />
                                            Su investigación se ha enfocado en el uso de análisis espacial y modelación en los campos de desarrollo agrícola, adaptación de medios de vida a cambio 
                                            climático y  mantenimiento de servicios ecosistémicos.  Ha publicado más de 50 artículos en libros y revistas científicas.<br />
                                            Se ha desempeñado como consultor para instituciones como la Organización de Agricultura y Alimentos (FAO), en el desarrollo de estrategias para la conservación 
                                            de la agro-biodiversidad frente el cambio climático, y  en otros proyectos con la Unión Europea y el Fondo Mundial del Medio-Ambiente (GEF).<br />
                                            En 2003 ganó el premio de mejor artículo publicado en la revista Crop Science, relacionado con Recursos Genéticos. En 2009 fue el ganador del prestigioso 
                                            premio Ebbe Nielsen por su investigación en Bioinformática, relacionada con los impactos de cambio climático sobre la agro biodiversidad.<br /><br />
                                            
                                            <strong>Luis Armando Muñoz Borja</strong><br />
                                            <strong>Centro Internacional de Agricultura Tropical  (CIAT), Decision and Policy Analysis (DAPA).</strong> l.a.munoz@cgiar.org<br />
                                            Es biólogo de la Universidad del Valle, con tesis en cultivo de tejidosin vitro. Especialista en propagación in vitro e implementación de Biofábricas del
                                            Instituto de Biotecnología de las Plantas, Cuba.  Magister en Ciencias Agrarias de la Universidad Nacional de Colombia, con énfasis en fitomejoramiento. 
                                            Cuenta con 13 años de experiencia en formulación y ejecución de proyectos en frutales, realizando actividades puntuales como investigador y/o coordinador de estos, 
                                            con fuentes nacionales (MADR, Colciencias y Asohofrucol) e internacionales (BID, Banco Mundial y Fontagro).<br />
                                            Gracias a su trayectoria, posee un gran número de competencias en  seguimiento y evaluación de proyectos (levantamiento de línea base, seguimiento 
                                            a indicadores y metas), construcción de sistemas de información geográfica para pequeños productores de frutales, evaluación de adopción de tecnología 
                                            para pequeños productores rurales, aplicación de herramientas de evaluación participativa de tecnología, caracterización agromorfológica de materiales 
                                            en campo e invernadero y selección de clones élite. Además, cuentas con habilidades para el trabajo con comunidades rurales y empresarios del sector agrícola. 
                                            Ha estado a cargo de las relaciones institucionales en proyectos con socios en Colombia, Ecuador y Costa Rica.<br />
                                            Actualmente es el coordinador operativo del proyecto Agricultura Específica por Sitio Compartiendo Experiencias.<br /><br />
                                            
                                            <strong>James Cock</strong><br />
                                            <strong>Centro Internacional de Agricultura Tropical  (CIAT), Decision and Policy Analysis (DAPA).</strong><br />
                                            James fue el  pionero de la Agricultura Específica por Sitio -AEPS-en Colombia, cuando siendo director de Ceñicaña aplicó este sistema en el cultivo 
                                            de caña de azúcar. Posteriormente,  participó en su implementación en camarones en el Centro de Investigación de la Agricultura de Colombia (CENIACUA); 
                                            en café en el programa Decisión y Análisis de Políticas -DAPA- del Centro Internacional de Agricultura Tropical - CIAT ; y en frutales, primero en el 
                                            CIAT y luego como asesor de CIAT en  la Corporación Biotec para guanábana, mora y lulo . Actualmente es el asesor científico del proyecto Agricultura 
                                            Específica por Sitio Compartiendo Experiencias.<br /><br />
                                            
                                            <strong>Sylvain Delerce</strong><br />
                                            <strong>Centro Internacional de Agricultura Tropical  (CIAT), Decision and Policy Analysis (DAPA).</strong> s.delerce@cgiar.org<br />
                                            "Por haber trabajado en un organismo de extensión, veo que además de explorar nuevos  campos científicos, el proyecto AESCE desarrolla una metodología 
                                            con gran potencial, y que realmente les puede servir a los productores"<br />
                                            Ingeniero agrónomo, de nacionalidad francesa, con maestría en Agronomía y sistemas productivos sostenibles de Agroparistech.  
                                            Trabajó 3 años en el sur de Francia en el SUAMME, organismo de investigación aplicada y extensión con enfoque la ganadería pastoril. Actualmente, 
                                            coordina la parte agronómica con los análisis del proyecto AESCE.<br />
                                            Durante su carrera, Sylvain ha trabajado temas como impacto del cambio climático sobre sistemas pastoriles del sur de Francia (proyecto Climfourel), 
                                            optimización de la alimentación del ganado con pastoreo de superficie no cultivadas, pagos por servicios ambientales en el marco de la red Natura 2000.<br />
                                            Además de ser agrónomo, en su formación como en sus primeros años de trabajo, siempre ha manejado los Sistemas de Información Geográfica (SIG), 
                                            y los análisis de datos (estadísticas) lo que le permite contribuir a la articulación de las múltiples disciplinas con las que cuenta el proyecto AESCE.<br /><br />
                                            
                                            <strong>Fanny Howland</strong><br />
                                            <strong>Centro Internacional de Agricultura Tropical  (CIAT), Decision and Policy Analysis (DAPA).</strong> f.c.howland@cgiar.org<br />
                                            "Integrar los saberes científicos con los de agricultores grandes y pequeños para lograr nuevos conocimientos me parece un enfoque muy interesante y útil. 
                                            Promover el intercambio de conocimientos y la asociación entre productores con mismo cultivo, mismas condiciones productivas por medio del uso de tecnologías; 
                                            creando nuevas comunidades de interés común para mitigar  la asimetría de información en el sector rural y de esta manera mejorar su productividad 
                                            y competitividad es muy innovador"<br />
                                            Antropóloga, de nacionalidad francesa con Maestría en Estudios de Desarrollo con énfasis en Desarrollo Local de la Universidad de La Sorbona en Paris. 
                                            Trabajó en la Federación de Cooperativas de Utilización de Materiales Agrícolas en la elaboración de un estudio de factibilidad para la instauración 
                                            de cultivos de cáñamo en el marco de un proyecto de rescate de recursos hídricos. Su experiencia en Colombia comienza en la ONG Corporación YANAPAQUI, 
                                            en el proyecto de agricultura urbana PLAENFA (Plantemos en Familia) en la ciudad de Medellín.<br />
                                            En Agricultura Específica por Sitio, Fanny trabaja con más de 250 pequeños productores involucrados en el proyecto de Escuelas de Campo para Agricultores (ECAs), 
                                            para que validen y apliquen las herramientas desarrolladas desde el proyecto. La metodología de sus talleres es participativa y grupal; con el objetivo 
                                            de lograr una mejor apropiación de dichas herramientas por parte de los agricultores.<br /><br />
                                            
                                            <strong>Hugo Andrés Dorado</strong><br />
                                            <strong>Centro Internacional de Agricultura Tropical  (CIAT), Decision and Policy Analysis (DAPA).</strong> h.a.dorado@cgiar.org<br />
                                            "Analizar información real proporcionada por los mismos agricultores del país para generar recomendaciones a través de sus experiencias, utilizando diversas
                                            metodologías, contribuirá a comprender aspectos importantes del sistema de producción lo cual beneficiará directamente a los mismos agricultores"<br />
                                            Estadista de la Universidad del Valle. Se vinculó al proyecto hace año y medio con el fin de hacer su tesis con el Proyecto de AESCE: "Un modelo estadístico 
                                            para el cultivo de plátano y la práctica de agricultura específica por sitio".  Ahora apoya las tareas de procesamiento y análisis de información empleando 
                                            métodos de análisis multivariado y regresión.<br />

                                        </p>                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>


