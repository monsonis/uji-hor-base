CREATE OR REPLACE FUNCTION UJI_HORARIOS.hor_contar_festivos (
   p_f_ini               date,
   p_f_fin               date,
   p_dia_semana     in   number,
   p_cada_semanas   in   number
)
   RETURN NUMBER IS
   v_rdo   NUMBER;
BEGIN
   v_rdo := 0;

   select count (*)
   into   v_rdo
   from   (select id, dia, mes, año, dia_semana, dia_semana_id, tipo_dia, fecha, vacaciones
           from   hor_ext_calendario c2
           where  c2.fecha between p_f_ini and p_f_fin
           and    tipo_dia = 'F'
           and    vacaciones = 0
           and    dia_semana_id = p_dia_semana
           and    p_cada_semanas = 1
           union all
           select id, dia, mes, año, dia_semana, dia_semana_id, tipo_dia, fecha, vacaciones
           from   (select id, dia, mes, año, dia_semana, dia_semana_id, tipo_dia, fecha, vacaciones,
                          row_number () over (partition by dia_semana_id order by fecha) orden
                   from   hor_ext_calendario c2
                   where  c2.fecha between p_f_ini and p_f_fin
                   and    dia_semana_id = p_dia_semana
                   and    vacaciones = 0
                   and    p_cada_semanas = 2) x
           where  tipo_dia = 'F'
           and    decode (mod (orden, 2), 1, 'S', 'N') = 'S'
           union all
           select id, dia, mes, año, dia_semana, dia_semana_id, tipo_dia, fecha, vacaciones
           from   (select id, dia, mes, año, dia_semana, dia_semana_id, tipo_dia, fecha, vacaciones,
                          row_number () over (partition by dia_semana_id order by fecha) orden
                   from   hor_ext_calendario c2
                   where  c2.fecha between p_f_ini and p_f_fin
                   and    dia_semana_id = p_dia_semana
                   and    vacaciones = 0
                   and    p_cada_semanas = 3) x
           where  tipo_dia = 'F'
           and    decode (mod (orden, 3), 1, 'S', 'N') = 'S'
           union all
           select id, dia, mes, año, dia_semana, dia_semana_id, tipo_dia, fecha, vacaciones
           from   (select id, dia, mes, año, dia_semana, dia_semana_id, tipo_dia, fecha, vacaciones,
                          row_number () over (partition by dia_semana_id order by fecha) orden
                   from   hor_ext_calendario c2
                   where  c2.fecha between p_f_ini and p_f_fin
                   and    dia_semana_id = p_dia_semana
                   and    vacaciones = 0
                   and    p_cada_semanas = 4) x
           where  tipo_dia = 'F'
           and    decode (mod (orden, 4), 1, 'S', 'N') = 'S');

   RETURN v_rdo;
END hor_contar_festivos;



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
         and             dia_semana_id is null
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


