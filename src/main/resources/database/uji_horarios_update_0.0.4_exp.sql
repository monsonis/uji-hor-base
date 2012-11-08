ALTER TABLE UJI_HORARIOS.HOR_ITEMS
 ADD (repetir_cada_semanas  NUMBER);

ALTER TABLE UJI_HORARIOS.HOR_ITEMS
 ADD (numero_iteraciones  NUMBER);

ALTER TABLE UJI_HORARIOS.HOR_ITEMS
 ADD (detalle_manual  NUMBER                        DEFAULT 0                     NOT NULL);
 

ALTER TABLE UJI_HORARIOS.HOR_ITEMS DROP COLUMN MODIFICA_DETALLE;


CREATE INDEX uji_horarios.hor_items_v_idx ON uji_horarios.hor_items 
    ( 
     estudio_id ASC , 
     curso_id ASC , 
     semestre_id ASC , 
     asignatura_id ASC , 
     grupo_id ASC , 
     tipo_subgrupo_id ASC , 
     subgrupo_id ASC , 
     dia_semana_id ASC 
    ) 
;
CREATE INDEX uji_horarios.hor_items_v2_IDX ON uji_horarios.hor_items 
    ( 
     id ASC , 
     estudio_id ASC , 
     dia_semana_id ASC , 
     detalle_manual ASC 
    ) 
;
CREATE INDEX uji_horarios.hor_item_det_man_idx ON uji_horarios.hor_items 
    ( 
     id ASC , 
     detalle_manual ASC 
    ) 
;

CREATE INDEX uji_horarios.hor_semestres_detalle_v_idx ON uji_horarios.hor_semestres_detalle 
    ( 
     tipo_estudio_id ASC 
    ) 
;
 
CREATE INDEX uji_horarios.hor_ext_cal_fecha_idx ON uji_horarios.hor_ext_calendario 
    ( 
     fecha ASC , 
     tipo_dia ASC , 
     dia_semana_id ASC 
    ) 
;
 
 
CREATE INDEX uji_horarios.hor_items_detalle_fechas_IDX ON uji_horarios.hor_items_detalle 
    ( 
     item_id ASC , 
     inicio ASC 
    ) 
;
 
 
CREATE OR REPLACE FORCE VIEW UJI_HORARIOS.HOR_V_ITEMS_DETALLE (ID,
                                                               FECHA,
                                                               DOCENCIA_PASO_1,
                                                               DOCENCIA_PASO_2,
                                                               DOCENCIA,
                                                               ORDEN_ID,
                                                               NUMERO_ITERACIONES,
                                                               REPETIR_CADA_SEMANAS,
                                                               FECHA_INICIO,
                                                               FECHA_FIN,
                                                               ESTUDIO_ID,
                                                               SEMESTRE_ID,
                                                               CURSO_ID,
                                                               ASIGNATURA_ID,
                                                               GRUPO_ID,
                                                               TIPO_SUBGRUPO_ID,
                                                               SUBGRUPO_ID,
                                                               DIA_SEMANA_ID
                                                              ) AS
select i.id, fecha, docencia docencia_paso_1,
                 decode (nvl (d.repetir_cada_semanas, 1),
                         1, docencia,
                         decode (mod (orden_id, d.repetir_cada_semanas), 1, docencia, 'N')
                        ) docencia_paso_2,
                 decode (d.numero_iteraciones,
                         null, decode (nvl (d.repetir_cada_semanas, 1),
                                       1, docencia,
                                       decode (mod (orden_id, d.repetir_cada_semanas), 1, docencia, 'N')
                                      ),
                         decode (sign ((orden_id / d.repetir_cada_Semanas) - d.numero_iteraciones),
                                 -1, decode (nvl (d.repetir_cada_semanas, 1),
                                             1, docencia,
                                             decode (mod (orden_id, d.repetir_cada_semanas), 1, docencia, 'N')
                                            ),
                                 'N'
                                )
                        ) docencia,
                 d.orden_id, d.numero_iteraciones, d.repetir_cada_semanas, d.fecha_inicio, d.fecha_fin, d.estudio_id,
                 d.semestre_id, d.curso_id, d.asignatura_id, d.grupo_id, d.tipo_subgrupo_id, d.subgrupo_id,
                 d.dia_semana_id
          from   (select id, fecha,
                         row_number () over (partition by decode
                                                             (hor_f_fecha_entre (x.fecha, fecha_inicio, fecha_fin),
                                                              'S', decode (hor_f_fecha_entre (x.fecha,
                                                                                              nvl (desde_el_dia,
                                                                                                   fecha_inicio),
                                                                                              nvl (hasta_el_dia,
                                                                                                   fecha_fin)),
                                                                           'S', 'S',
                                                                           'N'
                                                                          ),
                                                              'N'
                                                             ), id, estudio_id, semestre_id, curso_id, asignatura_id, grupo_id, tipo_subgrupo_id, subgrupo_id, dia_semana_id order by fecha)
                                                                                                               orden_id,
                         decode (hor_f_fecha_entre (x.fecha, fecha_inicio, fecha_fin),
                                 'S', decode (hor_f_fecha_entre (x.fecha, nvl (desde_el_dia, fecha_inicio),
                                                                 nvl (hasta_el_dia, fecha_fin)),
                                              'S', 'S',
                                              'N'
                                             ),
                                 'N'
                                ) docencia,
                         estudio_id, curso_id, semestre_id, grupo_id, tipo_subgrupo_id, subgrupo_id, dia_Semana_id,
                         asignatura_id, fecha_inicio, fecha_fin, fecha_examenes_inicio, fecha_examenes_fin,
                         desde_el_dia, hasta_el_dia, repetir_cada_semanas, numero_iteraciones, detalle_manual, tipo_dia,
                         dia_semana
                  from   (select i.id, i.estudio_id, i.curso_id, i.semestre_id, i.grupo_id, i.tipo_subgrupo_id,
                                 i.subgrupo_id, i.dia_Semana_id, i.asignatura_id, fecha_inicio, fecha_fin,
                                 fecha_examenes_inicio, fecha_examenes_fin, i.desde_el_dia, hasta_el_dia,
                                 repetir_cada_semanas, numero_iteraciones, detalle_manual, c.fecha, tipo_dia,
                                 dia_semana
                          from   hor_estudios e,
                                 hor_semestres_detalle s,
                                 hor_items i,
                                 hor_ext_calendario c
                          where  e.tipo_id = s.tipo_estudio_id
                          and    i.estudio_id = e.id
                          and    i.semestre_id = s.semestre_id
                          and    c.fecha between fecha_inicio and nvl (fecha_examenes_fin, fecha_fin)
                          and    c.dia_semana_id = i.dia_semana_id
                          and    tipo_dia in ('L', 'E')
                          and    detalle_manual = 0) x) d,
                 hor_items i
          where  i.estudio_id = d.estudio_id
          and    i.curso_id = d.curso_id
          and    i.semestre_id = d.semestre_id
          and    i.asignatura_id = d.asignatura_id
          and    i.grupo_id = d.grupo_id
          and    i.tipo_subgrupo_id = d.tipo_subgrupo_id
          and    i.subgrupo_id = d.subgrupo_id
          and    i.dia_semana_id = d.dia_semana_id
          and    i.detalle_manual = 0
          and    i.id = d.id
          union all
          select c.id, c.fecha, 'N' docencia_paso_1, null docencia_paso_2, decode (d.id, null, 'N', 'S') docencia,
                 1 orden_id, numero_iteraciones, repetir_cada_semanas, fecha_inicio, fecha_fin, estudio_id, semestre_id,
                 curso_id, asignatura_id, grupo_id, tipo_subgrupo_id, subgrupo_id, dia_semana_id
          from   (select i.id, c.fecha, numero_iteraciones, repetir_cada_semanas, s.fecha_inicio, s.fecha_fin,
                         i.estudio_id, i.semestre_id, i.curso_id, i.asignatura_id, i.grupo_id, i.tipo_subgrupo_id,
                         i.subgrupo_id, i.dia_semana_id
                  from   hor_estudios e,
                         hor_semestres_detalle s,
                         hor_items i,
                         hor_ext_calendario c
                  where  e.tipo_id = s.tipo_estudio_id
                  and    i.estudio_id = e.id
                  and    i.semestre_id = s.semestre_id
                  and    c.fecha between fecha_inicio and nvl (fecha_examenes_fin, fecha_fin)
                  and    c.dia_semana_id = i.dia_semana_id
                  and    tipo_dia in ('L', 'E')
                  and    detalle_manual = 1) c,
                 hor_items_detalle d
          where  c.id = d.item_id(+)
          and    c.fecha = d.inicio(+);



 

CREATE OR REPLACE TRIGGER UJI_HORARIOS.MUTANTE_1_INICIAL BEFORE INSERT OR UPDATE ON UJI_HORARIOS.HOR_ITEMS
BEGIN
  MUTANTE_ITEMS.V_NUM := 0;
END;


CREATE OR REPLACE TRIGGER uji_horarios.mutante_2_por_fila
   after insert or update of dia_semana_id, desde_el_dia, hasta_el_dia, repetir_cada_semanas, numero_iteraciones
   on uji_horarios.hor_items
   referencing old as new new as old
   for each row
begin
   mutante_items.v_num := mutante_items.v_num + 1;
   mutante_items.v_var_tabla (mutante_items.v_num) := :new.rowid;
end;


CREATE OR REPLACE TRIGGER UJI_HORARIOS.mutante_3_final
   after insert or update of repetir_cada_semanas
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
                                    (id, item_id, inicio, fin
                                    )
                        values      (v_aux, v_reg.id, x.fecha, x.fecha
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


CREATE OR REPLACE TRIGGER uji_horarios.hor_items_delete
   before delete
   on uji_horarios.hor_items
   referencing new as new old as old
   for each row
begin
   delete      uji_horarios.hor_items_detalle
   where       item_id = :old.id;
end hor_items_delete;


 