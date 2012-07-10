Ext.define('HOR.model.Curso',
{
    extend : 'Ext.data.Model',

    fields : [ 'id', 'curso' ],
    validations : [
    {
        type : 'length',
        field : 'id',
        min : 1
    } ]
});