Ext.define('HOR.store.StoreEstudiosGestion',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.Estudio',

    proxy :
    {
        type : 'rest',
        url : '/hor/rest/estudio/gestion',

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