ALTER TABLE UJI_HORARIOS.HOR_ITEMS
 ADD (repetir_cada_semanas  NUMBER);

ALTER TABLE UJI_HORARIOS.HOR_ITEMS
 ADD (numero_iteraciones  NUMBER);

ALTER TABLE UJI_HORARIOS.HOR_ITEMS
 ADD (detalle_manual  NUMBER                        DEFAULT 0                     NOT NULL);
 
CREATE OR REPLACE VIEW uji_horarios.hor_v_items_detalle AS
SELECT i.id,
  d.fecha,
  d.docencia docencia_paso_1,
  DECODE(d.repetir_cada_semanas, NULL, d.docencia, 1, d.docencia, DECODE(mod(d.orden_id, d.repetir_cada_semanas), 1, d.docencia, 'N')) docencia_paso_2,
  DECODE(d.repetir_cada_semanas, NULL, d.docencia, DECODE(d.numero_iteraciones, NULL, DECODE(d.repetir_cada_semanas, NULL, d.docencia, 1, d.docencia, DECODE(mod(d.orden_id, d.repetir_cada_semanas), 1, d.docencia, 'N')), DECODE(SIGN((d.orden_id / NVL(d.repetir_cada_semanas, 1)) - NVL(d.numero_iteraciones, 0)), 1, 'N', DECODE(d.repetir_cada_semanas, NULL, d.docencia, 1, d.docencia, DECODE(mod(d.orden_id, d.repetir_cada_semanas), 1, d.docencia, 'N'))))) docencia,
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
  d.dia_semana_id
FROM
  (SELECT x.id,
    x.fecha,
    row_number() OVER (PARTITION BY x.estudio_id, x.semestre_id, x.curso_id, x.asignatura_id, x.grupo_id, x.tipo_subgrupo_id, x.subgrupo_id, x.dia_semana_id, DECODE(hor_f_fecha_entre(x.fecha, x.fecha_inicio, x.fecha_fin), 'S', DECODE(hor_f_fecha_entre(x.fecha, NVL(x.desde_el_dia, x.fecha_inicio), NVL(x.hasta_el_dia, x.fecha_fin)), 'S', 'S', 'N'), 'N') ORDER BY x.fecha) orden_id,
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
    x.dia_semana,
    x.incremento
  FROM
    (SELECT i.id,
      i.estudio_id,
      i.curso_id,
      i.semestre_id,
      i.grupo_id,
      i.tipo_subgrupo_id,
      i.subgrupo_id,
      i.dia_semana_id,
      i.asignatura_id,
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
      c.dia_semana,
      DECODE(i.repetir_cada_semanas, NULL, 0, 1, 0, 2, 1 / 2, 3, 2 / 3, 4, 3 / 4) incremento
    FROM hor_estudios e,
      hor_semestres_detalle s,
      hor_items i,
      hor_ext_calendario c
    WHERE e.tipo_id       = s.tipo_estudio_id
    AND i.estudio_id      = e.id
    AND i.semestre_id     = s.semestre_id
    AND c.dia_semana_id   = i.dia_semana_id
    AND (i.detalle_manual = 0
    AND c.fecha BETWEEN s.fecha_inicio AND NVL(s.fecha_examenes_fin, s.fecha_fin)
    AND c.tipo_dia IN ('L', 'E'))
    ) x
  ) d,
  hor_items i
WHERE i.estudio_id     = d.estudio_id
AND i.curso_id         = d.curso_id
AND i.semestre_id      = d.semestre_id
AND i.asignatura_id    = d.asignatura_id
AND i.grupo_id         = d.grupo_id
AND i.tipo_subgrupo_id = d.tipo_subgrupo_id
AND i.subgrupo_id      = d.subgrupo_id
AND i.dia_semana_id    = d.dia_semana_id ;





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

 