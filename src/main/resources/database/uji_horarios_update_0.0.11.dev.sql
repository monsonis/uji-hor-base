drop table uji_horarios.hor_items_circuitos;

drop table uji_horarios.hor_circuitos;

CREATE TABLE uji_horarios.hor_circuitos 
    ( 
     id NUMBER  NOT NULL , 
     grupo_id VARCHAR2 (10)  NOT NULL , 
     nombre VARCHAR2 (100)  NOT NULL , 
     plazas NUMBER  NOT NULL 
    ) 
;



ALTER TABLE uji_horarios.hor_circuitos 
    ADD CONSTRAINT hor_circuitos_est_PK PRIMARY KEY ( id ) ;



CREATE TABLE uji_horarios.hor_items_circuitos 
    ( 
     id NUMBER  NOT NULL , 
     item_id NUMBER  NOT NULL , 
     circuito_id NUMBER  NOT NULL 
    ) 
;



ALTER TABLE uji_horarios.hor_items_circuitos 
    ADD CONSTRAINT hor_items_circuitos_PK PRIMARY KEY ( id ) ;




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

CREATE TABLE uji_horarios.hor_circuitos_estudios 
    ( 
     id NUMBER  NOT NULL , 
     circuito_id NUMBER  NOT NULL , 
     estudio_id NUMBER  NOT NULL 
    ) 
;


CREATE INDEX uji_horarios.hor_circuitos_est_cir_IDX ON uji_horarios.hor_circuitos_estudios 
    ( 
     circuito_id ASC 
    ) 
;
CREATE INDEX uji_horarios.hor_circuitos_est_est_IDX ON uji_horarios.hor_circuitos_estudios 
    ( 
     estudio_id ASC 
    ) 
;

ALTER TABLE uji_horarios.hor_circuitos_estudios 
    ADD CONSTRAINT hor_circuitos_estudios_PK PRIMARY KEY ( id ) ;




ALTER TABLE uji_horarios.hor_circuitos_estudios 
    ADD CONSTRAINT hor_circuitos_est_cir_FK FOREIGN KEY 
    ( 
     circuito_id
    ) 
    REFERENCES uji_horarios.hor_circuitos 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_circuitos_estudios 
    ADD CONSTRAINT hor_circuitos_est_est_FK FOREIGN KEY 
    ( 
     estudio_id
    ) 
    REFERENCES uji_horarios.hor_estudios 
    ( 
     id
    ) 
;



ALTER TABLE uji_horarios.hor_circuitos 
    ADD ( 
     semestre_id NUMBER  NOT NULL 
    ) 
;




ALTER TABLE uji_horarios.hor_circuitos 
    ADD CONSTRAINT hor_circuitos_hor_semestres_FK FOREIGN KEY 
    ( 
     semestre_id
    ) 
    REFERENCES uji_horarios.hor_semestres 
    ( 
     id
    ) 
;

