Ext.define('HOR.model.SemestreDetalle',
{
    extend : 'Ext.data.Model',

    
    fields : [ 'id', 
               {name: 'fechaFin', type : 'date', dateFormat : 'd/m/Y H:i:s' },               
               'tipoEstudioId', 
               'nombreSemestre', 
               'nombreTipoEstudio', 
               'numeroSemanas', 
               {name: 'fechaInicio', type : 'date', dateFormat : 'd/m/Y H:i:s' },
               {name: 'fechaExamenesFin', type : 'date', dateFormat : 'd/m/Y H:i:s' },
               {name: 'fechaExamenesInicio', type : 'date', dateFormat : 'd/m/Y H:i:s' }
     		]
});