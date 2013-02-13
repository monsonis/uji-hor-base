ALTER TABLE UJI_HORARIOS.HOR_ITEMS
  DROP CONSTRAINT HOR_ITEMS_HOR_AULAS_PLAN_FK;
  
ALTER TABLE UJI_HORARIOS.HOR_AULAS_PLANIFICACION DROP COLUMN NOMBRE;

ALTER TABLE UJI_HORARIOS.HOR_AULAS_PLANIFICACION DROP COLUMN CURSO_ID;

ALTER TABLE UJI_HORARIOS.HOR_AULAS_PLANIFICACION
MODIFY(AULA_ID  NOT NULL);

ALTER TABLE uji_horarios.hor_aulas_planificacion 
    ADD CONSTRAINT hor_aulas_planificacion__UN UNIQUE ( aula_id , estudio_id , semestre_id ) ;

update hor_items
set aula_planificacion_id = (select aula_id from hor_aulas_planificacion where aula_planificacion_id= id)
where aula_planificacion_id is not null;


ALTER TABLE uji_horarios.hor_items 
    ADD CONSTRAINT hor_items_hor_aulas_FK FOREIGN KEY 
    ( 
     aula_planificacion_id
    ) 
    REFERENCES uji_horarios.hor_aulas 
    ( 
     id
    ) 
;


CREATE OR REPLACE FORCE VIEW UJI_HORARIOS.HOR_V_AULAS_PERSONAS (PERSONA_ID,
                                                                AULA_ID,
                                                                NOMBRE,
                                                                CENTRO_ID,
                                                                CENTRO,
                                                                CODIGO,
                                                                TIPO
                                                               ) AS
   select distinct c.persona_id, ap.aula_id, a.nombre, a.centro_id, cen.nombre centro, codigo, tipo
   from            uji_horarios.hor_ext_Cargos_per c,
                   uji_horarios.hor_aulas_planificacion ap,
                   uji_horarios.hor_aulas a,
                   uji_horarios.hor_Centros cen
   where           c.estudio_id = ap.estudio_id
   and             ap.aula_id = a.id
   and a.centro_id= cen.id;
   

ALTER TABLE UJI_HORARIOS.HOR_EXT_PERSONAS
MODIFY(DEPARTAMENTO_ID  NULL);



CREATE TABLE uji_horarios.hor_curso_academico 
    ( 
     id NUMBER  NOT NULL 
    ) 
;

ALTER TABLE uji_horarios.hor_curso_academico 
    ADD CONSTRAINT hor_curso_academico_PK PRIMARY KEY ( id ) ;


insert into uji_horarios.hor_curso_academico
values(2012);

commit;   


CREATE TABLE uji_horarios.hor_ext_asignaturas_detalle 
    ( 
     asignatura_id VARCHAR2 (10)  NOT NULL , 
     nombre_asignatura VARCHAR2 (1000) , 
     creditos NUMBER , 
     ciclo NUMBER , 
     crd_Te NUMBER , 
     crd_pr NUMBER , 
     crd_la NUMBER , 
     crd_se NUMBER , 
     crd_tu NUMBER , 
     crd_ev NUMBER , 
     crd_ctotal NUMBER 
    ) 
;



ALTER TABLE uji_horarios.hor_ext_asignaturas_detalle 
    ADD CONSTRAINT hor_ext_asignaturas_detalle_PK PRIMARY KEY ( asignatura_id ) ;


Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1001', 'Introducci� a la Comptabilitat (Empresa)', 6, 1, 'FB', 2.8, 2.8, 0, 0, 0, 0.4, 6);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1002', 'Introducci� a l''Administraci� d''Empreses (Empresa)', 6, 1, 'FB', 3, 2.4, 0, 0, 0.35, 0.2, 5.95);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1003', 'Matem�tica de les Operacions Financeres (Empresa)', 6, 1, 'FB', 2, 1, 1.5, 0, 0.1, 0.4, 5);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1004', 'Introducci� a la Microeconomia (Economia)', 6, 1, 'FB', 2.25, 1.5, 0.75, 0, 0.3, 0.2, 5);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1005', 'Matem�tiques I (Matem�tiques)', 6, 1, 'FB', 2.7, 3, 0, 0, 0, 0.3, 6);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1006', 'Introducci� a la Macroeconomia (Economia)', 6, 1, 'FB', 2.25, 1.5, 0.75, 0, 0.3, 0.2, 5);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1007', 'Matem�tiques II (Matem�tiques)', 6, 1, 'FB', 2.7, 3, 0, 0, 0, 0.3, 6);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1008', 'Hist�ria Econ�mica (Economia)', 6, 1, 'FB', 3, 1.5, 0, 0, 0.3, 0.2, 5);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1009', 'Introducci� al Dret (Dret)', 6, 1, 'FB', 5.8, 0, 0, 0, 0, 0.2, 6);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1010', 'Introducci� als Sistemes d''Informaci� de l''Empresa', 6, 1, 'OB', 3, 1, 1, 0, 0.3, 0.4, 5.7);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1011', 'Estad�stica', 6, 1, 'FB', 2.7, 2, 1, 0, 0, 0.3, 6);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1012', 'Comptabilitat Financera', 6, 1, 'OB', 2.8, 2.8, 0, 0, 0, 0.4, 6);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1013', 'Direcci� Financera', 6, 1, 'OB', 2.5, 0, 2.5, 0.1, 0.6, 0.3, 6);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1014', 'Direcci� d''Empreses', 6, 1, 'OB', 3, 1.7, 0, 0, 0.25, 0.4, 5.35);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1015', 'Macroeconomia', 6, 1, 'OB', 2.25, 0.75, 1.5, 0, 0.3, 0.2, 5);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1016', 'An�lisi d''Estats Financers', 6, 1, 'OB', 2.7, 3, 0, 0, 0, 0.3, 6);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1017', 'Economia Espanyola i Mundial', 6, 1, 'OB', 3.2, 0, 1.2, 0, 0.3, 0.2, 4.9);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1018', 'Fonaments de M�rqueting', 6, 1, 'OB', 3.6, 1.6, 0, 0.2, 0.2, 0.4, 6);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1019', 'M�todes Quantitatius', 6, 1, 'OB', 2.25, 0, 2.25, 0, 0.3, 0.2, 5);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1020', 'Microeconomia', 6, 1, 'OB', 2.25, 0.75, 1.5, 0, 0.3, 0.2, 5);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1021', 'Fonaments d''Econometria', 6, 1, 'OB', 2.25, 0, 2.25, 0, 0.3, 0.2, 5);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1022', 'Angl�s per a l''Empresa', 6, 1, 'OB', 0, 5.5, 0, 0, 0.1, 0.4, 6);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1023', 'Comptabilitat de Costos', 6, 1, 'OB', 2.5, 2.5, 0, 0, 0.1, 0.4, 5.5);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1024', 'An�lisi i Formulaci� d''Estrat�gies Empresarials', 6, 1, 'OB', 3, 1.9, 0, 0, 0.2, 0.2, 5.3);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1025', 'M�rqueting Operatiu', 6, 1, 'OB', 3, 2.1, 0, 0.3, 0.3, 0.3, 6);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1026', 'Dret Mercantil i Tributari de Societats', 6, 1, 'OB', 4.8, 1, 0, 0, 0, 0.2, 6);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1027', 'Investigaci� de Mercats', 6, 1, 'OB', 2.7, 0, 2.7, 0, 0.2, 0.4, 6);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1028', 'Direcci� de Recursos Humans', 6, 1, 'OB', 3.7, 1.5, 0, 0, 0.3, 0.5, 6);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1029', 'Direcci� d''Operacions', 6, 1, 'OB', 3.7, 0.6, 0, 0, 0.7, 0.4, 5.4);
Insert into UJI_HORARIOS.HOR_EXT_ASIGNATURAS_DETALLE
   (ASIGNATURA_ID, NOMBRE_ASIGNATURA, CREDITOS, CICLO, CARACTER, CRD_TE, CRD_PR, CRD_LA, CRD_SE, CRD_TU, CRD_EV, CRD_CTOTAL)
 Values
   ('AE1030', 'Implantaci� d''Estrat�gies Empresarials', 6, 1, 'OB', 3, 2.1, 0, 0, 0.3, 0.3, 5.7);
COMMIT;

