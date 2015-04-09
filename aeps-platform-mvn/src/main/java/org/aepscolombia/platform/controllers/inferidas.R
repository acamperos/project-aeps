
load("funciones_AESCE.RData") 

#RUN ONE OBSERVATION

myvec <-  c("ID_LOTE","ID_FINCA","PENDIENTE_RASTA","TERRENO_CIRCUN_RASTA","POSICION_PERFIL_RASTA",
            "NO_CAPAS_RASTA","ESPESOR","COLOR_SECO","COLOR_HUMEDO","TEXTURA","RESIST_ROMPIMIENTO",
            "PH_RASTA","CARBONATOS_RASTA","PROFUNDIDAD_CARBONATOS","PEDREG_SUPERF_PIEDRAS","PEDREG_SUPERF_ROCAS",
            "PEDREG_PERFIL_PIEDRAS","PEDREG_PERFIL_ROCAS","HOR_PEDREG_ROCOSO_RASTA",
            "HOR_PEDREG_ROCOSO_PROFUNDIDAD_RASTA","HOR_PEDREG_ROCOSO_ESPESOR_RASTA",
            "PROFUND_PRIMERAS_ROCAS_PIEDRAS_RASTA (CM)","CAP_ENDURE_RASTA","PROFUND_CAP_ENDURE_RASTA",
            "ESPESOR_CAP_ENDURE_RASTA","MOTEADOS_RASTA","PROFUND_MOTEADOS_RASTA","MOTEADOS_MAS70cm _RASTA",
            "ESTRUCTURA_RASTA","OBSERVA_EROSION_RASTA","OBSERVA_MOHO_RASTA","OBSERVA_COSTRAS_DURAS_RASTA",
            "SITIO_EXPUESTO_SOL_RASTA","OBSERVA_COSTRAS_BLANCAS_RASTA","OBSERVA_COSTAS_NEGRAS_RASTA",
            "REGION_SECA_ARIDA_RASTA","OBSERVA_RAICES_VIVAS_RASTA","PROFUND_RAICES_VIVAS_RASTA",
            "OBSERVA_PLANTAS_PEQUENAS_RASTA","OBSERVA_HOJARASCA_MO_RASTA","SUELO_NEGRO_BLANDO_RASTA",
            "CUCHILLO_PRIMER_HTE_RASTA","CERCA_RIOS_QUEBRADAS_RASTA","RECUBRIMIENTO_VEGETAL__SUELO_RASTA")


vec <- read.table("clipboard",sep="\t")


vec <- as.data.frame(vec)
colnames(vec) <- myvec
print(vec)


#COMPUTE OF INFERENCE VARIABLES



rasta <- vec

prof_efectiva     <- 0
materia_organica  <- 0
drenaje_interno   <- 0

# CICLO PARA EXTRAER DE CADA FILA DE LA MATRIZ LA PRO.EFC, MAT.ORG Y DREN.INT


prof_efectiva    <- prof.efect(rasta[1,])[[2]]
materia_organica <- materia.organica.perf(rasta[1,])
drenaje_interno  <- drenaje.interno(rasta[1,])

rasta$d.interno  <- drenaje_interno
drenaje_externo  <- drenaje.externo(rasta[1,])
  
inferidas        <- c(prof_efectiva,materia_organica,drenaje_interno,drenaje_externo)
print(inferidas)


