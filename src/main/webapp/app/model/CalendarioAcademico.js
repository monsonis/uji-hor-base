Ext.define('HOR.model.CalendarioAcademico',
{
    extend : 'Ext.data.Model',
    fields : [ 'id', 'dia', 'mes', 'anyo', 'diaSemana', 'diaSemanaId', 'tipoDia',
    {
        name : 'fecha',
        type : 'date',
        dateFormat : 'd/m/Y H:i:s'
    }, 'vacaciones' ]
});