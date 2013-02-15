Ext.define('HOR.store.StoreSemestresAulas',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.Semestre',
    autoLoad : false,
    autoSync : false,
    proxy :
    {
        type : 'rest',
        url : '/hor/rest/semestre/aulas',

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