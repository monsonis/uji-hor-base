Ext.define('HOR.store.StoreEdificios',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.Edificio',
    autoLoad : false,
    autoSync : false,
    proxy :
    {
        type : 'rest',
        url : '/hor/rest/edificio',

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
