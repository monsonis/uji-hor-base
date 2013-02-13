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

grant select on gra_pod.pod_vsp_27  to uji_horarios;
grant select on gra_pod.pod_asignaturas  to uji_horarios;
grant select on gra_pod.pod_asig_sin_coste  to uji_horarios;
grant select on gra_pod.pod_asignaturas_titulaciones to uji_horarios;


create or replace view uji_horarios.hor_ext_asignaturas_detalle as 
select v.asi_id asignatura_id, nom_asi nombre_asignatura, a.creditos, cicle ciclo,
               decode (v.tit_id,
                       9, v.caracter,
                       26, v.caracter,
                       31, v.caracter,
                       decode (v.caracter, 'TR', 'FB', 'LC', 'PE', v.caracter)
                      ) caracter,
               crd_te, crd_pr, crd_la, crd_se, crd_tu, crd_ev, crd_ctotal
        from   gra_pod.pod_vsp_27 v,
               gra_pod.pod_asignaturas a,
               gra_pod.pod_asig_sin_coste sc,
               gra_pod.pod_asignaturas_titulaciones atit,
               uji_horarios.hor_curso_academico ca
        where  v.curso_Aca = ca.id
        and    v.asi_id = a.id
        and    v.curso_aca = sc.curso_aca(+)
        and    v.asi_id = sc.asi_id(+)
        and    v.tit_id = atit.tit_id
        and    v.asi_id = atit.asi_id;   
		
CREATE OR REPLACE PROCEDURE UJI_HORARIOS.VALIDACION_HORARIOS_PDF (
   name_array    IN   euji_util.ident_arr2,
   value_array   IN   euji_util.ident_arr2
) IS
   -- Parametros
   pEstudio    CONSTANT VARCHAR2 (40)   := euji_util.euji_getparam (name_array, value_array, 'pEstudio');
   pSemestre   CONSTANT VARCHAR2 (40)   := euji_util.euji_getparam (name_array, value_array, 'pSemestre');
   pGrupo      CONSTANT VARCHAR2 (40)   := euji_util.euji_getparam (name_array, value_array, 'pGrupo');
   pCurso      CONSTANT VARCHAR2 (40)   := euji_util.euji_getparam (name_array, value_array, 'pCurso');
   v_nombre             varchar2 (2000);

   procedure cabecera is
   begin
      owa_util.mime_header (ccontent_type => 'text/xml', bclose_header => true);
      fop.documentOpen (margin_left => 1.5, margin_right => 1.5, margin_top => 0.5, margin_bottom => 0.5,
                        extent_before => 3, extent_after => 1.5, extent_margin_top => 3, extent_margin_bottom => 1.5);
      fop.pageBodyOpen;
      htp.p (fof.documentheaderopen || fof.blockopen (space_after => 0.1) || fof.tableOpen
             || fof.tablecolumndefinition (column_width => 3) || fof.tablecolumndefinition (column_width => 15)
             || fof.tablebodyopen || fof.tableRowOpen || fof.tablecellopen
             || fof.block (fof.logo_uji, text_align => 'center') || fof.tablecellclose
             || fof.tablecellopen (display_align => 'center', padding => 0.4)
             || fof.block (text => 'Controls horaris', font_size => 12, font_weight => 'bold', font_family => 'Arial',
                           text_align => 'center')
             || fof.block (text => v_nombre, font_size => 12, font_weight => 'bold', font_family => 'Arial',
                           text_align => 'center')
             || fof.block (text => 'Curs: ' || pCurso || ' - Semestre: ' || pSemestre || ' - Grup: ' || pGrupo,
                           font_size => 12, font_weight => 'bold', font_family => 'Arial', text_align => 'center')
             || fof.tablecellclose || fof.tableRowClose || fof.tablebodyclose || fof.tableClose || fof.blockclose
             || fof.documentheaderclose);
      -- El numero paginas se escribe a mano para poner el texto más pequeño
      htp.p (fof.documentfooteropen || fof.tableOpen || fof.tablecolumndefinition (column_width => 18)
             || fof.tablebodyopen || fof.tableRowOpen || fof.tablecellopen
             || fof.block (text => 'Pag. <fo:page-number /> de <fo:page-number-citation ref-id="lastBlock" />',
                           text_align => 'right', font_weight => 'bold', font_size => 8)
             || fof.tablecellclose || fof.tableRowClose || fof.tablebodyclose || fof.tableClose
             || fof.documentfooterclose);
      fop.pageFlowOpen;
      fop.block (fof.white_space);
   end;

   procedure pie is
   begin
      htp.p ('<fo:block  id="lastBlock" space-before="1cm" font-size="8pt">');
      htp.p ('Data de generació: ' || to_char (sysdate, 'dd/mm/yyy hh24:mi'));
      fop.blockclose ();
      fop.pageFlowClose;
      fop.pageBodyClose;
      fop.documentClose;
   end;

   function espacios (p_espacios in number)
      return varchar2 is
      v_rdo   varchar2 (2000);
   begin
      v_rdo := null;

      for x in 1 .. p_espacios loop
         v_rdo := v_rdo || fof.white_space;
      end loop;

      return v_rdo;
   end;

   procedure p (p_texto in varchar2) is
   begin
      fop.block (espacios (3) || p_texto);
   end;

   procedure control_1 is
      v_aux   number := 0;

      cursor lista_errores_1 is
         select distinct asignatura_id, tipo_subgrupo_id || subgrupo_id subgrupo,
                         decode (tipo_subgrupo_id, 'TE', 1, 'PR', 2, 'LA', 3, 'SE', 4, 0) tipo
         from            uji_horarios.hor_items i,
                         hor_items_asignaturas asi
         where           i.id = asi.item_id
         and             curso_id = pCurso
         and             semestre_id = pSemestre
         and             grupo_id = pGrupo
         and             tipo_subgrupo_id not in ('AV', 'TU')
         and             aula_planificacion_id is null
         and             estudio_id = pEstudio
         order by        1,
                         decode (tipo_subgrupo_id, 'TE', 1, 'PR', 2, 'LA', 3, 'SE', 4, 0);
   begin
      fop.block ('Control 1 - Subgrups sense planificar', font_size => 12, font_weight => 'bold',
                 border_bottom => 'solid');

      for x in lista_errores_1 loop
         v_aux := v_aux + 1;
         p (x.asignatura_id || ' ' || x.subgrupo);
      end loop;

      if v_aux = 0 then
         fop.block ('No hi ha cap error.', space_before => 0.5);
      elsif v_aux = 1 then
         fop.block ('Trovat ' || v_aux || ' error.', space_before => 0.5);
      else
         fop.block ('Trovats ' || v_aux || ' errors.', space_before => 0.5);
      end if;
   exception
      when others then
         p (sqlerrm);
   end;

   procedure control_2 is
      v_aux          number        := 0;
      v_asignatura   varchar2 (20);

      cursor lista_errores_2_v1 is
         select   asignatura_id, tipo_subgrupo_id || subgrupo_id subgrupo,
                  decode (dia_semana_id,
                          1, 'Dilluns',
                          2, 'Dimarts',
                          3, 'Dimecres',
                          4, 'Dijous',
                          5, 'Divendres',
                          6, 'Dissabte',
                          7, 'Diumenge',
                          'Error'
                         ) dia_semana,
                  hora_inicio, hora_fin,
                  (select asi2.asignatura_id || ' ' || i2.tipo_subgrupo_id || i2.subgrupo_id grupo
                   from   uji_horarios.hor_items i2,
                          uji_horarios.hor_items_asignaturas asi2
                   where  i.id <> i2.id
                   and    i2.id = asi2.item_id
                   and    i.curso_id = i2.curso_id
                   and    i.semestre_id = i2.semestre_id
                   and    i.grupo_id = i2.grupo_id
                   and    i.tipo_subgrupo_id <> i2.tipo_subgrupo_id
                   and    i.dia_semana_id = i2.dia_semana_id
                   and    asi.estudio_id = asi2.estudio_id
                   and    asi.asignatura_id = asi2.asignatura_id
                   and    (   to_number (to_char (i.hora_inicio, 'hh24mi')) between to_number (to_char (i2.hora_inicio,
                                                                                                        'hh24mi'))
                                                                                and to_number (to_char (i2.hora_fin,
                                                                                                        'hh24mi'))
                           or to_number (to_char (i.hora_fin, 'hh24mi')) between to_number (to_char (i2.hora_inicio,
                                                                                                     'hh24mi'))
                                                                             and to_number (to_char (i2.hora_fin,
                                                                                                     'hh24mi'))
                          )
                   and    rownum <= 1) solape
         from     uji_horarios.hor_items i,
                  uji_horarios.hor_items_asignaturas asi
         where    i.id = asi.item_id
         and      curso_id = pCurso
         and      semestre_id = pSemestre
         and      grupo_id = pGrupo
         and      tipo_subgrupo_id in ('TE')
         and      estudio_id = pEstudio
         and      exists (
                     select 1
                     from   uji_horarios.hor_items i2,
                            uji_horarios.hor_items_asignaturas asi2
                     where  i.id <> i2.id
                     and    i2.id = asi2.item_id
                     and    i.curso_id = i2.curso_id
                     and    i.semestre_id = i2.semestre_id
                     and    i.grupo_id = i2.grupo_id
                     and    i.tipo_subgrupo_id <> i2.tipo_subgrupo_id
                     and    i.dia_semana_id = i2.dia_semana_id
                     and    asi.estudio_id = asi2.estudio_id
                     and    asi.asignatura_id = asi2.asignatura_id
                     and    (   to_number (to_char (i.hora_inicio, 'hh24mi')) between to_number
                                                                                               (to_char (i2.hora_inicio,
                                                                                                         'hh24mi'))
                                                                                  and to_number (to_char (i2.hora_fin,
                                                                                                          'hh24mi'))
                             or to_number (to_char (i.hora_fin, 'hh24mi')) between to_number (to_char (i2.hora_inicio,
                                                                                                       'hh24mi'))
                                                                               and to_number (to_char (i2.hora_fin,
                                                                                                       'hh24mi'))
                            ))
         order by 1,
                  2;

      cursor lista_errores_2 is
         select distinct asignatura_id, tipo_subgrupo_id || subgrupo_id subgrupo,
                         decode (dia_semana_id,
                                 1, 'Dilluns',
                                 2, 'Dimarts',
                                 3, 'Dimecres',
                                 4, 'Dijous',
                                 5, 'Divendres',
                                 6, 'Dissabte',
                                 7, 'Diumenge',
                                 'Error'
                                ) dia_semana,
                         to_char (inicio, 'dd/mm/yyyy') fecha, to_char (inicio, 'hh24:mi') hora_inicio,
                         to_char (fin, 'hh24:mi') hora_fin,
                         (select asi2.asignatura_id || ' ' || i2.tipo_subgrupo_id || i2.subgrupo_id || ' - '
                                 || to_char (inicio, 'hh24:mi') || '-' || to_char (fin, 'hh24:mi') grupo
                          from   uji_horarios.hor_items i2,
                                 uji_horarios.hor_items_asignaturas asi2,
                                 uji_horarios.hor_items_detalle det2
                          where  i.id <> i2.id
                          and    i2.id = det2.item_id
                          and    i2.id = asi2.item_id
                          and    i.curso_id = i2.curso_id
                          and    i.semestre_id = i2.semestre_id
                          and    i.grupo_id = i2.grupo_id
                          and    i.tipo_subgrupo_id <> i2.tipo_subgrupo_id
                          and    i.dia_semana_id = i2.dia_semana_id
                          and    asi.estudio_id = asi2.estudio_id
                          and    asi.asignatura_id = asi2.asignatura_id
                          and    trunc (det.inicio) = trunc (det2.inicio)
                          and    (   to_number (to_char (det.inicio + 1 / 1440, 'hh24mi'))
                                        between to_number (to_char (det2.inicio, 'hh24mi'))
                                            and to_number (to_char (det2.fin, 'hh24mi'))
                                  or to_number (to_char (det.fin - 1 / 1440, 'hh24mi'))
                                        between to_number (to_char (det2.inicio, 'hh24mi'))
                                            and to_number (to_char (det2.fin, 'hh24mi'))
                                 )
                          and    rownum <= 1) solape
         from            uji_horarios.hor_items i,
                         uji_horarios.hor_items_asignaturas asi,
                         uji_horarios.hor_items_detalle det
         where           i.id = asi.item_id
         and             i.id = det.item_id
         and             curso_id = pCurso
         and             semestre_id = pSemestre
         and             grupo_id = pGrupo
         and             tipo_subgrupo_id in ('TE')
         and             estudio_id = pEstudio
         and             exists (
                            select 1
                            from   uji_horarios.hor_items i2,
                                   uji_horarios.hor_items_asignaturas asi2,
                                   uji_horarios.hor_items_detalle det2
                            where  i.id <> i2.id
                            and    i2.id = det2.item_id
                            and    i2.id = asi2.item_id
                            and    i.curso_id = i2.curso_id
                            and    i.semestre_id = i2.semestre_id
                            and    i.grupo_id = i2.grupo_id
                            and    i.tipo_subgrupo_id <> i2.tipo_subgrupo_id
                            and    i.dia_semana_id = i2.dia_semana_id
                            and    asi.estudio_id = asi2.estudio_id
                            and    asi.asignatura_id = asi2.asignatura_id
                            and    trunc (det.inicio) = trunc (det2.inicio)
                            and    (   to_number (to_char (det.inicio + 1 / 1440, 'hh24mi'))
                                          between to_number (to_char (det2.inicio, 'hh24mi'))
                                              and to_number (to_char (det2.fin, 'hh24mi'))
                                    or to_number (to_char (det.fin - 1 / 1440, 'hh24mi'))
                                          between to_number (to_char (det2.inicio, 'hh24mi'))
                                              and to_number (to_char ((det2.fin), 'hh24mi'))
                                   ))
         order by        1,
                         2,
                         4;
   begin
      fop.block ('Control 2 - Solpament subgrups TE amb la resta', font_size => 12, font_weight => 'bold',
                 border_bottom => 'solid', space_before => 0.5);
      v_asignatura := 'PRIMERA';

      for x in lista_errores_2 loop
         v_aux := v_aux + 1;

         if     v_aux <> 1
            and v_asignatura <> x.asignatura_id then
            p (' ');
         end if;

         p (x.asignatura_id || ' ' || x.subgrupo || ' ' || x.dia_semana || ' - ' || x.fecha || ' ' || x.hora_inicio
            || '  ->  solapa amb: ' || x.solape);
         v_asignatura := x.asignatura_id;
      end loop;

      if v_aux = 0 then
         fop.block ('No hi ha cap error.', space_before => 0.5);
      elsif v_aux = 1 then
         fop.block ('Trovat ' || v_aux || ' error.', space_before => 0.5);
      else
         fop.block ('Trovats ' || v_aux || ' errors.', space_before => 0.5);
      end if;
   exception
      when others then
         p (sqlerrm);
   end;

   procedure control_3 is
      v_aux          number        := 0;
      v_asignatura   varchar2 (20);

      cursor lista_asignaturas is
         select distinct asi.asignatura_id
         from            uji_horarios.hor_items i,
                         uji_horarios.hor_items_asignaturas asi,
                         uji_horarios.hor_items_detalle det
         where           i.id = asi.item_id
         and             i.id = det.item_id
         and             curso_id = pCurso
         and             semestre_id = pSemestre
         and             grupo_id = pGrupo
         and             estudio_id = pEstudio
         order by        1;

      cursor lista_errores_3 (p_asignatura in varchar2) is
         select   asignatura_id, tipo_subgrupo_id, subgrupo, creditos, horas_totales, sum (horas_detalle) horas_detalle
         from     (select asi.asignatura_id, tipo_subgrupo_id, tipo_subgrupo_id || subgrupo_id subgrupo,
                          decode (tipo_subgrupo_id,
                                  'TE', crd_te,
                                  'PR', crd_pr,
                                  'LA', crd_la,
                                  'SE', crd_se,
                                  'TU', crd_tu,
                                  crd_ev
                                 ) creditos,
                          decode (tipo_subgrupo_id,
                                  'TE', crd_te,
                                  'PR', crd_pr,
                                  'LA', crd_la,
                                  'SE', crd_se,
                                  'TU', crd_tu,
                                  crd_ev
                                 )
                          * 10 horas_totales,
                          (fin - inicio) * 24 horas_detalle
                   from   uji_horarios.hor_items i,
                          uji_horarios.hor_items_asignaturas asi,
                          uji_horarios.hor_items_detalle det,
                          uji_horarios.hor_ext_asignaturas_detalle ad
                   where  i.id = asi.item_id
                   and    i.id = det.item_id
                   and    curso_id = pCurso
                   and    semestre_id = pSemestre
                   and    grupo_id = pGrupo
                   and    estudio_id = pEstudio
                   and    asi.asignatura_id = ad.asignatura_id
                   and    asi.asignatura_id = p_asignatura)
         group by asignatura_id,
                  tipo_subgrupo_id,
                  subgrupo,
                  creditos
         having   horas_totales not between sum (horas_detalle) * 0.95 and sum (horas_detalle) * 1.05
         order by 1,
                  decode (tipo_subgrupo_id, 'TE', 1, 'PR', 2, 'LA', 3, 'SE', 4, 'TU', 5, 6),
                  3;
   begin
      fop.block ('Control 3 - Control d''hores planificades per subgrup', font_size => 12, font_weight => 'bold',
                 border_bottom => 'solid', space_before => 0.5);
      v_asignatura := 'PRIMERA';

      for asi in lista_asignaturas loop
         for x in lista_errores_3 (asi.asignatura_id) loop
            v_aux := v_aux + 1;

            if     v_aux <> 1
               and v_asignatura <> x.asignatura_id then
               p (' ');
            end if;

            if x.horas_totales > x.horas_detalle then
               p (x.asignatura_id || ' ' || x.subgrupo || ' ' || 'Planificades de menys '
                  || to_char (x.horas_totales - x.horas_detalle) || ' hores, cal planificar-ne ' || x.horas_totales);
            else
               p (x.asignatura_id || ' ' || x.subgrupo || ' ' || 'Planificades de més '
                  || to_char (x.horas_detalle - x.horas_totales) || ' hores, cal planificar-ne ' || x.horas_totales);
            end if;

            v_asignatura := x.asignatura_id;
         end loop;
      end loop;

      if v_aux = 0 then
         fop.block ('No hi ha cap error.', space_before => 0.5);
      elsif v_aux = 1 then
         fop.block ('Trovat ' || v_aux || ' error.', space_before => 0.5);
      else
         fop.block ('Trovats ' || v_aux || ' errors.', space_before => 0.5);
      end if;
   exception
      when others then
         p (sqlerrm);
   end;

   procedure control_4 is
      v_aux          number        := 0;
      v_asignatura   varchar2 (20);

      cursor lista_errores_4 is
         select   asi.asignatura_id, grupo_id, i.tipo_subgrupo_id, subgrupo_id,
                  to_char (hora_inicio, 'hh24:mi') hora_inicio,
                  decode (dia_semana_id,
                          1, 'Dilluns',
                          2, 'Dimarts',
                          3, 'Dimecres',
                          4, 'Dijous',
                          5, 'Divendres',
                          6, 'Dissabte',
                          7, 'Diumenge',
                          'Error'
                         ) dia_semana,
                  dia_semana_id
         from     uji_horarios.hor_items i,
                  uji_horarios.hor_items_asignaturas asi
         where    i.id = asi.item_id
         and      curso_id = pCurso
         and      semestre_id = pSemestre
         and      grupo_id = pGrupo
         and      estudio_id = pEstudio
         and      dia_semana_id is not null
         and      aula_planificacion_id is null
         order by 1,
                  decode (tipo_subgrupo_id, 'TE', 1, 'PR', 2, 'LA', 3, 'SE', 4, 'TU', 5, 6),
                  4;
   begin
      fop.block ('Control 4 - Control d''hores planificades per subgrup', font_size => 12, font_weight => 'bold',
                 border_bottom => 'solid', space_before => 0.5);
      v_asignatura := 'PRIMERA';

      for x in lista_errores_4 loop
         v_aux := v_aux + 1;

         if     v_aux <> 1
            and v_asignatura <> x.asignatura_id then
            p (' ');
         end if;

         p (x.asignatura_id || ' ' || x.tipo_subgrupo_id || x.subgrupo_id || ' ' || x.dia_semana || ' ' || x.hora_inicio
            || ' - no te aula assignada');
         v_asignatura := x.asignatura_id;
      end loop;
   exception
      when others then
         p (sqlerrm);
   end;

   procedure control_5 is
      v_aux          number          := 0;
      v_aula         number;
      v_dia_semana   number;
      v_hora         varchar2 (20);
      v_txt          varchar2 (2000);

      cursor lista_errores_5 is
         select   dia_semana_id, trunc (det.inicio) fecha, to_char (hora_inicio, 'hh24:mi') hora_inicio,
                  aula_planificacion_id,
                  decode (dia_semana_id,
                          1, 'Dilluns',
                          2, 'Dimarts',
                          3, 'Dimecres',
                          4, 'Dijous',
                          5, 'Divendres',
                          6, 'Dissabte',
                          7, 'Diumenge',
                          'Error'
                         ) dia_semana,
                  nvl (a.nombre, 'Aula mal assignada a l''estudi (' || aula_planificacion_id || ')') nombre_aula
         from     uji_horarios.hor_items i,
                  uji_horarios.hor_items_asignaturas asi,
                  uji_horarios.hor_items_detalle det,
                  uji_horarios.hor_aulas a
         where    i.id = asi.item_id
         and      i.id = det.item_id
         and      curso_id = pCurso
         and      semestre_id = pSemestre
         and      grupo_id = pGrupo
         and      estudio_id = pEstudio
         and      dia_semana_id is not null
         and      aula_planificacion_id is not null
         and      aula_planificacion_id = a.id(+)
         group by asi.asignatura_id,
                  grupo_id,
                  dia_semana_id,
                  trunc (det.inicio),
                  to_char (hora_inicio, 'hh24:mi'),
                  aula_planificacion_id,
                  a.nombre
         having   count (*) > 1
         order by 5,
                  1,
                  3,
                  2;

      cursor lista_subgrupos (p_dia_Semana in number, p_fecha in date, p_hora in varchar2, p_aula in number) is
         select   nvl (comun_texto, asignatura_id) asignatura_id, tipo_subgrupo_id || subgrupo_id subgrupo
         from     uji_horarios.hor_items i,
                  uji_horarios.hor_items_asignaturas asi,
                  uji_horarios.hor_items_detalle det
         where    i.id = asi.item_id
         and      i.id = det.item_id
         and      dia_semana_id = p_dia_semana
         and      trunc (inicio) = p_fecha
         and      to_char (hora_inicio, 'hh24:mi') = p_hora
         and      aula_planificacion_id = p_aula
         and      estudio_id = pEstudio
         order by 1,
                  2;
   begin
      fop.block ('Control 5 - Control de solapament en aules', font_size => 12, font_weight => 'bold',
                 border_bottom => 'solid', space_before => 0.5);
      v_aula := 0;
      v_dia_semana := 0;
      v_hora := '';

      for x in lista_errores_5 loop
         v_aux := v_aux + 1;

         if     v_aux <> 1
            and not (    v_aula = x.aula_planificacion_id
                     and v_dia_semana = x.dia_semana_id
                     and v_hora = x.hora_inicio) then
            p (' ');
         end if;

         v_txt := null;

         if 1 = 2 then
            p (x.nombre_aula || ' - ' || to_char (x.fecha, 'dd/mm/yyyy') || ' ' || x.dia_semana || ' ' || x.hora_inicio);

            for det in lista_subgrupos (x.dia_semana_id, x.fecha, x.hora_inicio, x.aula_planificacion_id) loop
               p (espacios (30) || det.asignatura_id || '-' || det.subgrupo);
            end loop;
         else
            for det in lista_subgrupos (x.dia_semana_id, x.fecha, x.hora_inicio, x.aula_planificacion_id) loop
               v_txt := v_txt || det.asignatura_id || '-' || det.subgrupo || ' | ';
            end loop;

            v_txt := trim (v_txt);
            v_txt := substr (v_txt, 1, length (v_txt) - 2);
            p (x.nombre_aula || ' - ' || to_char (x.fecha, 'dd/mm/yyyy') || ' ' || x.dia_semana || ' ' || x.hora_inicio
               || ' ==> ' || v_txt);
         end if;

         v_aula := x.aula_planificacion_id;
         v_dia_semana := x.dia_semana_id;
         v_hora := x.hora_inicio;
      end loop;
   exception
      when others then
         p (sqlerrm);
   end;

   procedure advertencia_1 is
      v_aux             number        := 0;
      v_asignatura      varchar2 (20);
      v_tipo_subgrupo   varchar2 (20);
      v_subgrupo        number;

      cursor lista_advertencia_1 is
         select   asignatura_id, grupo_id, tipo_subgrupo_id, subgrupo_id
         from     uji_horarios.hor_items i,
                  uji_horarios.hor_items_asignaturas asi
         where    i.id = asi.item_id
         and      curso_id = pCurso
         and      semestre_id = pSemestre
         and      grupo_id = pGrupo
         and      estudio_id = pEstudio
         and      dia_semana_id is not null
         and      aula_planificacion_id is not null
         group by asignatura_id,
                  grupo_id,
                  tipo_subgrupo_id,
                  subgrupo_id
         having   count (distinct aula_planificacion_id) > 1;

      cursor lista_aulas (p_asignatura in varchar2, p_tipo_subgrupo in varchar2, p_subgrupo in number) is
         select distinct nvl (a.nombre, 'Aula mal assignada a l''estudi (' || aula_planificacion_id || ')') nombre_aula
         from            uji_horarios.hor_items i,
                         uji_horarios.hor_items_asignaturas asi,
                         uji_horarios.hor_aulas a
         where           i.id = asi.item_id
         and             i.aula_planificacion_id = a.id(+)
         and             curso_id = pCurso
         and             semestre_id = pSemestre
         and             grupo_id = pGrupo
         and             estudio_id = pEstudio
         and             dia_semana_id is not null
         and             aula_planificacion_id is not null
         and             asignatura_id = p_asignatura
         and             tipo_subgrupo_id = p_tipo_subgrupo
         and             subgrupo_id = p_subgrupo
         order by        1;
   begin
      fop.block ('Advertencia 1 - Classes d''un subgrup en aules diferents', font_size => 12, font_weight => 'bold',
                 border_bottom => 'solid', space_before => 0.5);
      v_aux := v_aux + 1;
      v_asignatura := 'PRIMERA';
      v_tipo_subgrupo := 'XX';
      v_subgrupo := 0;

      for x in lista_advertencia_1 loop
         v_aux := v_aux + 1;

         if     v_aux <> 1
            and not (    v_asignatura = x.asignatura_id
                     and v_tipo_subgrupo = x.tipo_subgrupo_id
                     and v_subgrupo = x.subgrupo_id
                    ) then
            p (' ');
         end if;

         for det in lista_aulas (x.asignatura_id, x.tipo_subgrupo_id, x.subgrupo_id) loop
            p (x.asignatura_id || ' - ' || x.tipo_subgrupo_id || x.subgrupo_id || ' ' || det.nombre_aula);
         end loop;

         v_asignatura := x.asignatura_id;
         v_tipo_subgrupo := x.tipo_subgrupo_id;
         v_subgrupo := x.subgrupo_id;
      end loop;
   exception
      when others then
         p (sqlerrm);
   end;
BEGIN
   select nombre
   into   v_nombre
   from   hor_estudios
   where  id = pEstudio;

   cabecera;
   control_1;
   control_2;
   control_3;
   control_4;
   control_5;
   advertencia_1;
   pie;
EXCEPTION
   WHEN OTHERS THEN
      htp.p (sqlerrm);
END VALIDACION_HORARIOS_PDF;
		
		
CREATE OR REPLACE PROCEDURE UJI_HORARIOS.HORARIOS_PDF_SEMGEN (
   name_array    IN   euji_util.ident_arr2,
   value_array   IN   euji_util.ident_arr2
) IS
   -- Parametros
   pEstudio    CONSTANT VARCHAR2 (40) := euji_util.euji_getparam (name_array, value_array, 'pEstudio');
   pSemestre   CONSTANT VARCHAR2 (40) := euji_util.euji_getparam (name_array, value_array, 'pSemestre');
   pGrupo      CONSTANT VARCHAR2 (40) := euji_util.euji_getparam (name_array, value_array, 'pGrupo');
   pCurso      CONSTANT VARCHAR2 (40) := euji_util.euji_getparam (name_array, value_array, 'pCurso');
   pSemana     CONSTANT VARCHAR2 (40) := euji_util.euji_getparam (name_array, value_array, 'pSemana');
   vCursoAcaNum         number;
   vFechaIniSemestre    date;
   vFechaFinSemestre    date;
   vEsGenerica          boolean       := pSemana = 'G';

     -- Variables
   --  vPerId              per_personas.id%TYPE := euji_get_perid ();
   CURSOR c_items_gen IS
      SELECT asignatura_id, asignatura, estudio, caracter, semestre_id, comun, grupo_id, tipo_subgrupo,
             tipo_subgrupo_id, subgrupo_id, dia_semana_id, hora_inicio, hora_fin, tipo_asignatura_id, tipo_asignatura,
             to_date (dia_semana_id + 1 || '/01/2006 ' || to_char (hora_fin, 'hh24:mi'), 'dd/mm/yyyy hh24:mi') fin,
             to_date (dia_semana_id + 1 || '/01/2006 ' || to_char (hora_inicio, 'hh24:mi'),
                      'dd/mm/yyyy hh24:mi') inicio
      FROM   UJI_HORARIOS.HOR_ITEMS items,
             uji_horarios.hor_items_asignaturas asi
      WHERE  items.id = asi.item_id
      and    asi.estudio_id = pEstudio
      AND    items.curso_id = pCurso
      AND    items.grupo_id = pGrupo
      AND    items.semestre_id = pSemestre
      AND    items.dia_semana_id IS NOT NULL;

   CURSOR c_items_det (vIniDate date, vFinDate date) IS
      SELECT   asignatura_id, asignatura, estudio, grupo_id, tipo_subgrupo, tipo_subgrupo_id, subgrupo_id,
               dia_semana_id, hora_inicio, hora_fin, inicio, fin
      FROM     UJI_HORARIOS.HOR_ITEMS items,
               UJI_HORARIOS.HOR_ITEMS_DETALLE det,
               uji_horarios.hor_items_asignaturas asi
      WHERE    items.id = asi.item_id
      and      asi.estudio_id = pEstudio
      AND      items.curso_id = pCurso
      AND      items.grupo_id = pGrupo
      AND      items.semestre_id = pSemestre
      AND      items.dia_semana_id IS NOT NULL
      and      det.item_id = items.id
      and      det.inicio > vIniDate
      and      det.fin < vFinDate
      order by det.inicio;

   CURSOR c_asigs is
      SELECT distinct (asignatura_id), asignatura
      FROM            UJI_HORARIOS.HOR_ITEMS items,
                      uji_horarios.hor_items_asignaturas asi
      WHERE           items.id = asi.item_id
      and             asi.estudio_id = pEstudio
      AND             items.curso_id = pCurso
      AND             items.grupo_id = pGrupo
      AND             items.semestre_id = pSemestre
      AND             items.dia_semana_id IS NOT NULL
      order by        asignatura_id;

   CURSOR c_clases_asig (vAsignaturaId varchar2) is
      SELECT   tipo_subgrupo_id || subgrupo_id subgrupo,
               decode (dia_semana_id, 1, 'L', 2, 'M', 3, 'X', 4, 'J', 5, 'V', '?') dia_sem,
               to_char (hora_fin, 'hh24:mi') hora_fin_str, to_char (hora_inicio, 'hh24:mi') hora_inicio_str
      FROM     UJI_HORARIOS.HOR_ITEMS items,
               uji_horarios.hor_items_asignaturas asi
      WHERE    items.id = asi.item_id
      and      asi.estudio_id = pEstudio
      AND      items.curso_id = pCurso
      AND      items.grupo_id = pGrupo
      AND      items.semestre_id = pSemestre
      and      asi.asignatura_id = vAsignaturaId
      AND      items.dia_semana_id IS NOT NULL
      order by dia_semana_id,
               hora_inicio,
               subgrupo;

   vFechas              t_horario;
   vRdo                 clob;

   function contenido_item (hora_ini date, hora_fin date, texto varchar2, subtexto varchar2)
      return varchar2 is
      contenido   varchar2 (4000);
   begin
      --contenido := '<fo:block font-size="6pt" text-align="center">' || to_char (hora_ini, 'hh24:mi') ||' - '||to_char (hora_fin, 'hh24:mi')||' '||texto||' ('||subtexto||')</fo:block>';
      contenido := '<fo:table border="none" width="100%" height="100%" text-align="start">';
      contenido := contenido || '<fo:table-column column-width="25%"/>';
      contenido := contenido || '<fo:table-column column-width="50%"/>';
      contenido := contenido || '<fo:table-column column-width="25%"/>';
      contenido := contenido || fof.tableBodyOpen;
      contenido := contenido || fof.tableRowOpen;
      contenido := contenido || fof.tableCellOpen;
      contenido :=
         contenido
         || fof.block (to_char (hora_ini, 'hh24:mi'), font_size => 6, text_align => 'start', padding_bottom => 0.3);
      contenido := contenido || fof.tableCellClose;
      contenido := contenido || fof.tableRowClose;
      contenido := contenido || fof.tableRowOpen;
      contenido := contenido || fof.tableCellOpen;
      contenido := contenido || fof.tableCellClose;
      contenido := contenido || fof.tableCellOpen;
      contenido := contenido || fof.block (fof.bold (texto), font_size => 6, text_align => 'center');
      contenido := contenido || fof.block (subtexto, font_size => 5, text_align => 'center');
      contenido := contenido || fof.tableCellClose;
      contenido := contenido || fof.tableRowClose;
      contenido := contenido || fof.tableRowOpen;
      contenido := contenido || fof.tableCellOpen;
      contenido :=
         contenido
         || fof.block (to_char (hora_fin, 'hh24:mi'), font_size => 6, text_align => 'start', padding_top => 0.3);
      contenido := contenido || fof.tableCellClose;
      contenido := contenido || fof.tableRowClose;
      contenido := contenido || fof.tableBodyClose;
      contenido := contenido || fof.tableClose;
      return contenido;
   end;

   procedure cabecera is
   begin
      owa_util.mime_header (ccontent_type => 'text/xml', bclose_header => true);
      fop.documentOpen (page_width => 29.7, page_height => 21, margin_left => 0.5, margin_right => 0.5,
                        margin_top => 0.5, margin_bottom => 0, extent_before => 0, extent_after => 0.5,
                        extent_margin_top => 0, extent_margin_bottom => 0.5);
      fop.pageBodyOpen;
      fop.documentfooteropen;
      fop.block ('Fecha documento: ' || to_char (sysdate, 'yyyy/mm/dd HH24:MI'), font_size => 6,
                 font_style => 'italic');
      fop.documentfooterclose;
      fop.pageFlowOpen;
   end;

   procedure calcula_curso_aca is
   begin
      SELECT   curso_academico_id
      into     vCursoAcaNum
      FROM     hor_semestres_detalle d,
               hor_estudios e
      WHERE    e.id = pEstudio
      AND      e.tipo_id = d.tipo_estudio_id
      AND      ROWNUM = 1
      ORDER BY fecha_inicio;
   end;

   procedure info_calendario_gen is
      vCursoAca           varchar2 (4000);
      vTitulacionNombre   varchar2 (4000);
      vCursoNombre        varchar2 (4000) := pCurso || 'º';
      vTextoCabecera      varchar2 (4000);
   begin
      select nombre
      into   vTitulacionNombre
      from   hor_estudios
      where  id = pEstudio;

      vCursoAca := to_char (vCursoAcaNum) || '/' || to_char (vCursoAcaNum + 1);

      if pCurso in ('1', '3') then
         vCursoNombre := pCurso || 'er';
      elsif pCurso = '2' then
         vCursoNombre := '2on';
      elsif pCurso = '4' then
         vCursoNombre := '4t';
      else
         vCursoNombre := pCurso || 'è';
      end if;

      vTextoCabecera :=
         vTitulacionNombre || ' - ' || vCursoNombre || ' curs - Grup ' || pGrupo || ' (semestre ' || pSemestre || ') - '
         || vCursoAca;
      fop.block (text => vTextoCabecera, border_bottom => 'solid', border_width => 0.05);
   end;

   procedure muestra_leyenda_asig (item c_asigs%ROWTYPE) is
      vClasesText   varchar2 (4000);
   begin
      fop.block (item.asignatura_id || ', ' || item.asignatura || ':', font_size => 7, padding_top => 0.1);
      vClasesText := '<fo:inline color="white">___</fo:inline>';

      for c in c_clases_asig (item.asignatura_id) loop
         vClasesText :=
            vClasesText || c.dia_sem || ', ' || c.hora_inicio_str || '-' || c.hora_fin_str || ' (' || c.subgrupo
            || '); ';
      end loop;

      vClasesText := substr (vClasesText, 1, length (vClasesText) - 2);
      fop.block (vClasesText, font_size => 7, padding_top => 0.06);
   end;

   procedure leyenda_asigs is
   begin
      fop.block (' ', padding_top => 1);

      for item in c_asigs loop
         muestra_leyenda_asig (item);
      end loop;
   end;

   procedure pie is
   begin
      fop.pageFlowClose;
      fop.pageBodyClose;
      fop.documentClose;
   end;

   procedure calendario_gen is
   begin
      vFechas := t_horario ();

      for item in c_items_gen loop
         vFechas.extend;
         vFechas (vFechas.count) :=
            ItemHorarios (contenido_item (item.hora_inicio, item.hora_fin, item.asignatura_id,
                                          '(' || item.tipo_subgrupo_id || item.subgrupo_id || ')'),
                          item.inicio, item.fin, null);
      end loop;

      if vFechas.count > 0 then
         vRdo := xpfpkg_horarios2.drawTimetable (vFechas, 5, null, 'CA', 'PDF_APP_HORARIOS');
         xup_utils.dump (vRdo);
         dbms_lob.freeTemporary (vRdo);
      end if;
   end;

   procedure calendario_det (vFechaIni date, vFechaFin date) is
   begin
      vFechas := t_horario ();

      for item in c_items_det (vFechaIni, vFechaFin) loop
         vFechas.extend;
         vFechas (vFechas.count) :=
            ItemHorarios (contenido_item (item.hora_inicio, item.hora_fin, item.asignatura_id,
                                          '(' || item.tipo_subgrupo_id || item.subgrupo_id || ')'),
                          item.inicio, item.fin, null);
      end loop;

      if vFechas.count > 0 then
         vRdo := xpfpkg_horarios2.drawTimetable (vFechas, 5, null, 'CA', 'PDF_APP_HORARIOS_DET');
         xup_utils.dump (vRdo);
         dbms_lob.freeTemporary (vRdo);
      end if;
   end;

   procedure calcula_fechas_semestre is
   begin
      SELECT fecha_inicio, fecha_examenes_fin
      into   vFechaIniSemestre, vFechaFinSemestre
      FROM   hor_semestres_detalle,
             hor_estudios e
      WHERE  semestre_id = pSemestre
      and    e.id = pEstudio
      and    e.tipo_id = tipo_estudio_id;
   end;

   function semana_tiene_clase (vIniDate date, vFinDate date)
      return boolean is
      num_clases   number;
   begin
      SELECT   count (*)
      into     num_clases
      FROM     UJI_HORARIOS.HOR_ITEMS items,
               UJI_HORARIOS.HOR_ITEMS_DETALLE det,
               uji_horarios.hor_items_asignaturas asi
      WHERE    items.id = asi.item_id
      and      asi.estudio_id = pEstudio
      AND      items.curso_id = pCurso
      AND      items.grupo_id = pGrupo
      AND      items.semestre_id = pSemestre
      AND      items.dia_semana_id IS NOT NULL
      and      det.item_id = items.id
      and      det.inicio > vIniDate
      and      det.fin < vFinDate
      order by det.inicio;

      return num_clases > 0;
   end;

   procedure info_calendario_det (vFechaIni date, vFechaFin date) is
      vCursoAca           varchar2 (4000);
      vTitulacionNombre   varchar2 (4000);
      vCursoNombre        varchar2 (4000) := pCurso || 'º';
      vTextoCabecera      varchar2 (4000);
      vFechaIniStr        varchar2 (4000) := to_char (vFechaIni, 'dd/mm/yyyy');
      vFechaFinStr        varchar2 (4000) := to_char (vFechaFin - 1, 'dd/mm/yyyy');
   begin
      select nombre
      into   vTitulacionNombre
      from   hor_estudios
      where  id = pEstudio;

      vCursoAca := to_char (vCursoAcaNum) || '/' || to_char (vCursoAcaNum + 1);

      if pCurso in ('1', '3') then
         vCursoNombre := pCurso || 'er';
      elsif pCurso = '2' then
         vCursoNombre := '2on';
      elsif pCurso = '4' then
         vCursoNombre := '4t';
      else
         vCursoNombre := pCurso || 'è';
      end if;

      vTextoCabecera :=
         vTitulacionNombre || ' - ' || vCursoNombre || ' curs - Grup ' || pGrupo || ' (semestre ' || pSemestre
         || ') - Del ' || vFechaIniStr || ' al ' || vFechaFinStr;
      fop.block (text => vTextoCabecera, border_bottom => 'solid', border_width => 0.05);
   end;

   procedure paginas_calendario_det is
      vFechaIniSemana   date;
      vFechaFinSemana   date;
   begin
      calcula_fechas_semestre;
      vFechaIniSemana := vFechaIniSemestre;
      vFechaFinSemana := vFechaIniSemana + 7;

      while vFechaFinSemana <= vFechaFinSemestre loop
         if semana_tiene_clase (vFechaIniSemana, vFechaFinSemana) then
            info_calendario_det (vFechaIniSemana, vFechaFinSemana);
            calendario_det (vFechaIniSemana, vFechaFinSemana);
            htp.p ('<fo:block break-before="page" color="white">_</fo:block>');
         end if;

         vFechaIniSemana := vFechaFinSemana;
         vFechaFinSemana := vFechaFinSemana + 7;
      end loop;
   end;
BEGIN
   --euji_control_acceso (vItem);
   calcula_curso_aca;
   cabecera;

   if vEsGenerica then
      info_calendario_gen;
      calendario_gen;
      leyenda_asigs;
   else
      paginas_calendario_det;
      leyenda_asigs;
   end if;

   pie;
EXCEPTION
   WHEN OTHERS THEN
      htp.p (sqlerrm);
END;


CREATE OR REPLACE PROCEDURE UJI_HORARIOS.horarios_ocupacion_aulas_pdf 
(
   name_array    IN   euji_util.ident_arr2,
   value_array   IN   euji_util.ident_arr2
) IS

   pAula    CONSTANT VARCHAR2 (40) := euji_util.euji_getparam (name_array, value_array, 'pAula');
   pSemestre   CONSTANT VARCHAR2 (40) := euji_util.euji_getparam (name_array, value_array, 'pSemestre');   
   pSemana     CONSTANT VARCHAR2 (40) := euji_util.euji_getparam (name_array, value_array, 'pSemana');
   vCursoAcaNum         number;
   vFechaIniSemestre    date;
   vFechaFinSemestre    date;
   vEsGenerica          boolean       := pSemana = 'G';
   vFechas              t_horario;
   vRdo                 clob;
   
   CURSOR c_items_det (vIniDate date, vFinDate date) IS
      SELECT   items.id, items.grupo_id, items.tipo_subgrupo, items.tipo_subgrupo_id, items.subgrupo_id,
            items.dia_semana_id, items.hora_inicio, items.hora_fin, det.inicio, det.fin,  rtrim (xmlagg (xmlelement (e, asi.asignatura_id || ', ')).extract ('//text()'), ', ') as asignatura_id
      FROM     UJI_HORARIOS.HOR_ITEMS items,
               UJI_HORARIOS.HOR_ITEMS_DETALLE det,
               uji_horarios.hor_items_asignaturas asi,
               hor_aulas_planificacion p                                    
      WHERE    items.id = asi.item_id
            AND items.aula_planificacion_id = p.id
            AND p.aula_id = pAula
            AND p.semestre_id = pSemestre
            AND      items.dia_semana_id IS NOT NULL
            and      det.item_id = items.id
            and      det.inicio > vIniDate
            and      det.fin < vFinDate
            group by items.id, items.grupo_id, items.tipo_subgrupo, items.tipo_subgrupo_id, items.subgrupo_id,  items.dia_semana_id, items.hora_inicio, items.hora_fin, det.inicio, det.fin
            order by det.inicio;
            

   CURSOR c_items_gen IS
      SELECT   items.id, items.grupo_id, items.tipo_subgrupo, items.tipo_subgrupo_id, items.subgrupo_id,
            items.dia_semana_id, items.hora_inicio, items.hora_fin,  rtrim (xmlagg (xmlelement (e, asi.asignatura_id || ', ')).extract ('//text()'), ', ') as asignatura_id,
              to_date (dia_semana_id + 1 || '/01/2006 ' || to_char (hora_fin, 'hh24:mi'), 'dd/mm/yyyy hh24:mi') fin,
             to_date (dia_semana_id + 1 || '/01/2006 ' || to_char (hora_inicio, 'hh24:mi'), 'dd/mm/yyyy hh24:mi') inicio
      FROM     UJI_HORARIOS.HOR_ITEMS items,
               uji_horarios.hor_items_asignaturas asi,
               hor_aulas_planificacion p                                    
      WHERE    items.id = asi.item_id
            AND items.aula_planificacion_id = p.id
            AND p.aula_id = pAula
            AND p.semestre_id = pSemestre
            AND      items.dia_semana_id IS NOT NULL            
            group by items.id, items.grupo_id, items.tipo_subgrupo, items.tipo_subgrupo_id, items.subgrupo_id,  items.dia_semana_id, items.hora_inicio, items.hora_fin
            order by items.hora_inicio;            

   CURSOR c_asigs is
      SELECT distinct (asignatura_id), asignatura
      FROM            UJI_HORARIOS.HOR_ITEMS items,
                      uji_horarios.hor_items_asignaturas asi,
                      hor_aulas_planificacion p    
      WHERE           items.id = asi.item_id
            AND items.aula_planificacion_id = p.id
            AND p.aula_id = pAula
            AND p.semestre_id = pSemestre      
          AND             items.dia_semana_id IS NOT NULL
          order by        asignatura_id;

   CURSOR c_clases_asig (vAsignaturaId varchar2) is
      SELECT   tipo_subgrupo_id || subgrupo_id subgrupo,
               decode (dia_semana_id, 1, 'L', 2, 'M', 3, 'X', 4, 'J', 5, 'V', '?') dia_sem,
               to_char (hora_fin, 'hh24:mi') hora_fin_str, to_char (hora_inicio, 'hh24:mi') hora_inicio_str
      FROM     UJI_HORARIOS.HOR_ITEMS items,
               uji_horarios.hor_items_asignaturas asi,
               hor_aulas_planificacion p                   
      WHERE    items.id = asi.item_id
            AND items.aula_planificacion_id = p.id      
            AND p.aula_id = pAula          
          AND      items.semestre_id = pSemestre
          and      asi.asignatura_id = vAsignaturaId
          AND      items.dia_semana_id IS NOT NULL
          order by dia_semana_id,
                   hora_inicio,
                   subgrupo;   
                   
function semana_tiene_clase (vIniDate date, vFinDate date)
      return boolean is
      num_clases   number;
   begin
      SELECT   count(*)
      into     num_clases
      FROM     UJI_HORARIOS.HOR_ITEMS items,
               UJI_HORARIOS.HOR_ITEMS_DETALLE det,
               hor_aulas_planificacion p                                    
      WHERE     items.aula_planificacion_id = p.id
            AND p.aula_id = pAula
            AND p.semestre_id = pSemestre
            AND      items.dia_semana_id IS NOT NULL
            and      det.item_id = items.id
            and      det.inicio > vIniDate
            and      det.fin < vFinDate;
            

      return num_clases > 0;
   end;                   
            
procedure calcula_curso_aca is
   begin    
      SELECT   distinct(curso_academico_id)
      into     vCursoAcaNum      
      FROM     hor_semestres_detalle d,
               hor_estudios e
      WHERE    ROWNUM = 1
      ORDER BY curso_academico_id;
   end;
   
   procedure calcula_fechas_semestre is
   begin      
      SELECT fecha_inicio, fecha_examenes_fin
      into   vFechaIniSemestre, vFechaFinSemestre
      FROM   hor_semestres_detalle,
             hor_estudios e
      WHERE  semestre_id = pSemestre
             and rownum = 1;
   end;
   
   function contenido_item (hora_ini date, hora_fin date, texto varchar2, subtexto varchar2)
      return varchar2 is
      contenido   varchar2 (4000);
   begin
      --contenido := '<fo:block font-size="6pt" text-align="center">' || to_char (hora_ini, 'hh24:mi') ||' - '||to_char (hora_fin, 'hh24:mi')||' '||texto||' ('||subtexto||')</fo:block>';
      contenido := '<fo:table border="none" width="100%" height="100%" text-align="start">';
      contenido := contenido || '<fo:table-column column-width="25%"/>';
      contenido := contenido || '<fo:table-column column-width="50%"/>';
      contenido := contenido || '<fo:table-column column-width="25%"/>';
      contenido := contenido || fof.tableBodyOpen;
      contenido := contenido || fof.tableRowOpen;
      contenido := contenido || fof.tableCellOpen;
      contenido :=
         contenido
         || fof.block (to_char (hora_ini, 'hh24:mi'), font_size => 6, text_align => 'start', padding_bottom => 0.3);
      contenido := contenido || fof.tableCellClose;
      contenido := contenido || fof.tableRowClose;
      contenido := contenido || fof.tableRowOpen;
      contenido := contenido || fof.tableCellOpen;
      contenido := contenido || fof.tableCellClose;
      contenido := contenido || fof.tableCellOpen;
      contenido := contenido || fof.block (fof.bold (texto), font_size => 6, text_align => 'center');
      contenido := contenido || fof.block (subtexto, font_size => 5, text_align => 'center');
      contenido := contenido || fof.tableCellClose;
      contenido := contenido || fof.tableRowClose;
      contenido := contenido || fof.tableRowOpen;
      contenido := contenido || fof.tableCellOpen;
      contenido :=
         contenido
         || fof.block (to_char (hora_fin, 'hh24:mi'), font_size => 6, text_align => 'start', padding_top => 0.3);
      contenido := contenido || fof.tableCellClose;
      contenido := contenido || fof.tableRowClose;
      contenido := contenido || fof.tableBodyClose;
      contenido := contenido || fof.tableClose;
      return contenido;
   end;
   
   procedure calendario_det (vFechaIni date, vFechaFin date) is
   begin
      vFechas := t_horario ();

      for item in c_items_det (vFechaIni, vFechaFin) loop
         vFechas.extend;
         vFechas (vFechas.count) :=
            ItemHorarios (contenido_item (item.hora_inicio, item.hora_fin, item.asignatura_id || ' ' || item.grupo_id,
                                          '(' || item.tipo_subgrupo_id || item.subgrupo_id || ')'),
                          item.inicio, item.fin, null);
      end loop;

      if vFechas.count > 0 then
         vRdo := xpfpkg_horarios2.drawTimetable (vFechas, 5, null, 'CA', 'PDF_APP_HORARIOS_DET');
         xup_utils.dump (vRdo);
         dbms_lob.freeTemporary (vRdo);
      end if;
   end;   
 
   procedure calendario_gen is
   begin
      vFechas := t_horario ();

      for item in c_items_gen loop
         vFechas.extend;
         vFechas (vFechas.count) :=
            ItemHorarios (contenido_item (item.hora_inicio, item.hora_fin, item.asignatura_id || ' ' || item.grupo_id,
                                          '(' || item.tipo_subgrupo_id || item.subgrupo_id || ')'),
                          item.inicio, item.fin, null);
      end loop;

      if vFechas.count > 0 then
         vRdo := xpfpkg_horarios2.drawTimetable (vFechas, 5, null, 'CA', 'PDF_APP_HORARIOS_DET');
         xup_utils.dump (vRdo);
         dbms_lob.freeTemporary (vRdo);
      end if;
   end;    

   
   procedure info_calendario_det (vFechaIni date, vFechaFin date) is
      vCursoAca           varchar2 (4000);   
      vTextoCabecera      varchar2 (4000);
      vFechaIniStr        varchar2 (4000) := to_char (vFechaIni, 'dd/mm/yyyy');
      vFechaFinStr        varchar2 (4000) := to_char (vFechaFin - 1, 'dd/mm/yyyy');
      vAulaNombre         varchar2 (4000);
      vEdificio         varchar2 (4000);
      vPlanta         varchar2 (4000);
      vCentroNombre         varchar2 (4000);
   begin      

      vCursoAca := to_char (vCursoAcaNum) || '/' || to_char (vCursoAcaNum + 1);
      
      SELECT  a.nombre AS aulaNombre,
         a.edificio,
         a.planta,
         c.nombre AS centroNombre
      INTO vAulaNombre, vEdificio, vPlanta, vCentroNombre
      FROM   hor_aulas a, hor_centros c
      WHERE   a.id = pAula AND c.id = a.centro_id;
      
      vTextoCabecera :=
         vAulaNombre || ' - ' || vCentroNombre || ' - Edifici ' || vEdificio || ' - Planta '||vPlanta ||'  - (semestre ' || pSemestre || ') - Del ' || vFechaIniStr || ' al ' || vFechaFinStr;
      fop.block (text => vTextoCabecera, border_bottom => 'solid', border_width => 0.05);
   end;


procedure info_calendario_gen  is
      vCursoAca           varchar2 (4000);   
      vTextoCabecera      varchar2 (4000);      
      vAulaNombre         varchar2 (4000);
      vEdificio         varchar2 (4000);
      vPlanta         varchar2 (4000);
      vCentroNombre         varchar2 (4000);
   begin      

      vCursoAca := to_char (vCursoAcaNum) || '/' || to_char (vCursoAcaNum + 1);
      
      SELECT  a.nombre AS aulaNombre,
         a.edificio,
         a.planta,
         c.nombre AS centroNombre
      INTO vAulaNombre, vEdificio, vPlanta, vCentroNombre
      FROM   hor_aulas a, hor_centros c
      WHERE   a.id = pAula AND c.id = a.centro_id;
      
      vTextoCabecera :=
         vAulaNombre || ' - ' || vCentroNombre || ' - Edifici ' || vEdificio || ' - Planta '||vPlanta ||'  - (semestre ' || pSemestre || ') ';
      fop.block (text => vTextoCabecera, border_bottom => 'solid', border_width => 0.05);
   end;
   
procedure paginas_calendario_det is
      vFechaIniSemana   date;
      vFechaFinSemana   date;
   begin
      calcula_fechas_semestre;
      vFechaIniSemana := vFechaIniSemestre;
      vFechaFinSemana := vFechaIniSemana + 7;

      while vFechaFinSemana <= vFechaFinSemestre loop
         if semana_tiene_clase (vFechaIniSemana, vFechaFinSemana) then
            info_calendario_det (vFechaIniSemana, vFechaFinSemana);
            calendario_det (vFechaIniSemana, vFechaFinSemana);
            htp.p ('<fo:block break-before="page" color="white">_</fo:block>');
         end if;

         vFechaIniSemana := vFechaFinSemana;
         vFechaFinSemana := vFechaFinSemana + 7;
      end loop;
   end;  
   
procedure cabecera is
   begin
      owa_util.mime_header (ccontent_type => 'text/xml', bclose_header => true);
      fop.documentOpen (page_width => 29.7, page_height => 21, margin_left => 0.5, margin_right => 0.5,
                        margin_top => 0.5, margin_bottom => 0, extent_before => 0, extent_after => 0.5,
                        extent_margin_top => 0, extent_margin_bottom => 0.5);
      fop.pageBodyOpen;
      fop.documentfooteropen;
      fop.block ('Fecha documento: ' || to_char (sysdate, 'yyyy/mm/dd HH24:MI'), font_size => 6,
                 font_style => 'italic');
      fop.documentfooterclose;
      fop.pageFlowOpen;
   end;   
   
   
procedure muestra_leyenda_asig (item c_asigs%ROWTYPE) is
      vClasesText   varchar2 (4000);
   begin
      fop.block (item.asignatura_id || ', ' || item.asignatura || ':', font_size => 7, padding_top => 0.1);
      vClasesText := '<fo:inline color="white">___</fo:inline>';

      for c in c_clases_asig (item.asignatura_id) loop
         vClasesText :=
            vClasesText || c.dia_sem || ', ' || c.hora_inicio_str || '-' || c.hora_fin_str || ' (' || c.subgrupo
            || '); ';
      end loop;

      vClasesText := substr (vClasesText, 1, length (vClasesText) - 2);
      fop.block (vClasesText, font_size => 7, padding_top => 0.06);
   end;

   procedure leyenda_asigs is
   begin
      fop.block (' ', padding_top => 1);

      for item in c_asigs loop
         muestra_leyenda_asig (item);
      end loop;
   end;   
   
procedure pie is
   begin
      fop.pageFlowClose;
      fop.pageBodyClose;
      fop.documentClose;
   end;   
   
   
      
BEGIN
   calcula_curso_aca;
   cabecera;

   if vEsGenerica then
      info_calendario_gen;
      calendario_gen;
      leyenda_asigs;
   else
        paginas_calendario_det;
        leyenda_asigs;
   end if;

   pie;
EXCEPTION
   WHEN OTHERS THEN
      htp.p (sqlerrm);
END;



		