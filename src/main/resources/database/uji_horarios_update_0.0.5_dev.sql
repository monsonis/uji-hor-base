drop table hor_aulas_estudio;

ALTER TABLE UJI_HORARIOS.HOR_AULAS
 ADD (area  VARCHAR2(10));

ALTER TABLE UJI_HORARIOS.HOR_AULAS
 ADD (edificio  VARCHAR2(10));

ALTER TABLE UJI_HORARIOS.HOR_AULAS
 ADD (planta  VARCHAR2(10));

 CREATE INDEX uji_horarios.hor_aulas_centro_IDX ON uji_horarios.hor_aulas 
    ( 
     centro_id ASC 
    ) 
;



CREATE INDEX uji_horarios.hor_aulas_plan_aula_IDX ON uji_horarios.hor_aulas_planificacion 
    ( 
     aula_id ASC 
    ) 
;
CREATE INDEX uji_horarios.hor_aulas_plan_est_IDX ON uji_horarios.hor_aulas_planificacion 
    ( 
     estudio_id ASC 
    ) 
;


ALTER TABLE uji_horarios.hor_aulas_planificacion 
    ADD CONSTRAINT hor_aulas_planif_hor_est_FK FOREIGN KEY 
    ( 
     estudio_id
    ) 
    REFERENCES uji_horarios.hor_estudios 
    ( 
     id
    ) 
;

update hor_aulas
set area = substr(codigo,1,1),
edificio = substr(codigo,2,2),
planta = substr(codigo,4,1);

commit;


ALTER TABLE UJI_HORARIOS.HOR_EXT_CALENDARIO
 ADD (vacaciones  NUMBER                            DEFAULT 0                     NOT NULL);


update hor_ext_calendario set vacaciones = 1 where id = 127;
update hor_ext_calendario set vacaciones = 1 where id = 128;
update hor_ext_calendario set vacaciones = 1 where id = 129;
update hor_ext_calendario set vacaciones = 1 where id = 130;
update hor_ext_calendario set vacaciones = 1 where id = 131;
update hor_ext_calendario set vacaciones = 1 where id = 132;
update hor_ext_calendario set vacaciones = 1 where id = 133;
update hor_ext_calendario set vacaciones = 1 where id = 134;
update hor_ext_calendario set vacaciones = 1 where id = 135;
update hor_ext_calendario set vacaciones = 1 where id = 136;
update hor_ext_calendario set vacaciones = 1 where id = 137;
update hor_ext_calendario set vacaciones = 1 where id = 138;
update hor_ext_calendario set vacaciones = 1 where id = 139;
update hor_ext_calendario set vacaciones = 1 where id = 195;
update hor_ext_calendario set vacaciones = 1 where id = 196;
update hor_ext_calendario set vacaciones = 1 where id = 197;
update hor_ext_calendario set vacaciones = 1 where id = 198;
update hor_ext_calendario set vacaciones = 1 where id = 199;
update hor_ext_calendario set vacaciones = 1 where id = 200;
update hor_ext_calendario set vacaciones = 1 where id = 201;
update hor_ext_calendario set vacaciones = 1 where id = 202;
update hor_ext_calendario set vacaciones = 1 where id = 223;
update hor_ext_calendario set vacaciones = 1 where id = 224;
update hor_ext_calendario set vacaciones = 1 where id = 225;
update hor_ext_calendario set vacaciones = 1 where id = 226;
update hor_ext_calendario set vacaciones = 1 where id = 227;
update hor_ext_calendario set vacaciones = 1 where id = 228;
update hor_ext_calendario set vacaciones = 1 where id = 229;
update hor_ext_calendario set vacaciones = 1 where id = 230;
update hor_ext_calendario set vacaciones = 1 where id = 231;
update hor_ext_calendario set vacaciones = 1 where id = 232;

commit;


CREATE INDEX UJI_HORARIOS.HOR_EXT_CAL_FECHA_VAC_IDX ON UJI_HORARIOS.HOR_EXT_CALENDARIO
(FECHA, TIPO_DIA, DIA_SEMANA_ID, VACACIONES);

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
                                                               DIA_SEMANA_ID,
                                                               TIPO_DIA,
                                                               FESTIVOS
                                                              ) AS
   SELECT i.id, fecha, docencia docencia_paso_1,
          DECODE (NVL (d.repetir_cada_semanas, 1),
                  1, docencia,
                  DECODE (MOD (orden_id, d.repetir_cada_semanas), 1, docencia, 'N')
                 ) docencia_paso_2,
          decode (tipo_dia,
                  'F', 'N',
                  DECODE (d.numero_iteraciones,
                          NULL, DECODE (NVL (d.repetir_cada_semanas, 1),
                                        1, docencia,
                                        DECODE (MOD (orden_id, d.repetir_cada_semanas), 1, docencia, 'N')
                                       ),
                          DECODE (SIGN (((orden_id - festivos) / d.repetir_cada_Semanas) - (d.numero_iteraciones)),
                                  1, 'N',
                                  DECODE (NVL (d.repetir_cada_semanas, 1),
                                          1, docencia,
                                          DECODE (MOD (orden_id, d.repetir_cada_semanas), 1, docencia, 'N')
                                         )
                                 )
                         )
                 ) docencia,
          d.orden_id, d.numero_iteraciones, d.repetir_cada_semanas, d.fecha_inicio, d.fecha_fin, d.estudio_id,
          d.semestre_id, d.curso_id, d.asignatura_id, d.grupo_id, d.tipo_subgrupo_id, d.subgrupo_id, d.dia_semana_id,
          tipo_dia, festivos
   FROM   (SELECT id, fecha,
                  (select count (*)
                   from   hor_ext_calendario c2
                   where  c2.fecha between NVL (x.desde_el_dia, fecha_inicio) and x.fecha
                   and    tipo_dia = 'F'
                   and    dia_semana_id = x.dia_semana_id) festivos,
                  ROW_NUMBER () OVER (PARTITION BY DECODE
                                                       (decode (sign (x.fecha - fecha_inicio),
                                                                -1, 'N',
                                                                decode (sign (fecha_fin - x.fecha), -1, 'N', 'S')
                                                               ),
                                                        'S', DECODE (decode (sign (x.fecha
                                                                                   - NVL (x.desde_el_dia, fecha_inicio)),
                                                                             -1, 'N',
                                                                             decode (sign (NVL (x.hasta_el_dia,
                                                                                                fecha_fin)
                                                                                           - x.fecha),
                                                                                     -1, 'N',
                                                                                     'S'
                                                                                    )
                                                                            ),
                                                                     'S', 'S',
                                                                     'N'
                                                                    ),
                                                        'N'
                                                       ), id, estudio_id, semestre_id, curso_id, asignatura_id, grupo_id, tipo_subgrupo_id, subgrupo_id, dia_semana_id ORDER BY fecha)
                                                                                                               orden_id,
                  DECODE (hor_f_fecha_entre (x.fecha, fecha_inicio, fecha_fin),
                          'S', DECODE (hor_f_fecha_entre (x.fecha, NVL (desde_el_dia, fecha_inicio),
                                                          NVL (hasta_el_dia, fecha_fin)),
                                       'S', 'S',
                                       'N'
                                      ),
                          'N'
                         ) docencia,
                  estudio_id, curso_id, semestre_id, grupo_id, tipo_subgrupo_id, subgrupo_id, dia_Semana_id,
                  asignatura_id, fecha_inicio, fecha_fin, fecha_examenes_inicio, fecha_examenes_fin, desde_el_dia,
                  hasta_el_dia, repetir_cada_semanas, numero_iteraciones, detalle_manual, tipo_dia, dia_semana
           FROM   (SELECT i.id, i.estudio_id, i.curso_id, i.semestre_id, i.grupo_id, i.tipo_subgrupo_id, i.subgrupo_id,
                          i.dia_Semana_id, i.asignatura_id, fecha_inicio, fecha_fin, fecha_examenes_inicio,
                          fecha_examenes_fin, i.desde_el_dia, hasta_el_dia, repetir_cada_semanas, numero_iteraciones,
                          detalle_manual, c.fecha, tipo_dia, dia_semana
                   FROM   hor_estudios e,
                          hor_semestres_detalle s,
                          hor_items i,
                          hor_ext_calendario c
                   WHERE  e.tipo_id = s.tipo_estudio_id
                   AND    i.estudio_id = e.id
                   AND    i.semestre_id = s.semestre_id
                   AND    trunc (c.fecha) BETWEEN fecha_inicio AND NVL (fecha_examenes_fin, fecha_fin)
                   AND    c.dia_semana_id = i.dia_semana_id
                   AND    tipo_dia IN ('L', 'E', 'F')
                   and    vacaciones = 0
                   AND    detalle_manual = 0) x) d,
          hor_items i
   WHERE  i.estudio_id = d.estudio_id
   AND    i.curso_id = d.curso_id
   AND    i.semestre_id = d.semestre_id
   AND    i.asignatura_id = d.asignatura_id
   AND    i.grupo_id = d.grupo_id
   AND    i.tipo_subgrupo_id = d.tipo_subgrupo_id
   AND    i.subgrupo_id = d.subgrupo_id
   AND    i.dia_semana_id = d.dia_semana_id
   AND    i.detalle_manual = 0
   AND    i.id = d.id
   UNION ALL
   SELECT c.id, c.fecha, 'N' docencia_paso_1, 'N' docencia_paso_2, DECODE (d.id, NULL, 'N', 'S') docencia, 1 orden_id,
          numero_iteraciones, repetir_cada_semanas, fecha_inicio, fecha_fin, estudio_id, semestre_id, curso_id,
          asignatura_id, grupo_id, tipo_subgrupo_id, subgrupo_id, dia_semana_id, tipo_dia,
          decode (tipo_dia, 'F', 1, 0) festivos
   FROM   (SELECT i.id, c.fecha, numero_iteraciones, repetir_cada_semanas, s.fecha_inicio, s.fecha_fin, i.estudio_id,
                  i.semestre_id, i.curso_id, i.asignatura_id, i.grupo_id, i.tipo_subgrupo_id, i.subgrupo_id,
                  i.dia_semana_id, tipo_dia
           FROM   hor_estudios e,
                  hor_semestres_detalle s,
                  hor_items i,
                  hor_ext_calendario c
           WHERE  e.tipo_id = s.tipo_estudio_id
           AND    i.estudio_id = e.id
           AND    i.semestre_id = s.semestre_id
           AND    trunc (c.fecha) BETWEEN fecha_inicio AND NVL (fecha_examenes_fin, fecha_fin)
           AND    c.dia_semana_id = i.dia_semana_id
           AND    tipo_dia IN ('L', 'E', 'F')
           and    vacaciones = 0
           AND    detalle_manual = 1) c,
          hor_items_detalle d
   WHERE  c.id = d.item_id(+)
   AND    trunc (c.fecha) = trunc (d.inicio(+));

ALTER TABLE UJI_HORARIOS.HOR_ESTUDIOS
 ADD (numero_cursos  NUMBER);


update hor_estudios
set numero_cursos = 4
where id between 200 and 999;

update hor_estudios
set numero_cursos = 6
where id = 229;

commit;

