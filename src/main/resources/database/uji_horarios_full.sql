-- Generado por Oracle SQL Developer Data Modeler 3.1.1.703
--   en:        2012-11-07 14:58:59 CET
--   sitio:      Oracle Database 11g
--   tipo:      Oracle Database 11g



DROP VIEW uji_horarios.hor_v_cursos 
;
DROP VIEW uji_horarios.hor_v_grupos 
;
DROP VIEW uji_horarios.hor_v_items_detalle 
;
DROP TABLE uji_horarios.hor_areas CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_aulas CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_aulas_estudio CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_aulas_planificacion CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_centros CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_circuitos CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_departamentos CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_dias_semana CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_estudios CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_ext_calendario CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_ext_cargos_per CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_ext_circuitos CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_ext_personas CASCADE CONSTRAINTS 
;
DROP TABLE hor_horarios_horas CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_items CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_items_circuitos CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_items_detalle CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_permisos_extra CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_profesores CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_semestres CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_semestres_detalle CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_tipos_cargos CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_tipos_estudios CASCADE CONSTRAINTS 
;
CREATE TABLE uji_horarios.hor_areas 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (100)  NOT NULL , 
     departamento_id NUMBER  NOT NULL , 
     activa NUMBER DEFAULT 1  NOT NULL CHECK ( activa IN (0, 1)) 
    ) 
;



ALTER TABLE uji_horarios.hor_areas 
    ADD CONSTRAINT hor_areas_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_aulas 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (100)  NOT NULL , 
     centro_id NUMBER  NOT NULL , 
     tipo VARCHAR2 (100) , 
     plazas NUMBER , 
     codigo VARCHAR2 (100) 
    ) 
;



ALTER TABLE uji_horarios.hor_aulas 
    ADD CONSTRAINT hor_aulas_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_aulas_estudio 
    ( 
     id NUMBER  NOT NULL , 
     aula_id NUMBER  NOT NULL , 
     estudio_id NUMBER  NOT NULL , 
     descripcion VARCHAR2 (1000) 
    ) 
;



ALTER TABLE uji_horarios.hor_aulas_estudio 
    ADD CONSTRAINT hor_aulas_estudio_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_aulas_planificacion 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (100)  NOT NULL , 
     aula_id NUMBER , 
     estudio_id NUMBER , 
     curso_id NUMBER , 
     semestre_id NUMBER 
    ) 
;



ALTER TABLE uji_horarios.hor_aulas_planificacion 
    ADD CONSTRAINT hor_aulas_planificacion_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_centros 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (100)  NOT NULL 
    ) 
;



ALTER TABLE uji_horarios.hor_centros 
    ADD CONSTRAINT hor_centros_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_circuitos 
    ( 
     id NUMBER  NOT NULL , 
     estudio_id NUMBER  NOT NULL , 
     grupo_id VARCHAR2 (10)  NOT NULL , 
     id_circuito NUMBER  NOT NULL , 
     nombre VARCHAR2 (100)  NOT NULL , 
     especial NUMBER DEFAULT 0  NOT NULL CHECK ( especial IN (0, 1)) 
    ) 
;



ALTER TABLE uji_horarios.hor_circuitos 
    ADD CONSTRAINT hor_circuitos_est_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_departamentos 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (100)  NOT NULL , 
     centro_id NUMBER  NOT NULL , 
     activo NUMBER DEFAULT 1  NOT NULL CHECK ( activo IN (0, 1)) 
    ) 
;



ALTER TABLE uji_horarios.hor_departamentos 
    ADD CONSTRAINT hor_departamentos_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_dias_semana 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (10)  NOT NULL 
    ) 
;



ALTER TABLE uji_horarios.hor_dias_semana 
    ADD CONSTRAINT hor_dias_semana_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_estudios 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (1000)  NOT NULL , 
     tipo_id VARCHAR2 (10)  NOT NULL , 
     centro_id NUMBER  NOT NULL , 
     oficial NUMBER DEFAULT 1  NOT NULL CHECK ( oficial IN (0, 1)) 
    ) 
;



ALTER TABLE uji_horarios.hor_estudios 
    ADD CONSTRAINT hor_estudios_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_ext_calendario 
    ( 
     id NUMBER  NOT NULL , 
     dia NUMBER  NOT NULL , 
     mes NUMBER  NOT NULL , 
     año NUMBER  NOT NULL , 
     dia_semana VARCHAR2 (100)  NOT NULL , 
     dia_semana_id NUMBER  NOT NULL , 
     tipo_dia VARCHAR2 (10)  NOT NULL , 
     fecha DATE  NOT NULL 
    ) 
;



ALTER TABLE uji_horarios.hor_ext_calendario 
    ADD CONSTRAINT hor_ext_calendario_PK PRIMARY KEY ( id ) ;


ALTER TABLE uji_horarios.hor_ext_calendario 
    ADD CONSTRAINT hor_ext_cal_fecha_UN UNIQUE ( fecha ) ;


ALTER TABLE uji_horarios.hor_ext_calendario 
    ADD CONSTRAINT hor_ext_cal_fecha_det__UN UNIQUE ( dia , mes , año ) ;



CREATE TABLE uji_horarios.hor_ext_cargos_per 
    ( 
     id NUMBER  NOT NULL , 
     tipo_cargo_id NUMBER  NOT NULL , 
     persona_id NUMBER  NOT NULL , 
     departamento_id NUMBER , 
     estudio_id NUMBER , 
     curso_id NUMBER 
    ) 
;



ALTER TABLE uji_horarios.hor_ext_cargos_per 
    ADD CONSTRAINT hor_ext_cargos_per_CK 
    CHECK ((departamento_id is not null and estudio_id is null)
 or
(departamento_id is null and estudio_id is not null) )
;


ALTER TABLE uji_horarios.hor_ext_cargos_per 
    ADD CONSTRAINT hor_ext_cargos_per_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_ext_circuitos 
    ( 
     id NUMBER  NOT NULL , 
     curso_aca NUMBER  NOT NULL , 
     tipo VARCHAR2 (10)  NOT NULL , 
     subgrupo_id NUMBER  NOT NULL , 
     grupo_id VARCHAR2 (10)  NOT NULL , 
     detalle_id NUMBER  NOT NULL , 
     asignatura_id VARCHAR2 (10)  NOT NULL , 
     circuito_id NUMBER  NOT NULL , 
     estudio_id NUMBER  NOT NULL , 
     plazas NUMBER  NOT NULL 
    ) 
;



ALTER TABLE uji_horarios.hor_ext_circuitos 
    ADD CONSTRAINT hor_ext_circuitos_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_ext_personas 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (1000)  NOT NULL , 
     email VARCHAR2 (100)  NOT NULL , 
     actividad_id VARCHAR2 (10)  NOT NULL , 
     departamento_id NUMBER  NOT NULL 
    ) 
;



ALTER TABLE uji_horarios.hor_ext_personas 
    ADD CONSTRAINT hor_ext_personas_PK PRIMARY KEY ( id ) ;


ALTER TABLE uji_horarios.hor_ext_personas 
    ADD CONSTRAINT hor_ext_personas__UN UNIQUE ( email ) ;



CREATE TABLE hor_horarios_horas 
    ( 
     id NUMBER  NOT NULL , 
     estudio_id NUMBER  NOT NULL , 
     curso_id NUMBER  NOT NULL , 
     semestre_id NUMBER  NOT NULL , 
     grupo_id VARCHAR2 (10)  NOT NULL , 
     hora_inicio DATE , 
     hora_fin DATE 
    ) 
;



ALTER TABLE hor_horarios_horas 
    ADD CONSTRAINT hor_horarios_horas_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_items 
    ( 
     id NUMBER  NOT NULL , 
     asignatura_id VARCHAR2 (10)  NOT NULL , 
     asignatura VARCHAR2 (1000) , 
     estudio_id NUMBER  NOT NULL , 
     estudio VARCHAR2 (1000) , 
     curso_id NUMBER  NOT NULL , 
     caracter_id VARCHAR2 (10)  NOT NULL , 
     caracter VARCHAR2 (100) , 
     semestre_id NUMBER  NOT NULL , 
     aula_planificacion_id NUMBER , 
     profesor_id NUMBER , 
     comun NUMBER DEFAULT 0  NOT NULL CHECK ( comun IN (0, 1)) , 
     porcentaje_comun NUMBER , 
     grupo_id VARCHAR2 (10)  NOT NULL , 
     tipo_subgrupo_id VARCHAR2 (10)  NOT NULL , 
     tipo_subgrupo VARCHAR2 (100) , 
     subgrupo_id NUMBER  NOT NULL , 
     dia_semana_id NUMBER , 
     hora_inicio DATE , 
     hora_fin DATE , 
     tipo_asignatura_id VARCHAR2 (10) , 
     tipo_asignatura VARCHAR2 (100) , 
     tipo_estudio_id VARCHAR2 (10) , 
     tipo_estudio VARCHAR2 (100) , 
     plazas NUMBER , 
     desde_el_dia DATE , 
     hasta_el_dia DATE , 
     repetir_cada_semanas NUMBER , 
     numero_iteraciones NUMBER , 
     detalle_manual NUMBER DEFAULT 0  NOT NULL CHECK ( detalle_manual IN (0, 1)) 
    ) 
;



ALTER TABLE uji_horarios.hor_items 
    ADD CONSTRAINT TABLE_1_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_items_circuitos 
    ( 
     id NUMBER  NOT NULL , 
     item_id NUMBER  NOT NULL , 
     circuito_id NUMBER  NOT NULL , 
     plazas NUMBER 
    ) 
;



ALTER TABLE uji_horarios.hor_items_circuitos 
    ADD CONSTRAINT hor_items_circuitos_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_items_detalle 
    ( 
     id NUMBER  NOT NULL , 
     item_id NUMBER  NOT NULL , 
     inicio DATE  NOT NULL , 
     fin DATE  NOT NULL , 
     descripcion VARCHAR2 (1000) 
    ) 
;



ALTER TABLE uji_horarios.hor_items_detalle 
    ADD CONSTRAINT hor_items_detalle_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_permisos_extra 
    ( 
     id NUMBER  NOT NULL , 
     persona_id NUMBER  NOT NULL , 
     tipo_cargo_id NUMBER  NOT NULL , 
     estudio_id NUMBER , 
     departamento_id NUMBER 
    ) 
;



ALTER TABLE uji_horarios.hor_permisos_extra 
    ADD CONSTRAINT hor_permisos_extra_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_profesores 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (100)  NOT NULL , 
     email VARCHAR2 (100) , 
     area_id NUMBER  NOT NULL , 
     departamento_id NUMBER  NOT NULL , 
     pendiente_contratacion NUMBER DEFAULT 0  NOT NULL CHECK ( pendiente_contratacion IN (0, 1)) 
    ) 
;



ALTER TABLE uji_horarios.hor_profesores 
    ADD CONSTRAINT hor_profesores_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_semestres 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (100)  NOT NULL 
    ) 
;



ALTER TABLE uji_horarios.hor_semestres 
    ADD CONSTRAINT hor_semestres_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_semestres_detalle 
    ( 
     id NUMBER  NOT NULL , 
     semestre_id NUMBER  NOT NULL , 
     tipo_estudio_id VARCHAR2 (10)  NOT NULL , 
     fecha_inicio DATE  NOT NULL , 
     fecha_fin DATE  NOT NULL , 
     fecha_examenes_inicio DATE , 
     fecha_examenes_fin DATE , 
     numero_semanas NUMBER  NOT NULL 
    ) 
;



ALTER TABLE uji_horarios.hor_semestres_detalle 
    ADD CONSTRAINT hor_semestres_detalle_PK PRIMARY KEY ( id ) ;


ALTER TABLE uji_horarios.hor_semestres_detalle 
    ADD CONSTRAINT hor_semestres_detalle__UN UNIQUE ( semestre_id , tipo_estudio_id ) ;



CREATE TABLE uji_horarios.hor_tipos_cargos 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (100)  NOT NULL 
    ) 
;



ALTER TABLE uji_horarios.hor_tipos_cargos 
    ADD CONSTRAINT hor_tipos_cargos_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_tipos_estudios 
    ( 
     id VARCHAR2 (10)  NOT NULL , 
     nombre VARCHAR2 (100)  NOT NULL , 
     orden NUMBER 
    ) 
;



ALTER TABLE uji_horarios.hor_tipos_estudios 
    ADD CONSTRAINT hor_tipos_estudios_PK PRIMARY KEY ( id ) ;




ALTER TABLE uji_horarios.hor_areas 
    ADD CONSTRAINT hor_areas_hor_dep_FK FOREIGN KEY 
    ( 
     departamento_id
    ) 
    REFERENCES uji_horarios.hor_departamentos 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_aulas_estudio 
    ADD CONSTRAINT hor_aulas_estudio_aulas_FK FOREIGN KEY 
    ( 
     aula_id
    ) 
    REFERENCES uji_horarios.hor_aulas 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_aulas_estudio 
    ADD CONSTRAINT hor_aulas_estudio_est_FK FOREIGN KEY 
    ( 
     estudio_id
    ) 
    REFERENCES uji_horarios.hor_estudios 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_aulas 
    ADD CONSTRAINT hor_aulas_hor_centros_FK FOREIGN KEY 
    ( 
     centro_id
    ) 
    REFERENCES uji_horarios.hor_centros 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_aulas_planificacion 
    ADD CONSTRAINT hor_aulas_planif_aulas_FK FOREIGN KEY 
    ( 
     aula_id
    ) 
    REFERENCES uji_horarios.hor_aulas 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_circuitos 
    ADD CONSTRAINT hor_circuitos_est_FK FOREIGN KEY 
    ( 
     estudio_id
    ) 
    REFERENCES uji_horarios.hor_estudios 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_departamentos 
    ADD CONSTRAINT hor_dep_hor_centros_FK FOREIGN KEY 
    ( 
     centro_id
    ) 
    REFERENCES uji_horarios.hor_centros 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_estudios 
    ADD CONSTRAINT hor_estudios_hor_centros_FK FOREIGN KEY 
    ( 
     centro_id
    ) 
    REFERENCES uji_horarios.hor_centros 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_estudios 
    ADD CONSTRAINT hor_estudios_hor_tipos_est_FK FOREIGN KEY 
    ( 
     tipo_id
    ) 
    REFERENCES uji_horarios.hor_tipos_estudios 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_ext_cargos_per 
    ADD CONSTRAINT hor_ext_car_per_dep_FK FOREIGN KEY 
    ( 
     departamento_id
    ) 
    REFERENCES uji_horarios.hor_departamentos 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_ext_cargos_per 
    ADD CONSTRAINT hor_ext_car_per_est_FK FOREIGN KEY 
    ( 
     estudio_id
    ) 
    REFERENCES uji_horarios.hor_estudios 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_ext_cargos_per 
    ADD CONSTRAINT hor_ext_car_per_per_FK FOREIGN KEY 
    ( 
     persona_id
    ) 
    REFERENCES uji_horarios.hor_ext_personas 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_ext_cargos_per 
    ADD CONSTRAINT hor_ext_car_per_tip_car_FK FOREIGN KEY 
    ( 
     tipo_cargo_id
    ) 
    REFERENCES uji_horarios.hor_tipos_cargos 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_ext_personas 
    ADD CONSTRAINT hor_ext_per_dept_FK FOREIGN KEY 
    ( 
     departamento_id
    ) 
    REFERENCES uji_horarios.hor_departamentos 
    ( 
     id
    ) 
;


ALTER TABLE hor_horarios_horas 
    ADD CONSTRAINT hor_horarios_horas_est_FK FOREIGN KEY 
    ( 
     estudio_id
    ) 
    REFERENCES uji_horarios.hor_estudios 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_items_circuitos 
    ADD CONSTRAINT hor_items_cir_cir_FK FOREIGN KEY 
    ( 
     circuito_id
    ) 
    REFERENCES uji_horarios.hor_circuitos 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_items_circuitos 
    ADD CONSTRAINT hor_items_cir_items_FK FOREIGN KEY 
    ( 
     item_id
    ) 
    REFERENCES uji_horarios.hor_items 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_items_detalle 
    ADD CONSTRAINT hor_items_detalle_hor_items_FK FOREIGN KEY 
    ( 
     item_id
    ) 
    REFERENCES uji_horarios.hor_items 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_items 
    ADD CONSTRAINT hor_items_hor_aulas_plan_FK FOREIGN KEY 
    ( 
     aula_planificacion_id
    ) 
    REFERENCES uji_horarios.hor_aulas_planificacion 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_items 
    ADD CONSTRAINT hor_items_hor_dias_semana_FK FOREIGN KEY 
    ( 
     dia_semana_id
    ) 
    REFERENCES uji_horarios.hor_dias_semana 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_items 
    ADD CONSTRAINT hor_items_hor_estudios_FK FOREIGN KEY 
    ( 
     estudio_id
    ) 
    REFERENCES uji_horarios.hor_estudios 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_items 
    ADD CONSTRAINT hor_items_hor_profesores_FK FOREIGN KEY 
    ( 
     profesor_id
    ) 
    REFERENCES uji_horarios.hor_profesores 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_items 
    ADD CONSTRAINT hor_items_hor_semestres_FK FOREIGN KEY 
    ( 
     semestre_id
    ) 
    REFERENCES uji_horarios.hor_semestres 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_permisos_extra 
    ADD CONSTRAINT hor_perm_ext_dept_FK FOREIGN KEY 
    ( 
     departamento_id
    ) 
    REFERENCES uji_horarios.hor_departamentos 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_permisos_extra 
    ADD CONSTRAINT hor_perm_ext_est_FK FOREIGN KEY 
    ( 
     estudio_id
    ) 
    REFERENCES uji_horarios.hor_estudios 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_permisos_extra 
    ADD CONSTRAINT hor_perm_ext_per_FK FOREIGN KEY 
    ( 
     persona_id
    ) 
    REFERENCES uji_horarios.hor_ext_personas 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_permisos_extra 
    ADD CONSTRAINT hor_perm_ext_tip_car_FK FOREIGN KEY 
    ( 
     tipo_cargo_id
    ) 
    REFERENCES uji_horarios.hor_tipos_cargos 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_profesores 
    ADD CONSTRAINT hor_profesores_hor_areas_FK FOREIGN KEY 
    ( 
     area_id
    ) 
    REFERENCES uji_horarios.hor_areas 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_semestres_detalle 
    ADD CONSTRAINT hor_sem_det_sem_FK FOREIGN KEY 
    ( 
     semestre_id
    ) 
    REFERENCES uji_horarios.hor_semestres 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_semestres_detalle 
    ADD CONSTRAINT hor_sem_det_tipos_est_FK FOREIGN KEY 
    ( 
     tipo_estudio_id
    ) 
    REFERENCES uji_horarios.hor_tipos_estudios 
    ( 
     id
    ) 
;

CREATE OR REPLACE VIEW uji_horarios.hor_v_cursos AS
SELECT DISTINCT uji_horarios.hor_items.estudio_id,
  uji_horarios.hor_items.estudio,
  uji_horarios.hor_items.curso_id
FROM uji_horarios.hor_items ;



CREATE OR REPLACE VIEW uji_horarios.hor_v_grupos AS
SELECT DISTINCT uji_horarios.hor_items.estudio_id,
  uji_horarios.hor_items.estudio,
  uji_horarios.hor_items.grupo_id,
  DECODE(uji_horarios.hor_items.grupo_id, 'Y', 'Grupo ARA', '') especial
FROM uji_horarios.hor_items ;



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
  d.dia_Semana_id
FROM
  (SELECT x.id,
    x.fecha,
    row_number() OVER (PARTITION BY x.estudio_id, x.semestre_id, x.curso_id, x.asignatura_id, x.grupo_id, x.tipo_subgrupo_id, x.subgrupo_id, x.dia_Semana_id, DECODE(hor_f_fecha_entre(x.fecha, x.fecha_inicio, x.fecha_fin), 'S', DECODE(hor_f_fecha_entre(x.fecha, NVL(x.desde_el_dia, x.fecha_inicio), NVL(x.hasta_el_dia, x.fecha_fin)), 'S', 'S', 'N'), 'N') ORDER BY x.fecha) orden_id,
    DECODE(hor_f_fecha_entre(x.fecha, x.fecha_inicio, x.fecha_fin), 'S', DECODE(hor_f_fecha_entre(x.fecha, NVL(x.desde_el_dia, x.fecha_inicio), NVL(x.hasta_el_dia, x.fecha_fin)), 'S', 'S', 'N'), 'N') docencia,
    x.estudio_id,
    x.curso_id,
    x.semestre_id,
    x.grupo_id,
    x.tipo_subgrupo_id,
    x.subgrupo_id,
    x.dia_Semana_id,
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
      i.dia_Semana_id,
      i.asignatura_id,
      fecha_inicio,
      fecha_fin,
      fecha_examenes_inicio,
      fecha_examenes_fin,
      i.desde_el_dia,
      hasta_el_dia,
      repetir_cada_semanas,
      numero_iteraciones,
      detalle_manual,
      c.fecha,
      tipo_dia,
      dia_semana,
      DECODE(repetir_cada_semanas, NULL, 0, 1, 0, 2, 1 / 2, 3, 2 / 3, 4, 3 / 4) incremento
    FROM hor_estudios e,
      hor_semestres_detalle s,
      hor_items i,
      hor_ext_calendario c
    WHERE e.tipo_id     = s.tipo_estudio_id
    AND i.estudio_id    = e.id
    AND i.semestre_id   = s.semestre_id
    AND c.dia_semana_id = i.dia_semana_id
    AND (detalle_manual = 0
    AND c.fecha BETWEEN fecha_inicio AND NVL(fecha_examenes_fin, fecha_fin)
    AND tipo_dia = 'L')
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
AND i.dia_semana_id    = d.dia_Semana_id ;





-- Informe de Resumen de Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                            23
-- CREATE INDEX                             0
-- ALTER TABLE                             58
-- CREATE VIEW                              3
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE STRUCTURED TYPE                   0
-- CREATE COLLECTION TYPE                   0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
