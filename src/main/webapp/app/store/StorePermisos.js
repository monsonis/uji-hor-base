Ext.define('HOR.store.StorePermisos',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.Permiso',

    proxy :
    {
        type : 'rest',
        url : '/hor/rest/permisoExtra',

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