-- ==> hor_dias_semana

insert into uji_horarios.hor_dias_semana
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
order by 1;

commit;



-- ==> hor_semestres

insert into uji_horarios.hor_semestres
select 1 id, 'Primer semestre' nombre from dual union all
select 2 id, 'Segon semestre' nombre from dual 
order by 2;
commit;


--==> hor_tipos_estudios

insert into uji_horarios.hor_tipos_estudios
select 'G' id, 'Graus' nombre, 1 orden
from dual;
commit;




==> hor_semestres_detalle (falta fecha_examenes_inicio, fecha_examenes_fin)

insert into uji_horarios.hor_semestres_detalle

ID, SEMESTRE_ID, TIPO_ESTUDIO_ID, FECHA_INICIO, FECHA_FIN, FECHA_EXAMENES_INICIO, FECHA_EXAMENES_FIN, NUMERO_SEMANAS, CURSO_ACADEMICO_ID

select 1 id, 1 semestre_id, 'G' tipo_estudio_id, ini_sem1_g fecha_inicio, fin_sem1_g fecha_fin, fin_sem1_g , fin_sem1_g ,round(((fin_sem1_g-ini_sem1_g)/7+0.49999),0) numero_semanas, cursos_aca
from pod_cursos_aca
where cursos_aca = 2012
union all
select 2 id, 2 semestre_id, 'G' tipo_estudio_id, ini_sem2_g fecha_inicio, fin_sem2_g fecha_fin, fin_sem2_g , fin_sem2_g ,round(((fin_sem2_g-ini_sem2_g)/7+0.49999),0) numero_semanas, cursos_aca
from pod_cursos_aca
where cursos_aca = 2012
order by 1;

commit;


==> hor_centros

select id, nombre
from est_ubic_estructurales
where tuest_id = 'CE'
union
select 0 id, 'Incorrecte' nombre
from dual


==> hor_estudios

select id, nombre, tipo_estudio tipo_id, uest_id centro_id, decode(id, 51001,0,51002,0,51003,0,1) oficial, decode(id, 229, 6, 4) numero_cursos
from pod_titulaciones
where tipo_estudio in ('G')
and activa = 'S'
order by 1



==> hor_circuitos

select rownum id, id id_circuito, nombre, tit_id estudio_id, grp_id grupo_id, decode(grp_id,'Y',1,0) especial
from pod_circuitos_cab
where curso_aca = 2012


==> hor_departamentos

/* Formatted on 17/12/2012 11:32 (Formatter Plus v4.8.8) */
select uest.id, uest.nombre, uest_id centro_id,  estado activo
from   est_estructuras e,
       est_ubic_estructurales uest
where  estado = 1
and    uest_id_relacionad = uest.id
and    tuest_id = 'DE'
and    status = 'A'
and trel_id = 4
union all
select   0 id, 'Desconocido' nombre, 0 centro_id, 0
from     dual
order by 1



==> hor_areas  



/* Formatted on 17/12/2012 11:45 (Formatter Plus v4.8.8) */
select uest.id, uest.nombre, uest_id departamento_id, estado activa
from   est_estructuras e,
       est_ubic_estructurales uest
where  estado = 1
and    uest_id_relacionad = uest.id
and    tuest_id = 'AC'
and    status = 'A'
and    uest_id in (
                  select uest.id
                  from   est_estructuras e,
                         est_ubic_estructurales uest
                  where  estado = 1
                  and    uest_id_relacionad = uest.id
                  and    tuest_id = 'DE'
                  and    status = 'A'
                  and    trel_id = 4)
union all
select 0 id, 'Desconeguda' nombre, 0 departamento_id, 0 activa
from   dual


==> hor_profesores

/* Formatted on 17/12/2012 11:28 (Formatter Plus v4.8.8) */
select id, nombre, nvl (departamento_id, 0) departamento_id, nvl (area_id, 0) area_id,
       substr (email, 1, instr (email, '@') - 1) email
from   (select p.id, p.nombre || ' ' || apellido1 || ' ' || apellido2 nombre, ubicacion_id departamento_id, area_id,
               busca_cuenta (per_id) email
        from   grh_vw_contrataciones_ult c,
               per_personas p
        where  act_id = 'PDI'
        and    per_id = p.id
        and    per_id <> 0)
        
==> hor_aulas

select distinct u.id, u.nombre, nvl(u.centro_id,0) centro_id, u.tipo_nombre tipo, u.plazas, u.codigo
from            (select 0 id, grc_cur_tit_id titulacion_id, grc_cur_id curso_id, sgr_grp_asi_id asignatura_id, caracter,
                        sgr_grp_id grupo_id, to_number (semestre) semestre_id, sgr_grp_curso_aca curso_aca,
                        sgr_tipo tipo_id, sgr_id subgrupo_id, ubi_id ubicacion_id
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
                 and    sgr_id = SGD_SGR_ID(+)) x,
                (select u.id id, u.descripcion nombre, u.id aula_id,  uest_id centro_id, tubic_id tipo, num_alumnos plazas,
       edi_are_area || edi_edificio || planta || dependencia || tubic_id codigo, t.nombre tipo_nombre
                 from   est_ubicaciones u,
                        est_areas_ubicacion a,
                        est_tipos_ubicacion t
                 where  edi_are_area = a.area
                 and    u.tubic_id = t.id) u
where           ubicacion_id is not null
and             ubicacion_id = u.id(+)        



==> hor_aulas_planificacion 

select rownum id, nombre, aula_id, estudio_id, semestre_id
from (
select distinct 0 id, replace(u.nombre,chr(10), ' ') nombre, u.id aula_id, titulacion_id estudio_id, semestre_id
from            (select 0 id, grc_cur_tit_id titulacion_id, grc_cur_id curso_id, sgr_grp_asi_id asignatura_id, caracter,
                        sgr_grp_id grupo_id, to_number (semestre) semestre_id, sgr_grp_curso_aca curso_aca,
                        sgr_tipo tipo_id, sgr_id subgrupo_id, ubi_id ubicacion_id
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
)

==> hor_items  KKKKKKKKKKKKKKK

select rownum id, asignatura_id, asignatura, estudio_id, estudio, tipo_estudio_id, te.nombre tipo_estudio, curso_id,
       caracter_id, cr.nombre caracter, semestre_id, ubicacion_id aula_planificacion_id, persona_id profesor_id,
       grupo_id, tipo_subgrupo_id, tsg.nombre tipo_subgrupo, subgrupo_id, dia_semana_id, hora_inicio, hora_fin,
       null desde_el_dia, null hasta_el_dia, 'N' modifica_detalle, tipo_asignatura_id, ta.nombre tipo_asignatura, comun,
       porcentaje_comun, plazas
from   (select 0 id, asit.tit_id estudio_id, t.nombre estudio, t.tipo_estudio tipo_estudio_id, cur_id curso_id,
               grp_asi_id asignatura_id, caracter caracter_id, grp_id grupo_id, to_number (semestre) semestre_id,
               grp_curso_aca curso_aca, s.tipo tipo_subgrupo_id, s.id subgrupo_id, null dia_semana_id, null hora_inicio,
               null hora_fin, null persona_id, null ubicacion_id, null compartido, a.tipo tipo_asignatura_id,
               a.nombre asignatura, (select decode (count (*), 0, 0, 1)
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
                and    asi_id = a.id) porcentaje_comun, s.limite_nuevos plazas
        from   pod_grupos g,
               pod_subgrupos s,
               pod_asignaturas_titulaciones asit,
               pod_asi_cursos ac,
               pod_asignaturas a,
               pod_titulaciones t
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
        and    asit.tit_id = t.id
        and    g.asi_id = a.id
        and    not exists (
                  select 1
                  from   pod_horarios h
                  where  s.grp_asi_id = sgr_grp_asi_id
                  and    s.grp_id = sgr_grp_id
                  and    s.grp_curso_aca = sgr_grp_curso_aca
                  and    s.id = sgr_id
                  and    s.tipo = sgr_tipo)
        union
        select 0 id, grc_cur_tit_id titulacion_id, t.nombre estudio, t.tipo_estudio tipo_estudio_id,
               grc_cur_id curso_id, sgr_grp_asi_id asignatura_id, caracter, sgr_grp_id grupo_id,
               to_number (semestre) semestre_id, sgr_grp_curso_aca curso_aca, sgr_tipo tipo_id, sgr_id subgrupo_id,
               dia_sem dia_semana, to_date ('1-1-1 ' || to_char (ini, 'hh24:mi'), 'dd-mm-yyyy hh24:mi') hora_inicio,
               to_date ('1-1-1 ' || to_char (fin, 'hh24:mi'), 'dd-mm-yyyy hh24:mi') hora_fin, per_id persona_id,
               ubi_id ubicacion_id, compartido, a.tipo tipo_asignatura_id, a.nombre asignatura,
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
                and    asi_id = a.id) porcentaje_comun, s.limite_nuevos plazas
        from   pod_horarios h,
               pod_asi_cursos ac,
               pod_asignaturas a,
               pod_titulaciones t,
               pod_subgrupos s
        where  sgr_grp_curso_aca = 2012
        --and    sgr_grp_asi_id = 'AE1008'
        --and    sgr_grp_id = 'A'
        and    sgr_grp_curso_aca = ac.curso_aca
        and    grc_cur_tit_id = ac.cur_tit_id
        and    grc_cur_id = ac.cur_id
        and    sgr_grp_asi_id = ac.asi_id
        and    cur_tit_id between 201 and 9999
        and    cur_tit_id = t.id
        and    ac.asi_id = a.id
        and    s.GRP_ASI_ID = h.sgr_grp_asi_id
        and    s.GRP_ID = h.sgr_grp_id
        and    s.GRP_CURSO_ACA = h.sgr_grp_curso_aca
        and    s.ID = h.sgr_id
        and    s.TIPO = h.sgr_tipo) i,
       (select 'AV' id, 'Avaluació' nombre, 7 orden
        from   dual
        union all
        select 'LA' id, 'Laboratori' nombre, 4 orden
        from   dual
        union all
        select 'PR' id, 'Problemes' nombre, 3 orden
        from   dual
        union all
        select 'SE' id, 'Seminari' nombre, 5 orden
        from   dual
        union all
        select 'TE' id, 'Teoria' nombre, 1 orden
        from   dual
        union all
        select 'TP' id, 'Teoria i problemes' nombre, 2 orden
        from   dual
        union all
        select 'TU' id, 'Tutories' nombre, 6 orden
        from   dual) tsg,
       (select 'S' id, 'Semestral' nombre, 1 orden
        from   dual
        union all
        select 'A' id, 'Anual' nombre, 2 orden
        from   dual) ta,
       (select 'TR' id, 'Troncal' nombre, 1 orden
        from   dual
        union all
        select 'FB' id, 'Formació bàsica' nombre, 2 orden
        from   dual
        union all
        select 'OB' id, 'Obligatoria' nombre, 3 orden
        from   dual
        union all
        select 'OP' id, 'Optativa' nombre, 4 orden
        from   dual
        union all
        select 'LC' id, 'Lliure configuració' nombre, 5 orden
        from   dual
        union all
        select 'PR' id, 'Pràctiques externes' nombre, 6 orden
        from   dual
        union all
        select 'PF' id, 'Treball fi de grau' nombre, 7 orden
        from   dual) cr,
       (select '12C' id, 'Primer i segon cicle' nombre, 2 orden
        from   dual
        union all
        select 'G' id, 'Grau' nombre, 1 orden
        from   dual
        union all
        select 'M' id, 'Màster' nombre, 3 orden
        from   dual) te
where  tipo_subgrupo_id = tsg.id
and    tipo_asignatura_id = ta.id
and    caracter_id = cr.id
and    tipo_estudio_id = te.id
--and    asignatura_id = 'DR1001'
        

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
         
         
==> hor_tipos_cargos

select 1 id, 'Director d''estudi' nombre
from dual
union all
select 2 id, 'Coordinador de curs' nombre
from dual
union all
select 3 id, 'Director, Dega o Secretari de Centre' nombre
from dual
union all
select 4 id, 'PAS de centre' nombre
from dual
union all
select 5 id, 'PAS de departament' nombre
from dual
union all
select 0 id, 'Error' nombre
from dual

         
==> hor_ext_personas

select per_id id, nombre, busca_cuenta (per_id) email, act_id actividad_id, ubicacion_id departamento_id
from   grh_vw_contrataciones_ult c
where  act_id in ('PAS', 'PDI')
and    ubicacion_id in (select id
                        from   est_ubic_estructurales
                        where  tuest_id = 'DE'
                        and    status = 'A')
and    act_id = (select max (act_id)
                 from   grh_vw_contrataciones_ult c2
                 where  c.per_id = c2.per_id)
                 

/*         
==> hor_estudios_persona
         
   select rownum id, cargo_id tipo_cargo_id, per_id persona_id, tit_id estudio_id, curso_id 
   from   (select tit_id, per_id, nvl (ciclo_cargo, 0) curso_id, 1 cargo_id
           from   grh_grh.grh_cargos_per
           where  crg_id in (108, 192, 193, 257, 258)
           and    (   f_fin is null
                   or f_fin >= sysdate)
           and    tit_id is not null
           union
           select tit_id, per_id, nvl (ciclo_cargo, 1) curso_id, 2 cargo_id
           from   grh_grh.grh_cargos_per
           where  crg_id in (292, 293, 305, 307)
           and    (   f_fin is null
                   or f_fin >= sysdate)
           and    tit_id is not null
           union
           select a.estudio_id, a.persona_id, nvl (curso_id, 0) curso_id, decode(tipo_permiso,'DIR',1,'COR',2,0) cargo_id
           from   uji_guiasdocentes.gdo_directores_extra a
           union
           select pmas_id, per_id, 0 curso_id, 1 cargo_id
           from   gra_pop.pop_comisiones
           where  crg_id in (1, 3)
           and    trunc (fecha_ini) <= trunc (sysdate)
           and    trunc (nvl (fecha_fin, sysdate)) >= trunc (sysdate)) x,
          gra_exp.exp_v_titu_todas t
   where  tit_id = t.id(+)
   and    (   t.tipo in ('G')
           )
           
           
           
   union all
   select tt.id estudio_id, tt.nombre_ca, persona_id, 0 curso_id, 'ADM' cargo_id
   from   uji_horarios.hor_estudios tt,
          uji_apa.APA_APLICACIONES_EXTRAS e
   where  (   tipo = 'G'
           )
   and    role_id = 1
   and    e.aplicacion_id = 4;
*/


==> hor_ext_circuitos

select rownum id, SGD_SGR_GRP_CURSO_ACA curso_aca, CIC_TIT_ID estudio_id, SGD_SGR_GRP_ASI_ID asignatura_id,
       SGD_SGR_GRP_ID grupo_id, SGD_SGR_TIPO tipo, SGD_SGR_ID subgrupo_id, SGD_ID detalle_id, CIC_ID circuito_id,
       limite plazas
from   pod_circuitos_det c,
       pod_subgrupos_det d
where  sgd_sgr_grp_curso_aca = 2012
--and    sgd_sgr_grp_asi_id = 'RL0908'
and    c.SGD_SGR_GRP_CURSO_ACA = d.sgr_grp_curso_aca
and    c.SGD_SGR_TIPO = d.sgr_tipo
and    c.SGD_SGR_ID = d.sgr_id
and    c.SGD_SGR_GRP_ID = d.sgr_grp_id
and    c.SGD_SGR_GRP_ASI_ID = d.sgr_grp_asi_id
and    c.SGD_ID = d.id


         
==> hor_items_detalles

declare
   v_aux   number;

   cursor lista is
      select i.id item_id, c.fecha dia, hora_inicio, hora_fin, null descripcion
      from   uji_horarios.hor_items i,
             uji_horarios.hor_estudios e,
             uji_horarios.hor_semestres_detalle sd,
             uji_horarios.hor_ext_calendario c
      where  i.semestre_id = sd.semestre_id
      and    i.estudio_id = e.id
      and    e.tipo_id = sd.tipo_estudio_id
      and    c.fecha between sd.fecha_inicio and sd.fecha_fin
      and    i.dia_semana_id = c.dia_semana_id
      and    c.tipo_dia in ('L');
begin
   for x in lista loop
      v_aux := uji_horarios.hibernate_sequence.nextval;

      insert into uji_horarios.hor_items_detalle
                  (ID, ITEM_ID,
                   INICIO,
                   FIN,
                   DESCRIPCION
                  )
      values      (v_aux, x.item_id,
                   to_date (to_char (x.dia, 'dd/mm/yyyy') || ' ' || to_char (x.hora_inicio, 'hh24:mi'),
                            'dd/mm/yyyy hh24:mi'),
                   to_date (to_char (x.dia, 'dd/mm/yyyy') || ' ' || to_char (x.hora_fin, 'hh24:mi'),
                            'dd/mm/yyyy hh24:mi'),
                   x.descripcion
                  );

      commit;
   end loop;
end;

         
         
==> hor_items_circuitos

insert into uji_horarios.hor_items_circuitos
            (id, item_id, circuito_id, plazas)
   select rownum id, i.id item_id, circuito_id, e.plazas
   from   uji_horarios.hor_ext_circuitos e,
          uji_horarios.hor_circuitos c,
          uji_horarios.hor_items i
   where  e.estudio_id = c.estudio_id
   and    e.circuito_id = c.id_circuito
   and    e.asignatura_id = i.asignatura_id
   and    e.estudio_id = i.estudio_id
   and    e.grupo_id = i.grupo_id
   and    e.tipo = i.tipo_subgrupo_id
   and    e.subgrupo_id = i.subgrupo_id;
   
   

==> hor_items_comunes

                 
select rownum id, id item_id, asignatura_id, asignatura_comun_id, item_comun_id
from (
   select i.id, i.asignatura_id, c.asignatura_id asignatura_comun_id, x.id item_comun_id
   from   uji_horarios.hor_items i,
          uji_horarios.hor_ext_asignaturas_comunes c,
          uji_horarios.hor_items x
   where  c.nombre like '%' || i.asignatura_id || '%'
   and    c.asignatura_id <> i.asignatura_id
   and    c.asignatura_id = x.asignatura_id
   and    i.curso_id = x.curso_id
   and    i.semestre_id = x.semestre_id
   and    i.grupo_id = x.grupo_id
   and    i.tipo_subgrupo_id = x.tipo_subgrupo_id
   and    i.subgrupo_id = x.subgrupo_id
   and    i.dia_semana_id = x.dia_Semana_id
   and    to_char (i.hora_inicio, 'hh24:mi') = to_char (x.hora_inicio, 'hh24:mi'));
   
   