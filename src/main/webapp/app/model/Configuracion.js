Ext.define('HOR.model.Configuracion',
{
    extend : 'Ext.data.Model',

    fields : [ 'estudioId', 'semestreId', 'cursoId', 'grupoId', 'inicio', 'fin' ],
    validations : [
    {
        type : 'length',
        field : 'id',
        min : 1
    } ]
});