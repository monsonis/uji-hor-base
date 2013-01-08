-- Generado por Oracle SQL Developer Data Modeler 3.1.1.703
--   en:        2013-01-08 11:05:22 CET
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
DROP TABLE hor_aulas_planificacion CASCADE CONSTRAINTS 
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
DROP TABLE uji_horarios.hor_ext_asignaturas_comunes CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_ext_calendario CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_ext_cargos_per CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_ext_circuitos CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_ext_personas CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_horarios_horas CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_items CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_items_asignaturas CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_items_circuitos CASCADE CONSTRAINTS 
;
DROP TABLE uji_horarios.hor_items_comunes CASCADE CONSTRAINTS 
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
CREATE USER uji_horarios 
    IDENTIFIED BY  
    ACCOUNT UNLOCK 
;

CREATE TABLE uji_horarios.hor_areas 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (100)  NOT NULL , 
     departamento_id NUMBER  NOT NULL , 
     activa NUMBER DEFAULT 1  NOT NULL CHECK ( activa IN (0, 1)) 
    ) 
    LOGGING 
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
     codigo VARCHAR2 (100) , 
     area VARCHAR2 (10) , 
     edificio VARCHAR2 (10) , 
     planta VARCHAR2 (10) 
    ) 
    LOGGING 
;


CREATE INDEX uji_horarios.hor_aulas_centro_IDX ON uji_horarios.hor_aulas 
    ( 
     centro_id ASC 
    ) 
    LOGGING 
;

ALTER TABLE uji_horarios.hor_aulas 
    ADD CONSTRAINT hor_aulas_PK PRIMARY KEY ( id ) ;



CREATE TABLE hor_aulas_planificacion 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (100)  NOT NULL , 
     aula_id NUMBER , 
     estudio_id NUMBER  NOT NULL , 
     curso_id NUMBER , 
     semestre_id NUMBER 
    ) 
    LOGGING 
;


CREATE INDEX hor_aulas_plan_aula_IDX ON hor_aulas_planificacion 
    ( 
     aula_id ASC 
    ) 
    LOGGING 
;
CREATE INDEX hor_aulas_plan_est_IDX ON hor_aulas_planificacion 
    ( 
     estudio_id ASC 
    ) 
    LOGGING 
;

ALTER TABLE hor_aulas_planificacion 
    ADD CONSTRAINT hor_aulas_planificacion_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_centros 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (100)  NOT NULL 
    ) 
    LOGGING 
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
    LOGGING 
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
    LOGGING 
;



ALTER TABLE uji_horarios.hor_departamentos 
    ADD CONSTRAINT hor_departamentos_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_dias_semana 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (10)  NOT NULL 
    ) 
    LOGGING 
;



ALTER TABLE uji_horarios.hor_dias_semana 
    ADD CONSTRAINT hor_dias_semana_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_estudios 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (1000)  NOT NULL , 
     tipo_id VARCHAR2 (10)  NOT NULL , 
     centro_id NUMBER  NOT NULL , 
     oficial NUMBER DEFAULT 1  NOT NULL CHECK ( oficial IN (0, 1)) , 
     numero_cursos NUMBER 
    ) 
    LOGGING 
;



ALTER TABLE uji_horarios.hor_estudios 
    ADD CONSTRAINT hor_estudios_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_ext_asignaturas_comunes 
    ( 
     id NUMBER  NOT NULL , 
     grupo_comun_id NUMBER  NOT NULL , 
     nombre VARCHAR2 (1000)  NOT NULL , 
     asignatura_id VARCHAR2 (10)  NOT NULL 
    ) 
    LOGGING 
;


CREATE INDEX uji_horarios.hor_ext_asi_comunes_nom_IDX ON uji_horarios.hor_ext_asignaturas_comunes 
    ( 
     nombre ASC 
    ) 
    LOGGING 
;
CREATE INDEX uji_horarios.hor_ext_asi_comunes_ca_IDX ON uji_horarios.hor_ext_asignaturas_comunes 
    ( 
     asignatura_id ASC 
    ) 
    LOGGING 
;

ALTER TABLE uji_horarios.hor_ext_asignaturas_comunes 
    ADD CONSTRAINT hor_ext_asignaturas_comunes_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_ext_calendario 
    ( 
     id NUMBER  NOT NULL , 
     dia NUMBER  NOT NULL , 
     mes NUMBER  NOT NULL , 
     año NUMBER  NOT NULL , 
     dia_semana VARCHAR2 (100)  NOT NULL , 
     dia_semana_id NUMBER  NOT NULL , 
     tipo_dia VARCHAR2 (10)  NOT NULL , 
     fecha DATE  NOT NULL , 
     vacaciones NUMBER DEFAULT 0  NOT NULL CHECK ( vacaciones IN (0, 1)) 
    ) 
    LOGGING 
;


CREATE INDEX uji_horarios.hor_ext_cal_fecha_idx ON uji_horarios.hor_ext_calendario 
    ( 
     fecha ASC , 
     tipo_dia ASC , 
     dia_semana_id ASC 
    ) 
    LOGGING 
;
CREATE INDEX uji_horarios.hor_ext_cal_fecha_vac_IDX ON uji_horarios.hor_ext_calendario 
    ( 
     fecha ASC , 
     tipo_dia ASC , 
     dia_semana_id ASC , 
     vacaciones ASC 
    ) 
    LOGGING 
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
    LOGGING 
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
    LOGGING 
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
    LOGGING 
;



ALTER TABLE uji_horarios.hor_ext_personas 
    ADD CONSTRAINT hor_ext_personas_PK PRIMARY KEY ( id ) ;


ALTER TABLE uji_horarios.hor_ext_personas 
    ADD CONSTRAINT hor_ext_personas__UN UNIQUE ( email ) ;



CREATE TABLE uji_horarios.hor_horarios_horas 
    ( 
     id NUMBER  NOT NULL , 
     estudio_id NUMBER  NOT NULL , 
     curso_id NUMBER  NOT NULL , 
     semestre_id NUMBER  NOT NULL , 
     grupo_id VARCHAR2 (10)  NOT NULL , 
     hora_inicio DATE , 
     hora_fin DATE 
    ) 
    LOGGING 
;



ALTER TABLE uji_horarios.hor_horarios_horas 
    ADD CONSTRAINT hor_horarios_horas_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_items 
    ( 
     id NUMBER  NOT NULL , 
     curso_id NUMBER  NOT NULL , 
     caracter_id VARCHAR2 (10)  NOT NULL , 
     caracter VARCHAR2 (100) , 
     semestre_id NUMBER  NOT NULL , 
     aula_planificacion_id NUMBER , 
     aula_planificacion_nombre VARCHAR2 (100) , 
     profesor_id NUMBER , 
     comun NUMBER DEFAULT 0  NOT NULL CHECK ( comun IN (0, 1)) , 
     porcentaje_comun NUMBER , 
     comun_texto VARCHAR2 (1000) , 
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
    LOGGING 
;


CREATE INDEX uji_horarios.hor_items_v_idx ON uji_horarios.hor_items 
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
CREATE INDEX uji_horarios.hor_items_v2_IDX ON uji_horarios.hor_items 
    ( 
     id ASC , 
     dia_semana_id ASC , 
     detalle_manual ASC 
    ) 
    LOGGING 
;
CREATE INDEX uji_horarios.hor_item_det_man_idx ON uji_horarios.hor_items 
    ( 
     id ASC , 
     detalle_manual ASC 
    ) 
    LOGGING 
;

ALTER TABLE uji_horarios.hor_items 
    ADD CONSTRAINT TABLE_1_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_items_asignaturas 
    ( 
     id NUMBER  NOT NULL , 
     item_id NUMBER  NOT NULL , 
     asignatura_id VARCHAR2 (10)  NOT NULL , 
     asignatura VARCHAR2 (1000) , 
     estudio_id NUMBER  NOT NULL , 
     estudio VARCHAR2 (1000) 
    ) 
    LOGGING 
;


CREATE INDEX uji_horarios.hor_items_asig_it_IDX ON uji_horarios.hor_items_asignaturas 
    ( 
     item_id ASC 
    ) 
    LOGGING 
;
CREATE INDEX uji_horarios.hor_items_asig_est_IDX ON uji_horarios.hor_items_asignaturas 
    ( 
     estudio_id ASC 
    ) 
    LOGGING 
;
CREATE INDEX uji_horarios.hor_items_asig_est_asi_IDX ON uji_horarios.hor_items_asignaturas 
    ( 
     asignatura_id ASC , 
     estudio_id ASC 
    ) 
    LOGGING 
;

ALTER TABLE uji_horarios.hor_items_asignaturas 
    ADD CONSTRAINT hor_items_asignaturas_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_items_circuitos 
    ( 
     id NUMBER  NOT NULL , 
     item_id NUMBER  NOT NULL , 
     circuito_id NUMBER  NOT NULL , 
     plazas NUMBER 
    ) 
    LOGGING 
;



ALTER TABLE uji_horarios.hor_items_circuitos 
    ADD CONSTRAINT hor_items_circuitos_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_items_comunes 
    ( 
     id NUMBER  NOT NULL , 
     item_id NUMBER  NOT NULL , 
     asignatura_id VARCHAR2 (100) , 
     asignatura_comun_id VARCHAR2 (100) , 
     item_comun_id NUMBER  NOT NULL 
    ) 
    LOGGING 
;


CREATE INDEX uji_horarios.hor_items_comunes__IDX ON uji_horarios.hor_items_comunes 
    ( 
     item_id ASC 
    ) 
    LOGGING 
;
CREATE INDEX uji_horarios.hor_items_comunes_com_IDX ON uji_horarios.hor_items_comunes 
    ( 
     item_comun_id ASC 
    ) 
    LOGGING 
;

ALTER TABLE uji_horarios.hor_items_comunes 
    ADD CONSTRAINT hor_items_comunes_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_items_detalle 
    ( 
     id NUMBER  NOT NULL , 
     item_id NUMBER  NOT NULL , 
     inicio DATE  NOT NULL , 
     fin DATE  NOT NULL , 
     descripcion VARCHAR2 (1000) 
    ) 
    LOGGING 
;


CREATE INDEX uji_horarios.hor_items_detalle_fechas_IDX ON uji_horarios.hor_items_detalle 
    ( 
     item_id ASC , 
     inicio ASC 
    ) 
    LOGGING 
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
    LOGGING 
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
    LOGGING 
;



ALTER TABLE uji_horarios.hor_profesores 
    ADD CONSTRAINT hor_profesores_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_semestres 
    ( 
     id NUMBER  NOT NULL , 
     nombre VARCHAR2 (100)  NOT NULL 
    ) 
    LOGGING 
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
     numero_semanas NUMBER  NOT NULL , 
     curso_academico_id NUMBER  NOT NULL 
    ) 
    LOGGING 
;


CREATE INDEX uji_horarios.hor_semestres_detalle_v_idx ON uji_horarios.hor_semestres_detalle 
    ( 
     tipo_estudio_id ASC 
    ) 
    LOGGING 
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
    LOGGING 
;



ALTER TABLE uji_horarios.hor_tipos_cargos 
    ADD CONSTRAINT hor_tipos_cargos_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_tipos_estudios 
    ( 
     id VARCHAR2 (10)  NOT NULL , 
     nombre VARCHAR2 (100)  NOT NULL , 
     orden NUMBER 
    ) 
    LOGGING 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
;


ALTER TABLE hor_aulas_planificacion 
    ADD CONSTRAINT hor_aulas_planif_aulas_FK FOREIGN KEY 
    ( 
     aula_id
    ) 
    REFERENCES uji_horarios.hor_aulas 
    ( 
     id
    ) 
    NOT DEFERRABLE 
;


ALTER TABLE hor_aulas_planificacion 
    ADD CONSTRAINT hor_aulas_planif_hor_est_FK FOREIGN KEY 
    ( 
     estudio_id
    ) 
    REFERENCES uji_horarios.hor_estudios 
    ( 
     id
    ) 
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
;


ALTER TABLE uji_horarios.hor_horarios_horas 
    ADD CONSTRAINT hor_horarios_horas_est_FK FOREIGN KEY 
    ( 
     estudio_id
    ) 
    REFERENCES uji_horarios.hor_estudios 
    ( 
     id
    ) 
    NOT DEFERRABLE 
;


ALTER TABLE uji_horarios.hor_items_asignaturas 
    ADD CONSTRAINT hor_items_asig_estudios_FK FOREIGN KEY 
    ( 
     estudio_id
    ) 
    REFERENCES uji_horarios.hor_estudios 
    ( 
     id
    ) 
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
;


ALTER TABLE uji_horarios.hor_items_comunes 
    ADD CONSTRAINT hor_items_comunes_it_FK FOREIGN KEY 
    ( 
     item_id
    ) 
    REFERENCES uji_horarios.hor_items 
    ( 
     id
    ) 
    NOT DEFERRABLE 
;


ALTER TABLE uji_horarios.hor_items_comunes 
    ADD CONSTRAINT hor_items_comunes_it_com_FK FOREIGN KEY 
    ( 
     item_comun_id
    ) 
    REFERENCES uji_horarios.hor_items 
    ( 
     id
    ) 
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
;


ALTER TABLE uji_horarios.hor_items 
    ADD CONSTRAINT hor_items_hor_aulas_plan_FK FOREIGN KEY 
    ( 
     aula_planificacion_id
    ) 
    REFERENCES hor_aulas_planificacion 
    ( 
     id
    ) 
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
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
    NOT DEFERRABLE 
;

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



CREATE OR REPLACE TRIGGER uji_horarios.hor_items_delete 
    BEFORE DELETE ON uji_horarios.hor_items REFERENCING 
    NEW AS new 
    OLD AS old 
    FOR EACH ROW 
begin
   delete      uji_horarios.hor_items_detalle
   where       item_id = :old.id;
end hor_items_delete; 
/

ALTER TRIGGER uji_horarios.hor_items_delete ENABLE 


CREATE OR REPLACE TRIGGER uji_horarios.mutante_1_inicial 
    BEFORE INSERT OR UPDATE ON uji_horarios.hor_items 
    
BEGIN
  MUTANTE_ITEMS.V_NUM := 0;
END; 
/

ALTER TRIGGER uji_horarios.mutante_1_inicial ENABLE 


CREATE OR REPLACE TRIGGER uji_horarios.mutante_2_por_fila 
    AFTER INSERT OR UPDATE ON uji_horarios.hor_items REFERENCING 
    NEW AS new 
    OLD AS old 
    FOR EACH ROW 
begin
   mutante_items.v_num := mutante_items.v_num + 1;
   mutante_items.v_var_tabla (mutante_items.v_num) := :new.rowid;
end; 
/

ALTER TRIGGER uji_horarios.mutante_2_por_fila ENABLE 


CREATE OR REPLACE TRIGGER uji_horarios.mutante_3_final 
    AFTER INSERT OR UPDATE OF dia_semana_id, desde_el_dia, hasta_el_dia, repetir_cada_semanas, numero_iteraciones ON uji_horarios.hor_items 
    
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
/

ALTER TRIGGER uji_horarios.mutante_3_final ENABLE 




-- Informe de Resumen de Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                            25
-- CREATE INDEX                            17
-- ALTER TABLE                             62
-- CREATE VIEW                              3
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           4
-- ALTER TRIGGER                            4
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
-- CREATE USER                              1
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
