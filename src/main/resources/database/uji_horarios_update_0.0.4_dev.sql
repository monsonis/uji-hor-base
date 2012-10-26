ALTER TABLE UJI_HORARIOS.HOR_ITEMS
 ADD (repetir_cada_semanas  NUMBER);

ALTER TABLE UJI_HORARIOS.HOR_ITEMS
 ADD (numero_iteraciones  NUMBER);

ALTER TABLE UJI_HORARIOS.HOR_ITEMS
 ADD (detalle_manual  NUMBER                        DEFAULT 0                     NOT NULL);
 
 