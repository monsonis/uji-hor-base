==> hor_dias_semana

select   dia id, decode (dia, 1, 'Dilluns', 2, 'Dimarts', 3, 'Dimecres', 4, 'Dijous', 5, 'Divendres') nombre
from     (select to_char (sysdate, 'd') dia, to_char (sysdate, 'Day') dia_txt_es
          from   dual
          union all
          select to_char (sysdate + 1, 'd'), to_char (sysdate + 1, 'Day')
          from   dual
          union all
          select to_char (sysdate + 2, 'd'), to_char (sysdate + 2, 'Day')
          from   dual
          union all
          select to_char (sysdate + 3, 'd'), to_char (sysdate + 3, 'Day')
          from   dual
          union all
          select to_char (sysdate + 4, 'd'), to_char (sysdate + 4, 'Day')
          from   dual
          union all
          select to_char (sysdate + 5, 'd'), to_char (sysdate + 5, 'Day')
          from   dual
          union all
          select to_char (sysdate + 6, 'd'), to_char (sysdate + 6, 'Day')
          from   dual)
where    dia between 1 and 5
order by 1


==> hor_tipos_subgrupo

select 'AV' id, 'Avaluació' nombre, 7 orden from dual union all
select 'LA' id, 'Laboratori' nombre, 4 orden from dual union all
select 'PR' id, 'Problemes' nombre, 3 orden from dual union all
select 'SE' id, 'Seminari' nombre, 5 orden from dual union all
select 'TE' id, 'Teoria' nombre, 1 orden from dual union all
select 'TP' id, 'Teoria i problemes' nombre, 2 orden from dual union all
select 'TU' id, 'Tutories' nombre, 6 orden from dual 
order by 3


==> hor_semestres

select 1 id, 'Primer semestre' nombre from dual union all
select 2 id, 'Segon semestre' nombre from dual 
order by 2


==> hor_tipos_estudios

select '12C' id, 'Primer i segon cicle' nombre, 2 orden  from dual union all
select 'G' id, 'Graus' nombre, 1 orden  from dual union all
select 'M' id, 'Màsters' nombre, 3 orden from dual
order by 3


==> hor_semestres_detalle (falta fecha_examenes_inicio, fecha_examenes_fin)

select 1 id, 1 semestre_id, '12C' tipo_estudio_id, ini_sem1 fecha_inicio, fin_sem1 fecha_fin, round(((fin_sem1-ini_sem1)/7+0.49999),0) numero_semanas
from pod_cursos_aca
where cursos_aca = 2011
union all
select 2 id, 2 semestre_id, '12C' tipo_estudio_id, ini_sem2 fecha_inicio, fin_sem2 fecha_fin, round(((fin_sem2-ini_sem2)/7+0.49999),0) numero_semanas
from pod_cursos_aca
where cursos_aca = 2011
union all
select 3 id, 1 semestre_id, 'G' tipo_estudio_id, ini_sem1_g fecha_inicio, fin_sem1_g fecha_fin, round(((fin_sem1_g-ini_sem1_g)/7+0.49999),0) numero_semanas
from pod_cursos_aca
where cursos_aca = 2011
union all
select 4 id, 2 semestre_id, 'G' tipo_estudio_id, ini_sem2_g fecha_inicio, fin_sem2_g fecha_fin, round(((fin_sem2_g-ini_sem2_g)/7+0.49999),0) numero_semanas
from pod_cursos_aca
where cursos_aca = 2011
order by 1


==> hor_centros

select id, nombre
from est_ubic_estructurales
where tuest_id = 'CE'
union
select 0 id, 'Incorrecte' nombre
from dual


==> hor_estudios

select id, nombre, tipo_estudio tipo_id, uest_id centro_id, decode(id, 51001,0,51002,0,51003,0,1) oficial
from pod_titulaciones
where tipo_estudio in ('12C','G','M')
and activa = 'S'
order by 1


==> hor_cursos

select distinct id
from pod_cursos
where id <> 7
order by 1


==> hor_cursos_estudios

select tit_id * 100 + id id, id curso_id, tit_id estudio_id
from   pod_cursos
where  id <> 7
and    tit_id in (select id
                  from   pod_titulaciones
                  where  tipo_estudio in ('12C', 'G', 'M')
                  and    activa = 'S') 


==> hor_tipos_asignaturas

select 'S' id, 'Semestral' nombre, 1 orden
from dual
union all
select 'A' id, 'Anual' nombre, 2 orden
from dual
order by 3


==> hor_caracteres

select 'TR' id, 'Troncal' nombre, 1 orden from dual union all
select 'FB' id, 'Formació bàsica' nombre, 2 orden from dual union all
select 'OB' id, 'Obligatoria' nombre, 3 orden from dual union all
select 'OP' id, 'Optativa' nombre, 4 orden from dual union all
select 'LC' id, 'Lliure configuració' nombre, 5 orden from dual union all
select 'PR' id, 'Pràctiques externes' nombre, 6 orden from dual union all
select 'PF' id, 'Treball fi de grau' nombre, 7 orden from dual 
order by 3



==> hor_asignaturas

select distinct a.id, nombre, cur_id curso_id, ta.tit_id estudio_id, caracter caracter_id, tipo tipo_asignatura_id,
       (select decode (count (*), 0, 0, 1)
        from   pod_comunes com,
               pod_grp_comunes gc
        where  gc.id = com.gco_id
        and    curso_aca = ac.curso_aca
        and    asi_id = a.id) comun,
       (select porcentaje
        from   pod_comunes com,
               pod_grp_comunes gc
        where  gc.id = com.gco_id
        and    curso_aca = ac.curso_aca
        and    asi_id = a.id) porcentaje
from   pod_asignaturas a,
       pod_Asignaturas_titulaciones ta,
       pod_asi_cursos ac
where  a.id = ta.asi_id
and    a.id = ac.asi_id
and    curso_aca = 2011
and    ac.cur_tit_id = ta.tit_id
and    tit_id between 201 and 9999
order by 1


==> hor_grupos

select distinct id, decode(id,'Y',1,0) especial
from pod_grupos
where curso_aca = 2011
order by 1


==> hor_grupos_asignaturas

select rownum id, id grupo_id, asi_id asignatura_id, to_number(semestre) semestre_id, limite limite_repetidores, nvl(limite_nuevos,0) limite_nuevos
from pod_grupos
where curso_aca = 2011
and asi_id in (select asi_id from pod_Asignaturas_titulaciones where tit_id between 201 and 9999)



==> hor_circuitos

select rownum id, id circuito_id, nombre, tit_id estudio_id, grp_id grupo_id, decode(grp_id,'Y',1,0) especial
from pod_circuitos_cab
where curso_aca = 2011


==> hor_departamentos

select id, nombre, nvl(uest_id,0) centro_id, nvl(decode(status,'A',1,0),0) activo
from   est_ubic_estructurales e,
       (select * from gri_est.est_relaciones_ulogicas where uest_id in (2,3,4,2922)) u
where  tuest_id = 'DE'
--and    status = 'A'
and    id = uest_id_relacionad (+)
union all
select 0 id, 'Desconocido' nombre, 0 centro_id, 0 
from dual



==> hor_areas  (mal faltan muchos datos)

select id, nombre, departamento_id, activa
from   (select x.*, u.status, row_number () over (partition by x.id order by u.status) orden
        from   (select distinct e.id, e.nombre, nvl (uest_id, 0) departamento_id, decode (e.status, 'A', 1, 0) activa
                from            est_ubic_estructurales e,
                                (select *
                                 from   gri_est.est_relaciones_ulogicas
                                 where  uest_id in (select id
                                                    from   est_ubic_estructurales
                                                    where  tuest_id = 'DE')) r
                where           e.tuest_id = 'AC'
--and    e.status = 'A'
--and    trel_id = 2
                and             e.id = uest_id_relacionad(+)) x,
               est_ubic_estructurales u
        where  departamento_id = u.id(+))
where  orden = 1
union all

select 0 id, 'Desconeguda' nombre, 0 departamento_id, 0 activa
from dual


==> hor_asignaturas_area

select rownum id, area_id, asignatura_id, gestiona_acta, porcentaje
from   (select distinct uest_id area_id, asi_id asignatura_id, decode (recibe_acta, 'S', 1, 0) gestiona_acta,
                        porcentaje
        from            pod_asignaturas_area
        where           curso_aca = 2011
        and             asi_id in (select asi_id
                                   from   pod_asignaturas_titulaciones
                                   where  tit_id between 201 and 9999)
        and             uest_id in (
                           select e.id
                           from   est_ubic_estructurales e,
                                  gri_est.est_relaciones_ulogicas r,
                                  est_ubic_estructurales e2
                           where  e.tuest_id = 'AC'
--and    e.status = 'A'
--and    trel_id = 2
                           and    e.id = uest_id_relacionad
                           and    uest_id = e2.id
                           and    e2.tuest_id = 'DE'
                           and    e2.status = 'A'))


==> hor_profesores

select id, nombre, nvl(departamento_id, 0) departamento_id, nvl(area_id,0) area_id, substr (email, 1, instr (email, '@') - 1) email
from   (select p.id, p.nombre || ' ' || apellido1 || ' ' || apellido2 nombre, ubicacion_id departamento_id, area_id,
               busca_cuenta (per_id) email
        from   grh_vw_contrataciones_ult c,
               per_personas p
        where  act_id = 'PDI'
        and    per_id = p.id
        and    per_id <> 0)

		
==> hor_aulas

select distinct u.id, u.nombre, nvl(u.centro_id,0) centro_id, u.tipo, u.plazas, u.codigo
from            (select 0 id, grc_cur_tit_id titulacion_id, grc_cur_id curso_id, sgr_grp_asi_id asignatura_id, caracter,
                        sgr_grp_id grupo_id, to_number (semestre) semestre_id, sgr_grp_curso_aca curso_aca,
                        sgr_tipo tipo_id, sgr_id subgrupo_id, ubi_id ubicacion_id
                 from   pod_horarios h,
                        pod_circuitos_det cir,
                        pod_asi_cursos ac
                 where  sgr_grp_curso_aca = 2011
                 --and    sgr_grp_asi_id = 'AE1008'
                 --and    sgr_grp_id = 'A'
                 and    sgr_grp_curso_aca = ac.curso_aca
                 and    grc_cur_tit_id = ac.cur_tit_id
                 and    grc_cur_id = ac.cur_id
                 and    sgr_grp_asi_id = ac.asi_id
                 and    cur_tit_id between 201 and 9999
                 and    sgr_grp_curso_aca = sgd_sgr_grp_curso_aca(+)
                 and    sgr_grp_asi_id = sgd_sgr_grp_asi_id(+)
                 and    sgr_grp_id = SGD_SGR_GRP_ID(+)
                 and    sgr_tipo = SGD_SGR_TIPO(+)
                 and    sgr_id = SGD_SGR_ID(+)) x,
                (select u.id id, u.descripcion nombre, u.id aula_id,  uest_id centro_id, tubic_id tipo, num_alumnos plazas,
       edi_are_area || edi_edificio || planta || dependencia || tubic_id codigo
                 from   est_ubicaciones u,
                        est_areas_ubicacion a,
                        est_tipos_ubicacion t
                 where  edi_are_area = a.area
                 and    u.tubic_id = t.id) u
where           ubicacion_id is not null
and             ubicacion_id = u.id(+)        


==> hor_aulas_planificacion

select distinct u.id, u.nombre, u.id aula_id
from            (select 0 id, grc_cur_tit_id titulacion_id, grc_cur_id curso_id, sgr_grp_asi_id asignatura_id, caracter,
                        sgr_grp_id grupo_id, to_number (semestre) semestre_id, sgr_grp_curso_aca curso_aca,
                        sgr_tipo tipo_id, sgr_id subgrupo_id, ubi_id ubicacion_id
                 from   pod_horarios h,
                        pod_circuitos_det cir,
                        pod_asi_cursos ac
                 where  sgr_grp_curso_aca = 2011
                 --and    sgr_grp_asi_id = 'AE1008'
                 --and    sgr_grp_id = 'A'
                 and    sgr_grp_curso_aca = ac.curso_aca
                 and    grc_cur_tit_id = ac.cur_tit_id
                 and    grc_cur_id = ac.cur_id
                 and    sgr_grp_asi_id = ac.asi_id
                 and    cur_tit_id between 201 and 9999
                 and    sgr_grp_curso_aca = sgd_sgr_grp_curso_aca(+)
                 and    sgr_grp_asi_id = sgd_sgr_grp_asi_id(+)
                 and    sgr_grp_id = SGD_SGR_GRP_ID(+)
                 and    sgr_tipo = SGD_SGR_TIPO(+)
                 and    sgr_id = SGD_SGR_ID(+)) x,
                (select u.id id, u.descripcion nombre, u.id aula_id,  uest_id centro_id, tubic_id tipo, num_alumnos plazas,
       edi_are_area || edi_edificio || planta || dependencia || tubic_id codigo
                 from   est_ubicaciones u,
                        est_areas_ubicacion a,
                        est_tipos_ubicacion t
                 where  edi_are_area = a.area
                 and    u.tubic_id = t.id) u
where           ubicacion_id is not null
and             ubicacion_id = u.id(+)


==> hor_items

select rownum id, asignatura_id, estudio_id, curso_id, caracter_id, semestre_id, ubicacion_id aula_planificacion_id,
       circuito_id, persona_id profesor_id,
       (select decode (count (*), 0, 0, 1)
        from   pod_comunes com,
               pod_grp_comunes gc
        where  gc.id = com.gco_id
        and    curso_aca = 2012
        and    asi_id = asignatura_id) comun,
       (select distinct gc.nombre
        from            pod_comunes com,
                        pod_grp_comunes gc
        where           gc.id = com.gco_id
        and             curso_aca = 2012
        and             asi_id = asignatura_id) comun_texto, grupo_id, tipo_subgrupo_id, subgrupo_id, dia_semana_id,
       hora_inicio, hora_fin, null desde_el_dia, null hasta_el_dia, 'N' modifica_detalle
from   (select 0 id, asit.tit_id estudio_id, cur_id curso_id, grp_asi_id asignatura_id, caracter caracter_id,
               grp_id grupo_id, to_number (semestre) semestre_id, grp_curso_aca curso_aca, tipo tipo_subgrupo_id,
               s.id subgrupo_id, null dia_semana_id, null hora_inicio, null hora_fin, null persona_id,
               null ubicacion_id, null compartido, null circuito_id
        from   pod_grupos g,
               pod_subgrupos s,
               pod_asignaturas_titulaciones asit,
               pod_asi_cursos ac
        where  g.curso_aca = s.grp_curso_aca
        and    g.id = s.grp_id
        and    g.asi_id = s.grp_asi_id
        and    s.grp_asi_id = asit.asi_id
        --and    grp_asi_id = 'AE1008'
        --and    grp_id = 'A'
        and    grp_curso_aca = 2012
        and    grp_curso_aca = ac.curso_aca
        and    grp_asi_id = ac.asi_id
        and    asit.tit_id = ac.cur_tit_id
        and    asit.tit_id between 201 and 9999
        and    not exists (
                  select 1
                  from   pod_horarios h
                  where  s.grp_asi_id = sgr_grp_asi_id
                  and    s.grp_id = sgr_grp_id
                  and    s.grp_curso_aca = sgr_grp_curso_aca
                  and    s.id = sgr_id
                  and    s.tipo = sgr_tipo)
        union
        select 0 id, grc_cur_tit_id titulacion_id, grc_cur_id curso_id, sgr_grp_asi_id asignatura_id, caracter,
               sgr_grp_id grupo_id, to_number (semestre) semestre_id, sgr_grp_curso_aca curso_aca, sgr_tipo tipo_id,
               sgr_id subgrupo_id, dia_sem dia_semana,
               to_date ('1-1-1 ' || to_char (ini, 'hh24:mi'), 'dd-mm-yyyy hh24:mi') hora_inicio,
               to_date ('1-1-1 ' || to_char (fin, 'hh24:mi'), 'dd-mm-yyyy hh24:mi') hora_fin, per_id persona_id,
               ubi_id ubicacion_id, compartido, cic_id circuito_id
        from   pod_horarios h,
               pod_circuitos_det cir,
               pod_asi_cursos ac
        where  sgr_grp_curso_aca = 2012
        --and    sgr_grp_asi_id = 'AE1008'
        --and    sgr_grp_id = 'A'
        and    sgr_grp_curso_aca = ac.curso_aca
        and    grc_cur_tit_id = ac.cur_tit_id
        and    grc_cur_id = ac.cur_id
        and    sgr_grp_asi_id = ac.asi_id
        and    cur_tit_id between 201 and 9999
        and    sgr_grp_curso_aca = sgd_sgr_grp_curso_aca(+)
        and    sgr_grp_asi_id = sgd_sgr_grp_asi_id(+)
        and    sgr_grp_id = SGD_SGR_GRP_ID(+)
        and    sgr_tipo = SGD_SGR_TIPO(+)
        and    sgr_id = SGD_SGR_ID(+))
                
		
==> hor_ext_calendario

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
                                 where  año = :p_curso_aca
                                 and    semana = to_char (to_Date ('1/9/' || :p_curso_aca, 'dd/mm/yyyy'), 'ww'))
                            and (select max (fecha_completa)
                                 from   grh_calendario
                                 where  año = :p_curso_aca + 1
                                 and    semana =
                                           to_char (to_Date ('30/9/' || to_char (:p_curso_aca + 1), 'dd/mm/yyyy'), 'ww'))
order by año,
         mes,
         dia;
		 
		 
==> hor_estudios_persona
         
   select tit_id, nombre, per_id, curso_id, cargo_id
   from   (select tit_id, per_id, nvl (ciclo_cargo, 0) curso_id, 'DIR' cargo_id
           from   grh_grh.grh_cargos_per
           where  crg_id in (108, 192, 193, 257, 258)
           and    (   f_fin is null
                   or f_fin >= sysdate)
           and    tit_id is not null
           union
           select tit_id, per_id, nvl (ciclo_cargo, 1) curso_id, 'COR' cargo_id
           from   grh_grh.grh_cargos_per
           where  crg_id in (292, 293, 305, 307)
           and    (   f_fin is null
                   or f_fin >= sysdate)
           and    tit_id is not null
           union
           select a.estudio_id, a.persona_id, nvl (curso_id, 0) curso_id, tipo_permiso cargo_id
           from   uji_guiasdocentes.gdo_directores_extra a
           union
           select pmas_id, per_id, 0 curso_id, 'DIR' cargo_id
           from   gra_pop.pop_comisiones
           where  crg_id in (1, 3)
           and    trunc (fecha_ini) <= trunc (sysdate)
           and    trunc (nvl (fecha_fin, sysdate)) >= trunc (sysdate)) x,
          gra_exp.exp_v_titu_todas t
   where  tit_id = t.id(+)
   and    (   t.tipo = 'G'
           )
   union all
   select tt.id estudio_id, tt.nombre_ca, persona_id, 0 curso_id, 'ADM' cargo_id
   from   hor_estudios tt,
          uji_apa.APA_APLICACIONES_EXTRAS e
   where  (   tipo = 'G'
           )
   and    role_id = 1
   and    e.aplicacion_id = 4;

         
         
==> hor_items_detalles

declare
   v_aux   number;

   cursor lista is
      select i.id item_id, c.fecha dia, hora_inicio, hora_fin, null descripcion
      from   hor_items i,
             hor_estudios e,
             hor_semestres_detalle sd,
             hor_ext_calendario c
      where  i.semestre_id = sd.semestre_id
      and    i.estudio_id = e.id
      and    e.tipo_id = sd.tipo_estudio_id
      and    c.fecha between sd.fecha_inicio and sd.fecha_fin
      and    i.dia_semana_id = c.dia_semana_id
      and    c.tipo_dia in ('L');
begin
   for x in lista loop
      v_aux := uji_horarios.hibernate_sequence.nextval;

      insert into hor_items_detalle
                  (ID, ITEM_ID, DIA, HORA_INICIO, HORA_FIN, DESCRIPCION
                  )
      values      (v_aux, x.item_id, x.dia, x.hora_inicio, x.hora_fin, x.descripcion
                  );

      commit;
   end loop;
end;



         
         
         		
		