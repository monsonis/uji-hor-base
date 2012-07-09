Ext.define('HOR.store.StoreSemestres',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.Semestre',

    proxy :
    {
        type : 'rest',
        url : '/hor/rest/semestre',

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