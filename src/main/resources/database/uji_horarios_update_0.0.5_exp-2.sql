CREATE OR REPLACE TRIGGER uji_horarios.hor_items_delete
   before delete
   on uji_horarios.hor_items
   referencing new as new old as old
   for each row
begin
   delete      uji_horarios.hor_items_detalle
   where       item_id = :old.id;
end hor_items_delete;


CREATE OR REPLACE PACKAGE UJI_HORARIOS.MUTANTE_ITEMS IS
TYPE TABLA_MUT IS TABLE OF rowid INDEX BY BINARY_INTEGER;
V_VAR_TABLA TABLA_MUT;
V_NUM NUMBER;  
END;



CREATE OR REPLACE TRIGGER UJI_HORARIOS.MUTANTE_1_INICIAL BEFORE INSERT OR UPDATE ON UJI_HORARIOS.HOR_ITEMS
BEGIN
  MUTANTE_ITEMS.V_NUM := 0;
END;


CREATE OR REPLACE TRIGGER uji_horarios.mutante_2_por_fila
   after insert or update of dia_semana_id, desde_el_dia, hasta_el_dia, repetir_cada_semanas, numero_iteraciones
   on uji_horarios.hor_items
   referencing old as new new as old
   for each row
begin
   mutante_items.v_num := mutante_items.v_num + 1;
   mutante_items.v_var_tabla (mutante_items.v_num) := :new.rowid;
end;


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
         select *
         from   uji_horarios.hor_v_items_detalle
         where  id = p_id
         and    docencia = 'S';
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
                                    (id, item_id, inicio, fin
                                    )
                        values      (v_aux, v_reg.id, x.fecha, x.fecha
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


