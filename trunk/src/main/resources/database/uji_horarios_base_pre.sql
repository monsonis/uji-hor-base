---- Creación de usuarios 
create user uji_horarios identified by scrum2010 quota unlimited on datos; 
grant connect, resource, create view to uji_horarios;

create user uji_horarios_usr identified by scrum2010;
grant connect to uji_horarios_usr;

-- Secuencia para Hibernate 
create sequence uji_horarios.hibernate_sequence;



GRANT SELECT ON UJI_APA.APA_VW_PERSONAS_ITEMS TO uji_horarios;
GRANT SELECT ON UJI_APA.APA_VW_PERSONAS_ITEMS TO uji_horarios_usr;



