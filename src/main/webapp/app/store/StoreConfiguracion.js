Ext.define('HOR.store.StoreConfiguracion',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.Configuracion',

    proxy :
    {
        type : 'rest',
        url : '/hor/rest/calendario/config',

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