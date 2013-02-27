Ext.define('HOR.store.StoreTiposAula',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.TipoAula',
    autoLoad : false,
    autoSync : false,
    proxy :
    {
        type : 'rest',
        url : '/hor/rest/aula/tipo',

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