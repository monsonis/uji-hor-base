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
           and    dia_semana_id = p_dia_semana
           and    p_cada_semanas = 1
           union all
           select id, dia, mes, año, dia_semana, dia_semana_id, tipo_dia, fecha, vacaciones
           from   (select id, dia, mes, año, dia_semana, dia_semana_id, tipo_dia, fecha, vacaciones,
                          row_number () over (partition by dia_semana_id order by fecha) orden
                   from   hor_ext_calendario c2
                   where  c2.fecha between p_f_ini and p_f_fin
                   and    dia_semana_id = p_dia_semana
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
                   and    p_cada_semanas = 4) x
           where  tipo_dia = 'F'
           and    decode (mod (orden, 4), 1, 'S', 'N') = 'S');

   RETURN v_rdo;
END hor_contar_festivos;




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
               dia_semana_id, hora_inicio, hora_fin,
               to_date (dia_semana_id + 1 || '/01/2006 ' || to_char (hora_fin, 'hh24:mi'), 'dd/mm/yyyy hh24:mi') fin,
               to_date (dia_semana_id + 1 || '/01/2006 ' || to_char (hora_inicio, 'hh24:mi'),
                        'dd/mm/yyyy hh24:mi') inicio
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
   -- Temporalmente mientras estemos con datos de prueba
   --   vFechaIni date := vFechaIni2 - 365;
   --   vFechaFin date := vFechaFin2 - 365;
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
         vRdo := xpfpkg_horarios2.drawTimetable (vFechas, 5, null, 'CA', 'PDF_APP_HORARIOS');
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
     -- Temporalmente mientras estemos con datos de prueba
   -- vIniDate date := vIniDate2 - 365;
   -- vFinDate date := vFinDate2 - 365;
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
