Ext.define('HOR.store.StoreGruposAsignaturasSinAsignar',
{
    extend : 'Ext.data.Store',
    model : 'HOR.model.GrupoAsignatura',

    autoLoad : true,
    autoSync : true,

    proxy :
    {
        type : 'rest',
        url : '/apa/rest/grupoAsignatura/sinAsignar',

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