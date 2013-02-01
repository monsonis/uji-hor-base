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
           select   per.persona_id, p.nombre || ' ' || apellido1 || ' ' || apellido2 nombre, null centro_id,
                    null centro, tit.id titulacion_id, tit.nombre titulacion, tc.id cargo_id, tc.nombre cargo
           from     uji_horarios.hor_permisos_extra per,
                    gri_per.per_personas p,
                    gra_Exp.exp_v_titu_todas tit,
                    uji_horarios.hor_tipos_cargos tc
           where    persona_id = p.id
           and      estudio_id = tit.id
           and      tipo_Cargo_id = tc.id
           and      tit.activa = 'S'
           and      tit.tipo = 'G'
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

