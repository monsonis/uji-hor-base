CREATE OR REPLACE FORCE VIEW UJI_HORARIOS.HOR_EXT_CARGOS_PER (ID,
                                                              PERSONA_ID,
                                                              NOMBRE,
                                                              CENTRO_ID,
                                                              CENTRO,
                                                              ESTUDIO_ID,
                                                              ESTUDIO,
                                                              CARGO_ID,
                                                              CARGO
                                                             ) AS
   select rownum id, PERSONA_ID, NOMBRE, CENTRO_ID, CENTRO, TITULACION_ID estudio_id, TITULACION estudio, CARGO_ID,
          CARGO
   from   (select   cp.per_id persona_id, p.nombre || ' ' || apellido1 || ' ' || apellido2 nombre, ulogica_id centro_id,
                    ubic.nombre centro, tit.id titulacion_id, tit.nombre titulacion, tc.id cargo_id, tc.nombre cargo
           from     grh_grh.grh_cargos_per cp,
                    gri_est.est_ubic_estructurales ubic,
                    gra_Exp.exp_v_titu_todas tit,
                    uji_horarios.hor_tipos_Cargos tc,
                    gri_per.per_personas p
           where    (    f_fin is null
                     and (   f_fin is null
                          or f_fin >= sysdate)
                     and crg_id in (189, 195, 188, 30))
           and      ulogica_id = ubic.id
           and      ulogica_id = tit.uest_id
           and      tit.activa = 'S'
           and      tit.tipo = 'G'
           and      tc.id = 3
           and      cp.per_id = p.id
           union all
/* PAS de centro */
           select   cp.per_id persona_id, p.nombre || ' ' || apellido1 || ' ' || apellido2 nombre,
                    ubicacion_id centro_id, ubic.nombre centro, tit.id titulacion_id, tit.nombre titulacion,
                    tc.id cargo_id, tc.nombre cargo
           from     grh_grh.grh_vw_contrataciones_ult cp,
                    gri_est.est_ubic_estructurales ubic,
                    gra_Exp.exp_v_titu_todas tit,
                    uji_horarios.hor_tipos_Cargos tc,
                    gri_per.per_personas p
           where    ubicacion_id = ubic.id
           and      ubicacion_id = tit.uest_id
           and      tit.activa = 'S'
           and      tit.tipo = 'G'
           and      tc.id = 4
           and      cp.per_id = p.id
           union all
/* directores de titulacion */
           select   cp.per_id persona_id, p.nombre || ' ' || apellido1 || ' ' || apellido2 nombre, ulogica_id centro_id,
                    ubic.nombre centro, tit.id titulacion_id, tit.nombre titulacion, tc.id cargo_id, tc.nombre cargo
           from     grh_grh.grh_cargos_per cp,
                    gri_est.est_ubic_estructurales ubic,
                    gra_Exp.exp_v_titu_todas tit,
                    uji_horarios.hor_tipos_Cargos tc,
                    gri_per.per_personas p
           where    (    f_fin is null
                     and (   f_fin is null
                          or f_fin >= sysdate)
                     and crg_id in (192, 193))
           and      ulogica_id = ubic.id
           and      ulogica_id = tit.uest_id
           and      tit.activa = 'S'
           and      tit.tipo = 'G'
           and      tc.id = 1
           and      cp.per_id = p.id
           and      cp.tit_id = tit.id
           union all
/* permisos extra */
           select   per.persona_id, p.nombre || ' ' || apellido1 || ' ' || apellido2 nombre, ubic.id centro_id,
                    ubic.nombre centro, tit.id titulacion_id, tit.nombre titulacion, tc.id cargo_id, tc.nombre cargo
           from     uji_horarios.hor_permisos_extra per,
                    gri_per.per_personas p,
                    gra_Exp.exp_v_titu_todas tit,
                    uji_horarios.hor_tipos_cargos tc,
                    gri_est.est_ubic_estructurales ubic
           where    persona_id = p.id
           and      estudio_id = tit.id
           and      tipo_Cargo_id = tc.id
           and      tit.activa = 'S'
           and      tit.tipo = 'G'
           and tit.uest_id= ubic.id
/* administradores */
           union all
           select distinct per.persona_id, p.nombre || ' ' || apellido1 || ' ' || apellido2 nombre, u.id centro_id,
                           u.nombre centro, tit.id titulacion_id, tit.nombre titulacion, tc.id cargo_id,
                           tc.nombre cargo
           from            uji_apa.apa_vw_personas_items per,
                           gri_per.per_personas p,
                           gra_Exp.exp_v_titu_todas tit,
                           uji_horarios.hor_tipos_cargos tc,
                           gri_Est.est_ubic_estructurales u
           where           persona_id = p.id
           and             tc.id = 1
           and             tit.activa = 'S'
           and             tit.tipo = 'G'
           and             aplicacion_id = 46
           and             role = 'ADMIN'
           and             tit.uest_id = u.id
           order by        titulacion_id);


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