delete hor_items_detalle;

delete hor_items_comunes;

delete hor_items_circuitos;

delete hor_items;

commit;


ALTER TABLE UJI_HORARIOS.HOR_ITEMS
  DROP CONSTRAINT HOR_ITEMS_HOR_ESTUDIOS_FK;

ALTER TABLE UJI_HORARIOS.HOR_ITEMS DROP COLUMN ASIGNATURA_ID;

ALTER TABLE UJI_HORARIOS.HOR_ITEMS DROP COLUMN ASIGNATURA;

ALTER TABLE UJI_HORARIOS.HOR_ITEMS DROP COLUMN ESTUDIO_ID;

ALTER TABLE UJI_HORARIOS.HOR_ITEMS DROP COLUMN ESTUDIO;

  
CREATE TABLE uji_horarios.hor_items_asignaturas 
    ( 
     id NUMBER  NOT NULL , 
     item_id NUMBER  NOT NULL , 
     asignatura_id VARCHAR2 (10)  NOT NULL , 
     asignatura VARCHAR2 (1000) , 
     estudio_id NUMBER  NOT NULL , 
     estudio VARCHAR2 (1000) 
    ) 
;



ALTER TABLE uji_horarios.hor_items_asignaturas 
    ADD CONSTRAINT hor_items_asignaturas_PK PRIMARY KEY ( id ) ;




ALTER TABLE uji_horarios.hor_items_asignaturas 
    ADD CONSTRAINT hor_items_asig_estudios_FK FOREIGN KEY 
    ( 
     estudio_id
    ) 
    REFERENCES uji_horarios.hor_estudios 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_items_asignaturas 
    ADD CONSTRAINT hor_items_asig_items_FK FOREIGN KEY 
    ( 
     item_id
    ) 
    REFERENCES uji_horarios.hor_items 
    ( 
     id
    ) 
;

CREATE INDEX uji_horarios.hor_items_asig_it_IDX ON uji_horarios.hor_items_asignaturas 
    ( 
     item_id ASC 
    ) 
;
CREATE INDEX uji_horarios.hor_items_asig_est_IDX ON uji_horarios.hor_items_asignaturas 
    ( 
     estudio_id ASC 
    ) 
;
CREATE INDEX uji_horarios.hor_items_asig_est_asi_IDX ON uji_horarios.hor_items_asignaturas 
    ( 
     asignatura_id ASC , 
     estudio_id ASC 
    ) 
;

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
         select distinct id, fecha, docencia_paso_1, docencia_paso_2, docencia, orden_id, numero_iteraciones,
                         repetir_cada_semanas, fecha_inicio, fecha_fin, semestre_id, curso_id, grupo_id,
                         tipo_subgrupo_id, subgrupo_id, dia_semana_id, tipo_dia, festivos
         from            uji_horarios.hor_v_items_detalle
         where           id = p_id
         and             docencia = 'S';
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


CREATE INDEX hor_items_v_idx ON hor_items 
    ( 
     curso_id ASC , 
     semestre_id ASC , 
     grupo_id ASC , 
     tipo_subgrupo_id ASC , 
     subgrupo_id ASC , 
     dia_semana_id ASC 
    ) 
    LOGGING 
;
CREATE INDEX hor_items_v2_IDX ON hor_items 
    ( 
     id ASC , 
     dia_semana_id ASC , 
     detalle_manual ASC 
    ) 
    LOGGING 
;
CREATE INDEX hor_item_det_man_idx ON hor_items 
    ( 
     id ASC , 
     detalle_manual ASC 
    ) 
    LOGGING 
;




CREATE OR REPLACE VIEW uji_horarios.hor_v_items_detalle AS
SELECT i.id,
  d.fecha,
  d.docencia docencia_paso_1,
  DECODE(NVL(d.repetir_cada_semanas, 1), 1, d.docencia, DECODE(MOD(d.orden_id, d.repetir_cada_semanas), 1, d.docencia, 'N')) docencia_paso_2,
  DECODE(d.tipo_dia, 'F', 'N', DECODE(d.numero_iteraciones, NULL, DECODE(NVL(d.repetir_cada_semanas, 1), 1, d.docencia, DECODE(MOD(d.orden_id, d.repetir_cada_semanas), 1, d.docencia, 'N')), DECODE(SIGN(((d.orden_id - d.festivos) / d.repetir_cada_semanas) - (d.numero_iteraciones)), 1, 'N', DECODE(NVL(d.repetir_cada_semanas, 1), 1, d.docencia, DECODE(MOD(d.orden_id, d.repetir_cada_semanas), 1, d.docencia, 'N'))))) docencia,
  d.orden_id,
  d.numero_iteraciones,
  d.repetir_cada_semanas,
  d.fecha_inicio,
  d.fecha_fin,
  d.estudio_id,
  d.semestre_id,
  d.curso_id,
  d.asignatura_id,
  d.grupo_id,
  d.tipo_subgrupo_id,
  d.subgrupo_id,
  d.dia_semana_id,
  d.tipo_dia,
  d.festivos
FROM
  (SELECT x.id,
    x.fecha,
    hor_contar_festivos(NVL(x.desde_el_dia, x.fecha_inicio), x.fecha, x.dia_semana_id, x.repetir_cada_semanas) festivos,
    ROW_NUMBER() OVER (PARTITION BY DECODE(DECODE(SIGN(x.fecha - x.fecha_inicio), -1, 'N', DECODE(SIGN(x.fecha_fin - x.fecha), -1, 'N', 'S')), 'S', DECODE(DECODE(SIGN(x.fecha - NVL(x.desde_el_dia, x.fecha_inicio)), -1, 'N', DECODE(SIGN(NVL(x.hasta_el_dia, x.fecha_fin) - x.fecha), -1, 'N', 'S')), 'S', 'S', 'N'), 'N'), x.id, x.estudio_id, x.semestre_id, x.curso_id, x.asignatura_id, x.grupo_id, x.tipo_subgrupo_id, x.subgrupo_id, x.dia_semana_id ORDER BY x.fecha) orden_id,
    DECODE(hor_f_fecha_entre(x.fecha, x.fecha_inicio, x.fecha_fin), 'S', DECODE(hor_f_fecha_entre(x.fecha, NVL(x.desde_el_dia, x.fecha_inicio), NVL(x.hasta_el_dia, x.fecha_fin)), 'S', 'S', 'N'), 'N') docencia,
    x.estudio_id,
    x.curso_id,
    x.semestre_id,
    x.grupo_id,
    x.tipo_subgrupo_id,
    x.subgrupo_id,
    x.dia_semana_id,
    x.asignatura_id,
    x.fecha_inicio,
    x.fecha_fin,
    x.fecha_examenes_inicio,
    x.fecha_examenes_fin,
    x.desde_el_dia,
    x.hasta_el_dia,
    x.repetir_cada_semanas,
    x.numero_iteraciones,
    x.detalle_manual,
    x.tipo_dia,
    x.dia_semana
  FROM
    (SELECT i.id,
      ia.estudio_id,
      i.curso_id,
      i.semestre_id,
      i.grupo_id,
      i.tipo_subgrupo_id,
      i.subgrupo_id,
      i.dia_semana_id,
      ia.asignatura_id,
      s.fecha_inicio,
      s.fecha_fin,
      s.fecha_examenes_inicio,
      s.fecha_examenes_fin,
      i.desde_el_dia,
      i.hasta_el_dia,
      i.repetir_cada_semanas,
      i.numero_iteraciones,
      i.detalle_manual,
      c.fecha,
      c.tipo_dia,
      c.dia_semana
    FROM hor_estudios e,
      hor_semestres_detalle s,
      hor_items i,
      hor_items_asignaturas ia,
      hor_ext_calendario c
    WHERE i.id            = ia.item_id
    AND e.tipo_id         = s.tipo_estudio_id
    AND ia.estudio_id     = e.id
    AND i.semestre_id     = s.semestre_id
    AND c.dia_semana_id   = i.dia_semana_id
    AND (i.detalle_manual = 0
    AND c.tipo_dia       IN ('L', 'E', 'F')
    AND TRUNC(c.fecha) BETWEEN s.fecha_inicio AND NVL(s.fecha_examenes_fin, s.fecha_fin)
    AND c.vacaciones = 0)
    ) x
  ) d,
  hor_items i,
  hor_items_asignaturas ia
WHERE i.id             = ia.item_id
AND ia.estudio_id      = d.estudio_id
AND i.curso_id         = d.curso_id
AND i.semestre_id      = d.semestre_id
AND ia.asignatura_id   = d.asignatura_id
AND i.grupo_id         = d.grupo_id
AND i.tipo_subgrupo_id = d.tipo_subgrupo_id
AND i.subgrupo_id      = d.subgrupo_id
AND i.dia_semana_id    = d.dia_semana_id
AND i.id               = d.id
AND (i.detalle_manual  = 0)
UNION ALL
SELECT c.id,
  c.fecha,
  'N' docencia_paso_1,
  'N' docencia_paso_2,
  DECODE(d.id, NULL, 'N', 'S') docencia,
  1 orden_id,
  c.numero_iteraciones,
  c.repetir_cada_semanas,
  c.fecha_inicio,
  c.fecha_fin,
  c.estudio_id,
  c.semestre_id,
  c.curso_id,
  c.asignatura_id,
  c.grupo_id,
  c.tipo_subgrupo_id,
  c.subgrupo_id,
  c.dia_semana_id,
  c.tipo_dia,
  DECODE(c.tipo_dia, 'F', 1, 0) festivos
FROM
  (SELECT i.id,
    c.fecha,
    i.numero_iteraciones,
    i.repetir_cada_semanas,
    s.fecha_inicio,
    s.fecha_fin,
    ia.estudio_id,
    i.semestre_id,
    i.curso_id,
    ia.asignatura_id,
    i.grupo_id,
    i.tipo_subgrupo_id,
    i.subgrupo_id,
    i.dia_semana_id,
    c.tipo_dia
  FROM hor_estudios e,
    hor_semestres_detalle s,
    hor_items i,
    hor_items_asignaturas ia,
    hor_ext_calendario c
  WHERE i.id          = ia.item_id
  AND e.tipo_id       = s.tipo_estudio_id
  AND ia.estudio_id   = e.id
  AND i.semestre_id   = s.semestre_id
  AND c.dia_semana_id = i.dia_semana_id
  AND (c.tipo_dia    IN ('L', 'E', 'F')
  AND TRUNC(c.fecha) BETWEEN s.fecha_inicio AND NVL(s.fecha_examenes_fin, s.fecha_fin)
  AND c.vacaciones     = 0
  AND i.detalle_manual = 1)
  ) c,
  hor_items_detalle d
WHERE c.id         = d.item_id(+)
AND TRUNC(c.fecha) = TRUNC(d.inicio(+)) ;



CREATE OR REPLACE VIEW uji_horarios.hor_v_cursos AS
SELECT DISTINCT hor_items_asignaturas.estudio_id,
  hor_items_asignaturas.estudio,
  hor_items.curso_id
FROM hor_items_asignaturas,
  hor_items
WHERE hor_items.id = hor_items_asignaturas.item_id ;



CREATE OR REPLACE VIEW uji_horarios.hor_v_grupos AS
SELECT DISTINCT hor_items_asignaturas.estudio_id,
  hor_items_asignaturas.estudio,
  hor_items.grupo_id,
  DECODE(hor_items.grupo_id, 'Y', 'Grupo ARA', '') especial
FROM hor_items,
  hor_items_asignaturas
WHERE hor_items.id = hor_items_asignaturas.item_id ;



