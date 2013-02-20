CREATE TABLE uji_horarios.hor_estudios_compartidos 
    ( 
     id NUMBER  NOT NULL , 
     estudio_id NUMBER  NOT NULL , 
     estudio_id_compartido NUMBER  NOT NULL 
    ) 
;


CREATE INDEX uji_horarios.hor_estudios_comp_est_IDX ON uji_horarios.hor_estudios_compartidos 
    ( 
     estudio_id ASC 
    ) 
;

ALTER TABLE uji_horarios.hor_estudios_compartidos 
    ADD CONSTRAINT hor_estudios_compartidos_PK PRIMARY KEY ( id ) ;




ALTER TABLE uji_horarios.hor_estudios_compartidos 
    ADD CONSTRAINT hor_estudios_comp_est1_FK FOREIGN KEY 
    ( 
     estudio_id
    ) 
    REFERENCES uji_horarios.hor_estudios 
    ( 
     id
    ) 
;


ALTER TABLE uji_horarios.hor_estudios_compartidos 
    ADD CONSTRAINT hor_estudios_comp_est2_FK FOREIGN KEY 
    ( 
     estudio_id_compartido
    ) 
    REFERENCES uji_horarios.hor_estudios 
    ( 
     id
    ) 
;

insert into uji_horarios.hor_estudios_compartidos values (1,210,211); 
insert into uji_horarios.hor_estudios_compartidos values (2,210,212); 
insert into uji_horarios.hor_estudios_compartidos values (3,211,210); 
insert into uji_horarios.hor_estudios_compartidos values (4,211,212); 
insert into uji_horarios.hor_estudios_compartidos values (5,212,210); 
insert into uji_horarios.hor_estudios_compartidos values (6,212,211); 
insert into uji_horarios.hor_estudios_compartidos values (7,215,216); 
insert into uji_horarios.hor_estudios_compartidos values (8,216,215); 
insert into uji_horarios.hor_estudios_compartidos values (9,225,223); 
insert into uji_horarios.hor_estudios_compartidos values (10,223,225); 
insert into uji_horarios.hor_estudios_compartidos values (11,227,228); 
insert into uji_horarios.hor_estudios_compartidos values (12,227,220); 
insert into uji_horarios.hor_estudios_compartidos values (13,227,221); 
insert into uji_horarios.hor_estudios_compartidos values (14,227,222); 
insert into uji_horarios.hor_estudios_compartidos values (15,228,227); 
insert into uji_horarios.hor_estudios_compartidos values (16,228,220); 
insert into uji_horarios.hor_estudios_compartidos values (17,228,221); 
insert into uji_horarios.hor_estudios_compartidos values (18,228,222); 
insert into uji_horarios.hor_estudios_compartidos values (19,220,227); 
insert into uji_horarios.hor_estudios_compartidos values (20,220,228); 
insert into uji_horarios.hor_estudios_compartidos values (21,220,221); 
insert into uji_horarios.hor_estudios_compartidos values (22,220,222); 
insert into uji_horarios.hor_estudios_compartidos values (23,221,227); 
insert into uji_horarios.hor_estudios_compartidos values (24,221,228); 
insert into uji_horarios.hor_estudios_compartidos values (25,221,220); 
insert into uji_horarios.hor_estudios_compartidos values (26,221,222); 
insert into uji_horarios.hor_estudios_compartidos values (27,222,227); 
insert into uji_horarios.hor_estudios_compartidos values (28,222,228); 
insert into uji_horarios.hor_estudios_compartidos values (29,222,220); 
insert into uji_horarios.hor_estudios_compartidos values (30,222,221); 
 

 