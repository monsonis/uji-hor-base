Ext.define('HOR.store.StoreGruposAsignaturasSinAsignar',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.GrupoAsignatura',

    autoLoad : false,
    autoSync : false,

    proxy :
    {
        type : 'rest',
        url : '/hor/rest/grupoAsignatura/sinAsignar',

        reader :
        {
            type : 'json',
            successProperty : 'success',
            root : 'data'
        },

        writer :
        {
            type : 'json',
            successProperty : 'success'
        }
    }

});