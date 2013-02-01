delete hor_items_detalle;

delete hor_items_comunes;

delete hor_items_circuitos;

delete hor_items;

commit;


ALTER TABLE UJI_HORARIOS.HOR_ITEMS
  DROP CONSTRAINT HOR_ITEMS_HOR_ESTUDIOS_FK;

ALTER TABLE UJI_HORARIOS.HOR_ITEMS DROP COLUMN ASIGNATURA_ID;

ALTER TABLE UJI_HORARIOS.HOR_ITEMS DROP COLUMN ASIGNATURA;

ALTER TABLE UJI_HORARIOS.HOR_ITEMS DROP COLUMN ESTUDIO_ID;

ALTER TABLE UJI_HORARIOS.HOR_ITEMS DROP COLUMN ESTUDIO;

  
CREATE TABLE uji_horarios.hor_items_asignaturas 
    ( 
     id NUMBER  NOT NULL , 
     item_id NUMBER  NOT NULL , 
     asignatura_id VARCHAR2 (10)  NOT NULL , 
     asignatura VARCHAR2 (1000) , 
     estudio_id NUMBER  NOT NULL , 
     estudio VARCHAR2 (1000) 
    ) 
;



ALTER TABLE uji_horarios.hor_items_asignaturas 
    ADD CONSTRAINT hor_items_asignaturas_PK PRIMARY KEY ( id ) ;



drop table uji_horarios.hor_ext_cargos_per;


CREATE TABLE uji_horarios.hor_ext_cargos_per 
    ( 
     id NUMBER  NOT NULL , 
     persona_id NUMBER  NOT NULL , 
     nombre VARCHAR2 (1000) , 
     centro_id NUMBER , 
     centro VARCHAR2 (1000) , 
     estudio_id NUMBER , 
     estudio VARCHAR2 (1000) , 
     cargo_id NUMBER  NOT NULL , 
     cargo VARCHAR2 (1000) 
    ) 
;



ALTER TABLE uji_horarios.hor_ext_cargos_per 
    ADD CONSTRAINT hor_ext_cargos_per_PK PRIMARY KEY ( id ) ;




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
     cargo_id
    ) 
    REFERENCES uji_horarios.hor_tipos_cargos 
    ( 
     id
    ) 
;

	
	


Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (1, 62481, 'Cristina Pauner Chulvi', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 201, 'Grau en Relacions Laborals i Recursos Humans', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (2, 56321, 'Vicente Budí Orduña', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 201, 'Grau en Relacions Laborals i Recursos Humans', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (3, 62481, 'Cristina Pauner Chulvi', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 202, 'Grau en Turisme', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (4, 56321, 'Vicente Budí Orduña', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 202, 'Grau en Turisme', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (5, 61463, 'Rosa María Agost Canós', 2, 'Facultat de Ciències Humanes i Socials', 203, 'Grau en Comunicació Audiovisual', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (6, 104181, 'Eva Breva Franch', 2, 'Facultat de Ciències Humanes i Socials', 203, 'Grau en Comunicació Audiovisual', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (7, 61463, 'Rosa María Agost Canós', 2, 'Facultat de Ciències Humanes i Socials', 204, 'Grau en Periodisme', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (8, 104181, 'Eva Breva Franch', 2, 'Facultat de Ciències Humanes i Socials', 204, 'Grau en Periodisme', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (9, 61463, 'Rosa María Agost Canós', 2, 'Facultat de Ciències Humanes i Socials', 205, 'Grau en Estudis Anglesos', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (10, 104181, 'Eva Breva Franch', 2, 'Facultat de Ciències Humanes i Socials', 205, 'Grau en Estudis Anglesos', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (11, 61463, 'Rosa María Agost Canós', 2, 'Facultat de Ciències Humanes i Socials', 206, 'Grau en Publicitat i Relacions Públiques', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (12, 104181, 'Eva Breva Franch', 2, 'Facultat de Ciències Humanes i Socials', 206, 'Grau en Publicitat i Relacions Públiques', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (13, 61463, 'Rosa María Agost Canós', 2, 'Facultat de Ciències Humanes i Socials', 207, 'Grau en Traducció i Interpretació', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (14, 104181, 'Eva Breva Franch', 2, 'Facultat de Ciències Humanes i Socials', 207, 'Grau en Traducció i Interpretació', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (15, 65364, 'María Mercedes Fernández Alonso', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 208, 'Grau en Química', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (16, 60700, 'José Joaquin Gual Arnau', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 208, 'Grau en Química', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (17, 65364, 'María Mercedes Fernández Alonso', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 209, 'Grau en Arquitectura Tècnica', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (18, 60700, 'José Joaquin Gual Arnau', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 209, 'Grau en Arquitectura Tècnica', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (19, 62481, 'Cristina Pauner Chulvi', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 210, 'Grau en Administració d''Empreses', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (20, 56321, 'Vicente Budí Orduña', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 210, 'Grau en Administració d''Empreses', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (21, 62481, 'Cristina Pauner Chulvi', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 211, 'Grau en Economia', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (22, 56321, 'Vicente Budí Orduña', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 211, 'Grau en Economia', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (23, 62481, 'Cristina Pauner Chulvi', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 212, 'Grau en Finances i Comptabilitat', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (24, 56321, 'Vicente Budí Orduña', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 212, 'Grau en Finances i Comptabilitat', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (25, 62481, 'Cristina Pauner Chulvi', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 213, 'Grau en Dret', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (26, 56321, 'Vicente Budí Orduña', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 213, 'Grau en Dret', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (27, 62481, 'Cristina Pauner Chulvi', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 214, 'Grau en Criminologia i Seguretat', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (28, 56321, 'Vicente Budí Orduña', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 214, 'Grau en Criminologia i Seguretat', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (29, 61463, 'Rosa María Agost Canós', 2, 'Facultat de Ciències Humanes i Socials', 215, 'Grau en Història i Patrimoni', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (30, 104181, 'Eva Breva Franch', 2, 'Facultat de Ciències Humanes i Socials', 215, 'Grau en Història i Patrimoni', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (31, 61463, 'Rosa María Agost Canós', 2, 'Facultat de Ciències Humanes i Socials', 216, 'Grau en Humanitats: Estudis Interculturals', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (32, 104181, 'Eva Breva Franch', 2, 'Facultat de Ciències Humanes i Socials', 216, 'Grau en Humanitats: Estudis Interculturals', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (33, 61463, 'Rosa María Agost Canós', 2, 'Facultat de Ciències Humanes i Socials', 217, 'Grau en Mestre o Mestra d''Educació Infantil', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (34, 104181, 'Eva Breva Franch', 2, 'Facultat de Ciències Humanes i Socials', 217, 'Grau en Mestre o Mestra d''Educació Infantil', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (35, 61463, 'Rosa María Agost Canós', 2, 'Facultat de Ciències Humanes i Socials', 218, 'Grau en Mestre o Mestra d''Educació Primària', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (36, 104181, 'Eva Breva Franch', 2, 'Facultat de Ciències Humanes i Socials', 218, 'Grau en Mestre o Mestra d''Educació Primària', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (37, 65428, 'Rafael Ballester Arnal', 2922, 'Facultat de Ciències de la Salut', 219, 'Grau en Psicologia', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (38, 63254, 'Eva Cifre Gallego', 2922, 'Facultat de Ciències de la Salut', 219, 'Grau en Psicologia', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (39, 65364, 'María Mercedes Fernández Alonso', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 220, 'Grau en Enginyeria Química', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (40, 60700, 'José Joaquin Gual Arnau', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 220, 'Grau en Enginyeria Química', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (41, 65364, 'María Mercedes Fernández Alonso', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 221, 'Grau en Enginyeria en Tecnologies Industrials', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (42, 60700, 'José Joaquin Gual Arnau', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 221, 'Grau en Enginyeria en Tecnologies Industrials', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (43, 65364, 'María Mercedes Fernández Alonso', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 222, 'Grau en Enginyeria Mecànica', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (44, 60700, 'José Joaquin Gual Arnau', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 222, 'Grau en Enginyeria Mecànica', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (45, 65364, 'María Mercedes Fernández Alonso', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 223, 'Grau en Matemàtica Computacional', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (46, 60700, 'José Joaquin Gual Arnau', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 223, 'Grau en Matemàtica Computacional', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (47, 65364, 'María Mercedes Fernández Alonso', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 224, 'Grau en Enginyeria en Disseny Industrial i Desenvolupament de Productes', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (48, 60700, 'José Joaquin Gual Arnau', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 224, 'Grau en Enginyeria en Disseny Industrial i Desenvolupament de Productes', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (49, 65364, 'María Mercedes Fernández Alonso', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 225, 'Grau en Enginyeria Informàtica', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (50, 60700, 'José Joaquin Gual Arnau', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 225, 'Grau en Enginyeria Informàtica', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (51, 65364, 'María Mercedes Fernández Alonso', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 227, 'Grau en Enginyeria Agroalimentària i del Medi Rural', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (52, 60700, 'José Joaquin Gual Arnau', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 227, 'Grau en Enginyeria Agroalimentària i del Medi Rural', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (53, 65364, 'María Mercedes Fernández Alonso', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 228, 'Grau en Enginyeria Elèctrica', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (54, 60700, 'José Joaquin Gual Arnau', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 228, 'Grau en Enginyeria Elèctrica', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (55, 65428, 'Rafael Ballester Arnal', 2922, 'Facultat de Ciències de la Salut', 229, 'Grau en Medicina', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (56, 63254, 'Eva Cifre Gallego', 2922, 'Facultat de Ciències de la Salut', 229, 'Grau en Medicina', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (57, 65428, 'Rafael Ballester Arnal', 2922, 'Facultat de Ciències de la Salut', 230, 'Grau en Infermeria', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (58, 63254, 'Eva Cifre Gallego', 2922, 'Facultat de Ciències de la Salut', 230, 'Grau en Infermeria', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (59, 65364, 'María Mercedes Fernández Alonso', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 231, 'Grau en Disseny i Desenvolupament de Videojocs (pendent d''autorització d''implantació)', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (60, 60700, 'José Joaquin Gual Arnau', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 231, 'Grau en Disseny i Desenvolupament de Videojocs (pendent d''autorització d''implantació)', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (61, 62481, 'Cristina Pauner Chulvi', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 232, 'Grau en Gestió i Administració Pública (pendent d''autorització d''implantació)', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (62, 56321, 'Vicente Budí Orduña', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 232, 'Grau en Gestió i Administració Pública (pendent d''autorització d''implantació)', 3, 'Director, Dega o Secretari de Centre');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (153, 60701, 'María Arantzazu Vicente Palacio', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 201, 'Grau en Relacions Laborals i Recursos Humans', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (154, 49999, 'Rafael Lapiedra Alcamí', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 202, 'Grau en Turisme', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (155, 74888, 'Emilio Sáez Soro', 2, 'Facultat de Ciències Humanes i Socials', 203, 'Grau en Comunicació Audiovisual', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (156, 7544, 'Andreu Casero Ripollés', 2, 'Facultat de Ciències Humanes i Socials', 204, 'Grau en Periodisme', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (157, 87794, 'Maria Lluisa Gea Valor', 2, 'Facultat de Ciències Humanes i Socials', 205, 'Grau en Estudis Anglesos', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (158, 95402, 'María del Rocío Blay Arráez', 2, 'Facultat de Ciències Humanes i Socials', 206, 'Grau en Publicitat i Relacions Públiques', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (159, 65480, 'María Jesús Blasco Mayor', 2, 'Facultat de Ciències Humanes i Socials', 207, 'Grau en Traducció i Interpretació', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (160, 60611, 'Joaquín Beltrán Arandes', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 208, 'Grau en Química', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (161, 176839, 'Angel Miguel Pitarch Roig', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 209, 'Grau en Arquitectura Tècnica', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (162, 58645, 'Luis Jose Callarisa Fiol', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 210, 'Grau en Administració d''Empreses', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (163, 85454, 'Miguel Ginés Vilar', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 211, 'Grau en Economia', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (164, 65167, 'María Jesús Muñoz Torres', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 212, 'Grau en Finances i Comptabilitat', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (165, 63685, 'Fernando Juan Mateu', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 213, 'Grau en Dret', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (166, 56094, 'Cristina Guisasola Lerma', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 214, 'Grau en Criminologia i Seguretat', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (167, 65106, 'Carles Alfred Rabassa Vaquer', 2, 'Facultat de Ciències Humanes i Socials', 215, 'Grau en Història i Patrimoni', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (168, 58607, 'Sonia Reverter Bañón', 2, 'Facultat de Ciències Humanes i Socials', 216, 'Grau en Humanitats: Estudis Interculturals', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (169, 59325, 'Miguel Salvador Bauza', 2, 'Facultat de Ciències Humanes i Socials', 217, 'Grau en Mestre o Mestra d''Educació Infantil', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (170, 64514, 'Roberto Jose García Antolin', 2, 'Facultat de Ciències Humanes i Socials', 218, 'Grau en Mestre o Mestra d''Educació Primària', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (171, 104164, 'Ana Hermenegilda Alarcon Aguilar', 2922, 'Facultat de Ciències de la Salut', 219, 'Grau en Psicologia', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (172, 65016, 'Vicente Beltrán Porcar', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 220, 'Grau en Enginyeria Química', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (173, 62314, 'María Dolores Bovea Edo', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 221, 'Grau en Enginyeria en Tecnologies Industrials', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (174, 65259, 'Amelia Simó Vidal', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 223, 'Grau en Matemàtica Computacional', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (175, 65066, 'Julia Galán Serrano', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 224, 'Grau en Enginyeria en Disseny Industrial i Desenvolupament de Productes', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (176, 54341, 'María José Aramburu Cabo', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 225, 'Grau en Enginyeria Informàtica', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (177, 62645, 'Víctor Flors Herrero', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 227, 'Grau en Enginyeria Agroalimentària i del Medi Rural', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (178, 65314, 'Enrique Francisco Belenguer Balaguer', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 228, 'Grau en Enginyeria Elèctrica', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (179, 471242, 'María Trinidad Herrero Ezquerro', 2922, 'Facultat de Ciències de la Salut', 229, 'Grau en Medicina', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (180, 249058, 'Maria Loreto Josefa Maciá Soler', 2922, 'Facultat de Ciències de la Salut', 230, 'Grau en Infermeria', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (181, 65388, 'Miguel Chover Selles', 4, 'Escola Superior de Tecnologia i Ciències Experimentals', 231, 'Grau en Disseny i Desenvolupament de Videojocs (pendent d''autorització d''implantació)', 1, 'Director d''estudi');
Insert into UJI_HORARIOS.HOR_EXT_CARGOS_PER
   (ID, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, ESTUDIO_ID, ESTUDIO, CARGO_ID, CARGO)
 Values
   (182, 61360, 'Beatriz Susana Tomás Mallén', 3, 'Facultat de Ciències Jurídiques i Econòmiques', 232, 'Grau en Gestió i Administració Pública (pendent d''autorització d''implantació)', 1, 'Director d''estudi');
COMMIT;
	

ALTER TABLE uji_horarios.hor_items_asignaturas 
    ADD CONSTRAINT hor_items_asig_estudios_FK FOREIGN KEY 
    ( 
     estudio_id
    ) 
    REFERENCES uji_horarios.hor_estudios 
    ( 
     id
    ) 
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
;

CREATE INDEX uji_horarios.hor_items_asig_it_IDX ON uji_horarios.hor_items_asignaturas 
    ( 
     item_id ASC 
    ) 
;
CREATE INDEX uji_horarios.hor_items_asig_est_IDX ON uji_horarios.hor_items_asignaturas 
    ( 
     estudio_id ASC 
    ) 
;
CREATE INDEX uji_horarios.hor_items_asig_est_asi_IDX ON uji_horarios.hor_items_asignaturas 
    ( 
     asignatura_id ASC , 
     estudio_id ASC 
    ) 
;

CREATE OR REPLACE TRIGGER UJI_HORARIOS.mutante_3_final
   after insert or update of dia_semana_id, desde_el_dia, hasta_el_dia, repetir_cada_semanas, numero_iteraciones
   ON UJI_HORARIOS.HOR_ITEMS
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


CREATE INDEX hor_items_v_idx ON hor_items 
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
CREATE INDEX hor_items_v2_IDX ON hor_items 
    ( 
     id ASC , 
     dia_semana_id ASC , 
     detalle_manual ASC 
    ) 
    LOGGING 
;
CREATE INDEX hor_item_det_man_idx ON hor_items 
    ( 
     id ASC , 
     detalle_manual ASC 
    ) 
    LOGGING 
;




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
                  hor_contar_festivos (NVL (x.desde_el_dia, fecha_inicio), x.fecha, x.dia_semana_id,
                                       x.repetir_cada_semanas) festivos,
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
           FROM   (SELECT i.id, null estudio_id, i.curso_id, i.semestre_id, i.grupo_id, i.tipo_subgrupo_id,
                          i.subgrupo_id, i.dia_Semana_id, null asignatura_id, fecha_inicio, fecha_fin,
                          fecha_examenes_inicio, fecha_examenes_fin, i.desde_el_dia, hasta_el_dia, repetir_cada_semanas,
                          numero_iteraciones, detalle_manual, c.fecha, tipo_dia, dia_semana
                   FROM   hor_semestres_detalle s,
                          hor_items i,
                          hor_ext_calendario c
                   WHERE  i.semestre_id = s.semestre_id
                   AND    trunc (c.fecha) BETWEEN fecha_inicio AND NVL (fecha_examenes_fin, fecha_fin)
                   AND    c.dia_semana_id = i.dia_semana_id
                   AND    tipo_dia IN ('L', 'E', 'F')
                   and    vacaciones = 0
                   AND    detalle_manual = 0) x) d,
          hor_items i
   WHERE  i.curso_id = d.curso_id
   AND    i.semestre_id = d.semestre_id
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
   FROM   (SELECT i.id, c.fecha, numero_iteraciones, repetir_cada_semanas, s.fecha_inicio, s.fecha_fin, null estudio_id,
                  i.semestre_id, i.curso_id, null asignatura_id, i.grupo_id, i.tipo_subgrupo_id, i.subgrupo_id,
                  i.dia_semana_id, tipo_dia
           FROM   hor_semestres_detalle s,
                  hor_items i,
                  hor_ext_calendario c
           WHERE  i.semestre_id = s.semestre_id
           AND    trunc (c.fecha) BETWEEN fecha_inicio AND NVL (fecha_examenes_fin, fecha_fin)
           AND    c.dia_semana_id = i.dia_semana_id
           AND    tipo_dia IN ('L', 'E', 'F')
           and    vacaciones = 0
           AND    detalle_manual = 1) c,
          hor_items_detalle d
   WHERE  c.id = d.item_id(+)
   AND    trunc (c.fecha) = trunc (d.inicio(+));

grant select on est_ubic_estructurales to uji_horarios

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
                  hor_contar_festivos (NVL (x.desde_el_dia, fecha_inicio), x.fecha, x.dia_semana_id,
                                       x.repetir_cada_semanas) festivos,
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
           FROM   (SELECT i.id, null estudio_id, i.curso_id, i.semestre_id, i.grupo_id, i.tipo_subgrupo_id,
                          i.subgrupo_id, i.dia_Semana_id, null asignatura_id, fecha_inicio, fecha_fin,
                          fecha_examenes_inicio, fecha_examenes_fin, i.desde_el_dia, hasta_el_dia, repetir_cada_semanas,
                          numero_iteraciones, detalle_manual, c.fecha, tipo_dia, dia_semana
                   FROM   hor_semestres_detalle s,
                          hor_items i,
                          hor_ext_calendario c
                   WHERE  i.semestre_id = s.semestre_id
                   AND    trunc (c.fecha) BETWEEN fecha_inicio AND NVL (fecha_examenes_fin, fecha_fin)
                   AND    c.dia_semana_id = i.dia_semana_id
                   AND    tipo_dia IN ('L', 'E', 'F')
                   and    vacaciones = 0
                   AND    detalle_manual = 0) x) d,
          hor_items i
   WHERE  i.curso_id = d.curso_id
   AND    i.semestre_id = d.semestre_id
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
   FROM   (SELECT i.id, c.fecha, numero_iteraciones, repetir_cada_semanas, s.fecha_inicio, s.fecha_fin, null estudio_id,
                  i.semestre_id, i.curso_id, null asignatura_id, i.grupo_id, i.tipo_subgrupo_id, i.subgrupo_id,
                  i.dia_semana_id, tipo_dia
           FROM   hor_semestres_detalle s,
                  hor_items i,
                  hor_ext_calendario c
           WHERE  i.semestre_id = s.semestre_id
           AND    trunc (c.fecha) BETWEEN fecha_inicio AND NVL (fecha_examenes_fin, fecha_fin)
           AND    c.dia_semana_id = i.dia_semana_id
           AND    tipo_dia IN ('L', 'E', 'F')
           and    vacaciones = 0
           AND    detalle_manual = 1) c,
          hor_items_detalle d
   WHERE  c.id = d.item_id(+)
   AND    trunc (c.fecha) = trunc (d.inicio(+));



