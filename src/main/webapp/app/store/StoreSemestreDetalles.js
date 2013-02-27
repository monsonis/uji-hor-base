Ext.define('HOR.store.StoreSemestreDetalles',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.SemestreDetalle',
    autoLoad : true,

    sorters : [
    {
        property : 'nombreTipoEstudio'
    } ],

    proxy :
    {
        type : 'rest',
        url : '/hor/rest/semestredetalle',

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