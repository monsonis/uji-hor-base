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


