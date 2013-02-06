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


CREATE OR REPLACE VIEW uji_horarios.hor_v_aulas_personas AS
SELECT DISTINCT c.persona_id,
  ap.aula_id,
  a.nombre,
  a.centro_id,
  c.centro,
  a.codigo,
  a.tipo
FROM uji_horarios.hor_ext_cargos_per c,
  uji_horarios.hor_aulas_planificacion ap,
  uji_horarios.hor_aulas a
WHERE c.estudio_id = ap.estudio_id
AND ap.aula_id     = a.id ;




	