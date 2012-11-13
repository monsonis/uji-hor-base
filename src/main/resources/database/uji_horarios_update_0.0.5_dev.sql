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

