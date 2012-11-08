Ext.define('HOR.model.SemestreDetalle',
{
    extend : 'Ext.data.Model',

    
    fields : [ 'id', 
               {name: 'fecha_fin', type : 'date', dateFormat : 'd/m/Y H:i:s' },               
               'tipo_estudioId', 
               'nombreSemestre', 
               'nombreTipoEstudio', 
               'numero_semanas', 
               {name: 'fecha_inicio', type : 'date', dateFormat : 'd/m/Y H:i:s' },
               {name: 'fecha_examenes_fin', type : 'date', dateFormat : 'd/m/Y H:i:s' },
               {name: 'fecha_examenes_inicio', type : 'date', dateFormat : 'd/m/Y H:i:s' }
     		]
});