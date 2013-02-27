Ext.define('HOR.store.StoreGruposAsignaturasSinAsignar',
{
    extend : 'Ext.data.Store',
    autoLoad : false,
    autoSync : false,
    model : 'HOR.model.GrupoAsignatura',
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