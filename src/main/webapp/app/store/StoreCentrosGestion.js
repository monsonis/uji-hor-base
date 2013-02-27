Ext.define('HOR.store.StoreCentrosGestion',
{

    extend : 'Ext.data.Store',
    model : 'HOR.model.Centro',
    proxy :
    {
        type : 'rest',
        url : '/hor/rest/centro/gestion',

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