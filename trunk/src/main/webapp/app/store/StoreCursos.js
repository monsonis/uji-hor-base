Ext.define('HOR.store.StoreCursos',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.Curso',

    proxy :
    {
        type : 'rest',
        url : '/hor/rest/curso',

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