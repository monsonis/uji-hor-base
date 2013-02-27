Ext.define('HOR.store.StoreCargos',
{

    extend : 'Ext.data.Store',
    model : 'HOR.model.Cargo',
    autoLoad : false,
    proxy :
    {
        type : 'rest',
        url : '/hor/rest/persona/cargos',

        reader :
        {
            type : 'json',
            successProperty : 'success',
            root : 'data'
        },

        extraParams :
        {
            estudioId : null
        },
        writer :
        {
            type : 'json'
        }
    }

});