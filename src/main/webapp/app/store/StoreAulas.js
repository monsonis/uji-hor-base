Ext.define('HOR.store.StoreAulas',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.Aula',
    autoLoad : false,
    autoSync : false,
    proxy :
    {
        type : 'rest',
        url : '/hor/rest/aula',

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