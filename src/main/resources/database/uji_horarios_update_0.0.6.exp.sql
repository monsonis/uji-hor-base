ALTER TABLE UJI_HORARIOS.HOR_ITEMS ADD (comun_texto  VARCHAR2(1000));

update hor_items
set comun_texto = (select distinct nombre
                   from            gra_pod.pod_grp_comunes
                   where           nombre like '%' || i.asignatura_id || '%'
                   and             curso_Aca = 2013)
where  comun = 1;

commit;


grant select on gra_pod.pod_grp_comunes to uji_horarios;
grant select on gra_pod.pod_comunes to uji_horarios;

create or replace view hot_ext_asignaturas_comunes (id, grupo_comun_id, nombre, curso_academico_id, asignatura_id) as
select rownum id, id grupo_comun_id, nombre, curso_aca curso_academico_id, asi_id asignatura_id
from   pod_grp_comunes g,
       pod_comunes c
where  g.id = c.gco_id;

