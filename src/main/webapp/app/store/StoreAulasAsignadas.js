Ext.define('HOR.store.StoreAulasAsignadas',
{
    extend : 'Ext.data.Store',
    autoLoad : false,
    autoSync : true,    
    model: 'HOR.model.AulaPlanificacion',
    proxy :
    {
        type : 'rest',
        url : '/hor/rest/aula/estudio/',

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