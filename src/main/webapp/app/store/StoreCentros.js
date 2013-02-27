Ext.define('HOR.store.StoreCentros',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.Centro',
    autoLoad : false,
    autoSync : false,
    proxy :
    {
        type : 'rest',
        url : '/hor/rest/centro',

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