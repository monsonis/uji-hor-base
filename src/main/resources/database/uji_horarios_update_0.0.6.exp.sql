ALTER TABLE UJI_HORARIOS.HOR_ITEMS ADD (comun_texto  VARCHAR2(1000));

update hor_items
set comun_texto = (select distinct nombre
                   from            gra_pod.pod_grp_comunes
                   where           nombre like '%' || i.asignatura_id || '%'
                   and             curso_Aca = 2013)
where  comun = 1;

commit;


grant select on gra_pod.pod_grp_comunes to uji_horarios;
grant select on gra_pod.pod_comunes to uji_horarios;

create or replace view hot_ext_asignaturas_comunes (id, grupo_comun_id, nombre, curso_academico_id, asignatura_id) as
select rownum id, id grupo_comun_id, nombre, curso_aca curso_academico_id, asi_id asignatura_id
from   pod_grp_comunes g,
       pod_comunes c
where  g.id = c.gco_id;

/* Formatted on 05/12/2012 13:12 (Formatter Plus v4.8.8) */
CREATE OR REPLACE TRIGGER UJI_HORARIOS.mutante_3_final
   after insert or update of dia_semana_id, desde_el_dia, hasta_el_dia, repetir_cada_semanas, numero_iteraciones
   ON UJI_HORARIOS.HOR_ITEMS
begin
   declare
      v_aux   NUMBER;
      v_id    number;

      cursor reg (v_rowid rowid) is
         select *
         from   uji_horarios.hor_items
         where  rowid = v_rowid;

      cursor lista_detalle (p_id in number) is
         select *
         from   uji_horarios.hor_v_items_detalle
         where  id = p_id
         and    docencia = 'S';
   begin
      for i in 1 .. mutante_items.v_num loop
         for v_reg in reg (mutante_items.v_var_tabla (i)) loop
            if v_reg.detalle_manual = 0 then
               delete      uji_horarios.hor_items_detalle
               where       item_id = v_reg.id;

               for x in lista_detalle (v_reg.id) loop
                  if x.docencia = 'S' then
                     begin
                        v_aux := uji_horarios.hibernate_sequence.nextval;

                        insert into hor_items_detalle
                                    (id, item_id,
                                     inicio,
                                     fin
                                    )
                        values      (v_aux, v_reg.id,
                                     to_date (to_char (x.fecha, 'dd/mm/yyyy') || ' '
                                              || to_char (v_reg.hora_inicio, 'hh24:mi:ss'),
                                              'dd/mm/yyyy hh24:mi:ss'),
                                     to_date (to_char (x.fecha, 'dd/mm/yyyy') || ' '
                                              || to_char (v_reg.hora_fin, 'hh24:mi:ss'),
                                              'dd/mm/yyyy hh24:mi:ss')
                                    );
                     exception
                        when others then
                           null;
                     end;
                  end if;
               end loop;
            end if;
         end loop;
      end loop;
   end;
end mutante_3_final;



update uji_horarios.hor_items
set aula_planificacion_id = null
where aula_planificacion_id in (select id from uji_horarios.hor_aulas_planificacion where estudio_id is null);

commit;


delete UJI_HORARIOS.HOR_AULAS_PLANIFICACION
where estudio_id is null;

commit;

ALTER TABLE UJI_HORARIOS.HOR_AULAS_PLANIFICACION
MODIFY(ESTUDIO_ID  NOT NULL)
;



ALTER TABLE UJI_HORARIOS.HOR_SEMESTRES_DETALLE
 ADD (curso_academico_id  NUMBER);

update UJI_HORARIOS.HOR_SEMESTRES_DETALLE
set curso_academico_id = 2012;

commit;

ALTER TABLE UJI_HORARIOS.HOR_SEMESTRES_DETALLE
MODIFY(CURSO_ACADEMICO_ID  NOT NULL);


CREATE OR REPLACE FORCE VIEW UJI_HORARIOS.HOT_EXT_ASIGNATURAS_COMUNES (ID,
                                                                       GRUPO_COMUN_ID,
                                                                       NOMBRE,
                                                                       ASIGNATURA_ID
                                                                      ) AS
select rownum id, id grupo_comun_id, nombre, asi_id asignatura_id
   from   pod_grp_comunes g,
          pod_comunes c
   where  g.id = c.gco_id
   and    curso_aca = (select max (curso_academico_id)
                       from   uji_horarios.hor_semestres_detalle);


					   
CREATE OR REPLACE FORCE VIEW UJI_HORARIOS.HOR_V_ITEMS_COMUNES (ID, ASIGNATURA_ID, ASIGNATURA_COMUN_ID, ITEM_COMUN_ID) AS
   select i.id, i.asignatura_id, c.asignatura_id asignatura_comun_id, x.id item_comun_id
   from   hor_items i,
          hor_ext_asignaturas_comunes c,
          hor_items x
   where  c.nombre like '%' || i.asignatura_id || '%'
   and    c.asignatura_id <> i.asignatura_id
   and    c.asignatura_id = x.asignatura_id
   and    i.curso_id = x.curso_id
   and    i.semestre_id = x.semestre_id
   and    i.grupo_id = x.grupo_id
   and    i.tipo_subgrupo_id = x.tipo_subgrupo_id
   and    i.subgrupo_id = x.subgrupo_id
   and    i.dia_semana_id = x.dia_Semana_id
   and    to_char (i.hora_inicio, 'hh24:mi') = to_char (x.hora_inicio, 'hh24:mi');




 