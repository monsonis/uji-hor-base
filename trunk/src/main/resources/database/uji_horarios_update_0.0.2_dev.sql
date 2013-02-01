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
;



ALTER TABLE uji_horarios.hor_horarios_horas 
    ADD CONSTRAINT hor_horarios_horas_PK PRIMARY KEY ( id ) ;




ALTER TABLE uji_horarios.hor_horarios_horas 
    ADD CONSTRAINT hor_horarios_horas_est_FK FOREIGN KEY 
    ( 
     estudio_id
    ) 
    REFERENCES uji_horarios.hor_estudios 
    ( 
     id
    ) 
;