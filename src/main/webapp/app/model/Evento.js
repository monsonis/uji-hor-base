Ext.define('HOR.model.Evento',
{
    extend : 'Ext.data.Model',

    fields : [ 'id', 'title', 'cid', 'start', 'end' ],

    validations : [
    {
        type : 'length',
        field : 'title',
        min : 1
    } ]
});
