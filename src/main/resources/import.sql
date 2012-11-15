
-- Datos para (al menos) es.uji.apps.hor.services.rest.SemestreDetalleResourceTest
-- HOR_TIPOS_ESTUDIOS
Insert into HOR_TIPOS_ESTUDIOS (ID, NOMBRE, ORDEN) Values  ('G', 'Graus', 1);
Insert into HOR_TIPOS_ESTUDIOS  (ID, NOMBRE, ORDEN) Values   ('12C', 'Primer i segon cicle', 2);
Insert into HOR_TIPOS_ESTUDIOS   (ID, NOMBRE, ORDEN) Values   ('M', 'Màsters', 3);


-- HOR_SEMESTRES
Insert into HOR_SEMESTRES   (ID, NOMBRE) Values   (1, 'Primer semestre');
Insert into HOR_SEMESTRES   (ID, NOMBRE) Values   (2, 'Segon semestre');

   
-- HOR_SEMESTRES_DETALLE    
Insert into HOR_SEMESTRES_DETALLE   (ID, SEMESTRE_ID, TIPO_ESTUDIO_ID, FECHA_INICIO, FECHA_FIN, FECHA_EXAMENES_INICIO, FECHA_EXAMENES_FIN, NUMERO_SEMANAS) Values  (1, 1, '12C', parsedatetime('09/20/2011 00:00:00', 'dd/MM/yyyy HH:mm:ss'), parsedatetime('12/23/2011 00:00:00', 'dd/MM/yyyy HH:mm:ss'),  NULL, NULL, 14);
Insert into HOR_SEMESTRES_DETALLE   (ID, SEMESTRE_ID, TIPO_ESTUDIO_ID, FECHA_INICIO, FECHA_FIN, FECHA_EXAMENES_INICIO, FECHA_EXAMENES_FIN, NUMERO_SEMANAS) Values   (2, 2, '12C', parsedatetime('01/25/2012 00:00:00', 'dd/MM/yyyy HH:mm:ss'), parsedatetime('05/18/2012 00:00:00', 'dd/MM/yyyy HH:mm:ss'), NULL, NULL, 17);
Insert into HOR_SEMESTRES_DETALLE   (ID, SEMESTRE_ID, TIPO_ESTUDIO_ID, FECHA_INICIO, FECHA_FIN, FECHA_EXAMENES_INICIO, FECHA_EXAMENES_FIN, NUMERO_SEMANAS) Values  (3, 1, 'G', parsedatetime('09/20/2011 00:00:00', 'dd/MM/yyyy HH:mm:ss'), parsedatetime('01/24/2012 00:00:00', 'dd/MM/yyyy HH:mm:ss'), NULL, NULL, 18);
Insert into HOR_SEMESTRES_DETALLE   (ID, SEMESTRE_ID, TIPO_ESTUDIO_ID, FECHA_INICIO, FECHA_FIN, FECHA_EXAMENES_INICIO, FECHA_EXAMENES_FIN, NUMERO_SEMANAS) Values   (4, 2, 'G', parsedatetime('01/25/2012 00:00:00', 'dd/MM/yyyy HH:mm:ss'), parsedatetime('06/08/2012 00:00:00', 'dd/MM/yyyy HH:mm:ss'), NULL, NULL, 20);
