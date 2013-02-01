Ext.define('HOR.store.StoreGrupos',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.Grupo',

    proxy :
    {
        type : 'rest',
        url : '/hor/rest/grupo',

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