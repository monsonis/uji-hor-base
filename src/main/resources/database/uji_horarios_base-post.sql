drop table uji_horarios.hor_ext_personas cascade constraints;

create or replace view uji_horarios.hor_ext_personas as
select per_id id, nombre, busca_cuenta (per_id) email, act_id actividad_id, ubicacion_id departamento_id
from   grh_vw_contrataciones_ult c
where  act_id in ('PAS', 'PDI')
and    ubicacion_id in (select id
                        from   est_ubic_estructurales
                        where  tuest_id = 'DE'
                        and    status = 'A')
and    act_id = (select max (act_id)
                 from   grh_vw_contrataciones_ult c2
                 where  c.per_id = c2.per_id);
                 
                 
grant select on grh_grh.grh_calendario to uji_horarios, uji_horarios_usr;
                 

drop table uji_horarios.hor_ext_calendario cascade constraints;                 

create or replace view uji_horarios.hor_ext_calendario as                 
select   rownum id, dia, mes, año, tipo_aca tipo_dia, dia_semana,
         decode (dia_semana,
                 'LUNES', 1,
                 'MARTES', 2,
                 'MIÉRCOLES', 3,
                 'JUEVES', 4,
                 'VIERNES', 5,
                 'SÁBADO', 6,
                 'DOMINGO', 7,
                 8
                ) dia_Semana_id,
         fecha_completa fecha
from     grh_calendario 
where    fecha_completa between (select min (fecha_completa)
                                 from   grh_calendario
                                 where  año = 2012
                                 and    semana = to_char (to_Date ('1/9/' || 2012, 'dd/mm/yyyy'), 'ww'))
                            and (select max (fecha_completa)
                                 from   grh_calendario
                                 where  año = (2012+1)
                                 and    semana =
                                           to_char (to_Date ('30/9/' || to_char (2012 + 1), 'dd/mm/yyyy'), 'ww'))
order by año,
         mes,
         dia;                 
         
         
grant select on gra_pod.pod_circuitos_det to uji_horarios, uji_horarios_usr;         
grant select on gra_pod.pod_subgrupos_det to uji_horarios, uji_horarios_usr;         


drop table uji_horarios.hor_ext_circuitos cascade constraints;

create or replace view uji_horarios.hor_ext_circuitos as
select rownum id, SGD_SGR_GRP_CURSO_ACA curso_aca, CIC_TIT_ID estudio_id, SGD_SGR_GRP_ASI_ID asignatura_id,
       SGD_SGR_GRP_ID grupo_id, SGD_SGR_TIPO tipo, SGD_SGR_ID subgrupo_id, SGD_ID detalle_id, CIC_ID circuito_id,
       limite plazas
from   pod_circuitos_det c,
       pod_subgrupos_det d
where  sgd_sgr_grp_curso_aca >= 2011
and    c.SGD_SGR_GRP_CURSO_ACA = d.sgr_grp_curso_aca
and    c.SGD_SGR_TIPO = d.sgr_tipo
and    c.SGD_SGR_ID = d.sgr_id
and    c.SGD_SGR_GRP_ID = d.sgr_grp_id
and    c.SGD_SGR_GRP_ASI_ID = d.sgr_grp_asi_id
and    c.SGD_ID = d.id
         
         
		 
		
         
grant select on grh_grh.grh_cargos_per to uji_horarios, uji_horarios_usr;         


drop table uji_horarios.hor_ext_cargos_per cascade constraints;


create or replace view uji_horarios.hor_ext_cargos_per as
select rownum id, decode(crg_id,28,1,11,2) tipo_cargo_id, per_id persona_id, ulogica_id departamento_id, tit_id estudio_id, ciclo_cargo curso_id
from   grh_grh.grh_cargos_per
where crg_id in (28, 11)

 