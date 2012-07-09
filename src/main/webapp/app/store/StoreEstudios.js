Ext.define('HOR.store.StoreEstudios',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.Estudio',

    proxy :
    {
        type : 'rest',
        url : '/hor/rest/estudio',

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