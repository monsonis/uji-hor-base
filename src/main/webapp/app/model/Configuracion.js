Ext.define('HOR.model.Configuracion',
{
    extend : 'Ext.data.Model',

    fields : [ 'estudioId', 'semestreId', 'cursoId', 'grupoId', 'horaInicio', 'horaFin' ],
    validations : [
    {
        type : 'length',
        field : 'id',
        min : 1
    } ]
});