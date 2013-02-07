Ext.define('HOR.store.StoreEstudiosTodos',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.Estudio',

    proxy :
    {
        type : 'rest',
        url : '/hor/rest/estudio/todos',

        reader :
        {
            type : 'json',
            successProperty : 'success',
            root : 'data'
        },

        writer :
        {
            type : 'json'
        }
    }

});